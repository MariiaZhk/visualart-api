package com.visualart.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.visualart.dto.*;
import com.visualart.entity.Artist;
import com.visualart.entity.Artwork;
import com.visualart.exception.ResourceNotFoundException;
import com.visualart.mapper.ArtworkMapper;
import com.visualart.repository.ArtistRepository;
import com.visualart.repository.ArtworkRepository;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final ArtistRepository artistRepository;

    // Create
    public ArtworkResponseDTO createArtwork(ArtworkRequestDTO dto) {
        Artist artist = artistRepository.findById(dto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist", dto.getArtistId()));
        Artwork artwork = ArtworkMapper.fromRequestDTO(dto, artist);
        return ArtworkMapper.toDTO(artworkRepository.save(artwork));
    }

    // Read by ID
    public ArtworkResponseDTO getArtworkById(Long id) {
        return artworkRepository.findById(id)
                .map(ArtworkMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Artwork", id));
    }

    // Read all
    public List<ArtworkResponseDTO> getAllArtworks() {
        return artworkRepository.findAll().stream()
                .map(ArtworkMapper::toDTO)
                .toList();
    }

    // Update
    public ArtworkResponseDTO updateArtwork(Long id, ArtworkRequestDTO dto) {
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artwork", id));
        Artist artist = artistRepository.findById(dto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist", dto.getArtistId()));
        ArtworkMapper.updateEntity(artwork, dto, artist);
        return ArtworkMapper.toDTO(artworkRepository.save(artwork));
    }

    // Delete
    public void deleteArtwork(Long id) {
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artwork", id));
        artworkRepository.delete(artwork);
    }

    // Pagination & Filtering
    public PagedResponseDTO<ArtworkShortDTO> getPaginatedArtworksDTO(ArtworkListRequestDTO request) {
        int page = Math.max(request.getPage(), 0);
        int size = request.getSize() > 0 ? request.getSize() : 20;
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Artwork> pageResult = artworkRepository.findAll(buildSpecification(request), pageRequest);

        return PagedResponseDTO.<ArtworkShortDTO>builder()
                .items(pageResult.getContent().stream().map(ArtworkMapper::toShortDTO).toList())
                .totalPages(pageResult.getTotalPages())
                .totalItems(pageResult.getTotalElements())
                .build();
    }

    private Specification<Artwork> buildSpecification(ArtworkListRequestDTO request) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (request.getArtistId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("artist").get("id"), request.getArtistId()));
            }
            if (request.getGenre() != null) {
                predicate = cb.and(predicate, cb.isMember(request.getGenre(), root.get("genres")));
            }
            if (request.getMedia() != null) {
                predicate = cb.and(predicate, cb.isMember(request.getMedia(), root.get("media")));
            }
            return predicate;
        };
    }
}
