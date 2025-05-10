package com.example.library.dto;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String name;
    private String author;
    private Integer year;
    private String genre;
    private Integer pages;
}
