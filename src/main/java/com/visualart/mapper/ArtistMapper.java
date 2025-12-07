package com.visualart.mapper;

import com.visualart.dto.ArtistRequestDTO;
import com.visualart.dto.ArtistResponseDTO;
import com.visualart.entity.Artist;

public class ArtistMapper {

    private ArtistMapper() {}
public static ArtistResponseDTO toDto(Artist artist) {
    return new ArtistResponseDTO(artist.getId(), artist.getName());
}

public static Artist fromDTO(ArtistRequestDTO dto) {
    return Artist.builder().name(dto.name()).build();
}

 
}


