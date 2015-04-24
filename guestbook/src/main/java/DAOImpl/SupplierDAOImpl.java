package DAOImpl;

import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import entity.Customer;
import entity.Supplier;
import DAO.SupplierDAO;
import RowMapper.CustomerRowMapper;
import RowMapper.SupplierRowMapper;

public class SupplierDAOImpl implements SupplierDAO {
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public Supplier findByID(int id) {
		String sql = "SELECT * FROM SUPPLIER WHERE id = ?";
		Supplier supplier = new Supplier();
		try {
			supplier = (Supplier) jdbcTemplate.queryForObject(
					sql, new Object[] { id }, new SupplierRowMapper());
		}
		catch(Exception e){}
		return supplier;
	}
	@Override
	public Supplier insert(final Supplier supplier) {
		final String sql = "INSERT IGNORE INTO SUPPLIER " +
				"(name, address) "
				+ "VALUES (?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int row = 0;
		try {
			row= this.jdbcTemplate.update(new PreparedStatementCreator(){
	            @Override
				public java.sql.PreparedStatement createPreparedStatement(
						java.sql.Connection con) throws SQLException {
	            	
	            	java.sql.PreparedStatement ps =con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	            	
	                ps.setString(1, supplier.getName());
	                ps.setString(2, supplier.getAddress());
	                return ps;
				}
	        }, keyHolder);
		}catch(Exception e){}
		if(row > 0) return findByID(keyHolder.getKey().intValue());
		else return new Supplier();
	}
	@Override
	public Supplier findByName(String name) {
		String sql = "SELECT * FROM SUPPLIER WHERE name = ?";
		Supplier supplier = new Supplier();
		try {
			supplier = (Supplier) jdbcTemplate.queryForObject(
					sql, new Object[] { name }, new SupplierRowMapper());
		}
		catch(Exception e){}
		return supplier;
	}
	
}
