package com.btv.app.genre;

import com.btv.app.book.Book;
import com.btv.app.book.BookService;
import com.btv.app.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    public List<Genre> getAllGenres(){
        try {
            return genreService.getAllGenres();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Genre getGenreByID(Long id){
        try{
            return genreService.getGenreByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Genre addGenre(Genre genre){
        try{
            Genre tmp = genreService.addGenre(genre);
            return new Genre(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Genre modifyGenre(Long id, Genre genre){
        try{
            Genre curGenre = genreService.getGenreByID(id);
            Genre tmp = genreService.modifyGenre(curGenre, genre);
            return new Genre(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void deleteGenre(Long id){
        try{
            genreService.deleteGenre(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
