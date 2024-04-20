package com.btv.app.features.book.services;

import com.btv.app.features.book.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;
    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks(){
        try {
            List<Book> res = bookService.getAllBooks();
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getBookByID/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable("id") Long id){
        try{
            Book res = bookService.getBookByID(id);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("admin/addBook")
    public ResponseEntity<Book> addBook(@ModelAttribute Book book){
        try{
            Book res = bookService.addBook(book);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("admin/modifyBook/{id}")
    public ResponseEntity<Book> modifyBook(@PathVariable("id") Long id, @ModelAttribute Book book){
        try{
            Book curBook = bookService.getBookByID(id);
            if(curBook == null){
                return ResponseEntity.status(404).build();
            }
            Book tmp = bookService.modifyBook(curBook, book);
            return ResponseEntity.status(200).body(tmp);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("admin/deleteBook/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Long id){
        try{
            Book curBook = bookService.getBookByID(id);
            if(curBook == null){
                return ResponseEntity.status(404).build();
            }
            bookService.deleteBook(id);
            return ResponseEntity.status(200).body(curBook);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
