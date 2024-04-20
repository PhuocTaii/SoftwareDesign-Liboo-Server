package com.btv.app.features.genre.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "genre")

public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "nvarchar(255)", unique = true)
    private String name;
}
