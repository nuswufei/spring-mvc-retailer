package DAO;

import java.util.List;

import entity.Product;

public interface ProductDAO {
	public List<Product> findAll();
	public Product post(Product product);
	public Product deleteByID(int id);
	public Product findByID(int id);
}
