package main.SquidDevs.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {

	// Private Fields
	private String userId;
	private String password;
	private String name;
	private String job;
	private String email;
	private String contactNo;

	// Default Constructor
	public User() {

	}

	// Other Constructor
	public User(String userId, String password, String name, String job, String email, String contactNo) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.job = job;
		this.email = email;
		this.contactNo = contactNo;
	}

	public User(String userId) {
		this.userId = userId;
		setUser();
	}

	// Accessor Methods
	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getJob() {
		return job;
	}

	public String getEmail() {
		return email;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setUser() {
		Connection conn = dbConnection();
		String query = "SELECT [user].[id], [user].[password], [user].[name], [user].[job], [user].[email], [user].[contactNo] "
				+ "FROM [user] " + "WHERE [user].[id] = '" + userId + "';";
		try (Statement st = conn.createStatement()) {
			ResultSet set = st.executeQuery(query);
			while (set.next()) {

				this.password = set.getString("password");
				this.name = set.getString("name");
				this.job = set.getString("job");
				this.email = set.getString("email");
				this.contactNo = String.valueOf(set.getInt("contactNo"));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public boolean doesUserExist(String userId, String password, String job) {
		Connection conn = dbConnection();
		String query = "SELECT [id] FROM [dbo].[user] WHERE [id] = '" + userId + "' AND [password] = '" + password
				+ "' AND [job] = '" + job + "'";
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

	public boolean isIdInDatabase(String id) {
		boolean nameExists = false;

		Connection conn = dbConnection();
		String query = "SELECT * FROM [dbo].[user] WHERE [id] = '" + id + "'";
		try (Statement st = conn.createStatement()) {
			st.executeQuery(query);
			nameExists = true;

		} catch (SQLException e) {
			nameExists = false;
			System.out.println(e);
		}
		return nameExists;
	}

	public String getName(String user_id, String password, String job) {

		String name = "";
		Connection conn = dbConnection();
		String query = "SELECT [name] FROM [dbo].[user] WHERE [id] = '" + user_id + "' AND [password] = '" + password
				+ "' AND [job] = '" + job + "'";
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

	public boolean createUser(String userId, String password, String name, String job, String email, String contactNo) {
		Connection conn = dbConnection();
		String query = "INSERT INTO [user] (id, password, name, job, email, contactNo) VALUES ('" + userId + "', '"
				+ password + "', '" + name + "', '" + job + "', '" + email + "', '" + contactNo + "')";
		try (Statement st = conn.createStatement()) {
			int rowsAffected = st.executeUpdate(query);
			// check if insert successful
			if (rowsAffected > 0) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			//e.printStackTrace();

		}
		return false;

	}

	public ArrayList<User> getUsersBySearch(String id) {
		ArrayList<User> userList = new ArrayList<>();
		Connection conn = dbConnection();
		String query = "SELECT * FROM [dbo].[user] WHERE [id] = '" + id + "'";
		try (Statement st = conn.createStatement()) {
			ResultSet set = st.executeQuery(query);
			while (set.next()) {
				String userId = set.getString("id");
				String password = set.getString("password");
				String name = set.getString("name");
				String job = set.getString("job");
				String email = set.getString("email");
				String contactNo = set.getString("contactNo");

				User user = new User(userId, password, name, job, email, contactNo);
				userList.add(user);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return userList;
	}

	public boolean deleteUserUsingUserId(String userId) {
		boolean deleted = false;

		Connection conn = dbConnection();
		String query = "DELETE FROM [prescriptionInfo] "
				+ "WHERE [prescriptionId] IN (SELECT [prescriptionId] FROM [prescription] WHERE " + "[doctorId] = '"
				+ userId + "' OR [patientId] = '" + userId + "' or [pharmacistId] = '" + userId + "');\r\n" + "\r\n"
				+ "DELETE FROM [prescription] WHERE [doctorId] = '" + userId + "' OR [patientId] = '" + userId
				+ "' or [pharmacistId] = '" + userId + "';\r\n" + "\r\n" + "DELETE FROM [user] WHERE [id] = '" + userId
				+ "';";
		try (Statement st = conn.createStatement()) {
			st.executeUpdate(query);
			deleted = true;

		} catch (SQLException e) {
			deleted = false;
			System.out.println(e);
		}
		return deleted;
	}

	public String getEmail(String user_id) {

		String email = "";
		Connection conn = dbConnection();
		String query = "SELECT [email] FROM [dbo].[user] WHERE [id] = '" + user_id + "'";
		try (Statement st = conn.createStatement()) {
			ResultSet set = st.executeQuery(query);
			while (set.next()) {
				email = set.getString("email");
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return email;
	}

	public ArrayList<User> getAllUsers() {
		ArrayList<User> userList = new ArrayList<>();
		Connection conn = dbConnection();
		String query = "SELECT * FROM [dbo].[user]";
		try (Statement st = conn.createStatement()) {
			ResultSet set = st.executeQuery(query);
			while (set.next()) {
				String userId = set.getString("id");
				String password = set.getString("password");
				String name = set.getString("name");
				String job = set.getString("job");
				String email = set.getString("email");
				String contactNo = set.getString("contactNo");

				User user = new User(userId, password, name, job, email, contactNo);
				userList.add(user);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return userList;
	}

	public boolean updateUserUsingUserId(String userId, String password, String name, String selectedJob, String email,
			int contactNo) {
		boolean updated = false;

		Connection conn = dbConnection();
		String query = "UPDATE [dbo].[user]\r\n" + "					SET \r\n" + "					[password] = '"
				+ password + "',\r\n" + "					[name] = '" + name + "',\r\n"
				+ "					[job] = '" + selectedJob + "',\r\n" + "					[email] = '" + email
				+ "',\r\n" + "					[contactNo] = '" + contactNo + "'\r\n"
				+ "					WHERE [id] = '" + userId + "';\r\n";

		try (Statement st = conn.createStatement()) {
			st.executeUpdate(query);
			updated = true;

		} catch (SQLException e) {
			updated = false;
			System.out.println(e);
		}
		return updated;
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
