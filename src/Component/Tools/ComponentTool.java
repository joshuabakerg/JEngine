package Component.Tools;


import Component.Component;
import Component.*;
import Component.Graphics.SpriteRendererComponent;
import Component.Tools.GUI.SpriteGUI;
import Component.Tools.GUI.Vector2dGUI;
import Component.Tools.GUI.fieldGUI;
import Component.TransformComponent;
import Entity.GameObject;
import graphics.sprite.Sprite;
import maths.*;

import javax.swing.*;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua on 2016/05/29.
 */
public class ComponentTool extends Component{

    private JFrame frame;
    private JMenuBar menuBar;

    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private List<fieldGUI> fields = new ArrayList<fieldGUI>();


    public ComponentTool(GameObject gameObject){
        this.gameObject = gameObject;
        start();
    }

    public void start(){
        frame = new JFrame(gameObject.getName());
        frame.setSize(250,400);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        display();
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    private void display(){
        for(int i = 0; i < gameObject.components.size();i++){
            Component component = gameObject.components.get(i);
            if(component instanceof TransformComponent){
                add(new Vector2dGUI("Position",((TransformComponent) component).transform.position));
                add(new Vector2dGUI("Rotation",((TransformComponent) component).transform.rotation));
                add(new Vector2dGUI("Scale",((TransformComponent) component).transform.scale));
            }else if(component instanceof SpriteRendererComponent){
                frame.add(new JLabel("Sprite Component:"));
                add(new SpriteGUI("Sprite",((SpriteRendererComponent) component).getSprite()));
                Sprite s = ((SpriteRendererComponent) component).getSprite();
                add(new Vector2dGUI("offsets",new Vector2d(s.drawPosX,s.drawPosY)));
            }else if(component instanceof CollisionBox2dComponent){
                frame.add(new JLabel("Collision Component"));
                add(new Vector2dGUI("position",((CollisionBox2dComponent) component).collisionBox.position));
                Rectangle collisionBox = ((CollisionBox2dComponent) component).collisionBox;
                add(new Vector2dGUI("width",new Vector2d(collisionBox.width,collisionBox.height)));
            }
        }
    }

    public void add(fieldGUI f){
        frame.add((JPanel)f);
        fields.add(f);
    }

    @Override
    public void update() {
        for(int i = 0 ;i< fields.size();i++){
            fields.get(i).update();
        }
    }


}
