package com.visualart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for creating or updating an artist")
public record ArtistRequestDTO(
    

        @NotBlank(message = "Name is required")
          @Schema(description = "Name of the artist", example = "Vincent van Gogh")
@Size(max = 100, message = "Artist name must be at most 100 characters")
String name
) {}
