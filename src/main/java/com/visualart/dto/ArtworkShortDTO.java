package com.visualart.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Short DTO for artwork listing")
public record ArtworkShortDTO(
        @Schema(description = "ID of the artwork", example = "1")
        Long id,

        @Schema(description = "Title of the artwork", example = "Starry Night")
        String title,

        @Schema(description = "Year created", example = "1889")
        Integer yearCreated,

        @Schema(description = "Artist name", example = "Vincent van Gogh")
        String artistName
) {}
