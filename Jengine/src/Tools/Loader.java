package Tools;

import Component.TransformComponent;
import Entity.GameObject;
import maths.Vector2d;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joshua on 2016/06/02.
 */
public class Loader {

    private static Map<String,GameObject> gameObjects = new HashMap<String ,GameObject>();

    public static GameObject instantiate(String name){
        return instantiate(name,null,new Vector2d(0,0),new Vector2d(1,1));
    }

    public static GameObject instantiate(String name,Vector2d pos){
        return instantiate(name,pos,new Vector2d(0,0),new Vector2d(1,1));
    }

    public static GameObject instantiate(String name,Vector2d pos,Vector2d rotation, Vector2d scale){
        if(gameObjects.containsKey(name)){
            GameObject temp = (GameObject) DeepCopy.copy(gameObjects.get(name));
            if(temp.transform != null){
                if(pos != null)temp.transform.position = pos;
                temp.transform.rotation = rotation;
                temp.transform.scale = scale;
            }else{
                temp.addComponent(new TransformComponent(pos.x,pos.y));
                temp.transform.position = pos;
                temp.transform.rotation = rotation;
                temp.transform.scale = scale;
            }
            gameObjects.put(name,temp);
            return temp;
        }
        GameObject temp = loadGameObjectFromFile("objects/"+name+".go");
        if(temp != null){
            if(temp.transform != null){
                if(pos != null)temp.transform.position = pos;
                temp.transform.rotation = rotation;
                temp.transform.scale = scale;
            }else{
                temp.addComponent(new TransformComponent(pos.x,pos.y));
                temp.transform.position = pos;
                temp.transform.rotation = rotation;
                temp.transform.scale = scale;
            }
            gameObjects.put(name,temp);
            return temp;
        }else{
            return loadGameObjectFromFile("objects/nullGameObject.go");
        }
    }

    public static GameObject loadGameObjectFromFile(String path){
        GameObject result = null;
        try
        {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            result = (GameObject) in.readObject();
            in.close();
            fileIn.close();
            return result;
        }catch(IOException i)
        {
            i.printStackTrace();
            return null;
        }catch(ClassNotFoundException c)
        {
            System.out.println("GameObject class not found");
            c.printStackTrace();
            return null;
        }

    }

    public static void saveGameObjectToFile(GameObject object,String name){
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(name+".go");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
