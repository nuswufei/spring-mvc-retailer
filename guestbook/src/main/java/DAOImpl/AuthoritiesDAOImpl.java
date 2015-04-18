package DAOImpl;

import org.springframework.jdbc.core.JdbcTemplate;

import entity.Authorities;
import entity.Users;
import DAO.AuthoritiesDAO;

public class AuthoritiesDAOImpl implements AuthoritiesDAO{

	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public void insert(Authorities authorities) {
		String sql = "INSERT IGNORE INTO authorites " +
					"(USERNAME, AUTHORITY) "
					+ "VALUES (?, ?)";
		jdbcTemplate.update(sql, 
			new Object[]{authorities.getUsername(), authorities.getAuthority()});
	}

}
