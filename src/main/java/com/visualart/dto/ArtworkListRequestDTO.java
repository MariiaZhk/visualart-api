package com.visualart.dto;

import lombok.Data;

@Data
public class ArtworkListRequestDTO {
    private Long artistId;
    private String genre;
    private String media;
    private int page = 0;
    private int size = 20;
}
