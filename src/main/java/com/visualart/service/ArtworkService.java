package com.visualart.service;

import com.visualart.dto.*;
import com.visualart.entity.Artist;
import com.visualart.entity.Artwork;
import com.visualart.exception.ResourceNotFoundException;
import com.visualart.mapper.ArtworkMapper;
import com.visualart.repository.ArtistRepository;
import com.visualart.repository.ArtworkRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final ArtistRepository artistRepository;
    private final ObjectMapper objectMapper;

    // ---------------- CREATE ----------------
    public ArtworkResponseDTO createArtwork(ArtworkRequestDTO dto) {
        log.info("Creating artwork '{}', artistId={}", dto.title(), dto.artistId());

        if (dto.artistId() == null) {
            throw new IllegalArgumentException("artistId must not be null");
        }

        Artist artist = artistRepository.findById(dto.artistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist", dto.artistId()));

        Artwork artwork = ArtworkMapper.fromDTO(dto, artist);
        return ArtworkMapper.toDTO(artworkRepository.save(artwork));
    }

    // ---------------- READ ----------------
    public ArtworkResponseDTO getArtworkById(Long id) {
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artwork", id));
        return ArtworkMapper.toDTO(artwork);
    }

    // ---------------- UPDATE ----------------
    public ArtworkResponseDTO updateArtwork(Long id, ArtworkRequestDTO dto) {
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artwork", id));

        if (dto.artistId() == null) {
            throw new IllegalArgumentException("artistId must not be null");
        }

        Artist artist = artistRepository.findById(dto.artistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist", dto.artistId()));

        artwork.setTitle(dto.title());
        artwork.setYearCreated(dto.yearCreated());
        artwork.setGenres(dto.genres() != null ? dto.genres() : List.of());
        artwork.setMedia(dto.media() != null ? dto.media() : List.of());
        artwork.setArtist(artist);

        return ArtworkMapper.toDTO(artworkRepository.save(artwork));
    }

    // ---------------- DELETE ----------------
    public void deleteArtwork(Long id) {
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artwork", id));
        artworkRepository.delete(artwork);
    }

    // ---------------- PAGINATION + FILTERING ----------------
    public PagedResponseDTO<ArtworkShortDTO> getPaginatedArtworksDTO(ArtworkListRequestDTO request) {
        int page = Math.max(request.page(), 0);
        int size = request.size() > 0 ? request.size() : 20;
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Artwork> pageResult = artworkRepository.findAll(buildSpecification(request), pageRequest);

        return new PagedResponseDTO<>(
                pageResult.getContent().stream()
                        .map(ArtworkMapper::toShortDTO)
                        .toList(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );
    }

    private Specification<Artwork> buildSpecification(ArtworkListRequestDTO request) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (request.artistId() != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("artist").get("id"), request.artistId()));
            }
            if (request.genre() != null) {
                predicate = cb.and(predicate,
                        cb.isMember(request.genre(), root.get("genres")));
            }
            if (request.media() != null) {
                predicate = cb.and(predicate,
                        cb.isMember(request.media(), root.get("media")));
            }
            return predicate;
        };
    }

    // ---------------- CSV REPORT ----------------
    public byte[] generateCsvReport(ArtworkListRequestDTO request) throws IOException {
        List<ArtworkResponseDTO> artworks = getPaginatedArtworksDTO(request).items().stream()
                .map(artwork -> getArtworkById(artwork.id()))
                .toList();

        StringWriter writer = new StringWriter();
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeNext(new String[]{"ID", "Title", "Artist", "Year Created", "Genres", "Media"});
            for (ArtworkResponseDTO a : artworks) {
                csvWriter.writeNext(new String[]{
                        String.valueOf(a.id()),
                        a.title(),
                        a.artist().name(),
                        String.valueOf(a.yearCreated()),
                        a.genres() != null ? String.join("|", a.genres()) : "",
                        a.media() != null ? String.join("|", a.media()) : ""
                });
            }
        }

        return writer.toString().getBytes(StandardCharsets.UTF_8);
    }

    // ---------------- UPLOAD JSON ----------------
    public UploadResponseDTO uploadFromJson(MultipartFile file) throws IOException {
        int success = 0;
        int failed = 0;

        ArtworkRequestDTO[] items = objectMapper.readValue(file.getInputStream(), ArtworkRequestDTO[].class);
        for (ArtworkRequestDTO dto : items) {
            try {
                createArtwork(dto);
                success++;
            } catch (Exception e) {
                failed++;
                log.warn("Failed to import artwork '{}': {}", dto.title(), e.getMessage());
            }
        }

        return new UploadResponseDTO(success, failed);
    }
}
