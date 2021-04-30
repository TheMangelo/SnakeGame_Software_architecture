package com.group9.partysnake.gamestate;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

<<<<<<< HEAD
=======

>>>>>>> 50b1647339a361fe615ea519c0fed69dded62c30
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group9.partysnake.gameElements.Snake;
<<<<<<< HEAD
import com.group9.partysnake.gameElements.TestJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

=======

import org.json.JSONException;
import org.json.JSONObject;

>>>>>>> 50b1647339a361fe615ea519c0fed69dded62c30
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

<<<<<<< HEAD
    private Snake mySnake, onlineSnake;

    private String room;



    public OnlineState(GameStateManager gsm) {
        super(gsm);
        mySnake = new Snake();
        onlineSnake = new Snake();

    }

=======
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
>>>>>>> 50b1647339a361fe615ea519c0fed69dded62c30


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

<<<<<<< HEAD

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
=======
    //Denne må håndtere swipe
    @Override
    public void handleInput() {

    }

>>>>>>> 50b1647339a361fe615ea519c0fed69dded62c30
    public void updateServer(float dt){
        timer += dt;
        if (timer >= UPDATE_TIME && mySnake != null && game_over != true){
            JSONObject data = new JSONObject();
<<<<<<< HEAD
            JSONObject positionJson = new JSONObject();

            try{
                data.put("room", "INSERT ROOM ID HERE");
                positionJson.put("p1",mySnake.getAllPositions());
                data.put("board",positionJson);
                data.put("time", 1000); //Vet ijkke hva jeg skal bruke her
                gsm.socket.emit("tick", data);

=======

            try{
                data.put("x",1);
                data.put("x",2);

                socket.emit("updatePlayer", data);
>>>>>>> 50b1647339a361fe615ea519c0fed69dded62c30
            } catch (JSONException e){
                Gdx.app.log("SOCKET.IO", "Error sending JSON");
            }
        }

    }


<<<<<<< HEAD

    //Denne må håndtere swipe
    @Override
    public void handleInput() {

    }

=======
>>>>>>> 50b1647339a361fe615ea519c0fed69dded62c30
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
<<<<<<< HEAD
=======

>>>>>>> 50b1647339a361fe615ea519c0fed69dded62c30
    }

    @Override
    public void dispose() {
        super.dispose();
<<<<<<< HEAD
        mySnake.dispose();
        if (onlineSnake != null){
            onlineSnake.dispose();
        }
    }

=======

    }


>>>>>>> 50b1647339a361fe615ea519c0fed69dded62c30
}