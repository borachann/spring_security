package com.kosign.wecafe.services;

import java.util.List;

import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.entities.UserRole;

public interface UserService {

	public User findUserById(Long id);
	
	public User findUserByUsername(String username);
	
	public User findUserByEmail(String email);
	
	public Boolean saveUser(User user);
	
	public Boolean updateUser(User user);
	
	public List<User> getAllUser(Pagination pagination);
	public List<User> searchUser(String str);
	public List<User> getAllUsers();
	
	public List<UserRole> getAllUserRoles();
	
	public Boolean updateUserStatus(Long id);
	
	public Boolean deleteUser(Long id);
	
	public Long count();
}
