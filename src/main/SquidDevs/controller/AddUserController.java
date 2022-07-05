package main.SquidDevs.controller;

import main.SquidDevs.entity.User;

public class AddUserController {
	private User user = new User();

	public boolean validateCreate(String userId, String password, String name, String job, String email,
			String contactNo) {

		// doesUserExist is for validation, not in diagrams.
		// if user id already in use, will not be able to create.
		if (user.doesUserExist(userId, password, job)) {
			return false;
		} else {
			return user.createUser(userId, password, name, job, email, contactNo);
		}

	}
}
