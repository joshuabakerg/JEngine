package Component;

import graphics.Renderer;
import maths.Rectangle;
import maths.Vector2d;

/**
 * Created by Joshua on 2016/05/27.
 */
public class CollisionBox2dComponent extends Component {

    public Rectangle collisionBox;
    public boolean haveSetCollisionBoxPos = false;
    private boolean canSeeCollisionBox = false;

    public CollisionBox2dComponent(double width, double height){
        super("collisionBox2d");
        collisionBox = new Rectangle(new Vector2d(0,0),width,height,(int)width/2,(int)height/2);
    }

    public void render(Renderer renderer){
        if(canSeeCollisionBox){
            renderer.renderColour((int)collisionBox.position.x-collisionBox.xoffset,(int)collisionBox.position.y-collisionBox.yoffset,(int)collisionBox.width,(int)collisionBox.height,0xff00ff);
        }
    }

    public void update(){
        if(!haveSetCollisionBoxPos){
            collisionBox.position = gameObject.transform.position;
            haveSetCollisionBoxPos = true;
        }



    }

    public void seeCollisionBox(){
        this.canSeeCollisionBox = true;
    }



}
