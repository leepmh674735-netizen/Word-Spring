package com.springinpratice.ch04.dao;

import com.winter.word.Account;

public interface AccountDao {
	void create(Account account, String password);
	Account findByUsername(String username);
}
