package com.visualart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.visualart.entity.Artwork;
import com.visualart.repository.ArtworkRepository;

@Service
@Transactional
public class ArtworkService {

    private final ArtworkRepository artworkRepository;

    public ArtworkService(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    public Artwork createArtwork(Artwork artwork) {
        return artworkRepository.save(artwork);
    }

    public Optional<Artwork> getArtworkById(Long id) {
        return artworkRepository.findById(id);
    }

    public List<Artwork> getAllArtworks() {
        return artworkRepository.findAll();
    }

    public Artwork updateArtwork(Long id, Artwork updatedArtwork) {
        return artworkRepository.findById(id)
                .map(artwork -> {
                    artwork.setTitle(updatedArtwork.getTitle());
                    artwork.setYearCreated(updatedArtwork.getYearCreated());
                    artwork.setGenres(updatedArtwork.getGenres());
                    artwork.setMedia(updatedArtwork.getMedia());
                    artwork.setArtist(updatedArtwork.getArtist());
                    return artworkRepository.save(artwork);
                })
                .orElseThrow(() -> new RuntimeException("Artwork not found with id " + id));
    }

    public void deleteArtwork(Long id) {
        artworkRepository.deleteById(id);
    }
}
