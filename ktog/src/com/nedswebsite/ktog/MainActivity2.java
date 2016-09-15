package com.nedswebsite.ktog;

import java.util.Arrays;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;

public class MainActivity2 extends ActionBarActivity {
	
	int playerNumberAttacked;
	//int i;
	int turn = 1;
	int gameOn;
	int computerAction;
	//int gameOn = 1;	
	//int turn;
	
	int max = 0;
	//int numberOfPlayers = 2;
	
	static int numberOfPlayers = 1; // NEED THIS???????????
	
	//static int playerNumberAttacked;
	
	// NEEDS TO BE GLOBAL??
	//int attackResult;
	//int attackDamage;
	//int criticalHitAttackDamageOne;
	//int criticalHitAttackDamageTwo;
	//int cureResult;
	
	//int computerAttackResultAgainstDisarmed;
	
	int computerAttackDamageDisarmed;
	public static int[] attackDamageOneDisarmed = new int[1];
	public static int[] attackDamageTwoDisarmed = new int[1];
	
	
	//int turnhumandisarmedcomputer; ========= disarmedTurnStart[1] = turn;
	//int turncomputercritmiss;=====disarmedTurnStart[0] = turn;
	//int turncomputerdisarmedhuman; ========= disarmedTurnStart[0] = turn;
	//int turnhumancritmiss;========disarmedTurnStart[1] = turn;	
	
	public static int[] disarmedTurnStart = new int[6];
	String didHumanCriticalMiss = "no";
	String didComputerCriticalMiss = "no";
	
	
	// SOME OF THESE MAY NEED TO BE AN ARRAY-CLASS:
	public static int[] blessSpell = new int[] {1, 1, 1, 1, 1, 1, 1};
	public static int[] cureSpell = new int[] {1, 1, 1, 1, 1, 1, 1};
	public static int[] dodgeBlowSpell = new int[] {0, 0, 1, 1, 1, 1, 1};
	public static int[] mightyBlowSpell = new int[] {0, 0, 1, 1, 1, 1, 1};
	public static int[] hasteSpell = new int[] {2, 2, 2, 2, 2, 2, 2};	
	
	
	
	
	//IS THIS WORKING NOW (W NEW onbackpressed CODE)????????????
	// Using variable because was getting null pointer if onbackpressed before rollfromleft was completed:
	String onBackPressedOk = "no";
	
	String isInvokingService = "true";	
	
	String isinitiativestarted = "no";	
	static String isinitiativestartedinterrupted = "no";
	String issixsidedrolledforinitiative = "no";
	String aretheredoubles = "yes";	
	
	
	//String preventinitiativediefromleaking = "on";	
	String preventattackdamagediefromleaking = "on";
	String preventcureresultdiefromleaking = "on";	
	
	
	// ARRAYS?: (COMBINE W COMP BELOW?)
	String isattackrolled = "no";
	String isdisarmwithblessrolled = "no";
	String isdisarmnoblessrolled = "no";	
	String isblessrolled = "no";	
	String ishasteused = "no";
	String issecondroundofhasteused = "no";
	String iscurerolled = "no";
	String isattackdamagerolled = "no";
	String ismightyblowdamagerolled = "no";
	String iscriticalmissrolled = "no";
	String iscriticalmissloseweaponrolled = "no";
	String iscriticalmissdamagerolled = "no";
	String iscriticalhitfirstrollrolled = "no";
	String iscriticalhitsecondrollrolled = "no";
	String iscriticalhitmightyblowfirstrollrolled = "no";
	String iscriticalhitmightyblowsecondrollrolled = "no";
	
	// ARRAYS?: (COMBINE W HUMIE ABOVE?)
	//public static String[] usedHaste = new String[] {"no"};//FOR COMP. ONLY: so computer doesn't use a haste during a haste.
	String iscomputerhasteused = "no";
	String iscomputerblessrolled = "no";
	
	String startGameNow ="no";	
	
	
	// SOME OF THESE MAY NEED TO BE AN ARRAY-CLASS:
	public static String[] playerDeadYet = new String[] {"yes", "yes", "yes", "yes", "yes", "yes"}; // NEED 6?????????	
	//String playerDeadYet[] = {"yes", "yes", "yes", "yes", "yes", "yes"};
	public static String[] canHasDisarmed = new String[] {"no", "no", "no", "no", "no", "no"}; // NEED 6?????????		
	
	//FOR onResume
	String isSixSidedReadyToBeRolled = "no";
	String isTwentySidedReadyToBeRolled = "no";
	String isInitiativeOver = "yes";
	String istitlerulesopen = "no";
	
	
	//Dialog mydialog = null;
	String endGameAfterFirstHaste = "no";
	
	
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		// USED THE FOLLOWING TO REMOVE TITLE BAR:
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// This will hide the system bar until user swipes up from bottom or down from top.		
		//getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		
		setContentView(R.layout.activity_main_activity2);		
		// For the little space between the action & attack button.
		getWindow().getDecorView().setBackgroundColor(Color.BLACK);		
		
		
		
		
		//Globals g = Globals.getInstance();
		//g.setTest(BuildConfig.VERSION_CODE);
		
		
		
		
		// Sound stuff:
		/*
    	final MediaPlayer activityOpeningSound = MediaPlayer.create(MainActivity2.this, R.raw.buttonsound6);
		activityOpeningSound.start();
		*/
    	MediaPlayerWrapper.play(MainActivity2.this, R.raw.buttonsound6);    	
    			
		
		
		// Crashes if this is put up top.
		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
		
		
		TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
		playerNameTextView.setTypeface(typeFace);		
		playerNameTextView.setText(ArrayOfPlayers.player[0]);		
		
		TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
		computerNameTextView.setTypeface(typeFace);
		computerNameTextView.setText(ArrayOfPlayers.player[1]);
		
		
		ArrayOfHitPoints.hitpoints[0] = 1;		
		final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
		playerHitPointsTextView.setTypeface(typeFace);
		playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));		
		
		ArrayOfHitPoints.hitpoints[1] = 1;
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
		
		
		ImageView blessLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftbless);
		blessLeft.setVisibility(View.INVISIBLE);
		ImageView cureLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftcure);
		cureLeft.setVisibility(View.INVISIBLE);
		ImageView dodgeLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftdodge);
		dodgeLeft.setVisibility(View.INVISIBLE);
		ImageView mbLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftmb);
		mbLeft.setVisibility(View.INVISIBLE);
		ImageView hasteLeft1 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste1);
		hasteLeft1.setVisibility(View.INVISIBLE);
		ImageView hasteLeft2 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste2);
		hasteLeft2.setVisibility(View.INVISIBLE);
		
		ImageView blessRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightbless);
		blessRight.setVisibility(View.INVISIBLE);
		ImageView cureRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightcure);
		cureRight.setVisibility(View.INVISIBLE);
		ImageView dodgeRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightdodge);
		dodgeRight.setVisibility(View.INVISIBLE);
		ImageView mbRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightmb);
		mbRight.setVisibility(View.INVISIBLE);
		ImageView hasteRight1 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste1);
		hasteRight1.setVisibility(View.INVISIBLE);
		ImageView hasteRight2 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste2);
		hasteRight2.setVisibility(View.INVISIBLE);
		
		
		
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
		
		
		
		
		final TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);		
		
		titletext.setTypeface(typeFace);
		
		titletext.setVisibility(View.VISIBLE);				  		
		titletext.append("KtOG");
		
		
		final TextView titleinitiativetext = (TextView) findViewById(R.id.textviewtitleinitiativetext);
		titleinitiativetext.setVisibility(View.INVISIBLE);
		
		
		
		
		// THESE RUN METHODS ARE THREAD-SAFE, SUPPOSEDLY.
		final Handler h3 = new Handler(); // A handler is associated with the thread it is created in.
  	  	h3.postDelayed(new Runnable() {

  	  		@Override
  	  		public void run()
  	  		{  	  			
  	  			centerscrolltext.setVisibility(View.VISIBLE);
  	  			centerscrolltext.startAnimation(animAlphaText);
	  			centerscrolltext.append("> Welcome, " + ArrayOfPlayers.player[0] + ".");  	  	  				  	  	  			
	  	  	  			
	  	  	  			
  	  	  		final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
	  	  	  			titletext.setVisibility(View.INVISIBLE);
	  	  	  			
		  	  	  		//Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		  	  	  		titleinitiativetext.setTypeface(typeFace);
	  		  			
		  				//titletext.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(100));
		  					
		  	  	  		titleinitiativetext.setVisibility(View.VISIBLE);				  		
		  	  	  		titleinitiativetext.append("Initiative");
		  				
		  				
		  				final Handler h4 = new Handler();
			  	  	  	h4.postDelayed(new Runnable() {
			  	  	  		
			  	  	  		@Override
			  	  	  		public void run() {				  				
			  	  	  			
			  	  	  			// Sets sixSidedBlank visible & enabled.
			  	  	  			sixSidedRollFromLeft();  	  	  			
			  		  			
			  	  	  			
				  		  		final Handler h5 = new Handler();
					  	  	  	h5.postDelayed(new Runnable() {
					  	  	  			
					  	  	  			// Does this thread help:?
						  	  	  		@Override
						  	  	  		public void run()
						  	  	  		{  	  			
							  	  	  		sixSidedWobbleStart();
							  	  	  		
							  	  	  		isSixSidedReadyToBeRolled = "yes";
							  	  	  		isInitiativeOver = "no";
							  	  	  		
						  	  	  			centerscrolltext.setVisibility(View.VISIBLE);
						  		  	  		centerscrolltext.startAnimation(animAlphaText);
						  		  			centerscrolltext.append("\n" + "> Please slide the die...");								  		  			
						  		  			
						  		  			playerCardStartFadeInFadeOut();
						  		  			//playerTurnBackgroundStart();			  		  			
						  		  			
						  		  			
						  		  			//issixsidedrolledforinitiative = "yes";
						  		  			isinitiativestarted = "yes";			  		  			
						  		  			onBackPressedOk = "yes";
						  		  			
						  		  			
						  		  			//preventinitiativediefromleaking = "off";
					  	  	  		}
					  	  	  	}, 750);
			  	  	  		}
			  	  	  	}, 2000);
		  	  	  	}
	  	  	  	}, 2800);//MAINLY FINAGLING TO GET RIGHT - should be 4700?	  	  		  			
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
            	
            	//SystemClock.sleep(1000);
            	
            	if (isinitiativestarted.equals("no")) {
            		
            		//myInitiativeNotStarted();
            		
            		TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);	  					
    	  			
    	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
    	  			titletext.setTypeface(typeFace);
    	  			
    	  			//titletext.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(100));
    	  			
    	  			titletext.setVisibility(View.VISIBLE);    	  						  		
    	  			titletext.append("KtOG");
            		
            		
            	}
            	
            	else if (isinitiativestarted.equals("yes") && aretheredoubles.equals("no")) {
            		
            		if (isinitiativestartedinterrupted.equals("no")) {
            			
            			isinitiativestartedinterrupted = "yes";
            			
            			istitlerulesopen = "yes";
                		
                		myInitiativeIsStarted();
                		
                		titleBlankButton.setEnabled(false);// HERE & BELOW BECUSE GETTING WEIRD BEHAVIOR WHEN BUTTON WAS HIT AN ODD NUMBER OF TIMES (EXCEPT THE FIRST TIME).
                		
                		
                		final Handler h = new Handler();
    		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
    		  	  	  			
    		  	  	  		@Override
    			  	  	  	public void run() {
    		  	  	  			
	    		  	  	  		final TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);    			  	  		    						
	    							
		    		  	  	  	titletext.setVisibility(View.INVISIBLE);
			  	  	  			
	    		  	  	  		final TextView titlerulestext = (TextView) findViewById(R.id.textviewtitlerulestext);	
	    							
	    			  	  		titlerulestext.setVisibility(View.VISIBLE);
	    			  	  		
	    			  	  		
		    			  	  	final Handler h = new Handler();
		    		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		    		  	  	  			
		    		  	  	  		@Override
		    			  	  	  	public void run() {
		    		  	  	  			
			    		  	  	  		titlerulestext.setVisibility(View.INVISIBLE);
		    		  	  	  			
			    		  	  	  		final TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);    			  	  		    						
		    							
			    			  	  		titletext.setVisibility(View.VISIBLE);
			    			  	  		
			    			  	  		titleBlankButton.setEnabled(true);
			    			  	  		
			    			  	  		istitlerulesopen = "no";
		    			  	  	  	}
		    		  	  	  	}, 10975);
    			  	  	  	}
    		  	  	  	}, 600);
            		}
            		/*
            		else if (isinitiativestartedinterrupted.equals("yes")) {
            			
            			isinitiativestartedinterrupted = "no";
            			
            			myInitiativeIsStartedTitleInterrupt();
            			
            			
            			final TextView titlerulestext = (TextView) findViewById(R.id.textviewtitlerulestext);	
						
			  	  		titlerulestext.setVisibility(View.VISIBLE);			  	  		
	    			  	  		
	    			  	  		
    			  	  	final Handler h = new Handler();
    		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
    		  	  	  			
    		  	  	  		@Override
    			  	  	  	public void run() {
    		  	  	  			
	    		  	  	  		titlerulestext.setVisibility(View.INVISIBLE);
    		  	  	  			
	    		  	  	  		TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);    			  	  		    						
    							
	    			  	  		titletext.setVisibility(View.VISIBLE);		    			  	  	
    			  	  	  	}
    		  	  	  	}, 525);
    			  	  	  	
            		}
            		*/            				
            	}            	
			}            
		});		
				
		
		// USE android:background="@drawable/(SOME PNG)" TO SPECIFY AREA ON SCREEN ??
		sixSidedBlank.setOnTouchListener(new OnSixSidedSwipeTouchListener(MainActivity2.this) {
			
		    /*
			public void onSwipeTop() {
		        Toast.makeText(MainActivity2.this, "top", Toast.LENGTH_SHORT).show();		        
		    }
		    */			
			
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
			//SharedPreferences.Editor editor = preferences.edit();			
			
			
		    public void onSwipeRight() {
		    	/*
		    	Toast.makeText(MainActivity2.this, "INITIATIVE ROLLED = " + issixsidedrolledforinitiative, Toast.LENGTH_SHORT).show();											  	  	  			
				Toast.makeText(MainActivity2.this, "DAMAGE ROLLED = " + isattackdamagerolled, Toast.LENGTH_SHORT).show();
				int attackDamage = preferences.getInt("attackDamage", 0);
				Toast.makeText(MainActivity2.this, "DAMAGE = " + attackDamage, Toast.LENGTH_SHORT).show();
		    	*/
		    	//if (issixsidedrolledforinitiative.equals("yes")) {	  				
					
				sixSidedWobbleStop();				
				
				//sixSidedRollFromCenterToRight();
				//determineInitiative();				
				
				
				if (issixsidedrolledforinitiative.equals("no")) {
				
					if (ArrayOfInitiative.initiative[0] == 1) {
						
						sixSidedRollFromCenterToRight1();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[0] == 2) {
						
						sixSidedRollFromCenterToRight2();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[0] == 3) {
						
						sixSidedRollFromCenterToRight3();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[0] == 4) {
						
						sixSidedRollFromCenterToRight4();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[0] == 5) {
						
						sixSidedRollFromCenterToRight5();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[0] == 6) {
						
						sixSidedRollFromCenterToRight6();
						
						initiativeResults();
					}					
				}	
				
				//ArrayOfAttackDamage.attackDamage[0]
				else if (isattackdamagerolled.equals("yes")) {
					
					int attackDamage = preferences.getInt("attackDamage", 0);
					
					if (attackDamage == 1) {
						
						sixSidedRollFromCenterToRight1();
						
						damageResults();
					}
					else if (attackDamage == 2) {
						
						sixSidedRollFromCenterToRight2();
						
						damageResults();
					}
					else if (attackDamage == 3) {
						
						sixSidedRollFromCenterToRight3();
						
						damageResults();
					}
					else if (attackDamage == 4) {
						
						sixSidedRollFromCenterToRight4();
						
						damageResults();
					}
					else if (attackDamage == 5) {
						
						sixSidedRollFromCenterToRight5();
						
						damageResults();
					}
					else if (attackDamage == 6) {
						
						sixSidedRollFromCenterToRight6();
						
						damageResults();
					}
				}
				
				else if (iscurerolled.equals("yes")) {
					
					int cureResult = preferences.getInt("cureResult", 0);
					
					if (cureResult == 1) {
						
						sixSidedRollFromCenterToRight1();
						
						cureResults();
					}
					else if (cureResult == 2) {
						
						sixSidedRollFromCenterToRight2();
						
						cureResults();
					}
					else if (cureResult == 3) {
						
						sixSidedRollFromCenterToRight3();
						
						cureResults();
					}
					else if (cureResult == 4) {
						
						sixSidedRollFromCenterToRight4();
						
						cureResults();
					}
					else if (cureResult == 5) {
						
						sixSidedRollFromCenterToRight5();
						
						cureResults();
					}
					else if (cureResult == 6) {
						
						sixSidedRollFromCenterToRight6();
						
						cureResults();
					}
				}
				
				else if (ismightyblowdamagerolled.equals("yes")) {
					
					int attackDamage = preferences.getInt("attackDamage", 0);
					
					if (attackDamage == 1) {
						
						sixSidedRollFromCenterToRight1();
						
						mightyBlowResults();
					}
					else if (attackDamage == 2) {
						
						sixSidedRollFromCenterToRight2();
						
						mightyBlowResults();
					}
					else if (attackDamage == 3) {
						
						sixSidedRollFromCenterToRight3();
						
						mightyBlowResults();
					}
					else if (attackDamage == 4) {
						
						sixSidedRollFromCenterToRight4();
						
						mightyBlowResults();
					}
					else if (attackDamage == 5) {
						
						sixSidedRollFromCenterToRight5();
						
						mightyBlowResults();
					}
					else if (attackDamage == 6) {
						
						sixSidedRollFromCenterToRight6();
						
						mightyBlowResults();
					}					
				}
				
				else if (iscriticalmissdamagerolled.equals("yes")) {
					
					int attackDamage = preferences.getInt("attackDamage", 0);
					
					if (attackDamage == 1) {
						
						sixSidedRollFromCenterToRight1();
						
						criticalMissDamageResults();
					}
					else if (attackDamage == 2) {
						
						sixSidedRollFromCenterToRight2();
						
						criticalMissDamageResults();
					}
					else if (attackDamage == 3) {
						
						sixSidedRollFromCenterToRight3();
						
						criticalMissDamageResults();
					}
					else if (attackDamage == 4) {
						
						sixSidedRollFromCenterToRight4();
						
						criticalMissDamageResults();
					}
					else if (attackDamage == 5) {
						
						sixSidedRollFromCenterToRight5();
						
						criticalMissDamageResults();
					}
					else if (attackDamage == 6) {
						
						sixSidedRollFromCenterToRight6();
						
						criticalMissDamageResults();
					}					
				}
				
				else if (iscriticalhitfirstrollrolled.equals("yes")) {
					
					//int attackDamage = preferences.getInt("attackDamage", 0);
					
					if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 1) {
						
						sixSidedRollFromCenterToRight1();
						
						criticalHitPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 2) {
						
						sixSidedRollFromCenterToRight2();
						
						criticalHitPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 3) {
						
						sixSidedRollFromCenterToRight3();
						
						criticalHitPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 4) {
						
						sixSidedRollFromCenterToRight4();
						
						criticalHitPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 5) {
						
						sixSidedRollFromCenterToRight5();
						
						criticalHitPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 6) {
						
						sixSidedRollFromCenterToRight6();
						
						criticalHitPartTwo();
					}					
				}
				
				else if (iscriticalhitsecondrollrolled.equals("yes")) {
					
					//int attackDamage = preferences.getInt("attackDamage", 0);
					
					if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 1) {
						
						sixSidedRollFromCenterToRight1();
						
						criticalHitDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 2) {
						
						sixSidedRollFromCenterToRight2();
						
						criticalHitDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 3) {
						
						sixSidedRollFromCenterToRight3();
						
						criticalHitDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 4) {
						
						sixSidedRollFromCenterToRight4();
						
						criticalHitDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 5) {
						
						sixSidedRollFromCenterToRight5();
						
						criticalHitDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 6) {
						
						sixSidedRollFromCenterToRight6();
						
						criticalHitDamageResults();
					}					
				}
				
				else if (iscriticalhitmightyblowfirstrollrolled.equals("yes")) {
					
					//int attackDamage = preferences.getInt("attackDamage", 0);
					
					if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 1) {
						
						sixSidedRollFromCenterToRight1();
						
						criticalHitMightyBlowPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 2) {
						
						sixSidedRollFromCenterToRight2();
						
						criticalHitMightyBlowPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 3) {
						
						sixSidedRollFromCenterToRight3();
						
						criticalHitMightyBlowPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 4) {
						
						sixSidedRollFromCenterToRight4();
						
						criticalHitMightyBlowPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 5) {
						
						sixSidedRollFromCenterToRight5();
						
						criticalHitMightyBlowPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 6) {
						
						sixSidedRollFromCenterToRight6();
						
						criticalHitMightyBlowPartTwo();
					}					
				}
				
				else if (iscriticalhitmightyblowsecondrollrolled.equals("yes")) {
					
					//int attackDamage = preferences.getInt("attackDamage", 0);
					
					if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 1) {
						
						sixSidedRollFromCenterToRight1();
						
						criticalHitMightyBlowDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 2) {
						
						sixSidedRollFromCenterToRight2();
						
						criticalHitMightyBlowDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 3) {
						
						sixSidedRollFromCenterToRight3();
						
						criticalHitMightyBlowDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 4) {
						
						sixSidedRollFromCenterToRight4();
						
						criticalHitMightyBlowDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 5) {
						
						sixSidedRollFromCenterToRight5();
						
						criticalHitMightyBlowDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 6) {
						
						sixSidedRollFromCenterToRight6();
						
						criticalHitMightyBlowDamageResults();
					}					
				}								
			//}
		    }		    
		    public void onSwipeLeft() {
		    	/*
		    	Toast.makeText(MainActivity2.this, "INITIATIVE ROLLED = " + issixsidedrolledforinitiative, Toast.LENGTH_SHORT).show();											  	  	  			
				Toast.makeText(MainActivity2.this, "DAMAGE ROLLED = " + isattackdamagerolled, Toast.LENGTH_SHORT).show();
				int attackDamage = preferences.getInt("attackDamage", 0);
				Toast.makeText(MainActivity2.this, "DAMAGE = " + attackDamage, Toast.LENGTH_SHORT).show();
		    	*/
		    	
		    	//if (issixsidedrolledforinitiative.equals("yes")) {	  				
					
				sixSidedWobbleStop();				
	  	  		
				//sixSidedRollFromCenterToLeft();
				//determineInitiative();				
				
				
				if (issixsidedrolledforinitiative.equals("no")) {
					
					if (ArrayOfInitiative.initiative[0] == 1) {
						
						sixSidedRollFromCenterToLeft1();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[0] == 2) {
						
						sixSidedRollFromCenterToLeft2();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[0] == 3) {
						
						sixSidedRollFromCenterToLeft3();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[0] == 4) {
						
						sixSidedRollFromCenterToLeft4();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[0] == 5) {
						
						sixSidedRollFromCenterToLeft5();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[0] == 6) {
						
						sixSidedRollFromCenterToLeft6();
						
						initiativeResults();
					}
				}
				
				else if (isattackdamagerolled.equals("yes")) {
					
					int attackDamage = preferences.getInt("attackDamage", 0);
					
					if (attackDamage == 1) {
						
						sixSidedRollFromCenterToLeft1();
						
						damageResults();
					}
					else if (attackDamage == 2) {
						
						sixSidedRollFromCenterToLeft2();
						
						damageResults();
					}
					else if (attackDamage == 3) {
						
						sixSidedRollFromCenterToLeft3();
						
						damageResults();
					}
					else if (attackDamage == 4) {
						
						sixSidedRollFromCenterToLeft4();
						
						damageResults();
					}
					else if (attackDamage == 5) {
						
						sixSidedRollFromCenterToLeft5();
						
						damageResults();
					}
					else if (attackDamage == 6) {
						
						sixSidedRollFromCenterToLeft6();
						
						damageResults();
					}
				}
				
				else if (iscurerolled.equals("yes")) {
					
					int cureResult = preferences.getInt("cureResult", 0);
					
					if (cureResult == 1) {
						
						sixSidedRollFromCenterToLeft1();
						
						cureResults();
					}
					else if (cureResult == 2) {
						
						sixSidedRollFromCenterToLeft2();
						
						cureResults();
					}
					else if (cureResult == 3) {
						
						sixSidedRollFromCenterToLeft3();
						
						cureResults();
					}
					else if (cureResult == 4) {
						
						sixSidedRollFromCenterToLeft4();
						
						cureResults();
					}
					else if (cureResult == 5) {
						
						sixSidedRollFromCenterToLeft5();
						
						cureResults();
					}
					else if (cureResult == 6) {
						
						sixSidedRollFromCenterToLeft6();
						
						cureResults();
					}
				}
				
				else if (ismightyblowdamagerolled.equals("yes")) {
					
					int attackDamage = preferences.getInt("attackDamage", 0);
					
					if (attackDamage == 1) {
						
						sixSidedRollFromCenterToLeft1();
						
						mightyBlowResults();
					}
					else if (attackDamage == 2) {
						
						sixSidedRollFromCenterToLeft2();
						
						mightyBlowResults();
					}
					else if (attackDamage == 3) {
						
						sixSidedRollFromCenterToLeft3();
						
						mightyBlowResults();
					}
					else if (attackDamage == 4) {
						
						sixSidedRollFromCenterToLeft4();
						
						mightyBlowResults();
					}
					else if (attackDamage == 5) {
						
						sixSidedRollFromCenterToLeft5();
						
						mightyBlowResults();
					}
					else if (attackDamage == 6) {
						
						sixSidedRollFromCenterToLeft6();
						
						mightyBlowResults();
					}
				}
				
				else if (iscriticalmissdamagerolled.equals("yes")) {
					
					int attackDamage = preferences.getInt("attackDamage", 0);
					
					if (attackDamage == 1) {
						
						sixSidedRollFromCenterToLeft1();
						
						criticalMissDamageResults();
					}
					else if (attackDamage == 2) {
						
						sixSidedRollFromCenterToLeft2();
						
						criticalMissDamageResults();
					}
					else if (attackDamage == 3) {
						
						sixSidedRollFromCenterToLeft3();
						
						criticalMissDamageResults();
					}
					else if (attackDamage == 4) {
						
						sixSidedRollFromCenterToLeft4();
						
						criticalMissDamageResults();
					}
					else if (attackDamage == 5) {
						
						sixSidedRollFromCenterToLeft5();
						
						criticalMissDamageResults();
					}
					else if (attackDamage == 6) {
						
						sixSidedRollFromCenterToLeft6();
						
						criticalMissDamageResults();
					}					
				}
				
				else if (iscriticalhitfirstrollrolled.equals("yes")) {
					
					//int attackDamage = preferences.getInt("attackDamage", 0);
					
					if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 1) {
						
						sixSidedRollFromCenterToLeft1();
						
						criticalHitPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 2) {
						
						sixSidedRollFromCenterToLeft2();
						
						criticalHitPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 3) {
						
						sixSidedRollFromCenterToLeft3();
						
						criticalHitPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 4) {
						
						sixSidedRollFromCenterToLeft4();
						
						criticalHitPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 5) {
						
						sixSidedRollFromCenterToLeft5();
						
						criticalHitPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 6) {
						
						sixSidedRollFromCenterToLeft6();
						
						criticalHitPartTwo();
					}					
				}
				
				else if (iscriticalhitsecondrollrolled.equals("yes")) {
					
					//int attackDamage = preferences.getInt("attackDamage", 0);
					
					if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 1) {
						
						sixSidedRollFromCenterToLeft1();
						
						criticalHitDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 2) {
						
						sixSidedRollFromCenterToLeft2();
						
						criticalHitDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 3) {
						
						sixSidedRollFromCenterToLeft3();
						
						criticalHitDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 4) {
						
						sixSidedRollFromCenterToLeft4();
						
						criticalHitDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 5) {
						
						sixSidedRollFromCenterToLeft5();
						
						criticalHitDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 6) {
						
						sixSidedRollFromCenterToLeft6();
						
						criticalHitDamageResults();
					}					
				}
				
				else if (iscriticalhitmightyblowfirstrollrolled.equals("yes")) {
					
					//int attackDamage = preferences.getInt("attackDamage", 0);
					
					if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 1) {
						
						sixSidedRollFromCenterToLeft1();
						
						criticalHitMightyBlowPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 2) {
						
						sixSidedRollFromCenterToLeft2();
						
						criticalHitMightyBlowPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 3) {
						
						sixSidedRollFromCenterToLeft3();
						
						criticalHitMightyBlowPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 4) {
						
						sixSidedRollFromCenterToLeft4();
						
						criticalHitMightyBlowPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 5) {
						
						sixSidedRollFromCenterToLeft5();
						
						criticalHitMightyBlowPartTwo();
					}
					else if (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] == 6) {
						
						sixSidedRollFromCenterToLeft6();
						
						criticalHitMightyBlowPartTwo();
					}					
				}
				
				else if (iscriticalhitmightyblowsecondrollrolled.equals("yes")) {
					
					//int attackDamage = preferences.getInt("attackDamage", 0);
					
					if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 1) {
						
						sixSidedRollFromCenterToLeft1();
						
						criticalHitMightyBlowDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 2) {
						
						sixSidedRollFromCenterToLeft2();
						
						criticalHitMightyBlowDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 3) {
						
						sixSidedRollFromCenterToLeft3();
						
						criticalHitMightyBlowDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 4) {
						
						sixSidedRollFromCenterToLeft4();
						
						criticalHitMightyBlowDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 5) {
						
						sixSidedRollFromCenterToLeft5();
						
						criticalHitMightyBlowDamageResults();
					}
					else if (ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] == 6) {
						
						sixSidedRollFromCenterToLeft6();
						
						criticalHitMightyBlowDamageResults();
					}					
				}			
			//}
		    }
		    /*
		    public void onSwipeBottom() {
		        Toast.makeText(MainActivity2.this, "bottom", Toast.LENGTH_SHORT).show();
		    }
		    */
		});		
		
		
		// USE android:background="@drawable/(SOME PNG)" TO SPECIFY AREA ON SCREEN ??
		twentySidedBlank.setOnTouchListener(new OnTwentySidedSwipeTouchListener(MainActivity2.this) {
		    /*
			public void onSwipeTop() {
		        Toast.makeText(MainActivity2.this, "top", Toast.LENGTH_SHORT).show();
		    }
		    */
		    public void onSwipeRight() {		    		  				
					
				twentySidedWobbleStop();				
				
				if (ArrayOfAttackResult.attackResult[0] == 1) {
					twentySidedRollFromCenterToRight1();
				}
				if (ArrayOfAttackResult.attackResult[0] == 2) {
					twentySidedRollFromCenterToRight2();
				}
				if (ArrayOfAttackResult.attackResult[0] == 3) {
					twentySidedRollFromCenterToRight3();
				}
				if (ArrayOfAttackResult.attackResult[0] == 4) {
					twentySidedRollFromCenterToRight4();
				}
				if (ArrayOfAttackResult.attackResult[0] == 5) {
					twentySidedRollFromCenterToRight5();
				}
				if (ArrayOfAttackResult.attackResult[0] == 6) {
					twentySidedRollFromCenterToRight6();
				}
				if (ArrayOfAttackResult.attackResult[0] == 7) {
					twentySidedRollFromCenterToRight7();
				}
				if (ArrayOfAttackResult.attackResult[0] == 8) {
					twentySidedRollFromCenterToRight8();
				}
				if (ArrayOfAttackResult.attackResult[0] == 9) {
					twentySidedRollFromCenterToRight9();
				}
				if (ArrayOfAttackResult.attackResult[0] == 10) {
					twentySidedRollFromCenterToRight10();
				}
				if (ArrayOfAttackResult.attackResult[0] == 11) {
					twentySidedRollFromCenterToRight11();
				}
				if (ArrayOfAttackResult.attackResult[0] == 12) {
					twentySidedRollFromCenterToRight12();
				}
				if (ArrayOfAttackResult.attackResult[0] == 13) {
					twentySidedRollFromCenterToRight13();
				}
				if (ArrayOfAttackResult.attackResult[0] == 14) {
					twentySidedRollFromCenterToRight14();
				}
				if (ArrayOfAttackResult.attackResult[0] == 15) {
					twentySidedRollFromCenterToRight15();
				}
				if (ArrayOfAttackResult.attackResult[0] == 16) {
					twentySidedRollFromCenterToRight16();
				}
				if (ArrayOfAttackResult.attackResult[0] == 17) {
					twentySidedRollFromCenterToRight17();
				}
				if (ArrayOfAttackResult.attackResult[0] == 18) {
					twentySidedRollFromCenterToRight18();
				}
				if (ArrayOfAttackResult.attackResult[0] == 19) {
					twentySidedRollFromCenterToRight19();
				}
				if (ArrayOfAttackResult.attackResult[0] == 20) {
					twentySidedRollFromCenterToRight20();
				}
				
				
				if (isattackrolled.equals("yes")) {
					attackResults();
				}
				if (isdisarmwithblessrolled.equals("yes")) {					
					disarmWithBlessResults();
				}
				if (isdisarmnoblessrolled.equals("yes")) {					
					disarmNoBlessResults();
				}
				if (isblessrolled.equals("yes")) {
					blessResults();
				}
				if (iscriticalmissrolled.equals("yes")) {
					criticalMissResults();
				}
				if (iscriticalmissloseweaponrolled.equals("yes")) {
					criticalMissLoseWeaponResults();
				}				
		    }		    
		    public void onSwipeLeft() {		    		  				
					
				twentySidedWobbleStop();				
				
				if (ArrayOfAttackResult.attackResult[0] == 1) {
					twentySidedRollFromCenterToLeft1();
				}
				if (ArrayOfAttackResult.attackResult[0] == 2) {
					twentySidedRollFromCenterToLeft2();
				}
				if (ArrayOfAttackResult.attackResult[0] == 3) {
					twentySidedRollFromCenterToLeft3();
				}
				if (ArrayOfAttackResult.attackResult[0] == 4) {
					twentySidedRollFromCenterToLeft4();
				}
				if (ArrayOfAttackResult.attackResult[0] == 5) {
					twentySidedRollFromCenterToLeft5();
				}
				if (ArrayOfAttackResult.attackResult[0] == 6) {
					twentySidedRollFromCenterToLeft6();
				}
				if (ArrayOfAttackResult.attackResult[0] == 7) {
					twentySidedRollFromCenterToLeft7();
				}
				if (ArrayOfAttackResult.attackResult[0] == 8) {
					twentySidedRollFromCenterToLeft8();
				}
				if (ArrayOfAttackResult.attackResult[0] == 9) {
					twentySidedRollFromCenterToLeft9();
				}
				if (ArrayOfAttackResult.attackResult[0] == 10) {
					twentySidedRollFromCenterToLeft10();
				}
				if (ArrayOfAttackResult.attackResult[0] == 11) {
					twentySidedRollFromCenterToLeft11();
				}
				if (ArrayOfAttackResult.attackResult[0] == 12) {
					twentySidedRollFromCenterToLeft12();
				}
				if (ArrayOfAttackResult.attackResult[0] == 13) {
					twentySidedRollFromCenterToLeft13();
				}
				if (ArrayOfAttackResult.attackResult[0] == 14) {
					twentySidedRollFromCenterToLeft14();
				}
				if (ArrayOfAttackResult.attackResult[0] == 15) {
					twentySidedRollFromCenterToLeft15();
				}
				if (ArrayOfAttackResult.attackResult[0] == 16) {
					twentySidedRollFromCenterToLeft16();
				}
				if (ArrayOfAttackResult.attackResult[0] == 17) {
					twentySidedRollFromCenterToLeft17();
				}
				if (ArrayOfAttackResult.attackResult[0] == 18) {
					twentySidedRollFromCenterToLeft18();
				}
				if (ArrayOfAttackResult.attackResult[0] == 19) {
					twentySidedRollFromCenterToLeft19();
				}
				if (ArrayOfAttackResult.attackResult[0] == 20) {
					twentySidedRollFromCenterToLeft20();
				}
				
				
				if (isattackrolled.equals("yes")) {
					attackResults();
				}
				if (isdisarmwithblessrolled.equals("yes")) {					
					disarmWithBlessResults();
				}
				if (isdisarmnoblessrolled.equals("yes")) {					
					disarmNoBlessResults();
				}
				if (isblessrolled.equals("yes")) {
					blessResults();
				}
				if (iscriticalmissrolled.equals("yes")) {
					criticalMissResults();
				}
				if (iscriticalmissloseweaponrolled.equals("yes")) {
					criticalMissLoseWeaponResults();
				}
		    }
		    /*
		    public void onSwipeBottom() {
		        Toast.makeText(MainActivity2.this, "bottom", Toast.LENGTH_SHORT).show();
		    }
		    */
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
	
	
	protected int getDataAttackDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	public void hideNavigation() {
		
		// This will hide the system bar until user swipes up from bottom or down from top.		
  		getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_IMMERSIVE
                  | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);		
	}
	*/
	
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
	    			
	    			dialog.dismiss();
	    			
	    			//hideNavigation();
	    			
	    			//NOT SURE ARRAYS ARE GETTING WIPED COMPLETELY W THE INTENT CODE BELOW, SO ADDED THIS:
	    			ArrayOfPlayers.player = new String[6];
	    			ArrayOfAvatars.avatar = new String[6];
	    			ArrayOfHitPoints.hitpoints = new int[6];
	    			ArrayOfInitiative.initiative = new int[6];
	    			//ArrayOfCureResult.cureResult = new int[6];
	    			ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo = new int[1];
	    			ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne = new int[1];
	    			ArrayOfAttackResult.attackResult = new int[1];
	    			ArrayOfAttackDamage.attackDamage = new int[1];
	    				    			
	    			
	    			blessSpell = new int[] {1, 1, 1, 1, 1, 1, 1};
	    			cureSpell = new int[] {1, 1, 1, 1, 1, 1, 1};
	    			dodgeBlowSpell = new int[] {1, 1, 1, 1, 1, 1, 1};
	    			mightyBlowSpell = new int[] {1, 1, 1, 1, 1, 1, 1};
	    			hasteSpell = new int[] {2, 2, 2, 2, 2, 2, 2};
	    			
	    			
	    			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
	    			SharedPreferences.Editor editor = preferences.edit();
	    			editor.clear();
	    			editor.commit();
	    			
	    			
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
	    			
	    			dialog.dismiss();
	    			
	    			//hideNavigation();		         		            		  
	    		}
	    	});	  	    	
	    	alert.show();				
		
		//Toast.makeText(MainActivity2.this,"onBackPressed WORKING!!!!", Toast.LENGTH_SHORT).show();
    }
	
	public void onDestroy() {
	    
		//NOT SURE ARRAYS ARE GETTING WIPED COMPLETELY W THE INTENT CODE BELOW, SO ADDED THIS:
		ArrayOfPlayers.player = new String[6];
		ArrayOfAvatars.avatar = new String[6];
		ArrayOfHitPoints.hitpoints = new int[6];
		ArrayOfInitiative.initiative = new int[6];
		//ArrayOfCureResult.cureResult = new int[6];
		ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo = new int[1];
		ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne = new int[1];
		ArrayOfAttackResult.attackResult = new int[1];
		ArrayOfAttackDamage.attackDamage = new int[1];
		
		
		blessSpell = new int[] {1, 1, 1, 1, 1, 1, 1};
		cureSpell = new int[] {1, 1, 1, 1, 1, 1, 1};
		dodgeBlowSpell = new int[] {1, 1, 1, 1, 1, 1, 1};
		mightyBlowSpell = new int[] {1, 1, 1, 1, 1, 1, 1};
		hasteSpell = new int[] {2, 2, 2, 2, 2, 2, 2};
		
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
		
	    // Must always call the super method at the end.
	    super.onDestroy();
	}
	
	@Override
	public void onResume(){
	    super.onResume();
	    // put your code here...
	    
	    unfoldScrolls();
	    
	    //ONE OF THE 1ST 2-IFS & THE 3RD-IF COULD HAPPEN AT SAME TIME ON onResume.(WORKING-MULTIPLE IFS CAN HAPPEN AT SAME TIME)
	    if (isSixSidedReadyToBeRolled.equals("yes")) {
	    	
	    	sixSidedRollFromLeft();
	    	
	    	//sixSidedWobbleStart();
	    	
	    	//isSixSidedReadyToBeRolled = "no";
	    }
	    
	    if (isTwentySidedReadyToBeRolled.equals("yes")) {
	    	
	    	twentySidedRollFromLeft();
	    	
	    	//twentySidedWobbleStart();
	    	
	    	//isTwentySidedReadyToBeRolled = "no";
	    }
	    
	    if (issixsidedrolledforinitiative.equals("no") && isInitiativeOver.equals("no")) {
	    	
	    	ImageView img = (ImageView)findViewById(R.id.titleanimation);
	    	img.setBackgroundResource(R.anim.titleanimationnoinitiative);
	    	img.bringToFront();
	    	   	
	    	
	    	
	    	TextView titleinitiativetext = (TextView) findViewById(R.id.textviewtitleinitiativetext);
	    	//titleinitiativetext.bringToFront();
			
			//Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  	  	//titleinitiativetext.setTypeface(typeFace);			
					
  	  		titleinitiativetext.setVisibility(View.VISIBLE);				  		
  	  		//titleinitiativetext.append("Initiative");
	    }
	    
	    //THIS IF IS EXCLUSIVE FROM THE ABOVE IF
	    if (istitlerulesopen.equals("yes")) {
	    	
	    	ImageView img = (ImageView)findViewById(R.id.titleanimation);
	    	img.setBackgroundResource(R.anim.titleanimationnoinitiative);
	    	img.bringToFront();
	    	
	    	TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);			
			//titletext.bringToFront();			
			titletext.setVisibility(View.VISIBLE);
			
			TextView titlerulestext = (TextView) findViewById(R.id.textviewtitlerulestext);	
			titlerulestext.setVisibility(View.INVISIBLE);			
			
	    	
	    	istitlerulesopen = "no";
	    }
	    
	    /*
	    ImageView img1 = (ImageView)findViewById(R.id.imageviewscroll3b5left);
  		img1.bringToFront();
  		
  		ImageView img2 = (ImageView)findViewById(R.id.imageviewscroll3b5right);
  		img2.bringToFront();
  		*/
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
				
				//getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
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
				
				//getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
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
	public void unfoldScrolls() {
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
		  	  	
		  	  	//TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);
		  	  	
		  	  	//Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			//titletext.setTypeface(typeFace);
	  			
	  			//titletext.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(100));
	  				
	  			//titletext.setVisibility(View.VISIBLE);				  		
	  			//titletext.append("KtOG");
	  	    }
  		});	
	}
	
	
	/*
	 * 
	 * 
	 * 
	 * Other Animations***********************************************************************************
	 * 
	 * 
	 * 
	 */
	
	
	public void blessGraphic() {
		
		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
		
		Animation a = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.textscaletobig);					  	  	  	
	  	  	
  	  	TextView blessGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
  		
  	  	blessGraphic.setVisibility(View.VISIBLE);
		blessGraphic.bringToFront();
  	  	
  	  	blessGraphic.setTypeface(typeFace);
  		blessGraphic.setText("Bless");  		
  	  	
  	  	blessGraphic.clearAnimation();
  		blessGraphic.startAnimation(a);
  		
  		MediaPlayerWrapper.play(MainActivity2.this, R.raw.badonkshort);
  		
  		
		final Handler h = new Handler();
  	  	h.postDelayed(new Runnable() {		  	  	  			
  	  			
  	  		@Override
  	  		public void run() {
  	  			
	  	  		Animation a = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.textscaletosmall);						  	  	  	
	  	  	  	
		  	  	final TextView blessGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
  	  	  		blessGraphic.setTypeface(typeFace);
  	  	  		blessGraphic.setText("Bless");
  	  	  		
	  	  	  	blessGraphic.clearAnimation();
  	  	  		blessGraphic.startAnimation(a);
  	  		}	  	  		
  	  	}, 3000);		
	}
	
	public void cureGraphic() {
		
		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
		
		Animation a = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.textscaletobig);					  	  	  	
	  	  	
  	  	TextView cureGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
  	  	
  	  	cureGraphic.setVisibility(View.VISIBLE);
	  	cureGraphic.bringToFront();
  	  	
  	  	cureGraphic.setTypeface(typeFace);
  	  	cureGraphic.setText(" Cure");  	  	
  	  	
  	  	cureGraphic.clearAnimation();
  	  	cureGraphic.startAnimation(a);
  		
  		MediaPlayerWrapper.play(MainActivity2.this, R.raw.badonkshort);
  		
  		
		final Handler h = new Handler();
  	  	h.postDelayed(new Runnable() {		  	  	  			
  	  			
  	  		@Override
  	  		public void run() {
  	  			
	  	  		Animation a = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.textscaletosmall);						  	  	  	
	  	  	  	
		  	  	final TextView cureGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  	  	cureGraphic.setTypeface(typeFace);
		  	  	cureGraphic.setText(" Cure");
	  	  	  	
		  	  	cureGraphic.clearAnimation();
		  	  	cureGraphic.startAnimation(a);
  	  		}	  	  		
  	  	}, 3000);		
	}
	
	public void dodgeGraphic() {
		
		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
		
		Animation a = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.textscaletobig);					  	  	  	
	  	  	
  	  	TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
  	  	
  	  	dodgeGraphic.setVisibility(View.VISIBLE);
	  	dodgeGraphic.bringToFront();
  	  	
  	  	dodgeGraphic.setTypeface(typeFace);
  	  	dodgeGraphic.setText("Dodge");  	  	
  	  	
  	  	dodgeGraphic.clearAnimation();
  	  	dodgeGraphic.startAnimation(a);
  		
  		MediaPlayerWrapper.play(MainActivity2.this, R.raw.badonkshort);
  		
  		
		final Handler h = new Handler();
  	  	h.postDelayed(new Runnable() {		  	  	  			
  	  			
  	  		@Override
  	  		public void run() {
  	  			
	  	  		Animation a = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.textscaletosmall);						  	  	  	
	  	  	  	
		  	  	final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
		  	  	dodgeGraphic.setTypeface(typeFace);
		  	  	dodgeGraphic.setText("Dodge");
	  	  	  	
		  	  	dodgeGraphic.clearAnimation();
		  	  	dodgeGraphic.startAnimation(a);
  	  		}	  	  		
  	  	}, 3000);	
	}
	
	public void mightyBlowGraphic() {
		
		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
		
		Animation a = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.textscaletobig);					  	  	  	
	  	  	
  	  	TextView mightyBlowGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
  	  	
  	  	mightyBlowGraphic.setVisibility(View.VISIBLE);
	  	mightyBlowGraphic.bringToFront();
  	  	
  	  	mightyBlowGraphic.setTypeface(typeFace);
  	  	mightyBlowGraphic.setText("Mighty Blow");  	  	
  	  	
  	  	mightyBlowGraphic.clearAnimation();
  	  	mightyBlowGraphic.startAnimation(a);
  		
  		MediaPlayerWrapper.play(MainActivity2.this, R.raw.badonkshort);
  		
  		
		final Handler h = new Handler();
  	  	h.postDelayed(new Runnable() {		  	  	  			
  	  			
  	  		@Override
  	  		public void run() {
  	  			
	  	  		Animation a = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.textscaletosmall);						  	  	  	
	  	  	  	
		  	  	final TextView mightyBlowGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
		  	  	mightyBlowGraphic.setTypeface(typeFace);
		  	  	mightyBlowGraphic.setText("Mighty Blow");
	  	  	  	
		  	  	mightyBlowGraphic.clearAnimation();
		  	  	mightyBlowGraphic.startAnimation(a);
  	  		}	  	  		
  	  	}, 3000);		
	}
	
	public void hasteGraphic() {
		
		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
		
		Animation a = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.textscaletobig);					  	  	  	
	  	  	
  	  	TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
  	  	
  	  	hasteGraphic.setVisibility(View.VISIBLE);
	  	hasteGraphic.bringToFront();
  	  	
  	  	hasteGraphic.setTypeface(typeFace);
  	  	hasteGraphic.setText("Haste");  	  	
  	  	
  	  	hasteGraphic.clearAnimation();
  	  	hasteGraphic.startAnimation(a);
  		
  		MediaPlayerWrapper.play(MainActivity2.this, R.raw.badonkshort);
  		
  		
		final Handler h = new Handler();
  	  	h.postDelayed(new Runnable() {		  	  	  			
  	  			
  	  		@Override
  	  		public void run() {
  	  			
	  	  		Animation a = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.textscaletosmall);						  	  	  	
	  	  	  	
		  	  	final TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  	  	hasteGraphic.setTypeface(typeFace);
		  	  	hasteGraphic.setText("Haste");
	  	  	  	
		  	  	hasteGraphic.clearAnimation();
		  	  	hasteGraphic.startAnimation(a);
  	  		}	  	  		
  	  	}, 3000);		
	}
	
	public void criticalHitGraphic() {
		
		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
		
		Animation a = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.textscaletobig);					  	  	  	
	  	  	
  	  	TextView criticalHitGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
  	  	
  	  	criticalHitGraphic.setVisibility(View.VISIBLE);
	  	criticalHitGraphic.bringToFront();
  	  	
  	  	criticalHitGraphic.setTypeface(typeFace);
  	  	criticalHitGraphic.setText("Critical     Hit");  	  	
  	  	
  	  	criticalHitGraphic.clearAnimation();
  	  	criticalHitGraphic.startAnimation(a);
  		
  		MediaPlayerWrapper.play(MainActivity2.this, R.raw.badonkshort);
  		
  		
		final Handler h = new Handler();
  	  	h.postDelayed(new Runnable() {		  	  	  			
  	  			
  	  		@Override
  	  		public void run() {
  	  			
	  	  		Animation a = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.textscaletosmall);						  	  	  	
	  	  	  	
		  	  	final TextView criticalHitGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
		  	  	criticalHitGraphic.setTypeface(typeFace);
		  	  	criticalHitGraphic.setText("Critical     Hit");
	  	  	  	
		  	  	criticalHitGraphic.clearAnimation();
		  	  	criticalHitGraphic.startAnimation(a);
  	  		}	  	  		
  	  	}, 3000);		
	}
	
	public void criticalMissGraphic() {
		
		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
		
		Animation a = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.textscaletobig);					  	  	  	
	  	  	
  	  	TextView criticalMissGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
  	  	
  	  	criticalMissGraphic.setVisibility(View.VISIBLE);
	  	criticalMissGraphic.bringToFront();
  	  	
  	  	criticalMissGraphic.setTypeface(typeFace);
  	  	criticalMissGraphic.setText("Critical Miss");  	  	
  	  	
  	  	criticalMissGraphic.clearAnimation();
  	  	criticalMissGraphic.startAnimation(a);
  		
  		MediaPlayerWrapper.play(MainActivity2.this, R.raw.badonkshort);
  		
  		
		final Handler h = new Handler();
  	  	h.postDelayed(new Runnable() {		  	  	  			
  	  			
  	  		@Override
  	  		public void run() {
  	  			
	  	  		Animation a = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.textscaletosmall);						  	  	  	
	  	  	  	
		  	  	final TextView criticalMissGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
		  	  	criticalMissGraphic.setTypeface(typeFace);
		  	  	criticalMissGraphic.setText("Critical Miss");
	  	  	  	
		  	  	criticalMissGraphic.clearAnimation();
		  	  	criticalMissGraphic.startAnimation(a);
  	  		}	  	  		
  	  	}, 3000);		
	}
	
	public void stopGraphics() {
		
		TextView blessGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		TextView cureGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
		TextView mightyBlowGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
		TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		TextView criticalHitGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
		TextView criticalMissGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
		
		blessGraphic.clearAnimation();
		blessGraphic.setVisibility(View.GONE);
		
		cureGraphic.clearAnimation();
		cureGraphic.setVisibility(View.GONE);
		
		dodgeGraphic.clearAnimation();
		dodgeGraphic.setVisibility(View.GONE);
		
		mightyBlowGraphic.clearAnimation();
		mightyBlowGraphic.setVisibility(View.GONE);
		
		hasteGraphic.clearAnimation();
		hasteGraphic.setVisibility(View.GONE);
		
		criticalHitGraphic.clearAnimation();
		criticalHitGraphic.setVisibility(View.GONE);
		
		criticalMissGraphic.clearAnimation();
		criticalMissGraphic.setVisibility(View.GONE);
	}
	
	
	/*
	public void swordsCrashing() {	
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// Setting up scroll frame animation.
				ImageView img = (ImageView)findViewById(R.id.swordscrashinganimation);		
				img.setBackgroundResource(R.anim.swordscrashing);
		  	  
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});	
	}
	*/
	
	
	/*
	 * 
	 * 
	 * 
	 * Dice-Related*******************************************************************************************
	 * 
	 * 
	 * 
	 */
	
	
	public void computerRolls20SidedDie() {		
		
		if (ArrayOfAttackResult.attackResult[0] == 1) {
			computerTwentySidedRollFromLeft1();
		}
		if (ArrayOfAttackResult.attackResult[0] == 2) {
			computerTwentySidedRollFromLeft2();
		}
		if (ArrayOfAttackResult.attackResult[0] == 3) {
			computerTwentySidedRollFromLeft3();
		}
		if (ArrayOfAttackResult.attackResult[0] == 4) {
			computerTwentySidedRollFromLeft4();
		}
		if (ArrayOfAttackResult.attackResult[0] == 5) {
			computerTwentySidedRollFromLeft5();
		}
		if (ArrayOfAttackResult.attackResult[0] == 6) {
			computerTwentySidedRollFromLeft6();
		}
		if (ArrayOfAttackResult.attackResult[0] == 7) {
			computerTwentySidedRollFromLeft7();
		}
		if (ArrayOfAttackResult.attackResult[0] == 8) {
			computerTwentySidedRollFromLeft8();
		}
		if (ArrayOfAttackResult.attackResult[0] == 9) {
			computerTwentySidedRollFromLeft9();
		}
		if (ArrayOfAttackResult.attackResult[0] == 10) {
			computerTwentySidedRollFromLeft10();
		}
		if (ArrayOfAttackResult.attackResult[0] == 11) {
			computerTwentySidedRollFromLeft11();
		}
		if (ArrayOfAttackResult.attackResult[0] == 12) {
			computerTwentySidedRollFromLeft12();
		}
		if (ArrayOfAttackResult.attackResult[0] == 13) {
			computerTwentySidedRollFromLeft13();
		}
		if (ArrayOfAttackResult.attackResult[0] == 14) {
			computerTwentySidedRollFromLeft14();
		}
		if (ArrayOfAttackResult.attackResult[0] == 15) {
			computerTwentySidedRollFromLeft15();
		}
		if (ArrayOfAttackResult.attackResult[0] == 16) {
			computerTwentySidedRollFromLeft16();
		}
		if (ArrayOfAttackResult.attackResult[0] == 17) {
			computerTwentySidedRollFromLeft17();
		}
		if (ArrayOfAttackResult.attackResult[0] == 18) {
			computerTwentySidedRollFromLeft18();
		}
		if (ArrayOfAttackResult.attackResult[0] == 19) {
			computerTwentySidedRollFromLeft19();
		}
		if (ArrayOfAttackResult.attackResult[0] == 20) {
			computerTwentySidedRollFromLeft20();
		}
	}
	
	public int computerRolls6SidedDie() {
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
		//SharedPreferences.Editor editor = preferences.edit();
		int attackDamage = preferences.getInt("attackDamage", 0);
		int cureResult = preferences.getInt("cureResult", 0);		
		
		//Globals g = Globals.getInstance();
		//int attackDamage=g.getDataAttackDamage();
		//int cureResult=g.getDataCureResult();	
		
		if ((attackDamage == 1 && preventattackdamagediefromleaking.equals("off")) || (cureResult == 1 && preventcureresultdiefromleaking.equals("off"))) {
			computerSixSidedRollFromLeft1();							  		  	  	
		}
		else if ((attackDamage == 2 && preventattackdamagediefromleaking.equals("off")) || (cureResult == 2 && preventcureresultdiefromleaking.equals("off"))) {
			computerSixSidedRollFromLeft2();
		}
		else if ((attackDamage == 3 && preventattackdamagediefromleaking.equals("off")) || (cureResult == 3 && preventcureresultdiefromleaking.equals("off"))) {
			computerSixSidedRollFromLeft3();
		}
		else if ((attackDamage == 4 && preventattackdamagediefromleaking.equals("off")) || (cureResult == 4 && preventcureresultdiefromleaking.equals("off"))) {
			computerSixSidedRollFromLeft4();
		}
		else if ((attackDamage == 5 && preventattackdamagediefromleaking.equals("off")) || (cureResult == 5 && preventcureresultdiefromleaking.equals("off"))) {
			computerSixSidedRollFromLeft5();
		}
		else if ((attackDamage == 6 && preventattackdamagediefromleaking.equals("off")) || (cureResult == 6 && preventcureresultdiefromleaking.equals("off"))) {
			computerSixSidedRollFromLeft6();
		}
		return attackDamage;		
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
	
	public void computerSixSidedRollFromLeft1() {	
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
				img.setBackgroundResource(R.anim.computersixsidedrollfromleft1);
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
	
	public void computerSixSidedRollFromLeft2() {	
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
				img.setBackgroundResource(R.anim.computersixsidedrollfromleft2);
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
	
	public void computerSixSidedRollFromLeft3() {	
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
				img.setBackgroundResource(R.anim.computersixsidedrollfromleft3);
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
	
	public void computerSixSidedRollFromLeft4() {	
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
				img.setBackgroundResource(R.anim.computersixsidedrollfromleft4);
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
	
	public void computerSixSidedRollFromLeft5() {	
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
				img.setBackgroundResource(R.anim.computersixsidedrollfromleft5);
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
	
	public void computerSixSidedRollFromLeft6() {	
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
				img.setBackgroundResource(R.anim.computersixsidedrollfromleft6);
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
	
	public void computerTwentySidedRollFromLeft1() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft1);
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
	
	public void computerTwentySidedRollFromLeft2() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft2);
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
	
	public void computerTwentySidedRollFromLeft3() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft3);
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
	
	public void computerTwentySidedRollFromLeft4() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft4);
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
	
	public void computerTwentySidedRollFromLeft5() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft5);
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
	
	public void computerTwentySidedRollFromLeft6() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft6);
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
	
	public void computerTwentySidedRollFromLeft7() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft7);
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
	
	public void computerTwentySidedRollFromLeft8() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft8);
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
	
	public void computerTwentySidedRollFromLeft9() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft9);
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
	
	public void computerTwentySidedRollFromLeft10() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft10);
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
	
	public void computerTwentySidedRollFromLeft11() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft11);
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
	
	public void computerTwentySidedRollFromLeft12() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft12);
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
	
	public void computerTwentySidedRollFromLeft13() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft13);
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
	
	public void computerTwentySidedRollFromLeft14() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft14);
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
	
	public void computerTwentySidedRollFromLeft15() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft15);
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
	
	public void computerTwentySidedRollFromLeft16() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft16);
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
	
	public void computerTwentySidedRollFromLeft17() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft17);
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
	
	public void computerTwentySidedRollFromLeft18() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft18);
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
	
	public void computerTwentySidedRollFromLeft19() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft19);
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
	
	public void computerTwentySidedRollFromLeft20() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft20);
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
		
	/*
	public void myInitiativeNotStarted() {
	    	  
		final ImageView img = (ImageView)findViewById(R.id.titleanimation);		
		img.setBackgroundResource(R.anim.titleanimationnoinitiative);
  
		// Get the background, which has been compiled to an AnimationDrawable object.
		final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

		// Animation is just 1 slide so user can see title.
		frameAnimation.stop();
		frameAnimation.start();	      
	}
	*/
	
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
	/*
	public void  myInitiativeIsStartedTitleInterrupt() {
		
		final ImageView img = (ImageView)findViewById(R.id.titleanimation);		
		img.setBackgroundResource(R.anim.initiativestartedtitleinterruptanimation);
  
		// Get the background, which has been compiled to an AnimationDrawable object.
		final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

		// Animation is just 1 slide so user can see title.
		frameAnimation.stop();
		frameAnimation.start();
	}
	*/
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
	
	public void initiativeResults() {
		
		isSixSidedReadyToBeRolled = "no";
		
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
	  			centerscrolltext.append("\n" + "> You roll " + ArrayOfInitiative.initiative[0] + " for initiative.");
	  			
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
							  			centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfInitiative.initiative[1] + " for initiative.");								  			
					  	  	  			
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
		  			centerscrolltext.append("\n" + "> Initiative is a tie.");	  			
		  			
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
							  	  	  	    	
							  	  	  	    	isSixSidedReadyToBeRolled = "yes";
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
			
			isInitiativeOver = "yes";
			
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
		
		
  		final Handler h = new Handler();
	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  			
  	  		@Override
  	  		public void run() {
  	  			
	  	  		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
	  			
	  	  		alert.setCancelable(false);
	  	  		
	  	    	alert.setTitle("Initiative Results");
	  	    	
	  	    	//alert.setMessage(ArrayOfPlayers.player[0] + getString(R.string.tab) + ArrayOfPlayers.player[1]);
	  	    	//alert.setMessage(ArrayOfInitiative.initiative[0] + getString(R.string.tab) + ArrayOfInitiative.initiative[0]);
	  	    	
	  	    	for (int i = 0; i < 2; i++) {
	  	    		// Was "numberOfPlayers", which = 2 up top.
	  	        
	  	           if (max == ArrayOfInitiative.initiative[i])
	  	           {
	  	        	   alert.setMessage("\n" + ArrayOfPlayers.player[i] + " wins with a " + max + ".");
	  	           }
	  	        }
	  	    	
	  	    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	  		    	public void onClick(DialogInterface dialog, int whichButton) {
	  		    		
	  		    		//hideNavigation();
	  		    		
	  		    		finishInitiative();
	  		    		
	  		    		dialog.dismiss();
	  		    	}
	  	    	});		  	    	
	  	    	alert.show(); 	  	  							  	  	  	
  	  		}
  	  	}, 2000);	  	
	}
	
	public void finishInitiative() {
		
		final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		centerscrolltext.setTypeface(typeFace);
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		// CRASHES IF USE THIS:
		//img.setVisibility(View.INVISIBLE);
		
  		// Use a blank drawable to hide the imageview animation:
  		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
  		img.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
		img.setImageResource(R.drawable.sixsixrightleftrotateblank);		
  		
		
		// Re-enables ability to use srollbar:
		centerscrolltext.bringToFront();												
		
		centerscrolltext.setVisibility(View.VISIBLE);													
  		centerscrolltext.startAnimation(animAlphaText);
		centerscrolltext.append("\n" + "> Let the battle begin...");	
		
		
		
		
		myInitiativeTransition();	
	  	  		
	  	  		
  	  	final Handler h = new Handler();
  		h.postDelayed(new Runnable() {		  	  	  			
  	  			
  	  		@Override
  	  	  	public void run() {
  	  			
  	  			TextView titleinitiativetext = (TextView) findViewById(R.id.textviewtitleinitiativetext);
  	  			titleinitiativetext.setVisibility(View.INVISIBLE);
  	  			
  	  			final TextView titlerulestext = (TextView) findViewById(R.id.textviewtitlerulestext);
  	  			
	  	  		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  	  		titlerulestext.setTypeface(typeFace);
	  			
				//titletext.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(100));
					
	  	  		titlerulestext.setVisibility(View.VISIBLE);				  		
	  	  		titlerulestext.append("Instructions go here...");	  			
  	  			
  	  			
	  	  		final Handler h3 = new Handler();
		  	  	h3.postDelayed(new Runnable() {
	
		  	  		@Override
		  	  		public void run()
		  	  		{
		  	  			titlerulestext.setVisibility(View.INVISIBLE);
		  	  			
		  	  			
		  	  			TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);
		  	  			
			  	  		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
			  	  		titletext.setTypeface(typeFace);
			  			
						//titletext.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(100));
							
			  	  		titletext.setVisibility(View.VISIBLE);
			  	  		//THIS IS ALREADY VISIBLE (NOT GONE):
			  	  		//titletext.append("KtOG");
			  	  		
		  	  			
		  	  			
		  	  			
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
		  	  			
		  	  			
		  	  			
		  	  			//preventinitiativediefromleaking.equals("on");
		  	  			
		  	  			
		  	  			//Thread myThread = new Thread(myRunnable);
		  	  			//myThread.start();
			  	  		
		  	  		}
		  	  	}, 10050);		  	  			
  	  	  	}
  	  	}, 525);  		  	    	  	  			  	  			
	
	  	//Toast.makeText(MainActivity2.this,"isinitiativestarted = " +  isinitiativestarted + " aretheredoubles = " + aretheredoubles, Toast.LENGTH_SHORT).show();  	 	
	  	  		
	}	 	
	
	
	/*
	 * 
	 * 
	 * 
	 * Computer Methods*****************************************************************************************
	 * 
	 * 
	 * 
	 */
	
	
	public void computerAttack() { //WAS int
		// player uses methods as normal, BUT computer must use new methods?	
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
	  			// NO NEED FOR ANOTHER METHOD - ACCOUNTED FOR DISARMED HUMAN AS WELL AS A DISARMED COMPUTER ALL IN 1 METHOD.
				/*	
				if (canHasDisarmed[0] == "yes") { 
					// the goto method to get bonus if attacking disarmed opponent.										 
				
					computerAttackAgainstDisarmed();
					return;
				}
				*/				
		
				// CURE
				if ((ArrayOfHitPoints.hitpoints[1] <= 12 && cureSpell[1] > 0) && (iscomputerhasteused.equals("no"))) {							
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer uses cure spell...");
					
					// for(int x = 0; x < 100; --x)
					// {}				
		  	  	  			
	  	  	  		computerCure();
					return;			  	  						
				}
		
				// ATTACK && ATTACK USING FIRST HASTE && ATTACK IF NO HASTES LEFT && ATTACK IF NO BLESS && ATTACK IF HUMIE ALREADY DISARMED
				else if ((computerAction <= 40 && iscomputerhasteused.equals("no")) || (computerAction >= 1 && iscomputerhasteused.equals("yes")) || ((computerAction >= 51 && computerAction <= 75) && (hasteSpell[1] < 1 && iscomputerhasteused.equals("no"))) || ((computerAction >= 41 && computerAction <= 50) && (blessSpell[1] < 1 && iscomputerhasteused.equals("no")))
					|| ((computerAction > 75 && computerAction <= 100) && iscomputerhasteused.equals("no") && canHasDisarmed[0].equals("yes"))) {
					
					if (canHasDisarmed[1].equals("no")) {						
			  	  	  			
		  	  	  		ArrayOfAttackResult.attackResult[0] = (int) (Math.random() * 20) + 1;					
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> The computer attacks...");
						
						// for(int x = 0; x < 100; --x)
						// {}
						
						final Handler h1 = new Handler();
			  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {			  	  	  			
			  	  	  			
			  	  	  			ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
			  	  	  			img.setVisibility(View.VISIBLE);
			  	  	  			
			  	  	  			computerRolls20SidedDie();
			  	  	  			
				  	  	  		final Handler h2 = new Handler();
					  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			/*
						  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> The computer rolls a " + ArrayOfAttackResult.attackResult[0] + ".");
										*/
							
										if (ArrayOfAttackResult.attackResult[0] >= 20) {
											
											computerCriticalHit();
											return;
										}
										
										if (canHasDisarmed[0].equals("no")) {
										
											if (ArrayOfAttackResult.attackResult[0] >= 14 && ArrayOfAttackResult.attackResult[0] <= 19) {
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);
														centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ".");
									  	  	  			
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
														  		centerscrolltext.startAnimation(animAlphaText);
																centerscrolltext.append("\n" + "> The computer's attack hits.");
																
																final Handler h3 = new Handler();
													  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
														  	  	  		if (dodgeBlowSpell[0] > 0) {
																			/*
																			centerscrolltext.setVisibility(View.VISIBLE);													
																	  		centerscrolltext.startAnimation(animAlphaText);			  		
																			centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
																			*/
																			
																			AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
																  			
																			alert.setCancelable(false);
																			
																  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your Dodge spell?");
																  	    	/*
																  	    	alert.setMessage("something");
																  	    	*/	  	    	
																  	    	
																  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
																  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
																  		    		
																  		    		/*
																  		    		//NEED THIS??????????????????
																  		    		if (dodgeBlowSpell[0] < 1) {
																  		    			
																  		    			hideNavigation();
																  						
																  						centerscrolltext.setVisibility(View.VISIBLE);													
																  				  		centerscrolltext.startAnimation(animAlphaText);			  		
																  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
																  						
																  						//break;
																  					}
																  		    		*/
																  		    		dialog.dismiss();
																  		    		
																  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
																  		    		
																  		    		centerscrolltext.setVisibility(View.VISIBLE);													
																			  		centerscrolltext.startAnimation(animAlphaText);
																					centerscrolltext.append("\n" + "> You dodge the hit.");
																  		    		
																  		    		skillsCheck();
																  		    		
																	  		    	// Use a blank drawable to hide the imageview animation:
																	  		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
																	  		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
																	  		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
																	  		    	img.setImageResource(R.drawable.twentytwentyblank);
																  		    		
																  		    		dodgeGraphic();
																  		    		
																  		    		final Handler h = new Handler();
																		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																		  	  	  			
																		  	  	  		@Override
																			  	  	  	public void run() {
																		  	  	  			
																			  	  	  		//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
																		  	  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
																		  	  	  			
																		  	  	  			stopGraphics();
																		  	  	  			
																			  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																			  	    			
																			  	    			gameEngineComputerFirst2();   							
																			  				}
														
																						  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																						  						
																						  		turn();    							
																						  	}
																						  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																						  	    			
																						  		computerHastePartTwo();   							
																						  	}
																	
																						  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																						  						
																						  		computerHastePartTwo();    							
																						  	}								  		    		
																		  					return;
																			  	  	  	}
																		  	  	  	}, 6000);														  		    		
																  		    	}
																  	    	});
																  	    	
																  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
																          	  public void onClick(DialogInterface dialog, int whichButton) {
																          		  
																          		  	//hideNavigation();
																          		  	
																          		  	computerDamage();								          		  	
																          		  	
																          		  	dialog.dismiss();
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
														  	  	  		
														  	  	  		else {
														  	  	  			
															  	  	  		computerDamage();
																			return;						  	  	  			
														  	  	  		}
														  	  	  	}
													  	  	  	}, 2000);
												  	  	  	}
											  	  	  	}, 2000);										  	  	  		
										  	  	  	}
									  	  	  	}, 2000);																				
											}
											
											if (ArrayOfAttackResult.attackResult[0] < 14 && ArrayOfAttackResult.attackResult[0] > 1) {
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);
														centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ".");
									  	  	  			
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
														  		centerscrolltext.startAnimation(animAlphaText);
																centerscrolltext.append("\n" + "> The computer's attack misses.");
																
																final Handler h4 = new Handler();
													  	  	  	h4.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
														  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
														  	    			
														  	    			gameEngineComputerFirst2();   							
														  				}
									
																	  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																	  						
																	  		turn();    							
																	  	}
																	  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																	  	    			
																	  		computerHastePartTwo();   							
																	  	}
												
																	  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																	  						
																	  		computerHastePartTwo();    							
																	  	}					  	  	  			
														  	  	  	}
													  	  	  	}, 2000);								
																return;
												  	  	  	}
											  	  	  	}, 2000);										  	  	  											  	  	  			
										  	  	  	}
									  	  	  	}, 2000);												
											}
											
											if (ArrayOfAttackResult.attackResult[0] <= 1) {
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		computerCriticalMiss();
														return;
										  	  	  	}
									  	  	  	}, 2000);												
											}
											
											// NEED THIS?:
											//return;
										}
										
										if (canHasDisarmed[0].equals("yes")) {
											
											if (ArrayOfAttackResult.attackResult[0] >= 12 && ArrayOfAttackResult.attackResult[0] <= 19) {
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);
														centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ", + 2 = " + (ArrayOfAttackResult.attackResult[0] + 2) + ".");
									  	  	  			
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
														  		centerscrolltext.startAnimation(animAlphaText);
																centerscrolltext.append("\n" + "> The computer's attack hits.");				
												
																final Handler h5 = new Handler();
													  	  	  	h5.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
														  	  	  		if (dodgeBlowSpell[0] > 0) {
																			/*
																			centerscrolltext.setVisibility(View.VISIBLE);													
																	  		centerscrolltext.startAnimation(animAlphaText);			  		
																			centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
																			*/
																			
																			AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
																  			
																			alert.setCancelable(false);
																			
																  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your Dodge spell?");
																  	    	/*
																  	    	alert.setMessage("something");
																  	    	*/	  	    	
																  	    	
																  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
																  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
																  		    		
																  		    		/*
																  		    		//NEED THIS??????????????????
																  		    		if (dodgeBlowSpell[0] < 1) {
																  		    			
																  		    			hideNavigation();
																  						
																  						centerscrolltext.setVisibility(View.VISIBLE);													
																  				  		centerscrolltext.startAnimation(animAlphaText);			  		
																  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
																  						
																  						//break;
																  					}
																  		    		*/
																  		    		dialog.dismiss();																  		    		
																  		    		
																  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
																  		    		
																  		    		centerscrolltext.setVisibility(View.VISIBLE);													
																			  		centerscrolltext.startAnimation(animAlphaText);
																					centerscrolltext.append("\n" + "> You dodge the hit.");
																  		    		
																  		    		skillsCheck();
																  		    		
																	  		    	// Use a blank drawable to hide the imageview animation:
																	  		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
																	  		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
																	  		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
																	  		    	img.setImageResource(R.drawable.twentytwentyblank);
																  		    		
																  		    		dodgeGraphic();
																  		    		
																  		    		final Handler h = new Handler();
																		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																		  	  	  			
																		  	  	  		@Override
																			  	  	  	public void run() {
																		  	  	  			
																			  	  	  		//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
																		  	  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
																		  	  	  			
																		  	  	  			stopGraphics();
																		  	  	  			
																			  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																			  	    			
																			  	    			gameEngineComputerFirst2();   							
																			  				}
														
																						  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																						  						
																						  		turn();    							
																						  	}
																						  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																						  	    			
																						  		computerHastePartTwo();   							
																						  	}
																	
																						  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																						  						
																						  		computerHastePartTwo();  							
																						  	}								  		    		
																		  					//return;
																			  	  	  	}
																		  	  	  	}, 6000);											  		    		
																  		    	}
																  	    	});
																  	    	
																  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
																          	  public void onClick(DialogInterface dialog, int whichButton) {
																          		  
																          		  	//hideNavigation();
																          		  	
																          		  	computerDamage();								          		  	
																          		  	//return;
																          		  	
																          		  	dialog.dismiss();
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
														  	  	  		
														  	  	  		else {
														  	  	  			
															  	  	  		computerDamage();
																			//return;						  	  	  			
														  	  	  		}
														  	  	  	}
													  	  	  	}, 2000);
												  	  	  	}
											  	  	  	}, 2000);										  	  	  		
										  	  	  	}
									  	  	  	}, 2000);																			
											}
											
											if (ArrayOfAttackResult.attackResult[0] < 12 && ArrayOfAttackResult.attackResult[0] > 1) {
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);
												  		centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ", + 2 = " + (ArrayOfAttackResult.attackResult[0] + 2) + ".");
									  	  	  			
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
														  		centerscrolltext.startAnimation(animAlphaText);
																centerscrolltext.append("\n" + "> The computer's attack misses.");
																
																final Handler h6 = new Handler();
													  	  	  	h6.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
														  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
														  	    			
														  	    			gameEngineComputerFirst2();   							
														  				}
									
																	  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																	  						
																	  		turn();    							
																	  	}
																	  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																	  	    			
																	  		computerHastePartTwo();   							
																	  	}
												
																	  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																	  						
																	  		computerHastePartTwo();    							
																	  	}					  	  	  			
														  	  	  	}
													  	  	  	}, 2000);								
																//return;
												  	  	  	}
											  	  	  	}, 2000);										  	  	  											  	  	  			
										  	  	  	}
									  	  	  	}, 2000);											
											}
											
											if (ArrayOfAttackResult.attackResult[0] <= 1) {
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		computerCriticalMiss();
														//return;
										  	  	  	}
									  	  	  	}, 2000);												
											}
											
											// NEED THIS?:
											//return;
										}
						  	  	  	}
					  	  	  	}, 2000);				  	  	  		
				  	  	  	}
			  	  	  	}, 2000);				  	  	  														
					}
					
					if (canHasDisarmed[1].equals("yes")) {						
			  	  	  			
		  	  	  		ArrayOfAttackResult.attackResult[0] = (int) (Math.random() * 20) + 1;
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> The computer attacks...");
						
						// for(int x = 0; x < 100; --x)
						// {}
						
						final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			computerRolls20SidedDie();
			  	  	  			
				  	  	  		final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			/*
						  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> The computer rolls a " + ArrayOfAttackResult.attackResult[0] + ".");
										*/
							
										if (ArrayOfAttackResult.attackResult[0] >= 20) {
											
											computerCriticalHit();
											return;
										}
										
										if (canHasDisarmed[0].equals("no")) {
										
											if (ArrayOfAttackResult.attackResult[0] >= 15 && ArrayOfAttackResult.attackResult[0] <= 19) { // -1 to-hit for being disarmed.
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);
														centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ", - 1 = " + (ArrayOfAttackResult.attackResult[0] - 1) + ".");
									  	  	  			
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
														  		centerscrolltext.startAnimation(animAlphaText);
																centerscrolltext.append("\n" + "> The computer's punch hits.");				
												
																final Handler h = new Handler();
													  	  	  	h.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
														  	  	  		if (dodgeBlowSpell[0] > 0) {
																			/*
																			centerscrolltext.setVisibility(View.VISIBLE);													
																	  		centerscrolltext.startAnimation(animAlphaText);			  		
																			centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
																			*/
																			
																			AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
																  			
																			alert.setCancelable(false);
																			
																  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your Dodge spell?");
																  	    	/*
																  	    	alert.setMessage("something");
																  	    	*/	  	    	
																  	    	
																  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
																  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
																  		    		
																  		    		/*
																  		    		//NEED THIS??????????????????
																  		    		if (dodgeBlowSpell[0] < 1) {
																  		    			
																  		    			hideNavigation();
																  						
																  						centerscrolltext.setVisibility(View.VISIBLE);													
																  				  		centerscrolltext.startAnimation(animAlphaText);			  		
																  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
																  						
																  						//break;
																  					}
																  		    		*/
																  		    		dialog.dismiss();
																  		    		
																  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
																  		    		
																  		    		centerscrolltext.setVisibility(View.VISIBLE);													
																			  		centerscrolltext.startAnimation(animAlphaText);
																					centerscrolltext.append("\n" + "> You dodge the hit.");
																  		    		
																  		    		skillsCheck();
																  		    		
																  		    		// Use a blank drawable to hide the imageview animation:
																	  		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
																	  		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
																	  		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
																	  		    	img.setImageResource(R.drawable.twentytwentyblank);
																  		    		
																  		    		dodgeGraphic();
																  		    		
																  		    		final Handler h = new Handler();
																		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																		  	  	  			
																		  	  	  		@Override
																			  	  	  	public void run() {
																		  	  	  			
																			  	  	  		//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
																		  	  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
																		  	  	  			
																		  	  	  			stopGraphics();
																		  	  	  			
																			  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																			  	    			
																			  	    			gameEngineComputerFirst2();   							
																			  				}
														
																						  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																						  						
																						  		turn();    							
																						  	}
																						  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																						  	    			
																						  		computerHastePartTwo();   							
																						  	}
																	
																						  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																						  						
																						  		computerHastePartTwo();    							
																						  	}								  		    		
																		  					//return;
																			  	  	  	}
																		  	  	  	}, 6000);													  		    		
																  		    	}
																  	    	});
																  	    	
																  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
																          	  public void onClick(DialogInterface dialog, int whichButton) {
																          		  
																          		  	//hideNavigation();
																          		  	
																          		  	computerDamage();								          		  	
																          		  	//return;
																          		  	
																          		  	dialog.dismiss();
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
														  	  	  		
														  	  	  		else {
														  	  	  			
															  	  	  		computerDamage();
																			//return;						  	  	  			
														  	  	  		}
														  	  	  	}
													  	  	  	}, 2000);
												  	  	  	}
											  	  	  	}, 2000);										  	  	  		
										  	  	  	}
									  	  	  	}, 2000);																				
											}
											
											if (ArrayOfAttackResult.attackResult[0] < 15 && ArrayOfAttackResult.attackResult[0] >= 1) {
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);
												  		centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ", - 1 = " + (ArrayOfAttackResult.attackResult[0] - 1) + ".");
									  	  	  			
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
														  		centerscrolltext.startAnimation(animAlphaText);
																centerscrolltext.append("\n" + "> The computer's punch misses.");
																
																final Handler h = new Handler();
													  	  	  	h.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
														  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
														  	    			
														  	    			gameEngineComputerFirst2();   							
														  				}
									
																	  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																	  						
																	  		turn();    							
																	  	}
																	  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																	  	    			
																	  		computerHastePartTwo();   							
																	  	}
												
																	  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																	  						
																	  		computerHastePartTwo();    							
																	  	}					  	  	  			
														  	  	  	}
													  	  	  	}, 2000);								
																//return;
												  	  	  	}
											  	  	  	}, 2000);										  	  	  		
										  	  	  	}
									  	  	  	}, 2000);												
											}
											
											// NEW
											//	CAN YOU CRITICL MISS A PUNCH???????????????????????????????????? -----DISARM AGAIN TO MAINTAIN BALANCE.
											// for now i'll do no rolls to keep it simpler, but in future might go w roll to hurt yourself (ie stumble & fall) but no roll to lose weapon.
											/*
											if (attackResult <= 1) {
												computerCriticalMiss();
												return;
											}
											*/
											
											// NEED THIS?:
											//return;						
										}
										
										if (canHasDisarmed[0].equals("yes")) {
											
											if (ArrayOfAttackResult.attackResult[0] >= 13 && ArrayOfAttackResult.attackResult[0] <= 19) { // -1 to-hit for being disarmed but, +2 because computer is disarmed (+1 total)
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);
												  		centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ", + 1 = " + (ArrayOfAttackResult.attackResult[0] + 1) + ".");
									  	  	  			
												  		final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
														  		centerscrolltext.startAnimation(animAlphaText);
																centerscrolltext.append("\n" + "> The computer's punch hits.");				
												
																final Handler h = new Handler();
													  	  	  	h.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
														  	  	  		if (dodgeBlowSpell[0] > 0) {
																			/*
																			centerscrolltext.setVisibility(View.VISIBLE);													
																	  		centerscrolltext.startAnimation(animAlphaText);			  		
																			centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
																			*/
																			
																			AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
																  			
																			alert.setCancelable(false);
																			
																  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your Dodge spell?");
																  	    	/*
																  	    	alert.setMessage("something");
																  	    	*/	  	    	
																  	    	
																  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
																  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
																  		    		
																  		    		/*
																  		    		//NEED THIS??????????????????
																  		    		if (dodgeBlowSpell[0] < 1) {
																  		    			
																  		    			hideNavigation();
																  						
																  						centerscrolltext.setVisibility(View.VISIBLE);													
																  				  		centerscrolltext.startAnimation(animAlphaText);			  		
																  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
																  						
																  						//break;
																  					}
																  		    		*/
																  		    		dialog.dismiss();																  		    		
																  		    		
																  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
																  		    		
																  		    		centerscrolltext.setVisibility(View.VISIBLE);													
																			  		centerscrolltext.startAnimation(animAlphaText);
																					centerscrolltext.append("\n" + "> You dodge the hit.");
																  		    		
																  		    		skillsCheck();
																  		    		
																  		    		// Use a blank drawable to hide the imageview animation:
																	  		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
																	  		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
																	  		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
																	  		    	img.setImageResource(R.drawable.twentytwentyblank);
																  		    		
																  		    		dodgeGraphic();
																  		    		
																  		    		final Handler h = new Handler();
																		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																		  	  	  			
																		  	  	  		@Override
																			  	  	  	public void run() {
																		  	  	  			
																			  	  	  		//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
																		  	  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
																		  	  	  			
																		  	  	  			stopGraphics();
																		  	  	  			
																			  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																			  	    			
																			  	    			gameEngineComputerFirst2();   							
																			  				}
														
																						  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																						  						
																						  		turn();    							
																						  	}
																						  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																						  	    			
																						  		computerHastePartTwo();   							
																						  	}
																	
																						  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																						  						
																						  		computerHastePartTwo();    							
																						  	}								  		    		
																		  					//return;
																			  	  	  	}
																		  	  	  	}, 6000);													  		    		
																  		    	}
																  	    	});
																  	    	
																  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
																          	  public void onClick(DialogInterface dialog, int whichButton) {
																          		  
																          		  	//hideNavigation();
																          		  	
																          		  	computerDamage();								          		  	
																          		  	//return;
																          		  	
																          		  	dialog.dismiss();
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
														  	  	  		
														  	  	  		else {
														  	  	  			
															  	  	  		computerDamage();
																			//return;						  	  	  			
														  	  	  		}
														  	  	  	}
													  	  	  	}, 2000);
												  	  	  	}
											  	  	  	}, 2000);										  	  	  		
										  	  	  	}
									  	  	  	}, 2000);												
											}
											
											if (ArrayOfAttackResult.attackResult[0] < 13 && ArrayOfAttackResult.attackResult[0] >= 1) {
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);
												  		centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ", + 1 = " + (ArrayOfAttackResult.attackResult[0] + 1) + ".");
									  	  	  			
												  		final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
														  		centerscrolltext.startAnimation(animAlphaText);
																centerscrolltext.append("\n" + "> The computer's punch misses.");
																
																final Handler h = new Handler();
													  	  	  	h.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
														  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
														  	    			
														  	    			gameEngineComputerFirst2();   							
														  				}
									
																	  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																	  						
																	  		turn();    							
																	  	}
																	  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																	  	    			
																	  		computerHastePartTwo();   							
																	  	}
												
																	  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																	  						
																	  		computerHastePartTwo();    							
																	  	}					  	  	  			
														  	  	  	}
													  	  	  	}, 2000);								
																//return;
												  	  	  	}
											  	  	  	}, 2000);									  	  	  		
										  	  	  	}
									  	  	  	}, 2000);												
											}
											
											// NEW
											//	CAN YOU CRITICL MISS A PUNCH???????????????????????????????????? -----DISARM AGAIN TO MAINTAIN BALANCE.
											// for now i'll do no rolls to keep it simpler, but in future might go w roll to hurt yourself (ie stumble & fall) but no roll to lose weapon.
											/*
											if (attackResult <= 1) {
												computerCriticalMiss();
												return;
											}
											*/
											
											// NEED THIS?:
											//return;						
										}
						  	  	  	}
					  	  	  	}, 2000);				  	  	  		
				  	  	  	}
			  	  	  	}, 2000);
				  	  	  												
					}
				}
				
				// BLESS
				else if ((computerAction >= 41 && computerAction <= 50)	&& (blessSpell[1] == 1 && iscomputerhasteused.equals("no"))) {
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer uses a bless spell...");
							  	  	  			
	  	  	  		computerBless();
					return;			  	  	  						
				}				
				
				
				// HASTE IF DISARMED
				// Determined at beginning of computer's turn and resolved in computerDisarmedAction().
				
				
				// HASTE
				else if ((computerAction >= 51 && computerAction <= 75)	&& (hasteSpell[1] > 0 && iscomputerhasteused.equals("no"))) {
					
					// so computer doesn't use a haste during a haste.
					//usedHaste[1] = "yes"; 
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> The computer uses a haste spell...");							
		  	  	  			
	  	  	  		computerHaste();
					return;	  	  	  						
				}				
				
				
				/*
				// ATTACK DURING HASTE
				// CAN ONLY ATTACK ON COMPUTER'S 2ND HASTE. MOVED ATTACK STUFF TO computerHastePartTwo().
				else if ((computerAction >= 41 && computerAction <= 75)	&& (iscomputerhasteused.equals("yes"))) { // WAS 51, BUT HAD A GAP BETWEEN 41 TO 51 WHEN COMP USING HASTE.
				// in case the roll is between 51 & 75 & the computer used a haste spell.								
				}
				*/				
				
				
				/*
				// ATTACK IF NO HASTES LEFT
				// MOVED TO ATTACK (SAME AS ATTACK)
				else if ((computerAction >= 51 && computerAction <= 75)	&& (hasteSpell[1] < 1 && iscomputerhasteused.equals("no"))) {
				// in case the roll is between 51 & 75 & the computer used both it's haste spells.					
				}
				*/
				
				
				// DISARM
				else if ((computerAction > 75 && computerAction <= 100) && iscomputerhasteused.equals("no") && canHasDisarmed[0].equals("no")) {
					
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
		//return playerNumberAttacked;
	}		
	
	public void computerCriticalHit() { //WAS int					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
	  			
		  		// Use a blank drawable to hide the imageview animation:
		  		// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
		  		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
		  		img.setBackgroundResource(R.drawable.twentytwentyblank);
		  		img.setImageResource(R.drawable.twentytwentyblank);
				
	  			criticalHitGraphic();					  	  	  		
			  	  	  	
				  	  	  		
	  	  	  	final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		//final TextView criticalHitGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//criticalHitGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
		  	  	  		if (dodgeBlowSpell[0] > 0) {
	  						/*
	  						centerscrolltext.setVisibility(View.VISIBLE);													
	  				  		centerscrolltext.startAnimation(animAlphaText);			  		
	  						centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
	  						*/
	  						
	  						AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
	  			  			
	  						alert.setCancelable(false);
	  						
	  			  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your Dodge spell?");
	  			  	    	/*
	  			  	    	alert.setMessage("something");
	  			  	    	*/	  	    	
	  			  	    	
	  			  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	  			  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
	  			  		    		
	  			  		    		//NEED THIS??????????????????
	  			  		    		/*
	  			  		    		if (dodgeBlowSpell[0] < 1) {
	  			  		    			
	  			  		    			hideNavigation();
	  			  						
	  			  						centerscrolltext.setVisibility(View.VISIBLE);													
	  			  				  		centerscrolltext.startAnimation(animAlphaText);			  		
	  			  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
	  			  						
	  			  						//break;
	  			  					}
	  			  		    		*/
	  			  		    		dialog.dismiss();
	  			  		    		
	  			  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
	  			  		    		
		  			  		    	centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> You dodge the hit.");
	  			  		    		
	  			  		    		skillsCheck();
	  			  		    		
	  			  		    		// Use a blank drawable to hide the imageview animation:
					  		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
					  		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
					  		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
					  		    	img.setImageResource(R.drawable.twentytwentyblank);
	  			  		    		
	  			  		    		dodgeGraphic();			  			  		    		
	  			  		    		
	  			  		    		final Handler h = new Handler();
	  					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  					  	  	  			
	  					  	  	  		@Override
	  						  	  	  	public void run() {
	  					  	  	  			
			  					  	  	  	//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
						  	  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
	  					  	  	  			
	  					  	  	  			stopGraphics();
	  					  	  	  			
	  						  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
	  						  	    			
	  						  	    			gameEngineComputerFirst2();   							
	  						  				}
	  		
	  									  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
	  									  						
	  									  		turn();    							
	  									  	}
	  									  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
	  									  	    			
	  									  		computerHastePartTwo();   							
	  									  	}
	  				
	  									  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
	  									  						
	  									  		computerHastePartTwo();    							
	  									  	}				  	  	  			
	  						  	  	  	}
	  					  	  	  	}, 6000);
	  			  		    		
	  			  					//return;
	  			  		    	}
	  			  	    	});
	  			  	    	
	  			  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
	  			          	  public void onClick(DialogInterface dialog, int whichButton) {		          		  
	  			          		  
	  			          		  //hideNavigation();	          		  	
	  			          		  	
	  			          		  computerCriticalHitDamage();
	  			          		  
	  			          		  dialog.dismiss();
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
	  		  			
	  		  			else {
	  		  				
	  		  				computerCriticalHitDamage();
	  		  				//return;	  				
	  		  			}
		  	  	  	}
	  	  	  	}, 6000);	  	  	  		  			
  	  	    }
		});		
		//return playerNumberAttacked;
	}
	
	public void computerMightyBlow() {// WAS int[]					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		//Globals g = Globals.getInstance();
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
		SharedPreferences.Editor editor = preferences.edit();		
		editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
		editor.apply();
		
		//attackDamage = g.setDataAttackDamage((int) ((Math.random() * 6) + 1)); 
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);				
	  			
	  			preventattackdamagediefromleaking = "off";
	  			
	  			mightyBlowGraphic();  					  	  	  	
						  	  	
			  	  	  			
  	  	  		final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		//final TextView mightyBlowGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//mightyBlowGraphic.setVisibility(View.INVISIBLE);
		  	  	  		
		  	  	  		stopGraphics();
		  	  	  		
		  	  	  		
	  	  	  			computerRolls6SidedDie();
	  	  	  			
	  	  	  			
		  	  	  		final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			preventattackdamagediefromleaking = "on";				  	  	  		
			  	  	  			
			  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
			  	  	  			//SharedPreferences.Editor editor = preferences.edit();
			  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + " for damage.");
								
								final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
					  	  	  			//SharedPreferences.Editor editor = preferences.edit();
					  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
					  	  	  			
						  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);			  		
										centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (attackDamage * 2) + ".");
										
										/*
										for (int x = 0; x < 1000; --x)// To give human time to read.
										{
										}
										*/
										
										ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - (attackDamage * 2);
										
										
										TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
						    			playerHitPointsTextView.setTypeface(typeFace);
						    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
						    			//playerHitPointsTextView.bringToFront();
						    			
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
								  	  	  		if (ArrayOfHitPoints.hitpoints[0] == 0) {
													
													centerscrolltext.setVisibility(View.VISIBLE);													
											  		centerscrolltext.startAnimation(animAlphaText);			  		
													centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been knocked unconscious!");
													
													/*
													AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
												    
													alert.setCancelable(false);
													
													alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious.");
										  	    	
										  	    	//alert.setMessage("something");
										  	    		    	
											    	
											    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
												    	public void onClick(DialogInterface dialog, int whichButton) {
												    */		
												    		//hideNavigation();
												    
													final Handler h = new Handler();
										  	  	  	h.postDelayed(new Runnable() {		  	  	  			
										  	  	  			
										  	  	  		@Override
											  	  	  	public void run() {
										  	  	  			
											  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
											  	    			
											  	    			gameEngineComputerFirst2();   							
											  				}
							
														  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
														  						
														  		turn();    							
														  	}
											  	  	  	}
										  	  	  	}, 2000);									    		
														  	
														  	//dialog.dismiss();
												    	//}
											    	//});
											    	
											    	//alert.show(); 
													
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
												      
													alert.setCancelable(false);
													
													alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain.");
										  	    	/*
										  	    	alert.setMessage("something");
										  	    	*/	    	
											    	
											    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
												    	public void onClick(DialogInterface dialog, int whichButton) {
												    		
												    		//hideNavigation();
												    		
												    		playerDeadYet[0] = "yes";
												    		
												    		gameOverCheck();
												    		
												    		dialog.dismiss();
												    	}
											    	});
											    	
											    	alert.show();			
													
													/*
													System.out.print("Press a key to continue... ");
													Scanner input = new Scanner(System.in);
													input.nextLine();
													*/							
												}
												
												else {
													
													if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
									  	    			
									  	    			gameEngineComputerFirst2();   							
									  				}
					
												  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
												  						
												  		turn();    							
												  	}
												}	  	  	  			
								  	  	  	}
							  	  	  	}, 2000);
						  	  	  	}
					  	  	  	}, 2000);							
				  	  	  	}
			  	  	  	}, 2000);
	  	  	  		}
			  	}, 6000);// CHANGED FROM 3000 TO 6000 BECAUSE DICE WERE COMING IN BEFORE SKILL GRAPHIC FINISHED. 	  	  	  									
  	  	    }
		});
		//return ArrayOfHitPoints.hitpoints;
	}
	
	public void computerDamage() {// WAS int[]					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	  			
	  			
	  			
		  		// Use a blank drawable to hide the imageview animation:
		  		// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
		  		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
		  		img.setBackgroundResource(R.drawable.twentytwentyblank);
		  		img.setImageResource(R.drawable.twentytwentyblank);
	  			
	  			
	  			// IF MB
	  			if (mightyBlowSpell[1] > 0 && iscomputerhasteused.equals("no") && iscomputerblessrolled.equals("no")) {	  				
		  	  	  			
	  	  	  		int computerUseMightyBlow = (int) ((Math.random() * 20) + 1);
					// NEED 1 SCENARIO FOR HUMAN PLAYER HP ABOVE 12 AND 1 SCENARIO FOR HUMAN PLAYER HP BELOW 12
					
					if (ArrayOfHitPoints.hitpoints[0] >= 12) {
						// using 12 just because if comp roll 6 for damage * 2 = 12, comp can kill human player.
					
						if (computerUseMightyBlow >= 15) { // less likely for comp to use MB because human HP > 12.						
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> The computer uses mighty blow.");
							
							/*
							for (int x = 0; x < 1000; --x) {
							}
							*/
							
							mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
							
							skillsCheck();				

							computerMightyBlow();
							return;
						}
						
						// NO MB
						else {
							
							iscomputerblessrolled = "no";			  				
			  				
							SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
							SharedPreferences.Editor editor = preferences.edit();		
							editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
							editor.apply();
			  				
			  				preventattackdamagediefromleaking = "off";
			  				
			  				centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer rolls for damage...");	  				
			  				
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			computerRolls6SidedDie();
				  	  	  			
					  	  	  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			preventattackdamagediefromleaking = "on";						  	  	  			
						  	  	  			
						  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
						  	  	  			//SharedPreferences.Editor editor = preferences.edit();
						  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
						  	  	  			
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + " for damage.");
											
											/*
											for (int x = 0; x < 1000; --x)// To give human time to read.
											{
											}
											*/
											
											ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - attackDamage;
											
											
											TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
							    			playerHitPointsTextView.setTypeface(typeFace);
							    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
							    			//playerHitPointsTextView.bringToFront();
							    			
											
											final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
									  	  	  		if (ArrayOfHitPoints.hitpoints[0] == 0) {
														
														centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);			  		
														centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0]	+ ", you have been knocked unconscious!");
														
														/*
														AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
													    
														alert.setCancelable(false);
														
														alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious.");
											  	    	
											  	    	//alert.setMessage("something");
											  	    		    	
												    	
												    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
													    	public void onClick(DialogInterface dialog, int whichButton) {
													    */		
													    		//hideNavigation();
													    
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
												  	    			
												  	    			gameEngineComputerFirst2();   							
												  				}
							
															  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
															  						
															  		turn();    							
															  	}
															  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
															  	    			
															  		computerHastePartTwo();   							
															  	}
										
															  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
															  						
															  		computerHastePartTwo();    							
															  	}
												  	  	  	}
											  	  	  	}, 2000);											    		
															  	
															  	//dialog.dismiss();
													    	//}
												    	//});
												    	
												    	//alert.show();
														
														/*
														System.out.print("Press a key to continue... ");
														input.nextLine();
														*/
													}
											
													else if (ArrayOfHitPoints.hitpoints[0] < 0) {
														
														
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
													    
														alert.setCancelable(false);
														
														alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain.");
											  	    	/*
											  	    	alert.setMessage("something");
											  	    	*/	    	
												    	
												    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
													    	public void onClick(DialogInterface dialog, int whichButton) {
													    		
													    		//hideNavigation();
													    		
													    		playerDeadYet[0] = "yes";
													    		
													    		gameOverCheck();
													    		
													    		dialog.dismiss();
													    	}
												    	});
												    	
												    	alert.show();
														
														/*
														System.out.print("Press a key to continue... ");
														Scanner input = new Scanner(System.in);
														input.nextLine();
														*/						
													}
													
													else {
														
														if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
										  	    			
										  	    			gameEngineComputerFirst2();   							
										  				}
							
													  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
													  						
													  		turn();    							
													  	}
													  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
													  	    			
													  		computerHastePartTwo();   							
													  	}
							
													  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
													  						
													  		computerHastePartTwo();    							
													  	}
													}									  	  	  		
									  	  	  	}
								  	  	  	}, 2000);
							  	  	  	}
						  	  	  	}, 2000);			  	  	  		
					  	  	  	}
				  	  	  	}, 2000);
				  	  	  	//NEED THIS?:
				  	  	  	return;
						}
					}
					
					if (ArrayOfHitPoints.hitpoints[0] < 12) { // see above for explanation
					
						if (computerUseMightyBlow < 15) {
							// MORE likely for comp to use MB because human HP < 12.
						
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> The computer uses mighty blow.");
							
							/*
							for (int x = 0; x < 1000; --x) {
							}
							*/
							
							mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
							
							skillsCheck();							
							
							computerMightyBlow();
							return;
						}
						
						// NO MB
						else {
							
							iscomputerblessrolled = "no";
			  				
							SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
							SharedPreferences.Editor editor = preferences.edit();		
							editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
							editor.apply();
			  				
			  				preventattackdamagediefromleaking = "off";
			  				
			  				centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer rolls for damage...");	  				
			  				
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			computerRolls6SidedDie();
				  	  	  			
					  	  	  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			preventattackdamagediefromleaking = "on";
						  	  	  			
						  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
						  	  	  			//SharedPreferences.Editor editor = preferences.edit();
						  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + " for damage.");
											
											/*
											for (int x = 0; x < 1000; --x)// To give human time to read.
											{
											}
											*/
											
											ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - attackDamage;
											
											
											TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
							    			playerHitPointsTextView.setTypeface(typeFace);
							    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
							    			//playerHitPointsTextView.bringToFront();
							    			
											
											final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
									  	  	  		if (ArrayOfHitPoints.hitpoints[0] == 0) {
														
														centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);			  		
														centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0]	+ ", you have been knocked unconscious!");
														
														/*
														AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
													    
														alert.setCancelable(false);
														
														alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious.");
											  	    	
											  	    	//alert.setMessage("something");
											  	    		    	
												    	
												    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
													    	public void onClick(DialogInterface dialog, int whichButton) {
													    */		
													    		//hideNavigation();
													    
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
												  	    			
												  	    			gameEngineComputerFirst2();   							
												  				}
							
															  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
															  						
															  		turn();    							
															  	}
															  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
															  	    			
															  		computerHastePartTwo();   							
															  	}
										
															  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
															  						
															  		computerHastePartTwo();    							
															  	}
												  	  	  	}
											  	  	  	}, 2000);										    		
															  	
															  	//dialog.dismiss();
													    	//}
												    	//});
												    	
												    	//alert.show();
														
														/*
														System.out.print("Press a key to continue... ");
														input.nextLine();
														*/
													}
											
													else if (ArrayOfHitPoints.hitpoints[0] < 0) {
														
														
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
													    
														alert.setCancelable(false);
														
														alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain.");
											  	    	/*
											  	    	alert.setMessage("something");
											  	    	*/	    	
												    	
												    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
													    	public void onClick(DialogInterface dialog, int whichButton) {
													    		
													    		//hideNavigation();
													    		
													    		playerDeadYet[0] = "yes";
													    		
													    		gameOverCheck();
													    		
													    		dialog.dismiss();
													    	}
												    	});
												    	
												    	alert.show();
														
														/*
														System.out.print("Press a key to continue... ");
														Scanner input = new Scanner(System.in);
														input.nextLine();
														*/						
													}
													
													else {
														
														if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
										  	    			
										  	    			gameEngineComputerFirst2();   							
										  				}
							
													  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
													  						
													  		turn();    							
													  	}
													  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
													  	    			
													  		computerHastePartTwo();   							
													  	}
							
													  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
													  						
													  		computerHastePartTwo();    							
													  	}
													}		  	  	  			
									  	  	  	}
								  	  	  	}, 2000);
							  	  	  	}
						  	  	  	}, 2000);			  	  	  		
					  	  	  	}
				  	  	  	}, 2000);
				  	  	  	//NEED THIS?:
				  	  	  	return;
						}
					}			  	  	  						
				}
	  			
	  			// IF NO MB LEFT
	  			else {
	  				
	  				iscomputerblessrolled = "no";
	  				
	  				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
	  				SharedPreferences.Editor editor = preferences.edit();		
	  				editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
	  				editor.apply();
	  				
	  				preventattackdamagediefromleaking = "off";
	  				
	  				centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> The computer rolls for damage...");	  				
	  				
					
					final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
		  	  	  			computerRolls6SidedDie();
		  	  	  			
			  	  	  		final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			preventattackdamagediefromleaking = "on";
				  	  	  			
				  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
				  	  	  			//SharedPreferences.Editor editor = preferences.edit();
				  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
				  	  	  			
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);			  		
									centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + " for damage.");
									
									/*
									for (int x = 0; x < 1000; --x)// To give human time to read.
									{
									}
									*/
									
									ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - attackDamage;
									
									
									TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
					    			playerHitPointsTextView.setTypeface(typeFace);
					    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
					    			//playerHitPointsTextView.bringToFront();
					    			
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		if (ArrayOfHitPoints.hitpoints[0] == 0) {
												
												centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);			  		
												centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0]	+ ", you have been knocked unconscious!");
												
												/*
												AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
											    
												alert.setCancelable(false);
												
												alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious.");
									  	    	
									  	    	//alert.setMessage("something");
									  	    		    	
										    	
										    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											    	public void onClick(DialogInterface dialog, int whichButton) {
											    */		
											    		//hideNavigation();
											    		
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
										  	    			
										  	    			gameEngineComputerFirst2();   							
										  				}
					
													  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
													  						
													  		turn();    							
													  	}
													  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
													  	    			
													  		computerHastePartTwo();   							
													  	}
								
													  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
													  						
													  		computerHastePartTwo();    							
													  	}
										  	  	  	}
									  	  	  	}, 2000);											
													  	
													  	//dialog.dismiss();
											    	//}
										    	//});
										    	
										    	//alert.show();
												
												/*
												System.out.print("Press a key to continue... ");
												input.nextLine();
												*/
											}
									
											else if (ArrayOfHitPoints.hitpoints[0] < 0) {
												
												
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
											    
												alert.setCancelable(false);
												
												alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain.");
									  	    	/*
									  	    	alert.setMessage("something");
									  	    	*/	    	
										    	
										    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											    	public void onClick(DialogInterface dialog, int whichButton) {
											    		
											    		//hideNavigation();
											    		
											    		playerDeadYet[0] = "yes";
											    		
											    		gameOverCheck();
											    		
											    		dialog.dismiss();
											    	}
										    	});
										    	
										    	alert.show();
												
												/*
												System.out.print("Press a key to continue... ");
												Scanner input = new Scanner(System.in);
												input.nextLine();
												*/						
											}
											
											else {
												
												if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
								  	    			
								  	    			gameEngineComputerFirst2();   							
								  				}
					
											  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
											  						
											  		turn();    							
											  	}
											  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
											  	    			
											  		computerHastePartTwo();   							
											  	}
					
											  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
											  						
											  		computerHastePartTwo();    							
											  	}
											}		  	  	  			
							  	  	  	}
						  	  	  	}, 2000);
					  	  	  	}
				  	  	  	}, 2000);			  	  	  		
			  	  	  	}
		  	  	  	}, 2000);									
	  			}		
  	  	    }
		});
		//return ArrayOfHitPoints.hitpoints;
	}
	
	public void computerCure() {//WAS int[]			
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				cureSpell[1] = cureSpell[1] - 1;
				
				skillsCheck();
				
				
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putInt("cureResult", (int) ((Math.random() * 6) + 1));
				editor.apply();
				
				preventcureresultdiefromleaking = "off";
				
				
				cureGraphic();	  	  					  	  	  	
						  	  	
						  	  	
		  	  	final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		//final TextView cureGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
	  	  	  			//cureGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
		  	  	  		computerRolls6SidedDie();
		  	  			
		  	  			
		  	  	  		final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			preventcureresultdiefromleaking = "on";
			  	  	  			
			  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
			  	  	  			//SharedPreferences.Editor editor = preferences.edit();
			  	  	  			int cureResult = preferences.getInt("cureResult", 0);
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> The computer rolls " + cureResult + "."); // WAS cure
								
								/*
								for (int x = 0; x < 1000; --x)// To give human time to read.
								{
								}
								*/
								
								ArrayOfHitPoints.hitpoints[1] = ArrayOfHitPoints.hitpoints[1] + cureResult; // WAS cure
								
								
								TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
				    			computerHitPointsTextView.setTypeface(typeFace);
				    			computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[1]));
				    			//computerHitPointsTextView.bringToFront();
				    			
								
								final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {					  	  	  			
					  	  	  			
						  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
						  	    			
						  	    			gameEngineComputerFirst2();   							
						  				}
					
									  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
									  						
									  		turn();    							
									  	}
									  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
									  	    			
									  		computerHastePartTwo();   							
									  	}
					
									  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
									  						
									  		computerHastePartTwo();    							
									  	}	  	  	  			
						  	  	  	}
					  	  	  	}, 2000);				
								//return;
				  	  	  	}
			  	  	  	}, 2000);
		  	  	  	}
	  	  	  	}, 6000);		  	  	  						  	  	  			
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
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
	  			
	  			iscomputerblessrolled = "yes";
	  			
				blessSpell[1] = blessSpell[1] - 1;
				
				skillsCheck();
				
				blessGraphic(); 	  	  				  	  	  	
				  	  	  		
				  	  	  		
	  	  	  	final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
	  	  	  			//final TextView blessGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
	  	  	  			//blessGraphic.setVisibility(View.INVISIBLE);			  	  	  			
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
		  	  	  		if (canHasDisarmed[0].equals("no")) {
		  					
							ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer player attacks...");
							
							// for(int x = 0; x < 100; --x)
							// {}
							
							ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
		  	  	  			img.bringToFront();
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {					  	  	  		
				  	  	  			
				  	  	  			computerRolls20SidedDie();
				  	  	  			
					  	  	  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> The computer player rolls " + ArrayOfAttackResult.attackResult[0] + ", +2 for the Bless Spell = " + (ArrayOfAttackResult.attackResult[0] + 2));
											
									
											if (ArrayOfAttackResult.attackResult[0] >= 20) {
												
												computerCriticalHit();
												return;
											}
									
											if (ArrayOfAttackResult.attackResult[0] >= 12 && ArrayOfAttackResult.attackResult[0] <= 19) {
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);			  		
														centerscrolltext.append("\n" + "> The computer's attack hits.");
														
														final Handler h3 = new Handler();
											  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		if (dodgeBlowSpell[0] > 0) {
																	/*
																	centerscrolltext.setVisibility(View.VISIBLE);													
															  		centerscrolltext.startAnimation(animAlphaText);			  		
																	centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
																	*/
																	
																	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
														  			
																	alert.setCancelable(false);
																	
														  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your Dodge spell?");
														  	    	/*
														  	    	alert.setMessage("something");
														  	    	*/	  	    	
														  	    	
														  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
														  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
														  		    		
														  		    		/*
														  		    		//NEED THIS??????????????????
														  		    		if (dodgeBlowSpell[0] < 1) {
														  		    			
														  		    			//hideNavigation();
														  						
														  						centerscrolltext.setVisibility(View.VISIBLE);													
														  				  		centerscrolltext.startAnimation(animAlphaText);			  		
														  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
														  						
														  						//break;
														  					}
														  		    		*/
														  		    		dialog.dismiss();
														  		    		
														  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
														  		    		
														  		    		centerscrolltext.setVisibility(View.VISIBLE);													
																	  		centerscrolltext.startAnimation(animAlphaText);
																			centerscrolltext.append("\n" + "> You dodge the hit.");
														  		    		
														  		    		skillsCheck();
														  		    		
														  		    		// Use a blank drawable to hide the imageview animation:
															  		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
															  		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
															  		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
															  		    	img.setImageResource(R.drawable.twentytwentyblank);
														  		    		
														  		    		dodgeGraphic();
														  		    		
														  		    		final Handler h = new Handler();
																  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																  	  	  			
																  	  	  		@Override
																	  	  	  	public void run() {
																  	  	  			
																	  	  	  		//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
																  	  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
																  	  	  			
																  	  	  			stopGraphics();
																  	  	  			
																	  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																	  	    			
																	  	    			gameEngineComputerFirst2();   							
																	  				}
												
																				  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																				  						
																				  		turn();
																				  	}
																				  									  		    		
																  					//return;
																	  	  	  	}
																  	  	  	}, 6000);														  		    		
														  		    	}
														  	    	});
														  	    	
														  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
														          	  public void onClick(DialogInterface dialog, int whichButton) {
														          		  
														          		  	//hideNavigation();
														          		  	
														          		  	computerDamage();								          		  	
														          		  	//return;
														          		  	
														          		  	dialog.dismiss();
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
												  	  	  		
												  	  	  		else {
												  	  	  			
													  	  	  		computerDamage();
																	//return;						  	  	  			
												  	  	  		}
												  	  	  	}
											  	  	  	}, 2000);															
														
														//computerDamage();
														//return;
										  	  	  	}
									  	  	  	}, 2000);										
											}
									
											if (ArrayOfAttackResult.attackResult[0] < 12 && ArrayOfAttackResult.attackResult[0] > 0) {
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);			  		
														centerscrolltext.append("\n" + "> The computer's attack misses.");
														
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
												  	    			
												  	    			gameEngineComputerFirst2();   							
												  				}
									
															  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
															  						
															  		turn();    							
															  	}			  	  	  			
												  	  	  	}
											  	  	  	}, 2000);
										  	  	  	}
									  	  	  	}, 2000);									
											}
											
											// don't critically miss when using bless.
											/*
											 * if (attackResult <= 1) { computerCriticalMiss(i, turn); }
											 */
											// return gameOn;
											
											//NEED THIS?:
											//return;			  	  	  			
							  	  	  	}
						  	  	  	}, 2000);		  	  	  			  	  	  			
				  	  	  		}
				  	  	  	}, 2000);				
						}			
						
						else if (canHasDisarmed[0].equals("yes")) {					
							
							ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
							
							int computerAttackResultAgainstDisarmed = (ArrayOfAttackResult.attackResult[0] + 2);
							
							final int computerAttackResultAgainstDisarmedPlusBless = (computerAttackResultAgainstDisarmed + 2);
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer player attacks...");
							
							// for(int x = 0; x < 100; --x)
							// {}
							
							ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
		  	  	  			img.bringToFront();
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			computerRolls20SidedDie();
				  	  	  			
					  	  	  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> The computer player rolls " + ArrayOfAttackResult.attackResult[0] + " +4 = " + computerAttackResultAgainstDisarmedPlusBless);
											
									
											if (ArrayOfAttackResult.attackResult[0] >= 20) {
												
												computerCriticalHit();
												return;
											}
									
											if (ArrayOfAttackResult.attackResult[0] >= 10 && ArrayOfAttackResult.attackResult[0] <= 19) {
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);			  		
														centerscrolltext.append("\n" + "> The computer's attack hits.");
														
														
														final Handler h3 = new Handler();
											  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		if (dodgeBlowSpell[0] > 0) {
																	/*
																	centerscrolltext.setVisibility(View.VISIBLE);													
															  		centerscrolltext.startAnimation(animAlphaText);			  		
																	centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
																	*/
																	
																	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
														  			
																	alert.setCancelable(false);
																	
														  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your Dodge spell?");
														  	    	/*
														  	    	alert.setMessage("something");
														  	    	*/	  	    	
														  	    	
														  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
														  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
														  		    		
														  		    		/*
														  		    		//NEED THIS??????????????????
														  		    		if (dodgeBlowSpell[0] < 1) {
														  		    			
														  		    			//hideNavigation();
														  						
														  						centerscrolltext.setVisibility(View.VISIBLE);													
														  				  		centerscrolltext.startAnimation(animAlphaText);			  		
														  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
														  						
														  						//break;
														  					}
														  		    		*/
														  		    		dialog.dismiss();
														  		    		
														  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
														  		    		
														  		    		centerscrolltext.setVisibility(View.VISIBLE);													
																	  		centerscrolltext.startAnimation(animAlphaText);
																			centerscrolltext.append("\n" + "> You dodge the hit.");
														  		    		
														  		    		skillsCheck();
														  		    		
														  		    		// Use a blank drawable to hide the imageview animation:
															  		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
															  		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
															  		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
															  		    	img.setImageResource(R.drawable.twentytwentyblank);
														  		    		
														  		    		dodgeGraphic();
														  		    		
														  		    		final Handler h = new Handler();
																  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																  	  	  			
																  	  	  		@Override
																	  	  	  	public void run() {
																  	  	  			
																	  	  	  		//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
																  	  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
																  	  	  			
																  	  	  			stopGraphics();
																  	  	  			
																	  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																	  	    			
																	  	    			gameEngineComputerFirst2();   							
																	  				}
												
																				  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																				  						
																				  		turn();
																				  	}
																				  									  		    		
																  					//return;
																	  	  	  	}
																  	  	  	}, 6000);														  		    		
														  		    	}
														  	    	});
														  	    	
														  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
														          	  public void onClick(DialogInterface dialog, int whichButton) {
														          		  
														          		  	//hideNavigation();
														          		  	
														          		  	computerDamage();								          		  	
														          		  	//return;
														          		  	
														          		  	dialog.dismiss();
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
												  	  	  		
												  	  	  		else {
												  	  	  			
													  	  	  		computerDamage();
																	//return;						  	  	  			
												  	  	  		}
												  	  	  	}
											  	  	  	}, 2000);															
														
														//computerDamage();
														//return;
										  	  	  	}
									  	  	  	}, 2000);										
											}
									
											if (ArrayOfAttackResult.attackResult[0] < 10 && ArrayOfAttackResult.attackResult[0] > 0) {
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);			  		
														centerscrolltext.append("\n" + "> The computer's attack misses.");
														
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
												  	    			
												  	    			gameEngineComputerFirst2();   							
												  				}
									
															  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
															  						
															  		turn();    							
															  	}			  	  	  			
												  	  	  	}
											  	  	  	}, 2000);
										  	  	  	}
									  	  	  	}, 2000);									
											}
											
											// don't critically miss when using bless.
											/*
											 * if (attackResult <= 1) { computerCriticalMiss(i, turn); }
											 */
											// return gameOn;
											
											//NEED THIS?:
											//return;				  	  	  			
							  	  	  	}
						  	  	  	}, 2000);			  	  	  		
					  	  	  	}
				  	  	  	}, 2000);					
						}
		  	  	  	}
	  	  	  	}, 6000);	  	  	  								
  	  	    }
		});	
	}
	
	public void computerHaste() {//WAS int[]
		// no bless because you can't use 2 spells in one turn.
		
		iscomputerhasteused = "yes";
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	
  	  	    	
	
				hasteSpell[1] = hasteSpell[1] - 1;
				
				skillsCheck();
				
				hasteGraphic();  	  	  					  	  	  	
						  	  	
						  	  	
				final Handler h = new Handler();
				h.postDelayed(new Runnable() {		  	  	  			
			  			
					@Override
			  	  	public void run() {
			  			
						//final TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  	  	  		//hasteGraphic.setVisibility(View.INVISIBLE);
			  			
						stopGraphics();
						
			  	  		centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);			  		
						centerscrolltext.append("\n" + "> TWO attacks...");
						
						/*
						System.out.println("     /\\___________           ___________/\\");
						System.out.println("/|---||___________\\   TWO   /___________||---|\\");
						System.out.println("\\|---||___________/ ATTACKS \\___________||---|/");
						System.out.println("     \\/                                 \\/");
						for (int x = 0; x < 1; --x) {
						}
						*/
						
						
						final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> FIRST attack...");
								
								/*				
								System.out.println();
								System.out.println("     /\\____________");
								System.out.println("/|---||_1st Attack_\\");
								System.out.println("\\|---||____________/");
								System.out.println("     \\/           ");
								System.out.println();
								*/
								
								final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
						  	  	  		computerAttack();								
										//return;
						  	  	  	}
					  	  	  	}, 2000);						
				  	  	  	}
			  	  	  	}, 2000);
			  	  	}
			  	}, 6000);	  	  	  								
  	  	    }
		});
	}
	
	public void computerHastePartTwo() {		
		
		iscomputerhasteused = "no";
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			// IN CASE DAMAGE IS ROLLED FROM 1ST ATTACK:
	  			// Use a blank drawable to hide the imageview animation:
	  			ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
	  			img.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
	  			img.setImageResource(R.drawable.sixsixrightleftrotateblank);
	  			
	  			// THIS IS WRONG - CAN GET 2ND ATTACK, YOU'RE JUST DISARMED:
				//if (canHasDisarmed[1] == "yes")// so if you critically miss & drop weapon you don't get 2nd attack.
				//{
				//	return;
				//}				  			
	  			
	  			TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
    			playerHitPointsTextView.setTypeface(typeFace);
    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
	  			
	  			
	  			centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> SECOND attack...");
				
				/*
				System.out.println("     /\\____________");
				System.out.println("/|---||_2nd Attack_\\");
				System.out.println("\\|---||____________/");
				System.out.println("     \\/           ");
				System.out.println();
				*/				
				
	  	  	  	
				// COMPUTER MUST BE ARMED AT THIS POINT.
				ArrayOfAttackResult.attackResult[0] = (int) (Math.random() * 20) + 1;
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> The computer attacks...");
						
						// for(int x = 0; x < 100; --x)
						// {}
						
						final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			computerRolls20SidedDie();
			  	  	  			
				  	  	  		final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			if (canHasDisarmed[0].equals("no")) {
					  	  	  				
						  	  	  			centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ".");
					  	  	  			}
					  	  	  			
					  	  	  			if (canHasDisarmed[0].equals("yes")) {
					  	  	  				
						  	  	  			centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ", +2 for disarmed = " + (ArrayOfAttackResult.attackResult[0] + 2));
					  	  	  			}
					  	  	  			
					  	  	  			
						  	  	  		final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
								  	  	  		if (ArrayOfAttackResult.attackResult[0] >= 20) {
													
													computerCriticalHit();
													return;
												}
												
												
												if (canHasDisarmed[0].equals("no")) {
												
													if (ArrayOfAttackResult.attackResult[0] >= 14 && ArrayOfAttackResult.attackResult[0] <= 19) {
														
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
														  		centerscrolltext.startAnimation(animAlphaText);
																centerscrolltext.append("\n" + "> The computer's attack hits.");		
																
																final Handler h3 = new Handler();
													  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
														  	  	  		if (dodgeBlowSpell[0] > 0) {
																			/*
																			centerscrolltext.setVisibility(View.VISIBLE);													
																	  		centerscrolltext.startAnimation(animAlphaText);			  		
																			centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
																			*/
																			
																			AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
																  			
																			alert.setCancelable(false);
																			
																  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your Dodge spell?");
																  	    	/*
																  	    	alert.setMessage("something");
																  	    	*/	  	    	
																  	    	
																  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
																  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
																  		    		
																  		    		/*
																  		    		//NEED THIS??????????????????
																  		    		if (dodgeBlowSpell[0] < 1) {
																  		    			
																  		    			//hideNavigation();
																  						
																  						centerscrolltext.setVisibility(View.VISIBLE);													
																  				  		centerscrolltext.startAnimation(animAlphaText);			  		
																  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
																  						
																  						//break;
																  					}
																  		    		*/
																  		    		dialog.dismiss();
																  		    		
																  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
																  		    		
																  		    		centerscrolltext.setVisibility(View.VISIBLE);													
																			  		centerscrolltext.startAnimation(animAlphaText);
																					centerscrolltext.append("\n" + "> You dodge the hit.");
																  		    		
																  		    		skillsCheck();
																  		    		
																	  		    	// Use a blank drawable to hide the imageview animation:
																	  		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
																	  		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
																	  		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
																	  		    	img.setImageResource(R.drawable.twentytwentyblank);
																  		    		
																  		    		dodgeGraphic();
																  		    		
																  		    		final Handler h = new Handler();
																		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																		  	  	  			
																		  	  	  		@Override
																			  	  	  	public void run() {
																		  	  	  			
																			  	  	  		//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
																		  	  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
																		  	  	  			
																		  	  	  			stopGraphics();
																		  	  	  			
																			  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																			  	    			
																			  	    			gameEngineComputerFirst2();   							
																			  				}
														
																						  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																						  						
																						  		turn();    							
																						  	}
																						  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																						  	    			
																						  		computerHastePartTwo();   							
																						  	}
																	
																						  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																						  						
																						  		computerHastePartTwo();    							
																						  	}								  		    		
																		  					return;
																			  	  	  	}
																		  	  	  	}, 6000);														  		    		
																  		    	}
																  	    	});
																  	    	
																  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
																          	  public void onClick(DialogInterface dialog, int whichButton) {
																          		  
																          		  	//hideNavigation();
																          		  	
																          		  	computerDamage();								          		  	
																          		  	//return;
																          		  	
																          		  	dialog.dismiss();
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
														  	  	  		
														  	  	  		else {
														  	  	  			
															  	  	  		computerDamage();
																			return;						  	  	  			
														  	  	  		}
														  	  	  	}
													  	  	  	}, 2000);
												  	  	  	}
											  	  	  	}, 2000);												
													}
													
													if (ArrayOfAttackResult.attackResult[0] < 14 && ArrayOfAttackResult.attackResult[0] > 1) {
														
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
														  		centerscrolltext.startAnimation(animAlphaText);
																centerscrolltext.append("\n" + "> The computer's attack misses.");
																
																final Handler h = new Handler();
													  	  	  	h.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
														  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
														  	    			
														  	    			gameEngineComputerFirst2();   							
														  				}
														  	  	  		
																	  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
																	  						
																	  		turn();    							
																	  	}								  						  	  	  			
														  	  	  	}
													  	  	  	}, 2000);						
																return;
												  	  	  	}
											  	  	  	}, 2000);											
													}
													
													if (ArrayOfAttackResult.attackResult[0] <= 1) {
														
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		computerCriticalMiss();
																return;
												  	  	  	}
											  	  	  	}, 2000);												
													}				
												}
												
												if (canHasDisarmed[0].equals("yes")) {
													
													if (ArrayOfAttackResult.attackResult[0] >= 12 && ArrayOfAttackResult.attackResult[0] <= 19) {
														
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
														  		centerscrolltext.startAnimation(animAlphaText);
																centerscrolltext.append("\n" + "> The computer's attack hits.");			
																
																final Handler h5 = new Handler();
													  	  	  	h5.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
														  	  	  		if (dodgeBlowSpell[0] > 0) {
																			/*
																			centerscrolltext.setVisibility(View.VISIBLE);													
																	  		centerscrolltext.startAnimation(animAlphaText);			  		
																			centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
																			*/
																			
																			AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
																  			
																			alert.setCancelable(false);
																			
																  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your Dodge spell?");
																  	    	/*
																  	    	alert.setMessage("something");
																  	    	*/	  	    	
																  	    	
																  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
																  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
																  		    		
																  		    		/*
																  		    		//NEED THIS??????????????????
																  		    		if (dodgeBlowSpell[0] < 1) {
																  		    			
																  		    			//hideNavigation();
																  						
																  						centerscrolltext.setVisibility(View.VISIBLE);													
																  				  		centerscrolltext.startAnimation(animAlphaText);			  		
																  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
																  						
																  						//break;
																  					}
																  		    		*/
																  		    		dialog.dismiss();
																  		    		
																  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
																  		    		
																  		    		centerscrolltext.setVisibility(View.VISIBLE);													
																			  		centerscrolltext.startAnimation(animAlphaText);
																					centerscrolltext.append("\n" + "> You dodge the hit.");
																  		    		
																  		    		skillsCheck();
																  		    		
																	  		    	// Use a blank drawable to hide the imageview animation:
																	  		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
																	  		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
																	  		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
																	  		    	img.setImageResource(R.drawable.twentytwentyblank);
																  		    		
																  		    		dodgeGraphic();
																  		    		
																  		    		final Handler h = new Handler();
																		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																		  	  	  			
																		  	  	  		@Override
																			  	  	  	public void run() {
																		  	  	  			
																			  	  	  		//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
																		  	  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
																		  	  	  			
																		  	  	  			stopGraphics();
																		  	  	  			
																			  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																			  	    			
																			  	    			gameEngineComputerFirst2();   							
																			  				}
														
																						  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																						  						
																						  		turn();    							
																						  	}
																						  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																						  	    			
																						  		computerHastePartTwo();   							
																						  	}
																	
																						  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																						  						
																						  		computerHastePartTwo();  							
																						  	}								  		    		
																		  					//return;
																			  	  	  	}
																		  	  	  	}, 6000);											  		    		
																  		    	}
																  	    	});
																  	    	
																  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
																          	  public void onClick(DialogInterface dialog, int whichButton) {
																          		  
																          		  	//hideNavigation();
																          		  	
																          		  	computerDamage();								          		  	
																          		  	//return;
																          		  	
																          		  	dialog.dismiss();
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
														  	  	  		
														  	  	  		else {
														  	  	  			
															  	  	  		computerDamage();
																			//return;						  	  	  			
														  	  	  		}
														  	  	  	}
													  	  	  	}, 2000);
												  	  	  	}
											  	  	  	}, 2000);												
													}
													
													if (ArrayOfAttackResult.attackResult[0] < 12 && ArrayOfAttackResult.attackResult[0] > 1) {
														
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
														  		centerscrolltext.startAnimation(animAlphaText);
																centerscrolltext.append("\n" + "> The computer's attack misses.");
																
																final Handler h = new Handler();
													  	  	  	h.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
														  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
														  	    			
														  	    			gameEngineComputerFirst2();   							
														  				}
														  	  	  		
																	  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
																	  						
																	  		turn();
																	  	}								  						  	  	  			
														  	  	  	}
													  	  	  	}, 2000);						
																return;
												  	  	  	}
											  	  	  	}, 2000);												
													}
													
													if (ArrayOfAttackResult.attackResult[0] <= 1) {
														
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		computerCriticalMiss();
																return;
												  	  	  	}
											  	  	  	}, 2000);												
													}					
												}
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
	
	public void computerDisarm() {// WAS String[]			
		
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
					
					
					// IF BLESS AND COMP USES BLESS TO DISARM
					if (result >= 51) {
						
						final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		blessSpell[1] = blessSpell[1] - 1;
								
								skillsCheck();
								
								centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> The computer uses bless spell...");
								
								blessGraphic();								
						  	  	  		
										
								final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
						  	  	  		//final TextView blessGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
					  	  	  			//blessGraphic.setVisibility(View.INVISIBLE);
					  	  	  			
					  	  	  			stopGraphics();					  	  	  			
					  	  	  			
					  	  	  			ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
					  	  	  			
					  	  	  			ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
					  	  	  			img.bringToFront();
					  	  	  			
					  	  	  			computerRolls20SidedDie();
					  	  	  			
						  	  	  		final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
								  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);			  		
												centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ", +2 for the Bless Spell = " + (ArrayOfAttackResult.attackResult[0] + 2));
												
								
												if (ArrayOfAttackResult.attackResult[0] >= 15) {
													
													canHasDisarmed[0] = "yes";
								
													disarmedTurnStart[0] = turn;
								
													// playerWhoDisarmed[0] = 1;// was i for the 1.
													
													final Handler h = new Handler();
										  	  	  	h.postDelayed(new Runnable() {		  	  	  			
										  	  	  			
										  	  	  		@Override
											  	  	  	public void run() {
										  	  	  			
											  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
													  		centerscrolltext.startAnimation(animAlphaText);			  		
															centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been disarmed.");
															
															final Handler h = new Handler();
												  	  	  	h.postDelayed(new Runnable() {		  	  	  			
												  	  	  			
												  	  	  		@Override
													  	  	  	public void run() {
												  	  	  			
													  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
													  	    			
													  	    			gameEngineComputerFirst2();   							
													  				}
									
																  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
																  						
																  		turn();    							
																  	}				  	  	  			
													  	  	  	}
												  	  	  	}, 2000);
															
												  	  	  	//NEED THIS?:
															//return;
											  	  	  	}
										  	  	  	}, 2000);											
												}
								
												if (ArrayOfAttackResult.attackResult[0] <= 14 && ArrayOfAttackResult.attackResult[0] > 0) {
													
													final Handler h = new Handler();
										  	  	  	h.postDelayed(new Runnable() {		  	  	  			
										  	  	  			
										  	  	  		@Override
											  	  	  	public void run() {
										  	  	  			
											  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
													  		centerscrolltext.startAnimation(animAlphaText);			  		
															centerscrolltext.append("\n" + "> The computer's attempt to disarm you misses.");
															
															final Handler h = new Handler();
												  	  	  	h.postDelayed(new Runnable() {		  	  	  			
												  	  	  			
												  	  	  		@Override
													  	  	  	public void run() {
												  	  	  			
													  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
													  	    			
													  	    			gameEngineComputerFirst2();   							
													  				}
									
																  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
																  						
																  		turn();    							
																  	}				  	  	  			
													  	  	  	}
												  	  	  	}, 2000);
															
												  	  	  	//NEED THIS?:
															//return;
											  	  	  	}
										  	  	  	}, 2000);											
												}
												// don't critically miss when using bless.
												/*
												 * if (attackResult <= 1) { computerCriticalMiss(i, turn);
												 * return canHasDisarmed; }
												 */
								  	  	  	}
							  	  	  	}, 2000);				  	  	  		
						  	  	  	}
					  	  	  	}, 6000);
				  	  	  	}
			  	  	  	}, 4000);//2000 just wasn't enough to separate this text from text from computerAttack() - guess stuff going on in bkgrd?								  	  	  											
					}					
					
					// IF BLESS AND COMP DOES NOT USE BLESS TO DISARM
					if (result < 51) {					
						
						final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {	  	  			
			  	  	  			
				  	  	  		ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
								
				  	  	  		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
				  	  	  		img.bringToFront();
				  	  	  		
								computerRolls20SidedDie();
								
								final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
						  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);			  		
										centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ".");
										
						
										if (ArrayOfAttackResult.attackResult[0] >= 17) {
											
											canHasDisarmed[0] = "yes";
						
											disarmedTurnStart[0] = turn;
						
											// playerWhoDisarmed[0] = 1;// was i for the 1.
											
											final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
									  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
											  		centerscrolltext.startAnimation(animAlphaText);			  		
													centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been disarmed.");
													
													final Handler h = new Handler();
										  	  	  	h.postDelayed(new Runnable() {		  	  	  			
										  	  	  			
										  	  	  		@Override
											  	  	  	public void run() {
										  	  	  			
											  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
											  	    			
											  	    			gameEngineComputerFirst2();   							
											  				}
							
														  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
														  						
														  		turn();    							
														  	}				  	  	  			
											  	  	  	}
										  	  	  	}, 2000);
													
										  	  	  	//NEED THIS?:
													//return;
									  	  	  	}
								  	  	  	}, 2000);								
										}
						
										if (ArrayOfAttackResult.attackResult[0] <= 16 && ArrayOfAttackResult.attackResult[0] > 1) {
											
											final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
									  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
											  		centerscrolltext.startAnimation(animAlphaText);			  		
													centerscrolltext.append("\n" + "> The computer's attempt to disarm you misses.");
													
													final Handler h = new Handler();
										  	  	  	h.postDelayed(new Runnable() {		  	  	  			
										  	  	  			
										  	  	  		@Override
											  	  	  	public void run() {
										  	  	  			
											  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
											  	    			
											  	    			gameEngineComputerFirst2();   							
											  				}
							
														  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
														  						
														  		turn();    							
														  	}				  	  	  			
											  	  	  	}
										  	  	  	}, 2000);
													
										  	  	  	//NEED THIS:?
													//return;
									  	  	  	}
								  	  	  	}, 2000);									
										}
										if (ArrayOfAttackResult.attackResult[0] <= 1) {
											
											final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
									  	  	  		computerCriticalMiss();
													//return;
									  	  	  	}
								  	  	  	}, 2000);									
										}
						  	  	  	}
					  	  	  	}, 2000);
				  	  	  	}
			  	  	  	}, 3000);											
					}					
				} 
				
				
				// IF NO BLESS AND DISARM
				else {				
					
					final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {		  	  	  			
		  	  	  			
			  	  	  		ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
							
			  	  	  		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
			  	  	  		img.bringToFront();
			  	  	  		
							computerRolls20SidedDie();
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);			  		
									centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ".");
									
						
									if (ArrayOfAttackResult.attackResult[0] >= 17) {
										
										canHasDisarmed[0] = "yes";
						
										disarmedTurnStart[0] = turn;
						
										// playerWhoDisarmed[0] = 1;// was i for the 1.
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
								  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);			  		
												centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been disarmed.");
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
										  	    			
										  	    			gameEngineComputerFirst2();   							
										  				}
					
													  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
													  						
													  		turn();    							
													  	}				  	  	  			
										  	  	  	}
									  	  	  	}, 2000);
												
									  	  	  	//NEED THIS?:
												//return;
								  	  	  	}
							  	  	  	}, 2000);							
									}
						
									if (ArrayOfAttackResult.attackResult[0] <= 16 && ArrayOfAttackResult.attackResult[0] > 1) {
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
								  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);			  		
												centerscrolltext.append("\n" + "> The computer's attempt to disarm you misses.");
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
										  	    			
										  	    			gameEngineComputerFirst2();   							
										  				}
					
													  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
													  						
													  		turn();    							
													  	}				  	  	  			
										  	  	  	}
									  	  	  	}, 2000);
												
												//return;
								  	  	  	}
							  	  	  	}, 2000);								
									}
									
									if (ArrayOfAttackResult.attackResult[0] <= 1) {
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
								  	  	  		computerCriticalMiss();
												//return;
								  	  	  	}
							  	  	  	}, 2000);								
									}
					  	  	  	}
				  	  	  	}, 2000);
			  	  	  	}
		  	  	  	}, 2000);								
				}
  	  	    }
		});
		//return canHasDisarmed;
	}
	
	public void computerDisarmedAction() { // WAS int	
		
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
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
	  	  	  			// CURE
	  					if (ArrayOfHitPoints.hitpoints[1] <= 12 && cureSpell[1] > 0) {
	  						
	  						centerscrolltext.setVisibility(View.VISIBLE); 													
	  				  		centerscrolltext.startAnimation(animAlphaText);
	  						centerscrolltext.append("\n" + "> The computer uses cure spell...");			
	  						
	  						computerCure();
	  						return;
	  					}
	  			
	  					int computerDisarmedAction = (int) ((Math.random() * 100) + 1);
	  			
	  					// HASTE
	  					if (((computerDisarmedAction <= 50 && hasteSpell[1] > 0 && canHasDisarmed[1].equals("yes")) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1] && didComputerCriticalMiss.equals("yes") && disarmedTurnStart[1] + 1 == turn)) || 
	  							((computerDisarmedAction <= 50 && hasteSpell[1] > 0 && canHasDisarmed[1].equals("yes")) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1] && didComputerCriticalMiss.equals("no") && disarmedTurnStart[1] == turn)) ||
	  							((computerDisarmedAction <= 50 && hasteSpell[1] > 0 && canHasDisarmed[1].equals("yes")) && (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1] && didComputerCriticalMiss.equals("yes") && disarmedTurnStart[1] == turn)) ||
	  							((computerDisarmedAction <= 50 && hasteSpell[1] > 0 && canHasDisarmed[1].equals("yes")) && (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1] && didComputerCriticalMiss.equals("no") && disarmedTurnStart[1] + 1 == turn))) {
	  						
	  						centerscrolltext.setVisibility(View.VISIBLE); 													
	  				  		centerscrolltext.startAnimation(animAlphaText);
	  						centerscrolltext.append("\n" + "> The computer uses a haste spell...");
	  									
	  						computerHasteDisarmed();
	  						return;
	  					}
	  			
	  					// PUNCH A
	  					if (computerDisarmedAction <= 50 && hasteSpell[1] < 1) {
	  						
	  						centerscrolltext.setVisibility(View.VISIBLE); 													
	  				  		centerscrolltext.startAnimation(animAlphaText);
	  						centerscrolltext.append("\n" + "> The computer attempts to punch you...");			
	  						
	  						computerPunch();
	  						return;
	  					}
	  			
	  					// PUNCH B
	  					if (computerDisarmedAction >= 51) {
	  						
	  						centerscrolltext.setVisibility(View.VISIBLE); 													
	  				  		centerscrolltext.startAnimation(animAlphaText);
	  						centerscrolltext.append("\n" + "> The computer attempts to punch you...");			
	  						
	  						computerPunch();
	  						return;
	  					}
		  	  	  	}
	  	  	  	}, 2000);			
  	  	    }
		});
		//return playerNumberAttacked;
	}
	
	public void computerHasteDisarmed() {//WAS int[]
		
		canHasDisarmed[1] = "no";		
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			hasteSpell[1] = hasteSpell[1] - 1;
	  			
	  			skillsCheck();
	  			
	  			hasteGraphic();  	  	  						  	  	  	
						  	  	
			  	  	  			
  	  	  		final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		//final TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  	  	  		//hasteGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> The computer picks up it's weapon.");
						
						
						final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
				  	    			
				  	    			gameEngineComputerFirst2();   							
				  				}
	
							  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
							  						
							  		turn();    							
							  	}			  	  	  			
				  	  	  	}
			  	  	  	}, 2000);
		  	  	  	}
	  	  	  	}, 6000);		  	  	  	  							
  	  	    }  	  	
		});		
	}
	
	public void computerPunch() {//WAS int								
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);		
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
  	  	    	
	  			
	  			final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
	  	  	  			computerRolls20SidedDie();
	  	  	  			
		  	  	  		final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ", -1 for being disarmed = " + (ArrayOfAttackResult.attackResult[0] - 1));
								
								
								if (ArrayOfAttackResult.attackResult[0] >= 20) {
									
									computerCriticalHitDisarmed();
									return;
								}
								
								if (canHasDisarmed[0].equals("no")) {
									
									if (ArrayOfAttackResult.attackResult[0] >= 15 && ArrayOfAttackResult.attackResult[0] <= 19) { // -1 to-hit for being disarmed.
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
								  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);
												centerscrolltext.append("\n" + "> The computer's punch hits.");				
								
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		if (dodgeBlowSpell[0] > 0) {
															/*
															centerscrolltext.setVisibility(View.VISIBLE);													
													  		centerscrolltext.startAnimation(animAlphaText);			  		
															centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
															*/
															
															AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
												  			
															alert.setCancelable(false);
															
												  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your Dodge spell?");
												  	    	/*
												  	    	alert.setMessage("something");
												  	    	*/	  	    	
												  	    	
												  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
												  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
												  		    		
												  		    		/*
												  		    		//NEED THIS??????????????????
												  		    		if (dodgeBlowSpell[0] < 1) {
												  		    			
												  		    			//hideNavigation();
												  						
												  						centerscrolltext.setVisibility(View.VISIBLE);													
												  				  		centerscrolltext.startAnimation(animAlphaText);			  		
												  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
												  						
												  						//break;
												  					}
												  		    		*/
												  		    		dialog.dismiss();
												  		    		
												  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
												  		    		
												  		    		centerscrolltext.setVisibility(View.VISIBLE);													
															  		centerscrolltext.startAnimation(animAlphaText);
																	centerscrolltext.append("\n" + "> You dodge the hit.");
												  		    		
												  		    		skillsCheck();
												  		    		
												  		    		// Use a blank drawable to hide the imageview animation:
													  		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
													  		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
													  		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
													  		    	img.setImageResource(R.drawable.twentytwentyblank);
												  		    		
												  		    		dodgeGraphic();
												  		    		
												  		    		final Handler h = new Handler();
														  	  	  	h.postDelayed(new Runnable() {		  	  	  			
														  	  	  			
														  	  	  		@Override
															  	  	  	public void run() {
														  	  	  			
															  	  	  		//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
														  	  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
														  	  	  			
														  	  	  			stopGraphics();
														  	  	  			
															  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
															  	    			
															  	    			gameEngineComputerFirst2();   							
															  				}
										
																		  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
																		  						
																		  		turn();    							
																		  	}
																		  									  		    		
														  					//return;
															  	  	  	}
														  	  	  	}, 6000);											  		    		
												  		    	}
												  	    	});
												  	    	
												  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
												          	  public void onClick(DialogInterface dialog, int whichButton) {
												          		  
												          		  	//hideNavigation();
												          		  	
												          		  	computerDamageDisarmed();												          		  									          		  	
												          		  	//return;
												          		  	
												          		  	dialog.dismiss();
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
										  	  	  		
										  	  	  		else {
										  	  	  			
										  	  	  			computerDamageDisarmed();										  	  	  			
															//return;						  	  	  			
										  	  	  		}
										  	  	  	}
									  	  	  	}, 2000);
								  	  	  	}
							  	  	  	}, 2000);																		
									}
									
									if (ArrayOfAttackResult.attackResult[0] < 15 && ArrayOfAttackResult.attackResult[0] >= 1) {
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
								  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);
												centerscrolltext.append("\n" + "> The computer's punch misses.");
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
										  	    			
										  	    			gameEngineComputerFirst2();   							
										  				}
					
													  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
													  						
													  		turn();    							
													  	}							  						  	  	  			
										  	  	  	}
									  	  	  	}, 2000);
									  	  	  	
												//return;
								  	  	  	}
							  	  	  	}, 2000);										
									}
									
									// NEW
									//	CAN YOU CRITICL MISS A PUNCH???????????????????????????????????? -----DISARM AGAIN TO MAINTAIN BALANCE.
									// for now i'll do no rolls to keep it simpler, but in future might go w roll to hurt yourself (ie stumble & fall) but no roll to lose weapon.
									/*
									if (attackResult <= 1) {
										computerCriticalMiss();
										return;
									}
									*/
									
									// NEED THIS:?
									//return;						
								}
								
								if (canHasDisarmed[0].equals("yes")) {
									
									if (ArrayOfAttackResult.attackResult[0] >= 13 && ArrayOfAttackResult.attackResult[0] <= 19) { // -1 to-hit for being disarmed but, +2 because computer is disarmed (+1 total)
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
								  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);
												centerscrolltext.append("\n" + "> The computer's punch hits.");				
								
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		if (dodgeBlowSpell[0] > 0) {
															/*
															centerscrolltext.setVisibility(View.VISIBLE);													
													  		centerscrolltext.startAnimation(animAlphaText);			  		
															centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
															*/
															
															AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
												  			
															alert.setCancelable(false);
															
												  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your Dodge spell?");
												  	    	/*
												  	    	alert.setMessage("something");
												  	    	*/	  	    	
												  	    	
												  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
												  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
												  		    		
												  		    		/*
												  		    		//NEED THIS??????????????????
												  		    		if (dodgeBlowSpell[0] < 1) {
												  		    			
												  		    			//hideNavigation();
												  						
												  						centerscrolltext.setVisibility(View.VISIBLE);													
												  				  		centerscrolltext.startAnimation(animAlphaText);			  		
												  						centerscrolltext.append("\n" + "> You have already used your Dodge spell!");
												  						
												  						//break;
												  					}
												  		    		*/
												  		    		dialog.dismiss();
												  		    		
												  		    		dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
												  		    		
												  		    		centerscrolltext.setVisibility(View.VISIBLE);													
															  		centerscrolltext.startAnimation(animAlphaText);
																	centerscrolltext.append("\n" + "> You dodge the hit.");
												  		    		
												  		    		skillsCheck();
												  		    		
												  		    		// Use a blank drawable to hide the imageview animation:
													  		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
													  		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
													  		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
													  		    	img.setImageResource(R.drawable.twentytwentyblank);
												  		    		
												  		    		dodgeGraphic();
												  		    		
												  		    		final Handler h = new Handler();
														  	  	  	h.postDelayed(new Runnable() {		  	  	  			
														  	  	  			
														  	  	  		@Override
															  	  	  	public void run() {
														  	  	  			
															  	  	  		//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
														  	  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
														  	  	  			
														  	  	  			stopGraphics();
														  	  	  			
															  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
															  	    			
															  	    			gameEngineComputerFirst2();   							
															  				}
										
																		  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
																		  						
																		  		turn();    							
																		  	}
																		  									  		    		
														  					//return;
															  	  	  	}
														  	  	  	}, 6000);						  		    		
												  		    	}
												  	    	});
												  	    	
												  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
												          	  public void onClick(DialogInterface dialog, int whichButton) {
												          		  
												          		  	//hideNavigation();
												          		  	
												          		  	computerDamageDisarmed();												          		  	
												          		  	//return;
												          		  	
												          		  	dialog.dismiss();
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
										  	  	  		
										  	  	  		else {
										  	  	  			
										  	  	  			computerDamageDisarmed();										  	  	  			
															//return;						  	  	  			
										  	  	  		}
										  	  	  	}
									  	  	  	}, 2000);
								  	  	  	}
							  	  	  	}, 2000);										
									}
									
									if (ArrayOfAttackResult.attackResult[0] < 13 && ArrayOfAttackResult.attackResult[0] >= 1) {
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
								  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);
												centerscrolltext.append("\n" + "> The computer's punch misses.");
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
										  	    			
										  	    			gameEngineComputerFirst2();   							
										  				}
					
													  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
													  						
													  		turn();    							
													  	}							  						  	  	  			
										  	  	  	}
									  	  	  	}, 2000);
									  	  	  	
												//return;
								  	  	  	}
							  	  	  	}, 2000);									
									}
									
									// NEW
									//	CAN YOU CRITICL MISS A PUNCH???????????????????????????????????? -----DISARM AGAIN TO MAINTAIN BALANCE.
									// for now i'll do no rolls to keep it simpler, but in future might go w roll to hurt yourself (ie stumble & fall) but no roll to lose weapon.
									/*
									if (attackResult <= 1) {
										computerCriticalMiss();
										return;
									}
									*/							
									
									// NEED THIS:?
									//return;						
								}
				  	  	  	}
			  	  	  	}, 2000);		  	  	  		
		  	  	  	}
	  	  	  	}, 6000);								
  	  	    }
		});		
	}
	
	public void computerCriticalHitDisarmed() {				
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
	  			
	  			criticalHitGraphic();  	  	  			  	  	
						  	  	
						  	  	
		  	  	final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
			  			
		  	  		@Override
			  	  	public void run() {
			  			
			  	  		//final TextView criticalHitGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//criticalHitGraphic.setVisibility(View.INVISIBLE);
			  			
		  	  			stopGraphics();
		  	  			
			  	  		if (dodgeBlowSpell[0] > 0) {
							/*
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
							*/
							
							AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
							
							alert.setCancelable(false);
							
					    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your Dodge spell?");
					    	/*
					    	alert.setMessage("something");
					    	*/	  	    	
					    	
					    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					    		public void onClick(DialogInterface dialog, int whichButton) {		  		    		
					    			
					    			dialog.dismiss();
					    			
					    			dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;
					    			
					    			centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> You dodge the hit.");
				    		
					    			skillsCheck();
					    			
					    			// Use a blank drawable to hide the imageview animation:
					  		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
					  		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
					  		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
					  		    	img.setImageResource(R.drawable.twentytwentyblank);
					    			
					    			dodgeGraphic();						    		
				    		
					    			final Handler h = new Handler();
					    			h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
					    				@Override
					    				public void run() {
					    					
					    					//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
						  	  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
					    					
					    					stopGraphics();
					    					
					    					if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
					  	    			
					    						gameEngineComputerFirst2();   							
					    					}
			
					    					if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
								  						
					    						turn();    							
					    					}								  					  	  	  			
					    				}
					    			}, 6000);
				    		
					    			//return;
					    		}
					    	});
			    	
					    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
					    		public void onClick(DialogInterface dialog, int whichButton) {		          		  
			      		  
					    			//hideNavigation();
			      		  
					    			computerCriticalHitDamageDisarmed();
					    			
					    			dialog.dismiss();
					    		}
					    	});	  	    	
			    	
					    	alert.show();					
			  	  		}
				
			  	  		else {
					
			  	  			computerCriticalHitDamageDisarmed();	  				
			  	  			//return;	  				
			  	  		}
		  	  		}
		  	  	}, 3000);	  	  	  							
  	  	    }
		});
	}
	
	public void computerMightyBlowDisarmed() {// WAS int[]					
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
		SharedPreferences.Editor editor = preferences.edit();		
		editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
		editor.apply();
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	  			
	  			
				
				preventattackdamagediefromleaking = "off";
				
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
				//SharedPreferences.Editor editor = preferences.edit();
				int attackDamage = preferences.getInt("attackDamage", 0);
				
				computerAttackDamageDisarmed = (attackDamage - 2);
				
				if (computerAttackDamageDisarmed < 0) {
					computerAttackDamageDisarmed = 0;
				}
	  			
				
				mightyBlowGraphic(); 	  	  			  	  	
						  	  	
			  	  	  			
  	  	  		final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		//final TextView mightyBlowGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//mightyBlowGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
	  	  	  			computerRolls6SidedDie();
	  	  	  			
								
						final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			preventattackdamagediefromleaking = "on";
			  	  	  			
			  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
			  	  	  			//SharedPreferences.Editor editor = preferences.edit();
			  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + ", -2 damage for punch = " + computerAttackDamageDisarmed + " damage.");
								
								
								final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
						  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);			  		
										centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (computerAttackDamageDisarmed * 2) + ".");
										
										/*
										for (int x = 0; x < 1000; --x)// To give human time to read.
										{
										}
										*/
										
										ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - (computerAttackDamageDisarmed * 2);
							  			
							  			
										TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
						    			playerHitPointsTextView.setTypeface(typeFace);
						    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
						    			//playerHitPointsTextView.bringToFront();
						    			
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
								  	  	  		if (ArrayOfHitPoints.hitpoints[0] == 0) {
													
													centerscrolltext.setVisibility(View.VISIBLE);													
											  		centerscrolltext.startAnimation(animAlphaText);			  		
													centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been knocked unconscious!");
													
													/*
													AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
												    
													alert.setCancelable(false);
													
													alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious.");
										  	    	
										  	    	//alert.setMessage("something");
										  	    		    	
											    	
											    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
												    	public void onClick(DialogInterface dialog, int whichButton) {
												    */		
												    		//hideNavigation();
												    
													final Handler h = new Handler();
										  	  	  	h.postDelayed(new Runnable() {		  	  	  			
										  	  	  			
										  	  	  		@Override
											  	  	  	public void run() {
										  	  	  			
											  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
											  	    			
											  	    			gameEngineComputerFirst2();   							
											  				}
							
														  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
														  						
														  		turn();    							
														  	}
											  	  	  	}
										  	  	  	}, 2000);									    		
														  	
														  	//dialog.dismiss();
												    	//}
											    	//});
											    	
											    	//alert.show(); 
													
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
												    
													alert.setCancelable(false);
													
													alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain.");
										  	    	/*
										  	    	alert.setMessage("something");
										  	    	*/	    	
											    	
											    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
												    	public void onClick(DialogInterface dialog, int whichButton) {
												    		
												    		//hideNavigation();
												    		
												    		playerDeadYet[0] = "yes";
												    		
												    		gameOverCheck();
												    		
												    		dialog.dismiss();
												    	}
											    	});
											    	
											    	alert.show();			
													
													/*
													System.out.print("Press a key to continue... ");
													Scanner input = new Scanner(System.in);
													input.nextLine();
													*/							
												}
												
												else {
													
													if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
									  	    			
									  	    			gameEngineComputerFirst2();   							
									  				}
					
												  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
												  						
												  		turn();    							
												  	}
												}	  	  	  			
								  	  	  	}
							  	  	  	}, 2000);
						  	  	  	}
					  	  	  	}, 2000);							
				  	  	  	}
			  	  	  	}, 2000);		  	  	  		
		  	  	  	}
	  	  	  	}, 6000);	  	  	  										
  	  	    }
		});
		//return ArrayOfHitPoints.hitpoints;
	}
	
	public void computerDamageDisarmed() {// WAS int[]				
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
		SharedPreferences.Editor editor = preferences.edit();		
		editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
		editor.apply();
		
		// Use a blank drawable to hide the imageview animation:
		// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
		img.setBackgroundResource(R.drawable.twentytwentyblank);
		img.setImageResource(R.drawable.twentytwentyblank);
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			if (mightyBlowSpell[1] > 0 && iscomputerhasteused.equals("no") && iscomputerblessrolled.equals("no")) {
					
					int computerUseMightyBlow = (int) ((Math.random() * 20) + 1);
					// NEED 1 SCENARIO FOR HUMAN PLAYER HP ABOVE 12 AND 1 SCENARIO FOR HUMAN PLAYER HP BELOW 12
					
					if (ArrayOfHitPoints.hitpoints[0] >= 12) {
						// using 12 just because if comp roll 6 for damage * 2 = 12, comp can kill human player.
					
						if (computerUseMightyBlow >= 15) { // less likely for comp to use MB because human HP > 12.						
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> The computer uses mighty blow.");
							
							/*
							for (int x = 0; x < 1000; --x) {
							}
							*/
							
							mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
							
							skillsCheck();							

							computerMightyBlowDisarmed();
							return;
						}
						
						else {						
							
							preventattackdamagediefromleaking = "off";
							
							SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
							//SharedPreferences.Editor editor = preferences.edit();
							int attackDamage = preferences.getInt("attackDamage", 0);
							
							computerAttackDamageDisarmed = (attackDamage - 2);
							
							if (computerAttackDamageDisarmed < 0) {
								computerAttackDamageDisarmed = 0;
							}
			  				
			  				centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer rolls for damage...");
							
							// for(int x = 0; x < 100; --x)
							// {}				
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			computerRolls6SidedDie();
				  	  	  			
					  	  	  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			preventattackdamagediefromleaking = "on";
						  	  	  			
						  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
						  	  	  			//SharedPreferences.Editor editor = preferences.edit();
						  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + ", -2 damage for punch = " + computerAttackDamageDisarmed + " damage.");
											
											/*
											for (int x = 0; x < 1000; --x)// To give human time to read.
											{
											}
											*/
											
											ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - computerAttackDamageDisarmed;
											
											
											TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
							    			playerHitPointsTextView.setTypeface(typeFace);
							    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
							    			//playerHitPointsTextView.bringToFront();
											
											
											final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
									  	  	  		if (ArrayOfHitPoints.hitpoints[0] == 0) {
														
														centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);			  		
														centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0]	+ ", you have been knocked unconscious!");
														
														/*
														AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
													    
														alert.setCancelable(false);
														
														alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious.");
											  	    	
											  	    	//alert.setMessage("something");
											  	    		    	
												    	
												    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
													    	public void onClick(DialogInterface dialog, int whichButton) {
													    */		
													    		//hideNavigation();
													    		
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
												  	    			
												  	    			gameEngineComputerFirst2();   							
												  				}
							
															  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
															  						
															  		turn();    							
															  	}
												  	  	  	}
											  	  	  	}, 2000);													
															  	
															  	//dialog.dismiss();
													    	//}
												    	//});
												    	
												    	//alert.show();
														
														/*
														System.out.print("Press a key to continue... ");
														input.nextLine();
														*/
													}
											
													else if (ArrayOfHitPoints.hitpoints[0] < 0) {
														
														
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
													    
														alert.setCancelable(false);
														
														alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain.");
											  	    	/*
											  	    	alert.setMessage("something");
											  	    	*/	    	
												    	
												    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
													    	public void onClick(DialogInterface dialog, int whichButton) {
													    		
													    		//hideNavigation();
													    		
													    		playerDeadYet[0] = "yes";
													    		
													    		gameOverCheck();
													    		
													    		dialog.dismiss();
													    	}
												    	});
												    	
												    	alert.show();
														
														/*
														System.out.print("Press a key to continue... ");
														Scanner input = new Scanner(System.in);
														input.nextLine();
														*/						
													}
													
													else {
														
														if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
										  	    			
										  	    			gameEngineComputerFirst2();   							
										  				}
							
													  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
													  						
													  		turn();    							
													  	}					  	
													}
									  	  	  	}
								  	  	  	}, 2000);								
							  	  	  	}
						  	  	  	}, 2000);			  	  	  		
					  	  	  	}
				  	  	  	}, 6000);
				  	  	  	//NEED THIS?:
				  	  	  	//return;
						}
					}
					
					if (ArrayOfHitPoints.hitpoints[0] < 12) { // see above for explanation
					
						if (computerUseMightyBlow < 15) {
							// more likely for comp to use MB because human HP < 12.
						
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> The computer uses mighty blow.");
							
							/*
							for (int x = 0; x < 1000; --x) {
							}
							*/
							
							mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
							
							skillsCheck();						
							
							computerMightyBlowDisarmed();
							return;
						}
						
						else {
							
							//Globals g = Globals.getInstance();
							//g.setDataAttackDamage((int) ((Math.random() * 6) + 1));
							
							preventattackdamagediefromleaking = "off";
							
							SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
							//SharedPreferences.Editor editor = preferences.edit();
							int attackDamage = preferences.getInt("attackDamage", 0);
							
							computerAttackDamageDisarmed = (attackDamage - 2);
							
							if (computerAttackDamageDisarmed < 0) {
								computerAttackDamageDisarmed = 0;
							}
			  				
			  				centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer rolls for damage...");
							
							// for(int x = 0; x < 100; --x)
							// {}				
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			computerRolls6SidedDie();
				  	  	  			
					  	  	  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			preventattackdamagediefromleaking = "on";
						  	  	  			
						  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
						  	  	  			//SharedPreferences.Editor editor = preferences.edit();
						  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + ", -2 damage for punch = " + computerAttackDamageDisarmed + " damage.");
											
											/*
											for (int x = 0; x < 1000; --x)// To give human time to read.
											{
											}
											*/
											
											ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - computerAttackDamageDisarmed;
											
											
											TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
							    			playerHitPointsTextView.setTypeface(typeFace);
							    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
							    			//playerHitPointsTextView.bringToFront();
											
											
											final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
									  	  	  		if (ArrayOfHitPoints.hitpoints[0] == 0) {
														
														centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);			  		
														centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0]	+ ", you have been knocked unconscious!");
														
														/*
														AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
													    
														alert.setCancelable(false);
														
														alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious.");
											  	    	
											  	    	//alert.setMessage("something");
											  	    		    	
												    	
												    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
													    	public void onClick(DialogInterface dialog, int whichButton) {
													    */		
													    		//hideNavigation();
													    
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
												  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
												  	    			
												  	    			gameEngineComputerFirst2();   							
												  				}
							
															  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
															  						
															  		turn();    							
															  	}
												  	  	  	}
											  	  	  	}, 2000);											    		
															  	
															  	//dialog.dismiss();
													    	//}
												    	//});
												    	
												    	//alert.show();
														
														/*
														System.out.print("Press a key to continue... ");
														input.nextLine();
														*/
													}
											
													else if (ArrayOfHitPoints.hitpoints[0] < 0) {
														
														
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
													    
														alert.setCancelable(false);
														
														alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain.");
											  	    	/*
											  	    	alert.setMessage("something");
											  	    	*/	    	
												    	
												    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
													    	public void onClick(DialogInterface dialog, int whichButton) {
													    		
													    		//hideNavigation();
													    		
													    		playerDeadYet[0] = "yes";
													    		
													    		gameOverCheck();
													    		
													    		dialog.dismiss();
													    	}
												    	});
												    	
												    	alert.show();
														
														/*
														System.out.print("Press a key to continue... ");
														Scanner input = new Scanner(System.in);
														input.nextLine();
														*/						
													}
													
													else {
														
														if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
										  	    			
										  	    			gameEngineComputerFirst2();   							
										  				}
							
													  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
													  						
													  		turn();    							
													  	}					  	
													}
									  	  	  	}
								  	  	  	}, 2000);								
							  	  	  	}
						  	  	  	}, 2000);			  	  	  		
					  	  	  	}
				  	  	  	}, 2000);
				  	  	  	//NEED THIS?:
				  	  	  	//return;
						}
					}
				}
	  			
	  			else {	  				
	  				
	  				//Globals g = Globals.getInstance();
		  			//int attackDamage = g.setDataAttackDamage((int) ((Math.random() * 6) + 1));
					
					preventattackdamagediefromleaking = "off";
					
					SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
					//SharedPreferences.Editor editor = preferences.edit();
					int attackDamage = preferences.getInt("attackDamage", 0);
					
					computerAttackDamageDisarmed = (attackDamage - 2);
					
					if (computerAttackDamageDisarmed < 0) {
						computerAttackDamageDisarmed = 0;
					}
	  				
	  				centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);			  		
					centerscrolltext.append("\n" + "> The computer rolls for damage...");
					
					// for(int x = 0; x < 100; --x)
					// {}				
					
					final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
		  	  	  			computerRolls6SidedDie();
		  	  	  			
			  	  	  		final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			preventattackdamagediefromleaking = "on";
				  	  	  			
				  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
				  	  	  			//SharedPreferences.Editor editor = preferences.edit();
				  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
				  	  	  			
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);			  		
									centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + ", -2 damage for punch = " + computerAttackDamageDisarmed + " damage.");
									
									/*
									for (int x = 0; x < 1000; --x)// To give human time to read.
									{
									}
									*/
									
									ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - computerAttackDamageDisarmed;
									
									
									TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
					    			playerHitPointsTextView.setTypeface(typeFace);
					    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
					    			//playerHitPointsTextView.bringToFront();
									
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		if (ArrayOfHitPoints.hitpoints[0] == 0) {
												
												centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);			  		
												centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0]	+ ", you have been knocked unconscious!");
												
												/*
												AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
											    
												alert.setCancelable(false);
												
												alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious.");
									  	    	
									  	    	//alert.setMessage("something");
									  	    		    	
										    	
										    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											    	public void onClick(DialogInterface dialog, int whichButton) {
											    */		
											    		//hideNavigation();
											    
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
										  	    			
										  	    			gameEngineComputerFirst2();   							
										  				}
					
													  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
													  						
													  		turn();    							
													  	}
										  	  	  	}
									  	  	  	}, 2000);								    		
													  	
													  	//dialog.dismiss();
											    	//}
										    	//});
										    	
										    	//alert.show();
												
												/*
												System.out.print("Press a key to continue... ");
												input.nextLine();
												*/
											}
									
											else if (ArrayOfHitPoints.hitpoints[0] < 0) {
												
												
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
											    
												alert.setCancelable(false);
												
												alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain.");
									  	    	/*
									  	    	alert.setMessage("something");
									  	    	*/	    	
										    	
										    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											    	public void onClick(DialogInterface dialog, int whichButton) {
											    		
											    		//hideNavigation();
											    		
											    		playerDeadYet[0] = "yes";
											    		
											    		gameOverCheck();
											    		
											    		dialog.dismiss();
											    	}
										    	});
										    	
										    	alert.show();
												
												/*
												System.out.print("Press a key to continue... ");
												Scanner input = new Scanner(System.in);
												input.nextLine();
												*/						
											}
											
											else {
												
												if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
								  	    			
								  	    			gameEngineComputerFirst2();   							
								  				}
					
											  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
											  						
											  		turn();    							
											  	}					  	
											}
							  	  	  	}
						  	  	  	}, 2000);								
					  	  	  	}
				  	  	  	}, 2000);			  	  	  		
			  	  	  	}
		  	  	  	}, 2000);			
	  			}		
  	  	    }
		});
		//return ArrayOfHitPoints.hitpoints;
	}
	
	public void computerCriticalHitMightyBlowDamageDisarmed() { // WAS int[]			
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
		SharedPreferences.Editor editor = preferences.edit();		
		editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
		editor.apply();
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			mightyBlowGraphic();  	  	  			  	  	  	
						  	  	
						  	  	
		  	  	final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		//final TextView mightyBlowGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//mightyBlowGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);			  		
						centerscrolltext.append("\n" + "> The computer will roll twice for critical hit damage.");
						
						
						final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> The computer's first roll...");												
								
								
								//Globals g = Globals.getInstance();
					  			//g.setDataAttackDamage((int) ((Math.random() * 6) + 1));
								
								preventattackdamagediefromleaking = "off";
								
								SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
								//SharedPreferences.Editor editor = preferences.edit();
								int attackDamage = preferences.getInt("attackDamage", 0);
								
								int resultOne = attackDamage;
								
								attackDamageOneDisarmed[0] = (resultOne - 2);
								
								if (attackDamageOneDisarmed[0] < 0) {
									attackDamageOneDisarmed[0] = 0;
								}
								
								
								final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			computerRolls6SidedDie();
					  	  	  			
					  	  	  			
						  	  	  		final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
							  	  	  			preventattackdamagediefromleaking = "on";
							  	  	  			
							  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
							  	  	  			//SharedPreferences.Editor editor = preferences.edit();
							  					int attackDamage = preferences.getInt("attackDamage", 0);
							  	  	  			
								  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);			  		
												centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + ", -2 damage for punch = " + attackDamageOneDisarmed[0] + " damage.");
												
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);			  		
														centerscrolltext.append("\n" + "> The computer's second roll...");
																																
														
														SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
														SharedPreferences.Editor editor = preferences.edit();		
														editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
														editor.apply();
														
														preventattackdamagediefromleaking = "off";														
														
														
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
											  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
											  	  	  			//SharedPreferences.Editor editor = preferences.edit();
											  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);

																
																int resultTwo = attackDamage;
																
																attackDamageTwoDisarmed[0] = (resultTwo - 2);
																
																if (attackDamageTwoDisarmed[0] < 0) {
																	attackDamageTwoDisarmed[0] = 0;
																}
											  	  	  			
											  	  	  			computerRolls6SidedDie();
											  	  	  			
											  	  	  			
												  	  	  		final Handler h = new Handler();
													  	  	  	h.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
													  	  	  			preventattackdamagediefromleaking = "on";
													  	  	  			
													  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
													  	  	  			//SharedPreferences.Editor editor = preferences.edit();
													  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);

													  	  	  			
														  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
																  		centerscrolltext.startAnimation(animAlphaText);			  		
																		centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + ", -2 damage for punch = " + attackDamageTwoDisarmed[0] + " damage.");
																		
																		final int totalAttackDamage = (attackDamageOneDisarmed[0] + attackDamageTwoDisarmed[0]);
																		
																		
																		final Handler h = new Handler();
															  	  	  	h.postDelayed(new Runnable() {		  	  	  			
															  	  	  			
															  	  	  		@Override
																  	  	  	public void run() {															  	  	  			
															  	  	  			
																  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
																		  		centerscrolltext.startAnimation(animAlphaText);			  		
																				centerscrolltext.append("\n" + "> The computer rolls a total of " + totalAttackDamage + " for critical hit damage.");
																				
																				
																				final Handler h = new Handler();
																	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																	  	  	  			
																	  	  	  		@Override
																		  	  	  	public void run() {
																	  	  	  			
																		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
																				  		centerscrolltext.startAnimation(animAlphaText);			  		
																						centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (totalAttackDamage * 2) + ".");
																						
																																																
																						ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - (totalAttackDamage * 2);
																						
																						
																						TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
																		    			playerHitPointsTextView.setTypeface(typeFace);
																		    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
																		    			//playerHitPointsTextView.bringToFront();
																						
																				
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
																				
																						final Handler h = new Handler();
																			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																			  	  	  			
																			  	  	  		@Override
																				  	  	  	public void run() {
																			  	  	  			
																				  	  	  		if (ArrayOfHitPoints.hitpoints[0] == 0) {
																									
																									centerscrolltext.setVisibility(View.VISIBLE);													
																							  		centerscrolltext.startAnimation(animAlphaText);			  		
																									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been knocked unconscious!");
																									
																									/*
																									AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
																								    
																									alert.setCancelable(false);
																									
																									alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious.");
																						  	    	
																						  	    	//alert.setMessage("something");
																						  	    		    	
																							    	
																							    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
																								    	public void onClick(DialogInterface dialog, int whichButton) {
																								    */		
																								    		//hideNavigation();
																								    		
																									final Handler h = new Handler();
																						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																						  	  	  			
																						  	  	  		@Override
																							  	  	  	public void run() {
																						  	  	  			
																							  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																							  	    			
																							  	    			gameEngineComputerFirst2();   							
																							  				}
																			
																										  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																										  						
																										  		turn();    							
																										  	}
																							  	  	  	}
																						  	  	  	}, 2000);																								
																										  	
																										  	//dialog.dismiss();
																								    	//}
																							    	//});
																							    	
																							    	//alert.show(); 
																									
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
																								    
																									alert.setCancelable(false);
																									
																									alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain.");
																						  	    	/*
																						  	    	alert.setMessage("something");
																						  	    	*/	    	
																							    	
																							    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
																								    	public void onClick(DialogInterface dialog, int whichButton) {
																								    		
																								    		//hideNavigation();
																								    		
																								    		playerDeadYet[0] = "yes";
																								    		
																								    		gameOverCheck();
																								    		
																								    		dialog.dismiss();
																								    	}
																							    	});
																							    	
																							    	alert.show();			
																									
																									/*
																									System.out.print("Press a key to continue... ");
																									Scanner input = new Scanner(System.in);
																									input.nextLine();
																									*/							
																								}
																								
																								else {
																									
																									if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																					  	    			
																					  	    			gameEngineComputerFirst2();   							
																					  				}
																	
																								  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																								  						
																								  		turn();    							
																								  	}
																								}	  	  	  			
																				  	  	  	}
																			  	  	  	}, 2000);
																		  	  	  	}
																	  	  	  	}, 2000);																							
																  	  	  	}
															  	  	  	}, 2000);																						
														  	  	  	}
													  	  	  	}, 2000);															  	  	  		
												  	  	  	}
											  	  	  	}, 2000);																	
										  	  	  	}
									  	  	  	}, 2000);															
								  	  	  	}
							  	  	  	}, 2000);					  	  	  		
						  	  	  	}
					  	  	  	}, 2000);												
				  	  	  	}
			  	  	  	}, 2000);							
		  	  	  	}
	  	  	  	}, 3000);		  	  	  			
  	  	    }
		});
		// NEED THIS?:
		//return;
	}
	
	public void computerCriticalHitMightyBlowDamage() {// WAS int[]					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	 			
				
				
	  			mightyBlowGraphic();  			  	  	
						  	  	
						  	  	
		  	  	final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		//final TextView mightyBlowGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//mightyBlowGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);			  		
						centerscrolltext.append("\n" + "> The computer rolls twice for critical hit damage...");
						
						
						final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> The computer makes it's first roll...");											
								
								//int resultOne = (int) ((Math.random() * 6) + 1);
								//int attackDamageOne = resultOne;
								
								SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
								SharedPreferences.Editor editor = preferences.edit();		
								editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
								editor.apply();
								
								preventattackdamagediefromleaking = "off";							
								
								
								final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			computerRolls6SidedDie();
					  	  	  			
					  	  	  			
						  	  	  		final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
							  	  	  			preventattackdamagediefromleaking = "on";
							  	  	  			
							  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
							  	  	  			//SharedPreferences.Editor editor = preferences.edit();
							  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
							  	  	  			
							  	  	  			final int resultOne = attackDamage;
							  	  	  			
								  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);			  		
												centerscrolltext.append("\n" + "> The computer rolls " + resultOne + ".");
												
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
										  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
												  		centerscrolltext.startAnimation(animAlphaText);			  		
														centerscrolltext.append("\n" + "> The computer makes it's second roll...");																	
														
														//int resultTwo = (int) ((Math.random() * 6) + 1);
														//int attackDamageTwo = resultTwo;
														
														SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
														SharedPreferences.Editor editor = preferences.edit();		
														editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
														editor.apply();
														
														preventattackdamagediefromleaking = "off";														
														
														
														final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
											  	  	  			computerRolls6SidedDie();
											  	  	  			
											  	  	  			
												  	  	  		final Handler h = new Handler();
													  	  	  	h.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
													  	  	  			preventattackdamagediefromleaking = "on";
													  	  	  			
													  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
													  	  	  			//SharedPreferences.Editor editor = preferences.edit();
													  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
													  	  	  			
													  	  	  			int resultTwo = attackDamage;
													  	  	  			
														  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
																  		centerscrolltext.startAnimation(animAlphaText);			  		
																		centerscrolltext.append("\n" + "> The computer rolls " + resultTwo	+ ".");
																		
																		final int totalAttackDamage = (resultOne + resultTwo);
																		
																		
																		final Handler h = new Handler();
															  	  	  	h.postDelayed(new Runnable() {		  	  	  			
															  	  	  			
															  	  	  		@Override
																  	  	  	public void run() {																  	  	  		
																				
																				centerscrolltext.setVisibility(View.VISIBLE);													
																		  		centerscrolltext.startAnimation(animAlphaText);			  		
																				centerscrolltext.append("\n" + "> The computer rolls a total of " + totalAttackDamage + " for critical hit damage.");
																				
																				
																				final Handler h = new Handler();
																	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																	  	  	  			
																	  	  	  		@Override
																		  	  	  	public void run() {
																	  	  	  			
																		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
																				  		centerscrolltext.startAnimation(animAlphaText);			  		
																						centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (totalAttackDamage * 2) + ".");																							
																						
																						
																						ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - (totalAttackDamage * 2);
																						
																						
																						TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
																		    			playerHitPointsTextView.setTypeface(typeFace);
																		    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
																		    			//playerHitPointsTextView.bringToFront();
																						
																						
																						final Handler h = new Handler();
																			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																			  	  	  			
																			  	  	  		@Override
																				  	  	  	public void run() {
																			  	  	  			
																				  	  	  		if (ArrayOfHitPoints.hitpoints[0] == 0) {
																									
																									centerscrolltext.setVisibility(View.VISIBLE);													
																							  		centerscrolltext.startAnimation(animAlphaText);			  		
																									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0]	+ ", you have been knocked unconscious!");
																									
																									/*
																									AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
																								    
																									alert.setCancelable(false);
																									
																									alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious.");
																						  	    	
																						  	    	//alert.setMessage("something");
																						  	    		    	
																							    	
																							    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
																								    	public void onClick(DialogInterface dialog, int whichButton) {
																								    */		
																								    		//hideNavigation();
																								    		
																									final Handler h = new Handler();
																						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																						  	  	  			
																						  	  	  		@Override
																							  	  	  	public void run() {
																						  	  	  			
																							  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																							  	    			
																							  	    			gameEngineComputerFirst2();   							
																							  				}
																			
																										  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																										  						
																										  		turn();    							
																										  	}
																										  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																										  	    			
																										  		computerHastePartTwo();   							
																										  	}
																					
																										  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																										  						
																										  		computerHastePartTwo();    							
																										  	}
																							  	  	  	}
																						  	  	  	}, 2000);																								
																										  	
																										  	//dialog.dismiss();
																								    	//}
																							    	//});
																							    	
																							    	//alert.show();	
																									
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
																								    
																									alert.setCancelable(false);
																									
																									alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain.");
																						  	    	/*
																						  	    	alert.setMessage("something");
																						  	    	*/	    	
																							    	
																							    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
																								    	public void onClick(DialogInterface dialog, int whichButton) {
																								    		
																								    		//hideNavigation();
																								    		
																								    		playerDeadYet[0] = "yes";
																								    		
																											gameOverCheck();
																											
																											dialog.dismiss();
																								    	}
																							    	});
																							    	
																							    	alert.show();
																									
																									/*
																									System.out.print("Press a key to continue... ");
																									Scanner input = new Scanner(System.in);
																									input.nextLine();
																									*/
																									
																							    	//HERE?:
																									//playerDeadYet[0] = "yes";
																									//gameOverCheck();
																								}
																								
																								else {
																									
																									if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																					  	    			
																					  	    			gameEngineComputerFirst2();   							
																					  				}
																			
																								  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																								  						
																								  		turn();    							
																								  	}
																								  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																								  	    			
																								  		computerHastePartTwo();   							
																								  	}
																			
																								  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																								  						
																								  		computerHastePartTwo();    							
																								  	}
																								}
																				  	  	  	}
																			  	  	  	}, 2000);																										
																		  	  	  	}
																	  	  	  	}, 2000);																							
																  	  	  	}
															  	  	  	}, 2000);																						
														  	  	  	}
													  	  	  	}, 2000);																  	  	  		
												  	  	  	}
											  	  	  	}, 2000);																		
										  	  	  	}
									  	  	  	}, 2000);															
								  	  	  	}
							  	  	  	}, 2000);										  	  	  		
						  	  	  	}
					  	  	  	}, 2000);											
				  	  	  	}
			  	  	  	}, 2000);										
		  	  	  	}
	  	  	  	}, 6000);	  	  	  	
  	  	    }
		});
		//return ArrayOfHitPoints.hitpoints;
	}
	
	public void computerCriticalHitDamage() {// WAS int[]					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			if (mightyBlowSpell[1] > 0 && iscomputerhasteused.equals("no") && iscomputerblessrolled.equals("no")) {
					
					int computerUseMightyBlow = (int) ((Math.random() * 20) + 1);
					// using a 20-based percentage calculation.
		
					if (ArrayOfHitPoints.hitpoints[0] >= 12) {
						// using just 1 scenario since it's a criticle hit (computerAttackAgainstDisarmed used 2 scenarios)
					
						if (computerUseMightyBlow >= 11) {
							// 50/50 chance to use MB if human HP above 12.
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer uses mighty blow.");
							
							/*
							for (int x = 0; x < 1000; --x) {
							}
							*/
							
							
							mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
							
							skillsCheck();	
							
							computerCriticalHitMightyBlowDamage();
							return;
						} 
						
						else { 
													
							computerCriticalHitDamageResults();
							return;
						}
					}
					
					if (ArrayOfHitPoints.hitpoints[0] < 12) {// if human HP below 12 then MB
						
						centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);			  		
						centerscrolltext.append("\n" + "> The computer uses mighty blow.");
						
						/*
						for (int x = 0; x < 1000; --x) {
						}
						*/
						
						
						mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
						
						skillsCheck();
						
						computerCriticalHitMightyBlowDamage();
						return;
					}
				}
	  			
	  			else {
	  				
	  				computerCriticalHitDamageResults();
					return;	  				
	  			}
  	  	    }
		});
		//return ArrayOfHitPoints.hitpoints;
	}
	
	public void computerCriticalHitDamageResults() {
		
		iscomputerblessrolled = "no";
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer will roll twice for critical hit damage.");
				
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);			  		
						centerscrolltext.append("\n" + "> The computer's first roll...");					
						
						//int resultOne = (int) ((Math.random() * 6) + 1);
						//int attackDamageOne = resultOne;
						
						SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
						SharedPreferences.Editor editor = preferences.edit();		
						editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
						editor.apply();
						
						preventattackdamagediefromleaking = "off";
						
						
						final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			computerRolls6SidedDie();
			  	  	  			
			  	  	  			
				  	  	  		final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			preventattackdamagediefromleaking = "on";
					  	  	  			
					  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
					  	  	  			//SharedPreferences.Editor editor = preferences.edit();
					  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
					  	  	  			
					  	  	  			final int resultOne = attackDamage;
					  	  	  			
						  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);			  		
										centerscrolltext.append("\n" + "> The computer rolls " + resultOne + ".");
										
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
								  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);			  		
												centerscrolltext.append("\n" + "> The computer's second roll...");									
												
												//int resultTwo = (int) ((Math.random() * 6) + 1);
												//int attackDamageTwo = resultTwo;
												
												SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
												SharedPreferences.Editor editor = preferences.edit();		
												editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
												editor.apply();
												
												preventattackdamagediefromleaking = "off";											
												
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
									  	  	  			computerRolls6SidedDie();				  	  	  			
									  	  	  			
									  	  	  			
										  	  	  		final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
											  	  	  			preventattackdamagediefromleaking = "on";											  	  	  			
											  	  	  			
											  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
											  	  	  			//SharedPreferences.Editor editor = preferences.edit();
											  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
										  	  	  			
											  	  	  			int resultTwo = attackDamage;
											  	  	  			
												  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
														  		centerscrolltext.startAnimation(animAlphaText);			  		
																centerscrolltext.append("\n" + "> The computer rolls " + resultTwo + ".");
																
																final int totalAttackDamage = (resultOne + resultTwo);
																
																
																final Handler h = new Handler();
													  	  	  	h.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {													  	  	  			
														  	  	  																				
																		centerscrolltext.setVisibility(View.VISIBLE);													
																  		centerscrolltext.startAnimation(animAlphaText);			  		
																		centerscrolltext.append("\n" + "> The computer rolls a total of " + totalAttackDamage + " for critical hit damage.");
																																		
																
																		ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - totalAttackDamage;
																		
																		
																		TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
														    			playerHitPointsTextView.setTypeface(typeFace);
														    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
														    			//playerHitPointsTextView.bringToFront();
																		
																		
																		final Handler h = new Handler();
															  	  	  	h.postDelayed(new Runnable() {		  	  	  			
															  	  	  			
															  	  	  		@Override
																  	  	  	public void run() {
															  	  	  			
																  	  	  		if (ArrayOfHitPoints.hitpoints[0] == 0) {
																					
																					centerscrolltext.setVisibility(View.VISIBLE);													
																			  		centerscrolltext.startAnimation(animAlphaText);			  		
																					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0]	+ ", you have been knocked unconscious!");
																					
																					/*
																					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
																				    
																					alert.setCancelable(false);
																					
																					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious.");
																		  	    	
																		  	    	//alert.setMessage("something");
																		  	    		    	
																			    	
																			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
																				    	public void onClick(DialogInterface dialog, int whichButton) {
																				    */		
																				    		//hideNavigation();
																				    
																					final Handler h = new Handler();
																		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																		  	  	  			
																		  	  	  		@Override
																			  	  	  	public void run() {
																		  	  	  			
																			  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																			  	    			
																			  	    			gameEngineComputerFirst2();   							
																			  				}
															
																						  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																						  						
																						  		turn();    							
																						  	}
																						  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																						  	    			
																						  		computerHastePartTwo();   							
																						  	}
																	
																						  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																						  						
																						  		computerHastePartTwo();    							
																						  	}
																			  	  	  	}
																		  	  	  	}, 2000);																	    		
																						  	
																						  	//dialog.dismiss();
																				    	//}
																			    	//});
																			    	
																			    	//alert.show();
																					
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
																				    
																					alert.setCancelable(false);
																					
																					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain.");
																		  	    	/*
																		  	    	alert.setMessage("something");
																		  	    	*/	    	
																			    	
																			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
																				    	public void onClick(DialogInterface dialog, int whichButton) {
																				    		
																				    		//hideNavigation();
																				    		
																				    		playerDeadYet[0] = "yes";
																				    		
																							gameOverCheck();
																							
																							dialog.dismiss();
																				    	}
																			    	});
																			    	
																			    	alert.show();
																					
																					/*
																					System.out.print("Press a key to continue... ");
																					Scanner input = new Scanner(System.in);
																					input.nextLine();
																					*/
																			    	
																					// HERE?:
																					//playerDeadYet[0] = "yes";
																					//gameOverCheck();
																				}
																				
																				else {
																					
																					if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																	  	    			
																	  	    			gameEngineComputerFirst2();   							
																	  				}
															
																				  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																				  						
																				  		turn();    							
																				  	}
																				  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																				  	    			
																				  		computerHastePartTwo();   							
																				  	}
															
																				  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																				  						
																				  		computerHastePartTwo();    							
																				  	}				
																				}
																  	  	  	}
															  	  	  	}, 2000);												
														  	  	  	}
													  	  	  	}, 2000);															
												  	  	  	}
											  	  	  	}, 2000);				  	  	  		
										  	  	  	}
									  	  	  	}, 2000);				
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
	
	public void computerCriticalMiss() {					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		  		// Use a blank drawable to hide the imageview animation:
		  		// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
		  		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
		  		img.setBackgroundResource(R.drawable.twentytwentyblank);
		  		img.setImageResource(R.drawable.twentytwentyblank);
	  			
	  			
	  			criticalMissGraphic(); 	  	
				  	  	
				  	  	
		  	  	final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		//final TextView criticalMissGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//criticalMissGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);			  		
						centerscrolltext.append("\n" + "> The computer player must roll to see if it hit itself...");
						
						// for(int x = 0; x < 100; --x)
						// {}
						
						computerCriticalMissAttack();
						//return;
		  	  	  	}
	  	  	  	}, 6000);	  	  	  				
  	  	    }
		});		
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
  	  	    	
	  			
	  			//int criticalMissAttackResult = (int) ((Math.random() * 20) + 1);
  	  	  		
  	  			ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
  	  			
	  			
	  			final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {		  	  	  				  	  	  		
		  	  	  		
		  	  	  		computerRolls20SidedDie();
		  	  	  		
		  	  	  		
			  	  	  	final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ".");
								
								
								if (ArrayOfAttackResult.attackResult[0] >= 17) {
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> The computer hits itself.");
											
											computerCriticalMissDamage();
											
											//return;
							  	  	  	}
						  	  	  	}, 2000);								
								
								} 
								
								else {
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> The computer did not hit itself... now it must roll to see if it loses it's weapon...");
											
											ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
											
											
											final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {								  	  	  			
								  	  	  			
								  	  	  			computerRolls20SidedDie();
								  	  	  			
								  	  	  			
									  	  	  		final Handler h = new Handler();
										  	  	  	h.postDelayed(new Runnable() {		  	  	  			
										  	  	  			
										  	  	  		@Override
											  	  	  	public void run() {
										  	  	  			
											  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
													  		centerscrolltext.startAnimation(animAlphaText);			  		
															centerscrolltext.append("\n" + "> The computer rolls " + ArrayOfAttackResult.attackResult[0] + ".");
															
															
															if (ArrayOfAttackResult.attackResult[0] >= 17) {
																
																canHasDisarmed[1] = "yes";
																
																didComputerCriticalMiss = "yes";
																
																disarmedTurnStart[1] = turn;
																
																
																final Handler h = new Handler();
													  	  	  	h.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
														  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
																  		centerscrolltext.startAnimation(animAlphaText);			  		
																		centerscrolltext.append("\n" + "> The computer is disarmed.");
																		
																		
																		final Handler h = new Handler();
															  	  	  	h.postDelayed(new Runnable() {		  	  	  			
															  	  	  			
															  	  	  		@Override
																  	  	  	public void run() {
															  	  	  			
																  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																  	    			
																  	    			gameEngineComputerFirst2();   							
																  				}
													
																			  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																			  						
																			  		turn();    							
																			  	}
																			  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																			  	    			
																			  		computerHastePartTwo();   							
																			  	}
													
																			  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																			  						
																			  		computerHastePartTwo();    							
																			  	}			  	  	  			
																  	  	  	}
															  	  	  	}, 2000);
														  	  	  	}
													  	  	  	}, 2000);														
															} 
															
															else {
																
																final Handler h = new Handler();
													  	  	  	h.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {
													  	  	  			
														  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
																  		centerscrolltext.startAnimation(animAlphaText);			  		
																		centerscrolltext.append("\n" + "> The computer holds on to it's weapon.");
																		
																		
																		final Handler h = new Handler();
															  	  	  	h.postDelayed(new Runnable() {		  	  	  			
															  	  	  			
															  	  	  		@Override
																  	  	  	public void run() {
															  	  	  			
																  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
																  	    			
																  	    			gameEngineComputerFirst2();   							
																  				}
													
																			  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
																			  						
																			  		turn();    							
																			  	}
																			  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
																			  	    			
																			  		computerHastePartTwo();   							
																			  	}
													
																			  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
																			  						
																			  		computerHastePartTwo();    							
																			  	}			  	  	  			
																  	  	  	}
															  	  	  	}, 2000);
														  	  	  	}
													  	  	  	}, 2000);															
															}
											  	  	  	}
										  	  	  	}, 2000);							
									  	  	  	}
								  	  	  	}, 2000);				
							  	  	  	}
						  	  	  	}, 2000);								
								}								
				  	  	  	}
			  	  	  	}, 2000);						
		  	  	  	}
	  	  	  	}, 2000);				
  	  	    }
		});
	}
	
	public void computerCriticalMissDamage() {// WAS int[]				
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
	  			
	  			final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);			  		
						centerscrolltext.append("\n" + "> The computer must roll for damage to itself...");
						
						// for(int x = 0; x < 100; --x)
						// {}
						
						//int result = (int) ((Math.random() * 6) + 1);
						//int attackDamage = result;
						
						SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
						SharedPreferences.Editor editor = preferences.edit();		
						editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
						editor.apply();

						
						preventattackdamagediefromleaking = "off";
						
						// Use a blank drawable to hide the imageview animation:
						// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
						ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
						img.setBackgroundResource(R.drawable.twentytwentyblank);
						img.setImageResource(R.drawable.twentytwentyblank);
						
						
						final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			computerRolls6SidedDie();
			  	  	  			
			  	  	  			
				  	  	  		final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			preventattackdamagediefromleaking = "on";
					  	  	  			
					  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
					  	  	  			//SharedPreferences.Editor editor = preferences.edit();
					  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
					  	  	  			
						  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);			  		
										centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + " for damage.");										
										
										
										ArrayOfHitPoints.hitpoints[1] = ArrayOfHitPoints.hitpoints[1] - attackDamage;
										
										
										TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
						    			computerHitPointsTextView.setTypeface(typeFace);
						    			computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[1]));
						    			//computerHitPointsTextView.bringToFront();
										
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {							  	  	  			
								    			
								  	  	  		if (ArrayOfHitPoints.hitpoints[1] == 0) {
													
													centerscrolltext.setVisibility(View.VISIBLE);													
											  		centerscrolltext.startAnimation(animAlphaText);			  		
													centerscrolltext.append("\n" + "> The computer has been knocked unconscious!");
													
													/*
													AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
												    
													alert.setCancelable(false);
													
													alert.setTitle("The computer has been knocked unconscious.");
										  	    	
										  	    	//alert.setMessage("something");										  	    		    	
											    	
													
													
											    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
												    	public void onClick(DialogInterface dialog, int whichButton) {
												    */		
												    		//hideNavigation();
													
												    
													final Handler h = new Handler();
										  	  	  	h.postDelayed(new Runnable() {		  	  	  			
										  	  	  			
										  	  	  		@Override
											  	  	  	public void run() {
										  	  	  			
											  	  	  		if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
											  	    			
											  	    			gameEngineComputerFirst2();   							
											  				}
							
														  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
														  						
														  		turn();    							
														  	}
														  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
														  	    			
														  		computerHastePartTwo();   							
														  	}
									
														  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
														  						
														  		computerHastePartTwo();    							
														  	}
											  	  	  	}
										  	  	  	}, 2000);										    		
														  	
														  	//dialog.dismiss();
												    	//}
											    	//});
											    	
											    	//alert.show();												
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
												    
													alert.setCancelable(false);
													
													alert.setTitle("The computer has been slain.");
										  	    	/*
										  	    	alert.setMessage("something");
										  	    	*/	    	
											    	
											    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
												    	public void onClick(DialogInterface dialog, int whichButton) {
												    		
												    		//hideNavigation();
												    		
												    		playerDeadYet[1] = "yes";
												    		
															gameOverCheck();
															
															dialog.dismiss();
												    	}
											    	});
											    	
											    	alert.show();
													
													/*
													System.out.print("Press a key to continue... ");
													Scanner input = new Scanner(System.in);
													input.nextLine();
													*/					
												}
												
												else {
													
													if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {		
									  	    			
									  	    			gameEngineComputerFirst2();   							
									  				}
							
												  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("no")) {				
												  						
												  		turn();    							
												  	}
												  	if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {		
												  	    			
												  		computerHastePartTwo();   							
												  	}
							
												  	if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && iscomputerhasteused.equals("yes")) {				
												  						
												  		computerHastePartTwo();    							
												  	}
												}
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
		//return ArrayOfHitPoints.hitpoints;
	}
	
	public void computerCriticalHitDamageDisarmed() {// WAS int[]						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);  			
  	  	    	
		
	  			if (mightyBlowSpell[1] > 0) {
					
					int computerUseMightyBlow = (int) ((Math.random() * 20) + 1);
					// using a 20-based percentage calculation.
		
					if (ArrayOfHitPoints.hitpoints[0] >= 12) {
						// using just 1 scenario since it's a criticle hit (computerAttackAgainstDisarmed used 2 scenarios)
					
						if (computerUseMightyBlow >= 11) {
							// 50/50 chance to use MB if human HP above 12.
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer uses mighty blow.");
							
							/*
							for (int x = 0; x < 1000; --x) {
							}
							*/
							
							
							mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
							
							skillsCheck();														
							
							computerCriticalHitMightyBlowDamageDisarmed();
							return;
						}
						
						else {
							
							computerCriticalHitDamageDisarmedResults();
							return;
						}
					}
					
					if (ArrayOfHitPoints.hitpoints[0] < 12) {// if human HP below 12 then MB						
					
						computerCriticalHitMightyBlowDamageDisarmed();
						return;
					}
				}
	  			
	  			else {
	  				
	  				computerCriticalHitDamageDisarmedResults();	  				
	  			}				
  	  	    }
		});
		//return ArrayOfHitPoints.hitpoints;
	}
	
	public void computerCriticalHitDamageDisarmedResults() {
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
		SharedPreferences.Editor editor = preferences.edit();		
		editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
		editor.apply();
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	  			
	  			
		
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);			  		
				centerscrolltext.append("\n" + "> The computer will roll twice for critical hit damage.");
				
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);			  		
						centerscrolltext.append("\n" + "> The computer's first roll...");					
						
						//int resultOne = (int) ((Math.random() * 6) + 1);
						//int attackDamageOne = resultOne;
						//int attackDamageOneDisarmed = (resultOne - 2);
						
						//if (attackDamageOneDisarmed < 0) {
						//	attackDamageOneDisarmed = 0;
						//}
						
						//Globals g = Globals.getInstance();
						//g.setDataAttackDamage((int) ((Math.random() * 6) + 1));
						
						preventattackdamagediefromleaking = "off";
						
						SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
						//SharedPreferences.Editor editor = preferences.edit();
						int attackDamage = preferences.getInt("attackDamage", 0);
						
						final int resultOne = attackDamage;
						
						attackDamageOneDisarmed[0] = (resultOne - 2);
						
						if (attackDamageOneDisarmed[0] < 0) {
							attackDamageOneDisarmed[0] = 0;
						}
						
						
						final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			computerRolls6SidedDie();
			  	  	  			
			  	  	  			
				  	  	  		final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			preventattackdamagediefromleaking = "on";
					  	  	  			
					  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
					  	  	  			//SharedPreferences.Editor editor = preferences.edit();
					  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
					  	  	  			
						  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);			  		
										centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + ", -2 damage for punch = " + attackDamageOneDisarmed[0] + " damage.");
										
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
								  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);			  		
												centerscrolltext.append("\n" + "> The computer's second roll...");										
												
												
												//int resultTwo = (int) ((Math.random() * 6) + 1);
												//int attackDamageTwo = resultTwo;
												//int attackDamageTwoDisarmed = (resultTwo - 2);
												
												//if (attackDamageTwoDisarmed < 0) {
												//	attackDamageTwoDisarmed = 0;
												//}
												
												SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
												SharedPreferences.Editor editor = preferences.edit();		
												editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
												editor.apply();
												
												preventattackdamagediefromleaking = "off";												
												
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
									  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
									  	  	  			//SharedPreferences.Editor editor = preferences.edit();
									  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
														
														int resultTwo = attackDamage;
														
														attackDamageTwoDisarmed[0] = (resultTwo - 2);
														
														if (attackDamageTwoDisarmed[0] < 0) {
															attackDamageTwoDisarmed[0] = 0;
														}
									  	  	  			
									  	  	  			computerRolls6SidedDie();
									  	  	  			
									  	  	  			
										  	  	  		final Handler h = new Handler();
											  	  	  	h.postDelayed(new Runnable() {		  	  	  			
											  	  	  			
											  	  	  		@Override
												  	  	  	public void run() {
											  	  	  			
											  	  	  			preventattackdamagediefromleaking = "on";
											  	  	  			
											  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
											  	  	  			//SharedPreferences.Editor editor = preferences.edit();
											  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
											  	  	  			
												  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
														  		centerscrolltext.startAnimation(animAlphaText);			  		
																centerscrolltext.append("\n" + "> The computer rolls " + attackDamage + ", -2 damage for punch = " + attackDamageTwoDisarmed[0] + " damage.");
																
																final int totalAttackDamage = (attackDamageOneDisarmed[0] + attackDamageTwoDisarmed[0]);
																
																
																final Handler h = new Handler();
													  	  	  	h.postDelayed(new Runnable() {		  	  	  			
													  	  	  			
													  	  	  		@Override
														  	  	  	public void run() {														  	  	  		
																		
																		centerscrolltext.setVisibility(View.VISIBLE);													
																  		centerscrolltext.startAnimation(animAlphaText);			  		
																		centerscrolltext.append("\n" + "> The computer rolls a total of " + totalAttackDamage + " for critical hit damage.");
																		
																		
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
																		
																		
																		TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
														    			playerHitPointsTextView.setTypeface(typeFace);
														    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
														    			//playerHitPointsTextView.bringToFront();
																		
																		
																		final Handler h = new Handler();
															  	  	  	h.postDelayed(new Runnable() {		  	  	  			
															  	  	  			
															  	  	  		@Override
																  	  	  	public void run() {
															  	  	  			
																  	  	  		if (ArrayOfHitPoints.hitpoints[0] == 0) {
																					
																					centerscrolltext.setVisibility(View.VISIBLE);													
																			  		centerscrolltext.startAnimation(animAlphaText);			  		
																					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0]	+ ", you have been knocked unconscious!");
																					
																					/*
																					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
																				    
																					alert.setCancelable(false);
																					
																					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been knocked unconscious.");
																		  	    	
																		  	    	//alert.setMessage("something");
																		  	    		    	
																			    	
																			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
																				    	public void onClick(DialogInterface dialog, int whichButton) {
																				    */		
																				    		//hideNavigation();
																				    
																					final Handler h = new Handler();
																		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
																		  	  	  			
																		  	  	  		@Override
																			  	  	  	public void run() {
																		  	  	  			
																			  	  	  		if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
																			  	    			
																			  	    			gameEngineComputerFirst2();   							
																			  				}
															
																						  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
																						  						
																						  		turn();    							
																						  	}
																			  	  	  	}
																		  	  	  	}, 2000);																	    		
																						  	
																						  	//dialog.dismiss();
																				    	//}
																			    	//});
																			    	
																			    	//alert.show();
																					
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
																				    
																					alert.setCancelable(false);
																					
																					alert.setTitle(ArrayOfPlayers.player[0] + ", you have been slain.");
																		  	    	/*
																		  	    	alert.setMessage("something");
																		  	    	*/	    	
																			    	
																			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
																				    	public void onClick(DialogInterface dialog, int whichButton) {
																				    		
																				    		//hideNavigation();
																				    		
																				    		playerDeadYet[0] = "yes";
																				    		
																							gameOverCheck();
																							
																							dialog.dismiss();
																				    	}
																			    	});
																			    	
																			    	alert.show();
																					
																					/*
																					System.out.print("Press a key to continue... ");
																					Scanner input = new Scanner(System.in);
																					input.nextLine();
																					*/					
																				}
																				
																				else {
																					
																					if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
																	  	    			
																	  	    			gameEngineComputerFirst2();   							
																	  				}
															
																				  	if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {				
																				  						
																				  		turn();    							
																				  	}					
																				}
																  	  	  	}
															  	  	  	}, 2000);																	
														  	  	  	}
													  	  	  	}, 2000);																
												  	  	  	}
											  	  	  	}, 2000);										  	  	  		
										  	  	  	}
									  	  	  	}, 2000);											
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
	
	
	/*
	 * 
	 * 
	 * 
	 * Human Methods*****************************************************************************************
	 * 
	 * 
	 * 
	 */
	
	
	public void attack() {// WAS int

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
  	  	    	
	  			/*
				if (canHasDisarmed[playerNumberAttacked] == "yes")
					// the goto method to get bonus if attacking disarmed opponent.
				{
					attackAgainstDisarmed();
					return;
				}
				*/				 
				
  	  	  		final Handler h2 = new Handler();
	  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
	  	  	  			ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			img.bringToFront();
	  	  	  			
	  	  	  			twentySidedRollFromLeft();				  	  	  		
			  	  	  			
		  	  	  		final Handler h3 = new Handler();
			  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {				
			  	  	  			
			  	  	  			twentySidedWobbleStart();
			  	  	  			
			  	  	  			isTwentySidedReadyToBeRolled = "yes";
			  	  	  			
								centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> Please slide the die...");
								
								ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				  	  	    	titleBlankButton.bringToFront();
						
								/*
								 * 
								 * 
								 * SLIDE 20-SIDED DIE
								 * 
								 * 
								 */
						
								ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
								//int attackResult = (int) ((Math.random() * 20) + 1);										
								
								isattackrolled = "yes";
								
								// Re-enables ability to use srollbar:
					  			centerscrolltext.bringToFront();
			  	  	  		}
			  	  	  	}, 750);					  	  	  		
	  	  	  		}
	  	  	  	}, 2000);	  	  	  														
  	  	    }
		});
		//return playerNumberAttacked;
	}
					  	  	  		
	public void attackResults() {
		
		isTwentySidedReadyToBeRolled = "no";
		
		// Here to prevent pre-mature (BUT STILL SEE ) roll
		final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);		
		twentySidedBlank.setEnabled(false);
		
		isattackrolled = "no";
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			if (canHasDisarmed[0].equals("no")) {
	  				
	  				final Handler h1 = new Handler();
		  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {		
		  	  	  			
			  	  	  		if (canHasDisarmed[playerNumberAttacked].equals("no")) {
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ".");			  	  	  			
			  	  	  		}
			  	  	  		
				  	  	  	if (canHasDisarmed[playerNumberAttacked].equals("yes")) {				  	  	  		
				  	  	  		
					  	  	  	centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ", +2 for your opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 2));			  	  	  			
			  	  	  		}												
							
							final Handler h2 = new Handler();
				  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {						
					
									if (ArrayOfAttackResult.attackResult[0] >= 20) {
										
										criticalHit();
										return;
									}
									
									if (canHasDisarmed[playerNumberAttacked].equals("no")) {
										
										if (ArrayOfAttackResult.attackResult[0] >= 14 && ArrayOfAttackResult.attackResult[0] <= 19) {
											
											centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> Your attack hits.");											
											
											damage();
											return;
										}
										
										if (ArrayOfAttackResult.attackResult[0] < 14 && ArrayOfAttackResult.attackResult[0] > 1) {
											
											centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Your attack misses.");
											
											final Handler h3 = new Handler();
								  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {  	  		
					
								  	  	  			if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
													
								  	  	  				gameEngineHumanFirst2();    							
													}
	
													if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
										    			
										    			turn();   							
													}
													
													if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
														
										    			hastePartTwo();    							
													}
	
													if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
										    			
														hastePartTwo();   							
													}					  	  	  	
									  	  	  	}
								  	  	  	}, 2000);
											
											return;
										}
										
										if (ArrayOfAttackResult.attackResult[0] <= 1) {
											
											criticalMiss();
											return;
										}
									}
									
									if (canHasDisarmed[playerNumberAttacked].equals("yes")) {
										
										if (ArrayOfAttackResult.attackResult[0] >= 12 && ArrayOfAttackResult.attackResult[0] <= 19) {
											
											centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> Your attack hits.");
											
											damage();
											return;
										}
										
										if (ArrayOfAttackResult.attackResult[0] < 12 && ArrayOfAttackResult.attackResult[0] > 1) {
											
											centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Your attack misses.");
											
											final Handler h3 = new Handler();
								  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {  	  		
					
								  	  	  			if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
													
									    			gameEngineHumanFirst2();    							
													}
	
													if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
										    			
										    			turn();   							
													}
													
													if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
														
										    			hastePartTwo();    							
													}
	
													if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
										    			
														hastePartTwo();   							
													}					  	  	  	
									  	  	  	}
								  	  	  	}, 2000);
											
											return;
										}
										
										if (ArrayOfAttackResult.attackResult[0] <= 1) {
											
											criticalMiss();
											return;
										}
									}									
				  	  	  		}
				  	  	  	}, 2000);
		  	  	  		}
		  	  	  	}, 2000);	  				
	  			}
	  			
	  			if (canHasDisarmed[0].equals("yes")) {
	  				
	  				final Handler h4 = new Handler();
		  	  	  	h4.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {		
			
			  	  	  		if (canHasDisarmed[playerNumberAttacked].equals("no")) {			  	  	  			
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ", -1 for being disarmed = " + (ArrayOfAttackResult.attackResult[0] - 1));			  	  	  			
			  	  	  		}
			  	  	  		
				  	  	  	if (canHasDisarmed[playerNumberAttacked].equals("yes")) {				  	  	  		
				  	  	  		
					  	  	  	centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ", +1 for being disarmed and your opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 1));			  	  	  			
			  	  	  		}						
							
							final Handler h5 = new Handler();
				  	  	  	h5.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {						
				  	  	  			/* CAN'T CRIT HIT WHEN DISARMED (20-1=19)..ALSO CHANGED THE ArrayOfAttackResult.attackResult[0] FROM 19S TO 20S BELOW.
									if (ArrayOfAttackResult.attackResult[0] >= 20) {
										criticalHit();
										return;
									}
									*/
									if (canHasDisarmed[playerNumberAttacked].equals("no")) {
										
										if (ArrayOfAttackResult.attackResult[0] >= 15 && ArrayOfAttackResult.attackResult[0] <= 20) { // -1 to-hit for being disarmed.
											
											centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> Your punch hits.");
											
											damage();
											return;
										}
										
										if (ArrayOfAttackResult.attackResult[0] < 15 && ArrayOfAttackResult.attackResult[0] >= 1) {
											
											centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Your punch misses.");
											
											final Handler h6 = new Handler();
								  	  	  	h6.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {  	  		
					
								  	  	  			if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
													
									    			gameEngineHumanFirst2();    							
													}
	
													if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
										    			
										    			turn();   							
													}
													
													if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
														
										    			hastePartTwo();    							
													}
	
													if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
										    			
														hastePartTwo();   							
													}					  	  	  	
									  	  	  	}
								  	  	  	}, 2000);
											
											return;
										}
										
										//OLD
										/*if (attackResult <= 1)//CAN YOU CRITICL MISS A PUNCH???????????????????????????????????? -----DISARM AGAIN TO MAINTAIN BALANCE.
									      {
									         criticalMissDisarmed(i, turn, playerNumberAttacked);//NEW METHOD!!!!!!!!!!!!!!!!!!!!!!!
									         return playerNumberAttacked;
									      }*/
										
										//NEW
										// for now ill do no rolls to keep it simpler, but in future might go w roll to hurt yourself (ie stumble & fall) but no roll to lose weapon.
										/*
										if (attackResult <= 1) {
											criticalMiss();
											return;
										}
										*/
									}
									
									if (canHasDisarmed[playerNumberAttacked].equals("yes")) {
										
										if (ArrayOfAttackResult.attackResult[0] >= 13 && ArrayOfAttackResult.attackResult[0] <= 20) { // -1 to-hit for being disarmed but, +2 because computer is disarmed (+1 total)
											
											centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> Your punch hits.");
											
											damage();
											return;
										}
										
										if (ArrayOfAttackResult.attackResult[0] < 13 && ArrayOfAttackResult.attackResult[0] >= 1) {
											
											centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Your punch misses.");
											
											final Handler h6 = new Handler();
								  	  	  	h6.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {  	  		
					
								  	  	  			if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
													
									    			gameEngineHumanFirst2();    							
													}
	
													if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
										    			
										    			turn();   							
													}
													
													if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
														
										    			hastePartTwo();    							
													}
	
													if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
										    			
														hastePartTwo();   							
													}					  	  	  	
									  	  	  	}
								  	  	  	}, 2000);
											
											return;
										}
										
										//OLD
										/*if (attackResult <= 1)//CAN YOU CRITICL MISS A PUNCH???????????????????????????????????? -----DISARM AGAIN TO MAINTAIN BALANCE.
									      {
									         criticalMissDisarmed(i, turn, playerNumberAttacked);//NEW METHOD!!!!!!!!!!!!!!!!!!!!!!!
									         return playerNumberAttacked;
									      }*/
										
										//NEW
										// for now ill do no rolls to keep it simpler, but in future might go w roll to hurt yourself (ie stumble & fall) but no roll to lose weapon.
										/*
										if (attackResult <= 1) {
											criticalMiss();
											return;
										}
										*/
									}									
				  	  	  		}
				  	  	  	}, 2000);
		  	  	  		}
		  	  	  	}, 2000);  				
	  			}  			
	  			//NEED THIS???
	  	  	  	//return; 
  	  	    }
		});		
	}

	public void mightyBlow() {					
		
		// Use a blank drawable to hide the imageview animation:
		// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
  		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
		img.setBackgroundResource(R.drawable.twentytwentyblank);
		img.setImageResource(R.drawable.twentytwentyblank);
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	
  	  	    	
	  			// NO DODGING MIGHTY BLOW:		
				//if (dodgeBlowSpell[playerNumberAttacked] > 0) {
				
	  			mightyBlowGraphic();
	  			
	  			
				if (numberOfPlayers == 1) {
					
					//if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] >= 11) {
						
						//centerscrolltext.setVisibility(View.VISIBLE);
				  		//centerscrolltext.startAnimation(animAlphaText);
						//centerscrolltext.append("\n" + "> The computer does not dodge.");	  	  	  			
					
		  	  	  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {	  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		//final TextView mightyBlowGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
				  	  	  		//mightyBlowGraphic.setVisibility(View.INVISIBLE);
			  	  	  			
			  	  	  			stopGraphics();
			  	  	  			
				  	  	  		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
				  	  	  		img.bringToFront();
				  	  	  		
			  	  	  			sixSidedRollFromLeft();				  	  	  		
					  	  	  			
				  	  	  		final Handler h3 = new Handler();
					  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {				
					  	  	  			
					  	  	  			sixSidedWobbleStart();
					  	  	  			
					  	  	  			isSixSidedReadyToBeRolled = "yes";
					  	  	  			
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Please slide the die...");
										
										ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
						  	  	    	titleBlankButton.bringToFront();
								
										/*
										 * 
										 * ROLL 6-SIDED DIE
										 * 
										 */
										
										SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
										SharedPreferences.Editor editor = preferences.edit();		
										editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
										editor.apply();
										
								        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
								        //same as: (int) Math.ceil(Math.random()*6); ?										
										
										ismightyblowdamagerolled = "yes";
										preventattackdamagediefromleaking = "off";
										
					  	  	  		}
					  	  	  	}, 750);					  	  	  		
			  	  	  		}
			  	  	  	}, 6000);		  	  	  		
					//}
			  	  	/*  	
		  	  	  	if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] <= 10 && ArrayOfHitPoints.hitpoints[playerNumberAttacked] > 0) {
					
						centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> The computer uses it's dodge blow spell.");
						
						mightyBlowSpell[playerNumberAttacked] = mightyBlowSpell[playerNumberAttacked] - 1;
						
						
						final Handler h4 = new Handler();
			  	  	  	h4.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {  	  		

				  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {				
									
					    			gameEngineHumanFirst2();    							
								}

								if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1])) {		
					    			
					    			turn();   							
								}													  	  	  	
				  	  	  	}
			  	  	  	}, 2000);
						
						return;
		  	  	  	}*/
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
				//}				
				
				// NO DODGING MIGHTY BLOW:							
				/*else {
		  			
		  			final Handler h5 = new Handler();
		  	  	  	h5.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {	  	  	  			
							  
							  
							  
							// damage Graphic(); ??????????????							 
							
							
					
			  	  	  		final Handler h6 = new Handler();
				  	  	  	h6.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {					  	  	  			
				  	  	  			
				  	  	  			sixSidedRollFromLeft();				  	  	  		
						  	  	  			
					  	  	  		final Handler h7 = new Handler();
						  	  	  	h7.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {				
						  	  	  			
						  	  	  			sixSidedWobbleStart();
						  	  	  			
											centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Please slide the die...");
									
											
											  
											 // ROLL 6-SIDED DIE
											  
											 
											
											attackDamage = (int)(Math.random()*6)+1;
									        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
									        //same as: (int) Math.ceil(Math.random()*6); ?										
											
											ismightyblowdamagerolled = "yes";										
						  	  	  		}
						  	  	  	}, 750);					  	  	  		
				  	  	  		}
				  	  	  	}, 2000);
		  	  	  		}
		  	  	  	}, 2000);
		  	  	  	
		  	  	  	
					//if (numberOfPlayers > 1) {					
					//}
					
	  			}*/	  			
  	  	    }  	  	    
		});
		// NEED THIS?:
		//return;
	}							
	
	public void mightyBlowResults() {
		
		isSixSidedReadyToBeRolled = "no";
		
		// Here to prevent pre-mature (BUT STILL SEE ) roll
		final ImageView sixSidedBlankAttackDamage = (ImageView) findViewById(R.id.sixsidedanimation);		
		sixSidedBlankAttackDamage.setEnabled(false);
		
		ismightyblowdamagerolled = "no";
		preventattackdamagediefromleaking = "on";
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
	  			
	  			final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		if (canHasDisarmed[0].equals("no")) {
		  	  	  			
		  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
		  	  	  			//SharedPreferences.Editor editor = preferences.edit();
		  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
		  	  	  			
			  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll " + attackDamage	+ " for damage.");
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {				  	  	  			
				  	  	  			
				  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
				  	  	  			//SharedPreferences.Editor editor = preferences.edit();
				  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
				  	  	  			
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (attackDamage * 2) + ".");
									
				
									ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked]	- (attackDamage * 2);
									
									
									TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
									playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
									playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
									//playerNumberAttackedHitPointsTextView.bringToFront();
									
									
									mightyBlowResultsHandler();
					  	  	  	}
				  	  	  	}, 2000);						
		  	  	  		}
						
						if (canHasDisarmed[0].equals("yes")) {							
							
							SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
							//SharedPreferences.Editor editor = preferences.edit();
							int attackDamage = preferences.getInt("attackDamage", 0);
							
							int attackDamageDisarmed = (attackDamage - 2);
							
							if (attackDamageDisarmed < 0) {
								
				                  attackDamageDisarmed = 0;					            
							}
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll " + attackDamage + ", -2 damage for punch = " + attackDamageDisarmed + " damage.");
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
				  	  	  			//SharedPreferences.Editor editor = preferences.edit();
				  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
				  	  	  			
					  	  	  		int attackDamageDisarmed = (attackDamage - 2);
									
									if (attackDamageDisarmed < 0) {
										
						                  attackDamageDisarmed = 0;					            
									}
				  	  	  			
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (attackDamageDisarmed * 2) + ".");
									
				
									ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked]	- (attackDamageDisarmed * 2);
									
									
									TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
									playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
									playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
									//playerNumberAttackedHitPointsTextView.bringToFront();
									
									
									mightyBlowResultsHandler();
					  	  	  	}
				  	  	  	}, 2000);						
						}		  	  	  	
			  	  	  	//NEED THIS?:
						//return;		  	  	  		
	  	  	  		}
	  	  	  	}, 2000);								 
  	  	    }
		});		
	}
	
	public void mightyBlowResultsHandler() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
	  			
	  	  	    final Handler h2 = new Handler();
	  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
				
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] == 0) {
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer has been knocked unconscious!");
							
							/*
							AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
						    
							alert.setCancelable(false);
							
							alert.setTitle("The computer has been knocked unconscious.");
				  	    	
				  	    	//alert.setMessage("something");
				  	    		    	
					    	
					    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int whichButton) {
						    */		
						    		//hideNavigation();
						    
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
					  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
										
						    			gameEngineHumanFirst2();    							
									}
	
									if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
						    			
						    			turn();   							
									}
					  	  	  	}
				  	  	  	}, 2000);			    		
									
									//dialog.dismiss();
						    	//}
					    	//});								    	
					    	//alert.show();						
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
						    
							alert.setCancelable(false);							
							
							alert.setTitle("The computer has been slain.");
				  	    	/*
				  	    	alert.setMessage("something");
				  	    	*/	    	
					    	
					    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int whichButton) {
						    		
						    		//hideNavigation();
						    		
						    		playerDeadYet[playerNumberAttacked] = "yes";
						    		
						    		gameOverCheck();
						    		
						    		dialog.dismiss();
						    	}
					    	});								    	
					    	alert.show();																			
						}
						
						else {									
	
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
								
				    			gameEngineHumanFirst2();    							
							}
	
							if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
				    			
				    			turn();   							
							}									
						}				  	  	  			
		  	  	  	}
	  	  	  	}, 2000);  	  	    	
  	  	    }
		});	
	}
	
	public void damage() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			//if (numberOfPlayers == 1) {
				//}
				
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
	  			
				
	  			// IF DODGE BLOW:
	  			
	  			if (dodgeBlowSpell[playerNumberAttacked] > 0) {					
						
					if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] >= 7) {
						
						final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> The computer does not dodge.");				
								
								
								final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
						  	  	  		if (mightyBlowSpell[0] > 0 && ishasteused.equals("no") && isblessrolled.equals("no") && issecondroundofhasteused.equals("no")) {
											/*
											centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);		  		
											centerscrolltext.append("\n" + ArrayOfPlayers.player[i] + ", do you want to use Mighty Blow?");
											*/
											
											AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
								  			
											alert.setCancelable(false);
											
								  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use Mighty Blow?");
								  	    	/*
								  	    	alert.setMessage("something");
								  	    	*/	  	    	
								  	    	
								  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
								  		    	public void onClick(DialogInterface dialog, int whichButton) {
								  		    		
								  		    		//hideNavigation();
								  		    		
								  		    		mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
								  		    		
								  		    		skillsCheck();					
													
								  		    		mightyBlow();
													//return;
								  		    		
								  		    		dialog.dismiss();
								  		    	}
								  	    	});
								  	    	
								  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
								          	  public void onClick(DialogInterface dialog, int whichButton) {
								          		  
								          		  //hideNavigation();
								          		  
								          		  damagePartTwo();
								          		  //return;
								          		  
								          		  dialog.dismiss();
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
						  	  	  		
										else {
											
											damagePartTwo();
											//return;
										}				  	  	  			
						  	  	  	}
					  	  	  	}, 2000);
				  	  	  	}
			  	  	  	}, 2000);												
					}				  	  	  	
			  	  	  	
					if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] <= 6 && ArrayOfHitPoints.hitpoints[playerNumberAttacked] > 0) {
						
						final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> The computer uses dodge.");
								
								dodgeBlowSpell[playerNumberAttacked] = dodgeBlowSpell[playerNumberAttacked] - 1;
								
								skillsCheck();
								
								// Use a blank drawable to hide the imageview animation:
				  		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				  		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				  		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
				  		    	img.setImageResource(R.drawable.twentytwentyblank);
								
								dodgeGraphic();						
								
								final Handler h4 = new Handler();
					  	  	  	h4.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
						  	  	  		//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
					  	  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
					  	  	  			
					  	  	  			stopGraphics();
					  	  	  			
						  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
											
							    			gameEngineHumanFirst2();    							
										}
	
										if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
							    			
							    			turn();   							
										}
										
										if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
											
							    			hastePartTwo();    							
										}
	
										if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
							    			
											hastePartTwo();   							
										}					  	  	  	
						  	  	  	}
					  	  	  	}, 6000);
								// NEED THIS?:
								//return;
				  	  	  	}
			  	  	  	}, 2000);					
					}					
				}
	  			
	  			// IF NO DODGE BLOW:
	  			
	  			else {
	  			
	  				final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
			  	  	  		if (mightyBlowSpell[0] > 0 && ishasteused.equals("no") && isblessrolled.equals("no") && issecondroundofhasteused.equals("no")) {
								/*
								centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);		  		
								centerscrolltext.append("\n" + ArrayOfPlayers.player[i] + ", do you want to use Mighty Blow?");
								*/
								
								AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
					  			
								alert.setCancelable(false);
								
					  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use Mighty Blow?");
					  	    	/*
					  	    	alert.setMessage("something");
					  	    	*/	  	    	
					  	    	
					  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					  		    	public void onClick(DialogInterface dialog, int whichButton) {
					  		    		
					  		    		//hideNavigation();
					  		    		
					  		    		mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
					  		    		
					  		    		skillsCheck();				
										
					  		    		mightyBlow();
										//return;
					  		    		
					  		    		dialog.dismiss();
					  		    	}
					  	    	});
					  	    	
					  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
					          	  public void onClick(DialogInterface dialog, int whichButton) {
					          		  
					          		  //hideNavigation();
					          		  
					          		  damagePartTwo();
					          		  //return;
					          		  
					          		  dialog.dismiss();
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
			  	  	  		
							else {
								
								damagePartTwo();
								//return;
							}				  	  	  			
			  	  	  	}
		  	  	  	}, 2000);
	  			}	  			
  	  	    }  	  	    
		});
		// NEED THIS?:
		//return;
	}
	
	public void damagePartTwo() {		
		
		// Use a blank drawable to hide the imageview animation:
		// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
  		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
		img.setBackgroundResource(R.drawable.twentytwentyblank);
		img.setImageResource(R.drawable.twentytwentyblank);
		
		img.setEnabled(false);
		
		
		isblessrolled = "no";
		
		if (issecondroundofhasteused.equals("yes")) {
			
			issecondroundofhasteused = "no";
		}
		
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
				centerscrolltext.append("\n" + "> Roll for damage...");
				
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
  	  			img.bringToFront();
	  			
				
  	  	  		final Handler h2 = new Handler();
	  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {  			
	  	  	  			
	  	  	  			sixSidedRollFromLeft();				  	  	  		
			  	  	  			
		  	  	  		final Handler h3 = new Handler();
			  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {				
			  	  	  			
			  	  	  			sixSidedWobbleStart();
			  	  	  			
			  	  	  			isSixSidedReadyToBeRolled = "yes";
			  	  	  			
			  	  	  			ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
			  	  	  			titleBlankButton.bringToFront();
						
								/*
								 * 
								 * ROLL 6-SIDED DIE
								 * 
								 */
								
			  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
			  	  	  			SharedPreferences.Editor editor = preferences.edit();		
			  	  	  			editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
			  	  	  			editor.apply();
			  	  	  			
						        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
						        //same as: (int) Math.ceil(Math.random()*6); ?										
								
								isattackdamagerolled = "yes";
								preventattackdamagediefromleaking = "off";
								
								
								
			  	  	  		}
			  	  	  	}, 750);					  	  	  		
	  	  	  		}
	  	  	  	}, 2000);  	  	  			  			
  	  	    }
		});	
	}
	
	public void damageResults() {
		
		isSixSidedReadyToBeRolled = "no";
		
		// Here to prevent pre-mature (BUT STILL SEE ) roll
		final ImageView img = (ImageView) findViewById(R.id.sixsidedanimation);		
		img.setEnabled(false);
		
		isattackdamagerolled = "no";
		preventattackdamagediefromleaking = "on";
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
	  			
	  			final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		if (canHasDisarmed[0].equals("no")) {							
							
		  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
		  	  	  			//SharedPreferences.Editor editor = preferences.edit();
		  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
		  	  	  			
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll " + attackDamage + " for damage.");
							
		
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - attackDamage;
							
							
							TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
							playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
							playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
							//playerNumberAttackedHitPointsTextView.bringToFront();
							
							
							damageResultsHandler();
						}
							
		  	  	  		else if (canHasDisarmed[0].equals("yes")) {							
							
		  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
		  	  	  			//SharedPreferences.Editor editor = preferences.edit();
		  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
		  	  	  			
							int attackDamageDisarmed = (attackDamage - 2);
							
							if (attackDamageDisarmed < 0) {
								
				                  attackDamageDisarmed = 0;					            
							}
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll " + attackDamage + ", -2 damage for punch = " + attackDamageDisarmed + " damage.");
							
		
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - attackDamageDisarmed;
							
							
							TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
							playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
							playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
							//playerNumberAttackedHitPointsTextView.bringToFront();
							
							
							damageResultsHandler();
						}					
			  	  	  	// NEED THIS?:
						//return;		  	  	  		
	  	  	  		}
	  	  	  	}, 2000);								 
  	  	    }
		});		
	}
	
	public void damageResultsHandler() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
	  			
	  	  	    final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {	  	  	  		
	  	  	  			
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] == 0) {
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer has been knocked unconscious!");
							
							/*
							AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
						    
							alert.setCancelable(false);
							
							alert.setTitle("The computer has been knocked unconscious.");
				  	    	
				  	    	//alert.setMessage("something");
				  	    		    	
					    	
					    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int whichButton) {
						    */		
						    		//hideNavigation();
						    
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
					  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
										
						    			gameEngineHumanFirst2();    							
									}
	
									if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
						    			
						    			turn();   							
									}
									
									if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
										
						    			hastePartTwo();    							
									}
	
									if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
						    			
										hastePartTwo();   							
									}
					  	  	  	}
				  	  	  	}, 2000); 		
									
									//dialog.dismiss();
						    	//}
					    	//});								    	
					    	//alert.show();						
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
						    
							alert.setCancelable(false);
							
							alert.setTitle("The computer has been slain.");
				  	    	/*
				  	    	alert.setMessage("something");
				  	    	*/	    	
					    	
					    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int whichButton) {
						    		
						    		//hideNavigation();
						    		
						    		playerDeadYet[playerNumberAttacked] = "yes";
						    		
						    		gameOverCheck();
						    		
						    		dialog.dismiss();
						    	}
					    	});								    	
					    	alert.show();																			
						}
						
						else {									
	
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
								
				    			gameEngineHumanFirst2();    							
							}
	
							if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
				    			
				    			turn();   							
							}
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
								
				    			hastePartTwo();    							
							}
	
							if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
				    			
								hastePartTwo();   							
							}										
						}				  	  	  			
		  	  	  	}
	  	  	  	}, 2000);
  	  	    }
		});	
	}
	
	public void criticalHit() { // WAS int
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);  			  	  	  			
	  			
	  			
	  			//if (numberOfPlayers == 1) {	
				//}
				
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
	  			
		  		// Use a blank drawable to hide the imageview animation:
		  		// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
		  		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
		  		img.setBackgroundResource(R.drawable.twentytwentyblank);
		  		img.setImageResource(R.drawable.twentytwentyblank);
	  			
		  		
	  			criticalHitGraphic();		
	  	  	  	
	  	  	  			
  	  	  		final Handler h2 = new Handler();
	  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		//final TextView criticalHitGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//criticalHitGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
	  	  	  			// IF DODGE BLOW:
	  	  			
	  					if (dodgeBlowSpell[playerNumberAttacked] > 0) {		  						
	  							
							if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] >= 12) {
								
								centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
						  		centerscrolltext.append("\n" + "> The computer player does not dodge.");
						  		
						  		
	  						  	final Handler h = new Handler();
	  				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  				  	  	  			
	  				  	  	  		@Override
	  					  	  	  	public void run() {
	  				  	  	  			
	  				  	  	  			if (mightyBlowSpell[0] > 0 && ishasteused.equals("no") && isblessrolled.equals("no")) {
									
											AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
								  			
											alert.setCancelable(false);
											
								  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use Mighty Blow?");
								  	    	/*
								  	    	alert.setMessage("something");
								  	    	*/	  	    	
								  	    	
								  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
								  		    	public void onClick(DialogInterface dialog, int whichButton) {								  		    		
								  		    		
								  		    		//hideNavigation();
								  		    		
								  		    		mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
								  		    		
								  		    		skillsCheck();
								  		    		
								  		    		criticalHitMightyBlowPartOne();
								  					//return;
								  		    		
								  		    		dialog.dismiss();
								  		    	}
								  	    	});
								  	    	
								  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
								          	  public void onClick(DialogInterface dialog, int whichButton) {
								          		
								          		//hideNavigation();
								          		  
								          		criticalHitPartOne();
												//return;
								          		
								          		dialog.dismiss();
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
	  				  	  	  			
	  				  	  	  			else {
			  	  	  				
	  				  	  	  				criticalHitPartOne();
	  				  	  	  				//return;
	  				  	  	  			}
	  					  	  	  	}
	  				  	  	  	}, 2000);			  						  		
							}
							
							else {
								
								centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
						  		centerscrolltext.append("\n" + "> The computer uses dodge.");					
			
								dodgeBlowSpell[playerNumberAttacked] = dodgeBlowSpell[playerNumberAttacked] - 1;
								
								skillsCheck();
								
								// Use a blank drawable to hide the imageview animation:
				  		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				  		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				  		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
				  		    	img.setImageResource(R.drawable.twentytwentyblank);
								
								dodgeGraphic();								
								
								final Handler h4 = new Handler();
					  	  	  	h4.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {  	  		
					  	  	  			
						  	  	  		//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
					  	  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
					  	  	  			
					  	  	  			stopGraphics();
					  	  	  			
						  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
											
							    			gameEngineHumanFirst2();    							
										}
		
										if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
							    			
							    			turn();   							
										}
										
										if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
											
							    			hastePartTwo();    							
										}
		
										if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
							    			
											hastePartTwo();   							
										}					  	  	  	
						  	  	  	}
					  	  	  	}, 6000);
								// NEED THIS?:
								//return;
							}						
	  					}		  	  	  			
	  	  	  			
	  					// IF NO DODGE BLOW:
	  					
	  	  	  			else {
	  	  	  				
		  	  	  			final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			if (mightyBlowSpell[0] > 0 && ishasteused.equals("no") && isblessrolled.equals("no")) {
								
										AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
							  			
										alert.setCancelable(false);
										
							  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use Mighty Blow?");
							  	    	/*
							  	    	alert.setMessage("something");
							  	    	*/	  	    	
							  	    	
							  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							  		    	public void onClick(DialogInterface dialog, int whichButton) {							  		    		
							  		    		
							  		    		//hideNavigation();
							  		    		
							  		    		mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
							  		    		
							  		    		skillsCheck();
							  		    		
							  		    		criticalHitMightyBlowPartOne();
							  					//return;
							  		    		
							  		    		dialog.dismiss();
							  		    	}
							  	    	});
							  	    	
							  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
							          	  public void onClick(DialogInterface dialog, int whichButton) {
							          		
							          		//hideNavigation();
							          		  
							          		criticalHitPartOne();
											//return;
							          		
							          		dialog.dismiss();
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
				  	  	  			
				  	  	  			else {
		  	  	  				
				  	  	  				criticalHitPartOne();
				  	  	  				//return;
				  	  	  			}
					  	  	  	}
				  	  	  	}, 2000);			  	  	  				
	  	  	  			}
		  	  	  	}
	  	  	  	}, 6000);		  	  	  	  	  			  	  	  			
  	  	    }
		});
		
		// NEE THIS?
		//return playerNumberAttacked;
	}
	
	public void criticalHitPartOne() {
		
		// Use a blank drawable to hide the imageview animation:
		// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
  		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
		img.setBackgroundResource(R.drawable.twentytwentyblank);
		img.setImageResource(R.drawable.twentytwentyblank);
		
		isblessrolled = "no";
		
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
				  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you roll twice for critical hit damage.");
				  		
				  		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
	  	  	  			img.bringToFront();
				  		
		  	  	  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {					  	  	  			
			  	  	  			
			  	  	  			sixSidedRollFromLeft();				  	  	  		
					  	  	  			
				  	  	  		final Handler h3 = new Handler();
					  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {				
					  	  	  			
					  	  	  			sixSidedWobbleStart();
					  	  	  			
					  	  	  			isSixSidedReadyToBeRolled = "yes";
					  	  	  			
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Make your first roll...");
										
										ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
						  	  	    	titleBlankButton.bringToFront();
								
										/*
										 * 
										 * ROLL 6-SIDED DIE
										 * 
										 */
										
										ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] = (int)(Math.random()*6)+1;
								        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
								        //same as: (int) Math.ceil(Math.random()*6); ?										
										
										iscriticalhitfirstrollrolled = "yes";											
					  	  	  		}
					  	  	  	}, 750);					  	  	  		
			  	  	  		}
			  	  	  	}, 2000);
	  	  	  		}
	  	  	  	}, 2000);
	  	  	  	
	  	  	  	/*
				if (numberOfPlayers > 1) {
				
				}
				*/				
	  	    }  	  	    
		});
		// NEED THIS?:
		//return;
	}
	
	public void criticalHitPartTwo() {
		
		isSixSidedReadyToBeRolled = "no";
		
		iscriticalhitfirstrollrolled = "no";
		
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
				centerscrolltext.append("\n" + "> You roll " + ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + " for damage.");  			
	  			
	  				  			
	  			final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {					  	  	  			
	  	  	  			
	  	  	  			sixSidedRollFromLeft();				  	  	  		
			  	  	  			
		  	  	  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {				
			  	  	  			
			  	  	  			sixSidedWobbleStart();
			  	  	  			
			  	  	  			isSixSidedReadyToBeRolled = "yes";
			  	  	  			
								centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> Make your second roll...");
								
								ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				  	  	    	titleBlankButton.bringToFront();
						
								/*
								 * 
								 * ROLL 6-SIDED DIE
								 * 
								 */
								
								ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] = (int)(Math.random()*6)+1;
						        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
						        //same as: (int) Math.ceil(Math.random()*6); ?										
								
								iscriticalhitsecondrollrolled = "yes";								
			  	  	  		}
			  	  	  	}, 750);					  	  	  		
	  	  	  		}
	  	  	  	}, 2000);
  	  	    }
  	  	});
		// NEED THIS?:
		//return;
	}

	public void criticalHitDamageResults() {
		
		isSixSidedReadyToBeRolled = "no";
		
		// Here to prevent pre-mature (BUT STILL SEE ) roll
		final ImageView sixSidedBlank = (ImageView) findViewById(R.id.sixsidedanimation);		
		sixSidedBlank.setEnabled(false);
		
		iscriticalhitsecondrollrolled = "no";
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
	  			
	  			centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> You roll " + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] + " for damage.");
	  			
	  			
	  			final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		if (canHasDisarmed[0].equals("no")) {							
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + " for damage.");
							
		
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]);
							
							
							TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
							playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
							playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
							//playerNumberAttackedHitPointsTextView.bringToFront();
							
							
							criticalHitDamageResultsHandler();
						}
							
						if (canHasDisarmed[0].equals("yes")) {							
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + " for damage, -2 damage for punch = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) + " damage.");
							
							
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2);
							
							
							TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
							playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
							playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
							//playerNumberAttackedHitPointsTextView.bringToFront();
							
							
							criticalHitDamageResultsHandler();
						}					
			  	  	  	// NEED THIS?:
						//return;		  	  	  		
	  	  	  		}
	  	  	  	}, 2000);								 
  	  	    }
		});	
	}
	
	public void criticalHitDamageResultsHandler() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
  	  	    	
  	    		final Handler h2 = new Handler();
		  	  	h2.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {		  	  		
		  	  			
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] == 0) {
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer has been knocked unconscious!");
							
							/*
							AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
						    
							alert.setCancelable(false);
							
							alert.setTitle("The computer has been knocked unconscious.");
				  	    	
				  	    	//alert.setMessage("something");
				  	    		    	
					    	
					    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int whichButton) {
						    */		
						    		//hideNavigation();
						    
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
					  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
										
						    			gameEngineHumanFirst2();    							
									}
	
									if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
						    			
						    			turn();   							
									}
									
									if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
										
						    			hastePartTwo();    							
									}
	
									if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
						    			
										hastePartTwo();   							
									}
					  	  	  	}
				  	  	  	}, 2000);			    		
									
									//dialog.dismiss();
						    	//}
					    	//});								    	
					    	//alert.show();						
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
						    
							alert.setCancelable(false);
							
							alert.setTitle("The computer has been slain.");
				  	    	/*
				  	    	alert.setMessage("something");
				  	    	*/	    	
					    	
					    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int whichButton) {
						    		
						    		//hideNavigation();
						    		
						    		playerDeadYet[playerNumberAttacked] = "yes";
						    		
						    		gameOverCheck();
						    		
						    		dialog.dismiss();
						    	}
					    	});								    	
					    	alert.show();																			
						}
						
						else {									
		
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
								
				    			gameEngineHumanFirst2();    							
							}
		
							if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
				    			
				    			turn();   							
							}
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
								
				    			hastePartTwo();    							
							}
		
							if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
				    			
								hastePartTwo();   							
							}										
						}				  	  	  			
		  	  	  	}
		  	  	}, 2000);
  	  	    }
		});	
	}
	
	public void criticalHitMightyBlowPartOne() {
		
		// Use a blank drawable to hide the imageview animation:
		// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
  		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
		img.setBackgroundResource(R.drawable.twentytwentyblank);
		img.setImageResource(R.drawable.twentytwentyblank);
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			// NO DODGING MIGHTY BLOW
	  			
  	  	    	
	  			mightyBlowGraphic();  	  	  	  	  								
							
						
				final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		//final TextView mightyBlowGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//mightyBlowGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
				  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you roll twice for critical hit damage.");
				  		
				  		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
	  	  	  			img.bringToFront();
				  		
		  	  	  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {		  	  	  			
			  	  	  			
			  	  	  			sixSidedRollFromLeft();				  	  	  		
					  	  	  			
				  	  	  		final Handler h3 = new Handler();
					  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {				
					  	  	  			
					  	  	  			sixSidedWobbleStart();
					  	  	  			
					  	  	  			isSixSidedReadyToBeRolled = "yes";
					  	  	  			
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Make your first roll...");
										
										ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
						  	  	    	titleBlankButton.bringToFront();
								
										/*
										 * 
										 * ROLL 6-SIDED DIE
										 * 
										 */
										
										ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] = (int)(Math.random()*6)+1;
								        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
								        //same as: (int) Math.ceil(Math.random()*6); ?										
										
										iscriticalhitmightyblowfirstrollrolled = "yes";										
					  	  	  		}
					  	  	  	}, 750);					  	  	  		
			  	  	  		}
			  	  	  	}, 2000);
	  	  	  		}
	  	  	  	}, 6000);		  	  	  					  			
	  	    }  	  	    
		});
		// NEED THIS?:
		//return;
	}
	
	public void criticalHitMightyBlowPartTwo() {
		
		isSixSidedReadyToBeRolled = "no";
		
		iscriticalhitmightyblowfirstrollrolled = "no";
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> You roll " + ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + " for damage.");
	  	  	  			
		  	  	  		final Handler h1 = new Handler();
			  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {					  	  	  			
			  	  	  			
			  	  	  			sixSidedRollFromLeft();				  	  	  		
					  	  	  			
				  	  	  		final Handler h2 = new Handler();
					  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {				
					  	  	  			
					  	  	  			sixSidedWobbleStart();
					  	  	  			
					  	  	  			isSixSidedReadyToBeRolled = "yes";
					  	  	  			
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Make your second roll...");
										
										ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
						  	  	    	titleBlankButton.bringToFront();
								
										/*
										 * 
										 * ROLL 6-SIDED DIE
										 * 
										 */
										
										ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] = (int)(Math.random()*6)+1;
								        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
								        //same as: (int) Math.ceil(Math.random()*6); ?										
										
										iscriticalhitmightyblowsecondrollrolled = "yes";								
					  	  	  		}
					  	  	  	}, 750);					  	  	  		
			  	  	  		}
			  	  	  	}, 2000);
		  	  	  	}
	  	  	  	}, 2000);		  			
  	  	    }
  	  	});
		// NEED THIS?:
		//return;	
	}
	
	public void criticalHitMightyBlowDamageResults() {
		
		isSixSidedReadyToBeRolled = "no";
		
		// Here to prevent pre-mature (BUT STILL SEE ) roll
		final ImageView sixSidedBlank = (ImageView) findViewById(R.id.sixsidedanimation);		
		sixSidedBlank.setEnabled(false);
		
		iscriticalhitmightyblowsecondrollrolled = "no";
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
	  			
	  			final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> You roll " + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] + " for damage.");
	  	  	  			
		  	  	  		final Handler h1 = new Handler();
			  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (canHasDisarmed[0].equals("no")) {							
									
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + " for damage.");
									
									final Handler h2 = new Handler();
						  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) * 2) + ".");
											
											
											ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) * 2);
											
											
											TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
											playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
											playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
											//playerNumberAttackedHitPointsTextView.bringToFront();
							    			
											
											criticalHitMightyBlowDamageResultsHandler();
						  	  	  			
							  	  	  	}
						  	  	  	}, 2000);						
								}
									
								if (canHasDisarmed[0].equals("yes")) {								
									
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + " for damage, -2 damage for punch = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) + " damage.");
									
									final Handler h3 = new Handler();
						  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) * 2) + ".");
										
										
										ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) * 2);
										
										
										TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
										playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
										playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
										//playerNumberAttackedHitPointsTextView.bringToFront();
										
										
										criticalHitMightyBlowDamageResultsHandler();
						  	  	  			
							  	  	  	}
						  	  	  	}, 2000);							
								}								
					  	  	  	//NEED THIS:?
								//return;		  	  	  		
			  	  	  		}
			  	  	  	}, 2000);
		  	  	  	}
	  	  	  	}, 2000);  											 
  	  	    }
		});		
	}
	
	public void criticalHitMightyBlowDamageResultsHandler() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
	  			
  	    		final Handler h4 = new Handler();
		  	  	h4.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {
			
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] == 0) {
							
							centerscrolltext.setVisibility(View.VISIBLE);													
					  		centerscrolltext.startAnimation(animAlphaText);			  		
							centerscrolltext.append("\n" + "> The computer has been knocked unconscious!");
							
							/*
							AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
						    
							alert.setCancelable(false);
							
							alert.setTitle("The computer has been knocked unconscious.");
				  	    	
				  	    	//alert.setMessage("something");
				  	    		    	
					    	
					    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int whichButton) {
						    */		
						    		//hideNavigation();
						    
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
					  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
										
						    			gameEngineHumanFirst2();    							
									}
	
									if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
						    			
						    			turn();   							
									}
									
									if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
										
						    			hastePartTwo();    							
									}
	
									if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
						    			
										hastePartTwo();   							
									}
					  	  	  	}
				  	  	  	}, 2000);			    		
									
									//dialog.dismiss();
						    	//}
					    	//});								    	
					    	//alert.show();						
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
						    
							alert.setCancelable(false);
							
							alert.setTitle("The computer has been slain.");
				  	    	/*
				  	    	alert.setMessage("something");
				  	    	*/	    	
					    	
					    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int whichButton) {
						    		
						    		//hideNavigation();
						    		
						    		playerDeadYet[playerNumberAttacked] = "yes";
						    		
						    		gameOverCheck();
						    		
						    		dialog.dismiss();
						    	}
					    	});								    	
					    	alert.show();																			
						}
						
						else {									
		
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
								
				    			gameEngineHumanFirst2();    							
							}
		
							if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
				    			
				    			turn();   							
							}
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
								
				    			hastePartTwo();    							
							}
		
							if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
				    			
								hastePartTwo();   							
							}										
						}				  	  	  			
		  	  	  	}
		  	  	}, 2000);
  	  	    }
		});		
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
  	  	    	
	  			
		  		// Use a blank drawable to hide the imageview animation:
		  		// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
		  		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
		  		img.setBackgroundResource(R.drawable.twentytwentyblank);
		  		img.setImageResource(R.drawable.twentytwentyblank);
	  			
		  		
	  			criticalMissGraphic();  				  					
	  					
	  	  	  			
  	  	  		final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {			  	  	  			

		  	  	  		//final TextView criticalMissGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//criticalMissGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
	  			  		centerscrolltext.startAnimation(animAlphaText);
	  					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you must roll to see if you hit yourself...");
	  					
	  					criticalMissAttack();
	  					
	  					// NEED THIS?:
	  					//return; // playerNumberAttacked?????????????????????
		  	  	  	}
	  	  	  	}, 6000);	  	  	  				
  	  	    }
		});
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
	  			
	  			
	  			final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
	  	  	  			// ROLLFROMLEFT (20-SIDED)
	  	  	  			twentySidedRollFromLeft();				  	  	  		
			  	  	  			
		  	  	  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {				
			  	  	  			
			  	  	  			twentySidedWobbleStart();
			  	  	  			
			  	  	  			isTwentySidedReadyToBeRolled = "yes";
			  	  	  			
								centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> Please slide the die...");
								
								ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				  	  	    	titleBlankButton.bringToFront();
						
								/*
								 * 
								 * 
								 * SLIDE 20-SIDED DIE
								 * 
								 * 
								 */
						
								ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
								//int attackResult = (int) ((Math.random() * 20) + 1);										
								
								iscriticalmissrolled = "yes";								
			  	  	  		}
			  	  	  	}, 750);					  	  	  		
	  	  	  		}
	  	  	  	}, 3000);			
  	  	    }
		});
	}
	
	public void criticalMissResults() {
		
		isTwentySidedReadyToBeRolled = "no";
		
		// Here to prevent pre-mature (BUT STILL SEE ) roll
		final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);		
		twentySidedBlank.setEnabled(false);
		
		iscriticalmissrolled = "no";
		
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
				  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ".");
				  		
				  		
				  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (ArrayOfAttackResult.attackResult[0] >= 17) {
									
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> You hit yourself.");							  		
							  		
							  		
							  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", roll for damage to yourself...");						  	  	  			
						  	  	  			
						  	  	  			criticalMissDamage();					  	  	  			
							  	  	  	}
						  	  	  	}, 2000);																	
								}
								
								else {
									
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you did not hit yourself... now you must roll to see if you lose your weapon...");
																  		
							  		criticalMissLoseWeapon();						  		
								}		  	  	  			
				  	  	  	}
			  	  	  	}, 2000);	  	  	  			
		  	  	  	}
	  	  	  	}, 2000);				  			
  	  	    }
		});	
	}
	
	public void criticalMissDamage() {
		
		// Use a blank drawable to hide the imageview animation:
		// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
		img.setBackgroundResource(R.drawable.twentytwentyblank);
		img.setImageResource(R.drawable.twentytwentyblank);
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);  			
	  			
				
  	  	  		final Handler h2 = new Handler();
	  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {					  	  	  			
	  	  	  			
		  	  	  		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
	  	  	  			img.bringToFront();
	  	  	  			
	  	  	  			sixSidedRollFromLeft();				  	  	  		
			  	  	  			
		  	  	  		final Handler h3 = new Handler();
			  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {				
			  	  	  			
			  	  	  			sixSidedWobbleStart();
			  	  	  			
			  	  	  			isSixSidedReadyToBeRolled = "yes";
			  	  	  			
								centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> Please slide the die...");
								
								ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				  	  	    	titleBlankButton.bringToFront();
						
								/*
								 * 
								 * ROLL 6-SIDED DIE
								 * 
								 */
						
								SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
								SharedPreferences.Editor editor = preferences.edit();		
								editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
								editor.apply();
								
						        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
						        //same as: (int) Math.ceil(Math.random()*6); ?
								
								iscriticalmissdamagerolled = "yes";
								preventattackdamagediefromleaking = "off";
			  	  	  		}
			  	  	  	}, 750);					  	  	  		
	  	  	  		}
	  	  	  	}, 2000);  	  	  									
  	  	    }
		});		
	}
	
	public void criticalMissDamageResults() {// WAS int[]
		
		isSixSidedReadyToBeRolled = "no";
		
		// Here to prevent pre-mature (BUT STILL SEE ) roll
		final ImageView sixSidedBlank = (ImageView) findViewById(R.id.sixsidedanimation);		
		sixSidedBlank.setEnabled(false);
		
		iscriticalmissdamagerolled = "no";
		preventattackdamagediefromleaking = "on";
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	  			
	  			
	  			final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
						//SharedPreferences.Editor editor = preferences.edit();
						int attackDamage = preferences.getInt("attackDamage", 0);
			  			
						centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> You roll " + attackDamage + " for damage.");
						
	
						ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] - attackDamage;
						
						
						TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
		    			playerHitPointsTextView.setTypeface(typeFace);
		    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
		    			//playerHitPointsTextView.bringToFront();
	
	
						final Handler h = new Handler();
		  	  	  		h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {	  	  	  		
						
								if (ArrayOfHitPoints.hitpoints[0] == 0) {
									
									if (numberOfPlayers == 1) {								
										
										centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);			  		
										centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have been knocked unconscious!");
										
										/*
										AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
									    
										alert.setCancelable(false);
										
										alert.setTitle("You have been knocked unconscious.");
							  	    	
							  	    	//alert.setMessage("something");
							  	    		    	
								    	
								    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									    	public void onClick(DialogInterface dialog, int whichButton) {
									    */		
									    		//hideNavigation();
									    
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
								  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
													
									    			gameEngineHumanFirst2();    							
												}
			
												if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
									    			
									    			turn();   							
												}
												
												if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
													
									    			hastePartTwo();    							
												}
			
												if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
									    			
													hastePartTwo();   							
												}
								  	  	  	}
							  	  	  	}, 2000);				    		
												
												//dialog.dismiss();
									    	//}
								    	//});						    	
								    	//alert.show();				
									}
									/*
									if (numberOfPlayers > 1) {
										System.out.println("Player " + (playerNumberAttacked + 1) + ", you have been knocked unconscious!");
									}
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
									
									
									if (numberOfPlayers == 1) {
										
										AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
									    
										alert.setCancelable(false);
										
										alert.setTitle("You have been slain.");
							  	    	/*
							  	    	alert.setMessage("something");
							  	    	*/	    	
								    	
								    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									    	public void onClick(DialogInterface dialog, int whichButton) {
									    		
									    		//hideNavigation();
									    		
									    		playerDeadYet[0] = "yes";
									    		
									    		gameOverCheck();
									    		
									    		dialog.dismiss();
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
								}
								
								else {						
	
									if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
										
						    			gameEngineHumanFirst2();    							
									}
	
									if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
						    			
						    			turn();   							
									}
									
									// TAKE THIS OUT?:
									
									if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
										
						    			hastePartTwo();    							
									}
	
									if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
						    			
										hastePartTwo();   							
									}
								}
				  	  	  	}
			  	  	  	}, 2000);
		  	  	  	}
	  	  	  	}, 2000);  						
  	  	    }
		});
		//return ArrayOfHitPoints.hitpoints;
	}
	
	public void criticalMissLoseWeapon() {		
		
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
	  	  	  			
	  	  	  			// ROLLFROMLEFT (20-SIDED)
	  	  	  			twentySidedRollFromLeft();				  	  	  		
			  	  	  			
		  	  	  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {				
			  	  	  			
			  	  	  			twentySidedWobbleStart();
			  	  	  			
			  	  	  			isTwentySidedReadyToBeRolled = "yes";
			  	  	  			
								centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> Please slide the die...");
								
								ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				  	  	    	titleBlankButton.bringToFront();
						
								/*
								 * 
								 * 
								 * SLIDE 20-SIDED DIE
								 * 
								 * 
								 */
						
								ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
								//int attackResult = (int) ((Math.random() * 20) + 1);										
								
								iscriticalmissloseweaponrolled = "yes";								
			  	  	  		}
			  	  	  	}, 750);					  	  	  		
	  	  	  		}
	  	  	  	}, 2000);
  	  	    }
		});
	}
  	
  	public void criticalMissLoseWeaponResults() {
  		
  		isTwentySidedReadyToBeRolled = "no";
  		
  		// Here to prevent pre-mature (BUT STILL SEE ) roll
  		final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);		
  		twentySidedBlank.setEnabled(false);
  		
  		iscriticalmissloseweaponrolled = "no";
		
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
				  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ".");
						
						if (ArrayOfAttackResult.attackResult[0] >= 17) {
							
							canHasDisarmed[0] = "yes";
							
							didHumanCriticalMiss = "yes";
							
							disarmedTurnStart[0] = turn;
							
							final Handler h2 = new Handler();
				  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> You are disarmed.");
							  		
							  		final Handler h3 = new Handler();
						  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {	
						  	  	  			
							  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
												
								    			gameEngineHumanFirst2();    							
											}
			
											if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
								    			
								    			turn();   							
											}
											
											// TAKE THIS OUT?:
											
											if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
												
								    			hastePartTwo();    							
											}
			
											if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
								    			
												hastePartTwo();   							
											}						  	  	  			
							  	  	  	}
						  	  	  	}, 2000);							  						  	  	  			
					  	  	  	}
				  	  	  	}, 2000);															
						}
						
						else {
							
							final Handler h4 = new Handler();
				  	  	  	h4.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> You hold on to your weapon.");
							  		
							  		final Handler h5 = new Handler();
						  	  	  	h5.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
												
								    			gameEngineHumanFirst2();    							
											}
			
											if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
								    			
								    			turn();   							
											}
											
											if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {				
												
								    			hastePartTwo();    							
											}
			
											if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("yes")) {		
								    			
												hastePartTwo();   							
											}						  	  	  			
							  	  	  	}
						  	  	  	}, 2000);							  						  	  	  			
					  	  	  	}
				  	  	  	}, 2000);							
						}
	  	  	  		}	  	  	  	
	  	  	  	}, 2000);
  	  	    }
		});	
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
			  			
						alert.setCancelable(false);
						
			  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your bless spell?");
			  	    	/*
			  	    	alert.setMessage("something");
			  	    	*/	  	    	
			  	    	
			  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			  	    		public void onClick(DialogInterface dialog, int whichButton) {
			  	    			
			  	    			//hideNavigation();
			  		    		
			  		    		blessSpell[0] = blessSpell[0] - 1;
			  		    		
			  		    		skillsCheck();
			  		    		
			  		    		dialog.dismiss();
			  		    		
			  		    		
			  		    		// NEED THIS HANDLER OTHERWISE BLESS GRAPHIC GETS OFF-CENTER MID-WAY THRU.
			  		    		final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			disarmWithBless();
						  	  	  	}
					  	  	  	}, 1000);		  		    					  		    			  		    		
			  		    	}
			  	    	});
			  	    	
			  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			  	    		public void onClick(DialogInterface dialog, int whichButton) {
			  	    			
			  	    			//hideNavigation();			  	    			
			  	    				  	    			
			  	    			disarmNoBless();
			  	    			
			  	    			dialog.dismiss();
			          	  }
			          	});			  	    	
			  	    	alert.show();						
					}
					
					else {
						
						disarmNoBless();
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
	  			
	  			
	  			centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " uses a bless...");
	  			
	  			blessGraphic();    	
		  	  	    	
				
  	  	  		final Handler h2 = new Handler();
	  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {			  	  	  			
	  	  	  			
		  	  	  		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			img.bringToFront();	  	  	  			
	  	  	  			
		  	  	  		//final TextView blessGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
	  	  	  			//blessGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
	  	  	  			// ROLLFROMLEFT (20-SIDED)
	  	  	  			twentySidedRollFromLeft();				  	  	  		
			  	  	  			
		  	  	  		final Handler h3 = new Handler();
			  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			twentySidedWobbleStart();
			  	  	  			
			  	  	  			isTwentySidedReadyToBeRolled = "yes";
			  	  	  			
								centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
						  		centerscrolltext.append("\n" + "> Press slide the die... ");
						  		
						  		ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				  	  	    	titleBlankButton.bringToFront();
						  		
						  		
						  		/*
								 * 
								 * 
								 * SLIDE 20-SIDED DIE
								 * (SEE ABOVE)
								 * 
								 * 
								 */
						  		
						  		ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
								//int attackResult = (int) ((Math.random() * 20) + 1);
						  		
						  		isdisarmwithblessrolled = "yes";
								
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
			  	  	  	}, 750);					  	  	  		
	  	  	  		}
	  	  	  	}, 6000);	  	  	  		
  	  	    }
		});
	}
	
	public void disarmWithBlessResults() {
		
		isTwentySidedReadyToBeRolled = "no";
		
		isdisarmwithblessrolled = "no";
		
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
				  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0]	+ ", +2 for the Bless Spell = "	+ (ArrayOfAttackResult.attackResult[0] + 2));
						
				  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (ArrayOfAttackResult.attackResult[0] >= 15) {
									
					  	  	  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
									  		centerscrolltext.append("\n" + "> Your opponent has been disarmed.");
									  		
									  		final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
									  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {				
														
										    			gameEngineHumanFirst2();    							
													}
	
													if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1])) {		
										    			
										    			turn();   							
													}
									  	  	  	}
								  	  	  	}, 2000);
							  	  	  	}
						  	  	  	}, 2000);						    	
									
									canHasDisarmed[playerNumberAttacked] = "yes";
					
									disarmedTurnStart[playerNumberAttacked] = turn;
					
									// playerWhoDisarmed[playerNumberAttacked] = i;
									
									/* FOR PLAYERS > 1 ??
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> You have disarmed player " + (playerNumberAttacked + 1) + "!");
									
									//for (int x = 0; x < 1000; --x)// To give human player time to read.
									//{
									//}
									
									return;
									*/
								}
					
								if (ArrayOfAttackResult.attackResult[0] <= 14 && ArrayOfAttackResult.attackResult[0] > 0) {
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
									  		centerscrolltext.append("\n" + "> Your attempt to disarm misses.");
									  		
									  		final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
									  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {				
														
										    			gameEngineHumanFirst2();    							
													}
	
													if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1])) {		
										    			
										    			turn();   							
													}
									  	  	  	}
								  	  	  	}, 2000);
							  	  	  	}
						  	  	  	}, 2000);		    	
									
									/*
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> Your attempt to disarm misses!");
									
									//for (int x = 0; x < 1000; --x)// To give human player time to read.
									//{
									//}
									
									return;
									*/
								}				  		
			  	  	  		}
			  	  	  	}, 2000);						
	  	  	  		}
	  	  	  	}, 2000);
	  	  	  	// NEED THIS?:
	  	  	  	//return; 
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
	  			
  	  	    	
  	  	    	/*
				 * 
				 * ???????????????????
				 * 
				 * disarmGraphic(); ???????????
				 * 
				 */ 
	  			
		
  	  	  		final Handler h2 = new Handler();
	  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
	  	  	  			ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			img.bringToFront();
	  	  	  			
	  	  	  			// ROLLFROMLEFT (20-SIDED)
	  	  	  			twentySidedRollFromLeft();				  	  	  		
			  	  	  			
		  	  	  		final Handler h3 = new Handler();
			  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  		
			  	  	  		twentySidedWobbleStart();
			  	  	  		
			  	  	  		isTwentySidedReadyToBeRolled = "yes";
			  	  	  		
			  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> Press slide the die... ");
					  		
					  		ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
			  	  	    	titleBlankButton.bringToFront();
					  		
					  		
					  		/*
							 * 
							 * 
							 * SLIDE 20-SIDED DIE
							 * 
							 * 
							 */									  		
							
							ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
							
							isdisarmnoblessrolled = "yes";
							
			  	  	  		}
			  	  	  	}, 750);				  	  	  		
	  	  	  		}
	  	  	  	}, 2000);	  	  	  						
  	  	    }
		});		
	}
	
	public void disarmNoBlessResults() {
		
		isTwentySidedReadyToBeRolled = "no";
		
		isdisarmnoblessrolled = "no";
		
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
				  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ".");
						
				  		
				  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (ArrayOfAttackResult.attackResult[0] >= 17) {
									
					  	  	  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
									  		centerscrolltext.append("\n" + "> Your opponent has been disarmed.");
									  		
									  		final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
									  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {				
														
										    			gameEngineHumanFirst2();    							
													}
	
													if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1])) {		
										    			
										    			turn();   							
													}
									  	  	  	}
								  	  	  	}, 2000);
							  	  	  	}
						  	  	  	}, 2000);							    	
									
									canHasDisarmed[playerNumberAttacked] = "yes";
					
									disarmedTurnStart[playerNumberAttacked] = turn;
									
									/*
									// playerWhoDisarmed[playerNumberAttacked] = i;
									
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> You have disarmed player " + (playerNumberAttacked + 1) + "!");
									
									return;
									*/
								}
					
								if (ArrayOfAttackResult.attackResult[0] <= 16 && ArrayOfAttackResult.attackResult[0] > 1) {
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
									  		centerscrolltext.append("\n" + "> Your attempt to disarm misses.");
									  		
									  		final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
									  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {				
														
										    			gameEngineHumanFirst2();    							
													}
	
													if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1])) {		
										    			
										    			turn();   							
													}
									  	  	  	}
								  	  	  	}, 2000);
							  	  	  	}
						  	  	  	}, 2000);							
									
									/*
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> Your attempt to disarm misses!");
									
									return;
									*/
								}
								
								if (ArrayOfAttackResult.attackResult[0] <= 1) {
									
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> You have rolled a critical miss...");
									
							  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			criticalMiss();
							  	  	  	}
						  	  	  	}, 2000);																    	
									
									/*
									criticalMiss();
									
									return;
									*/
								}			  	  	  			
			  	  	  		}
			  	  	  	}, 2000);						
	  	  	  		}
	  	  	  	}, 2000);
	  	  	  	// NEED THIS?:
	  	  	  	//return; 
  	  	    }
		});
	}
	
	public void haste() { // Can't use 2 spells in one turn & ONLY 2 ATTACKS OR RE-ARM ONESELF.	
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			if (hasteSpell[0] > 0) {
	  				
	  				if (numberOfPlayers == 1) {
	  					
	  					hasteGraphic();
	  					
	  					final Handler h1 = new Handler();
	  		  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  		  	  	  			
	  		  	  	  		@Override
	  			  	  	  	public void run() {
	  		  	  	  			
	  		  	  	  			hasteSpell[0] = hasteSpell[0] - 1;
	  		  	  	  			
	  		  	  	  			skillsCheck();
	  		  	  	  			
	  		  	  	  			stopGraphics();
	  		  	  	  			
	  		  	  	  			
	  		  	  	  			if (canHasDisarmed[0].equals("no")) {	  		  	  	  				
	  		  	  	  				
	  		  	  	  				ishasteused = "yes";					  	  	  			

					  	  	  		final TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
					  	  	  		hasteGraphic.setVisibility(View.INVISIBLE);
				  	  	  			
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> TWO attacks...");
									
									final Handler h2 = new Handler();
						  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> FIRST attack...");
											
											final Handler h3 = new Handler();
								  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {  	  		
					
									  	  	  		attack();					  	  	  	
									  	  	  	}
								  	  	  	}, 2000);
							  	  	  	}
						  	  	  	}, 2000);						  	  	  	
	  		  	  	  			}
	  		  	  	  			
			  		  	  	  	if (canHasDisarmed[0].equals("yes")) {
			  		  	  	  		
			  		  	  	  		canHasDisarmed[0] = "no";	  	  	  								  	  	  			

					  	  	  		final TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
					  	  	  		hasteGraphic.setVisibility(View.INVISIBLE);
				  	  	  			
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> You are no longer disarmed.");
									
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
								}	  		  	  	  			
	  		  	  	  		}
	  		  	  	  	}, 6000);
	  				}
	  			}	  			
	  			
	  			else {
  	  	  			
	  				AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have already used your Haste spells.");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		
				    		//hideNavigation();
				    		
				    		runActionsOnUi();
				    		
				    		dialog.dismiss();
				    	}
			    	});								    	
			    	alert.show();	  				
	  				
	  				/* THIS DIDN'T WORK:
					centerscrolltext.setVisibility(View.VISIBLE);
			  		centerscrolltext.startAnimation(animAlphaText);
			  		centerscrolltext.append("\n" + "> You have already used your Haste spells!");
					
					final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
		  	  	  			//
							// 
							// THIS LETS HUMAN PLAYER CHOOSE WHAT THEY WANT TO DO (ACTION/ATTACK)
							// 
							// Bring Action To Front?
							// 
							// action(i, turn, gameOn);
							// 
							//			  		
					  		
					  		if (isInvokingService.equals("true")){
								//NEED THIS?
								SystemClock.sleep(1000);	        		
									
								runActionsOnUi();
					  		}										  	  	  							  	  	  	
			  	  	  	}
		  	  	  	}, 2000);
		  	  	  	*/	  	  	  	
				}	  	  	    				 
  	  	    }
		});			
	}
	
	public void hastePartTwo() {
		
		if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] < 0) {
			
			endGame();
		}
		
		else if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] == 0 && cureSpell[1] < 1) {		
			
			ishasteused = "no";
			issecondroundofhasteused = "yes";
			
			if (isInvokingService.equals("true")){
				//NEED THIS?
				SystemClock.sleep(1000);
				
				endGameAfterFirstHaste = "yes";
				
				endGame();
			}
			
			
			/*
			if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {				
				
    			gameEngineHumanFirst2();    							
			}

			if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) && ishasteused.equals("no")) {		
    			
    			turn();   							
			}
			*/
		}
		
		else {
			
			ishasteused = "no";
			issecondroundofhasteused = "yes";
			
			final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
			
			runOnUiThread(new Runnable() {
	  	  	    @Override
	  	  	    public void run() {
	  	  	    	
		  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		  			
		  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		  			centerscrolltext.setTypeface(typeFace);	
		  			
		  			
		  			TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
		  			computerHitPointsTextView.setTypeface(typeFace);
		  			computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[1]));	  			
		  			
		  			
		  			
		  			
		  			// SO IF PLAYER KILLS/KNOCKS OUT COMP ON 1ST RD OF HASTE:
		  			//endGame();
		  			
		  			
		  			
		  			
		  			// THIS IS WRONG - CAN GET 2ND ATTACK, YOU'RE JUST DISARMED:
					//if (canHasDisarmed[i] == "yes")// so if you critically miss & drop weapon you don't get 2nd attack.
					//{
					//	return;
					//}	  			
		  				
	  				/*
					System.out.println("     /\\____________");
					System.out.println("/|---||_2nd Attack_\\");
					System.out.println("\\|---||____________/");
					System.out.println("     \\/           ");
					System.out.println();
					*/
					
					final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
				  	  	  	// Use a blank drawable to hide the imageview animation:
				  	  	  	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				  	  	  	ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				  	  	  	img1.setBackgroundResource(R.drawable.twentytwentyblank);
				  	  	  	img1.setImageResource(R.drawable.twentytwentyblank);
			
				  	  	  	// Use a blank drawable to hide the imageview animation:
				  	  	  	ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
				  	  	  	img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
				  	  	  	img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
		  	  	  			
		  	  	  			
			  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> SECOND attack...");			  	  	  							  	  	  	
			  	  	  	}
		  	  	  	}, 2000);
	  				
		  	  	  	// CAN'T USE CURE OR ANOTHER HASTE WITH A HASTE:
	  				//punch(); // SAME AS ATTACK
		  	  	  	
	  				attack();
		  	  	  	
	  				// NEED THIS?:
	  				//return;  											 
	  	  	    }
			});
			// NEED THIS?:
			//return;			
		}			
	}
	
	public void bless() { // WAS int
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				if (blessSpell[0] > 0) {
					
					if (numberOfPlayers == 1) {
						
						isblessrolled = "yes";
						
						blessSpell[0] = blessSpell[0] - 1;
						
						skillsCheck();
						
						blessGraphic();	  			 	  	  							  	  	  		
						  	  	  		
						  	  	  		
			  	  	  	final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		//final TextView blessGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
			  	  	  			//blessGraphic.setVisibility(View.INVISIBLE);
			  	  	  			
			  	  	  			stopGraphics();
			  	  	  			
			  	  	  			ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
			  	  	  			img.bringToFront();
			  	  	  			
			  	  	  			// ROLLFROMLEFT (20-SIDED)
			  	  	  			twentySidedRollFromLeft();				  	  	  		
					  	  	  			
				  	  	  		final Handler h3 = new Handler();
					  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {				
					  	  	  			
					  	  	  			twentySidedWobbleStart();
					  	  	  			
					  	  	  			isTwentySidedReadyToBeRolled = "yes";
					  	  	  			
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Please slide the die...");
										
										ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
						  	  	    	titleBlankButton.bringToFront();
								
										/*
										 * 
										 * 
										 * SLIDE 20-SIDED DIE
										 * 
										 * 
										 */
								
										ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
										//int attackResult = (int) ((Math.random() * 20) + 1);										
										
										isblessrolled = "yes";
										
					  	  	  		}
					  	  	  	}, 750);					  	  	  		
			  	  	  		}			  	  	  		
			  	  	  	}, 6000);// CHANGED FROM 3000 TO 6000 BECAUSE DIE WAS COMING IN BEFORE GRAPHIC WAS FINISHED.	  	  	  		
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
					
				} 
				
				else {
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have already used your Bless spell.");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		
				    		//hideNavigation();
				    		
				    		runActionsOnUi();
				    		
				    		dialog.dismiss();
				    	}
			    	});								    	
			    	alert.show();					
					
					/* THIS DIDN'T WORK:
					centerscrolltext.setVisibility(View.VISIBLE);
			  		centerscrolltext.startAnimation(animAlphaText);
			  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have already used your bless spell!");								  		
			  		
			  		//
					// 
					// THIS LETS HUMAN PLAYER CHOOSE WHAT THEY WANT TO DO (ACTION/ATTACK)
					// 
					// Bring Action To Front?
					// 
					// action(i, turn, gameOn);
					// 
					//
								  		
			  		if (isInvokingService.equals("true")){
						//NEED THIS?
						SystemClock.sleep(1000);	        		
							
						runActionsOnUi();
					}
			  		*/		  		
				}
  	  	    }
		});
		//return playerNumberAttacked;		
	}
	
	public void blessResults() {//WAS int				
		
		isTwentySidedReadyToBeRolled = "no";
		
		// Here to prevent pre-mature (BUT STILL SEE ) roll
		final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);		
		twentySidedBlank.setEnabled(false);
		
		//isblessrolled = "no";
		
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
	  	  	  			
		  	  	  		if (canHasDisarmed[playerNumberAttacked].equals("no")) {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0]	+ ", +2 for Bless Spell = " + (ArrayOfAttackResult.attackResult[0] + 2));							
						}
						
						if (canHasDisarmed[playerNumberAttacked].equals("yes")) {						
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0]	+ ", +4 for Bless Spell and your opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 4));						
						}												
						
						final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {						
				
								if (ArrayOfAttackResult.attackResult[0] == 20) {//WAS >= 20
									
									criticalHit();
									return;
								}
								
								if (canHasDisarmed[playerNumberAttacked].equals("no")) {
									
									if (ArrayOfAttackResult.attackResult[0] >= 12 && ArrayOfAttackResult.attackResult[0] <= 19) {
										
										centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);			  		
										centerscrolltext.append("\n" + "> Your attack hits.");
										
										/* Only one spell can be cast in a round (no spell can be cast in conjunction with another).
										if (mightyBlowSpell[i] > 0) {
											
											//centerscrolltext.setVisibility(View.VISIBLE);													
									  		//centerscrolltext.startAnimation(animAlphaText);		  		
											//centerscrolltext.append("\n" + ArrayOfPlayers.player[i] + ", do you want to use Mighty Blow?");
											
											
											AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
								  			
								  	    	alert.setTitle(ArrayOfPlayers.player[i] + ", do you want to use Mighty Blow?");
								  	    	
								  	    	//alert.setMessage("something");
								  	    		  	    	
								  	    	
								  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
								  		    	public void onClick(DialogInterface dialog, int whichButton) {
								  		    		
								  		    		hideNavigation();
								  		    		
								  		    		mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
								  		    		
								  		    		
								  		    		//
													// 
													// Picture of swords clanging together:
													// 
													// 
													// swordsGraphic();
													// 
													//
								  		    		
								  		    		
								  		    		//System.out
													//.println("     /\\___________          ___________/\\");
								  		    		//System.out
													//.println("/|---||___________\\ MIGHTY /___________||---|\\");
								  		    		//System.out
													//.println("\\|---||___________/  BLOW  \\___________||---|/");
								  		    		//System.out
													//.println("     \\/                                \\/");
								  		    		//for (int x = 0; x < 1; --x) {
													//}
													
							
													
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
											
											
											//String s = input.next();
											//char selection = s.charAt(0);
											//switch (selection) {
											//case 'y':
											//case 'Y':
												//if (numberOfPlayers == 1) {
													//mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
												//}
												//if (numberOfPlayers > 1) {
													//mightyBlowSpell[i] = mightyBlowSpell[i] - 1;
												//}
							
												//System.out.println();
							
												//swordsGraphic();
												//System.out
														//.println("     /\\___________          ___________/\\");
												//System.out
														//.println("/|---||___________\\ MIGHTY /___________||---|\\");
												//System.out
														//.println("\\|---||___________/  BLOW  \\___________||---|/");
												//System.out
														//.println("     \\/                                \\/");
												//for (int x = 0; x < 1; --x) {
												//}
							
												//mightyBlow(i, playerNumberAttacked, gameOn);
												//return playerNumberAttacked;
											//case 'n':
											//case 'N':
												//damage(i, playerNumberAttacked, gameOn);
												//return playerNumberAttacked;
											//default:
												//damage(i, playerNumberAttacked, gameOn);
												
												// IDEALLY WANT THIS TO GO BACK AND ASK AGAIN....RECODE???
												 
												//return playerNumberAttacked;
											//}
											
										}
										else {
											
											damage();
											return;
										}
										*/
										
										damage();
										return;
									}
									
									if (ArrayOfAttackResult.attackResult[0] < 12 && ArrayOfAttackResult.attackResult[0] > 0) {
										
										// don't critically miss when using bless.
										
										isblessrolled = "no";
										
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Your attack misses.");
										
										final Handler h3 = new Handler();
							  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {  	  		
				
								  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {				
													
									    			gameEngineHumanFirst2();    							
												}
	
												if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1])) {		
									    			
									    			turn();   							
												}																	  	  	  	
								  	  	  	}
							  	  	  	}, 2000);
										
										return;
									}
								}
								
								if (canHasDisarmed[playerNumberAttacked].equals("yes")) {
									
									if (ArrayOfAttackResult.attackResult[0] >= 10 && ArrayOfAttackResult.attackResult[0] <= 19) {
										
										centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);			  		
										centerscrolltext.append("\n" + "> Your attack hits.");
										
										/* Only one spell can be cast in a round (no spell can be cast in conjunction with another).
										if (mightyBlowSpell[i] > 0) {
											
											//centerscrolltext.setVisibility(View.VISIBLE);													
									  		//centerscrolltext.startAnimation(animAlphaText);		  		
											//centerscrolltext.append("\n" + ArrayOfPlayers.player[i] + ", do you want to use Mighty Blow?");
											
											
											AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
								  			
								  	    	alert.setTitle(ArrayOfPlayers.player[i] + ", do you want to use Mighty Blow?");
								  	    	
								  	    	//alert.setMessage("something");
								  	    		  	    	
								  	    	
								  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
								  		    	public void onClick(DialogInterface dialog, int whichButton) {
								  		    		
								  		    		hideNavigation();
								  		    		
								  		    		mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
								  		    		
								  		    		
								  		    		//
													// 
								  		    		// Picture of swords clanging together:
								  		    		// 
								  		    		// 
								  		    		// swordsGraphic();
								  		    		// 
								  		    		//
								  		    		
								  		    		
								  		    		//System.out
													//.println("     /\\___________          ___________/\\");
								  		    		//System.out
													//.println("/|---||___________\\ MIGHTY /___________||---|\\");
								  		    		//System.out
													//.println("\\|---||___________/  BLOW  \\___________||---|/");
								  		    		//System.out
													//.println("     \\/                                \\/");
								  		    		//for (int x = 0; x < 1; --x) {
													//}
													
							
													
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
											
											
											//String s = input.next();
											//char selection = s.charAt(0);
											//switch (selection) {
											//case 'y':
											//case 'Y':
												//if (numberOfPlayers == 1) {
													//mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
												//}
												//if (numberOfPlayers > 1) {
													//mightyBlowSpell[i] = mightyBlowSpell[i] - 1;
												//}
							
												//System.out.println();
							
												//swordsGraphic();
												//System.out
														//.println("     /\\___________          ___________/\\");
												//System.out
														//.println("/|---||___________\\ MIGHTY /___________||---|\\");
												//System.out
														//.println("\\|---||___________/  BLOW  \\___________||---|/");
												//System.out
														//.println("     \\/                                \\/");
												//for (int x = 0; x < 1; --x) {
												//}
							
												//mightyBlow(i, playerNumberAttacked, gameOn);
												//return playerNumberAttacked;
											//case 'n':
											//case 'N':
												//damage(i, playerNumberAttacked, gameOn);
												//return playerNumberAttacked;
											//default:
												//damage(i, playerNumberAttacked, gameOn);
												
												// IDEALLY WANT THIS TO GO BACK AND ASK AGAIN....RECODE???
												 
												//return playerNumberAttacked;
											//}
											
										}
										else {
											
											damage();
											return;
										}
										*/
										
										damage();
										return;
									}
									
									if (ArrayOfAttackResult.attackResult[0] < 10 && ArrayOfAttackResult.attackResult[0] > 0) {
										
										// don't critically miss when using bless.
										
										isblessrolled = "no";
										
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Your attack misses.");
										
										final Handler h3 = new Handler();
							  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {  	  		
				
								  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {				
													
									    			gameEngineHumanFirst2();    							
												}
	
												if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1])) {		
									    			
									    			turn();   							
												}																	  	  	  	
								  	  	  	}
							  	  	  	}, 2000);
							  	  	  	// NEED THIS?:
										//return;
									}										
								}							
			  	  	  		}
			  	  	  	}, 2000);
	  	  	  		}
	  	  	  	}, 2000);								 
  	  	    }
		});
		//return playerNumberAttacked;
	}
	
	public void cure() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				if (cureSpell[0] > 0) {
					
					if (numberOfPlayers == 1) {
						
						cureSpell[0] = cureSpell[0] - 1;
						
						skillsCheck();
						
						cureGraphic();	  			
			  	  	  			
						
		  	  	  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		//final TextView cureGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
			  	  	  			//cureGraphic.setVisibility(View.INVISIBLE);
			  	  	  			
			  	  	  			stopGraphics();
			  	  	  			
			  	  	  			ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
			  	  	  			img.bringToFront();
			  	  	  			
			  	  	  			sixSidedRollFromLeft();				  	  	  		
					  	  	  			
				  	  	  		final Handler h3 = new Handler();
					  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {				
					  	  	  			
					  	  	  			sixSidedWobbleStart();
					  	  	  			
					  	  	  			isSixSidedReadyToBeRolled = "yes";
					  	  	  			
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Please slide the die...");
										
										ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
						  	  	    	titleBlankButton.bringToFront();
								
										/*
										 * 
										 * ROLL 6-SIDED DIE
										 * 
										 */
								
										SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
										SharedPreferences.Editor editor = preferences.edit();
										editor.putInt("cureResult", (int) ((Math.random() * 6) + 1));
										editor.apply();

								        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
								        //same as: (int) Math.ceil(Math.random()*6); ?
										
										preventcureresultdiefromleaking = "off";
										
										iscurerolled = "yes";
										
					  	  	  		}
					  	  	  	}, 750);					  	  	  		
			  	  	  		}
			  	  	  	}, 6000);// CHANGED FROM 3000 TO 6000 BECAUSE DIE WAS COMING IN BEFORE GRAPHIC WAS FINISHED.			  	  	  		
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
				} 
				
				else {
					
					AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
				      
					alert.setTitle(ArrayOfPlayers.player[0] + ", you have already used your Cure spell.");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		
				    		//hideNavigation();
				    		
				    		runActionsOnUi();
				    		
				    		dialog.dismiss();
				    	}
			    	});								    	
			    	alert.show();				
					
					/*
					centerscrolltext.setVisibility(View.VISIBLE);
			  		centerscrolltext.startAnimation(animAlphaText);
			  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you have already used your cure spell!");
								  		
			  		
			  		//
					// 
					// THIS LETS HUMAN PLAYER CHOOSE WHAT THEY WANT TO DO (ACTION/ATTACK)
					// 
					// Bring Action To Front?
					// 
					// action(i, turn, gameOn);
					// 
					//
			  		
			  		if (isInvokingService.equals("true")){
						//NEED THIS?
						SystemClock.sleep(1000);	        		
							
						runActionsOnUi();
					}
			  		*/		  		
				}
  	  	    }
		});		 
	}
	
	public void cureResults() {
		
		isSixSidedReadyToBeRolled = "no";
		
		// Here to prevent pre-mature (BUT STILL SEE ) roll
		final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);		
		twentySidedBlank.setEnabled(false);
		
		iscurerolled = "no";
		
		preventcureresultdiefromleaking = "on";
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
	  			
	  			final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
	  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
	  	  	  			//SharedPreferences.Editor editor = preferences.edit();
	  	  	  			int cureResult = preferences.getInt("cureResult", 0);
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> You roll " + cureResult + ".");				
						
						
						ArrayOfHitPoints.hitpoints[0] = ArrayOfHitPoints.hitpoints[0] + cureResult;	  	  	  			
		  	  	  		
						
						TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
		    			playerHitPointsTextView.setTypeface(typeFace);
		    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
		    			//playerHitPointsTextView.bringToFront();
						
						
						final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {  	  	  			
			  	  	  			
				  	  	  		if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {				
									
					    			gameEngineHumanFirst2();    							
								}
	
								if ((ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1])) {		
					    			
					    			turn();   							
								}																						
			  	  	  		}
			  	  	  	}, 2000);
	  	  	  		}
	  	  	  	}, 2000);								 
  	  	    }
		});		
	}
	
	public void runActionsOnUi() {		
		
		isInvokingService = "false";
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	  			
	  			
	  			
	  			// NEW WAY TO DO DIALOG
		  		final String[] items = {"Attack", "Disarm", "Haste", "Cure", "Bless"};
	
		  		// Instead of String[] items, Here you can also use ArrayList for your custom object..
	
		  		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row, R.id.title,items) {
	
		  		    ViewHolder holder;
		  		    Drawable icon;
	
		  		    class ViewHolder {
		  		        ImageView icon;
		  		        TextView title;						
		  		    }
	
		  		    public View getView(int position, View convertView, ViewGroup parent) {
		  		        final LayoutInflater inflater = (LayoutInflater) getApplicationContext()
		  		                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  		        
		  		        //THIS STUFF DID NOT WORK:
		  		        //TextView textView = (TextView) findViewById(R.layout.list_row);
		  		        //textView.setGravity(Gravity.CENTER);
		  		        //textView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 32 /* this is item height */));
		  		        
	
		  		        if (convertView == null) {
		  		            convertView = inflater.inflate(R.layout.list_row, null);
	
		  		            holder = new ViewHolder();
		  		            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
		  		            holder.title = (TextView) convertView.findViewById(R.id.title);		  		           
		  		            
		  		            convertView.setTag(holder);
		  		        } else {
		  		            // view already defined, retrieve view holder
		  		            holder = (ViewHolder) convertView.getTag();
		  		        }       
	
		  		       // Drawable drawable = getResources().getDrawable(R.drawable.list_icon); //this is an image from the drawables folder
	
		  		        holder.title.setText(items[position]);
		  		        //holder.icon.setImageDrawable(drawable);     
		  		        
	
		  		        return convertView;
		  		    }
		  		};		  		
		  		
		  		
		  		// THIS WAY ALLOWS YOU TO STYLE THE DIALOG:
		  		ContextThemeWrapper cw = new ContextThemeWrapper(MainActivity2.this, R.style.DialogWindowTitle_Holo);
		  		AlertDialog.Builder builder = new AlertDialog.Builder(cw);
		  		
		  		//ORIGINAL WAY TO DO IT:
	  			//AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
	  			
	  			builder.setCancelable(false);	  			
	  			
	  			builder.setTitle("Choose Action");	  			
	  			
	  			
	  			// if back pressed: DOES THIS WORK????????????
				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						
						//GOTO SOME METHOD!!!!!!!!!!!!!!
						
						runActionsOnUi();
					}
				});
	  			
				
	            builder.setAdapter(adapter,
	                    new DialogInterface.OnClickListener() {
	                        @Override
	                        public void onClick(final DialogInterface dialog,
	                                int item) {
	                        	if (item == 0) {										
									
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " attacks...");
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			//hideNavigation();
											//isInvokingService = "true";
											attack();
											
											dialog.dismiss();
							  	  	  	}
						  	  	  	}, 1000);										
								}
								if (item == 1) {
									
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " attempts to disarm...");
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			//hideNavigation();
											//isInvokingService = "true";
											disarm();
											
											dialog.dismiss();
							  	  	  	}
						  	  	  	}, 1000);										
								}
								if (item == 2) {
									
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts haste...");
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			//hideNavigation();
											//isInvokingService = "true";
											haste();
											
											dialog.dismiss();
							  	  	  	}
						  	  	  	}, 1000);									
								}
								if (item == 3) {
									
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts cure...");
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			//hideNavigation();
											//isInvokingService = "true";
											cure();
											
											dialog.dismiss();
							  	  	  	}
						  	  	  	}, 1000);										
								}
								if (item == 4) {
									
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts bless...");
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			//hideNavigation();
											//isInvokingService = "true";
											bless();
											
											dialog.dismiss();
							  	  	  	}
						  	  	  	}, 1000);									
								}
								
								//((AlertDialog) dialog).getButton(dialog.BUTTON1).setGravity(Gravity.CENTER);
								//SET TEXT SIXE IN XML						
								//View messageText = ((TextView) dialog).findViewById(R.id.title);		  		
					            //((TextView) messageText).setGravity(Gravity.CENTER);
					            //messageText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
								
								isInvokingService = "true";
	                        	
	                            dialog.dismiss();
	                        }
	                    });	            
	            
	            AlertDialog alert = builder.create();
	            alert.show();	            
	            
	            
	            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

	            lp.copyFrom(alert.getWindow().getAttributes());
	            lp.width = 1050;	            
	            alert.getWindow().setAttributes(lp);
	            
	            /* CAN ADJUST DIALOGS HEIGHT & WIDTH
	            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	            lp.copyFrom(alert.getWindow().getAttributes());
	            lp.width = 2000;
	            lp.height = 1000;
	            lp.x=-170;
	            lp.y=100;
	            alert.getWindow().setAttributes(lp);
	            */
  	  	    }
  	  	    
  	  	    // IS THIS USED?:
			private LayoutInflater getSystemService(String layoutInflaterService) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		
		// OLD WAY (STANDARD - CAN'T ADJUST ANYTHING):
		/*
		isInvokingService = "false";
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
	  	  	    @Override
	  	  	    public void run() {
	  	  	    	
		  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		  			
		  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		  			centerscrolltext.setTypeface(typeFace);
		  			
	  	  	    	
		  	  	    final String[] items = new String[] { "Attack", "Disarm", "Haste", "Cure", "Bless" };
		      		
					final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);					
					
					builder.setCancelable(false);
					//DOESN'T WORK:
					//builder.setCanceledOnTouchOutside(false);
					
					
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
								public void onClick(final DialogInterface dialog,	int item) {
	
									if (item == 0) {										
										
										centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " attacks...");
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
							  	  	  			//hideNavigation();
												//isInvokingService = "true";
												attack();
												
												dialog.dismiss();
								  	  	  	}
							  	  	  	}, 1000);										
									}
									if (item == 1) {
										
										centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " attempts to disarm...");
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
							  	  	  			//hideNavigation();
												//isInvokingService = "true";
												disarm();
												
												dialog.dismiss();
								  	  	  	}
							  	  	  	}, 1000);										
									}
									if (item == 2) {
										
										centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts haste...");
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
							  	  	  			//hideNavigation();
												//isInvokingService = "true";
												haste();
												
												dialog.dismiss();
								  	  	  	}
							  	  	  	}, 1000);									
									}
									if (item == 3) {
										
										centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts cure...");
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
							  	  	  			//hideNavigation();
												//isInvokingService = "true";
												cure();
												
												dialog.dismiss();
								  	  	  	}
							  	  	  	}, 1000);										
									}
									if (item == 4) {
										
										centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts bless...");
										
										final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
							  	  	  			//hideNavigation();
												//isInvokingService = "true";
												bless();
												
												dialog.dismiss();
								  	  	  	}
							  	  	  	}, 1000);									
									}
									
									//((AlertDialog) dialog).getButton(dialog.BUTTON1).setGravity(Gravity.CENTER);
									
									
									isInvokingService = "true";								
								}
							});
					builder.create().show();				
	  	  	    }
		});
		*/
	}
	
	public void disarmedAction() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		isInvokingService = "false";
		
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
	  	  	    					
					
					final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			/* THIS WAS REPLACED BY 2ND "ELSE IF" FOR HASTE OPTION:
		  	  	  			if (disarmedTurnStart[0] + 2 == turn) { // SO PLAYER DOESN'T HASTE ON 2ND TURN OF BEING DISARMED.
		  	  	  				
			  	  	  			final String[] items = new String[] { "Punch", "Cure" };
					      		
								AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
				
								// if back pressed: DOES THIS WORK????????????
								builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
									@Override
									public void onCancel(DialogInterface dialog) {
										
										//GOTO SOME METHOD!!!!!!!!!!!!!!
										
										disarmedAction();
									}
								});					
								
								builder.setTitle("Choose Action").setItems(items,
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,	int item) {							
												
												if (item == 0) {
													//hideNavigation();
													//isInvokingService = "true";
													//punch();
													attack();
												}
												
												if (item == 1) {
													//hideNavigation();
													//isInvokingService = "true";
													cure();
												}										
												
												isInvokingService = "true";											
											}
										});
								builder.create().show();	  	  	  				
		  	  	  			}
		  	  	  			
		  	  	  			else {(STUFF BELOW)}
		  	  	  			*/
		  	  	  			
		  	  	  			
		  	  	  			// NEW WAY TO DO DIALOG
		  			  		final String[] items = {"Punch", "Haste", "Cure"};
		  		
		  			  		// Instead of String[] items, Here you can also use ArrayList for your custom object..
		  		
		  			  		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row, R.id.title,items) {
		  		
		  			  		    ViewHolder holder;
		  			  		    Drawable icon;
		  		
		  			  		    class ViewHolder {
		  			  		        ImageView icon;
		  			  		        TextView title;						
		  			  		    }
		  		
		  			  		    public View getView(int position, View convertView, ViewGroup parent) {
		  			  		        final LayoutInflater inflater = (LayoutInflater) getApplicationContext()
		  			  		                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  			  		        
		  			  		        //THIS STUFF DID NOT WORK:
		  			  		        //TextView textView = (TextView) findViewById(R.layout.list_row);
		  			  		        //textView.setGravity(Gravity.CENTER);
		  			  		        //textView.setLayoutParams(new ListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 32 /* this is item height */));
		  			  		        
		  		
		  			  		        if (convertView == null) {
		  			  		            convertView = inflater.inflate(R.layout.list_row, null);
		  		
		  			  		            holder = new ViewHolder();
		  			  		            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
		  			  		            holder.title = (TextView) convertView.findViewById(R.id.title);		  		           
		  			  		            
		  			  		            convertView.setTag(holder);
		  			  		        } else {
		  			  		            // view already defined, retrieve view holder
		  			  		            holder = (ViewHolder) convertView.getTag();
		  			  		        }       
		  		
		  			  		       // Drawable drawable = getResources().getDrawable(R.drawable.list_icon); //this is an image from the drawables folder
		  		
		  			  		        holder.title.setText(items[position]);
		  			  		        //holder.icon.setImageDrawable(drawable);     
		  			  		        
		  		
		  			  		        return convertView;
		  			  		    }
		  			  		};		  		
		  			  		
		  			  		
		  			  		// THIS WAY ALLOWS YOU TO STYLE THE DIALOG:
		  			  		ContextThemeWrapper cw = new ContextThemeWrapper(MainActivity2.this, R.style.DialogWindowTitle_Holo);
		  			  		AlertDialog.Builder builder = new AlertDialog.Builder(cw);
		  			  		
		  			  		//ORIGINAL WAY TO DO IT:
		  		  			//AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
		  		  			
		  		  			builder.setCancelable(false);	  			
		  		  			
		  		  			builder.setTitle("Choose Action");	  			
		  		  			
		  		  			
		  		  			// if back pressed: DOES THIS WORK????????????
		  					builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
		  						@Override
		  						public void onCancel(DialogInterface dialog) {
		  							
		  							//GOTO SOME METHOD!!!!!!!!!!!!!!
		  							
		  							runActionsOnUi();
		  						}
		  					});
		  		  			
		  					
		  		            builder.setAdapter(adapter,
		  		                    new DialogInterface.OnClickListener() {
		  		                        @Override
		  		                        public void onClick(final DialogInterface dialog,
		  		                                int item) {
		  		                        	if (item == 0) {										
		  										
		  		                        		centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);
												centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " attacks...");
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
									  	  	  			//hideNavigation();
														//isInvokingService = "true";
														//punch();
														attack();
										  	  	  	}
									  	  	  	}, 1000);										
		  									}
		  									if (item == 1) {
		  										
		  									//hideNavigation();
												//isInvokingService = "true";												
													
												if (hasteSpell[0] < 1) {
													
													centerscrolltext.setVisibility(View.VISIBLE);
											  		centerscrolltext.startAnimation(animAlphaText);
											  		centerscrolltext.append("\n" + "> You have already used your Haste spells.");
													
													disarmedAction();
												}												
												
												else if ((hasteSpell[0] > 0) && !(disarmedTurnStart[0] + 2 == turn)) {
													
													centerscrolltext.setVisibility(View.VISIBLE);													
											  		centerscrolltext.startAnimation(animAlphaText);
													centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts haste...");
													
													final Handler h = new Handler();
										  	  	  	h.postDelayed(new Runnable() {		  	  	  			
										  	  	  			
										  	  	  		@Override
											  	  	  	public void run() {
										  	  	  			
										  	  	  			haste();
											  	  	  	}
										  	  	  	}, 1000);												
												}
												
												else {
													
													disarmedAction();
												}										
		  									}
		  									if (item == 2) {
		  										
		  									//hideNavigation();
												//isInvokingService = "true";
												if (cureSpell[0] > 0) {
													
													centerscrolltext.setVisibility(View.VISIBLE);													
											  		centerscrolltext.startAnimation(animAlphaText);
													centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts cure...");
													
													final Handler h = new Handler();
										  	  	  	h.postDelayed(new Runnable() {		  	  	  			
										  	  	  			
										  	  	  		@Override
											  	  	  	public void run() {
										  	  	  			
										  	  	  			cure();
											  	  	  	}
										  	  	  	}, 1000);													
												}
												
												if (cureSpell[0] < 1) {
													
													disarmedAction();
												}									
		  									}						
		  									
		  									
		  									isInvokingService = "true";
		  		                        	
		  		                            dialog.dismiss();
		  		                        }
		  		                    });	            
		  		            
		  		            AlertDialog alert = builder.create();
		  		            alert.show();	            
		  		            
		  		            
		  		            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

		  		            lp.copyFrom(alert.getWindow().getAttributes());
		  		            lp.width = 1050;	            
		  		            alert.getWindow().setAttributes(lp);	  	  			
		  	  	  			
		  	  	  			
		  	  	  			// OLD DIALOG BUILDER:
		  	  	  			/*
	  	  	  				final String[] items = new String[] { "Punch", "Haste", "Cure" };
			      		
							AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
							
							builder.setCancelable(false);
							//DOESN'T WORK:
							//builder.setCanceledOnTouchOutside(false);
							
							// if back pressed: DOES THIS WORK????????????
							
							builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
								@Override
								public void onCancel(DialogInterface dialog) {
									
									//GOTO SOME METHOD!!!!!!!!!!!!!!
									
									disarmedAction();
								}
							});					
							
							builder.setTitle("Choose Action").setItems(items,
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,	int item) {							
											
											if (item == 0) {
												
												centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);
												centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " attacks...");
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
									  	  	  			//hideNavigation();
														//isInvokingService = "true";
														//punch();
														attack();
										  	  	  	}
									  	  	  	}, 1000);											
											}
											if (item == 1) {
												//hideNavigation();
												//isInvokingService = "true";												
													
												if (hasteSpell[0] < 1) {
													
													centerscrolltext.setVisibility(View.VISIBLE);
											  		centerscrolltext.startAnimation(animAlphaText);
											  		centerscrolltext.append("\n" + "> You have already used your Haste spells.");
													
													disarmedAction();
												}												
												
												else if ((hasteSpell[0] > 0) && !(disarmedTurnStart[0] + 2 == turn)) {
													
													centerscrolltext.setVisibility(View.VISIBLE);													
											  		centerscrolltext.startAnimation(animAlphaText);
													centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts haste...");
													
													final Handler h = new Handler();
										  	  	  	h.postDelayed(new Runnable() {		  	  	  			
										  	  	  			
										  	  	  		@Override
											  	  	  	public void run() {
										  	  	  			
										  	  	  			haste();
											  	  	  	}
										  	  	  	}, 1000);												
												}
												
												else {
													
													disarmedAction();
												}
											}
											if (item == 2) {
												//hideNavigation();
												//isInvokingService = "true";
												if (cureSpell[0] > 0) {
													
													centerscrolltext.setVisibility(View.VISIBLE);													
											  		centerscrolltext.startAnimation(animAlphaText);
													centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts cure...");
													
													final Handler h = new Handler();
										  	  	  	h.postDelayed(new Runnable() {		  	  	  			
										  	  	  			
										  	  	  		@Override
											  	  	  	public void run() {
										  	  	  			
										  	  	  			cure();
											  	  	  	}
										  	  	  	}, 1000);													
												}
												
												if (cureSpell[0] < 1) {
													
													disarmedAction();
												}												
											}										
											
											isInvokingService = "true";											
										}
									});
							
							builder.create().show();
							*/		  	  	  			
			  	  	  	}
		  	  	  	}, 2000);					
	  	  	    }
		});		
	}
	
	/*
	public void punch() {
		
		//
		// 
		// Picture of fists?
		// 
		// punchGraphic();
		// 
		//
		
		attack(); //ADJUSTED TO-HIT FOR BEING DISARMED
		
		
		
		//if (numberOfPlayers > 1) {
			//System.out.print("Player " + playerNumber[i]
					//+ ", who do you want to punch? (Enter player number): ");
			//playerNumberAttacked = input.nextInt();
			//playerNumberAttacked = playerNumberAttacked - 1;
		//}
				
		
		
		//if (attackResult <= 1) CAN YOU CRITICL MISS A PUNCH???????????????????????????????????? -----DISARM AGAIN TO MAINTAIN BALANCE. 
		//FOR NOW i'll do no rolls to keep it simpler, but in future might go w roll to hurt yourself (ie stumble & fall) but no roll to lose weapon. 
				
	}
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
		    	iscomputerhasteused.equals("no");// so computer doesn't use a haste during a haste.		    	
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
				
				//int i = 0;			
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);

				// Use a blank drawable to hide the imageview animation:
				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
				
				computerCardStopFadeInFadeOut();
    			playerCardStartFadeInFadeOut();
    			
    			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
    			
    			//NEED THIS??
    			TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
    			playerHitPointsTextView.setTypeface(typeFace);
    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
        		
    			gameEngineHumanFirst1();    			
    			
    			turn++;				
			}
			
			if (ArrayOfInitiative.initiative[0] < ArrayOfInitiative.initiative[1]) {		
				
				//int i = 1;				
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);

				// Use a blank drawable to hide the imageview animation:
				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
				
				playerCardStopFadeInFadeOut();
    			computerCardStartFadeInFadeOut();
    			
    			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
    			
    			//NEED THIS??
    			TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
    			computerHitPointsTextView.setTypeface(typeFace);
    			computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[1]));
    			
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
		
		
		if (canHasDisarmed[1].equals("yes") && didComputerCriticalMiss.equals("yes") && disarmedTurnStart[1] + 3 == turn) {
			
			canHasDisarmed[1] = "no";
		
			didComputerCriticalMiss = "no";					
		}	
		
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {  	  	    	
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			// Re-enables ability to use srollbar:
				//centerscrolltext.bringToFront();
	  			
	  			ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
  	  	    	titleBlankButton.bringToFront();
	  			
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);
		  		//centerscrolltext.append("\n");
		  		centerscrolltext.append("\n" + " >>>>>>>>>>>   " + " Turn " + turn + "   <<<<<<<<<<<");				
		  		//centerscrolltext.append("\n");
				
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		if (ArrayOfHitPoints.hitpoints[0] == 0 && endGameAfterFirstHaste.equals("no")) {
		  	  	  			
	  	  	  				//isInvokingService = "true";
	  	  	  				
	  	  	  				//NEED THIS?
	  	  	  				//SystemClock.sleep(1000);
		  	  	  			
			  	  	  		if (isInvokingService.equals("true")) {
								//NEED THIS?
								SystemClock.sleep(1000);
		  	  	  				endGame(); 	// took out map
			  	  	  		}
						}
			  	  		/*
			  	  	  	else if (ArrayOfHitPoints.hitpoints[0] == 0 && endGameAfterFirstHaste.equals("yes")) {
		  	  	  			
		  	  	  			endGameAfterFirstHasteMethod();
		  	  	  		}
		  	  	  		*/
			  	  		else if (canHasDisarmed[0].equals("yes")) {
							
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
							
							/*
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
							*/
			  	  			if ((didHumanCriticalMiss.equals("yes") && disarmedTurnStart[0] == turn) || (didHumanCriticalMiss.equals("yes") && disarmedTurnStart[0] + 1 == turn)) {			  	  				
				  	  											
			  	  				disarmedAction();				  	  			
			  	  			}
			  	  			
			  	  			else if ((didHumanCriticalMiss.equals("no") && disarmedTurnStart[0] + 1 == turn) || (didHumanCriticalMiss.equals("no") && disarmedTurnStart[0] + 2 == turn)) {
			  	  						  	  											
								disarmedAction();
												  	  			
			  	  			}
			  	  			
			  	  			else if (canHasDisarmed[0].equals("yes") && didHumanCriticalMiss.equals("yes") && disarmedTurnStart[0] + 2 == turn) {
				  				
				  				canHasDisarmed[0] = "no";				
				  				
				  				didHumanCriticalMiss = "no";
				  				
				  				if (isInvokingService.equals("true")){
									//NEED THIS?
									SystemClock.sleep(1000);	        		
										
									runActionsOnUi();
								}
				  			}
				  	    	
				  			else if (canHasDisarmed[0].equals("yes") && didHumanCriticalMiss.equals("no") && disarmedTurnStart[0] + 3 == turn) {
				  									
				  				canHasDisarmed[0] = "no";				
				  			
				  				//didHumanCriticalMiss = "no";
				  				
				  				if (isInvokingService.equals("true")){
									//NEED THIS?
									SystemClock.sleep(1000);	        		
										
									runActionsOnUi();
								}
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
		
		//Toast.makeText(MainActivity2.this, "COMPUTER TURN START", Toast.LENGTH_SHORT).show();	
		
    	
    	if (canHasDisarmed[1].equals("yes") && didComputerCriticalMiss.equals("no") && disarmedTurnStart[1] + 2 == turn) {
			
			canHasDisarmed[1] = "no";	  		  				
		}		
		
		
		// THIS THREAD IS BEING USED TO TEST ACCESS TO CENTERSCROLLTEXT
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {  	  	    	
  	  	    	
  	  	    	// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);

				// Use a blank drawable to hide the imageview animation:
				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
  	  	    	
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			// Re-enables ability to use srollbar:
	  			centerscrolltext.bringToFront();
	  			
	  			ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
  	  	    	titleBlankButton.bringToFront();
	  			
	  			
	  			// FROM HERE (WAS NOT IN THREAD BEFORE)
	  			
	  			//i = 1;
	  			//i = 0;
	  			
	  			
	  			// NEED THIS?:
	  			iscomputerhasteused.equals("no");// so computer doesn't use a haste during a haste.		
	  			
	  			playerCardStopFadeInFadeOut();
	  			computerCardStartFadeInFadeOut();		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			
	  			//NEED THIS??
	  			TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
	  			computerHitPointsTextView.setTypeface(typeFace);
	  			computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[1]));
	  			
	  			
	  			final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		if (ArrayOfHitPoints.hitpoints[1] == 0 && endGameAfterFirstHaste.equals("no")) {
		  	  	  			
			  	  	  		//isInvokingService = "true";
	  	  	  				
	  	  	  				//NEED THIS?
	  	  	  				//SystemClock.sleep(1000);
	  	  	  			
			  	  	  		if (isInvokingService.equals("true")) {
								//NEED THIS?
								SystemClock.sleep(1000);
		  	  	  				endGame(); 	// took out map
			  	  	  		}									
			  			}
		  	  	  		
		  	  	  		//TEST
		  	  	  		//SO TEXT/DIALOG/INSTANCE DOSENT GET REPEATED WHEN COMP IS AT HP = 0 & NO CURE:
		  	  	  		else if (ArrayOfHitPoints.hitpoints[1] == 0 && endGameAfterFirstHaste.equals("yes")) {
		  	  	  			
		  	  	  			endGameAfterFirstHasteMethod();
		  	  	  		}
								  	  	  		
			  			else if (canHasDisarmed[1].equals("yes")) {
			  				
			  				
			  				/*
			  				 * NEED GRAPHICS HERE:
			  				 * 
			  				 * disarmGraphic();
			  				 */
			  				
			  				
			  				// player number whose turn it is is less than the player
			  				// number of the player who disarmed him.
			  				/*
			  				if (disarmedTurnStart[1] == (turn)) {
			  					//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
			  					computerDisarmedAction();
			  				}
			  				
			  				else if (disarmedTurnStart[1] + 1 == turn) {
			  					//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
			  					computerDisarmedAction();
			  					//canHasDisarmed[1] = "no";//THIS HAS TO GO??????????
			  				}	
			  				//OLD:
			  				//else if (disarmedTurnStart[1] + 2 == turn) {
			  					//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
			  				//	computerDisarmedAction();
			  				//	canHasDisarmed[1] = "no";
			  				//}
			  				
			  				
			  				//NEED THIS? THIS IS IN gameEngineHumanFirst1().
			  				else if (disarmedTurnStart[1] + 2 == turn) {
			  					
			  					canHasDisarmed[1] = "no";
			  					gameEngineHumanFirst2();
			  				}
			  				*/
			  				
			  				if ((didComputerCriticalMiss.equals("yes") && disarmedTurnStart[1] + 1 == turn) || (didComputerCriticalMiss.equals("yes") && disarmedTurnStart[1] + 2 == turn)) {
			  	  												
			  	  				computerDisarmedAction();								
			  	  			}
			  	  			
			  	  			else if ((didComputerCriticalMiss.equals("no") && disarmedTurnStart[1] == turn) || (didComputerCriticalMiss.equals("no") && disarmedTurnStart[1] + 1 == turn)) { //HUMAN MUST HAVE DISARMED HUMAN
			  	  												
			  	  				computerDisarmedAction();										  	  			
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
			  				
			  				
			  				computerAction = (int) (Math.random() * 100) + 1;
			  				computerAttack();					
			  			}	  	  	  			
	  	  	  		}
	  	  	  	}, 2000);  			
	  			
	  			// TO HERE
  	  	    }
		});	
	}
	
	public void gameEngineComputerFirst1() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);  	
    	
    	
		if (canHasDisarmed[0].equals("yes") && didHumanCriticalMiss.equals("yes") && disarmedTurnStart[0] + 3 == turn) {
			
			canHasDisarmed[0] = "no";
			
			didHumanCriticalMiss = "no";				
		}
    	
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {  	    	
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			// Re-enables ability to use srollbar:
	  			centerscrolltext.bringToFront();
	  			
	  			ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
  	  	    	titleBlankButton.bringToFront();
	  			
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);
		  		//centerscrolltext.append("\n");
		  		centerscrolltext.append("\n" + " >>>>>>>>>>>   " + " Turn " + turn + "   <<<<<<<<<<<");				
		  		//centerscrolltext.append("\n");
				
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		if (ArrayOfHitPoints.hitpoints[1] == 0 && endGameAfterFirstHaste.equals("no")) {
		  	  	  			
			  	  	  		//isInvokingService = "true";
	  	  	  				
	  	  	  				//NEED THIS?
	  	  	  				//SystemClock.sleep(1000);
	  	  	  			
			  	  	  		if (isInvokingService.equals("true")) {
								//NEED THIS?
								SystemClock.sleep(1000);
		  	  	  				endGame(); 	// took out map
			  	  	  		}										
						}
		  	  	  		
		  	  	  		//TEST
		  	  	  		//SO TEXT/DIALOG/INSTANCE DOSENT GET REPEATED WHEN COMP IS AT HP = 0 & NO CURE:
			  	  	  	else if (ArrayOfHitPoints.hitpoints[1] == 0 && endGameAfterFirstHaste.equals("yes")) {
		  	  	  			
		  	  	  			endGameAfterFirstHasteMethod();
		  	  	  		}
		  	  	  		
			  	  		else if (canHasDisarmed[1].equals("yes")) {
							
							
							/*
							 * NEED GRAPHICS HERE:
							 * 
							 * disarmGraphic();
							 */
							
							/*
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
							*/
			  	  			
				  	  		if ((didComputerCriticalMiss.equals("yes") && disarmedTurnStart[1] == turn) || (didComputerCriticalMiss.equals("yes") && disarmedTurnStart[1] + 1 == turn)) {			
			  	  												
			  	  				computerDisarmedAction();				  	  			
			  	  			}
			  	  			
			  	  			else if ((didComputerCriticalMiss.equals("no") && disarmedTurnStart[1] + 1 == turn) || (didComputerCriticalMiss.equals("no") && disarmedTurnStart[1] + 2 == turn)) {
			  	  												
			  	  				computerDisarmedAction();									  	  			
			  	  			}
				  	  		
				  	  		
				  	  		
				  	  		
			  	  			else if (canHasDisarmed[1].equals("yes") && didComputerCriticalMiss.equals("yes") && disarmedTurnStart[1] + 2 == turn) {
								
								canHasDisarmed[1] = "no";
								
								didComputerCriticalMiss = "no";	
								
								computerAction = (int) (Math.random() * 100) + 1;
								computerAttack();
							}
					    	
							else if (canHasDisarmed[1].equals("yes") && didComputerCriticalMiss.equals("no") && disarmedTurnStart[1] + 3 == turn) {
													
								canHasDisarmed[1] = "no";
								
								//didComputerCriticalMiss = "no";
								
								computerAction = (int) (Math.random() * 100) + 1;
								computerAttack();
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
							
							
							computerAction = (int) (Math.random() * 100) + 1;
							computerAttack();					
						}
		  	  	  	}
	  	  	  	}, 2000);
  	  	    }
		});
	}	
	
	public void gameEngineComputerFirst2() {
		
		   	
    	if (canHasDisarmed[0].equals("yes") && didHumanCriticalMiss.equals("no") && disarmedTurnStart[0] + 2 == turn) {
			
			canHasDisarmed[0] = "no"; 		  				
		}	
		
		
		runOnUiThread(new Runnable() {//DID NOT NEED THIS BEFORE? USED IN CASE NEEDED FOR REF TO IMAGEVIEWS.
  	  	    @Override
  	  	    public void run() {  				  	  	    
  	  	    	
  	  	    	
				//i = 0;		
				//i = 1;
				
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
		
				// Use a blank drawable to hide the imageview animation:
				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
				
				
				computerCardStopFadeInFadeOut();
				playerCardStartFadeInFadeOut();
				
				final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
				//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
					
				Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				centerscrolltext.setTypeface(typeFace);
				
				
				// Re-enables ability to use srollbar:
				//centerscrolltext.bringToFront();
				
				ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
  	  	    	titleBlankButton.bringToFront();
				
  	  	    	//NEED THIS??
				TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
				playerHitPointsTextView.setTypeface(typeFace);
				playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		if (ArrayOfHitPoints.hitpoints[0] == 0 && endGameAfterFirstHaste.equals("no")) {
		  	  	  			
			  	  	  		//isInvokingService = "true";
	  	  	  				
	  	  	  				//NEED THIS?
	  	  	  				//SystemClock.sleep(1000);
	  	  	  			
			  	  	  		if (isInvokingService.equals("true")) {
								//NEED THIS?
								SystemClock.sleep(1000);
		  	  	  				endGame(); 	// took out map
			  	  	  		}									
						}
		  	  	  		/*
		  	  	  		else if (ArrayOfHitPoints.hitpoints[0] == 0 && endGameAfterFirstHaste.equals("yes")) {
		  	  	  			
		  	  	  			endGameAfterFirstHasteMethod();
		  	  	  		}
						*/
						else if (canHasDisarmed[0].equals("yes")) {
							
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
							/*
							if (disarmedTurnStart[0] == (turn)) {
								//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
								disarmedAction();
							}
							
							else if (disarmedTurnStart[0] + 1 == turn) {
								//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
								disarmedAction();
								//canHasDisarmed[0] = "no";//THIS HAS TO GO??????????					
							}
							//OLD:
							//else if (disarmedTurnStart[0] + 2 == turn) {
								//playersTemplate(navigableMap); THIS JUST SHOWS PLAYERS HP & SKILLS LEFT
							//	disarmedAction();
							//	canHasDisarmed[0] = "no";
							//}
												
							//NEED THIS? THIS IS IN gameEngineComputerFirst1().
							else if (disarmedTurnStart[0] + 2 == turn) {
								
								canHasDisarmed[0] = "no";
								gameEngineComputerFirst2();
							}
							*/
							
							if ((didHumanCriticalMiss.equals("yes") && disarmedTurnStart[0] + 1 == turn) || (didHumanCriticalMiss.equals("yes") && disarmedTurnStart[0] + 2 == turn)) {
			  	  													
								disarmedAction();								
			  	  			}
			  	  			
			  	  			else if ((didHumanCriticalMiss.equals("no") && disarmedTurnStart[0] == turn) || (didHumanCriticalMiss.equals("no") && disarmedTurnStart[0] + 1 == turn)) { //COMPUTER MUST HAVE DISARMED HUMAN
			  	  													
								disarmedAction();										  	  			
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
	  	  	  	}, 2000);				
  	  	    }
		});
	}
	
	public void skillsCheck() {
		
		if (numberOfPlayers == 1) {
			
			if (blessSpell[0] < 1) {
				
				ImageView blessLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftbless);
				blessLeft.setVisibility(View.VISIBLE);				
			}				
			if (cureSpell[0] < 1) {
				
				ImageView cureLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftcure);
				cureLeft.setVisibility(View.VISIBLE);				
			}			
			if (dodgeBlowSpell[0] < 1) {
				
				ImageView dodgeLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftdodge);
				dodgeLeft.setVisibility(View.VISIBLE);				
			}			
			if (mightyBlowSpell[0] < 1) {
				
				ImageView mbLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftmb);
				mbLeft.setVisibility(View.VISIBLE);				
			}			
			if (hasteSpell[0] < 2) {
				
				ImageView hasteLeft1 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste1);
				hasteLeft1.setVisibility(View.VISIBLE);				
			}			
			if (hasteSpell[0] < 1) {
				
				ImageView hasteLeft1 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste1);
				hasteLeft1.setVisibility(View.VISIBLE);
				ImageView hasteLeft2 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste2);
				hasteLeft2.setVisibility(View.VISIBLE);				
			}
			
			
			if (blessSpell[1] < 1) {
				
				ImageView blessRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightbless);
				blessRight.setVisibility(View.VISIBLE);				
			}				
			if (cureSpell[1] < 1) {
				
				ImageView cureRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightcure);
				cureRight.setVisibility(View.VISIBLE);				
			}			
			if (dodgeBlowSpell[1] < 1) {
				
				ImageView dodgeRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightdodge);
				dodgeRight.setVisibility(View.VISIBLE);				
			}			
			if (mightyBlowSpell[1] < 1) {
				
				ImageView mbRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightmb);
				mbRight.setVisibility(View.VISIBLE);				
			}			
			if (hasteSpell[1] < 2) {
				
				ImageView hasteRight1 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste1);
				hasteRight1.setVisibility(View.VISIBLE);				
			}			
			if (hasteSpell[1] < 1) {
				
				ImageView hasteRight1 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste1);
				hasteRight1.setVisibility(View.VISIBLE);
				ImageView hasteRight2 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste2);
				hasteRight2.setVisibility(View.VISIBLE);				
			}
						
		}
		
		
		/*
		if (numberOfPlayers > 1) {
			
		}
		*/
	}	
		
	public void endGame() {
		
		isInvokingService = "false";
		
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
					
					
	    			// USED BY HASTE PART TWO?:
					if (ArrayOfHitPoints.hitpoints[0] < 0) {
						
						playerDeadYet[0] = "yes";
						
						gameOverCheck();
						
						//isInvokingService = "true";
						
						// NEED THIS?:
						//return;
					}
					
					else if (ArrayOfHitPoints.hitpoints[1] < 0) {
						
						playerDeadYet[1] = "yes";
						
						gameOverCheck();
						
						//isInvokingService = "true";
						
						// NEED THIS?:
						//return;
					}
					
					else if (ArrayOfHitPoints.hitpoints[1] == 0) {			
						
						centerscrolltext.setVisibility(View.VISIBLE);													
		  	  	  		centerscrolltext.startAnimation(animAlphaText);
		  	  			centerscrolltext.append("\n" + "> The computer is unconscious.");
						
			  	  		final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (cureSpell[1] > 0) {
									
									centerscrolltext.setVisibility(View.VISIBLE);												
					  	  	  		centerscrolltext.startAnimation(animAlphaText);
					  	  			centerscrolltext.append("\n" + "> The computer uses cure spell...");
									
									computerCure();
									
									isInvokingService = "true";																		
								}
				  	  	  		
				  	  	  		else {
				  	  	  			
				  	  	  			//isInvokingService = "false";
				  	  	  			
				  	  	  			// DIALOG WAS SHOWING TWICE (NOT SURE WHY isInvokingService ISNT WORKING) SO USED THIS:
				  	  	  			//if ((mydialog == null) || !mydialog.isShowing()) {
				  	  	  				
					  	  	  			final String[] items = new String[] { "Slay", "Mercy" };
							      		
										AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
										
										builder.setCancelable(false);
										
										// if back pressed: DOES THIS WORK????????????
										
										builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
											@Override
											public void onCancel(DialogInterface mydialog) {
												
												//GOTO SOME METHOD!!!!!!!!!!!!!!
												
												endGame();										
											}
										});					
										
										builder.setTitle("The computer is at your mercy...").setItems(items,
												new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface mydialog, int item) {							
														
														if (item == 0) {
															//hideNavigation();
															
															//mydialog.dismiss();
															
															playerDeadYet[1] = "yes";
															playerDeadYet[0] = "no";
															
															centerscrolltext.setVisibility(View.VISIBLE);												
											  	  	  		centerscrolltext.startAnimation(animAlphaText);
											  	  			centerscrolltext.append("\n" + "> Let it be so...");
															
															final Handler h = new Handler();
												  	  	  	h.postDelayed(new Runnable() {		  	  	  			
												  	  	  			
												  	  	  		@Override
													  	  	  	public void run() {
												  	  	  			
												  	  	  			gameOverCheck();
													  	  	  	}
												  	  	  	}, 2000);													
														}
														
														if (item == 1) {
															//hideNavigation();
															
															//mydialog.dismiss();
															
															playerDeadYet[1] = "yes";
															playerDeadYet[0] = "no";
															
															centerscrolltext.setVisibility(View.VISIBLE);												
											  	  	  		centerscrolltext.startAnimation(animAlphaText);
											  	  			centerscrolltext.append("\n" + "> The old gods are pleased...");
															
															final Handler h = new Handler();
												  	  	  	h.postDelayed(new Runnable() {		  	  	  			
												  	  	  			
												  	  	  		@Override
													  	  	  	public void run() {
												  	  	  			
												  	  	  			gameOverCheck();
													  	  	  	}
												  	  	  	}, 2000);
														}
														
														mydialog.dismiss();
														//isInvokingService = "true";
													}
												});
										builder.create().show();
				  	  	  			//}				  	  	  							  	  	  			
				  	  	  		}
				  	  	  	}
			  	  	  	}, 2000);						
					}
					
					else if (ArrayOfHitPoints.hitpoints[0] == 0) {				
										
						centerscrolltext.setVisibility(View.VISIBLE);												
		  	  	  		centerscrolltext.startAnimation(animAlphaText);
		  	  			centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you are unconscious.");				
						
			  	  		final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (cureSpell[0] > 0) {
									
				  	  	  			//isInvokingService = "false";
				  	  	  			
									/*
									centerscrolltext.setVisibility(View.VISIBLE);												
					  	  	  		centerscrolltext.startAnimation(animAlphaText);
					  	  			centerscrolltext.append("\n" + "> Do you want to use your cure spell?");
					  	  			*/  	  			
					  	  			
						  	  		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity2.this);
						  			
						  	  		alert.setCancelable(false);
						  	  		
						  	    	alert.setTitle("Do you want to use your Cure spell?");
						  	    	/*
						  	    	alert.setMessage("something");
						  	    	*/	  	    	
						  	    	
						  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						  		    	public void onClick(DialogInterface dialog, int whichButton) {
						  		    		
						  		    		//hideNavigation();
						  		    		
						  		    		cure();
						  		    		
						  		    		isInvokingService = "true";
						  		    		
						  		    		dialog.dismiss();					  		    		
						  		    	}
						  	    	});
						  	    	
						  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
						          	  public void onClick(DialogInterface dialog, int whichButton) {
						          		  	
						          		  	dialog.dismiss();
						          		  
						          		  	//hideNavigation();
						          		  	
						          		  	//endGame();
						          		  	
							          		final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
									  	  	  		centerscrolltext.setVisibility(View.VISIBLE);												
									  	  	  		centerscrolltext.startAnimation(animAlphaText);
									  	  			centerscrolltext.append("\n" + "> Yes, it is probably best if you stay down...");
									  	  			
										  	  		final Handler h = new Handler();
										  	  	  	h.postDelayed(new Runnable() {		  	  	  			
										  	  	  			
										  	  	  		@Override
											  	  	  	public void run() {
										  	  	  			
											  	  	  		playerDeadYet[1] = "no";
										          		  	playerDeadYet[0] = "yes";
										          		  	
										          		  	//isInvokingService = "true";
										          		  	
										          		  	gameOverCheck();
											  	  	  	}
										  	  	  	}, 2000);
									  	  	  	}
								  	  	  	}, 2000);						          		  	
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
								
								else {
									
									centerscrolltext.setVisibility(View.VISIBLE);												
					  	  	  		centerscrolltext.startAnimation(animAlphaText);
					  	  			centerscrolltext.append("\n" + "> " + "The computer decides to let you live as an example to others.");
					  	  			
					  	  			playerDeadYet[1] = "no";
					  	  			playerDeadYet[0] = "yes";
					  	  			
						  	  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			//isInvokingService = "true";
						  	  	  			
						  	  	  			gameOverCheck();
							  	  	  	}
						  	  	  	}, 2000);				  	  			
								}
				  	  	  	}
			  	  	  	}, 2000);						
					}					
				}
  	  	    }
  	  	});
	}
	
	public void endGameAfterFirstHasteMethod() {
		
		Toast.makeText(MainActivity2.this, "IT'S FUCKING LOOPING", Toast.LENGTH_SHORT).show();
	}
	
	public void gameOverCheck() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
  	  	    	Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");				
  	  	    	
    			
  	  	    	victoryDefeatAnimation();
  	  	    	
  	  	    	
				if (playerDeadYet[0] == "no" && playerDeadYet[1] == "yes") {
				/*&& playerDeadYet[2] == "yes" && playerDeadYet[3] == "yes"	&& playerDeadYet[4] == "yes" && playerDeadYet[5] == "yes"*/
					
					gameOn = 0;
					
					
					final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
					//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
					
					//Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
					centerscrolltext.setTypeface(typeFace);					
					
					
					centerscrolltext.setVisibility(View.VISIBLE);												
		  	  		centerscrolltext.startAnimation(animAlphaText);
		  			centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you are victorious!");
		  			
		  			final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
		  	  	  			centerscrolltext.setVisibility(View.VISIBLE);												
		  	  	  			centerscrolltext.startAnimation(animAlphaText);
		  	  	  			centerscrolltext.append("\n" + "> Game Over!");
		  	  	  			
		  	  	  			MediaPlayerWrapper.play(MainActivity2.this, R.raw.buttonsound6);
		  	  	  			
			  	  	  		final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			//MediaPlayerWrapper.play(MainActivity2.this, R.raw.buttonsound6);
				  	  	  			
				  	  	  			foldScrolls();
				  	  	  			
					  	  	  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			finish();
						  	  	  			
						  	  	  			Intent i = new Intent(MainActivity2.this, MainActivity1.class);
						  	  	  			MainActivity2.this.startActivity(i);
						  	  	  			
							  	  	  		//Intent openMainActivity1 = new Intent("com.nedswebsite.ktog.MAINACTIVITY2");
						    	        	//startActivity(openMainActivity1);
							  	  	  	}
						  	  	  	}, 630);
					  	  	  	}
				  	  	  	}, 2000);
			  	  	  	}
		  	  	  	}, 2000);	  			
		  			
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
					
					//Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
					centerscrolltext.setTypeface(typeFace);				
					
					//final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
					
					centerscrolltext.setVisibility(View.VISIBLE);												
		  	  		centerscrolltext.startAnimation(animAlphaText);
		  			centerscrolltext.append("\n" + "> The computer is victorious!");
		  			
		  			final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
		  	  	  			centerscrolltext.setVisibility(View.VISIBLE);												
		  	  	  			centerscrolltext.startAnimation(animAlphaText);
		  	  	  			centerscrolltext.append("\n" + "> Game Over!");
		  	  	  			
		  	  	  			MediaPlayerWrapper.play(MainActivity2.this, R.raw.buttonsound6);
		  	  	  			
			  	  	  		final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			//MediaPlayerWrapper.play(MainActivity2.this, R.raw.buttonsound6);
				  	  	  			
				  	  	  			foldScrolls();
				  	  	  			
					  	  	  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			finish();
						  	  	  			
						  	  	  			Intent i = new Intent(MainActivity2.this, MainActivity1.class);
						  	  	  			MainActivity2.this.startActivity(i);
						  	  	  			
							  	  	  		//Intent openMainActivity1 = new Intent("com.nedswebsite.ktog.MAINACTIVITY2");
						    	        	//startActivity(openMainActivity1);
							  	  	  	}
						  	  	  	}, 630);
					  	  	  	}
				  	  	  	}, 2000);
			  	  	  	}
		  	  	  	}, 2000);
		  			
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
	
	public void victoryDefeatAnimation() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				ImageView img = (ImageView)findViewById(R.id.titleanimation);		
				img.setBackgroundResource(R.anim.victorydefeatanimation);		  	  
		  	  	
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();				
		  	  	
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
	  	  	  			TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);	  	  	  			
	  	  	  			titletext.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			
		  	  	  		TextView titlevictorydefeat = (TextView) findViewById(R.id.textviewtitlevictorydefeattext);		
						
						Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						titlevictorydefeat.setTypeface(typeFace);
						
						titlevictorydefeat.setVisibility(View.VISIBLE);
						
						
						if (playerDeadYet[0] == "no" && playerDeadYet[1] == "yes") {
							
							titlevictorydefeat.append("Victory");
						}
						
						if (playerDeadYet[1] == "no" && playerDeadYet[0] == "yes") {
							
							titlevictorydefeat.append("Defeat");
						}					
		  	  	  	}
	  	  	  	}, 600);	  	  	
			}
  		});
	}
	
	public void foldScrolls() {		
		
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// Setting up scroll frame animation.
				ImageView img = (ImageView)findViewById(R.id.scrollanimation);
				img.setBackgroundResource(R.anim.scrollanimationdown);
				
				img.bringToFront();
			
				// Get the background, which has been compiled to an AnimationDrawable object.
				AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
							
				// Start the animation.
				frameAnimation.stop();
				frameAnimation.start();
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		ImageView hasteLeft1 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste1);
		  	  	  		hasteLeft1.setVisibility(View.INVISIBLE);
		  	  	  		ImageView hasteLeft2 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste2);
		  	  	  		hasteLeft2.setVisibility(View.INVISIBLE);
	
		  	  	  		ImageView hasteRight1 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste1);
		  	  	  		hasteRight1.setVisibility(View.INVISIBLE);
		  	  	  		ImageView hasteRight2 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste2);
		  	  	  		hasteRight2.setVisibility(View.INVISIBLE);
		  	  	  		
		  	  	  		ImageView img1 = (ImageView) findViewById(R.id.imageviewplayerbox4left);
		  	  	  		img1.setVisibility(View.INVISIBLE);
		  	  	  		
		  	  	  		ImageView img2 = (ImageView) findViewById(R.id.imageviewplayerbox4right);
		  	  	  		img2.setVisibility(View.INVISIBLE);		  	  	  		
		  	  	  	}
	  	  	  	}, 600);
	  	    }
  		});	
	}

}