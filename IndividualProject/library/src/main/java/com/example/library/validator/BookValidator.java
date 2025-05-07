package com.example.library.validator;

import com.example.library.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class BookValidator {

    public static List<String> validate(Book book) {
        List<String> errors = new ArrayList<>();

        if (!isValidText(book.getName())) {
            errors.add("Book name is required.");
        }

        if (!isValidText(book.getAuthor())) {
            errors.add("Author name is required.");
        }

        if (!isValidYear(book.getYear())) {
            errors.add("Year must be between 1400 and the current year.");
        }

        if (!isValidText(book.getGenre())) {
            errors.add("Genre is required.");
        }

        if (!isValidPages(book.getPages())) {
            errors.add("Pages must be a number between 1 and 10,000.");
        }

        return errors;
    }

    private static boolean isValidText(String text) {
        return text != null && !text.trim().isEmpty();
    }

    private static boolean isValidYear(Integer year) {
        if (year == null) return false;
        int currentYear = java.time.Year.now().getValue();
        return year > 1400 && year <= currentYear;
    }

    private static boolean isValidPages(Integer pages) {
        return pages != null && pages > 0 && pages < 10_000;
    }
}


