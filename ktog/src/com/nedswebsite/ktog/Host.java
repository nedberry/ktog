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
	
	
	static int numberOfPlayers = 1; // NEED THIS???????????
	
	
	
	
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
		
		
		MediaPlayerWrapper.play(Host.this, R.raw.buttonsound6);    			
		
		
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
		playerNameTextView.setText(ArrayOfPlayers.player[0]);		
		
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
	  			centerscrolltext.append("\n" + "> Welcome, " + ArrayOfPlayers.player[0] + ".");  	  	  				  	  	  			
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
	  	  	  	}, 3000);//FINAGLING TO GET RIGHT (MAINLY 1ST TIME) - should be at least 4700?	  	  		  			
  	  		}
  	  	}, 2000);
  	  	
  	  	
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
  		  	  			
  		  	  			
  		  	  			/*
	  		  	  		for (int counter = 0; counter < clientWorkers.size(); counter++) {
	  		        	  
	  		        	  Toast.makeText(Host.this, clientWorkers.get(counter), Toast.LENGTH_LONG).show();		               		
	  		  	  		}
	  		  	  		*/	  		  	  		
        	  	  	}
        	  	}, 675); // SHOULD BE AT LEAST 675? WAS 525       	      	        				
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
		    	  			
		    	  			final Handler h = new Handler();
		    	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		    	  	  	  			
		    	  	  	  		@Override
		    		  	  	  	public void run() {
		      	  	    	
		      	  	    			centerscrolltext.setVisibility(View.VISIBLE);
		    				  		//centerscrolltext.startAnimation(animAlphaText);
		    						centerscrolltext.append("\n" + ArrayOfPlayers.player[0] + ": " + str);		        
		    	  	  	  		}
		    	  	  	  	}, 2000);
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
  	  	
  	  	
  	  	
  	  	
	}	
	
	
	//=============================================================================================
	//SEPERATOR
	//=============================================================================================
	
	
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
	
	
	public void sixSidedWobbleStart() {
		final ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
		final Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.wobblesixsided);
		img.setAnimation(shake);
	}	
	
	
	
	
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
	
	
	
	//=============================================================================================
	//SEPERATOR
	//=============================================================================================
	
	
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
		super.onDestroy();
		
		//final Intent svc=new Intent(this, Badonk2SoundService.class);
		
		if (serverSocket != null) {
			try {
				
				serverSocket.close();
				
				
				android.os.Process.killProcess(android.os.Process.myPid());
			    
			    super.onDestroy();
				
				//stopService(svc);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

		// Toast.makeText(MainActivity2.this,"onBackPressed WORKING!!!!",
		// Toast.LENGTH_SHORT).show();
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
		        	// HELPS TO PREVENT ADDITIONAL CLIENTS LOGGING INTO SERVER THAN TOTAL INVITES SENT BY HOST.
		        	if (clientWorkers.size() > ArrayOfInvites.invites[0]) {
			        	  
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
        	client.print(ArrayOfPlayers.player[0] + ": " + str);
    }
	
	
	public void sendToAllClients(Object read){
        for(ClientWorker client : clientWorkers)                    	
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
