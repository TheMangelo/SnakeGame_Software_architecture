package com.group9.partysnake.gameElements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.css.Rect;

public class Apple extends SuperEatable{

    private Rectangle rectangle;

    public Apple (){
        super();
        texture = new Texture("apple.png");
        this. rectangle = new Rectangle(0,0,texture.getHeight(),texture.getWidth());
        System.out.println("I have an apple");
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
