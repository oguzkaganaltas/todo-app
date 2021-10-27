package com.oguzkaganaltas.todoapp.service;

import com.oguzkaganaltas.todoapp.model.Task;
import com.oguzkaganaltas.todoapp.model.User;
import com.oguzkaganaltas.todoapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User createUser(User newUser){
        return userRepository.save(newUser);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("User not found"));
    }
}
