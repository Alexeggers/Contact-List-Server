package de.xailabs.server;

import java.io.Serializable;

import de.xailabs.interfaces.IContact;

public class Contact implements IContact, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9022314547799392674L;
	private int id;
	private String name;
	private String phonenumber;
	private String notes;
	
	public Contact() {
		
	}
	
	public Contact(String name, String phonenumber, String notes) {
		this.name = name;
		this.phonenumber = phonenumber;
		this.notes = notes;
	}
	
	public Contact(int id, String name, String phonenumber, String notes) {
		this(name, phonenumber, notes);
		this.id = id;
	}
	
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	@Override
	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPhonenumber() {
		return phonenumber;
	}

	@Override
	public String getNotes() {
		return notes;
	}

	@Override
	public int getId() {
		return id;
	}
}
