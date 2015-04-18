package DAO;

import entity.Customer;

public interface CustomerDAO {
	Customer insert(Customer customer);
	Customer findByID(int id);
	int findIDByUsername(String username);
}
