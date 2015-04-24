package DAO;

import entity.Users;

public interface UsersDAO {
	public void insert(Users users);
	public void updatePassword(String username, String password);
}
