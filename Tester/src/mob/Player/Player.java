package mob.Player;

import Entity.Entity;
import graphics.Renderer;
import graphics.input.Keyboard;
import graphics.input.Mouse;
import graphics.sprite.Sprite;
import maths.Vector2d;
import throwable.Ball;

import java.awt.event.KeyEvent;

public class Player extends Entity {
	private Keyboard keys;
	private Mouse mouse;
	private boolean grounded = false;
    private int timer = 0;
	
	public Player(double x, double y, Sprite sprite, Keyboard keys, Mouse mouse) {
		super(x, y, sprite);
		isStatic = false;
		solid = false;
		this.keys = keys;
		this.mouse = mouse;
		
	}

	public void update(){
        if(timer == 10000000)timer = 0;
		timer ++;

		if(keys.isKeyPressed(KeyEvent.VK_A)){
			//this.move(-5,0);
			this.setVector("leftmove", new Vector2d(180, 5), true,0.5f);
		}
		if(keys.isKeyPressed(KeyEvent.VK_D)){
			//this.move(5,0);
			this.setVector("rightmove", new Vector2d(0, 5),true,0.5f);
		}
		if(keys.isKeyPressed(KeyEvent.VK_S)){
			this.move(0,5);
		}
		if(keys.isKeyPressed(KeyEvent.VK_W)){
			this.move(0,-5);
		}
		if(keys.isKeyPressed(KeyEvent.VK_SPACE)){
			this.setVector("jump", new Vector2d(-90, 10), true,0.5f);
		}

		if(mouse.getButton() == 1){
            if(timer%5==0) {
                Ball ball = new Ball(this.position.x, this.position.y);
                double dx = mouse.getX() - (900/2);
                double dy = mouse.getY() - (600/2);
                double dir = Math.atan2(dy, dx);
                dir = Math.toDegrees(dir);
                System.out.println(dir);
                ball.setVector("shoot", new Vector2d(dir, 10), false, 0f);
                scene.addEntity(ball);
            }
        }
		updateVectors();
		
	}
	
	public void render(Renderer renderer){
		renderer.renderSprite(sprite, position.x-sprite.width/2, position.y-sprite.height/2);
		//renderer.renderColour((int)collisionBox.position.x,(int)collisionBox.position.y,(int)collisionBox.width,(int)collisionBox.height,0xff00ff);
	}
	
}
