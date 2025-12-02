package com.visualart.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
//DTO
@Data
@Builder
public class ArtistResponseDTO {
    private Long id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String nationality;
    private List<String> fields;
    private List<String> affiliatedSchools;
}
