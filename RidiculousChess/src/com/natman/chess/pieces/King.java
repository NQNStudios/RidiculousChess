package com.natman.chess.pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.natman.chess.ChessBoard;
import com.natman.chess.ChessPiece;
import com.natman.chess.Point;

public class King extends ChessPiece {

	private boolean positionSet = false;
	private boolean moved = false;
	
	public boolean IS_IN_CHECK = false;
	
	public King(boolean team) {
		super(team);
	}

	@Override
	protected void initSprite() {
		Texture texture = new Texture(Gdx.files.internal("pieces.png"));
		int y = (team == ChessBoard.BLACK_TEAM ? ChessBoard.SQUARE_HEIGHT : 0);
		TextureRegion region = new TextureRegion(texture, ChessBoard.SQUARE_WIDTH * 4, y, ChessBoard.SQUARE_WIDTH, ChessBoard.SQUARE_HEIGHT);
		sprite = new Sprite(region);
	}
	
	@Override
	public void setPosition(int x, int y) {
		Point p = new Point(x, y);
		if (positionSet && !p.equals(position)) moved = true;
		
		super.setPosition(x, y);
		
		if (!positionSet) positionSet = true;
	}
	
	@Override
	protected Array<Point> getMovePoints(ChessBoard board) {
		board.checkForCheck();
		
		Array<Point> movePoints = new Array<Point>();
		
		if (!moved && !IS_IN_CHECK && board.getFreshCastles(team).size > 0) {
			for (Castle castle : board.getFreshCastles(team)) {
				boolean canCastleTo = true;
				Point loc = new Point(castle.getPosition());
				
				int dist = position.x - loc.x;
				
				if (dist > 0) {
					for (int i = 1; i < dist; i++) {
						loc.x++;
						
						if (board.isOccupied(loc)) {
							canCastleTo = false;
						}
					}
				} else if (dist < 0) {
					for (int i = -1; i > dist; i--) {
						loc.x--;
						
						if (board.isOccupied(loc)) {
							canCastleTo = false;
						}
					}
				}
				
				if (canCastleTo) {
					Point p = new Point(position);
					
					if (dist < 0) {
						p.x += 2;
					} else {
						p.x -= 2;
					}
					
					movePoints.add(p);
				}
			}
		}
		
		Point p = new Point(position.x - 1, position.y);
		if (!board.isOccupied(p) || board.getPiece(p.x, p.y).getTeam() != team) movePoints.add(p);
		
		p = new Point(position.x - 1, position.y - 1);
		if (!board.isOccupied(p) || board.getPiece(p.x, p.y).getTeam() != team) movePoints.add(p);
	
		p = new Point(position.x - 1, position.y + 1);
		if (!board.isOccupied(p) || board.getPiece(p.x, p.y).getTeam() != team) movePoints.add(p);
		
		p = new Point(position.x, position.y - 1);
		if (!board.isOccupied(p) || board.getPiece(p.x, p.y).getTeam() != team) movePoints.add(p);
		
		p = new Point(position.x, position.y + 1);
		if (!board.isOccupied(p) || board.getPiece(p.x, p.y).getTeam() != team) movePoints.add(p);
		
		p = new Point(position.x + 1, position.y - 1);
		if (!board.isOccupied(p) || board.getPiece(p.x, p.y).getTeam() != team) movePoints.add(p);
		
		p = new Point(position.x + 1, position.y);
		if (!board.isOccupied(p) || board.getPiece(p.x, p.y).getTeam() != team) movePoints.add(p);
		
		p = new Point(position.x + 1, position.y + 1);
		if (!board.isOccupied(p) || board.getPiece(p.x, p.y).getTeam() != team) movePoints.add(p);
		
		return movePoints;
	}

}
