package com.btv.app.book;

import com.btv.app.author.Author;
import com.btv.app.genre.Genre;
import com.btv.app.publisher.Publisher;
import jakarta.persistence.*;

import java.util.*;


@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "isbn", nullable = false, unique = true, length = 13)
    private String ISBN;
    @Column(name = "b_name", nullable = false, length = 100)
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

//    public Book(String ISBN,
//                String name,
//                String author,
//                String publisher,
//                Integer publishYear,
//                String description,
//                String genre,
//                Integer price,
//                Integer quantity,
//                Integer borrowed,
//                String image) {
//        this.ISBN = ISBN;
//        this.name = name;
//        this.author = author;
//        this.publisher = publisher;
//        this.publishYear = publishYear;
//        this.description = description;
//        this.genre = genre;
//        this.price = price;
//        this.quantity = quantity;
//        this.borrowed = borrowed;
//        this.image = image;
//    }
//
//    public Book(Long id,
//                String ISBN,
//                String name,
//                String author,
//                String publisher,
//                Integer publishYear,
//                String description,
//                String genre,
//                Integer price,
//                Integer quantity,
//                Integer borrowed,
//                String image) {
//        this.id = id;
//        this.ISBN = ISBN;
//        this.name = name;
//        this.author = author;
//        this.publisher = publisher;
//        this.publishYear = publishYear;
//        this.description = description;
//        this.genre = genre;
//        this.price = price;
//        this.quantity = quantity;
//        this.borrowed = borrowed;
//        this.image = image;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getISBN() {
//        return ISBN;
//    }
//
//    public void setISBN(String ISBN) {
//        this.ISBN = ISBN;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    public String getPublisher() {
//        return publisher;
//    }
//
//    public void setPublisher(String publisher) {
//        this.publisher = publisher;
//    }
//
//    public Integer getPublishYear() {
//        return publishYear;
//    }
//
//    public void setPublishYear(Integer publishYear) {
//        this.publishYear = publishYear;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getGenre() {
//        return genre;
//    }
//
//    public void setGenre(String genre) {
//        this.genre = genre;
//    }
//
//    public Integer getPrice() {
//        return price;
//    }
//
//    public void setPrice(Integer price) {
//        this.price = price;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public Integer getBorrowed() {
//        return borrowed;
//    }
//
//    public void setBorrowed(Integer borrowed) {
//        this.borrowed = borrowed;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
}
