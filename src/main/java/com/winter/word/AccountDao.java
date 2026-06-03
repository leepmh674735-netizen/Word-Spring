package com.winter.word;

import java.util.List;

public interface AccountDao {
	List<Account> findAll() throws Exception;

}
