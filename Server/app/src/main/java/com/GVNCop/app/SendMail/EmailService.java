package com.GVNCop.app.SendMail;

public interface EmailService {
    String sendSimpleMail(EmailDetails emailDetails);
    String sendMailWithAttachment(EmailDetails emailDetails);
    void sendActiveMail(String link,String accMail,String activeToken);
}
