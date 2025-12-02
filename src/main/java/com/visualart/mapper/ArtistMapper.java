package com.visualart.mapper;

import com.visualart.dto.ArtistRequestDTO;
import com.visualart.dto.ArtistResponseDTO;
import com.visualart.entity.Artist;
//Mapper
public class ArtistMapper {

    public static ArtistResponseDTO toResponseDTO(Artist artist) {
        if (artist == null) return null;
        return ArtistResponseDTO.builder()
                .id(artist.getId())
                .name(artist.getName())
                .birthYear(artist.getBirthYear())
                .deathYear(artist.getDeathYear())
                .nationality(artist.getNationality())
                .fields(artist.getFields())
                .affiliatedSchools(artist.getAffiliatedSchools())
                .build();
    }

    public static Artist fromRequestDTO(ArtistRequestDTO dto) {
        if (dto == null) return null;
        return Artist.builder()
                .name(dto.getName())
                .birthYear(dto.getBirthYear())
                .deathYear(dto.getDeathYear())
                .nationality(dto.getNationality())
                .fields(dto.getFields())
                .affiliatedSchools(dto.getAffiliatedSchools())
                .build();
    }

    public static void updateEntity(Artist artist, ArtistRequestDTO dto) {
        artist.setName(dto.getName());
        artist.setBirthYear(dto.getBirthYear());
        artist.setDeathYear(dto.getDeathYear());
        artist.setNationality(dto.getNationality());
        artist.setFields(dto.getFields());
        artist.setAffiliatedSchools(dto.getAffiliatedSchools());
    }
}
