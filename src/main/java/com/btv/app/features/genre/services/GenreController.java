package com.btv.app.features.genre.services;

import com.btv.app.exception.MyException;
import com.btv.app.features.author.model.Author;
import com.btv.app.features.author.services.AuthorController;
import com.btv.app.features.genre.model.Genre;
import com.btv.app.features.publisher.model.Publisher;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/admin")
public class GenreController {
    private final GenreService genreService;

    @AllArgsConstructor
    public static class GenreListResponse {
        public List<Genre> genres;
        public int pageNumber;
        public int totalPages;
        public long totalItems;
    }

    @GetMapping("/all-genres")
    public ResponseEntity<GenreController.GenreListResponse> getAllGenres(@RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber){
        Page<Genre> res = genreService.getAllGenres(pageNumber);
        return ResponseEntity.ok(new GenreController.GenreListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
    }

    @GetMapping("/genre/{id}")
    public ResponseEntity<Genre> getGenreByID(@PathVariable("id") Long id){
        Genre res = genreService.getGenreByID(id);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/add-genre")
    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre){
        if(genreService.findByName(genre.getName()) != null){
            throw new MyException(HttpStatus.BAD_REQUEST, "Genre already exists");
        }
        Genre res = genreService.addGenre(genre);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/modify-genre/{id}")
    public ResponseEntity<Genre> modifyGenre(@PathVariable("id") Long id, @RequestBody Genre genre){
        Genre curGenre = genreService.getGenreByID(id);
        if(curGenre == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Genre not found");
        }
        Genre tmp = genreService.modifyGenre(curGenre, genre);
        if(tmp == null){
            throw new MyException(HttpStatus.BAD_REQUEST, "Genre already exists");
        }
        return ResponseEntity.ok(tmp);
    }

    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getAllGenres(){
        List<Genre> res = genreService.allGenres();
        return ResponseEntity.ok(res);
    }
}
