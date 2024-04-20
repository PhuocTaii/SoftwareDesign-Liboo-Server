package com.btv.app.features.author.services;

import com.btv.app.features.author.model.Author;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/getAllAuthors")
    public ResponseEntity<List<Author>>getAllAuthors(){
        try {
            List<Author> res =  authorService.getAllAuthors();
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getAuthorByID/{id}")
    public ResponseEntity<Author> getAuthorByID(@PathVariable("id") Long id){
        try{
            Author res = authorService.getAuthorByID(id);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/addAuthor")
    public ResponseEntity<Author> addAuthor(@ModelAttribute Author author) {
        try{
            Author res = authorService.addAuthor(author);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


    @PutMapping("/modifyAuthor/{id}")
    public ResponseEntity<Author> modifyAuthor(@PathVariable("id") Long id, @ModelAttribute Author author){
        try{
            Author curAuthor = authorService.getAuthorByID(id);
            if(curAuthor == null){
                return ResponseEntity.status(404).build();
            }
            Author tmp = authorService.modifyAuthor(curAuthor, author);
            Author res = new Author(tmp);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/deleteAuthor/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable("id") Long id){
        try{
            Author curAuthor = authorService.getAuthorByID(id);
            if(curAuthor == null){
                return ResponseEntity.status(404).build();
            }
            authorService.deleteAuthor(id);
            return ResponseEntity.status(200).body(curAuthor);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
