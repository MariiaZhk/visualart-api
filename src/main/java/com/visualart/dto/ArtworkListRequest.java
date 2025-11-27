package com.visualart.dto;

import lombok.Data;

@Data
public class ArtworkListRequest {
    private Long artistId;  // фільтрація за художником
    private String genre;   // фільтрація за жанром
    private String media;   // фільтрація за медіа
    private int page = 0;   // номер сторінки, default 0
    private int size = 20;  // розмір сторінки, default 20
}
