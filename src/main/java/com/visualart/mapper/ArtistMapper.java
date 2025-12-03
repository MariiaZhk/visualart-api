package com.visualart.mapper;

import com.visualart.dto.ArtistRequestDTO;
import com.visualart.dto.ArtistResponseDTO;
import com.visualart.entity.Artist;

import java.util.List;

public class ArtistMapper {

    public static ArtistResponseDTO toDTO(Artist artist) {
        if (artist == null) return null;
        return new ArtistResponseDTO(
                artist.getId(),
                artist.getName(),
                artist.getBirthYear(),
                artist.getDeathYear(),
                artist.getNationality(),
                artist.getFields() == null ? List.of() : artist.getFields(),
                artist.getAffiliatedSchools() == null ? List.of() : artist.getAffiliatedSchools()
        );
    }

    public static Artist fromDTO(ArtistRequestDTO dto) {
        if (dto == null) return null;
        return Artist.builder()
                .name(dto.name())
                .birthYear(dto.birthYear())
                .deathYear(dto.deathYear())
                .nationality(dto.nationality())
                .fields(dto.fields() == null ? List.of() : dto.fields())
                .affiliatedSchools(dto.affiliatedSchools() == null ? List.of() : dto.affiliatedSchools())
                .build();
    }
}
