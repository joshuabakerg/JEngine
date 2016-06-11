package Component.Physics;

import Component.Component;
import maths.Vector2d;

/**
 * Created by Joshua on 2016/05/27.
 */
public class FollowerComponent extends Component{

    public Vector2d target;
    private boolean foundTarget = false;

    public FollowerComponent(Vector2d target){
        super("follower");
        this.target = target;
    }

    public void update(){
        if(gameObject.transform != null && !foundTarget && target != null){
            gameObject.transform.position = target;
            foundTarget = true;
        }
    }

}
