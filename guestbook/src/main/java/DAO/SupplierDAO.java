package DAO;

import entity.Supplier;

public interface SupplierDAO {
	public Supplier insert(Supplier supplier);
	public Supplier findByID(int id);
	public Supplier findByName(String name);
}
