package com.visualart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.visualart.dto.ArtistRequestDTO;
import com.visualart.dto.ArtistResponseDTO;
import com.visualart.entity.Artist;
import com.visualart.exception.ResourceNotFoundException;
import com.visualart.mapper.ArtistMapper;
import com.visualart.repository.ArtistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;

    // Create
    public ArtistResponseDTO createArtist(ArtistRequestDTO dto) {
        if (artistRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Artist with this name already exists");
        }
        Artist artist = ArtistMapper.fromRequestDTO(dto);
        return ArtistMapper.toResponseDTO(artistRepository.save(artist));
    }

    // Read by ID
    public ArtistResponseDTO getArtistById(Long id) {
        return artistRepository.findById(id)
                .map(ArtistMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", id));
    }

    // Read all
    public List<ArtistResponseDTO> getAllArtists() {
        return artistRepository.findAll().stream()
                .map(ArtistMapper::toResponseDTO)
                .toList();
    }

    // Update
    public ArtistResponseDTO updateArtist(Long id, ArtistRequestDTO dto) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", id));
        ArtistMapper.updateEntity(artist, dto);
        return ArtistMapper.toResponseDTO(artistRepository.save(artist));
    }

    // Delete
    public void deleteArtist(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", id));
        artistRepository.delete(artist);
    }
}
//Serv