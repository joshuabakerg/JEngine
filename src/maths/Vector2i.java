package maths;

public class Vector2i {
	public int x,y;
	
	public Vector2i(int x , int y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2i add(Vector2i other){
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	
	public Vector2i subtract(Vector2i other){
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	
	public Vector2i divide(Vector2i other){
		this.x /= other.x;
		this.y /= other.y;
		return this;
	}
	
	public Vector2i multiply(Vector2i other){
		this.x *= other.x;
		this.y *= other.y;
		return this;
	}
	
}
