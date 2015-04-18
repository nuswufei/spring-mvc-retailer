package DAO;

import java.util.List;

import RowMapper.AccountRowMapper;
import entity.Account;
import entity.SupplierProduct;

public interface SupplierProductDAO {
	List<SupplierProduct> findAll();
}
