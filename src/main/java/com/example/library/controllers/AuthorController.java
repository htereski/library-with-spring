package com.example.library.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
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

import com.example.library.dtos.AuthorRecordDTO;
import com.example.library.models.entities.Author;
import com.example.library.repositories.AuthorRepository;

import jakarta.validation.Valid;

@RestController
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @SuppressWarnings("null")
    @PostMapping("/authors")
    public ResponseEntity<Author> saveAuthor(@RequestBody @Valid AuthorRecordDTO authorRecordDTO) {

        var author = new Author();

        BeanUtils.copyProperties(authorRecordDTO, author);

        return ResponseEntity.status(HttpStatus.CREATED).body(authorRepository.save(author));
    }

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.status(HttpStatus.OK).body(authorRepository.findAll());
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Object> getOneAuthor(@PathVariable(value = "id") UUID id) {

        @SuppressWarnings("null")
        Optional<Author> author = authorRepository.findById(id);

        if (author.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(author.get());
    }

    @SuppressWarnings("null")
    @PutMapping("/authors/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid AuthorRecordDTO authorRecordDTO) {

        Optional<Author> author = authorRepository.findById(id);

        if (author.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found.");
        }

        var authorFinal = author.get();
        BeanUtils.copyProperties(authorRecordDTO, authorFinal);
        return ResponseEntity.status(HttpStatus.OK).body(authorRepository.save(authorFinal));
    }

    @SuppressWarnings("null")
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable(value = "id") UUID id) {

        Optional<Author> author = authorRepository.findById(id);

        if (author.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found.");
        }

        authorRepository.delete(author.get());

        return ResponseEntity.status(HttpStatus.OK).body("Author deleted sucessfully.");
    }
}
