package com.snake.game;

import android.graphics.Point;
import android.graphics.PointF;
import android.view.Display;

public class Grid {
	private int xBoundary;
	private int yBoundary;
	private int sizeOfX;
	private int sizeOfY;
	private Point center;
	private PointF pixelCenter;
	private Point navigationSize;

	public Grid(int x, int y, Display display, Point navigationSize){
		pixelCenter = new PointF();
		xBoundary = x;
		yBoundary = y;
		center = new Point();
		center.x = xBoundary / 2;
		center.y = yBoundary / 2;
		this.navigationSize = navigationSize;
	}
	
	public float getSizeOfX(){
		return sizeOfX;
	}
	
	public float getSizeOfY(){
		return sizeOfY;
	}
	
	public int getXBoundary(){
		return xBoundary;
	}
	
	public int getYBoundary(){
		return yBoundary;
	}
	
	public Point center(){
		return center;
	}
	
	public PointF getPixelCenter(){
		return pixelCenter;
	}
	
	public void setDeviceWidth(int deviceWidth){
		sizeOfX = (deviceWidth  - navigationSize.x) / xBoundary;
		pixelCenter.x = deviceWidth / 2;
	}
	
	public void setDeviceHeight(int deviceHeight){
		sizeOfY = deviceHeight / yBoundary;
		pixelCenter.y = deviceHeight / 2;
	}
}
