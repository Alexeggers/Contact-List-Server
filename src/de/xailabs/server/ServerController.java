package de.xailabs.server;

import de.xailabs.client.CommandObject;
import de.xailabs.interfaces.IContact;
import de.xailabs.client.Contact;

public class ServerController {
	
	private DatabaseConnection jpaCon;
	
	public ServerController() {
		jpaCon = new DatabaseConnection();
	}
	
	/**
	 * Processes the command given appropriately. 
	 * @param commandObject The command object containing the command and, if necessary, a contact or search parameter
	 * @return An object whose type varies depending on the command given
	 */
	public Object acceptCommand(CommandObject commandObject) {
		Object returnObject = null;
		String command = commandObject.getCommand();
		IContact contact = (Contact) commandObject.getContact();
		String searchParameter = commandObject.getSearchparameter();
		if (command.equals("view all contacts")) {
			returnObject = jpaCon.getContacts();
		} else if (command.equals("new contact")) {
			returnObject = jpaCon.addNewContact(contact);
		} else if (command.equals("update contact")) {
			jpaCon.updateContact(contact);
		} else if (command.equals("delete contact")) {
			jpaCon.deleteContact(contact);
		} else if (command.equals("check version")) {
			int sqlVersion = jpaCon.getVersion(contact.getId());
			if (sqlVersion == contact.getVersion()) {
				returnObject = new Boolean(true);
			} else {
				returnObject = new Boolean(false);
			}
		} else if (command.equals("get id")) {
			returnObject = jpaCon.getMaxID();
		} else if (command.equals("search contact")) {
			returnObject = jpaCon.searchForContact(searchParameter);
		} else if (command.equals("get contact")) {
			returnObject = jpaCon.getContact(contact.getId());
		}
		return returnObject;
	}

	/**
	 * Checks the version of the contact provided in the command.
	 * @param inputCommand The command sent by the client
	 * @return Boolean denoting whether the contact is in sync with the SQL database
	 */
	public Boolean checkVersion(CommandObject inputCommand) {
		Boolean congruent = false;
		int id = inputCommand.getContact().getId();
		int version = inputCommand.getContact().getVersion();
		if (version == jpaCon.getVersion(id)) {
			congruent = true;
		}		
		return congruent;
	}
}
