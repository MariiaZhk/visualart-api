package com.visualart.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for creating or updating an artist")
public record ArtistRequestDTO(
        @NotBlank(message = "Name is required")
        @Schema(description = "Name of the artist", example = "Vincent van Gogh")
        String name
) {}
