package com.group9.partysnake.gamestate;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class SettingsState extends State {
    private Stage stage;
    private Table table;
    private Button skinButtonLeft, skinButtonRight,
                   backgroundButtonLeft, backgroundButtonRight;

    private Texture skin, background;

    public SettingsState(GameStateManager gsm) {
        super(gsm);

        this.stage = new Stage();
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        this.stage.act(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        clearScreen();
        this.stage.draw();
    }
}
