package com.group9.partysnake.gamestate;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

import io.socket.client.Socket;

/**
 * Implementation of a game state manager that utilises the singleton pattern
 */
public class GameStateManager {
    private static final GameStateManager INSTANCE = new GameStateManager();

    private Stack<State> states;

    // "Global variables" that are used by multiple different game states
    public Socket socket;
    public String[] snakeSkin = {"snakehead.png", "snakebody.png"};
    public String background = "grass_back.png";

    private GameStateManager() {
        states = new Stack<>();
    }

    public static GameStateManager getInstance() {
        return INSTANCE;
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop() {
        states.pop();
    }

    public void set(State state) {
        states.pop();
        states.push(state);
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}
