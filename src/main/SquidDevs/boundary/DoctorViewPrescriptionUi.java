package main.SquidDevs.boundary;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import main.SquidDevs.controller.DoctorAddPrescriptionController;
import main.SquidDevs.controller.DoctorViewPrescriptionController;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DoctorViewPrescriptionUi extends JFrame {

	private JPanel topJPanel = new JPanel();
	private JPanel menuJPanel = new JPanel();
	private JButton btnBack = new JButton("Back");
	private final JLabel lblViewId = new JLabel("Viewing Presciption history for:");
	private final JLabel lblPatientID = new JLabel("");
	private final JLabel lblNewLabel = new JLabel("Medicine Name:");
	private JTextField txtMedicineName;
	private JTable prescription_table;
	private final JButton update_btn = new JButton("Update selected Prescription");
	private final JButton delete_btn = new JButton("Delete selected Prescription");
	DefaultTableModel model;
	private JTextField txtQty;

	public DoctorViewPrescriptionUi(String doctorId, String doctorName, String patientId, String patientName) {
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
		JLabel lblWelcome = new JLabel("Welcome " + doctorName + "!");
		topJPanel.add(lblWelcome);
		lblWelcome.setBounds(445, 11, 284, 51);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lblViewId.setBounds(10, 77, 195, 14);
		topJPanel.add(lblViewId);
		lblPatientID.setBounds(209, 77, 119, 14);

		topJPanel.add(lblPatientID);
		lblPatientID.setText(patientName);

		// menu panel items
		menuJPanel.setLayout(null);
		menuJPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		menuJPanel.setBounds(10, 124, 941, 362);
		lblNewLabel.setBounds(34, 57, 105, 14);

		menuJPanel.add(lblNewLabel);

		txtMedicineName = new JTextField();
		txtMedicineName.setBounds(149, 54, 86, 20);
		menuJPanel.add(txtMedicineName);
		txtMedicineName.setColumns(10);

		JLabel lblQty = new JLabel("Quantity:");
		lblQty.setBounds(34, 100, 87, 14);
		menuJPanel.add(lblQty);

		txtQty = new JTextField();
		txtQty.setBounds(149, 97, 86, 20);
		menuJPanel.add(txtQty);
		txtQty.setColumns(10);

		JScrollPane prescription_scrollPane = new JScrollPane();
		prescription_scrollPane.setBounds(263, 11, 668, 340);
		menuJPanel.add(prescription_scrollPane);

		prescription_table = new JTable();
		String[] column = { "Prescription ID", "Doctor Name", "Medicine Name", "Quantity", "Date of Issue",
				"Collection Status" };
		Object[] row = new Object[6];
		prescription_table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int getRow = prescription_table.getSelectedRow();

				txtMedicineName.setText(prescription_table.getModel().getValueAt(getRow, 2).toString());
				txtQty.setText(prescription_table.getModel().getValueAt(getRow, 3).toString());

			}
		});

		prescription_table.setDefaultEditor(Object.class, null);
		prescription_scrollPane.setViewportView(prescription_table);
		model = new DefaultTableModel();
		model.setColumnIdentifiers(column);
		prescription_table.setModel(model);
		model = (DefaultTableModel) prescription_table.getModel();
		prescription_table.getTableHeader().setReorderingAllowed(false);

		DoctorViewPrescriptionController doctorViewPrescriptionController = new DoctorViewPrescriptionController();
		ArrayList<ArrayList<String>> pre_pList = doctorViewPrescriptionController.getPrescription(patientId);

		getPrescriptionTable(pre_pList, row);

		update_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePrescription(patientId, doctorId, doctorName, patientName);
			}
		});

		update_btn.setBounds(34, 166, 201, 23);

		menuJPanel.add(update_btn);
		menuJPanel.add(delete_btn);
		delete_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletePrescription(doctorId, doctorName, patientId, patientName);

			}
		});
		delete_btn.setBounds(34, 274, 201, 23);

		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dispose();
					new DoctorPageUi(doctorId, doctorName);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex, "Error.", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		setVisible(true);

	}

	public void getPrescriptionTable(ArrayList<ArrayList<String>> pre_pList, Object[] row) {
		for (int i = 0; i < pre_pList.size(); i++) {

			for (int j = 0; j < pre_pList.get(i).size(); j++) {

				String test = pre_pList.get(i).get(j);
				// System.out.println(test + "loop");
				if (j < 6) {
					row[j] = test;
				}

				if (j == 5) {
					model.addRow(row);
				}

			}
		}
	}

	DoctorAddPrescriptionController doctorAddPrescriptionController = new DoctorAddPrescriptionController();
	DoctorViewPrescriptionController doctorViewPrescriptionController = new DoctorViewPrescriptionController();

	public void updatePrescription(String patientId, String doctorId, String doctorName, String patientName) {
		int confirmUpdate = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this prescription?",
				null, JOptionPane.YES_NO_OPTION);

		if (confirmUpdate == 0) {
			int i = prescription_table.getSelectedRow();
			if (i >= 0) {

				String p_id = prescription_table.getModel().getValueAt(i, 0).toString();
				String old_MedName = prescription_table.getModel().getValueAt(i, 2).toString();
				String new_MedName = txtMedicineName.getText();
				String medQty = txtQty.getText();
				Date date = new Date();
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				String strDate = dateFormat.format(date);

				if (Integer.parseInt(medQty) < 1) {
					JOptionPane.showMessageDialog(null, "ERROR: Invalid amount of medicine inputted.");
				} else {
					if (doctorAddPrescriptionController.validateMedicine(new_MedName) == true) {

						if (doctorViewPrescriptionController.updatePrescription(p_id, old_MedName, new_MedName, medQty,
								strDate) == true) {
							String email = doctorViewPrescriptionController.getEmail(patientId);
							doctorViewPrescriptionController.sendUpdatedToken(email, p_id, new_MedName, medQty);
							dispose();
							new DoctorViewPrescriptionUi(doctorId, doctorName, patientId, patientName);
							JOptionPane.showMessageDialog(null, "Prescription Updated Successfully!");
						} else {
							JOptionPane.showMessageDialog(null, "ERROR: Prescription was not updated successfully.");
						}

					} else {
						JOptionPane.showMessageDialog(null, "ERROR: That medicine does not exist.");
					}
				}

			} else {
				JOptionPane.showMessageDialog(null, "Please select a prescription from the table.");
			}
		}
	}

	public void deletePrescription(String doctorId, String doctorName, String patientId, String patientName) {
		int confirmDelete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this prescription?",
				null, JOptionPane.YES_NO_OPTION);

		if (confirmDelete == 0) {
			int i = prescription_table.getSelectedRow();
			if (i >= 0) {

				String p_id = prescription_table.getModel().getValueAt(i, 0).toString();
				String p_medName = prescription_table.getModel().getValueAt(i, 2).toString();

				if (doctorViewPrescriptionController.deletePrescription(p_id, p_medName) == true) {

					JOptionPane.showMessageDialog(null, "Prescription Deleted Successfully!");
					dispose();
					new DoctorViewPrescriptionUi(doctorId, doctorName, patientId, patientName);

				} else {
					JOptionPane.showMessageDialog(null, "ERROR: Prescription was not deleted successfully.");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Please select a prescription from the table.");
			}
		}
	}
}