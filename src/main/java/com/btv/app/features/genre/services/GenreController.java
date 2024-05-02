package com.btv.app.features.genre.services;

import com.btv.app.exception.MyException;
import com.btv.app.features.genre.model.Genre;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/admin")
public class GenreController {
    private final GenreService genreService;
    @GetMapping("/all-genres")
    public ResponseEntity<List<Genre>> getAllGenres(){
        List<Genre> res = genreService.getAllGenres();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/genre/{id}")
    public ResponseEntity<Genre> getGenreByID(@PathVariable("id") Long id){
        Genre res = genreService.getGenreByID(id);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/add-genre")
    public ResponseEntity<Genre> addGenre(@ModelAttribute Genre genre){
        if(genreService.findByName(genre.getName()) != null){
            throw new MyException(HttpStatus.BAD_REQUEST, "Genre already exists");
        }
        Genre res = genreService.addGenre(genre);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/modifyGenre/{id}")
    public ResponseEntity<Genre> modifyGenre(@PathVariable("id") Long id, @ModelAttribute Genre genre){
        Genre curGenre = genreService.getGenreByID(id);
        if(curGenre == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Genre not found");
        }
        Genre tmp = genreService.modifyGenre(curGenre, genre);
        return ResponseEntity.ok(tmp);
    }

    @DeleteMapping("/deleteGenre/{id}")
    public ResponseEntity<Genre> deleteGenre(@PathVariable("id") Long id){
        Genre curGenre = genreService.getGenreByID(id);
        if(curGenre == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Genre not found");
        }
        genreService.deleteGenre(id);
        return ResponseEntity.status(200).body(curGenre);
    }
}
