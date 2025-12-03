package com.visualart.dto;

import java.util.List;

public record ArtworkResponseDTO(
        Long id,
        String title,
        Integer yearCreated,
        List<String> genres,
        List<String> media,
        ArtistResponseDTO artist
) {}
