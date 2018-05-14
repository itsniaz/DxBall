package com.niaz.dxball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;

public class Brick {
    float bricktop,brickbottom,brickleft,brickright;
    Canvas canvas = new Canvas();
    Paint paint;
    Point point;
    int x,y;
    int brickcolor;
    int hit=0;

    
    Brick(float left,float top,float right,float bottom,int color){
        this.brickleft =left;
        this.bricktop=top;
        this.brickright=right;
        this.brickbottom=bottom;
        this.brickcolor=color;
        paint = new Paint();
        paint.setColor(brickcolor);
    }

    public void setBottom(float bottom) {
        this.brickbottom = bottom;
    }

    public void setLeft(float left) {
        this.brickleft = left;
    }

    public void setRight(float right) {
        this.brickright = right;
    }

    public void setTop(float top) {
        this.bricktop = top;
    }

    public void setPaintColor(int color)
    {
        paint.setColor(color);
    }
    public float getLeft() {
        return brickleft;
    }

    public float getRight() {
        return brickright;
    }

    public float getBottom() {
        return brickbottom;
    }

    public Paint getPaint() {
        return paint;
    }

    public float getTop() {
        return bricktop;
    }

    public int getHit()
    {
        return  hit;
    }
}