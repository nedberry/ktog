package com.nedswebsite.ktog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends ActionBarActivity {			
	
	ImageView computerAvatar;
	ImageView crossedswords2;
	ImageView stonedead2;	
	
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
		
		final ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
		
			titleBlankButton.setOnClickListener(new View.OnClickListener() {
	            @Override
				public void onClick(View v) {			                    	
	        	
	        	Toast.makeText(MainActivity2.this,"(QUICK RULES WILL GO HERE)", Toast.LENGTH_LONG).show();	        				
				}
			});		
		
		final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		
		TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);
		playerNameTextView.setText("  " + ArrayOfPlayers.player[0]);
		
		TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
		computerNameTextView.setText(ArrayOfPlayers.player[1]);
		
		
		computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft0);
		crossedswords2 = (ImageView) findViewById(R.id.imageviewavatarleft1);
		stonedead2 = (ImageView) findViewById(R.id.imageviewavatarleft2);
		
		if (ArrayOfAvatars.avatar[0].equals("computer")){
			crossedswords2.setVisibility(View.INVISIBLE);
			stonedead2.setVisibility(View.INVISIBLE);			
		}
		if (ArrayOfAvatars.avatar[0].equals("crossedswords")){
			computerAvatar.setVisibility(View.INVISIBLE);
			stonedead2.setVisibility(View.INVISIBLE);
		}
		if (ArrayOfAvatars.avatar[0].equals("stonedead")){
			crossedswords2.setVisibility(View.INVISIBLE);
			computerAvatar.setVisibility(View.INVISIBLE);
		}
		
		initiative (centerscrolltext);
		
	}
	
	public void initiative (final TextView centerscrolltext) {
	
		// Setting up scroll frame animation.
		ImageView img = (ImageView)findViewById(R.id.scrollanimation);
		img.setBackgroundResource(R.drawable.scrollanimationup);
	
		// Get the background, which has been compiled to an AnimationDrawable object.
		AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
					
		// Start the animation.
		frameAnimation.start();		
		
		
		final Handler h = new Handler();
		h.postDelayed(new Runnable() {

        	@Override
        	public void run()
        	{  
        		centerscrolltext.setVisibility(View.VISIBLE);
				centerscrolltext.append("Welcome, " + ArrayOfPlayers.player[0] + ".");				        						        						        		 			        						        	
			}

        }, 2500);		
	}	
}
