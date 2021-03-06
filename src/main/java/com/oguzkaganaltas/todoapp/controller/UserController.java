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
@RequestMapping("/api/v1")
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

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user){
        List<User> users = userService.getAllUsers();
        for (User other : users) {
            if (other.getPassword().equals(userService.hashSHA512(user.getPassword())) && other.getEmail().equals(user.getEmail())) {
                other.setSessionId(generateNewToken());
                userService.updateUser(other);
                return new ResponseEntity<>(other.getSessionId(),OK);
            }
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @PostMapping("/logout")
    public ResponseEntity<User> logoutUser(@RequestBody String sessionId){
        List<User> users = userService.getAllUsers();
        for (User other : users) {
            if (other.getSessionId().equals(sessionId)) {
                other.setSessionId(null);
                userService.updateUser(other);
                return new ResponseEntity<>(OK);
            }
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(this.userService.getAllUsers(), OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId){
        return new ResponseEntity<>(getResult(userId), OK);
    }

    private User getResult(int id) {
        return this.userService.getUserById(id);
    }
}
