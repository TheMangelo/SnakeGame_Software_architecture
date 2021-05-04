package com.group9.partysnake.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.group9.partysnake.gameElements.Apple;
import com.group9.partysnake.gameElements.Snake;

public class SinglePlayerState extends State {
    private Snake snakeInstance;
    private Apple apple;
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

    public SinglePlayerState(GameStateManager gsm) {
        super(gsm);
        show();
    }

    private void drawGrid() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int x = 0; x < Gdx.graphics.getWidth(); x+= GRID_CELL){
            for (int y = 0; y < Gdx.graphics.getHeight(); y+= GRID_CELL){
                shapeRenderer.rect(x,y, GRID_CELL, GRID_CELL);
            }
        }
        shapeRenderer.end();
    }

    public void show () {
        shapeRenderer = new ShapeRenderer();
        snakeInstance = new Snake();
        apple = new Apple();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            snakeInstance.setSnakeDirection(LEFT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            snakeInstance.setSnakeDirection(RIGHT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            snakeInstance.setSnakeDirection(UP);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            snakeInstance.setSnakeDirection(DOWN);
        }
    }

    @Override
    public void update(float dt) {
        if (snakeInstance.isHasHit()) {
            System.out.println("Game over");
            gsm.pop();
        }
        handleInput();
        timer -= dt;
        if (timer <= 0) {
            snakeInstance.updateSnake();
            snakeInstance.checkSnakeEat(apple);
            apple.checkAndPlace(snakeInstance);
            timer = MOVE_TIME;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        clearScreen(); //<----- Denne må være før hahah hvis ikke så clearer den før den tegner hver gang
        drawGrid();
        draw(sb);
    }

    public void draw(SpriteBatch batch){
        batch.begin();
        snakeInstance.draw(batch);
        apple.draw(batch);
        batch.end();
    }
}
