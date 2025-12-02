package com.visualart.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
//DTO
@Data
@Builder
public class ArtworkResponseDTO {
    private Long id;
    private String title;
    private Integer yearCreated;
    private List<String> genres;
    private List<String> media;
    private ArtistResponseDTO artist;
}
