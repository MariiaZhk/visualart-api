package com.visualart.mapper;

import com.visualart.dto.ArtworkRequestDTO;
import com.visualart.dto.ArtworkResponseDTO;
import com.visualart.dto.ArtworkShortDTO;
import com.visualart.dto.ArtistResponseDTO;
import com.visualart.entity.Artist;
import com.visualart.entity.Artwork;

import java.util.ArrayList;
import java.util.List;

public class ArtworkMapper {

    private ArtworkMapper() {}

   public static ArtworkResponseDTO toDto(Artwork artwork) {
List<String> genres = artwork.getGenres() == null ? List.of() : List.copyOf(artwork.getGenres());

List<String> media = artwork.getMedia() == null ? List.of(): List.copyOf(artwork.getMedia());

    return new ArtworkResponseDTO(
            artwork.getId(),
            artwork.getTitle(),
            artwork.getYearCreated(),
            genres,
            media,
            new ArtistResponseDTO(
                    artwork.getArtist().getId(),
                    artwork.getArtist().getName()
            )
    );
}

public static Artwork fromDTO(ArtworkRequestDTO dto, Artist artist) {
    return Artwork.builder()
            .title(dto.title())
            .yearCreated(dto.yearCreated())
          .genres(dto.genres() == null ? new ArrayList<>() : new ArrayList<>(dto.genres()))
.media(dto.media() == null ? new ArrayList<>() : new ArrayList<>(dto.media()))

            .artist(artist)
            .build();
}

public static ArtworkShortDTO toShortDTO(Artwork artwork) {
    return new ArtworkShortDTO(
            artwork.getId(),
            artwork.getTitle(),
            artwork.getYearCreated(),
            artwork.getArtist().getName()
    );
}

}
