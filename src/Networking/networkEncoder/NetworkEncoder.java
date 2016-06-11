package Networking.networkEncoder;


public class NetworkEncoder {

	public static NetworkData getData(String data){
		return NetworkEncoder.getData(data.getBytes());
	}
	
	public static NetworkData getData(byte[] bytes){
		String data = new String(bytes,0,bytes.length);
		String command;
		String[] message = data.split("/c/|/n/|/e/");
		if(data.endsWith("/e/")){
			String[] paramsNames = new String[message.length-2];
			String[] paramsValues = new String[message.length-2];
			command = message[1];
			for(int i = 2;i<message.length;i++){
				paramsNames[i-2] = message[i].split("/v/")[0];
				paramsValues[i-2] = message[i].split("/v/")[1];
			}
			return new NetworkData(command, new NetworkParam(paramsNames, paramsValues));
		}else{
			String[] paramsNames = new String[message.length-3];
			String[] paramsValues = new String[message.length-3];
			command = message[1];
			for(int i = 2;i<message.length-1;i++){
				paramsNames[i-2] = message[i].split("/v/")[0];
				//System.out.println(message[i]);
				paramsValues[i-2] = message[i].split("/v/")[1];
			}
			return new NetworkData(command, new NetworkParam(paramsNames, paramsValues));
		}
	}
	
	
}
