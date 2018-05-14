package com.niaz.dxball;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

class GameView extends View{
	
	public static int life = 3;
    public static boolean gameOver=false;
    static int score=0;
    int level;
    float xT;
    int speed;
    
    boolean createGame;
    boolean gameStart = true;
    boolean start = false;

    Context context;
    Canvas canvas;
    Paint paint;
    Bar bar;
    Ball ball;
    Score scorelist;
    ArrayList<Brick>bricks =new ArrayList<Brick>();
    Stage stage = new Stage();

    

//    @SuppressLint("ClickableViewAccessibility")
	public GameView(Context context) {
        super(context);
        this.context = context;
        paint =new Paint();
        level = 1;
        createGame = true;
        gameOver = false;
        speed=10;
        
        bar = new Bar();
        ball = new Ball(context,speed,level);
        
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if(!start)
                {
                    start=true;
                }
				if(ball.isBallAvailable()){
					xT = e.getX();
					if(xT<v.getWidth()/2 && bar.getBarLeft()>0){
						bar.setBarLeft(bar.getBarLeft()-10);
						bar.setBarRight(bar.getBarRight()-10);
					}
					else if(xT >= v.getWidth()/2 && bar.getBarRight()<v.getWidth()){
						bar.setBarLeft(bar.getBarLeft()+10);
						bar.setBarRight(bar.getBarRight()+10);
					}
					Log.i("Bar Position" , "Left : " + bar.getBarLeft() + "  Right : " + bar.getBarRight());
				} 
            	return true;	
            }
        });
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (gameStart) {
            gameStart = false;
            bar.setBar(canvas);
            ball.setBall(canvas,bar);
        }
        canvas.drawRGB(255, 255, 255);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        ball.drawBall(canvas,paint);
        paint.setColor(Color.BLACK);
        bar.drawBar(canvas,paint);
        if(start)
        {
            ball.nextPos(canvas, bar, paint);
        }
        drawLifeText(canvas);
        
        if (createGame) {
            createGame = false;
            if (level == 1) {
                ball.setLevel(1);
                this.speed = 10;
                ball.setSpeed(speed);
                stage.levelOne(canvas,bricks); 
            }
            else if (level == 2) {
                stage.levelTwo(canvas,bricks);
                ball.setLevel(2);
                this.speed = 15;
                ball.setSpeed(15);
            }
            else{
                gameOver = true;
            }
        }
        for(int i = 0; i< bricks.size(); i++){
            canvas.drawRect(bricks.get(i).getLeft(), bricks.get(i).getTop(), bricks.get(i).getRight(), bricks.get(i).getBottom(), bricks.get(i).getPaint());
        }
        invalidate();
        ball.ballBrickCollision(bricks,ball);
        
        if(!ball.isBallAvailable() && !gameOver)
        {
            ball.setBallAvailable(true);
            gameStart = true;
            start=false;
            ball.setSpeed(speed);
        }

        if(life !=0 && bricks.size()==0){
        	level++;
        	gameStart=true;
        	createGame=true;
        	start=true;
        }
        
        if(life==0 || gameOver==true){
        	levelComplete();
        }
    }
    
    public void levelComplete() {
          Handler handler = new Handler();
          handler.postDelayed(new Runnable() {
                public void run() {
                     Intent i = new Intent(getContext(), Score.class);
                     i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     String strName = String.valueOf(score);
                     i.putExtra("LEVEL", strName);
                     getContext().startActivity(i);
                     System.exit(0);
       
                }
         }, 100);
    }

    public void drawLifeText(Canvas canvas) {
        paint.setColor(Color.RED);
        paint.setTypeface(Typeface.create("Arial",Typeface.BOLD));
        paint.setTextSize(60);
        String lives= "";
        if(life==1)
        {
            lives = "❤";
        }
        else if(life==2)
        {
            lives = "❤❤";
        }
        else if(life==3)
        {
            lives =  "❤❤❤";
        }
        canvas.drawText("LIFE : " + lives,canvas.getWidth() - 400 , 60, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("LEVEL : " + level, canvas.getWidth() / 2 - 100, 60, paint);
        canvas.drawText("SCORE : " + score,  20, 60, paint); 
    }
    

 
}