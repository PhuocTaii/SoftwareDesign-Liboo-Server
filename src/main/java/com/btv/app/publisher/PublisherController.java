package com.btv.app.publisher;

import com.btv.app.author.Author;
import com.btv.app.author.AuthorService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    public List<Publisher> getAllPublishers(){
        try {
            return publisherService.getAllPublishers();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Publisher getPublisherByID(Long id){
        try{
            return publisherService.getPublisherByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
