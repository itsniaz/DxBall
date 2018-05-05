package com.niaz.dxball;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
	}
	
	@Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void onStop() {
        super.onStop();
    }

    public void onClickPlay(View view) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new GameView(this));
    }


    public void onClickHighScore(View view) {
    	Log.d("Click-Log","Click on High Score Button");
        Intent i = new Intent(MainActivity.this,HighScore.class);
        startActivity(i);
    }

    public void onClickExit(View view) {
       this.finish();
       System.exit(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        System.exit(0);
    }
	
}
