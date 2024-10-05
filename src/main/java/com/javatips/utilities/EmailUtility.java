package com.javatips.utilities;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtility {

    private static final String MAIL_SENDER = "dailyjavatips@gmail.com";
    private static final String TO_ADDRESS = "ktdouble@gmail.com";
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
    public static Message createEmailWithBCC(Set<String> bccEmailAddresses,
            String fromEmailAddress,
            String subject,
            String markdownContent,
            Session session)
            throws MessagingException {

        InternetAddress[] toAddresses = getBccAddresses(bccEmailAddresses);
        String htmlContent = convertMarkDownToHtmlContent(markdownContent);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmailAddress));
        message.setRecipient(RecipientType.TO, new InternetAddress(TO_ADDRESS));
        message.setRecipients(RecipientType.BCC, toAddresses);
        message.setSubject(subject);
        // convert markdown from LLM to html.
        message.setContent(htmlContent, "text/html; charset=utf-8");

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
    public static void sendEmail(Set<String> toEmailAddresses, String bodyText, String fromEmail, String token)
            throws MessagingException, IOException, Exception {

        // Create the email content
        String subject = SUBJECT + " | " + getDateString();
        Session session = configurePropertiesAndGetSession(fromEmail, token);
        Message message = createEmailWithBCC(toEmailAddresses, MAIL_SENDER, subject, bodyText, session);
        try {
            Transport.send(message);
            System.out.println("Emails sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static String convertMarkDownToHtmlContent(String markdownContent) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdownContent);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /**
     * @return the String representation of the first three parts of
     * java.utils.Date. I.e. Wed Jun 16.
     *
     */
    private static String getDateString() {
        String dateString = new Date().toString();
        String[] tokens = dateString.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(tokens[i]).append(" ");
        }
        return sb.toString();
    }

    private static Session configurePropertiesAndGetSession(String username, String password) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // use TLS
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private static InternetAddress[] getBccAddresses(Set<String> bccEmailAddresses) throws AddressException {
        int n = bccEmailAddresses.size();
        InternetAddress[] toAddresses = new InternetAddress[n];
        int i = 0;
        for (String email : bccEmailAddresses) {
            toAddresses[i++] = new InternetAddress(email);
        }
        return toAddresses;
    }
}
