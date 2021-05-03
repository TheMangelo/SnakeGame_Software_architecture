package com.group9.partysnake.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class LoginState extends State {

    //private final static String HOSTNAME = "35.228.7.69";
    private final static String HOSTNAME = "127.0.0.1";

    private final static int PORT = 3000;
    private final static Skin SKIN = new Skin(Gdx.files.internal("uiskin.json"));

    private Stage stage;
    private Table table;

    private TextField usernameField, passwordField;
    private TextButton loginButton, newUserButton;
    private Button backButton;

    private Dialog dialog;

    public LoginState(GameStateManager gsm) {
        super(gsm);

        setUpTextFields();
        setUpButtons();
        setUpTable();
        setUpDialog("");

        stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        stage.addActor(table);
        stage.addActor(backButton);
    }

    private void setUpTextFields() {
        usernameField = new TextField("Username", SKIN);
        passwordField = new TextField("Password", SKIN);
    }

    private void setUpButtons() {
        loginButton = new TextButton("Log in", SKIN);
        newUserButton = new TextButton("Create user", SKIN);

        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                connect(false);
            }
        });
        newUserButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                connect(true);
            }
        });

        TextureRegion backArrow = new TextureRegion(new Texture("return_arrow.png"));
        TextureRegionDrawable backDrawable = new TextureRegionDrawable(backArrow);

        backButton = new Button(backDrawable);

        backButton.setTransform(true);
        backButton.setScale(0.5f);
        backButton.setPosition(20, 310);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.push(new MenuState(gsm));
            }
        });
    }

    private void setUpTable() {
        table = new Table();

        table.add(new Image(new Texture("online.png"))).colspan(2);
        table.row();
        table.add(usernameField).colspan(2).padBottom(10);
        table.row();
        table.add(passwordField).colspan(2).padBottom(10);
        table.row();
        table.add(loginButton);
        table.add(newUserButton);

        table.setFillParent(true);
        table.pad(10);
    }

    private void setUpDialog(String text) {
        dialog = new Dialog("Could not connect", SKIN, "dialog");
        if (text.length() > 0) dialog.text(text);
        dialog.button("OK");
    }

    @Override
    public void handleInput() {
        // Handled by stage
    }

    private void connect(boolean newUser) {
        URI uri = URI.create( String.format("http://%s:%d/", HOSTNAME, PORT) );

        Map<String, String> auth = new HashMap<>();
        auth.put("name", usernameField.getText());
        auth.put("password", passwordField.getText());
        auth.put("new", Boolean.toString(newUser));

        IO.Options options = IO.Options.builder().setAuth(auth).build();

        gsm.socket = IO.socket(uri, options);
        gsm.socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args[0] instanceof Exception) {
                    setUpDialog(args[0].toString() + '\n' + ((Exception) args[0]).getCause());
                    dialog.show(stage);
                    gsm.socket.disconnect();
                    return;
                }
                JSONObject jsonObject = (JSONObject) args[0];
                String message;
                String data;
                try {
                    message = jsonObject.getString("message");
                    data = jsonObject.getString("data");
                } catch (JSONException e) {
                    setUpDialog("Unexpected response from server:\n" + jsonObject.toString());
                    dialog.show(stage);
                    return;
                }
                setUpDialog(message + '\n' + data);
                dialog.show(stage);
            }
        });
        gsm.socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        gsm.push(new ScoreState(gsm));
                    }
                });

                System.out.println("Connected!");
            }
        });
        gsm.socket.connect();
    }

    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
