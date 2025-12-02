package com.visualart.dto;

import java.util.List;
import lombok.Data;

@Data
public class ArtistResponseDTO {
    private Long id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String nationality;
    private List<String> fields;
    private List<String> affiliatedSchools;
}
