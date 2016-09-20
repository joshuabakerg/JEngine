package com.joshuabakerg.raincloud.serialization;

import java.nio.ByteBuffer;

public class SerializationUtils {

	//Bytes
	public static int writeBytes(byte[] dest, int pointer,byte[] src){
		assert(dest.length > pointer + src.length);
		for(int i = 0; i < src.length;i++)
			dest[pointer++] = src[i];
		return pointer;
	}
	
	//Chars
	public static int writeBytes(byte[] dest, int pointer,char[] src){
		assert(dest.length > pointer + src.length);
		for(int i = 0; i < src.length;i++)
			pointer = writeBytes(dest,pointer,src[i]);
		return pointer;
	}
	
	//Shorts
	public static int writeBytes(byte[] dest, int pointer,short[] src){
		assert(dest.length > pointer + src.length);
		for(int i = 0; i < src.length;i++)
			pointer = writeBytes(dest,pointer,src[i]);
		return pointer;
	}
	
	//Integers
	public static int writeBytes(byte[] dest, int pointer,int[] src){
		assert(dest.length > pointer + src.length);
		for(int i = 0; i < src.length;i++)
			pointer = writeBytes(dest,pointer,src[i]);
		return pointer;
	}
	
	//Longs
	public static int writeBytes(byte[] dest, int pointer,long[] src){
		assert(dest.length > pointer + src.length);
		for(int i = 0; i < src.length;i++)
			pointer = writeBytes(dest,pointer,src[i]);
		return pointer;
	}
	
	//Floats
	public static int writeBytes(byte[] dest, int pointer,float[] src){
		assert(dest.length > pointer + src.length);
		for(int i = 0; i < src.length;i++)
			pointer = writeBytes(dest,pointer,src[i]);
		return pointer;
	}
	
	//Doubles
	public static int writeBytes(byte[] dest, int pointer,double[] src){
		assert(dest.length > pointer + src.length);
		for(int i = 0; i < src.length;i++)
			pointer = writeBytes(dest,pointer,src[i]);
		return pointer;
	}
	
	//Booleans
	public static int writeBytes(byte[] dest, int pointer,boolean[] src){
		assert(dest.length > pointer + src.length);
		for(int i = 0; i < src.length;i++)
			pointer = writeBytes(dest,pointer,src[i]);
		return pointer;
	}
	
	//Byte
	public static int writeBytes(byte[] dest, int pointer,byte value){
		assert(dest.length > pointer + Type.getSize(Type.BYTE));
		dest[pointer++] = value;
		return pointer;
	}
	
	//Short
	public static int writeBytes(byte[] dest, int pointer,short value){
		assert(dest.length > pointer + Type.getSize(Type.SHORT));
		dest[pointer++] = (byte)((value >> 8) & 0xff) ;
		dest[pointer++] = (byte)((value >> 0) & 0xff) ;
		return pointer;
	}
	
	//Char
	public static int writeBytes(byte[] dest, int pointer,char value){
		assert(dest.length > pointer + Type.getSize(Type.CHAR));
		dest[pointer++] = (byte)((value >> 8) & 0xff) ;
		dest[pointer++] = (byte)((value >> 0) & 0xff) ;
		return pointer;
	}
	
	//Integer
	public static int writeBytes(byte[] dest, int pointer,int value){
		assert(dest.length > pointer + Type.getSize(Type.INTEGER));
		dest[pointer++] = (byte)((value >> 24) & 0xff) ;
		dest[pointer++] = (byte)((value >> 16) & 0xff) ;
		dest[pointer++] = (byte)((value >> 8) & 0xff) ;
		dest[pointer++] = (byte)((value >> 0) & 0xff) ;
		return pointer;
	}
	
	//Long
	public static int writeBytes(byte[] dest, int pointer,long value){
		assert(dest.length > pointer + Type.getSize(Type.LONG));
		dest[pointer++] = (byte)((value >> 56) & 0xff) ;
		dest[pointer++] = (byte)((value >> 48) & 0xff) ;
		dest[pointer++] = (byte)((value >> 40) & 0xff) ;
		dest[pointer++] = (byte)((value >> 32) & 0xff) ;
		dest[pointer++] = (byte)((value >> 24) & 0xff) ;
		dest[pointer++] = (byte)((value >> 16) & 0xff) ;
		dest[pointer++] = (byte)((value >> 8) & 0xff) ;
		dest[pointer++] = (byte)((value >> 0) & 0xff) ;
		return pointer;
	}
	
	//Float
	public static int writeBytes(byte[] dest , int pointer, float value){
		assert(dest.length > pointer + Type.getSize(Type.FLOAT));
		int data = Float.floatToIntBits(value);
		return writeBytes(dest, pointer, data);
	}
	
	//Double
	public static int writeBytes(byte[] dest , int pointer, double value){
		assert(dest.length > pointer + Type.getSize(Type.DOUBLE));
		long data = Double.doubleToLongBits(value);
		return writeBytes(dest, pointer, data);
	}
	
	//Boolean
	public static int writeBytes(byte[] dest , int pointer, boolean value){
		assert(dest.length > pointer + Type.getSize(Type.BOOLEAN));
		dest[pointer++] = (byte)(value ? 1 :0);
		return pointer;
	}


	//String
	public static int writeBytes(byte[] dest, int pointer, String name) {
		assert(dest.length > pointer + name.length());
		pointer = writeBytes(dest, pointer, (short)name.length());
		return writeBytes(dest,pointer,name.getBytes());
	}

	//Read Byte
	public static byte readByte(byte[] src, int pointer){
		return (src[pointer]) ;
	}
	
	//Read Bytes
	public static void readBytes(byte[] src, int pointer, byte[] dest){
		for(int i = 0; i < dest.length; i++)
			dest[i] = src[i+pointer];
	}
	
	//Read Short
	public static short readShort(byte[] src,int pointer){
		return ByteBuffer.wrap(src,pointer,2).getShort();
		//return (short)((src[pointer] << 8) | (src[pointer+1])) ;
	}
	
	//Read Shorts
	public static void readShorts(byte[] src,int pointer, short[] dest){
		for(int i = 0; i < dest.length;i++){
			dest[i] = readShort(src, pointer);
			pointer += Type.getSize(Type.SHORT);
		}
	}
	
	//Read Char
	public static char readChar(byte[] src,int pointer){
		return ByteBuffer.wrap(src,pointer,2).getChar();
		//return (char)((src[pointer] << 8) | (src[pointer+1])) ;
	}
	
	//Read Chars
	public static void readChars(byte[] src,int pointer, char[] dest){
		for(int i = 0; i < dest.length;i++){
			dest[i] = readChar(src, pointer);
			pointer += Type.getSize(Type.CHAR);
		}
	}
	
	//Read Integer
	public static int readInt(byte[] src,int pointer){
		return ByteBuffer.wrap(src, pointer, 4).getInt();
		//return (int)((src[pointer] << 24) | (src[pointer+1] << 16) | (src[pointer+2] << 8) | (src[pointer+3])) ;
	}
	
	//Read Integers
	public static void readInts(byte[] src,int pointer, int[] dest){
		for(int i = 0; i < dest.length;i++){
			dest[i] = readInt(src, pointer);
			pointer += Type.getSize(Type.INTEGER);
		}
	}
	
	//Read Long
	public static long readLong(byte[] src,int pointer){
		return ByteBuffer.wrap(src,pointer,8).getLong();
		/*return (long)((src[pointer] << 56) | (src[pointer+1] << 48) | (src[pointer+2] << 40) | (src[pointer+3] << 32) 
					 |(src[pointer+4] << 24) | (src[pointer+5] << 16) | (src[pointer+6] << 8) | (src[pointer+7])) ;*/
	}
	
	//Read Longs
	public static void readLongs(byte[] src,int pointer, long[] dest){
		for(int i = 0; i < dest.length;i++){
			dest[i] = readLong(src, pointer);
			pointer += Type.getSize(Type.LONG);
		}
	}
	
	//Read Float
	public static float readFloat(byte[] src, int pointer){
		return Float.intBitsToFloat(readInt(src,pointer));
	}
	
	//Read Floats
	public static void readFloats(byte[] src,int pointer, float[] dest){
		for(int i = 0; i < dest.length;i++){
			dest[i] = readFloat(src, pointer);
			pointer += Type.getSize(Type.FLOAT);
		}
	}
	
	//Read Double
	public static double readDouble(byte[] src, int pointer){
		return Double.longBitsToDouble(readLong(src,pointer));
	}
	
	//Read Doubles
	public static void readDoubles(byte[] src,int pointer, double[] dest){
		for(int i = 0; i < dest.length;i++){
			dest[i] = readDouble(src, pointer);
			pointer += Type.getSize(Type.DOUBLE);
		}
	}
	
	//Read Boolean
	public static boolean readBoolean(byte[] src, int pointer){
		assert(src[pointer] == 0 || src[pointer] == 1);
		return src[pointer] != 0;
	}
	
	//Read Booleans
	public static void readBooleans(byte[] src,int pointer, boolean[] dest){
		for(int i = 0; i < dest.length;i++){
			dest[i] = readBoolean(src, pointer);
			pointer += Type.getSize(Type.BOOLEAN);
		}
	}
	
	public static String readString(byte[] src, int pointer , int length){	
		return new String(src,pointer,length);
	}
	
	
}
