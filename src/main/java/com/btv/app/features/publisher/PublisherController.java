package com.btv.app.features.publisher;

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
