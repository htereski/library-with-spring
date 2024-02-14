package com.example.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.dtos.AuthenticationRecordDTO;
import com.example.library.dtos.UserRecordDTO;
import com.example.library.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRecordDTO authenticationDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.email(),
                authenticationDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserRecordDTO userRecordDTO) {

        if (userService.register(userRecordDTO) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exist.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("User created.");
    }
}
