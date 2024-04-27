package com.btv.app.features.user.services;

import com.btv.app.cloudinary.CloudinaryService;
import com.btv.app.features.book.model.Book;
import com.btv.app.features.image.model.Image;
import com.btv.app.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    @GetMapping("admin/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        try {
            List<User> res = userService.getAllUsers();
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("admin/getUserByID/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable("id") Long id){
        try{
            User res = userService.getUserByID(id);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("admin/addUser")
    public ResponseEntity<User> addUser(@ModelAttribute User user){
        try{
            User res = userService.addUser(user);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("admin/modifyUser/{id}")
    public ResponseEntity<User> modifyUser(@PathVariable("id") Long id, @ModelAttribute User user){
        try{
            User curUser = userService.getUserByID(id);
            if(curUser == null){
                return ResponseEntity.status(404).build();
            }
            User res = userService.modifyUser(curUser, user);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("admin/deleteUser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id){
        try{
            User curUser = userService.getUserByID(id);
            if(curUser == null){
                return ResponseEntity.status(404).build();
            }
            userService.deleteUser(id);
            return ResponseEntity.status(200).body(curUser);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/addUserImage/{id}")
    public ResponseEntity<User> uploadUserImage(@PathVariable("id") Long id, @RequestParam("image") MultipartFile file){
        try{
            User curUser = userService.getUserByID(id);
            if(curUser == null){
                return ResponseEntity.status(404).build();
            }
            Map data = cloudinaryService.upload(file);
            Image image = new Image(data.get("public_id").toString(), data.get("secure_url").toString());
            User res = userService.uploadImage(curUser, image);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
