package com.Controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.DAO.JobDAO;
import com.DAO.UserDAO;
import com.Model.ErrorClazz;
import com.Model.Job;
import com.Model.User;

@Controller
public class JobController {

	@Autowired
	private JobDAO jobDAO;
	private UserDAO userDAO;
	
	@RequestMapping(value="/savejob",method=RequestMethod.POST)
	public ResponseEntity<?> saveJob(@RequestBody Job job,HttpSession session){
		String username= (String)session.getAttribute("username");
		if(username==null){
			ErrorClazz error=new ErrorClazz(5,"Unauthorized Access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}
		User user=userDAO.getUserByUsername(username);
		
		if(!user.getRole().equals("ADMIN")){
			ErrorClazz error=new ErrorClazz(6,"Access Denied");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}
		job.setPostedOn(new Date());
		try{
			jobDAO.saveJob(job);
		}
		catch(Exception e){
			ErrorClazz error=new ErrorClazz(7,"Unable To Insert Job Details "+e.getMessage());
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
	
	@RequestMapping(value="/alljobs",method=RequestMethod.GET)
	public ResponseEntity<?> getAllJobs(HttpSession session){
		String username = (String)session.getAttribute("username");
		if(username==null){
			ErrorClazz error=new ErrorClazz(5,"Unauthorized Access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}	
		
		List<Job> jobs = jobDAO.getAllJobs();
		
		return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
	}
}
