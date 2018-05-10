package com.niit.collaboration.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.dao.UserDAO;
import com.niit.collaboration.model.JobApplication;
import com.niit.collaboration.model.User;

@RestController
public class UserController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private User user;
	
	@Autowired
	private JobApplication jobApplication;
	
	// http://localhost:8081/CollaborationRestService/
	
	@GetMapping("/")
	public String serverTest()
	{
		return "The server has started successful";
	}
	
	//http://localhost:8081/CollaborationRestService/user/list
	
	@GetMapping("user/list")
	public ResponseEntity< List<User>> getAllUsers()
	{
		List<User> users =  userDAO.list();
		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	//// http://localhost:8081/CollaborationRestService/user/get/jaya@gmail.com
	/**
	 * This method will return user object based on emailID
	 * if emailID exist, will return user object
	 * else empty
	 * @param emailID
	 * @return
	 */
	@GetMapping("user/get/{emailID}")
	public ResponseEntity<User>     getUser(@PathVariable String emailID)
	{
	user=	userDAO.get(emailID);
	if(user==null)
	{
		return new ResponseEntity<User>(user,HttpStatus.NOT_FOUND);
	}
	else
	{
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	}
	
	
	@PostMapping("/user/validate")
	public   ResponseEntity<User>      validateCredentials(@RequestBody User user) 
	{
	  user=	userDAO.validate(user.getEmailID(), user.getPassword());
	  
	  if(user==null)
		{
			return new ResponseEntity<User>(user,HttpStatus.UNAUTHORIZED);
		}
		else
		{
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
	  
	}
	
	
	//create new record
	@PostMapping("/user/create")
	public ResponseEntity<User>  createUser(@RequestBody User user)
	{
	
	  if(userDAO.save(user))
	  {
		  return new ResponseEntity<User>(user,HttpStatus.OK);
	  }
	  else
	  {
		  //need to write extra condition
		  return new ResponseEntity<User>(user,HttpStatus.CONFLICT);
	  }
	}
	
	/*
	 * @PutMapping("/user/update") public ResponseEntity<User>
	 * updateUser(@RequestBody User user) { if(userDAO.save(user)) { return new
	 * ResponseEntity<User>(user,HttpStatus.OK);
	 * 
	 * } else { return new
	 * ResponseEntity<User>(user,HttpStatus.INTERNAL_SERVER_ERROR);
	 * 
	 * } }
	 */
	@PutMapping("/user/update")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		if (userDAO.save(user)) {
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		} else 
		{
			return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
		}
	
	
	}
	
	

}