package com.niit.collaboration.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="c_job_application")
public class JobApplication {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private  int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public int getJobID() {
		return jobID;
	}

	public void setJobID(int jobID) {
		this.jobID = jobID;
	}

	public Date getApplied_date() {
		return applied_date;
	}

	public void setApplied_date(Date applied_date) {
		this.applied_date = applied_date;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	private String emailID;
	private int jobID;
	private Date applied_date;
	private char status;//N-New Apllication,R-reject,C-call for interview,S-Selected
	
	private String reason;
	
	

}
