package com.btv.app.features.author.services;

import com.btv.app.features.author.models.Author;
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

    public Author modifyAuthor(Long id, Author author){
        try{
            Author curAuthor = authorService.getAuthorByID(id);
            Author tmp = authorService.modifyAuthor(curAuthor, author);
            return new Author(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void deleteAuthor(Long id){
        try{
            authorService.deleteAuthor(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
