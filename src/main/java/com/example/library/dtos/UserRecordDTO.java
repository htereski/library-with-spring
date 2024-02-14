package com.example.library.dtos;

import com.example.library.models.entities.UserRole;

import jakarta.validation.constraints.NotBlank;

public record UserRecordDTO(@NotBlank String name, @NotBlank String email, @NotBlank String password,
                UserRole role) {
}
