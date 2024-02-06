package com.example.library.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthorRecordDTO(@NotBlank String name) {
    
}
