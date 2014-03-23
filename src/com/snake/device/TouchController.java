package com.snake.device;


import com.snake.game.Snake;
import com.snake.render.ScreenView;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

public class TouchController {
	//The object that handles drawing
	ScreenView m_draw;
	Snake snake;
	private PointF pixelCenter;
	
	public TouchController(ScreenView _draw, Snake snake, PointF pixelCenter){
		m_draw = _draw;
		this.snake = snake;
		this.pixelCenter = pixelCenter;
	}
	
	public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        	case MotionEvent.ACTION_DOWN:
        		if(snake.direction() == Snake.Direction.right || snake.direction() == Snake.Direction.left){
        			processVerticalMovement(event.getY());
        		}
        		else{
        			processHorazontalMovement(event.getX());
        		}
        	break;
	           
	        case MotionEvent.ACTION_POINTER_DOWN:

	        break;
	           
	        case MotionEvent.ACTION_UP:

	        case MotionEvent.ACTION_POINTER_UP:

	        break;
	           
	        case MotionEvent.ACTION_MOVE:

	        break;
        }
//        m_draw.postInvalidate();
        return true; // indicate event was handled
     }

	private void processVerticalMovement(float touchY) {
		if(touchY > pixelCenter.y){
			snake.setDirection(Snake.Direction.down);
		}
		else{
			snake.setDirection(Snake.Direction.up);
		}
	}
	
	private void processHorazontalMovement(float touchX) {
		if(touchX > pixelCenter.x){
			snake.setDirection(Snake.Direction.right);
		}
		else{
			snake.setDirection(Snake.Direction.left);
		}
	}
}
