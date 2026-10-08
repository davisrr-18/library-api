package com.davisrr.libraryapi.controller;

// Fase 1 — complete after BookService works:
// POST /api/v1/books -> 201 Created + BookResponse body (@Valid on request)
// GET  /api/v1/books -> 200 OK + JSON array of BookResponse

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<BookResponse> list() {
        return bookService.findAll();
    }
}
