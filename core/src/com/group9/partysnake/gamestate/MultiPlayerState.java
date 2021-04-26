package com.group9.partysnake.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.group9.partysnake.PartySnake;
import com.group9.partysnake.gameElements.Apple;
import com.group9.partysnake.gameElements.Apple_rectangle;
import com.group9.partysnake.gameElements.Snake;

import java.awt.Color;

public class MultiPlayerState extends State{

    private Texture yellow_snake, yellow_snake_body;

    private Snake snake1,snake2;
    private Apple apple1;
    private Apple_rectangle apple;
    private ShapeRenderer shapeRenderer;

    private Texture background;

    private boolean directionSet = false;
    private boolean hasHit = false;

    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;


    private static final float MOVE_TIME = 0.15F;  //Hvor fort slangen skal bevege seg og oppdatere movesa
    private float timer = MOVE_TIME;     // Definerer en timer
    private static final int SNAKE_MOVEMENT = 32; // Antall pixler som slangen skal bevege seg
    private static final int GRID_CELL = 32;



    @Override
    public void show () {

    }

    private void queryInput() {
        boolean lPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean uPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        if (lPressed) snake1.setSnakeDirection(LEFT);
        if (rPressed) snake1.setSnakeDirection(RIGHT);
        if (uPressed) snake1.setSnakeDirection(UP);
        if (dPressed) snake1.setSnakeDirection(DOWN);
    }

    private void queryInput2() {
        boolean lPressed = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean rPressed = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean uPressed = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.S);
        if (lPressed) snake2.setSnakeDirection(LEFT);
        if (rPressed) snake2.setSnakeDirection(RIGHT);
        if (uPressed) snake2.setSnakeDirection(UP);
        if (dPressed) snake2.setSnakeDirection(DOWN);
    }


    @Override
    public void render(float delta) {

    }

    public void draw(SpriteBatch batch){
        batch.begin();
        snake1.draw(batch);
        snake2.draw(batch);

        apple.draw(batch);



        batch.end();
    }



    public MultiPlayerState(GameStateManager gsm){
        super(gsm);
        yellow_snake = new Texture("yellow_snake.png");
        yellow_snake_body = new Texture("yellow_snake_body.png");

        background = new Texture("grass_back.png");

        shapeRenderer = new ShapeRenderer();
        snake1 = new Snake();
        snake2 = new Snake(yellow_snake,yellow_snake_body, 100,100);
        apple = new Apple_rectangle();


    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        timer -= dt;
        if(timer <= 0){
            queryInput();
            queryInput2();
            snake1.updateSnake();
            snake2.updateSnake();
            timer = MOVE_TIME;

            apple.checkAndPlace(snake1);
            apple.checkAndPlace(snake2);

            snake1.checkSnakeEat(apple);
            snake2.checkSnakeEat(apple);
        }

    }

    @Override
    public void render(SpriteBatch sb) {

        clearScreen(); //<----- Denne må være før hahah hvis ikke så clearer den før den tegner hver gang
        sb.begin();
        sb.draw(background,0,0, PartySnake.WIDTH,PartySnake.HEIGHT);
        sb.end();
        draw(sb);
    }
}
