package com.btv.app.features.publisher.services;

import com.btv.app.features.publisher.model.Publisher;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher getPublisherByID(Long id){
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
        return optionalPublisher.orElse(null);
    }

    public Publisher addPublisher(Publisher publisher) {
        if(publisherRepository.existsByName(publisher.getName())){
            throw new DataIntegrityViolationException("Publisher already exists");
        }
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

    public Publisher getPublicByName(String name){
        return publisherRepository.findByName(name);
    }
}
