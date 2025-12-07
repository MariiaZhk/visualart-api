package com.visualart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO for creating or updating an artwork")
public record ArtworkRequestDTO(
        @NotBlank(message = "Title is required")
        @Schema(description = "Title of the artwork", example = "Starry Night")
        String title,

        @Schema(description = "Year the artwork was created", example = "1889")
        Integer yearCreated,

        @Schema(description = "Genres of the artwork", example = "[\"Post-Impressionism\"]")
        List<String> genres,

        @NotNull(message = "Artist ID is required")
        @Schema(description = "ID of the artist", example = "1")
        Long artistId,

        @Schema(description = "Media types used in the artwork", example = "[\"Oil on canvas\"]")
        List<String> media
) {}
