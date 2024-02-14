package com.example.library.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.library.models.entities.Book;
import com.example.library.models.response.BookResponse;
import com.example.library.models.response.BookResponsePage;
import com.example.library.services.BookService;

import jakarta.validation.Valid;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/books")
    public ResponseEntity<BookResponse> saveBook(@RequestBody @Valid BookRecordDTO bookRecordDTO) {

        Book book = bookService.saveBook(bookRecordDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.transformToResponse(book));
    }

    @GetMapping("/books/page")
    public ResponseEntity<Page<BookResponse>> getAllBooks(@RequestBody BookResponsePage bookResponsePage) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks(bookResponsePage));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Object> getOneBook(@PathVariable(value = "id") UUID id) {

        BookResponse book = bookService.getOneBook(id);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid BookRecordDTO bookRecordDTO) {

        BookResponse book = bookService.updateBook(id, bookRecordDTO);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value = "id") UUID id) {
        if (!bookService.deleteBook(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Book deleted sucessfully.");
    }
}
