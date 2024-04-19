package com.btv.app.features.user.services;
import com.btv.app.features.user.models.User;
import com.btv.app.features.user.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User getUserByID(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }
    public User addUser(User user){
        return userRepository.save(user);
    }

    public User modifyUser(User curUser, User updateUser){
        if(updateUser.getName() != null){
            curUser.setName(updateUser.getName());
        }
        if(updateUser.getAddress() != null){
            curUser.setAddress(updateUser.getAddress());
        }
        return userRepository.save(curUser);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }


//    @Override
//    public UserDetails loadUserByUsername(String email) {
//        Optional<User> user = userRepository.findByEmail(email);
//        if (user.isEmpty()) {
//            throw new UsernameNotFoundException(email);
//        }
//        return new User(user);
//    }

//    public UserDetails loadUserById(Long id) {
//        User user = userRepository.findById(id).orElse(null);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with id : " + id);
//        }
//        return new User(user);
//    }
}
