package com.natman.chess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class ChessGame extends Game {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	@Override
	public void create() {
		Gdx.graphics.setDisplayMode(480, 800, false);
		Gdx.graphics.setTitle("Ridiculous Chess");
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
		Gdx.input.setCatchBackKey(true);
		
		StatsScreen.init();
		
		setScreen(new MainMenuScreen(batch));
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		super.render();
		
		if (getScreen() instanceof MainMenuScreen) {
			//Handle main menu
			
			int screenX = Gdx.input.getX();
			int screenY = Gdx.input.getY();
			screenX /= ((float)Gdx.graphics.getWidth() / 480);
			screenY /= ((float)Gdx.graphics.getHeight() / 800);
			
			Rectangle playButton = new Rectangle(195, 318, 106, 37);
			
			if (playButton.contains(screenX, screenY) && Gdx.input.isTouched()) {
				//play pressed
				setScreen(new GameScreen(batch));
			}
			
			Rectangle statsButton = new Rectangle(194, 392, 104, 31);
			
			if (statsButton.contains(screenX, screenY) && Gdx.input.isTouched()) {
				
				setScreen(new StatsScreen(batch));
				
			}
			
			Rectangle tutButton = new Rectangle(154, 464, 182, 32);
			
			if (tutButton.contains(screenX, screenY) && Gdx.input.isTouched()) {
				
				setScreen(new ImageScreen(Gdx.files.internal("howto.png"), batch));
				
			}
			
			Rectangle creditsButton = new Rectangle(175, 537, 146, 31);
			
			if (creditsButton.contains(screenX, screenY) && Gdx.input.isTouched()) {
				
				setScreen(new ImageScreen(Gdx.files.internal("credits.png"), batch));
				
			}
			
			Rectangle quitButton = new Rectangle(198, 611, 99, 30);
			
			if ((quitButton.contains(screenX, screenY) && Gdx.input.isTouched())) {
				Gdx.app.exit();
			}
		} else {
			if (getScreen() instanceof GameScreen) {
				//check game over for victory screen
				GameScreen screen = (GameScreen) getScreen();
				
				if (screen.getBoard().isGameOver()) {
					boolean team = screen.getBoard().getWinner();
					
					ChessBoard board = screen.getBoard();
					
					//Stats
					float time = board.getElapsedTime();
					
					if (time > StatsScreen.longestGame){
						StatsScreen.longestGame = time;
					}
					
					if (time < StatsScreen.shortestGame){
						StatsScreen.shortestGame = time;
					}
					
					StatsScreen.totalTime += time;
					
					
					if (team == ChessBoard.WHITE_TEAM) {
						setScreen(new ImageScreen(Gdx.files.internal("victoryscreens.png"), 0, 0, 480, 800, batch));
						StatsScreen.whiteWins++;
					} else {
						setScreen(new ImageScreen(Gdx.files.internal("victoryscreens.png"), 480, 0, 480, 800, batch));
						StatsScreen.blackWins++;
					}
					
					StatsScreen.totalGames++;
					
					StatsScreen.commit();
				}
			}
			
			if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				setScreen(new MainMenuScreen(batch));
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		Gdx.gl.glViewport(0, 0, width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
