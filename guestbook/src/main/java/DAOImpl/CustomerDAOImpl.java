package DAOImpl;

import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;



import entity.Customer;
import DAO.CustomerDAO;
import RowMapper.CustomerRowMapper;

public class CustomerDAOImpl implements CustomerDAO {
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Customer insert(final Customer customer) {
		final String sql = "INSERT IGNORE INTO CUSTOMER " +
				"(name, USERNAME, address) "
				+ "VALUES (?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int row = 0;
		try {
			row= this.jdbcTemplate.update(new PreparedStatementCreator(){
	            @Override
				public java.sql.PreparedStatement createPreparedStatement(
						java.sql.Connection con) throws SQLException {
	            	
	            	java.sql.PreparedStatement ps =con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	            	
	                ps.setString(1, customer.getName());
	                ps.setString(2, customer.getUsername());
	                ps.setString(3, customer.getAddress());
	                return ps;
				}
	        }, keyHolder);
		}catch(Exception e){}
		if(row > 0) return findByID(keyHolder.getKey().intValue());
		else return new Customer();
	}

	@Override
	public Customer findByID(int id) {
		String sql = "SELECT * FROM CUSTOMER WHERE id = ?";
		Customer customer = new Customer();
		try {
			customer = (Customer) jdbcTemplate.queryForObject(
					sql, new Object[] { id }, new CustomerRowMapper());
		}
		catch(Exception e){}
		return customer;
	}

	@Override
	public int findIDByUsername(String username) {
		String sql = "SELECT * FROM CUSTOMER WHERE USERNAME = ?";
		Customer customer = new Customer();
		try {
			customer = (Customer) jdbcTemplate.queryForObject(
					sql, new Object[] { username }, new CustomerRowMapper());
		}
		catch(Exception e){}
		return customer.getId();
	}
}
