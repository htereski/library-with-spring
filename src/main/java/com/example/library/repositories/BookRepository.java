package com.example.library.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.library.models.entities.Book;
import com.example.library.models.response.BookResponse;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    @Query("SELECT new com.example.library.models.response.BookResponse(b.id, b.title, a.name, b.language, b.publisher, b.subject) FROM Book b JOIN b.author a WHERE b.user.id = ?1")
    Page<BookResponse> findBooksByUser(UUID userId, Pageable pageable);

}
