package com.btv.app.genre;


import com.btv.app.book.Book;
import com.btv.app.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/genre")
public class GenreRouter {
    private final GenreController genreController;

    @Autowired
    public GenreRouter(GenreController genreController) {
        this.genreController = genreController;
    }

    @GetMapping("/getAllGenres")
    public List<Genre> getAllGenres(){
        return genreController.getAllGenres();
    }

    @GetMapping("/getGenreByID/{id}")
    public Genre getGenreByID(@PathVariable("id") Long id){
        return genreController.getGenreByID(id);
    }

    @PostMapping("/addGenre")
    public Genre addGenre(@ModelAttribute Genre genre){
        return genreController.addGenre(genre);
    }

    @PutMapping("/modifyGenre/{id}")
    public Genre modifyGenre(@PathVariable("id") Long id, @ModelAttribute Genre genre){
        return genreController.modifyGenre(id, genre);
    }

    @DeleteMapping("/deleteGenre/{id}")
    public void deleteGenre(@PathVariable("id") Long id){
        genreController.deleteGenre(id);
    }
}
