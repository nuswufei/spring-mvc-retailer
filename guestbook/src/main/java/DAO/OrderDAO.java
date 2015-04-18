package DAO;

import java.util.List;

import entity.Order;

public interface OrderDAO {
	public void insert(Order order);
	public List<Order> findByAccountNumber(String accountNumber);
}
