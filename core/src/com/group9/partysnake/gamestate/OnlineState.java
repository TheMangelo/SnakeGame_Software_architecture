package com.group9.partysnake.gamestate;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group9.partysnake.gameElements.Snake;

import org.json.JSONException;
import org.json.JSONObject;

//Disse blir relevante når




public class OnlineState extends State {

    private final float UPDATE_TIME = 1/30f;
    private float timer;
    private boolean game_over = false;

    // For handling the direction changes
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private Socket socket;
    private Snake mySnake, onlineSnake;


    public OnlineState(GameStateManager gsm) {
        super(gsm);
        mySnake = new Snake();
        onlineSnake = new Snake();
        connectSocket();

    }

    public OnlineState(GameStateManager gsm, Socket socket) {
        super(gsm);
        this.socket = socket;
        mySnake = new Snake();
        onlineSnake = new Snake();
        connectSocket();
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void connectSocket(){
        try {
            socket = IO.socket("Den relevante socketadressen");
            socket.connect();
        } catch(Exception e){
            System.out.println(e);
        }
    }


    private void queryInput() {
        boolean lPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean uPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        if (lPressed) {
            mySnake.setSnakeDirection(LEFT);
            System.out.println("HIEIHEIHE");
        }
        if (rPressed) mySnake.setSnakeDirection(RIGHT);
        if (uPressed) mySnake.setSnakeDirection(UP);
        if (dPressed) mySnake.setSnakeDirection(DOWN);
    }

    //Denne må håndtere swipe
    @Override
    public void handleInput() {

    }

    public void updateServer(float dt){
        timer += dt;
        if (timer >= UPDATE_TIME && mySnake != null && game_over != true){
            JSONObject data = new JSONObject();

            try{
                data.put("x",1);
                data.put("x",2);

                socket.emit("updatePlayer", data);
            } catch (JSONException e){
                Gdx.app.log("SOCKET.IO", "Error sending JSON");
            }
        }

    }


    //Sender info til server basert på hvilken tid jeg har
    @Override
    public void update(float dt) {



    }

    @Override
    public void render(SpriteBatch sb) {
        clearScreen();
        sb.begin();

        if(mySnake != null){
            mySnake.draw(sb);
        }
        if(onlineSnake != null){
            onlineSnake.draw(sb);
        }
        sb.end();

    }

    @Override
    public void dispose() {
        super.dispose();

    }


}