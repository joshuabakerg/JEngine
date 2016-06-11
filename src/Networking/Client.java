package Networking;

import java.net.InetAddress;
import java.util.Random;

public class Client {
	public String name;
	public InetAddress address;
	public int port;
	public int ID;
	
	public Client(String name, InetAddress address, int port){
		this.name = name;
		this.address = address;
		this.port = port;
		this.ID = new Random().nextInt();
	}
	
	
}
