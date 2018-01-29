package com.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.DAO.UserDAO;
import com.Model.ErrorClazz;
import com.Model.User;

@Controller
public class UserController {

	@Autowired
	private UserDAO userDAO;
	
	public UserController(){
		System.out.println("Creating Instance For UserController");
	}
	
	@RequestMapping (value="/registeruser",method=RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user){
		try{
			if(!userDAO.isUsernameValid(user.getUsername())){
				ErrorClazz error=new ErrorClazz(2,"Username Already Exists...Please Enter A different Username");
				return new ResponseEntity<ErrorClazz>(error,HttpStatus.CONFLICT);
			}
			if(!userDAO.isEmailValid(user.getEmail())){
				ErrorClazz error=new ErrorClazz(3,"Email Already Exists...Please Enter A different Email");
				return new ResponseEntity<ErrorClazz>(error,HttpStatus.CONFLICT);
			}
			
			userDAO.registerUser(user);
		}
		catch(Exception e){
			ErrorClazz error=new ErrorClazz(1,"Unable To Register The User....Please Fill All The Necessary Details");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody User user,HttpSession session ){
		User validUser = userDAO.login(user);
		if(validUser==null){
			ErrorClazz error = new ErrorClazz(4,"Invalid Usernamer Or Password");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}
		else{
			validUser.setOnline(true);
			session.setAttribute("username", validUser.getUsername());
			userDAO.updateUser(validUser);
			return new ResponseEntity<User>(validUser,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ResponseEntity<?> logout(HttpSession session){
		String username = (String)session.getAttribute("username");
		if(username==null){
			ErrorClazz error = new ErrorClazz(5,"Unauthorized Access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}
		User user = userDAO.getUserByUsername(username);
		user.setOnline(false);
		userDAO.updateUser(user);
		session.removeAttribute(username);
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/getuser",method=RequestMethod.GET)
	public ResponseEntity<?> getUser(HttpSession session){
		String username=(String)session.getAttribute("username");
		if(username==null){
			ErrorClazz error = new ErrorClazz(5,"Unauthorized Access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}
		User user=(User)userDAO.getUserByUsername(username);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@RequestMapping(value="/edituserprofile",method=RequestMethod.PUT)
	public ResponseEntity<?> editUserProfile(@RequestBody User user,HttpSession session){
		String username=(String)session.getAttribute("username");
		if(username==null){
			ErrorClazz error = new ErrorClazz(5,"Unauthorized Access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}
		try{
			userDAO.updateUser(user);
		}catch(Exception e){
			ErrorClazz error=new ErrorClazz(6,e.getMessage());
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	/*
	 //@ResponseBody - converts list of JAVA objects into array of JSON format
	@RequestMapping(value="/getallusers",method=RequestMethod.GET)
	public ResponseEntity<?> getAllUsers(){
		List<User> users = userDAO.getAllUsers();
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	 
	@RequestMapping(value="/deleteuser/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable int id){
		try{
			userDAO.deleteUser(id);
		}
		catch(Exception e){
			ErrorClazz error= new ErrorClazz(1,"User with id "+id+" doesn't exist");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.NOT_FOUND);
		}
		List<User> users=userDAO.getAllUsers();
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	
	
	
	*/
}
