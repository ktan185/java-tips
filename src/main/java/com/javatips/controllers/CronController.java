package com.javatips.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javatips.service.OpenAIService;
import com.javatips.service.UserService;
import com.javatips.utilities.EmailUtility;

@RestController
@RequestMapping("tips")
public class CronController {

    @Value("${gcp.auth.token}")
    String MASTERKEY;

    @Autowired
    OpenAIService openAIService;

    @Autowired
    UserService userService;

    /**
     * This endpoint handles the creation and sending of Java tips to subscribed
     * user's emails. It should be called daily at 0800H NZT via Google Cloud
     * Scheduler. Only the schedular should be allowed to invoke this endpoint.
     */
    @PostMapping
    public ResponseEntity<?> getDailyJavaTip(@CookieValue(value = "authToken", required = true) String authToken) {

        if (!authToken.equals(MASTERKEY)) {
            System.out.println(MASTERKEY);
            return ResponseEntity.status(401).build();
        }

        try {
            Set<String> mailingList = userService.getAllUserEmails();
            String dailyJavaTip = openAIService.requestForJavaTip();
            EmailUtility.sendEmail(mailingList, dailyJavaTip);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
