package com.btv.app.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("api/book")
public class BookRouter {
    @Autowired
    private final BookController bookController;
    public BookRouter(BookController bookController) {
        this.bookController = bookController;
    }
    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks(){
        return bookController.getAllBooks();
    }

    @GetMapping("/getBookByISBN/{ISBN}")
    public Book getBookByISBN(@PathVariable("ISBN") String ISBN){
        return bookController.getBookByISBN(ISBN);
    }
}
