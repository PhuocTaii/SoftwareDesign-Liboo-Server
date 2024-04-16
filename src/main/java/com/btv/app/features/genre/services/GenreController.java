package com.btv.app.features.genre.services;

import com.btv.app.features.genre.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/genre")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/getAllGenres")
    public List<Genre> getAllGenres(){
        try {
            return genreService.getAllGenres();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @GetMapping("/getGenreByID/{id}")
    public Genre getGenreByID(@PathVariable("id") Long id){
        try{
            return genreService.getGenreByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @PostMapping("/addGenre")
    public Genre addGenre(@ModelAttribute Genre genre){
        try{
            Genre tmp = genreService.addGenre(genre);
            return new Genre(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @PutMapping("/modifyGenre/{id}")
    public Genre modifyGenre(@PathVariable("id") Long id, @ModelAttribute Genre genre){
        try{
            Genre curGenre = genreService.getGenreByID(id);
            Genre tmp = genreService.modifyGenre(curGenre, genre);
            return new Genre(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @DeleteMapping("/deleteGenre/{id}")
    public void deleteGenre(@PathVariable("id") Long id){
        try{
            genreService.deleteGenre(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
