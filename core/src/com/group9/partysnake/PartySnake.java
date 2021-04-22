package com.group9.partysnake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.group9.partysnake.gamestate.SinglePlayerState;

public class PartySnake extends Game {
/*

	@Override
	public void create () {
		setScreen(new GameScreen());
	}
*/

	@Override
	public void create () {
		setScreen(new SinglePlayerState());
	}


}
