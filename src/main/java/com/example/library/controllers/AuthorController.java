package com.example.library.controllers;

import java.util.List;
import java.util.UUID;

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
import com.example.library.services.AuthorService;

import jakarta.validation.Valid;

@RestController
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping("/authors")
    public ResponseEntity<Author> saveAuthor(@RequestBody @Valid AuthorRecordDTO authorRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.saveAuthor(authorRecordDTO));
    }

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.status(HttpStatus.OK).body(authorService.getAllAuthors());
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Object> getOneAuthor(@PathVariable(value = "id") UUID id) {

        Author author = authorService.getOneAuthor(id);

        if (author == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid AuthorRecordDTO authorRecordDTO) {

        Author author = authorService.updateAuthor(authorRecordDTO, id);

        if (author == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable(value = "id") UUID id) {

        if (authorService.deleteAuthor(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Author deleted sucessfully.");
    }
}
