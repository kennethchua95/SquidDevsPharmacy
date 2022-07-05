package main.SquidDevs.boundary;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.xml.validation.Validator;

import main.SquidDevs.controller.AddUserController;

import java.awt.event.ActionListener;
import java.util.jar.Attributes.Name;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class AdminAddUserUi extends JFrame {
	private JPanel topJPanel = new JPanel();
	private JPanel menuJPanel = new JPanel();
	private JButton btnBack = new JButton("Back");
	private JLabel lblAddNewUser = new JLabel("Add new user");
	private JLabel lblName = new JLabel("Name");
	private JLabel lblPassword = new JLabel("Password");
	private final JLabel lblID = new JLabel("User Id");
	private final JLabel lblJob = new JLabel("Job");
	private final JLabel lblEmail = new JLabel("Email");
	private final JLabel lblContactNo = new JLabel("Contact Number");
	private JTextField txtUserId;
	private JPasswordField passwordField;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtContactNo;
	private JComboBox ComboBoxJob = new JComboBox();
	static public JDialog dialog;

	public AdminAddUserUi(String loginId, String name) {
		getContentPane().setBackground(new Color(0, 255, 255));
		// UI stuff
		setResizable(false);
		setTitle("SquidDevs Pharmarcy");
		setBounds(100, 100, 308, 403);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().add(topJPanel);
		getContentPane().add(menuJPanel);

		// top panel items
		topJPanel.setBounds(20, 11, 254, 67);
		topJPanel.setLayout(null);
		topJPanel.add(btnBack);
		btnBack.setBounds(0, 0, 84, 23);
		topJPanel.add(lblAddNewUser);
		lblAddNewUser.setFont(new Font("Arial", Font.PLAIN, 18));
		lblAddNewUser.setBounds(67, 31, 145, 36);

		// menu panel item
		menuJPanel.setLayout(null);
		menuJPanel.setBorder(null);
		menuJPanel.setBounds(20, 89, 254, 264);
		// labels
		menuJPanel.add(lblName);
		menuJPanel.add(lblPassword);
		menuJPanel.add(lblID);
		menuJPanel.add(lblJob);
		menuJPanel.add(lblEmail);
		menuJPanel.add(lblContactNo);

		lblName.setFont(new Font("Arial", Font.PLAIN, 11));
		lblName.setBounds(10, 74, 73, 14);

		lblPassword.setFont(new Font("Arial", Font.PLAIN, 11));
		lblPassword.setBounds(10, 49, 73, 14);

		lblID.setFont(new Font("Arial", Font.PLAIN, 11));
		lblID.setBounds(10, 21, 73, 14);

		lblJob.setFont(new Font("Arial", Font.PLAIN, 11));
		lblJob.setBounds(10, 101, 73, 14);

		lblEmail.setFont(new Font("Arial", Font.PLAIN, 11));
		lblEmail.setBounds(10, 129, 73, 14);

		lblContactNo.setFont(new Font("Arial", Font.PLAIN, 11));
		lblContactNo.setBounds(10, 154, 93, 14);

		txtUserId = new JTextField();
		txtUserId.setFont(new Font("Arial", Font.PLAIN, 11));
		txtUserId.setBounds(93, 18, 151, 20);
		txtUserId.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 11));
		passwordField.setBounds(93, 46, 151, 20);

		txtName = new JTextField();
		txtName.setFont(new Font("Arial", Font.PLAIN, 11));
		txtName.setColumns(10);
		txtName.setBounds(93, 71, 151, 20);

		// JComboBox ComboBoxJob = new JComboBox();
		ComboBoxJob.setFont(new Font("Arial", Font.PLAIN, 11));
		ComboBoxJob.setBounds(93, 97, 151, 22);
		ComboBoxJob.addItem("Doctor");
		ComboBoxJob.addItem("Pharmacist");
		ComboBoxJob.addItem("Patient");
		ComboBoxJob.addItem("Admin");

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 11));
		txtEmail.setColumns(10);
		txtEmail.setBounds(93, 126, 151, 20);
		menuJPanel.add(txtEmail);

		txtContactNo = new JTextField();
		txtContactNo.setFont(new Font("Arial", Font.PLAIN, 11));
		txtContactNo.setColumns(10);
		txtContactNo.setBounds(93, 154, 151, 20);
		menuJPanel.add(txtContactNo);

		// input fields
		menuJPanel.add(txtUserId);
		menuJPanel.add(txtName);
		menuJPanel.add(passwordField);
		menuJPanel.add(ComboBoxJob);

		AddUserController addUserController = new AddUserController();
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				setVisible(false);
				new AdminPageUi(loginId, name);
			}
		});

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String userId = txtUserId.getText();
					String password = passwordField.getText();
					String name = txtName.getText();
					String jobSelected = (String) ComboBoxJob.getSelectedItem();
					String email = txtEmail.getText();
					String contactNo = txtContactNo.getText();
					
					
					if (validateInputs(userId, password, name, email, contactNo) == true) {
						if (addUserController.validateCreate(userId, password, name, jobSelected, email, contactNo)) {
							createSuccess();
						} else {
							userAlreadyExist();
						}

					} else {
						createFail();
					}

				} catch (Exception ex) {
					System.out.println(ex);
				}
			}

		});
		btnSubmit.setBounds(14, 197, 230, 23);
		menuJPanel.add(btnSubmit);

		setVisible(true);

	}

	private void userAlreadyExist() {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
		optionPane.setMessage("User ID already exist. Please pick a new User ID");
		dialog = optionPane.createDialog(null, "User ID in use.");
		dialog.setVisible(true);

	}

	private void createFail() {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
		optionPane.setMessage("Insertion failed, Wrong input format.");
		dialog = optionPane.createDialog(null, "Creation of new user failed");
		dialog.setVisible(true);

	}

	private void createSuccess() {
		JOptionPane.showMessageDialog(null, "User Created successful", "User Created successfully",
				JOptionPane.WARNING_MESSAGE);
		txtUserId.setText("");
		passwordField.setText("");
		txtName.setText("");
		txtEmail.setText("");
		txtContactNo.setText("");
	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	//UI input validation, not in diagrams
	public boolean validateInputs(String userId, String password, String name, String email, String contactNo) {
		if (isNumeric(contactNo)) {
			return (userId != null && userId.length() > 0 && password != null && password.length() > 0 && name != null
					&& name.length() > 0 && email != null && email.length() > 0 && contactNo != null
					&& contactNo.length() == 8);
		}
		return false;
	}

}