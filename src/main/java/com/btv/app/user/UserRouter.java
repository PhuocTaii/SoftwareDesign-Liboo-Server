package com.btv.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user")

public class UserRouter {
    private final UserController userController;
    @Autowired
    public UserRouter(UserController userController) {
        this.userController = userController;
    }
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userController.getAllUsers();
    }

    @GetMapping("/getUserByID/{id}")
    public User getUserByID(@PathVariable("id") Long id){
        return userController.getUserByID(id);
    }
}
