package com.natman.chess;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public abstract class ChessPiece {
	
	protected boolean team;
	protected int verticalMod;
	protected Point position;
	
	protected Sprite sprite;
	
	public ChessPiece(boolean team) {
		this.team = team;
		
		if (team == ChessBoard.WHITE_TEAM)
			verticalMod = ChessBoard.WHITE_VERTICAL_MOD;
		
		if (team == ChessBoard.BLACK_TEAM)
			verticalMod = ChessBoard.BLACK_VERTICAL_MOD;
		
		initSprite();
	}
	
	public boolean getTeam() {
		return team;
	}
	
	public boolean enemyOf(ChessPiece other) {
		return other.team != team;
	}
	
	public boolean canMoveTo(ChessBoard board, Point p) {
		return getMovePoints(board).contains(p, false);
	}
	
	public void setPosition(int x, int y) {
		position = new Point(x, y);
		sprite.setPosition(x * ChessBoard.SQUARE_WIDTH, ChessBoard.BOARD_OFFSET + y * ChessBoard.SQUARE_HEIGHT);
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void render(SpriteBatch spriteBatch) {
		sprite.draw(spriteBatch);
	}
	
	protected abstract void initSprite();
	protected abstract Array<Point> getMovePoints(ChessBoard board);
	
}
