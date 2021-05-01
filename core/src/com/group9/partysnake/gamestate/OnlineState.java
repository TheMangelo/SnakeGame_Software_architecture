package com.group9.partysnake.gamestate;

import io.socket.emitter.Emitter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group9.partysnake.gameElements.OnlineSnake;
import com.group9.partysnake.gameElements.Snake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


//Disse blir relevante når



public class OnlineState extends State {

    private final float UPDATE_TIME = 1/30f;
    private float timer;
    private boolean game_over = false;
    private boolean joining;

    // For handling the direction changes
    private static final int RIGHT = 0;
    private static final int LEFT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    private Snake mySnake;
    private OnlineSnake onlineSnake;

    private String opponentName;

    private static final float MOVE_TIME = 0.1F;  //Hvor fort slangen skal bevege seg og oppdatere movesa

    public OnlineState(GameStateManager gsm, boolean joining) {
        super(gsm);
        this.joining = joining;
        mySnake = new Snake();
        onlineSnake = new OnlineSnake();

        if (joining) {
            tryToJoin();
        } else {
            searchForOpponent();
        }
    }

    private void tryToJoin() {
        gsm.socket.once("gameRequest", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                // Not sure if this is the way it works, but the documentation is fairly shit
                // regarding the specifics here.
                opponentName = (String) args[0];
                // This apparently doesn't work. Need to find out how to respond. Perhaps just
                // define a new emit?
                // Function responseFunction = (Function) args[1];
                // responseFunction.apply(true);
            }
        });
        // Some form of timeout or ability to cancel should be implemented
        while (joining) { ; }
    }

    private void searchForOpponent() {
        gsm.socket.emit("newGame", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject jsonObject = (JSONObject) args[0];
                try {
                    opponentName = jsonObject.getString("opponent");
                } catch (JSONException e) {
                    opponentName = null;
                }
            }
        });
        // Some form of timeout or ability to cancel should be implemented
        while (opponentName == "") { ; }
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
    //This will update the OnlineSnake class
    public void configSocketEvent(){
        gsm.socket.on("gameUpdate", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                //Must extract the relevant position_data
                JSONObject sent_data = (JSONObject) args[0];
                JSONObject board;
                try {
                   /* NB! board needs to be on the form
                    {"p1": "[[1,2], [3,2], ,,,]",
                    "p2":  "[3,6], [6,7], [2,4],,,,"}
                   * */
                    board = (JSONObject) sent_data.get("board");
                    onlineSnake.castJSON(board);
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
        queryInput();
    }


    //This updates the server and sends data to the server
    public void updateServer(float dt){
        timer += dt;
        if (timer >= UPDATE_TIME && mySnake != null && game_over != true){
            JSONObject data = new JSONObject();
            JSONObject positionJson = new JSONObject();

            try{
                String playerNumber = joining ? "p2" : "p1";
                positionJson.put(playerNumber, mySnake.getAllPositions());
                data.put("board", positionJson);
                data.put("time", timer);
                gsm.socket.emit("tick", data);

            } catch (JSONException e){
                Gdx.app.log("SOCKET.IO", "Error sending JSON");
            }
        }
    }

    public void draw(SpriteBatch sb){
        if(mySnake != null){
            mySnake.draw(sb);
        }
        if(onlineSnake != null){
            onlineSnake.draw(sb);
        }
    }

    //Sender info til server basert på hvilken tid jeg har
    @Override
    public void update(float dt) {
        updateServer(dt);
        mySnake.updateSnake();

    }

    @Override
    public void render(SpriteBatch sb) {
        clearScreen();
        sb.begin();
        draw(sb);
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
