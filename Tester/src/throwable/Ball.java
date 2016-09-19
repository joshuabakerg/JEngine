package throwable;

import Entity.Entity;
import graphics.sprite.Sprite;

/**
 * Created by Joshua on 2016/05/15.
 */
public class Ball extends Entity{

    private long startTime = System.currentTimeMillis();
    private int liveTime =5;

    public Ball(double x, double y){
        super(x,y,new Sprite("/textures/ball.png"));
        this.solid = false;
        this.setStatic(false);
    }

    public void update(){
        updateVectors();
        if(System.currentTimeMillis()-startTime>1000*liveTime){
            this.exists = false;
        }
    }


}
