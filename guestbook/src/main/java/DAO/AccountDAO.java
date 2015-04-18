package DAO;

import java.util.List;

import entity.Account;

public interface AccountDAO {
	public void insert(Account account);
	public List<Account> findAllByUserID(int id);
}
