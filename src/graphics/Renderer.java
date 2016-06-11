package graphics;

import graphics.sprite.Sprite;
import maths.Vector2d;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Renderer{
	private Window window;
	private BufferedImage image;
	public Graphics g;
	public int[] pixels;
	private Vector2d offsets;
	private int transparentColour = 0xffff00ff;
	
	public Renderer(Window window){
		this.window = window;
		this.image = new BufferedImage(window.width,window.height,BufferedImage.TYPE_INT_RGB) ;
		this.pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		window.setBufferedImage(image);
		offsets = new Vector2d(0, 0);
	}

	public void changeWindow(Window window){
		this.window = window;
		this.image = new BufferedImage(window.width,window.height,BufferedImage.TYPE_INT_RGB) ;
		this.pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		window.setBufferedImage(image);
	}

	public int getWidth(){
		return window.width;
	}
	
	public int getHeight(){
		return window.height;
	}
	
	public void setOffset(Vector2d offset){
		this.offsets = offset;
	}
	public Vector2d getOffset(){
		return offsets;
	}
	
	public void renderText(int x,int y, String text){
		
	}
	
	public void renderColour(double x0,double y0,double width, double height, int colour){
		x0 += (offsets.x + getWidth()/2);
		y0 += (offsets.y + getHeight()/2);
		for(int x = (int)x0; x < x0 + width;x++){
			if(x<0||x >= window.width)continue;
			for(int y = (int)y0; y < y0 + height;y++){
				if(y < 0 || y >= window.height)continue;
				pixels[x + y*window.width] = colour;
			}
		}
	}

	public void renderSprite(Sprite sprite,double x0 ,double y0){
		renderSprite(sprite,x0,y0,false);
	}

    public void renderSprite(Sprite sprite,double x0 ,double y0,boolean useSpriteOffset){
        x0 += (offsets.x + getWidth()/2);
        y0 += (offsets.y + getHeight()/2);
        if(useSpriteOffset){
        	x0 -= sprite.drawPosX;
            y0 -= sprite.drawPosY;
        }
        for(int x = 0; x < sprite.width;x++){
            int xp = (int)(x+x0);
            if(xp < 0 || xp >=window.width)continue;
            for(int y = 0;y < sprite.height;y++){
                int yp = (int)(y+y0);
                if(yp < 0 || yp >= window.height)continue;
                int col = sprite.pixels[x + y * sprite.width];
                if(col == transparentColour)continue;
                pixels[xp + yp * window.width] = col ;
            }
        }
    }
	
}
