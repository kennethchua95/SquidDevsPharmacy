package main.SquidDevs.boundary;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import main.SquidDevs.controller.LoginController;
import main.SquidDevs.entity.User;

import javax.swing.JPasswordField;
import javax.print.attribute.standard.PrinterInfo;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.jar.Attributes.Name;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.Color;

public class LoginPageUi extends JFrame {

	private JFrame loginFrame;
	public JTextField LoginIdField;
	public JPasswordField passwordField;
	static public JDialog dialog;
	public JButton btnLogin = new JButton("Login");

	public JRadioButton rdbtnAdmin = new JRadioButton("Admin");
	public JRadioButton rdbtnDoctor = new JRadioButton("Doctor");
	public JRadioButton rdbtnPharmacist = new JRadioButton("Pharmacist");
	public JRadioButton rdbtnPatient = new JRadioButton("Patient");

	public boolean isTest = false;
	public String nameString;

	public LoginPageUi() {

		loginFrame = new JFrame();
		loginFrame.getContentPane().setBackground(new Color(0, 255, 255));
		loginFrame.setTitle("SquidDevs Pharmarcy");
		loginFrame.setResizable(false);
		loginFrame.setBounds(100, 100, 347, 415);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.getContentPane().setLayout(null);

		JLabel lblLoginID = new JLabel("Login ID");
		lblLoginID.setBounds(86, 78, 89, 25);
		loginFrame.getContentPane().add(lblLoginID);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(86, 134, 89, 14);
		loginFrame.getContentPane().add(lblPassword);

		LoginIdField = new JTextField();
		LoginIdField.setBounds(85, 100, 163, 23);
		loginFrame.getContentPane().add(LoginIdField);
		LoginIdField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(86, 153, 163, 25);
		loginFrame.getContentPane().add(passwordField);

		JLabel lblNewLabel = new JLabel("SquidDevs Pharmarcy");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel.setBounds(86, 27, 203, 38);
		loginFrame.getContentPane().add(lblNewLabel);

		ButtonGroup jobTypeButtonGroup = new ButtonGroup();

		rdbtnDoctor.setBackground(Color.CYAN);
		rdbtnDoctor.setBounds(180, 221, 109, 23);
		loginFrame.getContentPane().add(rdbtnDoctor);
		rdbtnDoctor.setActionCommand("Doctor");

		rdbtnPharmacist.setBackground(Color.CYAN);
		rdbtnPharmacist.setBounds(180, 247, 109, 23);
		loginFrame.getContentPane().add(rdbtnPharmacist);
		rdbtnPharmacist.setActionCommand("Pharmacist");

		rdbtnPatient.setBackground(Color.CYAN);
		rdbtnPatient.setBounds(66, 247, 109, 23);
		loginFrame.getContentPane().add(rdbtnPatient);
		rdbtnPatient.setActionCommand("Patient");

		rdbtnAdmin.setBackground(Color.CYAN);
		rdbtnAdmin.setBounds(66, 221, 109, 23);
		loginFrame.getContentPane().add(rdbtnAdmin);
		rdbtnAdmin.setActionCommand("Admin");

		jobTypeButtonGroup.add(rdbtnDoctor);
		jobTypeButtonGroup.add(rdbtnPharmacist);
		jobTypeButtonGroup.add(rdbtnPatient);
		jobTypeButtonGroup.add(rdbtnAdmin);

		btnLogin.setForeground(new Color(0, 0, 0));

		LoginController loginController = new LoginController();

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jobTypeButtonGroup.getSelection() == null) {
					JOptionPane optionPane = new JOptionPane();
					optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
					optionPane.setMessage("Please select Doctor , Pharmarcist , Patient or Admin.");
					dialog = optionPane.createDialog(null, "Please select a job.");
					dialog.setVisible(true);

				} else {
					try {

						String userId = LoginIdField.getText();
						String password = passwordField.getText();
						String selectedJob = jobTypeButtonGroup.getSelection().getActionCommand();

						if (loginController.validateLogin(userId, password, selectedJob) != false) {
							nameString = loginController.getUsersName(userId, password, selectedJob);
							loginSuccess(userId, selectedJob, nameString);
						}

						else {
							loginFail();
						}
					}

					catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
					}

				}

			}

		});
		btnLogin.setBounds(86, 292, 163, 23);
		loginFrame.getContentPane().add(btnLogin);

		JLabel lbljobSelect = new JLabel("Occupation:");
		lbljobSelect.setBounds(69, 200, 106, 14);
		loginFrame.getContentPane().add(lbljobSelect);

		loginFrame.setVisible(true);
	}

	public void loginFail() {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
		optionPane.setMessage("Login failed, Username / password is incorrect.");
		dialog = optionPane.createDialog(null, "Login failed");

		if (isTest) {
			throwDialog(dialog);
		}

		dialog.setVisible(true);

	}

	public void throwDialog(JDialog dialog) {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				Thread.sleep(1000);

				return null;
			}

			protected void done() {
				dialog.dispose();
			};
		}.execute();
	}

	private void loginSuccess(String loginid, String jobType, String name) {
		if (jobType == "Doctor") {
			DoctorPageUi doctorPageUi = new DoctorPageUi(loginid, name);
			dispose();
			loginFrame.setVisible(false);

		} else if (jobType == "Pharmacist") {
			PharmacistViewPrescriptionUI pharmacistViewPrescriptionUI = new PharmacistViewPrescriptionUI(loginid, name);
			dispose();
			loginFrame.setVisible(false);

		} else if (jobType == "Patient") {
			PatientPageUi patientPageUi = new PatientPageUi(loginid, name);
			dispose();
			loginFrame.setVisible(false);
		} else if (jobType == "Admin") {
			AdminPageUi adminPageUi = new AdminPageUi(loginid, name);
			dispose();
			loginFrame.setVisible(false);
		}
	}
}