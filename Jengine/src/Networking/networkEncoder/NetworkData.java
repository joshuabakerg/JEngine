package Networking.networkEncoder;

public class NetworkData {

	private String command;
	private NetworkParam params;
	
	public NetworkData(String command,NetworkParam params){
		this.command = command;
		this.params = params;
	}
	
	public String getCommand(){
		return command;
	}
	
	public NetworkParam getParams(){
		return params;
	}
}
