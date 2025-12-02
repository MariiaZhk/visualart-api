package com.visualart.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
//DTO
@Data
public class ArtistRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String nationality;
    private List<String> fields;
    private List<String> affiliatedSchools;
}
