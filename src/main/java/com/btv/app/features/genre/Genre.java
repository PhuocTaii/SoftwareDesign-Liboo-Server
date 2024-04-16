package com.btv.app.features.genre;

import jakarta.persistence.*;

@Entity
@Table(name = "genre")

public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}
