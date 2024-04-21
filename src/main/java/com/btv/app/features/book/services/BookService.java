package com.btv.app.features.book.services;

import com.btv.app.features.book.model.Book;
import com.btv.app.features.image.model.Image;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
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
}
