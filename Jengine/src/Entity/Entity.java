package Entity;



import Scene.Scene;
import graphics.Renderer;
import graphics.sprite.Sprite;
import maths.*;

import java.util.ArrayList;
import java.util.List;

public class Entity {

	protected Vector2d position;
	public  Rectangle collisionBox;
	protected Sprite sprite;
	protected int id;
	protected int networkID;
	protected boolean isStatic = true;
	protected boolean exists = true;
	protected boolean solid = true;
	protected boolean collided = false;
	protected Vector2d velocity;
	protected List<Velocity> velocities = new ArrayList<Velocity>();
	protected Scene scene;

	private long lastTime = System.currentTimeMillis();
	private long now = System.currentTimeMillis();
	
	
	public Entity(double x,double y,Sprite sprite){
		position = new Vector2d(x, y);
		this.sprite = sprite;
		collisionBox = new Rectangle(position,sprite.width,sprite.height,sprite.width/2,sprite.height/2);
		id = Generator.getID();
		velocity = new Vector2d(0, 0);
	}
	
	public void init(Scene scene){
		this.scene = scene;
	}
	
	public void update(){
	}
	
	
	public void render(Renderer renderer){
		renderer.renderSprite(sprite, (int)Math.round(position.x-sprite.width/2), (int)Math.round(position.y-sprite.height/2));
		//renderer.renderColour((int)collisionBox.position.x, (int)collisionBox.position.y, (int)collisionBox.width, (int)collisionBox.height, 0xff00ff);
	}

	protected void updateVectors(){
		now = System.currentTimeMillis();
		velocity.x = 0;
		velocity.y = 0;
		for(int i = 0; i < velocities.size();i++){
			Velocity v = velocities.get(i);
			if(v.velocity.y > 0){
				//moveAngle(v.velocity.x, v.velocity.y);
				double x = Math.cos(Math.toRadians(v.velocity.x))*v.velocity.y;
				double y = Math.sin(Math.toRadians(v.velocity.x))*v.velocity.y;
				//velocity.add(new Vector2d(velocity.x+x*(now-lastTime)/1000, velocity.y+y*(now-lastTime)/1000));
				velocity.add(new Vector2d(x,y));
				
				v.velocity.y -= v.duration;
			}else{
				if(!v.constant){
					velocities.remove(i);
				}else{
					//v.velocity.x = 0;
					v.velocity.y = 0;
				}
			}
		}
		lastTime = now;

		move(velocity.x, velocity.y);
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public void makeSolid(boolean solid){
		this.solid = solid;
	}
	
	public int getId(){
		return id;
	}
	
	public int getNetworkId(){
		return networkID;
	}
	
	public void setNetworkId(int i){
		networkID = i;
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Vector2d getPosition(){
		return position;
	}
	
	public void setPosition(Vector2d pos){
		this.position = pos;
		this.collisionBox.position = pos;
	}
	
	public void setX(double x){
		this.position.x =x;
	}
	
	public void setY(double y){
		this.position.y = y;
	}
	
	public double getX(){
		return position.x;
	}
	
	public double getY(){
		return position.y;
	}
	
	/*public void addVelocity(Vector2d velocity){
		double x = velocity.x;
		double y = velocity.y;
		if(x<0)x=360+x;
		this.velocity.x = (this.velocity.x+x)/2;	
		if (this.velocity.y+y > 20)this.velocity.y = 20;
		else this.velocity.y += y;
	}*/
	
	public void setVectors(double x,double y){
		for(int i = 0; i < velocities.size();i++){
			Velocity v = velocities.get(i);
			v.velocity.x=x;
			v.velocity.y=y;
		}
	}
	
	public void setVectorsY(double y){
		for(int i = 0; i < velocities.size();i++){
			Velocity v = velocities.get(i);
			v.velocity.y=y;
		}
	}
	
	public void setVector(String name,Vector2d vec){
		setVector(name,vec,false);
	}
	
	public void setVector(String name,Vector2d vec, boolean constant){
		setVector(name,vec,constant,0.5f);
	}

	public void setVector(String name,Vector2d vec, boolean constant, float duration ){
		if(vec.x<0)vec.x=360+vec.x;
		for(int i = 0; i < velocities.size();i++){
			Velocity v = velocities.get(i);
			if(v.name.equals(name)){
				v.velocity = vec;
				return;
			}
		}
		Velocity v = (new Velocity(name, vec,constant));
		v.duration = duration;
		velocities.add(v);
	}
	
	public void moveAngle(double angle ,double velocity){
		double dx = velocity * Math.cos(Math.toRadians(angle));
		double dy = velocity * Math.sin(Math.toRadians(angle));
		move(dx, dy);
	}
	
	public void move1(double xa , double ya){
		if(!collision(xa, 0))
			position.x += xa;
		if(!collision(0, ya))
			position.y += ya;
	}
	
	public void move(double xa, double ya){
		while(xa != 0){
			if(Math.abs(xa)>1){
				if(!collision(maths.abs(xa), 0)){
					this.position.x+= maths.abs(xa);
				}
				xa-= maths.abs(xa);
			}else{
				if(!collision(maths.abs(xa), 0)){
					this.position.x+= xa;
				}
				xa = 0;
			}
		}
		while(ya != 0){
			if(Math.abs(ya)>1){
				if(!collision(0,maths.abs(ya))){
					this.position.y+= maths.abs(ya);
				}
				ya-= maths.abs(ya);
			}else{
				if(!collision(0,maths.abs(ya))){
					this.position.y+= ya;
				}
				ya = 0;
			}
		}
		

	}
	
	private boolean collision(double x,double y){
		return scene.entityCollision(new Rectangle(position.x+x, position.y+y, collisionBox.width, collisionBox.height,collisionBox.xoffset,collisionBox.yoffset), this);
	}
	
	public void onCollision(Entity e){

	}
	
	public void setCollided(boolean collided){
		this.collided = collided;
	}
	
	public boolean getCollided(){
		return collided;
	}
	
	public boolean exists(){
		return exists;
	}
	
	public boolean isStatic(){
		return isStatic;
	}
	
	public void setStatic(boolean isStatic){
		this.isStatic = isStatic;
	}
	
	public void remove(){
		exists = false;
	}
	
}
