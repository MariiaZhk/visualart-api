package com.visualart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.visualart.dto.ArtworkListRequest;
import com.visualart.dto.ArtworkListResponse;
import com.visualart.dto.ArtworkShortDTO;
import com.visualart.entity.Artist;
import com.visualart.entity.Artwork;
import com.visualart.exception.ResourceNotFoundException;
import com.visualart.repository.ArtistRepository;
import com.visualart.repository.ArtworkRepository;

import jakarta.persistence.criteria.Predicate;

@Service
@Transactional
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final ArtistRepository artistRepository;

    public ArtworkService(ArtworkRepository artworkRepository, ArtistRepository artistRepository) {
        this.artworkRepository = artworkRepository;
        this.artistRepository = artistRepository;
    }

    // ================= CRUD =================
    public Artwork createArtwork(Artwork artwork) {
        prepareArtworkForSave(artwork);
        return artworkRepository.save(artwork);
    }

    public Artwork getArtworkById(Long id) {
        return artworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artwork", id));
    }

    public List<Artwork> getAllArtworks() {
        return artworkRepository.findAll();
    }

    public Artwork updateArtwork(Long id, Artwork updatedArtwork) {
        Artwork artwork = getArtworkById(id);
        prepareArtworkForSave(updatedArtwork);

        artwork.setTitle(updatedArtwork.getTitle());
        artwork.setYearCreated(updatedArtwork.getYearCreated());
        artwork.setGenres(updatedArtwork.getGenres());
        artwork.setMedia(updatedArtwork.getMedia());
        artwork.setArtist(updatedArtwork.getArtist());

        return artworkRepository.save(artwork);
    }

    public void deleteArtwork(Long id) {
        Artwork artwork = getArtworkById(id);
        artworkRepository.delete(artwork);
    }

    // ================= Приватні методи =================
    private void prepareArtworkForSave(Artwork artwork) {
        if (artwork.getArtist() == null || artwork.getArtist().getId() == null) {
            throw new ResourceNotFoundException("Artist", "null");
        }
        Artist artist = artistRepository.findById(artwork.getArtist().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist", artwork.getArtist().getId()));
        artwork.setArtist(artist);
    }

    // ================= Пагінація та фільтрація =================
    public ArtworkListResponse getPaginatedArtworks(ArtworkListRequest request) {
        int page = Math.max(request.getPage(), 0);
        int size = request.getSize() > 0 ? request.getSize() : 20;
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Artwork> pageResult = artworkRepository.findAll(buildSpecification(request), pageRequest);

        List<ArtworkShortDTO> shortList = pageResult.getContent().stream()
                .map(a -> new ArtworkShortDTO(a.getId(), a.getTitle(), a.getArtist().getName()))
                .collect(Collectors.toList());

        return new ArtworkListResponse(shortList, pageResult.getTotalPages());
    }

    public List<Artwork> getFilteredArtworks(ArtworkListRequest request) {
        return artworkRepository.findAll(buildSpecification(request));
    }

    private Specification<Artwork> buildSpecification(ArtworkListRequest request) {
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
