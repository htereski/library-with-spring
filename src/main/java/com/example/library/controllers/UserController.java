package com.example.library.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.dtos.UserRecordDTO;
import com.example.library.models.entities.User;
import com.example.library.models.response.UserResponse;
import com.example.library.services.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/page/{num}")
    public ResponseEntity<Page<UserResponse>> getAllUsers(@PathVariable(value = "num") int page) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersPageable(page));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") UUID id) {

        Optional<User> user = userService.getById(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userService.transformToResponse(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid UserRecordDTO userRecordDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userRecordDTO, id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id) {

        if (userService.deleteUser(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("User deleted sucessfully.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

}
