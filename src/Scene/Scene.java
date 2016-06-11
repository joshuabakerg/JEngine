package Scene;

import Component.CollisionBox2dComponent;
import Engine.Jengine;
import Entity.Entity;
import Entity.GameObject;
import graphics.Renderer;
import graphics.sprite.Sprite;
import maths.Rectangle;
import maths.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Scene {

	protected int width,height;
	protected Sprite sprite;
	protected Entity targetEntity;
	private Jengine engine;
	protected boolean followTargetEntity = false;
	protected int gravity = 0;
	protected List<GameObject> gameObjects = new ArrayList<GameObject>() ;
	protected List<Entity> entities = new ArrayList<Entity>();
	protected List<Entity> collisionEntities = new ArrayList<Entity>();
	private Entity staticCombinedEntity;

	public Scene(){}

	public Scene(Sprite sprite){
		this(sprite.width,sprite.height);
		this.sprite = sprite;
	}
	
	public Scene(int width,int height){
		this.sprite = null;
		this.width = width;
		this.height = height;
	}

	public void init(Jengine engine){
		this.engine = engine;
	}

	public void setGravity(int gravity){
		this.gravity = gravity;
	}

	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void update(){
		checkCollisions();
		for(int i = 0;i< entities.size();i++){
			if(entities.get(i).exists()){
				entities.get(i).update();
				if(!entities.get(i).isStatic())entities.get(i).setVector("gravity", new Vector2d(90, gravity), true,3);
			}else{
				entities.remove(i);

			}
		}
        for(int i = 0;i< gameObjects.size();i++){
			if(!gameObjects.get(i).isDeleted()){
				if(gameObjects.get(i).enabled){
					gameObjects.get(i).update();
				}
			}else{
				gameObjects.remove(i);
			}
        }
	}
	
	public void render(Renderer renderer){
		if(sprite != null)renderer.renderSprite(sprite, 0, 0);
		for(int i = 0;i< entities.size();i++){
			entities.get(i).render(renderer);
		}
        for(int i = 0;i< gameObjects.size();i++){
            if(gameObjects.get(i).enabled){
                gameObjects.get(i).render(renderer);
            }
        }
	}
	
	public Entity getEntity(double x,double y) {
		for(int i = 0 ; i < entities.size();i++){
			Entity e = entities.get(i);
			if(e.collisionBox.contains(x, y)){
				return e;
			}
		}
		return null;
	}

	private void checkCollisions(){
		for(int i = 0; i < entities.size();i++){
			Entity e = entities.get(i);
			Entity eTmp = null;
			if(!e.isStatic()){
			//if(true){
				eTmp = getEntityCollision(e.collisionBox, e);
				if(eTmp != null){
					e.onCollision(eTmp);
				}
			}
		}
	}
	
	public boolean entityCollision(double x,double y,Entity owner){
		for(int i = 0 ; i < entities.size();i++){
			Entity e = entities.get(i);
			if(!e.isSolid())continue;
			if(e == owner)continue;
			if(e.collisionBox.contains(x, y)){
				return true;
			}
		}
		return false;
	}
	
	public boolean entityCollision(Rectangle rec,Entity owner){
		List<Entity> e = collisionEntities;
		for(int i = 0 ; i < e.size();i++){
			if(!e.get(i).isSolid())continue;
			if(e.get(i) == owner)continue;
			if(e.get(i).collisionBox.contains(rec))return true;
		}
		return false;
	}
	public boolean gameObjectCollision(Rectangle rec,GameObject owner,List<GameObject> ignore){
		List<GameObject> e = gameObjects;
		for(int i = 0 ; i < e.size();i++){
			if(e.get(i).getName().equals("componentTool"))continue;
			CollisionBox2dComponent temp = (CollisionBox2dComponent) e.get(i).find("collisionBox2d");
			if(e.get(i).transform.position.distance(owner.transform.position)>=50)continue;
			if(temp == null)continue;
			if(e.get(i) == owner)continue;
			if(ignore.contains(e.get(i)))return false;
			if(temp.collisionBox.contains(rec))return true;
		}
		return false;
	}

	public boolean gameObjectCollision(Rectangle rec,GameObject owner){
		List<GameObject> e = gameObjects;
		for(int i = 0 ; i < e.size();i++){
			CollisionBox2dComponent temp = (CollisionBox2dComponent) e.get(i).find("collisionBox2d");
			if(temp == null)continue;
			if(e.get(i) == owner)continue;
			if(temp.collisionBox.contains(rec))return true;
		}
		return false;
	}

	public GameObject findGameObject(String name){
		for(int i = 0; i < gameObjects.size();i++){
			if(gameObjects.get(i).getName().equals(name))return gameObjects.get(i);
		}
		return null;
	}

	public Jengine getEngine() {
		return engine;
	}

	public Entity getEntityCollision(Rectangle rec, Entity owner){
		for(int i = 0 ; i < entities.size();i++){
			Entity e = entities.get(i);
			if(e == owner)continue;
			if(e.collisionBox.contains(rec)){
				return e;
			}
		}
		return null;
	}
	
	public void setTargetEntity(Entity entity){
		targetEntity = entity;
		followTargetEntity = true;
	}
	
	public void followTarget(boolean follow){
		followTargetEntity = follow;
	}
	
	public void addEntity(Entity entity){
		entity.init(this);
		entities.add(entity);
		if(entity.isStatic()){
			if(staticCombinedEntity == null) {
				staticCombinedEntity = new Entity(entity.getX(),entity.getY(), entity.getSprite());
				collisionEntities.add(staticCombinedEntity);
			}
			else
				staticCombinedEntity.collisionBox.add(entity.collisionBox);
		}else {
			collisionEntities.add(entity);
		}
		System.out.println();
		
	}

    public void addGameObject(GameObject gameObject){
        gameObjects.add(gameObject);
		gameObject.Awake();
    }
	
	public void removeEntity(int id){
		for(int i = 0; i < entities.size();i++){
			if(entities.get(i).getId() == id){
				entities.remove(i);
			}
				
		}
	}
	
}
