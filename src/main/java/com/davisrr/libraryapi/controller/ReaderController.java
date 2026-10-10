package com.davisrr.libraryapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.davisrr.libraryapi.dto.CreateReaderRequest;
import com.davisrr.libraryapi.dto.ReaderResponse;
import com.davisrr.libraryapi.service.ReaderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/readers")
public class ReaderController {

    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReaderResponse register(@Valid @RequestBody CreateReaderRequest request) {
        return readerService.register(request);
    }

    @GetMapping("/{id}")
    public ReaderResponse findById(@PathVariable Long id) {
        return readerService.findById(id);
    }

    @GetMapping
    public List<ReaderResponse> list() {
        return readerService.findAll();
    }
}
