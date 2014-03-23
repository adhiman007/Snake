package com.snake.game;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;

public class Snake {
	
	public static enum Direction{
		left,
		up,
		right,
		down
	};
	
	int xBoundary;
	int yBoundary;
	int numOfFruitAte;
	boolean snakeDead;
	Direction direction;
	
	List<Point> locations = new ArrayList<Point>();

	public Snake(Grid grid) {
		Point head = grid.center();
		Point tail = new Point(head.x + 1, head.y);
		locations.add(head);
		locations.add(tail);
		xBoundary = grid.getXBoundary();
		yBoundary = grid.getYBoundary();
		direction = Direction.left;
	}
	
	public List<Point> getLocations(){
		return locations;
	}
	
	public Point location(){
		return locations.get(0);
	}

	public Direction direction() {
		return direction;
	}
	
	public void setDirection(Direction direction){
		this.direction = direction;
	}
	
	public boolean ateFruit(Point fruitLocation){
		if(locations.get(0).equals(fruitLocation)){
			locations.add(locations.get(0));
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean getLivingState() {
		return snakeDead;
	}
	
	public void loop(){
		List<Point> newLocations = new ArrayList<Point>();
		newLocations.add(getNewHeadLocation());
		newLocations.add(locations.get(0));
		for(int i = 1; i < locations.size() - 1; i++){
			newLocations.add(locations.get(i));
			if(snakeDead){
				break;
			}
		}
		locations = newLocations;
		if (hitSelf(locations.get(0))){
			snakeDead = true;
		}
	}

	private Point getNewHeadLocation() {
		switch(direction){
			case left:
				return moveLeft();
			case up:
				return moveUp();
			case right:
				return moveRight();
			default:
				return moveDown();
		}
	}

	private Point moveLeft() {
		Point newLocation = new Point(locations.get(0));
		newLocation.x -= 1;
		if(locations.get(0).x == 0){
			snakeDead = true;
		}
		return newLocation;
	}

	private Point moveUp() {
		Point newLocation = new Point(locations.get(0));
		newLocation.y -= 1;
		if(locations.get(0).y == 0){
			snakeDead = true;
		}
		return newLocation;
	}
	
	private Point moveRight() {
		Point newLocation = new Point(locations.get(0));
		newLocation.x += 1;
		if(locations.get(0).x == xBoundary){
			snakeDead = true;
		}
		return newLocation;
	}
	
	private Point moveDown() {
		Point newLocation = new Point(locations.get(0));
		newLocation.y += 1;
		if(locations.get(0).y == yBoundary){
			snakeDead = true;
		}
		return newLocation;
	}
	
	private boolean hitSelf(Point newLocation) {
		for(int i = 1; i < locations.size(); i++){
			if(locations.get(i).equals(newLocation)){
				return true;
			}
		}
		return false;
	}
}
