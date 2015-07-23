package de.xailabs.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		) {
			String command;
			while((command = in.readLine()) != null)  {
				switch(command) {
				case "New Contact":
					
				}
			}
		} catch (Exception e) {
			
		}
	}
}
