package com.group9.partysnake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PartySnake extends Game {

	@Override
	public void create () {
		setScreen(new GameScreen());
	}

}
