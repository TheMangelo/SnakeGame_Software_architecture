package com.group9.partysnake.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.group9.partysnake.PartySnake;

public class MenuState extends State {
    private Texture title, setting, online, single, score;

    private static final int GRID_CELL = 32;

    private int btn_height = 4 * GRID_CELL;
    private int btn_width = 6 * GRID_CELL;

    private int height = PartySnake.HEIGHT;
    private int width = PartySnake.WIDTH;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        title = new Texture("title.png");
        setting = new Texture("settings.png");
        online = new Texture("online.png");
        single = new Texture("single.png");
        score = new Texture("score.png");
    }


    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = PartySnake.HEIGHT - Gdx.input.getY();

            if ( (0 < x && x < setting.getWidth()) &&
                    (GRID_CELL*2 < y && y < GRID_CELL*2 + setting.getHeight()) ) {
                gsm.push(new SettingsState(gsm));
            } else if ( (width - online.getWidth() < x && x < width) &&
                    (height - GRID_CELL*8 < y && y < height - GRID_CELL*8 + online.getHeight()) ) {
                gsm.push(new LoginState(gsm, true));
            } else if ( (0 < x && x > score.getWidth()) &&
                    (GRID_CELL*2 < y && y < GRID_CELL*2 + score.getHeight()) ) {
                gsm.push(new LoginState(gsm, false));
            } else if ( (0 < x && x < single.getWidth()) &&
                    (height - GRID_CELL*8 < y && y < height - GRID_CELL*8 + single.getHeight()) ) {
                // TODO: SinglePlayerState should be pushed here when it is available
                // gsm.push(new SinglePlayerState());
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    private void drawGrid () {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int x = 0; x < width; x += GRID_CELL) {
            for (int y = 0; y < height; y += GRID_CELL) {
                shapeRenderer.rect(x, y, GRID_CELL, GRID_CELL);
            }
        }
        shapeRenderer.end();
    }


    @Override
    public void render(SpriteBatch sb) {
        clearScreen();
        draw(sb);
        // drawGrid();
    }

    public void dispose() {
        title.dispose();
        setting.dispose();
        online.dispose();
        single.dispose();
        score.dispose();
    }

        public void draw(SpriteBatch sb) {
            sb.begin();
            //TITLE
            sb.draw(title, (float) (width - title.getWidth())/2, height - title.getHeight());

            //Buttons
            sb.draw(single, 0, height - GRID_CELL*8);
            sb.draw(online, width - online.getWidth(), height - GRID_CELL*8);
            sb.draw(setting, 0, GRID_CELL*2);
            sb.draw(score, width - score.getWidth(), GRID_CELL*2);

            sb.end();
        }
}
