package Component;

import graphics.Renderer;
import maths.Vector2d;

/**
 * Created by Joshua on 2016/05/27.
 */
public class CameraComponent extends Component {

    private Vector2d position = new Vector2d(0,0);

    public CameraComponent(){
        super("camera");
    }

    public void update(){
        if(gameObject.transform != null) {
            position.x = -gameObject.transform.position.x;
            position.y = -gameObject.transform.position.y;
        }else{
            System.err.println("no transform added to "+gameObject.getName());
        }
    }

    public void render(Renderer renderer){
        if(gameObject.transform != null) {
            if (!gameObject.transform.equals(renderer.getOffset())) {
                renderer.setOffset(position);
            }
        }else{
            System.err.println("no transform added to "+gameObject.getName());
        }
    }
}
