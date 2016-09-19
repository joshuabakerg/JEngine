package tile;

import Entity.Entity;
import graphics.sprite.Sprite;

public class Ground extends Entity {

	public Ground(double x, double y, Sprite sprite) {
		super(x, y, sprite);
		this.isStatic = true;
		this.solid = true;
		//this.
	}
	
	public void update(){
		
	}
	
	public void onCollision(Entity e){

	}

}
