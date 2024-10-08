package com.javatips.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javatips.exceptions.JavaTipsInvalidModelException;
import com.javatips.model.User;
import com.javatips.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            // Return bad request if email has already been used.
            return new ResponseEntity<>(
                    "The email has already been signed up!",
                    HttpStatus.CONFLICT);
        } catch (JavaTipsInvalidModelException e) {
            return new ResponseEntity<>(
                    "One of the field values was missing.",
                    HttpStatus.BAD_REQUEST);
        }
    }

}
