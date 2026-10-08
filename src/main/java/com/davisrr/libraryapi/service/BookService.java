package com.davisrr.libraryapi.service;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.stereotype.Service;
import com.davisrr.libraryapi.dto.BookResponse;
import com.davisrr.libraryapi.dto.CreateBookRequest;
import com.davisrr.libraryapi.entity.Book;
import com.davisrr.libraryapi.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse register(CreateBookRequest request) {
        String title = request.title().trim();
        String author = request.author().trim();
        if (bookRepository.existsByTitleIgnoreCaseAndAuthorIgnoreCase(title, author)) {
            throw new IllegalArgumentException("Book already exists");
        }
        Book book = new Book(title, author);
        book = bookRepository.save(book);
        return new BookResponse(book);
    }

    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream()
                .map(BookResponse::new)
                .sorted(Comparator.comparing(BookResponse::id))
                .collect(Collectors.toList());
    }
}
