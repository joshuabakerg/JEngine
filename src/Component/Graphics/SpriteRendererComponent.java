package Component.Graphics;

import Component.Component;
import graphics.Renderer;
import graphics.sprite.Sprite;

/**
 * Created by Joshua on 2016/05/27.
 */
public class SpriteRendererComponent extends Component {

    private Sprite sprite;

    public SpriteRendererComponent(){
        super("spriteRenderer");
    }

    public SpriteRendererComponent(Sprite sprite){
        super("spriteRenderer");
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void render(Renderer renderer) {
        renderer.renderSprite(sprite,gameObject.transform.position.x,gameObject.transform.position.y,true);
    }

}
