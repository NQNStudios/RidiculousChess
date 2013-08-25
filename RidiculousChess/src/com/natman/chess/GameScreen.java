package com.natman.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

	private ChessBoard board;
	private SpriteBatch spriteBatch;
	
	public GameScreen(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
		
		board = new ChessBoard();
	}
	
	@Override
	public void render(float delta) {
		spriteBatch.begin();
		board.render(spriteBatch);
		spriteBatch.end();
	}

	public ChessBoard getBoard() {
		return board;
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(board);
		board.resume();
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		board.pause();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	
	
}
