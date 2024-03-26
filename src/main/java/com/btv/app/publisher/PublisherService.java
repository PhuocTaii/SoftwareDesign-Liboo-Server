package com.btv.app.publisher;

import com.btv.app.author.Author;
import com.btv.app.author.AuthorRepository;
import com.btv.app.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;
    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher getPublisherByID(Long id){
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
        return optionalPublisher.orElse(null);
    }

    public Publisher addPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Publisher modifyPublisher(Publisher curPub, Publisher updatePub){
        if(updatePub.getName() != null){
            curPub.setName(updatePub.getName());
        }
        return publisherRepository.save(curPub);
    }

    public void deletePublisher(Long id){
        publisherRepository.deleteById(id);
    }
}
