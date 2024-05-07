package com.btv.app.features.book.services;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.image.Image;
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
public class BookService {
    private final Integer PAGE_SIZE = 10;
    private final BookRepository bookRepository;
    public Page<Book> getAllBooks(int pageNumber) {
        return bookRepository.findAll(PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
    }

    public Book getBookByID(Long id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null);
    }

    public Book addBook(Book book){
        if(bookRepository.existsByISBN(book.getISBN())){
            throw new DataIntegrityViolationException("Book already exists");
        }
        return bookRepository.save(book);
    }
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }

    public Book modifyBook(Book curBook, Book updateBook){
        if(updateBook.getISBN() != null){
            curBook.setISBN(updateBook.getISBN());
        }
        if(updateBook.getName() != null){
            curBook.setName(updateBook.getName());
        }
        if(updateBook.getAuthor() != null){
            curBook.setAuthor(updateBook.getAuthor());
        }
        if(updateBook.getGenres() != null){
            curBook.setGenres(updateBook.getGenres());
        }
        if(updateBook.getPublisher() != null){
            curBook.setPublisher(updateBook.getPublisher());
        }
        if(updateBook.getPublishYear() != null){
            curBook.setPublishYear(updateBook.getPublishYear());
        }
        if(updateBook.getPrice() != null){
            curBook.setPrice(updateBook.getPrice());
        }
        if(updateBook.getQuantity() != null){
            curBook.setQuantity(updateBook.getQuantity());
        }
        if(updateBook.getBorrowed() != null){
            curBook.setBorrowed(updateBook.getBorrowed());
        }
        if(updateBook.getDescription() != null){
            curBook.setDescription(updateBook.getDescription());
        }
        if(updateBook.getImage() != null){
            curBook.setImage(updateBook.getImage());
        }
        return bookRepository.save(curBook);
    }

    public Book uploadImage(Book book, Image image){
        book.setImage(image);
        return bookRepository.save(book);
    }

    public Book increaseBookBorrowed(Book book){



        book.setBorrowed(book.getBorrowed() + 1);
        return bookRepository.save(book);
    }

    public Book decreaseBookBorrowed(Book book){
        book.setBorrowed(book.getBorrowed() - 1);
        return bookRepository.save(book);
    }

    public Book decreaseBookAmount(Book book){
        book.setQuantity(book.getQuantity() - 1);
        return bookRepository.save(book);
    }

    public Book getBookByISBN(String ISBN){
        return bookRepository.findByISBN(ISBN);
    }

    public List<Book> getBookByName(String name){
        return bookRepository.findByNameContainsAllIgnoreCase(name);
    }

    public Page<Book> getBooks(int pageNumber, String searchBy, String query){
        if(searchBy.equals("") || query.equals("")) {
            return bookRepository.findAll(PageRequest.of(pageNumber, PAGE_SIZE));
        }
        if(searchBy.equals("isbn")){
            return bookRepository.findByISBNContainsAllIgnoreCase(query, PageRequest.of(pageNumber, PAGE_SIZE));
        }
        if(searchBy.equals("name")) {
            return bookRepository.findByNameContainsAllIgnoreCase(query, PageRequest.of(pageNumber, PAGE_SIZE));
        }
        return bookRepository.findAll(PageRequest.of(pageNumber, PAGE_SIZE));
    }
}
