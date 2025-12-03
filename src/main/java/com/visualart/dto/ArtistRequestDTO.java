package com.visualart.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record ArtistRequestDTO(
        @NotBlank(message = "Name is required")
        String name,
        Integer birthYear,
        Integer deathYear,
        String nationality,
        List<String> fields,
        List<String> affiliatedSchools
) {}
