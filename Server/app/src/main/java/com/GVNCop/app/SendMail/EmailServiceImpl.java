package com.GVNCop.app.SendMail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;

    public String sendSimpleMail(EmailDetails emailDetails) {
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMsgBody());
            mailMessage.setSubject(emailDetails.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            throw e;
        }
    }

    public String sendMailWithAttachment(EmailDetails emailDetails) {
        return null;
    }


    //active link
    public void sendActiveMail(String link,String accMail,String activeToken){
        link = "http://localhost:3000/register/"+activeToken;
        String htmlBody = "<html><body>\n" +
                "<div style=\"text-align: center; background-color: rgb(212, 212, 212); border-radius: 5px; width: 900px; padding-bottom: 10px; overflow: hidden;\">\n" +
                "    <img src=\"https://wallpapercave.com/wp/wp7985922.jpg\" height=\"100px\" alt=\"Company Image\" style=\"width: 100%; height: 200px; object-fit: cover; margin-bottom: 20px;\">\n" +
                "    <div style=\"font-size: 24px; font-weight: bold; margin-top: 20px; background-color: black; color: white; display: inline-block; padding: 10px 30px;\">GVN CORPORATION</div>\n" +
                "    <p style=\"font-size: 18px; margin-top: 10px;\">Verify your email address to complete creating your GVN account</p>\n" +
                "    <a href=\"" + link + "\" target=\"_blank\" style=\"display: inline-block; padding: 10px 20px; margin-top: 20px; background-color: #752e66; color: white; text-decoration: none; border-radius: 5px; cursor: pointer;\">Verify your email</a>\n" +
                "</div>\n" +
                "</body></html>";
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");

            helper.setFrom(sender);
            helper.setTo(accMail);
            helper.setText(htmlBody,true);
            helper.setSubject("From GVNCORPORATION TO ACTIVE YOUR ACCOUNT");
            javaMailSender.send(mimeMessage);
            System.out.println("send email active success..");
        }
        catch (MessagingException e){
            e.printStackTrace();
        }
    }
}
