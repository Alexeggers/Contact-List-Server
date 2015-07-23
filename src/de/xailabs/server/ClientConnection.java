package de.xailabs.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnection {
	int portNumber;
	
	public ClientConnection(int portNumber) {
		this.portNumber = portNumber;
	}
	
	public void startConnection() {
		try (
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket clientSocket = serverSocket.accept();
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());                   
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
		) {
			CommandObject inputCommand;
			while((inputCommand = (CommandObject) in.readObject()) != null) {
				
			}
			
			
		} catch (Exception e) {
			
		}
	}
}
