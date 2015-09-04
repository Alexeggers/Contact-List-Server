package de.xailabs.server;

public class Connection implements Runnable {

	ServerController controller;
	int portNumber;
	
	public Connection (ServerController controller, int portNumber) {
		this.controller = controller;
		this.portNumber = portNumber;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
