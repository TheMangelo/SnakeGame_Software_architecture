package com.group9.partysnake.gamestate;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class jsonFromServer {


    public List<List<Integer>> allPositions;

    private Texture texture = new Texture("snakeHead.png");

    public jsonFromServer() {

    }

    ;


    public void castJSON(JSONObject jsonObject) throws JSONException {
        JSONArray positions = (JSONArray) jsonObject.get("p2");
        ArrayList posArray = new ArrayList();

        int len = positions.length();
        for (int i = 0; i < len; i++) {
            posArray.add((ArrayList<Integer>) positions.get(i));
        }
        allPositions = posArray;

    }

    public List<List<Integer>> jsonToArray(JSONObject jsonObject) throws JSONException {
        JSONArray positions = (JSONArray) jsonObject.get("p2");
        ArrayList posArray = new ArrayList();

        int len = positions.length();
        for (int i = 0; i < len; i++) {
            posArray.add((ArrayList<Integer>) positions.get(i));
        }
        allPositions = posArray;
        return allPositions;
    }

    /* Antar at Objekter er på formen
     * [{“player”: DavidF, “Score”: 4000} , {“player”: Mikkel, “Score”: 23},,,,,,]
     * JsonArray med Json objekter
     * */

    public JSONArray getTestJsonScore() throws JSONException {
        JSONArray fromServer = new JSONArray();

        JSONObject player1 = new JSONObject();
        player1.put("player", "Mikkel");
        player1.put("score", "1000");

        JSONObject player2 = new JSONObject();
        player2.put("player", "David");
        player2.put("score", "200");

        JSONObject player3 = new JSONObject();
        player3.put("player", "Kenny");
        player3.put("score", "30");

        JSONObject player4 = new JSONObject();
        player4.put("player", "Anne Borg");
        player4.put("score", "1");

        fromServer.put(player1);
        fromServer.put(player2);
        fromServer.put(player3);
        fromServer.put(player4);

        return fromServer;
    }

}
