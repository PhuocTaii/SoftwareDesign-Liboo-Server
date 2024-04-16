package com.btv.app.features.author.services;

import com.btv.app.features.author.model.Author;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/author")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/getAllAuthors")
    public List<Author> getAllAuthors(){
        try {
            return authorService.getAllAuthors();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @GetMapping("/getAuthorByID/{id}")
    public Author getAuthorByID(@PathVariable("id") Long id){
        try{
            return authorService.getAuthorByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @PostMapping("/addAuthor")
    public Author addAuthor(@ModelAttribute Author author) {
        try{
            Author tmp = authorService.addAuthor(author);
            return new Author(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @PutMapping("/modifyAuthor/{id}")
    public Author modifyAuthor(@PathVariable("id") Long id, @ModelAttribute Author author){
        try{
            Author curAuthor = authorService.getAuthorByID(id);
            Author tmp = authorService.modifyAuthor(curAuthor, author);
            return new Author(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @DeleteMapping("/deleteAuthor/{id}")
    public void deleteAuthor(@PathVariable("id") Long id){
        try{
            authorService.deleteAuthor(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
