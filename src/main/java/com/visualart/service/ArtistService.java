package com.visualart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.visualart.entity.Artist;
import com.visualart.repository.ArtistRepository;

@Service
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public Optional<Artist> getArtistById(Long id) {
        return artistRepository.findById(id);
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public Artist updateArtist(Long id, Artist updatedArtist) {
        return artistRepository.findById(id)
                .map(artist -> {
                    artist.setName(updatedArtist.getName());
                    artist.setBirthYear(updatedArtist.getBirthYear());
                    artist.setDeathYear(updatedArtist.getDeathYear());
                    artist.setNationality(updatedArtist.getNationality());
                    artist.setFields(updatedArtist.getFields());
                    artist.setAffiliatedSchools(updatedArtist.getAffiliatedSchools());
                    return artistRepository.save(artist);
                })
                .orElseThrow(() -> new RuntimeException("Artist not found with id " + id));
    }

    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }
}
