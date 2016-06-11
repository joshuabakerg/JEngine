package Component.Tools.GUI;

import graphics.sprite.Sprite;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Joshua on 2016/05/29.
 */
public class SpriteGUI extends JPanel implements fieldGUI {

    private Sprite sprite;
    private JLabel name ;
    private BufferedImage image;


    public SpriteGUI(String name,Sprite sprite){
        this.sprite = sprite;
        this.name = new JLabel(name);
        try {
            image = ImageIO.read(SpriteGUI.class.getResource(sprite.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(this.name);
        add(new JSprite(sprite,3));
        //add(new JSprite(image));
        setVisible(true);
    }


    @Override
    public void update() {


    }
}
