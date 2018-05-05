package com.niaz.dxball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.util.Log;

import java.util.ArrayList;

public class Ball {
    private boolean isBallAvailable = true;
    private float x,y;
    private float dx = 5;
    private float dy = -5;
    private float radius=30;
    private Context  context;
    MediaPlayer player;

    public Ball()
    {


	}

    public Ball(Context context)
    {

        this.context = context;
    }
    public boolean isBallAvailable() {
        return isBallAvailable;
    }
    
    public void setBallAvailable(boolean isBballAvailable) {
        this.isBallAvailable = isBballAvailable;
    }
    
    public void setX(float x) { 
    	this.x = x; 
    }
    
    public float getX() { 
    	return x; 
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    
    public void setDX(float dx) { 
    	this.dx = dx; 
    }

    public float getDX() {
        return dx;
    }

    public void setDY(float dy) { 
    	this.dy = dy; 
    }

    public float getDY() {
        return dy;
    }

    public void setRadius(float radius) { 
    	this.radius = radius; 
    }
    
    public float getRadius() { 
    	return radius; 
    }


    public void setBall(Canvas canvas,Bar bar) {
        float barMid = (bar.getBarRight()-bar.getBarLeft())/2;
        x = bar.getBarLeft()+barMid;
        y = bar.getBarTop()-radius;
    }

    public void drawBall(Canvas canvas, Paint paint){
    	//Log.d("Draw-Entry","Draw Ball in Ball Class");
        canvas.drawCircle(x,y,radius,paint);
    }

    public void nextPos(Canvas canvas, Bar bar, Paint paint) {

        //If the ball gets out of window through x axis
    	if(x< radius || x > (canvas.getWidth() - radius)){
    		dx = -dx;
    	}

        //If the ball gets out of window through top y axis
    	else if(y < radius|| y > (canvas.getHeight() - radius)){
    		dy=-dy;
    	}

    	//Ball drop on the bar
    	else if (y + radius > bar.getBarTop() && x > bar.getBarLeft() && x < bar.getBarRight()) {
    		 dy = -dy; 
    	}

        //Ball drop on left edge
    	else if(x+radius == bar.getBarLeft() - 20 && y>=bar.getBarTop()){
            dx = - dx;
        }

        //Ball drop on right edge
    	else if(x+radius == bar.getBarRight() + 10 && y >= bar.getBarTop()){
            dy = - dy;
        }

        //Ball falls out of bar
    	else if(y>bar.getBarBottom()-radius){
    		dx=0;
    		dy=0;
    		GameView.life--;
    		isBallAvailable=false;
    	}
        x += dx;
        y += dy;
    
    }

    
    public void ballBrickCollision(ArrayList<Brick> br, Ball ball){
        for(int i=0;i<br.size();i++) {
            if (((ball.getY() - ball.getRadius()) <= br.get(i).getBottom()) && ((ball.getY() + ball.getRadius()) >= br.get(i).getTop()) && ((ball.getX()) >= br.get(i).getLeft()) && ((ball.getX()) <= br.get(i).getRight())) {
                br.remove(i);
                GameView.score +=10;
                ball.setDY(-(ball.getDY()));
            }
            else if(((ball.getY()) <= br.get(i).getBottom()) && ((ball.getY()) >= br.get(i).getTop()) && ((ball.getX() + ball.getRadius()) >= br.get(i).getLeft()) && ((ball.getX() - ball.getRadius()) <= br.get(i).getRight())) {
                br.remove(i);
                GameView.score +=10;
                ball.setDX(-(ball.getDX()));
            }
        }
    }
}