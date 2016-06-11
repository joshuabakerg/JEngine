package Component.Graphics;

import Component.Component;
import graphics.Renderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Joshua on 2016/06/02.
 */
public class ImageRendererComponent extends Component {

    transient BufferedImage image;

    public ImageRendererComponent(BufferedImage image){
        super("imageRenderer");
        this.image = image;
    }

    public ImageRendererComponent(String path){
        super("imageRenderer");
        try {
            this.image = ImageIO.read(ImageRendererComponent.class.getResource(path));
        } catch (IOException e) {
            System.err.println("could not load image from path "+path);
            e.printStackTrace();
        }
    }

    public void render(Renderer renderer){
        renderer.g.drawImage(image,(int)gameObject.transform.position.x,(int)gameObject.transform.position.y,null);
    }

}
