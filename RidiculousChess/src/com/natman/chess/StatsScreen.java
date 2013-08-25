package com.natman.chess;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StatsScreen implements Screen {

	public static float shortestGame;
	public static float longestGame;
	public static float totalTime;
	public static int totalGames;
	public static int whiteWins;
	public static int blackWins;
	
	private BitmapFont font;
	private SpriteBatch spriteBatch;
	
	public static void init() {
		Preferences prefs = Gdx.app.getPreferences("R_CHESS_STATS");
		
		shortestGame = prefs.getFloat("shortest", 5000);
		longestGame = prefs.getFloat("longest", 0f);
		totalTime = prefs.getFloat("totalTime", 0f);
		totalGames = prefs.getInteger("totalGames", 0);
		whiteWins = prefs.getInteger("whiteWins", 0);
		blackWins = prefs.getInteger("blackWins", 0);
		
	}
	
	public static void commit() {
		Preferences prefs = Gdx.app.getPreferences("R_CHESS_STATS");
		
		prefs.putFloat("shortest", shortestGame);
		prefs.putFloat("longest", longestGame);
		prefs.putFloat("totalTime", totalTime);
		prefs.putInteger("totalGames", totalGames);
		prefs.putInteger("whiteWins", whiteWins);
		prefs.putInteger("blackWins", blackWins);
		
		prefs.flush();
	}
	
	public StatsScreen(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
		
		font = new BitmapFont();
		
		font.setColor(Color.BLACK);
		font.setScale(1.5f);
		
		init();
	}
	
	@Override
	public void render(float delta) {
		spriteBatch.begin();
		
		float x = 25;
		float y = 560;
		int i = 0;
		
		DecimalFormat format;
		format = (DecimalFormat) DecimalFormat.getInstance();
		format.setMaximumFractionDigits(3);
		format.setMinimumFractionDigits(2);
		format.setPositiveSuffix(" seconds");
		
		font.draw(spriteBatch, "Stats", 220, 770);
		font.draw(spriteBatch, "Total Time Played: " + format.format(totalTime), x, y + i++ * font.getLineHeight());
		font.draw(spriteBatch, "Longest Game: " + format.format(longestGame), x, y + i++ * font.getLineHeight());
		font.draw(spriteBatch, "Shortest Game: " + format.format(shortestGame), x, y + i++ * font.getLineHeight());
		font.draw(spriteBatch, "Black Wins: " + blackWins, x, y + i++ * font.getLineHeight());
		font.draw(spriteBatch, "White Wins: " + whiteWins, x, y + i++ * font.getLineHeight());
		font.draw(spriteBatch, "Games Played: " + totalGames, x, y + i++ * font.getLineHeight());
		
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
