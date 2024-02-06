package com.example.library.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.library.dtos.UserResponseDTO;
import com.example.library.models.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT new com.example.library.dtos.UserResponseDTO(u.id, u.name, u.email) FROM User u")
    Page<UserResponseDTO> findUsers(Pageable pageable);

    @Query("SELECT new com.example.library.dtos.UserResponseDTO(u.id, u.name, u.email) FROM User u WHERE u.id = ?1")
    Optional<UserResponseDTO> findByUserId(UUID id);
}
