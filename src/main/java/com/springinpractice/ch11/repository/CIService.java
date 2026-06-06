package com.springinpractice.ch11.repository;

import java.util.List;

import com.springinpractice.ch11.model.CI;

import io.netty.channel.unix.Errors;

public interface CIService<T extends CI<T>> {
	void create(T ci);
	
	void create(T ci, Errors erroes);
	
	List<T> findAll();
	
	T findOne (Long id);
	
	void update(T ci, Errors errors);
	
	void delete(T ci);
	
	void delete(Long id);
	

}
