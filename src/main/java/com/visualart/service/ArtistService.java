package com.visualart.service;

import com.visualart.dto.ArtistRequestDTO;
import com.visualart.dto.ArtistResponseDTO;
import com.visualart.entity.Artist;
import com.visualart.exception.ResourceNotFoundException;
import com.visualart.mapper.ArtistMapper;
import com.visualart.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistResponseDTO createArtist(ArtistRequestDTO dto) {
        log.info("Creating artist with name '{}'", dto.name());
        if (artistRepository.existsByName(dto.name())) {
            log.warn("Artist creation failed, name '{}' already exists", dto.name());
            throw new IllegalArgumentException("Artist with this name already exists");
        }

        Artist artist = ArtistMapper.fromDTO(dto);
        return ArtistMapper.toDTO(artistRepository.save(artist));
    }

    public ArtistResponseDTO getArtistById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", id));
        return ArtistMapper.toDTO(artist);
    }

    public List<ArtistResponseDTO> getAllArtists() {
        return artistRepository.findAll().stream()
                .map(ArtistMapper::toDTO)
                .toList();
    }

    public ArtistResponseDTO updateArtist(Long id, ArtistRequestDTO dto) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", id));

        artist.setName(dto.name());
        artist.setBirthYear(dto.birthYear());
        artist.setDeathYear(dto.deathYear());
        artist.setNationality(dto.nationality());
        artist.setFields(dto.fields() != null ? dto.fields() : List.of());
        artist.setAffiliatedSchools(dto.affiliatedSchools() != null ? dto.affiliatedSchools() : List.of());

        return ArtistMapper.toDTO(artistRepository.save(artist));
    }

    public void deleteArtist(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", id));
        artistRepository.delete(artist);
    }
}
