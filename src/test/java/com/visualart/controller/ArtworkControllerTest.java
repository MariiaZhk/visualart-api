package com.visualart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visualart.dto.*;
import com.visualart.entity.Artist;
import com.visualart.entity.Artwork;
import com.visualart.repository.ArtistRepository;
import com.visualart.repository.ArtworkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArtworkControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Artist artist;

    @BeforeEach
    void setup() {
        artworkRepository.deleteAll();
        artistRepository.deleteAll();
        artist = artistRepository.save(Artist.builder().name("Test Artist").build());
        artworkRepository.save(Artwork.builder()
                .title("Artwork One")
                .artist(artist)
                .yearCreated(2020)
                .build());
    }

    @Test
    void testCreateArtwork() {
        ArtworkRequestDTO dto = new ArtworkRequestDTO(
                "Artwork Two",
                2021,
                List.of("Abstract"),
                artist.getId(),
                List.of("Oil")
        );

        ResponseEntity<ArtworkResponseDTO> response =
                restTemplate.postForEntity("/api/artworks", dto, ArtworkResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().title()).isEqualTo("Artwork Two");
        assertThat(artworkRepository.findAll()).hasSize(2);
    }

    @Test
    void testGetArtworkById() {
        Artwork existing = artworkRepository.findAll().get(0);

        ResponseEntity<ArtworkResponseDTO> response =
                restTemplate.getForEntity("/api/artworks/" + existing.getId(), ArtworkResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().title()).isEqualTo(existing.getTitle());
    }

    @Test
    void testUpdateArtwork() {
        Artwork existing = artworkRepository.findAll().get(0);
        ArtworkRequestDTO updateDto = new ArtworkRequestDTO(
                "Updated Artwork",
                2022,
                List.of("Modern"),
                artist.getId(),
                List.of("Acrylic")
        );

        HttpEntity<ArtworkRequestDTO> entity = new HttpEntity<>(updateDto);
        ResponseEntity<ArtworkResponseDTO> response = restTemplate.exchange(
                "/api/artworks/" + existing.getId(),
                HttpMethod.PUT,
                entity,
                ArtworkResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().title()).isEqualTo("Updated Artwork");
    }

    @Test
    void testDeleteArtwork() {
        Artwork existing = artworkRepository.findAll().get(0);

        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/artworks/" + existing.getId(),
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(artworkRepository.existsById(existing.getId())).isFalse();
    }

       @Test
    void testGenerateCsvReport() {
        ArtworkListRequestDTO req = new ArtworkListRequestDTO(
                null, null, null, 1, 10, "id", "asc"
        );

        ResponseEntity<byte[]> response = restTemplate.postForEntity(
                "/api/artworks/_report",
                req,
                byte[].class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String content = new String(response.getBody(), StandardCharsets.UTF_8);
        assertThat(content).contains("ID", "Title", "Artist");
    }

    @Test
    void testUploadFromJson() throws Exception {
        ArtworkRequestDTO[] artworks = {
                new ArtworkRequestDTO("Uploaded Artwork", 2022, List.of("Impressionism"), artist.getId(), List.of("Oil"))
        };
        byte[] jsonBytes = objectMapper.writeValueAsBytes(artworks);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource resource = new ByteArrayResource(jsonBytes) {
            @Override
            public String getFilename() {
                return "artworks.json";
            }
        };
        body.add("file", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<UploadResponseDTO> response = restTemplate.postForEntity(
                "/api/artworks/upload",
                requestEntity,
                UploadResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().successCount()).isEqualTo(1);
    }
    @Test
void testGetArtworkByIdNotFound() {
    ResponseEntity<String> response =
            restTemplate.getForEntity("/api/artworks/99999", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
}

@Test
void testUpdateArtworkNotFound() {
    ArtworkRequestDTO dto = new ArtworkRequestDTO(
            "Does Not Exist",
            2022,
            List.of("Style"),
            artist.getId(),
            List.of("Media")
    );

    HttpEntity<ArtworkRequestDTO> entity = new HttpEntity<>(dto);
    ResponseEntity<String> response = restTemplate.exchange(
            "/api/artworks/99999",
            HttpMethod.PUT,
            entity,
            String.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
}

@Test
void testDeleteArtworkNotFound() {
    ResponseEntity<String> response = restTemplate.exchange(
            "/api/artworks/99999",
            HttpMethod.DELETE,
            null,
            String.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
}

@Test
void testGetArtworksPaginated() {
    artworkRepository.save(Artwork.builder().title("Artwork Two").artist(artist).build());
    artworkRepository.save(Artwork.builder().title("Artwork Three").artist(artist).build());

    ArtworkListRequestDTO request = new ArtworkListRequestDTO(
            null, null, null, 1, 2, "id", "asc"
    );

    ResponseEntity<PagedResponseDTO<ArtworkShortDTO>> response = restTemplate.exchange(
            "/api/artworks/_list",
            HttpMethod.POST,
            new HttpEntity<>(request),
            new ParameterizedTypeReference<PagedResponseDTO<ArtworkShortDTO>>() {}
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().list()).hasSize(2);
    assertThat(response.getBody().totalItems()).isEqualTo(3);
}

}
