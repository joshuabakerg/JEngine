package main;

import Networking.JServer;

public class server {

	public static void main(String[] args) {
		JServer server = new JServer(90);
		server.start();
	}

}
