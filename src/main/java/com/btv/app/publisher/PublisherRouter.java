package com.btv.app.publisher;

import com.btv.app.author.Author;
import com.btv.app.author.AuthorController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/publisher")
public class PublisherRouter {
    private final PublisherController publisherController;
    @Autowired
    public PublisherRouter(PublisherController publisherController) {
        this.publisherController = publisherController;
    }

    @GetMapping("/getAllPublishers")
    public List<Publisher> getAllPublishers(){
        return publisherController.getAllPublishers();
    }

    @GetMapping("/getPublisherByID/{id}")
    public Publisher getPublisherByID(@PathVariable("id") Long id){
        return publisherController.getPublisherByID(id);
    }
}