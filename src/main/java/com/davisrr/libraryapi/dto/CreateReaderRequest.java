package com.davisrr.libraryapi.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateReaderRequest(@NotBlank String name) {
}
