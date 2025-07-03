package com.example.service;


import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

   
    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            return null;
        }
        
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }
        
        Account duplicateAccount = accountRepository.findByUsername(account.getUsername());
        if (duplicateAccount != null) {
            return null;
        }
        
        return accountRepository.save(account);
    }

  
    public Account authenticateUser(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password);
    }

   
    public Account findAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
}