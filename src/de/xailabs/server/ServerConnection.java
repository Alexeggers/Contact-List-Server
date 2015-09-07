package de.xailabs.server;

import java.net.ServerSocket;
import java.net.Socket;

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
		
		try (ServerSocket serverSocket = new ServerSocket(portNumber);) {
			while (true) {
				Socket socket = serverSocket.accept();
				Connection connection = new Connection(controller, socket);
				Thread thread = new Thread(connection);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
