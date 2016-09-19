package graphics.sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

public class Sprite implements Serializable{

	public int[] pixels;
	private String path;
	public int width, height;
	public int drawPosX = 0,drawPosY = 0;
	
	public static SpriteSheet sheet = new SpriteSheet("/textures/sheets/player_sheet.png", 32);
	public static Sprite player_sprite = new Sprite(sheet,0,0);
	
	public Sprite(int[] pixels,int width ,int height){
		this.pixels = pixels;
		this.width = width;
		this.height = height;
	}

	public void invertSprite(){
		int[] newPixels = new int[pixels.length];
		int imageWidth = width;
		for (int i = 0; i < pixels.length; i++) {
			newPixels[i] = pixels[i - 2 * (i % imageWidth) + imageWidth - 1];
		}
		for(int i = 0;i<newPixels.length;i++){
			pixels[i] = newPixels[i];
		}
	}

	public Sprite(SpriteSheet sheet,int xPos,int yPos){
		this.path = sheet.getPath();
		if(xPos*sheet.getSpriteSize()>=sheet.getWidth()){
			System.err.println("Constuctor Sprite(sheet,xPos,yPos) xPos "+xPos+" is out of bounds");
			xPos = 0;
		}
		if(yPos*sheet.getSpriteSize()>=sheet.getheight()){
			System.err.println("Constuctor Sprite(sheet,xPos,yPos) yPos "+yPos+" is out of bounds");
			yPos = 0;
		}
		pixels = new int[sheet.getSpriteSize()*sheet.getSpriteSize()];
		for(int x = 0; x < sheet.getSpriteSize();x++){
			for(int y = 0; y < sheet.getSpriteSize();y++){
				pixels[x+y*sheet.getSpriteSize()] = sheet.getPixels()[(x+(xPos*sheet.getSpriteSize()))+(y+(yPos*sheet.getSpriteSize()))*sheet.getWidth()];
			}
		}
		width =height= sheet.getSpriteSize();
	}
	
	public Sprite(String path){
		try {
			BufferedImage image = ImageIO.read(Sprite.class.getResource(path));
			this.path = path;
			pixels = new int[image.getWidth()*image.getHeight()];
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
			this.width = image.getWidth();
			this.height = image.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Sprite(String path , int width , int height){
		try {
			BufferedImage image = ImageIO.read(Sprite.class.getResource(path));
			this.path = path;
			pixels = new int[width*height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
			this.width = width;
			this.height = height;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPath() {
		return path;
	}
}
