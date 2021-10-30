package com.oguzkaganaltas.todoapp.controller;
import com.oguzkaganaltas.todoapp.model.User;
import com.oguzkaganaltas.todoapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User newUser){
        if(isAnyMatch(newUser)){
            return new ResponseEntity<>(CONFLICT);
        }
        this.userService.createUser(newUser);
        return new ResponseEntity<>(CREATED);
    }

    private boolean isAnyMatch(User newUser) {
        return userService.getAllUsers().stream().anyMatch(obj -> Objects.equals(obj.getEmail(), newUser.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user){
        List<User> users = userService.getAllUsers();
        for (User other : users) {
            if (other.equals(user)) {
                other.setStatus(true);
                userService.updateUser(other);
                return new ResponseEntity<>(OK);
            }
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @PostMapping("/logout")
    public ResponseEntity<User> logoutUser(@RequestBody User user){
        List<User> users = userService.getAllUsers();
        for (User other : users) {
            if (other.equals(user)) {
                other.setStatus(false);
                userService.updateUser(other);
                return new ResponseEntity<>(OK);
            }
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(this.userService.getAllUsers(), OK);
    }
}
