package Networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import com.joshuabakerg.raincloud.serialization.RCObject;

import Entity.Entity;
import Networking.networkEncoder.NetworkData;
import Networking.networkEncoder.NetworkEncoder;
import Networking.networkEncoder.NetworkParam;
import graphics.Renderer;
import graphics.sprite.Sprite;
import maths.Vector2d;

public class Networker {
	
	private DatagramSocket socket ;
	private InetAddress serverAddress;
	private int serverPort;
	private Thread receive;
	private List<Entity> LocalEntities = new ArrayList<Entity>();
	private List<Entity> NetworkEntities = new ArrayList<Entity>();
	private int id;
	
	public Networker(String name ,String address,int port){
		try {
			socket = new DatagramSocket();
			serverPort = port;
			serverAddress = InetAddress.getByName(address);
		} catch (Exception e) {
			e.printStackTrace();
		}
		receive();
		send("/c/connect/n/name/v/"+name+"/e/");
	}
	
	public void addEntity(Entity e){
		e.setNetworkId(new Random().nextInt());
		LocalEntities.add(e);		
	}
	
	public void removeEntity(int id){
		for(int i =0;i < LocalEntities.size();i++){
			if(LocalEntities.get(i).getId() == i){
				LocalEntities.remove(i);
				return;
			}
			
		}
	}
	
	public void send(byte[] data){
		//new Thread("send"){
		//	public void run(){
				DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress,serverPort);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
		//	}
		//}.start();
	}
	
	public void send(String message){
		byte[] data = message.getBytes();
		send(data);
	}
	
	public void receive(){
		receive = new Thread("Receive"){
			public void run(){
				System.out.println("Listening ...");
				byte[] buffer = new byte[2048];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				while (true){
					try {
						socket.receive(packet);
						process(NetworkEncoder.getData(buffer));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		receive.start();
	}
	
	public boolean idExistsInLocalEntities(int networkID){
		boolean result = false;
		for(int i =0;i < LocalEntities.size();i++){
			if(LocalEntities.get(i).getNetworkId() == networkID)return true;
		}
		return false;
	}
	
	public boolean idExistsInNetworkEntities(int networkID){
		boolean result = false;
		for(int i =0;i < NetworkEntities.size();i++){
			if(NetworkEntities.get(i).getNetworkId() == networkID)return true;
		}
		return false;
	}
	
	private void process(NetworkData data){
		if(data.getCommand().equals("connect")){
			this.id = Integer.parseInt(data.getParams().getValue("id"));
		}else if(data.getCommand().equals("updateEntity")){
			NetworkParam params = data.getParams();
			Entity e = new Entity(Double.parseDouble(params.getValue("xpos")),Double.parseDouble(params.getValue("ypos")), Sprite.player_sprite);
			e.setNetworkId(Integer.parseInt(params.getValue("id")));
			if(!idExistsInLocalEntities(e.getNetworkId())){
				if(idExistsInNetworkEntities(e.getNetworkId())){
					updateNetworkEnity(e);
				}else{
					Entity temp = new Entity(e.getX(),e.getY(),Sprite.player_sprite);
					temp.setNetworkId(e.getNetworkId());
					NetworkEntities.add(temp);
				}
			}
		}
	}
	
	private void updateNetworkEnity(Entity e) {
		for(int i = 0; i < NetworkEntities.size();i++){
			Entity nEntity = NetworkEntities.get(i);
			if(nEntity.getNetworkId() == e.getNetworkId()){
				nEntity.setPosition(new Vector2d(e.getX(), e.getY()));
			}
		}
	}

	public void update(){
		//System.out.println(NetworkEntities.size());
		if(id != 0){
			for(int i = 0;i < LocalEntities.size();i++){
				Entity e = LocalEntities.get(i);
				send(("/c/updateEntity"+
						"/n/xpos/v/"+String.valueOf(e.getX())+
						"/n/ypos/v/"+String.valueOf(e.getY())+
						"/n/id/v/"+String.valueOf(e.getNetworkId())+
						"/e/")
						.getBytes());
			}
			System.out.println("send");
		}
	}
	
	public void render(Renderer renderer){
		for(int i = 0 ;i < NetworkEntities.size();i++){
			NetworkEntities.get(i).render(renderer);
		}
	}
	
	
}
