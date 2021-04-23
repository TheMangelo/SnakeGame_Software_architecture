package com.group9.partysnake.gameElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Apple extends SuperEatable{


    public Apple (){
        super();
        texture = new Texture("apple.png");
        System.out.println("I have an apple");
    }

}
