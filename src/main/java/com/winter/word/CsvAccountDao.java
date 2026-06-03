package com.winter.word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;

public class CsvAccountDao implements AccountDao {

	private Resource csvResource;

	public void setCsvResource(Resource csvFile) {
		this.csvResource = csvFile;
	}

	@Override
	public List<Account> findAll() throws Exception {
		List<Account> results = new ArrayList<Account>();
		DateFormat fmt = new SimpleDateFormat("MMddyyyy");

		try (BufferedReader br = new BufferedReader(new FileReader(csvResource.getFile()))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] fields = line.split(",");
				
				String accountNo = fields[0];
				BigDecimal balance = new BigDecimal(fields[1]);
				java.util.Date parsedDate = fmt.parse(fields[2]);
				Date lastPaidOn = new Date(parsedDate.getTime());
				
				Account account = new Account(accountNo, balance, lastPaidOn);
				results.add(account);
			}
		}
		return results;
	}
}