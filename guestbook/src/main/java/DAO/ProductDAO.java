package DAO;

import entity.Product;

public interface ProductDAO {
	Product findByID(int id);
}
