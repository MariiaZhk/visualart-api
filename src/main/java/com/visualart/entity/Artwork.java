package com.visualart.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "artworks",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_artwork_artist_title",
            columnNames = {"artist_id", "title"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "year_created")
    private Integer yearCreated;

    @ElementCollection
    @CollectionTable(name = "artwork_genres", joinColumns = @JoinColumn(name = "artwork_id"))
    @Column(name = "genre")
    @Builder.Default
    private List<String> genres = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "artwork_media", joinColumns = @JoinColumn(name = "artwork_id"))
    @Column(name = "media")
    @Builder.Default
    private List<String> media = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;
}
