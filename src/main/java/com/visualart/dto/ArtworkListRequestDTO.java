package com.visualart.dto;

public record ArtworkListRequestDTO(
        Long artistId,
        String genre,
        String media,
        int page,
        int size
) {}
