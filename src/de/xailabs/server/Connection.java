package de.xailabs.server;

import java.net.Socket;

public class Connection implements Runnable {

	ServerController controller;
	Socket socket;
	int i;
	
	public Connection (ServerController controller, Socket socket) {
		this.controller = controller;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
