package DAOImpl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import entity.SupplierProduct;
import DAO.SupplierProductDAO;
import RowMapper.SupplierProductRowMapper;

public class SupplierProductDAOImpl implements SupplierProductDAO{
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public List<SupplierProduct> findAll() {
		String sql = "SELECT * FROM SUPPLIERPRODUCT"; 
		List<SupplierProduct> supplierProducts = jdbcTemplate.query(sql, new SupplierProductRowMapper()); 
		return supplierProducts ;
	}
	@Override
	public List<SupplierProduct> findByID(int sid, int pid) {
		SupplierProduct supplierProduct = new SupplierProduct();
		String sql = "SELECT * FROM SUPPLIERPRODUCT WHERE supplierID = ? AND productID = ?";
		List<SupplierProduct> supplierProducts = jdbcTemplate.query(sql, new Object[]{sid, pid}, new SupplierProductRowMapper());
		return supplierProducts;
	}
	

}
