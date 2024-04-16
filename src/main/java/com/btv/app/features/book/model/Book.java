package com.btv.app.features.book.model;

import com.btv.app.features.author.model.Author;
import com.btv.app.features.genre.model.Genre;
import com.btv.app.features.publisher.Publisher;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


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
    @ManyToMany
    @Column(name = "author", nullable = false, length = 100)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors = new ArrayList<>();
    @ManyToOne()
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

    public Book() {
    }

    public Book(Book book) {
        this.ISBN = book.getISBN();
        this.name = book.getName();
        this.authors = new ArrayList<>(book.getAuthors());
        this.publisher = book.getPublisher();
        this.publishYear = book.getPublishYear();
        this.description = book.getDescription();
        this.genres = new ArrayList<>(book.getGenres());
        this.price = book.getPrice();
        this.quantity = book.getQuantity();
        this.borrowed = book.getBorrowed();
        this.image = book.getImage();
    }

    public Book(String ISBN,
                String name,
                List<Author> authors,
                Publisher publisher,
                Integer publishYear,
                String description,
                List<Genre> genres,
                Integer price,
                Integer quantity,
                Integer borrowed,
                String image) {
        this.ISBN = ISBN;
        this.name = name;
        this.authors = authors;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.description = description;
        this.genres = genres;
        this.price = price;
        this.quantity = quantity;
        this.borrowed = borrowed;
        this.image = image;
    }

    public Book(Long id,
                String ISBN,
                String name,
                List<Author> authors,
                Publisher publisher,
                Integer publishYear,
                String description,
                List<Genre> genres,
                Integer price,
                Integer quantity,
                Integer borrowed,
                String image) {
        this.id = id;
        this.ISBN = ISBN;
        this.name = name;
        this.authors = authors;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.description = description;
        this.genres = genres;
        this.price = price;
        this.quantity = quantity;
        this.borrowed = borrowed;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Integer borrowed) {
        this.borrowed = borrowed;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
