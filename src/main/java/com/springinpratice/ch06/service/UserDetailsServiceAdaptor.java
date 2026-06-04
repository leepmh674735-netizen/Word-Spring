package com.springinpractice.ch06.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springinpractice.ch06.dao.UserDetailsDao;
import com.winter.word.Account;

import jakarta.inject.Inject;

@Service("userDetailsService")
@Transactional(readOnly = true)
public class UserDetailsServiceAdaptor implements UserDetailsService {

    @Inject
    private AccountService accountService;
    
    @Inject
    private UserDetailsDao userDetailsDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.getAccountByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        } else if (account.getRoles().isEmpty()) {
            throw new UsernameNotFoundException("User " + username + " has no authorities");
        }

        UserDetailsAdapter user = new UserDetailsAdapter(account);
        user.setPassword(userDetailsDao.findPasswordByUsername(username));
        
        return user;
    }
}