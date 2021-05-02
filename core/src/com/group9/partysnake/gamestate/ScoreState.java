package com.group9.partysnake.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.group9.partysnake.PartySnake;

public class ScoreState extends State{
    private Stage stage;
    private Button backButton;
    private Texture background, highscore, name, score;
    private String names[];


    public ScoreState(GameStateManager gsm) {
        super(gsm);

        stage = new Stage();
        background = new Texture("background.png");
        highscore = new Texture("highscore.png");
        name = new Texture ("name.png");
        score = new Texture ("score.png");

        makeButton();
        clickHandling();

        Gdx.input.setInputProcessor(stage);
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
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(highscore, 130, 280, 400, 70);
        sb.draw(name, 240, 230, 70, 20);
        sb.draw(score, 470, 230, 70, 20);
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        highscore.dispose();
        name.dispose();
        score.dispose();
    }
}
