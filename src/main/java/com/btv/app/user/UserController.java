package com.btv.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    public List<User> getAllUsers(){
        try {
            List<User> UserList =  userService.getAllUsers();
            List<User> resList = new ArrayList<User>();

            for(User user: UserList){
                User tmp = new User(user);
                resList.add(tmp);
            }

            return resList;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public User getUserByID(Long id){
        try{
            User tmp = userService.getUserByID(id);
            User resUser = new User(tmp);
            return resUser;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
