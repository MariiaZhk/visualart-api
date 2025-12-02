package com.visualart.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "artist", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private Integer birthYear;
    private Integer deathYear;
    private String nationality;

    @ElementCollection
    @CollectionTable(name = "artist_fields", joinColumns = @JoinColumn(name = "artist_id"))
    @Column(name = "field")
    private List<String> fields;

    @ElementCollection
    @CollectionTable(name = "artist_schools", joinColumns = @JoinColumn(name = "artist_id"))
    @Column(name = "school")
    private List<String> affiliatedSchools;
}
