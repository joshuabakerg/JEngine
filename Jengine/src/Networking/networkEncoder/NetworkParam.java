package Networking.networkEncoder;

public class NetworkParam {

	private String[] name;
	private String[] value;
	private int size;
	
	public NetworkParam(String[] names,String[] values){
		if(names.length != values.length){
			Exception e = new Exception("array names and values must be same length");
			try {
				throw(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		name = names;
		value = values;
		size = value.length;
	}
	
	public String[] getNames(){
		return name;
	}
	
	public String[] getValues(){
		return value;
	}
	
	public String getName(int i){
		return name[i];
	}
	
	public String getValue(String name){
		for(int i = 0; i < this.name.length;i++){
			if(this.name[i].equals(name))
				return value[i];
		}
		try {
			throw(new Exception(name+" not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getValue(int i){
		return value[i];
	}
	
	
	
}
