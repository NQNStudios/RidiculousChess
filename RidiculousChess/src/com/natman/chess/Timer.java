package com.natman.chess;

import com.badlogic.gdx.Gdx;

public class Timer {

	private float value;
	private float current;
	
	private boolean paused = false;
	
	public Timer(float value) {
		this.value = value;
		current = value;
	}
	
	public void pause() {
		paused = true;
	}
	
	public void resume() {
		paused = false;
	}
	
	public void update() {
		if (paused) return;
		
		current -= Gdx.graphics.getDeltaTime();
		
		if (current < 0) current = 0;
	}
	
	public void reset() {
		current = value;
	}
	
	public boolean complete() {
		return current <= 0;
	}
	
	public float elapsed() {
		return value - current;
	}
	
	public int intValue() {
		return (int) current;
	}
	
}
