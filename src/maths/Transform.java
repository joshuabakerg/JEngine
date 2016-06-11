package maths;

import java.io.Serializable;

/**
 * Created by Joshua on 2016/05/27.
 */
public class Transform implements Serializable{
    public Vector2d position = new Vector2d(0,0) ;
    public Vector2d rotation = new Vector2d(0,0);
    public Vector2d scale = new Vector2d(0,0);

    public Transform(){}


    public Transform(Vector2d position, Vector2d rotation, Vector2d scale){
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void translate(Vector2d movement){
        this.position.x += movement.x;
        this.position.y += movement.y;
    }

}
