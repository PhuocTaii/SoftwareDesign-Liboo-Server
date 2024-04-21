package com.btv.app.features.genre.services;

import com.btv.app.features.genre.model.Genre;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreByID(Long id){
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        return optionalGenre.orElse(null);
    }

    public Genre addGenre(Genre genre){
        if(genreRepository.existsByName(genre.getName())){
            throw new DataIntegrityViolationException("Genre already exists");
        }
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
