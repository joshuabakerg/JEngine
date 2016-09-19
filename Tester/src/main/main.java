package main;

import Component.Graphics.SpriteRendererComponent;
import Component.Tools.ComponentTool;
import Engine.Jengine;
import Entity.GameObject;
import Networking.Networker;
import Scene.Scene;
import Tools.Loader;
import graphics.Renderer;
import graphics.Window;
import graphics.input.Keyboard;
import graphics.input.Mouse;
import maths.Vector2d;

import javax.swing.*;

public class main {
	private static Renderer renderer ;
	public static Window window ;
	private static Keyboard keys ;
	private static Mouse mouse ;
	private static Jengine engine;
	private static Scene scene,loadingScene ;
	private static Networker network;
	private static Boolean run = true;
	private static boolean multi = false;
	private static Vector2d playerPos = new Vector2d(500,450);
	
	
	public static void main(String[] args) {
		
		if(run){
			//window = new Window(1368, 768,1);
			window = new Window(900, 600,2);
			renderer = new Renderer(window);
			//scene = new Scene(new Sprite("/textures/test.jpg"));
			scene = new Scene();
			loadingScene = new Scene();
			engine = new Jengine(window, renderer, loadingScene);
			keys = Jengine.keys;
			mouse = engine.mouse;
			Prefabs.init();
			if(multi){
				network =  new Networker(JOptionPane.showInputDialog("Enter name","joshua"), JOptionPane.showInputDialog("enter ip address","localhost"), Integer.parseInt(JOptionPane.showInputDialog("port","90")));
				engine.setNetworker(network);
			}
			loop();
		}
	}
	
	public static void loop(){
		engine.start();
		//loadingScene.addGameObject(new );
		scene.setGravity(2);
		//if(multi)network.addEntity(player);
		scene.addGameObject(Loader.instantiate("background"));
		double before = System.nanoTime();
		for(int i = 0; i<50;i++){
			for(int z = 0 ;z < 8;z++){
				GameObject go = Loader.instantiate("grass",new Vector2d(i*16+500,z*16+500));
				if(z >= 1){
					((SpriteRendererComponent)go.find("spriteRenderer")).setSprite(Prefabs.floor_sprite1);
					scene.addGameObject(go);
                }else{
                    ((SpriteRendererComponent)go.find("spriteRenderer")).setSprite(Prefabs.floor_sprite);
                    scene.addGameObject(go);
				}
			}
		}

		for(int i = 0; i<5;i++){
			for(int z = 0 ;z < 2;z++){
				//scene.addGameObject(Prefabs.createGrass(i*16+(500+16*10), z*16+(500-16*2)));
				scene.addGameObject(Loader.instantiate("grass",new Vector2d(i*16+(500+16*10), z*16+(500-16*2))));
			}
		}
		for(int i=0;i<10;i++){
			//scene.addGameObject(Prefabs.createGrassy(i*60+500, 500-8));
			scene.addGameObject(Loader.instantiate("grassy", new Vector2d(i*60+500, 500-8)));
		}
		for(int i = 0 ; i < 5 ; i++){
			//scene.addGameObject(Prefabs.createTree(500+100*i,500-8));
			GameObject tmp = Loader.instantiate("tree", new Vector2d(500+100*i,500-8));
			scene.addGameObject(tmp);
		}

		test();
		engine.changeScene(scene);
		double after = System.nanoTime();
		System.out.println("scene object load time: "+(after-before)/1000000000);
	}



	public static void test(){

		GameObject player = Loader.instantiate("player",new Vector2d(530+16*15,450));
		scene.addGameObject(player);
		scene.addGameObject(Loader.instantiate("tank",new Vector2d(530+16*17,500-8)));
		scene.addGameObject(Loader.instantiate("camera"));
		ComponentTool cTool = new ComponentTool(player);
		GameObject gO = new GameObject("componentTool");
		gO.addComponent(cTool);
		scene.addGameObject(gO);

		//scene.addGameObject(Prefabs.follower);
	}

}
