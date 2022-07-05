package main.SquidDevs.boundary;

import javax.management.loading.PrivateClassLoader;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;
import java.awt.Color;

public class AdminPageUi extends JFrame {

	private JPanel topJPanel = new JPanel();
	private JPanel menuJPanel = new JPanel();
	private JButton btnLogout = new JButton("Log Out");
	private JLabel lblAddNewUser = new JLabel("Add a new user");
	private JButton btnAddNewUser = new JButton("");
	private JLabel lblEditUserAccount = new JLabel("Edit exisiting user");
	private JButton btnEditUserAccount = new JButton("");

	public AdminPageUi(String loginId, String name) {
		getContentPane().setBackground(new Color(0, 255, 255));
		setResizable(false);
		setTitle("SquidDevs Pharmarcy");
		setBounds(100, 100, 345, 497);
		getContentPane().setLayout(null);
		getContentPane().add(topJPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// top panel items
		topJPanel.setLayout(null);
		topJPanel.add(btnLogout);
		topJPanel.setBounds(10, 11, 309, 102);
		btnLogout.setBounds(0, 0, 89, 23);

		JLabel lblWelcome = new JLabel("Welcome " + name + "!");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblWelcome.setBounds(43, 27, 248, 64);
		topJPanel.add(lblWelcome);

		// menu panel items
		menuJPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		menuJPanel.setBounds(10, 123, 309, 324);
		getContentPane().add(menuJPanel);
		menuJPanel.setLayout(null);
		menuJPanel.add(lblAddNewUser);
		menuJPanel.add(btnAddNewUser);
		menuJPanel.add(lblEditUserAccount);

		Image img2 = new ImageIcon(getClass().getResource("/edituser.jpg")).getImage();
		btnEditUserAccount.setIcon(new ImageIcon(img2));
		btnEditUserAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				AdminEditUserUi adminEditUserUi = new AdminEditUserUi(loginId, name);
			}
		});

		menuJPanel.add(btnEditUserAccount);
		lblAddNewUser.setBounds(20, 21, 91, 14);
		lblEditUserAccount.setBounds(157, 21, 106, 14);

		// please add Images to source file in build path
		btnAddNewUser.setBounds(30, 37, 101, 80);
		Image img = new ImageIcon(getClass().getResource("/adduser.jpg")).getImage();
		btnAddNewUser.setIcon(new ImageIcon(img));
		btnEditUserAccount.setBounds(167, 37, 101, 80);

		// buttons action listener
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

		btnAddNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				AdminAddUserUi adminAddUserUi = new AdminAddUserUi(loginId, name);
			}
		});

		setVisible(true);

	}
}