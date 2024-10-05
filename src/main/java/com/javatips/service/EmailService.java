package com.javatips.service;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.javatips.utilities.EmailUtility;

import jakarta.mail.MessagingException;

@Service
public class EmailService {

    @Value("${gmail.user}")
    String SENDEREMAIL;

    @Value("${gmail.token}")
    String SENDERTOKEN;

    public void sendMail(Set<String> mailingList, String dailyJavaTip)
            throws MessagingException, IOException, Exception {
        EmailUtility.sendEmail(mailingList, dailyJavaTip, SENDEREMAIL, SENDERTOKEN);
    }
}
