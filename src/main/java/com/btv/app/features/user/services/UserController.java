package com.btv.app.features.user.services;

import com.btv.app.cloudinary.CloudinaryService;
import com.btv.app.exception.MyException;
import com.btv.app.features.authentication.services.AuthenticationService;
import com.btv.app.features.image.Image;
import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.membership.services.MembershipService;
import com.btv.app.features.transaction.services.TransactionController;
import com.btv.app.features.user.models.Role;
import com.btv.app.features.user.models.User;
import com.sun.tools.jconsole.JConsoleContext;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final MembershipService membershipService;
    private final AuthenticationService auth;

    @AllArgsConstructor
    public static class UserListResponse {
        public List<User> users;
        public int pageNumber;
        public int totalPages;
        public long totalItems;
    }

    @GetMapping("/admin/all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> res = userService.getAllUsers();
        if(auth.getCurrentUser().getStatus()){
            throw new MyException(HttpStatus.UNAUTHORIZED, "You are not allowed to access this resource");
        }
        if(res == null)
            throw new MyException(HttpStatus.NOT_FOUND, "No user found");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/admin/all-readers-amount")
    public ResponseEntity<Long> getAllReadersAmount(){
        if(auth.getCurrentUser().getStatus()){
            throw new MyException(HttpStatus.UNAUTHORIZED, "You are not allowed to access this resource");
        }
        if(auth.getCurrentUser().getStatus()){
            throw new MyException(HttpStatus.UNAUTHORIZED, "You are not allowed to access this resource");
        }
        long res = userService.getNumberOfReaders();
        return ResponseEntity.ok(res);
    }

    @GetMapping("librarian/readers")
    public ResponseEntity<UserListResponse> getReaders(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "search-by", required = false, defaultValue = "") String searchBy,
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "sort-by", required = false, defaultValue = "") String sortBy
    ){
        if(auth.getCurrentUser().getStatus()){
            throw new MyException(HttpStatus.UNAUTHORIZED, "You are not allowed to access this resource");
        }
        Page<User> res = userService.getReaders(pageNumber, searchBy, query, sortBy);
        return ResponseEntity.ok(new UserController.UserListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
    }

    @GetMapping("admin/accounts")
    public ResponseEntity<UserListResponse> getAccounts(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "search-by", required = false, defaultValue = "") String searchBy,
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "sort-by", required = false, defaultValue = "") String sortBy,
            @RequestParam(value = "role", required = false, defaultValue = "all") String role
    ){
        Page<User> res = userService.getAccounts(pageNumber, searchBy, query, sortBy, role);
        return ResponseEntity.ok(new UserController.UserListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable("id") Long id){
        User res = userService.getUserByID(id);
        if(res == null)
            throw new MyException(HttpStatus.NOT_FOUND, "User not found");
        return ResponseEntity.ok(res);
    }

    @PostMapping("/admin/add-user")
    public ResponseEntity<User> addUser(@RequestBody User user){
        if(userService.getUserByEmail(user.getEmail()) != null)
            throw new MyException(HttpStatus.CONFLICT, "This email is being used ");
        else if(userService.getUserByIdentifier(user.getIdentifier()) != null)
            throw new MyException(HttpStatus.CONFLICT, "This identifier is being used ");
        Role role = user.getRole();
        User res = userService.addUser(user, role);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/modify-user/{id}")
    public ResponseEntity<User> modifyUser(@PathVariable("id") Long id, @RequestBody User user){
        User curUser = userService.getUserByID(id);
        if(curUser == null){
            throw new MyException(HttpStatus.NOT_FOUND, "User not found");
        }
        User res = userService.modifyUser(curUser, user);
        return ResponseEntity.ok(res);
    }

    @PutMapping("admin/modify-user-status/{id}")
    public ResponseEntity<User> modifyUser(@PathVariable("id") Long id, @RequestParam("status") Boolean status){
        User user = userService.getUserByID(id);
        if(user == null){
            throw new MyException(HttpStatus.NOT_FOUND, "User not found");
        }
        User curUser = auth.getCurrentUser();
        if(Objects.equals(curUser.getId(), id)){
            throw new MyException(HttpStatus.CONFLICT, "You can't lock yourself");
        }

        User res = userService.modifyUserStatus(user, status);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/admin/delete-user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id){
        User curUser = userService.getUserByID(id);
        if(curUser == null){
            throw new MyException(HttpStatus.NOT_FOUND, "User not found");
        }
        userService.deleteUser(id);
        return ResponseEntity.ok(curUser);
    }

    @PutMapping("/add-user-image/{id}")
    public ResponseEntity<User> uploadUserImage(@PathVariable("id") Long id, @RequestParam("image") MultipartFile file){
        User curUser = userService.getUserByID(id);
        if(curUser == null){
            throw new MyException(HttpStatus.NOT_FOUND, "User not found");
        }
        Map data = cloudinaryService.upload(file);
        if(data == null){
            throw new MyException(HttpStatus.INTERNAL_SERVER_ERROR, "Upload image failed");
        }
        Image image = new Image(data.get("public_id").toString(), data.get("secure_url").toString());
        User res = userService.uploadImage(curUser, image);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/user/modify-membership/{membershipId}")
    public ResponseEntity<User> modifyMembership(@PathVariable("membershipId") Long membershipId){
        User curUser = auth.getCurrentUser();
        if(curUser == null){
            throw new MyException(HttpStatus.NOT_FOUND, "User not found");
        }
        Membership membership = membershipService.getMembershipByID(membershipId);
        if(membership == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Membership not found");
        }
        User res = userService.modifyUserMembership(curUser, membership);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/admin/registration-by-year")
    public ResponseEntity<List<Integer>> getUserAmountByYear(@RequestParam  ("year") Integer year){
        List<Integer> res = userService.getUserByYear(year);
        return ResponseEntity.ok(res);
    }

    @GetMapping("reader")
    public ResponseEntity<User> getReaderByEmail(
            @RequestParam(value = "email", required = true) String email
    ){
        User res = userService.getUserByEmail(email);
        if(res == null || !res.getRole().equals(Role.USER))
            throw new MyException(HttpStatus.NOT_FOUND, "User not found");
        return ResponseEntity.ok(res);
    }
}
