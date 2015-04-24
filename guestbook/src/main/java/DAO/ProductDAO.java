package DAO;

import entity.Product;

public interface ProductDAO {
	Product findByID(int id);
	Product findByName(String name);
	void insert(Product product);
}
