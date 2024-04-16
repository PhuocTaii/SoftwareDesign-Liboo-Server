package com.btv.app.features.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public List<Book> getAllBooks(){
        try {
            return bookService.getAllBooks();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Book getBookByID(Long id){
        try{
            return bookService.getBookByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

//    public Book addBook(Book book){
//        try{
//            return bookService.addBook(book);
//        } catch (Exception e) {
//            System.out.println(e);
//            return null;
//        }
//    }
}
