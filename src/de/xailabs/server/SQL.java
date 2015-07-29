package de.xailabs.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.xailabs.client.Contact;
import de.xailabs.interfaces.IContact;


public class SQL {
	
	private Connection connection = null;
    private Statement stmt = null;
	
	public SQL () {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:database/VerContacts.sqlite");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
	    }
	}
	
	public List<IContact> getContacts() {
		ResultSet rs = executeQuery("SELECT * FROM CONTACTS;");
		ArrayList<IContact> contacts = new ArrayList<IContact>();
		try {
			while(rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("Name");
				String number = rs.getString("Phonenumber");
				String notes = rs.getString("Notes");
				int version = rs.getInt("Version");
				IContact contact = new Contact(id, name, number, notes, version);
				contacts.add(contact);
			}
		} catch(SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return contacts;
	}
	
	public List<IContact> searchForContact(String searchParameter) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM CONTACTS WHERE Name LIKE '%");
		sb.append(searchParameter);
		sb.append("%' or Notes LIKE '%");
		sb.append(searchParameter);
		sb.append("%';");
		ResultSet rs = executeQuery(sb.toString());
		ArrayList<IContact> foundContacts = new ArrayList<IContact>();
		try {
			while(rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("Name");
				String number = rs.getString("Phonenumber");
				String notes = rs.getString("Notes");
				int version = rs.getInt("Version");
				IContact contact = new Contact(id, name, number, notes, version);
				foundContacts.add(contact);
			}
		} catch(SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return foundContacts;
	}
	
	public Integer addNewContact(IContact contact) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO CONTACTS (Name, Phonenumber, Notes, Version) VALUES ('");
		sb.append(contact.getName());
		sb.append("', '");
		sb.append(contact.getPhonenumber());
		sb.append("', '");
		sb.append(contact.getNotes());
		sb.append("', ");
		sb.append(1);
		sb.append(");");
		executeUpdate(sb.toString());
		sb = new StringBuilder();
		Integer id = getMaxID();
		return id;
	}
	
	public void updateContact(IContact contact) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE CONTACTS SET Name = '");
		sb.append(contact.getName());
		sb.append("', Phonenumber = '");
		sb.append(contact.getPhonenumber());
		sb.append("', Notes = '");
		sb.append(contact.getNotes());
		sb.append("', Version = Version + 1");
		sb.append(" WHERE ID = ");
		sb.append(contact.getId());
		executeUpdate(sb.toString());
	}
	
	public boolean deleteContact(IContact contact) {
		boolean deleted = executeUpdate("DELETE FROM CONTACTS WHERE ID = " + contact.getId() + ";");
		return deleted;
	}
	
	public boolean executeUpdate(String sql) {
		boolean executed = true;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch(SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			executed = false;
		}
		return executed;
	}
	
	public ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
		} catch(SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return rs;
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch(Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	
	public int getMaxID() {
		String sql = "SELECT MAX(ID) FROM CONTACTS";
		ResultSet rs = executeQuery(sql);
		int id = -1;
		try {
			id = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * Returns the version of the contact with the id given.
	 * @param id ID of checked contact
	 * @return int Version
	 */
	public int getVersion(int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM CONTACTS WHERE ID = ");
		sb.append(id);
		sb.append(";");
		String sql = sb.toString();
		ResultSet rs = executeQuery(sql);
		int version = 1;
		try {
			version = rs.getInt("Version");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return version;
	}
}