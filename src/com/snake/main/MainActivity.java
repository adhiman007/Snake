package com.snake.main;

import com.snake.render.ScreenView;
import com.snake.device.AccelerometerController;
import com.snake.device.TouchController;
import com.snake.game.Fruit;
import com.snake.game.Gameloop;
import com.snake.game.Grid;
import com.snake.game.Snake;
import com.snake.R;

import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MainActivity extends Activity implements OnTouchListener, SensorEventListener {

	ScreenView screenView;
	
	TouchController touchController;
	AccelerometerController accelerometerController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setupGame();
	}
	
	private void setupGame(){
		Point navigationSize = new Point();
		getNavigationSize(navigationSize);
		
		Grid grid = new Grid(20, 20, getWindowManager().getDefaultDisplay(), navigationSize);
		
		Snake snake = new Snake(grid);
		Fruit fruit = new Fruit(grid);
		screenView = new ScreenView(this, grid, snake, fruit);
        setContentView(screenView);
        
        touchController = new TouchController(screenView, snake, grid.getPixelCenter());
        screenView.setOnTouchListener(this);
        
        //Settings up Accelerometer settings
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerController = new AccelerometerController(screenView, sensorManager);
	}

	private void getNavigationSize(Point navigationSize) {
		Resources resources = this.getResources();
		int navigationHeightId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
		int navigationWidthId = resources.getIdentifier("navigation_bar_width", "dimen", "android");
		if (navigationHeightId > 0 && navigationHeightId > 0) {
			navigationSize.x = (int)resources.getDimension(navigationHeightId);
			navigationSize.y = (int)resources.getDimension(navigationWidthId);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    public boolean onTouch(View v, MotionEvent event){
    	return touchController.onTouch(v, event);
    }
    
    protected void onResume() {
    	super.onResume();
    	if(accelerometerController != null){
    		accelerometerController.onResume();
    	}
    }

    protected void onPause() {
    	super.onPause();
    	if(accelerometerController != null){
    		accelerometerController.onPause();
    	}
    }

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		accelerometerController.onAccuracyChanged(sensor, accuracy);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		accelerometerController.onSensorChanged(event);
	}
}
