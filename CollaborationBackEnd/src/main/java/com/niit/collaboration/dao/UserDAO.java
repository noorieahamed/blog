package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.User;

public interface UserDAO {
	
	
	//registration
	
		public boolean save(User user);
		
		//update user details
		public boolean update(User user);
		
		public boolean delete(String emaild);
		
		//see/fetch/get the details
		
		public User   get(String emailId);
		
		//admin may fetch all the user details
		
		public  List<User>     list();
		
		//login - validation
		
		public User validate(String emailID,  String password);
	
}
