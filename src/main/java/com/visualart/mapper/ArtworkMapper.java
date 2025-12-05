package com.visualart.mapper;

import com.visualart.dto.*;
import com.visualart.entity.Artist;
import com.visualart.entity.Artwork;

import java.util.List;

public class ArtworkMapper {

    public static ArtworkResponseDTO toDTO(Artwork artwork) {
        if (artwork == null) return null;
        return new ArtworkResponseDTO(
                artwork.getId(),
                artwork.getTitle(),
                artwork.getYearCreated(),
                artwork.getGenres() == null ? List.of() : artwork.getGenres(),
                ArtistMapper.toDTO(artwork.getArtist())
        );
    }

    public static ArtworkShortDTO toShortDTO(Artwork artwork) {
        if (artwork == null || artwork.getArtist() == null) return null;
        return new ArtworkShortDTO(
                artwork.getId(),
                artwork.getTitle(),
                artwork.getArtist().getName()
        );
    }

    public static Artwork fromDTO(ArtworkRequestDTO dto, Artist artist) {
        if (dto == null || artist == null) return null;
        return Artwork.builder()
                .title(dto.title())
                .yearCreated(dto.yearCreated())
                .genres(dto.genres() == null ? List.of() : dto.genres())
                .artist(artist)
                .build();
    }
}
