package Networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import Networking.networkEncoder.NetworkData;
import Networking.networkEncoder.NetworkEncoder;

public class JServer {
	private int port ;
	DatagramSocket socket;
	private Thread manage,  listen;
	private boolean running;
	
	private List<Client> clients = new ArrayList<Client>();
	
	public JServer(int port){
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void manage(){
		manage = new Thread("Manage"){
			public void run(){
				while(running){
					//System.out.println(clients.size());
				}
			}
		};
		manage.start();
	}
	
	private void sendToAll(byte[] data){
		for(Client client : clients){
			send(data,client.address,client.port);
		}
	}
	
	public void send(byte[] data,InetAddress address,int port){
		new Thread("send"){
			public void start(){
				if (running){
					try {
						DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
						socket.send(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void listen(){
		listen = new Thread("Listen"){
			public void run(){
				System.out.println("Listening ...");
				while (running){
					byte[] buffer = new byte[2048];
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
					try {
						socket.receive(packet);
						//System.out.println(new String(buffer));
						process(NetworkEncoder.getData(buffer),buffer,packet.getAddress(),packet.getPort());
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		};
		listen.start();
	}
	
	private void process(NetworkData data,byte[] bData,InetAddress address,int port){
		if(data.getCommand().equals("connect")){
			System.out.println("attempting to joinj");
			Client client = new Client(data.getParams().getValue("name"),address,port);
			System.out.println(client.name+" has joined");
			clients.add(client);
			send(("/c/connect/n/id/v/"+String.valueOf(client.ID)+"/e/").getBytes(), address, port);
		}else if(data.getCommand().equals("updateEntity")){
			sendToAll(bData);
		}
	}
	
	public void start(){
		running = true;
		listen();
		manage();
	}
	
	public void stop(){
		running = false;
		socket.close();
	}
	
	public boolean isRunning(){
		return running;
	}
	
	public void print(){
		System.out.println("worked");
	}
	
}
