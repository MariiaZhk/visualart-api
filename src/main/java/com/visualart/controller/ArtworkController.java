package com.visualart.controller;

import com.visualart.dto.*;
import com.visualart.service.ArtworkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/api/artworks")
@RequiredArgsConstructor
@Slf4j
public class ArtworkController {

    private final ArtworkService artworkService;

    @Operation(summary = "Create a new artwork")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Artwork created"),
            @ApiResponse(responseCode = "400", description = "Invalid request or artist not found")
    })
    @PostMapping
    public ResponseEntity<ArtworkResponseDTO> create(@Valid @RequestBody ArtworkRequestDTO dto) {
        log.info("Creating artwork: {}", dto.title());
        ArtworkResponseDTO created = artworkService.createArtwork(dto);
        return ResponseEntity.created(URI.create("/api/artworks/" + created.id())).body(created);
    }

    @Operation(summary = "Get artwork by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Artwork retrieved"),
            @ApiResponse(responseCode = "404", description = "Artwork not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ArtworkResponseDTO> getById(@PathVariable Long id) {
        log.info("Fetching artwork id={}", id);
        return ResponseEntity.ok(artworkService.getArtworkById(id));
    }

    @Operation(summary = "Get paginated artworks")
    @PostMapping("/_list")
    public ResponseEntity<PagedResponseDTO<ArtworkShortDTO>> list(@RequestBody ArtworkListRequestDTO req) {
        log.info("Listing artworks: page={}, size={}, artistId={}, title={}, year={}",
                req.page(), req.size(), req.artistId(), req.title(), req.yearCreated());
        return ResponseEntity.ok(artworkService.getPaginatedArtworks(req));
    }

    @Operation(summary = "Update an artwork by ID")
    @PutMapping("/{id}")
    public ResponseEntity<ArtworkResponseDTO> update(@PathVariable Long id,
                                                     @Valid @RequestBody ArtworkRequestDTO dto) {
        log.info("Updating artwork id={}: {}", id, dto.title());
        return ResponseEntity.ok(artworkService.updateArtwork(id, dto));
    }

    @Operation(summary = "Delete an artwork by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting artwork id={}", id);
        artworkService.deleteArtwork(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Generate CSV report for artworks")
    @PostMapping("/_report")
    public ResponseEntity<byte[]> generateReport(@RequestBody ArtworkListRequestDTO req) {
        log.info("Generating CSV report for artworks with filters: artistId={}, title={}, year={}",
                req.artistId(), req.title(), req.yearCreated());

        byte[] csvBytes = artworkService.generateCsvReport(req);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=artworks_report.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csvBytes);
    }

    @Operation(summary = "Upload artworks from JSON file")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadResponseDTO> upload(@RequestPart("file") MultipartFile file) {
        log.info("Uploading artworks from file: {}", file.getOriginalFilename());
        return ResponseEntity.ok(artworkService.uploadFromJson(file));
    }
}
