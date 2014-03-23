package com.snake.game;

import java.util.List;
import java.util.Random;

import android.graphics.Point;

public class Fruit {
	
	private int xBoundary;
	private int yBoundary;
	private Point location;
	
	public Fruit(Grid grid){
		xBoundary = grid.getXBoundary();
		yBoundary = grid.getYBoundary();
		
		location = new Point();
		location.x = grid.center().x;
		location.y = grid.center().y;
		while(location.equals(grid.center())){
			location = getNewRandomLocation();
		}
	}
	
	public Point getLocation(){
		return location;
	}
	
	public void makeNewFruit(List<Point> snakeLocations){
		while(isOnSnake(snakeLocations, getNewRandomLocation())){ }
		
	}
	
	private Point getNewRandomLocation(){
		Random randomNumber = new Random();
		return new Point(randomNumber.nextInt(xBoundary), randomNumber.nextInt(yBoundary));
	}
	
	private boolean isOnSnake(List<Point> snakeLocation, Point fruitLocation){
		for(Point location : snakeLocation){
			if(location.equals(fruitLocation)){
				return true;
			}
		}
		location = fruitLocation;
		return false;
	}
}
