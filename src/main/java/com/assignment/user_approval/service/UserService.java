package com.assignment.user_approval.service;

import com.assignment.user_approval.models.User;
import com.assignment.user_approval.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String password, String email) {
        if (userRepository.findByName(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setName(username);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public User authenticateUser(String username, String password) {
        Optional<User> user = userRepository.findByName(username);
        return user.filter(u -> passwordEncoder.matches(password, u.getPassword())).get();
    }

    public User getUserByEmail(String mail) {
        Optional<User> user = userRepository.findByEmail(mail);
        if(user.isPresent()){
            return user.get();
        }
        else{
            throw new RuntimeException("User not found");
        }

    }
    public List<User>findAllByEmail(List<String> approversMail) {

        List<User> userDetails = new ArrayList<>();
        for(String mail: approversMail){
            userDetails.add(userRepository.findByEmail(mail).get());
        }
        return userDetails;
    }
}
