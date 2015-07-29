package de.xailabs.server;

import de.xailabs.client.CommandObject;
import de.xailabs.interfaces.IContact;
import de.xailabs.client.Contact;

public class ServerController {
	
	private SQL sql;
	
	public ServerController() {
		sql = new SQL();
	}
	
	public Object acceptCommand(CommandObject commandObject) {
		Object returnObject = null;
		String command = commandObject.getCommand();
		IContact contact = (Contact) commandObject.getContact();
		String searchParameter = commandObject.getSearchparameter();
		if (command.equals("view all contacts")) {
			returnObject = sql.getContacts();
		} else if (command.equals("new contact")) {
			returnObject = sql.addNewContact(contact);
		} else if (command.equals("update contact")) {
			sql.updateContact(contact);
		} else if (command.equals("delete contact")) {
			sql.deleteContact(contact);
		} else if (command.equals("check version")) {
			int sqlVersion = sql.getVersion(contact.getId());
			if (sqlVersion == contact.getVersion()) {
				returnObject = new Boolean(true);
			} else {
				returnObject = new Boolean(false);
			}
		} else if (command.equals("get id")) {
			returnObject = sql.getMaxID();
		} else if (command.equals("search contact")) {
			returnObject = sql.searchForContact(searchParameter);
		}
		return returnObject;
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
