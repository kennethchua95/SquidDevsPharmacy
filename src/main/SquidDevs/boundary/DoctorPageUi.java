package main.SquidDevs.boundary;

import main.SquidDevs.controller.DoctorPageController;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JTextField;

import javax.swing.JDialog;

public class DoctorPageUi extends JFrame {

	private JPanel topJPanel = new JPanel();
	private JPanel menuJPanel = new JPanel();
	private JButton btnLogout = new JButton("Log Out");
	private JButton btnAddNewPresciption = new JButton("");
	private final JButton btnViewPatientPresciption = new JButton("");
	private final JLabel lbViewPastPresciption = new JLabel("View / Update presciption ");
	private final JButton btnCheck = new JButton("Check");
	private final JTextField txtPatientId = new JTextField();
	private final JLabel lblPatientID = new JLabel("Input patient ID:");
	private String patientName;
	private String patientid;
	static public JDialog dialog;

	public DoctorPageUi(String loginId, String doctorName) {
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
		JLabel lblWelcome = new JLabel("Welcome Dr." + doctorName + "!");
		topJPanel.add(lblWelcome);
		lblWelcome.setBounds(51, 23, 248, 51);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 20));

		btnCheck.setBounds(229, 68, 70, 23);

		topJPanel.add(btnCheck);
		txtPatientId.setColumns(10);
		txtPatientId.setBounds(118, 69, 103, 20);

		topJPanel.add(txtPatientId);
		lblPatientID.setBounds(21, 72, 111, 14);

		topJPanel.add(lblPatientID);

		// menu panel items
		menuJPanel.setLayout(null);
		menuJPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		menuJPanel.setBounds(10, 123, 309, 324);

		JLabel lblAddPrescription = new JLabel("Add new presciption");
		lblAddPrescription.setBounds(173, 23, 143, 14);
		menuJPanel.add(lblAddPrescription);
		

		Image img = new ImageIcon(getClass().getResource("/pill.jpg")).getImage();
		btnAddNewPresciption.setIcon(new ImageIcon(img));
		btnAddNewPresciption.setBounds(187, 38, 101, 80);
		btnAddNewPresciption.disable();
		menuJPanel.add(btnAddNewPresciption);

		Image img2 = new ImageIcon(getClass().getResource("/viewPx.jpg")).getImage();
		btnViewPatientPresciption.setIcon(new ImageIcon(img2));
		btnViewPatientPresciption.setBounds(21, 38, 101, 80);
		menuJPanel.add(btnViewPatientPresciption);
		lbViewPastPresciption.setBounds(10, 23, 153, 14);
		
		btnViewPatientPresciption.setEnabled(false);
		btnAddNewPresciption.setEnabled(false);

		menuJPanel.add(lbViewPastPresciption);

		DoctorPageController doctorPageController = new DoctorPageController();

		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					patientid = txtPatientId.getText();
					if (doctorPageController.patientExist(patientid) != false) {
						patientName = doctorPageController.getPatientName(patientid);
						checkSuccess();
					}

					else {
						checkFail();
						
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		

		
		btnViewPatientPresciption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new DoctorViewPrescriptionUi(loginId, doctorName, patientid, patientName);
					setVisible(false);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex, "Please input patient's ID and click check first.",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});

		btnAddNewPresciption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new DoctorAddPrescriptionUi(loginId, doctorName, patientid, patientName);
					setVisible(false);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex, "Please input patient's ID and click check first.",
							JOptionPane.WARNING_MESSAGE);
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
	
	public void checkSuccess() {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
		optionPane.setMessage("Patient records accessed.");
		dialog = optionPane.createDialog(null, "Success!");
		dialog.setVisible(true);
		btnViewPatientPresciption.setEnabled(true);
		btnAddNewPresciption.setEnabled(true);

	}

	public void checkFail() {
		JOptionPane optionPane = new JOptionPane();
		optionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
		optionPane.setMessage("Patient ID does not exist.");
		dialog = optionPane.createDialog(null, "check failed");
		dialog.setVisible(true);
		btnViewPatientPresciption.setEnabled(false);
		btnAddNewPresciption.setEnabled(false);

	}

}