package main.SquidDevs.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Medicine {

	public boolean doesMedicineExist(String medicineName) {
		Connection conn = dbConnection();
		String query = "SELECT [medicineName] FROM [dbo].[medicine] WHERE [medicineName] = '" + medicineName + "'";
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
