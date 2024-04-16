package com.btv.app.features.user.services;

import com.btv.app.features.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/getAllUsers")
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
    @GetMapping("/getUserByID/{id}")
    public User getUserByID(@PathVariable("id") Long id){
        try{
            User tmp = userService.getUserByID(id);
            return new User(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @PostMapping("/addUser")
        public User addUser(@ModelAttribute User user){
        try{
            User tmp = userService.addUser(user);
            return new User(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @PutMapping("/modifyUser/{id}")
    public User modifyUser(@PathVariable("id") Long id, @ModelAttribute User user){
        try{
            User curUser = userService.getUserByID(id);
            User tmp = userService.modifyUser(curUser, user);
            return new User(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        try{
            userService.deleteUser(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
