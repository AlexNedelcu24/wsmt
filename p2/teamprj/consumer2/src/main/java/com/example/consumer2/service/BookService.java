package com.example.consumer2.service;

import com.example.consumer2.domain.Book;
import com.example.consumer2.exception.DuplicateResourceException;
import com.example.consumer2.exception.ResourceNotFoundException;
import com.example.consumer2.exception.ValidationException;
import com.example.consumer2.repository.BookRepository;
import com.example.consumer2.validator.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Book findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public Book save(Book book) {
        List<String> errors = BookValidator.validate(book);
        if (!errors.isEmpty()) {
            throw new ValidationException(String.join(" ", errors));
        }

        if (repository.existsByNameAndAuthor(book.getName(), book.getAuthor())) {
            throw new DuplicateResourceException("A book with the same name and author already exists.");
        }

        return repository.save(book);
    }


    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found");
        }
        repository.deleteById(id);
    }


    public Book update(Book book, Long id) {
        List<String> errors = BookValidator.validate(book);
        if (!errors.isEmpty()) {
            throw new ValidationException(String.join(" ", errors));
        }

        return repository.findById(id)
                .map(existingBook -> {
                    existingBook.setName(book.getName());
                    existingBook.setAuthor(book.getAuthor());
                    existingBook.setYear(book.getYear());
                    existingBook.setGenre(book.getGenre());
                    existingBook.setPages(book.getPages());
                    return repository.save(existingBook);
                }).orElseThrow(() ->
                        new ResourceNotFoundException("Book not found")
                );
    }
}

