package com.visualart.dto;

public record ArtworkListRequestDTO(
        Long artistId,
        String genre,
        int page,
        int size
) {}
