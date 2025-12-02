package com.visualart.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.visualart.dto.*;
import com.visualart.service.ArtworkService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

//ControllerArtwork
@RestController
@RequestMapping("/api/artwork")
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkService artworkService;

    @PostMapping
    public ArtworkResponseDTO createArtwork(@Valid @RequestBody ArtworkRequestDTO dto) {
        return artworkService.createArtwork(dto);
    }

    @GetMapping("/{id}")
    public ArtworkResponseDTO getArtworkById(@PathVariable Long id) {
        return artworkService.getArtworkById(id);
    }

    @GetMapping
    public List<ArtworkResponseDTO> getAllArtworks() {
        return artworkService.getAllArtworks();
    }

    @PutMapping("/{id}")
    public ArtworkResponseDTO updateArtwork(@PathVariable Long id, @Valid @RequestBody ArtworkRequestDTO dto) {
        return artworkService.updateArtwork(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteArtwork(@PathVariable Long id) {
        artworkService.deleteArtwork(id);
    }

    @PostMapping("/_list")
    public PagedResponseDTO<ArtworkShortDTO> listArtworks(@RequestBody ArtworkListRequestDTO request) {
        return artworkService.getPaginatedArtworksDTO(request);
    }
}
