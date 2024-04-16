package com.btv.app.features.book.services;

import com.btv.app.features.book.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookByID(Long id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null);
    }

    public Book addBook(Book book){
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
        if(updateBook.getAuthors() != null){
            curBook.setAuthors(updateBook.getAuthors());
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
        return bookRepository.save(curBook);
    }
}
