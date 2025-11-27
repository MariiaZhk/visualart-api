package com.visualart.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.CSVWriter;
import com.visualart.dto.ArtworkListRequest;
import com.visualart.dto.ArtworkListResponse;
import com.visualart.entity.Artwork;
import com.visualart.exception.ResourceNotFoundException;
import com.visualart.service.ArtworkService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/artwork")
public class ArtworkController {

    private final ArtworkService artworkService;

    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @PostMapping
    public Artwork createArtwork(@RequestBody Artwork artwork) {
        return artworkService.createArtwork(artwork);
    }

    @GetMapping("/{id}")
    public Artwork getArtworkById(@PathVariable Long id) {
        return artworkService.getArtworkById(id);
    }

    @GetMapping
    public List<Artwork> getAllArtworks() {
        return artworkService.getAllArtworks();
    }

    @PutMapping("/{id}")
    public Artwork updateArtwork(@PathVariable Long id, @RequestBody Artwork artwork) {
        return artworkService.updateArtwork(id, artwork);
    }

    @DeleteMapping("/{id}")
    public void deleteArtwork(@PathVariable Long id) {
        artworkService.deleteArtwork(id);
    }

    @PostMapping("/_list")
    public ArtworkListResponse listArtworks(@RequestBody ArtworkListRequest request) {
        return artworkService.getPaginatedArtworks(request);
    }

    @PostMapping("/_report")
    public void reportArtworks(@RequestBody ArtworkListRequest request, HttpServletResponse response) throws IOException {
        List<Artwork> artworks = artworkService.getFilteredArtworks(request);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"artworks_report.csv\"");

        try (CSVWriter writer = new CSVWriter(response.getWriter())) {
            writer.writeNext(new String[]{"ID", "Title", "Year", "Artist"});
            for (Artwork a : artworks) {
                writer.writeNext(new String[]{
                        a.getId().toString(),
                        a.getTitle(),
                        a.getYearCreated() != null ? a.getYearCreated().toString() : "",
                        a.getArtist().getName()
                });
            }
        }
    }

    @PostMapping("/upload")
    public Map<String, Integer> uploadArtworks(@RequestBody List<Artwork> artworks) {
        int success = 0;
        int failed = 0;

        for (Artwork artwork : artworks) {
            try {
                artworkService.createArtwork(artwork);
                success++;
            } catch (ResourceNotFoundException e) {
                failed++;
            }
        }

        return Map.of("success", success, "failed", failed);
    }
}
