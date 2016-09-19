package mob;

import Entity.Entity;
import graphics.sprite.Sprite;
import maths.Vector2d;
import mob.Player.Player;

import java.util.Random;

public class Enemy extends Entity {

	private Random random = new Random();
	private int dir = 0;
	
	public Enemy(double x, double y, Sprite sprite) {
		super(x, y, sprite);
		isStatic = false;
		solid = false;
	}
	
	public void update(){
		updateVectors();
		if(random.nextInt(100) == 0){
		
			if(random.nextInt(2)==0){
				dir = 0;
			}else{
				dir = 1;
			}
			
		}
		if(dir == 0){
			moveRight();
		}else{
			moveLeft();
		}
	}
	
	public void onCollision(Entity e){
		if(!(e instanceof Player))return;
		e.setVector("hit",new Vector2d(-70,20),false,0.2f);
		//System.out.println(e.getId());
	}
	
	private void moveLeft(){
		setVector("moveLeft", new Vector2d(180,5), true,0.2f);
	}
	
	private void moveRight(){
		setVector("moveRight", new Vector2d(0,5), true,0.2f);
		
	}
	

}
