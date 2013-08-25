package com.natman.chess;

import com.badlogic.gdx.Gdx;

public class GameTimer {

	private float elapsedTime = 0f;
	
	private boolean paused = false;
	
	public void pause() {
		paused = true;
	}
	
	public void resume() {
		paused = false;
	}
	
	public void reset() {
		elapsedTime = 0f;
	}
	
	public void update() {
		if (paused) return;
		
		elapsedTime += Gdx.graphics.getDeltaTime();
	}
	
	public float getElapsed() {
		return elapsedTime;
	}
	
}
