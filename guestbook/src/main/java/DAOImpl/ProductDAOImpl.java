package DAOImpl;

import org.springframework.jdbc.core.JdbcTemplate;
import entity.Product;
import DAO.ProductDAO;
import RowMapper.ProductRowMapper;

public class ProductDAOImpl implements ProductDAO{
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public Product findByID(int id) {
		String sql = "SELECT * FROM PRODUCT WHERE id = ?";
		Product product = new Product();
		try {
			product = (Product) jdbcTemplate.queryForObject(
					sql, new Object[] { id }, new ProductRowMapper());
		}
		catch(Exception e){}
		return product;
	}

}
