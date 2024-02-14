package com.example.library.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.library.dtos.BookRecordDTO;
import com.example.library.models.entities.Author;
import com.example.library.models.entities.Book;
import com.example.library.models.entities.User;
import com.example.library.models.response.BookResponse;
import com.example.library.models.response.BookResponsePage;
import com.example.library.repositories.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorService authorService;

    public Book saveBook(BookRecordDTO bookRecordDTO) {

        Book book = new Book();

        Optional<User> user = userService.getById(UUID.fromString(bookRecordDTO.user()));

        if (user.isEmpty()) {
            return null;
        }

        Optional<Author> author = authorService.getById(UUID.fromString(bookRecordDTO.author()));

        if (author.isEmpty()) {
            return null;
        }

        book.setAuthor(author.get());
        book.setUser(user.get());

        BeanUtils.copyProperties(bookRecordDTO, book);

        return bookRepository.save(book);
    }

    public BookResponse transformToResponse(Book book) {
        if (book == null) {
            return null;
        }

        BookResponse bookResponse = new BookResponse(book.getId(), book.getTitle(), book.getAuthor().getName(),
                book.getLanguage(), book.getPublisher(), book.getSubject());

        return bookResponse;
    }

    public Page<BookResponse> getAllBooks(BookResponsePage bookResponsePage) {
        Pageable pageable = PageRequest.of(bookResponsePage.getNum(), 2);

        Page<BookResponse> books = bookRepository.findBooksByUser(bookResponsePage.getId(), pageable);

        return books;
    }

    @SuppressWarnings("null")
    public BookResponse getOneBook(UUID id) {

        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            return null;
        }

        BookResponse bookResponse = new BookResponse(id, book.get().getTitle(), book.get().getLanguage(),
                book.get().getLanguage(), book.get().getPublisher(), book.get().getSubject());

        return bookResponse;
    }

    @SuppressWarnings("null")
    public BookResponse updateBook(UUID id, BookRecordDTO bookRecordDTO) {

        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            return null;
        }

        BeanUtils.copyProperties(bookRecordDTO, book.get());

        bookRepository.save(book.get());

        BookResponse bookResponse = new BookResponse(id, book.get().getTitle(), book.get().getLanguage(),
                book.get().getLanguage(), book.get().getPublisher(), book.get().getSubject());

        return bookResponse;
    }

    @SuppressWarnings("null")
    public boolean deleteBook(UUID id) {

        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            return false;
        }

        bookRepository.delete(book.get());

        return true;
    }
}
