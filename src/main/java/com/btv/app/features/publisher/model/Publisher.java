package com.btv.app.features.publisher.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "nvarchar(255)")
    private String name;
}
