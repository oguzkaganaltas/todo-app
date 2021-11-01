package com.oguzkaganaltas.todoapp.service;

import com.oguzkaganaltas.todoapp.model.User;
import com.oguzkaganaltas.todoapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void createUser(User newUser){
        if (this.isValidEmailAddress(newUser.getEmail())) {
            newUser.setPassword(hashSHA512(newUser.getPassword()));
            userRepository.save(newUser);
        }
    }

    public String hashSHA512(String password){
        String encodedPass = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] byteOfText = password.getBytes(StandardCharsets.UTF_8);
            byte[] hashedByteOfText = md.digest(byteOfText);
            StringBuilder hexString = new StringBuilder();
            for(int i: hashedByteOfText){
                hexString.append(String.format("%02x", 0XFF & i));
            }

            encodedPass = hexString.toString();
            return encodedPass;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodedPass;
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("User not found"));
    }
    public User getUserBySessionId(String sessionId){
        User user = userRepository.findUserBySessionId(sessionId);
        if (user == null) {
            throw new RuntimeException("User not Found");
        }
        return user;
    }

    public void updateUser(User user){
        userRepository.save(user);
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
