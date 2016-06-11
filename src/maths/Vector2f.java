package maths;

public class Vector2f {
public float x,y;
	
	public Vector2f(float x , float y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2f add(Vector2f other){
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	
	public Vector2f subtract(Vector2f other){
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	
	public Vector2f divide(Vector2f other){
		this.x /= other.x;
		this.y /= other.y;
		return this;
	}
	
	public Vector2f multiply(Vector2f other){
		this.x *= other.x;
		this.y *= other.y;
		return this;
	}
}
