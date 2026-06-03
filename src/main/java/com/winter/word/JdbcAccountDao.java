package com.winter.word;

import javax.sql.DataSource;

public class JdbcAccountDao implements AccountDao {

	private DataSource dataSource;

	public JdbcAccountDao() {}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}