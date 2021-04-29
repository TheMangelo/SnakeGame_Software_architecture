package com.group9.partysnake.gamestate;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group9.partysnake.gameElements.Snake;

//Disse blir relevante når




public class OnlineState extends State {

    private Socket socket;
    private Snake mySnake, onlineSnake;


    protected OnlineState(GameStateManager gsm) {
        super(gsm);
        mySnake = new Snake();
        onlineSnake = new Snake();
        connectSocket();

    }

    protected OnlineState(GameStateManager gsm, Socket socket) {
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





    //Denne må håndtere swipe
    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {
        super.dispose();

    }


}