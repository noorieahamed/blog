package com.niit.collaboration.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.collaboration.dao.UserDAO;
import com.niit.collaboration.model.User;
@Repository("userDAO")
//@EnableTransactionManagement
@Transactional
public class UserDaoImpl  implements UserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private User user;

	public boolean save(User user) {
		try {
			if(user.getRole()==null || user.getRole()==' ')
			{
				user.setRole('S');
			}
			user.setStatus('N');
			
			sessionFactory.getCurrentSession().save(user);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(User user) {
		try {
			sessionFactory.getCurrentSession().update(user);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(String emailId) {
		try {
			//before delete,first check whether the record
			user=get(emailId);
			//existing or not
			//if the record does not exist,simply return false;
			
			if(user==null)
			{
				return false;
			}
			else
			{
				sessionFactory.getCurrentSession().delete(user);
				return true;
			}
			
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
   /*will return user object if the record exist
    * else it will return null
    */
	public User get(String emailId) {
	return	(User) sessionFactory.getCurrentSession().get(User.class,emailId);
	}

	public List<User> list() {
	return	sessionFactory.getCurrentSession().createQuery("from User").list();
	}

	public User validate(String emailID, String password) {
		return (User) sessionFactory.getCurrentSession()
		.createCriteria(User.class)
		.add(Restrictions.eq("emailID", emailID))
		.add(Restrictions.eq("password", password)).uniqueResult();
	}
	
	

}