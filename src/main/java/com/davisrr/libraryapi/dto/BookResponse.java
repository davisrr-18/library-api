package com.davisrr.libraryapi.dto;

import com.davisrr.libraryapi.entity.Book;

public record BookResponse(
        Long id,
        String title,
        String author,
        boolean available,
        Long borrowedReaderId) {

    public BookResponse(Book book) {
        this(book.getId(), book.getTitle(), book.getAuthor(), book.isAvailable(), book.getBorrowedReaderId());
    }
}
