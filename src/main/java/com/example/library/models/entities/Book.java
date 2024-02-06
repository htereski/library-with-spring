package com.example.library.models.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String title;

    @NotNull
    private String language;

    @NotNull
    private String publisher;

    @NotNull
    private String subject;

    @ManyToOne
    @NotNull
    private Author author;

    @ManyToOne
    @NotNull
    private User user;

}
