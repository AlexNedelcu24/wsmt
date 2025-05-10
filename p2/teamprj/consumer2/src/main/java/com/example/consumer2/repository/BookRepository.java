package com.example.consumer2.repository;

import com.example.consumer2.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByNameAndAuthor(String name, String author);
}
