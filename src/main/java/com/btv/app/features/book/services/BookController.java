package com.btv.app.features.book.services;

import com.btv.app.cloudinary.CloudinaryService;
import com.btv.app.exception.MyException;
import com.btv.app.features.book.model.Book;
import com.btv.app.features.image.Image;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Book> addBook(@RequestParam("uploadedImage") MultipartFile file, @RequestParam("book") String bookString) throws JsonProcessingException {
        // convert JSON string to Book object
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.readValue(bookString, Book.class);

        if(bookService.existsByISBN(book.getISBN()))
            throw new MyException(HttpStatus.CONFLICT, "This book is existed");

        Map data = cloudinaryService.upload(file);
        if(data == null){
            throw new MyException(HttpStatus.INTERNAL_SERVER_ERROR, "Upload image failed");
        }
        Image image = new Image(data.get("public_id").toString(), data.get("secure_url").toString());
        book.setImage(image);

        Book res = bookService.addBook(book);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/admin/modify-book/{id}")
    public ResponseEntity<Book> modifyBook(@PathVariable("id") Long id, @RequestParam(value = "uploadedImage", required = false) MultipartFile file, @RequestParam("book") String bookString) throws JsonProcessingException{
        Book curBook = bookService.getBookByID(id);
        if(curBook == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Book not found");
        }

        // convert JSON string to Book object
        ObjectMapper mapper = new ObjectMapper();
        Book book = mapper.readValue(bookString, Book.class);

        if(file != null){
            Map data = cloudinaryService.upload(file);
            if(data == null){
                throw new MyException(HttpStatus.INTERNAL_SERVER_ERROR, "Upload image failed");
            }
            Image image = new Image(data.get("public_id").toString(), data.get("secure_url").toString());
            book.setImage(image);
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

    @GetMapping("/books/name")
    public ResponseEntity<List<Book>> searchBooksByName(
            @RequestParam(value = "name", required = false, defaultValue = "") String name
    ){
        List<Book> res = bookService.getBookByName(name);
        return ResponseEntity.ok(res);
    }

    @GetMapping("admin/books")
    public ResponseEntity<BookListResponse> getAdminBooks(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "search-by", required = false, defaultValue = "") String searchBy,
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "sort-by", required = false, defaultValue = "") String sortBy
    ){
        Page<Book> res = bookService.getAdminBooks(pageNumber, searchBy, query, sortBy);
        return ResponseEntity.ok(new BookListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
    }

    @GetMapping("librarian/books")
    public ResponseEntity<BookListResponse> getLibrarianBooks(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "search-by", required = false, defaultValue = "") String searchBy,
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "sort-by", required = false, defaultValue = "") String sortBy
    ){
        Page<Book> res = bookService.getBooks(pageNumber, searchBy, query, sortBy);
        return ResponseEntity.ok(new BookListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
    }

    @GetMapping("admin/books-amount")
    public ResponseEntity<Integer> getBooksAmount(){
        Integer res = bookService.getNumberOfBooks();
        return ResponseEntity.ok(res);
    }

    @PutMapping("admin/modify-book-status/{id}")
    public ResponseEntity<Book> modifyBook(@PathVariable("id") Long id, @RequestParam("status") Boolean status){
        Book book = bookService.getBookByID(id);
        if(book == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Book not found");
        }
        Book res = bookService.modifyBookStatus(book, status);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/user/books")
    public ResponseEntity<BookListResponse> getBooks(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "search-by", required = false, defaultValue = "") String searchBy,
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "sort-by", required = false, defaultValue = "") String sortBy
    ){
        System.out.println("pageNumber = " + pageNumber);
        System.out.println("searchBy = " + searchBy);
        System.out.println("query = " + query);
        System.out.println("sortBy = " + sortBy);

        Page<Book> res = bookService.getBooks(pageNumber, searchBy, query, sortBy);
        return ResponseEntity.ok(new BookListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
    }
}
