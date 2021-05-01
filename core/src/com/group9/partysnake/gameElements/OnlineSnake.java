package com.group9.partysnake.gameElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
This a OnlineSnake class that only needs to render received Json-data from backend.
The Snake-object on the connected client side will handle all collision detection
and game logic

This class needs only a way to draw the Snake based on the im
 */

public class OnlineSnake {
    private int snakeX = 0, snakeY = 0;

    private Texture snakeHead = new Texture("yellow_snake.png");
    private Texture snakeBody = new Texture("yellow_snake_body.png");

    private List<List<Integer>> allPositions =new ArrayList<List<Integer>>();
    private List<Integer> headPosition = new ArrayList<Integer>();

    public OnlineSnake(){
        //To don't cast a NullPointerExcepetion
        headPosition.add(100);
        headPosition.add(100);
        allPositions.add(headPosition);

    }

    public void dispose(){
        snakeHead.dispose();
        snakeBody.dispose();
        }


    public void draw(Batch batch) {



        batch.draw(snakeHead, headPosition.get(0), headPosition.get(1));
        if (allPositions.size() > 1) {
            batch.draw(snakeHead, headPosition.get(0), headPosition.get(1));
            for (int i = 1; i < allPositions.size(); i++) {
                batch.draw(snakeBody, allPositions.get(i).get(0), allPositions.get(i).get(1));
            }
        }
    }


    /*
    JSON-object is expected on the form
    {"p2": [1,2],[3,5],[6,100]}
    Where "p2" is the key in the larger JSON-object, which contains relevant position data
     */
    public void castJSON(JSONObject jsonObject) throws JSONException {
        JSONArray positions = (JSONArray) jsonObject.get("p2");
        ArrayList posArray = new ArrayList();

        int len = positions.length();

        for (int i=0;i<len;i++){
            posArray.add((ArrayList<Integer>)positions.get(i));
        }
        this.allPositions = posArray;
        this.headPosition = allPositions.get(0);

    }
}
