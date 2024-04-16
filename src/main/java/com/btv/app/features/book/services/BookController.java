package com.btv.app.features.book.services;

import com.btv.app.features.book.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks(){
        try {
            return bookService.getAllBooks();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @GetMapping("/getBookByID/{id}")
    public Book getBookByID(Long id){
        try{
            return bookService.getBookByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @PostMapping("/addBook")
    public Book addBook(Book book){
        try{
            Book tmp = bookService.addBook(book);
            return new Book(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @DeleteMapping("/deleteBook/{id}")
    public void deleteBook(Long id){
        try{
            bookService.deleteBook(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
