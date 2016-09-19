package maths;

public class Velocity {
	
	public String name;
	public Vector2d velocity;
	public boolean constant;
	public float duration ;
	
	public Velocity(String name,Vector2d velocity,boolean constant){
		this.name = name;
		this.velocity = velocity;
		this.constant = constant;
	}
	
}
