package com.example.library.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.dtos.BookRecordDTO;
import com.example.library.dtos.BookResponseDTO;
import com.example.library.models.entities.Book;
import com.example.library.models.response.BookResponse;
import com.example.library.repositories.BookRepository;

import jakarta.validation.Valid;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @SuppressWarnings("null")
    @PostMapping("/books")
    public ResponseEntity<Book> saveBook(@RequestBody @Valid BookRecordDTO bookRecordDTO) {

        Book book = new Book();

        BeanUtils.copyProperties(bookRecordDTO, book);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookRepository.save(book));
    }

    @GetMapping("/books/page")
    public ResponseEntity<Page<BookResponseDTO>> getAllBooks(@RequestBody BookResponse bookResponse) {

        Pageable pageable = PageRequest.of(bookResponse.getNum(), 2);

        Page<BookResponseDTO> books = bookRepository.findBooksByUser(bookResponse.getId(), pageable);
    
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @SuppressWarnings("null")
    @GetMapping("/books/{id}")
    public ResponseEntity<Object> getOneBook(@PathVariable(value = "id") UUID id) {

        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(book.get());
    }

    @SuppressWarnings("null")
    @PutMapping("/books/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid BookRecordDTO bookRecordDTO) {

        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }

        Book auxBook = book.get();

        BeanUtils.copyProperties(bookRecordDTO, auxBook);

        return ResponseEntity.status(HttpStatus.OK).body(bookRepository.save(auxBook));
    }

    @SuppressWarnings("null")
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value = "id") UUID id) {

        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }

        bookRepository.delete(book.get());

        return ResponseEntity.status(HttpStatus.OK).body("Book deleted sucessfully.");
    }
}
