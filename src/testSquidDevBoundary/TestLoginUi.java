package testSquidDevBoundary;

import main.SquidDevs.boundary.LoginPageUi;
import static org.junit.Assert.assertEquals;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class TestLoginUi extends JFrame {
	private LoginPageUi loginPageUi;
	
	@Before
	public void start() throws Exception {
		this.loginPageUi = new LoginPageUi();
	}

	@After
	public void end() throws Exception {
		this.loginPageUi.dispose();
	}

	@Test
	public void testLoginFailWrongId() {
		try {
			loginPageUi.isTest = true;
			Thread.sleep(1000);
			loginPageUi.LoginIdField.setText("notAdmin");
			Thread.sleep(1000);
			loginPageUi.passwordField.setText("notAdmin");
			Thread.sleep(1000);
			loginPageUi.rdbtnAdmin.setSelected(true);
			Thread.sleep(1000);
			loginPageUi.btnLogin.doClick();
			Thread.sleep(1000);
			//expected result is null as user does not exist thus name could not be found.
			assertEquals("testLoginFailWrongId", null, loginPageUi.nameString);
			Thread.sleep(1000);
			loginPageUi.dispose();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLoginSuccess() {
		try {
			loginPageUi.isTest = true;
			Thread.sleep(1000);
			loginPageUi.LoginIdField.setText("admin");
			Thread.sleep(1000);
			loginPageUi.passwordField.setText("admin");
			Thread.sleep(1000);
			loginPageUi.rdbtnAdmin.setSelected(true);
			Thread.sleep(1000);
			loginPageUi.btnLogin.doClick();
			Thread.sleep(1000);
			//expected result is kenneth chua as userId: admin name is kenneth chua
			assertEquals("loginSuccess", "kenneth chua", loginPageUi.nameString);
			Thread.sleep(1000);
			loginPageUi.dispose();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
