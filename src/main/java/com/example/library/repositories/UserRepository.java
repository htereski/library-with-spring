package com.example.library.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.library.models.entities.User;
import com.example.library.models.response.UserResponse;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    UserDetails findByEmail(String email);

    @Query("SELECT new com.example.library.models.response.UserResponse(u.id, u.name, u.email) FROM User u")
    Page<UserResponse> findUsers(Pageable pageable);

    @Query("SELECT new com.example.library.models.response.UserResponse(u.id, u.name, u.email) FROM User u WHERE u.id = ?1")
    Optional<UserResponse> findByUserId(UUID id);
}
