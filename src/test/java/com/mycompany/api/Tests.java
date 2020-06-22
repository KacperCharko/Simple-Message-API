package com.mycompany.api;

import com.mycompany.api.exception.EmptyResultException;
import com.mycompany.api.exception.NullValueException;
import com.mycompany.api.models.Message;
import com.mycompany.api.models.wrappers.MessageWrapper;
import com.mycompany.api.repository.MessageRepo;
import com.mycompany.api.service.MailingService;
import com.mycompany.api.service.MessageService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Tests {
    @Mock
    MessageRepo messageRepo;

    @Mock
    MailingService mailingService;

    @InjectMocks
    MessageService messageService;

    @Test
    public void testFindMessagesByEmail() throws EmptyResultException, NullValueException {
        List<Message> messageList = getSampleMessagesWithSameEmail("anna.zajkowska@example.com");

        when(messageRepo.findAllByEmail("anna.zajkowska@example.com")).thenReturn(messageList);

        ResponseEntity<Iterable<MessageWrapper>> messages = messageService.findMessagesByEmail("anna.zajkowska@example.com");

        Iterable<MessageWrapper> messageWrappers = StreamSupport.stream(messageList.spliterator(), false)
                .map(message -> castMessage(message))
                .collect(Collectors.toList());

        Assert.assertEquals(messages.getBody(), messageWrappers);
        Assert.assertEquals(messages.getStatusCode().value(), 200);

    }
    @Test(expected = EmptyResultException.class)
    public void testFindMessagesByEmailThrowsExc() throws EmptyResultException, NullValueException {
        List<Message> messageList = getSampleMessagesWithSameEmail("anna.zajkowska@example.com");

        when(messageRepo.findAllByEmail("anna.zajkows1ka@example.com")).thenReturn(messageList);

        ResponseEntity<Iterable<MessageWrapper>> messages = messageService.findMessagesByEmail("anna.zajkowska@example.com");
    }

    @Test
    public void testCreateNewMessage() throws NullValueException {
        Message example = new Message("anna.zaj@example.com","hej hej", "witaj", new BigInteger("101"));
        example.setId(UUID.randomUUID().toString());

        when(messageRepo.save(example)).thenReturn(example);

        ResponseEntity<MessageWrapper> msg = messageService.create(example);
        Assert.assertEquals(msg.getStatusCode().value(),200);
       Assert.assertEquals(msg.getBody(), castMessage(example));
    }
    @Test(expected = NullValueException.class)
    public void testCreateNewMessageThrowsExc() throws NullValueException {
        Message example = new Message("anna.zaj@example.com","", "witaj", new BigInteger("101"));
        example.setId(UUID.randomUUID().toString());

        when(messageRepo.save(example)).thenReturn(example);

        ResponseEntity<MessageWrapper> msg = messageService.create(example);
        Assert.assertEquals(msg.getStatusCode().value(),200);
        Assert.assertEquals(msg.getBody(), castMessage(example));
    }

    public List<Message> getSampleMessagesWithSameEmail(String email) {
        List<Message> result = new ArrayList<>();
        Message msg1 = new Message(email, "Tytul", "Czesc", new BigInteger("101"));
        Message msg2 = new Message(email, "Tytul1", "Czesc1", new BigInteger("1021"));
        result.add(msg1);
        result.add(msg2);
        return result;
    }

    public MessageWrapper castMessage(Message msg) {
        return new MessageWrapper(msg.getEmail(), msg.getTitle(), msg.getContent(), msg.getMagic_number());
    }

}
