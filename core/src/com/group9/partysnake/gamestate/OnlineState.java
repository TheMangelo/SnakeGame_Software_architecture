package com.group9.partysnake.gamestate;

import io.socket.emitter.Emitter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group9.partysnake.gameElements.Snake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


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

    private Snake mySnake, onlineSnake;

    private String room;



    public OnlineState(GameStateManager gsm) {
        super(gsm);
        mySnake = new Snake();
        onlineSnake = new Snake();
    }

    private void queryInput() {
        boolean lPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean uPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        if (lPressed) mySnake.setSnakeDirection(LEFT);
        if (rPressed) mySnake.setSnakeDirection(RIGHT);
        if (uPressed) mySnake.setSnakeDirection(UP);
        if (dPressed) mySnake.setSnakeDirection(DOWN);
    }


    //Function that configures socket events
    // Tick is everytime the game is updated from the server
    public void configSocketEvent(){

        gsm.socket.on("gameUpdate", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                //Must extract the relevant position data to draw opponent Snake
                JSONObject sent_data = (JSONObject) args[0];
                JSONObject board;
                JSONArray vsPosition;
                try {
                    room = sent_data.getString("room");
                    board = (JSONObject) sent_data.get("board");
                    vsPosition = board.getJSONArray("p2");
                }catch(JSONException e){
                    Gdx.app.log("SocketIO", "Error updating game with gameUpdate");
                }
            }
        });
    }
    // Function that sends the relevant data as JSON.
    //Update

    //Denne må håndtere swipe
    @Override
    public void handleInput() {
    }

    public void updateServer(float dt){
        timer += dt;
        if (timer >= UPDATE_TIME && mySnake != null && game_over != true){
            JSONObject data = new JSONObject();
            JSONObject positionJson = new JSONObject();

            try{
                data.put("room", "INSERT ROOM ID HERE");
                positionJson.put("p1",mySnake.getAllPositions());
                data.put("board",positionJson);
                data.put("time", 1000); //Vet ijkke hva jeg skal bruke her
                gsm.socket.emit("tick", data);

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
        mySnake.dispose();
        if (onlineSnake != null){
            onlineSnake.dispose();
        }
    }
}
