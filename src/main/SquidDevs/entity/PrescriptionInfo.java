package main.SquidDevs.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PrescriptionInfo {

	private String prescriptionId;
	private String medicineName;
	private String medicineQty;

	// Default Constructor
	public PrescriptionInfo() {

	}

	// Other Constructor

	public PrescriptionInfo(String presriptionId, String medicineName, String medicineQty) {
		this.prescriptionId = presriptionId;
		this.medicineName = medicineName;
		this.medicineQty = medicineQty;
	}
	// Accessor Methods

	public String getPrescriptionId() {
		return prescriptionId;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public String getMedicineQty() {
		return medicineQty;
	}

	public boolean addPrescriptionInfo(String prescriptionId, String medicineName, String medicineQty) {
		Connection conn = dbConnection();
		String query = "INSERT INTO [prescriptioninfo] (prescriptionId, medicineName, medicineQty) VALUES (?,?,?)";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, prescriptionId);
			ps.setString(2, medicineName);
			ps.setString(3, medicineQty);
			ps.addBatch();
			if (ps.executeUpdate() > 0) {
				ps.close();
				conn.close();
				return true;
			} else {
				conn.close();
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean updatePrescriptionInfoUsingPrescriptionId(int p_id, String old_medName, String new_medName,
			int medQty, String date) {
		boolean updated = false;

		Connection conn = dbConnection();
		String query = "UPDATE [dbo].[prescriptionInfo]" + "SET" + "[medicineName] = '" + new_medName + "',"
				+ "[medicineQty] = '" + medQty + "'" + "WHERE [prescriptionId] = '" + p_id + "' AND [medicineName] = '"
				+ old_medName + "';" + "\r\n" + "UPDATE [dbo].[prescription]" + "SET" + "[date] = '" + date + "'"
				+ "WHERE [prescriptionId] = '" + p_id + "';";

		try (Statement st = conn.createStatement()) {
			st.executeUpdate(query);
			updated = true;

		} catch (SQLException e) {
			updated = false;
			System.out.println(e);
		}
		return updated;
	}

	public boolean checkIfMedicineInPrescription(String prescriptionId, String medicineName) {

		Connection conn = dbConnection();
		String query = "SELECT [prescriptionId] ,[medicineQty] FROM [dbo].[prescriptionInfo] "
				+ "WHERE [prescriptionId] = '" + prescriptionId + "' AND [medicineName] ='" + medicineName + "'";
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

	public void addToMedicineQty(String prescriptionId, String medicineName, String medicineQty) {

		int medicineQtyInt = Integer.parseInt(medicineQty);
		Connection conn = dbConnection();
		String query = "UPDATE [prescriptionInfo] SET [medicineQty] = [medicineQty]+ '" + medicineQty
				+ "'  WHERE [prescriptionId] = '" + prescriptionId + "' AND [medicineName] ='" + medicineName + "'";
		try (Statement st = conn.createStatement()) {
			st.executeQuery(query);

		} catch (SQLException e) {
			// System.out.println(e);
		}
	}

	public boolean deletePrescriptionInfoUsingPrescriptionIdAndMedicineName(int p_id, String medicineName) {
		boolean deleted = false;

		Connection conn = dbConnection();
		String query = "DELETE FROM [dbo].[prescriptionInfo]" + "WHERE [prescriptionId] = " + p_id + ""
				+ "AND [medicineName] = '" + medicineName + "';";
		try (Statement st = conn.createStatement()) {
			st.executeUpdate(query);
			deleted = true;

		} catch (SQLException e) {
			deleted = false;
			System.out.println(e);
		}
		return deleted;
	}

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

	public ArrayList<PrescriptionInfo> getMedicineFromPrescriptionId(String prescriptionId) {
		ArrayList<PrescriptionInfo> p_list = new ArrayList<PrescriptionInfo>();

		Connection conn = dbConnection();
		String query = "SELECT [prescriptionInfo].[prescriptionId], [prescriptionInfo].[MedicineName], [prescriptionInfo].[medicineQty] "
				+ "FROM [prescriptionInfo] " + "WHERE [prescriptionInfo].[prescriptionId] = '" + prescriptionId + "';";
		try (Statement st = conn.createStatement()) {
			ResultSet set = st.executeQuery(query);
			while (set.next()) {

				String p_id = String.valueOf(set.getInt("prescriptionId"));
				String p_medicineName = set.getString("medicineName");
				String p_quantity = String.valueOf(set.getInt("medicineQty"));

				PrescriptionInfo prescription = new PrescriptionInfo(p_id, p_medicineName, p_quantity);

				p_list.add(prescription);

			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return p_list;
	}
}
