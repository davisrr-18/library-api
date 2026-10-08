package com.davisrr.libraryapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private boolean available = true;

    private Long borrowedReaderId;

    protected Book() {
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;
        this.borrowedReaderId = null;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public Long getBorrowedReaderId() {
        return borrowedReaderId;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setBorrowedReaderId(Long borrowedReaderId) {
        this.borrowedReaderId = borrowedReaderId;
    }
}
