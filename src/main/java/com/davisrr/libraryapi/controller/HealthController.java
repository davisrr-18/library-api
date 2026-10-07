package com.davisrr.libraryapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davisrr.libraryapi.dto.HealthResponse;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    public HealthResponse health() {
        return new HealthResponse("UP");
    }
}
