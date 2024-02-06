package com.example.library.models.response;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponse {

    private UUID id;

    private int num;
}
