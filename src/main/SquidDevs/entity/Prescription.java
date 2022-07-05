package main.SquidDevs.entity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Prescription {

	// Private Fields
	private String prescriptionId;
	private String doctorId;
	private String doctorName;
	private String patientId;
	private String phamacistId;
	private String medicineId;
	private String date;
	private String isCollected;
	private String medicineName;
	private String medicineQty;

	// Default Constructor
	public Prescription() {

	}

	// Other Constructor

	public Prescription(String presriptionId, String doctorName, String medicineName, String medicineQty, String date,
			String isCollected) {
		this.prescriptionId = presriptionId;
		this.doctorName = doctorName;
		this.medicineName = medicineName;
		this.medicineQty = medicineQty;
		this.date = date;
		this.isCollected = isCollected;
	}

	public Prescription(String p_id, String p_doctorId, String p_date, String isCollectedString) {
		this.prescriptionId = p_id;
		this.doctorId = p_doctorId;
		this.date = p_date;
		this.isCollected = isCollectedString;
	}
	// Accessor Methods

	public String getPrescriptionId() {
		return prescriptionId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public String getMedicineQty() {
		return medicineQty;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public String getPatientId() {
		return patientId;
	}

	public String getPharmacistId() {
		return phamacistId;
	}

	public String getMedicineId() {
		return medicineId;
	}

	public String getDate() {
		return date;
	}

	public String getIsCollected() {
		return isCollected;
	}

	// insert into prescription table and returns prescriptionId.
	public String addPrescription(String doctorId, String patientId) {
		Connection conn = dbConnection();
		String query = "INSERT INTO [prescription] (doctorId, patientId, date, isCollected) VALUES (?,?,?,'False')";
		ResultSet rs = null;
		String prescriptionId = "";
		try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			java.sql.Date javaDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			String date = javaDate.toString();
			ps.setString(1, doctorId);
			ps.setString(2, patientId);
			ps.setString(3, date);

			ps.addBatch();
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			int newPrescriptionId = 0;

			if (rs.next()) {
				newPrescriptionId = (int) rs.getInt(1);
				prescriptionId = String.valueOf(newPrescriptionId);

				return prescriptionId;
			}

		} catch (Exception e) {

			return null;
		}
		return null;

	}

	public ArrayList<Prescription> getPrescriptionFromPatientId(String patientId) {
		ArrayList<Prescription> p_list = new ArrayList<Prescription>();

		Connection conn = dbConnection();
		String query = "SELECT [prescriptionInfo].[prescriptionId], [user].[name], [prescriptionInfo].[medicineName], [prescriptionInfo].[medicineQty], [prescription].[date], [prescription].[isCollected] "
				+ "FROM [prescription] "
				+ "INNER JOIN [prescriptionInfo] ON [prescription].[prescriptionId] = [prescriptionInfo].[prescriptionId] "
				+ "INNER JOIN [user] ON [user].[id] = [prescription].[doctorId]"
				+ "WHERE [prescription].[patientId] = '" + patientId + "';";
		try (Statement st = conn.createStatement()) {
			ResultSet set = st.executeQuery(query);
			while (set.next()) {

				String p_id = String.valueOf(set.getInt("prescriptionId"));
				String p_doctorName = set.getString("name");
				String p_medicineName = set.getString("medicineName");
				String p_quantity = String.valueOf(set.getInt("medicineQty"));
				String p_date = String.valueOf(set.getString("date"));
				int isCollectedInt = set.getInt("isCollected");
				String isCollectedString = "";
				if (isCollectedInt == 0) {
					isCollectedString = "Not Collected";
				} else {
					isCollectedString = "Collected";
				}

				Prescription prescription = new Prescription(p_id, p_doctorName, p_medicineName, p_quantity, p_date,
						isCollectedString);

				p_list.add(prescription);

			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return p_list;
	}

	public boolean deletePrescriptionUsingPrescriptionInfoId() {
		boolean deleted = false;

		Connection conn = dbConnection();
		String query = "DELETE FROM [dbo].[prescription] WHERE [prescriptionId] NOT IN (SELECT [prescriptionId] FROM [prescriptionInfo]);";
		try (Statement st = conn.createStatement()) {
			st.executeUpdate(query);
			deleted = true;

		} catch (SQLException e) {
			deleted = false;
			System.out.println(e);
		}
		return deleted;
	}

	public ArrayList<Prescription> getUniquePrescription(String patientId) {
		ArrayList<Prescription> p_list = new ArrayList<Prescription>();

		Connection conn = dbConnection();
		String query = "SELECT [prescription].[prescriptionId], [prescription].[doctorId], [prescription].[date], [prescription].[isCollected] "
				+ "FROM [prescription] " + "WHERE [prescription].[patientId] = '" + patientId + "';";
		try (Statement st = conn.createStatement()) {
			ResultSet set = st.executeQuery(query);
			while (set.next()) {

				String p_id = String.valueOf(set.getInt("prescriptionId"));
				String p_doctorId = set.getString("doctorId");
				String p_date = String.valueOf(set.getString("date"));
				int isCollectedInt = set.getInt("isCollected");
				String isCollectedString = "";
				if (isCollectedInt == 0) {
					isCollectedString = "Not Collected";
				} else {
					isCollectedString = "Collected";
				}

				Prescription prescription = new Prescription(p_id, p_doctorId, p_date, isCollectedString);

				p_list.add(prescription);

			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return p_list;
	}

	public boolean checkIfCollected(String prescriptionId) {
		// default set as collected
		int isCollected = 1;
		Connection conn = dbConnection();
		String query = "SELECT [prescription].[isCollected] FROM [prescription] WHERE [prescription].[prescriptionId] ='"
				+ prescriptionId + "'";
		try (Statement st = conn.createStatement()) {
			ResultSet result = st.executeQuery(query);

			if (result.next() == true) {
				isCollected = result.getInt("isCollected");
				if(isCollected == 0) {
					return false;
				}
				else {
					
					return true;
				}
				
			}

		} catch (SQLException e) {
			System.out.println(e);
		}
		return true;

	}

	public boolean updateCollectionStatus(String prescriptionId) {

		boolean updated = false;

		Connection conn = dbConnection();
		String query = "UPDATE [dbo].[prescription]  SET [prescription].[isCollected] = '1' WHERE [prescriptionId] = '"
				+ prescriptionId + "'";

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
