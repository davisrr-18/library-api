package com.davisrr.libraryapi.service;

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.davisrr.libraryapi.dto.CreateReaderRequest;
import com.davisrr.libraryapi.dto.ReaderResponse;
import com.davisrr.libraryapi.entity.Reader;
import com.davisrr.libraryapi.exception.ReaderNotFoundException;
import com.davisrr.libraryapi.repository.ReaderRepository;

@Service
public class ReaderService {

    private final ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public ReaderResponse register(CreateReaderRequest request) {
        String name = request.name().trim();
        if (readerRepository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Reader already exists");
        }
        Reader reader = new Reader(name);
        reader = readerRepository.save(reader);
        return new ReaderResponse(reader);
    }

    public ReaderResponse findById(Long id) {
        return readerRepository.findById(id)
                .map(ReaderResponse::new)
                .orElseThrow(() -> new ReaderNotFoundException(id));
    }

    public List<ReaderResponse> findAll() {
        return readerRepository.findAll().stream()
                .map(ReaderResponse::new)
                .sorted(Comparator.comparing(ReaderResponse::id))
                .collect(Collectors.toList());
    }
}
