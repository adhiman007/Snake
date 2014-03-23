package com.snake.game;

import java.util.Date;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.snake.render.ScreenView;

public class Gameloop extends Thread {
	
	private SurfaceHolder surface;
	private ScreenView screenView;
	private Snake snake;
	private Fruit fruit;

	public Gameloop(SurfaceHolder surfaceHolder, ScreenView screenView, Snake snake, Fruit fruit) {
		this.surface = surfaceHolder;
		this.screenView = screenView;
		this.snake = snake;
		this.fruit = fruit;
	}
	
	@Override
	public void run() {
		Canvas canvas = null;
		long loopTimer = new Date().getTime();
		boolean snakeDead = false;
		int score = 0;
		while(!snakeDead){
			long newLoopTimer = new Date().getTime();
			if (newLoopTimer > loopTimer + 200){
				loopTimer = newLoopTimer;
				snake.loop();
				snakeDead = snake.getLivingState();
				if(snake.ateFruit(fruit.getLocation())){
					screenView.setScore(++score);
					fruit.makeNewFruit(snake.getLocations());
				}
			}
			render(canvas);
		}
		screenView.gameOver();
		render(canvas);
	}

	private void render(Canvas canvas) {
		canvas = null;
		// try locking the canvas for exclusive pixel editing
		// in the surface
		try {
			canvas = surface.lockCanvas();
			synchronized (surface) {
				screenView.render(canvas);				
			}
		} finally {
			// in case of an exception the surface is not left in 
			// an inconsistent state
			if (canvas != null) {
				surface.unlockCanvasAndPost(canvas);
			}
		}
	}
}
