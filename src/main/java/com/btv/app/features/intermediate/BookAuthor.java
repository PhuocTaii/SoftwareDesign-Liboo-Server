package com.btv.app.features.intermediate;

import com.btv.app.features.author.model.Author;
import com.btv.app.features.book.model.Book;
import jakarta.persistence.*;

@Entity
@Table(name = "book_author")
public class BookAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
}
