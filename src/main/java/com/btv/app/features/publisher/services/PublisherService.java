package com.btv.app.features.publisher.services;

import com.btv.app.features.author.model.Author;
import com.btv.app.features.book.services.BookService;
import com.btv.app.features.publisher.model.Publisher;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final BookService bookService;
    private final Integer PAGE_SIZE = 5;

    public Page<Publisher> getAllPublishers(int pageNumber) {
        return publisherRepository.findAll(PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").ascending()));
    }

    public Publisher getPublisherByID(Long id){
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
        return optionalPublisher.orElse(null);
    }

    public Publisher addPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Publisher modifyPublisher(Publisher curPub, Publisher updatePub){
        if(publisherRepository.existsByName(updatePub.getName())){
            return null;
        }

        if(updatePub.getName() != null){
            curPub.setName(updatePub.getName());
        }
        return publisherRepository.save(curPub);
    }

    public void deletePublisher(Long id){
        bookService.deleteBookByPublisher(id);
        publisherRepository.deleteById(id);
    }

    public Publisher getPublicByName(String name){
        return publisherRepository.findByName(name);
    }
}
