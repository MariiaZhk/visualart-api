package com.visualart.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "artist")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
