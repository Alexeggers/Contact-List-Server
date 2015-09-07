package de.xailabs.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import de.xailabs.client.CommandObject;

public class Connection implements Runnable {

	ServerController controller;
	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;
	
	public Connection (ServerController controller, Socket socket) {
		this.controller = controller;
		this.socket = socket;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		CommandObject inputCommand;
		try {
			while ((inputCommand = (CommandObject) in.readObject()) != null) {
				if (inputCommand.getCommand().equals("delete contact") || inputCommand.getCommand().equals("update contact")) {
					controller.acceptCommand(inputCommand);
				} else {
					Object returnObject = controller.acceptCommand(inputCommand);
					out.writeObject(returnObject);
					out.flush();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
