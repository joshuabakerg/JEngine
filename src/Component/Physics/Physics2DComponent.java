package Component.Physics;

import Component.*;
import Engine.Jengine;
import Entity.GameObject;
import maths.Rectangle;
import maths.Vector2d;
import maths.Velocity;
import maths.maths;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua on 2016/05/27.
 */
public class Physics2DComponent extends Component{

    public boolean useGravity = true;
    public float mass = 1f;
    private Vector2d velocity;
    private List<Velocity> velocities = new ArrayList<Velocity>();
    private List<GameObject> ignoreGameObjects = new ArrayList<GameObject>();
    private Rectangle collisionBox;
    private Vector2d position;


    public Physics2DComponent(){
        super("physics2D");
        velocity = new Vector2d(0,0);

    }

    public void Awake(){
        CollisionBox2dComponent temp = (CollisionBox2dComponent) gameObject.find("collisionBox2d");
        if(temp == null)System.err.println("no collisionbox found on gameObject");
        this.collisionBox = temp.collisionBox;
        position = gameObject.transform.position;
    }

    public void update(){
        updateVectors();
        setVector("gravity", new Vector2d(90, 3), true,0.5f);
    }

    public void ignore(GameObject go){
        this.ignoreGameObjects.add(go);
    }

    protected void updateVectors(){
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
        move(velocity.x, velocity.y);
    }

    public void move1(double xa , double ya){
        if(!collision(xa, 0))
            gameObject.transform.position.x += xa;
        if(!collision(0, ya))
            gameObject.transform.position.y += ya;
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
            return Jengine.getScene().gameObjectCollision(new Rectangle(position.x+x, position.y+y, collisionBox.width, collisionBox.height,collisionBox.xoffset,collisionBox.yoffset), gameObject, ignoreGameObjects);
    }

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



}
