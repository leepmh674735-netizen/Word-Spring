package com.springinpratice.ch04.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springinpractice.dao.hibernate.AbstractHbnDao;
import com.winter.word.Account;

import jakarta.inject.Inject;
import jakarta.persistence.Query;

@Repository
public class HbnAccountDao extends AbstractHbnDao<Account> 
    implements AccuntDao {
	
	private static final String UPDATE_PASSWORD_SQL =
			"update account set password = ? where username = ?";
	@Inject private JdbcTemplate jdbcTemplate;
	
	public void create (Account account, String password) {
		create(account);
		jdbcTemplate.update(
				UPDATE_PASSWORD_SQL, password, account.getUsername());
	}
	
	public Account findByUsername(String username) {
		Query q = getSession().getNamedQuery("findAccountByUsername);
		q.setParameter("username", username );
		return (Account) q.uniqueResult ();
	}

}
