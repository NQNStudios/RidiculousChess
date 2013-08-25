package com.natman.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.natman.chess.pieces.*;

public final class ChessBoard implements InputProcessor {
	
	//TODO memory leaks all over
	
	public static final boolean WHITE_TEAM = false;
	public static final boolean BLACK_TEAM = true;
	
	public static final int WHITE_VERTICAL_MOD = 1;
	public static final int BLACK_VERTICAL_MOD = -1;
	
	public static final int BOARD_WIDTH = 8;
	public static final int BOARD_HEIGHT = 8;
	
	public static final int BOARD_OFFSET = 100;
	
	public static final int SQUARE_WIDTH = 60;
	public static final int SQUARE_HEIGHT = 75;
	
	private boolean gameOver = false;
	private boolean winner;
	
	private ChessPiece[][] board = new ChessPiece[BOARD_WIDTH][BOARD_HEIGHT];
	private boolean team = WHITE_TEAM;
	private ChessPiece selectedPiece;
	
	private Sprite boardSprite;
	private Texture overlayTexture;
	private TextureRegion blueOverlay;
	private TextureRegion redOverlay;
	
	private Texture countdownTexture;
	private Sprite topMessage;
	private Animation topCountdown;
	private Sprite bottomMessage;
	private Animation bottomCountdown;
	
	private GameTimer elapsedTimer;
	private Timer timer;
	
	public ChessBoard() {
		Texture texture = new Texture(Gdx.files.internal("board.png"));
		TextureRegion region = new TextureRegion(texture, 480, 600);
		boardSprite = new Sprite(region);
		boardSprite.setPosition(0, BOARD_OFFSET);
		
		overlayTexture = new Texture(Gdx.files.internal("overlays.png"));
		blueOverlay = new TextureRegion(overlayTexture, SQUARE_WIDTH, SQUARE_HEIGHT);
		redOverlay = new TextureRegion(overlayTexture, SQUARE_WIDTH, 0, SQUARE_WIDTH, SQUARE_HEIGHT);
		
		setBoard();
		
		timer = new Timer(10);
		elapsedTimer = new GameTimer();
		
		countdownTexture = new Texture(Gdx.files.internal("countdown.png"));
		TextureRegion topMsgRegion = new TextureRegion(countdownTexture, 601, 0, 374, 75);
		topMessage = new Sprite(topMsgRegion);
		topMessage.setPosition(15, 700);
		
		TextureRegion topAnimationRegion = new TextureRegion(countdownTexture, 600, 75);
		topCountdown = new Animation(1f, topAnimationRegion.split(60, 75)[0]);
		
		TextureRegion bottomMsgRegion = new TextureRegion(countdownTexture, 607, 79, 374, 72);
		bottomMessage = new Sprite(bottomMsgRegion);
		bottomMessage.setPosition(80, 0);
		
		TextureRegion bottomAnimationRegion = new TextureRegion(countdownTexture, 0, 75, 600, 75);
		bottomCountdown = new Animation(1f, bottomAnimationRegion.split(60, 75)[0]);
	}
	
	public void setBoard() {
		
		for (int y = 0; y < BOARD_HEIGHT; y++) {
			for (int x = 0; x < BOARD_WIDTH; x++) {
				board[x][y] = null;
			}
		}
		
		//Place pawns
		int y = 1;
		for (int x = 0; x < BOARD_WIDTH; x++) {
			board[x][y] = new Pawn(WHITE_TEAM);
		}
		
		y = 6;
		for (int x = 0; x < BOARD_WIDTH; x++) {
			board[x][y] = new Pawn(BLACK_TEAM);
		}
		
		//Place knights
		int x = 1;
		y = 0;
		board[x][y] = new Knight(WHITE_TEAM);
		x = 6;
		board[x][y] = new Knight(WHITE_TEAM);
		
		y = 7;
		board[x][y] = new Knight(BLACK_TEAM);
		x = 1;
		board[x][y] = new Knight(BLACK_TEAM);
		
		//place bishops
		x = 2;
		y = 0;
		board[x][y] = new Bishop(WHITE_TEAM);
		x = 5;
		board[x][y] = new Bishop(WHITE_TEAM);
		
		y = 7;
		board[x][y] = new Bishop(BLACK_TEAM);
		x = 2;
		board[x][y] = new Bishop(BLACK_TEAM);
		
		//Place castles
		x = 0;
		y = 0;
		board[x][y] = new Castle(WHITE_TEAM);
		
		x = 7;
		board[x][y] = new Castle(WHITE_TEAM);
		
		y = 7;
		board[x][y] = new Castle(BLACK_TEAM);
		
		x = 0;
		board[x][y] = new Castle(BLACK_TEAM);
		
		//Place Queens
		x = 3;
		y = 0;
		board[x][y] = new Queen(WHITE_TEAM);
		
		y = 7;
		board[x][y] = new Queen(BLACK_TEAM);
		
		//Place Kings
		x = 4;
		y = 0;
		board[x][y] = new King(WHITE_TEAM);
		
		y = 7;
		board[x][y] = new King(BLACK_TEAM);
		
	}
	
	public boolean isOccupied(Point p) {
		return getPiece(p.x, p.y) != null; 
	}
	
	public ChessPiece getPiece(int x, int y) {
		if (x >= BOARD_WIDTH || x < 0 || y < 0 || y >= BOARD_HEIGHT) return null;
		
		return board[x][y];
	}
	
	public Array<Castle> getFreshCastles(boolean team) {
		Array<Castle> freshCastles = new Array<Castle>();
		
		ChessPiece piece1;
		ChessPiece piece2;
		
		if (team == WHITE_TEAM) {
			piece1 = getPiece(0, 0);
			piece2 = getPiece(7, 0);
		} else {
			piece1 = getPiece(0, 7);
			piece2 = getPiece(7, 7);
		}
		
		if (piece1 instanceof Castle) {
			Castle c1 = (Castle) piece1;
			if (!c1.hasMoved())
				freshCastles.add(c1);
		}
		
		if (piece2 instanceof Castle) {
			Castle c2 = (Castle) piece2;
			if (!c2.hasMoved())
				freshCastles.add(c2);
		}
		
		return freshCastles;
	}
	
	public void render(SpriteBatch spriteBatch) {
		boardSprite.draw(spriteBatch);
		
		for (int y = 0; y < BOARD_HEIGHT; y++) {
			for (int x = 0; x < BOARD_WIDTH; x++) {
				ChessPiece piece = board[x][y];
				
				if (piece != null) {
					piece.setPosition(x, y);
					piece.render(spriteBatch);
				}
			}
		}
		
		if (selectedPiece != null) {
			for (Point p : selectedPiece.getMovePoints(this)) {
				if (p.x < 0 || p.x >= 8 || p.y < 0 || p.y >= 8) continue;
				
				if (getPiece(p.x, p.y) == null) {
					spriteBatch.draw(blueOverlay, p.x * SQUARE_WIDTH, BOARD_OFFSET + p.y * SQUARE_HEIGHT);
				} else {
					spriteBatch.draw(redOverlay, p.x * SQUARE_WIDTH, BOARD_OFFSET + p.y * SQUARE_HEIGHT);
				}
			}
		}
		
		timer.update();
		elapsedTimer.update();
		
		if (timer.complete()) {
			toggleTeam();
		}
		
		if (team == WHITE_TEAM) {
			topMessage.draw(spriteBatch);
			spriteBatch.draw(topCountdown.getKeyFrame(timer.elapsed()), 400, 700);
		} else {
			bottomMessage.draw(spriteBatch);
			spriteBatch.draw(bottomCountdown.getKeyFrame(timer.elapsed()), 10, 0);//, originX, originY, width, height, scaleX, scaleY, rotation)
		}
	}
	
	private void movePiece(Point from, Point to) {
		ChessPiece piece = getPiece(from.x, from.y);
		
		ChessPiece deadPeace = getPiece(to.x, to.y);
		
		board[to.x][to.y] = piece;
		
		if (piece instanceof Pawn) {
			//check for endzone transformation
			if (piece.getTeam() == WHITE_TEAM) {
				if (to.y == 7) {
					board[to.x][to.y]= new Queen(WHITE_TEAM); 
				}
			} else {
				if (to.y == 0) {
					board[to.x][to.y] = new Queen(BLACK_TEAM);
				}
			}
			
			//check for passant
			Pawn pwn = (Pawn) piece;
			
			if (board[to.x][to.y - pwn.verticalMod] != null) {
				ChessPiece pc = board[to.x][to.y - pwn.verticalMod];
				
				if (pc instanceof Pawn) {
					Pawn pwned = (Pawn) pc;
					
					if (pwned.PASSANTMEBRO) {
						board[to.x][to.y - pwn.verticalMod] = null; 
					}
				}
			}
		}
		
		board[from.x][from.y] = null;
		
		if (deadPeace instanceof King) {
			gameOver = true;
			winner = !deadPeace.getTeam();
		}
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public boolean getWinner() {
		return winner;
	}
	
	public void pause() {
		timer.pause();
		elapsedTimer.pause();
	}
	
	public float getElapsedTime() {
		return elapsedTimer.getElapsed();
	}
	
	public void resume() {
		timer.resume();
		elapsedTimer.resume();
	}
	
	private void toggleTeam() {
		//de-passant tag
		for (int y = 0; y < BOARD_HEIGHT; y++) {
			for (int x = 0; x < BOARD_WIDTH; x++) {
				ChessPiece piece = board[x][y];
				
				if (piece == null) continue;
				
				if (piece.getTeam() != team) {
					if (piece instanceof Pawn) {
						Pawn pwn = (Pawn) piece;
						if (pwn.PASSANTMEBRO) {
							pwn.PASSANTMEBRO = false;
						}
					}
				}
			}
		}
		
		team = !team;
		selectedPiece = null;
		
		timer.reset();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenX /= ((float)Gdx.graphics.getWidth() / 480);
		screenY /= ((float)Gdx.graphics.getHeight() / 800);
		
		int boardX = screenX / SQUARE_WIDTH;
		int boardY = (800 - screenY - BOARD_OFFSET) / SQUARE_HEIGHT;
		Point p = new Point(boardX, boardY);
		
		if (boardX >= 0 && boardX < 8 && boardY >= 0 && boardY < 8) { //the square is valid
			
			if (selectedPiece == null) {
				if (getPiece(boardX, boardY) != null 
						&& getPiece(boardX, boardY).getTeam() == team 
						&& getPiece(boardX, boardY).getMovePoints(this).size > 0) {
					selectedPiece = getPiece(boardX, boardY);
				}
			} else {
				if (!selectedPiece.canMoveTo(this, p)) {
					selectedPiece = null;
				} else {
					if (selectedPiece instanceof King) {
						//Check for castling
						Point loc = selectedPiece.position;
						int distX = Math.abs(loc.x - p.x);
						int distY = Math.abs(loc.y - p.y);
						
						if (distX + distY == 2 && distX != 1) {
							//it's a castle!
							if (p.x == 2) {
								movePiece(new Point(0, p.y), new Point(3, p.y));
							} else if (p.x == 6) {
								movePiece(new Point(7, p.y), new Point(5, p.y));
							}
						}
					}
					
					movePiece(selectedPiece.position, p);
					selectedPiece = null;
					toggleTeam();
				}
			}
			
		}
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
