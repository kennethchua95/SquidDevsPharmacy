package main.SquidDevs.controller;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import main.SquidDevs.entity.Prescription;
import main.SquidDevs.entity.PrescriptionInfo;
import main.SquidDevs.entity.User;

public class DoctorViewPrescriptionController {
	Prescription p = new Prescription();
	PrescriptionInfo pInfo = new PrescriptionInfo();
	User user = new User();

	public ArrayList<ArrayList<String>> getPrescription(String patientId) {
		ArrayList<Prescription> p_list = p.getPrescriptionFromPatientId(patientId);

		ArrayList<ArrayList<String>> final_list = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < p_list.size(); i++) {

			ArrayList<String> temp = new ArrayList<String>();

			temp.add(p_list.get(i).getPrescriptionId());
			temp.add(p_list.get(i).getDoctorName());
			temp.add(p_list.get(i).getMedicineName());
			temp.add(p_list.get(i).getMedicineQty());
			temp.add(p_list.get(i).getDate());
			temp.add(p_list.get(i).getIsCollected());

			final_list.add(temp);
			// System.out.println(final_list);
			// temp.clear();

		}

		return final_list;
	}

	public boolean updatePrescription(String p_id, String old_medName, String new_medName, String medQty, String date) {
		int id_Int = Integer.parseInt(p_id);
		int medQty_int = Integer.parseInt(medQty);

		return pInfo.updatePrescriptionInfoUsingPrescriptionId(id_Int, old_medName, new_medName, medQty_int, date);
	}

	public boolean deletePrescription(String id_Str, String medicineName) {
		int id_Int = Integer.parseInt(id_Str);

		boolean pListDel = pInfo.deletePrescriptionInfoUsingPrescriptionIdAndMedicineName(id_Int, medicineName);

		if (pListDel == true) {
			boolean pDel = p.deletePrescriptionUsingPrescriptionInfoId();
			return pDel;
		} else {
			return false;
		}
	}

	public String getEmail(String patientId) {
		return user.getEmail(patientId);
	}

	public void sendUpdatedToken(String email, String prescriptionId, String medicineName, String medQty) {
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
			message.setSubject("Prescription Update from SquidDev Pharmarcy.");
			message.setText("Your prescription (Prescription ID: " + prescriptionId
					+ "), has been updated by your doctor. Your medication has been adjusted to: " + "\n\r"
					+ medicineName + " (Quantity: " + medQty + ")" + "\n\r"
					+ "Please show the updated prescription to your nearest pharmarcist to collect your medication.");

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
