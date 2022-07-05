package main.SquidDevs.boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import main.SquidDevs.controller.PatientPrescriptionController;

import javax.swing.border.Border;

public class PatientPrescriptionUi extends JFrame {

	private JPanel topJPanel = new JPanel();
	private JPanel menuJPanel = new JPanel();
	private JButton btnReturn = new JButton("Return");
	private JTable prescription_table;
	DefaultTableModel model;
	
	public PatientPrescriptionUi(String patientId, String prescriptionID, String name, String doctorID, String date) {
		getContentPane().setBackground(new Color(0, 255, 255));

		setResizable(false);
		setTitle("SquidDevs Pharmarcy");
		setBounds(100, 100, 400, 400);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(topJPanel);
		getContentPane().add(menuJPanel);
		
		PatientPrescriptionController patientPrescriptionController = new PatientPrescriptionController();
		String DoctorName = patientPrescriptionController.getDoctorName(doctorID);
		// top panel items
		topJPanel.setLayout(null);
		topJPanel.setBounds(10, 11, 364, 101);
		topJPanel.add(btnReturn);
		btnReturn.setBounds(0, 0, 89, 23);
		JLabel lblPrescriptionNo = new JLabel("Prescription No. " + prescriptionID);
		topJPanel.add(lblPrescriptionNo);
		lblPrescriptionNo.setBounds(55, 10, 248, 64);
		lblPrescriptionNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JLabel lblDoctor = new JLabel("Prescribed by: " + DoctorName);
		topJPanel.add(lblDoctor);
		lblDoctor.setBounds(55, 27, 248, 64);
		lblDoctor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JLabel lblDate = new JLabel("Date Issued: " + date);
		topJPanel.add(lblDate);
		lblDate.setBounds(55, 44, 248, 64);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 16));

		// menu panel items
		menuJPanel.setLayout(null);
		menuJPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		menuJPanel.setBounds(10, 123, 364, 227);
		/*JLabel lblMedsName = new JLabel("  Medicine");
		JLabel lblQuantity = new JLabel("  Quantity");
		JLabel lblCollected = new JLabel("  Collected");
		menuJPanel.add(lblMedsName);
		menuJPanel.add(lblQuantity);
		menuJPanel.add(lblCollected);
		lblMedsName.setBounds(0, 0, 180, 30);
		lblMedsName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMedsName.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblQuantity.setBounds(180, 0, 100, 30);
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblQuantity.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblCollected.setBounds(280, 0, 100, 30);
		lblCollected.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCollected.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		*/
		//
		JScrollPane prescription_scrollPane = new JScrollPane();
		prescription_scrollPane.setBounds(0, 0, 364, 227);
		menuJPanel.add(prescription_scrollPane);
		
		prescription_table = new JTable();
		String[] column = { "Medicine Name", "Quantity"};
		Object[] row = new Object[2];

		ArrayList<ArrayList<String>> pre_pList = patientPrescriptionController.getMedicine(prescriptionID);
		
		prescription_table.setDefaultEditor(Object.class, null);
		prescription_scrollPane.setViewportView(prescription_table);
		model = new DefaultTableModel();
		model.setColumnIdentifiers(column);
		prescription_table.setModel(model);
		model = (DefaultTableModel) prescription_table.getModel();
		prescription_table.getTableHeader().setReorderingAllowed(false);

		getPrescriptionTable(pre_pList, row);
		//

		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// return to patientui
					dispose();
					new PatientPageUi(patientId, name);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex, "Logout failed.", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		setVisible(true);

	}
	public void getPrescriptionTable(ArrayList<ArrayList<String>> pre_pList, Object[] row) {
		for (int i = 0; i < pre_pList.size(); i++) {
			
			for (int j = 0; j < 2; j++) {
				String test = pre_pList.get(i).get(j + 1);
				row[j] = test;
			}
			model.addRow(row);
		}
	}
}