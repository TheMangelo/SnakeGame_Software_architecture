package com.group9.partysnake.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class LoginState extends State {
    private GameStateManager gsm;

    private Stage stage;
    private TextField usernameField, passwordField;
    private TextButton loginButton, newUserButton;

    private String HOSTNAME = "127.0.0.1";
    private int PORT = 3000;

    LoginState(GameStateManager gsm) {
        super(gsm);

        stage = new Stage();
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        usernameField = new TextField("Username", textFieldStyle);
        passwordField = new TextField("Password", textFieldStyle);
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        loginButton = new TextButton("Log in", buttonStyle);
        newUserButton = new TextButton("Create user", buttonStyle);

        Gdx.input.setInputProcessor(stage);
        stage.addActor(usernameField);
        stage.addActor(passwordField);
        stage.addActor(loginButton);
        stage.addActor(newUserButton);
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
    }

    @Override
    public void handleInput() {

    }

    private void connect(boolean newUser) {
        URI uri = URI.create( String.format("http://%s:%d/", this.HOSTNAME, this.PORT) );

        Map<String, String> auth = new HashMap<>();
        auth.put("name", this.usernameField.getText());
        auth.put("password", this.passwordField.getText());
        auth.put("new", Boolean.toString(newUser));

        IO.Options options = IO.Options.builder().setAuth(auth).build();

        gsm.socket = new IO.socket(uri, options);
        gsm.socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(String.format("Could not connect.\nError: %s\nReason: %s",
                                                 args.message,
                                                 args.data));
            }
        });
        this.socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Connected!");
            }
        });
        this.socket.connect();
    }

    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {

    }

    public void render(float dt) {
        this.stage.draw();
        this.stage.act();
    }

    public void dispose() {
        this.stage.dispose();
    }
}
