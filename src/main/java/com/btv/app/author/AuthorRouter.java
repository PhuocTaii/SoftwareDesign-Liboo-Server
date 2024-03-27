package com.btv.app.author;

import com.btv.app.genre.Genre;
import com.btv.app.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addAuthor")
    public Author addAuthor(@ModelAttribute Author author){
        return authorController.addAuthor(author);
    }
}
