package com.btv.app.features.book.services;

import com.btv.app.cloudinary.CloudinaryService;
import com.btv.app.exception.MyException;
import com.btv.app.features.book.model.Book;
import com.btv.app.features.image.Image;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;
    private final CloudinaryService cloudinaryService;
    @GetMapping("/all-books")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> res = bookService.getAllBooks();
        if(res == null)
            throw new MyException(HttpStatus.NOT_FOUND, "Book not found");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable("id") Long id){
        Book res = bookService.getBookByID(id);
        if(res == null)
            throw new MyException(HttpStatus.NOT_FOUND, "Book not found");
        return ResponseEntity.ok(res);
    }

    @PostMapping("/admin/add-book")
    public ResponseEntity<Book> addBook(@ModelAttribute Book book){
        if(bookService.getBookByISBN(book.getISBN()) != null)
            throw new MyException(HttpStatus.CONFLICT, "This book is existed");

        Book res = bookService.addBook(book);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/admin/modify-book/{id}")
    public ResponseEntity<Book> modifyBook(@PathVariable("id") Long id, @ModelAttribute Book book){
        Book curBook = bookService.getBookByID(id);
        if(curBook == null){
            return ResponseEntity.status(404).build();
        }
        Book tmp = bookService.modifyBook(curBook, book);
        return ResponseEntity.ok(tmp);
    }

    @DeleteMapping("/admin/delete-book/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Long id){
            Book curBook = bookService.getBookByID(id);
        if(curBook == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Book not found");
        }
        bookService.deleteBook(id);
        return ResponseEntity.ok(curBook);
    }

    @PutMapping("/admin/add-book-image/{id}")
    public ResponseEntity<Book> uploadBookImage(@PathVariable("id") Long id, @RequestParam("image") MultipartFile file){
        Book curBook = bookService.getBookByID(id);
        if(curBook == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Book not found");
        }
        Map data = cloudinaryService.upload(file);
        if(data == null)
            throw new MyException(HttpStatus.INTERNAL_SERVER_ERROR, "Upload image failed");
        Image image = new Image(data.get("public_id").toString(), data.get("secure_url").toString());

        Book res = bookService.uploadImage(curBook, image);
        return ResponseEntity.ok(res);
    }
}
