package com.visualart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO representing an artwork in responses")
public record ArtworkResponseDTO(
        @Schema(description = "Unique identifier of the artwork", example = "1")
        Long id,

        @Schema(description = "Title of the artwork", example = "Starry Night")
        String title,

        @Schema(description = "Year the artwork was created", example = "1889")
        Integer yearCreated,

        @Schema(description = "Genres of the artwork", example = "[\"Post-Impressionism\"]")
        List<String> genres,

        @Schema(description = "Media types used in the artwork", example = "[\"Oil on canvas\"]")
        List<String> media,

        @Schema(description = "Artist information associated with the artwork")
        ArtistResponseDTO artist
) {}
