package RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import entity.Customer;
import entity.Supplier;

public class SupplierRowMapper implements RowMapper<Supplier>{

	@Override
	public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
		Supplier supplier = new Supplier();
		supplier.setId(rs.getInt("id"));
		supplier.setName(rs.getString("name"));
		supplier.setAddress(rs.getString("address"));
		return supplier;
	}

}
