package Component;

import Entity.GameObject;
import graphics.Renderer;

import java.io.Serializable;

/**
 * Created by Joshua on 2016/05/27.
 */
public class Component implements ComponentInterface, Serializable{


    protected String name = "na";
    public boolean enabled = true;
    protected GameObject gameObject;


    public Component(){

    }

    public Component(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void init(GameObject object){
        this.gameObject = object;
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
