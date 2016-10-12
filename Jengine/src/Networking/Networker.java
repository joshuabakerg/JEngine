package Networking;

import Entity.Entity;
import Networking.networkEncoder.NetworkData;
import Networking.networkEncoder.NetworkParam;
import com.joshuabakerg.raincloud.serialization.RCDatabase;
import graphics.Renderer;
import graphics.sprite.Sprite;
import maths.Vector2d;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import com.joshuabakerg.raincloud.serialization.RCObject;

public class Networker {
	
	private NetworkHandler nHandler;
    private String name;
	private List<Entity> LocalEntities = new ArrayList<Entity>();
	private List<Entity> NetworkEntities = new ArrayList<Entity>();
	private int id;
	
	public Networker(String name ,String address,int port){
        try {
            nHandler = new NetworkHandler(InetAddress.getByName(address),port){
                public void process(RCDatabase database){
                    process(database);
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.name = name;
    }

    private void process(RCDatabase database){

    }

    public boolean connect(){
        return nHandler.connect(this.name);
    }

    public boolean disconnect(){
        return nHandler.disconnect();
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
		if(id != 0){
			for(int i = 0;i < LocalEntities.size();i++){
				Entity e = LocalEntities.get(i);
				//send herer
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
