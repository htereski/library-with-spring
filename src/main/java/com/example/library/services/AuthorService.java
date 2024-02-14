package com.example.library.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.library.dtos.AuthorRecordDTO;
import com.example.library.models.entities.Author;
import com.example.library.repositories.AuthorRepository;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @SuppressWarnings("null")
    @Transactional
    public Author saveAuthor(AuthorRecordDTO authorRecordDTO) {
        Author author = new Author();

        BeanUtils.copyProperties(authorRecordDTO, author);

        authorRepository.save(author);

        return author;
    }

    @SuppressWarnings("null")
    public Optional<Author> getById(UUID id) {
        Optional<Author> author = authorRepository.findById(id);

        if (author.isEmpty()) {
            return null;
        }

        return author;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getOneAuthor(UUID id) {
        Optional<Author> author = getById(id);

        if (author.isEmpty()) {
            return null;
        }

        return author.get();
    }

    @SuppressWarnings("null")
    @Transactional
    public Author updateAuthor(AuthorRecordDTO authorRecordDTO, UUID id) {

        Optional<Author> auxAuthor = getById(id);

        if (auxAuthor.isEmpty()) {
            return null;
        }

        Author newAuthor = auxAuthor.get();
        BeanUtils.copyProperties(authorRecordDTO, newAuthor);
        authorRepository.save(newAuthor);

        return newAuthor;
    }

    @SuppressWarnings("null")
    public Object deleteAuthor(UUID id) {
        Optional<Author> author = getById(id);

        if (author.isEmpty()) {
            return null;
        }

        authorRepository.delete(author.get());

        return "Sucess";
    }
}
