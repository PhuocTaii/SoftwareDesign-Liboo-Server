package com.btv.app.features.author;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    public List<Author> getAllAuthors(){
        try {
            return authorService.getAllAuthors();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Author getAuthorByID(Long id){
        try{
            return authorService.getAuthorByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
