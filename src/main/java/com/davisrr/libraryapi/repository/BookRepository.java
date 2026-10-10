package com.davisrr.libraryapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.davisrr.libraryapi.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByTitleIgnoreCaseAndAuthorIgnoreCase(String title, String author);

    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String titleFragment, String authorFragment);
}
