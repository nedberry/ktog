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
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Host extends Activity {
    
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
	
	
	
	
	public static int[] attackDamageOneDisarmed = new int[1];
	public static int[] attackDamageTwoDisarmed = new int[1];	
	
	
	public static int[] disarmedTurnStart = new int[6];
	String didHumanCriticalMiss = "no";
		
	
	// SOME OF THESE MAY NEED TO BE AN ARRAY-CLASS:
	public static int[] blessSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public static int[] cureSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public static int[] dodgeBlowSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public static int[] mightyBlowSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public static int[] hasteSpell = new int[] {2, 2, 2, 2, 2, 2, 2};//	{2, 2, 2, 2, 2, 2, 2};
	
	
	// FOR ORDERING PURPOSES IN TITLE:
	int firstsubscript;
	int secondsubscript;	
	
	
	//IS THIS WORKING NOW (W NEW onbackpressed CODE)????????????
	// Using variable because was getting null pointer if onbackpressed before rollfromleft was completed:
	String onBackPressedOk = "no";
	
	String isInvokingService = "true";	
	
	String isinitiativestarted = "no";	
	static String isinitiativestartedinterrupted = "no";
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
	
		
	String startGameNow ="no";	
	
	
	// SOME OF THESE MAY NEED TO BE AN ARRAY-CLASS:
	public static String[] playerDeadYet = new String[] {"yes", "yes", "yes", "yes", "yes", "yes"}; // NEED 6?????????	
	//String playerDeadYet[] = {"yes", "yes", "yes", "yes", "yes", "yes"};
	public static String[] canHasDisarmed = new String[] {"no", "no", "no", "no", "no", "no"}; // NEED 6?????????		
	
	
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
		
		//WAS FOR COMP NAME:
		TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
		computerNameTextView.setTypeface(typeFace);
		computerNameTextView.setText(ArrayOfPlayers.player[1]);
		computerNameTextView.setVisibility(View.INVISIBLE);
		
		
		ArrayOfHitPoints.hitpoints[0] = 20;//20		
		final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
		playerHitPointsTextView.setTypeface(typeFace);
		playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));		
		
		//WAS FOR COMP HP:
		ArrayOfHitPoints.hitpoints[1] = 20;//20
		final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
		computerHitPointsTextView.setTypeface(typeFace);
		computerHitPointsTextView.setVisibility(View.INVISIBLE);
		//computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[1]));		
		
		
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
		
		//FOR CLIENT/COMP AVATAR:
		ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
		clientAvatar.setVisibility(View.INVISIBLE);		
		
		
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
		
		
		
		unfoldScrolls();		
		
  	  	
  	  	
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
  	  	
  	  	
  	  	determineInitiative();
  	  	
  	  	
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
						  		  			
						  		  			/* FOR TESTING
						  		  			 * String str2 = "ONE CLIENT TEST";
						  		  			 * sendToClient1(str2);						  		  			
						  		  			 */
						  		  			
					  	  	  		}
					  	  	  	}, 750);
			  	  	  		}
			  	  	  	}, 1000);  		  	  			
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
				
				/*
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
			*/
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
				/*
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
			*/
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
				img.setBackgroundResource(R.anim.scrollanimationleftup);
			
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
	
	
	public void  determineInitiative() {
		
		int result = (int)(Math.random()*6)+1;
        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
        //same as: (int) Math.ceil(Math.random()*6); ?
		ArrayOfInitiative.initiative[5] = result;					
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
	  			centerscrolltext.append("\n" + "> You roll " + ArrayOfInitiative.initiative[5] + " for initiative.");
	  			
	  			
	  			initiativeRolled[5] = "yes";
	  			
	  			
	  			final Handler h2 = new Handler();
		  	  	h2.postDelayed(new Runnable() {

		  	  		@Override
		  	  		public void run()
		  	  		{  	  			
			  	  		String str = ArrayOfPlayers.player[5] + " rolls a " + ArrayOfInitiative.initiative[5] + ".";
						sendToAllClients(str);
		  	  			
		  	  			/*
		  	  			centerscrolltext.setVisibility(View.VISIBLE);
			  	  		centerscrolltext.startAnimation(animAlphaText);
			  			centerscrolltext.append("\n" + "> Players are rolling for initiative...");
		  	  			*/
		  	  			
		  	  			/*
		  	  			centerscrolltext.setVisibility(View.VISIBLE);
			  	  		centerscrolltext.startAnimation(animAlphaText);
			  			centerscrolltext.append("\n" + "> Initiatives" +
			  									"\n" + "  ===========");
		  	  			
		  	  			for (int i=0; i<=clientWorkers.size(); i++) {
				  	  		centerscrolltext.setVisibility(View.VISIBLE);
				  	  		centerscrolltext.startAnimation(animAlphaText);
				  			centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[i] + "= " + ArrayOfInitiative.initiative[i]);
			  	  		}
		  	  			*/
		  	  			/*
		  	  			centerscrolltext.setVisibility(View.VISIBLE);
			  	  		centerscrolltext.startAnimation(animAlphaText);
			  			centerscrolltext.append("\n" + "> Initiatives" +
			  									"\n" + "  ===========" +
			  									"\n" + "> " + ArrayOfPlayers.player[0] + "= " + ArrayOfInitiative.initiative[0] +
			  									"\n" + "> " + ArrayOfPlayers.player[1] + "= " + ArrayOfInitiative.initiative[1] +
			  									"\n" + "> " + ArrayOfPlayers.player[2] + "= " + ArrayOfInitiative.initiative[2] +
			  									"\n" + "> " + ArrayOfPlayers.player[3] + "= " + ArrayOfInitiative.initiative[3] +
			  									"\n" + "> " + ArrayOfPlayers.player[4] + "= " + ArrayOfInitiative.initiative[4]);
			  			
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
			else if (clientWorkers.size() == 4) {
				if (initiativeRolled[0].equals("yes") && initiativeRolled[1].equals("yes") && initiativeRolled[2].equals("yes") && initiativeRolled[3].equals("yes")) {

					determineDoubles();
				}
				
			}
			else if (clientWorkers.size() == 5) {
				if (initiativeRolled[0].equals("yes") && initiativeRolled[1].equals("yes") && initiativeRolled[2].equals("yes") && initiativeRolled[3].equals("yes") && initiativeRolled[4].equals("yes")) {

					determineDoubles();
				}
			}
		}
		
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
	}
	
	public void determineDoubles() {
		
		//DOES NOT LIKE TOASTS WITH HOST/CLIENT THREAD RUNNING
		//Toast.makeText(Host.this, "ALL PLAYERS FINISHED ROLLING", Toast.LENGTH_LONG).show();
		runOnUiThread(new Runnable() {
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
						}
						else if (id == 1) {
							ArrayOfPlayers.player[1]=part2;
							ArrayOfID.id[1] = 1;
						}
						else if (id == 2) {
							ArrayOfPlayers.player[2]=part2;
							ArrayOfID.id[2] = 2;
						}
						else if (id == 3) {
							ArrayOfPlayers.player[3]=part2;
							ArrayOfID.id[3] = 3;
						}
						else if (id == 4) {
							ArrayOfPlayers.player[4]=part2;
							ArrayOfID.id[4] = 4;
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
