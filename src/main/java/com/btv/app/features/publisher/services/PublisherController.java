package com.btv.app.features.publisher.services;

import com.btv.app.exception.MyException;
import com.btv.app.features.publisher.model.Publisher;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
@AllArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;
    @GetMapping("/all-publishers")
    public ResponseEntity<List<Publisher>> getAllPublishers(){
        List<Publisher> res = publisherService.getAllPublishers();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/publisher/{id}")
    public ResponseEntity<Publisher> getPublisherByID(@PathVariable("id") Long id){
        Publisher res = publisherService.getPublisherByID(id);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/add-publisher")
    public ResponseEntity<Publisher> addPublisher(@ModelAttribute Publisher publisher) {
        if(publisherService.getPublicByName(publisher.getName()) != null) {
            throw new MyException(HttpStatus.CONFLICT, "Publisher already exists");
        }
        Publisher res = publisherService.addPublisher(publisher);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/modify-publisher/{id}")
    public ResponseEntity<Publisher> modifyPublisher(@PathVariable("id") Long id, @ModelAttribute Publisher publisher) {
        Publisher curPub = publisherService.getPublisherByID(id);
        if(curPub == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Publisher not found");
        }
        Publisher res = publisherService.modifyPublisher(curPub, publisher);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/delete-publisher/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable("id") Long id){
        Publisher curPub = publisherService.getPublisherByID(id);
        if(curPub == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Publisher not found");
        }
        publisherService.deletePublisher(id);
        return ResponseEntity.ok(curPub);
    }
}
