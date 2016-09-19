package main;

import Component.*;
import Component.Component;
import Component.Graphics.SpriteRendererComponent;
import Component.Physics.AdvancedFollowerComponent;
import Component.Physics.FollowerComponent;
import Component.Physics.Physics2DComponent;
import Component.Physics.SimpleMover;
import Engine.Jengine;
import Entity.GameObject;
import Tools.Loader;
import graphics.Renderer;
import graphics.sprite.Sprite;
import graphics.sprite.SpriteSheet;
import maths.Vector2d;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Prefabs {

	private static boolean update = true;

	private Prefabs(){};
	
	public static SpriteSheet player_sheet = new SpriteSheet("/textures/sheets/player_sheet.png", 32);
	//public static Sprite player_sprite = new Sprite(player_sheet,0,0);
	//public static Sprite player_sprite = new Sprite(player_sheet,3,0);
	public static Sprite player_sprite = new Sprite("/textures/test.png");
	public static Sprite floor_sprite = new Sprite("/textures/dirt-grass.png");
	public static Sprite floor_sprite1 = new Sprite("/textures/dirt.png");
	public static Sprite grass_sprite = new Sprite("/textures/flower-grass.png");
	public static Sprite tank_sprite = new Sprite("/textures/tank.png");
	public static Sprite ball = new Sprite("/textures/ball.png");
	public static Sprite tree_sprite = new Sprite("/textures/tree.png");
	public static Sprite background_sprite = new Sprite("/textures/back2.png");

	public static GameObject player;
    public static GameObject follower;
	public static GameObject mainCamera;
	public static GameObject backgroundGO;
	
	public static void init(){

		setSpriteOffsets();
		if(update){
			double before = System.nanoTime();
			createTank(0,0);
			createGrassy(0,0);
			createGrass(0,0);
			createPlayer();
			createBackground();
			createTree(0,0);
			createBall(0,0);
			createFollower();
			createMainCamera();
			createBackground();
			double after = System.nanoTime();
			System.out.println("prefab saving time: "+(after-before)/1000000000);
		}
	}

    private static void setSpriteOffsets(){
        player_sprite.drawPosX = player_sprite.width/2;
        player_sprite.drawPosY = player_sprite.height/2;
        floor_sprite.drawPosX = floor_sprite.width/2;
        floor_sprite.drawPosY = floor_sprite.height/2;
		floor_sprite1.drawPosX = floor_sprite1.width/2;
		floor_sprite1.drawPosY = floor_sprite1.height/2;
		grass_sprite.drawPosX = grass_sprite.width/2;
		grass_sprite.drawPosY = grass_sprite.height;
		ball.drawPosX = ball.width/2;
		ball.drawPosY = ball.height/2;
		tree_sprite.drawPosX = tree_sprite.width/2;
		tree_sprite.drawPosY = tree_sprite.height;

    }

	public static void createBackground(){
		background_sprite.drawPosX = background_sprite.width/2;
		background_sprite.drawPosY = background_sprite.height/2+20;
		backgroundGO = new GameObject("background");
		backgroundGO.addComponent(new TransformComponent(530+16*15,450));
		backgroundGO.addComponent(new SpriteRendererComponent(background_sprite));
		backgroundGO.addComponent(new FollowerComponent(null));
		backgroundGO.addComponent(new Component(){
			boolean foundTarget = false;
			public void update(){

				if(!foundTarget){
					GameObject playerGO = Jengine.getScene().findGameObject("player");
					if(playerGO != null){
						FollowerComponent fC = (FollowerComponent) gameObject.find("follower");
						if(fC != null) fC.target = playerGO.transform.position;
						foundTarget = true;
					}
				}
			}

			});
		Loader.saveGameObjectToFile(backgroundGO,"objects/background");
	}

	public static GameObject createTank(double x, double y){
		tank_sprite.drawPosX = tank_sprite.width/2;
		tank_sprite.drawPosY = tank_sprite.height;
		GameObject gO = new GameObject("tank");
		gO.addComponent(new TransformComponent(x,y));
		gO.addComponent(new SpriteRendererComponent(tank_sprite));
		Loader.saveGameObjectToFile(gO,"objects/tank");
		return gO;
	}

	public static GameObject createGrassy(double x,double y){
		GameObject gO = new GameObject("grass");
		gO.addComponent(new TransformComponent(x,y));
		gO.addComponent(new SpriteRendererComponent(grass_sprite));
		Loader.saveGameObjectToFile(gO,"objects/grassy");
		return gO;
	}

	public static GameObject createGrass(double x,double y){
		return createGrass(x,y,floor_sprite);
	}

	public static GameObject createGrass(double x,double y,Sprite sprite){
		GameObject gO = new GameObject("grass");
		gO.addComponent(new TransformComponent(x,y));
		gO.addComponent(new SpriteRendererComponent(sprite));
		gO.addComponent(new CollisionBox2dComponent(sprite.width,sprite.height));
		Loader.saveGameObjectToFile(gO,"objects/grass");
		return gO;
	}

	private static void createPlayer(){
		player = new GameObject("player");
		player.addComponent(new TransformComponent(530+16*15,450));
		player.addComponent(new SpriteRendererComponent(Prefabs.player_sprite));
		//gm.addComponent(new BasicMovementComponent());
		player.addComponent(new CollisionBox2dComponent(player_sprite.width,player_sprite.height));
		player.addComponent(new Physics2DComponent());
		player.addComponent(new Component(){
			private Physics2DComponent p2d ;
			private int timer = 0;

			public void update(){
				if(timer == 10000000)timer = 0;
				timer ++;
				if(p2d == null){
					p2d = (Physics2DComponent) gameObject.find("physics2D");
				}
				if(gameObject.keys().isKeyPressed(KeyEvent.VK_D)){
					p2d.setVector("moveRight",new Vector2d(0,2),true,0.5f);
				}
				if(gameObject.keys().isKeyPressed(KeyEvent.VK_A)){
					p2d.setVector("moveLeft",new Vector2d(180,2),true,0.5f);
				}
				if(gameObject.keys().isKeyPressed(KeyEvent.VK_W)){
					p2d.setVector("moveUp",new Vector2d(-90,2),true,0.5f);
				}
				if(gameObject.keys().isKeyPressed(KeyEvent.VK_S)){
					p2d.setVector("moveDown",new Vector2d(90,2),true,0.5f);
				}
				if(gameObject.keys().isKeyPressed(KeyEvent.VK_SPACE)){
					p2d.setVector("moveDown",new Vector2d(-90,7),true,0.5f);
				}

				if(gameObject.mouse().getButton() == 1){
					if(timer%10==0) {
						GameObject tBall = createBall(gameObject.transform.position.x,gameObject.transform.position.y);

						double dx = gameObject.mouse().getX() - (900/2);
						double dy = gameObject.mouse().getY() - (600/2);
						double dir = Math.atan2(dy, dx);
						dir = Math.toDegrees(dir);
						System.out.println(dir);
						//((SimpleMover)tBall.find("simpleMover")).begin(10,dir);
						((Physics2DComponent)tBall.find("physics2D")).setVector("move",new Vector2d(dir,10),false,0.4f);
						((Physics2DComponent)tBall.find("physics2D")).ignore(gameObject);
						((Physics2DComponent)gameObject.find("physics2D")).ignore(tBall);
						System.out.println(((Physics2DComponent)gameObject.find("physics2D")).getIgnoredGameObjects().size());
						Jengine.getScene().addGameObject(tBall);
					}
				}

			}
		});
		Loader.saveGameObjectToFile(player,"objects/player");
	}

	public static GameObject createTree(double x ,double y){
		GameObject result = new GameObject("tree");
		result.addComponent(new TransformComponent(x,y));
		result.addComponent(new SpriteRendererComponent(tree_sprite));
		Loader.saveGameObjectToFile(result,"objects/tree");
		return result;
	}

	public static GameObject createBall(double x,double y){
		GameObject result = new GameObject("ball");
		result.addComponent(new TransformComponent(x,y));
		result.addComponent(new SpriteRendererComponent(Prefabs.ball));
		result.addComponent(new CollisionBox2dComponent(ball.width,ball.height));
		//result.addComponent(new SimpleMover());
		result.addComponent(new Physics2DComponent());
		result.addComponent(new LiveDurationComponent(1f,true));
		Loader.saveGameObjectToFile(result,"objects/ball.go");
		return result;
	}

	private static void createFollower(){
        follower = new GameObject("follower");
        follower.addComponent(new TransformComponent(560,450));
        follower.addComponent(new SpriteRendererComponent(Prefabs.floor_sprite));
        AdvancedFollowerComponent afc = new AdvancedFollowerComponent(player.transform.position);
		afc.speed = 10f;
		follower.addComponent(afc);
		Loader.saveGameObjectToFile(follower,"objects/follower");
	}

	private static void createMainCamera(){
		mainCamera = new GameObject("mainCamera");
		mainCamera.addComponent(new TransformComponent(530,450));
		mainCamera.addComponent(new CameraComponent());
		//mainCamera.addComponent(new AdvancedFollowerComponent(player.transform.position));
        mainCamera.addComponent(new FollowerComponent(null));
		mainCamera.addComponent(new Component(){
			AdvancedFollowerComponent follower;
			boolean keyMove = false;
			boolean foundTarget = false;
			public void update(){

				if(!foundTarget){
					GameObject playerGO = Jengine.getScene().findGameObject("player");
					if(playerGO != null){
						FollowerComponent fC = (FollowerComponent) gameObject.find("follower");
						if(fC != null) fC.target = playerGO.transform.position;
						foundTarget = true;
					}
				}

				if(follower == null){
					follower = (AdvancedFollowerComponent) gameObject.find("advancedFollower");
				}

				if(gameObject.keys().isKeyPressed(KeyEvent.VK_ENTER)){
					if(keyMove){
						keyMove = false;
						follower.enabled = true;
					}else{
						keyMove = true;
						follower.enabled = false;
					}
				}
				if(keyMove){
					if(gameObject.keys().isKeyPressed(KeyEvent.VK_LEFT))gameObject.transform.position.x -= 5;
					if(gameObject.keys().isKeyPressed(KeyEvent.VK_RIGHT))gameObject.transform.position.x += 5;
					if(gameObject.keys().isKeyPressed(KeyEvent.VK_UP))gameObject.transform.position.y -= 5;
					if(gameObject.keys().isKeyPressed(KeyEvent.VK_DOWN))gameObject.transform.position.y += 5;

				}
			}
		});
		Loader.saveGameObjectToFile(mainCamera,"objects/camera");
	}

}
