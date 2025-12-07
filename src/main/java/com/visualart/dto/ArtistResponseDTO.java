package com.visualart.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO representing an artist in responses")
public record ArtistResponseDTO(
        @Schema(description = "Unique identifier of the artist", example = "1")
        Long id,

        @Schema(description = "Name of the artist", example = "Vincent van Gogh")
        String name
) {}
