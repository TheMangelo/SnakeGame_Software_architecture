package com.group9.partysnake.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class SettingsState extends State {
    private Stage stage;
    private Table table;

    private Button skinButtonLeft, skinButtonRight,
                   backgroundButtonLeft, backgroundButtonRight,
                   backButton;

    private String[] skinPaths = {"snakehead.png", "yellow_snake.png", "snakehead_purp.png"};
    private String[] bodySkinPaths = {
            "snakebody.png","yellow_snake_body.png", "snakebody_purp.png"
    };
    private String[] backgroundPaths = {"grass_back.png", "bck_2.png"};

    private Array<Image> skins, backgrounds;
    private Image currentSkin, currentBackground;
    private int skinIndex, backgroundIndex;

    public SettingsState(GameStateManager gsm) {
        super(gsm);

        stage = new Stage();

        makeButtons();
        fetchSkinsAndBackgrounds();
        setUpTable();
        setUpClickHandling();

        Gdx.input.setInputProcessor(stage);
        stage.addActor(table);
        stage.addActor(backButton);
    }

    private void makeButtons() {
        TextureRegion leftArrow = new TextureRegion(new Texture("arrow.png"));
        leftArrow.flip(true, false);
        TextureRegionDrawable leftDrawable = new TextureRegionDrawable(leftArrow);
        TextureRegion rightArrow = new TextureRegion(new Texture("arrow.png"));
        TextureRegionDrawable rightDrawable = new TextureRegionDrawable(rightArrow);

        skinButtonLeft = new Button(leftDrawable);
        skinButtonRight = new Button(rightDrawable);
        backgroundButtonLeft = new Button(leftDrawable);
        backgroundButtonRight = new Button(rightDrawable);

        TextureRegion backArrow = new TextureRegion(new Texture("return_arrow.png"));
        TextureRegionDrawable backDrawable = new TextureRegionDrawable(backArrow);

        backButton = new Button(backDrawable);

        backButton.setTransform(true);
        backButton.setScale(0.5f);
        backButton.setPosition(20, 310);
    }

    private void fetchSkinsAndBackgrounds() {
        skins = new Array<>();
        backgrounds = new Array<>();

        for (String path : skinPaths) {
            skins.add(new Image(new Texture(path)));
        }
        for (String path : backgroundPaths) {
            backgrounds.add(new Image(new Texture(path)));
        }

        currentSkin = new Image();
        currentSkin.setDrawable(skins.get(0).getDrawable());
        currentBackground = new Image();
        currentBackground.setDrawable(backgrounds.get(0).getDrawable());

        skinIndex = 0;
        backgroundIndex = 0;
    }

    private void setUpClickHandling() {
        skinButtonLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skinIndex = skinIndex == 0 ? skins.size - 1 : skinIndex - 1;
                currentSkin.setDrawable(skins.get(skinIndex).getDrawable());
            }
        });
        skinButtonRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                skinIndex = skinIndex == skins.size - 1 ? 0 : skinIndex + 1;
                currentSkin.setDrawable(skins.get(skinIndex).getDrawable());
            }
        });
        backgroundButtonLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backgroundIndex = backgroundIndex == 0 ? backgrounds.size - 1 : backgroundIndex - 1;
                currentBackground.setDrawable(backgrounds.get(backgroundIndex).getDrawable());
            }
        });
        backgroundButtonRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backgroundIndex = backgroundIndex == backgrounds.size - 1 ? 0 : backgroundIndex + 1;
                currentBackground.setDrawable(backgrounds.get(backgroundIndex).getDrawable());
            }
        });
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.snakeSkin[0] = skinPaths[skinIndex];
                gsm.snakeSkin[1] = bodySkinPaths[skinIndex];
                gsm.background = backgroundPaths[backgroundIndex];
                gsm.pop();
                dispose();
            }
        });
    }

    private void setUpTable() {
        table = new Table();

        table.add(new Image(new Texture("settings.png"))).colspan(3);
        table.row();
        table.add(skinButtonLeft).width(50).height(50);
        table.add(currentSkin);
        table.add(skinButtonRight).width(50).height(50);
        table.row();
        table.add().colspan(3).height(20);
        table.row();
        table.add(backgroundButtonLeft).width(50).height(50);
        table.add(currentBackground).width(100).height(100);
        table.add(backgroundButtonRight).width(50).height(50);

        table.setFillParent(true);
        table.pad(10);
    }

    @Override
    public void handleInput() {
        // Handled by stage
    }

    @Override
    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        stage.draw();
    }
}
