package com.group9.partysnake.gamestate;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.group9.partysnake.gameElements.Apple;
import com.group9.partysnake.gameElements.Snake;

import java.awt.Color;

//This class is rendered by the Gamescreen
public class SinglePlayerState  {


    private Snake snakeInstance;
    private Apple apple;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private boolean directionSet = false;
    private boolean hasHit = false;

    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;


    private static final float MOVE_TIME = 0.1F;  //Hvor fort slangen skal bevege seg og oppdatere movesa
    private float timer = MOVE_TIME;     // Definerer en timer
    private static final int SNAKE_MOVEMENT = 32; // Antall pixler som slangen skal bevege seg
    private static final int GRID_CELL = 32;




    private void drawGrid() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int x = 0; x < Gdx.graphics.getWidth(); x+= GRID_CELL){
            for (int y = 0; y < Gdx.graphics.getHeight(); y+= GRID_CELL){
                shapeRenderer.rect(x,y, GRID_CELL, GRID_CELL);
            }
        }
        shapeRenderer.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.getRed(), Color.BLACK.getGreen(),
                Color.BLACK.getBlue(), Color.BLACK.getAlpha());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void show () {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        snakeInstance = new Snake();
        apple = new Apple();
    }

    private void queryInput() {
        boolean lPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean uPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        if (lPressed) {
            snakeInstance.setSnakeDirection(LEFT);
            System.out.println("HIEIHEIHE");
        }
        if (rPressed) snakeInstance.setSnakeDirection(RIGHT);
        if (uPressed) snakeInstance.setSnakeDirection(UP);
        if (dPressed) snakeInstance.setSnakeDirection(DOWN);
    }


    public void render(float delta) {
        timer -= delta;
        if (timer <= 0) {
            queryInput();
            snakeInstance.updateSnake();
            timer = MOVE_TIME;

        }

        clearScreen(); //<----- Denne må være før hahah hvis ikke så clearer den før den tegner hver gang
        drawGrid();
        draw();

    }

    public void draw(){
        batch.begin();
        snakeInstance.draw(batch);
        snakeInstance.checkSnakeEat(apple);
        apple.checkAndPlace(snakeInstance);
        apple.draw(batch);
        batch.end();
    }

}