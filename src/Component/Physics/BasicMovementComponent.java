package Component.Physics;

import Component.*;

import java.awt.event.KeyEvent;

import static Engine.Jengine.keys;

/**
 * Created by Joshua on 2016/05/27.
 */
public class BasicMovementComponent extends Component{

   private int speed = 2;

    public BasicMovementComponent(){
        name = "basicMovementComponent";
    }

    public void update(){
        if (keys.isKeyPressed(KeyEvent.VK_LEFT)){
            gameObject.transform.position.x -= speed;
        }
        if (keys.isKeyPressed(KeyEvent.VK_RIGHT)){
            gameObject.transform.position.x += speed;
        }
        if (keys.isKeyPressed(KeyEvent.VK_UP)){
            gameObject.transform.position.y -= speed;
        }
        if (keys.isKeyPressed(KeyEvent.VK_DOWN)){
            gameObject.transform.position.y += speed;
        }
    }

}
