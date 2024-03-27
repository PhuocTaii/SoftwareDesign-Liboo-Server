package com.btv.app.book;

import com.btv.app.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/book")

public class BookRouter {
    private final BookController bookController;
    @Autowired
    public BookRouter(BookController bookController) {
        this.bookController = bookController;
    }
    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks(){
        return bookController.getAllBooks();
    }

    @GetMapping("/getBookByID/{id}")
    public Book getBookByID(@PathVariable("id") Long id){
        return bookController.getBookByID(id);
    }

    @PostMapping("/addBook")
    public Book addBook(@ModelAttribute Book book){
        return bookController.addBook(book);
    }

    @DeleteMapping("/deleteBook/{id}")
    public void deleteBook(@PathVariable("id") Long id){
        bookController.deleteBook(id);
    }
}
