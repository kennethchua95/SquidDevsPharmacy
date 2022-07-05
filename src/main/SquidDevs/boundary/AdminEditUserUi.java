package main.SquidDevs.boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import main.SquidDevs.controller.EditUserController;
import main.SquidDevs.controller.LoginController;
import main.SquidDevs.entity.User;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminEditUserUi extends JFrame {

	private JPanel topJPanel = new JPanel();
	private JPanel menuJPanel = new JPanel();
	private JButton btnBack = new JButton("Back");
	private final JLabel lblViewId = new JLabel("Search by ID:");
	private final JLabel lblPatientID = new JLabel("");
	private JTable user_table;
	private final JButton update_btn = new JButton("Update User");
	private final JButton delete_btn = new JButton("Delete User");
	DefaultTableModel model;
	private JTextField txtPassword;
	private JTextField txtName;
	JComboBox<String> comboBoxJob = new JComboBox<String>();
	String[] jobs = { "-- Select --", "Admin", "Doctor", "Patient", "Pharmacist" };
	private JTextField txtEmail;
	private JTextField txtContactNo;
	private final JTextField txtSearch = new JTextField();
	static public JDialog dialog;
	boolean isSearchedPressed;

	public AdminEditUserUi(String loginId, String adminName) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
			}
		});
		txtSearch.setBounds(388, 74, 444, 20);
		txtSearch.setColumns(10);
		setResizable(false);
		getContentPane().setBackground(new Color(0, 255, 255));
		setTitle("SquidDevs Pharmarcy");
		setBounds(100, 100, 977, 535);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(topJPanel);
		getContentPane().add(menuJPanel);

		// top panel items
		topJPanel.setLayout(null);
		topJPanel.setBounds(10, 11, 941, 102);
		topJPanel.add(btnBack);
		btnBack.setBounds(0, 0, 89, 23);
		JLabel lblWelcome = new JLabel("Welcome " + adminName + "!");
		topJPanel.add(lblWelcome);
		lblWelcome.setBounds(445, 11, 284, 51);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lblViewId.setBounds(270, 77, 108, 14);
		topJPanel.add(lblViewId);
		lblPatientID.setBounds(209, 77, 119, 14);

		topJPanel.add(lblPatientID);
		topJPanel.add(txtSearch);

		// menu panel items
		menuJPanel.setLayout(null);
		menuJPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		menuJPanel.setBounds(10, 124, 941, 362);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(32, 59, 105, 14);
		menuJPanel.add(lblPassword);

		txtPassword = new JTextField();
		txtPassword.setBounds(149, 56, 104, 20);
		menuJPanel.add(txtPassword);
		txtPassword.setColumns(10);

		JScrollPane user_scrollPane = new JScrollPane();
		user_scrollPane.setBounds(263, 11, 668, 340);
		menuJPanel.add(user_scrollPane);

		comboBoxJob = new JComboBox(jobs);
		comboBoxJob.setSelectedItem(jobs[0]);
		comboBoxJob.setBounds(149, 87, 104, 22);
		menuJPanel.add(comboBoxJob);

		user_table = new JTable();
		String[] column = { "User ID", "Password", "Name", "Job", "Email", "Contact Number" };
		user_table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int getRow = user_table.getSelectedRow();

				txtPassword.setText(user_table.getModel().getValueAt(getRow, 1).toString());
				txtName.setText(user_table.getModel().getValueAt(getRow, 2).toString());
				comboBoxJob.setSelectedItem(user_table.getModel().getValueAt(getRow, 3));
				txtEmail.setText(user_table.getModel().getValueAt(getRow, 4).toString());
				txtContactNo.setText(user_table.getModel().getValueAt(getRow, 5).toString());

			}
		});

		user_table.setDefaultEditor(Object.class, null);
		user_scrollPane.setViewportView(user_table);
		model = new DefaultTableModel();
		model.setColumnIdentifiers(column);
		user_table.setModel(model);
		model = (DefaultTableModel) user_table.getModel();
		user_table.getTableHeader().setReorderingAllowed(false);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String id = txtSearch.getText();

				if (id.equals("")) {

					user_table.setModel(new DefaultTableModel(null,
							new String[] { "User ID", "Password", "Name", "Job", "Email", "Contact Number" }));
					model.setColumnIdentifiers(column);
					model = (DefaultTableModel) user_table.getModel();
					user_table.getTableHeader().setReorderingAllowed(false);
					displayAllUsers();
				} else {
					searchUsers(id);

					user_table.setModel(new DefaultTableModel(null,
							new String[] { "User ID", "Password", "Name", "Job", "Email", "Contact Number" }));
					model.setColumnIdentifiers(column);
					model = (DefaultTableModel) user_table.getModel();
					user_table.getTableHeader().setReorderingAllowed(false);
					displaySearchResults(id);
				}

			}
		});
		btnSearch.setBounds(842, 73, 89, 23);
		topJPanel.add(btnSearch);

		displayAllUsers();

		update_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateUser(loginId, adminName);
			}
		});

		update_btn.setBounds(32, 207, 221, 23);

		menuJPanel.add(update_btn);
		menuJPanel.add(delete_btn);
		delete_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteUser(loginId, adminName);
			}
		});
		delete_btn.setBounds(32, 284, 221, 23);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(32, 28, 105, 14);
		menuJPanel.add(lblName);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(149, 25, 104, 20);
		menuJPanel.add(txtName);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(149, 120, 104, 20);
		menuJPanel.add(txtEmail);

		txtContactNo = new JTextField();
		txtContactNo.setColumns(10);
		txtContactNo.setBounds(149, 151, 104, 20);
		menuJPanel.add(txtContactNo);

		JLabel lblJob = new JLabel("Job:");
		lblJob.setBounds(32, 91, 105, 14);
		menuJPanel.add(lblJob);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(32, 123, 105, 14);
		menuJPanel.add(lblEmail);

		JLabel lblContactNo = new JLabel("Contact Number:");
		lblContactNo.setBounds(32, 154, 105, 14);
		menuJPanel.add(lblContactNo);

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dispose();
					new AdminPageUi(loginId, adminName);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex, "Error.", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		setVisible(true);

	}

	EditUserController editUserController = new EditUserController();

	public void updateUser(String loginId, String adminName) {
		int confirmUpdate = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this user?", null,
				JOptionPane.YES_NO_OPTION);

		if (confirmUpdate == 0) {
			int i = user_table.getSelectedRow();
			if (i >= 0) {

				String userId = user_table.getModel().getValueAt(i, 0).toString();
				String password = txtPassword.getText();
				String name = txtName.getText();
				String selectedJob = (String) comboBoxJob.getSelectedItem();
				String email = txtEmail.getText();
				String contactNo = txtContactNo.getText();

				int numOfDigits = String.valueOf(contactNo).length();

				if ((password.equals("") || name.equals("") || selectedJob.equals("") || email.equals("")
						|| contactNo.equals(""))) {
					JOptionPane.showMessageDialog(null, "ERROR: Please fill in all the fields.");
				} else {
					if (numOfDigits == 8) {
						if (editUserController.updateUser(userId, password, name, selectedJob, email,
								contactNo) == true) {
							dispose();
							new AdminEditUserUi(loginId, adminName);
							updateSuccess();
						} else {
							updateFailed();
						}
					} else {
						JOptionPane.showMessageDialog(null, "ERROR: Contact Number must contain exactly 8 digits!");
					}

				}

			} else {
				noUserSelected();
			}
		}
	}

	public void deleteUser(String loginId, String adminName) {
		int confirmDelete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", null,
				JOptionPane.YES_NO_OPTION);

		if (confirmDelete == 0) {
			int i = user_table.getSelectedRow();
			if (i >= 0) {

				String userId = user_table.getModel().getValueAt(i, 0).toString();

				if (userId.equals(loginId)) {
					JOptionPane.showMessageDialog(null,
							"You cannot delete yourself! Please login as a different admin to delete this account.");

				} else {
					if (editUserController.deleteUser(userId) == true) {

						JOptionPane.showMessageDialog(null, "User Deleted Successfully!");
						dispose();
						new AdminEditUserUi(loginId, adminName);

					} else {
						JOptionPane.showMessageDialog(null, "ERROR: User was not deleted successfully.");
					}
				}

			} else {
				JOptionPane.showMessageDialog(null, "Please select a user from the table.");
			}
		}
	}

	public void searchUsers(String id) {
		if (editUserController.doesIdExist(id) == true) {
			editUserController.searchUsers(id);
		} else {
			JOptionPane.showMessageDialog(null, "ERROR: User does not exist.");
		}
	}

	public void displayAllUsers() {
		ArrayList<User> list = editUserController.getUsers();
		Object[] row = new Object[6];

		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getUserId();
			row[1] = list.get(i).getPassword();
			row[2] = list.get(i).getName();
			row[3] = list.get(i).getJob();
			row[4] = list.get(i).getEmail();
			row[5] = list.get(i).getContactNo();
			model.addRow(row);
		}

	}

	public void displaySearchResults(String name) {
		ArrayList<User> list = editUserController.searchUsers(name);
		Object[] row = new Object[6];

		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getUserId();
			row[1] = list.get(i).getPassword();
			row[2] = list.get(i).getName();
			row[3] = list.get(i).getJob();
			row[4] = list.get(i).getEmail();
			row[5] = list.get(i).getContactNo();
			model.addRow(row);
		}

	}

	private void updateSuccess() {
		JOptionPane.showMessageDialog(null, "Success!", "User updated successfully!", JOptionPane.PLAIN_MESSAGE);
		txtPassword.setText("");
		txtName.setText("");
		comboBoxJob.setSelectedItem(jobs[0]);
		txtEmail.setText("");
		txtContactNo.setText("");
	}

	private void updateFailed() {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
		optionPane.setMessage("ERROR: User was not updated successfully.");

	}

	private void noUserSelected() {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
		optionPane.setMessage("Please select a user from the table.");

	}
}
