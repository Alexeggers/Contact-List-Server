package de.xailabs.server;

import de.xailabs.interfaces.*;

public class CommandObject implements ICommandObject {

	private String command;
	private String searchParameter;
	private IContact contact;
	
	@Override
	public void setCommand(String command) {
		this.command = command;
	}

	@Override
	public String getCommand() {
		return command;
	}

	@Override
	public void setContact(IContact contact) {
		this.contact = contact;
	}

	@Override
	public IContact getContact() {
		return contact;
	}

	@Override
	public void setSearchParameter(String searchParameter) {
		this.searchParameter = searchParameter;
	}

	@Override
	public String getSearchParameter() {
		return searchParameter;
	}

}
