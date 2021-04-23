package com.group9.partysnake.gameElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Apple extends SuperEatable{


    public Apple (Viewport viewport){
        super(viewport);
        texture = new Texture("apple.png");
        System.out.println("I have an apple");
    }

}
