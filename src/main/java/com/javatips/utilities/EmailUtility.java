package com.javatips.utilities;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;

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

  /**
   * Send an email from the user's mailbox to its recipient.
   *
   * @param fromEmailAddress - Email address to appear in the from: header
   * @param toEmailAddress   - Email address of the recipient
   * @return the sent message, {@code null} otherwise.
   * @throws MessagingException - if a wrongly formatted address is encountered.
   * @throws IOException        - if service account credentials file not found.
   */
  public static Message sendEmail(String fromEmailAddress,
                                  String toEmailAddress)
      throws MessagingException, IOException {

   
    GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
    credentials.refreshIfExpired();
    AccessToken token = credentials.getAccessToken();

  // Create the gmail API client
  Gmail service = new Gmail.Builder(new NetHttpTransport(),
      GsonFactory.getDefaultInstance(), null)
      .setApplicationName("Daily Java Tips")
      .build();

    // Create the email content
    String messageSubject = "Test message";
    String bodyText = "lorem ipsum.";

    // Encode as MIME message
    MimeMessage email = createEmail(toEmailAddress, fromEmailAddress, messageSubject, bodyText);
    // Encode and wrap the MIME message into a gmail message
    Message message = createMessageWithEmail(email);
    
    try {
      // Create send message
      message = service.users().messages().send("me", message).execute();
      System.out.println("Message id: " + message.getId());
      System.out.println(message.toPrettyString());
      return message;
    } catch (GoogleJsonResponseException e) {
      GoogleJsonError error = e.getDetails();
      if (error.getCode() == 403) {
        System.err.println("Unable to send message: " + e.getDetails());
      } else {
        throw e;
      }
    }
    return null;
  }
}

