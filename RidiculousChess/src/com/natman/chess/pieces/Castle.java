package com.natman.chess.pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.natman.chess.ChessBoard;
import com.natman.chess.ChessPiece;
import com.natman.chess.Point;

public class Castle extends ChessPiece {

	private boolean positionSet = false;
	private boolean moved = false;
	
	public Castle(boolean team) {
		super(team);
	}
	
	@Override
	public void setPosition(int x, int y) {
		Point p = new Point(x, y);
		if (positionSet && !p.equals(position)) moved = true;
		
		super.setPosition(x, y);
		
		if (!positionSet) positionSet = true;
	}

	@Override
	protected void initSprite() {
		Texture texture = new Texture(Gdx.files.internal("pieces.png"));
		int y = (team == ChessBoard.BLACK_TEAM ? ChessBoard.SQUARE_HEIGHT : 0);
		TextureRegion region = new TextureRegion(texture, ChessBoard.SQUARE_WIDTH * 2, y, ChessBoard.SQUARE_WIDTH, ChessBoard.SQUARE_HEIGHT);
		sprite = new Sprite(region);
	}
	
	@Override
	protected Array<Point> getMovePoints(ChessBoard board) {
		
		Array<Point> movePoints = new Array<Point>();
		
		Point p = new Point(position);
		
		while (p.x > 0) {
			p = new Point(p.x - 1, p.y);
			
			if (!board.isOccupied(p)) {
				movePoints.add(p);
			} else {
				if (board.getPiece(p.x, p.y).getTeam() == team) break;
				else {
					movePoints.add(p); break;
				}
			}
		}
		
		p = new Point(position);
		
		while (p.x < 7) {
			p = new Point(p.x + 1, p.y);
			
			if (!board.isOccupied(p)) {
				movePoints.add(p);
			} else {
				if (board.getPiece(p.x, p.y).getTeam() == team) break;
				else {
					movePoints.add(p); break;
				}
			}
		}
		
		p = new Point(position);
		
		while (p.y > 0) {
			p = new Point(p.x, p.y - 1);
			
			if (!board.isOccupied(p)) {
				movePoints.add(p);
			} else {
				if (board.getPiece(p.x, p.y).getTeam() == team) break;
				else {
					movePoints.add(p); break;
				}
			}
		}
		
		p = new Point(position);
		
		while (p.y < 7) {
			p = new Point(p.x, p.y + 1);
			
			if (!board.isOccupied(p)) {
				movePoints.add(p);
			} else {
				if (board.getPiece(p.x, p.y).getTeam() == team) break;
				else {
					movePoints.add(p); break;
				}
			}
			
		}
	
		return movePoints;
		
	}

	public boolean hasMoved() {
		return moved;
	}
	
}
