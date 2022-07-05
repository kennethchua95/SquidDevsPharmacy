package main.SquidDevs.controller;

import java.util.ArrayList;

import main.SquidDevs.entity.Prescription;
import main.SquidDevs.entity.PrescriptionInfo;
import main.SquidDevs.entity.User;

public class PharmacistViewPrescriptionController {

	Prescription prescription = new Prescription();
	PrescriptionInfo prescriptionInfo = new PrescriptionInfo();
	User user = new User();

	public boolean prescriptionCollected(String prescriptionId) {
		//returns a true or false
		return prescription.checkIfCollected(prescriptionId);
	}
	
	//gets the medicine's name and QTY to add to boundary display table.
	public ArrayList<ArrayList<String>> getMedicineList(String prescriptionID) {

		ArrayList<PrescriptionInfo> pi_list = prescriptionInfo.getMedicineFromPrescriptionId(prescriptionID);

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

	public boolean dispenseMedStatus(String prescriptionId) {
		return prescription.updateCollectionStatus(prescriptionId);
	}

}
