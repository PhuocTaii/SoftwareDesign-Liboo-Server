package com.btv.app.features.book.services;

import com.btv.app.cloudinary.CloudinaryService;
import com.btv.app.exception.MyException;
import com.btv.app.features.book.model.Book;
import com.btv.app.features.image.Image;
import com.btv.app.features.transaction.models.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;
    private final CloudinaryService cloudinaryService;

    @AllArgsConstructor
    public static class BookListResponse {
        public List<Book> books;
        public int pageNumber;
        public int totalPages;
        public long totalItems;
    }

    @GetMapping("/all-books")
    public ResponseEntity<BookListResponse> getAllBooks(@RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber){
        Page<Book> res = bookService.getAllBooks(pageNumber);
        return ResponseEntity.ok(new BookListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable("id") Long id){
        Book res = bookService.getBookByID(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/book/isbn/{isbn}")
    public ResponseEntity<Book> getBookByISBN(@PathVariable("isbn") String ISBN){
        Book res = bookService.getBookByISBN(ISBN);
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

    @GetMapping("/books")
    public ResponseEntity<List<Book>> searchBook(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "isbn", required = false, defaultValue = "") String isbn
    ){
        System.out.println(name);
        System.out.println(isbn);
        List<Book> res;
        if(!Objects.equals(name, "") && Objects.equals(isbn, ""))
            res = bookService.getBookByName(name);
        else if(!Objects.equals(isbn, "") && Objects.equals(name, ""))
            res = bookService.getBooksContainIsbn(isbn);
        else
            res = new ArrayList<>();
        return ResponseEntity.ok(res);
    }

    @GetMapping("admin/books-amount")
    public ResponseEntity<Integer> getBooksAmount(){
        Integer res = bookService.getNumberOfBooks();
        return ResponseEntity.ok(res);
    }
}
