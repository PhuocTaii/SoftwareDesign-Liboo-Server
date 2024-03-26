package com.btv.app.publisher;

import com.btv.app.author.Author;
import com.btv.app.author.AuthorController;
import com.btv.app.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addPublisher")
    public Publisher addPublisher(@ModelAttribute Publisher publisher) {
        return publisherController.addPublisher(publisher);
    }

    @PutMapping("/modifyPublisher/{id}")
    public Publisher modifyPublisher(@PathVariable("id") Long id, @ModelAttribute Publisher publisher){
        return publisherController.modifyPublisher(id, publisher);
    }

    @DeleteMapping("/deletePublisher/{id}")
    public void deletePublisher(@PathVariable("id") Long id){
        publisherController.deletePublisher(id);
    }
}