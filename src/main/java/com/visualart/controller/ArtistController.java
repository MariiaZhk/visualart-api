package com.visualart.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.visualart.dto.ArtistRequestDTO;
import com.visualart.dto.ArtistResponseDTO;
import com.visualart.service.ArtistService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
// ControllerArtist
@RestController
@RequestMapping("/api/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping
    public ArtistResponseDTO createArtist(@Valid @RequestBody ArtistRequestDTO dto) {
        return artistService.createArtist(dto);
    }

    @GetMapping("/{id}")
    public ArtistResponseDTO getArtistById(@PathVariable Long id) {
        return artistService.getArtistById(id);
    }

    @GetMapping
    public List<ArtistResponseDTO> getAllArtists() {
        return artistService.getAllArtists();
    }

    @PutMapping("/{id}")
    public ArtistResponseDTO updateArtist(@PathVariable Long id, @Valid @RequestBody ArtistRequestDTO dto) {
        return artistService.updateArtist(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
    }
}
