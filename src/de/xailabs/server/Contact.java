package de.xailabs.server;

import de.xailabs.interfaces.IContact;

public class Contact implements IContact {
	
	private int id;
	private String name;
	private String phonenumber;
	private String notes;
	
	
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
