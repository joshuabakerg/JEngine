package Component.Physics;

import Component.Component;
import maths.Vector2d;

/**
 * Created by Joshua on 2016/05/30.
 */
public class SimpleMover extends Component {

    private float speed;
    private double dir;
    private boolean move = false;
    private Vector2d position;

    public SimpleMover(){
        super("simpleMover");
    }

    public void begin(){
        this.move = true;
    }

    public void begin(float speed,double dir){
        this.speed =speed;
        this.dir = dir;
        this.move = true;
    }

    public void update(){
        if(position == null){
            position = gameObject.transform.position;
        }
        if(move){
            double dx = Math.cos(Math.toRadians(dir))*speed;
            double dy = Math.sin(Math.toRadians(dir))*speed;
            position.x += dx;
            position.y += dy;
        }

    }
    public void stop(){
        this.move = false;
    }

}
