package de.xailabs.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import de.xailabs.interfaces.IContact;

public class ClientConnection {
	
	private int portNumber;
	private Controller controller;
	
	public ClientConnection(int portNumber, Controller controller) {
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
			
			CommandObject inputCommand;
			List<IContact> contacts;
			while((inputCommand = (CommandObject) in.readObject()) != null) {
				contacts = controller.acceptCommand(inputCommand);
				out.writeObject(contacts);
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
