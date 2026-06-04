package com.springinpratice.ch06.dao;

public interface UserDetailsDao {
	String findPasswordByUsername(String username);
}
