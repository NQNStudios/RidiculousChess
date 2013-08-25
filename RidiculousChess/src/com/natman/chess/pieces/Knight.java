package com.natman.chess.pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.natman.chess.ChessBoard;
import com.natman.chess.ChessPiece;
import com.natman.chess.Point;

public class Knight extends ChessPiece {
	
	public Knight(boolean team) {
		super(team);
	}

	@Override
	protected void initSprite() {
		Texture texture = new Texture(Gdx.files.internal("pieces.png"));
		int y = (team == ChessBoard.BLACK_TEAM ? ChessBoard.SQUARE_HEIGHT : 0);
		TextureRegion region = new TextureRegion(texture, ChessBoard.SQUARE_WIDTH, y, ChessBoard.SQUARE_WIDTH, ChessBoard.SQUARE_HEIGHT);
		sprite = new Sprite(region);
	}

	@Override
	protected Array<Point> getMovePoints(ChessBoard board) {
		Array<Point> movePoints = new Array<Point>();
		
		for (int y = 0; y < ChessBoard.BOARD_HEIGHT; y++)
			for (int x = 0; x < ChessBoard.BOARD_WIDTH; x++) {
				Point p = new Point(x, y);
				
				int distX = Math.abs(p.x - position.x);
				int distY = Math.abs(p.y - position.y);
				
				if (distX + distY == 3 && !(distX == 3 || distY == 3)) {
					ChessPiece piece = board.getPiece(p.x, p.y);
					
					if (piece != null && piece.getTeam() == team) continue;
					movePoints.add(p);
				}
			}
		
		return movePoints;
	}

}
