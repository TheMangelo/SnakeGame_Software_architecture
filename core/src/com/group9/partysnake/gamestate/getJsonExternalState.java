package com.group9.partysnake.gamestate;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class getJsonExternalState extends State{


    public List<List<Integer>> allPositions;

    private Texture texture = new Texture("snakeHead.png");


    public getJsonExternalState(GameStateManager gsm) throws JSONException {
        super(gsm);

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();
        sb.end();

    }

    public void castJSON(JSONObject jsonObject) throws JSONException {
        JSONArray positions = (JSONArray) jsonObject.get("p2");
        ArrayList posArray = new ArrayList();

        int len = positions.length();
        for (int i=0;i<len;i++){
            posArray.add((ArrayList<Integer>)positions.get(i));
        }
        allPositions = posArray;

    }

    public List<List<Integer>> jsonToArray(JSONObject jsonObject) throws JSONException {
        JSONArray positions = (JSONArray) jsonObject.get("p2");
        ArrayList posArray = new ArrayList();

        int len = positions.length();
        for (int i=0;i<len;i++){
            posArray.add((ArrayList<Integer>)positions.get(i));
        }
        allPositions = posArray;
        return allPositions;
    }
}
