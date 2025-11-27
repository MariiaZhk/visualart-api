package com.visualart.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtworkListResponse {
    private List<ArtworkShortDTO> list; 
    private int totalPages;
}
