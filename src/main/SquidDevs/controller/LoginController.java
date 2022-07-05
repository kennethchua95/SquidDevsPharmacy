package main.SquidDevs.controller;

import main.SquidDevs.entity.User;

public class LoginController {

	User user = new User();
	
	
	public boolean validateLogin(String userId, String password, String job) {
		//if user exists = login 
		return user.doesUserExist(userId, password, job);
	}
	
	// used to get user's name 
	public String getUsersName(String id, String password, String job) {
		String usernameString = user.getName(id, password, job);
		return usernameString;
	}

}
