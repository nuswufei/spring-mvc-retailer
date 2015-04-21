package DAOImpl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import entity.Order;
import DAO.OrderDAO;
import RowMapper.OrderRowMapper;

public class OrderDAOImpl implements OrderDAO {
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public void insert(Order order) {
		String sql = "INSERT IGNORE INTO ORDERS " +
				"(accountNumber, supplierID, productID, number) "
				+ "VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, 
		new Object[]{order.getAccountNumber(), order.getSupplierID(),
				order.getProductID(), order.getNumber()});
	}
	

	@Override
	public List<Order> findByAccountNumber(String accountNumber) {
		String sql = "SELECT * FROM ORDERS WHERE accountNumber = ?"; 
		List<Order> orders = jdbcTemplate.query(sql, new Object[]{accountNumber}, new OrderRowMapper()); 
		return orders; 
	}
	
}
