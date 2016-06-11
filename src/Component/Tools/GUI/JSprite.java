package Component.Tools.GUI;

import graphics.sprite.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by Joshua on 2016/05/29.
 */
public class JSprite extends Canvas {

    private BufferedImage image;
    private Sprite sprite;
    private int scale;

    public JSprite(Sprite sprite){
        this(sprite,1);
    }

    public JSprite(Sprite sprite, int scale){
        this.sprite = sprite;
        this.scale = scale;
        image = new BufferedImage(sprite.width,sprite.height, BufferedImage.TYPE_INT_RGB);
        int[] px = ((DataBufferInt)image.getRaster().getDataBuffer()).getData() ;
        for(int i = 0; i < sprite.pixels.length;i++){
            px[i] = sprite.pixels[i];
        }
        this.setSize(image.getWidth()*scale,image.getHeight()*scale);
        repaint();
    }




    public void paint(Graphics g){
        g.drawImage(image,0,0,image.getWidth()*scale,image.getHeight()*scale,null);
        g.setColor(Color.black);
        g.drawLine(0,sprite.drawPosY*scale,this.getWidth(),sprite.drawPosY*scale);
        g.drawLine(sprite.drawPosX*scale,0,sprite.drawPosX*scale,this.getHeight());
    }

}
