package com.springinpratice.ch06.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.inject.Inject;

@Repository
public class JdbcUserDetailsDao implements UserDetailsDao {
	@Inject private JdbcTemplate jdbcTemplate;
	
	private static final String FIND_PASSWORD_SQL =
			"select password from account where username = ?";
	
	@Override
	public String findPasswordByUsername(String username) {
		return jdbcTemplate.queryForObject(
				FIND_PASSWORD_SQL, new Object [] { username }, String.class);
				
	}
}