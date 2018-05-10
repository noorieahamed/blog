package com.niit.collaboration.daoimpl;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.collaboration.dao.JobDAO;
import com.niit.collaboration.dao.UserDAO;
import com.niit.collaboration.model.Job;
import com.niit.collaboration.model.JobApplication;

@Repository("jobDAO")
@Transactional
public class JobDAOImpl implements JobDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDAO userDAO;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * return max job id of all the records if the records are exist else return 100
	 * 
	 * @return
	 */
	private int getMaxJobID() {
		int maxValue = 100;
		try {
			maxValue = (Integer) getCurrentSession().createQuery("select max(id) from Job").uniqueResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 100;
		}

		return maxValue;
	}

	private int getMaxJobapplicationID() {
		int maxValue = 100;
		try {
			maxValue = (Integer) getCurrentSession().createQuery("select max(id) from JobApplication").uniqueResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 100;
		}

		return maxValue;
	}

	public boolean save(Job job) {
		try {
			job.setId(getMaxJobID() + 1);
			//job.setPosted_date(new Date(System.currentTimeMillis()));
			job.setStatus('N');
			getCurrentSession().save(job);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Job job) {
		try {
			getCurrentSession().update(job);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public Job get(int jobID) {
		return (Job) getCurrentSession().createCriteria(Job.class).add(Restrictions.eq("id", jobID)).uniqueResult();
	}

	public List<Job> list() {
		return getCurrentSession().createQuery("from Job").list();
	}

	public List<Job> list(char status) {
		return getCurrentSession().createCriteria(Job.class).add(Restrictions.eq("status", status)).list();

	}

	public boolean save(JobApplication jobApplication) {
		try {
			if (!isJobOpened(jobApplication.getJobID())) {
				return false;
			}
			// if you already applied, you can not apply again
			if (isJobAlreadyApplied(jobApplication.getEmailID(), jobApplication.getJobID())) {
				return false;
			}
			
			//if user does not exist, you can not apply
			
			if(userDAO.get(jobApplication.getEmailID())==null)
			{
				return false;
			}
			
			//if the job does not exist, you can not apply
			if(get(jobApplication.getJobID())==null)
			{
				return false;
			}

			jobApplication.setId(getMaxJobapplicationID() + 1);
			jobApplication.setStatus('N');
			jobApplication.setApplied_date(new Date(System.currentTimeMillis()));
			getCurrentSession().save(jobApplication);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(JobApplication jobApplication) {
		try {
			getCurrentSession().update(jobApplication);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public List<JobApplication> list(int jobID) {
		return getCurrentSession().createCriteria(JobApplication.class).add(Restrictions.eq("id", jobID)).list();

	}

	public List<JobApplication> list(int jobID, char status) {
		return getCurrentSession().createCriteria(JobApplication.class).add(Restrictions.eq("id", jobID))
				.add(Restrictions.eq("status", status)).list();

	}

	/**
	 * This method will return true, if the job with id exist and status is open.
	 * else return false.
	 */
	public boolean isJobOpened(int id) {
		Job job = (Job) getCurrentSession().createCriteria(Job.class).add(Restrictions.eq("id", id)).uniqueResult();

		if (job != null && job.getStatus() == 'N') {
			return true;
		}

		return false;

	}

	/**
	 * This method will return true if the job already applied with this emaild.
	 * else, return false
	 */

	public boolean isJobAlreadyApplied(String emailID, int jobID) {

		//select * from JobApplication where emailID = ? and jobID = ?
		JobApplication jobApplication = (JobApplication) getCurrentSession()
				.createCriteria(JobApplication.class)
				.add(Restrictions.eq("emailID", emailID))
				.add(Restrictions.eq("jobID", jobID)).uniqueResult();

		if (jobApplication == null) {
			return false;
		}
		return true;

	}

}