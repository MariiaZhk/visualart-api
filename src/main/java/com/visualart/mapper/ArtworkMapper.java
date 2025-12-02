package com.visualart.mapper;

import com.visualart.dto.*;
import com.visualart.entity.Artist;
import com.visualart.entity.Artwork;

public class ArtworkMapper {

    public static ArtworkResponseDTO toDTO(Artwork artwork) {
        if (artwork == null) return null;
        return ArtworkResponseDTO.builder()
                .id(artwork.getId())
                .title(artwork.getTitle())
                .yearCreated(artwork.getYearCreated())
                .genres(artwork.getGenres())
                .media(artwork.getMedia())
                .artist(ArtistMapper.toResponseDTO(artwork.getArtist()))
                .build();
    }

    public static ArtworkShortDTO toShortDTO(Artwork artwork) {
        if (artwork == null || artwork.getArtist() == null) return null;
        return new ArtworkShortDTO(
                artwork.getId(),
                artwork.getTitle(),
                artwork.getArtist().getName()
        );
    }

    public static Artwork fromRequestDTO(ArtworkRequestDTO dto, Artist artist) {
        if (dto == null || artist == null) return null;
        return Artwork.builder()
                .title(dto.getTitle())
                .yearCreated(dto.getYearCreated())
                .genres(dto.getGenres())
                .media(dto.getMedia())
                .artist(artist)
                .build();
    }

    public static void updateEntity(Artwork artwork, ArtworkRequestDTO dto, Artist artist) {
        artwork.setTitle(dto.getTitle());
        artwork.setYearCreated(dto.getYearCreated());
        artwork.setGenres(dto.getGenres());
        artwork.setMedia(dto.getMedia());
        artwork.setArtist(artist);
    }
}
//Mapper