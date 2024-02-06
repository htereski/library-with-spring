package com.example.library.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookResponseDTO {
    private UUID id;

    private String language;

    private String publisher;

    private String subject;

    private String title;
}
