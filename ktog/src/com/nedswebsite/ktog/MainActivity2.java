package com.nedswebsite.ktog;

import java.util.Arrays;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ContextThemeWrapper;
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
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends ActionBarActivity {		
	
	//IS THIS WORKING NOW (W NEW onbackpressed CODE)????????????
	// Using variable because was getting null pointer if onbackpressed before rollfromleft was completed:
	String onBackPressedOk = "no";
	
	int playerNumberAttacked;
	int i;
	int turn = 1;
	int gameOn;
	int computerAction;
	//int gameOn = 1;	
	//int turn;
	
	int max = 0;
	//int numberOfPlayers = 2;
	
	static int numberOfPlayers = 1; // NEED THIS???????????
	
	//static int playerNumberAttacked;
	
	int attackResult;
	
	
	String isInvokingService = "true";	
	
	String isinitiativestarted = "no";
	static String isinitiativestartedinterrupted = "no";
	String issixsidedrolledforinitiative = "no";
	String aretheredoubles = "yes";	
	
	String startGameNow ="no";	
	
	
	// SOME OF THESE MAY NEED TO BE AN ARRAY-CLASS:
	public static String[] playerDeadYet = new String[] {"yes", "yes", "yes", "yes", "yes", "yes"}; // NEED 6?????????	
	//String playerDeadYet[] = {"yes", "yes", "yes", "yes", "yes", "yes"};
	public static String[] canHasDisarmed = new String[] {"no", "no", "no", "no", "no", "no"}; // NEED 6?????????
	public static int[] disarmedTurnStart = new int[6];
	
	// NEED ARRAY HERE?
	public static String[] usedHaste = new String[] {"no"};//FOR COMP. ONLY: so computer doesn't use a haste during a haste.
	
	// SOME OF THESE MAY NEED TO BE AN ARRAY-CLASS:
	public static int[] cureSpell = new int[] {1, 1, 1, 1, 1, 1, 1};
	public static int[] hasteSpell = new int[] {2, 2, 2, 2, 2, 2, 2};
	public static int[] mightyBlowSpell = new int[] {1, 1, 1, 1, 1, 1, 1};
	public static int[] blessSpell = new int[] {1, 1, 1, 1, 1, 1, 1};
	public static int[] dodgeBlowSpell = new int[] {1, 1, 1, 1, 1, 1, 1};
	
	
		
	
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
		
		
		/*
    	final MediaPlayer activityOpeningSound = MediaPlayer.create(MainActivity2.this, R.raw.buttonsound6);
		activityOpeningSound.start();
		*/
    	MediaPlayerWrapper.play(MainActivity2.this, R.raw.buttonsound6);
		
		
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
		
		
		
		// MAKE THIS A RUNNABLE LIKE GAME ENGINE AND CALL IT AFTER INITIATIVE?
		/*
		Thread thread1 = new Thread() {
		    @Override
		    public void run() {
		    	final Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);
				playerHitPointsTextView.startAnimation(animPulsingAnimation);		
				computerHitPointsTextView.startAnimation(animPulsingAnimation);
		    }
		};
		thread1.start();
		*/
		/*
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				final Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);
				playerHitPointsTextView.startAnimation(animPulsingAnimation);		
				computerHitPointsTextView.startAnimation(animPulsingAnimation);
	  	    }
  		});
		*/
		final Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);
		playerHitPointsTextView.startAnimation(animPulsingAnimation);		
		computerHitPointsTextView.startAnimation(animPulsingAnimation);
		
		
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
		
		
		final ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
		titleBlankButton.setVisibility(View.INVISIBLE);
		
		// Here to prevent premature rolling (use ".setEnabled(false);" in "resultsInitiative()"):
		final ImageView sixSidedBlank = (ImageView) findViewById(R.id.sixsidedanimation);
		sixSidedBlank.setVisibility(View.INVISIBLE);
		
		final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
		twentySidedBlank.setVisibility(View.INVISIBLE);
		
		
		
		// ANIMATIONS RUNNING SLOWER IN THREADS:
		
		unfoldScrolls();		
		/*
		Thread thread2 = new Thread() {
		    @Override
		    public void run() {
		    	unfoldScrolls();
		    }
		};
		thread2.start();
		*/
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
		Thread thread3 = new Thread() {
		    @Override
		    public void run() {
		    	preInitiativeTitle();
		    }
		};
		thread3.start();
		*/
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
		final Handler h3 = new Handler(); // A handler is associated with the thread it is created in.
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
	  	  	  			// Sets sixSidedBlank visible & enabled.
	  	  	  			sixSidedRollFromLeft();  	  	  			
	  		  			
		  		  		final Handler h5 = new Handler();
			  	  	  	h5.postDelayed(new Runnable() {
			  	  	  			
			  	  	  			// Does this thread help:?
				  	  	  		@Override
				  	  	  		public void run()
				  	  	  		{  	  			
					  	  	  		sixSidedWobbleStart();
					  	  	  		
				  	  	  			centerscrolltext.setVisibility(View.VISIBLE);
				  		  	  		centerscrolltext.startAnimation(animAlphaText);
				  		  			centerscrolltext.append("\n" + "> Please slide the die...");			  		  			
				  		  			
				  		  			
				  		  			playerCardStartFadeInFadeOut();
				  		  			//playerTurnBackgroundStart();			  		  			
				  		  			
				  		  			
				  		  			//issixsidedrolledforinitiative = "yes";
				  		  			isinitiativestarted = "yes";			  		  			
				  		  			onBackPressedOk = "yes";			  		  			
			  	  	  		}
			  	  	  	}, 1000);
	  	  	  		}
	  	  	  	}, 4000);
  	  		}
  	  	}, 2000);
  	  	
  	  	
  	  	determineInitiative();
  	  	// THREAD MATTER?
  	  	/*
  	  	Thread thread4 = new Thread() {
		    @Override
		    public void run() {		    	
		    	determineInitiative();
		    }
		};
		thread4.start();
		*/
  	  			
				
		
		
				
		titleBlankButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	
            	if (isinitiativestarted.equals("no")) {
            		myInitiativeNotStarted();            		
            	}
            	
            	else if (isinitiativestarted.equals("yes") && aretheredoubles.equals("no")) {
            		
            		if (isinitiativestartedinterrupted.equals("no")) {
            			
            			isinitiativestartedinterrupted = "yes";            		
                		
                		myInitiativeIsStarted();
            		}
            		
            		else if (isinitiativestartedinterrupted.equals("yes")) {
            			
            			myInitiativeIsStartedTitleInterrupt();
            			
            			isinitiativestartedinterrupted = "no";
            		}            				
            	}            	
			}            
		});		
				
		
		// USE android:background="@drawable/(SOME PNG)" TO SPECIFY AREA ON SCREEN ??
		sixSidedBlank.setOnTouchListener(new OnSixSidedSwipeTouchListener(MainActivity2.this) {
		    public void onSwipeTop() {
		        Toast.makeText(MainActivity2.this, "top", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeRight() {
		    	//if (issixsidedrolledforinitiative.equals("yes")) {	  				
					
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
				
				if (issixsidedrolledforinitiative.equals("no")) {					
					resultsInitiative();
				}
			//}
		    }		    
		    public void onSwipeLeft() {
		    	//if (issixsidedrolledforinitiative.equals("yes")) {	  				
					
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
				
				if (issixsidedrolledforinitiative.equals("no")) {					
					resultsInitiative();
				}					
			//}
		    }
		    public void onSwipeBottom() {
		        Toast.makeText(MainActivity2.this, "bottom", Toast.LENGTH_SHORT).show();
		    }
		});		
		
		
		// USE android:background="@drawable/(SOME PNG)" TO SPECIFY AREA ON SCREEN ??
		twentySidedBlank.setOnTouchListener(new OnTwentySidedSwipeTouchListener(MainActivity2.this) {
		    public void onSwipeTop() {
		        Toast.makeText(MainActivity2.this, "top", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeRight() {		    		  				
					
				twentySidedWobbleStop();
				
				if (attackResult == 1) {
					twentySidedRollFromCenterToRight1();
				}
				if (attackResult == 2) {
					twentySidedRollFromCenterToRight2();
				}
				if (attackResult == 3) {
					twentySidedRollFromCenterToRight3();
				}
				if (attackResult == 4) {
					twentySidedRollFromCenterToRight4();
				}
				if (attackResult == 5) {
					twentySidedRollFromCenterToRight5();
				}
				if (attackResult == 6) {
					twentySidedRollFromCenterToRight6();
				}
				if (attackResult == 7) {
					twentySidedRollFromCenterToRight7();
				}
				if (attackResult == 8) {
					twentySidedRollFromCenterToRight8();
				}
				if (attackResult == 9) {
					twentySidedRollFromCenterToRight9();
				}
				if (attackResult == 10) {
					twentySidedRollFromCenterToRight10();
				}
				if (attackResult == 11) {
					twentySidedRollFromCenterToRight11();
				}
				if (attackResult == 12) {
					twentySidedRollFromCenterToRight12();
				}
				if (attackResult == 13) {
					twentySidedRollFromCenterToRight13();
				}
				if (attackResult == 14) {
					twentySidedRollFromCenterToRight14();
				}
				if (attackResult == 15) {
					twentySidedRollFromCenterToRight15();
				}
				if (attackResult == 16) {
					twentySidedRollFromCenterToRight16();
				}
				if (attackResult == 17) {
					twentySidedRollFromCenterToRight17();
				}
				if (attackResult == 18) {
					twentySidedRollFromCenterToRight18();
				}
				if (attackResult == 19) {
					twentySidedRollFromCenterToRight19();
				}
				if (attackResult == 20) {
					twentySidedRollFromCenterToRight20();
				}				
		    }		    
		    public void onSwipeLeft() {		    		  				
					
				twentySidedWobbleStop();
				
				if (attackResult == 1) {
					twentySidedRollFromCenterToLeft1();
				}
				if (attackResult == 2) {
					twentySidedRollFromCenterToLeft2();
				}
				if (attackResult == 3) {
					twentySidedRollFromCenterToLeft3();
				}
				if (attackResult == 4) {
					twentySidedRollFromCenterToLeft4();
				}
				if (attackResult == 5) {
					twentySidedRollFromCenterToLeft5();
				}
				if (attackResult == 6) {
					twentySidedRollFromCenterToLeft6();
				}
				if (attackResult == 7) {
					twentySidedRollFromCenterToLeft7();
				}
				if (attackResult == 8) {
					twentySidedRollFromCenterToLeft8();
				}
				if (attackResult == 9) {
					twentySidedRollFromCenterToLeft9();
				}
				if (attackResult == 10) {
					twentySidedRollFromCenterToLeft10();
				}
				if (attackResult == 11) {
					twentySidedRollFromCenterToLeft11();
				}
				if (attackResult == 12) {
					twentySidedRollFromCenterToLeft12();
				}
				if (attackResult == 13) {
					twentySidedRollFromCenterToLeft13();
				}
				if (attackResult == 14) {
					twentySidedRollFromCenterToLeft14();
				}
				if (attackResult == 15) {
					twentySidedRollFromCenterToLeft15();
				}
				if (attackResult == 16) {
					twentySidedRollFromCenterToLeft16();
				}
				if (attackResult == 17) {
					twentySidedRollFromCenterToLeft17();
				}
				if (attackResult == 18) {
					twentySidedRollFromCenterToLeft18();
				}
				if (attackResult == 19) {
					twentySidedRollFromCenterToLeft19();
				}
				if (attackResult == 20) {
					twentySidedRollFromCenterToLeft20();
				}				
		    }
		    public void onSwipeBottom() {
		        Toast.makeText(MainActivity2.this, "bottom", Toast.LENGTH_SHORT).show();
		    }
		});
		
		
	}
	
	
	/*
	 * 
	 * 
	 * 
	 * Other Methods***********************************************************************************
	 * 
	 * 
	 * 
	 */
	
	
	public void hideNavigation() {
		
		// This will hide the system bar until user swipes up from bottom or down from top.		
  		getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_IMMERSIVE
                  | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);		
	}
	
	
	/*
	 * 
	 * 
	 * 
	 * Android-Related Methods***********************************************************************************
	 * 
	 * 
	 * 
	 */		
			
	
	// Destroys data in arrays, and pro-actively cleans up memory (finish) for the user (good practice?).
	@Override
    public void onBackPressed() {		
			
		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
			
	    	alert.setTitle("KtOG");	  	    	
	    	alert.setMessage("Are you sure you want to exit?");	  	    		  	    	
	    	
	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int whichButton) {
	    			
	    			hideNavigation();
	    			
	    			//NOT SURE ARRAYS ARE GETTING WIPED COMPLETELY W THE INTENT CODE BELOW, SO ADDED THIS:
	    			ArrayOfPlayers.player = new String[6];
	    			ArrayOfAvatars.avatar = new String[6];
	    			ArrayOfHitPoints.hitpoints = new int[6];
	    			ArrayOfInitiative.initiative = new int[6];
	    			
	    			Intent intent = new Intent(MainActivity2.this, MainActivity1.class);
	    			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);  //this combination of flags would start a new instance even if the instance of same Activity exists.
	    			intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
	    			finish();
	    			startActivity(intent);	    			
	    			
	    			/*
	    			if (onBackPressedOk.equals("yes")){
	    				backMethodYes();
	    			}
    	
	    			else if (onBackPressedOk.equals("no")){	    			
	    				backMethodNo();			
	    			}
	    			*/ 					  		    			  		    		
	    		}
	    	});
	    	
	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int whichButton) {
	    			
	    			hideNavigation();		         		            		  
	    		}
	    	});	  	    	
	    	alert.show();				
		
		//Toast.makeText(MainActivity2.this,"onBackPressed WORKING!!!!", Toast.LENGTH_SHORT).show();
    }
	/*
	public void backMethodYes() {
		
		super.onBackPressed(); // HERE OR BELOW??????
		
		Arrays.fill(ArrayOfPlayers.player, null);
		Arrays.fill(ArrayOfAvatars.avatar, null);			
		Arrays.fill(ArrayOfHitPoints.hitpoints, 20);  // IS THIS RIGHT???????????????
		Arrays.fill(ArrayOfInitiative.initiative, 0); // IS THIS RIGHT???????????????			
		
		// NEED TO GET RID OF THREADS???????????????????
		
		// ANIMATIONS METHODS STILL A PROB
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		startService(svc);
		
		// SAME AS "super.onBackPressed();"?
		finish();		
	}
	
	public void backMethodNo() {
		
		android.os.Process.killProcess(android.os.Process.myPid());
		
		super.onBackPressed();		
	}
	*/
	
	/*
	public void onStop() {		
		
		//finish();
		
		//android.os.Process.killProcess(android.os.Process.myPid());
		
		super.onStop();    	
    }
	
	public void onRestart() {		
		
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
			
			super.onRestart();
		}
		else if (onBackPressedOk.equals("no")){
			android.os.Process.killProcess(android.os.Process.myPid());
			
			super.onRestart();
		}
		    	
    }	
	*/	
	
	
	/*
	 * 
	 * 
	 * 
	 * Player Cards*****************************************************************************************
	 * 
	 * 
	 * 
	 */
	
	
	// OK IN THEIR OWN THREADS????
	public void playerCardStartFadeInFadeOut() {		
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
			  	img.bringToFront();
			  	final Animation animAlphaTextRepeat = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.anim_alpha_text_repeat);
			  	img.startAnimation(animAlphaTextRepeat);			  	
		    }
  		});			  		  	
	}
	
	public void playerCardStopFadeInFadeOut() {
		/*
		TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
	  	playerNameTextView.clearAnimation();
	  	*/
		ImageView img = (ImageView)findViewById(R.id.playerturnbackgroundanimation);
		img.clearAnimation();
		img.setVisibility(View.GONE);		
	}
	
	public void computerCardStartFadeInFadeOut() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				/*
		    	TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
				final Animation animAlphaTextRepeat = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.anim_alpha_text_repeat);
				computerNameTextView.startAnimation(animAlphaTextRepeat);
				*/
				ImageView img = (ImageView)findViewById(R.id.computerturnbackgroundanimation);		  	
			  	img.bringToFront();
			  	final Animation animAlphaTextRepeat = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.anim_alpha_text_repeat);
			  	img.startAnimation(animAlphaTextRepeat);
		    }
		});			
	}
	
	public void computerCardStopFadeInFadeOut() {
		/*
		TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);		
		computerNameTextView.clearAnimation();
		*/
		ImageView img = (ImageView)findViewById(R.id.computerturnbackgroundanimation);
		img.clearAnimation();
		img.setVisibility(View.GONE);
	}	
	
	
	/* EXAMPLE CODE TO "STOP" FRAME ANIMATION BY ENDING ANIMATION ON AN IMAGE:
	
	public void computerTurnBackgroundStop() {
		// Use a drawable to hide the imageview animation:
  		ImageView img = (ImageView)findViewById(R.id.computerturnbackgroundanimation);		
		img.setBackgroundResource(R.drawable.rightscroll);
		img.setImageResource(R.drawable.rightscroll);		
	}
	*/	
	
	
	/*
	 * 
	 * 
	 * 
	 * Intro Animations***********************************************************************************
	 * 
	 * 
	 * 
	 */
	
	
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
	
	
	/*
	 * 
	 * 
	 * 
	 * Dice-Related*******************************************************************************************
	 * 
	 * 
	 * 
	 */
	
	
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
		
		final ImageView sixSidedBlank = (ImageView) findViewById(R.id.sixsidedanimation);
		sixSidedBlank.setVisibility(View.VISIBLE);
		sixSidedBlank.setEnabled(true);
		
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
	
	public void twentySidedRollFromLeft() {	  	
		
		final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
		twentySidedBlank.setVisibility(View.VISIBLE);
		twentySidedBlank.setEnabled(true);
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				final ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromleftanimation);
		  	  
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
	
	public void twentySidedWobbleStart() {
		final ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
		final Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.wobbletwentysided);
		img.setAnimation(shake);
	}
	
	public void twentySidedWobbleStop() {
		final ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
		
		img.clearAnimation();
	}
	
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
  	  	
  	  	//issixsidedrolledforinitiative = "no";
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
  	  	
  	  	//issixsidedrolledforinitiative = "no";
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
		
  	  	//issixsidedrolledforinitiative = "no";
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
		
  	  	//issixsidedrolledforinitiative = "no";
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
		
  	  	//issixsidedrolledforinitiative = "no";
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
		
  	  	//issixsidedrolledforinitiative = "no";
	}
	
	public void twentySidedRollFromCenterToLeft1() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation1);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft2() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation2);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft3() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation3);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft4() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation4);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft5() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation5);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft6() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation6);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft7() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation7);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft8() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation8);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft9() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation9);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft10() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation10);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft11() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation11);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft12() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation12);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft13() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation13);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft14() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation14);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft15() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation15);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft16() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation16);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft17() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation17);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft18() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation18);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft19() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation19);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToLeft20() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation20);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
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
  	  	
  	  	//issixsidedrolledforinitiative = "no";
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
		
  	  	//issixsidedrolledforinitiative = "no";
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
		
  	  	//issixsidedrolledforinitiative = "no";
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
		
  	  	//issixsidedrolledforinitiative = "no";
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
		
  	  	//issixsidedrolledforinitiative = "no";
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
		
  	  	//issixsidedrolledforinitiative = "no";
	}
	
	public void twentySidedRollFromCenterToRight1() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation1);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight2() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation2);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight3() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation3);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight4() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation4);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight5() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation5);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight6() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation6);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight7() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation7);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight8() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation8);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight9() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation9);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight10() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation10);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight11() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation11);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight12() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation12);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight13() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation13);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight14() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation14);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight15() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation15);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight16() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation16);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight17() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation17);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight18() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation18);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight19() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation19);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void twentySidedRollFromCenterToRight20() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertorightanimation20);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	
	/*
	 * 
	 * 
	 * 
	 * Initiative*****************************************************************************************
	 * 
	 * 
	 * 
	 */
		
	
	public void myInitiativeNotStarted() {
	    	  
		final ImageView img = (ImageView)findViewById(R.id.titleanimation);		
		img.setBackgroundResource(R.anim.titleanimationnoinitiative);
  
		// Get the background, which has been compiled to an AnimationDrawable object.
		final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

		// Animation is just 1 slide so user can see title.
		frameAnimation.stop();
		frameAnimation.start();	      
	}
	
	public void myInitiativeTransition() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {		
		
				final ImageView img = (ImageView)findViewById(R.id.titleanimation);		
				img.setBackgroundResource(R.anim.titleanimationyesinitiative);
		  
				// Get the background, which has been compiled to an AnimationDrawable object.
				final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
		
				// Animation is just 1 slide so user can see title.
				frameAnimation.stop();
				frameAnimation.start();
			}
  		});
	}
	
	public void  myInitiativeIsStarted() {	      
	    	  
		final ImageView img = (ImageView)findViewById(R.id.titleanimation);		
		img.setBackgroundResource(R.anim.titleanimationpostinitiative);
  
		// Get the background, which has been compiled to an AnimationDrawable object.
		final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

		// Animation is just 1 slide so user can see title.
		frameAnimation.stop();
		frameAnimation.start();
		
		waitToSetInterruptVariable();
	}
	
	public static String waitToSetInterruptVariable() {
		
		final Handler h1 = new Handler();
	  	  	h1.postDelayed(new Runnable() {

	  	  		public void run()
	  	  		{				  	  	  			
		  	  		isinitiativestartedinterrupted = "no";		  			
	  	  		}
	  	  	}, 12400);
	  	  	
	  	  	return isinitiativestartedinterrupted;	
	}
	
	public void  myInitiativeIsStartedTitleInterrupt() {
		
		final ImageView img = (ImageView)findViewById(R.id.titleanimation);		
		img.setBackgroundResource(R.anim.initiativestartedtitleinterruptanimation);
  
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
	
	public void resultsInitiative() {
		
		//Toast.makeText(MainActivity2.this,"At method determineDoubles().", Toast.LENGTH_SHORT).show();
		
		// Here to prevent pre-mature (BUT STILL SEE ) rolling:
		final ImageView sixSidedBlank = (ImageView) findViewById(R.id.sixsidedanimation);
		sixSidedBlank.setEnabled(false);		
				
		
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
				  			
				  			playerCardStopFadeInFadeOut();
				  			computerCardStartFadeInFadeOut();
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
						  	  	  			
								  			determineDoubles();
						  	  	  		}
						  	  	  	}, 1250);					  	  	  		
				  	  	  		}
				  	  	  	}, 3000);				  			
			  	  		}
			  	  	}, 4000);
	  	  		}
	  	  	}, 1250);		 		
	}	
	
	public void determineDoubles() {
		
		//final ImageView sixSidedBlank = (ImageView) findViewById(R.id.sixsidedanimation);
		
		final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		centerscrolltext.setTypeface(typeFace);				
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		
		if ((ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[1] || ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[0])) {			  										  	  	  						 	  	
			
			determineInitiative();			
			
			computerCardStopFadeInFadeOut();
			
			final Handler h2 = new Handler();
	  	  	h2.postDelayed(new Runnable() {
	
	  	  		@Override
	  	  		public void run()
	  	  		{  	  			
	  	  			centerscrolltext.setVisibility(View.VISIBLE);
	  	  			centerscrolltext.startAnimation(animAlphaText);
		  			centerscrolltext.append("\n" + "> Initiative is a tie!");	  			
		  			
		  			final Handler h3 = new Handler();
		  	  	  	h3.postDelayed(new Runnable() {
	
		  	  	  		@Override
		  	  	  		public void run()
		  	  	  		{
		  	  	  			sixSidedRollFromLeft();  	  	  			
		  		  			
			  		  		final Handler h4 = new Handler();
				  	  	  	h4.postDelayed(new Runnable() {				  	  	  			
				  	  	  			
					  	  	  		@Override
					  	  	  		public void run() {
					  	  	  			
					  	  	  			// need runOnUiThread?
					  	  	  			runOnUiThread(new Runnable() {
						  	  	  	    @Override
						  	  	  	    public void run() {
						  	  	  	    	
						  	  	  	    	sixSidedWobbleStart();						  	  	  	    	
							  	  	  	}
					  	  	  			});
					  	  	  			
				  	  	  			centerscrolltext.setVisibility(View.VISIBLE);
				  		  	  		centerscrolltext.startAnimation(animAlphaText);
				  		  			centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0]  + ", please slide the die...");				  		  			
				  		  			
				  		  			playerCardStartFadeInFadeOut();
				  		  			//playerTurnBackgroundStart();  		  			
					  		  		
					  		  		// NEED REF TO ONASWIPE CLASS?????????????????
				  		  			
					  	  	  		}
				  	  	  	}, 1000);
		  	  	  		}
		  	  	  	}, 4000);
	  	  		}
	  	  	}, 2000);				  			  	  				
		}	  	  	
	  	
		else if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[1] || ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[0])) {
						
			aretheredoubles = "no";
			
			issixsidedrolledforinitiative = "yes";
			
			maxInitiative();  	  	  	
		}
	}
	
	public void maxInitiative() {		
		
		for (int i = 0; i < 2; i++) // Was "numberOfPlayers", which = 2 up top.
        {
           if (ArrayOfInitiative.initiative[i] > max)
           {
              max = ArrayOfInitiative.initiative[i];
           }
        }
		
		//Toast.makeText(MainActivity2.this,"MAX = " + max, Toast.LENGTH_SHORT).show();	  	  		
	  	  			
  		computerCardStopFadeInFadeOut();
		//computerTurnBackgroundStop();
		
					  	  	  			
  		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
		
    	alert.setTitle("Initiative Results");
    	/*
    	alert.setMessage(ArrayOfPlayers.player[0] + getString(R.string.tab) + ArrayOfPlayers.player[1]);
    	alert.setMessage(ArrayOfInitiative.initiative[0] + getString(R.string.tab) + ArrayOfInitiative.initiative[0]);
    	*/
    	for (int i = 0; i < 2; i++) {
    		// Was "numberOfPlayers", which = 2 up top.
        
           if (max == ArrayOfInitiative.initiative[i])
           {
        	   alert.setMessage("\n" + ArrayOfPlayers.player[i] + " wins with a " + max + "!");
           }
        }
    	
    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int whichButton) {
	    		
	    		hideNavigation();
	    		
	    		finishInitiative();
	    	}
    	});		  	    	
    	alert.show();	  	  			
	}
	
	public void finishInitiative() {
		
		final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		centerscrolltext.setTypeface(typeFace);
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
						  	  	  			
	  		// Use a blank drawable to hide the imageview animation:
	  		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
			img.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
			img.setImageResource(R.drawable.sixsixrightleftrotateblank);
			
			// Re-enables ability to use srollbar:
			centerscrolltext.bringToFront();												
			
			centerscrolltext.setVisibility(View.VISIBLE);													
	  		centerscrolltext.startAnimation(animAlphaText);
			centerscrolltext.append("\n" +  ">" + "\n" + "> Let the battle begin...");  	  			
			
			myInitiativeTransition();	  	    	  	  							  	  	 	    	  	  			  	    	  	  			
		  	  			  	    	  	  			
	  	  		final Handler h3 = new Handler();
		  	  	h3.postDelayed(new Runnable() {
	
		  	  		@Override
		  	  		public void run()
		  	  		{
	    	  	  		final ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
		  	  			titleBlankButton.setVisibility(View.VISIBLE);
		  	  			titleBlankButton.bringToFront();		  	  			
		  	  			
		  	  			
		  	  			startGameNow ="yes";
		  	  			
		  	  			
		  	  			/*
		  	  			// Calls method from another class:
	  		  	  		Engine  engine = new Engine();
	  		  	  		Engine.gameEngine();
	  		  	  		*/
	  		  	  		
		  	  					  	  			
		  	  			//gameEngine(null, gameOn, gameOn);
		  	  			gameEngine();
		  	  			
		  	  			
		  	  			//Thread myThread = new Thread(myRunnable);
		  	  			//myThread.start();
	  		  	  		
		  	  		}
		  	  	}, 12400);  	    	  	  			  	  			
		
		  	//Toast.makeText(MainActivity2.this,"isinitiativestarted = " +  isinitiativestarted + " aretheredoubles = " + aretheredoubles, Toast.LENGTH_SHORT).show();
  	    	 	
	  	  		
	}
	
	
	 /* TEST DIALOG:
	    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
	      
	      	alert.setMessage("test");	    	
	    	
	    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int whichButton) {
		    				    		
		    	}
	    	});
	    	
	    	alert.show();     
	  */
	
	
	/*
	 * 
	 * 
	 * 
	 * Game Mechanics*****************************************************************************************
	 * 
	 * 
	 * 
	 */
	
		
	public void endGame() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);		
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
		
				final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
				//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
				
				Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				centerscrolltext.setTypeface(typeFace);		
				
				
				if (numberOfPlayers == 1) {
					// UNCONSCIOUS
					// TEMPLATE???????????????????????????????????????????????????????????????
					
					
					//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
					
					
					if (ArrayOfHitPoints.hitpoints[1] == 0) {			
						
						centerscrolltext.setVisibility(View.VISIBLE);													
		  	  	  		centerscrolltext.startAnimation(animAlphaText);
		  	  			centerscrolltext.append("\n" + "> The computer is unconscious.");
						
						
						if (cureSpell[1] > 0) {
							
							centerscrolltext.setVisibility(View.VISIBLE);												
			  	  	  		centerscrolltext.startAnimation(animAlphaText);
			  	  			centerscrolltext.append("\n" + "> The computer uses it's cure spell...");
							
							computerCure();
						}
					}
					
					if (ArrayOfHitPoints.hitpoints[0] == 0) {				
										
						centerscrolltext.setVisibility(View.VISIBLE);												
		  	  	  		centerscrolltext.startAnimation(animAlphaText);
		  	  			centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you are unconscious.");				
						
		
						if (cureSpell[0] > 0) {
							
							/*
							centerscrolltext.setVisibility(View.VISIBLE);												
			  	  	  		centerscrolltext.startAnimation(animAlphaText);
			  	  			centerscrolltext.append("\n" + "> Do you want to use your cure spell?");
			  	  			*/  	  			
			  	  			
				  	  		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				  			
				  	    	alert.setTitle("Do you want to use your Cure spell?");
				  	    	/*
				  	    	alert.setMessage("something");
				  	    	*/	  	    	
				  	    	
				  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				  		    	public void onClick(DialogInterface dialog, int whichButton) {
				  		    		
				  		    		hideNavigation();
				  		    		
				  		    		cure();
				  		    	}
				  	    	});
				  	    	
				  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
				          	  public void onClick(DialogInterface dialog, int whichButton) {
				          		  
				          		  	hideNavigation();
				          		  	
				          		  	endGame();
				          	  }
				          	});				  	    	
				  	    	alert.show();
			  	  			
			  	  			/*
			  	  			String s = input.next();
							char selection = s.charAt(0);
			  	  			switch (selection) {
							case 'y':
							case 'Y':
								cure(i, turn, gameOn);
								break;
							case 'n':
							case 'N':
								endGame(i, turn, gameOn);
								break;
							default:
								endGame(i, turn, gameOn);
								break;
							}
							*/
						}
					}
					
					if (ArrayOfHitPoints.hitpoints[0] < 0) {
						playerDeadYet[0] = "yes";
						return;
					}
					
					if (ArrayOfHitPoints.hitpoints[1] < 0) {
						playerDeadYet[1] = "yes";
						return;
					}
				}
  	  	    }
  	  	});
	}
	
	public void gameOverCheck() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
		
				if (playerDeadYet[0] == "no" && playerDeadYet[1] == "yes") {
				/*&& playerDeadYet[2] == "yes" && playerDeadYet[3] == "yes"	&& playerDeadYet[4] == "yes" && playerDeadYet[5] == "yes"*/
					
					gameOn = 0;
					
					
					final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
					//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
					
					Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
					centerscrolltext.setTypeface(typeFace);					
					
					
					centerscrolltext.setVisibility(View.VISIBLE);												
		  	  		centerscrolltext.startAnimation(animAlphaText);
		  			centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you are victorious!!");
		  			
		  			centerscrolltext.append("\n" + "Game Over!");
		  			
					/*
					if (numberOfPlayers == 1) {
						System.out.println(ArrayOfPlayers.player[0] + ", you are victorious!!");
					}
					
					if (numberOfPlayers > 1) {
						System.out.println("Player " + playerNumber[i]
								+ ", you are victorious!!");
					}
					
					System.out.println();
					System.out.println("Game Over!");
					System.exit(0);			
					*/
				}
				
				if (playerDeadYet[1] == "no" && playerDeadYet[0] == "yes"
						/*&& playerDeadYet[2] == "yes" && playerDeadYet[3] == "yes"
						&& playerDeadYet[4] == "yes" && playerDeadYet[5] == "yes"*/) {
					
					gameOn = 0;
					
					
					final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
					//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
					
					Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
					centerscrolltext.setTypeface(typeFace);				
					
					//final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
					
					centerscrolltext.setVisibility(View.VISIBLE);												
		  	  		centerscrolltext.startAnimation(animAlphaText);
		  			centerscrolltext.append("\n" + "> The computer is victorious!!");
		  			
		  			centerscrolltext.append("\n" + "> Game Over!");
		  			
					/*
					if (numberOfPlayers == 1) {
						System.out.println("The computer player is victorious!!");
					}
					
					if (numberOfPlayers > 1) {
						System.out.println("Player " + playerNumber[i]
								+ ", you are victorious!!");
					}
					
					System.out.println();
					System.out.println("Game Over!");
					System.exit(0);
					*/
				}
				/*
				if (playerDeadYet[2] == "no" && playerDeadYet[0] == "yes"
						&& playerDeadYet[1] == "yes" && playerDeadYet[3] == "yes"
						&& playerDeadYet[4] == "yes" && playerDeadYet[5] == "yes") {
					gameOn = 0;
					System.out.println();
					System.out.println("Player " + playerNumber[i]
							+ ", you are victorious!!");
					System.out.println();
					System.out.println("Game Over!");
					System.exit(0);
				}
				if (playerDeadYet[3] == "no" && playerDeadYet[0] == "yes"
						&& playerDeadYet[1] == "yes" && playerDeadYet[2] == "yes"
						&& playerDeadYet[4] == "yes" && playerDeadYet[5] == "yes") {
					gameOn = 0;
					System.out.println();
					System.out.println("Player " + playerNumber[i]
							+ ", you are victorious!!");
					System.out.println();
					System.out.println("Game Over!");
					System.exit(0);
				}
				if (playerDeadYet[4] == "no" && playerDeadYet[0] == "yes"
						&& playerDeadYet[1] == "yes" && playerDeadYet[2] == "yes"
						&& playerDeadYet[3] == "yes" && playerDeadYet[5] == "yes") {
					gameOn = 0;
					System.out.println();
					System.out.println("Player " + playerNumber[i]
							+ ", you are victorious!!");
					System.out.println();
					System.out.println("Game Over!");
					System.exit(0);
				}
				if (playerDeadYet[5] == "no" && playerDeadYet[0] == "yes"
						&& playerDeadYet[1] == "yes" && playerDeadYet[2] == "yes"
						&& playerDeadYet[3] == "yes" && playerDeadYet[4] == "yes") {
					gameOn = 0;
					System.out.println();
					System.out.println("Player " + playerNumber[i]
							+ ", you are victorious!!");
					System.out.println();
					System.out.println("Game Over!");
					System.exit(0);
				}
				*/
  	  	    }
		});
	}
	      
	public void disarmedAction() {		
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you are disarmed. What do you want to do? ");		
				
				/*
				 * 
				 * ACTION BUTTON MODIFIED BUTTON HERE?
				 * 
				 */
				
				
				/*
				 * if (numberOfPlayers == 1) { System.out.print(player[0] +
				 * ", you are disarmed. What do you want to do? "); }
				 */
  	  	    }
		});
	}
	
	public int computerDisarmedAction() {			
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> The computer is disarmed...");		
						
		
				// CURE
				if (ArrayOfHitPoints.hitpoints[1] <= 12 && cureSpell[1] > 0) {
					
					centerscrolltext.setVisibility(View.VISIBLE); 													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer uses it's cure spell...");			
					
					computerCure();
					return;
				}
		
				int computerDisarmedAction = (int) ((Math.random() * 100) + 1);
		
				// HASTE
				if (computerDisarmedAction <= 50 && hasteSpell[1] > 0) {
					
					centerscrolltext.setVisibility(View.VISIBLE); 													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer uses a haste spell...");
								
					computerHasteDisarmed();
					return;
				}
		
				// PUNCH 1
				if (computerDisarmedAction <= 50 && hasteSpell[1] < 1) {
					
					centerscrolltext.setVisibility(View.VISIBLE); 													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer attempts to punch you...");			
					
					computerPunch();
					return;
				}
		
				// PUNCH 2
				if (computerDisarmedAction >= 51) {
					
					centerscrolltext.setVisibility(View.VISIBLE); 													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer attempts to punch you...");			
					
					computerPunch();
					return;
				}
  	  	    }
		});
		return playerNumberAttacked;
	}
	
	public int computerAttack() {
		// player uses methods as normal, BUT computer must use new methods?		
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
					
				if (canHasDisarmed[0] == "yes") { 
					// the goto method to get bonus if attacking disarmed opponent.										 
				
					computerAttackAgainstDisarmed();
					return;
				}
		
				// NEED ALGORITHM FOR *ATTACK/*HASTE/*CURE/*BLESS/*DISARM
		
				// CURE
				if ((ArrayOfHitPoints.hitpoints[1] <= 12 && cureSpell[1] > 0) && (usedHaste[0] == "no")) {							
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer uses it's cure spell...");
					
					// for(int x = 0; x < 100; --x)
					// {}
					
					computerCure();
					return;
				}
		
				// ATTACK
				if (computerAction <= 40) {
					int attackResult = (int) (Math.random() * 20) + 1;
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer attacks...");
					
					// for(int x = 0; x < 100; --x)
					// {}
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer rolls a " + attackResult + "!");
					
		
					if (attackResult >= 20) {
						computerCriticalHit();
						return;
					}
					if (attackResult >= 14 && attackResult <= 19) {
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> The computer's attack hits!");				
		
						if (mightyBlowSpell[1] > 0) {
							
							int computerUseMightyBlow = (int) ((Math.random() * 20) + 1);
							// NEED 1 SCENARIO FOR HUMAN PLAYER HP ABOVE 12 AND 1 SCENARIO FOR HUMAN PLAYER HP BELOW 12
							
							if (ArrayOfHitPoints.hitpoints[0] >= 12) {
								// using 12 just because if comp roll 6 for damage * 2 = 12, comp can kill human player.
							
								if (computerUseMightyBlow >= 15) {
									// less likely for comp to use MB because human HP > 12.						
									
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> The computer uses mighty blow!");
									
									/*
									for (int x = 0; x < 1000; --x) {
									}
									*/
									
									mightyBlowSpell[1] = mightyBlowSpell[1] - 1;							
									
									
									/*
									 * 
									 * Picture of swords clanging together:
									 * 
									 * 
									 * swordsGraphic();
									 * 
									 */
									
									
									/*
									System.out
											.println("     /\\___________          ___________/\\");
									System.out
											.println("/|---||___________\\ MIGHTY /___________||---|\\");
									System.out
											.println("\\|---||___________/  BLOW  \\___________||---|/");
									System.out
											.println("     \\/                                \\/");
									for (int x = 0; x < 1; --x) {
									}
									*/
		
									computerMightyBlow();
									return;
								} else {
									computerDamage();
									return;
								}
							}
							
							if (ArrayOfHitPoints.hitpoints[0] < 12)// see above for explanation
							{
								if (computerUseMightyBlow < 15) {
									// more likely for comp to use MB because human HP < 12.
								
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> The computer uses mighty blow!");
									
									/*
									for (int x = 0; x < 1000; --x) {
									}
									*/
									
									mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
		
																
									/*
									 * 
									 * Picture of swords clanging together:
									 * 
									 * 
									 * swordsGraphic();
									 * 
									 */
									
									/*
									System.out
											.println("     /\\___________          ___________/\\");
									System.out
											.println("/|---||___________\\ MIGHTY /___________||---|\\");
									System.out
											.println("\\|---||___________/  BLOW  \\___________||---|/");
									System.out
											.println("     \\/                                \\/");
									for (int x = 0; x < 1; --x) {
									}
									*/
									
									computerMightyBlow();
									return;
								} else {
									computerDamage();
									return;
								}
							}
						}
						computerDamage();
						return;
					}
					
					if (attackResult < 14 && attackResult > 1) {
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> The computer's attack misses!");
						
						return;
					}
					
					if (attackResult <= 1) {
						computerCriticalMiss();
						return;
					}
					return;
				}
				
				// BLESS
				else if ((computerAction >= 41 && computerAction <= 50)	&& (blessSpell[1] == 1 && usedHaste[0] == "no")) {
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer uses a bless spell...");
					
					/*
					for (int x = 0; x < 1000; --x) {
					}
					*/
					
					computerBless();
					return;
				}
				// ATTACK IF NO BLESS
				else if ((computerAction >= 41 && computerAction <= 50)	&& (blessSpell[1] < 1 && usedHaste[0] == "no")) {
					
					int attackResult = (int) ((Math.random() * 20) + 1);
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer attacks...");
					
					// for(int x = 0; x < 100; --x)
					// {}
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer rolls a " + attackResult + "!");			
		
					if (attackResult >= 20) {
						computerCriticalHit();
						return;
					}
					
					if (attackResult >= 14 && attackResult <= 19) {
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> The computer's attack hits!");				
		
						if (mightyBlowSpell[1] > 0) {
							int computerUseMightyBlow = (int) (Math.random() * 20) + 1;
							// NEED 1 SCENARIO FOR HUMAN PLAYER HP ABOVE 12 AND 1 SCENARION FOR HUMAN PLAYER HP BELOW 12
							
							if (ArrayOfHitPoints.hitpoints[0] >= 12) {
								// using 12 just because if comp roll 6 for damage * 2 = 12, comp can kill human player.
							
								if (computerUseMightyBlow >= 15) {
									// less likely for comp to use MB because human HP > 12.
								
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> The computer uses mighty blow!");
									
									/*
									for (int x = 0; x < 1000; --x) {
									}
									*/
									
									mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
									
									
									/*
									 * 
									 * Picture of swords clanging together:
									 * 
									 * 
									 * swordsGraphic();
									 * 
									 */
									
									/*
									System.out
											.println("     /\\___________          ___________/\\");
									System.out
											.println("/|---||___________\\ MIGHTY /___________||---|\\");
									System.out
											.println("\\|---||___________/  BLOW  \\___________||---|/");
									System.out
											.println("     \\/                                \\/");
									for (int x = 0; x < 1; --x) {
									}
									*/
									
									computerMightyBlow();
									return;
								} else {
									computerDamage();
									return;
								}
							}
							
							if (ArrayOfHitPoints.hitpoints[0] < 12)// see above for explanation
							{
								if (computerUseMightyBlow < 15) {
									// more likely for comp to use MB because human HP < 12.
								
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> The computer uses mighty blow!");
									
									/*
									for (int x = 0; x < 1000; --x) {
									}
									*/
									
									mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
									
									
									/*
									 * 
									 * Picture of swords clanging together:
									 * 
									 * 
									 * swordsGraphic();
									 * 
									 */
									
									
									/*
									System.out
											.println("     /\\___________          ___________/\\");
									System.out
											.println("/|---||___________\\ MIGHTY /___________||---|\\");
									System.out
											.println("\\|---||___________/  BLOW  \\___________||---|/");
									System.out
											.println("     \\/                                \\/");
									for (int x = 0; x < 1; --x) {
									}
									*/
									
									computerMightyBlow();
									return;
								} else {
									computerDamage();
									return;
								}
							}
						}
						computerDamage();
						return;
					}
					
					if (attackResult < 14 && attackResult > 1) {
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> The computer's attack misses!");
						
						return;
					}
					
					if (attackResult <= 1) {
						computerCriticalMiss();
						return;
					}
					return;
				}
				
				// HASTE
				else if ((computerAction >= 51 && computerAction <= 75)	&& (hasteSpell[1] > 0 && usedHaste[0] == "no")) {
					
					usedHaste[0] = "yes"; // so computer doesn't use a haste during a haste.
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer uses a haste spell...");
					
					/*
					for (int x = 0; x < 1000; --x) {
					}
					*/
					
					computerHaste();
					return;
				}
				
				// ATTACK DURING HASTE
				else if ((computerAction >= 51 && computerAction <= 75)	&& (usedHaste[0] == "yes")) {
					// in case the roll is between 51 & 75 & the computer used a haste spell.		
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer attacks...");
					
					// for(int x = 0; x < 100; --x)
					// {}
					
					int attackResult = (int) ((Math.random() * 20) + 1);
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer rolls a " + attackResult + "!");
					
					if (attackResult >= 20) {
						computerCriticalHit();
						return;
					}
					if (attackResult >= 14 && attackResult <= 19) {
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> The computer's attack hits!");				
		
						if (mightyBlowSpell[1] > 0) {
							
							int computerUseMightyBlow = (int) ((Math.random() * 20) + 1);
							// NEED 1 SCENARIO FOR HUMAN PLAYER HP ABOVE 12 AND 1 SCENARIO FOR HUMAN PLAYER HP BELOW 12.
							
							if (ArrayOfHitPoints.hitpoints[0] >= 12) {
								// using 12 just because if comp roll 6 for damage * 2 = 12, comp can kill human player.
							
								if (computerUseMightyBlow >= 15) {
									// less likely for comp to use MB because human HP > 12.
									
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> The computer uses mighty blow!");
									
									/*
									for (int x = 0; x < 1000; --x) {
									}
									*/
									
									mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
									
									
									/*
									 * 
									 * Picture of swords clanging together:
									 * 
									 * 
									 * swordsGraphic();
									 * 
									 */
									
									
									/*
									System.out
											.println("     /\\___________          ___________/\\");
									System.out
											.println("/|---||___________\\ MIGHTY /___________||---|\\");
									System.out
											.println("\\|---||___________/  BLOW  \\___________||---|/");
									System.out
											.println("     \\/                                \\/");
									for (int x = 0; x < 1; --x) {
									}
									*/
									
									computerMightyBlow();
									return;
								} else {
									computerDamage();
									return;
								}
							}
							
							if (ArrayOfHitPoints.hitpoints[0] < 12)// see above for explanation
							{
								if (computerUseMightyBlow < 15)// more likely for comp to use MB because human HP < 12.
								{
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> The computer uses mighty blow!");
									
									/*
									for (int x = 0; x < 1000; --x) {
									}
									*/
									
									mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
									
									
									/*
									 * 
									 * Picture of swords clanging together:
									 * 
									 * 
									 * swordsGraphic();
									 * 
									 */
									
									
									/*
									System.out
											.println("     /\\___________          ___________/\\");
									System.out
											.println("/|---||___________\\ MIGHTY /___________||---|\\");
									System.out
											.println("\\|---||___________/  BLOW  \\___________||---|/");
									System.out
											.println("     \\/                                \\/");
									for (int x = 0; x < 1; --x) {
									}
									*/
									
									computerMightyBlow();
									return;
								} else {
									computerDamage();
									return;
								}
							}
						}
						computerDamage();
						return;
					}
					
					if (attackResult < 14 && attackResult > 1) {
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> The computer's attack misses!");
						
						return;
					}
					
					if (attackResult <= 1) {
						computerCriticalMiss();
						return;
					}
					return;
				}
				
				// ATTACK IF NO HASTES LEFT
				else if ((computerAction >= 51 && computerAction <= 75)	&& (hasteSpell[1] < 1 && usedHaste[0] == "no")) {
					// in case the roll is between 51 & 75 & the computer used both it's haste spells.
				
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer attacks...");
					
					// for(int x = 0; x < 100; --x)
					// {}
					
					int attackResult = (int) ((Math.random() * 20) + 1);
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer rolls a " + attackResult + "!");			
		
					if (attackResult >= 20) {
						computerCriticalHit();
						return;
					}
					
					if (attackResult >= 14 && attackResult <= 19) {
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> The computer's attack hits!");
						
						if (mightyBlowSpell[1] > 0) {
							int computerUseMightyBlow = (int) ((Math.random() * 20) + 1);
							// NEED 1 SCENARIO FOR HUMAN PLAYER HP ABOVE 12 AND 1 SCENARION FOR HUMAN PLAYER HP BELOW 12.
							
							if (ArrayOfHitPoints.hitpoints[0] >= 12) {
								// using 12 just because if comp roll 6 for damage * 2 = 12, comp can kill human player.
							
								if (computerUseMightyBlow >= 15) {
									// less likely for comp to use MB because human HP > 12.
								
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> The computer uses mighty blow!");
									
									/*
									for (int x = 0; x < 1000; --x) {
									}
									*/
									
									mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
									
									
									/*
									 * 
									 * Picture of swords clanging together:
									 * 
									 * 
									 * swordsGraphic();
									 * 
									 */
									
									/*
									System.out
											.println("     /\\___________          ___________/\\");
									System.out
											.println("/|---||___________\\ MIGHTY /___________||---|\\");
									System.out
											.println("\\|---||___________/  BLOW  \\___________||---|/");
									System.out
											.println("     \\/                                \\/");
									for (int x = 0; x < 1; --x) {
									}
									*/
		
									computerMightyBlow();
									return;
								} else {
									computerDamage();
									return;
								}
							}
							
							if (ArrayOfHitPoints.hitpoints[0] < 12)// see above for explanation
							{
								if (computerUseMightyBlow < 15) {
									// more likely for comp to use MB because human HP < 12.
								
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> The computer uses mighty blow!");
									
									/*
									for (int x = 0; x < 1000; --x) {
									}
									*/
									
									mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
									
									
									/*
									 * 
									 * Picture of swords clanging together:
									 * 
									 * 
									 * swordsGraphic();
									 * 
									 */
									
									/*
									System.out
											.println("     /\\___________          ___________/\\");
									System.out
											.println("/|---||___________\\ MIGHTY /___________||---|\\");
									System.out
											.println("\\|---||___________/  BLOW  \\___________||---|/");
									System.out
											.println("     \\/                                \\/");
									for (int x = 0; x < 1; --x) {
									}
									*/
									
									computerMightyBlow();
									return;
								} else {
									computerDamage();
									return;
								}
							}
						}
						computerDamage();
						return;
					}
					
					if (attackResult < 14 && attackResult > 1) {
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> The computer's attack misses!");
						
						return;
					}
					
					if (attackResult <= 1) {
						computerCriticalMiss();
						return;
					}
					return;
				}
				
				// DISARM
				else if (computerAction > 75 && computerAction <= 100) {
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer attempts to disarm you...");
					
					// for(int x = 0; x < 100; --x)
					// {}
					computerDisarm();
					return;
				}
  	  	    }
		});
		return playerNumberAttacked;
	}
	
	public void computerCure() {//WAS int[]			
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				cureSpell[1] = cureSpell[1] - 1;
		
				int result = (int) ((Math.random() * 10) + 1);
				int cure = result;						
				
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> The computer rolls a " + cure + "!");
				
				/*
				for (int x = 0; x < 1000; --x)// To give human time to read.
				{
				}
				*/
				
				ArrayOfHitPoints.hitpoints[1] = ArrayOfHitPoints.hitpoints[1] + cure;
				
				return;
  	  	    }
		});		
	}
	
	public int[] cure() {		
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
				
				if (cureSpell[i] > 0) {
					
					if (numberOfPlayers == 1) {
						
						cureSpell[0] = cureSpell[0] - 1;				
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", roll for cure...");
						
						
						/*
						 * 
						 * ROLL 6-SIDED DIE
						 * 
						 */
										
						/*
						Scanner input = new Scanner(System.in);
						input.nextLine();
						*/
						
						int result = (int) ((Math.random() * 10) + 1);
						int cure = result;
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> You roll a " + cure + "!");				
		
						ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] + cure;
						return;
					}
					/*
					if (numberOfPlayers > 1) {
						cureSpell[i] = cureSpell[i] - 1;
		
						System.out.print("Player " + playerNumber[i]
								+ ", who do you want to cure? (Enter player number): ");
		
						playerNumberCured = input.nextInt();
						playerNumberCured = playerNumberCured - 1;
		
						while (playerNumberCured < 0 || playerNumberCured > 5)// ||
																				// !input.hasNextInt());
						{
							System.out
									.print("Not valid entry!..Who do you want to cure? (Enter player number): ");
							playerNumberCured = input.nextInt();
						}
		
						System.out.println();
						System.out.print("Player " + playerNumber[i]
								+ ", roll for cure...");
						Scanner input = new Scanner(System.in);
						input.nextLine();
						int result = (int) ((Math.random() * 10) + 1);
						int cure = result;
						System.out.println("You roll a " + cure + "!");
						System.out.println();
		
						ArrayOfHitPoints.hitpoints[playerNumberCured] = ArrayOfHitPoints.hitpoints[playerNumberCured]
								+ cure;
						return hitPoints;
					}
					*/
				} else {		
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have already used your cure spell!");					
					
					
					/*
					 * 
					 * THIS LETS HUMAN PLAYER CHOOSE WHAT THEY WANT TO DO (ACTION/ATTACK)
					 * 
					 * Bring Action & Attack To Front?
					 * 
					 * action(i, turn, gameOn);
					 * 
					 */
					if (isInvokingService.equals("true")){
						//NEED THIS?
						SystemClock.sleep(1000);	        		
							
						runActionsOnUi();
					}
				}
  	  	    }
		});				
		return ArrayOfHitPoints.hitpoints; 
	}
	
	public void computerHasteDisarmed() {//WAS int[]						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> The computer picks up it's weapon!");		
				
				canHasDisarmed[1] = "no";
				hasteSpell[1] = hasteSpell[1] - 1;
  	  	    }  	  	
		});		
	}
	
	public void computerPunch() {//WAS int
		
		final int attackResult = (int) ((Math.random() * 20) + 1);						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);		
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> The computer rolls a " + attackResult + "!");
				
				
				if (attackResult >= 20) {
					computerCriticalHitDisarmed();
				}
				
				if (attackResult >= 17 && attackResult <= 19) {
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer's punch hits!");
					
		
					if (mightyBlowSpell[1] > 0) {
						int computerUseMightyBlow = (int) ((Math.random() * 20) + 1);
						// NEED 1 SCENARIO FOR HUMAN PLAYER HP ABOVE 12 AND 1 SCENARIO FOR HUMAN PLAYER HP BELOW 12
						
						if (ArrayOfHitPoints.hitpoints[0] >= 12) {
							// using 12 just because if comp roll 6 for damage * 2 = 12, comp can kill human player.
						
							if (computerUseMightyBlow >= 15) {
								// less likely for comp to use MB because human HP > 12.
								
								centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> The computer uses mighty blow!");
								
								/*
								for (int x = 0; x < 1000; --x) {
								}
								*/
								
								mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
		
								
								/*
								 * 
								 * Pictures of fists coming together.
								 * 
								 * punchGraphic();
								 * 
								 */
								
								/*
								System.out
										.println("_______________              _______________");
								System.out.println("            __ \\            / __");
								System.out.println("              \\ \\          / /");
								System.out
										.println("      ___/\\__  \\ \\ MIGHTY / /  __/\\___");
								System.out
										.println("           | \\  \\/  BLOW  \\/  / |");
								System.out
										.println("\\      ____|_/__/          \\__\\_|____      /");
								System.out
										.println(" \\_____/    \\/_/            \\_\\/    \\_____/");
								for (int x = 0; x < 1; --x) {
								}
								*/
								
								computerMightyBlowDisarmed();
								return;
							} else {
								computerDamageDisarmed();
								return;
							}
						}
						
						if (ArrayOfHitPoints.hitpoints[0] < 12)// see above for explanation
						{
							if (computerUseMightyBlow < 15) {
								// more likely for comp to use MB because human HP < 12.
							
								centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> The computer uses mighty blow!");
								
								/*
								for (int x = 0; x < 1000; --x) {
								}
								*/
								
								mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
		
								
								/*
								 * 
								 * Pictures of fists coming together.
								 * 
								 * punchGraphic();
								 * 
								 */
								
								/*
								System.out
										.println("_______________              _______________");
								System.out.println("            __ \\            / __");
								System.out.println("              \\ \\          / /");
								System.out
										.println("      ___/\\__  \\ \\ MIGHTY / /  __/\\___");
								System.out
										.println("           | \\  \\/  BLOW  \\/  / |");
								System.out
										.println("\\      ____|_/__/          \\__\\_|____      /");
								System.out
										.println(" \\_____/    \\/_/            \\_\\/    \\_____/");
								for (int x = 0; x < 1; --x) {
								}
								*/
								
								computerMightyBlowDisarmed();
								return;
							} else {
								computerDamageDisarmed();
								return;
							}
						}
					}
					computerDamageDisarmed();
					return;
				}
		
				if (attackResult <= 16 && attackResult > 1) {
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "The computer's punch misses!");			
				}
				/*
				 * if (attackResult <= 1)//CAN YOU CRITICL MISS A
				 * PUNCH???????????????????????????????????? -----DISARM AGAIN TO
				 * MAINTAIN BALANCE. { computerCriticalMissDisarmed(i, turn);//NEW
				 * METHOD!!!!!!!!!!!!!!!!!!!!!!! }
				 */
				
				return;
  	  	    }
		});		
	}
	
	public void computerAttackAgainstDisarmed() {//WAS int
		
		final int attackResult = (int) ((Math.random() * 20) + 1);
		final int attackResultAgainstDisarmed = (attackResult + 2);					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	
  	  	    	
		
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);  		
				centerscrolltext.append("\n" + "> The computer attacks...");
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> The computer rolls a " + attackResult + ", +2 because you are disarmed = " + attackResultAgainstDisarmed);
		
				
				if (attackResultAgainstDisarmed >= 20) {
					computerCriticalHit();
					return;
				}
				
				if (attackResultAgainstDisarmed >= 12 && attackResultAgainstDisarmed <= 19) {
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);	  		
					centerscrolltext.append("\n" + "> The computer's attack hits!");
					
		
					if (mightyBlowSpell[1] > 0) {
						
						int computerUseMightyBlow = (int) ((Math.random() * 20) + 1);
						// using a 20-based percentage calculation.
						
						// NEED 1 SCENARIO FOR HUMAN PLAYER HP ABOVE 12 AND 1 SCENARIO
						// FOR HUMAN PLAYER HP BELOW 12
						
						if (ArrayOfHitPoints.hitpoints[0] >= 12) {
							// using 12 just because if comp roll 6 for damage * 2 = 12, comp can kill human player.
						
							if (computerUseMightyBlow >= 15) {
								// less likely for comp to use MB because human HP > 12.
								
								centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);	  		
								centerscrolltext.append("\n" + "> The computer uses mighty blow!");
								
								/*
								for (int x = 0; x < 1000; --x) {
								}
								*/
								
								mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
								
								
								/*
								 * 
								 * Picture of swords clanging together:
								 * 
								 * 
								 * swordsGraphic();
								 * 
								 */
								
								
								/*
								System.out
										.println("     /\\___________          ___________/\\");
								System.out
										.println("/|---||___________\\ MIGHTY /___________||---|\\");
								System.out
										.println("\\|---||___________/  BLOW  \\___________||---|/");
								System.out
										.println("     \\/                                \\/");
								for (int x = 0; x < 1; --x) {
								}
								*/
								
								computerMightyBlow();
								return;
							} else {
								computerDamage();
								return;
							}
						}
						
						if (ArrayOfHitPoints.hitpoints[0] < 12)// see above for explanation
						{
							if (computerUseMightyBlow < 15) {
								// more likely for comp to use MB because human HP < 12.
								
								centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);	  		
								centerscrolltext.append("\n" + "> The computer uses mighty blow!");
								
								/*
								for (int x = 0; x < 1000; --x) {
								}
								*/
								
								mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
								
								
								/*
								 * 
								 * Picture of swords clanging together:
								 * 
								 * 
								 * swordsGraphic();
								 * 
								 */
								
								
								/*
								System.out
										.println("     /\\___________          ___________/\\");
								System.out
										.println("/|---||___________\\ MIGHTY /___________||---|\\");
								System.out
										.println("\\|---||___________/  BLOW  \\___________||---|/");
								System.out
										.println("     \\/                                \\/");
								for (int x = 0; x < 1; --x) {
								}
								*/
								
								computerMightyBlow();
								return;
							} else {
								computerDamage();
								return;
							}
						}
					}
					computerDamage();
					return;
				}
		
				if (attackResultAgainstDisarmed < 12 && attackResultAgainstDisarmed > 0) {
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);	  		
					centerscrolltext.append("\n" + "> The computer's attack misses!");			
				}
				
				// CAN'T CRITICALLY MISS WHEN ATTACKING DISARMED
				// OPPONENT????????????????????????????
				
				/*
				 * if (attackResult <= 1) { computerCriticalMiss(i, turn, gameOn); }
				 */
				
				return;
  	  	    }
		});		
	}
	
	public int computerCriticalHit() {						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
				
				/*
				 * 
				 * Picture of swords clanging together:
				 * 
				 * 
				 * swordsGraphic();
				 * 
				 */
				
				/*
				System.out.println("     /\\___________            ___________/\\");
				System.out
						.println("/|---||___________\\ CRITICAL /___________||---|\\");
				System.out
						.println("\\|---||___________/   HIT    \\___________||---|/");
				System.out.println("     \\/                                  \\/");
				for (int x = 0; x < 1; --x) {
				}
				*/
				
				
				if (mightyBlowSpell[1] > 0) {
					
					int computerUseMightyBlow = (int) ((Math.random() * 20) + 1);
					// using a 20-based percentage calculation.
		
					if (ArrayOfHitPoints.hitpoints[0] >= 12) {
						// using just 1 scenario since it's a criticle hit (computerAttackAgainstDisarmed used 2 scenarios)
					
						if (computerUseMightyBlow >= 11) {
							// 50/50 chance to use MB if human HP above 12.
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer uses mighty blow!");
							
							/*
							for (int x = 0; x < 1000; --x) {
							}
							*/
							
							
							mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
							
							
							/*
							 * 
							 * Picture of swords clanging together:
							 * 
							 * 
							 * swordsGraphic();
							 * 
							 */
							
							/*
							System.out
									.println("     /\\___________          ___________/\\");
							System.out
									.println("/|---||___________\\ MIGHTY /___________||---|\\");
							System.out
									.println("\\|---||___________/  BLOW  \\___________||---|/");
							System.out
									.println("     \\/                                \\/");
							for (int x = 0; x < 1; --x) {
							}
							*/
							
							computerCriticalHitMightyBlowDamage();
							return;
						} else// if human HP below 12 then MB
						{
							computerCriticalHitDamage();
							return;
						}
					}
					if (ArrayOfHitPoints.hitpoints[0] < 12) {
						computerCriticalHitMightyBlowDamage();
						return;
					}
				}
  	  	    }
		});
		computerCriticalHitDamage();
		return playerNumberAttacked;
	}
	
	public int[] computerMightyBlow() {					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	
  	  	    	
		
				if (dodgeBlowSpell[0] > 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
		  			
		  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", Do you want to use your Dodge spell?");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
		  		    		
		  		    		//NEED THIS??????????????????
		  		    		if (dodgeBlowSpell[0] < 1) {
		  		    			
		  		    			hideNavigation();
		  						
		  						centerscrolltext.setVisibility(View.VISIBLE);													
		  				  		centerscrolltext.startAnimation(animAlphaText);			  		
		  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
		  						
		  						//break;
		  					}
		  		    		
		  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
		  					return;
		  		    	}
		  	    	});
		  	    	
		  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
		          	  public void onClick(DialogInterface dialog, int whichButton) {
		          		  
		          		  	hideNavigation();          		  
		          	  }
		          	});	  	    	
		  	    	
		  	    	alert.show();
					
		  	    	/*
		  	    	String s = input.next();
					char selection = s.charAt(0);
					switch (selection) {
					case 'y':
					case 'Y':
						
						if (dodgeBlowSpell[0] < 1) {
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> You have already used your Dodge Blow spell!");
							
							break;
						}
						dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
						return dodgeBlowSpell;
					case 'n':
					case 'N':
						break;
					default:
						computerMightyBlow(i, gameOn);
					}
					*/
				}
				
				int result = (int) ((Math.random() * 6) + 1);
				int attackDamage = result;
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + " for damage!");
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (attackDamage * 2) + "!");
				
				/*
				for (int x = 0; x < 1000; --x)// To give human time to read.
				{
				}
				*/
				
				ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - (attackDamage * 2);
		
				if (ArrayOfHitPoints.hitpoints[0] == 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been knocked unconscious!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show(); 
					
			    	/*
					System.out.print("Press a key to continue... ");
					input.nextLine();
					*/
				}
		
				if (ArrayOfHitPoints.hitpoints[0] < 0) {
					
					
					/*
					 * 
					 * Picture of one sword destroying another.
					 * 
					 * deathGraphic();
					 * 
					 */
					
					
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been slain!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();			
					
					/*
					System.out.print("Press a key to continue... ");
					Scanner input = new Scanner(System.in);
					input.nextLine();
					*/
					
					playerDeadYet[0] = "yes";
					gameOverCheck();
				}
  	  	    }
		});
		return ArrayOfHitPoints.hitpoints;
	}
	
	public int[] computerDamage() {						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				if (dodgeBlowSpell[0] > 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
		  			
		  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", Do you want to use your Dodge spell?");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
		  		    		
		  		    		//NEED THIS??????????????????
		  		    		if (dodgeBlowSpell[0] < 1) {
		  		    			
		  		    			hideNavigation();
		  						
		  						centerscrolltext.setVisibility(View.VISIBLE);													
		  				  		centerscrolltext.startAnimation(animAlphaText);			  		
		  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
		  						
		  						//break;
		  					}
		  		    		
		  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
		  					return;
		  		    	}
		  	    	});
		  	    	
		  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
		          	  public void onClick(DialogInterface dialog, int whichButton) {
		          		  	hideNavigation();
		          	  }
		          	});	  	    	
		  	    	
		  	    	alert.show();
					
					/*
					String s = input.next();
					char selection = s.charAt(0);
					
					switch (selection) {
					case 'y':
					case 'Y':
						
						if (dodgeBlowSpell[0] < 1) {
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> You have already used your Dodge Blow spell!");
							
							break;
						}
						dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
						return dodgeBlowSpell;
					case 'n':
					case 'N':
						break;
					default:
						computerDamage(i, gameOn);
					}
					*/
				}
				
				int result = (int) ((Math.random() * 6) + 1);
				int attackDamage = result;
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + " for damage!");
				
				/*
				for (int x = 0; x < 1000; --x)// To give human time to read.
				{
				}
				*/
				
				ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - attackDamage;
		
				if (ArrayOfHitPoints.hitpoints[0] == 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0]	+ ", you have been knocked unconscious!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();
					
					/*
					System.out.print("Press a key to continue... ");
					input.nextLine();
					*/
				}
		
				if (ArrayOfHitPoints.hitpoints[0] < 0) {
					
					
					/*
					 * 
					 * Picture of one sword destroying another.
					 * 
					 * deathGraphic();
					 * 
					 */
					
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been slain!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();
					
					/*
					System.out.print("Press a key to continue... ");
					Scanner input = new Scanner(System.in);
					input.nextLine();
					*/
					
					playerDeadYet[0] = "yes";
					gameOverCheck();
				}
  	  	    }
		});
		return ArrayOfHitPoints.hitpoints;
	}
	
	public void computerCriticalMiss() {						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				/*
				 * 
				 * Picture of swords clanging together:
				 * 
				 * 
				 * swordsGraphic();
				 * 
				 */
				
				/*
				System.out.println("     /\\___________            ___________/\\");
				System.out
						.println("/|---||___________\\ CRITICAL /___________||---|\\");
				System.out
						.println("\\|---||___________/   MISS   \\___________||---|/");
				System.out.println("     \\/                                  \\/");
				for (int x = 0; x < 1; --x) {
				}
				*/
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer player must roll to see if it hit itself...");
				
				// for(int x = 0; x < 100; --x)
				// {}
				
				computerCriticalMissAttack();
				return;
  	  	    }
		});		
	}
	
	public void computerBless() {//WAS int						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				blessSpell[1] = blessSpell[1] - 1;
				
				
				/*
				 * 
				 * Picture of hands coming together.
				 * 
				 * blessGraphic();
				 * 
				 */
				
		
				int attackResult = (int) ((Math.random() * 20) + 1);
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer player attacks...");
				
				// for(int x = 0; x < 100; --x)
				// {}
				
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer player rolls a " + attackResult + ", +2 for the Bless Spell = " + (attackResult + 2));
				
		
				if (attackResult >= 20) {
					computerCriticalHit();
					return;
				}
		
				if (attackResult >= 12 && attackResult <= 19) {
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> The computer's attack hits!");
					
		
					if (mightyBlowSpell[1] > 0) {
						
						int computerUseMightyBlow = (int) ((Math.random() * 20) + 1);
						// NEED 1 SCENARIO FOR HUMAN PLAYER HP ABOVE 12 AND 1 SCENARIO FOR HUMAN PLAYER HP BELOW 12
						
						if (ArrayOfHitPoints.hitpoints[0] >= 12) {
							// using 12 just because if comp roll 6 for damage * 2 = 12, comp can kill human player.
						
							if (computerUseMightyBlow >= 15) {
								// less likely for comp to use MB because human HP > 12.
								
								centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> The computer uses mighty blow!");
								
								/*
								for (int x = 0; x < 1000; --x) {
								}
								*/
								
								
								mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
		
								
								/*
								 * 
								 * Picture of swords clanging together:
								 * 
								 * 
								 * swordsGraphic();
								 * 
								 */
								
								/*
								System.out
										.println("     /\\___________          ___________/\\");
								System.out
										.println("/|---||___________\\ MIGHTY /___________||---|\\");
								System.out
										.println("\\|---||___________/  BLOW  \\___________||---|/");
								System.out
										.println("     \\/                                \\/");
								for (int x = 0; x < 1; --x) {
								}
								*/
								
								computerMightyBlow();
								return;
							} else {
								computerDamage();
								return;
							}
						}
						if (ArrayOfHitPoints.hitpoints[0] < 12)// see above for explanation
						{
							if (computerUseMightyBlow < 15) {
								// more likely for comp to use MB because human HP < 12.
								
								centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> The computer uses mighty blow!");
								
								/*
								for (int x = 0; x < 1000; --x) {
								}
								*/
								
								
								mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
		
								
								/*
								 * 
								 * Picture of swords clanging together:
								 * 
								 * 
								 * swordsGraphic();
								 * 
								 */
								
								/*
								System.out
										.println("     /\\___________          ___________/\\");
								System.out
										.println("/|---||___________\\ MIGHTY /___________||---|\\");
								System.out
										.println("\\|---||___________/  BLOW  \\___________||---|/");
								System.out
										.println("     \\/                                \\/");
								for (int x = 0; x < 1; --x) {
								}
								*/
		
								computerMightyBlow();
								return;
							} else {
								computerDamage();
								return;
							}
						}
					}
					computerDamage();
					return;
				}
		
				if (attackResult < 12 && attackResult > 0) {
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> The computer's attack misses!");			
				}
				
				// don't critically miss when using bless.
				/*
				 * if (attackResult <= 1) { computerCriticalMiss(i, turn); }
				 */
				// return gameOn;
				
				return;
  	  	    }
		});	
	}
	
	public void computerHaste() {//WAS int[]
		// no bless because you can't use 2 spells in one turn.					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	
  	  	    	
	
				hasteSpell[1] = hasteSpell[1] - 1;
		
				
				/*
				 * 
				 * Picture of swords clanging together:
				 * 
				 * 
				 * swordsGraphic();
				 * 
				 */
				
				
				/*
				 * 
				 * ANIMATION FOR HASTE?
				 * 
				 */
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> TWO ATTACKS!");
				
				/*
				System.out.println("     /\\___________           ___________/\\");
				System.out.println("/|---||___________\\   TWO   /___________||---|\\");
				System.out.println("\\|---||___________/ ATTACKS \\___________||---|/");
				System.out.println("     \\/                                 \\/");
				for (int x = 0; x < 1; --x) {
				}
				*/
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> First Attack...");
				
				/*				
				System.out.println();
				System.out.println("     /\\____________");
				System.out.println("/|---||_1st Attack_\\");
				System.out.println("\\|---||____________/");
				System.out.println("     \\/           ");
				System.out.println();
				*/
				
				computerAttack();
		
				if (canHasDisarmed[1] == "yes")// so if you critically miss & drop
												// weapon you don't get 2nd attack.
				{
					return;
				}
				
				
				/*
				 * 
				 * ANIMATION FOR HASTE?
				 * 
				 */
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> Second Attack...");
				
				/*
				System.out.println("     /\\____________");
				System.out.println("/|---||_2nd Attack_\\");
				System.out.println("\\|---||____________/");
				System.out.println("     \\/           ");
				System.out.println();
				*/
				
				computerAttack();
		
				return;
  	  	    }
		});
	}
	
	public String[] computerDisarm() {					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	
  	  	    	
		
				if (blessSpell[1] > 0) {
					
					int result = (int) ((Math.random() * 100) + 1);
					
					if (result >= 51) {
						blessSpell[1] = blessSpell[1] - 1;
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);			  		
						centerscrolltext.append("\n" + "> The computer uses it's bless spell...");
						
						/*
						for (int x = 0; x < 1000; --x) {
						}
						*/
						
						
						/*
						 * 
						 * Picture of hands coming together.
						 * 
						 * blessGraphic();
						 * 
						 */
		
						
						int attackResult = (int) ((Math.random() * 20) + 1);
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);			  		
						centerscrolltext.append("\n" + "> The computer rolls a " + attackResult + ", +2 for the Bless Spell = " + (attackResult + 2));
						
		
						if (attackResult >= 15) {
							canHasDisarmed[0] = "yes";
		
							disarmedTurnStart[0] = turn;
		
							// playerWhoDisarmed[0] = 1;// was i for the 1.
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been disarmed!");
							
							return;
						}
		
						if (attackResult <= 14 && attackResult > 0) {
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer's attempt to disarm you misses!");
							
							return;
						}
						// don't critically miss when using bless.
						/*
						 * if (attackResult <= 1) { computerCriticalMiss(i, turn);
						 * return canHasDisarmed; }
						 */
					}
					if (result < 51) {
						
						int attackResult = (int) ((Math.random() * 20) + 1);
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);			  		
						centerscrolltext.append("\n" + "> The computer rolls a " + attackResult + "!");
						
		
						if (attackResult >= 17) {
							
							canHasDisarmed[0] = "yes";
		
							disarmedTurnStart[0] = turn;
		
							// playerWhoDisarmed[0] = 1;// was i for the 1.
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been disarmed!");
							
							return;
						}
		
						if (attackResult <= 16 && attackResult > 1) {
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer's attempt to disarm you misses!");
							
							return;
						}
						if (attackResult <= 1) {
							computerCriticalMiss();
							return;
						}
					}
				} else {
					
					int attackResult = (int) ((Math.random() * 20) + 1);
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> The computer rolls a " + attackResult + "!");
					
		
					if (attackResult >= 17) {
						canHasDisarmed[0] = "yes";
		
						disarmedTurnStart[0] = turn;
		
						// playerWhoDisarmed[0] = 1;// was i for the 1.
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);			  		
						centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been disarmed!");
						
						return;
					}
		
					if (attackResult <= 16 && attackResult > 1) {
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);			  		
						centerscrolltext.append("\n" + "> The computer's attempt to disarm you misses!");
						
						return;
					}
					if (attackResult <= 1) {
						computerCriticalMiss();
						return;
					}
				}
  	  	    }
		});
		return canHasDisarmed;
	}
	
	public void computerCriticalHitDisarmed() {						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				/*
				 * 
				 * Pictures of fists coming together.
				 * 
				 * punchGraphic();
				 * 
				 */
				
				/*
				System.out.println("_______________                _______________");
				System.out.println("            __ \\              / __");
				System.out.println("              \\ \\            / /");
				System.out.println("      ___/\\__  \\ \\ CRITICAL / /  __/\\___");
				System.out.println("           | \\  \\/   HIT    \\/  / |");
				System.out.println("\\      ____|_/__/            \\__\\_|____      /");
				System.out
						.println(" \\_____/    \\/_/              \\_\\/    \\_____/");
				for (int x = 0; x < 1; --x) {
				}
				*/
				
				
				if (mightyBlowSpell[1] > 0) {
					
					int computerUseMightyBlow = (int) ((Math.random() * 20) + 1);
		
					if (ArrayOfHitPoints.hitpoints[0] >= 12) {
						// using just 1 scenario since it's a criticle hit (computerAttackAgainstDisarmed used 2 scenarios)
					
						if (computerUseMightyBlow >= 11) {
							// 50/50 chance to use MB if human HP above 12.
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer uses mighty blow!");
							
							/*
							for (int x = 0; x < 1000; --x) {
							}
							*/
							
							
							mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
		
							
							/*
							 * 
							 * Pictures of fists coming together.
							 * 
							 * punchGraphic();
							 * 
							 */
							
							/*
							System.out
									.println("_______________              _______________");
							System.out.println("            __ \\            / __");
							System.out.println("              \\ \\          / /");
							System.out
									.println("      ___/\\__  \\ \\ MIGHTY / /  __/\\___");
							System.out.println("           | \\  \\/  BLOW  \\/  / |");
							System.out
									.println("\\      ____|_/__/          \\__\\_|____      /");
							System.out
									.println(" \\_____/    \\/_/            \\_\\/    \\_____/");
							for (int x = 0; x < 1; --x) {
							}
							*/
							
							
							computerCriticalHitMightyBlowDamageDisarmed();
						} else {
							computerCriticalHitDamageDisarmed();
						}
					}
					if (ArrayOfHitPoints.hitpoints[0] < 12) {
						// if human HP below 12 then MB
					
						computerCriticalHitMightyBlowDamageDisarmed();
					}
				}
				computerCriticalHitDamageDisarmed();
  	  	    }
		});
	}
	
	public int[] computerMightyBlowDisarmed() {					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	
  	  	    	
		
				if (dodgeBlowSpell[0] > 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
		  			
		  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", Do you want to use your Dodge spell?");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
		  		    		
		  		    		//NEED THIS??????????????????
		  		    		if (dodgeBlowSpell[0] < 1) {
		  		    			
		  		    			hideNavigation();
		  						
		  						centerscrolltext.setVisibility(View.VISIBLE);													
		  				  		centerscrolltext.startAnimation(animAlphaText);			  		
		  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
		  						
		  						//break;
		  					}
		  		    		
		  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
		  					return;
		  		    	}
		  	    	});
		  	    	
		  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
		          	  public void onClick(DialogInterface dialog, int whichButton) {
		          		  	hideNavigation();
		          	  }
		          	});	  	    	
		  	    	
		  	    	alert.show();
					
					/*
					String s = input.next();
					char selection = s.charAt(0);			
					switch (selection) {
					case 'y':
					case 'Y':
						System.out.println();
						if (dodgeBlowSpell[0] < 1) {
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> You have already used your Dodge Blow spell!");
							
							break;
						}
						dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
						return dodgeBlowSpell;
					case 'n':
					case 'N':
						break;
					default:
						computerMightyBlowDisarmed(i, gameOn);
					}
					*/
				}
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls for mighty blow damage...");
				
				// for(int x = 0; x < 100; --x)
				// {}
				
				int result = (int) ((Math.random() * 6) + 1);
				int attackDamage = result;
				int attackDamageDisarmed = (result - 2);
				
				if (attackDamageDisarmed < 0) {
					attackDamageDisarmed = 0;
				}
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + ", -2 damage for punch = " + attackDamageDisarmed + " damage!");
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (attackDamageDisarmed * 2) + "!");
				
				/*
				for (int x = 0; x < 1000; --x)// To give human time to read.
				{
				}
				*/
				
				ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - (attackDamageDisarmed * 2);
		
				if (ArrayOfHitPoints.hitpoints[0] == 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been knocked unconscious!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();
					
					/*
					System.out.print("Press a key to continue... ");
					input.nextLine();
					*/
				}
		
				if (ArrayOfHitPoints.hitpoints[0] < 0) {
					
					
					/*
					 * 
					 * Picture of one sword destroying another.
					 * 
					 * deathGraphic();
					 * 
					 */
					
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been slain!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();
					
					/*
					System.out.print("Press a key to continue... ");
					input.nextLine();
					*/
					
					playerDeadYet[0] = "yes";
					gameOverCheck();
				}
  	  	    }
		});
		return ArrayOfHitPoints.hitpoints;
	}
	
	public int[] computerDamageDisarmed() {					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	
  	  	    	
		
				if (dodgeBlowSpell[0] > 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
		  			
		  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", Do you want to use your Dodge spell?");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
		  		    		
		  		    		//NEED THIS??????????????????
		  		    		if (dodgeBlowSpell[0] < 1) {
		  		    			
		  		    			hideNavigation();
		  						
		  						centerscrolltext.setVisibility(View.VISIBLE);													
		  				  		centerscrolltext.startAnimation(animAlphaText);			  		
		  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
		  						
		  						//break;
		  					}
		  		    		
		  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
		  					return;
		  		    	}
		  	    	});
		  	    	
		  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
		          	  public void onClick(DialogInterface dialog, int whichButton) {
		          		  	hideNavigation();
		          	  }
		          	});	  	    	
		  	    	
		  	    	alert.show();
					
					/*
					String s = input.next();
					char selection = s.charAt(0);						
					switch (selection) {
					case 'y':
					case 'Y':
						System.out.println();
						if (dodgeBlowSpell[0] < 1) {
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> You have already used your Dodge Blow spell!");
							
							break;
						}
						dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
						return dodgeBlowSpell;
					case 'n':
					case 'N':
						break;
					default:
						computerDamageDisarmed(i, gameOn);
					}
					*/
				}
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls for damage...");
				
				// for(int x = 0; x < 100; --x)
				// {}
				
				int result = (int) ((Math.random() * 6) + 1);
				int attackDamage = result;
				int attackDamageDisarmed = (result - 2);
				
				if (attackDamageDisarmed < 0) {
					attackDamageDisarmed = 0;
				}
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + ", -2 damage for punch = " + attackDamageDisarmed + " damage!");
				
				/*
				for (int x = 0; x < 1000; --x)// To give human time to read.
				{
				}
				*/
				
				ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - attackDamageDisarmed;
		
				if (ArrayOfHitPoints.hitpoints[0] == 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been knocked unconscious!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();
					
					/*
					System.out.print("Press a key to continue... ");
					input.nextLine();
					*/
				}
		
				if (ArrayOfHitPoints.hitpoints[0] < 0) {
					
					
					/*
					 * 
					 * Picture of one sword destroying another.
					 * 
					 * deathGraphic();
					 * 
					 */
					
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been slain!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();			
					
					/*
					Scanner input = new Scanner(System.in);
					input.nextLine();
					*/
					
					playerDeadYet[0] = "yes";
					gameOverCheck();
				}
  	  	    }
		});
		return ArrayOfHitPoints.hitpoints;
	}
	
	public int[] computerCriticalHitMightyBlowDamageDisarmed() {					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	
  	  	    	
		
				if (dodgeBlowSpell[0] > 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
		  			
		  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", Do you want to use your Dodge spell?");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
		  		    		
		  		    		//NEED THIS??????????????????
		  		    		if (dodgeBlowSpell[0] < 1) {
		  		    			
		  		    			hideNavigation();
		  						
		  						centerscrolltext.setVisibility(View.VISIBLE);													
		  				  		centerscrolltext.startAnimation(animAlphaText);			  		
		  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
		  						
		  						//break;
		  					}
		  		    		
		  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
		  					return;
		  		    	}
		  	    	});
		  	    	
		  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
		          	  public void onClick(DialogInterface dialog, int whichButton) {
		          		  	hideNavigation();
		          	  }
		          	});	  	    	
		  	    	
		  	    	alert.show();
					
					
					/*String s = input.next();
					char selection = s.charAt(0);			
					switch (selection) {
					case 'y':
					case 'Y':
						
						if (dodgeBlowSpell[0] < 1) {
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> You have already used your Dodge Blow spell!");
							
							break;
						}
						dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
						return dodgeBlowSpell;
					case 'n':
					case 'N':
						break;
					default:
						computerCriticalHitMightyBlowDamageDisarmed(i, gameOn);
					}
					*/
				}
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer will roll twice for critical hit damage.");
				
		
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer's first roll...");
				
				/*
				for (int x = 0; x < 1000; --x) {
				}
				*/
				
				int resultOne = (int) ((Math.random() * 6) + 1);
				int attackDamageOne = resultOne;
				int attackDamageOneDisarmed = (resultOne - 2);
				
				if (attackDamageOneDisarmed < 0) {
					attackDamageOneDisarmed = 0;
				}
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls a " + attackDamageOne	+ ", -2 damage for punch = " + attackDamageOneDisarmed + " damage!");
				
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer's second roll...");
				
				/*
				for (int x = 0; x < 1000; --x) {
				}
				*/
				
				int resultTwo = (int) ((Math.random() * 6) + 1);
				int attackDamageTwo = resultTwo;
				int attackDamageTwoDisarmed = (resultTwo - 2);
				
				if (attackDamageTwoDisarmed < 0) {
					attackDamageTwoDisarmed = 0;
				}
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls a " + attackDamageTwo + ", -2 damage for punch = " + attackDamageTwoDisarmed + " damage!");
				
		
				int totalAttackDamage = (attackDamageOneDisarmed + attackDamageTwoDisarmed);
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls a total of " + totalAttackDamage + " for critical hit damage!");
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (totalAttackDamage * 2) + "!");
				
				/*
				for (int x = 0; x < 1000; --x)// To give human time to read.
				{
				}
				*/
				
				ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - (totalAttackDamage * 2);
		
				/*
				 * FIXED CRITICAL HIT SO DELETE??? System.out.println();
				 * System.out.print(
				 * "The computer player rolls for critical hit damage and mighty blow damage..."
				 * ); //for(int x = 0; x < 100; --x) //{} int result =
				 * (int)((Math.random()*12)+1); int attackDamage = result; int
				 * attackDamageDisarmed = (result - 2); if (attackDamageDisarmed < 0) {
				 * attackDamageDisarmed = 0; } System.out.println();
				 * System.out.println("The computer player rolls " + attackDamage +
				 * ", -2 damage for punch = " + attackDamageDisarmed + " damage!");
				 * System.out.println();
				 * System.out.println("Double damage for Mighty Blow = " +
				 * (attackDamageDisarmed * 2) + "!"); System.out.println();
				 * 
				 * for(int x = 0; x < 1000; --x)//To give human time to read. {}
				 * 
				 * hitPoints[0] = hitPoints[0] - (attackDamageDisarmed * 2);
				 */
		
				if (ArrayOfHitPoints.hitpoints[0] == 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0]	+ ", you have been knocked unconscious!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();
					
					/*
					System.out.print("Press a key to continue... ");
					input.nextLine();
					*/
				}
		
				if (ArrayOfHitPoints.hitpoints[0] < 0) {
					
					
					/*
					 * 
					 * Picture of one sword destroying another.
					 * 
					 * deathGraphic();
					 * 
					 */
					
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been slain!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();	
					
					/*
					System.out.print("Press a key to continue... ");
					Scanner input = new Scanner(System.in);
					input.nextLine();
					*/
					
					playerDeadYet[0] = "yes";
					gameOverCheck();
				}
  	  	    }
		});
		return ArrayOfHitPoints.hitpoints;
	}
	
	public int[] computerCriticalHitMightyBlowDamage() {						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				if (dodgeBlowSpell[0] > 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
		  			
		  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", Do you want to use your Dodge spell?");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
		  		    		
		  		    		//NEED THIS??????????????????
		  		    		if (dodgeBlowSpell[0] < 1) {
		  		    			
		  		    			hideNavigation();
		  						
		  						centerscrolltext.setVisibility(View.VISIBLE);													
		  				  		centerscrolltext.startAnimation(animAlphaText);			  		
		  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
		  						
		  						//break;
		  					}
		  		    		
		  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
		  					return;
		  		    	}
		  	    	});
		  	    	
		  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
		          	  public void onClick(DialogInterface dialog, int whichButton) {
		          		  	hideNavigation();
		          	  }
		          	});	  	    	
		  	    	
		  	    	alert.show();
					
					/*
					String s = input.next();
					char selection = s.charAt(0);			
					switch (selection) {
					case 'y':
					case 'Y':
						
						if (dodgeBlowSpell[0] < 1) {
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> You have already used your Dodge Blow spell!");
							
							break;
						}
						dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
						return dodgeBlowSpell;
					case 'n':
					case 'N':
						break;
					default:
						computerCriticalHitMightyBlowDamage(i, gameOn);
					}
					*/
				}
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls twice for critical hit damage...");
				
				// for(int x = 0; x < 100; --x)
				// {}
		
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer makes it's first roll...");
				
				/*
				for (int x = 0; x < 1000; --x) {
				}
				*/
				
				int resultOne = (int) ((Math.random() * 6) + 1);
				int attackDamageOne = resultOne;
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls a " + attackDamageOne	+ "!");
				
		
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer makes it's second roll...");
				
				/*
				for (int x = 0; x < 1000; --x) {
				}
				*/
				
				int resultTwo = (int) ((Math.random() * 6) + 1);
				int attackDamageTwo = resultTwo;
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls a " + attackDamageTwo	+ "!");
				
		
				int totalAttackDamage = (attackDamageOne + attackDamageTwo);
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls a total of " + totalAttackDamage + " for critical hit damage!");
				
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (totalAttackDamage * 2) + "!");
				
				/*
				for (int x = 0; x < 1000; --x)// To give human time to read.
				{
				}
				*/
				
				ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - (totalAttackDamage * 2);
		
				if (ArrayOfHitPoints.hitpoints[0] == 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0]	+ ", you have been knocked unconscious!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();	
					
					/*
					System.out.print("Press a key to continue... ");
					input.nextLine();
					*/
				}
		
				if (ArrayOfHitPoints.hitpoints[0] < 0) {
					
					
					/*
					 * 
					 * Picture of one sword destroying another.
					 * 
					 * deathGraphic();
					 * 
					 */
					
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been slain!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();
					
					/*
					System.out.print("Press a key to continue... ");
					Scanner input = new Scanner(System.in);
					input.nextLine();
					*/
					
					playerDeadYet[0] = "yes";
					gameOverCheck();
				}
  	  	    }
		});
		return ArrayOfHitPoints.hitpoints;
	}
	
	public int[] computerCriticalHitDamage() {					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	
  	  	    	
		
				if (dodgeBlowSpell[0] > 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
		  			
		  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", Do you want to use your Dodge spell?");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
		  		    		
		  		    		//NEED THIS??????????????????
		  		    		if (dodgeBlowSpell[0] < 1) {
		  		    			
		  		    			hideNavigation();
		  						
		  						centerscrolltext.setVisibility(View.VISIBLE);													
		  				  		centerscrolltext.startAnimation(animAlphaText);			  		
		  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
		  						
		  						//break;
		  					}
		  		    		
		  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
		  					return;
		  		    	}
		  	    	});
		  	    	
		  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
		          	  public void onClick(DialogInterface dialog, int whichButton) {
		          		  	hideNavigation();
		          	  }
		          	});	  	    	
		  	    	
		  	    	alert.show();
					
					/*
					String s = input.next();
					char selection = s.charAt(0);			
					switch (selection) {
					case 'y':
					case 'Y':
						
						if (dodgeBlowSpell[0] < 1) {
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> You have already used your Dodge Blow spell!");
							
							break;
						}
						dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
						return dodgeBlowSpell;
					case 'n':
					case 'N':
						break;
					default:
						computerCriticalHitDamage(i, gameOn);
					}
					*/
				}
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer will roll twice for critical hit damage.");
				
		
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer's first roll...");
				
				/*
				for (int x = 0; x < 1000; --x) {
				}
				*/
				
				int resultOne = (int) ((Math.random() * 6) + 1);
				int attackDamageOne = resultOne;
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls a " + attackDamageOne	+ "!");
				
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer's second roll...");
				
				/*
				for (int x = 0; x < 1000; --x) {
				}
				*/
				
				int resultTwo = (int) ((Math.random() * 6) + 1);
				int attackDamageTwo = resultTwo;
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls a " + attackDamageTwo	+ "!");
				
		
				int totalAttackDamage = (attackDamageOne + attackDamageTwo);
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls a total of " + totalAttackDamage + " for critical hit damage!");
				
				/*
				for (int x = 0; x < 1000; --x)// To give human time to read.
				{
				}
				*/
		
				ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - totalAttackDamage;
		
				if (ArrayOfHitPoints.hitpoints[0] == 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0]	+ ", you have been knocked unconscious!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();
					
					/*
					System.out.print("Press a key to continue... ");
					input.nextLine();
					*/
				}
		
				if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] < 0) {
					
					
					/*
					 * 
					 * Picture of one sword destroying another.
					 * 
					 * deathGraphic();
					 * 
					 */
					
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been slain!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();
					
					/*
					System.out.print("Press a key to continue... ");
					Scanner input = new Scanner(System.in);
					input.nextLine();
					*/
					
					playerDeadYet[0] = "yes";
					gameOverCheck();
				}
  	  	    }
		});
		return ArrayOfHitPoints.hitpoints;
	}
	
	public void computerCriticalMissAttack() {						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				int criticalMissAttackResult = (int) ((Math.random() * 20) + 1);
		
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls a " + criticalMissAttackResult + "!");
				
				if (criticalMissAttackResult >= 17) {
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> The computer hits itself!");
					
					computerCriticalMissDamage();
				
				} else {
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> The computer did not hit itself. But it must roll to see if it loses it's weapon...");
					
					// for(int x = 0; x < 100; --x)
					// {}
					
					int criticalMissDisarmResult = (int) ((Math.random() * 20) + 1);
		
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> The computer rolls a " + criticalMissDisarmResult + "!");
					
					if (criticalMissDisarmResult >= 17) {
						canHasDisarmed[1] = "yes";
						disarmedTurnStart[1] = turn;
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);			  		
						centerscrolltext.append("\n" + "> The computer is disarmed!");
						
					} else {
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);			  		
						centerscrolltext.append("\n" + "> The computer holds on to it's weapon!");				
					}
				}
				
				/*
				for (int x = 0; x < 1000; --x)// To give human time to read.
				{
				}
				*/
  	  	    }
		});
	}
	
	public int[] computerCriticalHitDamageDisarmed() {						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				if (dodgeBlowSpell[0] > 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
		  			
		  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your Dodge spell?");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
		  		    		
		  		    		//NEED THIS??????????????????
		  		    		if (dodgeBlowSpell[0] < 1) {
		  		    			
		  		    			hideNavigation();
		  						
		  						centerscrolltext.setVisibility(View.VISIBLE);													
		  				  		centerscrolltext.startAnimation(animAlphaText);			  		
		  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
		  						
		  						//break;
		  					}
		  		    		
		  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
		  					return;
		  		    	}
		  	    	});
		  	    	
		  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
		          	  public void onClick(DialogInterface dialog, int whichButton) {
		          		  	hideNavigation();
		          	  }
		          	});	  	    	
		  	    	
		  	    	alert.show();
					
					/*
					String s = input.next();
					char selection = s.charAt(0);			
					switch (selection) {
					case 'y':
					case 'Y':
						
						if (dodgeBlowSpell[0] < 1) {
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> You have already used your Dodge Blow spell!");
							
							break;
						}
						dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
						return dodgeBlowSpell;
					case 'n':
					case 'N':
						break;
					default:
						computerCriticalHitDamageDisarmed(i, gameOn);
					}
					*/
				}
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer will roll twice for critical hit damage.");
				
		
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer's first roll...");
				
				/*
				for (int x = 0; x < 1000; --x) {
				}
				*/
				
				int resultOne = (int) ((Math.random() * 6) + 1);
				int attackDamageOne = resultOne;
				int attackDamageOneDisarmed = (resultOne - 2);
				
				if (attackDamageOneDisarmed < 0) {
					attackDamageOneDisarmed = 0;
				}
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls a " + attackDamageOne + ", -2 damage for punch = " + attackDamageOneDisarmed + " damage!");
				
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer's second roll...");
				
				/*
				for (int x = 0; x < 1000; --x) {
				}
				*/
				
				int resultTwo = (int) ((Math.random() * 6) + 1);
				int attackDamageTwo = resultTwo;
				int attackDamageTwoDisarmed = (resultTwo - 2);
				
				if (attackDamageTwoDisarmed < 0) {
					attackDamageTwoDisarmed = 0;
				}
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls a " + attackDamageTwo + ", -2 damage for punch = " + attackDamageTwoDisarmed + " damage!");
				
		
				int totalAttackDamage = (attackDamageOneDisarmed + attackDamageTwoDisarmed);
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls a total of " + totalAttackDamage + " for critical hit damage!");
				
				
				/*
				 * FIXED CRITICAL HIT SO DELETE??? System.out.println();
				 * System.out.print
				 * ("The computer player rolls for critical hit damage..."); //for(int x
				 * = 0; x < 100; --x) //{} int result = (int)((Math.random()*12)+1); int
				 * attackDamage = result; int attackDamageDisarmed = (result - 2); if
				 * (attackDamageDisarmed < 0) { attackDamageDisarmed = 0; }
				 * System.out.println(); System.out.println("The computer player rolls "
				 * + attackDamage + ", -2 damage for punch = " + attackDamageDisarmed +
				 * " damage!");
				 * 
				 * for(int x = 0; x < 1000; --x)//To give human time to read. {}
				 */
		
				ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - totalAttackDamage;
		
				if (ArrayOfHitPoints.hitpoints[0] == 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0]	+ ", you have been knocked unconscious!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();
					
					/*
					System.out.print("Press a key to continue... ");
					input.nextLine();
					*/
				}
		
				if (ArrayOfHitPoints.hitpoints[0] < 0) {
					
					
					/*
					 * 
					 * Picture of one sword destroying another.
					 * 
					 * deathGraphic();
					 * 
					 */
					
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been slain!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();
					
					/*
					System.out.print("Press a key to continue... ");
					Scanner input = new Scanner(System.in);
					input.nextLine();
					*/
					
					playerDeadYet[0] = "yes";
					gameOverCheck();
				}
  	  	    }
		});
		return ArrayOfHitPoints.hitpoints;
	}
	
	public int[] computerCriticalMissDamage() {						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer must roll for damage to itself...");
				
				// for(int x = 0; x < 100; --x)
				// {}
				
				int result = (int) ((Math.random() * 6) + 1);
				int attackDamage = result;
				
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer rolls a " + attackDamage + " for damage!");
				
				/*
				for (int x = 0; x < 1000; --x)// To give human time to read.
				{
				}
				*/
				
				ArrayOfHitPoints.hitpoints[1] = ArrayOfHitPoints.hitpoints[1] - attackDamage;
		
				if (ArrayOfHitPoints.hitpoints[1] == 0) {
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> The computer has been knocked unconscious!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle("The computer has been knocked unconscious!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();
					
					/*
					System.out.print("Press a key to continue... ");
					input.nextLine();
					*/
				}
				if (ArrayOfHitPoints.hitpoints[1] < 0) {
					
					
					/*
					 * 
					 * Picture of one sword destroying another.
					 * 
					 * deathGraphic();
					 * 
					 */
					
					/*
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> The computer has been slain!");
					*/
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle("The computer has been slain!");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		hideNavigation();
				    	}
			    	});
			    	
			    	alert.show();
					
					/*
					System.out.print("Press a key to continue... ");
					Scanner input = new Scanner(System.in);
					input.nextLine();
					*/
					playerDeadYet[1] = "yes";
					gameOverCheck();
				}
  	  	    }
		});
		return ArrayOfHitPoints.hitpoints;
	}
	
	
	
	
	
	
	
	
	
	public int attack() {

		// INPUT VALIDATION ...NEEDS
		// WORK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!(try/catch??????)
		/*
		 * if (numberOfPlayers > 1) { System.out.print("Player " +
		 * playerNumber[i] +
		 * ", who do you want to attack? (Enter player number): ");
		 * playerNumberAttacked = input.nextInt(); playerNumberAttacked =
		 * playerNumberAttacked - 1; //USE 0-5 NOT 1-6 }
		 * 
		 * while (playerNumberAttacked < 0 || playerNumberAttacked > 5)// ||
		 * !input.hasNextInt()); { System.out.print(
		 * "Not valid entry!..Who do you want to attack? (Enter player number): "
		 * ); playerNumberAttacked = input.nextInt(); }
		 */					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	
  	  	    	
		
				if (canHasDisarmed[playerNumberAttacked] == "yes")
					// the goto method to get bonus if attacking disarmed opponent.
				{
					attackAgainstDisarmed();
					return;
				}
				
							
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> Please slide the die...");
		
				/*
				 * 
				 * 
				 * SLIDE 20-SIDED DIE
				 * 
				 * 
				 */
		
				
				int attackResult = (int) ((Math.random() * 20) + 1);
		
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> You roll a " + attackResult + "!");		
				
		
				if (attackResult >= 20) {
					criticalHit();
					return;
				}
				if (attackResult >= 14 && attackResult <= 19) {
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> Your attack hits!");
					
					if (mightyBlowSpell[i] > 0) {
						/*
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);		  		
						centerscrolltext.append("\n" + ArrayOfPlayers.player[i] + ", do you want to use Mighty Blow?");
						*/
						
						AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
			  			
			  	    	alert.setTitle(ArrayOfPlayers.player[i] + ", do you want to use Mighty Blow?");
			  	    	/*
			  	    	alert.setMessage("something");
			  	    	*/	  	    	
			  	    	
			  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			  		    	public void onClick(DialogInterface dialog, int whichButton) {
			  		    		
			  		    		hideNavigation();
			  		    		
			  		    		mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
			  		    		
			  		    		
			  		    		/*
								 * 
								 * Picture of swords clanging together:
								 * 
								 * 
								 * swordsGraphic();
								 * 
								 */
			  		    		
			  		    		/*
			  		    		System.out
								.println("     /\\___________          ___________/\\");
			  		    		System.out
								.println("/|---||___________\\ MIGHTY /___________||---|\\");
			  		    		System.out
								.println("\\|---||___________/  BLOW  \\___________||---|/");
			  		    		System.out
								.println("     \\/                                \\/");
			  		    		for (int x = 0; x < 1; --x) {
								}
								*/
		
								
			  		    		mightyBlow();
								return;
			  		    	}
			  	    	});
			  	    	
			  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			          	  public void onClick(DialogInterface dialog, int whichButton) {
			          		  
			          		  hideNavigation();
			          		  
			          		  damage();
			          		  return;
			          	  }
			          	});	  	    	
			  	    	
			  	    	alert.show();
						
						/*
						String s = input.next();
						char selection = s.charAt(0);
						switch (selection) {
						case 'y':
						case 'Y':
							if (numberOfPlayers == 1) {
								mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
							}
							if (numberOfPlayers > 1) {
								mightyBlowSpell[i] = mightyBlowSpell[i] - 1;
							}
		
							System.out.println();
		
							swordsGraphic();
							System.out
									.println("     /\\___________          ___________/\\");
							System.out
									.println("/|---||___________\\ MIGHTY /___________||---|\\");
							System.out
									.println("\\|---||___________/  BLOW  \\___________||---|/");
							System.out
									.println("     \\/                                \\/");
							for (int x = 0; x < 1; --x) {
							}
		
							mightyBlow(i, playerNumberAttacked, gameOn);
							return playerNumberAttacked;
						case 'n':
						case 'N':
							damage(i, playerNumberAttacked, gameOn);
							return playerNumberAttacked;
						default:
							damage(i, playerNumberAttacked, gameOn);
							
							// IDEALLY WANT THIS TO GO BACK AND ASK AGAIN....RECODE???
							 * 
							return playerNumberAttacked;
						}
						*/
					}
					damage();
					return;
				}
				if (attackResult < 14 && attackResult > 1) {
					
					centerscrolltext.setVisibility(View.VISIBLE);
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> Your attack misses!");
					
					return;
				}
				if (attackResult <= 1) {
					criticalMiss();
					return;
				}
  	  	    }
		});
		return playerNumberAttacked;
	}

	public void attackAgainstDisarmed() {//WAS int						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> Please slide the die...");		
				
				/*
				 * 
				 * 
				 * SLIDE 20-SIDED DIE
				 * 
				 * 
				 */		
				
				/*		
				Scanner input = new Scanner(System.in);
				input.nextLine();
				*/		
				int attackResult = (int) ((Math.random() * 20) + 1);
				int attackResultAgainstDisarmed = (attackResult + 2);
		
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> You roll a " + attackResult	+ ", +2 because your opponent is disarmed = " + attackResultAgainstDisarmed);
						
		
				if (attackResultAgainstDisarmed >= 20) {
					criticalHit();
					return;
				}
				
				if (attackResultAgainstDisarmed >= 12 && attackResultAgainstDisarmed <= 19) {
					
					centerscrolltext.setVisibility(View.VISIBLE);
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> Your attack hits!");
					
					if (mightyBlowSpell[i] > 0) {				
						
						AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
			  			
			  	    	alert.setTitle(ArrayOfPlayers.player[i] + ", do you want to use Mighty Blow?");
			  	    	/*
			  	    	alert.setMessage("something");
			  	    	*/	  	    	
			  	    	
			  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
			  		    		
			  		    		/*
								 * 
								 * Picture of swords clanging together:
								 * 
								 * 
								 * swordsGraphic();
								 * 
								 */	  		    		
			  		    		
			  		    		/*
			  		    		swordsGraphic();
								System.out
										.println("     /\\___________          ___________/\\");
								System.out
										.println("/|---||___________\\ MIGHTY /___________||---|\\");
								System.out
										.println("\\|---||___________/  BLOW  \\___________||---|/");
								System.out
										.println("     \\/                                \\/");
								for (int x = 0; x < 1; --x) {
								}
			  		    		*/
			  		    		
			  		    		
			  		    		hideNavigation();
			  		    		
			  		    		mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
			  		    		
			  		    		mightyBlow();
								return;	  		    		
			  		    	}
			  	    	});
			  	    	
			  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			          	  public void onClick(DialogInterface dialog, int whichButton) {
			          		  
			          		  hideNavigation();
			          		  
			          		  damage();
			          		  return;
			          	  }
			          	});	  	    	
			  	    	
			  	    	alert.show();				
						
						
			  		    /*
						System.out.print("Player " + playerNumber[i] + ", do you want to use Mighty Blow(Y/N)? ");
						String s = input.next();
						char selection = s.charAt(0);
						switch (selection) {
						case 'y':
						case 'Y':
							if (numberOfPlayers == 1) {
								mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
							}
							
							
							if (numberOfPlayers > 1) {
								mightyBlowSpell[i] = mightyBlowSpell[i] - 1;
							}					
		
							System.out.println();
		
							swordsGraphic();
							System.out
									.println("     /\\___________          ___________/\\");
							System.out
									.println("/|---||___________\\ MIGHTY /___________||---|\\");
							System.out
									.println("\\|---||___________/  BLOW  \\___________||---|/");
							System.out
									.println("     \\/                                \\/");
							for (int x = 0; x < 1; --x) {
							}
							
		
							mightyBlow(i, playerNumberAttacked, gameOn);
							return playerNumberAttacked;
						case 'n':
						case 'N':
							damage(i, playerNumberAttacked, gameOn);
							return playerNumberAttacked;
						default:
							damage(i, playerNumberAttacked, gameOn);
						}
						*/
					}
					damage();
					return;
				}
				
				if (attackResultAgainstDisarmed < 12 && attackResultAgainstDisarmed > 0) {
					
					centerscrolltext.setVisibility(View.VISIBLE);
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> Your attack misses!");			
				}
				// CAN'T CRITICALLY MISS WHEN ATTACKING DISARMED
				// OPPONENT????????????????????????????
				/*
				 * if (attackResult <= 1) { criticalMiss(i, turn, playerNumberAttacked,
				 * gameOn); return playerNumberAttacked; }
				 */
				
				return;
  	  	    }
		});		
	}

	public int criticalHit() {
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
		
				/*
				 * 
				 * Picture of swords clanging together:
				 * 
				 * 
				 * swordsGraphic();
				 * 
				 */
				
				/*
				swordsGraphic();
				System.out.println("     /\\___________            ___________/\\");
				System.out
						.println("/|---||___________\\ CRITICAL /___________||---|\\");
				System.out
						.println("\\|---||___________/   HIT    \\___________||---|/");
				System.out.println("     \\/                                  \\/");
				for (int x = 0; x < 1; --x) {
				}
				*/		
				
				
				if (mightyBlowSpell[i] > 0) {
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
		  			
		  	    	alert.setTitle(ArrayOfPlayers.player[i] + ", do you want to use Mighty Blow?");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
		  		    		
		  		    		/*
							 * 
							 * Picture of swords clanging together:
							 * 
							 * 
							 * swordsGraphic();
							 * 
							 */	  		    		
		  		    		
		  		    		/*
		  		    		swordsGraphic();
							System.out
									.println("     /\\___________          ___________/\\");
							System.out
									.println("/|---||___________\\ MIGHTY /___________||---|\\");
							System.out
									.println("\\|---||___________/  BLOW  \\___________||---|/");
							System.out
									.println("     \\/                                \\/");
							for (int x = 0; x < 1; --x) {
							}
		  		    		*/
		  		    		
		  		    		
		  		    		hideNavigation();
		  		    		
		  		    		mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
		  		    		
		  		    		criticalHitMightyBlowDamage();
		  					return;	  		    		
		  		    	}
		  	    	});
		  	    	
		  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
		          	  public void onClick(DialogInterface dialog, int whichButton) {
		          		
		          		hideNavigation();
		          		  
		          		criticalHitDamage();
						return;
		          	  }
		          	});		  	    	
		  	    	alert.show();					
					
					
					/*
					System.out.print("Player " + playerNumber[i] + ", do you want to use Mighty Blow(Y/N)? ");
					String s = input.next();
					char selection = s.charAt(0);
					switch (selection) {
					case 'y':
					case 'Y':
						if (numberOfPlayers == 1) {
							mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
						}
						if (numberOfPlayers > 1) {
							mightyBlowSpell[i] = mightyBlowSpell[i] - 1;
						}
		
						System.out.println();
		
						swordsGraphic();
						System.out
								.println("     /\\___________          ___________/\\");
						System.out
								.println("/|---||___________\\ MIGHTY /___________||---|\\");
						System.out
								.println("\\|---||___________/  BLOW  \\___________||---|/");
						System.out
								.println("     \\/                                \\/");
						for (int x = 0; x < 1; --x) {
						}
		
						criticalHitMightyBlowDamage(i, playerNumberAttacked, gameOn);
						return playerNumberAttacked;
					case 'n':
					case 'N':
						criticalHitDamage(i, playerNumberAttacked, gameOn);
						return playerNumberAttacked;
					default:
						criticalHitDamage(i, playerNumberAttacked, gameOn);
					}
					*/
				}
  	  	    }
		});
		criticalHitDamage();
		return playerNumberAttacked;
	}

	public int[] mightyBlow() {					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	
  	  	    	
		
				if (dodgeBlowSpell[playerNumberAttacked] > 0) {
					if (numberOfPlayers == 1) {
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] >= 11) {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> The computer does not dodge.");
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[i] + ", roll for mighty blow damage...");
							
							/*
							 * 
							 * ROLL 6-SIDED DIE
							 * 
							 */
							
							/*
							Scanner input = new Scanner(System.in);
							input.nextLine();
							*/
							int result = (int) ((Math.random() * 6) + 1);
							int attackDamage = result;
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll a " + attackDamage	+ " for damage!");
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (attackDamage * 2) + "!");
							
		
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked]	- (attackDamage * 2);
							
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
							
									if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] == 0) {						
										
										AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
									      
										alert.setTitle("The computer has been knocked unconscious!");
							  	    	/*
							  	    	alert.setMessage("something");
							  	    	*/	    	
								    	
								    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									    	public void onClick(DialogInterface dialog, int whichButton) {
									    		
									    		hideNavigation();
									    		
									    		if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
													
									    			gameEngineHumanFirst2();    							
												}

												if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
									    			
									    			turn();   							
												}
									    	}
								    	});								    	
								    	alert.show();						
									}
				
									if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] < 0) {
				
										/*
										 * 
										 * Picture of one sword destroying another.
										 * 
										 * deathGraphic();
										 * 
										 */
										
										
										AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
									      
										alert.setTitle("The computer has been slain!");
							  	    	/*
							  	    	alert.setMessage("something");
							  	    	*/	    	
								    	
								    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									    	public void onClick(DialogInterface dialog, int whichButton) {
									    		
									    		hideNavigation();
									    		
									    		gameOverCheck();
									    	}
								    	});								    	
								    	alert.show();										
										
										playerDeadYet[playerNumberAttacked] = "yes";										
									}
									
									else {									

										if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
											
							    			gameEngineHumanFirst2();    							
										}

										if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
							    			
							    			turn();   							
										}										
									}				  	  	  			
					  	  	  	}
				  	  	  	}, 2000);
				  	  	  	
							return;
						}
						
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] <= 10 && ArrayOfHitPoints.hitpoints[playerNumberAttacked] > 0) {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> The computer uses it's dodge blow spell.");
							
							dodgeBlowSpell[playerNumberAttacked] = dodgeBlowSpell[playerNumberAttacked] - 1;
							
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {  	  		
	
					  	  	  		if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
										
						    			gameEngineHumanFirst2();    							
									}
	
									if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
						    			
						    			turn();   							
									}					  	  	  	
					  	  	  	}
				  	  	  	}, 2000);
							
							return;
						}
					}
					/*
					if (numberOfPlayers > 1) {
						System.out.println();
						System.out.print("Player " + (playerNumberAttacked + 1)
								+ ", do you want to dodge(Y/N)? ");
						String s = input.next();
						char selection = s.charAt(0);
						switch (selection) {
						case 'y':
						case 'Y':
							System.out.println();
							if (dodgeBlowSpell[playerNumberAttacked] < 1) {
								System.out
										.println("You have already used your Dodge Blow spell!");
								break;
							}
							dodgeBlowSpell[playerNumberAttacked] = dodgeBlowSpell[playerNumberAttacked] - 1;
							return dodgeBlowSpell;
						case 'n':
						case 'N':
							break;
						default:
							mightyBlow(i, playerNumberAttacked, gameOn);
						}
					}
					*/
				}
				
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[i] + ", roll for mighty blow damage...");
						
				/*
				 * 
				 * ROLL 6-SIDED DIE
				 * 
				 */
				
				/*
				Scanner input = new Scanner(System.in);
				input.nextLine();
				*/
				int result = (int) ((Math.random() * 6) + 1);
				int attackDamage = result;
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> You roll a " + attackDamage + " for damage!");
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (attackDamage * 2) + "!");
				
		
				ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (attackDamage * 2);
				
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
				
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] == 0) {
							
							if (numberOfPlayers == 1) {
								
								AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
							      
								alert.setTitle("The computer has been knocked unconscious!");
					  	    	/*
					  	    	alert.setMessage("something");
					  	    	*/	    	
						    	
						    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							    	public void onClick(DialogInterface dialog, int whichButton) {
							    		
							    		hideNavigation();
							    		
							    		if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
											
							    			gameEngineHumanFirst2();    							
										}

										if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
							    			
							    			turn();   							
										}
							    	}
						    	});						    	
						    	alert.show();				
							}
							/*
							if (numberOfPlayers > 1) {
								System.out.println("Player " + (playerNumberAttacked + 1)
										+ ", you have been knocked unconscious!");
							}
							System.out.print("Press a key to continue... ");
							input.nextLine();
							*/
						}
				
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] < 0) {
				
							/*
							 * 
							 * Picture of one sword destroying another.
							 * 
							 * deathGraphic();
							 * 
							 */
							
							
							if (numberOfPlayers == 1) {
								
								AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
							      
								alert.setTitle("The computer has been slain!");
					  	    	/*
					  	    	alert.setMessage("something");
					  	    	*/	    	
						    	
						    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							    	public void onClick(DialogInterface dialog, int whichButton) {
							    		
							    		hideNavigation();
							    		
							    		gameOverCheck();
							    	}
						    	});						    	
						    	alert.show();				
							}
							/*
							if (numberOfPlayers > 1) {
								System.out.println("Player " + (playerNumberAttacked + 1)
										+ ", you have been slain!");
							}
							System.out.print("Press a key to continue... ");
							input.nextLine();
							*/
							
							playerDeadYet[playerNumberAttacked] = "yes";							
						}
						
						else {						

							if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
								
				    			gameEngineHumanFirst2();    							
							}

							if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
				    			
				    			turn();   							
							}
						}
		  	  	  	}
	  	  	  	}, 2000);
  	  	    }
		});
		return ArrayOfHitPoints.hitpoints;
	}

	public int[] damage() {						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				if (dodgeBlowSpell[playerNumberAttacked] > 0) {
					
					if (numberOfPlayers == 1) {
						
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] >= 7) {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> The computer does not dodge.");
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", roll for damage...");
							
							/*
							 * 
							 * ROLL 6-SIDED DIE
							 * 
							 */
							
							/*
							Scanner input = new Scanner(System.in);
							input.nextLine();
							*/
							int result = (int) ((Math.random() * 6) + 1);
							int attackDamage = result;
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll a " + attackDamage + " for damage!");
							
		
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - attackDamage;
							
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
							
									if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] == 0) {
										
										AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
									      
										alert.setTitle("The computer has been knocked unconscious!");
							  	    	/*
							  	    	alert.setMessage("something");
							  	    	*/	    	
								    	
								    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									    	public void onClick(DialogInterface dialog, int whichButton) {
									    		
									    		hideNavigation();
									    		
									    		if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
													
									    			gameEngineHumanFirst2();    							
												}

												if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
									    			
									    			turn();   							
												}
									    	}
								    	});								    	
								    	alert.show();						
									}
				
									if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] < 0) {
										
										/*
										 * 
										 * Picture of one sword destroying another.
										 * 
										 * deathGraphic();
										 * 
										 */
										
										AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
									      
										alert.setTitle("The computer has been slain!");
							  	    	/*
							  	    	alert.setMessage("something");
							  	    	*/	    	
								    	
								    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									    	public void onClick(DialogInterface dialog, int whichButton) {
									    		
									    		hideNavigation();
									    		
									    		gameOverCheck();
									    	}
								    	});								    	
								    	alert.show();				    	
										
										playerDeadYet[playerNumberAttacked] = "yes";										
									}
									
									else {									

										if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
											
							    			gameEngineHumanFirst2();    							
										}

										if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
							    			
							    			turn();   							
										}										
									}				  	  	  			
					  	  	  	}
				  	  	  	}, 2000);
				  	  	  	
							return;
						}
						
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] <= 6 && ArrayOfHitPoints.hitpoints[playerNumberAttacked] > 0) {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> The computer player uses it's dodge blow spell.");
							
							dodgeBlowSpell[playerNumberAttacked] = dodgeBlowSpell[playerNumberAttacked] - 1;
							
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {  	  		
	
					  	  	  		if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
										
						    			gameEngineHumanFirst2();    							
									}
	
									if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
						    			
						    			turn();   							
									}					  	  	  	
					  	  	  	}
				  	  	  	}, 2000);
							
							return;
						}
					}
					
					/*
					if (numberOfPlayers > 1) {
						System.out.println();
						System.out.print("Player " + (playerNumberAttacked + 1)
								+ ", do you want to dodge(Y/N)? ");
						String s = input.next();
						char selection = s.charAt(0);
						switch (selection) {
						case 'y':
						case 'Y':
							System.out.println();
							if (dodgeBlowSpell[playerNumberAttacked] < 1) {
								System.out
										.println("You have already used your Dodge Blow spell!");
								break;
							}
							dodgeBlowSpell[playerNumberAttacked] = dodgeBlowSpell[playerNumberAttacked] - 1;
							return dodgeBlowSpell;
						case 'n':
						case 'N':
							break;
						default:
							damage(i, playerNumberAttacked, gameOn);
						}
					}
					*/
				}
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[i] + ", roll for damage...");
				
				/*
				 * 
				 * ROLL 6-SIDED DIE
				 * 
				 */
				
				/*
				Scanner input = new Scanner(System.in);
				input.nextLine();
				*/
				int result = (int) ((Math.random() * 6) + 1);
				int attackDamage = result;
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> You roll a " + attackDamage + " for damage!");
				
		
				ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked]	- attackDamage;
				
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
				
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] == 0) {
							System.out.println();
							if (numberOfPlayers == 1) {
								
								AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
							      
								alert.setTitle("The computer has been knocked unconscious!");
					  	    	/*
					  	    	alert.setMessage("something");
					  	    	*/	    	
						    	
						    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							    	public void onClick(DialogInterface dialog, int whichButton) {
							    		
							    		hideNavigation();
							    		
							    		if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
											
							    			gameEngineHumanFirst2();    							
										}

										if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
							    			
							    			turn();   							
										}
							    	}
						    	});						    	
						    	alert.show();				
							}
							/*
							if (numberOfPlayers > 1) {
								System.out.println("Player " + (playerNumberAttacked + 1) + ", you have been knocked unconscious!");
							}
							System.out.print("Press a key to continue... ");
							input.nextLine();
							*/
						}
				
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] < 0) {
							
							/*
							 * 
							 * Picture of one sword destroying another.
							 * 
							 * deathGraphic();
							 * 
							 */
							
							
							if (numberOfPlayers == 1) {
								
								AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
							      
								alert.setTitle("The computer has been slain!");
					  	    	/*
					  	    	alert.setMessage("something");
					  	    	*/	    	
						    	
						    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							    	public void onClick(DialogInterface dialog, int whichButton) {
							    		
							    		hideNavigation();
							    		
							    		gameOverCheck();
							    	}
						    	});						    	
						    	alert.show();				
							}
							/*
							if (numberOfPlayers > 1) {
								System.out.println("Player " + (playerNumberAttacked + 1) + ", you have been slain!");
							}
							System.out.print("Press a key to continue... ");
							input.nextLine();
							*/
							
							playerDeadYet[playerNumberAttacked] = "yes";							
						}
						
						else {						

							if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
								
				    			gameEngineHumanFirst2();    							
							}

							if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
				    			
				    			turn();   							
							}
						}
		  	  	  	}
	  	  	  	}, 2000);
  	  	    }
		});
		return ArrayOfHitPoints.hitpoints;
	}
	
	public void criticalMiss() {//WAS int[]
		// NEED int playerNumberAttacked????????????????						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				/*
				 * 
				 * Picture of swords clanging together:
				 * 
				 * 
				 * swordsGraphic();
				 * 
				 */
				
				/*
				System.out.println("     /\\___________            ___________/\\");
				System.out
						.println("/|---||___________\\ CRITICAL /___________||---|\\");
				System.out
						.println("\\|---||___________/   MISS   \\___________||---|/");
				System.out.println("     \\/                                  \\/");
				for (int x = 0; x < 1; --x) {
				}
				*/
				
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[i] + ", you must roll to see if you hit yourself.");
				
				criticalMissAttack();
		
				return; // playerNumberAttacked?????????????????????
  	  	    }
		});
	}
	
	public int[] criticalHitMightyBlowDamage() {					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	
  	  	    	
		
				if (dodgeBlowSpell[playerNumberAttacked] > 0) {
					
					if (numberOfPlayers == 1) {
						
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] >= 12) {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> The computer does not dodge.");
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[i] + ", you roll twice for critical hit damage.");
							
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> Make your first roll...");
							
							/*
							 * 
							 * ROLL 6-SIDED DIE
							 * 
							 */
							
							/*
							Scanner input = new Scanner(System.in);
							input.nextLine();
							*/
							int resultOne = (int) ((Math.random() * 6) + 1);
							int attackDamageOne = resultOne;
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll a " + attackDamageOne + "!");
							
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> Make your second roll...");
							
							/*
							 * 
							 * ROLL 6-SIDED DIE
							 * 
							 */
							
							// input.nextLine();
							int resultTwo = (int) ((Math.random() * 6) + 1);
							int attackDamageTwo = resultTwo;
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll a " + attackDamageTwo + "!");
							
		
							int totalAttackDamage = (attackDamageOne + attackDamageTwo);
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[i] + ", you roll a total of " + totalAttackDamage	+ " for critical hit damage!");
							
					  		
					  		centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (totalAttackDamage * 2) + "!");
							
		
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (totalAttackDamage * 2);
							
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
					  	  	  		if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] == 0) {
										
										AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
									      
										alert.setTitle("The computer has been knocked unconscious!");
							  	    	/*
							  	    	alert.setMessage("something");
							  	    	*/	    	
								    	
								    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									    	public void onClick(DialogInterface dialog, int whichButton) {
									    		
									    		hideNavigation();									    		

									    		if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
													
									    			gameEngineHumanFirst2();    							
												}

									    		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
									    			
									    			turn();   							
												}												
									    	}
								    	});								    	
								    	alert.show();						
									}
				
									if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] < 0) {
										
										/*
										 * 
										 * Picture of one sword destroying another.
										 * 
										 * deathGraphic();
										 * 
										 */
										
										AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
									      
										alert.setTitle("The computer has been slain!");
							  	    	/*
							  	    	alert.setMessage("something");
							  	    	*/	    	
								    	
								    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									    	public void onClick(DialogInterface dialog, int whichButton) {
									    		
									    		hideNavigation();
									    		
									    		gameOverCheck();
									    	}
								    	});								    	
								    	alert.show();
										
										playerDeadYet[playerNumberAttacked] = "yes";										
									}
									
									else {									

										if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
											
							    			gameEngineHumanFirst2();    							
										}

										if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
							    			
							    			turn();   							
										}										
									}				  	  	  			
					  	  	  	}
				  	  	  	}, 2000);
				  	  	  	
							return;
							
						} else {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> The computer uses it's dodge.");					
		
							dodgeBlowSpell[playerNumberAttacked] = dodgeBlowSpell[playerNumberAttacked] - 1;
							
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {  	  		
	
					  	  	  		if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
										
						    			gameEngineHumanFirst2();    							
									}
	
									if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
						    			
						    			turn();   							
									}					  	  	  	
					  	  	  	}
				  	  	  	}, 2000);
							
							return;
						}
					}
					
					/*
					if (numberOfPlayers > 1) {
						System.out.println();
						System.out.print("Player " + (playerNumberAttacked + 1)
								+ ", do you want to dodge(Y/N)? ");
						String s = input.next();
						char selection = s.charAt(0);
						switch (selection) {
						case 'y':
						case 'Y':
							System.out.println();
							if (dodgeBlowSpell[playerNumberAttacked] < 1) {
								System.out
										.println("You have already used your Dodge Blow spell!");
								break;
							}
							dodgeBlowSpell[playerNumberAttacked] = dodgeBlowSpell[playerNumberAttacked] - 1;
							return dodgeBlowSpell;
						case 'n':
						case 'N':
							break;
						default:
							criticalHitMightyBlowDamage(i, playerNumberAttacked, gameOn);
						}
					}
					*/
				}
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[i] + ", you roll twice for critical hit damage.");
				
		  		centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> Make your first roll...");
				
		  		/*
				 * 
				 * ROLL 6-SIDED DIE
				 * 
				 */
		  		
		  		/*
				Scanner input = new Scanner(System.in);
				input.nextLine();
				*/
				int resultOne = (int) ((Math.random() * 6) + 1);
				int attackDamageOne = resultOne;
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> You roll a " + attackDamageOne + "!");
				
		  		
		  		centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> Make your second roll...");
				
		  		/*
				 * 
				 * ROLL 6-SIDED DIE
				 * 
				 */
		  		
				// input.nextLine();
				int resultTwo = (int) ((Math.random() * 6) + 1);
				int attackDamageTwo = resultTwo;
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> You roll a " + attackDamageTwo + "!");
				
		
				int totalAttackDamage = (attackDamageOne + attackDamageTwo);
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[i] + ", you roll a total of "	+ totalAttackDamage + " for critical hit damage!");
				
		  		
		  		centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (totalAttackDamage * 2) + "!");
				
		
		  		ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (totalAttackDamage * 2);
		  		
		  		
		  		final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {	  			
	  	  	  			
		  	  	  		if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] == 0) {
							
							if (numberOfPlayers == 1) {
								
								AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
							      
								alert.setTitle("The computer has been knocked unconscious!");
					  	    	/*
					  	    	alert.setMessage("something");
					  	    	*/	    	
						    	
						    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							    	public void onClick(DialogInterface dialog, int whichButton) {
							    		
							    		hideNavigation();						    		

							    		if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
											
							    			gameEngineHumanFirst2();    							
										}

										if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
							    			
							    			turn();   							
										}							
							    	}
						    	});						    	
						    	alert.show();				
							}
							/*
							if (numberOfPlayers > 1) {
								System.out.println("Player " + (playerNumberAttacked + 1)
										+ ", you have been knocked unconscious!");
							}			
							System.out.print("Press a key to continue... ");
							input.nextLine();
							*/
						}
				
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] < 0) {
							
							/*
							 * 
							 * Picture of one sword destroying another.
							 * 
							 * deathGraphic();
							 * 
							 */
							
							
							if (numberOfPlayers == 1) {
								
								AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
							      
								alert.setTitle("The computer has been slain!");
					  	    	/*
					  	    	alert.setMessage("something");
					  	    	*/	    	
						    	
						    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							    	public void onClick(DialogInterface dialog, int whichButton) {
							    		
							    		hideNavigation();
							    		
							    		gameOverCheck();
							    	}
						    	});						    	
						    	alert.show();				
							}
							/*
							if (numberOfPlayers > 1) {
								System.out.println("Player " + (playerNumberAttacked + 1)
										+ ", you have been slain!");
							}
							System.out.print("Press a key to continue... ");
							input.nextLine();
							*/
							
							playerDeadYet[playerNumberAttacked] = "yes";					
						}
						
						else {						

							if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
								
				    			gameEngineHumanFirst2();    							
							}

							if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
				    			
				    			turn();   							
							}
						}
		  	  	  	}
	  	  	  	}, 2000);				
  	  	    }
		});
		return ArrayOfHitPoints.hitpoints;
	}
	
	public int[] criticalHitDamage() {						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				if (dodgeBlowSpell[playerNumberAttacked] > 0) {
					
					if (numberOfPlayers == 1) {
						
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] >= 12) {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> The computer player does not dodge.");
							
					  		centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you roll twice for critical hit damage.");
							
		
					  		centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> Make your first roll...");
							
					  		/*
							 * 
							 * ROLL 6-SIDED DIE
							 * 
							 */			  		
					  		
					  		/*
							Scanner input = new Scanner(System.in);
							input.nextLine();
							*/
							int resultOne = (int) ((Math.random() * 6) + 1);
							int attackDamageOne = resultOne;
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> You roll a " + attackDamageOne + "!");
							
					  		
					  		centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> Make your second roll...");
							
					  		/*
							 * 
							 * ROLL 6-SIDED DIE
							 * 
							 */
					  		
							// input.nextLine();
							int resultTwo = (int) ((Math.random() * 6) + 1);
							int attackDamageTwo = resultTwo;
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> You roll a " + attackDamageTwo + "!");
							
		
							int totalAttackDamage = (attackDamageOne + attackDamageTwo);
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[i] + ", you roll a total of " + totalAttackDamage	+ " for critical hit damage!");
							
		
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked]	- totalAttackDamage;
							
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
							
									if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] == 0) {
										
										AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
									      
										alert.setTitle("The computer has been knocked unconscious!");
							  	    	/*
							  	    	alert.setMessage("something");
							  	    	*/	    	
								    	
								    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									    	public void onClick(DialogInterface dialog, int whichButton) {
									    		
									    		hideNavigation();
									    		
									    		if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
													
									    			gameEngineHumanFirst2();    							
												}

												if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
									    			
									    			turn();   							
												}
									    	}
								    	});								    	
								    	alert.show();						
									}
				
									if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] < 0) {
				
										/*
										 * 
										 * Picture of one sword destroying another.
										 * 
										 * deathGraphic();
										 * 
										 */
										
										AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
									      
										alert.setTitle("The computer has been slain!");
							  	    	/*
							  	    	alert.setMessage("something");
							  	    	*/	    	
								    	
								    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									    	public void onClick(DialogInterface dialog, int whichButton) {
									    		
									    		hideNavigation();
									    		
									    		gameOverCheck();
									    	}
								    	});								    	
								    	alert.show();						
										
										playerDeadYet[playerNumberAttacked] = "yes";										
									}
									
									else {									

										if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
											
							    			gameEngineHumanFirst2();    							
										}

										if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
							    			
							    			turn();   							
										}										
									}				  	  	  			
					  	  	  	}
				  	  	  	}, 2000);
				  	  	  	
							return;
							
						} else {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> The computer player uses it's dodge.");					
		
							dodgeBlowSpell[playerNumberAttacked] = dodgeBlowSpell[playerNumberAttacked] - 1;
							
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {  	  		
	
					  	  	  		if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
										
						    			gameEngineHumanFirst2();    							
									}
	
									if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
						    			
						    			turn();   							
									}					  	  	  	
					  	  	  	}
				  	  	  	}, 2000);
							
							return;
						}
					}
					
					/*
					if (numberOfPlayers > 1) {
						System.out.println();
						System.out.print("Player " + (playerNumberAttacked + 1)
								+ ", do you want to dodge(Y/N)? ");
						String s = input.next();
						char selection = s.charAt(0);
						switch (selection) {
						case 'y':
						case 'Y':
							System.out.println();
							if (dodgeBlowSpell[playerNumberAttacked] < 1) {
								System.out
										.println("You have already used your Dodge Blow spell!");
								break;
							}
							dodgeBlowSpell[playerNumberAttacked] = dodgeBlowSpell[playerNumberAttacked] - 1;
							return dodgeBlowSpell;
						case 'n':
						case 'N':
							break;
						default:
							criticalHitDamage(i, playerNumberAttacked, gameOn);
						}
					}
					*/
				}
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[i] + ", you roll twice for critical hit damage.");		
		
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> Make your first roll...");
				
		  		/*
				 * 
				 * ROLL 6-SIDED DIE
				 * 
				 */	
		  		
		  		/*
				Scanner input = new Scanner(System.in);
				input.nextLine();
				*/
				int resultOne = (int) ((Math.random() * 6) + 1);
				int attackDamageOne = resultOne;
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> You roll a " + attackDamageOne + "!");
				
		  		
		  		centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> Make your second roll...");
				
		  		/*
				 * 
				 * ROLL 6-SIDED DIE
				 * 
				 */	
		  		
				// input.nextLine();
				int resultTwo = (int) ((Math.random() * 6) + 1);
				int attackDamageTwo = resultTwo;
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> You roll a " + attackDamageTwo + "!");
				
		
				int totalAttackDamage = (attackDamageOne + attackDamageTwo);
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[i] + ", you roll a total of "	+ totalAttackDamage + " for critical hit damage!");
				
		
				ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked]	- totalAttackDamage;
				
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
				
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] == 0) {
							
							if (numberOfPlayers == 1) {
								
								AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
							      
								alert.setTitle("The computer has been knocked unconscious!");
					  	    	/*
					  	    	alert.setMessage("something");
					  	    	*/	    	
						    	
						    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							    	public void onClick(DialogInterface dialog, int whichButton) {
							    		
							    		hideNavigation();
							    		
							    		if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
											
							    			gameEngineHumanFirst2();    							
										}

										if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
							    			
							    			turn();   							
										}
							    	}
						    	});						    	
						    	alert.show();				
							}
							/*
							if (numberOfPlayers > 1) {
								System.out.println("Player " + (playerNumberAttacked + 1)
										+ ", you have been knocked unconscious!");
							}
							System.out.print("Press a key to continue... ");
							input.nextLine();
							*/
						}
				
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] < 0) {
							
							/*
							 * 
							 * Picture of one sword destroying another.
							 * 
							 * deathGraphic();
							 * 
							 */
							
							
							if (numberOfPlayers == 1) {
								
								AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
							      
								alert.setTitle("The computer has been slain!");
					  	    	/*
					  	    	alert.setMessage("something");
					  	    	*/	    	
						    	
						    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							    	public void onClick(DialogInterface dialog, int whichButton) {
							    		
							    		hideNavigation();
							    		
							    		gameOverCheck();
							    	}
						    	});
						    	
						    	alert.show();				
							}
							/*
							if (numberOfPlayers > 1) {
								System.out.println("Player " + (playerNumberAttacked + 1)
										+ ", you have been slain!");
							}
							System.out.print("Press a key to continue... ");
							input.nextLine();
							*/
							
							playerDeadYet[playerNumberAttacked] = "yes";							
						}
						
						else {						

							if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
								
				    			gameEngineHumanFirst2();    							
							}

							if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
				    			
				    			turn();   							
							}
						}
		  	  	  	}
	  	  	  	}, 2000);
  	  	    }
		});
		return ArrayOfHitPoints.hitpoints;
	}
	
	public void criticalMissAttack() {
		// playerCriticalMissAttacked = input.nextInt();						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> Press slide the die... ");
				
		  		/*
				 * 
				 * 
				 * SLIDE 20-SIDED DIE
				 * 
				 * 
				 */
		  		
		  		/*
				Scanner input = new Scanner(System.in);
				input.nextLine();
				*/
				int criticalMissAttackResult = (int) ((Math.random() * 20) + 1);
		
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> You roll a " + criticalMissAttackResult + "!");
				
				if (criticalMissAttackResult >= 17) {
					
					centerscrolltext.setVisibility(View.VISIBLE);
			  		centerscrolltext.startAnimation(animAlphaText);
			  		centerscrolltext.append("\n" + "> You hit yourself!");
					
					criticalMissDamage();
					
				} else {
					
					centerscrolltext.setVisibility(View.VISIBLE);
			  		centerscrolltext.startAnimation(animAlphaText);
			  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[i] + ", you did not hit yourself! But you must roll to see if you lose your weapon.");
					
			  		centerscrolltext.setVisibility(View.VISIBLE);
			  		centerscrolltext.startAnimation(animAlphaText);
			  		centerscrolltext.append("\n" + "> Press slide the die... ");
					
			  		/*
					 * 
					 * 
					 * SLIDE 20-SIDED DIE
					 * 
					 * 
					 */
			  		
					// input.nextLine();
					int criticalMissDisarmResult = (int) ((Math.random() * 20) + 1);
					
					centerscrolltext.setVisibility(View.VISIBLE);
			  		centerscrolltext.startAnimation(animAlphaText);
			  		centerscrolltext.append("\n" + "> You roll a " + criticalMissDisarmResult + "!");
					
					if (criticalMissDisarmResult >= 17) {
						
						canHasDisarmed[i] = "yes";
						disarmedTurnStart[i] = turn;
						
						centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
				  		centerscrolltext.append("\n" + "> You are disarmed!");
						
					} else {
						
						centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
				  		centerscrolltext.append("\n" + "> You hold on to your weapon!");				
					}
				}
  	  	    }
		});
	}
	
	public int[] criticalMissDamage() {						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[i] + ", roll for damage(to yourself)...");
				
		  		/*
				 * 
				 * ROLL 6-SIDED DIE
				 * 
				 */	
		  		
		  		/*
				Scanner input = new Scanner(System.in);
				input.nextLine();
				*/
				int result = (int) ((Math.random() * 6) + 1);
				int attackDamage = result;
				
				centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> You roll a " + attackDamage + "!");
				
		
		  		ArrayOfHitPoints.hitpoints[i] = ArrayOfHitPoints.hitpoints[i] - attackDamage;
		  		
		  		
		  		final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
		  		
						if (ArrayOfHitPoints.hitpoints[i] == 0) {
							
							AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
						      
							alert.setTitle(ArrayOfPlayers.player[i] + ", you have been knocked unconscious!");
				  	    	/*
				  	    	alert.setMessage("something");
				  	    	*/	    	
					    	
					    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int whichButton) {
						    		
						    		hideNavigation();
						    		
						    		if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
										
						    			gameEngineHumanFirst2();    							
									}

									if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
						    			
						    			turn();   							
									}
						    	}
					    	});					    	
					    	alert.show();			
						}
				
						if (ArrayOfHitPoints.hitpoints[i] < 0) {
				
							/*
							 * 
							 * Picture of one sword destroying another.
							 * 
							 * deathGraphic();
							 * 
							 */
							
							AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
						      
							alert.setTitle(ArrayOfPlayers.player[i] + ", you have been slain!");
				  	    	/*
				  	    	alert.setMessage("something");
				  	    	*/	    	
					    	
					    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int whichButton) {
						    		
						    		hideNavigation();
						    		
						    		gameOverCheck();
						    	}
					    	});					    	
					    	alert.show();
							
							playerDeadYet[i] = "yes";							
						}
						
						else {									

							if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
								
				    			gameEngineHumanFirst2();    							
							}

							if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
				    			
				    			turn();   							
							}										
						}				  	  	  			
		  	  	  	}
	  	  	  	}, 2000);
  	  	    }
		});
		return ArrayOfHitPoints.hitpoints;
	}
	
	public void disarm() { // WAS String[]					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				if (numberOfPlayers == 1) {
					
					if (blessSpell[0] > 0) {		
							
						AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
			  			
			  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your bless spell?");
			  	    	/*
			  	    	alert.setMessage("something");
			  	    	*/	  	    	
			  	    	
			  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			  	    		public void onClick(DialogInterface dialog, int whichButton) {
			  	    			
			  	    			hideNavigation();
			  		    		
			  		    		blessSpell[0] = blessSpell[0] - 1;			  		    				  		    		
			  		    		
			  		    		disarmWithBless();			  		    			  		    		
			  		    	}
			  	    	});
			  	    	
			  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			  	    		public void onClick(DialogInterface dialog, int whichButton) {
			  	    			
			  	    			hideNavigation();			  	    			
			  	    				  	    			
			  	    			disarmNoBless();			          		            		  
			          	  }
			          	});			  	    	
			  	    	alert.show();						
					}									
				}
				
				/*
				if (numberOfPlayers > 1) {
					if (blessSpell[i] > 0) {
						System.out.print("Player " + playerNumber[i]
								+ ", do you want to use your bless spell(Y/N)?");
						String s = input.next();
						char selection = s.charAt(0);
						switch (selection) {
						case 'y':
						case 'Y':
							if (numberOfPlayers == 1) {
								blessSpell[0] = blessSpell[0] - 1;
							}
							if (numberOfPlayers > 1) {
								blessSpell[i] = blessSpell[i] - 1;
							}
		
							blessGraphic();
		
							System.out
									.print("Player "
											+ playerNumber[i]
											+ ", who do you want to disarm? (Enter player number): ");
							playerNumberAttacked = input.nextInt();
							playerNumberAttacked = playerNumberAttacked - 1;
							// INPUT VALIDATION ...NEEDS
							// WORK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
							while (playerNumberAttacked < 0 || playerNumberAttacked > 5)// ||
																						// !input.hasNextInt());
							{
								System.out
										.print("Not valid entry!..Who do you want to attack? (Enter player number): ");
								playerNumberAttacked = input.nextInt();
							}
		
							System.out.println();
							System.out.print("Press a key to roll... ");
							Scanner input = new Scanner(System.in);
							input.nextLine();
							int attackResult = (int) ((Math.random() * 20) + 1);
		
							System.out.println();
							System.out.println("You roll a " + attackResult
									+ ", +2 for the Bless Spell = "
									+ (attackResult + 2));
							System.out.println();
		
							if (attackResult >= 15) {
								canHasDisarmed[playerNumberAttacked] = "yes";
		
								disarmedTurnStart[playerNumberAttacked] = turn;
		
								playerWhoDisarmed[playerNumberAttacked] = i;
		
								System.out.println("You have disarmed player "
										+ (playerNumberAttacked + 1) + "!");
								System.out.println();
								return canHasDisarmed;
							}
		
							if (attackResult <= 14 && attackResult > 0) {
								System.out.println("Your attempt to disarm misses!");
								System.out.println();
								return canHasDisarmed;
							}
							// don't critically miss when using bless.
							//
							// if (attackResult <= 1) { criticalMiss(i, turn,
							// playerNumberAttacked); return canHasDisarmed; }
							//
							break;
						case 'n':
						case 'N':
							System.out
									.print("Player "
											+ playerNumber[i]
											+ ", who do you want to disarm? (Enter player number): ");
							input = new Scanner(System.in);
							playerNumberAttacked = input.nextInt();
							playerNumberAttacked = playerNumberAttacked - 1;
							// INPUT VALIDATION ...NEEDS
							// WORK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!(try/catch??????)
							while (playerNumberAttacked < 0 || playerNumberAttacked > 5)// ||
																						// !input.hasNextInt());
							{
								System.out
										.print("Not valid entry!..Who do you want to attack? (Enter player number): ");
								playerNumberAttacked = input.nextInt();
							}
		
							System.out.println();
							System.out.print("Press a key to roll... ");
							input = new Scanner(System.in);
							input.nextLine();
							attackResult = (int) ((Math.random() * 20) + 1);
		
							System.out.println();
							System.out.println("You roll a " + attackResult + "!");
							System.out.println();
		
							if (attackResult >= 17) {
								canHasDisarmed[playerNumberAttacked] = "yes";
		
								disarmedTurnStart[playerNumberAttacked] = turn;
		
								playerWhoDisarmed[playerNumberAttacked] = i;
		
								System.out.println("You have disarmed player "
										+ (playerNumberAttacked + 1) + "!");
								System.out.println();
								return canHasDisarmed;
							}
		
							if (attackResult <= 16 && attackResult > 1) {
								System.out.println("Your attempt to disarm misses!");
								System.out.println();
								return canHasDisarmed;
							}
							if (attackResult <= 1) {
								criticalMiss(i, turn, playerNumberAttacked, gameOn);
								return canHasDisarmed;
							}
							break;
						default:
							disarm(i, turn, gameOn);
						}
					}
				}
				*/				
				
  	  	    }
		});
		// return canHasDisarmed;
	}
	
	public void disarmWithBless() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
  	  	    	
	  	  	    final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
  	  	    	
		  	  	    	/*
						 * 
						 * Picture of hands coming together.
						 * 
						 * blessGraphic();
						 * 
						 */  	    			
				
		  	  	  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			// ROLLFROMLEFT (20-SIDED)
			  	  	  			twentySidedRollFromLeft();
			  	  	  			
				  	  	  		final Handler h3 = new Handler();
					  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			// 20-SIDED WOBBLE START
					  	  	  			twentySidedWobbleStart();
					  	  	  			/*
					  	  	  			// need runOnUiThread?
					  	  	  			runOnUiThread(new Runnable() {
							  	  	  	    @Override
							  	  	  	    public void run() {
							  	  	  	    	
							  	  	  	    	twentySidedWobbleStart();						  	  	  	    	
								  	  	  	}
						  	  	  			});
					  	  	  			*/
					  	  	  			
						  	  	  		final Handler h4 = new Handler();
							  	  	  	h4.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
	  	  	  			
												centerscrolltext.setVisibility(View.VISIBLE);
										  		centerscrolltext.startAnimation(animAlphaText);
										  		centerscrolltext.append("\n" + "> Press slide the die... ");
										  		
										  		
										  		/*
												 * 
												 * 
												 * SLIDE 20-SIDED DIE
												 * (SEE ABOVE)
												 * 
												 * 
												 */
										  		
										  		attackResult = (int) ((Math.random() * 20) + 1);
												//int attackResult = (int) ((Math.random() * 20) + 1);
												
										  		/*
												centerscrolltext.setVisibility(View.VISIBLE);
										  		centerscrolltext.startAnimation(animAlphaText);
										  		centerscrolltext.append("\n" + "> You roll a " + attackResult	+ ", +2 for the Bless Spell = "	+ (attackResult + 2));
												
									
												if (attackResult >= 15) {
													
													canHasDisarmed[playerNumberAttacked] = "yes";
									
													disarmedTurnStart[playerNumberAttacked] = turn;
									
													// playerWhoDisarmed[playerNumberAttacked] = i;
													
													centerscrolltext.setVisibility(View.VISIBLE);
											  		centerscrolltext.startAnimation(animAlphaText);
											  		centerscrolltext.append("\n" + "> You have disarmed player " + (playerNumberAttacked + 1) + "!");
													
													//for (int x = 0; x < 1000; --x)// To give human player time to read.
													//{
													//}
													
													return;
												}
									
												if (attackResult <= 14 && attackResult > 0) {
													
													centerscrolltext.setVisibility(View.VISIBLE);
											  		centerscrolltext.startAnimation(animAlphaText);
											  		centerscrolltext.append("\n" + "> Your attempt to disarm misses!");
													
													//for (int x = 0; x < 1000; --x)// To give human player time to read.
													//{
													//}
													
													return;
												}*/
							  	  	  		}
							  	  	  	}, 2000);
					  	  	  		}
					  	  	  	}, 2000);
			  	  	  		}
			  	  	  	}, 2000);
	  	  	  		}
	  	  	  	}, 2000);
  	  	    }
		});
	}
	
	public void disarmWithBlessResults() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {						
										
						centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
				  		centerscrolltext.append("\n" + "> You roll a " + attackResult	+ ", +2 for the Bless Spell = "	+ (attackResult + 2));
						
			
						if (attackResult >= 15) {
							
							canHasDisarmed[playerNumberAttacked] = "yes";
			
							disarmedTurnStart[playerNumberAttacked] = turn;
			
							// playerWhoDisarmed[playerNumberAttacked] = i;
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> You have disarmed player " + (playerNumberAttacked + 1) + "!");
							/*
							for (int x = 0; x < 1000; --x)// To give human player time to read.
							{
							}
							*/
							return;
						}
			
						if (attackResult <= 14 && attackResult > 0) {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> Your attempt to disarm misses!");
							/*
							for (int x = 0; x < 1000; --x)// To give human player time to read.
							{
							}
							*/
							return;
						}
	  	  	  		}
	  	  	  	}, 2000);	  			
  	  	    }
		});
	}
	
	public void disarmNoBless() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			
	  			final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
  	  	    	
		  	  	    	/*
						 * 
						 * Picture of hands coming together.
						 * 
						 * blessGraphic();
						 * 
						 */  	    			
				
		  	  	  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			// ROLLFROMLEFT (20-SIDED)
			  	  	  			twentySidedRollFromLeft();
			  	  	  			
				  	  	  		final Handler h3 = new Handler();
					  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			// 20-SIDED WOBBLE START
					  	  	  			twentySidedWobbleStart();
					  	  	  			/*
					  	  	  			// need runOnUiThread?
					  	  	  			runOnUiThread(new Runnable() {
							  	  	  	    @Override
							  	  	  	    public void run() {
							  	  	  	    	
							  	  	  	    	twentySidedWobbleStart();						  	  	  	    	
								  	  	  	}
						  	  	  			});
					  	  	  			*/
					  	  	  			
						  	  	  		final Handler h4 = new Handler();
							  	  	  	h4.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
	  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
									  		centerscrolltext.append("\n" + "> Press slide the die... ");
									  		
									  		
									  		/*
											 * 
											 * 
											 * SLIDE 20-SIDED DIE
											 * 
											 * 
											 */
									  		
											
											attackResult = (int) ((Math.random() * 20) + 1);
											
							  	  	  		}
							  	  	  	}, 2000);
					  	  	  		}
					  	  	  	}, 2000);
			  	  	  		}
			  	  	  	}, 2000);
	  	  	  		}
	  	  	  	}, 2000);				
  	  	    }
		});		
	}
	
	public void disarmNoBlessResults() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
				  		centerscrolltext.append("\n" + "> You roll a " + attackResult + "!");
						
			
						if (attackResult >= 17) {
							
							canHasDisarmed[playerNumberAttacked] = "yes";
			
							disarmedTurnStart[playerNumberAttacked] = turn;
			
							// playerWhoDisarmed[playerNumberAttacked] = i;
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> You have disarmed player " + (playerNumberAttacked + 1) + "!");
							
							return;
						}
			
						if (attackResult <= 16 && attackResult > 1) {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> Your attempt to disarm misses!");
							
							return;
						}
						if (attackResult <= 1) {
							
							criticalMiss();
							
							return;
						}
	  	  	  		}
	  	  	  	}, 2000);	  			
  	  	    }
		});
	}
	
	public int[] haste() { // no bless because you can't use 2 spells in one turn.						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				if (hasteSpell[i] > 0) {
					
					if (numberOfPlayers == 1) {
						hasteSpell[0] = hasteSpell[0] - 1;
					}
					/*
					if (numberOfPlayers > 1) {
						hasteSpell[i] = hasteSpell[i] - 1;
					}
					*/
					
					
					/*
					 * 
					 * Picture of swords clanging together:
					 * 
					 * 
					 * swordsGraphic();
					 * 
					 */
					
					/*
					swordsGraphic();
					System.out.println("     /\\___________           ___________/\\");
					System.out
							.println("/|---||___________\\   TWO   /___________||---|\\");
					System.out
							.println("\\|---||___________/ ATTACKS \\___________||---|/");
					System.out.println("     \\/                                 \\/");
					for (int x = 0; x < 1; --x) {
					}
					
					System.out.println();
					System.out.println("     /\\____________");
					System.out.println("/|---||_1st Attack_\\");
					System.out.println("\\|---||____________/");
					System.out.println("     \\/           ");
					System.out.println();
					*/
					
					attack();
		
					if (canHasDisarmed[i] == "yes")// so if you critically miss & drop weapon you don't get 2nd attack.
					{
						return;
					}
					// else if(canHasDisarmed[i] == "no") DON'T NEED THIS?????
					
					/*
					System.out.println("     /\\____________");
					System.out.println("/|---||_2nd Attack_\\");
					System.out.println("\\|---||____________/");
					System.out.println("     \\/           ");
					System.out.println();
					*/
					
					attack();
		
				} else {
					
					centerscrolltext.setVisibility(View.VISIBLE);
			  		centerscrolltext.startAnimation(animAlphaText);
			  		centerscrolltext.append("\n" + "> You have already used your Haste spell!");					
			  		
			  		
			  		/*
					 * 
					 * THIS LETS HUMAN PLAYER CHOOSE WHAT THEY WANT TO DO (ACTION/ATTACK)
					 * 
					 * Bring Action To Front?
					 * 
					 * action(i, turn, gameOn);
					 * 
					 */					
			  		if (isInvokingService.equals("true")){
						//NEED THIS?
						SystemClock.sleep(1000);	        		
							
						runActionsOnUi();
					}
				}
  	  	    }
		});
		return hasteSpell;
	}
	
	public int bless() {						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				if (blessSpell[i] > 0) {
					
					if (numberOfPlayers == 1) {
						
						blessSpell[0] = blessSpell[0] - 1;
						
						/*
						 * 
						 * Picture of hands coming together.
						 * 
						 * blessGraphic();
						 * 
						 */
						
						//blessGraphic();
		
						centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
				  		centerscrolltext.append("\n" + "> Press slide the die... ");		  		
						
						int attackResult = (int) ((Math.random() * 20) + 1);
						
						centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
				  		centerscrolltext.append("\n" + "> You roll a " + attackResult	+ ", +2 for the Bless Spell = " + (attackResult + 2));
						
		
						if (attackResult >= 20) {
							criticalHit();
							return;
						}
						if (attackResult >= 12 && attackResult <= 19) {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> Your attack hits!");
							
							if (mightyBlowSpell[0] > 0) {
								
								AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
					  			
					  	    	alert.setTitle(ArrayOfPlayers.player[i] + ", do you want to use Mighty Blow?");
					  	    	/*
					  	    	alert.setMessage("something");
					  	    	*/	  	    	
					  	    	
					  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
					  		    		
					  		    		/*
										 * 
										 * Picture of swords clanging together:
										 * 
										 * 
										 * swordsGraphic();
										 * 
										 */	  		    		
					  		    		
					  		    		/*
					  		    		swordsGraphic();
										System.out
												.println("     /\\___________          ___________/\\");
										System.out
												.println("/|---||___________\\ MIGHTY /___________||---|\\");
										System.out
												.println("\\|---||___________/  BLOW  \\___________||---|/");
										System.out
												.println("     \\/                                \\/");
										for (int x = 0; x < 1; --x) {
										}
					  		    		*/
					  		    		
					  		    		
					  		    		hideNavigation();
					  		    		
					  		    		mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
					  		    		
					  		    		mightyBlow();
										return;	  		    		
					  		    	}
					  	    	});
					  	    	
					  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
					          	  public void onClick(DialogInterface dialog, int whichButton) {
					          		
					          		hideNavigation(); 
					          		  
					          		damage();
									return;
					          	  }
					          	});	  	    	
					  	    	
					  	    	alert.show();						
								
							}
							damage();
							return;
						}
						if (attackResult < 12 && attackResult > 0) {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> Your attack misses!");					
						}
						// don't critically miss when using bless.
						/*
						 * if (attackResult <= 1) { criticalMiss(i, turn,
						 * playerNumberAttacked); return playerNumberAttacked; }
						 */
					}
					/*
					if (numberOfPlayers > 1) {
						blessSpell[i] = blessSpell[i] - 1;
		
						blessGraphic();
						// INPUT VALIDATION ...NEEDS
						// WORK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
						System.out
								.print("Player "
										+ playerNumber[i]
										+ ", who do you want to attack? (Enter player number): ");
						playerNumberAttacked = input.nextInt();
						playerNumberAttacked = playerNumberAttacked - 1;
		
						while (playerNumberAttacked < 0 || playerNumberAttacked > 5)// ||
																					// !input.hasNextInt());
						{
							System.out
									.print("Not valid entry!..Who do you want to attack? (Enter player number): ");
							playerNumberAttacked = input.nextInt();
						}
		
						System.out.println();
						System.out.print("Press a key to roll... ");
						Scanner input = new Scanner(System.in);
						input.nextLine();
						int attackResult = (int) ((Math.random() * 20) + 1);
		
						System.out.println();
						System.out.println("You roll a " + attackResult
								+ ", +2 for the Bless Spell = " + (attackResult + 2));
						System.out.println();
		
						if (attackResult >= 20) {
							criticalHit(i, playerNumberAttacked, gameOn);
							return playerNumberAttacked;
						}
						if (attackResult >= 12 && attackResult <= 19) {
							System.out.println("Your attack hits!");
							System.out.println();
							if (mightyBlowSpell[i] > 0) {
								System.out.print("Player " + playerNumber[i]
										+ ", do you want to use Mighty Blow(Y/N)? ");
								String s = input.next();
								char selection = s.charAt(0);
								switch (selection) {
								case 'y':
								case 'Y':
									mightyBlowSpell[i] = mightyBlowSpell[i] - 1;
									System.out.println();
		
									swordsGraphic();
									System.out
											.println("     /\\___________          ___________/\\");
									System.out
											.println("/|---||___________\\ MIGHTY /___________||---|\\");
									System.out
											.println("\\|---||___________/  BLOW  \\___________||---|/");
									System.out
											.println("     \\/                                \\/");
									for (int x = 0; x < 1; --x) {
									}
		
									mightyBlow(i, playerNumberAttacked, gameOn);
									return playerNumberAttacked;
								case 'n':
								case 'N':
									damage(i, playerNumberAttacked, gameOn);
									return playerNumberAttacked;
								default:
									damage(i, playerNumberAttacked, gameOn);
								}
							}
							damage(i, playerNumberAttacked, gameOn);
							return playerNumberAttacked;
						}
						if (attackResult < 12 && attackResult > 0) {
							System.out.println("Your attack misses!");
							System.out.println();
						}
						// don't critically miss when using bless.
						//
						// if (attackResult <= 1) { criticalMiss(i, turn,
						// playerNumberAttacked); return playerNumberAttacked; }
						//
					}
					*/
				} else {
					
					centerscrolltext.setVisibility(View.VISIBLE);
			  		centerscrolltext.startAnimation(animAlphaText);
			  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[i] + ", you have already used your bless spell!");
								  		
			  		
			  		/*
					 * 
					 * THIS LETS HUMAN PLAYER CHOOSE WHAT THEY WANT TO DO (ACTION/ATTACK)
					 * 
					 * Bring Action To Front?
					 * 
					 * action(i, turn, gameOn);
					 * 
					 */			  		
			  		if (isInvokingService.equals("true")){
						//NEED THIS?
						SystemClock.sleep(1000);	        		
							
						runActionsOnUi();
					}
				}
  	  	    }
		});
		return playerNumberAttacked;
	}
	
	
	
	
	
	
	
	
	
	public void gameEngine() {		
		
		if (numberOfPlayers == 1) {
			
			playerNumberAttacked = 1;

			for (int a = 0; a < 2; a++) {
				playerDeadYet[a] = "no";
			}
		
		    //Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		    
		    final int gameOn = 1;
		    //final int turn = 1;
		    
		    
		    if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {
		    	
		    	// NEED ARRAY HERE?
		    	usedHaste[0] = "no";// so computer doesn't use a haste during a haste.		    	
		    }
		    
		    
		    turn();
		}
		
		if (numberOfPlayers > 1) {			
			
			//WHAT TO DO????????
		}					    
		    
		//return; // NEEDED? MAY HELP STOP THREAD? THREAD SHOULD STOP ON ITS OWN AFTER RUN IS COMPLETED.
	}
	
	public void turn() {
		
		if (numberOfPlayers == 1) {
			
			if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {
				
				int i = 0;
        		
    			gameEngineHumanFirst1();
    			
    			turn++;				
			}
			
			if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {
				
				int i = 1;
    			
    			gameEngineComputerFirst1();	
				
    			turn++;				
			}
			
		}
		
		if (numberOfPlayers > 1) {			
			
			//WHAT TO DO????????
		}
	}
	
	public void gameEngineHumanFirst1() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);  			
	  			
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> Turn " + turn);
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		if (ArrayOfHitPoints.hitpoints[0] <= 0) {
							endGame(); // took out map
						}
			  	  		
			  	  		else if (canHasDisarmed[0] == "yes") {
							
							/*
							for (int x = 0; x < 1000; --x) // To give human player time to read.
							{
							}
							*/
							
							
							/*
							 * NEED GRAPHICS HERE:
							 * 
							 * disarmGraphic();
							 */
							
							
							// following 3 steps will be followed for any given number of players.
							if (disarmedTurnStart[0] == (turn)) {
								//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
								disarmedAction();
							} else if (disarmedTurnStart[0] + 1 == turn) {
								//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
								disarmedAction();
							} else if (disarmedTurnStart[0] + 2 == turn) {
								//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
								disarmedAction();
								canHasDisarmed[0] = "no";
							}
						}
			  	  		
			  	  		else {
							
							/*
							 * 
							 * THIS LETS HUMAN PLAYER CHOOSE WHAT THEY WANT TO DO (ACTION/ATTACK)
							 * 
							 * Bring Action To Front?
							 * 
							 * action(i, turn, gameOn);
							 * 
							 */
			  	  			
							if (isInvokingService.equals("true")){
								//NEED THIS?
								SystemClock.sleep(1000);	        		
									
								runActionsOnUi();
							}
			  	  		}
		  	  	  	}
	  	  	  	}, 1000);				
  	  	    }
		});
	}
	
	public void gameEngineHumanFirst2() {		
		
		i = 1;
		usedHaste[0] = "no";// so computer doesn't use a haste during a haste.

		if (ArrayOfHitPoints.hitpoints[1] <= 0) {
			endGame(); // took out map
		}
		
		else if (canHasDisarmed[1] == "yes") {
			
			
			/*
			 * NEED GRAPHICS HERE:
			 * 
			 * disarmGraphic();
			 */
			
			
			// player number whose turn it is is less than the player
			// number of the player who disarmed him.

			if (disarmedTurnStart[1] == (turn)) {
				//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
				computerDisarmedAction();
			} else if (disarmedTurnStart[1] + 1 == turn) {
				//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
				computerDisarmedAction();
			} else if (disarmedTurnStart[1] + 2 == turn) {
				//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
				computerDisarmedAction();
				canHasDisarmed[1] = "no";
			}
		}
		
		else {
			
			
			//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
			
			
			/*
			 * 
			 * Bring Action To Front?
			 * 
			 * actionTemplate();
			 * 
			 */
			
			
			int computerAction = (int) (Math.random() * 100) + 1;
			computerAttack();					
		}
	}
	
	public void gameEngineComputerFirst1() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);  			
	  			
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> Turn " + turn);
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		if (ArrayOfHitPoints.hitpoints[1] <= 0) {
							endGame(); // took out map
						}
			  	  		
			  	  		else if (canHasDisarmed[1] == "yes") {
							
							
							/*
							 * NEED GRAPHICS HERE:
							 * 
							 * disarmGraphic();
							 */
							
							
							if (disarmedTurnStart[1] == (turn)) {
								//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
								computerDisarmedAction();
							} else if (disarmedTurnStart[1] + 1 == turn) {
								//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
								computerDisarmedAction();
							} else if (disarmedTurnStart[1] + 2 == turn) {
								//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
								computerDisarmedAction();
								canHasDisarmed[1] = "no";
							}
						}
			  	  		
			  	  		else {
							
							
							//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
							
							
							/*
							 * 
							 * Bring Action & Attack To Front?
							 * 
							 * actionTemplate();
							 * 
							 */
							
							
							int computerAction = (int) (Math.random() * 100) + 1;
							computerAttack();					
						}
		  	  	  	}
	  	  	  	}, 2000);
  	  	    }
		});
	}
	
	public void gameEngineComputerFirst2() {	
		
		i = 0;
		
		if (ArrayOfHitPoints.hitpoints[0] <= 0) {
			endGame(); // took out map
		}
		
		else if (canHasDisarmed[0] == "yes") {
			
			/*
			for (int x = 0; x < 1000; --x)// To give human player time
											// to read.
			{
			}
			*/
			
			
			/*
			 * NEED GRAPHICS HERE:
			 * 
			 * disarmGraphic();
			 */
			
			
			// player number whose turn it is is less than the player
			// number of the player who disarmed him.

			if (disarmedTurnStart[0] == (turn)) {
				//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
				disarmedAction();
			} else if (disarmedTurnStart[0] + 1 == turn) {
				//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
				disarmedAction();
			} else if (disarmedTurnStart[0] + 2 == turn) {
				//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
				disarmedAction();
				canHasDisarmed[0] = "no";
			}
		}
		
		else {							
			
			/*
			 * 
			 * THIS LETS HUMAN PLAYER CHOOSE WHAT THEY WANT TO DO (ACTION/ATTACK)
			 * 
			 * Bring Action To Front?
			 * 
			 * action(i, turn, gameOn);
			 * 
			 */							
			if (isInvokingService.equals("true")){
				//NEED THIS?
				SystemClock.sleep(1000);	        		
					
				runActionsOnUi();	
			}
		}
	}
	
	public void runActionsOnUi() {		
		
		isInvokingService = "false";
		
		runOnUiThread(new Runnable() {
	  	  	    @Override
	  	  	    public void run() {	  	  	    	
	  	  	    	
		  	  	    final String[] items = new String[] { "Attack", "Disarm", "Haste", "Cure", "Bless" };
		      		
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
	
					// if back pressed: DOES THIS WORK????????????
					builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {
							
							//GOTO SOME METHOD!!!!!!!!!!!!!!
							
							runActionsOnUi();
						}
					});
	
					builder.setTitle("Choose Action").setItems(items,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,	int item) {
	
									if (item == 0) {
										hideNavigation();
										//isInvokingService = "true";
										attack();
									}
									if (item == 1) {
										hideNavigation();
										//isInvokingService = "true";
										disarm();
									}
									if (item == 2) {
										hideNavigation();
										//isInvokingService = "true";
										haste();
									}
									if (item == 3) {
										hideNavigation();
										//isInvokingService = "true";
										cure();
									}
									if (item == 4) {
										hideNavigation();
										//isInvokingService = "true";
										bless();
									}
									
									isInvokingService = "true";
								}
							});
					builder.create().show();	  	  	    	
	  	  	    }
		});
	}
	
	

}