package com.example.library.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {

    private UUID id;

    private String name;

    private String email;
}
