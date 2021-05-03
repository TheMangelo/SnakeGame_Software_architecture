package com.group9.partysnake.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.group9.partysnake.PartySnake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.socket.client.Ack;
import io.socket.emitter.Emitter;

public class ScoreState extends State{
    private Stage stage;
    private Button backButton;
    private Texture backgroundTexture, highscoreTexture, nameTexture, scoreTexture;
    private boolean loaded = false;
    private BitmapFont font;


    // DATA: [{“player”: DavidF, “Score”: 4000} , {“player”: Mikkel, “Score”: 23}
    private ArrayList<HashMap<String,String>> scoreResult;
    private ArrayList scoreList;

    public ScoreState(GameStateManager gsm) {
        super(gsm);
        getScore();

        stage = new Stage();
        backgroundTexture = new Texture("background.png");
        highscoreTexture = new Texture("highscore.png");
        nameTexture = new Texture ("name.png");
        scoreTexture = new Texture ("score.png");
        font = new BitmapFont();
        font.getData().setScale(2f,2f);

//        //LINE TO TEST JSON-HANDLING IN SCORESTATE
//        jsonFromServer testJson = new jsonFromServer();
//        try{
//            convertJson(testJson.getTestJsonScore());
//        }  catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //omitable

        makeButton();
        clickHandling();

        Gdx.input.setInputProcessor(stage);
        stage.addActor(backButton);
    }

/*

    [{"score":0,"password":"test","_id":"608ebf5f023ac504802d6aa7","player":"David"}]
    class org.json.JSONArray
    {"score":0,"password":"test","_id":"608ebf5f023ac504802d6aa7","player":"David"}
    class org.json.JSONObject
 */


    public void getScore(){
        gsm.socket.emit("getTopTen", new Ack() {
            @Override
            public void call(Object... args) {
                JSONArray data = (JSONArray) args[0];
                System.out.println("Its in get score");
                try{

                    System.out.println(data);
                    System.out.println(data.getClass());

                    System.out.println(data.get(0));
                    System.out.println(data.get(0).getClass());

                    JSONObject tempJson = (JSONObject) data.get(0);

                    Integer number = (Integer) tempJson.get("score");
                    String user = (String)  tempJson.get("player");
                    System.out.println(user + " has this many points " +number);

                  convertJson(data);
                  loaded = true;


                } catch (Exception e){
                    System.out.println("Something went wrong reading from Server");
                }
            }

        });
    }


    private void makeButton() {
        TextureRegion backArrow = new TextureRegion(new Texture("return_arrow.png"));
        TextureRegionDrawable backDrawable = new TextureRegionDrawable(backArrow);
        backButton = new Button(backDrawable);
        backButton.setTransform(true);
        backButton.setScale(0.5f);
        backButton.setPosition(20, 310);
    }

    private void clickHandling() {
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gsm.push(new MenuState(gsm));
            }
        });
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {
        stage.act();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(backgroundTexture, 0, 0);
        sb.draw(highscoreTexture, 130, 280, 400, 70);
        sb.draw(nameTexture, 240, 250, 70, 20);
        sb.draw(scoreTexture, 470, 250, 70, 20);

        if(!loaded){
            font.draw(sb,"Loading...", 240,200);

        }
        else if (loaded){
            int startYpos = 230;
            for(int i = 0; i < scoreResult.size(); i++){
                HashMap<String,String> tempHash = scoreResult.get(i);
                String tempPlayer = tempHash.get("player");
                String tempScore = tempHash.get("score");
                font.draw(sb,i+1+"", 200,startYpos);
                font.draw(sb,tempPlayer, 250,startYpos);
                font.draw(sb,tempScore, 470,startYpos);
                startYpos -= 40;
            }
        }
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        highscoreTexture.dispose();
        nameTexture.dispose();
        scoreTexture.dispose();
    }

    public void convertJson(JSONArray fromServer) throws JSONException {
        ArrayList scores = new ArrayList();
        if( fromServer.length() > 5){
            try {
                for (int i = 0; i < 5; i++) {
                    JSONObject tempObj = fromServer.getJSONObject(i);
                    HashMap<String, String> tempHash = new HashMap<String, String>();
                    tempHash.put("player", (String) tempObj.get("player"));
                    tempHash.put("score", tempObj.get("score").toString());
                    scores.add(tempHash);
                }
                scoreResult = scores;
            }
            catch(JSONException e){
                System.out.println("Error getting JSONs");
            }
        } else {
            try {
                for (int i = 0; i < fromServer.length(); i++) {
                    JSONObject tempObj = fromServer.getJSONObject(i);
                    HashMap<String, String> tempHash = new HashMap<String, String>();
                    tempHash.put("player", (String) tempObj.get("player"));
                    tempHash.put("score", tempObj.get("score").toString());
                    scores.add(tempHash);
                }
                scoreResult = scores;
            }
            catch(JSONException e){
                System.out.println("Error getting JSONs");
            }
        }
    }
}
