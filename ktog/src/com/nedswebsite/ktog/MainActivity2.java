package com.nedswebsite.ktog;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity2 extends ActionBarActivity {			
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// USED THE FOLLOWING TO REMOVE TITLE BAR:
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
				
		setContentView(R.layout.activity_main_activity2);
		
		getWindow().getDecorView().setBackgroundColor(Color.BLACK);
		// for the little space between the action & attack button
		
		final MediaPlayer activityOpeningSound = MediaPlayer.create(MainActivity2.this, R.raw.sworddraw1);
		activityOpeningSound.start();
		
		TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);
		playerNameTextView.setText(ArrayOfPlayers.player[0]);
		
		TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
		computerNameTextView.setText(ArrayOfPlayers.player[1]);
	}
}
