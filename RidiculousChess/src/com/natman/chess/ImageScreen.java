package com.natman.chess;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageScreen implements Screen {

	private Texture texture;
	private TextureRegion region;
	private SpriteBatch spriteBatch;
	
	public ImageScreen(FileHandle file, int x, int y, int width, int height, SpriteBatch spriteBatch) {
		texture = new Texture(file);
		region = new TextureRegion(texture, x, y, width, height);
		
		this.spriteBatch = spriteBatch;
	}
	
	public ImageScreen(FileHandle file, SpriteBatch spriteBatch) {
		this(file, 0, 0, 480, 800, spriteBatch);
	}
	
	@Override
	public void render(float delta) {
		spriteBatch.begin();
		spriteBatch.draw(region, 0, 0, 480, 800);
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) { }

	@Override
	public void show() { }

	@Override
	public void hide() { }

	@Override
	public void pause() { }

	@Override
	public void resume() { }

	@Override
	public void dispose() {
		texture.dispose();
	}

}
