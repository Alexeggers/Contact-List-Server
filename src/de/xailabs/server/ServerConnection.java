package de.xailabs.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import de.xailabs.client.CommandObject;
import de.xailabs.interfaces.IContact;

public class ServerConnection {
	
	private int portNumber;
	private ServerController controller;
	
	
	public ServerConnection(int portNumber, ServerController controller) {
		this.portNumber = portNumber;
		this.controller = controller;
	}
	
	public void startConnection() {
		try (
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket clientSocket = serverSocket.accept();
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());                   
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
		) {
			System.out.println("Connection made");
			CommandObject inputCommand;
			while ((inputCommand = (CommandObject) in.readObject()) != null) {
				if (inputCommand.getCommand().equals("check version") || inputCommand.equals("get id")) {
					Boolean congruent = controller.checkVersion(inputCommand);
					out.writeObject(congruent);
					out.flush();
				} else {
					List<IContact> contacts = controller.acceptCommand(inputCommand);
					out.writeObject(contacts);
					out.flush();
				}
			}
		} catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
