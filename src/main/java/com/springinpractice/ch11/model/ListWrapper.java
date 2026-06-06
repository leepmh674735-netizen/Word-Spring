package com.springinpractice.ch11.model;

import java.util.List;

public interface ListWrapper<T extends CI<T>> {
	
	List<T> getList();
	
	void setList(List<T> list);

}
