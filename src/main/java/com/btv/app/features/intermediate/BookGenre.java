package com.btv.app.features.intermediate;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.genre.Genre;
import jakarta.persistence.*;

@Entity
@Table(name = "book_genre")
public class BookGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;
}
