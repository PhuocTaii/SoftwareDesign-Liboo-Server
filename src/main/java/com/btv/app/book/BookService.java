package com.btv.app.book;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookService {
    public List<Book> getAllBooks() {
        return List.of(
            new Book(1L,
                "9780743273565",
                "abcde",
                "Phuoc Tai",
                "Scribner",
                2020,
                "The Great Gatsby is a novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922.",
                Arrays.asList("Novel", "Fiction"),
                100000,
                 100,
                0,
                " "
            ),
            new Book(2L,
                "9780743273564",
                "xyz",
                "Phuoc Tai",
                "Thanh Van",
                2003,
                "Gia Bao",
                Arrays.asList("Novel", "Fiction"),
                150000,
                100,
                0,
                " "
            )
        );
    }

    public Book getBookByISBN(String ISBN){
        return new Book(1L,
                "9780743273565",
                "abcde",
                "Phuoc Tai",
                "Scribner",
                2020,
                "The Great Gatsby is a novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922.",
                Arrays.asList("Novel", "Fiction"),
                100000,
                100,
                0,
                " "
        );
    }
}
