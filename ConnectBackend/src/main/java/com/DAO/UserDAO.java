package com.DAO;

import java.util.List;

import com.Model.User;

public interface UserDAO {

	public List<User>getAllUsers();

	public void registerUser(User user);
	
	User login(User user);
	
	void updateUser(User user);
	
	boolean isEmailValid(String email);
	
	boolean isUsernameValid(String username);
	
	public void deleteUser(int id);

	public User getUserByUsername(String username);
}
