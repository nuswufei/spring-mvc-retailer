package DAOImpl;

import org.springframework.jdbc.core.JdbcTemplate;

import entity.Users;
import DAO.UsersDAO;

public class UsersDAOImpl implements UsersDAO{
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public void insert(Users users) {
		String sql = "INSERT IGNORE INTO users " +
					"(USERNAME, PASSWORD, ENABLED) "
					+ "VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, 
			new Object[]{users.getUserName(), users.getPassword(), users.getENABLED()});
	}

}
