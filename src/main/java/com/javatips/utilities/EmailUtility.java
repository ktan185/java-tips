package com.javatips.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.javatips.service.GmailService;

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
   * @param fromEmailAddress Email address to appear in the from: header
   * @param toEmailAddress   Email address of the recipient
   * @param subject          Subject of the email
   * @param bodyText         Body text of the email
   * @return the sent message, {@code null} otherwise.
   * @throws MessagingException If a wrongly formatted address is encountered.
   * @throws IOException        If there is an issue with the Gmail API.
   * @throws Exception          If there is an issue with authentication.
   */
  public static Message sendEmail(String fromEmailAddress,
                                  String toEmailAddress,
                                  String subject,
                                  String bodyText)
            throws MessagingException, IOException, Exception {

    // Get the authenticated Gmail service
    Gmail service = GmailService.getGmailService();
    // Create the email content
    MimeMessage email = createEmail(toEmailAddress, fromEmailAddress, subject, bodyText);
    // Encode and wrap the MIME message into a Gmail message
    Message message = createMessageWithEmail(email);

    try {
      // Send the email
      message = service.users().messages().send("me", message).execute();
      System.out.println("Message id: " + message.getId());
      System.out.println(message.toPrettyString());
      return message;
    } catch (GoogleJsonResponseException e) {
      // Handle API errors
      System.err.println("Failed to send email: " + e.getDetails());
      throw e;
    }
  }

  public static void main(String[] args) {
    try {
      // Replace with your sender and recipient email addresses
      String from = "ktdouble@gmail.com";
      String to = "ktdouble@gmail.com";
      String subject = "Test Email from Gmail API";
      String body = "Hello,\n\nThis is a test email sent using the Gmail API with OAuth 2.0 authentication.\n\nBest regards,\nYour Name";
      sendEmail(from, to, subject, body);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
