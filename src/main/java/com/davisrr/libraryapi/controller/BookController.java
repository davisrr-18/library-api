package com.davisrr.libraryapi.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.davisrr.libraryapi.dto.BookResponse;
import com.davisrr.libraryapi.dto.CreateBookRequest;
import com.davisrr.libraryapi.service.BookService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse register(@Valid @RequestBody CreateBookRequest request) {
        return bookService.register(request);
    }

    @GetMapping
    public List<BookResponse> list(@RequestParam(required = false) String search) {
        if (search == null) {
            return bookService.findAll();
        }
        return bookService.searchByTerm(search);
    }
}
