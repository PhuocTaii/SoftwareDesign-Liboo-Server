package com.btv.app.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
        if(updateUser.getEmail() != null){
            curUser.setEmail(updateUser.getEmail());
        }
        if(updateUser.getName() != null){
            curUser.setName(updateUser.getName());
        }
        if(updateUser.getAddress() != null){
            curUser.setAddress(updateUser.getAddress());
        }
        if(updateUser.getPhone() != null){
            curUser.setPhone(updateUser.getPhone());
        }
        return userRepository.save(curUser);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public PremiumUser addPremiumUser(PremiumUser premiumUser){
        return userRepository.save(premiumUser);
    }
}
