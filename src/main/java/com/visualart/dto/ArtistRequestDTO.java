package com.visualart.dto;

import java.util.List;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class ArtistRequestDTO {
    @NotBlank(message = "Name is required")
    @Size(min = 2, message = "Name must be at least 2 characters")
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    private String nationality;
    private List<String> fields;
    private List<String> affiliatedSchools;
}
