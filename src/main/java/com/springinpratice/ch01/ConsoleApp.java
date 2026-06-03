package com.springinpratice.ch01;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.springinpratice.ch01.service.AccountService;
import com.winter.word.Account;

public class ConsoleApp {

	public static void main(String[] args) throws Exception {
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
		AccountService accountService = (AccountService) appCtx.getBean("accountService");
		List<Account> delinquentAccounts = accountService.findDelinquentAccounts();
		
		for (Account a : delinquentAccounts) {
			System.out.println(a.getAccountNo());
		}
	}
}