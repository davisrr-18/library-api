package com.davisrr.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.davisrr.libraryapi.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByTitleIgnoreCaseAndAuthorIgnoreCase(String title, String author);
}
