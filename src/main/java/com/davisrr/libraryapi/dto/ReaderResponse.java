package com.davisrr.libraryapi.dto;

import com.davisrr.libraryapi.entity.Reader;

public record ReaderResponse(Long id, String name) {

    public ReaderResponse(Reader reader) {
        this(reader.getId(), reader.getName());
    }
}
