package com.visualart.service;

import com.visualart.dto.*;
import com.visualart.entity.Artist;
import com.visualart.entity.Artwork;
import com.visualart.exception.ResourceNotFoundException;
import com.visualart.mapper.ArtworkMapper;
import com.visualart.repository.ArtistRepository;
import com.visualart.repository.ArtworkRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final ArtistRepository artistRepository;
    private final ObjectMapper objectMapper;

    // ------------------ CREATE ------------------
    @Transactional
    public ArtworkResponseDTO createArtwork(ArtworkRequestDTO dto) {
        Artist artist = getArtistOrThrow(dto.artistId());
        Artwork artwork = ArtworkMapper.fromDTO(dto, artist);
        return ArtworkMapper.toDto(artworkRepository.save(artwork));
    }

    // ------------------ READ ------------------
    public ArtworkResponseDTO getArtworkById(Long id) {
        return ArtworkMapper.toDto(getArtworkOrThrow(id));
    }

    public PagedResponseDTO<ArtworkShortDTO> getPaginatedArtworks(ArtworkListRequestDTO req) {
        Pageable pageable = PageRequest.of(
                Math.max(req.page(), 1) - 1,
                req.size() <= 0 ? 20 : req.size(),
                "desc".equalsIgnoreCase(req.sortDir())
                        ? Sort.by(getSortField(req)).descending()
                        : Sort.by(getSortField(req)).ascending()
        );

        Page<Artwork> pageResult = artworkRepository.findAll(buildSpecification(req), pageable);
        List<ArtworkShortDTO> items = pageResult.getContent().stream()
                .map(ArtworkMapper::toShortDTO)
                .toList();

        return new PagedResponseDTO<>(items, pageResult.getTotalPages(), pageResult.getTotalElements());
    }

    // ------------------ UPDATE ------------------
    // @Transactional
    // public ArtworkResponseDTO updateArtwork(Long id, ArtworkRequestDTO dto) {
    //     Artwork artwork = getArtworkOrThrow(id);
    //     Artist artist = getArtistOrThrow(dto.artistId());

    //     artwork.setTitle(dto.title());
    //     artwork.setYearCreated(dto.yearCreated());
    //     artwork.setGenres(dto.genres() != null ? new ArrayList<>(dto.genres()) : new ArrayList<>());
    //     artwork.setMedia(dto.media() != null ? new ArrayList<>(dto.media()) : new ArrayList<>());
    //     artwork.setArtist(artist);

    //     return ArtworkMapper.toDto(artworkRepository.save(artwork));
    // }


    @Transactional
public ArtworkResponseDTO updateArtwork(Long id, ArtworkRequestDTO dto) {
    Artwork artwork = getArtworkOrThrow(id);
    Artist artist = getArtistOrThrow(dto.artistId());

    artwork.setTitle(dto.title());
    artwork.setYearCreated(dto.yearCreated());
    artwork.setArtist(artist);

    // ===== GENRES =====
    if (dto.genres() != null) {
        artwork.getGenres().clear();
        artwork.getGenres().addAll(dto.genres());
    }

    // ===== MEDIA =====
    if (dto.media() != null) {
        artwork.getMedia().clear();
        artwork.getMedia().addAll(dto.media());
    }

    return ArtworkMapper.toDto(artwork);
}

    // ------------------ DELETE ------------------
    @Transactional
    public void deleteArtwork(Long id) {
        artworkRepository.delete(getArtworkOrThrow(id));
    }

    // ------------------ CSV REPORT ------------------
    public byte[] generateCsvReport(ArtworkListRequestDTO req) {
        List<Artwork> artworks = artworkRepository.findAll(buildSpecification(req), Sort.by("id").ascending());

        try (StringWriter writer = new StringWriter(); CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeNext(new String[]{"ID", "Title", "Artist", "Year Created", "Genres", "Media"});
            artworks.forEach(a -> csvWriter.writeNext(new String[]{
                    String.valueOf(a.getId()),
                    a.getTitle(),
                    a.getArtist() != null ? a.getArtist().getName() : "",
                    a.getYearCreated() != null ? String.valueOf(a.getYearCreated()) : "",
                    String.join("|", a.getGenres()),
                    String.join("|", a.getMedia())
            }));
            return writer.toString().getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Error generating CSV report", e);
            throw new IllegalStateException("Error generating CSV report", e);
        }
    }

    // ------------------ UPLOAD FROM JSON ------------------
    @Transactional
    public UploadResponseDTO uploadFromJson(MultipartFile file) {
        int success = 0, failed = 0;

        try {
            ArtworkRequestDTO[] items = objectMapper.readValue(file.getInputStream(), ArtworkRequestDTO[].class);
            for (ArtworkRequestDTO dto : items) {
                try {
                    createArtwork(dto);
                    success++;
                } catch (Exception e) {
                    log.error("Failed to import artwork '{}': {}", dto.title(), e.getMessage());
                    failed++;
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON file", e);
        }

        return new UploadResponseDTO(success, failed);
    }

    // ------------------ PRIVATE ------------------
    private Artwork getArtworkOrThrow(Long id) {
        return artworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artwork", id));
    }

    private Artist getArtistOrThrow(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", id));
    }

    private Specification<Artwork> withArtist(Long artistId) {
        return (root, cq, cb) -> artistId == null ? null : cb.equal(root.get("artist").get("id"), artistId);
    }

    private Specification<Artwork> withTitle(String title) {
        return (root, cq, cb) -> (title == null || title.isBlank()) ? null
                : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    private Specification<Artwork> withYear(Integer year) {
        return (root, cq, cb) -> year == null ? null : cb.equal(root.get("yearCreated"), year);
    }

    private Specification<Artwork> buildSpecification(ArtworkListRequestDTO req) {
        return Specification.where(withArtist(req.artistId()))
                .and(withTitle(req.title()))
                .and(withYear(req.yearCreated()));
    }

    private String getSortField(ArtworkListRequestDTO req) {
        return (req.sortBy() == null || req.sortBy().isBlank()) ? "id" : req.sortBy();
    }
}
