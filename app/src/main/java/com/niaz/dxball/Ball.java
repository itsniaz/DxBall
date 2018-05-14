package com.niaz.dxball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
    int level;
    MediaPlayer firstHitl1;
    MediaPlayer firstHitl2;
    MediaPlayer secondHitl2;
    MediaPlayer barHit;

    public Ball()
    {


	}

    public Ball(Context context,int speed,int level)
    {

        this.context = context;
        this.dx = speed;
        this.dy = -(speed);
        this.level=level;
        firstHitl1 = MediaPlayer.create(context, R.raw.firsthit);
        firstHitl2 = MediaPlayer.create(context, R.raw.firsthit2);
        secondHitl2 = MediaPlayer.create(context, R.raw.secondhit);
        barHit = MediaPlayer.create(context, R.raw.barhit);

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

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void setBall(Canvas canvas,Bar bar) {
        float barMid = (bar.getBarRight()-bar.getBarLeft())/2;
        x = bar.getBarLeft()+barMid;
        y = bar.getBarTop()-radius;
    }

    public void setSpeed(int speed)
    {
        setDX(speed);
        setDY(-speed);
    }
    public void drawBall(Canvas canvas, Paint paint){
    	//Log.d("Draw-Entry","Draw Ball in Ball Class");
        canvas.drawCircle(x,y,radius,paint);
    }

    public void nextPos(Canvas canvas, Bar bar, Paint paint) {

        //If the ball hit x axis boundary
    	if(x< radius || x > (canvas.getWidth() - radius)){
    		dx = -dx;
    	}

        //If the ball hits top y axis boundary
    	else if(y < radius){
    		dy=-dy;
    	}

    	//Ball drop on the bar
    	else if (y + radius > bar.getBarTop() && x > bar.getBarLeft() && x < bar.getBarRight()) {
    		 dy = -dy;
    		 barHit.start();
    	}

//        //Ball drop on left edge
//    	else if(x+radius == bar.getBarLeft() - 20 && y>=bar.getBarTop()){
//            dx = - dx;
//        }
//
//        //Ball drop on right edge
//    	else if(x+radius == bar.getBarRight() + 10 && y >= bar.getBarTop()){
//            dy = - dy;
//        }

        //Ball falls out of bar
    	else if(y>bar.getBarBottom()-radius){
    		dx=0;
    		dy=0;
    		GameView.life--;
    		isBallAvailable=false;
    		return;
    	}
        x += dx;
        y += dy;
    
    }

    
    public void ballBrickCollision(ArrayList<Brick> br, Ball ball){

        if(level==1)
        {
            for(int i=0;i<br.size();i++) {
                if (((ball.getY() - ball.getRadius()) <= br.get(i).getBottom()) && ((ball.getY() + ball.getRadius()) >= br.get(i).getTop()) && ((ball.getX()) >= br.get(i).getLeft()) && ((ball.getX()) <= br.get(i).getRight())) {
                   firstHitl1.start();
                    br.remove(i);
                    GameView.score +=10;
                    ball.setDY(-(ball.getDY()));
                }
                else if(((ball.getY()) <= br.get(i).getBottom()) && ((ball.getY()) >= br.get(i).getTop()) && ((ball.getX() + ball.getRadius()) >= br.get(i).getLeft()) && ((ball.getX() - ball.getRadius()) <= br.get(i).getRight())) {
                    firstHitl1.start();
                    br.remove(i);
                    GameView.score +=10;
                    ball.setDX(-(ball.getDX()));
                }
            }
        }
        else if(level==2)
        {
            for(int i=0;i<br.size();i++) {
                if (((ball.getY() - ball.getRadius()) <= br.get(i).getBottom()) && ((ball.getY() + ball.getRadius()) >= br.get(i).getTop()) && ((ball.getX()) >= br.get(i).getLeft()) && ((ball.getX()) <= br.get(i).getRight())) {
                    if(br.get(i).getHit()==0)
                   {
                       firstHitl2.start();
                       br.get(i).hit++;
                       br.get(i).setPaintColor(Color.RED);
                   }
                   else if(br.get(i).getHit()==1)
                    {
                        secondHitl2.start();
                        br.remove(i);
                        GameView.score +=10;
                    }

                    ball.setDY(-(ball.getDY()));
                }
                else if(((ball.getY()) <= br.get(i).getBottom()) && ((ball.getY()) >= br.get(i).getTop()) && ((ball.getX() + ball.getRadius()) >= br.get(i).getLeft()) && ((ball.getX() - ball.getRadius()) <= br.get(i).getRight())) {
                    if(br.get(i).getHit()==0)
                    {
                        br.get(i).hit++;
                        br.get(i).setPaintColor(Color.RED);
                    }
                    else if(br.get(i).getHit()==1)
                    {
                        br.remove(i);
                        GameView.score +=10;
                    }
                    GameView.score +=10;
                    ball.setDX(-(ball.getDX()));
                }
            }

        }

    }
}