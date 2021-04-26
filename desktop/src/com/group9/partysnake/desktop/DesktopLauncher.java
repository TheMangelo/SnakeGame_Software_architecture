package com.group9.partysnake.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.group9.partysnake.PartySnake;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = PartySnake.WIDTH;
		config.height = PartySnake.HEIGHT;
		config.title = PartySnake.TITLE;

		new LwjglApplication(new PartySnake(), config);
	}
}
