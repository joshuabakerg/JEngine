package Component.Tools.GUI;

import maths.Vector2d;

import javax.swing.*;

/**
 * Created by Joshua on 2016/05/29.
 */
public class Vector2dGUI extends JPanel implements fieldGUI{

    private Vector2d vec;
    private JLabel name ;
    private JTextField field1;
    private JTextField field2;


    public Vector2dGUI(String name ,Vector2d vec){
        this.vec = vec;
        this.name = new JLabel(name);
        field1 = new JTextField(String.valueOf(vec.x));
        field2 = new JTextField(String.valueOf(vec.y));
        add(this.name);
        add(field1);
        add(field2);
        setVisible(true);
    }

    @Override
    public void update() {
        field1.setText(String.valueOf(vec.x));
        field2.setText(String.valueOf(vec.y));
    }
}
