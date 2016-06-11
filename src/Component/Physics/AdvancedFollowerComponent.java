package Component.Physics;

import Component.Component;
import Engine.Time;
import maths.Vector2d;

/**
 * Created by Joshua on 2016/05/27.
 */
public class AdvancedFollowerComponent extends Component {

    public Vector2d target;
    public float speed = 50f;

    public AdvancedFollowerComponent(Vector2d target){
        super("advancedFollower");
        this.target = target;
    }

    public void update(){
        //float distCovered = (System.currentTimeMillis() - startTime) * speed;
        //float fracJourney = distCovered / journeyLength;
        /*System.out.println(Time.deltaTime);
        if(gameObject.transform.position.distance(target)<3f)return;
        Vector2d dir = target.subtract(gameObject.transform.position).normalize();
        dir.x *= speed;
        dir.y *= speed;
        gameObject.transform.translate(dir);*/
        gameObject.transform.position = Vector2d.lerp(gameObject.transform.position,target,speed* Time.deltaTime);
    }

}
