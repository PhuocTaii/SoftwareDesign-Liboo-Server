package com.btv.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addUser")
    public User addUser(@ModelAttribute User user){
        return userController.addUser(user);
    }

    @PostMapping("/addPremiumUser")
    public PremiumUser addPremiumUser(@ModelAttribute PremiumUser premiumUser){
        return userController.addPremiumUser(premiumUser);
    }

}
