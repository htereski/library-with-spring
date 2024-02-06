package com.example.library.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.dtos.UserRecordDTO;
import com.example.library.dtos.UserResponseDTO;
import com.example.library.models.entities.User;
import com.example.library.repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("null")
    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserRecordDTO userRecordDTO) {
        var user = new User();

        BeanUtils.copyProperties(userRecordDTO, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }

    @GetMapping("/users/page/{num}")
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(@PathVariable(value = "num") int page) {

        Pageable pageable = PageRequest.of(page, 2);
        
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findUsers(pageable));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") UUID id) {

        Optional<UserResponseDTO> user = userRepository.findByUserId(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @SuppressWarnings("null")
    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid UserRecordDTO userRecordDTO) {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        User auxUser = user.get();

        BeanUtils.copyProperties(userRecordDTO, auxUser);

        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(auxUser));
    }

    @SuppressWarnings("null")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id) {
        
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        userRepository.delete(user.get());

        return ResponseEntity.status(HttpStatus.OK).body("User deleted sucessfully.");
    }

}
