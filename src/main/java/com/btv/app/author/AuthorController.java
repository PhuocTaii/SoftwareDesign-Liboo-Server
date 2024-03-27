package com.btv.app.author;

import com.btv.app.genre.Genre;
import com.btv.app.user.User;
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

    public Author addAuthor(Author author) {
        try{
            Author tmp = authorService.addAuthor(author);
            return new Author(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
