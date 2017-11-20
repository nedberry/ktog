package com.nedswebsite.ktog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.lang.ClassNotFoundException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Host extends Activity {
	
	float[] initiativeFinal = new float[6];
	
	String rollOff = "no";
	String rollOff305 = "no";
	String rollOff301 = "no";
	String rollOff315 = "no";
	String rollOff425 = "no";
	String rollOff420 = "no";
	String rollOff421 = "no";
	String rollOff4150 = "no";
	String rollOff4215 = "no";
	String rollOff4250 = "no";
	String rollOff4201 = "no";
	String rollOff40125 = "no";
	String rollOff530 = "no";
	String rollOff531 = "no";
	String rollOff532 = "no";
	String rollOff535 = "no";
	String rollOff5301 = "no";
	String rollOff5302 = "no";
	String rollOff5305 = "no";
	String rollOff5312 = "no";
	String rollOff5315 = "no";
	String rollOff5325 = "no";
	String rollOff53012 = "no";
	String rollOff53015 = "no";
	String rollOff53125 = "no";
	String rollOff53025 = "no";
	String rollOff530125 = "no";
	//String rollOff3150 ="no";
	
	
	boolean hostRolledDouble = false;
	boolean client0RolledDouble = false;
	boolean client1RolledDouble = false;
	boolean client2RolledDouble = false;
	boolean client3RolledDouble = false;
	
	
	int host;
	int client0;
	int client1;
	int client2;
	int client3;
	
	
	String doublesModifierOfInitiativeHost = "";
	String doublesModifierOfInitiativeClient0 = "";
	String doublesModifierOfInitiativeClient1 = "";
	String doublesModifierOfInitiativeClient2 = "";
	String doublesModifierOfInitiativeClient3 = "";
	
	
	String[] plyerAskedToDodgeCritHit = new String[] {"no", "no", "no", "no", "no", "no"};
	String[] playerAskedToDodgeDamage = new String[] {"no", "no", "no", "no", "no", "no"};
	String[] dgeRolled = new String[] {"null","null","null","null","null"};//IS THIS NULL BY DEFAULT???
	
	int numberOfPlayers;
	
	//public static String[] initiativeRolled = new String[6];
	String[] initiativeRolled = new String[] {"no", "no", "no", "no", "no", "no"};
	
	//Socket clientSocket;
	//Socket[] clientSocket = new Socket[1];
	
	ArrayList<ClientWorker> clientWorkers = new ArrayList<ClientWorker>();
	int idCounter = 0;
	
	ServerSocket serverSocket;
	//ServerSocket[] serverSocket = new ServerSocket[1];
	
	Handler updateConversationHandler;

	Thread serverThread;

	TextView text;

	//public static final int SERVERPORT = 2000;//WAS 6000
	
	
	
	int playerNumberAttacked;
		
	
	int gameOn;
	
	
	int max = 0;
	
	
	//static int numberOfPlayers = 1; // NEED THIS???????????
	
	
	
	int computerAttackDamageDisarmed;
	public int[] attackDamageOneDisarmed = new int[1];
	public int[] attackDamageTwoDisarmed = new int[1];	
	
	
	public int[] disarmedTurnStart = new int[6];
	//String didHumanCriticalMiss = "no";
	//String didComputerCriticalMiss = "no";
	String[] didHumanCriticalMiss = new String[] {"no", "no", "no", "no", "no", "no"};
		
	
	// SOME OF THESE MAY NEED TO BE AN ARRAY-CLASS:
	public int[] blessSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public int[] cureSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public int[] dodgeBlowSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public int[] mightyBlowSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public int[] hasteSpell = new int[] {2, 2, 2, 2, 2, 2, 2};//	{2, 2, 2, 2, 2, 2, 2};
	
	
	// FOR ORDERING PURPOSES IN TITLE:
	int firstsubscript;
	int secondsubscript;	
	
	
	//IS THIS WORKING NOW (W NEW onbackpressed CODE)????????????
	// Using variable because was getting null pointer if onbackpressed before rollfromleft was completed:
	String onBackPressedOk = "no";
	
	String isInvokingService = "true";	
	
	String isinitiativestarted = "no";	
	String isinitiativestartedinterrupted = "no";
	String issixsidedrolledforinitiative = "no";
	String aretheredoubles = "yes";
	
		
	String preventattackdamagediefromleaking = "on";
	String preventcureresultdiefromleaking = "on";	
	
	
	// ARRAYS?:
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
	
	
	//String iscomputerhasteused = "no";
	//String iscomputerblessrolled = "no";
	String ishasteused0 = "no";
	String ishasteused1 = "no";
	String ishasteused2 = "no";
	String ishasteused3 = "no";
	String ishasteused4 = "no";
	
	
	String rollDodgeFor = "null";
	
		
	String startGameNow ="no";	
	
	
	// SOME OF THESE MAY NEED TO BE AN ARRAY-CLASS:
	public String[] playerDeadYet = new String[] {"yes", "yes", "yes", "yes", "yes", "yes"}; // NEED 6?????????	
	//String playerDeadYet[] = {"yes", "yes", "yes", "yes", "yes", "yes"};
	public String[] canHasDisarmed = new String[] {"no", "no", "no", "no", "no", "no"}; // NEED 6?????????		
	
	
	//FOR onResume
	String isSixSidedReadyToBeRolled = "no";
	String isTwentySidedReadyToBeRolled = "no";
	String isInitiativeOver = "yes";
	String istitlestatsopen = "no";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		
		final MediaPlayer buttonSound = MediaPlayer.create(Host.this, R.raw.swordswing);
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		//startService(svc);
		
		MediaPlayerWrapper.play(Host.this, R.raw.buttonsound6);
		
		
		updateConversationHandler = new Handler();

		this.serverThread = new Thread(new ServerThread());
		this.serverThread.start();
		
		
		// USED THE FOLLOWING TO REMOVE TITLE BAR:
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// This will hide the system bar until user swipes up from bottom or down from top.		
		//getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		
		setContentView(R.layout.activity_main_activity2);		
		// For the little space between the action & attack button.
		getWindow().getDecorView().setBackgroundColor(Color.BLACK);		
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);		    			
		
		
		// Crashes if this is put up top.
		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
		
		
		//TextView startGameTextView = (TextView)findViewById(R.id.textviewstartgame);
		//startGameTextView.setTypeface(typeFace);		
		//startGameTextView.setText("Press Start to begin game...");
		
		final ImageButton startButton = (ImageButton) findViewById(R.id.imagebuttonstart);
		Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
		startButton.startAnimation(myFadeInAnimation);	
		
		
		TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
		playerNameTextView.setTypeface(typeFace);		
		playerNameTextView.setText(ArrayOfPlayers.player[5]);		
		
		/*FOR COMP/1 PLAYER NAME:
		TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
		computerNameTextView.setTypeface(typeFace);
		computerNameTextView.setText(ArrayOfPlayers.player[0]);
		computerNameTextView.setVisibility(View.INVISIBLE);
		*/
		
		
		ArrayOfHitPoints.hitpoints[5] = 20;//20		
		final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
		playerHitPointsTextView.setTypeface(typeFace);
		playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));		
		
		/*FOR COMP/1 PLAYER HP:
		ArrayOfHitPoints.hitpoints[0] = 20;//20
		final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
		computerHitPointsTextView.setTypeface(typeFace);
		computerHitPointsTextView.setVisibility(View.INVISIBLE);
		computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));		
		*/
		
		
		//TO MAKE HIT POINTS PULSATE, USE THIS:		
		final Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);
		playerHitPointsTextView.startAnimation(animPulsingAnimation);
		/*WAS FOR COMP HP:
		computerHitPointsTextView.startAnimation(animPulsingAnimation);
		*/
		
		
		ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
		ImageView crossedswords2 = (ImageView) findViewById(R.id.imageviewavatarleft2);
		ImageView stonedead2 = (ImageView) findViewById(R.id.imageviewavatarleft3);
		
		ImageView customImage = (ImageView) findViewById(R.id.imageviewavatarleft4);		
		if (getIntent().getExtras() != null) {			
				
			Intent intent = getIntent(); 
			String image_path= intent.getStringExtra("imageUri"); 
			Uri fileUri = Uri.parse(image_path);
			customImage.setImageURI(fileUri);
		}		
		
		
		if (ArrayOfAvatars.avatar[0].equals("computer")){
			crossedswords2.setVisibility(View.INVISIBLE);
			stonedead2.setVisibility(View.INVISIBLE);
			customImage.setVisibility(View.INVISIBLE);
		}
		else if (ArrayOfAvatars.avatar[0].equals("crossedswords")){
			computerAvatar.setVisibility(View.INVISIBLE);
			stonedead2.setVisibility(View.INVISIBLE);
			customImage.setVisibility(View.INVISIBLE);
		}
		else if (ArrayOfAvatars.avatar[0].equals("stonedead")){
			crossedswords2.setVisibility(View.INVISIBLE);
			computerAvatar.setVisibility(View.INVISIBLE);
			customImage.setVisibility(View.INVISIBLE);
		}
		else if (ArrayOfAvatars.avatar[0].equals("custom")){
			crossedswords2.setVisibility(View.INVISIBLE);
			stonedead2.setVisibility(View.INVISIBLE);
			computerAvatar.setVisibility(View.INVISIBLE);
		}
		
		/*FOR COMP/1 PLAYER AVATAR:
		ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
		clientAvatar.setVisibility(View.INVISIBLE);		
		*/
		
		
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
		
		
		
		final ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
		//titleBlankButton.setVisibility(View.INVISIBLE);
		
		final ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
		titleBlankButton.setVisibility(View.INVISIBLE);
		
		// Here to prevent premature rolling (use ".setEnabled(false);" in "resultsInitiative()"):
		final ImageView sixSidedBlank = (ImageView) findViewById(R.id.sixsidedanimation);
		sixSidedBlank.setVisibility(View.INVISIBLE);		
		
		final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
		twentySidedBlank.setVisibility(View.INVISIBLE);		
		
		
		
		unfoldLeftScroll();
		
  	  	
  	  	
        final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		
		centerscrolltext.setTypeface(typeFace);				
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);		
		
		
		final TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);	
		titletext.setTypeface(typeFace);		
		titletext.setVisibility(View.VISIBLE);				  		
		titletext.append("KtOG");
		
		
		final TextView titlelobbytext = (TextView) findViewById(R.id.textviewtitlelobbytext);
		titlelobbytext.setVisibility(View.INVISIBLE);
		
		TextView titleinitiativetext = (TextView) findViewById(R.id.textviewtitleinitiativetext);		
		titleinitiativetext.setVisibility(View.INVISIBLE);
		
		
		TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);		
		disarmedtextleft.setTypeface(typeFace);		
		disarmedtextleft.setVisibility(View.INVISIBLE);		
		disarmedtextleft.append("Disarmed");
		
		TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);		
		disarmedtextright.setTypeface(typeFace);		
		disarmedtextright.setVisibility(View.INVISIBLE);		
		disarmedtextright.append("Disarmed");
		
		View lineInSummaryTableLayout = (View) findViewById(R.id.line);
		lineInSummaryTableLayout.setVisibility(View.INVISIBLE);			
		
		
		preInitiativeTitle();//SHOULD REALLY BE CALLED "startTitle" (just using preInitiativeTitle() animation for "Lobby")		
		
		
		// THESE RUN METHODS ARE THREAD-SAFE, SUPPOSEDLY.
		final Handler h3 = new Handler(); // A handler is associated with the thread it is created in.
  	  	h3.postDelayed(new Runnable() {

  	  		@Override
  	  		public void run()
  	  		{  	  			
  	  			centerscrolltext.setVisibility(View.VISIBLE);
  	  			centerscrolltext.startAnimation(animAlphaText);
	  			centerscrolltext.append("\n" + "> Welcome, " + ArrayOfPlayers.player[5] + ".");  	  	  				  	  	  			
	  			/*TO TEST SCROLL BAR:
	  			centerscrolltext.append("\n" + "> NEW LINE TEST");
	  			centerscrolltext.append("\n" + "> NEW LINE TEST");
	  			centerscrolltext.append("\n" + "> NEW LINE TEST");
	  	  	  	*/		
  	  	  		final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
	  	  	  			titletext.setVisibility(View.INVISIBLE);
	  	  	  			
		  	  	  		//Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		  	  	  		titlelobbytext.setTypeface(typeFace);
	  		  			
		  				//titletext.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(100));
		  					
		  	  	  		titlelobbytext.setVisibility(View.VISIBLE);				  		
		  	  	  		titlelobbytext.append("Lobby");
		  				
		  	  	  		
		  	  	  		playerHitPointsTextView.clearAnimation();
		  	  	  		/*WAS FOR COMP HP:
		  	  	  		computerHitPointsTextView.clearAnimation();
		  	  	  		*/
		  				
		  				final Handler h4 = new Handler();
			  	  	  	h4.postDelayed(new Runnable() {
			  	  	  		
			  	  	  		@Override
			  	  	  		public void run() {				  				
			  	  	  			
			  	  	  			// Sets sixSidedBlank visible & enabled.
			  	  	  			//sixSidedRollFromLeft();
			  	  	  			
			  	  	  			startService(svc);
			  	  	  			
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
			  	  	  			centerscrolltext.startAnimation(animAlphaText);
			  	  	  			centerscrolltext.append("\n" + "> Waiting for opponents...");
			  		  			
			  	  	  			
				  		  		final Handler h5 = new Handler();
					  	  	  	h5.postDelayed(new Runnable() {
					  	  	  			
					  	  	  			// Does this thread help:?
						  	  	  		@Override
						  	  	  		public void run()
						  	  	  		{  	  			
						  	  	  			//startService(svc);
						  	  	  			
						  	  	  			/*
						  	  	  			ServerThread  NewServerThread= new ServerThread();
						  	  	  			NewServerThread.run();						  	  	  			
						  	  	  			*/
						  	  	  			
						  	  	  			
						  	  	  			/*
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
						  		  			*/
					  	  	  		}
					  	  	  	}, 500);
			  	  	  		}
			  	  	  	}, 1000);
		  	  	  	}
	  	  	  	}, 3000);//FINAGLING TO GET RIGHT (MAINLY 1ST TIME) - should be at least 4800?	  	  		  			
  	  		}
  	  	}, 2000);
  	  	
  	  	
  	  	//determineInitiative();
  	  	
  	  	
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
	          			
	          			istitlestatsopen = "yes";
	              		
	              		myInitiativeIsStarted();
	              		
	              		titleBlankButton.setEnabled(false);// HERE & BELOW BECUSE GETTING WEIRD BEHAVIOR WHEN BUTTON WAS HIT AN ODD NUMBER OF TIMES (EXCEPT THE FIRST TIME).
	              		
	              		/*
	              		if (numberOfPlayers == 2) {
	              		
		              		if (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) {
		              			
		              			firstsubscript = 5;
		              			secondsubscript = 0;
		              		}
		              		
		              		else if (ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) {
		              			
		              			firstsubscript = 0;
		              			secondsubscript = 5;
		              		}
	              		}
	              		*/
	              		
	              		final Handler h = new Handler();
	  		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  		  	  	  			
	  		  	  	  		@Override
	  			  	  	  	public void run() {  		  	  	  			
	  		  	  	  			
	    		  	  	  		final TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);    			  	  		    						
	    							
		    		  	  	  	titletext.setVisibility(View.INVISIBLE);
			  	  	  			
	    		  	  	  		//final TextView titlerulestext = (TextView) findViewById(R.id.textviewtitlerulestext);	    							
	    			  	  		//titlerulestext.setVisibility(View.VISIBLE);
			    		  	  	final TableLayout summaryTableLayout = (TableLayout) findViewById(R.id.summaryTable);
			    				summaryTableLayout.setVisibility(View.VISIBLE);
			    				//summaryTableLayout.setMovementMethod(new ScrollingMovementMethod());
			    				
			    				View lineInSummaryTableLayout = (View) findViewById(R.id.line);
			    				lineInSummaryTableLayout.setVisibility(View.VISIBLE);
			    				
			    				// NOTICE THESE HAVE setText:
			    				TextView statisticstextview = (TextView) findViewById(R.id.summarytext);
			    	  	  		statisticstextview.setTypeface(typeFace);
			    	  	  		statisticstextview.setText("Player Summary");
			    	  	  		statisticstextview.setVisibility(View.VISIBLE);
			    	  	  		
				    	  	  	TextView blanktextview = (TextView) findViewById(R.id.blanktext);
						  	  	blanktextview.setTypeface(typeFace);
						  	  	blanktextview.setText("");
						  	  	blanktextview.setVisibility(View.VISIBLE);
			    	  	  		
			    	  	  		TextView hitpointstextview = (TextView) findViewById(R.id.hitpointstext);
			    	  	  		hitpointstextview.setTypeface(typeFace);
			    	  	  		hitpointstextview.setText("HP");
			    	  	  		hitpointstextview.setVisibility(View.VISIBLE);
			    	  	  		
			    		  	  	TextView blesstextview = (TextView) findViewById(R.id.blesstext);
			    		  	  	blesstextview.setTypeface(typeFace);
			    		  	  	blesstextview.setText("Bless");
			    		  	  	blesstextview.setVisibility(View.VISIBLE);
			    	  	  		
			    		  	  	TextView curetextview = (TextView) findViewById(R.id.curetext);
			    		  	  	curetextview.setTypeface(typeFace);
			    		  	  	curetextview.setText("Cure");
			    		  	  	curetextview.setVisibility(View.VISIBLE);
			    	  	  		
			    		  	  	TextView dodgetextview = (TextView) findViewById(R.id.dodgetext);
			    		  	  	dodgetextview.setTypeface(typeFace);
			    		  	  	dodgetextview.setText("Dodge");
			    		  	  	dodgetextview.setVisibility(View.VISIBLE);
			    	  	  		
			    		  	  	TextView mightyblowtextview = (TextView) findViewById(R.id.mightyblowtext);
			    		  	  	mightyblowtextview.setTypeface(typeFace);
			    		  	  	mightyblowtextview.setText("MB");
			    		  	  	mightyblowtextview.setVisibility(View.VISIBLE);
			    		  	  	
			    		  	  	TextView hastetextview = (TextView) findViewById(R.id.hastetext);
			    		  	  	hastetextview.setTypeface(typeFace);
			    		  	  	hastetextview.setText("Haste");
			    		  	  	hastetextview.setVisibility(View.VISIBLE);			    		  	  	
			    		  	 
			    	  	  		
			    		  	  	TextView player1textview = (TextView) findViewById(R.id.player1);
			    		  	  	player1textview.setTypeface(typeFace);
			    		  	  	player1textview.setText(ArrayOfPlayers.player[firstsubscript]);
			    		  	  	player1textview.setVisibility(View.VISIBLE);
			    		  	  	
			    		  	  	TextView hitpointsplayer1textview = (TextView) findViewById(R.id.hitpointsplayer1);
			    		  	  	hitpointsplayer1textview.setTypeface(typeFace);
			    		  	  	String hitpointsplayer1String = Integer.toString(ArrayOfHitPoints.hitpoints[firstsubscript]);
			    		  	  	hitpointsplayer1textview.setText(hitpointsplayer1String);
			    		  		hitpointsplayer1textview.setVisibility(View.VISIBLE);
			    		  	  	
			    		  	  	TextView blessplayer1textview = (TextView) findViewById(R.id.blessplayer1);
			    		  	  	blessplayer1textview.setTypeface(typeFace);
			    		  	  	String blessplayer1String = Integer.toString(blessSpell[firstsubscript]);
			    		  	  	blessplayer1textview.setText(blessplayer1String);
			    		  	  	blessplayer1textview.setVisibility(View.VISIBLE);
			    		  	  	
			    		  	  	TextView cureplayer1textview = (TextView) findViewById(R.id.cureplayer1);
			    		  	  	cureplayer1textview.setTypeface(typeFace);
			    		  	  	String cureplayer1String = Integer.toString(cureSpell[firstsubscript]);
			    		  	  	cureplayer1textview.setText(cureplayer1String);
			    		  	  	cureplayer1textview.setVisibility(View.VISIBLE);
			    		  	  	
			    		  	  	TextView dodgeplayer1textview = (TextView) findViewById(R.id.dodgeplayer1);
			    		  	  	dodgeplayer1textview.setTypeface(typeFace);
			    		  	  	String dodgeplayer1String = Integer.toString(dodgeBlowSpell[firstsubscript]);
			    		  	  	dodgeplayer1textview.setText(dodgeplayer1String);
			    		  	  	dodgeplayer1textview.setVisibility(View.VISIBLE);
			    		  	  	
			    		  	  	TextView mightyblowplayer1textview = (TextView) findViewById(R.id.mightyblowplayer1);
			    		  	  	mightyblowplayer1textview.setTypeface(typeFace);
			    		  	  	String mightyblowplayer1String = Integer.toString(mightyBlowSpell[firstsubscript]);
			    		  	  	mightyblowplayer1textview.setText(mightyblowplayer1String);
			    		  	  	mightyblowplayer1textview.setVisibility(View.VISIBLE);
			    		  	  	
			    		  	  	TextView hasteplayer1textview = (TextView) findViewById(R.id.hasteplayer1);
			    		  	  	hasteplayer1textview.setTypeface(typeFace);
			    		  	  	String hasteplayer1String = Integer.toString(hasteSpell[firstsubscript]);
			    		  	  	hasteplayer1textview.setText(hasteplayer1String);
			    		  	  	hasteplayer1textview.setVisibility(View.VISIBLE);  	
			    		  	  	
			    		  	  	
			    		  	  	TextView player2textview = (TextView) findViewById(R.id.player2);
			    		  	  	player2textview.setTypeface(typeFace);
			    		  	  	player2textview.setText(ArrayOfPlayers.player[secondsubscript]);
			    		  	  	player2textview.setVisibility(View.VISIBLE);		  	  	
			    		  	  	
			    		  	  	TextView hitpoints2textview = (TextView) findViewById(R.id.hitpointsplayer2);
			    		  	  	hitpoints2textview.setTypeface(typeFace);
			    		  	  	String hitpoints2String = Integer.toString(ArrayOfHitPoints.hitpoints[secondsubscript]);
			    		  	  	hitpoints2textview.setText(hitpoints2String);
			    		  	  	hitpoints2textview.setVisibility(View.VISIBLE);
			    		  	  	
			    		  	  	TextView blessplayer2textview = (TextView) findViewById(R.id.blessplayer2);
			    		  	  	blessplayer2textview.setTypeface(typeFace);
			    		  	  	String blessplayer2String = Integer.toString(blessSpell[secondsubscript]);
			    		  	  	blessplayer2textview.setText(blessplayer2String);
			    		  	  	blessplayer2textview.setVisibility(View.VISIBLE);
			    		  	  	
			    		  	  	TextView cureplayer2textview = (TextView) findViewById(R.id.cureplayer2);
			    		  	  	cureplayer2textview.setTypeface(typeFace);
			    		  	  	String cureplayer2String = Integer.toString(cureSpell[secondsubscript]);
			    		  	  	cureplayer2textview.setText(cureplayer2String);
			    		  	  	cureplayer2textview.setVisibility(View.VISIBLE);
			    		  	  	
			    		  	  	TextView dodgeplayer2textview = (TextView) findViewById(R.id.dodgeplayer2);
			    		  	  	dodgeplayer2textview.setTypeface(typeFace);
			    		  	  	String dodgeplayer2String = Integer.toString(dodgeBlowSpell[secondsubscript]);
			    		  	  	dodgeplayer2textview.setText(dodgeplayer2String);
			    		  	  	dodgeplayer2textview.setVisibility(View.VISIBLE);
			    		  	  	
			    		  	  	TextView mightyblowplayer2textview = (TextView) findViewById(R.id.mightyblowplayer2);
			    		  	  	mightyblowplayer2textview.setTypeface(typeFace);
			    		  	  	String mightyblowplayer2String = Integer.toString(mightyBlowSpell[secondsubscript]);
			    		  	  	mightyblowplayer2textview.setText(mightyblowplayer2String);
			    		  	  	mightyblowplayer2textview.setVisibility(View.VISIBLE);
			    		  	  	
			    		  	  	TextView hasteplayer2textview = (TextView) findViewById(R.id.hasteplayer2);
			    		  	  	hasteplayer2textview.setTypeface(typeFace);
			    		  	  	String hasteplayer2String = Integer.toString(hasteSpell[secondsubscript]);
			    		  	  	hasteplayer2textview.setText(hasteplayer2String);
			    		  	  	hasteplayer2textview.setVisibility(View.VISIBLE);	    			  	  		
	    			  	  		
	    			  	  		
		    			  	  	final Handler h = new Handler();
		    		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		    		  	  	  			
		    		  	  	  		@Override
		    			  	  	  	public void run() {
		    		  	  	  			
			    		  	  	  		//titlerulestext.setVisibility(View.INVISIBLE);
			    		  	  	  		summaryTableLayout.setVisibility(View.INVISIBLE);
			    		  	  	  		
			    		  	  	  		View lineInSummaryTableLayout = (View) findViewById(R.id.line);
			    		  	  	  		lineInSummaryTableLayout.setVisibility(View.INVISIBLE);
		    		  	  	  			
			    		  	  	  		final TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);    			  	  		    						
		    							
			    			  	  		titletext.setVisibility(View.VISIBLE);
			    			  	  		
			    			  	  		titleBlankButton.setEnabled(true);
			    			  	  		
			    			  	  		istitlestatsopen = "no";
		    			  	  	  	}
		    		  	  	  	}, 11250);
	  			  	  	  	}
	  		  	  	  	}, 600);
	          		}         		           				
	          	}            	
			}            
		}); 	  	
  	  	
  	  	startButton.setOnClickListener(new View.OnClickListener() {
          @Override
			public void onClick(View v) {
			                    	
        	  buttonSound.start();      	
      	
        	  stopService(svc);
        	  
        	  
        	  startButton.setVisibility(View.INVISIBLE);        	  
        	  
        	  
        	  victoryDefeatAnimationForStartTransition();        	  
        	  
        	  
        	  final Handler h = new Handler();
        		h.postDelayed(new Runnable() {		  	  	  			
        	  			
        	  		@Override
        	  	  	public void run() {
        	  			
        	  			TextView titlelobbytext = (TextView) findViewById(R.id.textviewtitlelobbytext);
        	  			titlelobbytext.setVisibility(View.INVISIBLE); 
        	  			
        	  			
        	  			TextView titleinitiativetext = (TextView) findViewById(R.id.textviewtitleinitiativetext);
		  	  			
			  	  		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
			  	  		titleinitiativetext.setTypeface(typeFace);						
							
			  	  		titleinitiativetext.setVisibility(View.VISIBLE);  		  	  			
  		  	  							  		
  		  	  			titleinitiativetext.append("Initiative");
  		  	  			
  		  	  			
  		  	  			//gameEngine();
  		  	  			
  		  	  			
  		  	  			/*
	  		  	  		for (int counter = 0; counter < clientWorkers.size(); counter++) {
	  		        	  
	  		        	  Toast.makeText(Host.this, clientWorkers.get(counter), Toast.LENGTH_LONG).show();		               		
	  		  	  		}
	  		  	  		*/
  		  	  			
  		  	  			
  		  	  			//ABOVE METHOD IS FOLLOWING CODE:
  		  	  			/*
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
						  		  			
						  		  			
						  		  			String str = "StartInitiative";
						  		  			sendToAll(str);
						  		  			
						  		  			// FOR TESTING
						  		  			// String str2 = "ONE CLIENT TEST";
						  		  			// sendToClient1(str2);						  		  			
					  	  	  		}
					  	  	  	}, 750);
			  	  	  		}
			  	  	  	}, 1000);
			  	  	  	*/
  		  	  			
  		  	  			hostRollsInitiative();
  		  	  			
  		  	  			String str = "rollInitiative";
  		  	  			sendToAll(str);
  		  	  			
        	  	  	}
        	  	}, 713); // SHOULD BE AT LEAST 675?       	      	        				
			}
		});
  	  	
  	  	
  	  	chatBlankButton.setOnClickListener(new View.OnClickListener() {
          @Override
			public void onClick(View v) {
        	  
        	  //Toast.makeText(Host.this, "CHAT TEST", Toast.LENGTH_LONG).show();
        	  
        	  
        	  
        	  AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);

		    	alert.setTitle("Chat");
		    	alert.setMessage("Enter Message");

		    	// Set an EditText view to get user input:
		    	final EditText input = new EditText(Host.this);
		    	input.setSingleLine(true);
		    	// Limits to 1 line (clicking return is like clicking "ok".)
		    	alert.setView(input);
		    	// Limits the number of characters entered to 50.
		    	InputFilter[] FilterArray = new InputFilter[1];
		    	FilterArray[0] = new InputFilter.LengthFilter(50);
		    	input.setFilters(FilterArray);
		    	
		    	// THIS WILL GET KEYBOARD AUTOMATICALLY FOR S4:
		    	/*
		    	input.requestFocus();
		        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		        */
		    	
		    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int whichButton) {		    		
		    		
		    		
		    		final String str = input.getText().toString();
		    		sendToAll(str);
		    		
		    		
		    		//final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		        	
		        	runOnUiThread(new Runnable() {
		      	  	    @Override
		      	  	    public void run() {
		      	  	    	
		    	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		    	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		    	  			
		    	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		    	  			centerscrolltext.setTypeface(typeFace);		    	  			
		    	  			
		      	  	    	
      	  	    			centerscrolltext.setVisibility(View.VISIBLE);
    				  		//centerscrolltext.startAnimation(animAlphaText);
    						centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + ": " + str);		    	  	  	  	
		      	  	    }
		      	  	});
		    		
		    		/*
		    		try {		  	  	  		
		  	  	  		
		    			String str = input.getText().toString();
		  	  			PrintWriter out = new PrintWriter(new BufferedWriter(
		  	  					new OutputStreamWriter(socket.getOutputStream())),
		  	  					true);
		  	  			out.println(ArrayOfPlayers.player[0] + ": " + str);
		  	  			//NEW:
		  	  			//out.flush();
		  	  			//out.close();
		  	  			
		  	  		} catch (UnknownHostException e) {
		  	  			e.printStackTrace();
		  	  		} catch (IOException e) {
		  	  			e.printStackTrace();
		  	  		} catch (Exception e) {
		  	  			e.printStackTrace();
		  	  		}
		  	  		*/
		    	}
		    	});

		    	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    	  public void onClick(DialogInterface dialog, int whichButton) {
		    	    // Canceled.
		    	  }
		    	});
		    	
		    	alert.show();
          	
          	            	
			}            
  	  	});  	  	
  	  	
  	  	
		sixSidedBlank.setOnTouchListener(new OnSixSidedSwipeTouchListener(Host.this) {
			
		    /*
			public void onSwipeTop() {
		        Toast.makeText(MainActivity2.this, "top", Toast.LENGTH_SHORT).show();		        
		    }
		    */			
			
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Host.this);
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
				
					if (ArrayOfInitiative.initiative[5] == 1) {
						
						sixSidedRollFromCenterToRight1();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[5] == 2) {
						
						sixSidedRollFromCenterToRight2();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[5] == 3) {
						
						sixSidedRollFromCenterToRight3();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[5] == 4) {
						
						sixSidedRollFromCenterToRight4();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[5] == 5) {
						
						sixSidedRollFromCenterToRight5();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[5] == 6) {
						
						sixSidedRollFromCenterToRight6();
						
						initiativeResults();
					}				
				}
				
				
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
					
					if (ArrayOfInitiative.initiative[5] == 1) {
						
						sixSidedRollFromCenterToLeft1();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[5] == 2) {
						
						sixSidedRollFromCenterToLeft2();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[5] == 3) {
						
						sixSidedRollFromCenterToLeft3();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[5] == 4) {
						
						sixSidedRollFromCenterToLeft4();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[5] == 5) {
						
						sixSidedRollFromCenterToLeft5();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[5] == 6) {
						
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
			
		    }
		    /*
		    public void onSwipeBottom() {
		        Toast.makeText(MainActivity2.this, "bottom", Toast.LENGTH_SHORT).show();
		    }
		    */
		});
		
		
		// USE android:background="@drawable/(SOME PNG)" TO SPECIFY AREA ON SCREEN ??
		twentySidedBlank.setOnTouchListener(new OnTwentySidedSwipeTouchListener(Host.this) {
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
				else if (ArrayOfAttackResult.attackResult[0] == 2) {
					twentySidedRollFromCenterToRight2();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 3) {
					twentySidedRollFromCenterToRight3();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 4) {
					twentySidedRollFromCenterToRight4();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 5) {
					twentySidedRollFromCenterToRight5();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 6) {
					twentySidedRollFromCenterToRight6();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 7) {
					twentySidedRollFromCenterToRight7();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 8) {
					twentySidedRollFromCenterToRight8();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 9) {
					twentySidedRollFromCenterToRight9();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 10) {
					twentySidedRollFromCenterToRight10();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 11) {
					twentySidedRollFromCenterToRight11();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 12) {
					twentySidedRollFromCenterToRight12();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 13) {
					twentySidedRollFromCenterToRight13();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 14) {
					twentySidedRollFromCenterToRight14();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 15) {
					twentySidedRollFromCenterToRight15();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 16) {
					twentySidedRollFromCenterToRight16();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 17) {
					twentySidedRollFromCenterToRight17();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 18) {
					twentySidedRollFromCenterToRight18();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 19) {
					twentySidedRollFromCenterToRight19();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 20) {
					twentySidedRollFromCenterToRight20();
				}				
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		if (isattackrolled.equals("yes")) {
							attackResults();
						}
		  	  	  		else if (isdisarmwithblessrolled.equals("yes")) {					
							disarmWithBlessResults();
						}
		  	  	  		else if (isdisarmnoblessrolled.equals("yes")) {					
							disarmNoBlessResults();
						}
		  	  	  		else if (isblessrolled.equals("yes")) {
							blessResults();
						}
		  	  	  		else if (iscriticalmissrolled.equals("yes")) {
							criticalMissResults();
						}
		  	  	  		else if (iscriticalmissloseweaponrolled.equals("yes")) {
							criticalMissLoseWeaponResults();
						}
		  	  	  	}
	  	  	  	}, 50);								
		    }
		    
		    public void onSwipeLeft() {		    		  				
					
				twentySidedWobbleStop();				
				
				if (ArrayOfAttackResult.attackResult[0] == 1) {
					twentySidedRollFromCenterToLeft1();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 2) {
					twentySidedRollFromCenterToLeft2();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 3) {
					twentySidedRollFromCenterToLeft3();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 4) {
					twentySidedRollFromCenterToLeft4();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 5) {
					twentySidedRollFromCenterToLeft5();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 6) {
					twentySidedRollFromCenterToLeft6();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 7) {
					twentySidedRollFromCenterToLeft7();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 8) {
					twentySidedRollFromCenterToLeft8();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 9) {
					twentySidedRollFromCenterToLeft9();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 10) {
					twentySidedRollFromCenterToLeft10();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 11) {
					twentySidedRollFromCenterToLeft11();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 12) {
					twentySidedRollFromCenterToLeft12();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 13) {
					twentySidedRollFromCenterToLeft13();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 14) {
					twentySidedRollFromCenterToLeft14();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 15) {
					twentySidedRollFromCenterToLeft15();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 16) {
					twentySidedRollFromCenterToLeft16();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 17) {
					twentySidedRollFromCenterToLeft17();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 18) {
					twentySidedRollFromCenterToLeft18();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 19) {
					twentySidedRollFromCenterToLeft19();
				}
				else if (ArrayOfAttackResult.attackResult[0] == 20) {
					twentySidedRollFromCenterToLeft20();
				}				
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		if (isattackrolled.equals("yes")) {
							attackResults();
						}
		  	  	  		else if (isdisarmwithblessrolled.equals("yes")) {					
							disarmWithBlessResults();
						}
		  	  	  		else if (isdisarmnoblessrolled.equals("yes")) {					
							disarmNoBlessResults();
						}
		  	  	  		else if (isblessrolled.equals("yes")) {
							blessResults();
						}
		  	  	  		else if (iscriticalmissrolled.equals("yes")) {
							criticalMissResults();
						}
		  	  	  		else if (iscriticalmissloseweaponrolled.equals("yes")) {
							criticalMissLoseWeaponResults();
						}
		  	  	  	}
	  	  	  	}, 50);			
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
	 * Android-Related Methods***********************************************************************************
	 * 
	 * 
	 * 
	 */
	
	
	// ONSTOP OR ONDESTROY???????
		/*
		@Override
		protected void onStop() {
			super.onStop();
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		*/
		
	@Override
	protected void onDestroy() {
		
		String rollOff = "no";
		String rollOff305 = "no";
		String rollOff301 ="no";
		String rollOff315 ="no";
		
		ArrayOfPlayers.player = new String[6];
		ArrayOfInitiative.initiative = new int[6];
		
		initiativeRolled = new String[] {"no", "no", "no", "no", "no", "no"};
		
		//super.onDestroy();
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		
		if (serverSocket != null) {
			try {
				
				serverSocket.close();
				
				stopService(svc);
				
				android.os.Process.killProcess(android.os.Process.myPid());
			    
			    super.onDestroy();				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		super.onDestroy();
	}
		
	public void onBackPressed() {
		
		String rollOff = "no";
		String rollOff305 = "no";
		String rollOff301 ="no";
		String rollOff315 ="no";
		
		ArrayOfPlayers.player = new String[6];
		ArrayOfInitiative.initiative = new int[6];
		
		initiativeRolled = new String[] {"no", "no", "no", "no", "no", "no"};
		
		
		AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);

		alert.setTitle("KtOG");
		alert.setMessage("Are you sure you want to exit?");

		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				dialog.dismiss();
				
				stopService(svc);

				Intent intent = new Intent(Host.this, MainActivity1.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // this combination of flags would start a new instance even if the instance of same Activity exists.
				intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				finish();
				startActivity(intent);			
			}
		});

		alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				dialog.dismiss();

				// hideNavigation();
			}
		});
		alert.show();

		// Toast.makeText(MainActivity2.this,"onBackPressed WORKING!!!!", Toast.LENGTH_SHORT).show();
	}
	
	
	@Override
	public void onResume() {
	    super.onResume();
	    
	    
	    //unfoldScrolls();
	    
	    
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
	    if (istitlestatsopen.equals("yes")) {
	    	
	    	ImageView img = (ImageView)findViewById(R.id.titleanimation);
	    	img.setBackgroundResource(R.anim.titleanimationnoinitiative);
	    	img.bringToFront();
	    	
	    	TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);			
			//titletext.bringToFront();			
			titletext.setVisibility(View.VISIBLE);
			
			//TextView titlerulestext = (TextView) findViewById(R.id.textviewtitlerulestext);	
			//titlerulestext.setVisibility(View.INVISIBLE);
			
			TableLayout summaryTableLayout = (TableLayout) findViewById(R.id.summaryTable);
			summaryTableLayout.setVisibility(View.INVISIBLE);
			//summaryTableLayout.setMovementMethod(new ScrollingMovementMethod());
			
			View lineInSummaryTableLayout = (View) findViewById(R.id.line);
	  	  	lineInSummaryTableLayout.setVisibility(View.INVISIBLE);
			
	    	
	    	istitlestatsopen = "no";
	    }	    
	}
	
	
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
			  	final Animation animAlphaTextRepeat = AnimationUtils.loadAnimation(Host.this, R.anim.anim_alpha_text_repeat);
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
			  	final Animation animAlphaTextRepeat = AnimationUtils.loadAnimation(Host.this, R.anim.anim_alpha_text_repeat);
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
	public void unfoldLeftScroll() {		
		
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// Setting up scroll frame animation.
				ImageView img = (ImageView)findViewById(R.id.scrollanimation);
				img.setBackgroundResource(R.anim.scrollanimationleftup);
			
				// Get the background, which has been compiled to an AnimationDrawable object.
				AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
							
				// Start the animation.
				frameAnimation.stop();
				frameAnimation.start();
	  	    }
  		});	
	}
	
	public void unfoldRightScroll() {	
		
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// Setting up scroll frame animation.
				ImageView img = (ImageView)findViewById(R.id.scrollanimation);
				img.setBackgroundResource(R.anim.scrollanimationrightup);
			
				// Get the background, which has been compiled to an AnimationDrawable object.
				AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
							
				// Start the animation.
				frameAnimation.stop();
				frameAnimation.start();
	  	    }
  		});		
	}
		
	/*
	public void startTitle() {	
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				ImageView img = (ImageView)findViewById(R.id.titleanimation);		
				img.setBackgroundResource(R.anim.titleanimationpreinitiative);		  	  
		  	  	
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();	  	  	
	  	    }
  		});	
	}
	*/
	
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
	
	public void victoryDefeatAnimationForStartTransition() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {		
					
				// CLOSING ON SUMMARY TABLE
				ImageView img = (ImageView)findViewById(R.id.titleanimation);		
				img.setBackgroundResource(R.anim.victorydefeatanimation);		  	  
		  	  	
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();				
		  	  	
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();			
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
	  	    }
  		});	
	}
	
	
	/*
	 * 
	 * 
	 * 
	 * Other Animations*******************************************************************************************
	 * 
	 * 
	 * 
	 */	
	
	
	public void hasteGraphic() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
		
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
				
				Animation a = AnimationUtils.loadAnimation(Host.this, R.anim.textscaletobig);					  	  	  	
			  	  	
		  	  	TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  	  	
		  	  	hasteGraphic.setVisibility(View.VISIBLE);
			  	hasteGraphic.bringToFront();
		  	  	
		  	  	hasteGraphic.setTypeface(typeFace);
		  	  	hasteGraphic.setText("Haste");  	  	
		  	  	
		  	  	hasteGraphic.clearAnimation();
		  	  	hasteGraphic.startAnimation(a);
		  		
		  		MediaPlayerWrapper.play(Host.this, R.raw.badonkshort);
		  		
		  		
				final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {
		  	  			
			  	  		Animation a = AnimationUtils.loadAnimation(Host.this, R.anim.textscaletosmall);						  	  	  	
			  	  	  	
				  	  	final TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
				  	  	hasteGraphic.setTypeface(typeFace);
				  	  	hasteGraphic.setText("Haste");
			  	  	  	
				  	  	hasteGraphic.clearAnimation();
				  	  	hasteGraphic.startAnimation(a);
		  	  		}	  	  		
		  	  	}, 3000);
			}
		});
	}	
	
	public void cureGraphic() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
		
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
				
				Animation a = AnimationUtils.loadAnimation(Host.this, R.anim.textscaletobig);					  	  	  	
			  	  	
		  	  	TextView cureGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  	  	
		  	  	cureGraphic.setVisibility(View.VISIBLE);
			  	cureGraphic.bringToFront();
		  	  	
		  	  	cureGraphic.setTypeface(typeFace);
		  	  	cureGraphic.setText(" Cure");  	  	
		  	  	
		  	  	cureGraphic.clearAnimation();
		  	  	cureGraphic.startAnimation(a);
		  		
		  		MediaPlayerWrapper.play(Host.this, R.raw.badonkshort);
		  		
		  		
				final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {
		  	  			
			  	  		Animation a = AnimationUtils.loadAnimation(Host.this, R.anim.textscaletosmall);						  	  	  	
			  	  	  	
				  	  	final TextView cureGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
				  	  	cureGraphic.setTypeface(typeFace);
				  	  	cureGraphic.setText(" Cure");
			  	  	  	
				  	  	cureGraphic.clearAnimation();
				  	  	cureGraphic.startAnimation(a);
		  	  		}	  	  		
		  	  	}, 3000);
			}
		});
	}
	
	public void blessGraphic() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
		
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
				
				Animation a = AnimationUtils.loadAnimation(Host.this, R.anim.textscaletobig);					  	  	  	
			  	  	
		  	  	TextView blessGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  		
		  	  	blessGraphic.setVisibility(View.VISIBLE);
				blessGraphic.bringToFront();
		  	  	
		  	  	blessGraphic.setTypeface(typeFace);
		  		blessGraphic.setText("Bless");  		
		  	  	
		  	  	blessGraphic.clearAnimation();
		  		blessGraphic.startAnimation(a);
		  		
		  		MediaPlayerWrapper.play(Host.this, R.raw.badonkshort);
		  		
		  		
				final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {
		  	  			
			  	  		Animation a = AnimationUtils.loadAnimation(Host.this, R.anim.textscaletosmall);						  	  	  	
			  	  	  	
				  	  	final TextView blessGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  	  	  		blessGraphic.setTypeface(typeFace);
		  	  	  		blessGraphic.setText("Bless");
		  	  	  		
			  	  	  	blessGraphic.clearAnimation();
		  	  	  		blessGraphic.startAnimation(a);
		  	  		}	  	  		
		  	  	}, 3000);
			}
		});
	}
	
	public void dodgeGraphic() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
		
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
				
				Animation a = AnimationUtils.loadAnimation(Host.this, R.anim.textscaletobig);					  	  	  	
			  	  	
		  	  	TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
		  	  	
		  	  	dodgeGraphic.setVisibility(View.VISIBLE);
			  	dodgeGraphic.bringToFront();
		  	  	
		  	  	dodgeGraphic.setTypeface(typeFace);
		  	  	dodgeGraphic.setText("Dodge");  	  	
		  	  	
		  	  	dodgeGraphic.clearAnimation();
		  	  	dodgeGraphic.startAnimation(a);
		  		
		  		MediaPlayerWrapper.play(Host.this, R.raw.badonkshort);
		  		
		  		
				final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {
		  	  			
			  	  		Animation a = AnimationUtils.loadAnimation(Host.this, R.anim.textscaletosmall);						  	  	  	
			  	  	  	
				  	  	final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
				  	  	dodgeGraphic.setTypeface(typeFace);
				  	  	dodgeGraphic.setText("Dodge");
			  	  	  	
				  	  	dodgeGraphic.clearAnimation();
				  	  	dodgeGraphic.startAnimation(a);
		  	  		}	  	  		
		  	  	}, 3000);
			}
		});
	}
	
	public void mightyBlowGraphic() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
		
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
				
				Animation a = AnimationUtils.loadAnimation(Host.this, R.anim.textscaletobig);					  	  	  	
			  	  	
		  	  	TextView mightyBlowGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
		  	  	
		  	  	mightyBlowGraphic.setVisibility(View.VISIBLE);
			  	mightyBlowGraphic.bringToFront();
		  	  	
		  	  	mightyBlowGraphic.setTypeface(typeFace);
		  	  	mightyBlowGraphic.setText("Mighty Blow");  	  	
		  	  	
		  	  	mightyBlowGraphic.clearAnimation();
		  	  	mightyBlowGraphic.startAnimation(a);
		  		
		  		MediaPlayerWrapper.play(Host.this, R.raw.badonkshort);
		  		
		  		
				final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {
		  	  			
			  	  		Animation a = AnimationUtils.loadAnimation(Host.this, R.anim.textscaletosmall);						  	  	  	
			  	  	  	
				  	  	final TextView mightyBlowGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
				  	  	mightyBlowGraphic.setTypeface(typeFace);
				  	  	mightyBlowGraphic.setText("Mighty Blow");
			  	  	  	
				  	  	mightyBlowGraphic.clearAnimation();
				  	  	mightyBlowGraphic.startAnimation(a);
		  	  		}	  	  		
		  	  	}, 3000);
			}
		});
	}
	
	public void criticalHitGraphic() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
				
		
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
				
				Animation a = AnimationUtils.loadAnimation(Host.this, R.anim.textscaletobig);					  	  	  	
			  	  	
		  	  	TextView criticalHitGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
		  	  	
		  	  	criticalHitGraphic.setVisibility(View.VISIBLE);
			  	criticalHitGraphic.bringToFront();
		  	  	
		  	  	criticalHitGraphic.setTypeface(typeFace);
		  	  	criticalHitGraphic.setText("Critical     Hit");  	  	
		  	  	
		  	  	criticalHitGraphic.clearAnimation();
		  	  	criticalHitGraphic.startAnimation(a);
		  		
		  		MediaPlayerWrapper.play(Host.this, R.raw.badonkshort);
		  		
		  		
				final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {
		  	  			
			  	  		Animation a = AnimationUtils.loadAnimation(Host.this, R.anim.textscaletosmall);						  	  	  	
			  	  	  	
				  	  	final TextView criticalHitGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
				  	  	criticalHitGraphic.setTypeface(typeFace);
				  	  	criticalHitGraphic.setText("Critical     Hit");
			  	  	  	
				  	  	criticalHitGraphic.clearAnimation();
				  	  	criticalHitGraphic.startAnimation(a);
		  	  		}	  	  		
		  	  	}, 3000);
			}
		});
	}	
	
	public void criticalMissGraphic() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
				
		
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
				
				Animation a = AnimationUtils.loadAnimation(Host.this, R.anim.textscaletobig);					  	  	  	
			  	  	
		  	  	TextView criticalMissGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
		  	  	
		  	  	criticalMissGraphic.setVisibility(View.VISIBLE);
			  	criticalMissGraphic.bringToFront();
		  	  	
		  	  	criticalMissGraphic.setTypeface(typeFace);
		  	  	criticalMissGraphic.setText("Critical Miss");  	  	
		  	  	
		  	  	criticalMissGraphic.clearAnimation();
		  	  	criticalMissGraphic.startAnimation(a);
		  		
		  		MediaPlayerWrapper.play(Host.this, R.raw.badonkshort);
		  		
		  		
				final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {
		  	  			
			  	  		Animation a = AnimationUtils.loadAnimation(Host.this, R.anim.textscaletosmall);						  	  	  	
			  	  	  	
				  	  	final TextView criticalMissGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
				  	  	criticalMissGraphic.setTypeface(typeFace);
				  	  	criticalMissGraphic.setText("Critical Miss");
			  	  	  	
				  	  	criticalMissGraphic.clearAnimation();
				  	  	criticalMissGraphic.startAnimation(a);
		  	  		}	  	  		
		  	  	}, 3000);
			}
		});
	}	
	
	public void stopGraphics() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
		
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
	
	
	public void sixSidedWobbleStart() {
		final ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
		final Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.wobblesixsided);
		img.setAnimation(shake);
	}
	
	public void sixSidedWobbleStop() {
		final ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
		
		img.clearAnimation();
	}
	
	public void sixSidedRollFromLeft() {	  	
		
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
				    	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
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
	
	public void sixSidedRollFromCenterToLeft1() {	

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation1);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();		  	  	
					
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
			  	  	String str = "1computerSixSidedRollFromLeft1";
					sendToAllClients(str);		  	  		
		  	  	}				
	  	    }
  		}); 	  	
	}
	
	public void sixSidedRollFromCenterToLeft2() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation2);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "2computerSixSidedRollFromLeft2";
		  	  		sendToAllClients(str);
		  	  	}			  	  	
	  	    }
  		}); 	  	
	}

	public void sixSidedRollFromCenterToLeft3() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation3);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "3computerSixSidedRollFromLeft3";
		  	  		sendToAllClients(str);
		  	  	}
	  	    }
  		}); 	  	
	}
	
	public void sixSidedRollFromCenterToLeft4() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation4);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "4computerSixSidedRollFromLeft4";
		  	  		sendToAllClients(str);
		  	  	}
	  	    }
  		});		
	}
	
	public void sixSidedRollFromCenterToLeft5() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation5);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "5computerSixSidedRollFromLeft5";
		  	  		sendToAllClients(str);
		  	  	}
	  	    }
  		});
	}
	
	public void sixSidedRollFromCenterToLeft6() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertoleftanimation6);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "6computerSixSidedRollFromLeft6";
		  	  		sendToAllClients(str);
		  	  	}
	  	    }
  		});
	}
	
	
	public void sixSidedRollFromCenterToRight1() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation1);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "1computerSixSidedRollFromLeft1";
		  	  		sendToAllClients(str);
		  	  	}
	  	    }
  		});
	}
	
	public void sixSidedRollFromCenterToRight2() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation2);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "2computerSixSidedRollFromLeft2";
		  	  		sendToAllClients(str);
		  	  	}
	  	    }
  		});
	}
	
	public void sixSidedRollFromCenterToRight3() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation3);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "3computerSixSidedRollFromLeft3";
		  	  		sendToAllClients(str);
		  	  	}
	  	    }
  		});
	}
	
	public void sixSidedRollFromCenterToRight4() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation4);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "4computerSixSidedRollFromLeft4";
		  	  		sendToAllClients(str);
		  	  	}
	  	    }
  		});
	}
	
	public void sixSidedRollFromCenterToRight5() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation5);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "5computerSixSidedRollFromLeft5";
		  	  		sendToAllClients(str);
		  	  	}
	  	    }
  		});
	}
	
	public void sixSidedRollFromCenterToRight6() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.sixsidedrollfromcentertorightanimation6);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "6computerSixSidedRollFromLeft6";
		  	  		sendToAllClients(str);
		  	  	}
	  	    }
  		});
	}
	
	
	public void computerSixSidedRollFromLeft1() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
				
				
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.computersixsidedrollfromleft1);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void computerSixSidedRollFromLeft2() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
				
				
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.computersixsidedrollfromleft2);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void computerSixSidedRollFromLeft3() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
				
				
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.computersixsidedrollfromleft3);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void computerSixSidedRollFromLeft4() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
				
				
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.computersixsidedrollfromleft4);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void computerSixSidedRollFromLeft5() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
				
				
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.computersixsidedrollfromleft5);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});
	}
	
	public void computerSixSidedRollFromLeft6() {	
	  	
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
				
				
				ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);		
				img.setBackgroundResource(R.anim.computersixsidedrollfromleft6);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});  	  	
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
				    	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
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
	
	public void twentySidedRollFromCenterToLeft1() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.twentysidedrollfromcentertoleftanimation1);
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();		
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "cmputerTwentySidedRollFromLeft1";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft2";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft3";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft4";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft5";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft6";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft7";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft8";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft9";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft10";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft11";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft12";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft13";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft14";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft15";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft16";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft17";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft18";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft19";
				sendToAllClients(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "cmputerTwentySidedRollFromLeft20";
				sendToAllClients(str);
	  	    }
  		});
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "cmputerTwentySidedRollFromLeft1";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft2";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft3";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft4";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft5";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft6";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft7";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft8";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft9";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft10";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft11";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft12";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft13";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft14";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft15";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft16";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft17";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft18";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft19";
				sendToAllClients(str);
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
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "cmputerTwentySidedRollFromLeft20";
				sendToAllClients(str);
	  	    }
  		});
	}
	
	public void computerTwentySidedRollFromLeft1() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
				img.setBackgroundResource(R.anim.computertwentysidedrollfromleft1);
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Host.this, R.raw.dierolling3b);
		  	  	
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
	
	public String waitToSetInterruptVariable() {
		
		final Handler h1 = new Handler();
	  	  	h1.postDelayed(new Runnable() {

	  	  		public void run()
	  	  		{				  	  	  			
		  	  		isinitiativestartedinterrupted = "no";		  			
	  	  		}
	  	  	}, 12400);
	  	  	
	  	  	return isinitiativestartedinterrupted;	
	}
	
	
	public void  determineInitiative() {
		
		int result = (int)(Math.random()*6)+1;
        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
        //same as: (int) Math.ceil(Math.random()*6); ?
		ArrayOfInitiative.initiative[5] = result;					
	}
	
	public void initiativeResults() {
		
		isSixSidedReadyToBeRolled = "no";
		
		//Toast.makeText(MainActivity2.this,"At method determineDoubles().", Toast.LENGTH_SHORT).show();
		
		// Here to prevent pre-mature (BUT STILL SEE ?) rolling:
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
  	  		public void run() {  	  			
	  	  		
  	  			//centerscrolltext.bringToFront();
  	  			
  	  			centerscrolltext.setVisibility(View.VISIBLE);
	  	  		centerscrolltext.startAnimation(animAlphaText);
	  			centerscrolltext.append("\n" + "> You roll " + ArrayOfInitiative.initiative[5] + ".");
	  			
	  			
	  			initiativeRolled[5] = "yes";
	  			
	  			
	  			final Handler h2 = new Handler();
		  	  	h2.postDelayed(new Runnable() {

		  	  		@Override
		  	  		public void run() {
		  	  			
			  	  		String str = ArrayOfPlayers.player[5] + " rolls a " + ArrayOfInitiative.initiative[5] + ".";
						sendToAllClients(str);
		  	  			
		  	  			/*
		  	  			centerscrolltext.setVisibility(View.VISIBLE);
			  	  		centerscrolltext.startAnimation(animAlphaText);
			  			centerscrolltext.append("\n" + "> Players are rolling for initiative...");
		  	  			*/	  	  			
			  			
			  			
			  			playerCardStopFadeInFadeOut();
			  			//computerCardStartFadeInFadeOut();			  			
			  			
			  			
			  			checkInitiativeResults();
			  			
			  			
			  			//determineDoubles();			  			
		  	  		}
		  	  	}, 2000);//WAS 4000
  	  		}
  	  	}, 1250);		 		
	}
	
	//Determines when all players have rolled for initiative then calls for method to determine doubles:
	public void checkInitiativeResults() {
		
		if (initiativeRolled[5].equals("yes")) {
			
			if (clientWorkers.size() == 1) {
				if (initiativeRolled[0].equals("yes")) {					
					
					determineDoubles();
				}
			}			
			else if (clientWorkers.size() == 2) {
				if (initiativeRolled[0].equals("yes") && initiativeRolled[1].equals("yes")) {
					
					determineDoubles();
				}
			}
			else if (clientWorkers.size() == 3) {
				if (initiativeRolled[0].equals("yes") && initiativeRolled[1].equals("yes") && initiativeRolled[2].equals("yes")) {

					determineDoubles();
				}			
			}
			else if (clientWorkers.size() >= 4) {
				if (initiativeRolled[0].equals("yes") && initiativeRolled[1].equals("yes") && initiativeRolled[2].equals("yes") && initiativeRolled[3].equals("yes")) {

					determineDoubles();
				}
				
			}
			/*
			else if (clientWorkers.size() == 5) {
				if (initiativeRolled[0].equals("yes") && initiativeRolled[1].equals("yes") && initiativeRolled[2].equals("yes") && initiativeRolled[3].equals("yes") && initiativeRolled[4].equals("yes")) {

					determineDoubles();
				}
			}
			*/
		}
		
		else {
			
			if (clientWorkers.size() == 1) {
				if (initiativeRolled[5].equals("no") || initiativeRolled[0].equals("no")) {					
					
					String str = "Waiting for players to roll...";
					sendToAllClients(str);
					
					runOnUiThread(new Runnable() {
		      	  	    @Override
		      	  	    public void run() {
		      	  	    	
		    	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		    	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		    	  			
		    	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		    	  			centerscrolltext.setTypeface(typeFace);		    	  			
		    	  			
		      	  	    	
			  	    		centerscrolltext.setVisibility(View.VISIBLE);
					  		//centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "Waiting for players to roll...");		    	  	  	  	
		      	  	    }
		      	  	});
				}
			}			
			else if (clientWorkers.size() == 2) {
				if (initiativeRolled[5].equals("no") || initiativeRolled[0].equals("no") || initiativeRolled[1].equals("no")) {
					
					String str = "Waiting for players to roll...";
					sendToAllClients(str);
					
					runOnUiThread(new Runnable() {
		      	  	    @Override
		      	  	    public void run() {
		      	  	    	
		    	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		    	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		    	  			
		    	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		    	  			centerscrolltext.setTypeface(typeFace);		    	  			
		    	  			
		      	  	    	
			  	    		centerscrolltext.setVisibility(View.VISIBLE);
					  		//centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "Waiting for players to roll...");		    	  	  	  	
		      	  	    }
		      	  	});
				}
			}
			else if (clientWorkers.size() == 3) {
				if (initiativeRolled[5].equals("no") || initiativeRolled[0].equals("no") || initiativeRolled[1].equals("no") || initiativeRolled[2].equals("no")) {

					String str = "Waiting for players to roll...";
					sendToAllClients(str);
					
					runOnUiThread(new Runnable() {
		      	  	    @Override
		      	  	    public void run() {
		      	  	    	
		    	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		    	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		    	  			
		    	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		    	  			centerscrolltext.setTypeface(typeFace);		    	  			
		    	  			
		      	  	    	
			  	    		centerscrolltext.setVisibility(View.VISIBLE);
					  		//centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "Waiting for players to roll...");		    	  	  	  	
		      	  	    }
		      	  	});
				}			
			}
			else if (clientWorkers.size() == 4) {
				if (initiativeRolled[5].equals("no") || initiativeRolled[0].equals("no") || initiativeRolled[1].equals("no") || initiativeRolled[2].equals("no") || initiativeRolled[3].equals("no")) {

					String str = "Waiting for players to roll...";
					sendToAllClients(str);
					
					runOnUiThread(new Runnable() {
		      	  	    @Override
		      	  	    public void run() {
		      	  	    	
		    	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		    	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		    	  			
		    	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		    	  			centerscrolltext.setTypeface(typeFace);		    	  			
		    	  			
		      	  	    	
			  	    		centerscrolltext.setVisibility(View.VISIBLE);
					  		//centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "Waiting for players to roll...");		    	  	  	  	
		      	  	    }
		      	  	});
				}
				
			}
			/*
			else if (clientWorkers.size() == 5) {
				if (initiativeRolled[5].equals("no") || initiativeRolled[0].equals("no") || initiativeRolled[1].equals("no") || initiativeRolled[2].equals("no") || initiativeRolled[3].equals("no") || initiativeRolled[4].equals("no")) {

					determineDoubles();
				}
			}
			*/			
		}
		/*
		else if (initiativeRolled[5].equals("no") && (initiativeRolled[0].equals("yes") || initiativeRolled[1].equals("yes") || initiativeRolled[2].equals("yes") || initiativeRolled[3].equals("yes") || initiativeRolled[4].equals("yes"))) {
			
			String str = "Waiting for players to roll...";
			sendToAll(str);
			
			runOnUiThread(new Runnable() {
      	  	    @Override
      	  	    public void run() {
      	  	    	
    	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
    	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
    	  			
    	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
    	  			centerscrolltext.setTypeface(typeFace);		    	  			
    	  			
      	  	    	
	  	    		centerscrolltext.setVisibility(View.VISIBLE);
			  		//centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "Waiting for players to roll...");		    	  	  	  	
      	  	    }
      	  	});
		}
		
		else {
			String str = "Waiting for players to roll...";
			sendToAll(str);
			
			runOnUiThread(new Runnable() {
      	  	    @Override
      	  	    public void run() {
      	  	    	
    	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
    	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
    	  			
    	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
    	  			centerscrolltext.setTypeface(typeFace);		    	  			
    	  			
      	  	    	
	  	    		centerscrolltext.setVisibility(View.VISIBLE);
			  		//centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "Waiting for players to roll...");		    	  	  	  	
      	  	    }
      	  	});
		}
		*/
	}
	
	public void determineDoubles() {	
		
		//DOES NOT LIKE TOASTS WITH HOST/CLIENT THREAD RUNNING
		//Toast.makeText(Host.this, "ALL PLAYERS FINISHED ROLLING", Toast.LENGTH_LONG).show();
		// FOR TESTING:
		/*runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
  	  	    	
  	    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "ALL INITIAL ROLLS COMPLETED");		    	  	  	  	
  	  	    }
  	  	});*/		
		
		
		// NEED THIS ANYMORE????
		if ((initiativeRolled[5].equals("yes")) && (initiativeRolled[0].equals("yes"))) {
			
			numberOfPlayers = 2;
			
			String str = "numberOfPlayers :" + 2;
			sendToClient0(str);			
		}
		if ((initiativeRolled[5].equals("yes")) && (initiativeRolled[0].equals("yes")) && (initiativeRolled[1].equals("yes"))) {
			
			numberOfPlayers = 3;
			
			String str = "numberOfPlayers :" + 3;
			sendToClient0(str);
			
			String str2 = "numberOfPlayers :" + 3;
			sendToClient1(str2);
		}
		if ((initiativeRolled[5].equals("yes")) && (initiativeRolled[0].equals("yes")) && (initiativeRolled[1].equals("yes")) && (initiativeRolled[2].equals("yes"))) {
			
			numberOfPlayers = 4;
			
			String str = "numberOfPlayers :" + 4;
			sendToClient0(str);
			
			String str2 = "numberOfPlayers :" + 4;
			sendToClient1(str2);
			
			String str3 = "numberOfPlayers :" + 4;
			sendToClient2(str3);
		}
		if ((initiativeRolled[5].equals("yes")) && (initiativeRolled[0].equals("yes")) && (initiativeRolled[1].equals("yes")) && (initiativeRolled[2].equals("yes")) && (initiativeRolled[3].equals("yes"))) {
			
			numberOfPlayers = 5;
			
			String str = "numberOfPlayers :" + 5;
			sendToClient0(str);
			
			String str2 = "numberOfPlayers :" + 5;
			sendToClient1(str2);
			
			String str3 = "numberOfPlayers :" + 5;
			sendToClient2(str3);
			
			String str4 = "numberOfPlayers :" + 5;
			sendToClient3(str4);
		}
		/*
		if ((initiativeRolled[5].equals("yes")) && (initiativeRolled[0].equals("yes")) && (initiativeRolled[1].equals("yes")) && (initiativeRolled[2].equals("yes")) && (initiativeRolled[3].equals("yes")) && (initiativeRolled[4].equals("yes"))) {
			numberOfPlayers = 6;
		}		
		*/		
		
			
		if (rollOff.equals("no")) {				
			
			if ((ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[4])) {					
				
				tempInitiativeStorage();										
				
				double05();
			}
			
			if ((ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[4])) {
				
				tempInitiativeStorage();
				
				double01();
			}
			
			if ((ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[4])) {
				
				tempInitiativeStorage();
				
				double15();
			}
			
			if ((ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[4])) {
				
				tempInitiativeStorage();
				
				double25();
			}
			
			if ((ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[4])) {
				
				tempInitiativeStorage();
				
				double20();
			}
			
			if ((ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[4])) {
				
				tempInitiativeStorage();
				
				double21();
			}
			
			if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[4]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5])) {
				
				tempInitiativeStorage();
				
				double30();
			}
			
			if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[4]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5])) {
				
				tempInitiativeStorage();
				
				double31();
			}
			
			if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[4]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5])) {
				
				tempInitiativeStorage();
				
				double32();
			}
			
			if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[4])) {
				
				tempInitiativeStorage();
				
				double35();
			}
			
			if ((ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[4])) {
				
				tempInitiativeStorage();
				
				double150();
			}
			
			if ((ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[4])) {

				tempInitiativeStorage();
				
				double215();
			}
			
			if ((ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[4])) {

				tempInitiativeStorage();
				
				double250();
			}
			
			if ((ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[4])) {

				tempInitiativeStorage();	
				
				double201();
			}
			
			if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[4]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5])) {

				tempInitiativeStorage();
				
				double301();
			}
			
			if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[4]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5])) {

				tempInitiativeStorage();		
				
				double302();
			}
			
			if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[4])) {

				tempInitiativeStorage();
				
				double305();
			}
			
			if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[4]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5])) {

				tempInitiativeStorage();	
				
				double312();
			}
			
			if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[4])) {

				tempInitiativeStorage();	
				
				double315();
			}
			
			if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[4])) {

				tempInitiativeStorage();	
				
				double325();
			}
			
			if ((ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[4])) {
				
				tempInitiativeStorage();		
				
				double0125();
			}
			
			if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[4]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5])) {

				tempInitiativeStorage();
				
				double3012();
			}
			
			if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[4]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2])) {

				tempInitiativeStorage();	
				
				double3015();
			}
			
			if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[4])) {

				tempInitiativeStorage();	
				
				double3125();
			}
			
			if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[4])) {

				tempInitiativeStorage();
				
				double3025();
			}
			
			if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[4])) {

				tempInitiativeStorage();
				
				double30125();
			}		
			
			if ((numberOfPlayers == 2) && (ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[0])) {
				
				tempInitiativeStorage();
				
				
				doublesModifier();
				
			}
			else if ((numberOfPlayers == 3) && (ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[1]) && 
					(ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[1])) {
				
				tempInitiativeStorage();
				
				
				doublesModifier();
				
			}
			else if ((numberOfPlayers == 4) && (ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[1]) && 
					(ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[1]) && 
					(ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[2])) {
				
				tempInitiativeStorage();
				
				
				doublesModifier();
				
			}
			else if ((numberOfPlayers == 5) && (ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[1]) && 
					(ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[3]) && 
					(ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[2]) && 
					(ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[2]) && 
					(ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3])) {
				
				tempInitiativeStorage();
				
				
				doublesModifier();
				
			}
			/*
			else {					
				
				tempInitiativeStorage();
				
				
				doublesModifier();
			}
			*/
		}
		
		
		else if (rollOff.equals("yes")) {
			
			if (rollOff305.equals("yes")) {
				
				if ((ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[5])) {				
					
					double05();
				}
				
				else {
					
					if (rollOff530125.equals("yes") || rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")
							|| rollOff5325.equals("yes") || rollOff5315.equals("yes") || rollOff5312.equals("yes") || rollOff5305.equals("yes") || rollOff5302.equals("yes") || rollOff5301.equals("yes")
							|| rollOff4250.equals("yes") || rollOff4215.equals("yes") || rollOff4201.equals("yes") || rollOff4150.equals("yes")) {
						
						deadMethod();
					}					
					else if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5]) {
						initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.1f);
						initiativeFinal[5] = (float) initiativeFinal[5];					
						
						
						doublesModifier();						
					}
					else if (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) {
						initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.1f);
						initiativeFinal[0] = (float) initiativeFinal[0];							
						
						
						doublesModifier();						
					}					
				}
			}				
			
			if (rollOff301.equals("yes")) {
				
				if ((ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[1])) {				
					
					double01();
				}
				
				else {
					
					if (rollOff530125.equals("yes") || rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")
							|| rollOff5325.equals("yes") || rollOff5315.equals("yes") || rollOff5312.equals("yes") || rollOff5305.equals("yes") || rollOff5302.equals("yes") || rollOff5301.equals("yes")
							|| rollOff4250.equals("yes") || rollOff4215.equals("yes") || rollOff4201.equals("yes") || rollOff4150.equals("yes")) {
						
						deadMethod();
					}				
					else if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) {
						initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.1f);
						initiativeFinal[1] = (float) initiativeFinal[1];		
						
						
						doublesModifier();						
					}
					else if (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) {
						initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.1f);
						initiativeFinal[0] = (float) initiativeFinal[0];																		
						
						
						doublesModifier();						
					}	
				}
			}				
			
			if (rollOff315.equals("yes")) {
				
				if ((ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[5])) {				
											
					double15();
				}
				
				else {
					
					if (rollOff530125.equals("yes") || rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")
							|| rollOff5325.equals("yes") || rollOff5315.equals("yes") || rollOff5312.equals("yes") || rollOff5305.equals("yes") || rollOff5302.equals("yes") || rollOff5301.equals("yes")
							|| rollOff4250.equals("yes") || rollOff4215.equals("yes") || rollOff4201.equals("yes") || rollOff4150.equals("yes")) {
						
						deadMethod();
					}					
					else if (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5]) {
						initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.1f);
						initiativeFinal[5] = (float) initiativeFinal[5];												
						
						
						doublesModifier();						
					}
					else if (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1]) {
						initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.1f);
						initiativeFinal[1] = (float) initiativeFinal[1];												
						
						
						doublesModifier();					
					}	
				}
			}
			
			if (rollOff425.equals("yes")) {
				
				if ((ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[5])) {				
											
					double25();
				}
				
				else {
					
					if (rollOff530125.equals("yes") || rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")
							|| rollOff5325.equals("yes") || rollOff5315.equals("yes") || rollOff5312.equals("yes") || rollOff5305.equals("yes") || rollOff5302.equals("yes") || rollOff5301.equals("yes")
							|| rollOff4250.equals("yes") || rollOff4215.equals("yes") || rollOff4201.equals("yes") || rollOff4150.equals("yes")) {
						
						deadMethod();
					}					
					else if (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5]) {
						initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.1f);
						initiativeFinal[5] = (float) initiativeFinal[5];												
						
						
						doublesModifier();						
					}
					else if (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2]) {
						initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.1f);
						initiativeFinal[2] = (float) initiativeFinal[2];												
						
						
						doublesModifier();						
					}	
				}
			}
			
			if (rollOff420.equals("yes")) {
				
				if ((ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[0])) {				
											
					double20();
				}
				
				else {
					
					if (rollOff530125.equals("yes") || rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")
							|| rollOff5325.equals("yes") || rollOff5315.equals("yes") || rollOff5312.equals("yes") || rollOff5305.equals("yes") || rollOff5302.equals("yes") || rollOff5301.equals("yes")
							|| rollOff4250.equals("yes") || rollOff4215.equals("yes") || rollOff4201.equals("yes") || rollOff4150.equals("yes")) {
						
						deadMethod();
					}					
					else if (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) {
						initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.1f);
						initiativeFinal[0] = (float) initiativeFinal[0];												
						
						
						doublesModifier();						
					}
					else if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2]) {
						initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.1f);
						initiativeFinal[2] = (float) initiativeFinal[2];												
						
						
						doublesModifier();						
					}
				}
			}
			
			if (rollOff421.equals("yes")) {
				
				if ((ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[1])) {				
											
					double21();
				}
				
				else {
					
					if (rollOff530125.equals("yes") || rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")
							|| rollOff5325.equals("yes") || rollOff5315.equals("yes") || rollOff5312.equals("yes") || rollOff5305.equals("yes") || rollOff5302.equals("yes") || rollOff5301.equals("yes")
							|| rollOff4250.equals("yes") || rollOff4215.equals("yes") || rollOff4201.equals("yes") || rollOff4150.equals("yes")) {
						
						deadMethod();
					}					
					else if (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1]) {
						initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.1f);
						initiativeFinal[1] = (float) initiativeFinal[1];												
						
						
						doublesModifier();						
					}
					else if (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2]) {
						initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.1f);
						initiativeFinal[2] = (float) initiativeFinal[2];											
						
						
						doublesModifier();						
					}
				}
			}
			
			if (rollOff530.equals("yes")) {
				
				if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0])) {				
											
					double30();
				}
				
				else {
					
					if (rollOff530125.equals("yes") || rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")
							|| rollOff5325.equals("yes") || rollOff5315.equals("yes") || rollOff5312.equals("yes") || rollOff5305.equals("yes") || rollOff5302.equals("yes") || rollOff5301.equals("yes")
							|| rollOff4250.equals("yes") || rollOff4215.equals("yes") || rollOff4201.equals("yes") || rollOff4150.equals("yes")) {
						
						deadMethod();
					}					
					else if (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) {
						initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.1f);
						initiativeFinal[0] = (float) initiativeFinal[0];												
						
						
						doublesModifier();						
					}
					else if (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) {
						initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.1f);
						initiativeFinal[3] = (float) initiativeFinal[3];												
						
						
						doublesModifier();						
					}
				}
			}
			
			if (rollOff531.equals("yes")) {
				
				if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1])) {				
											
					double31();
				}
				
				else {
					
					if (rollOff530125.equals("yes") || rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")
							|| rollOff5325.equals("yes") || rollOff5315.equals("yes") || rollOff5312.equals("yes") || rollOff5305.equals("yes") || rollOff5302.equals("yes") || rollOff5301.equals("yes")
							|| rollOff4250.equals("yes") || rollOff4215.equals("yes") || rollOff4201.equals("yes") || rollOff4150.equals("yes")) {
						
						deadMethod();
					}					
					else if (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) {
						initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.1f);
						initiativeFinal[1] = (float) initiativeFinal[1];												
						
						
						doublesModifier();						
					}
					else if (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) {
						initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.1f);
						initiativeFinal[3] = (float) initiativeFinal[3];												
						
						
						doublesModifier();						
					}
				}
			}
			
			if (rollOff532.equals("yes")) {
				
				if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2])) {				
											
					double32();
				}
				
				else {
					
					if (rollOff530125.equals("yes") || rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")
							|| rollOff5325.equals("yes") || rollOff5315.equals("yes") || rollOff5312.equals("yes") || rollOff5305.equals("yes") || rollOff5302.equals("yes") || rollOff5301.equals("yes")
							|| rollOff4250.equals("yes") || rollOff4215.equals("yes") || rollOff4201.equals("yes") || rollOff4150.equals("yes")) {
						
						deadMethod();
					}					
					else if (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2]) {
						initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.1f);
						initiativeFinal[2] = (float) initiativeFinal[2];												
						
						
						doublesModifier();						
					}
					else if (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) {
						initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.1f);
						initiativeFinal[3] = (float) initiativeFinal[3];												
						
						
						doublesModifier();						
					}
				}
			}
			
			if (rollOff535.equals("yes")) {
				
				if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
											
					double35();
				}
				
				else {
					
					if (rollOff530125.equals("yes") || rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")
							|| rollOff5325.equals("yes") || rollOff5315.equals("yes") || rollOff5312.equals("yes") || rollOff5305.equals("yes") || rollOff5302.equals("yes") || rollOff5301.equals("yes")
							|| rollOff4250.equals("yes") || rollOff4215.equals("yes") || rollOff4201.equals("yes") || rollOff4150.equals("yes")) {
						
						deadMethod();
					}					
					else if (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5]) {
						initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.1f);
						initiativeFinal[5] = (float) initiativeFinal[5];												
						
						
						doublesModifier();						
					}
					else if (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) {
						initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.1f);
						initiativeFinal[3] = (float) initiativeFinal[3];												
						
						
						doublesModifier();						
					}
				}
			}				
			
			if (rollOff4150.equals("yes")) {
				
				if ((ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[0])) {				
											
					double150();
				}					
				else if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[5])) {					
					
					double05();
				}
				else if ((ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[0])) {						
					
					double01();
				}
				else if ((ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[0])) {																		
					
					double15();
				}
				else {
					
					if (rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")) {
						
						deadMethod();
					}					
					else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
						initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							initiativeFinal[0] = (float) initiativeFinal[0];													
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							initiativeFinal[5] = (float) initiativeFinal[5];											
							
							
							doublesModifier();							
						}							
					}
					else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
						initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							initiativeFinal[0] = (float) initiativeFinal[0];													
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							initiativeFinal[1] = (float) initiativeFinal[1];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
						initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							initiativeFinal[1] = (float) initiativeFinal[1];							
													
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							initiativeFinal[5] = (float) initiativeFinal[5];							
													
							
							doublesModifier();							
						}
					}
				}
			}				
			
			if (rollOff4215.equals("yes")) {

				if ((ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[5])) {				
											
					double215();
				}					
				else if ((ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[5])) {						
					
					double15();
				}
				else if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[5])) {						
										
					double25();
				}
				else if ((ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[5])) {																		
					
					double21();
				}					
				else {
					
					if (rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")) {
						
						deadMethod();
					}
					
					else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							initiativeFinal[1] = (float) initiativeFinal[1];													
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							initiativeFinal[5] = (float) initiativeFinal[5];							
													
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							initiativeFinal[2] = (float) initiativeFinal[2];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							initiativeFinal[5] = (float) initiativeFinal[5];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
						initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							initiativeFinal[1] = (float) initiativeFinal[1];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							initiativeFinal[2] = (float) initiativeFinal[2];
							
							
							doublesModifier();							
						}
					}
				}
			}				
			
			if (rollOff4250.equals("yes")) {

				if ((ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[0])) {				
											
					double250();
				}				
				else if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[5])) {					
					
					double05();
				}
				else if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[0])) {					
					
					double20();
				}
				else if ((ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[0])) {																		
					
					double25();
				}					
				else {
					
					if (rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")) {
						
						deadMethod();
					}
					
					else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
						initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							initiativeFinal[0] = (float) initiativeFinal[0];							
													
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							initiativeFinal[5] = (float) initiativeFinal[5];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
						initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							initiativeFinal[0] = (float) initiativeFinal[0];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							initiativeFinal[2] = (float) initiativeFinal[2];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							initiativeFinal[5] = (float) initiativeFinal[5];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							initiativeFinal[2] = (float) initiativeFinal[2];
							
							
							doublesModifier();							
						}
					}
				}
			}				
			
			if (rollOff4201.equals("yes")) {

				if ((ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[1])) {				
											
					double201();
				}
				else if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[1])) {					
					
					double01();
				}
				else if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[1])) {						
					
					double21();
				}
				else if ((ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[1])) {																	
					
					double20();
				}					
				else {
					
					if (rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")) {
						
						deadMethod();
					}
					
					else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
						initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							initiativeFinal[1] = (float) initiativeFinal[1];							
													
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							initiativeFinal[0] = (float) initiativeFinal[0];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
						initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							initiativeFinal[1] = (float) initiativeFinal[1];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							initiativeFinal[2] = (float) initiativeFinal[2];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
						initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							initiativeFinal[0] = (float) initiativeFinal[0];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							initiativeFinal[2] = (float) initiativeFinal[2];
							
							
							doublesModifier();							
						}
					}
				}
			}				
			
			if (rollOff5301.equals("yes")) {

				if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1])) {				
											
					double301();
				}
				else if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[1])) {					
					
					double01();
				}
				else if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1])) {					
					
					double31();
				}
				else if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1])) {					
					
					double30();
				}					
				else {
					
					if (rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")) {
						
						deadMethod();
					}
					
					else if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
						initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							initiativeFinal[1] = (float) initiativeFinal[1];							
													
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							initiativeFinal[0] = (float) initiativeFinal[0];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
						initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							initiativeFinal[1] = (float) initiativeFinal[1];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							initiativeFinal[3] = (float) initiativeFinal[3];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
						initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							initiativeFinal[0] = (float) initiativeFinal[0];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							initiativeFinal[3] = (float) initiativeFinal[3];
							
							
							doublesModifier();							
						}
					}
				}
			}			
			
			if (rollOff5302.equals("yes")) {

				if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2])) {				
											
					double302();
				}
				else if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[2])) {						
					
					double20();
				}
				else if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2])) {					
					
					double32();
				}
				else if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2])) {																	
					
					double30();
				}					
				else {
					
					if (rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")) {
						
						deadMethod();
					}
					
					else if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
						initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							initiativeFinal[2] = (float) initiativeFinal[2];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							initiativeFinal[0] = (float) initiativeFinal[0];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
						initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							initiativeFinal[2] = (float) initiativeFinal[2];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							initiativeFinal[3] = (float) initiativeFinal[3];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
						initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							initiativeFinal[0] = (float) initiativeFinal[0];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							initiativeFinal[3] = (float) initiativeFinal[3];
							
							
							doublesModifier();							
						}
					}
				}
			}				
			
			if (rollOff5305.equals("yes")) {

				if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
											
					double305();
				}
				else if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[5])) {					
					
					double05();
				}
				else if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {					
					
					double35();
				}
				else if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5])) {						
					
					double30();
				}				
				else {
					
					if (rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")) {
						
						deadMethod();
					}
					
					else if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							initiativeFinal[5] = (float) initiativeFinal[5];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							initiativeFinal[0] = (float) initiativeFinal[0];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							initiativeFinal[5] = (float) initiativeFinal[5];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							initiativeFinal[3] = (float) initiativeFinal[3];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
						initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							initiativeFinal[0] = (float) initiativeFinal[0];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							initiativeFinal[3] = (float) initiativeFinal[3];
							
							
							doublesModifier();							
						}
					}
				}
			}				
			
			if (rollOff5312.equals("yes")) {

				if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2])) {				
											
					double312();
				}
				else if ((ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[2])) {					
					
					double21();
				}
				else if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2])) {					
					
					double32();
				}
				else if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2])) {					
					
					double31();
				}					
				else {
					
					if (rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")) {
						
						deadMethod();
					}
					
					else if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
						initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							initiativeFinal[2] = (float) initiativeFinal[2];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							initiativeFinal[1] = (float) initiativeFinal[1];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
						initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							initiativeFinal[2] = (float) initiativeFinal[2];
							
							
							doublesModifier();								
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							initiativeFinal[3] = (float) initiativeFinal[3];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
						initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							initiativeFinal[1] = (float) initiativeFinal[1];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							initiativeFinal[3] = (float) initiativeFinal[3];
							
							
							doublesModifier();							
						}
					}
				}
			}				
			
			if (rollOff5315.equals("yes")) {

				if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
											
					double315();
				}
				else if ((ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[5])) {						
					
					double15();
				}
				else if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {						
					
					double35();
				}
				else if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5])) {					
					
					double31();
				}					
				else {
					
					if (rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")) {
						
						deadMethod();
					}
					
					else if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							initiativeFinal[5] = (float) initiativeFinal[5];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							initiativeFinal[1] = (float) initiativeFinal[1];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							initiativeFinal[5] = (float) initiativeFinal[5];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							initiativeFinal[3] = (float) initiativeFinal[3];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
						initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							initiativeFinal[1] = (float) initiativeFinal[1];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							initiativeFinal[3] = (float) initiativeFinal[3];
							
							
							doublesModifier();							
						}
					}
				}
			}				
			
			if (rollOff5325.equals("yes")) {

				if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
											
					double325();
				}
				else if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[5])) {					
					
					double25();
				}
				else if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {					
					
					double35();
				}
				else if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5])) {					
					
					double32();
				}					
				else {
					
					if (rollOff53025.equals("yes") || rollOff53125.equals("yes") || rollOff53015.equals("yes") || rollOff53012.equals("yes") || rollOff40125.equals("yes")) {
						
						deadMethod();
					}
					
					else if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							initiativeFinal[5] = (float) initiativeFinal[5];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							initiativeFinal[2] = (float) initiativeFinal[2];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							initiativeFinal[5] = (float) initiativeFinal[5];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							initiativeFinal[3] = (float) initiativeFinal[3];
							
							
							doublesModifier();							
						}
					}
					else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
						initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							initiativeFinal[2] = (float) initiativeFinal[2];
							
							
							doublesModifier();							
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							initiativeFinal[3] = (float) initiativeFinal[3];
							
							
							doublesModifier();							
						}
					}
				}
			}				
			
			if (rollOff40125.equals("yes")) {

				if ((ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[5])) {				
											
					double0125();
				}					
				if ((ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[5])) {				
											
					double215();
				}
				if ((ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[0])) {				
											
					double250();
				}
				if ((ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[5])) {				
											
					double150();
				}
				if ((ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[0])) {				
											
					double201();
				}				
				if ((ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[2])) {				
					
					double21();
				}
				if ((ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[5])) {
											
					double15();
				}
				if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[5])) {
					
					double25();
				}
				if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[5])) {				
					
					double05();
				}
				if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[0])) {
					
					double20();
				}
				if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[1])) {
					
					double01();
				}					
				else {
					
					if (rollOff530125.equals("yes")) {
						
						deadMethod();
					}
					
					else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);								
														
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
						initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
					}
				}
			}				
			
			if (rollOff53012.equals("yes")) {

				if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2])) {				
											
					double3012();
				}					
				if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[1])) {				
					
					double201();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2])) {				
					
					double312();
				}
				if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[2])) {				
					
					double302();
				}
				if ((ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[3])) {				
					
					double301();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0])) {				
					
					double30();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1])) {
					
					double31();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2])) {
					
					double32();
				}
				if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[1])) {				
					
					double01();
				}
				if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[1])) {
					
					double21();
				}
				if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[0])) {
					
					double20();
				}				
				else {
					
					if (rollOff530125.equals("yes")) {
						
						deadMethod();
					}
					
					else if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
						initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
						initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
						initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
						initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
					}
				}
			}				
			
			if (rollOff53015.equals("yes")) {

				if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
											
					double3015();
				}
				
				if ((ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[1])) {				
					
					double150();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
					
					double315();
				}
				if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[5])) {				
					
					double305();
				}
				if ((ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[3])) {				
					
					double301();
				}					
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0])) {				
					
					double30();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1])) {
					
					double31();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {
					
					double35();
				}
				if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[1])) {				
					
					double01();
				}
				if ((ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[1])) {
					
					double15();
				}
				if ((ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[0])) {
					
					double05();
				}					
				else {
					
					if (rollOff530125.equals("yes")) {
						
						deadMethod();
					}
					
					else if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
						initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
						initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
						initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);								
								
								
								doublesModifier();								
							}
						}
					}
				}
			}			
			
			if (rollOff53125.equals("yes")) {

				if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
											
					double3125();
				}					
				if ((ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[1])) {				
					
					double215();
				}
				if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[5])) {				
					
					double325();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
					
					double315();					}
				
				if ((ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[3])) {				
					
					double312();
				}				
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2])) {				
					
					double32();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1])) {
					
					double31();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {
					
					double35();
				}
				if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[1])) {				
					
					double21();
				}
				if ((ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[1])) {
					
					double15();
				}
				if ((ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[2])) {
					
					double25();
				}					
				else {
					
					if (rollOff530125.equals("yes")) {
						
						deadMethod();
					}
					
					else if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
						initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[1] = (float) (initiativeFinal[1]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
					}
				}
			}				
			
			if (rollOff53025.equals("yes")) {

				if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
											
					double3025();
				}					
				if ((ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[0])) {				
					
					double250();
				}
				if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[5])) {				
					
					double325();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
					
					double305();					}
				
				if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[3])) {				
					
					double302();
				}					
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2])) {				
					
					double32();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0])) {
					
					double30();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {
					
					double35();
				}
				if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[0])) {				
					
					double20();
				}
				if ((ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[0])) {
					
					double05();
				}
				if ((ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[2])) {
					
					double25();
				}				
				else {
					
					if (rollOff530125.equals("yes")) {
						
						deadMethod();
					}
					
					else if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[5] = (float) (initiativeFinal[5]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
					}
					else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
						initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[0] = (float) (initiativeFinal[0]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								initiativeFinal[2] = (float) (initiativeFinal[2]);
								
								
								doublesModifier();								
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								initiativeFinal[3] = (float) (initiativeFinal[3]);
								
								
								doublesModifier();								
							}
						}
					}
				}
			}			
			
			if (rollOff530125.equals("yes")) {

				if ((ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
											
					double30125();
				}				
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
					
					double3025();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
					
					double3125();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
					
					double3015();
				}					
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2])) {				
					
					double3012();
				}
				if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[5])) {				
					
					double0125();
				}					
				if ((ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[0])) {				
					
					double150();
				}
				if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[1])) {				
					
					double201();
				}
				if ((ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[5] == ArrayOfInitiative.initiative[1])) {				
					
					double215();
				}
				if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[0])) {				
					
					double250();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1])) {				
					
					double301();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2])) {				
					
					double302();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
					
					double305();
				}
				if ((ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[3])) {				
					
					double312();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {				
					
					double315();
				}
				if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[5])) {				
					
					double325();
				}				
				if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[5])) {
					
					double05();
				}
				if ((ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[0] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] == ArrayOfInitiative.initiative[1])) {
					
					double01();
				}
				if ((ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] == ArrayOfInitiative.initiative[5])) {
					
					double15();
				}
				if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[0])) {
					
					double20();
				}
				if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[1])) {
					
					double21();
				}
				if ((ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] == ArrayOfInitiative.initiative[5])) {
					
					double25();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[0])) {
					
					double30();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[1])) {
					
					double31();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[5]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[2])) {
					
					double32();
				}
				if ((ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] != ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] == ArrayOfInitiative.initiative[5])) {
					
					double35();
				}					
				else {
					
					if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);								
							
							if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
							}
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);								
							
							if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
							}
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);								
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[5])) {
						initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);								
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[5])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[5])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[5])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[5] = (float) (initiativeFinal[5]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
						}
						else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
						}
					}
					
					else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[2])) {
						initiativeFinal[5] = (float) (initiativeFinal[5]) + (0.6f);
						
						if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.5f);								
							
							if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
						}
						else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
						}
						else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[2])) {
							initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[2])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[2])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[2] = (float) (initiativeFinal[2]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
						}
						else if ((ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[2] > ArrayOfInitiative.initiative[1])) {
							initiativeFinal[2] = (float) (initiativeFinal[2]) + (0.5f);
							
							if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0]) && (ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[1])) {
								initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[1])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[1] = (float) (initiativeFinal[1]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
							else if ((ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[3]) && (ArrayOfInitiative.initiative[1] > ArrayOfInitiative.initiative[0])) {
								initiativeFinal[1] = (float) (initiativeFinal[1]) + (0.4f);
								
								if ((ArrayOfInitiative.initiative[3] > ArrayOfInitiative.initiative[0])) {
									initiativeFinal[3] = (float) (initiativeFinal[3]) + (0.3f);
									initiativeFinal[0] = (float) (initiativeFinal[0]);
									
									doublesModifier();
								}
								else if ((ArrayOfInitiative.initiative[0] > ArrayOfInitiative.initiative[3])) {
									initiativeFinal[0] = (float) (initiativeFinal[0]) + (0.3f);
									initiativeFinal[3] = (float) (initiativeFinal[3]);
									
									doublesModifier();
								}
							}
						}
					}
				}
			}
		}		  
	}
	
	
	public void tempInitiativeStorage() {
		
		initiativeFinal[0] = (float) ArrayOfInitiative.initiative[0];
		initiativeFinal[1] = (float) ArrayOfInitiative.initiative[1];
		initiativeFinal[2] = (float) ArrayOfInitiative.initiative[2];
		initiativeFinal[3] = (float) ArrayOfInitiative.initiative[3];
		initiativeFinal[4] = (float) ArrayOfInitiative.initiative[4];		
		initiativeFinal[5] = (float) ArrayOfInitiative.initiative[5];		
	}
	
	
	public void double05() {			
		
		initiativeRolled[0] = "no";
		initiativeRolled[5] = "no";
		
		client0RolledDouble = true;
		hostRolledDouble = true;
		
		rollOff ="yes";
		rollOff305 ="yes";
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
  	  	    	
  	    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Re-roll for inititiative...");						
  	  	    }
  	  	});
		
		String str = "re-roll for inititiative...";
		sendToClient0(str);						
		
		String str2 = "rerllInitiative";
		sendToClient0(str2);
		
		
		if (numberOfPlayers == 3) {
			
			String str3 = "Players are re-rolling...";
			sendToClient1(str3);
		}
		else if (numberOfPlayers == 4) {
			
			String str3 = "Players are re-rolling...";
			sendToClient1(str3);
			
			String str4 = "Players are re-rolling...";
			sendToClient2(str4);
		}
		else if (numberOfPlayers == 5) {
			
			String str3 = "Players are re-rolling...";
			sendToClient1(str3);
			
			String str4 = "Players are re-rolling...";
			sendToClient2(str4);
			
			String str5 = "Players are re-rolling...";
			sendToClient3(str5);
		}
		
		
		hostRollsInitiative();		
	}
	
	public void double01() {
		
		initiativeRolled[0] = "no";
		initiativeRolled[1] = "no";
		
		client0RolledDouble = true;
		client1RolledDouble = true;
		
		rollOff ="yes";
		rollOff301 ="yes";
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
  	  	    	
  	    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Players are re-rolling...");					
  	  	    }
  	  	});
		
		String str = "re-roll for inititiative...";
		sendToClient0(str);						
		
		String str2 = "rerllInitiative";
		sendToClient0(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient1(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient1(str4);		
		
		
		if (numberOfPlayers == 4) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient2(str5);
		}
		else if (numberOfPlayers == 5) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient2(str5);
			
			String str6 = "Players are re-rolling...";
			sendToClient3(str6);
		}
	}
	
	public void double15() {
		
		initiativeRolled[1] = "no";
		initiativeRolled[5] = "no";
		
		client1RolledDouble = true;
		hostRolledDouble = true;
		
		rollOff ="yes";
		rollOff315 ="yes";
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
  	  	    	
  	    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Re-roll for inititiative...");						
  	  	    }
  	  	});
		
		String str = "re-roll for inititiative...";
		sendToClient1(str);						
		
		String str2 = "rerllInitiative";
		sendToClient1(str2);
		
		
		if (numberOfPlayers == 3) {
			
			String str3 = "Players are re-rolling...";
			sendToClient0(str3);
		}
		else if (numberOfPlayers == 4) {
			
			String str3 = "Players are re-rolling...";
			sendToClient0(str3);
			
			String str4 = "Players are re-rolling...";
			sendToClient2(str4);
		}
		else if (numberOfPlayers == 5) {
			
			String str3 = "Players are re-rolling...";
			sendToClient0(str3);
			
			String str4 = "Players are re-rolling...";
			sendToClient2(str4);
			
			String str5 = "Players are re-rolling...";
			sendToClient3(str5);
		}
		
		
		hostRollsInitiative();
	}
	
	public void double25() {
		
		initiativeRolled[2] = "no";
		initiativeRolled[5] = "no";
		
		client2RolledDouble = true;
		hostRolledDouble = true;
		
		rollOff ="yes";
		rollOff425 ="yes";
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
  	  	    	
  	    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Re-roll for inititiative...");						
  	  	    }
  	  	});
		
		String str = "re-roll for inititiative...";
		sendToClient2(str);						
		
		String str2 = "rerllInitiative";
		sendToClient2(str2);
		
		
		if (numberOfPlayers == 3) {
			
			String str3 = "Players are re-rolling...";
			sendToClient0(str3);
		}
		else if (numberOfPlayers == 4) {
			
			String str3 = "Players are re-rolling...";
			sendToClient0(str3);
			
			String str4 = "Players are re-rolling...";
			sendToClient1(str4);
		}
		else if (numberOfPlayers == 5) {
			
			String str3 = "Players are re-rolling...";
			sendToClient0(str3);
			
			String str4 = "Players are re-rolling...";
			sendToClient1(str4);
			
			String str5 = "Players are re-rolling...";
			sendToClient3(str5);
		}
		
		
		hostRollsInitiative();
	}
	
	public void double20() {
		
		initiativeRolled[2] = "no";
		initiativeRolled[0] = "no";
		
		client2RolledDouble = true;
		client0RolledDouble = true;
		
		rollOff ="yes";
		rollOff420 ="yes";
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
  	  	    	
  	    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Players are re-rolling...");						
  	  	    }
  	  	});
		
		String str = "re-roll for inititiative...";
		sendToClient2(str);						
		
		String str2 = "rerllInitiative";
		sendToClient2(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient0(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient0(str4);
		
		
		if (numberOfPlayers == 4) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient1(str5);
		}
		else if (numberOfPlayers == 5) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient1(str5);
			
			String str6 = "Players are re-rolling...";
			sendToClient3(str6);
		}
	}
	
	public void double21() {
		
		initiativeRolled[2] = "no";
		initiativeRolled[1] = "no";
		
		client2RolledDouble = true;
		client1RolledDouble = true;
		
		rollOff ="yes";
		rollOff421 ="yes";
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
  	  	    	
  	    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Players are re-rolling...");						
  	  	    }
  	  	});
		
		String str = "re-roll for inititiative...";
		sendToClient2(str);						
		
		String str2 = "rerllInitiative";
		sendToClient2(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient1(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient1(str4);
		
		
		if (numberOfPlayers == 4) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient0(str5);
		}
		else if (numberOfPlayers == 5) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient0(str5);
			
			String str6 = "Players are re-rolling...";
			sendToClient3(str6);
		}
	}
	
	public void double150() {
		
		initiativeRolled[1] = "no";
		initiativeRolled[5] = "no";
		initiativeRolled[0] = "no";
		
		client1RolledDouble = true;
		hostRolledDouble = true;
		client0RolledDouble = true;
		
		rollOff ="yes";
		rollOff4150 ="yes";
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
  	  	    	
  	    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Re-roll for inititiative...");						
  	  	    }
  	  	});
		
		String str = "re-roll for inititiative...";
		sendToClient1(str);						
		
		String str2 = "rerllInitiative";
		sendToClient1(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient0(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient0(str4);
		
		
		if (numberOfPlayers == 4) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient2(str5);
		}
		else if (numberOfPlayers == 5) {
			
			String str5 = "Players are re-rolling...";
			sendToClient2(str5);			
			
			String str6 = "Players are re-rolling...";
			sendToClient3(str6);
		}
		
		
		hostRollsInitiative();
	}

	public void double215() {
	
		initiativeRolled[2] = "no";
		initiativeRolled[1] = "no";
		initiativeRolled[5] = "no";
		
		client2RolledDouble = true;		
		client1RolledDouble = true;
		hostRolledDouble = true;
		
		rollOff ="yes";
		rollOff4215 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Re-roll for inititiative...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient2(str);						
		
		String str2 = "rerllInitiative";
		sendToClient2(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient1(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient1(str4);
		
		
		if (numberOfPlayers == 4) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient0(str5);
		}
		else if (numberOfPlayers == 5) {
			
			String str5 = "Players are re-rolling...";
			sendToClient0(str5);			
			
			String str6 = "Players are re-rolling...";
			sendToClient3(str6);
		}
		
		
		hostRollsInitiative();
	}

	public void double250() {
	
		initiativeRolled[2] = "no";
		initiativeRolled[5] = "no";
		initiativeRolled[0] = "no";
		
		client2RolledDouble = true;
		hostRolledDouble = true;
		client0RolledDouble = true;	
		
		rollOff ="yes";
		rollOff4250 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Re-roll for inititiative...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient2(str);						
		
		String str2 = "rerllInitiative";
		sendToClient2(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient0(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient0(str4);
		
		
		if (numberOfPlayers == 4) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient1(str5);
		}
		else if (numberOfPlayers == 5) {
			
			String str5 = "Players are re-rolling...";
			sendToClient1(str5);			
			
			String str6 = "Players are re-rolling...";
			sendToClient3(str6);
		}
		
		
		hostRollsInitiative();
	}

	public void double201() {
	
		initiativeRolled[2] = "no";
		initiativeRolled[0] = "no";
		initiativeRolled[1] = "no";
		
		client2RolledDouble = true;
		client0RolledDouble = true;
		client1RolledDouble = true;
		
		rollOff ="yes";
		rollOff4201 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Players are re-rolling...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient2(str);						
		
		String str2 = "rerllInitiative";
		sendToClient2(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient0(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient0(str4);
		
		String str5 = "re-roll for inititiative...";
		sendToClient1(str5);						
		
		String str6 = "rerllInitiative";
		sendToClient1(str6);		
		
		
		if (numberOfPlayers == 5) {					
			
			String str7 = "Players are re-rolling...";
			sendToClient3(str7);
		}
	}
	
	public void double30() {
		
		initiativeRolled[3] = "no";
		initiativeRolled[0] = "no";
		
		client3RolledDouble = true;
		client0RolledDouble = true;
		
		rollOff ="yes";
		rollOff530 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Players are re-rolling...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient3(str);						
		
		String str2 = "rerllInitiative";
		sendToClient3(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient0(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient0(str4);
		
		
		if (numberOfPlayers == 4) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient1(str5);
		}
		else if (numberOfPlayers == 5) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient1(str5);
			
			String str6 = "Players are re-rolling...";
			sendToClient2(str6);
		}
	}
	
	public void double31() {
		
		initiativeRolled[3] = "no";
		initiativeRolled[1] = "no";
		
		client3RolledDouble = true;
		client1RolledDouble = true;
		
		rollOff ="yes";
		rollOff531 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Players are re-rolling...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient3(str);						
		
		String str2 = "rerllInitiative";
		sendToClient3(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient1(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient1(str4);
		
		
		if (numberOfPlayers == 4) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient0(str5);
		}
		else if (numberOfPlayers == 5) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient0(str5);
			
			String str6 = "Players are re-rolling...";
			sendToClient2(str6);
		}
	}
	
	public void double32() {
		
		initiativeRolled[3] = "no";
		initiativeRolled[2] = "no";
		
		client3RolledDouble = true;
		client2RolledDouble = true;
		
		rollOff ="yes";
		rollOff532 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Players are re-rolling...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient3(str);						
		
		String str2 = "rerllInitiative";
		sendToClient3(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient2(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient2(str4);
		
		
		if (numberOfPlayers == 4) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient0(str5);
		}
		else if (numberOfPlayers == 5) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient0(str5);
			
			String str6 = "Players are re-rolling...";
			sendToClient1(str6);
		}
	}
	
	public void double35() {
		
		initiativeRolled[3] = "no";
		initiativeRolled[5] = "no";
		
		client3RolledDouble = true;
		hostRolledDouble = true;
		
		rollOff ="yes";
		rollOff535 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Re-roll for inititiative...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient3(str);						
		
		String str2 = "rerllInitiative";
		sendToClient3(str2);
		
		
		if (numberOfPlayers == 3) {
			
			String str3 = "Players are re-rolling...";
			sendToClient0(str3);
		}
		else if (numberOfPlayers == 4) {
			
			String str3 = "Players are re-rolling...";
			sendToClient0(str3);
			
			String str4 = "Players are re-rolling...";
			sendToClient1(str4);
		}
		else if (numberOfPlayers == 5) {
			
			String str3 = "Players are re-rolling...";
			sendToClient0(str3);
			
			String str4 = "Players are re-rolling...";
			sendToClient1(str4);
			
			String str5 = "Players are re-rolling...";
			sendToClient2(str5);
		}
		
		
		hostRollsInitiative();		
	}
	
	public void double301() {
		
		initiativeRolled[3] = "no";
		initiativeRolled[0] = "no";
		initiativeRolled[1] = "no";
		
		client3RolledDouble = true;
		client0RolledDouble = true;
		client1RolledDouble = true;
		
		rollOff ="yes";
		rollOff5301 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Players are re-rolling...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient3(str);						
		
		String str2 = "rerllInitiative";
		sendToClient3(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient0(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient0(str4);
		
		String str5 = "re-roll for inititiative...";
		sendToClient1(str5);						
		
		String str6 = "rerllInitiative";
		sendToClient1(str6);
		
		
		if (numberOfPlayers == 5) {					
			
			String str7 = "Players are re-rolling...";
			sendToClient2(str7);
		}
	}

	public void double302() {
	
		initiativeRolled[3] = "no";
		initiativeRolled[0] = "no";
		initiativeRolled[2] = "no";
		
		client3RolledDouble = true;
		client0RolledDouble = true;
		client2RolledDouble = true;
		
		rollOff ="yes";
		rollOff5302 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Players are re-rolling...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient3(str);						
		
		String str2 = "rerllInitiative";
		sendToClient3(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient0(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient0(str4);
		
		String str5 = "re-roll for inititiative...";
		sendToClient2(str5);						
		
		String str6 = "rerllInitiative";
		sendToClient2(str6);
		
		
		if (numberOfPlayers == 5) {					
			
			String str7 = "Players are re-rolling...";
			sendToClient1(str7);
		}
	}
	
	public void double305() {
	
		initiativeRolled[3] = "no";
		initiativeRolled[0] = "no";
		initiativeRolled[5] = "no";
		
		client3RolledDouble = true;
		client0RolledDouble = true;
		hostRolledDouble = true;
		
		rollOff ="yes";
		rollOff5305 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Re-roll for inititiative...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient3(str);						
		
		String str2 = "rerllInitiative";
		sendToClient3(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient0(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient0(str4);
		
		
		if (numberOfPlayers == 4) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient1(str5);
		}
		else if (numberOfPlayers == 5) {
			
			String str5 = "Players are re-rolling...";
			sendToClient1(str5);			
			
			String str6 = "Players are re-rolling...";
			sendToClient2(str6);
		}
		
		
		hostRollsInitiative();
	}

	public void double312() {
	
		initiativeRolled[3] = "no";
		initiativeRolled[1] = "no";
		initiativeRolled[2] = "no";
		
		client3RolledDouble = true;
		client1RolledDouble = true;
		client2RolledDouble = true;
		
		rollOff ="yes";
		rollOff5312 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Players are re-rolling...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient3(str);						
		
		String str2 = "rerllInitiative";
		sendToClient3(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient1(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient1(str4);
		
		String str5 = "re-roll for inititiative...";
		sendToClient2(str5);						
		
		String str6 = "rerllInitiative";
		sendToClient2(str6);
		
		
		if (numberOfPlayers == 5) {					
			
			String str7 = "Players are re-rolling...";
			sendToClient0(str7);
		}
	}

	public void double315() {
		
		initiativeRolled[3] = "no";
		initiativeRolled[1] = "no";
		initiativeRolled[5] = "no";
		
		client3RolledDouble = true;
		client1RolledDouble = true;
		hostRolledDouble = true;
		
		rollOff ="yes";
		rollOff5315 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Re-roll for inititiative...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient3(str);						
		
		String str2 = "rerllInitiative";
		sendToClient3(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient1(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient1(str4);
		
		
		if (numberOfPlayers == 4) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient0(str5);
		}
		else if (numberOfPlayers == 5) {
			
			String str5 = "Players are re-rolling...";
			sendToClient0(str5);			
			
			String str6 = "Players are re-rolling...";
			sendToClient2(str6);
		}
		
		
		hostRollsInitiative();
	}
	
	public void double325() {
	
		initiativeRolled[3] = "no";
		initiativeRolled[2] = "no";
		initiativeRolled[5] = "no";
		
		client3RolledDouble = true;
		client2RolledDouble = true;
		hostRolledDouble = true;
		
		rollOff ="yes";
		rollOff5325 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Re-roll for inititiative...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient3(str);						
		
		String str2 = "rerllInitiative";
		sendToClient3(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient2(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient2(str4);
		
		
		if (numberOfPlayers == 4) {		
			
			String str5 = "Players are re-rolling...";
			sendToClient0(str5);
		}
		else if (numberOfPlayers == 5) {
			
			String str5 = "Players are re-rolling...";
			sendToClient0(str5);			
			
			String str6 = "Players are re-rolling...";
			sendToClient1(str6);
		}
		
		
		hostRollsInitiative();
	}
	
	public void double0125() {
	
		initiativeRolled[0] = "no";
		initiativeRolled[1] = "no";
		initiativeRolled[2] = "no";
		initiativeRolled[5] = "no";
		
		client0RolledDouble = true;
		client1RolledDouble = true;
		client2RolledDouble = true;
		hostRolledDouble = true;
		
		rollOff ="yes";
		rollOff40125 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Re-roll for inititiative...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient0(str);						
		
		String str2 = "rerllInitiative";
		sendToClient0(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient1(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient1(str4);
		
		String str5 = "re-roll for inititiative...";
		sendToClient2(str5);						
		
		String str6 = "rerllInitiative";
		sendToClient2(str6);
		
		
		if (numberOfPlayers == 5) {		
			
			String str7 = "Players are re-rolling...";
			sendToClient3(str7);
		}
	
		
		hostRollsInitiative();
	}

	public void double3012() {
	
		initiativeRolled[3] = "no";
		initiativeRolled[0] = "no";
		initiativeRolled[1] = "no";
		initiativeRolled[2] = "no";
		
		client3RolledDouble = true;
		client0RolledDouble = true;
		client1RolledDouble = true;
		client2RolledDouble = true;
		
		rollOff ="yes";
		rollOff53012 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Players are re-rolling...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient3(str);						
		
		String str2 = "rerllInitiative";
		sendToClient3(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient0(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient0(str4);
		
		String str5 = "re-roll for inititiative...";
		sendToClient1(str5);						
		
		String str6 = "rerllInitiative";
		sendToClient1(str6);
		
		String str7 = "re-roll for inititiative...";
		sendToClient2(str7);						
		
		String str8 = "rerllInitiative";
		sendToClient2(str8);
	}

	public void double3015() {
	
		initiativeRolled[3] = "no";
		initiativeRolled[0] = "no";
		initiativeRolled[1] = "no";
		initiativeRolled[5] = "no";
		
		client3RolledDouble = true;
		client0RolledDouble = true;
		client1RolledDouble = true;
		hostRolledDouble = true;
		
		rollOff ="yes";
		rollOff53015 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Re-roll for inititiative...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient3(str);						
		
		String str2 = "rerllInitiative";
		sendToClient3(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient0(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient0(str4);
		
		String str5 = "re-roll for inititiative...";
		sendToClient1(str5);						
		
		String str6 = "rerllInitiative";
		sendToClient1(str6);
		
		
		if (numberOfPlayers == 5) {		
			
			String str7 = "Players are re-rolling...";
			sendToClient2(str7);
		}

		
		hostRollsInitiative();
	}

	public void double3125() {
	
		initiativeRolled[3] = "no";
		initiativeRolled[1] = "no";
		initiativeRolled[2] = "no";
		initiativeRolled[5] = "no";
		
		client3RolledDouble = true;
		client1RolledDouble = true;
		client2RolledDouble = true;
		hostRolledDouble = true;
		
		rollOff ="yes";
		rollOff53125 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Re-roll for inititiative...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient3(str);						
		
		String str2 = "rerllInitiative";
		sendToClient3(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient1(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient1(str4);
		
		String str5 = "re-roll for inititiative...";
		sendToClient2(str5);						
		
		String str6 = "rerllInitiative";
		sendToClient2(str6);
		
		
		if (numberOfPlayers == 5) {		
			
			String str7 = "Players are re-rolling...";
			sendToClient0(str7);
		}
	
		
		hostRollsInitiative();
	}

	public void double3025() {
	
		initiativeRolled[3] = "no";
		initiativeRolled[0] = "no";
		initiativeRolled[2] = "no";
		initiativeRolled[5] = "no";
		
		client3RolledDouble = true;
		client0RolledDouble = true;
		client2RolledDouble = true;
		hostRolledDouble = true;
		
		rollOff ="yes";
		rollOff53025 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Re-roll for inititiative...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient3(str);						
		
		String str2 = "rerllInitiative";
		sendToClient3(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient0(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient0(str4);
		
		String str5 = "re-roll for inititiative...";
		sendToClient2(str5);						
		
		String str6 = "rerllInitiative";
		sendToClient2(str6);
		
		
		if (numberOfPlayers == 5) {		
			
			String str7 = "Players are re-rolling...";
			sendToClient1(str7);
		}
	
		
		hostRollsInitiative();
	}

	public void double30125() {
	
		initiativeRolled[3] = "no";
		initiativeRolled[0] = "no";
		initiativeRolled[1] = "no";
		initiativeRolled[2] = "no";
		initiativeRolled[5] = "no";
		
		client3RolledDouble = true;
		client0RolledDouble = true;
		client1RolledDouble = true;
		client2RolledDouble = true;
		hostRolledDouble = true;
		
		rollOff ="yes";
		rollOff530125 ="yes";
		
		runOnUiThread(new Runnable() {
		  	    @Override
		  	    public void run() {
		  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
		  	    	
		    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "Re-roll for inititiative...");						
		  	    }
		  	});
		
		String str = "re-roll for inititiative...";
		sendToClient3(str);						
		
		String str2 = "rerllInitiative";
		sendToClient3(str2);
		
		String str3 = "re-roll for inititiative...";
		sendToClient0(str3);						
		
		String str4 = "rerllInitiative";
		sendToClient0(str4);
		
		String str5 = "re-roll for inititiative...";
		sendToClient1(str5);						
		
		String str6 = "rerllInitiative";
		sendToClient1(str6);
		
		String str7 = "re-roll for inititiative...";
		sendToClient2(str7);						
		
		String str8 = "rerllInitiative";
		sendToClient2(str8);
	
		
		hostRollsInitiative();
	}
	
	
	public void hostRollsInitiative() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		//Moved below because it may have been calculating a new init (on a double) too fast before it got saved as initfinal.
		//determineInitiative();
		
		
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
	  	  	  			
	  	  	  			// Sets sixSidedBlank visible & enabled.
	  	  	  			sixSidedRollFromLeft();  	  	  			
	  		  			
	  	  	  			
		  		  		final Handler h5 = new Handler();
			  	  	  	h5.postDelayed(new Runnable() {
			  	  	  			
			  	  	  			// Does this thread help:?
				  	  	  		@Override
				  	  	  		public void run()
				  	  	  		{  	  			
				  	  	  			sixSidedWobbleStart(); 	  	  			
				  	  	  			
				  	  	  			
					  	  	  		determineInitiative();
					  	  	  		
					  	  	  		
					  	  	  		isSixSidedReadyToBeRolled = "yes";
					  	  	  		isInitiativeOver = "no";
					  	  	  		
				  	  	  			centerscrolltext.setVisibility(View.VISIBLE);
				  		  	  		centerscrolltext.startAnimation(animAlphaText);
				  		  			centerscrolltext.append("\n" + "> Please slide the die...");
				  		  			
				  		  			centerscrolltext.bringToFront();
				  		  			
				  		  			
				  		  			ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
				  		  			chatBlankButton.bringToFront();
				  		  			
				  		  			
				  		  			//playerCardStartFadeInFadeOut();
				  		  			//playerTurnBackgroundStart();			  		  			
				  		  			
				  		  			
				  		  			//issixsidedrolledforinitiative = "yes";
				  		  			isinitiativestarted = "yes";			  		  			
				  		  			onBackPressedOk = "yes";
				  		  			
				  		  			
				  		  			//preventinitiativediefromleaking = "off";
				  		  			
				  		  			
				  		  			//String str = "StartInitiative";
				  		  			//sendToAll(str);
				  		  			
				  		  			/* FOR TESTING
				  		  			 * String str2 = "ONE CLIENT TEST";
				  		  			 * sendToClient1(str2);						  		  			
				  		  			 */				  		  			
			  	  	  		}
			  	  	  	}, 750);
	  	  	  		}
	  	  	  	}, 1000);						
			}
  		});
		
	}
	
	
	public void deadMethod() {
		
	}
	
	
	public void doublesModifier() {
		
		if (hostRolledDouble) {
				
			doublesModifierOfInitiativeHost = " (" + ArrayOfInitiative.initiative[5] + ")";
		}		
		if (client0RolledDouble) {
			
			doublesModifierOfInitiativeClient0 = " (" + ArrayOfInitiative.initiative[0] + ")";
		}
		if (client1RolledDouble) {
			
			doublesModifierOfInitiativeClient1 = " (" + ArrayOfInitiative.initiative[1] + ")";
		}
		if (client2RolledDouble) {
			
			doublesModifierOfInitiativeClient2 = " (" + ArrayOfInitiative.initiative[2] + ")";
		}
		if (client3RolledDouble) {
			
			doublesModifierOfInitiativeClient3 = " (" + ArrayOfInitiative.initiative[3] + ")";
		}		
		
		
		displayInitiatives();		
	}
	
	public void displayInitiatives() {
		
		//FOR TESTING:
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		    	  			
	  			
  	  	    	
  	    		centerscrolltext.setVisibility(View.VISIBLE);
		  		//centerscrolltext.startAnimation(animAlphaText);  	    		
  	    		
  	    		
  	    		/*
  	    		centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + " rolls " + initiativeFinal[5] + ".");
  	    		
  	    		for (int i=0; i < (clientWorkers.size()); i++) {  	    			
  	    			
  	    			centerscrolltext.append("\n" + ArrayOfPlayers.player[i] + " rolls " + initiativeFinal[i] + ".");
  	    		}
  	    		*/
  	    		
  	    		
  	    		int host = (int) initiativeFinal[5];
	  	  		int client0 = (int) initiativeFinal[0];
	  	  		int client1 = (int) initiativeFinal[1];
	  	  		int client2 = (int) initiativeFinal[2];
	  	  		int client3 = (int) initiativeFinal[3];
	  	  		
  	    		
  	    		if (numberOfPlayers == 2) {
  	    			
  	    			centerscrolltext.append("\n" + "\n" + "Initiative Results:");
  	    			
  	    			final String str = "\n" + "Initiative Results:";
  	    			sendToClient0(str);	    			
  	    			
  	    			if (initiativeFinal[5] > initiativeFinal[0]) { 	    				
  	    				
  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	    				
  	    				final String str2 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	    				sendToClient0(str2);
  	    				final String str3 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	    				sendToClient0(str3);
  	    				
  	    				
  	    				String str4 = "PlayerName5 :" + ArrayOfPlayers.player[5];
  	    				sendToClient0(str4);
  	    				
  	    				
  	    				ArrayOfHitPoints.hitpoints[0] = 20;//20
  	    				
  	    				
  	    				firstsubscript = 5;
	  	  				secondsubscript = 0;
	  	  				
	  	  				String str5 = "FirstSubscript :" + 5;
	  	  				sendToAllClients(str5);
	  	  				
	  	  				String str6 = "Secondsbscript :" + 0;
	  	  				sendToAllClients(str6);
  	    			}
  	    			else if (initiativeFinal[0] > initiativeFinal[5]) {
  	    				
  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	    				
  	    				final String str2 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	    				sendToClient0(str2);
  	    				final String str3 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	    				sendToClient0(str3);
  	    				
  	    				
  	    				String str4 = "PlayerName5 :" + ArrayOfPlayers.player[5];
  	    				sendToClient0(str4);
  	    				
  	    				
  	    				ArrayOfHitPoints.hitpoints[0] = 20;//20
  	    				
  	    				
  	    				firstsubscript = 0;
	  	  				secondsubscript = 5;
	  	  				
	  	  				String str5 = "FirstSubscript :" + 0;
	  	  				sendToAllClients(str5);
	  	  				
	  	  				String str6 = "Secondsbscript :" + 5;
	  	  				sendToAllClients(str6);
  	    			}
  	    			
  	    			final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
		  	  	  			finishInitiative();
		  	  	  			
		  	  	  			String str = "finishInitiative";
		  	  	  			sendToClient0(str);
		  	  	  			
		  	  	  			//gameEngine();
			  	  	  	}
		  	  	  	}, 3000);
  	    		}
  	    		else if (numberOfPlayers == 3) {
  	    			
  	    			centerscrolltext.append("\n" + "\n" + "Initiative Results:");
  	    			
  	    			final String str = "\n" + "Initiative Results:";
  	    			sendToClient0(str);
  	    			
  	    			final String str8 = "\n" + "Initiative Results:";
  	    			sendToClient1(str8);
  	    			
  	    			
  	    			if ((initiativeFinal[5] > initiativeFinal[0]) && (initiativeFinal[5] > initiativeFinal[1])) {
  	    				
  	    				if (initiativeFinal[0] > initiativeFinal[1]) {
  	    					
  	    					centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	    				
  	  	    				final String str2 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	    				sendToClient0(str2);
  	  	    				final String str3 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	    				sendToClient0(str3);
  	  	    				final String str4 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	    				sendToClient0(str4);
  	  	    				
  	  	    				final String str5 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	    				sendToClient1(str5);
	  	    				final String str6 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	    				sendToClient1(str6);
	  	    				final String str7 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	    				sendToClient1(str7);
  	    				}
  	    				else if (initiativeFinal[1] > initiativeFinal[0]) {
  	    					
  	    					centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	    				
  	  	    				final String str2 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	    				sendToClient0(str2);
  	  	    				final String str3 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	    				sendToClient0(str3);
  	  	    				final String str4 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	    				sendToClient0(str4);
  	  	    				
  	  	    				final String str5 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	    				sendToClient1(str5);
	  	    				final String str6 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	    				sendToClient1(str6);
	  	    				final String str7 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	    				sendToClient1(str7);
  	    				} 	    				
  	    			}
  	    			else if ((initiativeFinal[0] > initiativeFinal[5]) && (initiativeFinal[0] > initiativeFinal[1])) {
  	    				
  	    				if (initiativeFinal[5] > initiativeFinal[1]) {
  	    					
  	    					centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	    				
  	  	    				final String str2 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	    				sendToClient0(str2);
  	  	    				final String str3 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	    				sendToClient0(str3);
  	  	    				final String str4 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	    				sendToClient0(str4);
  	  	    				
  	  	    				final String str5 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	    				sendToClient1(str5);
	  	    				final String str6 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	    				sendToClient1(str6);
	  	    				final String str7 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	    				sendToClient1(str7);
  	    				}
  	    				else if (initiativeFinal[1] > initiativeFinal[5]) {
  	    					
  	    					centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	    				
  	  	    				final String str2 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	    				sendToClient0(str2);
  	  	    				final String str3 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	    				sendToClient0(str3);
  	  	    				final String str4 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	    				sendToClient0(str4);
  	  	    				
  	  	    				final String str5 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	    				sendToClient1(str5);
	  	    				final String str6 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	    				sendToClient1(str6);
	  	    				final String str7 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	    				sendToClient1(str7);
  	    				}
  	    			}
  	    			else if ((initiativeFinal[1] > initiativeFinal[5]) && (initiativeFinal[1] > initiativeFinal[0])) {
  	    				
  	    				if (initiativeFinal[5] > initiativeFinal[0]) {
  	    					
  	    					centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	    				
  	  	    				final String str2 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	    				sendToClient0(str2);
  	  	    				final String str3 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	    				sendToClient0(str3);
  	  	    				final String str4 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	    				sendToClient0(str4);
  	  	    				
  	  	    				final String str5 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	    				sendToClient1(str5);
	  	    				final String str6 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	    				sendToClient1(str6);
	  	    				final String str7 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	    				sendToClient1(str7);
  	    				}
  	    				else if (initiativeFinal[0] > initiativeFinal[5]) {
  	    					
  	    					centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	    				
  	  	    				final String str2 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	    				sendToClient0(str2);
  	  	    				final String str3 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	    				sendToClient0(str3);
  	  	    				final String str4 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	    				sendToClient0(str4);
  	  	    				
  	  	    				final String str5 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	    				sendToClient1(str5);
	  	    				final String str6 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	    				sendToClient1(str6);
	  	    				final String str7 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	    				sendToClient1(str7);
  	    				}
  	    			}
  	    			
  	    			final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
			  	  	  		finishInitiative();
		  	  	  			
		  	  	  			String str = "finishInitiative";
		  	  	  			sendToClient0(str);
		  	  	  			
		  	  	  			//gameEngine();
			  	  	  	}
		  	  	  	}, 3000);
  	    		}
  	    		else if (numberOfPlayers == 4) {
  	    			
  	    			centerscrolltext.append("\n" + "\n" + "Initiative Results:");
  	    			
  	    			final String str = "\n" + "Initiative Results:";
  	    			sendToClient0(str);
  	    			
  	    			final String str14 = "\n" + "Initiative Results:";
  	    			sendToClient1(str14);
  	    			
  	    			final String str15 = "\n" + "Initiative Results:";
  	    			sendToClient2(str15);
  	    			
  	    			
  	    			if ((initiativeFinal[5] > initiativeFinal[0]) && (initiativeFinal[5] > initiativeFinal[1]) && (initiativeFinal[5] > initiativeFinal[2])) {
  	    				
  	    				if ((initiativeFinal[0] > initiativeFinal[1]) && (initiativeFinal[0] > initiativeFinal[2])) {
  	    					
  	    					if ((initiativeFinal[1] > initiativeFinal[2])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str13);
  	    					}
  	    					else if ((initiativeFinal[2] > initiativeFinal[1])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str13);
  	    					}
  	    				}
  	    				else if ((initiativeFinal[1] > initiativeFinal[0]) && (initiativeFinal[1] > initiativeFinal[2])) {
  	    					
  	    					if ((initiativeFinal[0] > initiativeFinal[2])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str13);
  	    					}
  	    					else if ((initiativeFinal[2] > initiativeFinal[0])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str13);
  	    					}
  	    				}
  	    				else if ((initiativeFinal[2] > initiativeFinal[0]) && (initiativeFinal[2] > initiativeFinal[1])) {
  	    					
  	    					if ((initiativeFinal[0] > initiativeFinal[1])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str13);
  	    					}
  	    					else if ((initiativeFinal[1] > initiativeFinal[0])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str13);
  	    					}
  	    				}
  	    			}
  	    			else if ((initiativeFinal[0] > initiativeFinal[5]) && (initiativeFinal[0] > initiativeFinal[1]) && (initiativeFinal[0] > initiativeFinal[2])) {
  	    				
  	    				if ((initiativeFinal[5] > initiativeFinal[1]) && (initiativeFinal[5] > initiativeFinal[2])) {
  	    					
  	    					if ((initiativeFinal[1] > initiativeFinal[2])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str13);
  	    					}
  	    					else if ((initiativeFinal[2] > initiativeFinal[1])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str13);
  	    					}
  	    				}
  	    				else if ((initiativeFinal[1] > initiativeFinal[5]) && (initiativeFinal[1] > initiativeFinal[2])) {
  	    					
  	    					if ((initiativeFinal[5] > initiativeFinal[2])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str13);
  	    					}
  	    					else if ((initiativeFinal[2] > initiativeFinal[5])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str13);
  	    					}
  	    				}
  	    				else if ((initiativeFinal[2] > initiativeFinal[5]) && (initiativeFinal[2] > initiativeFinal[1])) {
  	    					
  	    					if ((initiativeFinal[5] > initiativeFinal[1])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str13);
  	    					}
  	    					else if ((initiativeFinal[1] > initiativeFinal[5])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str13);
  	    					}
  	    				}
  	    			}
  	    			else if ((initiativeFinal[1] > initiativeFinal[5]) && (initiativeFinal[1] > initiativeFinal[0]) && (initiativeFinal[1] > initiativeFinal[2])) {
  	    				
  	    				if ((initiativeFinal[5] > initiativeFinal[0]) && (initiativeFinal[5] > initiativeFinal[2])) {
  	    					
  	    					if ((initiativeFinal[0] > initiativeFinal[2])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str13);
  	    					}
  	    					else if ((initiativeFinal[2] > initiativeFinal[0])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str13);
  	    					}
  	    				}
  	    				else if ((initiativeFinal[0] > initiativeFinal[5]) && (initiativeFinal[0] > initiativeFinal[2])) {
  	    					
  	    					if ((initiativeFinal[5] > initiativeFinal[2])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str13);
  	    					}
  	    					else if ((initiativeFinal[2] > initiativeFinal[5])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str13);
  	    					}
  	    				}
  	    				else if ((initiativeFinal[2] > initiativeFinal[5]) && (initiativeFinal[2] > initiativeFinal[0])) {
  	    					
  	    					if ((initiativeFinal[5] > initiativeFinal[0])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str13);
  	    					}
  	    					else if ((initiativeFinal[0] > initiativeFinal[5])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str13);
  	    					}
  	    				}
  	    			}
  	    			else if ((initiativeFinal[2] > initiativeFinal[5]) && (initiativeFinal[2] > initiativeFinal[0]) && (initiativeFinal[2] > initiativeFinal[1])) {
  	    				
  	    				if ((initiativeFinal[5] > initiativeFinal[0]) && (initiativeFinal[5] > initiativeFinal[1])) {
  	    					
  	    					if ((initiativeFinal[0] > initiativeFinal[1])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str13);
  	    					}
  	    					else if ((initiativeFinal[1] > initiativeFinal[0])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str13);
  	    					}
  	    				}
  	    				else if ((initiativeFinal[0] > initiativeFinal[5]) && (initiativeFinal[0] > initiativeFinal[1])) {
  	    					
  	    					if ((initiativeFinal[5] > initiativeFinal[1])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str13);
  	    					}
  	    					else if ((initiativeFinal[1] > initiativeFinal[5])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str13);
  	    					}
  	    				}
  	    				else if ((initiativeFinal[1] > initiativeFinal[5]) && (initiativeFinal[1] > initiativeFinal[0])) {
  	    					
  	    					if ((initiativeFinal[5] > initiativeFinal[0])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str13);
  	    					}
  	    					else if ((initiativeFinal[0] > initiativeFinal[5])) {
  	    						
  	    						centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0);
  	  	  	    				centerscrolltext.append("\n" + ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost);
  	  	  	    				
  	  	  	    				final String str2 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
  	  	  	    				sendToClient0(str2);
  	  	  	    				final String str3 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
  	  	  	    				sendToClient0(str3);
  	  	  	    				final String str4 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
  	  	  	    				sendToClient0(str4);
  	  	  	    				final String str5 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient0(str5);
	  	  	    				
	  	  	    				final String str6 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient1(str6);
	  	  	    				final String str7 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient1(str7);
	  	  	    				final String str8 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient1(str8);
	  	  	    				final String str9 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient1(str9);
	  	  	    				
	  	  	    				final String str10 = ArrayOfPlayers.player[2] + "..." + client2 + doublesModifierOfInitiativeClient2;
	  	  	    				sendToClient2(str10);
	  	  	    				final String str11 = ArrayOfPlayers.player[1] + "..." + client1 + doublesModifierOfInitiativeClient1;
	  	  	    				sendToClient2(str11);
	  	  	    				final String str12 = ArrayOfPlayers.player[0] + "..." + client0 + doublesModifierOfInitiativeClient0;
	  	  	    				sendToClient2(str12);
	  	  	    				final String str13 = ArrayOfPlayers.player[5] + "..." + host + doublesModifierOfInitiativeHost;
	  	  	    				sendToClient2(str13);
  	    					}
  	    				}
  	    			}
  	    			
  	    			final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
			  	  	  		finishInitiative();
		  	  	  			
		  	  	  			String str = "finishInitiative";
		  	  	  			sendToClient0(str);
		  	  	  			
		  	  	  			//gameEngine();
			  	  	  	}
		  	  	  	}, 3000);
  	    		}
  	    		else if (numberOfPlayers == 5) {
  	    			
  	    			//JUST 4 PLAYERS??????
  	    		}  	    							
  	  	    }
  	  	});			
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
		
		String str = "> Let the battle begin...";
		//sendToClient0(str);		
		sendToAllClients(str);				
		
		/*
		if (numberOfPlayers == 2) {
			
			ArrayOfHitPoints.hitpoints[0] = 20;//20
			
			
			if (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) {
				
				firstsubscript = 5;
				secondsubscript = 0;
				
				String str3 = "firstsubscript :" + 5;
				sendToAllClients(str3);
				
				String str4 = "secondsubscript :" + 0;
				sendToAllClients(str4);
			}
			
			else if (ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) {
				
				firstsubscript = 0;
				secondsubscript = 5;
				
				String str3 = "firstsubscript :" + 0;
				sendToAllClients(str3);
				
				String str4 = "secondsubscript :" + 5;
				sendToAllClients(str4);
			}			
		}
		*/
		
		myInitiativeTransition();
		
		//String str2 = "myInitiativeTransition";
		//sendToAllClients(str2);
	  	  		
	  	  		
  	  	final Handler h = new Handler();
  		h.postDelayed(new Runnable() {		  	  	  			
  	  			
  	  		@Override
  	  	  	public void run() {
  	  			
  	  			TextView titleinitiativetext = (TextView) findViewById(R.id.textviewtitleinitiativetext);
  	  			titleinitiativetext.setVisibility(View.INVISIBLE);
  	  			
  	  			
  	  			// NOTICE THE append's BELOW, JUST FOR INTRO (USED setText IN TITLE BUTTON):
  	  			final TableLayout summaryTableLayout = (TableLayout) findViewById(R.id.summaryTable);
  	  			final View lineInSummaryTableLayout = (View) findViewById(R.id.line);	  	  		
  	  			
	  	  		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  	  		//titlerulestext.setTypeface(typeFace);
	  			
				//titletext.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(100));
					
	  	  		summaryTableLayout.setVisibility(View.VISIBLE);
	  	  		//summaryTableLayout.setMovementMethod(new ScrollingMovementMethod());
	  	  		lineInSummaryTableLayout.setVisibility(View.VISIBLE);
	  	  		/*
	  	  		titlerulestext.append("Quick Instructions" + "\n" + "==================" + "\n" +
	  	  		"1. Determine who goes first (Initiative)." + "\n" + 
	  	  		"2. Take an action when prompted." + "\n" +
	  	  		"3. Win by reducing opponent's hit points to zero or below.");
	  	  		*/
	  	  		
		  	  	TextView statisticstextview = (TextView) findViewById(R.id.summarytext);
	  	  		statisticstextview.setTypeface(typeFace);
	  	  		statisticstextview.append("Player Summary");
	  	  		statisticstextview.setVisibility(View.VISIBLE);
	  	  		
		  	  	TextView blanktextview = (TextView) findViewById(R.id.blanktext);
		  	  	blanktextview.setTypeface(typeFace);
		  	  	blanktextview.append("");
		  	  	blanktextview.setVisibility(View.VISIBLE);
	  	  		
	  	  		TextView hitpointstextview = (TextView) findViewById(R.id.hitpointstext);
	  	  		hitpointstextview.setTypeface(typeFace);
	  	  		hitpointstextview.append("HP");
	  	  		hitpointstextview.setVisibility(View.VISIBLE);
	  	  		
		  	  	TextView blesstextview = (TextView) findViewById(R.id.blesstext);
		  	  	blesstextview.setTypeface(typeFace);
		  	  	blesstextview.append("Bless");
		  	  	blesstextview.setVisibility(View.VISIBLE);
	  	  		
		  	  	TextView curetextview = (TextView) findViewById(R.id.curetext);
		  	  	curetextview.setTypeface(typeFace);
		  	  	curetextview.append("Cure");
		  	  	curetextview.setVisibility(View.VISIBLE);
	  	  		
		  	  	TextView dodgetextview = (TextView) findViewById(R.id.dodgetext);
		  	  	dodgetextview.setTypeface(typeFace);
		  	  	dodgetextview.append("Dodge");
		  	  	dodgetextview.setVisibility(View.VISIBLE);
	  	  		
		  	  	TextView mightyblowtextview = (TextView) findViewById(R.id.mightyblowtext);
		  	  	mightyblowtextview.setTypeface(typeFace);
		  	  	mightyblowtextview.append("MB");
		  	  	mightyblowtextview.setVisibility(View.VISIBLE);
		  	  	
		  	  	TextView hastetextview = (TextView) findViewById(R.id.hastetext);
		  	  	hastetextview.setTypeface(typeFace);
		  	  	hastetextview.append("Haste");
		  	  	hastetextview.setVisibility(View.VISIBLE);
		  	  	
	  	  		
		  	  	TextView player1textview = (TextView) findViewById(R.id.player1);
		  	  	player1textview.setTypeface(typeFace);
		  	  	player1textview.append(ArrayOfPlayers.player[firstsubscript]);
		  	  	player1textview.setVisibility(View.VISIBLE);
		  	  	
		  	  	TextView hitpointsplayer1textview = (TextView) findViewById(R.id.hitpointsplayer1);
		  	  	hitpointsplayer1textview.setTypeface(typeFace);
		  	  	String hitpointsplayer1String = Integer.toString(ArrayOfHitPoints.hitpoints[firstsubscript]);
		  	  	hitpointsplayer1textview.append(hitpointsplayer1String);
		  		hitpointsplayer1textview.setVisibility(View.VISIBLE);
		  	  	
		  	  	TextView blessplayer1textview = (TextView) findViewById(R.id.blessplayer1);
		  	  	blessplayer1textview.setTypeface(typeFace);
		  	  	String blessplayer1String = Integer.toString(blessSpell[firstsubscript]);
		  	  	blessplayer1textview.append(blessplayer1String);
		  	  	blessplayer1textview.setVisibility(View.VISIBLE);
		  	  	
		  	  	TextView cureplayer1textview = (TextView) findViewById(R.id.cureplayer1);
		  	  	cureplayer1textview.setTypeface(typeFace);
		  	  	String cureplayer1String = Integer.toString(cureSpell[firstsubscript]);
		  	  	cureplayer1textview.append(cureplayer1String);
		  	  	cureplayer1textview.setVisibility(View.VISIBLE);
		  	  	
		  	  	TextView dodgeplayer1textview = (TextView) findViewById(R.id.dodgeplayer1);
		  	  	dodgeplayer1textview.setTypeface(typeFace);
		  	  	String dodgeplayer1String = Integer.toString(dodgeBlowSpell[firstsubscript]);
		  	  	dodgeplayer1textview.append(dodgeplayer1String);
		  	  	dodgeplayer1textview.setVisibility(View.VISIBLE);
		  	  	
		  	  	TextView mightyblowplayer1textview = (TextView) findViewById(R.id.mightyblowplayer1);
		  	  	mightyblowplayer1textview.setTypeface(typeFace);
		  	  	String mightyblowplayer1String = Integer.toString(mightyBlowSpell[firstsubscript]);
		  	  	mightyblowplayer1textview.append(mightyblowplayer1String);
		  	  	mightyblowplayer1textview.setVisibility(View.VISIBLE);
		  	  	
		  	  	TextView hasteplayer1textview = (TextView) findViewById(R.id.hasteplayer1);
		  	  	hasteplayer1textview.setTypeface(typeFace);
		  	  	String hasteplayer1String = Integer.toString(hasteSpell[firstsubscript]);
		  	  	hasteplayer1textview.append(hasteplayer1String);
		  	  	hasteplayer1textview.setVisibility(View.VISIBLE);  	
		  	  	
		  	  	
		  	  	TextView player2textview = (TextView) findViewById(R.id.player2);
		  	  	player2textview.setTypeface(typeFace);
		  	  	player2textview.append(ArrayOfPlayers.player[secondsubscript]);
		  	  	player2textview.setVisibility(View.VISIBLE);		  	  	
		  	  	
		  	  	TextView hitpoints2textview = (TextView) findViewById(R.id.hitpointsplayer2);
		  	  	hitpoints2textview.setTypeface(typeFace);
		  	  	String hitpoints2String = Integer.toString(ArrayOfHitPoints.hitpoints[secondsubscript]);
		  	  	hitpoints2textview.append(hitpoints2String);
		  	  	hitpoints2textview.setVisibility(View.VISIBLE);
		  	  	
		  	  	TextView blessplayer2textview = (TextView) findViewById(R.id.blessplayer2);
		  	  	blessplayer2textview.setTypeface(typeFace);
		  	  	String blessplayer2String = Integer.toString(blessSpell[secondsubscript]);
		  	  	blessplayer2textview.append(blessplayer2String);
		  	  	blessplayer2textview.setVisibility(View.VISIBLE);
		  	  	
		  	  	TextView cureplayer2textview = (TextView) findViewById(R.id.cureplayer2);
		  	  	cureplayer2textview.setTypeface(typeFace);
		  	  	String cureplayer2String = Integer.toString(cureSpell[secondsubscript]);
		  	  	cureplayer2textview.append(cureplayer2String);
		  	  	cureplayer2textview.setVisibility(View.VISIBLE);
		  	  	
		  	  	TextView dodgeplayer2textview = (TextView) findViewById(R.id.dodgeplayer2);
		  	  	dodgeplayer2textview.setTypeface(typeFace);
		  	  	String dodgeplayer2String = Integer.toString(dodgeBlowSpell[secondsubscript]);
		  	  	dodgeplayer2textview.append(dodgeplayer2String);
		  	  	dodgeplayer2textview.setVisibility(View.VISIBLE);
		  	  	
		  	  	TextView mightyblowplayer2textview = (TextView) findViewById(R.id.mightyblowplayer2);
		  	  	mightyblowplayer2textview.setTypeface(typeFace);
		  	  	String mightyblowplayer2String = Integer.toString(mightyBlowSpell[secondsubscript]);
		  	  	mightyblowplayer2textview.append(mightyblowplayer2String);
		  	  	mightyblowplayer2textview.setVisibility(View.VISIBLE);
		  	  	
		  	  	TextView hasteplayer2textview = (TextView) findViewById(R.id.hasteplayer2);
		  	  	hasteplayer2textview.setTypeface(typeFace);
		  	  	String hasteplayer2String = Integer.toString(hasteSpell[secondsubscript]);
		  	  	hasteplayer2textview.append(hasteplayer2String);
		  	  	hasteplayer2textview.setVisibility(View.VISIBLE);
	  	  		
	  	  		
	  	  		//titlerulestext.bringToFront();
		  	  	
		  	  	
		  	  	ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
				chatBlankButton.bringToFront();
	  	  		
  	  			  	  			
	  	  		final Handler h3 = new Handler();
		  	  	h3.postDelayed(new Runnable() {
	
		  	  		@Override
		  	  		public void run()
		  	  		{
		  	  			//titlerulestext.setVisibility(View.INVISIBLE);
		  	  			summaryTableLayout.setVisibility(View.INVISIBLE);		  	  			
			  	  		
		  	  	  		lineInSummaryTableLayout.setVisibility(View.INVISIBLE);
		  	  			
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
		  	  	}, 10325); // FINAGELED!	  	  			
  	  	  	}
  	  	}, 675); // SHOULD BE AT LEAST 675? WAS 525 		  	    	  	  			  	  			
	
	  	//Toast.makeText(MainActivity2.this,"isinitiativestarted = " +  isinitiativestarted + " aretheredoubles = " + aretheredoubles, Toast.LENGTH_SHORT).show();  	 	
	  	  		
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
		
		issixsidedrolledforinitiative = "yes";
		
		aretheredoubles = "no";
		
		
		if (numberOfPlayers == 2) {
			
			//issixsidedrolledforinitiative = "yes";
			
			
			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
			
			TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
			computerNameTextView.setTypeface(typeFace);
			computerNameTextView.setText(ArrayOfPlayers.player[0]);
			//computerNameTextView.setVisibility(View.INVISIBLE);

			//ArrayOfHitPoints.hitpoints[0] = 20;//20
			final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
			computerHitPointsTextView.setTypeface(typeFace);
			//computerHitPointsTextView.setVisibility(View.INVISIBLE);
			computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));

			ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
			clientAvatar.setVisibility(View.VISIBLE);
			
			
			unfoldRightScroll();
			
			
			//String str = "PlayerName5 :" + ArrayOfPlayers.player[5];
			//sendToClient0(str);			
			
			
			String str2 = "GameEngine2Player";
			sendToClient0(str2);			
			
			
			String str3 = "playerNumberAttacked :" + 5;
			sendToClient0(str3);
			
			
			//String str4 = "numberOfPlayers :" + 2;
			//sendToClient0(str4);
			
			
			playerDeadYet[5] = "no";
			playerDeadYet[0] = "no";
			
			String str4 = "playerDeadYet5 :" + "no";
			sendToClient0(str4);
			
			String str5 = "playerDeadYet0 :" + "no";
			sendToClient0(str5);
			
			
			final int gameOn = 1;			
			
			ArrayOfTurn.turn[0] = 0;			
			
			playerNumberAttacked = 0;
			
		    
		    turn();
		}
		
		else if (numberOfPlayers == 3) {
			
			ArrayOfHitPoints.hitpoints[0] = 20;
			ArrayOfHitPoints.hitpoints[1] = 20;
			
			playerDeadYet[5] = "no";
			playerDeadYet[0] = "no";
			playerDeadYet[1] = "no";
			
			String str4 = "playerDeadYet5 :" + "no";
			sendToAllClients(str4);
			
			String str5 = "playerDeadYet0 :" + "no";
			sendToAllClients(str5);
			
			String str6 = "playerDeadYet1 :" + "no";
			sendToAllClients(str6);
			
			
			final int gameOn = 1;			
			
			ArrayOfTurn.turn[0] = 0;			
		    
		    turn();
		}
		
		else if (numberOfPlayers == 4) {
			
			ArrayOfHitPoints.hitpoints[0] = 20;
			ArrayOfHitPoints.hitpoints[1] = 20;
			ArrayOfHitPoints.hitpoints[2] = 20;
			
			playerDeadYet[5] = "no";
			playerDeadYet[0] = "no";
			playerDeadYet[1] = "no";
			playerDeadYet[2] = "no";
			
			String str4 = "playerDeadYet5 :" + "no";
			sendToAllClients(str4);
			
			String str5 = "playerDeadYet0 :" + "no";
			sendToAllClients(str5);
			
			String str6 = "playerDeadYet1 :" + "no";
			sendToAllClients(str6);
			
			String str7 = "playerDeadYet2 :" + "no";
			sendToAllClients(str7);
			
			
			final int gameOn = 1;			
			
			ArrayOfTurn.turn[0] = 0;			
		    
		    turn();
		}
		
		else if (numberOfPlayers == 5) {
			
			ArrayOfHitPoints.hitpoints[0] = 20;
			ArrayOfHitPoints.hitpoints[1] = 20;
			ArrayOfHitPoints.hitpoints[2] = 20;
			ArrayOfHitPoints.hitpoints[3] = 20;
			
			playerDeadYet[5] = "no";
			playerDeadYet[0] = "no";
			playerDeadYet[1] = "no";
			playerDeadYet[2] = "no";
			playerDeadYet[3] = "no";
			
			String str4 = "playerDeadYet5 :" + "no";
			sendToAllClients(str4);
			
			String str5 = "playerDeadYet0 :" + "no";
			sendToAllClients(str5);
			
			String str6 = "playerDeadYet1 :" + "no";
			sendToAllClients(str6);
			
			String str7 = "playerDeadYet2 :" + "no";
			sendToAllClients(str7);
			
			String str8 = "playerDeadYet3 :" + "no";
			sendToAllClients(str8);
			
			
			final int gameOn = 1;		
			
			ArrayOfTurn.turn[0] = 0;			
		    
		    turn();
		}		
		//FOR TESTING:
		/*
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			centerscrolltext.setVisibility(View.VISIBLE);
		  		
	  			
	  			
	  			//for (int i=0; i < 6; i++) {
	  			//	centerscrolltext.append("\n" + ArrayOfPlayers.player[i] + " " + ArrayOfID.id[i]);//+ "\n"
	  			//}
	  				  			
	  			centerscrolltext.append("\n" + "Total clients = "+ idCounter);
  	  	    }
  		});
  		*/				
		
		/*
		for (int i=0; i<ArrayOfInvites.invites[0]; i++) {			
			Toast.makeText(Host.this, ArrayOfPlayers.player[i] + " " + ArrayOfID.id[i], Toast.LENGTH_LONG).show();			
		}
		*/				
	}
	
	public void turn() {
		
		if (numberOfPlayers == 2) {			
			
			if (initiativeFinal[5] > initiativeFinal[0]) {
				
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
						
						
						computerCardStopFadeInFadeOut();
		    			playerCardStartFadeInFadeOut();
		    			
		    			
		    			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		    			
		    			//NEED THIS??
		    			TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
		    			playerHitPointsTextView.setTypeface(typeFace);
		    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
		    			//Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);
		    			//playerHitPointsTextView.startAnimation(animPulsingAnimation);
						
		    			
		    			String str = "trn1V150";//2 PLAYER(1V1), HOST GOES FIRST(50)
		    			sendToClient0(str);
		    			
						
						ArrayOfTurn.turn[0] = ArrayOfTurn.turn[0] + 1;	
						
						
						gameEngine1V1501();//PART 1
		  	  	    }
		  		});
			}
			else if (initiativeFinal[0] > initiativeFinal[5]) {
				
				runOnUiThread(new Runnable() {
		  	  	    @Override
		  	  	    public void run() {
				
						// Use a blank drawable to hide the imageview animation:
						// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
						ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
						img1.setBackgroundResource(R.drawable.twentytwentyblank);
						img1.setImageResource(R.drawable.twentytwentyblank);
						
						/*
						//TEST:
						img1.bringToFront();
						*/
		
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
		    			computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
		    			//Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);				
		    			//computerHitPointsTextView.startAnimation(animPulsingAnimation);
		    			
		    			/*
		    			//TEST:
						ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);				
						img.bringToFront();
		    			*/
		    			
		    			String str = "turn1V105";//2 PLAYER(1V1), CLIENT GOES FIRST(05)
		    			sendToClient0(str);
		    			
		    			
		    			ArrayOfTurn.turn[0] = ArrayOfTurn.turn[0] + 1;
		    			
		    			
		    			gameEngine1V1051();//PART 1
		  	  	    }
		  		});
			}			
		}
		
		else if (numberOfPlayers == 3) {//WILL NEED DIALOG TO PICK OPPONENT
			
			if (initiativeFinal[5] > initiativeFinal[0] && initiativeFinal[5] > initiativeFinal[1]) {
				
				if (initiativeFinal[0] > initiativeFinal[1]) {
					
				}
				else if (initiativeFinal[1] > initiativeFinal[0]) {
					
				}				
			}
			
			else if (initiativeFinal[0] > initiativeFinal[5] && initiativeFinal[0] > initiativeFinal[1]) {
				
				if (initiativeFinal[5] > initiativeFinal[1]) {
					
				}
				else if (initiativeFinal[1] > initiativeFinal[5]) {
					
				}				
			}
			
			else if (initiativeFinal[1] > initiativeFinal[5] && initiativeFinal[1] > initiativeFinal[0]) {
				
				if (initiativeFinal[5] > initiativeFinal[0]) {
					
				}
				else if (initiativeFinal[0] > initiativeFinal[5]) {
					
				}				
			}			
		}
		//NOT FINISHED WITH BELOW:
		else if (numberOfPlayers == 4) {
			
			if (initiativeFinal[5] > initiativeFinal[0] && initiativeFinal[5] > initiativeFinal[1] && initiativeFinal[5] > initiativeFinal[2]) {
				
								
			}
			
			else if (initiativeFinal[0] > initiativeFinal[5] && initiativeFinal[0] > initiativeFinal[1] && initiativeFinal[0] > initiativeFinal[2]) {
				
								
			}
			
			else if (initiativeFinal[1] > initiativeFinal[5] && initiativeFinal[1] > initiativeFinal[0] && initiativeFinal[1] > initiativeFinal[2]) {
				
								
			}
			
			else if (initiativeFinal[2] > initiativeFinal[5] && initiativeFinal[2] > initiativeFinal[0] && initiativeFinal[2] > initiativeFinal[1]) {
				
				
			}			
		}
		
		else if (numberOfPlayers == 5) {
			
			if (initiativeFinal[5] > initiativeFinal[0] && initiativeFinal[5] > initiativeFinal[1] && initiativeFinal[5] > initiativeFinal[2] && initiativeFinal[5] > initiativeFinal[3]) {
				
				
			}
			
			else if (initiativeFinal[0] > initiativeFinal[5] && initiativeFinal[0] > initiativeFinal[1] && initiativeFinal[0] > initiativeFinal[2] && initiativeFinal[0] > initiativeFinal[3]) {
				
								
			}
			
			else if (initiativeFinal[1] > initiativeFinal[5] && initiativeFinal[1] > initiativeFinal[0] && initiativeFinal[1] > initiativeFinal[2] && initiativeFinal[1] > initiativeFinal[3]) {
				
								
			}
			
			else if (initiativeFinal[2] > initiativeFinal[5] && initiativeFinal[2] > initiativeFinal[0] && initiativeFinal[2] > initiativeFinal[1] && initiativeFinal[2] > initiativeFinal[3]) {
				
				
			}
			
			else if (initiativeFinal[3] > initiativeFinal[5] && initiativeFinal[3] > initiativeFinal[0] && initiativeFinal[3] > initiativeFinal[1] && initiativeFinal[3] > initiativeFinal[2]) {
				
				
			}			
		}		
	}
	
	
	public void gameEngine1V1501() {		
		
		//final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		issecondroundofhasteused = "no"; // SO HUMIE CAN USE MB AFTER THE 2RD ROUND OF A HASTE.
		
		if (canHasDisarmed[0].equals("yes") && didHumanCriticalMiss[0].equals("yes") && disarmedTurnStart[0] + 3 == ArrayOfTurn.turn[0]) {//CHANGED FROM 3 TO 2, SHOULD BE 3?
			
			canHasDisarmed[0] = "no";
			
			String str = "CanHasDisarmed0 :" + "no";
			sendToClient0(str);
			
			
			TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
	  		disarmedtextright.setVisibility(View.INVISIBLE);
	  		
	  		String str2 = "clientNotDisarmed";
			sendToClient0(str2);	  		
			
			
			didHumanCriticalMiss[0] = "no";
			
			String str3 = "DidHumanCriticalMiss0 :" + "no";
			sendToClient0(str3);
		}	
		
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() { 	  	    	
  	  	    	
  	  	    	//ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
	  			//chatBlankButton.bringToFront();
  	  	    	
  	  	    	
  	  	    	displayTurn();
  	  	    	
  	  	    	//int turnVariable = ArrayOfTurn.turn[0];
  	  	    	
	  	  	    String str4 = "Turn :" + ArrayOfTurn.turn[0];
				sendToClient0(str4);
  	  	    	
	  	  	    String str5 = "displayTrn";
				sendToClient0(str5);
  	  	    	
  	  	    	/*
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
		  		centerscrolltext.append("\n" + " >>>>>>>>>>>   " + " Turn " + ArrayOfTurn.turn[0] + "   <<<<<<<<<<<");				
		  		//centerscrolltext.append("\n");
				*/
  	  	    	
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
	  	  	  			
			  	  		if (canHasDisarmed[5].equals("yes")) {
							
				  	  		TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
		  	  	  			disarmedtextleft.setVisibility(View.VISIBLE);
				  	  	  	disarmedtextleft.bringToFront();
				  	  	  	
				  	  	  	String str6 = "hostSideDisarmed";
				  	  	  	sendToClient0(str6);				  	  	
			  	  			
							
			  	  			if ((didHumanCriticalMiss[5].equals("yes") && disarmedTurnStart[5] == ArrayOfTurn.turn[0]) || (didHumanCriticalMiss[5].equals("yes") && disarmedTurnStart[5] + 1 == ArrayOfTurn.turn[0]) || (didHumanCriticalMiss[5].equals("yes") && disarmedTurnStart[5] + 2 == ArrayOfTurn.turn[0])) {			  	  				
				  	  										
			  	  				disarmedAction();				  	  			
			  	  			}
			  	  			
			  	  			else if ((didHumanCriticalMiss[5].equals("no") && disarmedTurnStart[5] + 1 == ArrayOfTurn.turn[0]) || (didHumanCriticalMiss[5].equals("no") && disarmedTurnStart[5] + 2 == ArrayOfTurn.turn[0])) {
			  	  						  	  											
								disarmedAction();
												  	  			
			  	  			}			  	  			
				  	    	
				  			else if (canHasDisarmed[5].equals("yes") && didHumanCriticalMiss[5].equals("no") && disarmedTurnStart[5] + 3 == ArrayOfTurn.turn[0]) {
				  									
				  				canHasDisarmed[5] = "no";
				  				
				  				String str7 = "canHaSDisarmed5 :" + "no";
				  				sendToClient0(str7);
				  				
				  			
				  				//didHumanCriticalMiss = "no";				  				
				  				
				  		  		disarmedtextleft.setVisibility(View.INVISIBLE);
				  		  		
				  		  		String str8 = "hostSideNotDisarmed";
				  		  		sendToClient0(str8);
				  		  		
				  				
				  				if (isInvokingService.equals("true")){
									//NEED THIS?
									SystemClock.sleep(1000);	        		
										
									runActionsOnUi();
								}
				  			}
						}
			  	  		
			  	  		else {							
			  	  			
				  	  		TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
					  		disarmedtextleft.setVisibility(View.INVISIBLE);
			  	  			
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
	
	public void gameEngine1V1502() {//PART 2		
		
		//Toast.makeText(MainActivity2.this, "COMPUTER TURN START", Toast.LENGTH_SHORT).show();	
		
		//Toast.makeText(MainActivity2.this, "Turn = " + ArrayOfTurn.turn[0], Toast.LENGTH_SHORT).show();
		
		
		String str = "issecondroundofhasteused :" + "no";
		sendToClient0(str);
		
    	
    	if (canHasDisarmed[0].equals("yes") && didHumanCriticalMiss[0].equals("no") && disarmedTurnStart[0] + 2 == ArrayOfTurn.turn[0]) {
			
    		TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
	  		disarmedtextright.setVisibility(View.INVISIBLE);
	  		
	  		String str2 = "clientNotDisarmed";
			sendToClient0(str2);
    		
			
			canHasDisarmed[0] = "no";
			
			String str3 = "CanHasDisarmed0 :" + "no";
			sendToClient0(str3);
		}
    	
    	//THIS WAS "else if" (WANT THIS AND THE IF ABOVE TO BOTH BE EVALUATED):
    	if (canHasDisarmed[5].equals("yes") && didHumanCriticalMiss[5].equals("yes") && disarmedTurnStart[5] + 2 == ArrayOfTurn.turn[0]) {
    		
    		canHasDisarmed[5] = "no";
    		
    		String str4 = "canHaSDisarmed5 :" + "no";
			sendToClient0(str4);
    		
				
			didHumanCriticalMiss[5] = "no";
			
			String str5 = "didHuManCriticalMiss5 :" + "no";
			sendToClient0(str5);
			
			TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
	  		disarmedtextleft.setVisibility(View.INVISIBLE);
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
  	  	    	
  	  	    	
  	  	    	ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
	  	    	chatBlankButton.bringToFront();
	  			
	  			
	  			// NEED THIS?:
  	  	    	//ishasteused0.equals("no");// so computer doesn't use a haste during a haste. NEEDS '=' BTW
  	  	    	ishasteused0 = "no";
  	  	    	
	  			
	  			playerCardStopFadeInFadeOut();
	  			computerCardStartFadeInFadeOut();
	  			
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			
	  			//NEED THIS??
	  			final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
	  			computerHitPointsTextView.setTypeface(typeFace);
	  			computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
	  			//Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);				
	  			//computerHitPointsTextView.startAnimation(animPulsingAnimation);
	  			
	  			
	  			String str6 = "turn1V105";//2 PLAYER(1V1), CLIENT GOES FIRST(05)
    			sendToClient0(str6);
	  			
	  			
	  			final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {	  	  	  			
						
			  			if (canHasDisarmed[0].equals("yes")) {
			  				
			  				TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
		  	  	  			disarmedtextright.setVisibility(View.VISIBLE);
				  	  	  	disarmedtextright.bringToFront();
				  	  	  	
				  	  	  	String str7 = "clientDisarmed";
				  	  	  	sendToClient0(str7);
			  				
			  				
			  				if ((didHumanCriticalMiss[0].equals("yes") && disarmedTurnStart[0] + 1 == ArrayOfTurn.turn[0]) || (didHumanCriticalMiss[0].equals("yes") && disarmedTurnStart[0] + 2 == ArrayOfTurn.turn[0])) {
			  	  				
			  					String str8 = "disarmedAction";
					  	  	  	sendToClient0(str8);			  	  												
			  	  			}
			  	  			
			  	  			else if ((didHumanCriticalMiss[0].equals("no") && disarmedTurnStart[0] == ArrayOfTurn.turn[0]) || (didHumanCriticalMiss[0].equals("no") && disarmedTurnStart[0] + 1 == ArrayOfTurn.turn[0])) { //HUMAN MUST HAVE DISARMED HUMAN
			  	  												
			  	  				String str9 = "disarmedAction";
			  	  				sendToClient0(str9);										  	  			
			  	  			}
			  			}
			  			
			  			else {  				
			  				
			  				TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
		  	  	  			disarmedtextright.setVisibility(View.INVISIBLE);
		  	  	  			
		  	  	  			String str10 = "clientNotDisarmed";
		  	  	  			sendToClient0(str10);
			  				
		  	  	  			
		  	  	  			String str11 = "runActionsOnUi";
		  	  	  			sendToClient0(str11);
			  			}	  	  	  			
	  	  	  		}
	  	  	  	}, 2000);	  			
  	  	    }
		});	
	}
	
	
	public void gameEngine1V1051() {		
		
		//final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		//issecondroundofhasteused = "no"; // SO HUMIE CAN USE MB AFTER THE 2RD ROUND OF A HASTE.
		
		String str = "issecondroundofhasteused :" + "no";
		sendToClient0(str);		
		
		if (canHasDisarmed[5].equals("yes") && didHumanCriticalMiss[5].equals("yes") && disarmedTurnStart[5] + 3 == ArrayOfTurn.turn[0]) {//CHANGED FROM 3 TO 2, SHOULD BE 3?
			
			canHasDisarmed[5] = "no";			
			
			String str2 = "canHaSDisarmed5 :" + "no";
			sendToClient0(str2);
			
			
			TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
	  		disarmedtextleft.setVisibility(View.INVISIBLE);
			
	  		String str3 = "hostSideNotDisarmed";
			sendToClient0(str3);			
			
			
			didHumanCriticalMiss[5] = "no";
			
			String str4 = "didHuManCriticalMiss5 :" + "no";
			sendToClient0(str4);
		}	
		
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() { 	  	    	
  	  	    	
  	  	    	ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
  	  	    	chatBlankButton.bringToFront();
  	  	    	
  	  	    	
  	  	    	displayTurn();
  	  	    	
  	  	    	//int turnVariable = ArrayOfTurn.turn[0];
  	  	    	
	  	  	    String str5 = "Turn :" + ArrayOfTurn.turn[0];
				sendToClient0(str5);
  	  	    	
	  	  	    String str6 = "displayTrn";
				sendToClient0(str6);  	  	    	
  	  	    	
  	  	    					
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
	  	  	  			
			  	  		if (canHasDisarmed[0].equals("yes")) {							
							
							TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
							disarmedtextright.setVisibility(View.VISIBLE);
					  	  	disarmedtextright.bringToFront();
					  	  	
					  	  	String str7 = "clientDisarmed";
							sendToClient0(str7);				  	  		
			  	  			
							
			  	  			if ((didHumanCriticalMiss[0].equals("yes") && disarmedTurnStart[0] == ArrayOfTurn.turn[0]) || (didHumanCriticalMiss[0].equals("yes") && disarmedTurnStart[0] + 1 == ArrayOfTurn.turn[0]) || (didHumanCriticalMiss[0].equals("yes") && disarmedTurnStart[0] + 2 == ArrayOfTurn.turn[0])) {			  	  				
				  	  										
			  	  				//disarmedAction();
			  	  				
			  	  				String str8 = "disarmedAction";
			  	  				sendToClient0(str8);
			  	  			}
			  	  			
			  	  			else if ((didHumanCriticalMiss[0].equals("no") && disarmedTurnStart[0] + 1 == ArrayOfTurn.turn[0]) || (didHumanCriticalMiss[0].equals("no") && disarmedTurnStart[0] + 2 == ArrayOfTurn.turn[0])) {
			  	  						  	  											
								//disarmedAction();
			  	  				
			  	  				String str8 = "disarmedAction";
			  	  				sendToClient0(str8);
												  	  			
			  	  			}			  	  			
				  	    	
				  			else if (canHasDisarmed[0].equals("yes") && didHumanCriticalMiss[0].equals("no") && disarmedTurnStart[0] + 3 == ArrayOfTurn.turn[0]) {
				  									
				  				canHasDisarmed[0] = "no";
				  				
				  				String str8 = "CanHasDisarmed0 :" + "no";
				  				sendToClient0(str8);
				  				
				  			
				  				//didHumanCriticalMiss = "no";				  				
				  				
				  				disarmedtextright.setVisibility(View.INVISIBLE);
				  				
				  				String str9 = "clientNotDisarmed";
				  				sendToClient0(str9);
				  				
				  				
				  				if (isInvokingService.equals("true")){
									//NEED THIS?
									SystemClock.sleep(1000);	        		
										
									//runActionsOnUi();
									
									String str10 = "runActionsOnUi";
					  				sendToClient0(str10);
								}
				  			}
						}
			  	  		
			  	  		else {							
			  	  			
			  	  			
			  	  			TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
			  	    		disarmedtextright.setVisibility(View.INVISIBLE);
			  	  			
			  	    		String str11 = "clientNotDisarmed";
			  				sendToClient0(str11);
			  				
			  	  			
							if (isInvokingService.equals("true")){
								//NEED THIS?
								SystemClock.sleep(1000);	        		
									
								//runActionsOnUi();
								
								String str12 = "runActionsOnUi";
				  				sendToClient0(str12);
							}
			  	  		}
		  	  	  	}
	  	  	  	}, 1000);				
  	  	    }
		});		
	}	
	
	public void gameEngine1V1052() {//PART 2		
		
		//Toast.makeText(MainActivity2.this, "COMPUTER TURN START", Toast.LENGTH_SHORT).show();	
		
		//Toast.makeText(MainActivity2.this, "Turn = " + ArrayOfTurn.turn[0], Toast.LENGTH_SHORT).show();
		
		
		issecondroundofhasteused = "no"; // SO HUMIE CAN USE MB AFTER THE 2RD ROUND OF A HASTE.
		
    	
    	if (canHasDisarmed[5].equals("yes") && didHumanCriticalMiss[5].equals("no") && disarmedTurnStart[5] + 2 == ArrayOfTurn.turn[0]) {
			
    		runOnUiThread(new Runnable() {
      	  	    @Override
      	  	    public void run() {
      	  	    	
		    		TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
			  		disarmedtextleft.setVisibility(View.INVISIBLE);
      	  	    }
    		});
	  		
	  		String str = "hostSideNotDisarmed";
			sendToClient0(str);
    		
			
			canHasDisarmed[5] = "no";
			
			String str2 = "canHaSDisarmed5 :" + "no";
			sendToClient0(str2);
		}
    	
    	//THIS WAS "else if" (WANT THIS AND THE IF ABOVE TO BOTH BE EVALUATED):
    	if (canHasDisarmed[0].equals("yes") && didHumanCriticalMiss[0].equals("yes") && disarmedTurnStart[0] + 2 == ArrayOfTurn.turn[0]) {
    		
    		canHasDisarmed[0] = "no";
    		
    		String str3 = "CanHasDisarmed0 :" + "no";
			sendToClient0(str3);
    		
				
			didHumanCriticalMiss[0] = "no";
			
			String str4 = "DidHumanCriticalMiss0 :" + "no";
			sendToClient0(str4);
			
			
			TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
    		disarmedtextright.setVisibility(View.INVISIBLE);
  			
    		String str5 = "clientNotDisarmed";
			sendToClient0(str5);			
    	}
		
		
		// THIS THREAD IS BEING USED TO TEST ACCESS TO CENTERSCROLLTEXT
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {  	    	
  	  	    	
  	  	    	String str6 = "Turn1V1052";
  	  	    	sendToClient0(str6);
  	  	    	
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
  	  	    	
  	  	    	
  	  	    	ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
  	  	    	chatBlankButton.bringToFront();
	  			
	  			
	  			// NEED THIS?:
  	  	    	//ishasteused0.equals("no");// so computer doesn't use a haste during a haste. NEEDS '=' BTW    	
  	  	    	ishasteused0 = "no";
  	  	    	
	  			
	  			computerCardStopFadeInFadeOut();
	  			playerCardStartFadeInFadeOut();
	  			
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			
	  			//NEED THIS??
	  			TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
	  			playerHitPointsTextView.setTypeface(typeFace);
	  			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
	  			//Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);
	  			//playerHitPointsTextView.startAnimation(animPulsingAnimation);
	  			
	  			
	  			final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {	  	  	  			
						
			  			if (canHasDisarmed[5].equals("yes")) {
			  				
			  				TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
			  				disarmedtextleft.setVisibility(View.VISIBLE);
			  		  	  	disarmedtextleft.bringToFront();		  				
				  	  	  	
				  	  	  	String str = "hostSideDisarmed";
				  	  	  	sendToClient0(str);
			  				
			  				
			  				if ((didHumanCriticalMiss[5].equals("yes") && disarmedTurnStart[5] + 1 == ArrayOfTurn.turn[0]) || (didHumanCriticalMiss[5].equals("yes") && disarmedTurnStart[5] + 2 == ArrayOfTurn.turn[0])) {
			  	  				
			  					disarmedAction();			  	  												
			  	  			}
			  	  			
			  	  			else if ((didHumanCriticalMiss[5].equals("no") && disarmedTurnStart[5] == ArrayOfTurn.turn[0]) || (didHumanCriticalMiss[5].equals("no") && disarmedTurnStart[5] + 1 == ArrayOfTurn.turn[0])) { //HUMAN MUST HAVE DISARMED HUMAN
			  	  											
			  	  				disarmedAction();										  	  			
			  	  			}
			  			}
			  			
			  			else {  				
			  				
			  				TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
			  		  		disarmedtextleft.setVisibility(View.INVISIBLE);			  				
		  	  	  			
		  	  	  			String str = "hostSideNotDisarmed";
		  	  	  			sendToClient0(str);
			  				
		  	  	  			
		  	  	  			runActionsOnUi();
			  			}	  	  	  			
	  	  	  		}
	  	  	  	}, 2000);	  			
  	  	    }
		});	
	}
	
	
	public void displayTurn() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
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
  	  	    	
  	  	    	
  	  	    	ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
  	  	    	chatBlankButton.bringToFront();
	  			
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);
		  		//centerscrolltext.append("\n");
		  		centerscrolltext.append("\n" + " >>>>>>>>>>>   " + " Turn " + ArrayOfTurn.turn[0] + "   <<<<<<<<<<<");				
		  		//centerscrolltext.append("\n");		  		
  	  	    }
		});		
	}	
	
	public void skillsCheck() {
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
		
				//if (numberOfPlayers == 2) {
					
					if (blessSpell[5] < 1) {
						
						ImageView blessLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftbless);
						blessLeft.setVisibility(View.VISIBLE);				
					}				
					if (cureSpell[5] < 1) {
						
						ImageView cureLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftcure);
						cureLeft.setVisibility(View.VISIBLE);				
					}			
					if (dodgeBlowSpell[5] < 1) {
						
						ImageView dodgeLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftdodge);
						dodgeLeft.setVisibility(View.VISIBLE);				
					}			
					if (mightyBlowSpell[5] < 1) {
						
						ImageView mbLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftmb);
						mbLeft.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[5] < 2) {
						
						ImageView hasteLeft1 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste1);
						hasteLeft1.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[5] < 1) {
						
						ImageView hasteLeft1 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste1);
						hasteLeft1.setVisibility(View.VISIBLE);
						ImageView hasteLeft2 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste2);
						hasteLeft2.setVisibility(View.VISIBLE);				
					}
					
					
					if (blessSpell[0] < 1) {
						
						ImageView blessRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightbless);
						blessRight.setVisibility(View.VISIBLE);				
					}				
					if (cureSpell[0] < 1) {
						
						ImageView cureRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightcure);
						cureRight.setVisibility(View.VISIBLE);				
					}			
					if (dodgeBlowSpell[0] < 1) {
						
						ImageView dodgeRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightdodge);
						dodgeRight.setVisibility(View.VISIBLE);				
					}			
					if (mightyBlowSpell[0] < 1) {
						
						ImageView mbRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightmb);
						mbRight.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[0] < 2) {
						
						ImageView hasteRight1 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste1);
						hasteRight1.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[0] < 1) {
						
						ImageView hasteRight1 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste1);
						hasteRight1.setVisibility(View.VISIBLE);
						ImageView hasteRight2 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste2);
						hasteRight2.setVisibility(View.VISIBLE);				
					}								
				//}
  	  	    }
  	  	});
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
				
				
				if (numberOfPlayers == 2) {
					
					// UNCONSCIOUS TEMPLATE???????????????????????????????????????????????????????????????					
					
	    			// USED BY HASTE PART TWO?:
					if (ArrayOfHitPoints.hitpoints[5] <= 0) {
						
						playerDeadYet[5] = "yes";
						
						String str = "playerDeadYet5 :" + "yes";
						sendToClient0(str);
						
						
						gameOverCheck();
						
						//isInvokingService = "true";
						
						// NEED THIS?:
						//return;
					}
					
					else if (ArrayOfHitPoints.hitpoints[0] <= 0) {
						
						playerDeadYet[0] = "yes";
						
						String str = "playerDeadYet0 :" + "yes";
						sendToClient0(str);
						
						
						gameOverCheck();
						
						//isInvokingService = "true";
						
						// NEED THIS?:
						//return;
					}					
				}
  	  	    }
  	  	});
	}	
	
	public void gameOverCheck() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
		titleBlankButton.setEnabled(false);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
  	  	    	Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");				
  	  	    	
    			
  	  	    	victoryDefeatAnimation();
  	  	    	
  	  	    	String str = "victoryDefeatAnimation";
  	  	    	sendToAllClients(str);
  	  	    	
  	  	    	
  	  	    	if (numberOfPlayers == 2) { 	  	    	
  	  	    	
					if (playerDeadYet[5].equals("no") && playerDeadYet[0].equals("yes")) {
					/*&& playerDeadYet[2] == "yes" && playerDeadYet[3] == "yes"	&& playerDeadYet[4] == "yes" && playerDeadYet[5] == "yes"*/
						
						gameOn = 0;
						
						
						final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
						//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
						
						//Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						centerscrolltext.setTypeface(typeFace);					
						
						
						centerscrolltext.setVisibility(View.VISIBLE);												
			  	  		centerscrolltext.startAnimation(animAlphaText);
			  			centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + ", you are victorious!");
			  			
			  			String str2 = "> " + ArrayOfPlayers.player[5] + ", is victorious!";
						sendToClient0(str2);
						
			  			
			  			final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			centerscrolltext.setVisibility(View.VISIBLE);												
			  	  	  			centerscrolltext.startAnimation(animAlphaText);
			  	  	  			centerscrolltext.append("\n" + "> Game Over!");
			  	  	  			
			  	  	  			String str3 = "> Game Over!";
			  	  	  			sendToClient0(str3);
			  	  	  			
			  	  	  			
			  	  	  			MediaPlayerWrapper.play(Host.this, R.raw.buttonsound6);
			  	  	  			
				  	  	  		final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			//MediaPlayerWrapper.play(MainActivity2.this, R.raw.buttonsound6);
					  	  	  			
					  	  	  			foldScrolls();
					  	  	  			
					  	  	  			String str4 = "foldScrolls";
					  	  	  			sendToClient0(str4);
					  	  	  			
					  	  	  			
						  	  	  		playerCardStopFadeInFadeOut();
						  	  	  		computerCardStopFadeInFadeOut();					  	  	  	
					  	  	  			
					  	  	  			/*
						  	  	  		final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {						  	  	  		
							  	  	  			
								  	  	  		Intent intent = new Intent(Host.this, MainActivity1.class);
								    			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								    			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);  //this combination of flags would start a new instance even if the instance of same Activity exists.
								    			intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);							    			
								    			
								    			
								    			finish();
								    			startActivity(intent);						  	  	  			
								  	  	  	}
							  	  	  	}, 500);
							  	  	  	*/
						  	  	  	}
					  	  	  	}, 2000);
				  	  	  	}
			  	  	  	}, 2000);					
					}
					
					if (playerDeadYet[0].equals("no") && playerDeadYet[5].equals("yes")
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
			  			centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", is victorious!");
			  			
			  			String str4 = "> " + ArrayOfPlayers.player[0] + ", is victorious!";
						sendToClient0(str4);
			  			
			  			
			  			final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			centerscrolltext.setVisibility(View.VISIBLE);												
			  	  	  			centerscrolltext.startAnimation(animAlphaText);
			  	  	  			centerscrolltext.append("\n" + "> Game Over!");
			  	  	  			
			  	  	  			String str5 = "> Game Over!";
			  	  	  			sendToClient0(str5);		  	  	  			
			  	  	  			
			  	  	  			
			  	  	  			MediaPlayerWrapper.play(Host.this, R.raw.buttonsound6);
			  	  	  			
				  	  	  		final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			//MediaPlayerWrapper.play(MainActivity2.this, R.raw.buttonsound6);
					  	  	  			
					  	  	  			foldScrolls();
					  	  	  			
					  	  	  			String str6 = "foldScrolls";
					  	  	  			sendToClient0(str6);
					  	  	  			
					  	  	  			
						  	  	  		playerCardStopFadeInFadeOut();
						  	  	  		computerCardStopFadeInFadeOut();
					  	  	  			
					  	  	  			/*
						  	  	  		final Handler h = new Handler();
							  	  	  	h.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {				  	  	  		
							  	  	  			
								  	  	  		Intent intent = new Intent(Host.this, MainActivity1.class);
								    			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								    			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);  //this combination of flags would start a new instance even if the instance of same Activity exists.
								    			intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);							    			
								    			
								    			
								    			finish();						    			
								    			startActivity(intent);			  	  	  			
								  	  	  	}
							  	  	  	}, 500);
							  	  	  	*/
						  	  	  	}
					  	  	  	}, 2000);
				  	  	  	}
			  	  	  	}, 2000);					
					}
  	  	    	}		
  	  	    }
		});
	}	
	
	public void victoryDefeatAnimation() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {						
				
				if (istitlestatsopen.equals("yes")) {
					
					// CLOSING ON SUMMARY TABLE
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
		  	  	  			
		  	  	  			TableLayout summaryTableLayout = (TableLayout) findViewById(R.id.summaryTable);
			  	  	  		summaryTableLayout.setVisibility(View.GONE);
			  	  	  		
			  	  	  		View lineInSummaryTableLayout = (View) findViewById(R.id.line);
			  	  	  		lineInSummaryTableLayout.setVisibility(View.GONE);
			  	  	  		
				  	  	  	TextView titlevictorydefeat = (TextView) findViewById(R.id.textviewtitlevictorydefeattext);		
							
							Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
							titlevictorydefeat.setTypeface(typeFace);
							
							titlevictorydefeat.setVisibility(View.VISIBLE);
							
							
							if (numberOfPlayers == 2) {
							
								if (playerDeadYet[5].equals("no") && playerDeadYet[0].equals("yes")) {
									
									titlevictorydefeat.append("Victory");
									
									istitlestatsopen = "no";
								}
								
								else if (playerDeadYet[0].equals("no") && playerDeadYet[5].equals("yes")) {
									
									titlevictorydefeat.append("Defeat");
									
									istitlestatsopen = "no";
								}
							}
			  	  	  	}
		  	  	  	}, 600);					
				}
				
				else {				
					
					// CLOSING ON TITLE
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
							
							
							if (numberOfPlayers == 2) {
							
								if (playerDeadYet[5].equals("no") && playerDeadYet[0].equals("yes")) {
									
									titlevictorydefeat.append("Victory");
								}
								
								else if (playerDeadYet[0].equals("no") && playerDeadYet[5].equals("yes")) {
									
									titlevictorydefeat.append("Defeat");
								}
							}
			  	  	  	}
		  	  	  	}, 600);
				}
							
			}
  		});
	}
	
	
	//=============================================================================================
	//SEPERATOR
	//=============================================================================================
	
	
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
		  		        }
		  		        
		  		        else {
		  		            // view already defined, retrieve view holder
		  		            holder = (ViewHolder) convertView.getTag();
		  		        }       
	
		  		       // Drawable drawable = getResources().getDrawable(R.drawable.list_icon); //this is an image from the drawables folder
	
		  		        holder.title.setText(items[position]);
		  		        //holder.icon.setImageDrawable(drawable);     
		  		        
	
		  		        return convertView;
		  		    }
		  		};		  		
		  		
		  		
		  		// THIS WAY ALLOWS YOU TO STYLE THE DIALOG (ex. background doesn't dim.):
		  		ContextThemeWrapper cw = new ContextThemeWrapper(Host.this, R.style.DialogWindowTitle_Holo);
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
	                        public void onClick(final DialogInterface dialog, int item) {
	                        	
	                        	if (item == 0) {										
									
	                        		attack();
									
									dialog.dismiss();
	                        		
	                        		/*
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + " attacks...");									
									
									String str = "> " + ArrayOfPlayers.player[5] + " attacks...";
									sendToAllClients(str);
									
									
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
						  	  	  	*/										
								}
	                        	
	                        	else if (item == 1) {
									
	                        		disarm();
									
									dialog.dismiss();
	                        		
	                        		/*
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + " attempts to disarm...");									
									
									String str = "> " + ArrayOfPlayers.player[5] + " attempts to disarm...";
									sendToAllClients(str);
									
									
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
						  	  	  	*/										
								}
	                        	
	                        	else if (item == 2) {
									
	                        		haste();
	                        		
	                        		dialog.dismiss();
	                        		
	                        		/*
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + " casts haste...");
									
									
									String str = "> " + ArrayOfPlayers.player[5] + " casts haste...";
									sendToAllClients(str);									
									
									
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
						  	  	  	*/									
								}
	                        	
	                        	else if (item == 3) {
									
	                        		cure();
									
									dialog.dismiss();
	                        		
	                        		/*
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + " casts cure...");
									
									
									String str = "> " + ArrayOfPlayers.player[5] + " casts cure...";
									sendToAllClients(str);
									
									
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
						  	  	  	*/										
								}
	                        	
	                        	else if (item == 4) {
									
	                        		bless();
									
									dialog.dismiss();
	                        		
									/*
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + " casts bless...");
									
									
									String str = "> " + ArrayOfPlayers.player[5] + " casts bless...";
									sendToAllClients(str);									
									
									
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
						  	  	  	*/									
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
				centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + ", you are disarmed. What do you want to do? ");
  	  	    					
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {	  	  	  			
	  	  	  			
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
	  		
	  			  		        if (convertView == null) {
	  			  		            convertView = inflater.inflate(R.layout.list_row, null);
	  		
	  			  		            holder = new ViewHolder();
	  			  		            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
	  			  		            holder.title = (TextView) convertView.findViewById(R.id.title);		  		           
	  			  		            
	  			  		            convertView.setTag(holder);
	  			  		        }
	  			  		        
	  			  		        else {
	  			  		            // view already defined, retrieve view holder
	  			  		            holder = (ViewHolder) convertView.getTag();
	  			  		        }       
	  		
	  			  		       // Drawable drawable = getResources().getDrawable(R.drawable.list_icon); //this is an image from the drawables folder
	  		
	  			  		        holder.title.setText(items[position]);
	  			  		        //holder.icon.setImageDrawable(drawable);     
	  			  		        
	  		
	  			  		        return convertView;
	  			  		    }
	  			  		};
	  			  		
	  			  		
	  			  		// THIS WAY ALLOWS YOU TO STYLE THE DIALOG (ex. background doesn't dim.):
	  			  		ContextThemeWrapper cw = new ContextThemeWrapper(Host.this, R.style.DialogWindowTitle_Holo);
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
	  							
	  							disarmedAction();
	  						}
	  					});
	  		  			
	  					
	  		            builder.setAdapter(adapter,
	  		                    new DialogInterface.OnClickListener() {
	  		                        @Override
	  		                        public void onClick(final DialogInterface dialog, int item) {
	  		                        	
	  		                        	if (item == 0) {										
	  										
	  		                        		attack();
											
											dialog.dismiss();
	  		                        		
	  		                        		/*
	  		                        		centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + " attacks...");											
											
											String str = "> " + ArrayOfPlayers.player[5] + " attacks...";
											sendToAllClients(str);											
											
											
											final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
								  	  	  			//hideNavigation();
													//isInvokingService = "true";
													//punch();
								  	  	  			
													attack();
													
													dialog.dismiss();
									  	  	  	}
								  	  	  	}, 1000);
								  	  	  	*/										
	  									}
	  		                        	
	  		                        	else if (item == 1) {
	  										
	  		                        		//hideNavigation();
											//isInvokingService = "true";												
												
											if (hasteSpell[5] < 1) {
												
												dialog.dismiss();
												
												
												AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
											      
												alert.setTitle(ArrayOfPlayers.player[5] + ", you have already used your Haste spells.");
									  	    	/*
									  	    	alert.setMessage("something");
									  	    	*/	    	
										    	
										    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											    	public void onClick(DialogInterface dialog, int whichButton) {
											    		
											    		//hideNavigation();
											    		
											    		disarmedAction();
											    		
											    		dialog.dismiss();
											    	}
										    	});								    	
										    	alert.show();							
												
												
												/*
												centerscrolltext.setVisibility(View.VISIBLE);
										  		centerscrolltext.startAnimation(animAlphaText);
										  		centerscrolltext.append("\n" + "> You have already used your Haste spells.");
												
												disarmedAction();
												
												dialog.dismiss();
												*/
											}					
											
											else if ((hasteSpell[5] > 0) && !(disarmedTurnStart[5] + 2 == ArrayOfTurn.turn[0])) {
												
												haste();
												
												//disarmedAction();
							  	  	  			
							  	  	  			dialog.dismiss();
												
												/*
												centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);
												centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + " casts haste...");
												
												
												String str = "> " + ArrayOfPlayers.player[5] + " casts haste...";
												sendToAllClients(str);												
												
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
									  	  	  			haste();
									  	  	  			
									  	  	  			dialog.dismiss();
										  	  	  	}
									  	  	  	}, 1000);
									  	  	  	*/											
											}
											
											else {
												
												disarmedAction();												
												
												//haste();
												
												dialog.dismiss();
											}										
	  									}
	  		                        	
	  		                        	else if (item == 2) {
	  										
	  		                        		//hideNavigation();
											//isInvokingService = "true";
	  		                        		
											if (cureSpell[5] > 0) {
												
												cure();
							  	  	  			
							  	  	  			dialog.dismiss();
												
												/*
												centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);
												centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + " casts cure...");
												
												
												String str = "> " + ArrayOfPlayers.player[5] + " casts cure...";
												sendToAllClients(str);												
												
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
									  	  	  			cure();
									  	  	  			
									  	  	  			dialog.dismiss();
										  	  	  	}
									  	  	  	}, 1000);
									  	  	  	*/													
											}
											
											else if (cureSpell[5] < 1) {
												
												dialog.dismiss();
												
												
												AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
											      
												alert.setTitle(ArrayOfPlayers.player[5] + ", you have already used your Cure spell.");
									  	    	/*
									  	    	alert.setMessage("something");
									  	    	*/	    	
										    	
										    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											    	public void onClick(DialogInterface dialog, int whichButton) {
											    		
											    		//hideNavigation();
											    		
											    		disarmedAction();
											    		
											    		dialog.dismiss();
											    	}
										    	});								    	
										    	alert.show();
												
												
												//disarmedAction();
											}									
	  									}	  									
	  									
	  									isInvokingService = "true";
	  		                        	
	  		                            //dialog.dismiss();
	  		                        }
	  		                    });	            
	  		            
	  		            AlertDialog alert = builder.create();
	  		            alert.show();	            
	  		            
	  		            
	  		            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

	  		            lp.copyFrom(alert.getWindow().getAttributes());
	  		            lp.width = 1050;	            
	  		            alert.getWindow().setAttributes(lp);	  	  	  					  	  	  			
		  	  	  	}
	  	  	  	}, 2000);					
  	  	    }
		});		
	}
	
	
	
	
	public void hasteCureDisarmWithBlessDisarmNoBlessBlessCompleted() {
		
		if (numberOfPlayers == 2) {
			
  	  		if (ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) {
			
  	  			gameEngine1V1052();
  	  		}		
  	  		else if (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) {
			
  	  			turn();
  	  		}
		}		
	}	
	
	public void mightyBlowResultsCompleted() {
		
		if (numberOfPlayers == 2) {
			
			if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused0.equals("no")) {				
				
				gameEngine1V1052();
			}	
			else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused0.equals("no")) {		
    			
    			turn();
			}
		}		
	}	
	
	public void criticalMissLoseWeaponCriticalMissDamageCompleted() {
		
		if (numberOfPlayers == 2) {
  			
  	  		if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused0.equals("no")) {  	  			
				
	  	  		gameEngine1V1052();
			}			
  	  		else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused0.equals("no")) {		
    			
    			turn();
			}
			
			// TAKE THIS OUT?:											
  	  		else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused0.equals("yes")) {				
				
  	  			String str = "hastePartTwo";
  	  			sendToClient0(str);
  	  			
    			//hastePartTwo();
			}			
  	  		else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused0.equals("yes")) {		
    			
  	  			String str = "hastePartTwo";
	  			sendToClient0(str);
  	  			
				//hastePartTwo();
			}
  		}		
	}	
	
	public void attackDamageCriticalHitDamageCriticalHitMightyBlowDamageCompleted() {
		
		if (numberOfPlayers == 2) {
			
			if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused0.equals("no")) {				
				
  				gameEngine1V1052();
			}	
  			else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused0.equals("no")) {		
    			
    			turn();
			}													
  			else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused0.equals("yes")) {				
				
  				String str = "hastePartTwo";
  	  			sendToClient0(str);
  				
    			//hastePartTwo();
			}	
  			else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused0.equals("yes")) {		
    			
  				String str = "hastePartTwo";
  	  			sendToClient0(str);
  				
				//hastePartTwo();
			}  	  		
		}		
	}
	
	
	
	//=============================================================================================
	//SEPERATOR
	//=============================================================================================
	
	
	public void attack() {						
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
  	  	    	//ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
	  			//chatBlankButton.bringToFront();
  	  	    	
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);  	  	    	
	  						 
	  			centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + " attacks...");									
				
				String str = "> " + ArrayOfPlayers.player[5] + " attacks...";
				sendToAllClients(str);	  			
	  			
	  			
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
								
						
								ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);//THE 0 HERE IS NOT FOR PARTICULAR PLAYER
																		
								
								isattackrolled = "yes";
								
								
					  			centerscrolltext.bringToFront();
					  			
					  			
					  			ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
				  	  	    	chatBlankButton.bringToFront();
			  	  	  		}
			  	  	  	}, 750);					  	  	  		
	  	  	  		}
	  	  	  	}, 2000);	  	  	  														
  	  	    }
		});		
	}
	
	
	public void disarm() {				
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    //ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
	  			//chatBlankButton.bringToFront();
  	  	    	
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + " attempts to disarm...");									
				
				String str = "> " + ArrayOfPlayers.player[5] + " attempts to disarm...";
				sendToAllClients(str);
				
					
				if (blessSpell[5] > 0) {		
						
					AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
		  			
					alert.setCancelable(false);
					
		  	    	alert.setTitle(ArrayOfPlayers.player[5] + ", do you want to use your bless spell?");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  	    		public void onClick(DialogInterface dialog, int whichButton) {
		  	    			
		  	    			//hideNavigation();
		  		    		
		  		    		blessSpell[5] = blessSpell[5] - 1;
		  		    		
		  		    		String str2 = "usedBless";
							sendToAllClients(str2);
		  		    		
							
		  		    		skillsCheck();
		  		    		
		  		    		String str3 = "skillsCheck";
							sendToAllClients(str3);
		  		    		
		  		    		
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
		});		
	}
	
	
	public void haste() { // Can't use 2 spells in one turn & ONLY 2 ATTACKS OR RE-ARM ONESELF.	
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
  	  	    	ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
  	  	    	chatBlankButton.bringToFront();
  	  	    	
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			if (hasteSpell[5] > 0) {	  				
	  				
	  				centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + " casts haste...");					
					
					String str = "> " + ArrayOfPlayers.player[5] + " casts haste...";
					sendToAllClients(str);
	  				
	  				
  					hasteGraphic();
  					

  					String str2 = "hasteGraphic";
					sendToAllClients(str2);  					
  					
  					
  					final Handler h1 = new Handler();
  		  	  	  	h1.postDelayed(new Runnable() {
  		  	  	  			
  		  	  	  		@Override
  			  	  	  	public void run() {
  		  	  	  			
  		  	  	  			hasteSpell[5] = hasteSpell[5] - 1;
  		  	  	  			
  		  	  	  			String str3 = "usedHaste";
  		  	  	  			sendToAllClients(str3);
  		  	  	  			
  		  	  	  			
  		  	  	  			skillsCheck();
  		  	  	  			
  		  	  	  			String str4 = "skillsCheck";
  		  	  	  			sendToAllClients(str4);
  		  	  	  			
  		  	  	  			
  		  	  	  			stopGraphics();
  		  	  	  			
  		  	  	  			String str5 = "stopGraphics";
  		  	  	  			sendToAllClients(str5);
  		  	  	  			
  		  	  	  			
  		  	  	  			if (canHasDisarmed[5].equals("no")) {	  		  	  	  				
  		  	  	  				
  		  	  	  				ishasteused = "yes";					  	  	  			

				  	  	  		final TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
				  	  	  		hasteGraphic.setVisibility(View.INVISIBLE);
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
								centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> TWO attacks...");
								
								String str6 = "> TWO attacks...";
								sendToAllClients(str6);								
																
								
								final Handler h2 = new Handler();
					  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
						  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> FIRST attack...");
										
										String str7 = "> FIRST attack...";
										sendToAllClients(str7);										
																				
										
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
  		  	  	  			
  		  	  	  			else if (canHasDisarmed[5].equals("yes")) {
		  		  	  	  		
		  		  	  	  		canHasDisarmed[5] = "no";
		  		  	  	  		
		  		  	  	  		String str6 = "canHaSDisarmed5 :" + "no";
		  		  	  	  		sendToAllClients(str6);
		  		  	  	  		

				  	  	  		final TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
				  	  	  		hasteGraphic.setVisibility(View.INVISIBLE);
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> You are no longer disarmed.");
								
								String str7 = "> " +  ArrayOfPlayers.player[5] + " is no longer disarmed.";
								sendToAllClients(str7);
								
								
								TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
						  		disarmedtextleft.setVisibility(View.INVISIBLE);
								
								final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			if (numberOfPlayers == 2) {
	
							  	  	  		if (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) {				
												
							  	  	  			gameEngine1V1502();    							
											}		
							  	  	  		else if (ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) {		
								    			
								    			turn();   							
											}
					  	  	  			}
						  	  	  	}
					  	  	  	}, 2000);							  	  	  																						
							}	  		  	  	  			
  		  	  	  		}
  		  	  	  	}, 6000);	  				
	  			}	  			
	  			
	  			else {
  	  	  			
	  				AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
				      
					alert.setTitle(ArrayOfPlayers.player[5] + ", you have already used your Haste spells.");
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
				}	  	  	    				 
  	  	    }
		});			
	}
	
	
	public void cure() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
  	  	    	//ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
  	  	    	//chatBlankButton.bringToFront();
  	  	    	
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				if (cureSpell[5] > 0) {					
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + " casts cure...");					
					
					String str = "> " + ArrayOfPlayers.player[5] + " casts cure...";
					sendToAllClients(str);
					
					
					cureSpell[5] = cureSpell[5] - 1;
					
					String str2 = "usedCure";
					sendToAllClients(str2);
					
					
					skillsCheck();
					
					String str3 = "skillsCheck";
					sendToAllClients(str3);
					
					
					cureGraphic();
					
					String str4 = "cureGraphic";
					sendToAllClients(str4);			
		  	  	  			
					
	  	  	  		final Handler h2 = new Handler();
		  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
			  	  	  		//final TextView cureGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  	  	  			//cureGraphic.setVisibility(View.INVISIBLE);
		  	  	  			
		  	  	  			stopGraphics();
		  	  	  			
		  	  	  			String str5 = "stopGraphics";
		  	  	  			sendToAllClients(str5);
		  	  	  			
		  	  	  			
		  	  	  			ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
		  	  	  			img.bringToFront();
		  	  	  			
		  	  	  			sixSidedRollFromLeft();				  	  	  		
				  	  	  			
			  	  	  		final Handler h3 = new Handler();
				  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			sixSidedWobbleStart();
				  	  	  			
				  	  	  			isSixSidedReadyToBeRolled = "yes";					  	  	  			
					  	  	  		
				  	  	  			centerscrolltext.bringToFront();					  	  	  			
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> Please slide the die...");
									
									ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
					  	  	    	titleBlankButton.bringToFront();							
									
							
									SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Host.this);
									SharedPreferences.Editor editor = preferences.edit();
									editor.putInt("cureResult", (int) ((Math.random() * 6) + 1));
									editor.apply();

							        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
							        //same as: (int) Math.ceil(Math.random()*6); ?
									
									preventcureresultdiefromleaking = "off";
									
									iscurerolled = "yes";
									
									
									ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
					  	  	    	chatBlankButton.bringToFront();									
				  	  	  		}
				  	  	  	}, 750);					  	  	  		
		  	  	  		}
		  	  	  	}, 6000);// CHANGED FROM 3000 TO 6000 BECAUSE DIE WAS COMING IN BEFORE GRAPHIC WAS FINISHED.										
				} 
				
				else {
					
					AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
				      
					alert.setTitle(ArrayOfPlayers.player[5] + ", you have already used your Cure spell.");
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
				}
  	  	    }
		});		 
	}
	
	
	public void bless() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
  	  	    	//ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
  	  	    	//chatBlankButton.bringToFront();
  	  	    	
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				if (blessSpell[5] > 0) {
					
					centerscrolltext.setVisibility(View.VISIBLE);													
			  		centerscrolltext.startAnimation(animAlphaText);
					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + " casts bless...");					
					
					String str = "> " + ArrayOfPlayers.player[5] + " casts bless...";
					sendToAllClients(str);
					
					
					isblessrolled = "yes";
					
					blessSpell[5] = blessSpell[5] - 1;
					
					String str2 = "usedBless";
					sendToAllClients(str2);
					
					
					skillsCheck();
					
					String str3 = "skillsCheck";
					sendToAllClients(str3);
					
					
					blessGraphic();	  			 	  	  							  	  	  		
					
					String str4 = "blessGraphic";
					sendToAllClients(str4);
					
					  	  	  		
		  	  	  	final Handler h2 = new Handler();
		  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
			  	  	  		//final TextView blessGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  	  	  			//blessGraphic.setVisibility(View.INVISIBLE);
		  	  	  			
		  	  	  			stopGraphics();
		  	  	  			
		  	  	  			String str5 = "stopGraphics";
		  	  	  			sendToAllClients(str5);
		  	  	  			
		  	  	  			
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
									
							
									ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
									//int attackResult = (int) ((Math.random() * 20) + 1);										
									
									isblessrolled = "yes";
									
									
									ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
					  	  	    	chatBlankButton.bringToFront();									
				  	  	  		}
				  	  	  	}, 750);					  	  	  		
		  	  	  		}			  	  	  		
		  	  	  	}, 6000);// CHANGED FROM 3000 TO 6000 BECAUSE DIE WAS COMING IN BEFORE GRAPHIC WAS FINISHED.	  	  	  		
									
				} 
				
				else {
					
					AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
				      
					alert.setTitle(ArrayOfPlayers.player[5] + ", you have already used your Bless spell.");
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
				}
  	  	    }
		});				
	}
	
	
	public void disarmWithBless() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
  	  	    	//ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
  	  	    	//chatBlankButton.bringToFront();
  	  	    	
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + " uses a bless...");
	  			
		  		String str = "> " + ArrayOfPlayers.player[5] + " uses a bless...";
				sendToAllClients(str);
		  		
		  		
	  			blessGraphic();
	  			
	  			String str2 = "blessGraphic";
				sendToAllClients(str2);
		  	  	    	
				
  	  	  		final Handler h2 = new Handler();
	  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {			  	  	  			
	  	  	  			
		  	  	  		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			img.bringToFront();  	  	  				  	  	  			
	  	  	  			
		  	  	  		//final TextView blessGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
	  	  	  			//blessGraphic.setVisibility(View.INVISIBLE);	  	  	  			
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
	  	  	  			String str3 = "stopGraphics";
	  	  	  			sendToAllClients(str3);
	  	  	  			
	  	  	  			
	  	  	  			// ROLLFROMLEFT (20-SIDED)
	  	  	  			twentySidedRollFromLeft();				  	  	  		
			  	  	  			
		  	  	  		final Handler h3 = new Handler();
			  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			twentySidedWobbleStart();
			  	  	  			
			  	  	  			isTwentySidedReadyToBeRolled = "yes";
			  	  	  			
			  	  	  			centerscrolltext.bringToFront();
								centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
						  		centerscrolltext.append("\n" + "> Press slide the die... ");
						  		
						  		ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				  	  	    	titleBlankButton.bringToFront();						  		
						  		
						  		
						  		ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
								//int attackResult = (int) ((Math.random() * 20) + 1);
						  		
						  		isdisarmwithblessrolled = "yes";
						  		
						  		
						  		ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
				  	  	    	chatBlankButton.bringToFront();
			  	  	  		}
			  	  	  	}, 750);					  	  	  		
	  	  	  		}
	  	  	  	}, 6000);	  	  	  		
  	  	    }
		});
	}
	
	
	public void disarmNoBless() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
  	  	    	//ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
  	  	    	//chatBlankButton.bringToFront();
  	  	    	
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);	  			
	  			
		
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
				  	  	  		
				  	  	  		centerscrolltext.bringToFront();
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
						  		centerscrolltext.append("\n" + "> Press slide the die... ");
						  		
						  		ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				  	  	    	titleBlankButton.bringToFront();					  		
						  										  		
								
								ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
								
								isdisarmnoblessrolled = "yes";
								
								
								ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
				  	  	    	chatBlankButton.bringToFront();							
			  	  	  		}
			  	  	  	}, 750);				  	  	  		
	  	  	  		}
	  	  	  	}, 2000);	  	  	  						
  	  	    }
		});		
	}
	
	
	//=============================================================================================
	//SEPERATOR
	//=============================================================================================
	
	
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
	  			
	  			
	  			if (canHasDisarmed[5].equals("no")) {
	  				
	  				final Handler h1 = new Handler();
		  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {		
		  	  	  			
			  	  	  		if (canHasDisarmed[playerNumberAttacked].equals("no")) {
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ".");
								
								String str = "> " + ArrayOfPlayers.player[5] + " rolls " + ArrayOfAttackResult.attackResult[0] + ".";
								sendToAllClients(str);
			  	  	  		}
			  	  	  		
			  	  	  		else if (canHasDisarmed[playerNumberAttacked].equals("yes")) {				  	  	  		
				  	  	  		
					  	  	  	centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ", +2 for opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 2) + ".");
								
								String str = "> " + ArrayOfPlayers.player[5] + " rolls " + ArrayOfAttackResult.attackResult[0] + ", +2 for opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 2) + ".";
								sendToAllClients(str);
			  	  	  		}												
							
							final Handler h2 = new Handler();
				  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {						
					
									if (ArrayOfAttackResult.attackResult[0] == 20) {
										
										criticalHit();
										return;
									}
									
									else if (canHasDisarmed[playerNumberAttacked].equals("no")) {
										
										if (ArrayOfAttackResult.attackResult[0] >= 14 && ArrayOfAttackResult.attackResult[0] <= 19) {
											
											centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> Your attack hits.");
											
											String str = "> " + ArrayOfPlayers.player[5] + "'s" + " attack hits.";
											sendToAllClients(str);
											
											
											damage();
											return;
										}
										
										else if (ArrayOfAttackResult.attackResult[0] < 14 && ArrayOfAttackResult.attackResult[0] > 1) {
											
											centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Your attack misses.");
											
											String str = "> " + ArrayOfPlayers.player[5] + "'s" + " attack misses.";
											sendToAllClients(str);
											
											
											final Handler h3 = new Handler();
								  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {  	  		
							  	  	  				
								  	  	  			if (numberOfPlayers == 2) {
								  	  	  			
									  	  	  			if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {				
														
									  	  	  				gameEngine1V1502();    							
														}	
									  	  	  			else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {		
											    			
											    			turn();   							
														}													
									  	  	  			else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {				
															
											    			hastePartTwo();    							
														}	
									  	  	  			else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {		
											    			
															hastePartTwo();   							
														}
								  	  	  			}
									  	  	  	}
								  	  	  	}, 2000);
											
											return;
										}
										
										else if (ArrayOfAttackResult.attackResult[0] <= 1) {
											
											criticalMiss();
											return;
										}
									}
									
									else if (canHasDisarmed[playerNumberAttacked].equals("yes")) {
										
										if (ArrayOfAttackResult.attackResult[0] >= 12 && ArrayOfAttackResult.attackResult[0] <= 19) {
											
											centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> Your attack hits.");
											
											String str = "> " + ArrayOfPlayers.player[5] + "'s" + " attack hits.";
											sendToAllClients(str);
											
											
											damage();
											return;
										}
										
										else if (ArrayOfAttackResult.attackResult[0] < 12 && ArrayOfAttackResult.attackResult[0] > 1) {
											
											centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Your attack misses.");
											
											String str = "> " + ArrayOfPlayers.player[5] + "'s" + " attack misses.";
											sendToAllClients(str);
											
											
											final Handler h3 = new Handler();
								  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {  	  		
							  	  	  				
								  	  	  			if (numberOfPlayers == 2) {
								  	  	  			
									  	  	  			if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {				
														
									  	  	  				gameEngine1V1502();    							
														}	
									  	  	  			else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {		
											    			
											    			turn();   							
														}													
									  	  	  			else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {				
															
											    			hastePartTwo();    							
														}	
									  	  	  			else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {		
											    			
															hastePartTwo();   							
														}
								  	  	  			}
									  	  	  	}
								  	  	  	}, 2000);
											
											return;
										}
										
										else if (ArrayOfAttackResult.attackResult[0] <= 1) {
											
											criticalMiss();
											return;
										}
									}									
				  	  	  		}
				  	  	  	}, 2000);
		  	  	  		}
		  	  	  	}, 2000);	  				
	  			}
	  			
	  			else if (canHasDisarmed[5].equals("yes")) {
	  				
	  				final Handler h4 = new Handler();
		  	  	  	h4.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {		
			
			  	  	  		if (canHasDisarmed[playerNumberAttacked].equals("no")) {			  	  	  			
			  	  	  			
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ", -1 for being disarmed = " + (ArrayOfAttackResult.attackResult[0] - 1) + ".");
								
								String str = "> " + ArrayOfPlayers.player[5] + " rolls " + ArrayOfAttackResult.attackResult[0] + ", -1 for being disarmed = " + (ArrayOfAttackResult.attackResult[0] - 1) + ".";
								sendToAllClients(str);
			  	  	  		}
			  	  	  		
			  	  	  		else if (canHasDisarmed[playerNumberAttacked].equals("yes")) {				  	  	  		
				  	  	  		
					  	  	  	centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ", +1 for being disarmed and opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 1) + ".");
								
								String str = "> " + ArrayOfPlayers.player[5] + " rolls " + ArrayOfAttackResult.attackResult[0] + ", +1 for being disarmed and opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 1) + ".";
								sendToAllClients(str);
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
											
											String str = "> " + ArrayOfPlayers.player[5] + "'s" + " punch hits.";
											sendToAllClients(str);
											
											
											damage();
											return;
										}
										
										else if (ArrayOfAttackResult.attackResult[0] < 15 && ArrayOfAttackResult.attackResult[0] >= 1) {
											
											centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Your punch misses.");
											
											String str = "> " + ArrayOfPlayers.player[5] + "'s" + " punch misses.";
											sendToAllClients(str);
											
											
											final Handler h6 = new Handler();
								  	  	  	h6.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {  	  		
							  	  	  				
								  	  	  			if (numberOfPlayers == 2) {
								  	  	  			
									  	  	  			if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {				
														
									  	  	  				gameEngine1V1502();    							
														}	
									  	  	  			else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {		
											    			
											    			turn();   							
														}													
									  	  	  			else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {				
															
											    			hastePartTwo();    							
														}	
									  	  	  			else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {		
											    			
															hastePartTwo();   							
														}
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
									
									else if (canHasDisarmed[playerNumberAttacked].equals("yes")) {
										
										if (ArrayOfAttackResult.attackResult[0] >= 13 && ArrayOfAttackResult.attackResult[0] <= 20) { // -1 to-hit for being disarmed but, +2 because computer is disarmed (+1 total)
											
											centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> Your punch hits.");
											
											String str = "> " + ArrayOfPlayers.player[5] + "'s" + " punch hits.";
											sendToAllClients(str);
											
											
											damage();
											return;
										}
										
										else if (ArrayOfAttackResult.attackResult[0] < 13 && ArrayOfAttackResult.attackResult[0] >= 1) {
											
											centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Your punch misses.");
											
											String str = "> " + ArrayOfPlayers.player[5] + "'s" + " punch misses.";
											sendToAllClients(str);
											
											
											final Handler h6 = new Handler();
								  	  	  	h6.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {  	  		
							  	  	  				
								  	  	  			if (numberOfPlayers == 2) {
								  	  	  			
									  	  	  			if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {				
														
									  	  	  				gameEngine1V1502();    							
														}	
									  	  	  			else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {		
											    			
											    			turn();   							
														}													
									  	  	  			else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {				
															
											    			hastePartTwo();    							
														}	
									  	  	  			else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {		
											    			
															hastePartTwo();   							
														}
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
	  	  	  			
	  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Host.this);
	  	  	  			//SharedPreferences.Editor editor = preferences.edit();
	  	  	  			int cureResult = preferences.getInt("cureResult", 0);//0 IS THE INT TO RETURN IF PREFERENCE DOESN'T EXIST?
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> You roll " + cureResult + ".");				
						
						String str = "> " + ArrayOfPlayers.player[5] + " rolls " + cureResult + ".";
						sendToAllClients(str);
						
						
						ArrayOfHitPoints.hitpoints[5] = ArrayOfHitPoints.hitpoints[5] + cureResult;	  	  	  			
		  	  	  		
						
						final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
		    			playerHitPointsTextView.setTypeface(typeFace);
		    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
		    			//playerHitPointsTextView.bringToFront();
		    			Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);
		    			playerHitPointsTextView.startAnimation(animPulsingAnimation);
						
		    			
		    			String str2 = "5ArrayOfHitPoints.hitpoints[5] :" + ArrayOfHitPoints.hitpoints[5];
						sendToAllClients(str2);		    			
		    			
		    			
						final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {  	  	  			
			  	  	  			
			  	  	  			playerHitPointsTextView.clearAnimation();
			  	  	  			
			  	  	  			if (numberOfPlayers == 2) {
			  	  	  			
					  	  	  		if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {				
										
					  	  	  			gameEngine1V1502();    							
									}	
					  	  	  		else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0])) {		
						    			
						    			turn();   							
									}
			  	  	  			}
			  	  	  		}
			  	  	  	}, 2000);
	  	  	  		}
	  	  	  	}, 2000);								 
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
				  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0]	+ ", +2 for the Bless Spell = "	+ (ArrayOfAttackResult.attackResult[0] + 2) + ".");
						
				  		String str = "> " + ArrayOfPlayers.player[5] + " rolls "	+ ArrayOfAttackResult.attackResult[0]	+ ", +2 for the Bless Spell = "	+ (ArrayOfAttackResult.attackResult[0] + 2) + ".";
						sendToAllClients(str);
				  		
				  		
				  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (ArrayOfAttackResult.attackResult[0] >= 15) {
									
					  	  	  		canHasDisarmed[playerNumberAttacked] = "yes";
									
					  	  	  		disarmedTurnStart[playerNumberAttacked] = ArrayOfTurn.turn[0];
					  	  	  		
					  	  	  		
									if (playerNumberAttacked == 0) {
										
										String str = "CanHasDisarmed0 :" + "yes";
										sendToAllClients(str);
										
										String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
										sendToClient0(str2);
									}
									else if (playerNumberAttacked == 1) {
										
										String str = "cAnHasDisarmed1 :" + "yes";
										sendToAllClients(str);
										
										String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
										sendToClient1(str2);
									}
									else if (playerNumberAttacked == 2) {
										
										String str = "caNHasDisarmed2 :" + "yes";
										sendToAllClients(str);
										
										String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
										sendToClient2(str2);
									}
									else if (playerNumberAttacked == 3) {
										
										String str = "canhasDisarmed3 :" + "yes";
										sendToAllClients(str);
										
										String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
										sendToClient3(str2);
									}
									else if (playerNumberAttacked == 4) {
										
										String str = "canHAsDisarmed4 :" + "yes";
										sendToAllClients(str);
										
										String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
										sendToClient4(str2);
									}									
				  	  	  			
				  	  	  			
					  	  	  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
									  		centerscrolltext.append("\n" + "> Your opponent has been disarmed.");
									  		
									  		String str = "> " + ArrayOfPlayers.player[playerNumberAttacked] + " is disarmed.";
											sendToAllClients(str);											
											
									  		
									  		final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
								  	  	  			if (numberOfPlayers == 2) {
								  	  	  			
										  	  	  		if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {				
															
										  	  	  			gameEngine1V1502();    							
														}	
										  	  	  		else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0])) {		
											    			
											    			turn();   							
														}
								  	  	  			}
									  	  	  	}
								  	  	  	}, 2000);
							  	  	  	}
						  	  	  	}, 2000);																	
								}
					
				  	  	  		else if (ArrayOfAttackResult.attackResult[0] <= 14 && ArrayOfAttackResult.attackResult[0] > 0) {
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
									  		centerscrolltext.append("\n" + "> Your attempt to disarm misses.");
									  		
									  		String str = "> " + ArrayOfPlayers.player[5] + "'s" + " attempt to disarm misses.";
											sendToAllClients(str);
									  		
									  		
									  		final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
								  	  	  			if (numberOfPlayers == 2) {
								  	  	  			
										  	  	  		if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {				
															
										  	  	  			gameEngine1V1502();    							
														}	
										  	  	  		else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0])) {		
											    			
											    			turn();   							
														}
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
						
				  		String str = "> " + ArrayOfPlayers.player[5] + " rolls "	+ ArrayOfAttackResult.attackResult[0] + ".";
						sendToAllClients(str);
						
				  		
				  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (ArrayOfAttackResult.attackResult[0] >= 17) {
									
					  	  	  		canHasDisarmed[playerNumberAttacked] = "yes";
					  	  	  		
					  	  	  		disarmedTurnStart[playerNumberAttacked] = ArrayOfTurn.turn[0];
									
					  	  	  		
									if (playerNumberAttacked == 0) {
										
										String str = "CanHasDisarmed0 :" + "yes";
										sendToAllClients(str);
										
										String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
										sendToClient0(str2);
									}
									else if (playerNumberAttacked == 1) {
										
										String str = "cAnHasDisarmed1 :" + "yes";
										sendToAllClients(str);
										
										String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
										sendToClient1(str2);
									}
									else if (playerNumberAttacked == 2) {
										
										String str = "caNHasDisarmed2 :" + "yes";
										sendToAllClients(str);
										
										String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
										sendToClient2(str2);
									}
									else if (playerNumberAttacked == 3) {
										
										String str = "canhasDisarmed3 :" + "yes";
										sendToAllClients(str);
										
										String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
										sendToClient3(str2);
									}
									else if (playerNumberAttacked == 4) {
										
										String str = "canHAsDisarmed4 :" + "yes";
										sendToAllClients(str);
										
										String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
										sendToClient4(str2);
									}									
				  	  	  			
				  	  	  			
					  	  	  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
									  		centerscrolltext.append("\n" + "> Your opponent has been disarmed.");
									  		
									  		String str = "> " + ArrayOfPlayers.player[playerNumberAttacked] + " is disarmed.";
											sendToAllClients(str);
											
									  		
									  		final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
								  	  	  			if (numberOfPlayers == 2) {
								  	  	  			
										  	  	  		if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {				
															
										  	  	  			gameEngine1V1502();    							
														}	
										  	  	  		else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0])) {		
											    			
											    			turn();   							
														}
								  	  	  			}
									  	  	  	}
								  	  	  	}, 2000);
							  	  	  	}
						  	  	  	}, 2000);																		
								}
					
				  	  	  		else if (ArrayOfAttackResult.attackResult[0] <= 16 && ArrayOfAttackResult.attackResult[0] > 1) {
				  	  	  		
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
									  		centerscrolltext.append("\n" + "> Your attempt to disarm misses.");
									  		
									  		String str = "> " + ArrayOfPlayers.player[5] + "'s" + " attempt to disarm misses.";
											sendToAllClients(str);
									  		
									  		
									  		final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
								  	  	  			if (numberOfPlayers == 2) {
								  	  	  			
										  	  	  		if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {				
															
										  	  	  			gameEngine1V1502();    							
														}	
										  	  	  		else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0])) {		
											    			
											    			turn();   							
														}
								  	  	  			}
									  	  	  	}
								  	  	  	}, 2000);
							  	  	  	}
						  	  	  	}, 2000);								
								}
								
				  	  	  		else if (ArrayOfAttackResult.attackResult[0] <= 1) {
									
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> You have rolled a critical miss...");
									
							  		String str = "> " + ArrayOfPlayers.player[5] + " rolls "	+ "a critical miss...";
									sendToAllClients(str);
							  		
							  		
							  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			criticalMiss();
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
					  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0]	+ ", +2 for Bless Spell = " + (ArrayOfAttackResult.attackResult[0] + 2) + ".");
					  		
					  		String str = "> " + ArrayOfPlayers.player[5] + " rolls "	+ ArrayOfAttackResult.attackResult[0] + ", +2 for Bless Spell = " + (ArrayOfAttackResult.attackResult[0] + 2) + ".";
							sendToAllClients(str);
						}
						
		  	  	  		else if (canHasDisarmed[playerNumberAttacked].equals("yes")) {					
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ", +4 for Bless Spell and opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 4) + ".");
					  		
					  		String str = "> " + ArrayOfPlayers.player[5] + " rolls "	+ ArrayOfAttackResult.attackResult[0] + ", +4 for Bless Spell and opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 4) + ".";
							sendToAllClients(str);					  		
						}												
						
						final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {						
				
								if (ArrayOfAttackResult.attackResult[0] == 20) {//WAS >= 20
									
									criticalHit();
									return;
								}
								
								else if (canHasDisarmed[playerNumberAttacked].equals("no")) {
									
									if ((ArrayOfAttackResult.attackResult[0] >= 12 && ArrayOfAttackResult.attackResult[0] <= 19)  || (ArrayOfAttackResult.attackResult[0] + 2) > 20) {
										
										centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);			  		
										centerscrolltext.append("\n" + "> Your attack hits.");
										
										String str = "> " + ArrayOfPlayers.player[5] + "'s"	+ " attack hits.";
										sendToAllClients(str);
										
										
										// Only one spell can be cast in a round (no spell can be cast in conjunction with another).										
										
										
										damage();
										return;
									}
									
									else if (ArrayOfAttackResult.attackResult[0] < 12 && ArrayOfAttackResult.attackResult[0] > 0) {
										
										// don't critically miss when using bless.
										
										isblessrolled = "no";
										
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Your attack misses.");
										
										String str = "> " + ArrayOfPlayers.player[5] + "'s"	+ " attack misses.";
										sendToAllClients(str);
										
										
										final Handler h3 = new Handler();
							  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
							  	  	  			if (numberOfPlayers == 2) {
				
									  	  	  		if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {				
														
									  	  	  			gameEngine1V1502();    							
													}	
									  	  	  		else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0])) {		
										    			
										    			turn();   							
													}
							  	  	  			}
								  	  	  	}
							  	  	  	}, 2000);
										//NEED THIS?:
										return;
									}
								}
								
								else if (canHasDisarmed[playerNumberAttacked].equals("yes")) {
									
									if (ArrayOfAttackResult.attackResult[0] >= 10 && ArrayOfAttackResult.attackResult[0] <= 19  || (ArrayOfAttackResult.attackResult[0] + 2) > 20) {
										
										centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);			  		
										centerscrolltext.append("\n" + "> Your attack hits.");
										
										String str = "> " + ArrayOfPlayers.player[5] + "'s"	+ " attack hits.";
										sendToAllClients(str);
										
										
										
										// Only one spell can be cast in a round (no spell can be cast in conjunction with another).
										
										
										damage();
										//NEED THIS?:
										return;
									}
									
									else if (ArrayOfAttackResult.attackResult[0] < 10 && ArrayOfAttackResult.attackResult[0] > 0) {
										
										// don't critically miss when using bless.
										
										isblessrolled = "no";
										
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Your attack misses.");
										
										String str = "> " + ArrayOfPlayers.player[5] + "'s"	+ " attack misses.";
										sendToAllClients(str);
										
										
										final Handler h3 = new Handler();
							  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
							  	  	  			if (numberOfPlayers == 2) {
				
									  	  	  		if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0])) {				
														
									  	  	  			gameEngine1V1502();    							
													}	
									  	  	  		else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0])) {		
										    			
										    			turn();   							
													}
							  	  	  			}
								  	  	  	}
							  	  	  	}, 2000);							  	  	  	
									}										
								}							
			  	  	  		}
			  	  	  	}, 2000);
	  	  	  		}
	  	  	  	}, 2000);								 
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
				  		
				  		String str = "> " + ArrayOfPlayers.player[5] + " rolls " + ArrayOfAttackResult.attackResult[0] + ".";
						sendToAllClients(str);				  		
				  		
						
				  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (ArrayOfAttackResult.attackResult[0] >= 17) {
									
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> You hit yourself.");
							  		
							  		String str = "> " + ArrayOfPlayers.player[5] + " hits itself.";
									sendToAllClients(str);
							  		
							  		
							  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + ", roll for damage...");						  	  	  			
						  	  	  			
											String str = "> " + ArrayOfPlayers.player[5] + " is rolling for damage...";
											sendToAllClients(str);
											
											
						  	  	  			criticalMissDamage();					  	  	  			
							  	  	  	}
						  	  	  	}, 2000);																	
								}
								
								else {
									
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + ", you did not hit yourself, now you must roll to see if you lose your weapon...");
									
							  		String str = "> " + ArrayOfPlayers.player[5] + " did not hit itself, but must roll to see if it loses it's weapon...";
									sendToAllClients(str);
							  		
							  		
							  		criticalMissLoseWeapon();						  		
								}		  	  	  			
				  	  	  	}
			  	  	  	}, 2000);	  	  	  			
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
						
				  		String str = "> " + ArrayOfPlayers.player[5] + " rolls " + ArrayOfAttackResult.attackResult[0] + ".";
						sendToAllClients(str);
				  		
				  		
						if (ArrayOfAttackResult.attackResult[0] >= 17) {
							
							canHasDisarmed[5] = "yes";
							
							String str2 = "canHaSDisarmed5 :" + "yes";
							sendToAllClients(str2);
							
							
							didHumanCriticalMiss[5] = "yes";
							
							String str3 = "didHuManCriticalMiss5 :" + "yes";
							sendToAllClients(str3);
							
							
							disarmedTurnStart[5] = ArrayOfTurn.turn[0];
							
							TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
		  	  	  			disarmedtextleft.setVisibility(View.VISIBLE);
				  	  	  	disarmedtextleft.bringToFront();
				  	  	  	
							
							final Handler h2 = new Handler();
				  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> You are disarmed.");
							  		
							  		String str4 = "> " + ArrayOfPlayers.player[5] + " is disarmed.";
									sendToAllClients(str4);
							  		
							  		
							  		final Handler h3 = new Handler();
						  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {	
						  	  	  			
						  	  	  			if (numberOfPlayers == 2) {
						  	  	  			
								  	  	  		if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {				
													
								  	  	  			gameEngine1V1502();    							
												}			
								  	  	  		else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {		
									    			
									    			turn();   							
												}
												
												// TAKE THIS OUT?:											
								  	  	  		else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {				
													
									    			hastePartTwo();    							
												}			
								  	  	  		else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {		
									    			
													hastePartTwo();   							
												}
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
							  		
							  		String str5 = "> " + ArrayOfPlayers.player[5] + " holds on to it's weapon.";
									sendToAllClients(str5);
							  		
							  		
							  		final Handler h5 = new Handler();
						  	  	  	h5.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			if (numberOfPlayers == 2) {
						  	  	  			
								  	  	  		if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {				
													
								  	  	  			gameEngine1V1502();    							
												}			
								  	  	  		else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {		
									    			
									    			turn();   							
												}											
								  	  	  		else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {				
													
									    			hastePartTwo();    							
												}			
								  	  	  		else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {		
									    			
													hastePartTwo();   							
												}
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
	
	
	//=============================================================================================
	//SEPERATOR
	//=============================================================================================	
	
	
	public void criticalHit() {
		
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
	  			
	  			String str = "criticalHitGraphic";
				sendToAllClients(str);
	  	  	  	
	  	  	  			
  	  	  		final Handler h2 = new Handler();
	  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		//final TextView criticalHitGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//criticalHitGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
	  	  	  			String str2 = "stopGraphics";
	  	  	  			sendToAllClients(str2);
	  	  	  			
	  	  	  			// IF DODGE BLOW:
	  	  	  			
	  					if (dodgeBlowSpell[playerNumberAttacked] > 0) {	  						
	  						
	  						if(numberOfPlayers == 2) {
	  							
	  							String str3 = "rollDge";
	  							sendToClient0(str3);
	  							
	  							plyerAskedToDodgeCritHit[0] = "yes";
	  							
	  							checkForDodgeRoll();
	  						}																
	  					}		  	  	  			
	  	  	  			
	  					// IF NO DODGE BLOW:
	  					
	  	  	  			else {
	  	  	  				
		  	  	  			final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			if (mightyBlowSpell[5] > 0 && ishasteused.equals("no") && isblessrolled.equals("no")) {
								
										AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
							  			
										alert.setCancelable(false);
										
							  	    	alert.setTitle(ArrayOfPlayers.player[5] + ", do you want to use Mighty Blow?");
							  	    	/*
							  	    	alert.setMessage("something");
							  	    	*/	  	    	
							  	    	
							  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							  		    	public void onClick(DialogInterface dialog, int whichButton) {							  		    		
							  		    		
							  		    		//hideNavigation();
							  		    		
							  		    		mightyBlowSpell[5] = mightyBlowSpell[5] - 1;
							  		    		
							  		    		String str4 = "usedMightyBlow";
												sendToAllClients(str4);
							  		    		
												
							  		    		skillsCheck();
							  		    		
							  		    		String str5 = "skillsCheck";
												sendToAllClients(str5);
												
							  		    		
							  		    		criticalHitMightyBlowPartOne();							  					
							  		    		
							  		    		dialog.dismiss();
							  		    	}
							  	    	});
							  	    	
							  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
							          	  public void onClick(DialogInterface dialog, int whichButton) {
							          		
							          		//hideNavigation();
							          		  
							          		criticalHitPartOne();											
							          		
							          		dialog.dismiss();
							          	  }
							          	});
							  	    	
							  	    	alert.show();							
				  	  	  			}
				  	  	  			
				  	  	  			else {
		  	  	  				
				  	  	  				criticalHitPartOne();				  	  	  				
				  	  	  			}
					  	  	  	}
				  	  	  	}, 2000);			  	  	  				
	  	  	  			}
		  	  	  	}
	  	  	  	}, 6000);		  	  	  	  	  			  	  	  			
  	  	    }
		});	
	}
	
	public void criticalHit2() {
		
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
		  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[playerNumberAttacked] + " does not dodge.");
		  		
		  		String str = "> " + ArrayOfPlayers.player[playerNumberAttacked] + " does not dodge.";
				sendToAllClients(str);
		  		
		  		
			  	final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
	  	  	  			if (mightyBlowSpell[5] > 0 && ishasteused.equals("no") && isblessrolled.equals("no")) {
				
							AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
				  			
							alert.setCancelable(false);
							
				  	    	alert.setTitle(ArrayOfPlayers.player[5] + ", do you want to use Mighty Blow?");
				  	    	/*
				  	    	alert.setMessage("something");
				  	    	*/	  	    	
				  	    	
				  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				  		    	public void onClick(DialogInterface dialog, int whichButton) {								  		    		
				  		    		
				  		    		//hideNavigation();
				  		    		
				  		    		mightyBlowSpell[5] = mightyBlowSpell[5] - 1;
				  		    		
				  		    		String str2 = "usedMightyBlow";
									sendToAllClients(str2);
				  		    		
									
				  		    		skillsCheck();
				  		    		
				  		    		String str3 = "skillsCheck";
									sendToAllClients(str3);
									
				  		    		
				  		    		criticalHitMightyBlowPartOne();				  					
				  		    		
				  		    		dialog.dismiss();
				  		    	}
				  	    	});
				  	    	
				  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
				          	  public void onClick(DialogInterface dialog, int whichButton) {
				          		
				          		//hideNavigation();
				          		  
				          		criticalHitPartOne();								
				          		
				          		dialog.dismiss();
				          	  }
				          	});
				  	    	
				  	    	alert.show();
	  	  	  			}
	  	  	  			
	  	  	  			else {
	  				
	  	  	  				criticalHitPartOne();	  	  	  				
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
	  			
				
	  			// IF DODGE BLOW:
	  			
	  			if (dodgeBlowSpell[playerNumberAttacked] > 0) {
	  				
	  				if(numberOfPlayers == 2) {  					
							
						String str = "rollDge";
						sendToClient0(str);
						
						playerAskedToDodgeDamage[0] = "yes";
						
						checkForDodgeRoll();
					}										
				}
	  			
	  			// IF NO DODGE BLOW:
	  			
	  			else {
	  			
	  				final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
			  	  	  		if (mightyBlowSpell[5] > 0 && ishasteused.equals("no") && isblessrolled.equals("no") && issecondroundofhasteused.equals("no")) {								
								
								AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
					  			
								alert.setCancelable(false);
								
					  	    	alert.setTitle(ArrayOfPlayers.player[5] + ", do you want to use Mighty Blow?");
					  	    	/*
					  	    	alert.setMessage("something");
					  	    	*/	  	    	
					  	    	
					  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					  	    		public void onClick(DialogInterface dialog, int whichButton) {
					  		    		
					  		    		//hideNavigation();
					  		    		
					  		    		mightyBlowSpell[5] = mightyBlowSpell[5] - 1;
					  		    		
					  		    		String str2 = "usedMightyBlow";
										sendToAllClients(str2);
					  		    		
										
					  		    		skillsCheck();
					  		    		
					  		    		String str3 = "skillsCheck";
										sendToAllClients(str3);
										
										
					  		    		mightyBlow();										
					  		    		
					  		    		dialog.dismiss();
					  		    	}
					  	    	});
					  	    	
					  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
					  	    		public void onClick(DialogInterface dialog, int whichButton) {					  	    			
					          		  
					  	    			//hideNavigation();
					          		  
					  	    			damagePartTwo();					          		  
					          		  
					  	    			dialog.dismiss();
					          	  }
					          	});	  	    	
					  	    	
					  	    	alert.show();							
							}
			  	  	  		
							else {
								
								damagePartTwo();								
							}				  	  	  			
			  	  	  	}
		  	  	  	}, 2000);
	  			}	  			
  	  	    }  	  	    
		});		
	}
	
	
	public void damage2() {
		
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
		  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[playerNumberAttacked] + " does not dodge.");
		  		
		  		String str = "> " + ArrayOfPlayers.player[playerNumberAttacked] + " does not dodge.";
				sendToAllClients(str);				
				
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		if (mightyBlowSpell[5] > 0 && ishasteused.equals("no") && isblessrolled.equals("no") && issecondroundofhasteused.equals("no")) {											
							
							AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
				  			
							alert.setCancelable(false);
							
				  	    	alert.setTitle(ArrayOfPlayers.player[5] + ", do you want to use Mighty Blow?");
				  	    	/*
				  	    	alert.setMessage("something");
				  	    	*/  	    	
				  	    	
				  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				  		    	public void onClick(DialogInterface dialog, int whichButton) {
				  		    		
				  		    		//hideNavigation();
				  		    		
				  		    		mightyBlowSpell[5] = mightyBlowSpell[5] - 1;
				  		    		
				  		    		String str2 = "usedMightyBlow";
									sendToAllClients(str2);
				  		    		
									
				  		    		skillsCheck();
				  		    		
				  		    		String str3 = "skillsCheck";
									sendToAllClients(str3);
				  		    		
									
				  		    		mightyBlow();									
				  		    		
				  		    		dialog.dismiss();
				  		    	}
				  	    	});
				  	    	
				  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
				          	  public void onClick(DialogInterface dialog, int whichButton) {
				          		  
				          		  //hideNavigation();
				          		  
				          		  damagePartTwo();				          		  
				          		  
				          		  dialog.dismiss();
				          	  }
				          	});	  	    	
				  	    	
				  	    	alert.show();										
						}
		  	  	  		
						else {
							
							damagePartTwo();							
						}				  	  	  			
		  	  	  	}
	  	  	  	}, 2000);		
  	  	    }
		});		
	}
	
	public void hastePartTwo() {
		
		if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] <= 0) {
			
			endGame();
		}
		
		/*
		else if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] == 0 && cureSpell[1] < 1) {		
			
			ishasteused = "no";
			issecondroundofhasteused = "yes";
			
			if (isInvokingService.equals("true")){
				//NEED THIS?
				SystemClock.sleep(1000);
				
				endGameAfterFirstHaste = "yes";
				
				endGame();
			}
		}
		*/
		
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
		  			
		  			
		  			final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
		  			computerHitPointsTextView.setTypeface(typeFace);
		  			computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));	  			
		  			//Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);				
		  			//computerHitPointsTextView.startAnimation(animPulsingAnimation);		  			
		  			
		  			
		  			// SO IF PLAYER KILLS/KNOCKS OUT COMP ON 1ST RD OF HASTE:
		  			//endGame();	  			
		  			
		  			
		  			// THIS IS WRONG - CAN GET 2ND ATTACK, YOU'RE JUST DISARMED:
					//if (canHasDisarmed[i] == "yes")// so if you critically miss & drop weapon you don't get 2nd attack.
					//{
					//	return;
					//}		  				
	  				
					
					final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
		  	  	  			computerHitPointsTextView.clearAnimation();
		  	  	  			
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
							
							String str = "> SECOND attack...";
		  	  	  			sendToAllClients(str);
							
							
							attack();
			  	  	  	}
		  	  	  	}, 2000);
	  				
		  	  	  	// CAN'T USE CURE OR ANOTHER HASTE WITH A HASTE:
	  				//punch(); // SAME AS ATTACK
		  	  	  	
	  				//attack();
		  	  	  	
	  				// NEED THIS?:
	  				//return;  											 
	  	  	    }
			});						
		}			
	}	
	
	public void criticalMiss() {				
		
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
	  			
	  			String str = "criticalMissGraphic";
				sendToAllClients(str);
	  					
	  	  	  			
  	  	  		final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {			  	  	  			

		  	  	  		//final TextView criticalMissGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//criticalMissGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
	  	  	  			String str2 = "stopGraphics";
	  	  	  			sendToAllClients(str2);
	  	  	  			
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
	  			  		centerscrolltext.startAnimation(animAlphaText);
	  					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + ", you must roll to see if you hit yourself...");
	  					
	  					String str = "> " + ArrayOfPlayers.player[5] + " must roll to see if it hit itself...";
	  					sendToAllClients(str);
	  					
	  					
	  					criticalMissAttack();	  					 
		  	  	  	}
	  	  	  	}, 6000);	  	  	  				
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
				  	  	  		
			  	  	  			centerscrolltext.bringToFront();		  	  	  			
								centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> Please slide the die...");
								
								ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				  	  	    	titleBlankButton.bringToFront();
								
						
								SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Host.this);
								SharedPreferences.Editor editor = preferences.edit();		
								editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
								editor.apply();
								
						        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
						        //same as: (int) Math.ceil(Math.random()*6); ?
								
								iscriticalmissdamagerolled = "yes";
								preventattackdamagediefromleaking = "off";
								
								
								ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
			  		  			chatBlankButton.bringToFront();
			  	  	  		}
			  	  	  	}, 750);					  	  	  		
	  	  	  		}
	  	  	  	}, 2000);  	  	  									
  	  	    }
		});		
	}
	
	
	public void criticalMissDamageResults() {
		
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
	  	  	  			
		  	  	  		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Host.this);
						//SharedPreferences.Editor editor = preferences.edit();
						int attackDamage = preferences.getInt("attackDamage", 0);
			  			
						centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> You roll " + attackDamage + ".");
						
						String str = "> " + ArrayOfPlayers.player[5] + " rolls " + attackDamage + ".";
						sendToAllClients(str);
						
	
						ArrayOfHitPoints.hitpoints[5] = ArrayOfHitPoints.hitpoints[5] - attackDamage;
						
						String str2 = "5ArrayOfHitPoints.hitpoints[5] :" + ArrayOfHitPoints.hitpoints[5];
						sendToAllClients(str2);
						
						
						final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
		    			playerHitPointsTextView.setTypeface(typeFace);
		    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
		    			//playerHitPointsTextView.bringToFront();
		    			Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);
		    			playerHitPointsTextView.startAnimation(animPulsingAnimation);		    			
		    			
		    			
						final Handler h = new Handler();
		  	  	  		h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {  	  		
			  	  	  			
			  	  	  			playerHitPointsTextView.clearAnimation();			  	  	  			
			  	  	  			
			  	  	  			
								if (ArrayOfHitPoints.hitpoints[5] <= 0) {
									
									String str3 = ArrayOfPlayers.player[5] + "has been slain!";
									sendToAllClients(str3);
									
									/*
									 * 
									 * Picture of one sword destroying another.
									 * 
									 * deathGraphic();
									 * 
									 */									
									
										
									AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
								    
									alert.setCancelable(false);
									
									alert.setTitle("You have been slain.");
						  	    	/*
						  	    	alert.setMessage("something");
						  	    	*/	    	
							    	
									
									// if back pressed: DOES THIS WORK????????????
									alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
										@Override
										public void onCancel(DialogInterface dialog) {
											
											playerDeadYet[5] = "yes";
											
											String str4 = "playerDeadYet5 :" + "yes";
											sendToAllClients(str4);
											
								    		
								    		gameOverCheck();
								    		
								    		dialog.dismiss();
										}
									});									
									
									
							    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
								    	public void onClick(DialogInterface dialog, int whichButton) {
								    		
								    		//hideNavigation();
								    		
								    		playerDeadYet[5] = "yes";
								    		
								    		String str4 = "playerDeadYet5 :" + "yes";
											sendToAllClients(str4);
								    		
								    		
								    		gameOverCheck();
								    		
								    		dialog.dismiss();
								    	}
							    	});						    	
							    	alert.show();																							
								}
								
								else {						
									
									if (numberOfPlayers == 2) {
									
										if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {				
											
							    			gameEngine1V1502();
										}	
										else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {		
							    			
							    			turn();   							
										}
										
										// TAKE THIS OUT?:									
										else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {				
											
							    			hastePartTwo();    							
										}	
										else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {		
							    			
											hastePartTwo();   							
										}
									}
								}
				  	  	  	}
			  	  	  	}, 2000);
		  	  	  	}
	  	  	  	}, 2000);  						
  	  	    }
		});		
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
								
						
								ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);//(int) ((Math.random() * 20) + 1)
								//int attackResult = (int) ((Math.random() * 20) + 1);										
								
								iscriticalmissloseweaponrolled = "yes";
								
								// Re-enables ability to use srollbar:
					  			centerscrolltext.bringToFront();
					  			
					  			
					  			ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
			  		  			chatBlankButton.bringToFront();
			  	  	  		}
			  	  	  	}, 750);					  	  	  		
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
	  			
	  			String str = "mghtyBlowGraphic";
				sendToAllClients(str);
				
				
				final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		//final TextView mightyBlowGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//mightyBlowGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
	  	  	  			String str2 = "stopGraphics";
	  	  	  			sendToAllClients(str2);
	  	  	  			
	  	  	  			
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
				  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + ", you roll twice for critical hit damage.");
				  		
				  		String str3 = "> " + ArrayOfPlayers.player[5] + " rolls twice for critical hit damage...";
	  	  	  			sendToAllClients(str3);
	  	  	  			
				  		
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
						  	  	  		
					  	  	  			centerscrolltext.bringToFront();					  	  	  			
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Make your first roll...");
										
										ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
						  	  	    	titleBlankButton.bringToFront();								
										
										
										ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] = (int)(Math.random()*6)+1;
								        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
								        //same as: (int) Math.ceil(Math.random()*6); ?										
										
										iscriticalhitmightyblowfirstrollrolled = "yes";
										
										
										ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
					  		  			chatBlankButton.bringToFront();
					  	  	  		}
					  	  	  	}, 750);					  	  	  		
			  	  	  		}
			  	  	  	}, 2000);
	  	  	  		}
	  	  	  	}, 6000);		  	  	  					  			
	  	    }  	  	    
		});		
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
				  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[5] + ", you roll twice for critical hit damage.");
				  		
				  		String str = "> " + ArrayOfPlayers.player[5] + " rolls twice for critical hit damage...";
	  	  	  			sendToAllClients(str);
	  	  	  			
				  		 
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
						  	  	  		
					  	  	  			centerscrolltext.bringToFront();					  	  	  			
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Make your first roll...");
										
										ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
						  	  	    	titleBlankButton.bringToFront();								
										
										
										ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] = (int)(Math.random()*6)+1;
								        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
								        //same as: (int) Math.ceil(Math.random()*6); ?										
										
										iscriticalhitfirstrollrolled = "yes";
										
										
										ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
					  		  			chatBlankButton.bringToFront();
					  	  	  		}
					  	  	  	}, 750);					  	  	  		
			  	  	  		}
			  	  	  	}, 2000);
	  	  	  		}
	  	  	  	}, 2000);  	  	  					
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
	  			
	  			String str = "mghtyBlowGraphic";
				sendToAllClients(str);	  			
									
					
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
	  	  	  			
	  	  	  			String str2 = "stopGraphics";
	  	  	  			sendToAllClients(str2);
	  	  	  			
	  	  	  			
		  	  	  		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
		  	  	  		img.bringToFront();
		  	  	  		
	  	  	  			sixSidedRollFromLeft();				  	  	  		
			  	  	  			
		  	  	  		final Handler h3 = new Handler();
			  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {				
			  	  	  			
			  	  	  			sixSidedWobbleStart();
			  	  	  			
			  	  	  			isSixSidedReadyToBeRolled = "yes";					  	  	  			
			  	  	  			
			  	  	  			centerscrolltext.bringToFront();					  	  	  			
								centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> Please slide the die...");
								
								ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				  	  	    	titleBlankButton.bringToFront();							
								
								
								SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Host.this);
								SharedPreferences.Editor editor = preferences.edit();		
								editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
								editor.apply();
								
						        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
						        //same as: (int) Math.ceil(Math.random()*6); ?										
								
								ismightyblowdamagerolled = "yes";
								preventattackdamagediefromleaking = "off";
								
								
								ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
			  		  			chatBlankButton.bringToFront();
			  	  	  		}
			  	  	  	}, 750);
	  	  	  		}
	  	  	  	}, 6000);
				
				// NO DODGING MIGHTY BLOW				  			
  	  	    }  	  	    
		});		
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
				
				String str = "> " + ArrayOfPlayers.player[5] + " is rolling for damage...";
				sendToAllClients(str);
				
				
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
			  	  	  			
			  	  	  			centerscrolltext.bringToFront();
			  	  	  			centerscrolltext.setVisibility(View.VISIBLE);
			  	  	  			
			  	  	  			ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
			  	  	  			titleBlankButton.bringToFront();						
								
								
			  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Host.this);
			  	  	  			SharedPreferences.Editor editor = preferences.edit();		
			  	  	  			editor.putInt("attackDamage", (int) ((Math.random() * 6) + 1));
			  	  	  			editor.apply();
			  	  	  			
						        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
						        //same as: (int) Math.ceil(Math.random()*6); ?										
								
								isattackdamagerolled = "yes";
								preventattackdamagediefromleaking = "off";
								
								
								ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
			  		  			chatBlankButton.bringToFront();
			  	  	  		}
			  	  	  	}, 750);					  	  	  		
	  	  	  		}
	  	  	  	}, 2000);  	  	  			  			
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
				  	  	  		
			  	  	  			centerscrolltext.bringToFront();
			  	  	  			centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> Please slide the die...");
								
								ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				  	  	    	titleBlankButton.bringToFront();						
								
						
								ArrayOfAttackResult.attackResult[0] = (int) ((Math.random() * 20) + 1);
								//int attackResult = (int) ((Math.random() * 20) + 1);										
								
								iscriticalmissrolled = "yes";
								
								// Re-enables ability to use srollbar:
					  			centerscrolltext.bringToFront();
					  			
					  			
					  			ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
			  		  			chatBlankButton.bringToFront();
			  	  	  		}
			  	  	  	}, 750);					  	  	  		
	  	  	  		}
	  	  	  	}, 3000);			
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
	  	  	  			
		  	  	  		if (canHasDisarmed[5].equals("no")) {							
							
		  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Host.this);
		  	  	  			//SharedPreferences.Editor editor = preferences.edit();
		  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
		  	  	  			
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll " + attackDamage + ".");
							
							String str = "> " + ArrayOfPlayers.player[5] + " rolls " + attackDamage + ".";
							sendToAllClients(str);							
							
							
							String str2 = playerNumberAttacked + "ArrayOfHitPoints.hitpoints[" + playerNumberAttacked + "] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - attackDamage);
							sendToAllClients(str2);
							
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - attackDamage;							
							
							
							//playerNumberAttackedHitPoints();							
							
							TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
							playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
							playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
							//playerNumberAttackedHitPointsTextView.bringToFront();
							Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);				
							playerNumberAttackedHitPointsTextView.startAnimation(animPulsingAnimation);
							
							
							damageResultsHandler();
						}
							
		  	  	  		else if (canHasDisarmed[5].equals("yes")) {							
							
		  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Host.this);
		  	  	  			//SharedPreferences.Editor editor = preferences.edit();
		  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
		  	  	  			
							int attackDamageDisarmed = (attackDamage - 2);
							
							if (attackDamageDisarmed < 0) {
								
				                  attackDamageDisarmed = 0;					            
							}
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll " + attackDamage + ", -2 damage for punch = " + attackDamageDisarmed + " damage.");
							
							String str = "> " + ArrayOfPlayers.player[5] + " rolls " + attackDamage + ", -2 damage for punch = " + attackDamageDisarmed + " damage.";
							sendToAllClients(str);
							
							
							String str2 = playerNumberAttacked + "ArrayOfHitPoints.hitpoints[" + playerNumberAttacked + "] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - attackDamageDisarmed);
							sendToAllClients(str2);
							
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - attackDamageDisarmed;							
							
							
							//playerNumberAttackedHitPoints();							
							
							TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
							playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
							playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
							//playerNumberAttackedHitPointsTextView.bringToFront();
							Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);				
							playerNumberAttackedHitPointsTextView.startAnimation(animPulsingAnimation);
							
							
							damageResultsHandler();
						}		  	  	  			  	  	  		
	  	  	  		}
	  	  	  	}, 2000);								 
  	  	    }
		});		
	}
	/*
	public void playerNumberAttackedHitPoints() {
		
		if (playerNumberAttacked == 0) {
			
			String str = "ArrayOfHitPoints.hitpoints[0] :" + ArrayOfHitPoints.hitpoints[0];
			sendToClient0(str);			
		}
		else if (playerNumberAttacked == 1) {
			
			String str = "ArrayOfHitPoints.hitpoints[0] :" + ArrayOfHitPoints.hitpoints[1];
			sendToClient1(str);			
		}
		else if (playerNumberAttacked == 2) {
			
			String str = "ArrayOfHitPoints.hitpoints[0] :" + ArrayOfHitPoints.hitpoints[2];
			sendToClient2(str);			
		}
		else if (playerNumberAttacked == 3) {
			
			String str = "ArrayOfHitPoints.hitpoints[0] :" + ArrayOfHitPoints.hitpoints[3];
			sendToClient3(str);			
		}
		else if (playerNumberAttacked == 4) {
			
			String str = "ArrayOfHitPoints.hitpoints[0] :" + ArrayOfHitPoints.hitpoints[4];
			sendToClient4(str);			
		}
	}
	*/
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
	  	  	  			
	  	  	  			TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
	  	  	  			playerNumberAttackedHitPointsTextView.clearAnimation();	  	  	  			
	  	  	  			
	  	  	  			
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] <= 0) {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[playerNumberAttacked] + " has been slain!");
							
							String str = ArrayOfPlayers.player[playerNumberAttacked] + " has been slain!";
							sendToAllClients(str);
							
							
							playerDeadYet[playerNumberAttacked] = "yes";
							
							String str2 = "playerDeadYet" + playerNumberAttacked + " :" + "yes";
							sendToAllClients(str2);
							
				    		
				    		gameOverCheck();							
							
							  
							// Picture of one sword destroying another.
							 
							// deathGraphic();
							 
							
				    		/*
							AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
						    
							alert.setCancelable(false);
							
							alert.setTitle(ArrayOfPlayers.player[playerNumberAttacked] + " has been slain.");
				  	    	
				  	    	//alert.setMessage("something");
				  	    		    	
					    	
							
							// if back pressed: DOES THIS WORK????????????
							alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
								@Override
								public void onCancel(DialogInterface dialog) {
									
									//hideNavigation();
						    		
						    		playerDeadYet[playerNumberAttacked] = "yes";
						    		
						    		gameOverCheck();
						    		
						    		dialog.dismiss();
								}
							});
							
							
					    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int whichButton) {
						    		
						    		//hideNavigation();
						    		
						    		playerDeadYet[playerNumberAttacked] = "yes";
						    		
						    		gameOverCheck();
						    		
						    		dialog.dismiss();
						    	}
					    	});								    	
					    	alert.show();
					    	*/
						}
						
						else {
							
							if (numberOfPlayers == 2) {
	
								if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {				
									
									gameEngine1V1502();    							
								}	
								else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {		
					    			
					    			turn();   							
								}							
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {				
									
					    			hastePartTwo();    							
								}	
								else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {		
					    			
									hastePartTwo();   							
								}
							}
						}				  	  	  			
		  	  	  	}
	  	  	  	}, 2000);
  	  	    }
		});	
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
	  	  	  			
		  	  	  		if (canHasDisarmed[5].equals("no")) {
		  	  	  			
		  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Host.this);
		  	  	  			//SharedPreferences.Editor editor = preferences.edit();
		  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
		  	  	  			
			  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll " + attackDamage	+ ".");
							
							String str = "> " + ArrayOfPlayers.player[5] + " rolls " + attackDamage + ".";
							sendToAllClients(str);
							
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {				  	  	  			
				  	  	  			
				  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Host.this);
				  	  	  			//SharedPreferences.Editor editor = preferences.edit();
				  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
				  	  	  			
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (attackDamage * 2) + ".");
									
									String str2 = "> Double damage for Mighty Blow = " + (attackDamage * 2) + ".";
									sendToAllClients(str2);
									
									
									String str3 = playerNumberAttacked + "ArrayOfHitPoints.hitpoints[" + playerNumberAttacked + "] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (attackDamage * 2));
									sendToAllClients(str3);
									
									ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked]	- (attackDamage * 2);									
									
									
									TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
									playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
									playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
									//playerNumberAttackedHitPointsTextView.bringToFront();
									Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);				
									playerNumberAttackedHitPointsTextView.startAnimation(animPulsingAnimation);
									
									
									mightyBlowResultsHandler();
					  	  	  	}
				  	  	  	}, 2000);						
		  	  	  		}
						
		  	  	  		else if (canHasDisarmed[5].equals("yes")) {							
							
							SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Host.this);
							//SharedPreferences.Editor editor = preferences.edit();
							int attackDamage = preferences.getInt("attackDamage", 0);
							
							int attackDamageDisarmed = (attackDamage - 2);
							
							if (attackDamageDisarmed < 0) {
								
				                  attackDamageDisarmed = 0;					            
							}
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll " + attackDamage + ", -2 damage for punch = " + attackDamageDisarmed + " damage.");
							
							String str4 = "> " + ArrayOfPlayers.player[5] + " rolls " + attackDamage + ", -2 damage for punch = " + attackDamageDisarmed + " damage.";
							sendToAllClients(str4);
							
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Host.this);
				  	  	  			//SharedPreferences.Editor editor = preferences.edit();
				  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
				  	  	  			
					  	  	  		int attackDamageDisarmed = (attackDamage - 2);
									
									if (attackDamageDisarmed < 0) {
										
						                  attackDamageDisarmed = 0;					            
									}
				  	  	  			
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (attackDamageDisarmed * 2) + ".");
									
									String str5 = "> Double damage for Mighty Blow = " + (attackDamageDisarmed * 2) + ".";
									sendToAllClients(str5);
									
									
									String str6 = playerNumberAttacked + "ArrayOfHitPoints.hitpoints[" + playerNumberAttacked + "] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (attackDamageDisarmed * 2));
									sendToAllClients(str6);
									
									ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked]	- (attackDamageDisarmed * 2);								
									
									
									TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
									playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
									playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
									//playerNumberAttackedHitPointsTextView.bringToFront();
									Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);				
									playerNumberAttackedHitPointsTextView.startAnimation(animPulsingAnimation);
									
									
									mightyBlowResultsHandler();
					  	  	  	}
				  	  	  	}, 2000);						
						}			  	  	  			  	  	  		
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
	  	  	  			
	  	  	  			TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
	  	  	  			playerNumberAttackedHitPointsTextView.clearAnimation();	  	  	  			
	  	  	  			
	  	  	  			
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] <= 0) {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[playerNumberAttacked] + " has been slain!");
							
							String str = ArrayOfPlayers.player[playerNumberAttacked] + " has been slain!";
							sendToAllClients(str);
							
							
							playerDeadYet[playerNumberAttacked] = "yes";
							
							String str2 = "playerDeadYet" + playerNumberAttacked + " :" + "yes";
							sendToAllClients(str2);
							
				    		
				    		gameOverCheck();							
							
							 
							// Picture of one sword destroying another.
							 
							// deathGraphic();
							 
							
							/*
							AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
						    
							alert.setCancelable(false);							
							
							alert.setTitle(ArrayOfPlayers.player[playerNumberAttacked] + " has been slain.");
				  	    	
				  	    	//alert.setMessage("something");
				  	    		    	
					    	
							// if back pressed: DOES THIS WORK????????????
							alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
								@Override
								public void onCancel(DialogInterface dialog) {
									
									//hideNavigation();
						    		
						    		playerDeadYet[playerNumberAttacked] = "yes";
						    		
						    		gameOverCheck();
						    		
						    		dialog.dismiss();
								}
							});
							
					    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int whichButton) {
						    		
						    		//hideNavigation();
						    		
						    		playerDeadYet[playerNumberAttacked] = "yes";
						    		
						    		gameOverCheck();
						    		
						    		dialog.dismiss();
						    	}
					    	});								    	
					    	alert.show();
					    	*/																			
						}
						
						else {
							
							if (numberOfPlayers == 2) {
	
								if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {				
									
									gameEngine1V1502();    							
								}	
								else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {		
					    			
					    			turn();   							
								}
							}
						}				  	  	  			
		  	  	  	}
	  	  	  	}, 2000);  	  	    	
  	  	    }
		});	
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
				centerscrolltext.append("\n" + "> You roll " + ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ".");  			
	  			
				String str = "> " + ArrayOfPlayers.player[5] + " rolls " + ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ".";
				sendToAllClients(str);
				
	  			
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
				  	  	  		
			  	  	  			centerscrolltext.bringToFront();			  	  	  			
								centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> Make your second roll...");
								
								String str2 = "> " + ArrayOfPlayers.player[5] + " is making second roll...";
								sendToAllClients(str2);
								
								
								ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				  	  	    	titleBlankButton.bringToFront();						
								
								
								ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] = (int)(Math.random()*6)+1;
						        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
						        //same as: (int) Math.ceil(Math.random()*6); ?										
								
								iscriticalhitsecondrollrolled = "yes";
								
								
								ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
			  		  			chatBlankButton.bringToFront();
			  	  	  		}
			  	  	  	}, 750);					  	  	  		
	  	  	  		}
	  	  	  	}, 2000);
  	  	    }
  	  	});		
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
				centerscrolltext.append("\n" + "> You roll " + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] + ".");
	  			
				String str = "> " + ArrayOfPlayers.player[5] + " rolls " + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] + ".";
				sendToAllClients(str);
				
	  			
	  			final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		if (canHasDisarmed[5].equals("no")) {							
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ".");
							
							String str2 = "> " + ArrayOfPlayers.player[5] + " rolls a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ".";
							sendToAllClients(str2);
							
							
							String str3 = playerNumberAttacked + "ArrayOfHitPoints.hitpoints[" + playerNumberAttacked + "] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]));
							sendToAllClients(str3);
							
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]);							
							
							
							TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
							playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
							playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
							//playerNumberAttackedHitPointsTextView.bringToFront();
							Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);				
							playerNumberAttackedHitPointsTextView.startAnimation(animPulsingAnimation);
							
							
							criticalHitDamageResultsHandler();
						}
							
		  	  	  		else if (canHasDisarmed[5].equals("yes")) {							
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ", -2 damage for punch = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) + ".");
							
							String str4 = "> " + ArrayOfPlayers.player[5] + " rolls a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ", -2 damage for punch = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) + ".";
							sendToAllClients(str4);
							
							
							String str5 = playerNumberAttacked + "ArrayOfHitPoints.hitpoints[" + playerNumberAttacked + "] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2));
							sendToAllClients(str5);
							
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2);							
							
							
							TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
							playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
							playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
							//playerNumberAttackedHitPointsTextView.bringToFront();
							Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);				
							playerNumberAttackedHitPointsTextView.startAnimation(animPulsingAnimation);
							
							
							criticalHitDamageResultsHandler();
						}		  	  	  			  	  	  		
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
		  	  			
		  	  			TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
		  	  			playerNumberAttackedHitPointsTextView.clearAnimation();		  	  			
		  	  			
		  	  			
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] <= 0) {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[playerNumberAttacked] + " has been slain!");
							
							String str = ArrayOfPlayers.player[playerNumberAttacked] + " has been slain!";
							sendToAllClients(str);
							
							
							playerDeadYet[playerNumberAttacked] = "yes";
							
							String str2 = "playerDeadYet" + playerNumberAttacked + " :" + "yes";
							sendToAllClients(str2);
							
				    		
				    		gameOverCheck();						
							
							 
							// Picture of one sword destroying another.
							 
							// deathGraphic();
							 
							
							/*
							AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
						    
							alert.setCancelable(false);
							
							alert.setTitle(ArrayOfPlayers.player[playerNumberAttacked] + " has been slain.");
				  	    	
				  	    	//alert.setMessage("something");
				  	    		    	
					    	
							// if back pressed: DOES THIS WORK????????????
							alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
								@Override
								public void onCancel(DialogInterface dialog) {
									
									//hideNavigation();
						    		
						    		playerDeadYet[playerNumberAttacked] = "yes";
						    		
						    		gameOverCheck();
						    		
						    		dialog.dismiss();
								}
							});
							
					    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int whichButton) {
						    		
						    		//hideNavigation();
						    		
						    		playerDeadYet[playerNumberAttacked] = "yes";
						    		
						    		gameOverCheck();
						    		
						    		dialog.dismiss();
						    	}
					    	});								    	
					    	alert.show();
					    	*/																			
						}
						
						else {
							
							if (numberOfPlayers == 2) {
		
								if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {				
									
									gameEngine1V1502();    							
								}		
								else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {		
					    			
					    			turn();   							
								}							
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {				
									
					    			hastePartTwo();    							
								}		
								else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {		
					    			
									hastePartTwo();   							
								}
							}
						}				  	  	  			
		  	  	  	}
		  	  	}, 2000);
  	  	    }
		});	
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
						centerscrolltext.append("\n" + "> You roll " + ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ".");
	  	  	  			
						String str = "> " + ArrayOfPlayers.player[5] + " rolls " + ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ".";
						sendToAllClients(str);
						
						
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
						  	  	  		
					  	  	  			centerscrolltext.bringToFront();					  	  	  			
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Make your second roll...");
										
										String str2 = "> " + ArrayOfPlayers.player[5] + " is making second roll...";
										sendToAllClients(str2);
										
										
										ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
						  	  	    	titleBlankButton.bringToFront();								
										
										
										ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] = (int)(Math.random()*6)+1;
								        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
								        //same as: (int) Math.ceil(Math.random()*6); ?										
										
										iscriticalhitmightyblowsecondrollrolled = "yes";
										
										
										ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
					  		  			chatBlankButton.bringToFront();
					  	  	  		}
					  	  	  	}, 750);					  	  	  		
			  	  	  		}
			  	  	  	}, 2000);
		  	  	  	}
	  	  	  	}, 2000);		  			
  	  	    }
  	  	});			
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
						centerscrolltext.append("\n" + "> You roll " + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] + ".");
	  	  	  			
						String str = "> " + ArrayOfPlayers.player[5] + " rolls " + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] + ".";
						sendToAllClients(str);
						
						
		  	  	  		final Handler h1 = new Handler();
			  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (canHasDisarmed[5].equals("no")) {							
									
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ".");
									
									String str2 = "> " + ArrayOfPlayers.player[5] + " rolls a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ".";
									sendToAllClients(str2);
									
									
									final Handler h2 = new Handler();
						  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) * 2) + ".");
											
											String str3 = "> Double damage for Mighty Blow = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) * 2) + ".";
											sendToAllClients(str3);
											
											
											String str4 = playerNumberAttacked + "ArrayOfHitPoints.hitpoints[" + playerNumberAttacked + "] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) * 2));
											sendToAllClients(str4);
											
											ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) * 2);										
											
											
											TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
											playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
											playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
											//playerNumberAttackedHitPointsTextView.bringToFront();
											Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);				
											playerNumberAttackedHitPointsTextView.startAnimation(animPulsingAnimation);
											
											
											criticalHitMightyBlowDamageResultsHandler();
						  	  	  			
							  	  	  	}
						  	  	  	}, 2000);						
								}
									
								if (canHasDisarmed[5].equals("yes")) {								
									
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ", -2 damage for punch = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) + " damage.");
									
									String str5 = "> " + ArrayOfPlayers.player[5] + " rolls a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ", -2 damage for punch = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) + " damage.";
									sendToAllClients(str5);
									
									
									final Handler h3 = new Handler();
						  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) * 2) + ".");
										
										String str6 = "> Double damage for Mighty Blow = " + (((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) * 2) + ".";
										sendToAllClients(str6);
										
										
										String str7 = playerNumberAttacked + "ArrayOfHitPoints.hitpoints[" + playerNumberAttacked + "] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) * 2));
										sendToAllClients(str7);
										
										ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) * 2);										
										
										
										TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
										playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
										playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
										//playerNumberAttackedHitPointsTextView.bringToFront();
										Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);				
										playerNumberAttackedHitPointsTextView.startAnimation(animPulsingAnimation);
										
										
										criticalHitMightyBlowDamageResultsHandler();
						  	  	  			
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
		  	  			
		  	  			TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
		  	  			playerNumberAttackedHitPointsTextView.clearAnimation();		  	  			
		  	  			
		  	  			
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] <= 0) {
							
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[playerNumberAttacked] + " has been slain!");
							
							String str = ArrayOfPlayers.player[playerNumberAttacked] + " has been slain!";
							sendToAllClients(str);
							
							
							playerDeadYet[playerNumberAttacked] = "yes";
							
							String str2 = "playerDeadYet" + playerNumberAttacked + " :" + "yes";
							sendToAllClients(str2);
							
				    		
				    		gameOverCheck();						
							
							 
							// Picture of one sword destroying another.
							 
							// deathGraphic();
							 
							
							/*
							AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
						    
							alert.setCancelable(false);
							
							alert.setTitle(ArrayOfPlayers.player[playerNumberAttacked] + " has been slain.");
				  	    	
				  	    	//alert.setMessage("something");
				  	    		    	
					    	
							// if back pressed: DOES THIS WORK????????????
							alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
								@Override
								public void onCancel(DialogInterface dialog) {
									
									//hideNavigation();
						    		
						    		playerDeadYet[playerNumberAttacked] = "yes";
						    		
						    		gameOverCheck();
						    		
						    		dialog.dismiss();
								}
							});							
							
					    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						    	public void onClick(DialogInterface dialog, int whichButton) {
						    		
						    		//hideNavigation();
						    		
						    		playerDeadYet[playerNumberAttacked] = "yes";
						    		
						    		gameOverCheck();
						    		
						    		dialog.dismiss();
						    	}
					    	});								    	
					    	alert.show();
					    	*/																		
						}
						
						else {
							
							if (numberOfPlayers == 2) {
		
								if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {				
									
									gameEngine1V1502();    							
								}		
								else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {		
					    			
					    			turn();   							
								}							
								else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {				
									
					    			hastePartTwo();    							
								}		
								else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {		
					    			
									hastePartTwo();   							
								}
							}
						}				  	  	  			
		  	  	  	}
		  	  	}, 2000);
  	  	    }
		});		
	}
	
	
	//=============================================================================================
	//SEPERATOR
	//=============================================================================================
	
	
	public void checkForDodgeRoll() {//CLIENT DODGING HOST
		
		if (numberOfPlayers == 2) {
			
			if (plyerAskedToDodgeCritHit[0].equals("yes")) {
				
				if (dgeRolled[0].equals("yes")) {					
					
					plyerAskedToDodgeCritHit[0] = "no";
					
					dgeRolled[0] = "null";
					
					
					clientDodged();					
				}
				else if (dgeRolled[0].equals("no")) {
					
					plyerAskedToDodgeCritHit[0] = "no";
					
					dgeRolled[0] = "null";
					
					
					criticalHit2();
				}				
			}
			
			else if (playerAskedToDodgeDamage[0].equals("yes")) {
				
				if(dgeRolled[0].equals("yes")) {					
					
					playerAskedToDodgeDamage[0] = "no";
					
					dgeRolled[0] = "null";
					
					
					clientDodged();					
				}
				else if (dgeRolled[0].equals("no")) {
					
					playerAskedToDodgeDamage[0] = "no";
					
					dgeRolled[0] = "null";
					
					
					damage2();
				}				
			}
			
			else {
				
				String str = "Waiting for " + ArrayOfPlayers.player[playerNumberAttacked] + "...";
				sendToAllClients(str);				
			}
		}
	}
	
	public void clientDodged() {//CLIENT DODGING HOST
		
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
		  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[playerNumberAttacked] + " uses dodge.");					
		
				dodgeBlowSpell[playerNumberAttacked] = dodgeBlowSpell[playerNumberAttacked] - 1;
				
				if (playerNumberAttacked == 0) {
					
					String str = "usedDodge0";
					//sendToClient0(str);
					sendToAllClients(str);
				}
				else if (playerNumberAttacked == 1) {
					
					String str = "usedDodge1";
					//sendToClient1(str);
					sendToAllClients(str);
				}
				else if (playerNumberAttacked == 2) {
					
					String str = "usedDodge2";
					//sendToClient2(str);
					sendToAllClients(str);
				}
				else if (playerNumberAttacked == 3) {
					
					String str = "usedDodge3";
					//sendToClient3(str);
					sendToAllClients(str);
				}
				else if (playerNumberAttacked == 4) {
					
					String str = "usedDodge4";
					//sendToClient4(str);
					sendToAllClients(str);
				}
				
				
				skillsCheck();
				
				String str2 = "skillsCheck";
				sendToAllClients(str2);
				
				
				// Use a blank drawable to hide the imageview animation:
		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
		    	img.setImageResource(R.drawable.twentytwentyblank);
				
				dodgeGraphic();
				
				String str3 = "dodgeGraphic";
				sendToAllClients(str3);
				
				
				final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  			
		  	  		@Override
		  	  	  	public void run() {  	  		
			  	  			
		  	  	  		//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
		  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
		  	  			
		  	  			stopGraphics();
		  	  			
		  	  			String str4 = "stopGraphics";
		  	  			sendToAllClients(str4);
		  	  			
			  	  		
		  	  			if (numberOfPlayers == 2) {
		  	  			
			  	  	  		if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {				
								
			  	  	  			gameEngine1V1502();    							
							}		
			  	  	  		else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("no")) {		
				    			
				    			turn();   							
							}										
			  	  	  		else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {				
								
				    			hastePartTwo();    							
							}		
			  	  	  		else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused.equals("yes")) {		
				    			
								hastePartTwo();   							
							}
		  	  			}
		  	  	  	}
		  	  	}, 6000);
  	  	    }
		});		
	}
	
	
	public void rollDodge() {//HOST DODGING CLIENT
		
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
				
	  			//criticalHitGraphic();
			  	  	  	
				  	  	  		
	  	  	  	final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {		  	  	  		
	  	  	  			
	  	  	  			stopGraphics();
	  	  	  			
		  	  	  		//if (dodgeBlowSpell[5] > 0) {
	  						/*
	  						centerscrolltext.setVisibility(View.VISIBLE);													
	  				  		centerscrolltext.startAnimation(animAlphaText);			  		
	  						centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", do you want to dodge?");
	  						*/
	  						
	  						AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
	  			  			
	  						alert.setCancelable(false);
	  						
	  			  	    	alert.setTitle(ArrayOfPlayers.player[5] + ", do you want to use your Dodge spell?");
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
	  			  		    		isInvokingService = "true";
	  			  		    		
	  			  		    		dialog.dismiss();
	  			  		    		
	  			  		    		dodgeBlowSpell[5] = dodgeBlowSpell[5] - 1;
  			  		    		
  			  		    			String str = "usedDodge5";  								
  			  		    			sendToAllClients(str);
	  			  		    		
	  			  		    		
		  			  		    	centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> You dodge the hit.");
	  			  		    		
									String str2 = ArrayOfPlayers.player[5] + " dodges the hit.";  								
  			  		    			sendToAllClients(str2);
									
									
	  			  		    		skillsCheck();
	  			  		    		
	  			  		    		String str3 = "skillsCheck";
	  			  		    		sendToAllClients(str3);
	  			  		    		
	  			  		    	
	  			  		    		// Use a blank drawable to hide the imageview animation:
					  		    	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
					  		    	ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
					  		    	img.setBackgroundResource(R.drawable.twentytwentyblank);
					  		    	img.setImageResource(R.drawable.twentytwentyblank);
	  			  		    		
					  		    	
	  			  		    		dodgeGraphic();
	  			  		    		
	  			  		    		String str4 = "dodgeGraphic";
	  			  		    		sendToAllClients(str4);
	  			  		    		
	  			  		    		
	  			  		    		final Handler h = new Handler();
	  					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  					  	  	  			
	  					  	  	  		@Override
	  						  	  	  	public void run() {
	  					  	  	  			
			  					  	  	  	//final TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
						  	  	  			//dodgeGraphic.setVisibility(View.INVISIBLE);
	  					  	  	  			
	  					  	  	  			stopGraphics();
	  					  	  	  			
	  					  	  	  			String str5 = "stopGraphics";
	  					  	  	  			sendToAllClients(str5);
	  					  	  	  			
	  					  	  	  			if (numberOfPlayers == 2) {
	  					  	  	  			
		  						  	  	  		if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused0.equals("no")) {	  						  	    			
		  						  	  	  			
		  						  	  	  			//gameEngineComputerFirst2();
		  						  	  	  			gameEngine1V1052();//PART 2
		  						  				}
		  						  	  	  		
		  						  	  	  		else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused0.equals("no")) {				
		  									  						
		  									  		turn();    							
		  									  	}
		  						  	  	  		else if ((ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) && ishasteused0.equals("yes")) {		
		  									  	
		  									  		//computerHastePartTwo();//GOTO CLIENT HASTE PART 2
		  						  	  	  			
		  						  	  	  			String str = "hastePartTwo";
		  						  	  	  			sendToClient0(str);
		  									  	}
		  				
		  						  	  	  		else if ((ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) && ishasteused0.equals("yes")) {				
		  									  	
		  									  		//computerHastePartTwo();//GOTO CLIENT HASTE PART 2
		  						  	  	  			
		  						  	  	  			String str = "hastePartTwo";
		  						  	  	  			sendToClient0(str);
		  									  	}
	  					  	  	  			}
	  						  	  	  	}
	  					  	  	  	}, 6000);
	  			  		    		
	  			  					//return;
	  			  		    	}
	  			  	    	});
	  			  	    	
	  			  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
	  			          	  public void onClick(DialogInterface dialog, int whichButton) {
	  			          		  
	  			          		  if (numberOfPlayers == 2) {	  			          		  
	  			          		  
		  			          		  if (rollDodgeFor.equals("criticalHit")) {
		  			          		  
			  			          		  //computerCriticalHitDamage();//GOTO CLIENT CRITICAL HIT DAMAGE
			  			          		  String str = "criticalHit2";
			  			          		  sendToClient0(str);
		  			          		  }
		  			          		  else if (rollDodgeFor.equals("damage2")) {
			  			          		  
			  			          		  //computerCriticalHitDamage();//GOTO CLIENT CRITICAL HIT DAMAGE
			  			          		  String str = "damage2";
			  			          		  sendToClient0(str);
		  			          		  }
	  			          		  }
	  			          		  
	  			          		  
	  			          		  rollDodgeFor = "null";	  			          		  
	  			          		  
	  			          		  dialog.dismiss();
	  			          	  }
	  			          	});
	  			  	    	
	  			  	    	// if back pressed: DOES THIS WORK????????????
							alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
								@Override
								public void onCancel(DialogInterface dialog) {
									
									if (numberOfPlayers == 2) {	  			          		  
			  			          		  
			  			          		  if (rollDodgeFor.equals("criticalHit")) {
			  			          		  
				  			          		  //computerCriticalHitDamage();//GOTO CLIENT CRITICAL HIT DAMAGE
				  			          		  String str = "criticalHit2";
				  			          		  sendToClient0(str);
			  			          		  }
			  			          		  else if (rollDodgeFor.equals("damage2")) {
				  			          		  
				  			          		  //computerCriticalHitDamage();//GOTO CLIENT CRITICAL HIT DAMAGE
				  			          		  String str = "damage2";
				  			          		  sendToClient0(str);
			  			          		  }
		  			          		  }
		  			          		  
		  			          		  
		  			          		  rollDodgeFor = "null";	  			          		  
		  			          		  
		  			          		  dialog.dismiss();
								}
							});
	  			  	    	
	  			  	    	
	  			  	    	alert.show();	  						
	  					//}
	  		  			/*
	  		  			else {
	  		  				
	  		  				//computerCriticalHitDamage();//GOTO CLIENT CRITICAL HIT DAMAGE
			          		String str = "criticalHit2";
			          		sendToAllClients(str);	  		  					  				
	  		  			}
	  		  			*/
		  	  	  	}
	  	  	  	}, 2000);	  	  	  		  			
  	  	    }
		});		
	}
	
	
	//=============================================================================================
	//SEPERATOR
	//=============================================================================================
	
	
	class ServerThread implements Runnable {

		public void run() {
			
			try {
				
				/*
				runOnUiThread(new Runnable() {
		  	  	    @Override
		  	  	    public void run() {
		  	  	    	
			  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
			  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
			  			
			  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
			  			centerscrolltext.setTypeface(typeFace);
			  			
			  			centerscrolltext.setVisibility(View.VISIBLE);
				  		
						centerscrolltext.append("\n" + "> Waiting for opponents...");		  					  			
		  	  	    }
	  	  		});				
				*/
				
				
				serverSocket = new ServerSocket(); // <-- create an unbound socket first
				serverSocket.setReuseAddress(true);
				serverSocket.bind(new InetSocketAddress(2000)); // <-- now bind it				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			while(true) {
		        ClientWorker w;
		        try{		        	
		        	// HELPS TO PREVENT ADDITIONAL CLIENTS LOGGING INTO SERVER THAN TOTAL INVITES SENT BY HOST OR MORE THAN 5 CLIENTS.
		        	if ((clientWorkers.size() > ArrayOfInvites.invites[0]) || (clientWorkers.size() > 5)) {
			        	  
			        }		        	
		        	else {		        		
		        		//server.accept returns a client connection
			        	w = new ClientWorker(serverSocket.accept(), idCounter);
			        	idCounter++;
			        	clientWorkers.add(w); 
			        	Thread t = new Thread(w);
			        	t.start();		        		
		        	}     
		          
		        } catch (IOException e) {
		          System.exit(-1);
		        }
		      }
		}
	}

	
	class ClientWorker implements Runnable, CharSequence {

		private Socket clientSocket;

		private BufferedReader input;
		
		public int id;

		public ClientWorker(Socket clientSocket, int idCounter) {

			this.clientSocket = clientSocket;
			this.id = idCounter;
			/*
			try {

				this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

			} catch (IOException e) {
				e.printStackTrace();
			}
			*/			
		}

		public void run() {
			
			String line;
	        BufferedReader in = null;
	        PrintWriter out = null;
	        try{	        	
	        	
	        	this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
	        	//input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
	        	//out = new PrintWriter(clientSocket.getOutputStream(), true);
	        	out = new PrintWriter(new BufferedWriter(
	  					new OutputStreamWriter(clientSocket.getOutputStream())),
	  					true);
	        } catch (IOException e) {
	          System.out.println("in or out failed");
	          System.exit(-1);
	        }
	        

			while (true) { //WAS:(!Thread.currentThread().isInterrupted())

				try {

					String read = input.readLine();					
					
					if ((read == null) || read.equalsIgnoreCase("QUIT")) {
						clientSocket.close();
	                    return;
	                }
					
					else if (read.contains("PlayerName")) {//MAY WANT MORE COMPLICATED TERM SO IT DOESN'T GET REPEATED IN CHAT
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];
						
						
						/*
						//TESTING:
						//THESE DONT WORK?:
						//Toast.makeText(Host.this, id, Toast.LENGTH_LONG).show();						
						//Toast.makeText(Host.this, part2, Toast.LENGTH_LONG).show();
						read = "id= " + id + "name= " + part2;
						//THIS IS GETTING POSTED TWICE TO HOST UI?:
						updateConversationHandler.post(new updateUIThread(read));
						*/
						
						//read = "id= " + id + "name= " + part2;
						//updateConversationHandler.post(new updateUIThread(read));
						
						//CANT ADD STRING TO ARRAYLIST IN FORM:int, Host.ClientWorker. SO USE ARRAYOFPLAYERS?
						//clientWorkers.add(id, part2);						
						
						if (id == 0) {
							ArrayOfPlayers.player[0]=part2;
							ArrayOfID.id[0] = 0;
							
							String str = "ID :" + 0;
							sendToClient0(str);
						}
						else if (id == 1) {
							ArrayOfPlayers.player[1]=part2;
							ArrayOfID.id[1] = 1;
							
							String str = "ID :" + 1;
							sendToClient1(str);
						}
						else if (id == 2) {
							ArrayOfPlayers.player[2]=part2;
							ArrayOfID.id[2] = 2;
							
							String str = "ID :" + 2;
							sendToClient2(str);
						}
						else if (id == 3) {
							ArrayOfPlayers.player[3]=part2;
							ArrayOfID.id[3] = 3;
							
							String str = "ID :" + 3;
							sendToClient3(str);
						}
						else if (id == 4) {
							ArrayOfPlayers.player[4]=part2;
							ArrayOfID.id[4] = 4;
							
							String str = "ID :" + 4;
							sendToClient4(str);
						}												
					}
					
					else if (read.contains("InitiativeRolled")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];
						
						int initiative = Integer.parseInt(part2);
						
						
						if (id == 0) {
							ArrayOfInitiative.initiative[0]=initiative;
							initiativeRolled[0] = "yes";
							
							
							String str = ArrayOfPlayers.player[0] + " rolls a " + ArrayOfInitiative.initiative[0] + ".";
							sendToAllClients(str);
							
							runOnUiThread(new Runnable() {
				      	  	    @Override
				      	  	    public void run() {
				      	  	    	
				    	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
				    	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
				    	  			
				    	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				    	  			centerscrolltext.setTypeface(typeFace);		    	  			
				    	  			
				      	  	    	
					  	    		centerscrolltext.setVisibility(View.VISIBLE);
							  		//centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + " rolls a " + ArrayOfInitiative.initiative[0] + ".");		    	  	  	  	
				      	  	    }
				      	  	});
							
							
							checkInitiativeResults();
						}
						else if (id == 1) {
							ArrayOfInitiative.initiative[1]=initiative;
							initiativeRolled[1] = "yes";

							
							String str = ArrayOfPlayers.player[1] + " rolls a " + ArrayOfInitiative.initiative[1] + ".";
							sendToAllClients(str);
							
							runOnUiThread(new Runnable() {
				      	  	    @Override
				      	  	    public void run() {
				      	  	    	
				    	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
				    	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
				    	  			
				    	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				    	  			centerscrolltext.setTypeface(typeFace);		    	  			
				    	  			
				      	  	    	
					  	    		centerscrolltext.setVisibility(View.VISIBLE);
							  		//centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + " rolls a " + ArrayOfInitiative.initiative[1] + ".");		    	  	  	  	
				      	  	    }
				      	  	});
							
							
							checkInitiativeResults();
						}
						else if (id == 2) {
							ArrayOfInitiative.initiative[2]=initiative;
							initiativeRolled[2] = "yes";

							
							String str = ArrayOfPlayers.player[2] + " rolls a " + ArrayOfInitiative.initiative[2] + ".";
							sendToAllClients(str);
							
							runOnUiThread(new Runnable() {
				      	  	    @Override
				      	  	    public void run() {
				      	  	    	
				    	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
				    	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
				    	  			
				    	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				    	  			centerscrolltext.setTypeface(typeFace);		    	  			
				    	  			
				      	  	    	
					  	    		centerscrolltext.setVisibility(View.VISIBLE);
							  		//centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + ArrayOfPlayers.player[2] + " rolls a " + ArrayOfInitiative.initiative[2] + ".");		    	  	  	  	
				      	  	    }
				      	  	});
							
							
							checkInitiativeResults();
						}
						else if (id == 3) {
							ArrayOfInitiative.initiative[3]=initiative;
							initiativeRolled[3] = "yes";

							
							String str = ArrayOfPlayers.player[3] + " rolls a " + ArrayOfInitiative.initiative[3] + ".";
							sendToAllClients(str);
							
							runOnUiThread(new Runnable() {
				      	  	    @Override
				      	  	    public void run() {
				      	  	    	
				    	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
				    	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
				    	  			
				    	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				    	  			centerscrolltext.setTypeface(typeFace);		    	  			
				    	  			
				      	  	    	
					  	    		centerscrolltext.setVisibility(View.VISIBLE);
							  		//centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + ArrayOfPlayers.player[3] + " rolls a " + ArrayOfInitiative.initiative[3] + ".");		    	  	  	  	
				      	  	    }
				      	  	});
							
							
							checkInitiativeResults();
						}
						else if (id == 4) {
							ArrayOfInitiative.initiative[4]=initiative;
							initiativeRolled[4] = "yes";

							
							String str = ArrayOfPlayers.player[4] + " rolls a " + ArrayOfInitiative.initiative[4] + ".";
							sendToAllClients(str);
							
							runOnUiThread(new Runnable() {
				      	  	    @Override
				      	  	    public void run() {
				      	  	    	
				    	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
				    	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
				    	  			
				    	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				    	  			centerscrolltext.setTypeface(typeFace);		    	  			
				    	  			
				      	  	    	
					  	    		centerscrolltext.setVisibility(View.VISIBLE);
							  		//centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + ArrayOfPlayers.player[4] + " rolls a " + ArrayOfInitiative.initiative[4] + ".");		    	  	  	  	
				      	  	    }
				      	  	});
							
							
							checkInitiativeResults();
						}						
					}
					
					else if (read.contains("Cure.hitpoints")) {//FOR CURE
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
						
						
						if (id == 0) {
							
							ArrayOfHitPoints.hitpoints[0] = Integer.parseInt(part2);
							
							String str = "0ArrayOfHitPoints.hitpoints[0] :" + ArrayOfHitPoints.hitpoints[0];
							sendToAllClients(str);
							
							
							if (numberOfPlayers == 2) {								
								
								runOnUiThread(new Runnable() {
						  	  	    @Override
						  	  	    public void run() {
						  	  	    	
						  	  	    	final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						  	  	    	
							  	  	    final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
										computerHitPointsTextView.setTypeface(typeFace);
										computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
						    			//playerHitPointsTextView.bringToFront();
						    			Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);
						    			computerHitPointsTextView.startAnimation(animPulsingAnimation);						    			
						    			
						    			
						    			final Handler h = new Handler();
						    	  		h.postDelayed(new Runnable() {		  	  	  			
						    	  	  			
							      	  		@Override
							      	  	  	public void run() {  	  		
							    	  	  			
							      	  			computerHitPointsTextView.clearAnimation();		
							    	  	  	}
						    	  		}, 2000);
						  	  	    }
								});								
							}
						}
						else if (id == 1) {
							
							ArrayOfHitPoints.hitpoints[1]=Integer.parseInt(part2);
							
							String str = "1ArrayOfHitPoints.hitpoints[1] :" + ArrayOfHitPoints.hitpoints[1];
							sendToAllClients(str);
						}
						else if (id == 2) {
							
							ArrayOfHitPoints.hitpoints[2]=Integer.parseInt(part2);
							
							String str = "2ArrayOfHitPoints.hitpoints[2] :" + ArrayOfHitPoints.hitpoints[2];
							sendToAllClients(str);
						}
						else if (id == 3) {
							
							ArrayOfHitPoints.hitpoints[3]=Integer.parseInt(part2);
							
							String str = "3ArrayOfHitPoints.hitpoints[3] :" + ArrayOfHitPoints.hitpoints[3];
							sendToAllClients(str);
						}
						else if (id == 4) {
							
							ArrayOfHitPoints.hitpoints[4]=Integer.parseInt(part2);
							
							String str = "4ArrayOfHitPoints.hitpoints[4] :" + ArrayOfHitPoints.hitpoints[4];
							sendToAllClients(str);
						}
					}
					
					else if (read.contains("5ArrayOfHitPoints.hitpoints[5]")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];
						
						ArrayOfHitPoints.hitpoints[5] = (ArrayOfHitPoints.hitpoints[5] - Integer.parseInt(part2));
						
						String str = "5ArrayOfHitPoints.hitpoints[5] :" + ArrayOfHitPoints.hitpoints[5];
						sendToAllClients(str);						
						
						
						if (numberOfPlayers == 2) {							
							
							runOnUiThread(new Runnable() {
					  	  	    @Override
					  	  	    public void run() {
					  	  	    	
						  	  	    Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
					    			
					    			
					    			final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
					    			playerHitPointsTextView.setTypeface(typeFace);
					    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
					    			
					    			Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);
					    			playerHitPointsTextView.startAnimation(animPulsingAnimation);					    			
					    			
					    			
					    			final Handler h = new Handler();
					    	  		h.postDelayed(new Runnable() {		  	  	  			
					    	  	  			
						      	  		@Override
						      	  	  	public void run() {  	  			
						    	  	  			
						      	  			playerHitPointsTextView.clearAnimation();	  	  			
						    	  	  	}
					    	  		}, 2000);
					  	  	    }
							});								
						}
					}
					
					else if (read.contains("0ArrayOfHitPoints.hitpoints[0]")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];
						
						ArrayOfHitPoints.hitpoints[0] = (ArrayOfHitPoints.hitpoints[0] - Integer.parseInt(part2));
						
						String str = "0ArrayOfHitPoints.hitpoints[0] :" + ArrayOfHitPoints.hitpoints[0];
						sendToAllClients(str);						
						
						
						if (numberOfPlayers == 2) {							
							
							runOnUiThread(new Runnable() {
					  	  	    @Override
					  	  	    public void run() {
					  	  	    	
						  	  	    Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
					    			
					    			
						  	  	    final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
						  	  	    computerHitPointsTextView.setTypeface(typeFace);								
						  	  	    computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
					    			
					    			Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);
					    			computerHitPointsTextView.startAnimation(animPulsingAnimation);					    			
					    			
					    			
					    			final Handler h = new Handler();
					    	  		h.postDelayed(new Runnable() {		  	  	  			
					    	  	  			
						      	  		@Override
						      	  	  	public void run() {  	  			
						    	  	  			
						      	  			computerHitPointsTextView.clearAnimation();	  	  			
						    	  	  	}
					    	  		}, 2000);
					  	  	    }
							});								
						}
					}
					
					else if (read.contains("endGame")) {
						
						endGame();						
					}
					
					else if (read.contains("gameOver")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];
						
						final int deadGuy=Integer.parseInt(part2);
						
						//final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
						
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
								  		//centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[deadGuy] + " has been slain!");
										
										String str = "> " + ArrayOfPlayers.player[deadGuy] + " has been slain!";
										sendToAllClients(str);
										
										
										playerDeadYet[deadGuy] = "yes";
										
										String str2 = "playerDeadYet" + deadGuy + " :" + "yes";
										sendToAllClients(str2);
										
							    		
										final Handler h2 = new Handler();
							  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {						
										
									    		gameOverCheck();			
												
												 
												// Picture of one sword destroying another.
												 
												// deathGraphic();										 
												
												
									    		/*
												AlertDialog.Builder alert = new AlertDialog.Builder(Host.this);
											    
												alert.setCancelable(false);
												
												alert.setTitle(ArrayOfPlayers.player[deadGuy] + " has been slain.");
									  	    	
									  	    	//alert.setMessage("something");
									  	    		    	
										    	
												
												// if back pressed: DOES THIS WORK????????????
												alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
													@Override
													public void onCancel(DialogInterface dialog) {
														
														//hideNavigation();
											    		
											    		playerDeadYet[deadGuy] = "yes";
											    		
											    		gameOverCheck();
											    		
											    		dialog.dismiss();
													}
												});
												
												
										    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											    	public void onClick(DialogInterface dialog, int whichButton) {
											    		
											    		//hideNavigation();
											    		
											    		playerDeadYet[deadGuy] = "yes";
											    		
											    		gameOverCheck();
											    		
											    		dialog.dismiss();
											    	}
										    	});								    	
										    	alert.show();
										    	*/
							  	  	  		}
							  	  	  	}, 2000);
					  	  	  		}
					  	  	  	}, 2000);
					  	    }
						});					
					}
					
					else if (read.contains("canHasDisarmed")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];
						
						
						if (part2.equals("no")) {
							
							runOnUiThread(new Runnable() {
					  	  	    @Override
					  	  	    public void run() {
					  	  	    	
					  	  	    	TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
		  		  	  	  			disarmedtextright.setVisibility(View.INVISIBLE);
					  	  	    }
							});
						}
						
						
						if (id == 0) {							
							
							canHasDisarmed[0]=part2;
							
							disarmedTurnStart[0] = ArrayOfTurn.turn[0];
							
							String str = "CanHasDisarmed0 :" + part2;
							sendToAllClients(str);
							
							String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
							sendToClient0(str2);
						}
						else if (id == 1) {
							
							canHasDisarmed[1]=part2;
							
							disarmedTurnStart[1] = ArrayOfTurn.turn[0];
							
							String str = "cAnHasDisarmed1" + part2;
							sendToAllClients(str);
							
							String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
							sendToClient1(str2);
						}
						else if (id == 2) {
							
							canHasDisarmed[2]=part2;
							
							disarmedTurnStart[2] = ArrayOfTurn.turn[0];
							
							String str = "caNHasDisarmed2" + part2;
							sendToAllClients(str);
							
							String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
							sendToClient2(str2);
						}
						else if (id == 3) {
							
							canHasDisarmed[3]=part2;
							
							disarmedTurnStart[3] = ArrayOfTurn.turn[0];
							
							String str = "canhasDisarmed3" + part2;
							sendToAllClients(str);
							
							String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
							sendToClient3(str2);
						}
						else if (id == 4) {
							
							canHasDisarmed[4]=part2;
							
							disarmedTurnStart[4] = ArrayOfTurn.turn[0];
							
							String str = "canHAsDisarmed4" + part2;
							sendToAllClients(str);
							
							String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
							sendToClient4(str2);
						}																	
					}
					
					else if (read.contains("playerDisarmed")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];
						
						int playerDisarmed = Integer.parseInt(part2);
						
						canHasDisarmed[playerDisarmed] = "yes";
						
						disarmedTurnStart[playerDisarmed] = ArrayOfTurn.turn[0];
						
						
						if (playerDisarmed == 0) {
							
							String str = "CanHasDisarmed0 :" + "yes";
							sendToAllClients(str);
							
							String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
							sendToClient0(str2);
						}
						else if (playerDisarmed == 1) {
							
							String str = "cAnHasDisarmed1 :" + "yes";
							sendToAllClients(str);
							
							String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
							sendToClient1(str2);
						}
						else if (playerDisarmed == 2) {
							
							String str = "caNHasDisarmed2 :" + "yes";
							sendToAllClients(str);
							
							String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
							sendToClient2(str2);
						}
						else if (playerDisarmed == 3) {
							
							String str = "canhasDisarmed3 :" + "yes";
							sendToAllClients(str);
							
							String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
							sendToClient3(str2);
						}
						else if (playerDisarmed == 4) {
							
							String str = "canHAsDisarmed4 :" + "yes";
							sendToAllClients(str);
							
							String str2 = "disarmedTurnStart :" + ArrayOfTurn.turn[0];
							sendToClient4(str2);
						}
						else if (playerDisarmed == 5) {
							
							String str = "canHaSDisarmed5 :" + "yes";
							sendToAllClients(str);
							
							//DONT NEED TO SEND disarmedTurnStart TO HOST BECAUSE IT'S DEFINED ABOVE.
						}
					}
					
					else if (read.contains("didHumanCriticalMiss")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];
						
						if (id == 0) {
							
							didHumanCriticalMiss[0]=part2;						
							
							String str = "DidHumanCriticalMiss0 :" + "yes";
							sendToAllClients(str);
						}
						else if (id == 1) {
							
							didHumanCriticalMiss[1]=part2;						
							
							String str = "dIdHumanCriticalMiss1 :" + "yes";
							sendToAllClients(str);
						}
						else if (id == 2) {
							
							didHumanCriticalMiss[2]=part2;							
							
							String str = "diDHumanCriticalMiss2 :" + "yes";
							sendToAllClients(str);
						}
						else if (id == 3) {
							
							didHumanCriticalMiss[3]=part2;							
							
							String str = "didhumanCriticalMiss3 :" + "yes";
							sendToAllClients(str);
						}
						else if (id == 4) {
							
							didHumanCriticalMiss[4]=part2;
													
							String str = "didHUmanCriticalMiss4 :" + "yes";
							sendToAllClients(str);
						}																	
					}
					
					else if (read.contains("dgeRolled")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];
						
						dgeRolled[0]=part2;
						
						
						checkForDodgeRoll();						
					}
					
					else if (read.contains("rollDge")) {
						
						rollDodge();
					}
					
					else if (read.contains("rollDodgeFor")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];
						
						rollDodgeFor=part2;												
					}
					
					else if (read.contains("hasteGraphic")) {
						
						String str = "hasteGraphic";
						sendToAllClients(str);
						
						hasteGraphic();
					}
					
					else if (read.contains("cureGraphic")) {
						
						String str = "cureGraphic";
						sendToAllClients(str);
						
						cureGraphic();
					}
					
					else if (read.contains("blessGraphic")) {
						
						String str = "blessGraphic";
						sendToAllClients(str);
						
						blessGraphic();
					}
					
					else if (read.contains("criticalHitGraphic")) {
						
						String str = "criticalHitGraphic";
						sendToAllClients(str);
						
						criticalHitGraphic();
					}
					
					else if (read.contains("mghtyBlowGraphic")) {
						
						String str = "mghtyBlowGraphic";
						sendToAllClients(str);
						
						mightyBlowGraphic();
					}
					
					else if (read.contains("criticalMissGraphic")) {
						
						String str = "criticalMissGraphic";
						sendToAllClients(str);
						
						criticalMissGraphic();
					}
					
					else if (read.contains("stopGraphics")) {
						
						String str = "stopGraphics";
						sendToAllClients(str);
						
						stopGraphics();
					}					
					
					else if (read.contains("secondroundofhasteused")) {
						
						if (id == 0) {				
							
							ishasteused0 = "no";						
						}
						else if (id == 1) {							
							
							ishasteused1 = "no";							
						}
						else if (id == 2) {							
							
							ishasteused2 = "no";							
						}
						else if (id == 3) {							
							
							ishasteused3 = "no";							
						}
						else if (id == 4) {						
							
							ishasteused4 = "no";							
						}						
					}
					
					else if (read.contains("usedHaste")) {
						
						if (id == 0) {
							
							hasteSpell[0] = hasteSpell[0] - 1;
							
							ishasteused0 = "yes";
							
							skillsCheck();
						}
						else if (id == 1) {
							
							hasteSpell[1] = hasteSpell[1] - 1;
							
							ishasteused1 = "yes";
							
							skillsCheck();
						}
						else if (id == 2) {
							
							hasteSpell[2] = hasteSpell[2] - 1;
							
							ishasteused2 = "yes";
							
							skillsCheck();
						}
						else if (id == 3) {
							
							hasteSpell[3] = hasteSpell[3] - 1;
							
							ishasteused3 = "yes";
							
							skillsCheck();
						}
						else if (id == 4) {
							
							hasteSpell[4] = hasteSpell[4] - 1;
							
							ishasteused4 = "yes";
							
							skillsCheck();
						}						
					}
					
					else if (read.contains("usedCure")) {
						
						if (id == 0) {
							
							cureSpell[0] = cureSpell[0] - 1;
							
							skillsCheck();
						}
						else if (id == 1) {
							
							cureSpell[1] = cureSpell[1] - 1;
							
							skillsCheck();
						}
						else if (id == 2) {
							
							cureSpell[2] = cureSpell[2] - 1;
							
							skillsCheck();
						}
						else if (id == 3) {
							
							cureSpell[3] = cureSpell[3] - 1;
							
							skillsCheck();
						}
						else if (id == 4) {
							
							cureSpell[4] = cureSpell[4] - 1;
							
							skillsCheck();
						}						
					}
					
					else if (read.contains("usedBless")) {
						
						if (id == 0) {
							
							blessSpell[0] = blessSpell[0] - 1;
							
							skillsCheck();
						}
						else if (id == 1) {
							
							blessSpell[1] = blessSpell[1] - 1;
							
							skillsCheck();
						}
						else if (id == 2) {
							
							blessSpell[2] = blessSpell[2] - 1;
							
							skillsCheck();
						}
						else if (id == 3) {
							
							blessSpell[3] = blessSpell[3] - 1;
							
							skillsCheck();
						}
						else if (id == 4) {
							
							blessSpell[4] = blessSpell[4] - 1;
							
							skillsCheck();
						}						
					}
					
					else if (read.contains("usedMightyBlow")) {
						
						if (id == 0) {
							
							mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
							
							skillsCheck();
						}
						else if (id == 1) {
							
							mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
							
							skillsCheck();
						}
						else if (id == 2) {
							
							mightyBlowSpell[2] = mightyBlowSpell[2] - 1;
							
							skillsCheck();
						}
						else if (id == 3) {
							
							mightyBlowSpell[3] = mightyBlowSpell[3] - 1;
							
							skillsCheck();
						}
						else if (id == 4) {
							
							mightyBlowSpell[4] = mightyBlowSpell[4] - 1;
							
							skillsCheck();
						}						
					}
					
					else if (read.contains("skillsCheck")) {
						
						String str = "skillsCheck";
						sendToAllClients(str);
					}										
					
					else if (read.contains("hasteCureDisarmWithBlessDisarmNoBlessBlessCompleted")) {
						
						hasteCureDisarmWithBlessDisarmNoBlessBlessCompleted();
					}				
					
					else if (read.contains("mightyBlowResultsCompleted")) {
						
						mightyBlowResultsCompleted();
					}
					
					else if (read.contains("criticalMissLoseWeaponCriticalMissDamageCompleted")) {
						
						criticalMissLoseWeaponCriticalMissDamageCompleted();
					}
					
					else if (read.contains("criticalMissLoseWeaponCriticalMissDamageResultsCompleted")) {
						
						criticalMissLoseWeaponCriticalMissDamageCompleted();
					}					
					
					else if (read.contains("attackDamageCriticalHitDamageCriticalHitMightyBlowDamageCompleted")) {
						
						attackDamageCriticalHitDamageCriticalHitMightyBlowDamageCompleted();
					}
					
					else if (read.contains("cmputerTwentySidedRollFromLeft1")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft1();								
					}
					else if (read.contains("computerTwentySidedRollFromLeft2")) {					
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft2();	
					}
					else if (read.contains("computerTwentySidedRollFromLeft3")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft3();						
					}
					else if (read.contains("computerTwentySidedRollFromLeft4")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft4();						
					}
					else if (read.contains("computerTwentySidedRollFromLeft5")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft5();
					}
					else if (read.contains("computerTwentySidedRollFromLeft6")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft6();						
					}
					else if (read.contains("computerTwentySidedRollFromLeft7")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft7();						
					}
					else if (read.contains("computerTwentySidedRollFromLeft8")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft8();						
					}
					else if (read.contains("computerTwentySidedRollFromLeft9")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft9();						
					}
					else if (read.contains("computerTwentySidedRollFromLeft10")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft10();						
					}
					else if (read.contains("computerTwentySidedRollFromLeft11")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft11();						
					}
					else if (read.contains("computerTwentySidedRollFromLeft12")) {					
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft12();						
					}
					else if (read.contains("computerTwentySidedRollFromLeft13")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft13();						
					}
					else if (read.contains("computerTwentySidedRollFromLeft14")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft14();						
					}
					else if (read.contains("computerTwentySidedRollFromLeft15")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft15();						
					}
					else if (read.contains("computerTwentySidedRollFromLeft16")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft16();						
					}
					else if (read.contains("computerTwentySidedRollFromLeft17")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft17();						
					}
					else if (read.contains("computerTwentySidedRollFromLeft18")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft18();						
					}
					else if (read.contains("computerTwentySidedRollFromLeft19")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft19();						
					}
					else if (read.contains("cputerTwentySidedRollFromLeft20")) {						
							
						//ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			//img.bringToFront();
						
						computerTwentySidedRollFromLeft20();
					}
					
					
					else if (read.contains("computerSixSidedRollFromLeft1")) {				
						
						computerSixSidedRollFromLeft1();
					}
					
					else if (read.contains("computerSixSidedRollFromLeft2")) {				
						
						computerSixSidedRollFromLeft2();
					}
					
					else if (read.contains("computerSixSidedRollFromLeft3")) {				
						
						computerSixSidedRollFromLeft3();
					}
					
					else if (read.contains("computerSixSidedRollFromLeft4")) {				
						
						computerSixSidedRollFromLeft4();
					}
					
					else if (read.contains("computerSixSidedRollFromLeft5")) {				
						
						computerSixSidedRollFromLeft5();
					}
					
					else if (read.contains("computerSixSidedRollFromLeft6")) {				
						
						computerSixSidedRollFromLeft6();
					}
					
					else {
						
						updateConversationHandler.post(new updateUIThread(read));
						
						
			    		sendToAllClients(read);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}				
				
				
				/*
				//NEED THIS?????????????????
				finally {
					// The connection is closed for one reason or another,
					// so have the server dealing with it
					try {
						serverSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				*/
			}
		}		
		
		
		public void print(Object obj){
			
			try {  	  	  		
  	  			
  	  			PrintWriter out = new PrintWriter(new BufferedWriter(
  	  					new OutputStreamWriter(clientSocket.getOutputStream())),
  	  					true);
  	  			out.println(obj);  	  			
  	  		} 
			
			catch(IOException e) {				
				e.printStackTrace();  	  		
  	  		}
		}

		@Override
		public char charAt(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int length() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public CharSequence subSequence(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
	public void sendToAll(Object str){
        for(ClientWorker client : clientWorkers)                    	
        	client.print(ArrayOfPlayers.player[5] + ": " + str);
    }
	
	
	public void sendToAllClients(Object read){
        for(ClientWorker client : clientWorkers)                    	
        	client.print(read);
    }
	
	public void sendToClient0(Object read){
		//for(ClientWorker client : clientWorkers)                    	
		ClientWorker client = clientWorkers.get(0);
		client.print(read);
	}
	
	public void sendToClient1(Object read){
		//for(ClientWorker client : clientWorkers)                    	
		ClientWorker client = clientWorkers.get(1);
		client.print(read);
	}
	
	public void sendToClient2(Object read){
		//for(ClientWorker client : clientWorkers)                    	
		ClientWorker client = clientWorkers.get(2);
		client.print(read);
	}
	
	public void sendToClient3(Object read){
		//for(ClientWorker client : clientWorkers)                    	
		ClientWorker client = clientWorkers.get(3);
		client.print(read);
	}
	
	public void sendToClient4(Object read){
		//for(ClientWorker client : clientWorkers)                    	
		ClientWorker client = clientWorkers.get(4);
		client.print(read);
	}
	
	
	class updateUIThread implements Runnable {
		private String msg;		
		
		public updateUIThread(String str) {
			this.msg = str;
		}
		
		@Override
		public void run() {
			//text.setText(text.getText().toString()+"Client Says: "+ msg + "\n");
			
			runOnUiThread(new Runnable() {
	  	  	    @Override
	  	  	    public void run() {
	  	  	    	
		  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		  			
		  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		  			centerscrolltext.setTypeface(typeFace);
		  			
		  			centerscrolltext.setVisibility(View.VISIBLE);
			  		
		  			centerscrolltext.append("\n" + msg);//+ "\n"
		  			// WAS: centerscrolltext.append("\n" + "> Client Says: "+ msg + "\n");		  					  			
	  	  	    }
  	  		});			
		}
	}


}
