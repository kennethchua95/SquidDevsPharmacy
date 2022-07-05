package main.SquidDevs.controller;

import java.sql.SQLException;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import main.SquidDevs.entity.Medicine;
import main.SquidDevs.entity.Prescription;
import main.SquidDevs.entity.PrescriptionInfo;
import main.SquidDevs.entity.User;

public class DoctorAddPrescriptionController {

	User user = new User();
	Medicine medicine = new Medicine();
	Prescription prescription = new Prescription();
	PrescriptionInfo prescriptionInfo = new PrescriptionInfo();

	public String addNewPrescription(String doctorId, String patientId) throws SQLException {
		String prescriptionId = prescription.addPrescription(doctorId, patientId);
		if (prescriptionId != null) {
			return prescriptionId;
		}
		return "failed";

	}

	public boolean addPrescriptionInfo(String prescriptionId, String medicineName, String medicineQty) {
		return prescriptionInfo.addPrescriptionInfo(prescriptionId, medicineName, medicineQty);

	}
	
	//doesMedicineAlreadyExistInPrescription and addMoreToMedicineQty is not in BCE diagram
	//its used to make the database look nicer.
	
	//check if there is no duplicate medicineName in the same prescription.
	public boolean doesMedicineAlreadyExistInPrescription(String prescriptionId, String medicineName) {
		return prescriptionInfo.checkIfMedicineInPrescription(prescriptionId, medicineName);

	}
	//add medicineQty together.
	public void addMoreToMedicineQty(String prescriptionId, String medicineName, String medicineQtyInt) {
		prescriptionInfo.addToMedicineQty(prescriptionId, medicineName, medicineQtyInt);
	}


	
	//To make sure user does not input a medicine not in the database. Not included diagrams.
	public boolean validateMedicine(String medicineName) {
		return medicine.doesMedicineExist(medicineName);
	}
	
	public String getEmail(String patientId) {
		return user.getEmail(patientId);
	}
	
	public void sendEmail(String email, String prescriptionId) {
		// Recipient's email ID needs to be mentioned.
		String to = email;

		final String username = "squiddev1234@gmail.com";
		final String password = "Squidevs123";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); // TLS

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("squiddev123@outlook.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Prescription ID from SquidDev pharmarcy.");
			message.setText("Your prescription ID is " + prescriptionId + ". " + "\n\r"
					+ "Please show it to your nearest pharmarcist to collect your medication.");

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
