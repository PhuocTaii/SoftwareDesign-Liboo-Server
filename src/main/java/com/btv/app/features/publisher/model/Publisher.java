package com.btv.app.features.publisher.model;

import jakarta.persistence.*;

@Entity
@Table(name = "publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Publisher(Publisher tmp) {
        this.id = tmp.id;
        this.name = tmp.name;
    }

    public Publisher() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
