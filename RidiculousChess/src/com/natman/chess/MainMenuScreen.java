package com.natman.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen extends ImageScreen {

	public MainMenuScreen(SpriteBatch spriteBatch) {
		super(Gdx.files.internal("mainmenu.png"), spriteBatch);
	}

}
