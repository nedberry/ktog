package com.nedswebsite.ktog;

import java.util.Arrays;

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
	ImageView titleimageview;
	ImageView instructionsimageview;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// USED THE FOLLOWING TO REMOVE TITLE BAR:
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// This will hide the system bar until user swipes up from bottom or down from top.		
		getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		
		setContentView(R.layout.activity_main_activity2);		
		
		getWindow().getDecorView().setBackgroundColor(Color.BLACK);
		// For the little space between the action & attack button.	
		
		final MediaPlayer activityOpeningSound = MediaPlayer.create(MainActivity2.this, R.raw.buttonsound6);
		activityOpeningSound.start();			    			
			
			
		final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		
		TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);
		// Text not centered for the human player so inserted spaces.
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
		else if (ArrayOfAvatars.avatar[0].equals("crossedswords")){
			computerAvatar.setVisibility(View.INVISIBLE);
			stonedead2.setVisibility(View.INVISIBLE);
		}
		else if (ArrayOfAvatars.avatar[0].equals("stonedead")){
			crossedswords2.setVisibility(View.INVISIBLE);
			computerAvatar.setVisibility(View.INVISIBLE);
		}
		
		
		unfoldScrolls (centerscrolltext);
		
		showTitle();
		
		
		final ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
		
		titleBlankButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	
            	animateTitle();       	
			}	            
		});
	}
	
	
	// Destroys data in arrays, and pro-actively cleans up memory (finish) for the user (good practice?).
	@Override
    public void onBackPressed() {
			
			Arrays.fill(ArrayOfPlayers.player, null);
			Arrays.fill(ArrayOfAvatars.avatar, null);
			
            super.onBackPressed();
            this.finish();
    }
	
	public void showTitle() {
		
		final ImageView img1 = (ImageView)findViewById(R.id.titleanimation);		
		img1.setBackgroundResource(R.drawable.showtitle);		
		
		// Get the background, which has been compiled to an AnimationDrawable object.
		final AnimationDrawable frameAnimation1 = (AnimationDrawable) img1.getBackground();
		
		// Animation is just 1 slide so user can see title.
    	frameAnimation1.stop();
    	frameAnimation1.start();
    	
    	// Animation to show user quick instructions.
    	final Handler h = new Handler();
		h.postDelayed(new Runnable() {

        	@Override
        	public void run()
        	{  
        		animateTitle();				        						        						        		 			        						        	
			}

        }, 2700);
	}
	  
	public void unfoldScrolls (final TextView centerscrolltext) {
		
		// Setting up scroll frame animation.
		ImageView img = (ImageView)findViewById(R.id.scrollanimation);
		img.setBackgroundResource(R.drawable.scrollanimationup);
	
		// Get the background, which has been compiled to an AnimationDrawable object.
		AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
					
		// Start the animation.
		frameAnimation.stop();
		frameAnimation.start();		
		
		
		final Handler h = new Handler();
		h.postDelayed(new Runnable() {

        	@Override
        	public void run()
        	{  
        		centerscrolltext.setVisibility(View.VISIBLE);
				centerscrolltext.append("Welcome, " + ArrayOfPlayers.player[0] + ".");				        						        						        		 			        						        	
			}

        }, 2700);		
	}
	
	public void animateTitle() {
		
		final ImageView img1 = (ImageView)findViewById(R.id.titleanimation);		
		img1.setBackgroundResource(R.drawable.titleanimation);		
		
		// Get the background, which has been compiled to an AnimationDrawable object.
		final AnimationDrawable frameAnimation1 = (AnimationDrawable) img1.getBackground();
		
    	frameAnimation1.stop();
    	frameAnimation1.start();
	}	
}
