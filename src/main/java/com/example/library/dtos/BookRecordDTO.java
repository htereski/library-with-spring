package com.example.library.dtos;

import com.example.library.models.entities.Author;
import com.example.library.models.entities.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRecordDTO(@NotBlank String title, @NotBlank String language, @NotBlank String publisher,
        @NotBlank String subject, @NotNull Author author, @NotNull User user) {

}
