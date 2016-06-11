package Component;

import maths.Vector2d;

import java.awt.event.KeyEvent;

/**
 * Created by Joshua on 2016/06/02.
 */
public class SimpleControllerComponent extends Component {

    public SimpleControllerComponent(){
        super("simpleController");
    }

    public void update(){
        if(gameObject.keys().isKeyPressed(KeyEvent.VK_D)){
            gameObject.transform.translate(new Vector2d(5,0));
        }
        if(gameObject.keys().isKeyPressed(KeyEvent.VK_A)){
            gameObject.transform.translate(new Vector2d(-5,0));
        }
        if(gameObject.keys().isKeyPressed(KeyEvent.VK_W)){
            gameObject.transform.translate(new Vector2d(0,-5));
        }
        if(gameObject.keys().isKeyPressed(KeyEvent.VK_S)){
            gameObject.transform.translate(new Vector2d(0,5));
        }
    }

}
