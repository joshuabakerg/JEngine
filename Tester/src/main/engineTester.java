package main;

import Engine.Jengine;
import Entity.GameObject;
import Tools.DeepCopy;
import Tools.Loader;

/**
 * Created by Joshua on 2016/05/31.
 */
public class engineTester {

    private static Jengine test;

    public static void main(String[] args){
        GameObject test = Loader.instantiate("player");
        GameObject test2 = (GameObject)DeepCopy.copy2(test);
        System.out.println();
        System.out.println();
    }

    private static void init(){


    }


}
