package com.example.library.controller;

import com.example.library.domain.Book;
import com.example.library.domain.Message;
import com.example.library.dto.BookDTO;
import com.example.library.mapper.BookMapper;
import com.example.library.rabbitmq.RabbitMqSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin
public class BookController {

    private final RabbitMqSender rabbitMqSender;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BookController(RabbitMqSender rabbitMqSender) {
        this.rabbitMqSender = rabbitMqSender;
    }

    @GetMapping
    public List<BookDTO> getAllBooks() throws JsonProcessingException {
        String response = rabbitMqSender.send(new Message("READ", ""));
        BookDTO[] bookDTOS = objectMapper.readValue(response, BookDTO[].class);
        return Arrays.asList(bookDTOS);
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id) throws JsonProcessingException {
        BookDTO dto = new BookDTO();
        dto.setId(id);
        String response = rabbitMqSender.send(new Message("READID", objectMapper.writeValueAsString(dto)));
        return objectMapper.readValue(response, BookDTO.class);
    }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookDTO bookDTO) {
        try {
            String response = rabbitMqSender.send(new Message("CREATE", objectMapper.writeValueAsString(bookDTO)));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@RequestBody BookDTO bookDTO, @PathVariable Long id) throws JsonProcessingException {
        bookDTO.setId(id);
        String response = rabbitMqSender.send(new Message("UPDATE", objectMapper.writeValueAsString(bookDTO)));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) throws JsonProcessingException {
        BookDTO dto = new BookDTO();
        dto.setId(id);
        rabbitMqSender.send(new Message("DELETE", objectMapper.writeValueAsString(dto)));
        return ResponseEntity.ok("Book deleted");
    }
}
