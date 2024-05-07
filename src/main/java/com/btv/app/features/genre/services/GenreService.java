package com.btv.app.features.genre.services;

import com.btv.app.features.author.model.Author;
import com.btv.app.features.book.services.BookService;
import com.btv.app.features.genre.model.Genre;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookService bookService;
    private final Integer PAGE_SIZE = 5;
    public Page<Genre> getAllGenres(int pageNumber) {
        return genreRepository.findAll(PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").ascending()));
    }
    public Genre getGenreByID(Long id){
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        return optionalGenre.orElse(null);
    }

    public Genre addGenre(Genre genre){
        return genreRepository.save(genre);
    }

    public Genre modifyGenre(Genre curGenre, Genre updateGenre){
        if(genreRepository.existsByName(updateGenre.getName())){
            return null;
        }
        if(updateGenre.getName() != null){
            curGenre.setName(updateGenre.getName());
        }
        return genreRepository.save(curGenre);
    }

    public void deleteGenre(Long id){
        bookService.deleteBookByGenre(id);
        genreRepository.deleteById(id);
    }

    public Genre findByName(String name){
        return genreRepository.findByName(name);
    }
}
