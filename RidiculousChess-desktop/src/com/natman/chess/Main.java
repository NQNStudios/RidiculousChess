package com.natman.chess;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "RidiculousChess";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 800;
		cfg.resizable = false;
		
		new LwjglApplication(new ChessGame(), cfg);
	}
}
