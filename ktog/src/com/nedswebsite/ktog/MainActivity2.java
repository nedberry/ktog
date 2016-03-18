package com.nedswebsite.ktog;

import java.util.Arrays;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends ActionBarActivity {			
	
	
	// Using variable because was getting null pointer if onbackpressed before rollfromleft was completed:
	String onBackPressedOk = "no";
	
	String isinitiativestarted = "no";
	String issixsidedrolledforinitiative = "no";
	String aretheredoubles = "yes";
		
	
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
		
		
		Thread thread1 = new Thread() {
		    @Override
		    public void run() {
		    	/*
		    	final MediaPlayer activityOpeningSound = MediaPlayer.create(MainActivity2.this, R.raw.buttonsound6);
				activityOpeningSound.start();
				*/
		    	MediaPlayerWrapper.play(MainActivity2.this, R.raw.buttonsound6);
		    }
		};
		thread1.start();		
		
		// Crashes if this is put up top.
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		
		
		TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
		playerNameTextView.setTypeface(typeFace);		
		playerNameTextView.setText(ArrayOfPlayers.player[0]);		
		
		TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
		computerNameTextView.setTypeface(typeFace);
		computerNameTextView.setText(ArrayOfPlayers.player[1]);
		
		
		ArrayOfHitPoints.hitpoints[0] = 20;		
		final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
		playerHitPointsTextView.setTypeface(typeFace);
		playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));		
		
		ArrayOfHitPoints.hitpoints[1] = 20;
		final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
		computerHitPointsTextView.setTypeface(typeFace);
		computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[1]));		
		
		/*
		Thread thread3 = new Thread() {
		    @Override
		    public void run() {
		    	final Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);
				playerHitPointsTextView.startAnimation(animPulsingAnimation);		
				computerHitPointsTextView.startAnimation(animPulsingAnimation);
		    }
		};
		thread3.start();
		*/
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				final Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);
				playerHitPointsTextView.startAnimation(animPulsingAnimation);		
				computerHitPointsTextView.startAnimation(animPulsingAnimation);
	  	    }
  		});
		
		
		ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
		ImageView crossedswords2 = (ImageView) findViewById(R.id.imageviewavatarleft2);
		ImageView stonedead2 = (ImageView) findViewById(R.id.imageviewavatarleft3);
		
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
		
		
		unfoldScrolls();		
        /*
        final Handler h1 = new Handler();
  	  	h1.postDelayed(new Runnable() {

  	  		@Override
  	  		public void run()
  	  		{  	  			
	  	  		final MediaPlayer scrollsUnrollingSound = MediaPlayer.create(MainActivity2.this, R.raw.scrollsunrolling);
	  			scrollsUnrollingSound.start();			  		  			
  	  		}
  	  	}, 800);        
    	*/
		
  	  	preInitiativeTitle();		
  	  	/*
		final Handler h2 = new Handler();
	  	h2.postDelayed(new Runnable() {
	
	  		@Override
	  		public void run()
	  		{	  			
	  			MediaPlayerWrapper.play(MainActivity2.this, R.raw.scrollrollingandunrolling);
	  		}
	  	}, 4450);  	
	  	*/  	  	
  	  	
  	  	
        final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		
		centerscrolltext.setTypeface(typeFace);				
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);		
		
		// THESE RUN METHODS ARE THREAD-SAFE, SUPPOSEDLY.
		final Handler h3 = new Handler();
  	  	h3.postDelayed(new Runnable() {

  	  		@Override
  	  		public void run()
  	  		{  	  			
  	  			centerscrolltext.setVisibility(View.VISIBLE);
  	  			centerscrolltext.startAnimation(animAlphaText);
	  			centerscrolltext.append("> Welcome, " + ArrayOfPlayers.player[0] + ".");	  			
	  			
	  			final Handler h4 = new Handler();
	  	  	  	h4.postDelayed(new Runnable() {

	  	  	  		@Override
	  	  	  		public void run()
	  	  	  		{
	  	  	  			sixSidedRollFromLeft();  	  	  			
	  		  			
		  		  		final Handler h5 = new Handler();
			  	  	  	h5.postDelayed(new Runnable() {
			  	  	  			
			  	  	  			// Does this thread help:?
				  	  	  		@Override
				  	  	  		public void run()
				  	  	  		{  	  			
				  	  	  			runOnUiThread(new Runnable() {
					  	  	  	    @Override
					  	  	  	    public void run() {					  	  	  	    	
					  	  	  	    	sixSidedWobbleStart();
						  	  	  	}
					  	  	  	});			  	  	  			
			  	  	  			centerscrolltext.setVisibility(View.VISIBLE);
			  		  	  		centerscrolltext.startAnimation(animAlphaText);
			  		  			centerscrolltext.append("\n" + "> Please slide the die...");			  		  			
			  		  			
			  		  			
			  		  			playerNameStartFadeInFadeOut();
			  		  			//playerTurnBackgroundStart();			  		  			
			  		  			
			  		  			
			  		  			
			  		  			issixsidedrolledforinitiative = "yes";
			  		  			isinitiativestarted = "yes";
			  		  			
			  		  			onBackPressedOk = "yes";
			  	  	  		}
			  	  	  	}, 1000);
	  	  	  		}
	  	  	  	}, 4000);
  	  		}
  	  	}, 2000);
  	  	
  	  	
  	  	Thread thread2 = new Thread() {
		    @Override
		    public void run() {		    	
		    	determineInitiative();
		    }
		};
		thread2.start();
		
				
		
		final ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
		
		titleBlankButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	
            	if (isinitiativestarted.equals("no")) {
            		myInitiativeNotStarted();            		
            	}
            	
            	else if (isinitiativestarted.equals("yes") && aretheredoubles.equals("no")) {
            		myInitiativeIsStarted();            		
            	}            	       	
			}	            
		});
		
		  	  	
  	  	
  	  	final ImageView sixsixrightleftrotateblank = (ImageView) findViewById(R.id.sixsidedanimation);  	  	
		// Creating view for animation that rides on pre-existing view.
	  	
	  	sixsixrightleftrotateblank.setOnTouchListener(new OnSwipeTouchListener(MainActivity2.this) {	  	
	  		@Override  	  	
	  		public void onSwipeLeft() {	  			
	  			
	  			if (issixsidedrolledforinitiative.equals("yes")) {	  				
	  				
	  				sixSidedWobbleStop();
		  			//sixSidedRollFromCenterToLeft();
		  			//determineInitiative();
		  			
		  			if (ArrayOfInitiative.initiative[0] == 1){		  				
		  				sixSidedRollFromCenterToLeft1();								  		  	  	
					}
					else if (ArrayOfInitiative.initiative[0] == 2){
						sixSidedRollFromCenterToLeft2();
					}
					else if (ArrayOfInitiative.initiative[0] == 3){
						sixSidedRollFromCenterToLeft3();
					}
					else if (ArrayOfInitiative.initiative[0] == 4){
						sixSidedRollFromCenterToLeft4();
					}
					else if (ArrayOfInitiative.initiative[0] == 5){
						sixSidedRollFromCenterToLeft5();
					}
					else if (ArrayOfInitiative.initiative[0] == 6){
						sixSidedRollFromCenterToLeft6();
					}
		  			determineDoubles();
	  			}	  			
	  		}
	  		
	  		public void onSwipeRight() {	  			
	  			
	  			if (issixsidedrolledforinitiative.equals("yes")) {	  				
	  								
	  				sixSidedWobbleStop();
	  				//sixSidedRollFromCenterToRight();
	  				//determineInitiative();
	  				
	  				if (ArrayOfInitiative.initiative[0] == 1){
	  					sixSidedRollFromCenterToRight1();							  		  	  	
					}
					else if (ArrayOfInitiative.initiative[0] == 2){
						sixSidedRollFromCenterToRight2();
					}
					else if (ArrayOfInitiative.initiative[0] == 3){
						sixSidedRollFromCenterToRight3();
					}
					else if (ArrayOfInitiative.initiative[0] == 4){
						sixSidedRollFromCenterToRight4();
					}
					else if (ArrayOfInitiative.initiative[0] == 5){
						sixSidedRollFromCenterToRight5();
					}
					else if (ArrayOfInitiative.initiative[0] == 6){
						sixSidedRollFromCenterToRight6();
					}
	  				determineDoubles();
	  			}	  			
	  		}
	  	});
	  	
	  	
	}
	
	
	//========================================================================================================
	//  OnCreate SEPERATOR
	//========================================================================================================	
		
	// OK IN THEIR OWN THREADS????
	public void playerNameStartFadeInFadeOut() {		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {	
				/*
		    	TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		    	
				final Animation animAlphaTextRepeat = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.anim_alpha_text_repeat);
			  	playerNameTextView.startAnimation(animAlphaTextRepeat);
			  	*/
			  	
			  	// Changes color of imageview:
			  	/*
			  	ImageView img = (ImageView)findViewById(R.id.playerturnbackgroundanimation);
			  	img.setBackgroundResource(R.drawable.leftscroll);
				img.setImageResource(R.drawable.leftscroll);
			  	img.getBackground().setColorFilter(Color.parseColor("#ff0000"), PorterDuff.Mode.DARKEN);
			  	*/
			  	ImageView img = (ImageView)findViewById(R.id.playerturnbackgroundanimation);			  	
			  	//img.setBackgroundResource(R.drawable.leftscroll);
			  	//img.setImageResource(R.drawable.leftscroll);
			  	img.bringToFront();
			  	final Animation animAlphaTextRepeat = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.anim_alpha_text_repeat);
			  	img.startAnimation(animAlphaTextRepeat);
			  	
			  	
			  	
		    }
  		});			  		  	
	}
	
	public void playerNameStopFadeInFadeOut() {
		TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);
		
	  	playerNameTextView.clearAnimation();  	
	}
	
	public void computerStartFadeInFadeOut() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {		    	
		    	TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
				final Animation animAlphaTextRepeat = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.anim_alpha_text_repeat);
				computerNameTextView.startAnimation(animAlphaTextRepeat);
		    }
		});			
	}
	
	public void computerStopFadeInFadeOut() {
		TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
		
		computerNameTextView.clearAnimation();
	}
	
	//========================================================================================================
	
	public void playerTurnBackgroundStart() {		
		runOnUiThread(new Runnable() {
				@Override
				public void run() {
				// Setting up scroll frame animation.
				ImageView img = (ImageView)findViewById(R.id.playerturnbackgroundanimation);
				img.setBackgroundResource(R.anim.playerturnbackgroundanimation);
				
				// Get the background, which has been compiled to an AnimationDrawable object.
				AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
							
				// Start the animation.				
				frameAnimation.start();
		  	    }
	  		});
		
		/*Thread thread = new Thread() {
		    @Override
		    public void run() {
		    					
		    }
		};
		thread.start();*/		  		  	
	}
	
	public void playerTurnBackgroundStop() {
		// Use a drawable to hide the imageview animation:
  		ImageView img = (ImageView)findViewById(R.id.playerturnbackgroundanimation);		
		img.setBackgroundResource(R.drawable.leftscroll);
		img.setImageResource(R.drawable.leftscroll);		 	
	}
	
	public void computerTurnBackgroundStart() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {		
		/*Thread thread = new Thread() {
		    @Override
		    public void run() {*/
		    	// Setting up scroll frame animation.
				ImageView img = (ImageView)findViewById(R.id.computerturnbackgroundanimation);
				img.setBackgroundResource(R.anim.computerturnbackgroundanimation);
			
				// Get the background, which has been compiled to an AnimationDrawable object.
				AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
							
				// Start the animation.				
				frameAnimation.start();		    	
		    /*}
		};
		thread.start();*/
		    }
  		});
	}
	
	public void computerTurnBackgroundStop() {
		// Use a drawable to hide the imageview animation:
  		ImageView img = (ImageView)findViewById(R.id.computerturnbackgroundanimation);		
		img.setBackgroundResource(R.drawable.rightscroll);
		img.setImageResource(R.drawable.rightscroll);		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// Destroys data in arrays, and pro-actively cleans up memory (finish) for the user (good practice?).
	@Override
    public void onBackPressed() {
		
		if (onBackPressedOk.equals("yes")){
			Arrays.fill(ArrayOfPlayers.player, null);
			Arrays.fill(ArrayOfAvatars.avatar, null);			
			Arrays.fill(ArrayOfHitPoints.hitpoints, 20);  // IS THIS RIGHT???????????????
			Arrays.fill(ArrayOfInitiative.initiative, 0); // IS THIS RIGHT???????????????		
			
			// NEED TO GET RID OF THREADS???????????????????
			
			final Intent svc=new Intent(this, Badonk2SoundService.class);
			startService(svc);
			
			// SAME AS "super.onBackPressed();"?
			finish();
			
			super.onBackPressed();
		}
		else if (onBackPressedOk.equals("no")){
			android.os.Process.killProcess(android.os.Process.myPid());
		}		
		
		//Toast.makeText(MainActivity2.this,"onBackPressed WORKING!!!!", Toast.LENGTH_SHORT).show();
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	//@SuppressWarnings("deprecation")
	public void unfoldScrolls () {
		/*
		// TO PERFORM FRAME ANIMATION PROGRAMMATICALLY:
		AnimationDrawable animation;
		animation = new AnimationDrawable();
        animation.addFrame(getResources().getDrawable(R.drawable.dualingscrolls14), 75);
        animation.addFrame(getResources().getDrawable(R.drawable.dualingscrolls12), 75);
        animation.addFrame(getResources().getDrawable(R.drawable.dualingscrolls10), 75);
        animation.addFrame(getResources().getDrawable(R.drawable.dualingscrolls8), 75);
        animation.addFrame(getResources().getDrawable(R.drawable.dualingscrolls6), 75);
        animation.addFrame(getResources().getDrawable(R.drawable.dualingscrolls4), 75);
        animation.addFrame(getResources().getDrawable(R.drawable.dualingscrolls1), 75);
        
        animation.setOneShot(true);
       
        ImageView imageAnim =  (ImageView) findViewById(R.id.scrollanimation);
        imageAnim.setBackgroundDrawable(animation);       
        
        imageAnim.post(animation);
		*/
		
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// Setting up scroll frame animation.
				ImageView img = (ImageView)findViewById(R.id.scrollanimation);
				img.setBackgroundResource(R.anim.scrollanimationup);
			
				// Get the background, which has been compiled to an AnimationDrawable object.
				AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
							
				// Start the animation.
				frameAnimation.stop();
				frameAnimation.start();
	  	    }
  		});	
	}	
	
	public void preInitiativeTitle() {	
		/*
		ImageView img = (ImageView)findViewById(R.id.titleanimation);		
		img.setBackgroundResource(R.anim.titleanimationpreinitiative);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
		
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
  	  	*/
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// Setting up scroll frame animation.
				ImageView img = (ImageView)findViewById(R.id.titleanimation);		
				img.setBackgroundResource(R.anim.titleanimationpreinitiative);
		  	  
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});	
	}
	
	public void sixSidedRollFromLeft() {	
	  	/*
		final ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
		img.setBackgroundResource(R.anim.sixsidedrollfromleftanimation);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
  	  
  	  		Thread thread = new Thread() {
			    @Override
			    public void run() {
			    	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
			    }
			};
		thread.start();
  	  	//MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
  	  	
  	  	
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
  	  	*/
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				final ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromleftanimation);
		  	  
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  
		  	  		Thread thread = new Thread() {
					    @Override
					    public void run() {
					    	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
					    }
		  	  		};
		  	  		thread.start();
		  	  	//MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});	
	}
	
	public void sixSidedWobbleStart() {
		final ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
		final Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.wobblesixsided);
		img.setAnimation(shake);
	}
	
	public void sixSidedWobbleStop() {
		final ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
		
		img.clearAnimation();
	}
	
	
	//========================================================================================================
	// Six-sided rolling SEPERATOR
	//========================================================================================================
		
	public void sixSidedRollFromCenterToLeft1() {	
	  	/*
		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
		img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation1);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
  	  	
  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
  	  	
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
  	  	*/
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation1);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
  	  	
  	  	issixsidedrolledforinitiative = "no";
	}
	
	public void sixSidedRollFromCenterToLeft2() {	
	  	/*
		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
		img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation2);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
  	  	
  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
  	  	
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
  	  	*/
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation2);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
  	  	
  	  	issixsidedrolledforinitiative = "no";
	}

	public void sixSidedRollFromCenterToLeft3() {	
	  	/*
		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
		img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation3);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
  	  	
  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
  	  	
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
  	  	*/
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation3);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
		
  	  	issixsidedrolledforinitiative = "no";
	}
	
	public void sixSidedRollFromCenterToLeft4() {	
	  	/*
		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
		img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation4);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
		
  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
  	  	
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
  	  	*/
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation4);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
		
  	  	issixsidedrolledforinitiative = "no";
	}
	
	public void sixSidedRollFromCenterToLeft5() {	
	  	/*
		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
		img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation5);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
		
  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
  	  	
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
  	  	*/
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation5);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
		
  	  	issixsidedrolledforinitiative = "no";
	}
	
	public void sixSidedRollFromCenterToLeft6() {	
	  	/*
		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
		img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation6);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
		
  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
  	  	
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
  	  	*/
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation6);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
		
  	  	issixsidedrolledforinitiative = "no";
	}
	
	public void sixSidedRollFromCenterToRight1() {	
	  	/*
		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
		img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation1);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
		
  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
  	  	
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
  	  	*/
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation1);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
  	  	
  	  	issixsidedrolledforinitiative = "no";
	}
	
	public void sixSidedRollFromCenterToRight2() {	
	  	/*
		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
		img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation2);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
		
  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
  	  	
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
  	  	*/
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation2);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
		
  	  	issixsidedrolledforinitiative = "no";
	}
	
	public void sixSidedRollFromCenterToRight3() {	
	  	/*
		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
		img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation3);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
		
  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
  	  	
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
  	  	*/
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation3);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
		
  	  	issixsidedrolledforinitiative = "no";
	}
	
	public void sixSidedRollFromCenterToRight4() {	
	  	/*
		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
		img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation4);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
		
  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
  	  	
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
  	  	*/
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation4);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
		
  	  	issixsidedrolledforinitiative = "no";
	}
	
	public void sixSidedRollFromCenterToRight5() {	
	  	/*
		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
		img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation5);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
		
  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
  	  	
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
  	  	*/
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation5);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
		
  	  	issixsidedrolledforinitiative = "no";
	}
	
	public void sixSidedRollFromCenterToRight6() {	
	  	/*
		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
		img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation6);
  	  
  	  	// Get the background, which has been compiled to an AnimationDrawable object.
  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
		
  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
  	  	
  	  	// Animation is just 1 slide so user can see title.
  	  	frameAnimation.stop();
  	  	frameAnimation.start();
  	  	*/
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation6);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
		
  	  	issixsidedrolledforinitiative = "no";
	}
		
	//========================================================================================================
	//========================================================================================================
	
	
	public void myInitiativeNotStarted() {
	    	  
		final ImageView img = (ImageView)findViewById(R.id.titleanimation);		
		img.setBackgroundResource(R.anim.titleanimationnoinitiative);
  
		// Get the background, which has been compiled to an AnimationDrawable object.
		final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

		// Animation is just 1 slide so user can see title.
		frameAnimation.stop();
		frameAnimation.start();	      
	}
	
	public void  myInitiativeIsStarted() {	      
	    	  
		final ImageView img = (ImageView)findViewById(R.id.titleanimation);		
		img.setBackgroundResource(R.anim.titleanimationpostinitiative);
  
		// Get the background, which has been compiled to an AnimationDrawable object.
		final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

		// Animation is just 1 slide so user can see title.
		frameAnimation.stop();
		frameAnimation.start();	      
	}
	
	public void  determineInitiative() {
		
		int result = (int)(Math.random()*6)+1;
        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
        //same as: (int) Math.ceil(Math.random()*6); ?
		ArrayOfInitiative.initiative[0] = result;
		
		int resultComputer = (int)(Math.random()*6)+1;
		ArrayOfInitiative.initiative[1] = resultComputer;
				
		/*		
		Thread thread = new Thread() {
		    @Override
		    public void run() {
		    }
		};
		thread.start();
		*/		
	}
	
	public void  determineDoubles() {
		
		//Toast.makeText(MainActivity2.this,"At method determineDoubles().", Toast.LENGTH_SHORT).show();		
		
		final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		centerscrolltext.setTypeface(typeFace);				
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		final Handler h1 = new Handler();
	  	  	h1.postDelayed(new Runnable() {	  	  		
	  	  		
	  	  		@Override
	  	  		public void run()
	  	  		{  	  			
		  	  		centerscrolltext.setVisibility(View.VISIBLE);
		  	  		centerscrolltext.startAnimation(animAlphaText);
		  			centerscrolltext.append("\n" + ">" + "\n" + "> You roll a " + ArrayOfInitiative.initiative[0] + " for initiative.");
		  			
		  			final Handler h2 = new Handler();
			  	  	h2.postDelayed(new Runnable() {

			  	  		@Override
			  	  		public void run()
			  	  		{  	  			
				  	  		centerscrolltext.setVisibility(View.VISIBLE);
				  	  		centerscrolltext.startAnimation(animAlphaText);
				  			centerscrolltext.append("\n" + "> Now the computer rolls...");
				  			
				  			playerNameStopFadeInFadeOut();
				  			computerStartFadeInFadeOut();
				  			//playerTurnBackgroundStop();
				  			//computerTurnBackgroundStart();
				  			
				  			final Handler h3 = new Handler();
				  	  	  	h3.postDelayed(new Runnable() {
		
				  	  	  		@Override
				  	  	  		public void run()
				  	  	  		{				  	  	  			
					  	  	  		if (ArrayOfInitiative.initiative[1] == 1){
					  					sixSidedRollFromCenterToRight1();							  		  	  	
									}
									else if (ArrayOfInitiative.initiative[1] == 2){
										sixSidedRollFromCenterToRight2();
									}
									else if (ArrayOfInitiative.initiative[1] == 3){
										sixSidedRollFromCenterToRight3();
									}
									else if (ArrayOfInitiative.initiative[1] == 4){
										sixSidedRollFromCenterToRight4();
									}
									else if (ArrayOfInitiative.initiative[1] == 5){
										sixSidedRollFromCenterToRight5();
									}
									else if (ArrayOfInitiative.initiative[1] == 6){
										sixSidedRollFromCenterToRight6();
									}
					  	  	  		
						  	  	  	final Handler h4 = new Handler();
						  	  	  	h4.postDelayed(new Runnable() {
			
						  	  	  		@Override
						  	  	  		public void run()
						  	  	  		{				  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
								  	  		centerscrolltext.startAnimation(animAlphaText);
								  			centerscrolltext.append("\n" + ">" + "\n" + "> The computer rolls a " + ArrayOfInitiative.initiative[1] + " for initiative.");
								  			
								  			final Handler h5 = new Handler();
								  	  	  	h5.postDelayed(new Runnable() {
					
								  	  	  		@Override
								  	  	  		public void run()
								  	  	  		{	  	  	  		
									  	  	  		// Use a blank drawable to hide the imageview animation:
								  	  	  			ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
													img.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
													img.setImageResource(R.drawable.sixsixrightleftrotateblank);
													
													// Re-enables ability to use srollbar:
													centerscrolltext.bringToFront();													
													
													centerscrolltext.setVisibility(View.VISIBLE);													
											  		centerscrolltext.startAnimation(animAlphaText);
													centerscrolltext.append("\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "> THIS IS A TEST...");
													
													computerStopFadeInFadeOut();
													//computerTurnBackgroundStop();
													
													/* 	  	
 													do 
													{
														for (int i = 0; i < 1; i++)   
													    {
													       if (i != 1 && ArrayOfInitiative.initiative[i] == ArrayOfInitiative.initiative[1])   
													       {
													          do
													          {
													             System.out.println();
													             System.out.println(player[i] + " re-roll for inititiative..");
													             Scanner input = new Scanner(System.in);// used to let user hit enter to re-roll.
													             input.nextLine();// used to let user hit enter to re-roll.
													             int result = (int) Math.ceil(Math.random()*6);
													             ArrayOfInitiative.initiative[i] = result;
													             System.out.println(player[i] + " rolls a " + ArrayOfInitiative.initiative[i] + " !");
													
													             System.out.println();
													             System.out.println(player[1] + " re-rolls for inititiative..");
													             //input.nextLine();// used to let user hit enter to re-roll.
													             result = (int) Math.ceil(Math.random()*6);
													             ArrayOfInitiative.initiative[1] = result;
													             System.out.println(player[1] + " rolls a " + ArrayOfInitiative.initiative[1] + " !");
													          }
													          while (ArrayOfInitiative.initiative[i] == ArrayOfInitiative.initiative[1]);
													       }
													    }
													 }
													 while (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[1] || ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[0]);
													 */
													
								  	  	  		}
								  	  	  	}, 3000);
						  	  	  		}
						  	  	  	}, 1000);					  	  	  		
				  	  	  		}
				  	  	  	}, 3000);				  			
			  	  		}
			  	  	}, 4000);
	  	  		}
	  	  	}, 1250);		 		
	}	
	
	
}
