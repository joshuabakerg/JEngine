package Engine;

import Networking.Networker;
import Scene.Scene;
import Script.Script;
import graphics.Renderer;
import graphics.Window;
import graphics.input.Keyboard;
import graphics.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Jengine implements Runnable{
	private Thread thread;
	private static Scene scene;
	public Window window;
	private Renderer renderer;
	public  static Keyboard keys;
	public static Mouse mouse;
	private boolean running = false;
	private String title;
	private Networker networker;
	private List<Script> scripts = new ArrayList<Script>();
	
	
	public Jengine(){
		this.window = new Window(600,400);
		this.renderer = new Renderer(window);
		this.scene = new Scene();
		this.thread = new Thread(this,"Jengine");
		keys = window.getKeyboard();
		mouse = window.getMouse();
	}
	
	public Jengine(Window window){
		this(window,new Renderer(window),null);
	}
	
	public Jengine(Window window,Renderer renderer){
		this(window,renderer,null);
	}
	
	public Jengine(Window window,Renderer renderer,Scene scene){
		this.window = window;
		this.renderer = renderer;
		if(scene == null)scene = new Scene();
		this.scene = scene;
		this.thread = new Thread(this,"Jengine");
		keys = window.getKeyboard();
		mouse = window.getMouse();
	}

	public void changeWindow(Window window){
		this.window = window;
		this.keys = window.getKeyboard();
		this.mouse = window.getMouse();
		renderer.changeWindow(window);

	}

	public void addScript(Script script){
		scripts.add(script);
	}
	
	public void setNetworker(Networker networker){
		this.networker = networker;
	}


	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		String fpsUps = null;
		while (running){
			long now = System.nanoTime();
			long nowMs = System.currentTimeMillis();
			delta += (now-lastTime) / ns;
			lastTime = now; 
			while (delta >= 1 ){
				update();
				updates++;
				delta --;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println(updates + "ups, "+ frames + " fps ");
				fpsUps = title + "  |  " +updates + "ups, "+ frames + " fps, ";
				updates = 0;
				frames = 0;
			}
			window.setTitle(fpsUps+(System.currentTimeMillis()-nowMs)+" ms");
			Time.deltaTime = (System.nanoTime()-now)/1000000000.0f;
		}
		stop();
	}
	
	public void changeScene(Scene scene){
		this.scene = scene;
	}
	
	private void update(){
		if(networker!=null)networker.update();
		if(scene != null)scene.update();
		for(int i = 0;i <  scripts.size();i++){
			scripts.get(i).update();
		}
		
	}
	
	private void render(){

		Graphics g = window.beginDisplay();
		window.clear();
		if(g!= null){
			renderer.g = g;
			if(scene != null)scene.render(renderer);
			if(networker!=null){
				networker.render(renderer);
			}
		}
		window.endDisplay();
	}
	
	
	public synchronized void start(){
		running = true;
		startScripts();
		thread.start();
	}
	
	public synchronized void stop(){
		running = false;
		closeScripts();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static Scene getScene() {
		return scene;
	}

	private void startScripts(){
		for(int i = 0; i< scripts.size();i++){
			scripts.get(i).start();
		}
	}
	
	private void closeScripts(){
		for(int i = 0; i< scripts.size();i++){
			scripts.get(i).onClose();
		}
	}

	
}
