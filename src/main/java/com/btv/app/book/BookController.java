package com.btv.app.book;

import com.btv.app.user.User;
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

    public Book addBook(Book book){
        try{
            Book tmp = bookService.addBook(book);
            return new Book(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void deleteBook(Long id){
        try{
            bookService.deleteBook(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
