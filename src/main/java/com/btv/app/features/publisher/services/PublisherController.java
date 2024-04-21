package com.btv.app.features.publisher.services;

import com.btv.app.features.publisher.model.Publisher;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
@AllArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;
    @GetMapping("/getAllPublishers")
    public ResponseEntity<List<Publisher>> getAllPublishers(){
        try {
            List<Publisher> res = publisherService.getAllPublishers();
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getPublisherByID/{id}")
    public ResponseEntity<Publisher> getPublisherByID(@PathVariable("id") Long id){
        try{
            Publisher res = publisherService.getPublisherByID(id);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/addPublisher")
    public ResponseEntity<Publisher> addPublisher(@ModelAttribute Publisher publisher) {
        try{
            Publisher res = publisherService.addPublisher(publisher);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/modifyPublisher/{id}")
    public ResponseEntity<Publisher> modifyPublisher(@PathVariable("id") Long id, @ModelAttribute Publisher publisher) {
        try{
            Publisher curPub = publisherService.getPublisherByID(id);
            if(curPub == null){
                return ResponseEntity.status(404).build();
            }
            Publisher res = publisherService.modifyPublisher(curPub, publisher);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/deletePublisher/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable("id") Long id){
        try{
            Publisher curPub = publisherService.getPublisherByID(id);
            if(curPub == null){
                return ResponseEntity.status(404).build();
            }
            publisherService.deletePublisher(id);
            return ResponseEntity.status(200).body(curPub);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
