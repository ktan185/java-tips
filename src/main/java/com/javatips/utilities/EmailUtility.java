package com.javatips.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

import com.google.api.services.gmail.model.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


public class EmailUtility {

  /**
   * Create a MimeMessage using the parameters provided.
   *
   * @param toEmailAddress   email address of the receiver
   * @param fromEmailAddress email address of the sender, the mailbox account
   * @param subject          subject of the email
   * @param bodyText         body text of the email
   * @return the MimeMessage to be used to send email
   * @throws MessagingException - if a wrongly formatted address is encountered.
   */
  public static MimeMessage createEmail(String toEmailAddress,
                                        String fromEmailAddress,
                                        String subject,
                                        String bodyText)
      throws MessagingException {
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);

    MimeMessage email = new MimeMessage(session);

    email.setFrom(new InternetAddress(fromEmailAddress));
    email.addRecipient(jakarta.mail.Message.RecipientType.TO,
        new InternetAddress(toEmailAddress));
    email.setSubject(subject);
    email.setText(bodyText);
    return email;
  }

  /**
   * Create a message from an email.
   *
   * @param emailContent Email to be set to raw of message
   * @return a message containing a base64url encoded email
   * @throws IOException        - if service account credentials file not found.
   * @throws MessagingException - if a wrongly formatted address is encountered.
   */
  public static Message createMessageWithEmail(MimeMessage emailContent)
      throws MessagingException, IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    emailContent.writeTo(buffer);
    byte[] bytes = buffer.toByteArray();
    String encodedEmail = Base64.getUrlEncoder().encodeToString(bytes);
    Message message = new Message();
    message.setRaw(encodedEmail);
    return message;
  }
}

