package com.natman.chess.pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.natman.chess.ChessBoard;
import com.natman.chess.ChessPiece;
import com.natman.chess.Point;

public class Pawn extends ChessPiece {
	
	private boolean positionSet = false;
	private boolean moved = false;
	
	public boolean PASSANTMEBRO = false;
	
	public Pawn(boolean team) {
		super(team);
	}

	@Override
	protected void initSprite() {
		Texture texture = new Texture(Gdx.files.internal("pieces.png"));
		int y = (team == ChessBoard.BLACK_TEAM ? ChessBoard.SQUARE_HEIGHT : 0);
		TextureRegion region = new TextureRegion(texture, 0, y, ChessBoard.SQUARE_WIDTH, ChessBoard.SQUARE_HEIGHT);
		sprite = new Sprite(region);
	}
	
	@Override
	public void setPosition(int x, int y) {
		Point p = new Point(x, y);
		if (positionSet && !p.equals(position)) moved = true;
		
		if (positionSet && Math.abs(y - position.y) == 2) {
			//double move
			PASSANTMEBRO = true;
		}
		
		super.setPosition(x, y);
		
		if (!positionSet) positionSet = true;
	}

	@Override
	protected Array<Point> getMovePoints(ChessBoard board) {
		
		Array<Point> movePoints = new Array<Point>();
		
		if ((team == ChessBoard.WHITE_TEAM && position.y == 7) || (team == ChessBoard.BLACK_TEAM && position.y == 0)) {
			return movePoints;
		}
		
		ChessPiece leftSide = board.getPiece(position.x - 1, position.y);
		ChessPiece rightSide = board.getPiece(position.x + 1, position.y);
		
		if (leftSide != null && leftSide instanceof Pawn) {
			Pawn p = (Pawn) leftSide;
			
			if (p.PASSANTMEBRO) {
				Point spot = new Point(p.position);
				spot.y -= verticalMod;
				movePoints.add(spot);
			}
		}
		
		if (rightSide != null && rightSide instanceof Pawn) {
			Pawn p = (Pawn) rightSide;
			
			if (p.PASSANTMEBRO) {
				Point spot = new Point(p.position);
				spot.y += verticalMod;
				movePoints.add(spot);
			}
		}
		
		ChessPiece forward = board.getPiece(position.x, position.y + verticalMod);
		
		if (forward == null) {
			movePoints.add(new Point(position.x, position.y + verticalMod));
		}
		
		if (!moved) {
			ChessPiece farForward = board.getPiece(position.x, position.y + verticalMod * 2);
			
			if (farForward == null && forward == null)
				movePoints.add(new Point(position.x, position.y + verticalMod * 2));
		}
		
		ChessPiece forwardLeft = board.getPiece(position.x - 1, position.y + verticalMod);
		
		if (forwardLeft != null && enemyOf(forwardLeft)) {
			movePoints.add(new Point(position.x - 1, position.y + verticalMod));
		}
		
		ChessPiece forwardRight = board.getPiece(position.x + 1, position.y + verticalMod);
		
		if (forwardRight != null && enemyOf(forwardRight)) {
			movePoints.add(new Point(position.x + 1, position.y + verticalMod));
		}
		
		return movePoints;
		
	}
	
	public int getVertMod() {
		return verticalMod;
	}

}
