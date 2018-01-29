package com.DAO;

import java.util.List;

import com.Model.Job;

public interface JobDAO {
	
	void saveJob(Job job);
	List<Job> getAllJobs();
	Job getJobById(int id); 
}
