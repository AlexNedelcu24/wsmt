package com.example.consumer1.repository;

import com.example.consumer1.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByNameAndAuthor(String name, String author);
}
