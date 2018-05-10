package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.Job;
import com.niit.collaboration.model.JobApplication;

public interface JobDAO {
	//create a new job 
	public boolean save(Job job);
	//admin may update the job details
	
	public boolean update(Job job);
	//admin will not delate the job
	//once the job is closed , admin will change
	//status -F/C
	
	//fetch a particular job

	public Job get(int id);
	//fetching all jobs
	public List<Job> list();
	
	//fetch all the jobs based on status
	
	public List<Job> list(char status);
	
	public boolean isJobOpened(int id);
	
	//job application
	
	//apply for a particular job
	
public  boolean    save(JobApplication jobApplication);
	
	//Admin can reject/accept/call for interview
	
	public  boolean    update(JobApplication jobApplication);
	
	//Admin want to know the list of user those applid
	//for particular job
	
	public  List<JobApplication> list(int jobID);
	
	
	//Admin want fetch all the details of particular
	//job based on status
	
	public  List<JobApplication> list(int jobID,char status);
	
	
	public  boolean isJobAlreadyApplied(String emailID, int jobID);
	
}
