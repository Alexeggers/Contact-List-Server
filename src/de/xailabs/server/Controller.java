package de.xailabs.server;

import java.util.List;

import de.xailabs.client.CommandObject;
import de.xailabs.interfaces.IContact;
import de.xailabs.client.Contact;

public class Controller {
	
	private SQL sql;
	private List<IContact> contacts;
	
	public Controller() {
		sql = new SQL();
	}
	
	public List<IContact> acceptCommand(CommandObject commandObject) {
		String command = commandObject.getCommand();
		IContact contact = (Contact) commandObject.getContact();
		if (command.equals("view all contacts")) {
			
		} else if (command.equals("new contact")) {
			sql.addNewContact(contact);
		} else if (command.equals("update contact")) {
			sql.updateContact(contact);
		} else if (command.equals("delete contact")) {
			sql.deleteContact(contact);
		}
		contacts = sql.getContacts();
		return contacts;
	}
	
}
