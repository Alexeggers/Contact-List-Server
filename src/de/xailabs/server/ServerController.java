package de.xailabs.server;

import java.util.List;

import de.xailabs.client.CommandObject;
import de.xailabs.interfaces.IContact;
import de.xailabs.client.Contact;

public class ServerController {
	
	private SQL sql;
	private List<IContact> contacts;
	
	public ServerController() {
		sql = new SQL();
	}
	
	public List<IContact> acceptCommand(CommandObject commandObject) {
		String command = commandObject.getCommand();
		IContact contact = (Contact) commandObject.getContact();
		String searchparameter = commandObject.getSearchparameter();
		if (command.equals("view all contacts")) {
			contacts = sql.getContacts();
		} else if (command.equals("new contact")) {
			sql.addNewContact(contact);
			contacts = sql.getContacts();
		} else if (command.equals("update contact")) {
			sql.updateContact(contact);
			contacts = sql.getContacts();
		} else if (command.equals("delete contact")) {
			sql.deleteContact(contact);
			contacts = sql.getContacts();
		} else if (command.equals("search contact")) {
			contacts = sql.searchForContact(searchparameter);
		}
		return contacts;
	}

	public Boolean checkVersion(CommandObject inputCommand) {
		Boolean congruent = false;
		int id = inputCommand.getContact().getId();
		int version = inputCommand.getContact().getVersion();
		if (version == sql.getVersion(id)) {
			congruent = true;
		}		
		return congruent;
	}
}
