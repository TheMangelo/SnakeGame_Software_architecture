package com.group9.partysnake.gamestate;

import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import jdk.internal.module.SystemModuleFinders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group9.partysnake.gameElements.OnlineSnake;
import com.group9.partysnake.gameElements.Snake;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;


//Disse blir relevante når



public class OnlineState extends State {

    private final float UPDATE_TIME = 1/30f;
    private float timer;
    private boolean game_over;
    private String stopReason;
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

        game_over = false;
        this.joining = joining;
        mySnake = new Snake();
        onlineSnake = new OnlineSnake();

        configSocketEvent();

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
                opponentName = (String) args[0];
                // Responding with true accepts the invite.
                // User should probably be asked if they want to accept, but for now
                // the response is always true (accept).
                ((Ack) args[1]).call(true);
                joining = false;
            }
        });
        // Some form of timeout or ability to cancel should be implemented
        while (joining) { ; }
        joining = true;
    }

    private void searchForOpponent() {
        opponentName = "";
        gsm.socket.emit("newGame", new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject jsonObject = (JSONObject) args[0];
                try {
                    opponentName = jsonObject.getString("opponent");
                    System.out.println("received: " + opponentName);
                } catch (JSONException e) {
                    System.out.println("inside catch: " + e.toString());
                    opponentName = null;
                }
            }
        });
        // Some form of timeout or ability to cancel should be implemented.
        // Also, not finding a partner has to be handled somehow.
        System.out.println("pre-loop: " + opponentName);
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("loop: " + (opponentName == null && !opponentName.equals("")));
            } catch (Exception e) {
                ;
            }
            if (opponentName == null || !opponentName.equals("")) break;
        }
        System.out.println("post-loop");
        if (opponentName.equals("null")) {
            stopReason = "No opponent found";
            game_over = true;
            System.out.println("in loop");
        };
        System.out.println("post-if");
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

    private Emitter.Listener onGameOver = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            gsm.socket.off();
            if (args.length > 0) stopReason = (String) args[0];
            System.out.println("Game over: " + stopReason);
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    gsm.pop();
                    dispose();
                    System.out.println(gsm.getStates());
                }
            });
        }
    };

    //Function that configures socket events
    // Tick is everytime the game is updated from the server
    //This will update the OnlineSnake class
    public void configSocketEvent(){
        gsm.socket.on("gameUpdate", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                //Must extract the relevant position_data
                JSONObject board = (JSONObject) args[0];
                System.out.println("RAW: " + board);
                try {
                   /* NB! board needs to be on the form
                    {"p1": "[[1,2], [3,2], ,,,]",
                    "p2":  "[3,6], [6,7], [2,4],,,,"}
                   * */
                    onlineSnake.castJSON(board, joining ? "p1" : "p2");
                }catch(JSONException e){
                    Gdx.app.log("SocketIO", "Error updating game with gameUpdate");
                }
            }
        });
        gsm.socket.once("endGame", onGameOver);
        gsm.socket.once(Socket.EVENT_DISCONNECT, onGameOver);
    }
    // Function that sends the relevant data as JSON.
    //Update

    //Denne må håndtere swipe
    @Override
    public void handleInput() {
        queryInput();
    }


    //This updates the server and sends data to the server
    public void updateServer(float dt) {
        System.out.println("Updating server");
        timer += dt;
        if (timer >= UPDATE_TIME && mySnake != null && !game_over){
            System.out.println("Update started");
            JSONObject data = new JSONObject();
            JSONObject positionJson = new JSONObject();

            try{
                String playerNumber = joining ? "p2" : "p1";
                System.out.println("Playernum: " + playerNumber);
                positionJson.put(playerNumber, mySnake.getAllPositions());
                System.out.println(mySnake.getAllPositions());
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
        if (game_over) {
            onGameOver.call();
        }
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
