package com.btv.app.features.author.services;

import com.btv.app.features.author.model.Author;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorByID(Long id){
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        return optionalAuthor.orElse(null);
    }

    public Author addAuthor(Author author){
        if(authorRepository.existsByName(author.getName())){
            throw new DataIntegrityViolationException("Author already exists");
        }
        return authorRepository.save(author);
    }

    public Author modifyAuthor(Author curAuthor, Author updateAuthor){
        if(updateAuthor.getName() != null){
            curAuthor.setName(updateAuthor.getName());
        }
        return authorRepository.save(curAuthor);
    }

    public void deleteAuthor(Long id){
        authorRepository.deleteById(id);
    }
}
