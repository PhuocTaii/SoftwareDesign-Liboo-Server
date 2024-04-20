package com.btv.app.features.genre.services;

import com.btv.app.features.genre.model.Genre;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/admin")
public class GenreController {
    private final GenreService genreService;
    @GetMapping("/getAllGenres")
    public ResponseEntity<List<Genre>> getAllGenres(){
        try {
            List<Genre> res = genreService.getAllGenres();
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getGenreByID/{id}")
    public ResponseEntity<Genre> getGenreByID(@PathVariable("id") Long id){
        try{
            Genre res = genreService.getGenreByID(id);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/addGenre")
    public ResponseEntity<Genre> addGenre(@ModelAttribute Genre genre){
        try{
            Genre res = genreService.addGenre(genre);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/modifyGenre/{id}")
    public ResponseEntity<Genre> modifyGenre(@PathVariable("id") Long id, @ModelAttribute Genre genre){
        try{
            Genre curGenre = genreService.getGenreByID(id);
            if(curGenre == null){
                return ResponseEntity.status(404).build();
            }
            Genre tmp = genreService.modifyGenre(curGenre, genre);
            return ResponseEntity.status(200).body(tmp);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/deleteGenre/{id}")
    public ResponseEntity<Genre> deleteGenre(@PathVariable("id") Long id){
        try{
            Genre curGenre = genreService.getGenreByID(id);
            if(curGenre == null){
                return ResponseEntity.status(404).build();
            }
            genreService.deleteGenre(id);
            return ResponseEntity.status(200).body(curGenre);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
