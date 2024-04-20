package com.btv.app.features.book.model;

import com.btv.app.features.author.model.Author;
import com.btv.app.features.genre.model.Genre;
import com.btv.app.features.publisher.model.Publisher;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "isbn", nullable = false, unique = true, length = 13)
    private String ISBN;
    @Column(name = "b_name", nullable = false, length = 100, columnDefinition = "nvarchar(255)")
    private String name;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;
    @Column(name = "publish_year", nullable = false)
    private Integer publishYear;
    @Column(name = "b_description", nullable = false, length = 1000)
    private String description;
    @ManyToMany
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();
    @Column(name = "price", nullable = false)
    private Integer price;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "borrowed", nullable = false, columnDefinition = "integer default 0")
    private Integer borrowed;
    @Column(name = "image")
    private String image;
}
