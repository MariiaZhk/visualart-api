package com.visualart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.visualart.entity.Artist;
import com.visualart.exception.ResourceNotFoundException;
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

    public Artist getArtistById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", id));
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public Artist updateArtist(Long id, Artist updatedArtist) {
        Artist artist = getArtistById(id); 
        artist.setName(updatedArtist.getName());
        artist.setBirthYear(updatedArtist.getBirthYear());
        artist.setDeathYear(updatedArtist.getDeathYear());
        artist.setNationality(updatedArtist.getNationality());
        artist.setFields(updatedArtist.getFields());
        artist.setAffiliatedSchools(updatedArtist.getAffiliatedSchools());
        return artistRepository.save(artist);
    }

    public void deleteArtist(Long id) {
        Artist artist = getArtistById(id);
        artistRepository.delete(artist);
    }
}
