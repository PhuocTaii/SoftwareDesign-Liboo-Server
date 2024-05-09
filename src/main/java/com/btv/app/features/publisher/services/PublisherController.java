package com.btv.app.features.publisher.services;

import com.btv.app.exception.MyException;
import com.btv.app.features.author.model.Author;
import com.btv.app.features.author.services.AuthorController;
import com.btv.app.features.publisher.model.Publisher;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
@AllArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;
    @GetMapping("/publishers")
    public ResponseEntity<List<Publisher>> getAllPublishers(){
        List<Publisher> res = publisherService.allPublisher();
        return ResponseEntity.ok(res);
    }
    @AllArgsConstructor
    public static class PublisherListResponse {
        public List<Publisher> publishers;
        public int pageNumber;
        public int totalPages;
        public long totalItems;
    }

    @GetMapping("/all-publishers")
    public ResponseEntity<PublisherController.PublisherListResponse> getAllPublishers(@RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber){
        Page<Publisher> res = publisherService.getAllPublishers(pageNumber);
        return ResponseEntity.ok(new PublisherController.PublisherListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
    }

    @GetMapping("/publisher/{id}")
    public ResponseEntity<Publisher> getPublisherByID(@PathVariable("id") Long id){
        Publisher res = publisherService.getPublisherByID(id);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/add-publisher")
    public ResponseEntity<Publisher> addPublisher(@RequestBody Publisher publisher) {
        if(publisherService.getPublicByName(publisher.getName()) != null) {
            throw new MyException(HttpStatus.CONFLICT, "Publisher already exists");
        }
        Publisher res = publisherService.addPublisher(publisher);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/modify-publisher/{id}")
    public ResponseEntity<Publisher> modifyPublisher(@PathVariable("id") Long id, @RequestBody Publisher publisher) {
        Publisher curPub = publisherService.getPublisherByID(id);
        if(curPub == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Publisher not found");
        }
        Publisher res = publisherService.modifyPublisher(curPub, publisher);
        if(res == null){
            throw new MyException(HttpStatus.CONFLICT, "Publisher already exists");
        }
        return ResponseEntity.ok(res);
    }

}
