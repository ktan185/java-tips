package com.javatips.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.javatips.service.GmailService;

import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtility {

    private static final String MAIL_SENDER = "dailyjavatips@gmail.com";
    private static final String SUBJECT = "Daily Java Tip";

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param bccEmailAddresses email addresses of users subscribed to mailing
     * list.
     * @param fromEmailAddress email address of the sender, the mailbox account
     * @param subject subject of the email
     * @param markdownContent markdown content of the email.
     * @return the MimeMessage to be used to send email
     * @throws MessagingException - if a wrongly formatted address is
     * encountered.
     */
    public static MimeMessage createEmailWithBCC(Set<String> bccEmailAddresses,
            String fromEmailAddress,
            String subject,
            String markdownContent)
            throws MessagingException {

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.addRecipient(RecipientType.TO, new InternetAddress("ktdouble@gmail.com"));
        email.setFrom(new InternetAddress(fromEmailAddress));
        email.setSubject(subject);

        // convert markdown from LLM to html.
        String htmlContent = convertMarkDownToHtmlContent(markdownContent);
        email.setContent(htmlContent, "text/html; charset=utf-8");

        // Add each recipient to the BCC field (Note that this limits to 400 receipients)
        for (String bccEmail : bccEmailAddresses) {
            email.addRecipient(RecipientType.BCC, new InternetAddress(bccEmail));
        }

        return email;
    }

    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException - if service account credentials file not found.
     * @throws MessagingException - if a wrongly formatted address is
     * encountered.
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
     * Send an email from the user's mailbox to a list of subscribed users.
     *
     * @param toEmailAddresses Email addresse of subscribed users.
     * @param toEmailAddress Email address of the recipient
     * @param subject Subject of the email
     * @param bodyText Body text of the email
     * @return the sent message, {@code null} otherwise.
     * @throws MessagingException If a wrongly formatted address is encountered.
     * @throws IOException If there is an issue with the Gmail API.
     * @throws Exception If there is an issue with authentication.
     */
    public static Message sendEmail(Set<String> toEmailAddresses, String bodyText)
            throws MessagingException, IOException, Exception {

        // Get the authenticated Gmail service
        Gmail service = GmailService.getGmailService();
        // Create the email content
        String subject = SUBJECT + " | " + new Date().toString();
        MimeMessage email = createEmailWithBCC(toEmailAddresses, MAIL_SENDER, subject, bodyText);
        //  ncode and wrap the MIME message into a Gmail message
        var message = createMessageWithEmail(email);

        try {
            // Send the email
            message = service.users().messages().send(MAIL_SENDER, message).execute();
            System.out.println("Message id: " + message.getId());
            System.out.println(message.toPrettyString());
            return message;
        } catch (GoogleJsonResponseException e) {
            // Handle API errors
            System.err.println("Failed to send email: " + e.getDetails());
            throw e;
        }
    }

    private static String convertMarkDownToHtmlContent(String markdownContent) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdownContent);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
