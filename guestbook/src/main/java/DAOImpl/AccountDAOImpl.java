package DAOImpl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import entity.Account;
import DAO.AccountDAO;
import RowMapper.AccountRowMapper;

public class AccountDAOImpl implements AccountDAO{
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public void insert(Account account) {
		String sql = "INSERT IGNORE INTO ACCOUNT " +
				"(accountNumber, bankName, customerID) "
				+ "VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, 
		new Object[]{account.getAccountNumber(), account.getBankName(), account.getCustomerID()});
	}
	
	@Override
	public List<Account> findAllByUserID(int id) {
		String sql = "SELECT * FROM ACCOUNT WHERE customerID = ?"; 
		List<Account> accounts = jdbcTemplate.query(sql, new Object[]{id}, new AccountRowMapper()); 
		return accounts; 
	}

}
