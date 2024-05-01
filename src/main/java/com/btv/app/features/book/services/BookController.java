package com.btv.app.features.book.services;

import com.btv.app.cloudinary.CloudinaryService;
import com.btv.app.features.book.model.Book;
import com.btv.app.features.image.model.Image;
import com.btv.app.features.image.services.ImageService;
import lombok.AllArgsConstructor;
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

    @PostMapping("/admin/addBook")
    public ResponseEntity<Book> addBook(@ModelAttribute Book book){
        try{
            Book res = bookService.addBook(book);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/admin/modifyBook/{id}")
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

    @DeleteMapping("/admin/deleteBook/{id}")
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

    @PutMapping("/admin/addBookImage/{id}")
    public ResponseEntity<Book> uploadBookImage(@PathVariable("id") Long id, @RequestParam("image") MultipartFile file){
        try{
            Book curBook = bookService.getBookByID(id);
            if(curBook == null){
                return ResponseEntity.status(404).build();
            }
            Map data = cloudinaryService.upload(file);
            Image image = new Image(data.get("public_id").toString(), data.get("secure_url").toString());
            Book res = bookService.uploadImage(curBook, image);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
