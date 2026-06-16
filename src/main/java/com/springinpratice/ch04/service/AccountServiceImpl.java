package com.springinpratice.ch04.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import com.winter.word.Account;
import com.springinpratice.ch04.dao.AccountDao;

import jakarta.inject.Inject;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

	@Inject 
	private AccountDao accountDao;
	
	@Transactional
	public boolean registerAccount(Account account, String password, Errors errors) {
		validateUsername(account.getUsername(), errors);
		boolean valid = !errors.hasErrors();
		if (valid) { 
			accountDao.create(account, password); 
		}
		return valid;
	}

	private void validateUsername(String username, Errors errors) {
		if (accountDao.findByUsername(username) != null) {
			errors.rejectValue("username", "error.duplicate", new String[] { username }, null);
		}
	}
}