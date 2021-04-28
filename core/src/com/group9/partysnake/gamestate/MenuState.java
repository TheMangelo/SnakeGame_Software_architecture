package com.group9.partysnake.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.group9.partysnake.GameScreen;
import com.group9.partysnake.PartySnake;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


import java.util.ArrayList;
import java.util.Arrays;

public class MenuState extends State {
    private Texture title, setting, online, single, score;

    public Sprite sTitle, sSetting, sOnline, sSingle, sScore;

    private Button btnMenuPlay;
    private Button btnMenuOptions;

    private static final int GRID_CELL = 32;

    private int btn_height = 4 * GRID_CELL;
    private int btn_width = 6 * GRID_CELL;


    private int height = PartySnake.HEIGHT;
    private int width = PartySnake.WIDTH;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    boolean isPressed = false;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        title = new Texture("title.png");
        setting = new Texture("settings.png");
        online = new Texture("online.png");
        single = new Texture("single.png");
        score = new Texture("score.png");

        Texture snakeHead = new Texture("snakehead.png");



        sSetting = new Sprite(setting);
        sSetting.setPosition(0, GRID_CELL);

        sOnline = new Sprite(online);
        sOnline.setPosition(width-online.getWidth(), height-GRID_CELL*6);

        sSingle = new Sprite(single);
        sSingle.setPosition(0, height-GRID_CELL*6);

        sScore = new Sprite(score);
        sScore.setPosition(width-score.getWidth(), GRID_CELL);


        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera(PartySnake.HEIGHT, PartySnake.WIDTH);

    }





    @Override
    public void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                sSetting.setColor(1,1,0,1);
            } else{
                sSetting.setColor(1,1,1,1);

        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            sSingle.setColor(1,1,0,1);
        } else{
            sSingle.setColor(1,1,1,1);

        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            sOnline.setColor(1,1,0,1);
        } else{
            sOnline.setColor(1,1,1,1);

        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            sScore.setColor(1,1,0,1);
        } else{
            sScore.setColor(1,1,1,1);

        }
    }


    @Override
    public void update(float dt) {
        handleInput();
    }

    private void drawGrid() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int x = 0; x < width; x += GRID_CELL) {
            for (int y = 0; y < height; y += GRID_CELL) {
                shapeRenderer.rect(x,y, GRID_CELL, GRID_CELL);
            }
        }
        shapeRenderer.end();
    }


    @Override
    public void render(SpriteBatch sb) {
        clearScreen();
        draw(sb);
        //drawGrid();





    }

    public void dispose(){
        title.dispose();
        setting.dispose();
        online.dispose();
        single.dispose();
        score.dispose();
    }

    public void draw(SpriteBatch sb){
        sb.begin();
        //TITLE
        sb.draw(title, (width-title.getWidth())/2, height-title.getHeight());

        //Buttons
        //sb.draw(single, 0, height-GRID_CELL*6);
        sSingle.draw(sb);

        sSetting.draw(sb);

        sOnline.draw(sb);
        //sb.draw(online, width-online.getWidth(), height-GRID_CELL*6);

        //sb.draw(setting, 0, GRID_CELL);
        //sb.draw(score, width-score.getWidth(), GRID_CELL);

        sScore.draw(sb);


        sb.end();
    }
}