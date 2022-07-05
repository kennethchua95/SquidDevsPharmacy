package main.SquidDevs.controller;

import java.util.ArrayList;

import main.SquidDevs.entity.Patient;
import main.SquidDevs.entity.Prescription;
import main.SquidDevs.entity.PrescriptionInfo;
import main.SquidDevs.entity.User;

public class PatientPrescriptionController {

	Patient patient = new Patient();
	Prescription p = new Prescription();
	PrescriptionInfo pInfo = new PrescriptionInfo();

	public ArrayList<ArrayList<String>> getPrescription(String patientId) {
		ArrayList<Prescription> p_list = p.getUniquePrescription(patientId);

		ArrayList<ArrayList<String>> final_list = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < p_list.size(); i++) {

			ArrayList<String> temp = new ArrayList<String>();

			temp.add(p_list.get(i).getPrescriptionId());
			temp.add(p_list.get(i).getDate());
			temp.add(p_list.get(i).getIsCollected());
			temp.add(p_list.get(i).getDoctorId());
			final_list.add(temp);
		}

		return final_list;
	}

	public String getDoctorName(String doctorID) {
		User u = new User(doctorID);
		String name = u.getName();
		return name;
	}

	public ArrayList<ArrayList<String>> getMedicine(String prescriptionID) {

		ArrayList<PrescriptionInfo> pi_list = pInfo.getMedicineFromPrescriptionId(prescriptionID);

		ArrayList<ArrayList<String>> final_list = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < pi_list.size(); i++) {

			ArrayList<String> temp = new ArrayList<String>();

			temp.add(pi_list.get(i).getPrescriptionId());
			temp.add(pi_list.get(i).getMedicineName());
			temp.add(pi_list.get(i).getMedicineQty());
			final_list.add(temp);
		}

		return final_list;
	}
}
