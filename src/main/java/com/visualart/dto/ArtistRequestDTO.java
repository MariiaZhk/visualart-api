package com.visualart.dto;

import jakarta.validation.constraints.NotBlank;

public record ArtistRequestDTO(
        @NotBlank(message = "Name is required")
        String name
) {}
