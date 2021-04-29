package com.group9.partysnake.gamestate;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ScoreState extends State{
    private Stage stage;
    private Button backButton;
    private int score;


    public ScoreState(GameStateManager gsm) {
        super(gsm);

        stage = new Stage();

        makeButton();

        stage.addActor(backButton);
    }

    private void makeButton() {
        TextureRegion backArrow = new TextureRegion(new Texture("return_arrow.png"));
        TextureRegionDrawable backDrawable = new TextureRegionDrawable(backArrow);

        backButton = new Button(backDrawable);

        backButton.setTransform(true);
        backButton.setScale(0.5f);
        backButton.setPosition(20, 310);
    }

    private void clickHandling() {
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.push(new MenuState(gsm));
            }
        });
    }


    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        stage.act();
    }

    @Override
    public void render(SpriteBatch sb) {
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
