package com.nedswebsite.ktog;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
		// For the little space between the action & attack button.
		getWindow().getDecorView().setBackgroundColor(Color.BLACK);				
				
		
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		
		TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
		playerNameTextView.setTypeface(typeFace);		
		playerNameTextView.setText(ArrayOfPlayers.player[0]);
		
		TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
		computerNameTextView.setTypeface(typeFace);
		computerNameTextView.setText(ArrayOfPlayers.player[1]);		
		
		ArrayOfHitPoints.hitpoints[0] = 20;		
		TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
		playerHitPointsTextView.setTypeface(typeFace);
		playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
		
		ArrayOfHitPoints.hitpoints[1] = 20;
		TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
		computerHitPointsTextView.setTypeface(typeFace);
		computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[1]));
		
		computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
		crossedswords2 = (ImageView) findViewById(R.id.imageviewavatarleft2);
		stonedead2 = (ImageView) findViewById(R.id.imageviewavatarleft3);
		
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
		
		ArrayIsInitiativeStarted.isinitiativestarted[0] = "no";		
		
		final ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
		
		titleBlankButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	
            	if (ArrayIsInitiativeStarted.isinitiativestarted[0].equals("no")) {
            		myInitiativeNotStarted();            		
            	}
            	
            	else if (ArrayIsInitiativeStarted.isinitiativestarted[0].equals("yes")) {
            		myInitiativeIsStarted();            		
            	}            	       	
			}	            
		});
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
        
        // More sounds stuff:        
        final MediaPlayer activityOpeningSound = MediaPlayer.create(MainActivity2.this, R.raw.buttonsound6);
		activityOpeningSound.start();
        
        unfoldScrolls();
        preInitiativeTitle();
        
        final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		centerscrolltext.setMovementMethod(new ScrollingMovementMethod());
		
		//Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/Anaheim-Regular.ttf");
		centerscrolltext.setTypeface(typeFace);
		
		final Handler h1 = new Handler();
  	  	h1.postDelayed(new Runnable() {

  	  		@Override
  	  		public void run()
  	  		{  	  			
  	  			centerscrolltext.setVisibility(View.VISIBLE);
  	  			centerscrolltext.startAnimation(animAlphaText);
	  			centerscrolltext.append("> Welcome, " + ArrayOfPlayers.player[0] + ".");
	  			
	  			final Handler h2 = new Handler();
	  	  	  	h2.postDelayed(new Runnable() {

	  	  	  		@Override
	  	  	  		public void run()
	  	  	  		{  	  			
	  		  	  		sixSidedRollFromLeft();
	  		  			
		  		  		final Handler h3 = new Handler();
			  	  	  	h3.postDelayed(new Runnable() {
	
			  	  	  		@Override
			  	  	  		public void run()
			  	  	  		{  	  			
			  		  	  		centerscrolltext.setVisibility(View.VISIBLE);
			  		  	  		centerscrolltext.startAnimation(animAlphaText);
			  		  			centerscrolltext.append("\n" + "> Please slide the die...");
			  		  			
			  	  	  		}
			  	  	  	}, 1000);
	  	  	  		}
	  	  	  	}, 4000);
  	  		}
  	  	}, 2000);
	}
	
	        
        
        		
    
	
	
	//===================================================================================================
	// SEPERATOR
	//===================================================================================================
	
	
	// Destroys data in arrays, and pro-actively cleans up memory (finish) for the user (good practice?).
	@Override
    public void onBackPressed() {
			
		Arrays.fill(ArrayOfPlayers.player, null);
		Arrays.fill(ArrayOfAvatars.avatar, null);
	
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		startService(svc);
	
		super.onBackPressed();
		this.finish();
    }
	
	public void unfoldScrolls () {
		
		// Setting up scroll frame animation.
		ImageView img = (ImageView)findViewById(R.id.scrollanimation);
		img.setBackgroundResource(R.drawable.scrollanimationup);
	
		// Get the background, which has been compiled to an AnimationDrawable object.
		AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
					
		// Start the animation.
		frameAnimation.stop();
		frameAnimation.start();       		
	}
	
	public void preInitiativeTitle() {	
  	
		ImageView img = (ImageView)findViewById(R.id.titleanimation);		
		img.setBackgroundResource(R.drawable.titleanimationpreinitiative);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
		
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
	}
	
	public void sixSidedRollFromLeft() {	
	  	
		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
		img.setBackgroundResource(R.drawable.sixsidedrollfromleftanimation);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
		
  	  	final MediaPlayer dieRolling = MediaPlayer.create(MainActivity2.this, R.raw.dierolling3b);
  	  	dieRolling.start();
  	  	
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
	}	
	
	public void myInitiativeNotStarted() {
	    	  
		final ImageView img = (ImageView)findViewById(R.id.titleanimation);		
		img.setBackgroundResource(R.drawable.titleanimationnoinitiative);
  
		// Get the background, which has been compiled to an AnimationDrawable object.
		final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

		// Animation is just 1 slide so user can see title.
		frameAnimation.stop();
		frameAnimation.start();	      
	}
	
	public void  myInitiativeIsStarted() {	      
	    	  
		final ImageView img = (ImageView)findViewById(R.id.titleanimation);		
		img.setBackgroundResource(R.drawable.titleanimationyesinitiative);
  
		// Get the background, which has been compiled to an AnimationDrawable object.
		final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

		// Animation is just 1 slide so user can see title.
		frameAnimation.stop();
		frameAnimation.start();	      
	}	
	
}
