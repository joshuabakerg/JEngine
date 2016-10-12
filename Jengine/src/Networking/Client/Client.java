package Networking.Client;

import java.net.DatagramPacket;
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

    public Client(DatagramPacket packet){
        Random random = new Random();
        this.name = "temp-user_"+ random.nextInt();
        this.address = packet.getAddress();
        this.port = packet.getPort();
        this.ID = random.nextInt();
    }
	
}
