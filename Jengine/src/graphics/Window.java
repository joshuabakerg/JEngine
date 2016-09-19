package graphics;

import graphics.input.Keyboard;
import graphics.input.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Window extends Canvas{

	private static final long serialVersionUID = -1874393435789483805L;
	private JFrame frame = new JFrame();
	public int height;
	public int width;
	private int scale = 1;
	private BufferedImage image;
	private int[] pixels;
	private int clearColour = 0x255F73;
	private Keyboard keys;
	private Mouse mouse;
	
	public Window(int width, int height){
		this.frame = new JFrame();
		this.width = width/scale;
        this.height = height/scale;
        this.setSize(this.width*scale,this.height*scale);
        this.frame.setPreferredSize(new Dimension(width, height));
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.add(this);
		this.frame.pack();
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
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
		this.frame = new JFrame();
		this.scale = scale;
		this.width = width/scale;
		this.height = height/scale;
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setPreferredSize(new Dimension(width, height));
		this.frame.add(this);
		this.frame.pack();
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
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
		frame.setTitle(title);
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
	
	public void setTitle(String title){
		frame.setTitle(title);
	}
	
	public String getTitle(){
		return frame.getTitle();
	}

	public void clear(){
		for(int i = 0;i < pixels.length;i++)
			pixels[i] = clearColour;
	}


	private BufferStrategy bs;
	private Graphics g;
	public Graphics beginDisplay(){
		if(image == null)return null;
		bs = getBufferStrategy();
		if (bs == null){
			createBufferStrategy(3);
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
