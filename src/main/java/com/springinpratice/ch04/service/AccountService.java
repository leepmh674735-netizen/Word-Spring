package com.springinpratice.ch04.service;

import com.winter.word.Account;
import org.springframework.validation.Errors;

public interface AccountService {
	boolean registerAccount(Account account, String password, Errors errors);
}
