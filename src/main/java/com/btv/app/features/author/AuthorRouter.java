package com.btv.app.features.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/author")
public class AuthorRouter {
    private final AuthorController authorController;
    @Autowired
    public AuthorRouter(AuthorController authorController) {
        this.authorController = authorController;
    }

    @GetMapping("/getAllAuthors")
    public List<Author> getAllAuthors(){
        return authorController.getAllAuthors();
    }

    @GetMapping("/getAuthorByID/{id}")
    public Author getAuthorByID(@PathVariable("id") Long id){
        return authorController.getAuthorByID(id);
    }
}
