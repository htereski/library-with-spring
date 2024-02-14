package com.example.library.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRecordDTO(@NotBlank String title, @NotBlank String language, @NotBlank String publisher,
        @NotBlank String subject, @NotNull String author, @NotNull String user) {

}
