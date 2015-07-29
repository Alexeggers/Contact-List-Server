package de.xailabs.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import de.xailabs.client.CommandObject;

public class ServerConnection {
	
	private int portNumber;
	private ServerController controller;
	
	
	public ServerConnection(int portNumber, ServerController controller) {
		this.portNumber = portNumber;
		this.controller = controller;
	}
	
	/**
	 * Starts up the server and waits for input when it does get connected.
	 */
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
				if (inputCommand.getCommand().equals("delete contact") || inputCommand.getCommand().equals("update contact")) {
					controller.acceptCommand(inputCommand);
				} else {
					Object returnObject = controller.acceptCommand(inputCommand);
					out.writeObject(returnObject);
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
