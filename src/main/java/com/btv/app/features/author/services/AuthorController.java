package com.btv.app.features.author.services;

import com.btv.app.exception.MyException;
import com.btv.app.features.author.model.Author;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
@AllArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @AllArgsConstructor
    public static class AuthorListResponse {
        public List<Author> authors;
        public int pageNumber;
        public int totalPages;
        public long totalItems;
    }

    @GetMapping("/all-authors")
    public ResponseEntity<AuthorController.AuthorListResponse> getAllAuthors(@RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber){
        Page<Author> res = authorService.getAuthors(pageNumber);
        return ResponseEntity.ok(new AuthorController.AuthorListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<Author> getAuthorByID(@PathVariable("id") Long id){
        try{
            Author res = authorService.getAuthorByID(id);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/add-author")
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        if(authorService.getAuthorByName(author.getName()) != null) {
            throw new MyException(HttpStatus.CONFLICT, "Author already exists");
        }
        Author res = authorService.addAuthor(author);
        return ResponseEntity.ok(res);
    }


    @PutMapping("/modify-author/{id}")
    public ResponseEntity<Author> modifyAuthor(@PathVariable("id") Long id, @RequestBody Author author){
            Author curAuthor = authorService.getAuthorByID(id);
            if(curAuthor == null){
                throw new MyException(HttpStatus.NOT_FOUND, "Author not found");
            }
            Author res = authorService.modifyAuthor(curAuthor, author);
            if(res == null){
                throw new MyException(HttpStatus.CONFLICT, "Author already exists");
            }
            return ResponseEntity.ok(res);
    }

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAllAuthors(){
        List<Author> res = authorService.allAuthors();
        return ResponseEntity.ok(res);
    }
}
