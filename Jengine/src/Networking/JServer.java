package Networking;

import Networking.Client.Client;
import Networking.Client.ClientManager;
import Networking.networkEncoder.NetworkData;
import com.joshuabakerg.raincloud.serialization.RCDatabase;
import com.joshuabakerg.raincloud.serialization.RCObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class JServer {
	private int port ;
	DatagramSocket socket;
	private Thread manage,  listen;
	private boolean running;
	
	private List<Client> clients = new ArrayList<Client>();
	private ClientManager clientManager = new ClientManager();

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
			send(data,client);
		}
	}

    public void send(RCDatabase database,Client client) {
        byte[] data = new byte[database.getSize()];
        database.getBytes(data, 0);
        send(data,client);
    }

    private void send(byte[] data,Client client){
        send(data,client.address,client.port);
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
						//process(NetworkEncoder.getData(buffer),buffer,packet.getAddress(),packet.getPort());
                        process(buffer, new Client(packet));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		};
		listen.start();
	}

    private void process(byte[] data,Client tempClient){
        RCConstrutor rcc = new RCConstrutor();
        if(new String(data,0,4).equals("RCDB")){
            //in rcdb
            RCDatabase rcDatabase = RCDatabase.Deserialize(data);
            RCObject commandObject = rcDatabase.findObject("command");
            if(commandObject!= null){
                String command = commandObject.findString("value").getString();
                if(command.equals("connect")) {
                    clientManager.addClient(tempClient.address, tempClient.port, commandObject.findString("name").getString());
                    send(rcc.makeCommandPacket("value","connected"),tempClient);
                    System.out.println("connected");
                }else if(command.equals("disconnect")){
                    clientManager.removeClient(tempClient);
                    send(rcc.makeCommandPacket("value","disconnected"),tempClient);
                    System.out.println("disconnected");
                }
            }
        }else{
            System.out.println("not RCDB");
        }
        System.out.println();
    }

	private void process(NetworkData data,byte[] bData,InetAddress address,int port){
		if(data.getCommand().equals("connect")){
			System.out.println("attempting to join");
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
