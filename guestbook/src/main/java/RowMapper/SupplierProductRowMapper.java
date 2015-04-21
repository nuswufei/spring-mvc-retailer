package RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import entity.SupplierProduct;

public class SupplierProductRowMapper implements RowMapper<SupplierProduct>{

	@Override
	public SupplierProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
		SupplierProduct supplierProduct = new SupplierProduct();
		supplierProduct.setProductID(rs.getInt("productID"));
		supplierProduct.setSupplierID(rs.getInt("supplierID"));
		supplierProduct.setDiscount(rs.getString("discount"));
		return supplierProduct;
	}

}
