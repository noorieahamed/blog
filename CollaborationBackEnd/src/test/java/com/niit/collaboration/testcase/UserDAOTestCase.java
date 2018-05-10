package com.niit.collaboration.testcase;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.niit.collaboration.dao.UserDAO;
import com.niit.collaboration.model.User;


//@ComponentScan(basePackageClasses= "com.niit")
public class UserDAOTestCase {

private static AnnotationConfigApplicationContext context;
	
	@Autowired
	private static UserDAO userDAO;
	
	@Autowired
	private static User user;
	
	//we need create instance of AnnotationConfigApplicationContext
	//only once
	@BeforeClass
	public static void init()
	{
		context= new AnnotationConfigApplicationContext();
		context.scan("com.niit");
		context.refresh();
		
		userDAO = (UserDAO) context.getBean("userDAO");
		
		user = (User) context.getBean("user");
		
	}
	
	@Test
	public void addUserTestCase()
	{
		user.setEmailID("sara@gmail.com");
		user.setName("sara raii");
		user.setDetails("chennai");
		user.setPassword("1234");
		
		//boolean actual = userDAO.save(user);
		Assert.assertEquals("Add User Test Case" , true  , userDAO.save(user));
		
	}
	
	
	@Test
	public void updateUserTestCase()
	{
		//user = new User();
		//user.setEmailID("jivan@gmail.com");
		
		
		user = userDAO.get("sara@gmail.com");
		
		user.setMobile("8719324447");
		
		boolean actual = userDAO.update(user);
		
	    Assert.assertEquals("Update User", true, actual );
		
				
	}
	
	
	@Test
	public void getUserTestCase()
	{
		
		Assert.assertNotNull("Get User Test Cases", userDAO.get("tina@gmail.com"));
	}
	
	//delete test case
	
	//get all user test cases
	
	//validate test
	
	
	
	
	@Test
	public void validateUserTestCase()
	{
	 Assert.assertNotNull("Validate Testcase",userDAO.validate("tina@gmail.com", "1234"));
	}
	
		
	@Test	
	public void deleteUserTestCase()
	{
		boolean actual= userDAO.delete("jivan@gmail.com");
		Assert.assertEquals(true, actual);
	}
	@Test	
	public void getAllUsers()
	{
		int actualSize = userDAO.list().size();
		Assert.assertEquals(6, actualSize);
		
	}
		
		
		
		
		
		
		
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	

