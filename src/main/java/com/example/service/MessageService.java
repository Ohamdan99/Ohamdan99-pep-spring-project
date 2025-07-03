package com.example.service;


import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;



@Service
public class MessageService {


    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    
    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty()) {
            return null;
        }
        
        if (message.getMessageText().length() > 255) {
            return null;
        }
        
        if (message.getPostedBy() == null) {
            return null;
        }
        
        Optional<Account> userAccount = accountRepository.findById(message.getPostedBy());
        if (!userAccount.isPresent()) {
            return null;
        }
        
        return messageRepository.save(message);
    }

    
    public List<Message> retrieveAllMessages() {
        return messageRepository.findAll();
    }

    
    public Message retrieveMessageById(Integer messageId) {
        Optional<Message> foundMessage = messageRepository.findById(messageId);
        return foundMessage.orElse(null);
    }

    
    public List<Message> retrieveMessagesByUser(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

   
    public int updateMessageText(Integer messageId, Message messageUpdate) {
        if (messageUpdate.getMessageText() == null || messageUpdate.getMessageText().trim().isEmpty()) {
            return 0;
        }
        
        if (messageUpdate.getMessageText().length() > 255) {
            return 0;
        }
        
        Optional<Message> existingMessageWrapper = messageRepository.findById(messageId);
        if (!existingMessageWrapper.isPresent()) {
            return 0;
        }
        
        Message existingMessage = existingMessageWrapper.get();
        existingMessage.setMessageText(messageUpdate.getMessageText());
        messageRepository.save(existingMessage);
        
        return 1;
    }

   
    public int removeMessage(Integer messageId) {
        Optional<Message> messageToDelete = messageRepository.findById(messageId);
        if (messageToDelete.isPresent()) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }
}