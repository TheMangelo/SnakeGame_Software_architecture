package com.group9.partysnake.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class LoginState extends GameState {
    private GameStateManager gsm;

    private Stage stage;
    private TextField usernameField, passwordField;
    private TextButton loginButton, newUserButton;

    private Socket socket;

    private String HOSTNAME = "127.0.0.1";
    private int PORT = 3000;

    LoginState(GameStateManager gsm) {
        super(gsm);

        this.stage = new Stage();
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        this.usernameField = new TextField("Username", textFieldStyle);
        this.passwordField = new TextField("Password", textFieldStyle);
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        this.loginButton = new TextButton("Log in", buttonStyle);
        this.newUserButton = new TextButton("Create user", buttonStyle);

        Gdx.input.setInputProcessor(stage);
        this.stage.addActor(usernameField);
        this.stage.addActor(passwordField);
        this.stage.addActor(loginButton);
        this.stage.addActor(newUserButton);
        this.loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
    }

    private void connect() {
        String URI = String.format("http://%s:%d/", this.HOSTNAME, this.PORT);
        IO.Options options = new IO.Options();
        this.socket = new IO.socket(URI, options);
        this.socket.connect();
    }

    public void update(float dt) {

    }

    public void render(float dt) {
        this.stage.draw();
        this.stage.act();
    }

    public void dispose() {
        this.stage.dispose();
    }
}
