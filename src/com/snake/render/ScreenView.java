package com.snake.render;

import com.snake.game.Fruit;
import com.snake.game.Gameloop;
import com.snake.game.Grid;
import com.snake.game.Snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class ScreenView extends SurfaceView implements SurfaceHolder.Callback {
	Paint paint;
	Grid grid;
	Snake snake;
	Fruit fruit;
	Gameloop gameLoop;
	
	int width;
	int height;
	boolean gameOver;
	boolean drawingStarted;
	private int score;
	
    public ScreenView(Context context, Grid grid, Snake snake, Fruit fruit){
        super(context);
        getHolder().addCallback(this);
        this.grid = grid;
        this.snake = snake;
        this.fruit = fruit;
        gameLoop = new Gameloop(getHolder(), this, snake, fruit);
        paint = new Paint();
    }
    
    public boolean drawingStart(){
    	return drawingStarted;
    }
    
    public void gameOver(){
    	gameOver = true;
    }
    
    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
    	this.width = width;
    	this.height = height;
        grid.setDeviceWidth(width);
        grid.setDeviceHeight(height);
        super.onSizeChanged(width, height, oldWidth, oldHeight);
    }

    public void render(Canvas canvas) {  
        //Fill canvas with white
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        
        if(gameOver){
        	paint.setColor(Color.BLACK);
        	paint.setTextSize(32f);
        	canvas.drawText("Game Over!", grid.getPixelCenter().x - 20, grid.getPixelCenter().y - 20, paint);
        }
        else if(grid.getSizeOfX() != 0){
        	drawSnake(canvas);
        	drawFruit(canvas);
        	drawScore(canvas);
//	        drawGridPoints(canvas);
        }
        
        drawingStarted = true;
    }
    
    

	private void drawSnake(Canvas canvas){
    	paint.setColor(Color.BLACK);
    	for(Point location : snake.getLocations()){
    		drawRect(canvas, location.x, location.y);
    	}
    }
    
    private void drawFruit(Canvas canvas){
    	paint.setColor(Color.BLUE);
    	drawRect(canvas, fruit.getLocation().x, fruit.getLocation().y);
    }
    
    private void drawScore(Canvas canvas) {
    	paint.setColor(Color.BLACK);
    	paint.setTextSize(16f);
    	canvas.drawText("Score: " + score, 40, 40, paint);
	}

	private void drawGridPoints(Canvas canvas) {
		for(int x = 0; x <= grid.getXBoundary(); x++){
			for(int y = 0; y <= grid.getYBoundary(); y++){
				if(x % 2 == 0 || y % 2 == 0) { 
					paint.setColor(Color.WHITE); 
				}
				else { 
					paint.setColor(Color.BLACK); 
				}
				drawRect(canvas, x, y);
			}
		}
	}

	private void drawRect(Canvas canvas, int x, int y) {
		canvas.drawRect(((float)x) * grid.getSizeOfX(), 
				((float)y) * grid.getSizeOfY(), 
				(((float)x) * grid.getSizeOfX()) + grid.getSizeOfX(), 
				(((float)y) * grid.getSizeOfY()) + grid.getSizeOfY(), 
				paint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		gameLoop.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		Log.d("Snake", "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				gameLoop.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d("Snake", "Thread was shut down cleanly");
	}

	public void setScore(int score) {
		this.score = score;
	}
}