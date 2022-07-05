package main.SquidDevs.controller;

import java.util.ArrayList;

import main.SquidDevs.entity.Prescription;
import main.SquidDevs.entity.PrescriptionInfo;
import main.SquidDevs.entity.User;

public class EditUserController {

	User user = new User();
	Prescription prescription = new Prescription();
	PrescriptionInfo prescriptionInfo = new PrescriptionInfo();

	public ArrayList<User> getUsers() {
		return user.getAllUsers();
	}

	public ArrayList<User> searchUsers(String id) {
		return user.getUsersBySearch(id);
	}

	public boolean doesIdExist(String Id) {
		return user.isIdInDatabase(Id);
	}

	public boolean updateUser(String userId, String name, String password, String selectedJob, String email,
			String contactNo) {
		int contactNoInt = Integer.parseInt(contactNo);

		return user.updateUserUsingUserId(userId, name, password, selectedJob, email, contactNoInt);

	}

	public boolean deleteUser(String userId) {

		return user.deleteUserUsingUserId(userId);
	}

}
