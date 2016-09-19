package Component;

import graphics.Renderer;
import maths.Transform;
import maths.Vector2d;

/**
 * Created by Joshua on 2016/05/27.
 */
public class TransformComponent extends Component{

    public Transform transform = new Transform();


    public TransformComponent(){
        name = "transform";
    }

    public TransformComponent(double x,double y){
        name = "transform";
        transform = new Transform(new Vector2d(x,y),transform.rotation,transform.scale);
    }

    @Override
    public void Awake() {

    }

    @Override
    public void Start() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render(Renderer renderer) {

    }
}
