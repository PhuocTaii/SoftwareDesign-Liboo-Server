package com.btv.app.features.author.services;

import com.btv.app.features.author.model.Author;
import com.btv.app.features.book.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookService bookService;
    private final Integer PAGE_SIZE = 5;

    public Page<Author> getAuthors(int pageNumber) {
        return authorRepository.findAll(PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").ascending()));
    }

    public Author getAuthorByID(Long id){
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        return optionalAuthor.orElse(null);
    }

    public Author addAuthor(Author author){
        return authorRepository.save(author);
    }

    public Author modifyAuthor(Author curAuthor, Author updateAuthor){
        if(authorRepository.existsByName(updateAuthor.getName())){
            return null;
        }
        if(updateAuthor.getName() != null){
            curAuthor.setName(updateAuthor.getName());
        }
        return authorRepository.save(curAuthor);
    }

    public Author getAuthorByName(String name){
        return authorRepository.findByName(name);
    }

    public List<Author> allAuthors(){
        return authorRepository.findAll();
    }
}
