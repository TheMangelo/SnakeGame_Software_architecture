package com.group9.partysnake.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.group9.partysnake.PartySnake;
import com.group9.partysnake.gameElements.Apple;
import com.group9.partysnake.gameElements.Apple_rectangle;
import com.group9.partysnake.gameElements.Snake;

public class SinglePlayer extends State{



    private Snake snake1;
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

    @Override
    public void render(float delta) {

    }

    public void draw(SpriteBatch batch){
        batch.begin();
        snake1.draw(batch);

        apple.draw(batch);



        batch.end();
    }



    public SinglePlayer(GameStateManager gsm){
        super(gsm);

        //background = new Texture("grass_back.png");

        shapeRenderer = new ShapeRenderer();
        //snake1 = new Snake(new Texture("snakehead_purp.png"), new Texture("snakebody_purp.png"),10,10);
        snake1 = new Snake();

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
            snake1.updateSnake();
            timer = MOVE_TIME;

            apple.checkAndPlace(snake1);

            snake1.checkSnakeEat(apple);
        }

    }

    @Override
    public void render(SpriteBatch sb) {

        clearScreen(); //<----- Denne må være før hahah hvis ikke så clearer den før den tegner hver gang
        sb.begin();

        //sb.draw(background,0,0, PartySnake.WIDTH,PartySnake.HEIGHT);
        sb.end();
        draw(sb);
    }
}
