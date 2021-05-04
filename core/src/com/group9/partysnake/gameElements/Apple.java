package com.group9.partysnake.gameElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.css.Rect;

public class Apple extends SuperEatable {

    private Rectangle rectangle;

    public Apple () {
        texture = new Texture("apple.png");
        rectangle = new Rectangle(0,0, texture.getHeight(),texture.getWidth());
        System.out.println("I have an apple");
    }

    @Override
    public void checkAndPlace(Snake snake1) {
        if (!isAvailable) {
            do {
                position.x = MathUtils.random(Gdx.graphics.getWidth()
                        / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
                position.y = MathUtils.random(Gdx.graphics.getHeight()
                        / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
                isAvailable = true;
            } while (position.x == snake1.getSnakeX() && position.y == snake1.getSnakeY());
            rectangle.setPosition(position.x, position.y);
        }
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
