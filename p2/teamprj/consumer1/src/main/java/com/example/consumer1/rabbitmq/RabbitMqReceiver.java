package com.example.consumer1.rabbitmq;

import com.example.consumer1.domain.Book;
//import com.example.consumer1.domain.Message;
import com.example.consumer1.dto.BookDTO;
import com.example.consumer1.mapper.BookMapper;
import com.example.consumer1.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RabbitListener(queues = "${spring.rabbitmq.queue}")
public class RabbitMqReceiver {

    private final BookService bookService;
    private final BookMapper bookMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RabbitMqReceiver(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @RabbitHandler
    public String receiveMessage(com.example.library.domain.Message message) throws JsonProcessingException {
        return switch (message.getOperation()) {
            case "CREATE" -> handleCreate(message);
            case "READ" -> handleRead();
            case "READID" -> handleReadById(message);
            case "UPDATE" -> handleUpdate(message);
            case "DELETE" -> handleDelete(message);
            default -> "Unknown operation";
        };
    }

    private String handleCreate(com.example.library.domain.Message message) throws JsonProcessingException {
        BookDTO dto = objectMapper.readValue(message.getPayload(), BookDTO.class);
        Book saved = bookService.save(bookMapper.toEntity(dto));
        return objectMapper.writeValueAsString(bookMapper.toDTO(saved));
    }

    private String handleRead() throws JsonProcessingException {
        List<Book> books = bookService.findAll();
        List<BookDTO> dtos = books.stream().map(bookMapper::toDTO).collect(Collectors.toList());
        return objectMapper.writeValueAsString(dtos);
    }

    private String handleReadById(com.example.library.domain.Message message) throws JsonProcessingException {
        BookDTO dto = objectMapper.readValue(message.getPayload(), BookDTO.class);
        Book found = bookService.findById(dto.getId());
        return objectMapper.writeValueAsString(bookMapper.toDTO(found));
    }

    private String handleUpdate(com.example.library.domain.Message message) throws JsonProcessingException {
        BookDTO dto = objectMapper.readValue(message.getPayload(), BookDTO.class);
        Book updated = bookService.update(bookMapper.toEntity(dto), dto.getId());
        return objectMapper.writeValueAsString(bookMapper.toDTO(updated));
    }

    private String handleDelete(com.example.library.domain.Message message) throws JsonProcessingException {
        BookDTO dto = objectMapper.readValue(message.getPayload(), BookDTO.class);
        bookService.delete(dto.getId());
        return "";
    }

}
