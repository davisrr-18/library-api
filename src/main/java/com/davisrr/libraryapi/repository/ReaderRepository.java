package com.davisrr.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.davisrr.libraryapi.entity.Reader;

public interface ReaderRepository extends JpaRepository<Reader, Long> {

    boolean existsByNameIgnoreCase(String name);
}
