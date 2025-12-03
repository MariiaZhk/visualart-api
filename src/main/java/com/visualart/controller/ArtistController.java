package com.visualart.controller;

import com.visualart.dto.ArtistRequestDTO;
import com.visualart.dto.ArtistResponseDTO;
import com.visualart.service.ArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping
    public ArtistResponseDTO create(@Valid @RequestBody ArtistRequestDTO dto) {
        return artistService.createArtist(dto);
    }

    @GetMapping("/{id}")
    public ArtistResponseDTO getById(@PathVariable Long id) {
        return artistService.getArtistById(id);
    }

    @GetMapping
    public List<ArtistResponseDTO> getAll() {
        return artistService.getAllArtists();
    }

    @PutMapping("/{id}")
    public ArtistResponseDTO update(@PathVariable Long id, @Valid @RequestBody ArtistRequestDTO dto) {
        return artistService.updateArtist(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        artistService.deleteArtist(id);
    }
}
