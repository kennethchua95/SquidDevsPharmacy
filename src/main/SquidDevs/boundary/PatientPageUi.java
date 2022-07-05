package main.SquidDevs.boundary;

//import javax.security.auth.login.FailedLoginException;
import javax.swing.JButton;
//import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.io.StreamCorruptedException;
import java.util.ArrayList;
//import java.util.Arrays;

import javax.swing.border.MatteBorder;
import java.awt.Color;
//import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
//import javax.swing.JList;
import javax.swing.table.DefaultTableModel;
//import javax.swing.DefaultRowSorter;
//import javax.swing.border.MatteBorder;
//import javax.swing.event.RowSorterEvent;
//import javax.swing.event.RowSorterListener;
//import javax.swing.table.TableModel;
//import javax.swing.table.TableRowSorter;

import main.SquidDevs.controller.PatientPrescriptionController;

//import javax.swing.ScrollPaneConstants;
//import javax.swing.ListSelectionModel;

public class PatientPageUi extends JFrame {

	private JPanel topJPanel = new JPanel();
	private JPanel menuJPanel = new JPanel();
	private JButton btnLogout = new JButton("Log Out");
	private JTable patient_table;
	DefaultTableModel model;

	public PatientPageUi(String loginId, String name) {
		getContentPane().setBackground(new Color(0, 255, 255));

		setResizable(false);
		setTitle("SquidDevs Pharmarcy");
		setBounds(100, 100, 400, 400);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(topJPanel);
		getContentPane().add(menuJPanel);

		// top panel items
		topJPanel.setLayout(null);
		topJPanel.setBounds(10, 11, 364, 101);
		topJPanel.add(btnLogout);
		btnLogout.setBounds(0, 0, 89, 23);
		JLabel lblWelcome = new JLabel("Welcome " + name + "!");
		topJPanel.add(lblWelcome);
		lblWelcome.setBounds(55, 27, 248, 64);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 20));

		// menu panel items
		menuJPanel.setLayout(null);
		menuJPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		menuJPanel.setBounds(10, 123, 364, 227);
		
		JScrollPane patient_scrollPane = new JScrollPane();
		patient_scrollPane.setBounds(0, 0, 364, 227);
		menuJPanel.add(patient_scrollPane);
		
		patient_table = new JTable();
		String[] column = { "Prescription ID", "Date", "Collection Status"};
		Object[] row = new Object[3];
		
		PatientPrescriptionController patientPrescriptionController = new PatientPrescriptionController();
		ArrayList<ArrayList<String>> pre_pList = patientPrescriptionController.getPrescription(loginId);


		patient_table.setDefaultEditor(Object.class, null);
		patient_scrollPane.setViewportView(patient_table);
		model = new DefaultTableModel();
		model.setColumnIdentifiers(column);
		patient_table.setModel(model);
		model = (DefaultTableModel) patient_table.getModel();
		patient_table.getTableHeader().setReorderingAllowed(false);

		getPrescriptionTable(pre_pList, row);
		
		patient_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int getRow = patient_table.getSelectedRow();
				String prescriptionId = patient_table.getModel().getValueAt(getRow, 0).toString();
				String date = patient_table.getModel().getValueAt(getRow, 1).toString();
				String doctorID = pre_pList.get(getRow).get(3);
				try {
					new PatientPrescriptionUi(loginId, prescriptionId, name, doctorID, date);
					setVisible(false);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex, "Error.", JOptionPane.WARNING_MESSAGE);
				}
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
	
	public void getPrescriptionTable(ArrayList<ArrayList<String>> pre_pList, Object[] row) {
		for (int i = 0; i < pre_pList.size(); i++) {
			
			for (int j = 0; j < 3; j++) {
				String test = pre_pList.get(i).get(j);
				row[j] = test;
			}
			model.addRow(row);
		}
	}
}