package com.nedswebsite.ktog;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity1 extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// USED THE FOLLOWING TO REMOVE TITLE BAR:
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
				
		setContentView(R.layout.activity_main_activity1);
		
		ImageView img = (ImageView)findViewById(R.id.menu1);
		
		final ImageButton onePlayerButton = (ImageButton) findViewById(R.id.imagebuttononeplayer);
		final ImageButton multiPlayerButton = (ImageButton) findViewById(R.id.imagebuttonmultiplayer);
		final ImageButton aboutButton = (ImageButton) findViewById(R.id.imagebuttonabout);
		
		// For sound for buttons:
		final MediaPlayer buttonSound = MediaPlayer.create(MainActivity1.this, R.raw.swordswing);
		
		onePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
			                    	
        	buttonSound.start();        	
        	
        	Toast.makeText(MainActivity1.this,"One player button is working!!", Toast.LENGTH_LONG).show();
        	//USE THIS WHEN READY??:
        	//Intent openMain2Activity = new Intent("com.example.ktog1.MAIN2ACTIVITY");
			//startActivity(openMain2Activity);		
        				
			}
		});
		
		multiPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
			                    	
        	buttonSound.start();        	
        	
        	Toast.makeText(MainActivity1.this,"Multi-player button is working!!", Toast.LENGTH_LONG).show();
        	//USE THIS WHEN READY??:
        	//Intent openMain2Activity = new Intent("com.example.ktog1.MAIN2ACTIVITY");
			//startActivity(openMain2Activity);		
        				
			}
		});
		
		aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
			                    	
        	buttonSound.start();
        	
        	//Think I need this so user doesn't have to push 'back' more than once (possibly)?
        	finish();
        	
        	Intent i = new Intent(MainActivity1.this, Rules.class);
        	MainActivity1.this.startActivity(i);
        	
        	//Toast.makeText(MainActivity1.this,"About button is working!!", Toast.LENGTH_LONG).show();
        	
        	//USE THIS WHEN READY??:
        	//Intent openMain2Activity = new Intent("com.example.ktog1.MAIN2ACTIVITY");
			//startActivity(openMain2Activity);		
        				
			}
		});
	
	}
}