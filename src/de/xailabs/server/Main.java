package de.xailabs.server;

public class Main {
	
	public static void main(String[] args) {
		ServerController controller = new ServerController();
		ServerConnection clientConnection = new ServerConnection(13337, controller);
		clientConnection.startConnection();
	}
}