package com.btv.app.features.user.services;
import com.btv.app.features.user.models.User;
import com.btv.app.features.user.services.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User getUserByID(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }
    public User addUser(User user){
        if(userRepository.existsByEmail(user.getEmail()) || userRepository.existByIdentifier(user.getIdentifier())){
            return null;
        }
        return userRepository.save(user);
    }

    public User modifyUser(User curUser, User updateUser){
        if(updateUser.getName() != null){
            curUser.setName(updateUser.getName());
        }
        if(updateUser.getAddress() != null){
            curUser.setAddress(updateUser.getAddress());
        }
        if(updateUser.getPhone() != null){
            curUser.setPhone(updateUser.getPhone());
        }
        if(updateUser.getGender() != null){
            curUser.setGender(updateUser.getGender());
        }
        if(updateUser.getBirthDate() != null){
            curUser.setBirthDate(updateUser.getBirthDate());
        }

        return userRepository.save(curUser);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

}
