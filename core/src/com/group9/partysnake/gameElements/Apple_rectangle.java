package com.group9.partysnake.gameElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


public class Apple_rectangle extends SuperEatable{


    public Apple_rectangle (){
        super();
        texture = new Texture("apple.png");
        rectangle = new Rectangle(0,0,texture.getHeight(),texture.getWidth());
    }






}
