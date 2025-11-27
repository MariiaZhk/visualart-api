package com.visualart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visualart.entity.Artwork;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

   
    List<Artwork> findByArtistId(Long artistId);


    List<Artwork> findByGenresContaining(String genre);

    List<Artwork> findByMediaContaining(String media);
}
