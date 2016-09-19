package mob;

import Component.Graphics.SpriteRendererComponent;
import Component.TransformComponent;
import Entity.GameObject;
import main.Prefabs;
import maths.Vector2d;

/**
 * Created by Joshua on 2016/05/27.
 */
public class GameObjectCreator {

    public static GameObject creatObject(String name, Vector2d position){
        GameObject gm = new GameObject(name);
        gm.addComponent(new TransformComponent(position.x,position.y));
        gm.addComponent(new SpriteRendererComponent(Prefabs.ball));
        return gm;
    }

}
