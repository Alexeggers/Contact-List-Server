package de.xailabs.server;

public class Main {
	
	public static void main(String[] args) {
		Controller controller = new Controller();
		ClientConnection clientConnection = new ClientConnection(4000, controller);
		clientConnection.startConnection();
	}
}
