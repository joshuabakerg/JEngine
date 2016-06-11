package graphics.sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
	
	private String path;


	private int[] pixels;
	private int width,height;
	private int spriteSize;
	
	
	public SpriteSheet(String path,int spriteSize){
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			this.path = path;
			this.width = image.getWidth();
			this.height = image.getHeight();
			this.spriteSize = spriteSize;
			this.path = path;
			pixels = new int[width*height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int[] getPixels(){
		return pixels;
	}
	
	public int getSpriteSize(){
		return spriteSize;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getheight(){
		return height;
	}

	public String getPath() {
		return path;
	}


}
