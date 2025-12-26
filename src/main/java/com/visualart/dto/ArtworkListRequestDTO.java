package com.visualart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;

@Schema(description = "Request DTO for artworks list with filters and pagination")
public record ArtworkListRequestDTO(
        @Schema(description = "Artist ID to filter by", example = "2")
        Long artistId,

        @Schema(description = "Part of title to filter by (contains, case-insensitive)", example = "starry")
        String title,

        @Schema(description = "Year to filter by", example = "1889")
        Integer yearCreated,

        @Schema(description = "Page number (1-based)", example = "1")
        int page,

        @Max(value = 100, message = "Page size cannot exceed 100")
        @Schema(description = "Page size", example = "20")
        int size,

        @Schema(description = "Sort field", example = "title")
        String sortBy,

        @Schema(description = "Sort direction: asc|desc", example = "asc")
        String sortDir
) {}
