package RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import entity.Account;
import entity.Customer;

public class AccountRowMapper implements RowMapper<Account>{

	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account account = new Account();
		account.setAccountNumber(rs.getString("accountNumber"));
		account.setBankName(rs.getString("bankName"));
		account.setCustomerID(rs.getInt("customerID"));
		return account;
	}
	
}
