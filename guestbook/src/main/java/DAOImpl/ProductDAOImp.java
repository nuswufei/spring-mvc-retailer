package DAOImpl;

import java.util.*;

import entity.Product;
import DAO.ProductDAO;

public class ProductDAOImp implements ProductDAO{
	private Map<Integer, Product> prod;
	public ProductDAOImp() {
		prod = new HashMap<Integer, Product>();
		Product p = new Product();
		p.setId(132);
		p.setName("test");
		p.setSupplierID(11);
		p.setDescription("hello");
		prod.put(132, p);
	}
	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Product post(Product product) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Product deleteByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Product findByID(int id) {
		return prod.get(id);
	}
}
