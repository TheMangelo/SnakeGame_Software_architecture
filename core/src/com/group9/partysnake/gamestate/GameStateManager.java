package com.group9.partysnake.gamestate;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

import io.socket.client.IO;
import io.socket.client.Socket;

public class GameStateManager {
    private Stack<State> states;

    // "Global variable"
    public Socket socket;

    public GameStateManager() {
        states = new Stack<>();
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