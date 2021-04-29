package com.group9.partysnake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group9.partysnake.gamestate.GameStateManager;
import com.group9.partysnake.gamestate.MenuState;
import com.group9.partysnake.gamestate.SinglePlayerState;

public class PartySnake extends Game {

	public static final int WIDTH = 640;
	public static final int HEIGHT = 360 ;

	public static final String TITLE = "Party Snake";

	private GameStateManager gameStateManager;
	private SpriteBatch spriteBatch;


	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		gameStateManager = new GameStateManager();
		gameStateManager.push(new MenuState(gameStateManager));

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameStateManager.update(Gdx.graphics.getDeltaTime());
		gameStateManager.render(spriteBatch);
	}

}
