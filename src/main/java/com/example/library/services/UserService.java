package com.example.library.services;

import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.library.dtos.UserRecordDTO;
import com.example.library.models.entities.User;
import com.example.library.models.response.UserResponse;
import com.example.library.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User register(UserRecordDTO userRecordDTO) {

        if (userRepository.findByEmail(userRecordDTO.email()) != null) {
            return null;
        }

        User user = new User(userRecordDTO.name(), userRecordDTO.email(), encryptPassword(userRecordDTO.password()),
                userRecordDTO.role());

        userRepository.save(user);

        return user;
    }

    private String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public Page<UserResponse> getUsersPageable(int page) {

        Pageable pageable = PageRequest.of(page, 2);

        return userRepository.findUsers(pageable);
    }

    @SuppressWarnings("null")
    public Optional<User> getById(UUID id) {

        Optional<User> user = userRepository.findById(id);

        return user;
    }

    public Optional<UserResponse> transformToResponse(Optional<User> user) {
        if (user == null) {
            return null;
        }

        Optional<UserResponse> userResponse = Optional
                .of(new UserResponse(user.get().getId(), user.get().getName(), user.get().getEmail()));

        return userResponse;
    }

    @SuppressWarnings("null")
    @Transactional
    public Optional<UserResponse> updateUser(UserRecordDTO userRecordDTO, UUID id) {

        Optional<User> aux = getById(id);

        if (aux.isEmpty()) {
            return null;
        }

        User user = aux.get();

        if (userRecordDTO.password() != null) {
            String newPassword = encryptPassword(userRecordDTO.password());
            user.setPassword(newPassword);
        }

        BeanUtils.copyProperties(user, userRecordDTO);

        userRepository.save(user);

        return Optional.of(new UserResponse(id, userRecordDTO.name(), userRecordDTO.email()));
    }

    @SuppressWarnings("null")
    public boolean deleteUser(UUID id) {

        Optional<User> user = getById(id);

        if (user.isEmpty()) {
            return false;
        }

        userRepository.delete(user.get());
        return true;
    }

}
