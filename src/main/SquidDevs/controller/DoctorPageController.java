package main.SquidDevs.controller;

import main.SquidDevs.entity.Patient;
import main.SquidDevs.entity.User;

public class DoctorPageController {

	Patient patient = new Patient();
	User user = new User();

	public boolean patientExist(String patientId) {
		return patient.doesPatientExsit(patientId);
	}

	public String getPatientName(String patientId) {
		String name = patient.getName(patientId);
		return name;
	}

}
