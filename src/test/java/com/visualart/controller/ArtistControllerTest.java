package com.visualart.controller;

import com.visualart.dto.ArtistRequestDTO;
import com.visualart.dto.ArtistResponseDTO;
import com.visualart.entity.Artist;
import com.visualart.repository.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArtistControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ArtistRepository artistRepository;

    @BeforeEach
    void setup() {
        artistRepository.deleteAll();
        artistRepository.save(Artist.builder().name("Artist One").build());
        artistRepository.save(Artist.builder().name("Artist Two").build());
    }

    @Test
    void testGetAllArtists() {
        ResponseEntity<List<ArtistResponseDTO>> response = restTemplate.exchange(
                "/api/artists",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ArtistResponseDTO>>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<ArtistResponseDTO> artists = response.getBody();
        assertThat(artists).hasSize(2);
        assertThat(artists).extracting("name").containsExactlyInAnyOrder("Artist One", "Artist Two");
    }

    @Test
    void testCreateArtist() {
        ArtistRequestDTO request = new ArtistRequestDTO("New Artist");
        ResponseEntity<ArtistResponseDTO> response =
                restTemplate.postForEntity("/api/artists", request, ArtistResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().name()).isEqualTo("New Artist");
        assertThat(artistRepository.findAll()).hasSize(3);
    }

    @Test
    void testGetArtistById() {
        Artist existing = artistRepository.findAll().get(0);
        ResponseEntity<ArtistResponseDTO> response =
                restTemplate.getForEntity("/api/artists/" + existing.getId(), ArtistResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().name()).isEqualTo(existing.getName());
    }

    @Test
    void testUpdateArtist() {
        Artist existing = artistRepository.findAll().get(0);
        ArtistRequestDTO updateRequest = new ArtistRequestDTO("Updated Artist");

        HttpEntity<ArtistRequestDTO> entity = new HttpEntity<>(updateRequest);
        ResponseEntity<ArtistResponseDTO> response = restTemplate.exchange(
                "/api/artists/" + existing.getId(),
                HttpMethod.PUT,
                entity,
                ArtistResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().name()).isEqualTo("Updated Artist");
    }

    @Test
    void testDeleteArtist() {
        Artist existing = artistRepository.findAll().get(0);

        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/artists/" + existing.getId(),
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(artistRepository.existsById(existing.getId())).isFalse();
    }
    @Test
void testGetArtistByIdNotFound() {
    ResponseEntity<String> response =
            restTemplate.getForEntity("/api/artists/99999", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
}

@Test
void testUpdateArtistNotFound() {
    ArtistRequestDTO request = new ArtistRequestDTO("Does Not Exist");

    HttpEntity<ArtistRequestDTO> entity = new HttpEntity<>(request);
    ResponseEntity<String> response = restTemplate.exchange(
            "/api/artists/99999",
            HttpMethod.PUT,
            entity,
            String.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
}

@Test
void testDeleteArtistNotFound() {
    ResponseEntity<String> response = restTemplate.exchange(
            "/api/artists/99999",
            HttpMethod.DELETE,
            null,
            String.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
}

@Test
void testCreateArtistValidationError() {
    ArtistRequestDTO request = new ArtistRequestDTO("");

    ResponseEntity<String> response =
            restTemplate.postForEntity("/api/artists", request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
}

}
