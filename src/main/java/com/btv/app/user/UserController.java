package com.btv.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public List<User> getAllUsers(){
        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public User getUserByID(Long id){
        try{
            return userService.getUserByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
