package duy.packages.constant;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Email {

    private static final String FROM_EMAIL = "taiKhoanEmail@gmail.com";
    private static final String PASSWORD = "xxxx xxxx xxxx xxxx";

    public static boolean sendEmail(String toEmail, String subject, String body) {

        // Gmail SMTP configuration
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Gmail authentication
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            // Create email
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(FROM_EMAIL));

            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toEmail, false)
            );

            message.setSubject(subject, "UTF-8");

            message.setContent(
                body,
                "text/html; charset=UTF-8"
            );

            // Send
            Transport.send(message);

            System.out.println("Email sent successfully to: " + toEmail);

            return true;

        } catch (Exception e) {

            System.out.println("Failed to send email to: " + toEmail);
            e.printStackTrace();

            return false;
        }
    }
}