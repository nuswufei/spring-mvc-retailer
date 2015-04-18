package RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import entity.Order;

public class OrderRowMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		Order order = new Order();
		order.setId(rs.getInt("id"));;
		order.setAccountNumber(rs.getString("accountNumber"));
		order.setNumber(rs.getInt("number"));
		order.setSupplierID(rs.getInt("supplierID"));
		order.setProductID(rs.getInt("productID"));
		return order;
	}

}
