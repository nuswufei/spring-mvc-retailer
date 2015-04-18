package DAOImpl;

import org.springframework.jdbc.core.JdbcTemplate;

import entity.Customer;
import DAO.CustomerDAO;

public class CustomerDAOImpl implements CustomerDAO {
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Customer insert(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer findByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findIDByUsername(String username) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
