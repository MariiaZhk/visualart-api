package com.visualart.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "artworks")
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
    private List<String> genres;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;
}
