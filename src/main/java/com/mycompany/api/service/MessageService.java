package com.mycompany.api.service;

import com.mycompany.api.exception.EmptyResultException;
import com.mycompany.api.exception.NullValueException;
import com.mycompany.api.models.Message;
import com.mycompany.api.models.wrappers.MagicValueWrapper;
import com.mycompany.api.models.wrappers.MessageWrapper;
import com.mycompany.api.repository.MessageRepo;

import com.sun.mail.smtp.SMTPAddressFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class MessageService {

    private MessageRepo messageRepo;
    private MailingService mailingService;

    public @Autowired
    MessageService(MessageRepo messageRepo, MailingService mailingService) {
        this.messageRepo = messageRepo;
        this.mailingService = mailingService;
    }

    public ResponseEntity<Iterable<MessageWrapper>> findMessagesByEmail(String emailValue) throws EmptyResultException, NullValueException {
        if (emailValue == null) {
            throw new NullValueException();
        }

        Iterable<Message> messages = messageRepo.findAllByEmail(emailValue);

        Iterable<MessageWrapper> response = StreamSupport.stream(messages.spliterator(), false)
                .map(message -> castMessage(message))
                .collect(Collectors.toList());

        if (response.iterator().hasNext()) {
            return ResponseEntity.ok(response);
        } else {
            throw new EmptyResultException();
        }
    }

    public ResponseEntity<MessageWrapper> create(Message message) throws NullValueException {

        if (message.getId() == null) {
            message.setId(UUID.randomUUID().toString());
        }

        if (!message.hasNullValue()) {
            return ResponseEntity.ok(castMessage(messageRepo.save(message)));
        } else {
            throw new NullValueException();
        }
    }

    public ResponseEntity<Iterable<MessageWrapper>> sendMessages(MagicValueWrapper magicValueWrapper) throws EmptyResultException, NullValueException, MessagingException {

        BigInteger magicNumber = magicValueWrapper.getMagic_number();

        if (magicNumber == null) {
            throw new NullValueException();
        }
        Iterable<Message> messages = messageRepo.findAllByMagicNumber(magicNumber);
        List<MessageWrapper> messagesSent = new ArrayList<>();
        if (messages.iterator().hasNext()) {
            for (Message msg : messages) {
                try {
                    mailingService.sendMessage(msg.getEmail(), msg.getTitle(), msg.getContent());
                    messageRepo.delete(msg);
                    messagesSent.add(castMessage(msg));
                } catch (SMTPAddressFailedException ex) {
                    System.out.println("An error occurred while sending message, invalid email address: " + msg.toString());
                    System.out.println(ex.toString());
                    messageRepo.delete(msg);
                } catch (MailSendException e) {
                    System.out.println(e.toString());
                }
            }
            if (messagesSent.size() > 0) {
                return ResponseEntity.ok(messagesSent);
            } else {
                throw new EmptyResultException();
            }

        } else {
            throw new EmptyResultException();
        }

    }

    private MessageWrapper castMessage(Message msg) {
        return new MessageWrapper(msg.getEmail(), msg.getTitle(), msg.getContent(), msg.getMagic_number());
    }
}
