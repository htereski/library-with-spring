package com.example.library.models.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookResponse {
    private UUID id;
    private String title;
    private String author;
    private String language;
    private String publisher;
    private String subject;
}
