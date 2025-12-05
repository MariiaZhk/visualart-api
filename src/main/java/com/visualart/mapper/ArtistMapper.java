package com.visualart.mapper;

import com.visualart.dto.ArtistRequestDTO;
import com.visualart.dto.ArtistResponseDTO;
import com.visualart.entity.Artist;

public class ArtistMapper {

    public static ArtistResponseDTO toDTO(Artist artist) {
        if (artist == null) return null;
        return new ArtistResponseDTO(
                artist.getId(),
                artist.getName()
        );
    }

    public static Artist fromDTO(ArtistRequestDTO dto) {
        if (dto == null) return null;
        return Artist.builder()
                .name(dto.name())
                .build();
    }
}
