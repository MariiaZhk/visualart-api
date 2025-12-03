package com.visualart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ArtworkRequestDTO(
        @NotBlank(message = "Title is required")
        String title,
        Integer yearCreated,
        List<String> genres,
        List<String> media,
        @NotNull(message = "Artist ID is required")
        Long artistId
) {}
