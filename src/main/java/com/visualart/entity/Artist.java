package com.visualart.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "artists", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
