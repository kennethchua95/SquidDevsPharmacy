package main.SquidDevs.boundary;

import javax.security.auth.login.FailedLoginException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;

import main.SquidDevs.controller.DoctorAddPrescriptionController;
import main.SquidDevs.entity.Prescription;

import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;

public class DoctorAddPrescriptionUi extends JFrame {

	private JPanel topJPanel = new JPanel();
	private JPanel menuJPanel = new JPanel();
	private JButton btnBack = new JButton("back");
	private final JLabel lblAdd = new JLabel("Adding Presciption for:");
	private final JLabel lblPatientID = new JLabel("");
	private final JLabel lblMedicine = new JLabel("Medicine name:");
	private JTable medicineListTable;
	private final JScrollPane medicineList = new JScrollPane();
	private JTextField txtMedicineId;
	private final JButton btnSubmitPrescription = new JButton("Create Prescritption");
	private JTextField txtQuantity;
	private DefaultTableModel model;
	private String[] columnName;
	static public JDialog dialog;
	private String prescriptionId;
	private JButton btnDeletePrescription = new JButton("Delete");

	public DoctorAddPrescriptionUi(String loginId, String doctorName, String patientId, String patientName) {
		getContentPane().setBackground(new Color(0, 255, 255));
		setResizable(false);
		setTitle("SquidDevs Pharmarcy");
		setBounds(100, 100, 424, 519);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(topJPanel);
		getContentPane().add(menuJPanel);

		// top panel items
		topJPanel.setLayout(null);
		topJPanel.setBounds(10, 11, 386, 105);
		topJPanel.add(btnBack);
		btnBack.setBounds(0, 0, 89, 23);
		JLabel lblWelcome = new JLabel("Welcome Dr." + doctorName + "!");
		topJPanel.add(lblWelcome);
		lblWelcome.setBounds(89, 25, 248, 51);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 20));

		lblAdd.setBounds(0, 91, 136, 14);
		topJPanel.add(lblAdd);
		lblPatientID.setBounds(138, 91, 119, 14);

		topJPanel.add(lblPatientID);
		lblPatientID.setText(patientName);

		// menu panel items
		menuJPanel.setLayout(null);
		menuJPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		menuJPanel.setBounds(10, 123, 386, 349);
		lblMedicine.setBounds(21, 11, 94, 14);

		menuJPanel.add(lblMedicine);
		medicineList.setBounds(21, 54, 355, 240);

		menuJPanel.add(medicineList);

		txtMedicineId = new JTextField();
		txtMedicineId.setBounds(125, 8, 139, 20);
		menuJPanel.add(txtMedicineId);
		txtMedicineId.setColumns(10);

		JButton btnAddPrescritpion = new JButton("Add");
		btnAddPrescritpion.setBounds(176, 32, 89, 23);
		menuJPanel.add(btnAddPrescritpion);

		btnSubmitPrescription.setBounds(62, 307, 266, 31);
		menuJPanel.add(btnSubmitPrescription);

		JLabel lbQuantity = new JLabel("Quantity:");
		lbQuantity.setBounds(21, 36, 73, 14);
		menuJPanel.add(lbQuantity);

		txtQuantity = new JTextField();
		txtQuantity.setColumns(10);
		txtQuantity.setBounds(125, 33, 41, 20);
		menuJPanel.add(txtQuantity);

		// ArrayList<String> prescriptArrayList = new ArrayList<String>();
		// prescriptArrayList = new ArrayList<>();

		columnName = new String[] { "Medicine Name", "Quantity" };
		model = new DefaultTableModel(null, columnName);
		// medicineListTable.setModel(new DefaultTableModel(new Object[][] {}, new
		// String[] { "Medicine Name", "Quantity" }));

		medicineListTable = new JTable(model) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		medicineListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		medicineList.setViewportView(medicineListTable);
		medicineListTable.getTableHeader().setReorderingAllowed(false);

		btnDeletePrescription.setBounds(267, 32, 89, 23);
		menuJPanel.add(btnDeletePrescription);

		btnAddPrescritpion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRow();

			}
		});

		btnDeletePrescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// delete row
				if (medicineListTable.getSelectedRow() != -1) {
					deleteRow();
				} else {
					// no rows selected
					JOptionPane.showMessageDialog(btnDeletePrescription, "Please select a row to delete.");
				}

			}

		});

		btnSubmitPrescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createPrescription(loginId, patientId);
			}
		});

		btnBack.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					dispose();
					new DoctorPageUi(loginId, doctorName);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex, "Error.", JOptionPane.WARNING_MESSAGE);
				}
			}

		});
		setVisible(true);

	}

	public void addRow() {
		DoctorAddPrescriptionController doctorAddPrescriptionController = new DoctorAddPrescriptionController();
		Boolean success = false;
		String medicineName = txtMedicineId.getText();
		String medicineQuantity = txtQuantity.getText();

		try {
			if (txtMedicineId.getText().isEmpty() || txtQuantity.getText().isEmpty()
					|| Integer.parseInt(medicineQuantity) < 1) {
				JOptionPane.showMessageDialog(btnDeletePrescription, "Invalid Input.");

			} else {
				int i = Integer.parseInt(medicineQuantity);
				if (doctorAddPrescriptionController.validateMedicine(medicineName) != false) {
					success = true;
				} else {
					medDoesNotExsit();
				}

			}
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(btnDeletePrescription, "Please input a number.");
			success = false;

		}
		if (success) {
			String data[] = { medicineName, medicineQuantity };
			model.addRow(data);
			txtMedicineId.setText("");
			txtQuantity.setText("");
		}

	}

	public void deleteRow() {
		model.removeRow(medicineListTable.getSelectedRow());
	}

	public void medDoesNotExsit() {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
		optionPane.setMessage("Insert fail, medicine does not exisit.");
		dialog = optionPane.createDialog(null, "Medicine does not exsit.");
		dialog.setVisible(true);

	}

	public void failToSubmit() {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
		optionPane.setMessage("Submission of prescription failed.");
		dialog = optionPane.createDialog(null, "error.");
		dialog.setVisible(true);

	}

	public void createPrescription(String loginId, String patientId) {
		DoctorAddPrescriptionController doctorAddPrescriptionController = new DoctorAddPrescriptionController();
		String medicineNameData = "";
		String medicineQtyData = "";
		boolean success = false;
		boolean medicineExisit = false;
		try {
			prescriptionId = doctorAddPrescriptionController.addNewPrescription(loginId, patientId);
			if (prescriptionId != "failed") {
				for (int i = 0; i < medicineListTable.getRowCount(); i++) { // Loop through the rows

					medicineNameData = medicineListTable.getModel().getValueAt(i, 0).toString();
					medicineQtyData = medicineListTable.getModel().getValueAt(i, 1).toString();

					// checks if prescriptionInfo already have the same medicine.
					medicineExisit = doctorAddPrescriptionController
							.doesMedicineAlreadyExistInPrescription(prescriptionId, medicineNameData);
					if (medicineExisit != true) {
						// adds medicine name and qty into the prescriptionInfo table.
						success = doctorAddPrescriptionController.addPrescriptionInfo(prescriptionId, medicineNameData,
								medicineQtyData);

					} else {

						doctorAddPrescriptionController.addMoreToMedicineQty(prescriptionId, medicineNameData,
								medicineQtyData);

					}

				}
				if (success) {
					sendToken(patientId, prescriptionId);

				} else {
					failToSubmit();

				}

			}

		}

		catch (Exception e2) {
			System.err.println(e2);
		}

	}

	public void submissionSuccess(String prescriptionId) {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
		optionPane.setMessage(
				"Prescription " + prescriptionId + " successful added. A token has been sent to the patient.");
		dialog = optionPane.createDialog(null, "Prescription insertion successful.");
		dialog.setVisible(true);
		model.setRowCount(0);

	}
	
	//used to send email to the patient
	public void sendToken(String patientId, String prescriptionId) {
		DoctorAddPrescriptionController doctorAddPrescriptionController = new DoctorAddPrescriptionController();
		String email = doctorAddPrescriptionController.getEmail(patientId);
		doctorAddPrescriptionController.sendEmail(email, prescriptionId);
		submissionSuccess(prescriptionId);

	}

}