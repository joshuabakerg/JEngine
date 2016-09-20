package com.joshuabakerg.raincloud.serialization;

import static com.joshuabakerg.raincloud.serialization.SerializationUtils.*;

public class RCString extends RCBase{

	public static final byte CONTAINER_TYPE = ContainerType.STRING;
	public int count ;
	private char[] characters;
	
	private RCString(){
		size += 1 + 4;
	}
	
	private void updataSize(){
		size += getDataSize();
	}
	
	public int getBytes(byte[] dest, int pointer){
		pointer = writeBytes(dest,pointer,CONTAINER_TYPE);
		pointer = writeBytes(dest,pointer,nameLength);
		pointer = writeBytes(dest,pointer,name);
		pointer = writeBytes(dest,pointer,size);
		pointer = writeBytes(dest,pointer,count);
		pointer = writeBytes(dest,pointer,characters);

		return pointer;
	}
	
	public int getSize(){
		return size;
	}
	
	public String getString() {
		return new String(characters);
	}
	
	public int getDataSize(){
 		return characters.length * Type.getSize(Type.CHAR);
	}
	
	public static RCString Create(String name,String data){
		RCString string = new RCString();
		string.setName(name);
		string.count = data.length();
		string.characters = data.toCharArray();
		string.updataSize();
		return string;
	}
	
	public static RCString Deserialize(byte[] data, int pointer){
		byte containerType = readByte(data, pointer++);
		assert(containerType == CONTAINER_TYPE);
		
		RCString result = new RCString();
		result.nameLength = readShort(data, pointer);
		pointer += 2;
		result.name = readString(data, pointer, result.nameLength).getBytes();
		pointer  += result.nameLength;
		
		result.size = readInt(data, pointer);
		pointer += 4;
		
		result.count = readInt(data, pointer);
		pointer += 4;
		
		result.characters = new char[result.count];
		readChars(data, pointer, result.characters);
		
		pointer += Type.getSize(Type.CHAR ) * result.count;
		
		return result;
	}
	
}
