package com.group9.partysnake.gamestate;

import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group9.partysnake.gameElements.OnlineSnake;
import com.group9.partysnake.gameElements.Snake;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class OnlineState extends State {
    private final float UPDATE_TIME = 0.1f;
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
                if (opponentName.equals("null")) {
                    game_over = true;
                    stopReason = "No games available";
                    joining = false;
                    return;
                }
                // Responding with true accepts the invite.
                // User should probably be asked if they want to accept, but for now
                // the response is always true (accept).
                ((Ack) args[1]).call(true);
                joining = false;
            }
        });
        // An ability to cancel should be implemented
        int timeout = 0;
        while (joining) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
            timeout += 100;
            if (timeout >= 8000) {
                game_over = true;
                stopReason = "Timed out while searching for games";
                break;
            }
        }
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
                } catch (JSONException e) {
                    opponentName = null;
                }
            }
        });
        // An ability to cancel should be implemented
        int timeout = 0;
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
            if (opponentName == null || !opponentName.equals("")) break;
            timeout += 100;
            if (timeout >= 8000) {
                opponentName = " ";
                stopReason = "Timed out while searching for opponent";
                game_over = true;
                break;
            }
        }
        if (opponentName.equals("null")) {
            stopReason = "No opponent found";
            game_over = true;
        }
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
                try {
                   /* NB! board needs to be on the form
                    {"p1": "[[1,2], [3,2], ,,,]",
                    "p2":  "[3,6], [6,7], [2,4],,,,"}
                   * */
                    System.out.println("Received: " + board);
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
        if (mySnake != null && !game_over) {
            JSONObject data = new JSONObject();
            JSONObject positionJson = new JSONObject();

            try{
                String playerNumber = joining ? "p2" : "p1";
                positionJson.put(playerNumber, mySnake.getAllPositions());
                data.put("board", positionJson);
                data.put("time", System.currentTimeMillis());
                System.out.println("Sending to " + playerNumber + ": " + data);
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
            return;
        }
        timer -= dt;
        if (timer <= 0) {
            handleInput();
            mySnake.updateSnake();
            updateServer(dt);
            timer = UPDATE_TIME;
        }
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
