package com.mycompany.api.api;


import com.mycompany.api.exception.EmptyResultException;
import com.mycompany.api.exception.NullValueException;
import com.mycompany.api.models.Message;
import com.mycompany.api.models.wrappers.MagicValueWrapper;
import com.mycompany.api.models.wrappers.MessageWrapper;
import com.mycompany.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;


@RestController
@RequestMapping("/api")
public class MessageApi {

    private MessageService messageService;

    @Autowired
    public MessageApi(MessageService messageService) {
        this.messageService = messageService;

    }

    @PostMapping("/message")
    public ResponseEntity<MessageWrapper> createMessage(@RequestBody Message message) throws NullValueException {
        return messageService.create(message);
    }

    @PostMapping("/send")
    public ResponseEntity<Iterable<MessageWrapper>> sendMessages(@RequestBody MagicValueWrapper magicValueWrapper) throws EmptyResultException, NullValueException, MessagingException {
        return messageService.sendMessages(magicValueWrapper);
    }

    @GetMapping("/messages/{emailValue}")
    public ResponseEntity<Iterable<MessageWrapper>> getMessages(@PathVariable String emailValue) throws EmptyResultException, NullValueException {
        return messageService.findMessagesByEmail(emailValue);
    }

}
