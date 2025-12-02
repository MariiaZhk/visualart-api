package com.visualart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
//DTO
@Data
@AllArgsConstructor
public class ArtworkShortDTO {
    private Long id;
    private String title;
    private String artistName;
}
