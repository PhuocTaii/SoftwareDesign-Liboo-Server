package com.btv.app.features.publisher.services;

import com.btv.app.features.publisher.model.Publisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/publisher")
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/getAllPublishers")
    public List<Publisher> getAllPublishers(){
        try {
            return publisherService.getAllPublishers();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @GetMapping("/getPublisherByID/{id}")
    public Publisher getPublisherByID(@PathVariable("id") Long id){
        try{
            return publisherService.getPublisherByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @PostMapping("/addPublisher")
    public Publisher addPublisher(@ModelAttribute Publisher publisher) {
        try{
            Publisher tmp = publisherService.addPublisher(publisher);
            return new Publisher(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @PutMapping("/modifyPublisher/{id}")
    public Publisher modifyPublisher(@PathVariable("id") Long id, @ModelAttribute Publisher publisher) {
        try{
            Publisher curPub = publisherService.getPublisherByID(id);
            Publisher tmp = publisherService.modifyPublisher(curPub, publisher);
            return new Publisher(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @DeleteMapping("/deletePublisher/{id}")
    public void deletePublisher(@PathVariable("id") Long id){
        try{
            publisherService.deletePublisher(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
