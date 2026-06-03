package com.springinpratice.ch01.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.winter.word.Account;

public class AccountService {
	private AccountDao accountDao;
	
	public AccountService() {}
	
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	public List<Account> findDelinquentAccounts() throws Exception {
		List<Account> delinquentAccounts = new ArrayList<Account>();
		List<Account> accounts = accountDao.findAll();
		
		Date thirtyDaysAgo = daysAgo(30);
		
		for (Account account : accounts) {
			boolean owesMoney = account.getLastPaidOn().compareTo(thirtyDaysAgo) <= 0;
			if (owesMoney) {
				delinquentAccounts.add(account);
			}
		} 
		return delinquentAccounts;
	}
	
	private static Date daysAgo(int days) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.add(Calendar.DATE, -days);
		return new Date(gc.getTimeInMillis());
	}
}