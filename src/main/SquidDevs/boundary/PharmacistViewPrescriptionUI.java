package main.SquidDevs.boundary;

import main.SquidDevs.controller.PharmacistViewPrescriptionController;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JTextField;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class PharmacistViewPrescriptionUI extends JFrame {

	private JPanel topJPanel = new JPanel();
	private JPanel menuJPanel = new JPanel();
	private JButton btnLogout = new JButton("Log Out");
	private final JButton btnCheck = new JButton("Check");
	private final JButton btnDispense = new JButton("Dispense");

	private final JTextField txtPrescriptionId = new JTextField();
	private final JLabel lblPrescriptionId = new JLabel("Input prescription ID:");
	private final JLabel lblCurrentPrescriptionId = new JLabel("");
	private String prescriptionId;
	static public JDialog dialog;
	private JTable prescriptionTable;
	private DefaultTableModel model;
	private String checkedPrescriptionId = "nothing";

	public PharmacistViewPrescriptionUI(String loginId, String pharmName) {
		getContentPane().setBackground(new Color(0, 255, 255));
		setResizable(false);
		setTitle("SquidDevs Pharmarcy");
		setBounds(100, 100, 345, 497);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(topJPanel);
		getContentPane().add(menuJPanel);

		// top panel items
		topJPanel.setLayout(null);
		topJPanel.setBounds(10, 11, 309, 101);
		topJPanel.add(btnLogout);
		btnLogout.setBounds(0, 0, 89, 23);
		JLabel lblWelcome = new JLabel("Welcome " + pharmName + "!");
		topJPanel.add(lblWelcome);
		lblWelcome.setBounds(51, 23, 248, 51);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 20));

		btnCheck.setBounds(229, 68, 70, 23);

		topJPanel.add(btnCheck);
		txtPrescriptionId.setColumns(10);
		txtPrescriptionId.setBounds(118, 69, 103, 20);

		topJPanel.add(txtPrescriptionId);
		lblPrescriptionId.setBounds(0, 72, 119, 14);

		topJPanel.add(lblPrescriptionId);

		// menu panel items
		menuJPanel.setLayout(null);
		menuJPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		menuJPanel.setBounds(10, 123, 309, 324);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 25, 289, 265);
		menuJPanel.add(scrollPane);

		prescriptionTable = new JTable();
		scrollPane.setViewportView(prescriptionTable);
		btnDispense.setEnabled(false);

		btnDispense.setBounds(210, 290, 89, 23);
		menuJPanel.add(btnDispense);

		lblCurrentPrescriptionId.setVisible(false);
		lblCurrentPrescriptionId.setBounds(10, 11, 183, 14);
		menuJPanel.add(lblCurrentPrescriptionId);

		PharmacistViewPrescriptionController pharmacistViewPageController = new PharmacistViewPrescriptionController();

		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					prescriptionId = txtPrescriptionId.getText();
					if (isNumeric(prescriptionId)) {
						if (pharmacistViewPageController.prescriptionCollected(prescriptionId) != true) {
							checkSuccess(prescriptionId);
						}

						else {
							checkFail();

						}

					}
					else {
						checkFail();
					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		btnDispense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispenseMeds();

			}
		});

		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dispose();
					new LoginPageUi();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex, "Logout failed.", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		setVisible(true);

	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public void checkSuccess(String prescriptionId) {
		checkedPrescriptionId = prescriptionId;
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
		optionPane.setMessage("Here are the medicines listed in prescription " + prescriptionId + ".");
		dialog = optionPane.createDialog(null, "Prescription Found!");
		dialog.setVisible(true);
		lblCurrentPrescriptionId.setText("Prescription ID: " + prescriptionId);
		lblCurrentPrescriptionId.setVisible(true);
		fillTable(prescriptionId);
	}

	public void checkFail() {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
		optionPane.setMessage("Prescription does not exist or is already collected.");
		dialog = optionPane.createDialog(null, "Prescription not found.");
		dialog.setVisible(true);
		txtPrescriptionId.setText("");
		lblCurrentPrescriptionId.setVisible(false);
		checkedPrescriptionId = "nothing";
		btnDispense.setEnabled(false);
		if (model != null) {
			model.setRowCount(0);
		}
	}

	public void fillTable(String prescriptionId) {
		PharmacistViewPrescriptionController pharmacistViewPrescriptionController = new PharmacistViewPrescriptionController();
		ArrayList<ArrayList<String>> medicineList = pharmacistViewPrescriptionController
				.getMedicineList(prescriptionId);
		String[] column = { "Medicine Name", "Quantity" };
		Object[] row = new Object[2];

		prescriptionTable.setDefaultEditor(Object.class, null);
		model = new DefaultTableModel();
		model.setColumnIdentifiers(column);
		prescriptionTable.setModel(model);
		model = (DefaultTableModel) prescriptionTable.getModel();
		prescriptionTable.getTableHeader().setReorderingAllowed(false);

		for (int i = 0; i < medicineList.size(); i++) {
			for (int j = 0; j < 2; j++) {
				String data = medicineList.get(i).get(j + 1);
				row[j] = data;
			}
			model.addRow(row);
		}
		btnDispense.setEnabled(true);

	}

	public void dispenseMeds() {
		PharmacistViewPrescriptionController pharmacistViewPrescriptionController = new PharmacistViewPrescriptionController();
		JOptionPane optionPane = new JOptionPane();
		if (checkedPrescriptionId != "nothing") {
			pharmacistViewPrescriptionController.dispenseMedStatus(checkedPrescriptionId);
			optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
			optionPane.setMessage("Your Prescription has been successfully dispensed.");
			dialog = optionPane.createDialog(null, "Prescription dispsensed.");
			dialog.setVisible(true);
			txtPrescriptionId.setText("");
			lblCurrentPrescriptionId.setVisible(false);
			checkedPrescriptionId = "nothing";
			if (model != null) {
				model.setRowCount(0);
			}
			btnDispense.setEnabled(false);

		} else {
			checkFail();
		}

	}

}