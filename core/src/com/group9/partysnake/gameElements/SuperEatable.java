package com.group9.partysnake.gameElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public abstract class  SuperEatable {
    Texture texture;
    boolean isAvailable = false;
    Vector2 position = new Vector2(0,0);
    int SNAKE_MOVEMENT = 32;



    //Denne funksjonen sjekker at den ikke plasseres oppå en av slangene

    public void checkAndPlace(Snake snake1, Snake snake2) {
        if (isAvailable) {
            do {
                position.x = MathUtils.random(Gdx.graphics.getWidth()
                        / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
                position.y = MathUtils.random(Gdx.graphics.getHeight()
                        / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
                isAvailable = false;
            } while (position.x == snake1.getPosition().x && position.y == snake1.getPosition().y
                    ||
                    position.x == snake2.getPosition().x && position.y == snake2.getPosition().y
            );
        }
    }


    public void checkAndPlace(Snake snake1) {
        if (!isAvailable) {
            do {
                position.x = MathUtils.random(Gdx.graphics.getWidth()
                        / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
                position.y = MathUtils.random(Gdx.graphics.getHeight()
                        / SNAKE_MOVEMENT - 1) * SNAKE_MOVEMENT;
                isAvailable = true;
            } while (position.x == snake1.getPosition().x && position.y == snake1.getPosition().y);
        }
    };

    public void draw(Batch batch){
        if (isAvailable) {
            batch.draw(texture, position.x, position.y);
        }
    }


}
