package com.visualart.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
//DTO
@Data
public class ArtworkRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;
    private Integer yearCreated;
    private List<String> genres;
    private List<String> media;

    @NotNull(message = "Artist ID is required")
    private Long artistId;
}
