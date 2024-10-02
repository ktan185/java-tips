package com.javatips.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javatips.model.User;
import com.javatips.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void saveUser(User user) throws IllegalStateException {
        Set<String> existingEmails = getAllUserEmails();
        String userEmail = user.getEmail();
        if (existingEmails.contains(userEmail)) {
            throw new IllegalStateException("This email has aready been taken!");
        }
        userRepository.save(user);
    }

    public Set<String> getAllUserEmails() {
        return userRepository.findAll().stream().map(User::getEmail).collect(Collectors.toSet());
    }

}
