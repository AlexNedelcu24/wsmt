package com.example.library.controller;

import com.example.library.domain.Book;
import com.example.library.dto.BookDTO;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.exception.ValidationException;
import com.example.library.mapper.BookMapper;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    @Autowired
    private BookMapper mapper;

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return service.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id) {
        return mapper.toDTO(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        Book savedBook = service.save(mapper.toEntity(bookDTO));
        return new ResponseEntity<>(mapper.toDTO(savedBook), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@RequestBody BookDTO bookDTO, @PathVariable Long id) {
        Book updatedBook = service.update(mapper.toEntity(bookDTO), id);
        return mapper.toDTO(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Book with ID " + id + " has been successfully deleted.");
    }
}




