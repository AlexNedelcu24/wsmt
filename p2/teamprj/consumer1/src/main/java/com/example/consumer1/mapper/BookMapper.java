package com.example.consumer1.mapper;

import com.example.consumer1.domain.Book;
import com.example.consumer1.dto.BookDTO;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setAuthor(book.getAuthor());
        dto.setYear(book.getYear());
        dto.setGenre(book.getGenre());
        dto.setPages(book.getPages());
        return dto;
    }

    public Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setName(dto.getName());
        book.setAuthor(dto.getAuthor());
        book.setYear(dto.getYear());
        book.setGenre(dto.getGenre());
        book.setPages(dto.getPages());
        return book;
    }
}
