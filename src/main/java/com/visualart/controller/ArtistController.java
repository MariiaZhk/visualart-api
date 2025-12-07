package com.visualart.controller;

import com.visualart.dto.ArtistRequestDTO;
import com.visualart.dto.ArtistResponseDTO;
import com.visualart.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
@Slf4j
public class ArtistController {

    private final ArtistService artistService;

    @Operation(summary = "Create a new artist", description = "Creates a new artist record. Name must be unique.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Artist successfully created",
                    content = @Content(schema = @Schema(implementation = ArtistResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request or duplicate name",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<ArtistResponseDTO> create(@Valid @RequestBody ArtistRequestDTO dto) {
        log.info("Creating new artist: {}", dto.name());
        ArtistResponseDTO created = artistService.createArtist(dto);
        return ResponseEntity
                .created(URI.create("/api/artists/" + created.id()))
                .body(created);
    }

    @Operation(summary = "Get artist by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Artist found",
                    content = @Content(schema = @Schema(implementation = ArtistResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Artist not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponseDTO> getById(@PathVariable Long id) {
        log.info("Fetching artist with id={}", id);
        return ResponseEntity.ok(artistService.getArtistById(id));
    }

    @Operation(summary = "Get all artists")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of artists returned",
                    content = @Content(schema = @Schema(implementation = ArtistResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<ArtistResponseDTO>> getAll() {
        log.info("Fetching all artists");
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    @Operation(summary = "Update an artist by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Artist successfully updated",
                    content = @Content(schema = @Schema(implementation = ArtistResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request or duplicate name",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Artist not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ArtistResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ArtistRequestDTO dto) {
        log.info("Updating artist id={}: {}", id, dto.name());
        return ResponseEntity.ok(artistService.updateArtist(id, dto));
    }

    @Operation(summary = "Delete an artist by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Artist successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Artist not found",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting artist with id={}", id);
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}
