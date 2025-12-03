package com.visualart.controller;

import com.visualart.dto.*;
import com.visualart.service.ArtworkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/artworks")
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkService artworkService;

    @PostMapping
    public ArtworkResponseDTO create(@Valid @RequestBody ArtworkRequestDTO dto) {
        return artworkService.createArtwork(dto);
    }

    @GetMapping("/{id}")
    public ArtworkResponseDTO getById(@PathVariable Long id) {
        return artworkService.getArtworkById(id);
    }

    @GetMapping
    public PagedResponseDTO<ArtworkShortDTO> list(@RequestBody ArtworkListRequestDTO request) {
        return artworkService.getPaginatedArtworksDTO(request);
    }

    @PutMapping("/{id}")
    public ArtworkResponseDTO update(@PathVariable Long id, @Valid @RequestBody ArtworkRequestDTO dto) {
        return artworkService.updateArtwork(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        artworkService.deleteArtwork(id);
    }

    @PostMapping("/_report")
    public byte[] generateReport(@RequestBody ArtworkListRequestDTO request) throws Exception {
        return artworkService.generateCsvReport(request);
    }

    @PostMapping("/upload")
    public UploadResponseDTO upload(@RequestParam("file") MultipartFile file) throws Exception {
        return artworkService.uploadFromJson(file);
    }
}
