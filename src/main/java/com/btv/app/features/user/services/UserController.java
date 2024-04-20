package com.btv.app.features.user.services;

import com.btv.app.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("api")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("admin/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        try {
            List<User> res = userService.getAllUsers();
            ResponseEntity.status(200).body(res);
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
        } catch (DataIntegrityViolationException e) {
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

}
