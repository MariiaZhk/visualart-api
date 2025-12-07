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
@Slf4j
@Transactional(readOnly = true)
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Transactional
    public ArtistResponseDTO createArtist(ArtistRequestDTO dto) {
        if (artistRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Artist with this name already exists");
        }
        Artist saved = artistRepository.save(ArtistMapper.fromDTO(dto));
        return ArtistMapper.toDto(saved);
    }

    public ArtistResponseDTO getArtistById(Long id) {
        return ArtistMapper.toDto(
            artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", id))
        );
    }

    public List<ArtistResponseDTO> getAllArtists() {
        return artistRepository.findAll().stream()
                .map(ArtistMapper::toDto)
                .toList();
    }

    @Transactional
    public ArtistResponseDTO updateArtist(Long id, ArtistRequestDTO dto) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", id));

        String newName = dto.name();
        if (!artist.getName().equals(newName) && artistRepository.existsByName(newName)) {
            throw new IllegalArgumentException("Artist with this name already exists");
        }

        artist.setName(newName);
        return ArtistMapper.toDto(artistRepository.save(artist));
    }

    @Transactional
    public void deleteArtist(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", id));
        artistRepository.delete(artist);
    }
}
