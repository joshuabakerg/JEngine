package graphics;

import graphics.input.Keyboard;
import graphics.input.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Window extends JFrame{

	private static final long serialVersionUID = -1874393435789483805L;
	public int height;
	public int width;
    private Canvas canvas;
	private int scale = 1;
	private BufferedImage image;
	private int[] pixels;
	private int clearColour = 0x255F73;
	private Keyboard keys;
	private Mouse mouse;
	
	public Window(int width, int height){
		this.width = width/scale;
        this.height = height/scale;
        this.canvas = new Canvas();
        this.canvas.setSize(this.width*scale,this.height*scale);
        this.canvas.setVisible(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(canvas);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		keys = new Keyboard();		
		addKeyListener(keys);
		mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Window(int width, int height, int scale){
		this.scale = scale;
		this.width = width/scale;
		this.height = height/scale;
        this.setSize(width,height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.canvas = new Canvas();
        this.canvas.setSize(new Dimension(width, height));
        this.canvas.setVisible(true);
        this.canvas.setFocusable(false);
        this.add(canvas);
        this.pack();
        requestFocus();
		keys = new Keyboard();		
		addKeyListener(keys);
		mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public float getScale(){
		return scale;
	}
	
	public Window(int width, int height, String title){
		this(width,height);
		this.setTitle(title);
	}
	
	public void setBufferedImage(BufferedImage image){
		this.image = image;
		this.pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}
	
	public void setPixels(int[] pixels){
		this.setPixels(pixels);
	}
	
	public int[] getPixels(){
		return pixels;
	}
	
	public Keyboard getKeyboard(){
		return keys;
	}
	
	public Mouse getMouse(){
		return mouse;
	}


	public void clear(){
		for(int i = 0;i < pixels.length;i++)
			pixels[i] = clearColour;
	}


	private BufferStrategy bs;
	private Graphics g;
	public Graphics beginDisplay(){
		if(image == null)return null;
		bs = this.canvas.getBufferStrategy();
		if (bs == null){
			this.canvas.createBufferStrategy(3);
				return null;
		}
		g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,width*scale,height*scale);
		g.drawImage(image, 0, 0,width*scale , height*scale, null);
		return g;
	}

	public void endDisplay(){
		if(g == null || bs  == null)return;
		g.dispose();
		bs.show();
	}

	
}
