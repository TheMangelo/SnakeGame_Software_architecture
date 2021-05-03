package com.group9.partysnake.gameElements;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TestJson {


    public TestJson(){

    };

    public JSONObject exampleJson() throws JSONException {
        List<List<Integer>> allPos =new ArrayList<List<Integer>>();
        List<Integer> headPos =new ArrayList<Integer>();

        headPos.add(10);
        headPos.add(2);

        List<Integer> bodyPos =new ArrayList<Integer>();
        bodyPos.add(100);
        bodyPos.add(100);

        allPos.add(headPos);
        allPos.add(bodyPos);

        List<Integer> bodyPos2 =new ArrayList<Integer>();
        bodyPos2.add(200);
        bodyPos2.add(200);

        allPos.add(bodyPos2);


        JSONObject data = new JSONObject();
        data.put("p2", allPos);

        return data;
    };
}
