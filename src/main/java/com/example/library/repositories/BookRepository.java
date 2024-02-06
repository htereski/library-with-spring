package com.example.library.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.library.dtos.BookResponseDTO;
import com.example.library.models.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    @Query("SELECT new com.example.library.dtos.BookResponseDTO(b.id, b.language, b.publisher, b.subject, b.title) FROM Book b WHERE b.user.id = ?1")
    Page<BookResponseDTO> findBooksByUser(UUID userId, Pageable pageable);
    
}
