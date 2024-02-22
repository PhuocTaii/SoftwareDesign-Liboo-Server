package com.btv.app.book;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private Long id;
    private String ISBN;
    private String name;
    private String author;
    private String publisher;
    private Integer publishYear;
    private String description;
    private List<String> genre;
    private Integer price;
    private Integer quantity                                                                                                                                                                                                                          ;
    private Integer borrowed;
    private String image;

    public Book() {
    }

    public Book(String ISBN,
                String name,
                String author,
                String publisher,
                Integer publishYear,
                String description,
                List<String> genre,
                Integer price,
                Integer quantity,
                Integer borrowed,
                String image) {
        this.ISBN = ISBN;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.description = description;
        this.genre = genre;
        this.price = price;
        this.quantity = quantity;
        this.borrowed = borrowed;
        this.image = image;
    }

    public Book(Long id,
                String ISBN,
                String name,
                String author,
                String publisher,
                Integer publishYear,
                String description,
                List<String> genre,
                Integer price,
                Integer quantity,
                Integer borrowed,
                String image) {
        this.id = id;
        this.ISBN = ISBN;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.description = description;
        this.genre = genre;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
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

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
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
