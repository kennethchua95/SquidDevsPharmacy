package main.SquidDevs.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Patient extends User {
	// default constructor
	public Patient() {
		super();
	}

	public Patient(String userid) {
		super(userid);
	}

	// insert new id into Patient Table
	public boolean insertNewPatient(String userId) {
		Connection conn = dbConnection();
		String query = "INSERT INTO [patient] (id) VALUES ('" + userId + "')";
		try (Statement st = conn.createStatement()) {
			int rowsAffected = st.executeUpdate(query);
			if (rowsAffected > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean doesPatientExsit(String userId) {
		Connection conn = dbConnection();
		String query = "SELECT [id] FROM [dbo].[user] WHERE [id] = '" + userId + "' AND [job] = 'patient'";
		try (Statement st = conn.createStatement()) {
			ResultSet result = st.executeQuery(query);
			if (result.next() == true) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	public String getName(String patientId) {

		String name = "";
		Connection conn = dbConnection();
		String query = "SELECT [name] FROM [user] WHERE [id] ='" + patientId + "'";
		try (Statement st = conn.createStatement()) {
			ResultSet set = st.executeQuery(query);
			while (set.next()) {
				name = set.getString("name");
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return name;
	}

	// Set up DB Connection
	private Connection dbConnection() {

		String connectionUrl = "jdbc:sqlserver://localhost;databaseName=SquidDev;";
		String username = "sa";
		String password = "a";
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(connectionUrl, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Not Connected!");
		}
		return connection;
	}

}