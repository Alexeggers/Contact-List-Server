package de.xailabs.server;

import java.util.List;

import de.xailabs.interfaces.IContact;

public class Controller {
	
	private SQL sql;
	private List<IContact> contacts;
	
	public List<IContact> acceptCommand(CommandObject commandObject) {
		String command = commandObject.getCommand();
		IContact contact = commandObject.getContact();
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
