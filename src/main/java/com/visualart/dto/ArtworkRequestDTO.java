package com.visualart.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "DTO for creating or updating an artwork")
public record ArtworkRequestDTO(
@NotBlank(message = "Title is required")
@Size(max = 100, message = "Title must be at most 100 characters")
String title,

        @NotNull(message = "Year is required")
        @Min(value = 1, message = "Year must be positive")
        @Max(value = 3000, message = "Year is not valid")
        Integer yearCreated,


List<
    @NotBlank(message = "Genre must not be blank")
    @Size(max = 50, message = "Genre is too long")
    String
> genres,

@NotNull(message = "Artist ID is required")
@Schema(description = "ID of the artist", example = "1")
Long artistId,

List<
    @NotBlank(message = "Media must not be blank")
    @Size(max = 50, message = "Media is too long")
    String
> media

) {}
