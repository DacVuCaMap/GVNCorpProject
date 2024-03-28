package com.GVNCop.app.SendMail;

public interface EmailService {
    String sendSimpleMail(EmailDetails emailDetails);
    String sendMailWithAttachment(EmailDetails emailDetails);
    String sendActiveMail(String link,String accMail);
}
