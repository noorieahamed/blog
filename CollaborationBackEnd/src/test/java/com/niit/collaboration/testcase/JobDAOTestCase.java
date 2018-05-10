package com.niit.collaboration.testcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.niit.collaboration.dao.JobDAO;
import com.niit.collaboration.model.Job;
import com.niit.collaboration.model.JobApplication;

public class JobDAOTestCase {

	private static AnnotationConfigApplicationContext context;

	@Autowired
	private static JobDAO jobDAO;

	@Autowired
	private static Job job;

	@Autowired
	private static JobApplication jobApplication;

	// we need create instance of AnnotationConfigApplicationContext
	// only once
	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.niit");
		context.refresh();

		jobDAO = (JobDAO) context.getBean("jobDAO");

		job = (Job) context.getBean("job");
		jobApplication = (JobApplication) context.getBean("jobApplication");

	}

	@Test
	public void saveJobTestCase() {

		job.setDescription("This is trainee job.");
		job.setNo_of_openings(3);
		job.setQualification("B.Tech");
		job.setSalary(30000);
		job.setTitle("Trainee");

		Assert.assertEquals("Save job test case", true, jobDAO.save(job));

	}

	@Test
	public void updateJobTestCaseSuccess() {
		job = jobDAO.get(103);
		job.setStatus('N');
		job.setQualification("B.Sc");

		Assert.assertEquals(true, jobDAO.update(job));
	}

	@Test
	public void updateJobTestCaseFailure() {
		job = jobDAO.get(105);

		Assert.assertNotNull(job);
		job.setStatus('N');
		job.setQualification("B.Sc");

		Assert.assertEquals(true, jobDAO.update(job));
	}
	
	@Test
	public void getJobSuccessTestCase()
	{
		Assert.assertNotNull( jobDAO.get(101));
	}
	
	@Test
	public void getJobFailureTestCase()
	{
		Assert.assertNull( jobDAO.get(108));
	}
	
	@Test
	public void getAllJobsTestCase()
	{
	Assert.assertEquals(23,	jobDAO.list().size());
	}
	
	@Test
	public void closeJobTestCase()
	{
	job   =	jobDAO.get(102);
	job.setStatus('C');
	 Assert.assertEquals(true ,jobDAO.update(job));
	}
	@Test
	public void isJobOpendSuccessTestCase()
	{
	   Assert.assertEquals(true,	jobDAO.isJobOpened(118));
	}
	
	@Test
	public void isJobOpendFailureTestCase()
	{
	   Assert.assertEquals(false,	jobDAO.isJobOpened(103));
	}
	
	//========================================================================//
	
	//JOB Application related test cases
	
	@Test
	public void applyForAJobSuccessTestCase()
	{
		jobApplication.setEmailID("munmun@gmail.com");
		jobApplication.setJobID(118);
		Assert.assertEquals(true ,jobDAO.save(jobApplication));
	}
	
	@Test
	public void applyForAJobFailureTestCase()
	{
		jobApplication.setEmailID("tina@gmail.com");
		jobApplication.setJobID(101);
		Assert.assertEquals(false ,jobDAO.save(jobApplication));
	}
	
	@Test public void isJobAlreadyAppliedSuccessTestCase()
	{
		Assert.assertEquals(true ,jobDAO.isJobAlreadyApplied("munmun@gmail.com", 102));
	}
	
	@Test public void isJobAlreadyAppliedFailureTestCase()
	{
		Assert.assertEquals(false ,jobDAO.isJobAlreadyApplied("tina@gmail.com", 106));
	}
	
	
	
	
	
	
	
	

}