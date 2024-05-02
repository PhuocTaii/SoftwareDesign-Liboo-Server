package com.btv.app.features.user.services;
import com.btv.app.features.image.Image;
import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
        if(userRepository.existsByEmail(user.getEmail())){
            throw new DataIntegrityViolationException("Email already exists");
        }
        else if(userRepository.existsByIdentifier(user.getIdentifier())){
            throw new DataIntegrityViolationException("Identifier already exists");
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

    public User uploadImage(User user, Image image){
        user.setImage(image);
        return userRepository.save(user);
    }

    public User increaseAvailableBorrow(User user){
        user.setAvailableBorrow(user.getAvailableBorrow() + 1);
        return userRepository.save(user);
    }

    public User decreaseAvailableBorrow(User user, Integer quantity){
        user.setAvailableBorrow(user.getAvailableBorrow() - quantity);
        return userRepository.save(user);
    }

    public User modifyUserMembership(User user, Membership membership){
        user.setMembership(membership);
        user.setAvailableBorrow(membership.getMaxBook());
        return userRepository.save(user);
    }

    public User getUserByIdentifier(String identifier){
        return userRepository.findByIdentifier(identifier);
    }

    public User getUserByEmail(String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }
}
