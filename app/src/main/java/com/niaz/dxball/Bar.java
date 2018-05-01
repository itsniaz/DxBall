package com.niaz.dxball;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Bar {
    private float barLeft, barTop, barRight, barBottom;
    private float barWidth;
    
    public void setBar(Canvas canvas) {
    	barLeft= (canvas.getWidth()/2)-(canvas.getWidth()/6);
        barRight = barLeft + (canvas.getWidth()/3);
        barBottom = canvas.getHeight();
        barTop = barBottom - 60;
        barWidth = barRight-barLeft;
    }

    public void drawBar(Canvas canvas, Paint paint) {
        canvas.drawRect(barLeft, barTop, barRight, barBottom, paint);
    }

    public float getBarLeft(){
        return barLeft;
    }

    public float getBarBottom() {
        return barBottom;
    }

    public float getBarRight() {
        return barRight;
    }

    public float getBarTop() {
        return barTop;
    }

    public void setBarBottom(float barBottom) {
        this.barBottom = barBottom;
    }

    public void setBarTop(float barTop) {
        this.barTop = barTop;
    }

    public void setBarLeft(float barLeft) {
        this.barLeft = barLeft;
    }

    public void setBarRight(float barRight) {
        this.barRight = barRight;
    }
    
    public float getBarWidth(){
    	return this.barWidth;
    }
    
}