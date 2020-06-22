package com.mycompany.api.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailingService  {

    public JavaMailSender emailSender;

    @Autowired
    public MailingService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendMessage (String receiver, String subject, String content) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(receiver);
        helper.setSubject(subject);
        helper.setText(content);

        emailSender.send(message);

    }
}
