package com.visualart.dto;

import java.util.List;

public record ArtistResponseDTO(
        Long id,
        String name,
        Integer birthYear,
        Integer deathYear,
        String nationality,
        List<String> fields,
        List<String> affiliatedSchools
) {}
