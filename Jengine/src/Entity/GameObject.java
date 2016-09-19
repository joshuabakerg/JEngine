package Entity;

import Component.*;
import Component.TransformComponent;
import Engine.Jengine;
import Scene.Scene;
import graphics.Renderer;
import graphics.input.Keyboard;
import graphics.input.Mouse;
import maths.Transform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameObject implements ComponentInterface, Serializable {

    private String name ;
    public Scene scene;
    public Transform transform;
    public List<Component> components = new ArrayList<Component>();
    private boolean deleted = false;
    public boolean enabled = true;
    public boolean canUpdate = true;
    public boolean canRender = true;


    public GameObject(String name){
        this.name = name;
    }



    public Keyboard keys(){
        return Jengine.keys;
    }

    public Mouse mouse(){
        return Jengine.mouse;
    }

    public Component find(String name){
        for(int i = 0; i < components.size();i++){
            if(components.get(i).getName().equals(name))
                return components.get(i);
        }
        return null;
    }

    public List<Component> findAll(String name){
        List<Component> cs = new ArrayList<Component>();
        for(int i = 0; i < components.size();i++){
            if(components.get(i).getName().equals(name))
                cs.add(components.get(i));
        }
        return cs;
    }

    public void addComponent(Component component){
        for(int i = 0;i < components.size() ; i++){
            if(components.get(i).getName().equals(component.getName())){
                System.err.println("Only on component of each type");
                return;
            }
        }
        if(component!= null && component.getName().equals("transform")){
            transform = ((TransformComponent)component).transform;
        }
        components.add(component);
        component.init(this);

    }

    public boolean isDeleted(){
        return deleted;
    }

    public void delete(){
        this.deleted = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void Awake() {
        for(int i = 0; i < components.size(); i++){
            components.get(i).Awake();
        }
    }


    public void Start() {
        for(int i = 0; i < components.size(); i++){
            components.get(i).Start();
        }
    }


    public void update() {
        if(!canUpdate)return;
        for(int i = 0; i < components.size(); i++){
            if(!components.get(i).enabled)continue;
            components.get(i).update();
        }
    }


    public void render(Renderer renderer) {
        if(!canRender)return;
        for(int i = 0; i < components.size(); i++){
            if(!components.get(i).enabled)continue;
            components.get(i).render(renderer);
        }
    }

}
