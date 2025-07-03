package com.example.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import java.util.List;


@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    
    @Autowired
    private MessageService messageService;

   
    @PostMapping("/register")
    public ResponseEntity<Account> handleUserRegistration(@RequestBody Account account) {
        Account registeredAccount = accountService.registerAccount(account);
        if (registeredAccount != null) {
            return ResponseEntity.ok(registeredAccount);
        } else {
            Account existingAccount = accountService.findAccountByUsername(account.getUsername());
            if (existingAccount != null) {
                return ResponseEntity.status(409).build();
            } else {
                return ResponseEntity.status(400).build();
            }
        }
    }

   
    @PostMapping("/login")
    public ResponseEntity<Account> handleUserLogin(@RequestBody Account account) {
        Account authenticatedAccount = accountService.authenticateUser(account.getUsername(), account.getPassword());
        if (authenticatedAccount != null) {
            return ResponseEntity.ok(authenticatedAccount);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

   
    @PostMapping("/messages")
    public ResponseEntity<Message> handleMessageCreation(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage != null) {
            return ResponseEntity.ok(createdMessage);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

   
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> handleGetAllMessages() {
        List<Message> allMessages = messageService.retrieveAllMessages();
        return ResponseEntity.ok(allMessages);
    }

    
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> handleGetMessagesByUser(@PathVariable("accountId") Integer accountId) {
        List<Message> userMessages = messageService.retrieveMessagesByUser(accountId);
        return ResponseEntity.ok(userMessages);
    }

    
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> handleGetMessageById(@PathVariable("messageId") Integer messageId) {
        Message foundMessage = messageService.retrieveMessageById(messageId);
        if (foundMessage != null) {
            return ResponseEntity.ok(foundMessage);
        } else {
            return ResponseEntity.ok().build();
        }
    }

    
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> handleMessageUpdate(@PathVariable("messageId") Integer messageId, @RequestBody Message messageUpdate) {
        int updatedRows = messageService.updateMessageText(messageId, messageUpdate);
        if (updatedRows > 0) {
            return ResponseEntity.ok(updatedRows);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> handleMessageDeletion(@PathVariable("messageId") Integer messageId) {
        int deletedRows = messageService.removeMessage(messageId);
        if (deletedRows > 0) {
            return ResponseEntity.ok(deletedRows);
        } else {
            return ResponseEntity.ok().build();
        }
    }
}