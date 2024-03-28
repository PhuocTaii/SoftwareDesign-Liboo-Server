package com.btv.app.genre;

import com.btv.app.book.Book;
import com.btv.app.book.BookRepository;
import com.btv.app.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreByID(Long id){
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        return optionalGenre.orElse(null);
    }

    public Genre addGenre(Genre genre){
        return genreRepository.save(genre);
    }

    public Genre modifyGenre(Genre curGenre, Genre updateGenre){
        if(updateGenre.getName() != null){
            curGenre.setName(updateGenre.getName());
        }
        return genreRepository.save(curGenre);
    }

    public void deleteGenre(Long id){
        genreRepository.deleteById(id);
    }
}
