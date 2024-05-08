package com.btv.app.features.user.services;
import com.btv.app.features.image.Image;
import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.user.models.Role;
import com.btv.app.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Integer PAGE_SIZE = 20;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> getReaders(int pageNumber, String searchBy, String query, String sortBy){
        String sortField;
        boolean sortAsc = true;

        switch (sortBy) {
            case "name-asc" -> sortField = "name";
            case "name-desc" -> {
                sortField = "name";
                sortAsc = false;
            }
            case "email-asc" -> sortField = "email";
            case "email-desc" -> {
                sortField = "email";
                sortAsc = false;
            }
            case "joined-date-asc" -> sortField = "joinedDate";
            case "joined-date-desc" -> {
                sortField = "joinedDate";
                sortAsc = false;
            }
            default -> sortField = "id";
        }
        Sort sort = Sort.by(sortAsc ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);

        if(searchBy.equals("") || query.equals("")) {
            return userRepository.findByRole(Role.USER, PageRequest.of(pageNumber, PAGE_SIZE, sort));
        }
        if(searchBy.equals("email")){
            return userRepository.findByRoleAndEmailContainsAllIgnoreCase(Role.USER, query, PageRequest.of(pageNumber, PAGE_SIZE, sort));
        }
        return userRepository.findByRoleAndNameContainsAllIgnoreCase(Role.USER, query, PageRequest.of(pageNumber, PAGE_SIZE, sort));
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
        if(updateUser.getAvailableBorrow() != null){
            curUser.setAvailableBorrow(updateUser.getAvailableBorrow());
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
        int curMaxBook = user.getMembership().getMaxBook();
        user.setMembership(membership);
        int curAvailableBorrow = user.getAvailableBorrow();
        user.setAvailableBorrow(membership.getMaxBook() - (curMaxBook - curAvailableBorrow));
        if(user.getExpiredDate() == null){
            user.setExpiredDate(LocalDate.now().plusYears(1));
        } else{
            user.setExpiredDate(user.getExpiredDate().plusYears(1));
        }
        return userRepository.save(user);
    }

    public User getUserByIdentifier(String identifier){
        return userRepository.findByIdentifier(identifier);
    }

    public User getUserByEmail(String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }

    public List<Integer> getUserByYear(Integer year){
        List<Integer> res = new ArrayList<>(12);
        for(int i = 0; i < 12; i++){
            res.add(0);
        }
        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            if(user.getJoinedDate().getYear() == year){
                res.set(user.getJoinedDate().getMonth().getValue() - 1, res.get(user.getJoinedDate().getMonth().getValue() - 1) + 1);
            }
        });

        return res;
    }
}
