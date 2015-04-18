package DAOImpl;

import org.springframework.jdbc.core.JdbcTemplate;

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
	
}
