package com.nedswebsite.ktog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.lang.ClassNotFoundException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import com.nedswebsite.ktog.Host.ClientWorker;
import com.nedswebsite.ktog.Host.updateUIThread;

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
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class implements java Socket server
 * @author pankaj
 *
 */
public class Client2 extends Activity {
    
	Handler updateConversationHandler;
	
	private Socket socket;

	private static final int SERVERPORT = 2000;// WAS 5000
	String hostIP;//WAS: private static final String SERVER_IP = "192.168.1.208";
	
	
	public int id;
	
	
	int playerNumberAttacked;
	//int i;
	
	//int turn = 0;
	
	
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
	
	
	// SOME OF THESE MAY NEED TO BE AN ARRAY-CLASS:
	public static int[] blessSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public static int[] cureSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public static int[] dodgeBlowSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public static int[] mightyBlowSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public static int[] hasteSpell = new int[] {2, 2, 2, 2, 2, 2, 2};//	{2, 2, 2, 2, 2, 2, 2};
	
	
	// FOR ORDERING PURPOSES IN TITLE:
	int firstsubscript;
	int secondsubscript;
	
	
	int finalAttackDamage;
	
	
	
	
	//IS THIS WORKING NOW (W NEW onbackpressed CODE)????????????
	// Using variable because was getting null pointer if onbackpressed before rollfromleft was completed:
	String onBackPressedOk = "no";
	
	//String isInvokingService = "true";	
	
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
	
	public static int[] disarmedTurnStart = new int[6];
	//String didHumanCriticalMiss = "no";
	//String didComputerCriticalMiss = "no";
	String[] didHumanCriticalMiss = new String[] {"no", "no", "no", "no", "no", "no"};
	
	
	//FOR onResume
	String isSixSidedReadyToBeRolled = "no";
	String isTwentySidedReadyToBeRolled = "no";
	String isInitiativeOver = "yes";
	String istitlestatsopen = "no";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		
		MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound6);
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		//stopService(svc);
		//startService(svc);		
		
		
		updateConversationHandler = new Handler();
		
		//startup();
		hostIP = ArrayOfIP.hostIP[5];
		
		// MOVE THIS DOWN A BIT??????????????
		new Thread(new ClientThread()).start();
		
		
		
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
		
		
		final ImageButton startButton = (ImageButton) findViewById(R.id.imagebuttonstart);
		startButton.setVisibility(View.INVISIBLE);		
		
		
		TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
		computerNameTextView.setTypeface(typeFace);
		computerNameTextView.setText(ArrayOfPlayers.player[0]);//WAS 1
		
		
		/*
		ArrayOfHitPoints.hitpoints[0] = 20;
		final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
		playerHitPointsTextView.setTypeface(typeFace);
		playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
		playerHitPointsTextView.setVisibility(View.INVISIBLE);
		*/
		
		ArrayOfHitPoints.hitpoints[0] = 20;
		final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
		computerHitPointsTextView.setTypeface(typeFace);
		computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));		
		
		
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
		
		//TO MAKE HIT POINTS PULSATE, USE THIS:
		
		final Animation animPulsingAnimation = AnimationUtils.loadAnimation(Client2.this, R.anim.pulsinganimation);
		/*WAS FOR 'HUMAN' HP:
		playerHitPointsTextView.startAnimation(animPulsingAnimation);
		*/
		computerHitPointsTextView.startAnimation(animPulsingAnimation);
		
		/*
		ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
		ImageView crossedswords2 = (ImageView) findViewById(R.id.imageviewavatarleft2);
		ImageView stonedead2 = (ImageView) findViewById(R.id.imageviewavatarleft3);
		
		ImageView customImage = (ImageView) findViewById(R.id.imageviewavatarleft4);
		//customImage.getLayoutParams().height = 100;
		//customImage.getLayoutParams().width = 100;
		if (getIntent().getExtras() != null) {			
				
			Intent intent2 = getIntent(); 
			String image_path= intent2.getStringExtra("imageUri"); 
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
		*/
		
		
		//NEW WAY: TRYING TO SET DRAWABLE PROGRAMMATICALLY:
		//FOR CLIENT AVATAR:
		ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
		//clientAvatar.setVisibility(View.INVISIBLE);
		
		ImageView customImage = (ImageView) findViewById(R.id.imageviewavatarright);
		//customImage.getLayoutParams().height = 100;
		//customImage.getLayoutParams().width = 100;
		if (getIntent().getExtras() != null) {			
				
			Intent intent2 = getIntent(); 
			String image_path= intent2.getStringExtra("imageUri"); 
			Uri fileUri = Uri.parse(image_path);
			customImage.setImageURI(fileUri);
		}		
		
		
		if (ArrayOfAvatars.avatar[0].equals("computer")){
			clientAvatar.setBackgroundResource(R.drawable.computer);
		}
		else if (ArrayOfAvatars.avatar[0].equals("crossedswords")){
			clientAvatar.setBackgroundResource(R.drawable.crossedswords2);
		}
		else if (ArrayOfAvatars.avatar[0].equals("stonedead")){
			clientAvatar.setBackgroundResource(R.drawable.stonedead2);
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
		
		
		
		final ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
		//titleBlankButton.setVisibility(View.INVISIBLE);
		
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
		
		
		final TextView titlelobbytext = (TextView) findViewById(R.id.textviewtitlelobbytext);
		titlelobbytext.setVisibility(View.INVISIBLE);
		
		final TextView titleinitiativetext = (TextView) findViewById(R.id.textviewtitleinitiativetext);
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
	  			centerscrolltext.append("> Welcome, " + ArrayOfPlayers.player[0] + ".");
	  			/*TO TEST SCROLL BAR:
	  			centerscrolltext.append("\n" + "> NEW LINE TEST");
	  			centerscrolltext.append("\n" + "> NEW LINE TEST");
	  			centerscrolltext.append("\n" + "> NEW LINE TEST");
	  	  	  	*/
	  			
	  			
	  			startService(svc);
	  	  	  	
	  			
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
		  				
		  	  	  		/*WAS FOR 'HUMAN' HP:
		  	  	  		playerHitPointsTextView.clearAnimation();		
		  	  	  		*/
		  	  	  		computerHitPointsTextView.clearAnimation();
		  	  	  		
		  				
		  				final Handler h4 = new Handler();
			  	  	  	h4.postDelayed(new Runnable() {
			  	  	  		
			  	  	  		@Override
			  	  	  		public void run() {				  				
			  	  	  			
			  	  	  			// Sets sixSidedBlank visible & enabled.
			  	  	  			//sixSidedRollFromLeft();  	  	  			
			  		  			
			  	  	  			
				  		  		final Handler h5 = new Handler();
					  	  	  	h5.postDelayed(new Runnable() {
					  	  	  			
					  	  	  			// Does this thread help:?
						  	  	  		@Override
						  	  	  		public void run()
						  	  	  		{  	  			
						  	  	  		try {
						  	  	  		
							  	  	  		//Toast.makeText(Client2.this, "THIS IS THE HOSTIP" + hostIP, Toast.LENGTH_LONG).show();
						  	  	  			
							  	  			String str = ArrayOfPlayers.player[0] + " has entered the game!";
							  	  			PrintWriter out = new PrintWriter(new BufferedWriter(
							  	  					new OutputStreamWriter(socket.getOutputStream())),
							  	  					true);
							  	  			out.println(str);
							  	  		
							  	  			
							  	  			String str2 = ArrayOfPlayers.player[0];
							  	  			out.println("PlayerName :" + str2);
							  	  			
							  	  			
							  	  			//gameEngine();
							  	  			
							  	  			
							  	  			//NEW:
							  	  			/*
							  	  			out.flush();
							  	  			out.close();
							  	  			*/
							  	  			/*
								  	  		centerscrolltext.setVisibility(View.VISIBLE);
						  		  	  		centerscrolltext.startAnimation(animAlphaText);
						  		  			centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + " has entered the game!");
							  	  			*/
							  	  		} catch (UnknownHostException e) {
							  	  			e.printStackTrace();
							  	  		} catch (IOException e) {
							  	  			e.printStackTrace();
							  	  		} catch (Exception e) {
							  	  			e.printStackTrace();
							  	  		}
						  	  	  			
						  	  	  			
						  	  	  			
						  	  	  			
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
					  	  	  	}, 750);
			  	  	  		}
			  	  	  	}, 2000);
		  	  	  	}
	  	  	  	}, 3000);//FINAGLING TO GET RIGHT (MAINLY 1ST TIME) - should be at least 4700?	  	  		  			
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
	              		
	              		
	              		if (numberOfPlayers == 2) {
	              		
		              		if (ArrayOfInitiative.initiative[5] > ArrayOfInitiative.initiative[0]) {
		              			
		              			firstsubscript = 0;
		              			secondsubscript = 1;
		              		}
		              		
		              		else if (ArrayOfInitiative.initiative[5] < ArrayOfInitiative.initiative[0]) {
		              			
		              			firstsubscript = 1;
		              			secondsubscript = 0;
		              		}
	              		}
	              		
	              		
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
  	  	
  	  	
		chatBlankButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// Toast.makeText(Client2.this, "CHAT TEST",
				// Toast.LENGTH_LONG).show();				
				
				
				AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this);

		    	alert.setTitle("Chat");
		    	alert.setMessage("Enter Message");

		    	// Set an EditText view to get user input:
		    	final EditText input = new EditText(Client2.this);
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
		    		
		    		try {		  	  	  		
		  	  	  		
		    			final String str = input.getText().toString();
		  	  			PrintWriter out = new PrintWriter(new BufferedWriter(
		  	  					new OutputStreamWriter(socket.getOutputStream())),
		  	  					true);
		  	  			out.println(ArrayOfPlayers.player[0] + ": " + str);
		  	  			
		  	  			/*
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
			    						centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + ": " + str);		        
			    	  	  	  		}
			    	  	  	  	}, 2000);
			      	  	    }
			      	  	});
		  	  			*/
		  	  			
		  	  			//NEW:
		  	  			/*
		  	  			out.flush();
		  	  			out.close();
		  	  			*/
		  	  		} catch (UnknownHostException e) {
		  	  			e.printStackTrace();
		  	  		} catch (IOException e) {
		  	  			e.printStackTrace();
		  	  		} catch (Exception e) {
		  	  			e.printStackTrace();
		  	  		}	        	
		    	}
		    	});

		    	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    	  public void onClick(DialogInterface dialog, int whichButton) {
		    	    // Canceled.
		    	  }
		    	});
		    	
		    	alert.show();				
				
				// END

			}
		});
		
		
		// USE android:background="@drawable/(SOME PNG)" TO SPECIFY AREA ON SCREEN ??
		sixSidedBlank.setOnTouchListener(new OnSixSidedSwipeTouchListener(Client2.this) {
			
		    /*
			public void onSwipeTop() {
		        Toast.makeText(MainActivity2.this, "top", Toast.LENGTH_SHORT).show();		        
		    }
		    */			
			
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Client2.this);
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
				
					if (ArrayOfInitiative.initiative[1] == 1) {
						
						sixSidedRollFromCenterToRight1();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[1] == 2) {
						
						sixSidedRollFromCenterToRight2();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[1] == 3) {
						
						sixSidedRollFromCenterToRight3();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[1] == 4) {
						
						sixSidedRollFromCenterToRight4();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[1] == 5) {
						
						sixSidedRollFromCenterToRight5();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[1] == 6) {
						
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
					
					if (ArrayOfInitiative.initiative[1] == 1) {
						
						sixSidedRollFromCenterToLeft1();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[1] == 2) {
						
						sixSidedRollFromCenterToLeft2();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[1] == 3) {
						
						sixSidedRollFromCenterToLeft3();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[1] == 4) {
						
						sixSidedRollFromCenterToLeft4();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[1] == 5) {
						
						sixSidedRollFromCenterToLeft5();
						
						initiativeResults();
					}
					else if (ArrayOfInitiative.initiative[1] == 6) {
						
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
		twentySidedBlank.setOnTouchListener(new OnTwentySidedSwipeTouchListener(Client2.this) {
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
	
	
	public void onStart() {
		super.onStart();
		
		//hostIP = ArrayOfIP.hostIP[0];
		//hostIP = getIntent().getStringExtra("hostIP");
		
		
		//METHODS FOR GETTING IP FROM LINK
		
		//METHOD 2:
		/*
		try {
		    
		    }		    
		    
		} catch (Throwable t) {
		    //t.printStackTrace();
			Toast.makeText(Client2.this, "HOSTIP CONVERSION DID NOT WORK", Toast.LENGTH_LONG).show();
		}
		*/	
		//Intent intent = new Intent(Intent.ACTION_VIEW);
		//startActivity(intent);
		//String host = intent.getData().getHost();   // "www.roflcopter.se"
		//List<String> ip = intent.getData().getPathSegments(); // {"hai", "2u"}
		//hostIP = ip.get(1);
		
		
		//METHOD 1:												//http://www.ktog.multiplayer.com/?verification=" + hostIP + "&username=larry
		//Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ktog.multiplayer.com/"));
		//startActivity(intent);		
				
		
		// NEED THIS??:
		//super.loadUrl(�file:///android_asset/www/index.html�);
		
		//WebView webview = new WebView(this);
        //setContentView(webview);
        //webview.loadUrl("http://www.ktog.multiplayer.com/?hostip=");
        
		/*
		try {
		    Uri uri = getIntent().getData();					  //http://www.ktog.multiplayer.com/?verification=" + hostIP + "&username=larry
		    String data = uri.getSchemeSpecificPart();//this will set data to //mysite.com/iphoneopen.php?verification=XXXXXXXXX&username=larry
		    if (data != null) {
		        String[] vars = data.split("?");
		        vars = vars[1].split("&");
		        hostIP = vars[0].split("=")[1];
		        String username = vars[1].split("=")[1];
		        //TODO: handle verification and username from here.
		    }
		} catch (Throwable t) {
			Toast.makeText(Client2.this, "HOSTIP CONVERSION DID NOT WORK", Toast.LENGTH_LONG).show();
		}
		*/
		/*
		try {
			String ip = getIntent().getData().getQueryParameter("ip");
		    if (ip != null) {
		        
		    	
		        hostIP = ip;
		        
		    }
		} catch (Throwable t) {
			Toast.makeText(Client2.this, "HOSTIP CONVERSION DID NOT WORK", Toast.LENGTH_LONG).show();
		}
		*/
		/*
		try {
		WebView webview = new WebView(this);
		Uri url = Uri.parse(webview.getUrl());
		Set<String> paramNames = url.getQueryParameterNames();
		for (String key: paramNames) {
		    String value = url.getQueryParameter("ip");
		    hostIP = value;
		    Toast.makeText(Client2.this, hostIP, Toast.LENGTH_LONG).show();
			} 
		}catch (Throwable t) {
			Toast.makeText(Client2.this, "HOSTIP CONVERSION DID NOT WORK", Toast.LENGTH_LONG).show();
		}
		*/
		/*
		try {
			Uri uri = Uri.parse("http://www.ktog.multiplayer.com/?ip=" + hostIP);
			//uri.getQueryParameter("ip");
			String value = uri.getQueryParameter("ip");
		    hostIP = value;
		    Toast.makeText(Client2.this, hostIP, Toast.LENGTH_LONG).show();
		} catch (Throwable t) {
				Toast.makeText(Client2.this, "HOSTIP CONVERSION DID NOT WORK", Toast.LENGTH_LONG).show();
		}
		*/
		/*
		try {// NEED THIS???????????? IN MANIFEST: android:pathPrefix="/"
			Intent intent = getIntent();
			Uri data = intent.getData();
			String ip = data.getQueryParameter("ip");
			hostIP = ip;
		    Toast.makeText(Client2.this, hostIP, Toast.LENGTH_LONG).show();
		} catch (Throwable t) {
				Toast.makeText(Client2.this, "HOSTIP CONVERSION DID NOT WORK", Toast.LENGTH_LONG).show();
		}
		*/
		
	}	
	
	// ONSTOP OR ONDESTROY???????
	@Override
	protected void onDestroy() {		
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);

		if (socket != null) {
			try {
				socket.close();
				
				stopService(svc);
				
				android.os.Process.killProcess(android.os.Process.myPid());
			    
			    super.onDestroy();
				
				//stopService(svc);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		super.onDestroy();
	}
	
	public void onBackPressed() {
		
		AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this);
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);

		alert.setTitle("KtOG");
		alert.setMessage("Are you sure you want to exit?");

		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				stopService(svc);
				
				dialog.dismiss();				

				Intent intent = new Intent(Client2.this, MainActivity1.class);
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
	
	
	/*
	 * 
	 * 
	 * 
	 * Player Cards*****************************************************************************************
	 * 
	 * 
	 * 
	 */
	
	
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
			  	final Animation animAlphaTextRepeat = AnimationUtils.loadAnimation(Client2.this, R.anim.anim_alpha_text_repeat);
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
			  	final Animation animAlphaTextRepeat = AnimationUtils.loadAnimation(Client2.this, R.anim.anim_alpha_text_repeat);
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
				img.setBackgroundResource(R.anim.scrollanimationleftdown);
			
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
	
	public void unfoldLeftScrollNoRight() {		
		
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// Setting up scroll frame animation.
				ImageView img = (ImageView)findViewById(R.id.scrollanimation);
				img.setBackgroundResource(R.anim.scrollanimationleftupnoright);
			
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
	
	public void hasteGraphic() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
				
				Animation a = AnimationUtils.loadAnimation(Client2.this, R.anim.textscaletobig);					  	  	  	
			  	  	
		  	  	TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  	  	
		  	  	hasteGraphic.setVisibility(View.VISIBLE);
			  	hasteGraphic.bringToFront();
		  	  	
		  	  	hasteGraphic.setTypeface(typeFace);
		  	  	hasteGraphic.setText("Haste");  	  	
		  	  	
		  	  	hasteGraphic.clearAnimation();
		  	  	hasteGraphic.startAnimation(a);
		  		
		  		MediaPlayerWrapper.play(Client2.this, R.raw.badonkshort);
		  		
		  		
				final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {
		  	  			
			  	  		Animation a = AnimationUtils.loadAnimation(Client2.this, R.anim.textscaletosmall);						  	  	  	
			  	  	  	
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
				
				Animation a = AnimationUtils.loadAnimation(Client2.this, R.anim.textscaletobig);					  	  	  	
			  	  	
		  	  	TextView cureGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  	  	
		  	  	cureGraphic.setVisibility(View.VISIBLE);
			  	cureGraphic.bringToFront();
		  	  	
		  	  	cureGraphic.setTypeface(typeFace);
		  	  	cureGraphic.setText(" Cure");  	  	
		  	  	
		  	  	cureGraphic.clearAnimation();
		  	  	cureGraphic.startAnimation(a);
		  		
		  		MediaPlayerWrapper.play(Client2.this, R.raw.badonkshort);
		  		
		  		
				final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {
		  	  			
			  	  		Animation a = AnimationUtils.loadAnimation(Client2.this, R.anim.textscaletosmall);						  	  	  	
			  	  	  	
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
				
				Animation a = AnimationUtils.loadAnimation(Client2.this, R.anim.textscaletobig);					  	  	  	
			  	  	
		  	  	TextView blessGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  		
		  	  	blessGraphic.setVisibility(View.VISIBLE);
				blessGraphic.bringToFront();
		  	  	
		  	  	blessGraphic.setTypeface(typeFace);
		  		blessGraphic.setText("Bless");  		
		  	  	
		  	  	blessGraphic.clearAnimation();
		  		blessGraphic.startAnimation(a);
		  		
		  		MediaPlayerWrapper.play(Client2.this, R.raw.badonkshort);
		  		
		  		
				final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {
		  	  			
			  	  		Animation a = AnimationUtils.loadAnimation(Client2.this, R.anim.textscaletosmall);						  	  	  	
			  	  	  	
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
				
				Animation a = AnimationUtils.loadAnimation(Client2.this, R.anim.textscaletobig);					  	  	  	
			  	  	
		  	  	TextView criticalMissGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
		  	  	
		  	  	criticalMissGraphic.setVisibility(View.VISIBLE);
			  	criticalMissGraphic.bringToFront();
		  	  	
		  	  	criticalMissGraphic.setTypeface(typeFace);
		  	  	criticalMissGraphic.setText("Critical Miss");  	  	
		  	  	
		  	  	criticalMissGraphic.clearAnimation();
		  	  	criticalMissGraphic.startAnimation(a);
		  		
		  		MediaPlayerWrapper.play(Client2.this, R.raw.badonkshort);
		  		
		  		
				final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {
		  	  			
			  	  		Animation a = AnimationUtils.loadAnimation(Client2.this, R.anim.textscaletosmall);						  	  	  	
			  	  	  	
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
				
				Animation a = AnimationUtils.loadAnimation(Client2.this, R.anim.textscaletobig);					  	  	  	
			  	  	
		  	  	TextView criticalHitGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
		  	  	
		  	  	criticalHitGraphic.setVisibility(View.VISIBLE);
			  	criticalHitGraphic.bringToFront();
		  	  	
		  	  	criticalHitGraphic.setTypeface(typeFace);
		  	  	criticalHitGraphic.setText("Critical     Hit");  	  	
		  	  	
		  	  	criticalHitGraphic.clearAnimation();
		  	  	criticalHitGraphic.startAnimation(a);
		  		
		  		MediaPlayerWrapper.play(Client2.this, R.raw.badonkshort);
		  		
		  		
				final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {
		  	  			
			  	  		Animation a = AnimationUtils.loadAnimation(Client2.this, R.anim.textscaletosmall);						  	  	  	
			  	  	  	
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
	
	public void dodgeGraphic() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
				
				
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
				
				Animation a = AnimationUtils.loadAnimation(Client2.this, R.anim.textscaletobig);					  	  	  	
			  	  	
		  	  	TextView dodgeGraphic = (TextView)findViewById(R.id.textviewspellgraphicdodge);
		  	  	
		  	  	dodgeGraphic.setVisibility(View.VISIBLE);
			  	dodgeGraphic.bringToFront();
		  	  	
		  	  	dodgeGraphic.setTypeface(typeFace);
		  	  	dodgeGraphic.setText("Dodge");  	  	
		  	  	
		  	  	dodgeGraphic.clearAnimation();
		  	  	dodgeGraphic.startAnimation(a);
		  		
		  		MediaPlayerWrapper.play(Client2.this, R.raw.badonkshort);
		  		
		  		
				final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {
		  	  			
			  	  		Animation a = AnimationUtils.loadAnimation(Client2.this, R.anim.textscaletosmall);						  	  	  	
			  	  	  	
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
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);				
				
				
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
				
				Animation a = AnimationUtils.loadAnimation(Client2.this, R.anim.textscaletobig);					  	  	  	
			  	  	
		  	  	TextView mightyBlowGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
		  	  	
		  	  	mightyBlowGraphic.setVisibility(View.VISIBLE);
			  	mightyBlowGraphic.bringToFront();
		  	  	
		  	  	mightyBlowGraphic.setTypeface(typeFace);
		  	  	mightyBlowGraphic.setText("Mighty Blow");  	  	
		  	  	
		  	  	mightyBlowGraphic.clearAnimation();
		  	  	mightyBlowGraphic.startAnimation(a);
		  		
		  		MediaPlayerWrapper.play(Client2.this, R.raw.badonkshort);
		  		
		  		
				final Handler h = new Handler();
		  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {
		  	  			
			  	  		Animation a = AnimationUtils.loadAnimation(Client2.this, R.anim.textscaletosmall);						  	  	  	
			  	  	  	
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
				    	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
			  	  	String str = "computerSixSidedRollFromLeft1";
					sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "computerSixSidedRollFromLeft2";
		  	  		sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "computerSixSidedRollFromLeft3";
		  	  		sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "computerSixSidedRollFromLeft4";
		  	  		sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "computerSixSidedRollFromLeft5";
		  	  		sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "computerSixSidedRollFromLeft6";
		  	  		sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "computerSixSidedRollFromLeft1";
		  	  		sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "computerSixSidedRollFromLeft2";
		  	  		sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "computerSixSidedRollFromLeft3";
		  	  		sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "computerSixSidedRollFromLeft4";
		  	  		sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "computerSixSidedRollFromLeft5";
		  	  		sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	if (issixsidedrolledforinitiative.equals("yes")) {
		  	  		
		  	  		String str = "computerSixSidedRollFromLeft6";
		  	  		sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
				img.setVisibility(View.VISIBLE);
				
				img.bringToFront();
		  	  	// Get the background, which has been compiled to an AnimationDrawable object.
		  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
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
				    	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
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
	
	public void twentySidedWobbleStart() {
		final ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
		final Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.wobbletwentysided);
		img.setAnimation(shake);
	}
	
	public void twentySidedWobbleStop() {
		final ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
		
		img.clearAnimation();
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "cmputerTwentySidedRollFromLeft1";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft2";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft3";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft4";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft5";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft6";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft7";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft8";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft9";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft10";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft11";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft12";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft13";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft14";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft15";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft16";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft17";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft18";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft19";
				sendToHost(str);
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
		  	  	
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "cputerTwentySidedRollFromLeft20";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "cmputerTwentySidedRollFromLeft1";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft2";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft3";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft4";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft5";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft6";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft7";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft8";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft9";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft10";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft11";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft12";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft13";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft14";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft15";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft16";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft17";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft18";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "computerTwentySidedRollFromLeft19";
				sendToHost(str);
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
				
		  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
		  	  	
		  	  	String str = "cputerTwentySidedRollFromLeft20";
				sendToHost(str);
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
	
	
	public void  determineInitiative() {		
		
		int result = (int)(Math.random()*6)+1;
        //(Math.random()*6) returns a number between 0 (inclusive) and 6 (exclusive)
        //same as: (int) Math.ceil(Math.random()*6); ?
		ArrayOfInitiative.initiative[1] = result;								
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
  	  			//centerscrolltext.bringToFront();
  	  			
  	  			//playerCardStopFadeInFadeOut();
	  			//computerCardStartFadeInFadeOut();
	  			
	  			
	  			try {	  	  	  			
	  	  			
	  	  			PrintWriter out = new PrintWriter(new BufferedWriter(
	  	  					new OutputStreamWriter(socket.getOutputStream())),
	  	  					true);
	  	  			out.println("InitiativeRolled :" + ArrayOfInitiative.initiative[1]);				  	  		
	  	  			
	  	  		} catch (UnknownHostException e) {
	  	  			e.printStackTrace();
	  	  		} catch (IOException e) {
	  	  			e.printStackTrace();
	  	  		} catch (Exception e) {
	  	  			e.printStackTrace();
	  	  		}
	  			
	  			
	  			//determineDoubles();
  	  		}
  	  	}, 4000);		 		
	}
	
	public void rollInitiative() {		
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		/*
		String str = ArrayOfPlayers.player[1];
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("PlayerName :" + str);
		*/
		
		MediaPlayer buttonSound = MediaPlayer.create(Client2.this, R.raw.swordswing);
		buttonSound.start();      	
      	
		Intent svc=new Intent(this, Badonk2SoundService.class);
  	  	stopService(svc);	  	        	  
  	  
  	  
  	  	victoryDefeatAnimationForStartTransition();
  	  	
  	  	
  	  	determineInitiative();	  	
  	  	
		
		runOnUiThread(new Runnable() {
	  	    @Override
	  	    public void run() {	  	  	  	
	  	    	
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
						  	  	  			
						  	  	  			
						  	  	  			final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
								  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
								  			
								  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
								  			centerscrolltext.setTypeface(typeFace);
							  	  	  		
						  	  	  			centerscrolltext.setVisibility(View.VISIBLE);
						  		  	  		centerscrolltext.startAnimation(animAlphaText);
						  		  			centerscrolltext.append("\n" + "> Please slide the die...");
						  	  	  			
						  	  	  			centerscrolltext.bringToFront();
						  	  	  			
						  	  	  			
						  	  	  			ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
						  	  	  			chatBlankButton.bringToFront();
						  	  	  			
							  	  	  		
							  	  	  		isSixSidedReadyToBeRolled = "yes";
							  	  	  		isInitiativeOver = "no";  	  	 			
						  		  			
						  		  			
						  		  			//computerCardStartFadeInFadeOut();		  		  			
						  		  			
						  		  			
						  		  			//issixsidedrolledforinitiative = "yes";
						  		  			isinitiativestarted = "yes";			  		  			
						  		  			onBackPressedOk = "yes";
						  		  			
						  		  			
						  		  			//preventinitiativediefromleaking = "off";						  		  			
					  	  	  		}
					  	  	  	}, 750);
			  	  	  		}
			  	  	  	}, 1000);  		  	  			
			  	  	}
			  	}, 713); // SHOULD BE AT LEAST 675?
	  	    }
		});
	}
	
	public void reRollInitiative() {	
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		
		determineInitiative();
		
		
		runOnUiThread(new Runnable() {
	  	    @Override
	  	    public void run() {
		
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
				  	  	  		public void run() {
				  	  	  			
					  	  	  		sixSidedWobbleStart();					  	  	  		
					  	  	  		
					  	  	  		
					  	  	  		isSixSidedReadyToBeRolled = "yes";
					  	  	  		isInitiativeOver = "no";
					  	  	  		
					  	  	  		
						  	  	  	final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
									//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
								
									final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
									centerscrolltext.setTypeface(typeFace);
					  	  	  		
				  	  	  			centerscrolltext.setVisibility(View.VISIBLE);
				  		  	  		centerscrolltext.startAnimation(animAlphaText);
				  		  			centerscrolltext.append("\n" + "> Please slide the die...");
				  		  			
				  		  			centerscrolltext.bringToFront();
				  		  			
				  		  			
				  		  			ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
				  		  			chatBlankButton.bringToFront();
				  		  			
				  		  			
				  		  			//computerCardStartFadeInFadeOut();		  		  			
				  		  			
				  		  			
				  		  			//issixsidedrolledforinitiative = "yes";
				  		  			isinitiativestarted = "yes";			  		  			
				  		  			onBackPressedOk = "yes";
				  		  			
				  		  			
				  		  			//preventinitiativediefromleaking = "off";						  		  			
			  	  	  		}
			  	  	  	}, 750);
		  	  		}
		  	  	}, 1000);
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
	
	
	public void gameEngine2Player() {
		
		issixsidedrolledforinitiative = "yes";
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
		
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				
				TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
				playerNameTextView.setTypeface(typeFace);		
				playerNameTextView.setText(ArrayOfPlayers.player[5]);
				
				ArrayOfHitPoints.hitpoints[5] = 20;//20		
				final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
				playerHitPointsTextView.setTypeface(typeFace);
				playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
				
				ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
				computerAvatar.setVisibility(View.VISIBLE);
				
				
				unfoldLeftScrollNoRight();				
			}
		});
	}
	
	public void turn1V150() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
		
				//hideImageView();
				
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
				
				
				computerCardStopFadeInFadeOut();
				playerCardStartFadeInFadeOut();
				
				
				Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				
				//NEED THIS??
				TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
				playerHitPointsTextView.setTypeface(typeFace);
				playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
				//Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);
				//playerHitPointsTextView.startAnimation(animPulsingAnimation);				
				
				
				//Toast.makeText(Client2.this, "Player # Attacked = " + playerNumberAttacked + ", # Of Players = " + numberOfPlayers, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public void turn1V105() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {				
				
				//hideImageView();
				
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
				computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
				//Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);				
				//computerHitPointsTextView.startAnimation(animPulsingAnimation);
				
				/*
				//TEST:
				ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);				
				img.bringToFront();
				*/
				
				//Toast.makeText(Client2.this, "Player # Attacked = " + playerNumberAttacked + ", # Of Players = " + numberOfPlayers, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public void turn1V1052() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
		
				//hideImageView();
				
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
				
				
		    	computerCardStopFadeInFadeOut();
				playerCardStartFadeInFadeOut();
				
				
				Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				
				//NEED THIS??
				TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
				playerHitPointsTextView.setTypeface(typeFace);
				playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
				//Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);
				//playerHitPointsTextView.startAnimation(animPulsingAnimation);
			}
		});
	}
	
	public void hideImageView() {
		
		// Use a blank drawable to hide the imageview animation:
		// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
		ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
		img1.setBackgroundResource(R.drawable.twentytwentyblank);
		img1.setImageResource(R.drawable.twentytwentyblank);

		// Use a blank drawable to hide the imageview animation:
		ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
		img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
		img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
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
	  			
				
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);
		  		//centerscrolltext.append("\n");
		  		centerscrolltext.append("\n" + " >>>>>>>>>>>   " + " Turn " + ArrayOfTurn.turn[0] + "   <<<<<<<<<<<");				
		  		//centerscrolltext.append("\n");
		  		
		  		if (ArrayOfTurn.turn[0] == 1) {
		  			
		  			ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);				
					img1.bringToFront();
		  		}
  	  	    }
		});
	}
	
	
	public void rollDodge() {
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
		
				AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this);
					
				alert.setCancelable(false);
				
		    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use Dodge?");
		    	/*
		    	alert.setMessage("something");
		    	*/	  	    	
		    	
		    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			    	public void onClick(DialogInterface dialog, int whichButton) {							  		    		
			    		/*
			    		try {	  	  	  			
			  	  			
			  	  			PrintWriter out = new PrintWriter(new BufferedWriter(
			  	  					new OutputStreamWriter(socket.getOutputStream())),
			  	  					true);
			  	  			out.println("dodgeRolled :yes");				  	  		
			  	  			
			  	  		} catch (UnknownHostException e) {
			  	  			e.printStackTrace();
			  	  		} catch (IOException e) {
			  	  			e.printStackTrace();
			  	  		} catch (Exception e) {
			  	  			e.printStackTrace();
			  	  		}
			  	  		*/
			    		
			    		String str = "dgeRolled :yes";
						sendToHost(str);
			    		
			    		dialog.dismiss();
			    	}
		    	});
		    	
		    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
		    		public void onClick(DialogInterface dialog, int whichButton) {
		    			/*
		    			try {	  	  	  			
			  	  			
			  	  			PrintWriter out = new PrintWriter(new BufferedWriter(
			  	  					new OutputStreamWriter(socket.getOutputStream())),
			  	  					true);
			  	  			out.println("dodgeRolled :no");				  	  		
			  	  			
			  	  		} catch (UnknownHostException e) {
			  	  			e.printStackTrace();
			  	  		} catch (IOException e) {
			  	  			e.printStackTrace();
			  	  		} catch (Exception e) {
			  	  			e.printStackTrace();
			  	  		}	    		
			    		*/
		    			
		    			String str = "dgeRolled :no";
						sendToHost(str);
		    			
			    		dialog.dismiss();
		    		}
		    	});		  	    	
		    	alert.show();
  	  	    }
		});
	}
	
	public void changeHostHitPoints() {
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
		
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				
				final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
				playerHitPointsTextView.setTypeface(typeFace);
				playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
				//playerHitPointsTextView.bringToFront();
				Animation animPulsingAnimation = AnimationUtils.loadAnimation(Client2.this, R.anim.pulsinganimation);
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
	
	public void changeClientHitPoints() {
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
		
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				
				final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
				computerHitPointsTextView.setTypeface(typeFace);
				computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
				//playerHitPointsTextView.bringToFront();
				Animation animPulsingAnimation = AnimationUtils.loadAnimation(Client2.this, R.anim.pulsinganimation);
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
							
								if (playerDeadYet[5] == "no" && playerDeadYet[0] == "yes") {
									
									titlevictorydefeat.append("Victory");
									
									istitlestatsopen = "no";
								}
								
								else if (playerDeadYet[0] == "no" && playerDeadYet[5] == "yes") {
									
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
							
								if (playerDeadYet[5] == "no" && playerDeadYet[0] == "yes") {
									
									titlevictorydefeat.append("Victory");
								}
								
								else if (playerDeadYet[0] == "no" && playerDeadYet[5] == "yes") {
									
									titlevictorydefeat.append("Defeat");
								}
							}
			  	  	  	}
		  	  	  	}, 600);
				}
							
			}
  		});
	}
	
	
	public void clientNotDisarmed() {
		
		TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
  		disarmedtextright.setVisibility(View.INVISIBLE);
	}
	
	public void hostSideNotDisarmed() {
		
		TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
  		disarmedtextleft.setVisibility(View.INVISIBLE);
	}
	
	public void clientDisarmed() {
		
		TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
		disarmedtextright.setVisibility(View.VISIBLE);
  	  	disarmedtextright.bringToFront();
	}
	
	public void hostSideDisarmed() {
		
		TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
		disarmedtextleft.setVisibility(View.VISIBLE);
  	  	disarmedtextleft.bringToFront();		
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
	
	
	//=============================================================================================
	//SEPERATOR
	//=============================================================================================
	
	
	public void runActionsOnUi() {
		
		//isInvokingService = "false";
		
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
		  		ContextThemeWrapper cw = new ContextThemeWrapper(Client2.this, R.style.DialogWindowTitle_Holo);
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
									/*
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " attacks...");
									*/
									
									String str = "> " + ArrayOfPlayers.player[0] + " attacks...";
									sendToHost(str);																
									
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {						  	  	  			
						  	  	  			
						  	  	  			// CHOOSE PLAYER 
						  	  	  			attack();
											
											dialog.dismiss();
							  	  	  	}
						  	  	  	}, 1000);										
								}
	                        	
	                        	else if (item == 1) {
									/*
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " attempts to disarm...");
									*/
									
									String str = "> " + ArrayOfPlayers.player[0] + " attempts to disarm...";
									sendToHost(str);									
									
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {						  	  	  			
						  	  	  			
						  	  	  			// CHOOSE PLAYER
						  	  	  			disarm();
											
											dialog.dismiss();
							  	  	  	}
						  	  	  	}, 1000);										
								}
	                        	
	                        	else if (item == 2) {
									/*
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts haste...");
									*/
									
									String str = "> " + ArrayOfPlayers.player[0] + " casts haste...";
									sendToHost(str);									
									
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {						  	  	  			
						  	  	  			
											haste();
											
											dialog.dismiss();
							  	  	  	}
						  	  	  	}, 1000);									
								}
	                        	
	                        	else if (item == 3) {
									/*
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts cure...");
									*/
									
									String str = "> " + ArrayOfPlayers.player[0] + " casts cure...";
									sendToHost(str);									
									
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {						  	  	  			
						  	  	  			
											cure();
											
											dialog.dismiss();
							  	  	  	}
						  	  	  	}, 1000);										
								}
	                        	
	                        	else if (item == 4) {
									/*
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts bless...");
									*/
									
									String str = "> " + ArrayOfPlayers.player[0] + " casts bless...";
									sendToHost(str);									
									
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {						  	  	  			
						  	  	  			
						  	  	  			// CHOOSE PLAYER
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
								
								//isInvokingService = "true";
	                        	
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
		
		//isInvokingService = "false";
		
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
	  			  		ContextThemeWrapper cw = new ContextThemeWrapper(Client2.this, R.style.DialogWindowTitle_Holo);
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
	  										/*
	  		                        		centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " attacks...");
											*/
											
											String str = "> " + ArrayOfPlayers.player[0] + " attacks...";
											sendToHost(str);											
											
											
											final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {								  	  	  			
								  	  	  			
													//punch();
								  	  	  			
								  	  	  			// CHOOSE PLAYER
								  	  	  			attack();
									  	  	  	}
								  	  	  	}, 1000);										
	  									}
	  		                        	
	  		                        	else if (item == 1) {	  		                        														
											
											if (hasteSpell[0] < 1) {
												
												centerscrolltext.setVisibility(View.VISIBLE);
										  		centerscrolltext.startAnimation(animAlphaText);
										  		centerscrolltext.append("\n" + "> You have already used your Haste spells.");
												
												disarmedAction();
											}												
											
											else if ((hasteSpell[0] > 0) && !(disarmedTurnStart[0] + 2 == ArrayOfTurn.turn[0])) {
												/*
												centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);
												centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts haste...");
												*/
												
												String str = "> " + ArrayOfPlayers.player[0] + " casts haste...";
												sendToHost(str);												
												
												
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
	  		                        	
	  		                        	else if (item == 2) {	  										
	  		                        		
											if (cureSpell[0] > 0) {
												/*
												centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);
												centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts cure...");
												*/
												
												String str = "> " + ArrayOfPlayers.player[0] + " casts cure...";
												sendToHost(str);												
												
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
									  	  	  			cure();
										  	  	  	}
									  	  	  	}, 1000);													
											}
											
											else if (cureSpell[0] < 1) {
												
												disarmedAction();
											}									
	  									}	  									
	  									
	  									//isInvokingService = "true";
	  		                        	
	  		                            dialog.dismiss();
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
	
	public void attack() {						
		
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
			  	  	  		}
			  	  	  	}, 750);					  	  	  		
	  	  	  		}
	  	  	  	}, 2000);	  	  	  														
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
	  					
  					//hasteGraphic();  					
  					
  					String str = "hasteGraphic";
  					sendToHost(str);  					
  					
  					
  					final Handler h1 = new Handler();
  		  	  	  	h1.postDelayed(new Runnable() {
  		  	  	  			
  		  	  	  		@Override
  			  	  	  	public void run() {
  		  	  	  			
  		  	  	  			hasteSpell[0] = hasteSpell[0] - 1;
  		  	  	  			
  		  	  	  			String str2 = "usedHaste";
  		  	  	  			sendToHost(str2);
  		  	  	  			
  		  	  	  			
  		  	  	  			//skillsCheck();
  		  	  	  			
  		  	  	  			String str3 = "skillsCheck";
  		  	  	  			sendToHost(str3);
  		  	  	  			
  		  	  	  			
  		  	  	  			//stopGraphics();
  		  	  	  			
  		  	  	  			String str4 = "stopGraphics";
  		  	  	  			sendToHost(str4);
  		  	  	  			
  		  	  	  			
  		  	  	  			if (canHasDisarmed[0].equals("no")) {	  		  	  	  				
  		  	  	  				
  		  	  	  				// CHOOSE PLAYER
  		  	  	  				
  		  	  	  				ishasteused = "yes";					  	  	  			

				  	  	  		final TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
				  	  	  		hasteGraphic.setVisibility(View.INVISIBLE);
			  	  	  			
				  	  	  		/*
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
								centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> TWO attacks...");
								*/
								String str5 = "> TWO attacks...";
								sendToHost(str5);								
																
								
								final Handler h2 = new Handler();
					  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			/*
						  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> FIRST attack...");
										*/
										String str6 = "> FIRST attack...";
										sendToHost(str6);										
																				
										
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
  		  	  	  			
  		  	  	  			else if (canHasDisarmed[0].equals("yes")) {
		  		  	  	  		
		  		  	  	  		canHasDisarmed[0] = "no";
		  		  	  	  		
		  		  	  	  		String str7 = "canHasDisarmed :" + "no";
		  		  	  	  		sendToHost(str7);
		  		  	  	  		

				  	  	  		final TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
				  	  	  		hasteGraphic.setVisibility(View.INVISIBLE);
			  	  	  			
				  	  	  		/*
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> You are no longer disarmed.");
								*/
								String str8 = "> " +  ArrayOfPlayers.player[0] + " is no longer disarmed.";
								sendToHost(str8);
								
								
								TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
						  		disarmedtextleft.setVisibility(View.INVISIBLE);
								
								final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			String str9 = "hasteCureDisarmWithBlessDisarmNoBlessBlessCompleted";
					  	  	  			sendToHost(str9);
						  	  	  	}
					  	  	  	}, 2000);						  	  	  																						
							}	  		  	  	  			
  		  	  	  		}
  		  	  	  	}, 6000);	  				
	  			}	  			
	  			
	  			else {
  	  	  			
	  				AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this);
				      
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
				}	  	  	    				 
  	  	    }
		});			
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
						
					cureSpell[0] = cureSpell[0] - 1;
					
					String str = "usedCure";
					sendToHost(str);
					
					
					//skillsCheck();
					
					String str2 = "skillsCheck";
					sendToHost(str2);
					
					
					//cureGraphic();
					
					String str3 = "cureGraphic";
					sendToHost(str3);			
		  	  	  			
					
	  	  	  		final Handler h2 = new Handler();
		  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
			  	  	  		//final TextView cureGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  	  	  			//cureGraphic.setVisibility(View.INVISIBLE);
		  	  	  			
		  	  	  			//stopGraphics();
		  	  	  			
		  	  	  			String str4 = "stopGraphics";
		  	  	  			sendToHost(str4);
		  	  	  			
		  	  	  			
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
									
							
									SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Client2.this);
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
				
				else {
					
					AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this);
				      
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
				}
  	  	    }
		});		 
	}
	
	public void disarm() {					
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);		
				
					
				if (blessSpell[0] > 0) {		
						
					AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this);
		  			
					alert.setCancelable(false);
					
		  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use your bless spell?");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  	    		public void onClick(DialogInterface dialog, int whichButton) {
		  	    			
		  	    			//hideNavigation();
		  		    		
		  		    		blessSpell[0] = blessSpell[0] - 1;
		  		    		
		  		    		String str = "usedBless";
		  		    		sendToHost(str);
		  		    		
							
		  		    		//skillsCheck();
		  		    		
		  		    		String str2 = "skillsCheck";
		  		    		sendToHost(str2);
		  		    		
		  		    		
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
	
	public void disarmWithBless() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			/*
	  			centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
		  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " uses a bless...");
	  			*/
		  		String str = "> " + ArrayOfPlayers.player[0] + " uses a bless...";
		  		sendToHost(str);
		  		
		  		
	  			//blessGraphic();
	  			
	  			String str2 = "blessGraphic";
	  			sendToHost(str2);
		  	  	    	
				
  	  	  		final Handler h2 = new Handler();
	  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {			  	  	  			
	  	  	  			
		  	  	  		ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
	  	  	  			img.bringToFront();  	  	  				  	  	  			
	  	  	  			
		  	  	  		//final TextView blessGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
	  	  	  			//blessGraphic.setVisibility(View.INVISIBLE);	  	  	  			
	  	  	  			
	  	  	  			//stopGraphics();
	  	  	  			
	  	  	  			String str3 = "stopGraphics";
	  	  	  			sendToHost(str3);
	  	  	  			
	  	  	  			
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
							
			  	  	  		}
			  	  	  	}, 750);				  	  	  		
	  	  	  		}
	  	  	  	}, 2000);	  	  	  						
  	  	    }
		});		
	}
	
	public void bless() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
  	  	    	
		
				if (blessSpell[0] > 0) {				
						
					isblessrolled = "yes";
					
					blessSpell[0] = blessSpell[0] - 1;
					
					String str = "usedBless";
					sendToHost(str);
					
					
					//skillsCheck();
					
					String str2 = "skillsCheck";
					sendToHost(str2);
					
					
					//blessGraphic();	  			 	  	  							  	  	  		
					
					String str3 = "blessGraphic";
					sendToHost(str3);
					
					  	  	  		
		  	  	  	final Handler h2 = new Handler();
		  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
			  	  	  		//final TextView blessGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  	  	  			//blessGraphic.setVisibility(View.INVISIBLE);
		  	  	  			
		  	  	  			stopGraphics();
		  	  	  			
		  	  	  			String str4 = "stopGraphics";
		  	  	  			sendToHost(str4);
		  	  	  			
		  	  	  			
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
									
				  	  	  		}
				  	  	  	}, 750);					  	  	  		
		  	  	  		}			  	  	  		
		  	  	  	}, 6000);// CHANGED FROM 3000 TO 6000 BECAUSE DIE WAS COMING IN BEFORE GRAPHIC WAS FINISHED.	  	  	  		
									
				} 
				
				else {
					
					AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this);
				      
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
				}
  	  	    }
		});				
	}
	
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
	  			
		  		
	  			//criticalHitGraphic();
	  			
	  			String str = "criticalHitGraphic";
	  			sendToHost(str);
	  	  	  	
	  	  	  			
  	  	  		final Handler h2 = new Handler();
	  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		//final TextView criticalHitGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//criticalHitGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			//stopGraphics();
	  	  	  			
	  	  	  			String str2 = "stopGraphics";
	  	  	  			sendToHost(str2);
	  	  	  			
	  	  	  			// IF DODGE BLOW:
	  	  	  			
	  					if (dodgeBlowSpell[playerNumberAttacked] > 0) {	  						
	  						
	  						if(numberOfPlayers == 2) {
	  							
	  							String str3 = "rollDodgeFor :" + "criticalHit";
	  							sendToHost(str3);
	  							
	  							String str4 = "rollDge";
	  							sendToHost(str4);	  							
	  							
	  							
	  							//playerAskedToDodgeCritHit[0] = "yes";
	  							
	  							//checkForDodgeRoll();
	  						}
	  						
	  						else {
	  							
	  							String str4 = "rollDge";
	  							//sendToHost(str4); SEND TO 'PLAYERATTACKED'????????????????	  							
	  						}
	  					}		  	  	  			
  					
	  					// IF NO DODGE BLOW:	  					
	  	  	  			else {
	  	  	  				
		  	  	  			final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			if (mightyBlowSpell[0] > 0 && ishasteused.equals("no") && isblessrolled.equals("no")) {
								
										AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this);
							  			
										alert.setCancelable(false);
										
							  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use Mighty Blow?");
							  	    	/*
							  	    	alert.setMessage("something");
							  	    	*/	  	    	
							  	    	
							  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							  		    	public void onClick(DialogInterface dialog, int whichButton) {							  		    		
							  		    		
							  		    		//hideNavigation();
							  		    		
							  		    		mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
						  		    		
							  		    		String str5 = "usedMightyBlow";
						  		    			sendToHost(str5);
							  		    		
												
							  		    		//skillsCheck();
							  		    		
							  		    		String str6 = "skillsCheck";
							  		    		sendToHost(str6);
												
							  		    		
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
	
	public void criticalHit2() {//IF CHOOSE NO DODGE BLOW
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);  	  				
  	  			
	  	  	  			
  	  			if (mightyBlowSpell[0] > 0 && ishasteused.equals("no") && isblessrolled.equals("no")) {
			
					AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this);
		  			
					alert.setCancelable(false);
					
		  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use Mighty Blow?");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  		    	public void onClick(DialogInterface dialog, int whichButton) {							  		    		
		  		    		
		  		    		//hideNavigation();
		  		    		
		  		    		mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
	  		    		
		  		    		String str = "usedMightyBlow";
	  		    			sendToHost(str);
		  		    		
							
		  		    		//skillsCheck();
		  		    		
		  		    		String str2 = "skillsCheck";
		  		    		sendToHost(str2);
							
		  		    		
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
	  			
  	  	    	
	  			//mightyBlowGraphic();
	  			
	  			String str = "mghtyBlowGraphic";
	  			sendToHost(str);
				
				
				final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		//final TextView mightyBlowGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//mightyBlowGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			//stopGraphics();
	  	  	  			
	  	  	  			String str2 = "stopGraphics";
	  	  	  			sendToHost(str2);
	  	  	  			
	  	  	  			/*
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
				  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you roll twice for critical hit damage.");
				  		*/
				  		String str3 = "> " + ArrayOfPlayers.player[0] + " rolls twice for critical hit damage...";
				  		sendToHost(str3);
	  	  	  			
				  		
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
	  	  	  			
	  	  	  			/*
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
				  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you roll twice for critical hit damage.");
				  		*/
				  		String str = "> " + ArrayOfPlayers.player[0] + " rolls twice for critical hit damage...";
				  		sendToHost(str);
	  	  	  			
				  		 
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
					  	  	  		}
					  	  	  	}, 750);					  	  	  		
			  	  	  		}
			  	  	  	}, 2000);
	  	  	  		}
	  	  	  	}, 2000);  	  	  					
	  	    }  	  	    
		});		
	}
	
	
	//=============================================================================================
	//SEPERATOR
	//=============================================================================================
	
	
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
	  	  	  			
	  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Client2.this);
	  	  	  			//SharedPreferences.Editor editor = preferences.edit();
	  	  	  			int cureResult = preferences.getInt("cureResult", 0);//0 IS THE INT TO RETURN IF PREFERENCE DOESN'T EXIST?
	  	  	  			
	  	  	  			/*
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> You roll " + cureResult + ".");				
						*/
						String str = "> " + ArrayOfPlayers.player[0] + " rolls " + cureResult + ".";
						sendToHost(str);
						
						
						ArrayOfHitPoints.hitpoints[0] = (ArrayOfHitPoints.hitpoints[0] + cureResult);
						
						String str2 = "Cure.hitpoints :" + ArrayOfHitPoints.hitpoints[0];
						sendToHost(str2);
						
						
						final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
						computerHitPointsTextView.setTypeface(typeFace);
						computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
		    			//playerHitPointsTextView.bringToFront();
		    			Animation animPulsingAnimation = AnimationUtils.loadAnimation(Client2.this, R.anim.pulsinganimation);
		    			computerHitPointsTextView.startAnimation(animPulsingAnimation);		    					    			
		    			
		    			
						final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			computerHitPointsTextView.clearAnimation();
			  	  	  			
				  	  	  		String str3 = "hasteCureDisarmWithBlessDisarmNoBlessBlessCompleted";
			  	  	  			sendToHost(str3);
			  	  	  		}
			  	  	  	}, 2000);
	  	  	  		}
	  	  	  	}, 2000);								 
  	  	    }
		});		
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
			  	  	  			
			  	  	  			/*
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ".");
								*/
								String str = "> " + ArrayOfPlayers.player[0] + " rolls " + ArrayOfAttackResult.attackResult[0] + ".";
								sendToHost(str);
			  	  	  		}
			  	  	  		
			  	  	  		else if (canHasDisarmed[playerNumberAttacked].equals("yes")) {				  	  	  		
				  	  	  		
			  	  	  			/*
					  	  	  	centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ", +2 for opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 2) + ".");
								*/
								String str2 = "> " + ArrayOfPlayers.player[0] + " rolls " + ArrayOfAttackResult.attackResult[0] + ", +2 for opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 2) + ".";
								sendToHost(str2);
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
											
											/*
											centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> Your attack hits.");
											*/
											String str3 = "> " + ArrayOfPlayers.player[0] + "'s" + " attack hits.";
											sendToHost(str3);
											
											
											damage();
											return;
										}
										
										else if (ArrayOfAttackResult.attackResult[0] < 14 && ArrayOfAttackResult.attackResult[0] > 1) {
											
											/*
											centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Your attack misses.");
											*/
											String str4 = "> " + ArrayOfPlayers.player[0] + "'s" + " attack misses.";
											sendToHost(str4);
											
											
											final Handler h3 = new Handler();
								  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {  	  		
								  	  	  		
								  	  	  			String str5 = "attackDamageCriticalHitDamageCriticalHitMightyBlowDamageCompleted";
								  	  	  			sendToHost(str5);							  	  	  			
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
											
											/*
											centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> Your attack hits.");
											*/
											String str6 = "> " + ArrayOfPlayers.player[0] + "'s" + " attack hits.";
											sendToHost(str6);
											
											
											damage();
											return;
										}
										
										else if (ArrayOfAttackResult.attackResult[0] < 12 && ArrayOfAttackResult.attackResult[0] > 1) {
											
											/*
											centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Your attack misses.");
											*/
											String str7 = "> " + ArrayOfPlayers.player[0] + "'s" + " attack misses.";
											sendToHost(str7);
											
											
											final Handler h3 = new Handler();
								  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {  	  		
							  	  	  				
									  	  	  		String str8 = "attackDamageCriticalHitDamageCriticalHitMightyBlowDamageCompleted";
								  	  	  			sendToHost(str8);
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
	  			
	  			else if (canHasDisarmed[0].equals("yes")) {
	  				
	  				final Handler h4 = new Handler();
		  	  	  	h4.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {		
			
			  	  	  		if (canHasDisarmed[playerNumberAttacked].equals("no")) {			  	  	  			
			  	  	  			
			  	  	  			/*
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ", -1 for being disarmed = " + (ArrayOfAttackResult.attackResult[0] - 1) + ".");
								*/
								String str9 = "> " + ArrayOfPlayers.player[0] + " rolls " + ArrayOfAttackResult.attackResult[0] + ", -1 for being disarmed = " + (ArrayOfAttackResult.attackResult[0] - 1) + ".";
								sendToHost(str9);
			  	  	  		}
			  	  	  		
			  	  	  		else if (canHasDisarmed[playerNumberAttacked].equals("yes")) {				  	  	  		
				  	  	  		
			  	  	  			/*
					  	  	  	centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ", +1 for being disarmed and opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 1) + ".");
								*/
								String str10 = "> " + ArrayOfPlayers.player[0] + " rolls " + ArrayOfAttackResult.attackResult[0] + ", +1 for being disarmed and opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 1) + ".";
								sendToHost(str10);
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
											
											/*
											centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> Your punch hits.");
											*/
											String str11 = "> " + ArrayOfPlayers.player[0] + "'s" + " punch hits.";
											sendToHost(str11);
											
											
											damage();
											return;
										}
										
										else if (ArrayOfAttackResult.attackResult[0] < 15 && ArrayOfAttackResult.attackResult[0] >= 1) {
											
											/*
											centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Your punch misses.");
											*/
											String str12 = "> " + ArrayOfPlayers.player[0] + "'s" + " punch misses.";
											sendToHost(str12);
											
											
											final Handler h6 = new Handler();
								  	  	  	h6.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {  	  		
							  	  	  				
									  	  	  		String str13 = "attackDamageCriticalHitDamageCriticalHitMightyBlowDamageCompleted";
								  	  	  			sendToHost(str13);
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
											
											/*
											centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);			  		
											centerscrolltext.append("\n" + "> Your punch hits.");
											*/
											String str14 = "> " + ArrayOfPlayers.player[0] + "'s" + " punch hits.";
											sendToHost(str14);
											
											
											damage();
											return;
										}
										
										else if (ArrayOfAttackResult.attackResult[0] < 13 && ArrayOfAttackResult.attackResult[0] >= 1) {
											
											/*
											centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Your punch misses.");
											*/
											String str15 = "> " + ArrayOfPlayers.player[0] + "'s" + " punch misses.";
											sendToHost(str15);
											
											
											final Handler h6 = new Handler();
								  	  	  	h6.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {  	  		
							  	  	  				
								  	  	  			String str16 = "attackDamageCriticalHitDamageCriticalHitMightyBlowDamageCompleted";
								  	  	  			sendToHost(str16);
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
						
	  					String str = "rollDodgeFor :" + "damage2";
						sendToHost(str);
	  					
	  					String str2 = "rollDge";
						sendToHost(str2);						
						
						
						//playerAskedToDodgeDamage[0] = "yes";
						
						//checkForDodgeRoll();
					}
	  				
	  				else {
	  					
	  					String str2 = "rollDge";
						//sendToHost(str2); SEND TO 'PLAYERATTACKED'???????????????? 					
	  				}
				}
	  			
	  			// IF NO DODGE BLOW:
	  			
	  			else {
	  			
	  				final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
			  	  	  		if (mightyBlowSpell[0] > 0 && ishasteused.equals("no") && isblessrolled.equals("no") && issecondroundofhasteused.equals("no")) {								
								
								AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this);
					  			
								alert.setCancelable(false);
								
					  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use Mighty Blow?");
					  	    	/*
					  	    	alert.setMessage("something");
					  	    	*/	  	    	
					  	    	
					  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					  	    		public void onClick(DialogInterface dialog, int whichButton) {
					  		    		
					  		    		//hideNavigation();
					  		    		
					  		    		mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
					  		    		
					  		    		String str3 = "usedMightyBlow";
					  		    		sendToHost(str3);
					  		    		
										
					  		    		//skillsCheck();
					  		    		
					  		    		String str4 = "skillsCheck";
					  		    		sendToHost(str4);
										
										
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
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			if (mightyBlowSpell[0] > 0 && ishasteused.equals("no") && isblessrolled.equals("no") && issecondroundofhasteused.equals("no")) {								
					
					AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this);
		  			
					alert.setCancelable(false);
					
		  	    	alert.setTitle(ArrayOfPlayers.player[0] + ", do you want to use Mighty Blow?");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  	    		public void onClick(DialogInterface dialog, int whichButton) {
		  		    		
		  		    		//hideNavigation();
		  		    		
		  		    		mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
		  		    		
		  		    		String str3 = "usedMightyBlow";
		  		    		sendToHost(str3);
		  		    		
							
		  		    		//skillsCheck();
		  		    		
		  		    		String str4 = "skillsCheck";
		  		    		sendToHost(str4);
							
							
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
				
	  			//mightyBlowGraphic();	  			
	  			
	  			String str = "mghtyBlowGraphic";
	  			sendToHost(str);	  				  			
									
					
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
	  	  	  			
	  	  	  			//stopGraphics();
	  	  	  			
	  	  	  			String str2 = "stopGraphics";
	  	  	  			sendToHost(str2);
	  	  	  			
	  	  	  			
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
								
								
								SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Client2.this);
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
	  			
	  			/*
	  			centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> Roll for damage...");
				*/
				String str = "> " + ArrayOfPlayers.player[0] + " rolls for damage...";
				sendToHost(str);
				
				
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
								
								
			  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Client2.this);
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
	  			
		  		
	  			//criticalMissGraphic();
	  			
	  			String str = "criticalMissGraphic";
	  			sendToHost(str);
	  					
	  	  	  			
  	  	  		final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {			  	  	  			

		  	  	  		//final TextView criticalMissGraphic = (TextView)findViewById(R.id.textviewspellgraphicsmall);
		  	  	  		//criticalMissGraphic.setVisibility(View.INVISIBLE);
	  	  	  			
	  	  	  			//stopGraphics();
	  	  	  			
	  	  	  			String str2 = "stopGraphics";
	  	  	  			sendToHost(str2);
	  	  	  			
	  	  	  			/*
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
	  			  		centerscrolltext.startAnimation(animAlphaText);
	  					centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you must roll to see if you hit yourself...");
	  					*/
	  					String str = "> " + ArrayOfPlayers.player[0] + " must roll to see if it hit itself...";
	  					sendToHost(str);
	  					
	  					
	  					criticalMissAttack();	  					 
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
			  	  	  		}
			  	  	  	}, 750);					  	  	  		
	  	  	  		}
	  	  	  	}, 3000);			
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
						
	  	  	  			/*
						centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
				  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0]	+ ", +2 for the Bless Spell = "	+ (ArrayOfAttackResult.attackResult[0] + 2) + ".");
						*/
				  		String str = "> " + ArrayOfPlayers.player[0] + " rolls "	+ ArrayOfAttackResult.attackResult[0]	+ ", +2 for the Bless Spell = "	+ (ArrayOfAttackResult.attackResult[0] + 2) + ".";
				  		sendToHost(str);
				  		
				  		
				  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (ArrayOfAttackResult.attackResult[0] >= 15) {
									
					  	  	  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			/*
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
									  		centerscrolltext.append("\n" + "> Your opponent has been disarmed.");
									  		*/
									  		String str2 = "> " + ArrayOfPlayers.player[playerNumberAttacked] + " is disarmed.";
									  		sendToHost(str2);											
											
									  		
									  		final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
								  	  	  			String str3 = "hasteCureDisarmWithBlessDisarmNoBlessBlessCompleted";
								  	  	  			sendToHost(str3);								  	  	  			
									  	  	  	}
								  	  	  	}, 2000);
							  	  	  	}
						  	  	  	}, 2000);   	
									
						  	  	  	String str4 = "playerDisarmed :" + playerNumberAttacked;
						  	  	  	sendToHost(str4);
						  	  	  	
									//canHasDisarmed[playerNumberAttacked] = "yes";																		
									
									//disarmedTurnStart[playerNumberAttacked] = ArrayOfTurn.turn[0];									
								}
					
				  	  	  		else if (ArrayOfAttackResult.attackResult[0] <= 14 && ArrayOfAttackResult.attackResult[0] > 0) {
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			/*
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
									  		centerscrolltext.append("\n" + "> Your attempt to disarm misses.");
									  		*/
									  		String str5 = "> " + ArrayOfPlayers.player[0] + "'s" + " attempt to disarm misses.";
									  		sendToHost(str5);
									  		
									  		
									  		final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
								  	  	  			String str6 = "hasteCureDisarmWithBlessDisarmNoBlessBlessCompleted";
								  	  	  			sendToHost(str6);
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
	  	  	  			
	  	  	  			/*
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
				  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ".");
						*/
				  		String str = "> " + ArrayOfPlayers.player[0] + " rolls "	+ ArrayOfAttackResult.attackResult[0] + ".";
				  		sendToHost(str);
						
				  		
				  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (ArrayOfAttackResult.attackResult[0] >= 17) {
									
					  	  	  		canHasDisarmed[playerNumberAttacked] = "yes";
					  	  	  		
					  	  	  		//disarmedTurnStart[playerNumberAttacked] = ArrayOfTurn.turn[0];
					  	  	  		
									
					  	  	  		String str2 = "playerDisarmed :" + playerNumberAttacked;
					  	  	  		sendToHost(str2);													  	  	  			
				  	  	  			
				  	  	  			
					  	  	  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			/*
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
									  		centerscrolltext.append("\n" + "> Your opponent has been disarmed.");
									  		*/
									  		String str3 = "> " + ArrayOfPlayers.player[playerNumberAttacked] + " is disarmed.";
									  		sendToHost(str3);
											
									  		
									  		final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {								  	  	  		
								  	  	  		
								  	  	  			String str4 = "hasteCureDisarmWithBlessDisarmNoBlessBlessCompleted";
							  	  	  				sendToHost(str4);							  	  	  			
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
						  	  	  			
						  	  	  			/*
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
									  		centerscrolltext.append("\n" + "> Your attempt to disarm misses.");
									  		*/
									  		String str5 = "> " + ArrayOfPlayers.player[0] + "'s" + " attempt to disarm misses.";
									  		sendToHost(str5);
									  		
									  		
									  		final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {
								  	  	  			
								  	  	  			String str6 = "hasteCureDisarmWithBlessDisarmNoBlessBlessCompleted";
								  	  	  			sendToHost(str6);
									  	  	  	}
								  	  	  	}, 2000);
							  	  	  	}
						  	  	  	}, 2000);								
								}
								
				  	  	  		else if (ArrayOfAttackResult.attackResult[0] <= 1) {
									
				  	  	  			/*
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> You have rolled a critical miss...");
									*/
							  		String str7 = "> " + ArrayOfPlayers.player[0] + " rolls "	+ "a critical miss...";
							  		sendToHost(str7);
							  		
							  		
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
							
		  	  	  			/*
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0]	+ ", +2 for Bless Spell = " + (ArrayOfAttackResult.attackResult[0] + 2) + ".");
					  		*/
					  		String str = "> " + ArrayOfPlayers.player[0] + " rolls "	+ ArrayOfAttackResult.attackResult[0] + ", +2 for Bless Spell = " + (ArrayOfAttackResult.attackResult[0] + 2) + ".";
					  		sendToHost(str);
						}
						
		  	  	  		else if (canHasDisarmed[playerNumberAttacked].equals("yes")) {					
							
		  	  	  			/*
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ", +4 for Bless Spell and opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 4) + ".");
					  		*/
					  		String str2 = "> " + ArrayOfPlayers.player[0] + " rolls "	+ ArrayOfAttackResult.attackResult[0] + ", +4 for Bless Spell and opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 4) + ".";
					  		sendToHost(str2);					  		
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
										
										/*
										centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);			  		
										centerscrolltext.append("\n" + "> Your attack hits.");
										*/
										String str3 = "> " + ArrayOfPlayers.player[0] + "'s"	+ " attack hits.";
										sendToHost(str3);
										
										
										// Only one spell can be cast in a round (no spell can be cast in conjunction with another).										
										
										
										damage();
										return;
									}
									
									else if (ArrayOfAttackResult.attackResult[0] < 12 && ArrayOfAttackResult.attackResult[0] > 0) {
										
										// don't critically miss when using bless.
										
										isblessrolled = "no";
										
										/*
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Your attack misses.");
										*/
										String str4 = "> " + ArrayOfPlayers.player[0] + "'s"	+ " attack misses.";
										sendToHost(str4);
										
										
										final Handler h3 = new Handler();
							  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
							  	  	  			String str5 = "hasteCureDisarmWithBlessDisarmNoBlessBlessCompleted";
							  	  	  			sendToHost(str5);
								  	  	  	}
							  	  	  	}, 2000);
										//NEED THIS?:
										return;
									}
								}
								
								else if (canHasDisarmed[playerNumberAttacked].equals("yes")) {
									
									if (ArrayOfAttackResult.attackResult[0] >= 10 && ArrayOfAttackResult.attackResult[0] <= 19  || (ArrayOfAttackResult.attackResult[0] + 2) > 20) {
										
										/*
										centerscrolltext.setVisibility(View.VISIBLE);													
								  		centerscrolltext.startAnimation(animAlphaText);			  		
										centerscrolltext.append("\n" + "> Your attack hits.");
										*/
										String str6 = "> " + ArrayOfPlayers.player[0] + "'s"	+ " attack hits.";
										sendToHost(str6);										
										
										
										// Only one spell can be cast in a round (no spell can be cast in conjunction with another).
										
										
										damage();
										//NEED THIS?:
										return;
									}
									
									else if (ArrayOfAttackResult.attackResult[0] < 10 && ArrayOfAttackResult.attackResult[0] > 0) {
										
										// don't critically miss when using bless.
										
										isblessrolled = "no";
										
										/*
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Your attack misses.");
										*/
										String str7 = "> " + ArrayOfPlayers.player[0] + "'s"	+ " attack misses.";
										sendToHost(str7);
										
										
										final Handler h3 = new Handler();
							  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
							  	  	  			
							  	  	  		@Override
								  	  	  	public void run() {
							  	  	  			
							  	  	  			String str8 = "hasteCureDisarmWithBlessDisarmNoBlessBlessCompleted";
							  	  	  			sendToHost(str8);
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
	  	  	  			
	  	  	  			/*
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
				  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ".");
				  		*/
				  		String str = "> " + ArrayOfPlayers.player[0] + " rolls " + ArrayOfAttackResult.attackResult[0] + ".";
				  		sendToHost(str);				  		
				  		
						
				  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (ArrayOfAttackResult.attackResult[0] >= 17) {
									
				  	  	  			/*
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> You hit yourself.");
							  		*/
							  		String str2 = "> " + ArrayOfPlayers.player[0] + " hits itself.";
							  		sendToHost(str2);
							  		
							  		
							  		final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			/*
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", roll for damage...");						  	  	  			
						  	  	  			*/
											String str3 = "> " + ArrayOfPlayers.player[0] + " rolls for damage...";
											sendToHost(str3);
											
											
						  	  	  			criticalMissDamage();					  	  	  			
							  	  	  	}
						  	  	  	}, 2000);																	
								}
								
								else {
									
									/*
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you did not hit yourself, now you must roll to see if you lose your weapon...");
									*/
							  		String str4 = "> " + ArrayOfPlayers.player[0] + " did not hit itself, but must roll to see if it loses it's weapon...";
							  		sendToHost(str4);
							  		
							  		
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
				  	  	  		
			  	  	  			centerscrolltext.bringToFront();		  	  	  			
								centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> Please slide the die...");
								
								ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				  	  	    	titleBlankButton.bringToFront();						
								
						
								SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Client2.this);
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
	  	  	  			
	  	  	  			/*
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
				  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ".");
						*/
				  		String str = "> " + ArrayOfPlayers.player[0] + " rolls " + ArrayOfAttackResult.attackResult[0] + ".";
				  		sendToHost(str);
				  		
				  		
						if (ArrayOfAttackResult.attackResult[0] >= 17) {
							
							canHasDisarmed[0] = "yes";							
							
							String str2 = "canHasDisarmed :" + "yes";
	  		  	  	  		sendToHost(str2);				
							
							
							didHumanCriticalMiss[0] = "yes";
							
							String str3 = "didHumanCriticalMiss :" + "yes";
							sendToHost(str3);
							
							
							//THIS IS SENT BACK FROM HOST FROM canHasDisarmed ABOVE:
							//disarmedTurnStart[0] = ArrayOfTurn.turn[0];
							
							
							TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
		  	  	  			disarmedtextleft.setVisibility(View.VISIBLE);
				  	  	  	disarmedtextleft.bringToFront();
				  	  	  	
				  	  	  	
							final Handler h2 = new Handler();
				  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			/*
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> You are disarmed.");
							  		*/
							  		String str4 = "> " + ArrayOfPlayers.player[0] + " is disarmed.";
							  		sendToHost(str4);
							  		
							  		
							  		final Handler h3 = new Handler();
						  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {	
						  	  	  			
						  	  	  			String str5 = "criticalMissLoseWeaponCriticalMissDamageCompleted";
						  	  	  			sendToHost(str5);					  	  	  			
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
				  	  	  			
				  	  	  			/*
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> You hold on to your weapon.");
							  		*/
							  		String str6 = "> " + ArrayOfPlayers.player[0] + " holds on to it's weapon.";
							  		sendToHost(str6);
							  		
							  		
							  		final Handler h5 = new Handler();
						  	  	  	h5.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			String str7 = "criticalMissLoseWeaponCriticalMissDamageCompleted";
						  	  	  			sendToHost(str7);
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
							
		  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Client2.this);
		  	  	  			//SharedPreferences.Editor editor = preferences.edit();
		  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
		  	  	  			
		  	  	  			/*
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll " + attackDamage + ".");
							*/
							String str = "> " + ArrayOfPlayers.player[0] + " rolls " + attackDamage + ".";
							sendToHost(str);							
							
							
							finalAttackDamage = attackDamage;
							
							sendHitPoints();
							
							/*
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - attackDamage;							
							
							String str2 = "ArrayOfHitPoints.hitpoints[5] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - attackDamage);
							sendToHost(str2);
							
														
							
							final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
			    			playerHitPointsTextView.setTypeface(typeFace);
			    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
			    			
			    			Animation animPulsingAnimation = AnimationUtils.loadAnimation(Client2.this, R.anim.pulsinganimation);
			    			playerHitPointsTextView.startAnimation(animPulsingAnimation);
							*/
							
							damageResultsHandler();
						}
							
		  	  	  		else if (canHasDisarmed[0].equals("yes")) {							
							
		  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Client2.this);
		  	  	  			//SharedPreferences.Editor editor = preferences.edit();
		  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
		  	  	  			
							int attackDamageDisarmed = (attackDamage - 2);
							
							if (attackDamageDisarmed < 0) {
								
				                  attackDamageDisarmed = 0;					            
							}
							
							/*
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll " + attackDamage + ", -2 damage for punch = " + attackDamageDisarmed + " damage.");
							*/
							String str = "> " + ArrayOfPlayers.player[0] + " rolls " + attackDamage + ", -2 damage for punch = " + attackDamageDisarmed + " damage.";
							sendToHost(str);
							
							
							finalAttackDamage = attackDamageDisarmed;
							
							sendHitPoints();
							
							/*
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - attackDamageDisarmed;
							
							String str2 = "ArrayOfHitPoints.hitpoints[5] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - attackDamageDisarmed);
							sendToHost(str2);
							
														
							
							final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
			    			playerHitPointsTextView.setTypeface(typeFace);
			    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
			    			
			    			Animation animPulsingAnimation = AnimationUtils.loadAnimation(Client2.this, R.anim.pulsinganimation);
			    			playerHitPointsTextView.startAnimation(animPulsingAnimation);
							*/
							
							damageResultsHandler();
						}		  	  	  			  	  	  		
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
	  	  	  			
	  	  	  			//TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
	  	  	  			//playerNumberAttackedHitPointsTextView.clearAnimation();	  	  	  			
	  	  	  			
	  	  	  			
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] <= 0) {
							
							//String str = ArrayOfPlayers.player[playerNumberAttacked] + "has been slain!";
							//sendToHost(str);
							
							String str = "gameOver :" + playerNumberAttacked;
							sendToHost(str);
							
							
							/*
							 * 
							 * Picture of one sword destroying another.
							 * 
							 * deathGraphic();
							 * 
							 */																										
						}
						
						else {							
							
							String str2 = "attackDamageCriticalHitDamageCriticalHitMightyBlowDamageCompleted";
		  	  	  			sendToHost(str2);							
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
	  	  	  			
		  	  	  		if (canHasDisarmed[0].equals("no")) {
		  	  	  			
		  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Client2.this);
		  	  	  			//SharedPreferences.Editor editor = preferences.edit();
		  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
		  	  	  			
		  	  	  			/*
			  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll " + attackDamage	+ ".");
							*/
							String str = "> " + ArrayOfPlayers.player[0] + " rolls " + attackDamage + ".";
							sendToHost(str);
							
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {				  	  	  			
				  	  	  			
				  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Client2.this);
				  	  	  			//SharedPreferences.Editor editor = preferences.edit();
				  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
				  	  	  			
				  	  	  			/*
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (attackDamage * 2) + ".");
									*/
									String str2 = "> Double damage for Mighty Blow = " + (attackDamage * 2) + ".";
									sendToHost(str2);
									
									
									finalAttackDamage = (attackDamage * 2);
									
									sendHitPoints();
									
									/*
									ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked]	- (attackDamage * 2);
									
									String str3 = "ArrayOfHitPoints.hitpoints[0] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (attackDamage * 2));
									sendToAllClients(str3);
									
									
									TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
									playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
									playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
									//playerNumberAttackedHitPointsTextView.bringToFront();
									Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);				
									playerNumberAttackedHitPointsTextView.startAnimation(animPulsingAnimation);
									*/
									
									mightyBlowResultsHandler();
					  	  	  	}
				  	  	  	}, 2000);						
		  	  	  		}
						
		  	  	  		else if (canHasDisarmed[0].equals("yes")) {							
							
							SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Client2.this);
							//SharedPreferences.Editor editor = preferences.edit();
							int attackDamage = preferences.getInt("attackDamage", 0);
							
							int attackDamageDisarmed = (attackDamage - 2);
							
							if (attackDamageDisarmed < 0) {
								
				                  attackDamageDisarmed = 0;					            
							}
							
							/*
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll " + attackDamage + ", -2 damage for punch = " + attackDamageDisarmed + " damage.");
							*/
							String str3 = "> " + ArrayOfPlayers.player[0] + " rolls " + attackDamage + ", -2 damage for punch = " + attackDamageDisarmed + " damage.";
							sendToHost(str3);
							
							
							final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Client2.this);
				  	  	  			//SharedPreferences.Editor editor = preferences.edit();
				  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
				  	  	  			
					  	  	  		int attackDamageDisarmed = (attackDamage - 2);
									
									if (attackDamageDisarmed < 0) {
										
						                  attackDamageDisarmed = 0;					            
									}
				  	  	  			
									/*
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (attackDamageDisarmed * 2) + ".");
									*/
									String str4 = "> Double damage for Mighty Blow = " + (attackDamageDisarmed * 2) + ".";
									sendToHost(str4);
									
									
									finalAttackDamage = (attackDamageDisarmed * 2);
									
									sendHitPoints();
									
									/*
									ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked]	- (attackDamageDisarmed * 2);
									
									String str6 = "ArrayOfHitPoints.hitpoints[0] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (attackDamageDisarmed * 2));
									sendToAllClients(str6);
									
									
									TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
									playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
									playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
									//playerNumberAttackedHitPointsTextView.bringToFront();
									Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);				
									playerNumberAttackedHitPointsTextView.startAnimation(animPulsingAnimation);
									*/
									
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
	  	  	  			
	  	  	  			//TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
	  	  	  			//playerNumberAttackedHitPointsTextView.clearAnimation();	  	  	  			
	  	  	  			
	  	  	  			
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] <= 0) {
							
							//String str = ArrayOfPlayers.player[playerNumberAttacked] + "has been slain!";
							//sendToAllClients(str);
							
							String str = "gameOver :" + playerNumberAttacked;
							sendToHost(str);
							
							/*
							 * 
							 * Picture of one sword destroying another.
							 * 
							 * deathGraphic();
							 * 
							 */																									
						}
						
						else {							
							
							String str2 = "mightyBlowResultsCompleted";
		  	  	  			sendToHost(str2);														
						}				  	  	  			
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
	  	  	  			
		  	  	  		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Client2.this);
						//SharedPreferences.Editor editor = preferences.edit();
						int attackDamage = preferences.getInt("attackDamage", 0);
			  			
						/*
						centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> You roll " + attackDamage + ".");
						*/
						String str = "> " + ArrayOfPlayers.player[0] + " rolls " + attackDamage + ".";
						sendToHost(str);
						
						
						finalAttackDamage = attackDamage;
						
						//sendHitPoints();
						
						if (id == 0) {
							
							String str2 = "0ArrayOfHitPoints.hitpoints[0] :" + finalAttackDamage;
							sendToHost(str2);			
						}
						else if (id == 1) {
							
							String str3 = "1ArrayOfHitPoints.hitpoints[1] :" + finalAttackDamage;
							sendToHost(str3);			
						}
						else if (id == 2) {
							
							String str4 = "2ArrayOfHitPoints.hitpoints[2] :" + finalAttackDamage;
							sendToHost(str4);			
						}
						else if (id == 3) {
							
							String str5 = "3ArrayOfHitPoints.hitpoints[3] :" + finalAttackDamage;
							sendToHost(str5);			
						}
						else if (id == 4) {
							
							String str6 = "4ArrayOfHitPoints.hitpoints[4] :" + finalAttackDamage;
							sendToHost(str6);			
						}		
						
						/*
						ArrayOfHitPoints.hitpoints[5] = ArrayOfHitPoints.hitpoints[5] - attackDamage;
						
						String str2 = "ArrayOfHitPoints.hitpoints[5] :" + ArrayOfHitPoints.hitpoints[5];
						sendToAllClients(str2);
						
						
						final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
		    			playerHitPointsTextView.setTypeface(typeFace);
		    			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
		    			//playerHitPointsTextView.bringToFront();
		    			Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);
		    			playerHitPointsTextView.startAnimation(animPulsingAnimation);		    			
		    			*/
		    			
						final Handler h = new Handler();
		  	  	  		h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {  	  		
			  	  	  			
			  	  	  			//playerHitPointsTextView.clearAnimation();			  	  	  			
			  	  	  			
			  	  	  			
								if (ArrayOfHitPoints.hitpoints[0] <= 0) {
									
									//String str2 = ArrayOfPlayers.player[0] + "has been slain!";
									//sendToHost(str2);
									
									String str = "gameOver :" + id;
									sendToHost(str);
									
									/*
									 * 
									 * Picture of one sword destroying another.
									 * 
									 * deathGraphic();
									 * 
									 */																															
								}
								
								else {
										
									String str2 = "criticalMissLoseWeaponCriticalMissDamageCompleted";
				  	  	  			sendToHost(str2);								
								}
				  	  	  	}
			  	  	  	}, 2000);
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
	  			
	  			/*
	  			centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> You roll " + ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ".");  			
	  			*/
				String str = "> " + ArrayOfPlayers.player[0] + " rolls " + ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ".";
				sendToHost(str);
				
	  			
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
				  	  	  		
			  	  	  			/*
			  	  	  			centerscrolltext.bringToFront();			  	  	  			
								centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> Make your second roll...");
								*/
								String str2 = "> " + ArrayOfPlayers.player[0] + " makes second roll...";
								sendToHost(str2);
								
								
								ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				  	  	    	titleBlankButton.bringToFront();						
								
								
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
  	  	    	
	  			/*
	  			centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> You roll " + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] + ".");
	  			*/
				String str = "> " + ArrayOfPlayers.player[0] + " rolls " + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] + ".";
				sendToHost(str);
				
	  			
	  			final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		if (canHasDisarmed[0].equals("no")) {							
							
		  	  	  			/*
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ".");
							*/
							String str2 = "> " + ArrayOfPlayers.player[0] + " rolls a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ".";
							sendToHost(str2);
							
							
							finalAttackDamage = (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]);
							
							sendHitPoints();
							
							/*
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]);
							
							String str3 = "ArrayOfHitPoints.hitpoints[0] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]));
							sendToAllClients(str3);
							
							
							TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
							playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
							playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
							//playerNumberAttackedHitPointsTextView.bringToFront();
							Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);				
							playerNumberAttackedHitPointsTextView.startAnimation(animPulsingAnimation);
							*/
							
							criticalHitDamageResultsHandler();
						}
							
		  	  	  		else if (canHasDisarmed[0].equals("yes")) {							
							
		  	  	  			/*
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ", -2 damage for punch = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) + ".");
							*/
							String str4 = "> " + ArrayOfPlayers.player[0] + " rolls a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ", -2 damage for punch = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) + ".";
							sendToHost(str4);
							
							
							finalAttackDamage = ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2);
							
							sendHitPoints();
							
							/*
							ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2);
							
							String str5 = "ArrayOfHitPoints.hitpoints[0] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2));
							sendToAllClients(str5);
							
							
							TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
							playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
							playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
							//playerNumberAttackedHitPointsTextView.bringToFront();
							Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);				
							playerNumberAttackedHitPointsTextView.startAnimation(animPulsingAnimation);
							*/
							
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
		  	  			
		  	  			//TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
		  	  			//playerNumberAttackedHitPointsTextView.clearAnimation();		  	  			
		  	  			
		  	  			
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] <= 0) {
							
							//String str = ArrayOfPlayers.player[playerNumberAttacked] + "has been slain!";
							//sendToHost(str);
							
							String str = "gameOver :" + playerNumberAttacked;
							sendToHost(str);
							
							/*
							 * 
							 * Picture of one sword destroying another.
							 * 
							 * deathGraphic();
							 * 
							 */																										
						}
						
						else {
							
							String str2 = "attackDamageCriticalHitDamageCriticalHitMightyBlowDamageCompleted";
							sendToHost(str2);						
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
	  	  	  			
	  	  	  			/*
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> You roll " + ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ".");
	  	  	  			*/
						String str = "> " + ArrayOfPlayers.player[0] + " rolls " + ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ".";
						sendToHost(str);
						
						
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
						  	  	  		
					  	  	  			/*
					  	  	  			centerscrolltext.bringToFront();					  	  	  			
										centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Make your second roll...");
										*/
										String str2 = "> " + ArrayOfPlayers.player[0] + " makes second roll...";
										sendToHost(str2);
										
										
										ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
						  	  	    	titleBlankButton.bringToFront();								
										
										
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
	  	  	  			
	  	  	  			/*
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> You roll " + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] + ".");
	  	  	  			*/
						String str = "> " + ArrayOfPlayers.player[0] + " rolls " + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] + ".";
						sendToHost(str);
						
						
		  	  	  		final Handler h1 = new Handler();
			  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (canHasDisarmed[0].equals("no")) {							
									
				  	  	  			/*
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ".");
									*/
									String str2 = "> " + ArrayOfPlayers.player[0] + " rolls a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ".";
									sendToHost(str2);
									
									
									final Handler h2 = new Handler();
						  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  			
						  	  	  			/*
							  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) * 2) + ".");
											*/
											String str3 = "> Double damage for Mighty Blow = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) * 2) + ".";
											sendToHost(str3);
											
											
											finalAttackDamage = ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) * 2);
											
											sendHitPoints();											
											
											/*
											ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) * 2);
											
											String str4 = "ArrayOfHitPoints.hitpoints[0] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) * 2));
											sendToAllClients(str4);
											
											
											TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
											playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
											playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
											//playerNumberAttackedHitPointsTextView.bringToFront();
											Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);				
											playerNumberAttackedHitPointsTextView.startAnimation(animPulsingAnimation);
											*/
											
											criticalHitMightyBlowDamageResultsHandler();
						  	  	  			
							  	  	  	}
						  	  	  	}, 2000);						
								}
									
								if (canHasDisarmed[0].equals("yes")) {								
									
									/*
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ", -2 damage for punch = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) + " damage.");
									*/
									String str5 = "> " + ArrayOfPlayers.player[0] + " rolls a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ", -2 damage for punch = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) + " damage.";
									sendToHost(str5);
									
									
									final Handler h3 = new Handler();
						  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {
						  	  	  		
						  	  	  		/*
						  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> Double damage for Mighty Blow = " + (((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) * 2) + ".");
										*/
										String str6 = "> Double damage for Mighty Blow = " + (((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) * 2) + ".";
										sendToHost(str6);
										
										
										finalAttackDamage = (((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) * 2);
										
										sendHitPoints();										
										
										/*
										ArrayOfHitPoints.hitpoints[playerNumberAttacked] = ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) * 2);
										
										String str7 = "ArrayOfHitPoints.hitpoints[0] :" + (ArrayOfHitPoints.hitpoints[playerNumberAttacked] - (((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) * 2));
										sendToAllClients(str7);
										
										
										TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
										playerNumberAttackedHitPointsTextView.setTypeface(typeFace);
										playerNumberAttackedHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
										//playerNumberAttackedHitPointsTextView.bringToFront();
										Animation animPulsingAnimation = AnimationUtils.loadAnimation(Host.this, R.anim.pulsinganimation);				
										playerNumberAttackedHitPointsTextView.startAnimation(animPulsingAnimation);
										*/
										
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
		  	  			
		  	  			//TextView playerNumberAttackedHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
		  	  			//playerNumberAttackedHitPointsTextView.clearAnimation();		  	  			
		  	  			
		  	  			
						if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] <= 0) {
							
							//String str = ArrayOfPlayers.player[playerNumberAttacked] + "has been slain!";
							//sendToAllClients(str);
							
							String str = "gameOver :" + playerNumberAttacked;
							sendToHost(str);
														
							/*
							 * 
							 * Picture of one sword destroying another.
							 * 
							 * deathGraphic();
							 * 
							 */																										
						}
						
						else {
							
							String str2 = "attackDamageCriticalHitDamageCriticalHitMightyBlowDamageCompleted";
							sendToHost(str2);							
						}				  	  	  			
		  	  	  	}
		  	  	}, 2000);
  	  	    }
		});		
	}
	
	public void hastePartTwo() {
		
		if (ArrayOfHitPoints.hitpoints[playerNumberAttacked] <= 0) {
			
			//endGame();
			
			String str = "endGame";
			sendToHost(str);
		}	
		
		else {
			
			ishasteused = "no";
			
			String str2 = "ishasteused0";
			sendToHost(str2);
			
			issecondroundofhasteused = "yes";
			
			final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
			
			runOnUiThread(new Runnable() {
	  	  	    @Override
	  	  	    public void run() {
	  	  	    	
		  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
		  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
		  			
		  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		  			centerscrolltext.setTypeface(typeFace);
		  				  			
		  			
		  			/*
		  			final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
		  			computerHitPointsTextView.setTypeface(typeFace);
		  			computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));	  			
		  			//Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);				
		  			//computerHitPointsTextView.startAnimation(animPulsingAnimation);		  			
		  			*/
		  			
		  			//NEED THIS??:
		  			final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
		  			playerHitPointsTextView.setTypeface(typeFace);
		  			playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacked]));
		  					  					  				
	  				
					
					final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
		  	  	  			//NEED THIS??:
		  	  	  			playerHitPointsTextView.clearAnimation();
		  	  	  			
				  	  	  	// Use a blank drawable to hide the imageview animation:
				  	  	  	// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				  	  	  	ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				  	  	  	img1.setBackgroundResource(R.drawable.twentytwentyblank);
				  	  	  	img1.setImageResource(R.drawable.twentytwentyblank);
			
				  	  	  	// Use a blank drawable to hide the imageview animation:
				  	  	  	ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
				  	  	  	img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
				  	  	  	img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
		  	  	  			
		  	  	  			/*
			  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> SECOND attack...");
							*/
							String str3 = "> SECOND attack...";
							sendToHost(str3);
							
							
							attack();
			  	  	  	}
		  	  	  	}, 2000);
	  				
		  	  	  	// CAN'T USE CURE OR ANOTHER HASTE WITH A HASTE:
	  				//punch(); // SAME AS ATTACK
		  	  	  	
	  				//attack();	  				  											 
	  	  	    }
			});						
		}			
	}
	
	
	public void sendHitPoints() {
		
		if (playerNumberAttacked == 0) {
			
			String str = "0ArrayOfHitPoints.hitpoints[0] :" + finalAttackDamage;
			sendToHost(str);			
		}
		else if (playerNumberAttacked == 1) {
			
			String str = "1ArrayOfHitPoints.hitpoints[1] :" + finalAttackDamage;
			sendToHost(str);			
		}
		else if (playerNumberAttacked == 2) {
			
			String str = "2ArrayOfHitPoints.hitpoints[2] :" + finalAttackDamage;
			sendToHost(str);			
		}
		else if (playerNumberAttacked == 3) {
			
			String str = "3ArrayOfHitPoints.hitpoints[3] :" + finalAttackDamage;
			sendToHost(str);			
		}
		else if (playerNumberAttacked == 4) {
			
			String str = "4ArrayOfHitPoints.hitpoints[4] :" + finalAttackDamage;
			sendToHost(str);			
		}
		else if (playerNumberAttacked == 5) {
			
			String str = "5ArrayOfHitPoints.hitpoints[5] :" + finalAttackDamage;
			sendToHost(str);		
		}
	}
	
	public void test() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {			
				
				Toast.makeText(Client2.this, "DOES NOT CONTAIN :", Toast.LENGTH_LONG).show();
			}
		});
		
	}
	
	//=============================================================================================
	//SEPERATOR
	//=============================================================================================
	
	
	public void print(Object obj){
		
		try {  	  	  		
	  			
	  			PrintWriter out = new PrintWriter(new BufferedWriter(
	  					new OutputStreamWriter(socket.getOutputStream())),
	  					true);
	  			out.println(obj);  	  			
	  		} 
		
		catch(IOException e) {				
			e.printStackTrace();  	  		
	  		}			
	}
	
	public void sendToHost(Object read){		                    	
		
		print(read);
	}
	
	
	
	class ClientThread implements Runnable {
		
		private BufferedReader input;
		
		@Override
		public void run() {

			try {
				InetAddress serverAddr = InetAddress.getByName(hostIP);// WAS: SERVER_IP

				socket = new Socket(serverAddr, SERVERPORT);
				
				
				this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				

			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			while (true) { //WAS:(!Thread.currentThread().isInterrupted())

				try {
					
					String read = input.readLine();
					
					if ((read == null) || read.equalsIgnoreCase("QUIT")) {
						socket.close();
	                    return;
	                }					
					else if (read.contains("numberOfPlayers")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						numberOfPlayers=Integer.parseInt(part2);											
					}
					else if (read.contains("ID")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];
												
						id=Integer.parseInt(part2);											
					}
					else if (read.contains("rollInitiative")) {
						
						rollInitiative();						
					}					
					else if (read.contains("rerllInitiative")) {			
						
						reRollInitiative();						
					}
					else if (read.contains("playerNumberAttacked")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						playerNumberAttacked=Integer.parseInt(part2);											
					}
					else if (read.contains("PlayerName5")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						ArrayOfPlayers.player[5]=part2;																		
					}				
					else if (read.contains("GameEngine2Player")) {
						
						gameEngine2Player();																		
					}					
					else if (read.contains("trn1V150")) {
						
						turn1V150();																		
					}					
					else if (read.contains("turn1V105")) {
						
						turn1V105();																		
					}
					else if (read.contains("Turn1V1052")) {
						
						turn1V1052();																		
					}
					else if (read.contains("issecondroundofhasteused")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];
						
						issecondroundofhasteused=part2;																
					}
					else if (read.contains("Turn")) {		
						/*A CHECK THAT DONT FULLY UNDERSTAND USED WHEN TRYING TO DEBUG 'ARRAY OUT OF BOUNDS ERROR':
						String[] parts = read.split(":");
						String part1 = "";
						String part2 = "";
						if (read.length() > 1) {
							part1 = parts[0];
							part2 = parts[1];
							
							ArrayOfTurn.turn[0]=Integer.parseInt(part2);
						}
						*/
						
						if(read.contains(":")) {
						
							String[] parts = read.split(":");
							String part1 = parts[0];  
							//String part2 = parts[1].trim();//IF THERE WAS A SPACE
							String part2 = parts[1];				
													
							ArrayOfTurn.turn[0]=Integer.parseInt(part2);
						}
						else {
							
							test();
						}
						
					}					
					else if (read.contains("displayTrn")) {
						
						displayTurn();																	
					}
					else if (read.contains("runActionsOnUi")) {
						
						runActionsOnUi();																	
					}					
					else if (read.contains("criticalHit2")) {
						
						criticalHit2();																	
					}
					else if (read.contains("damage2")) {
						
						damage2();																	
					}
					else if (read.contains("hastePartTwo")) {
						
						hastePartTwo();
					}
					else if (read.contains("5ArrayOfHitPoints.hitpoints[5]")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						ArrayOfHitPoints.hitpoints[5]=Integer.parseInt(part2);
						
						changeHostHitPoints();
					}
					else if (read.contains("0ArrayOfHitPoints.hitpoints[0]")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						ArrayOfHitPoints.hitpoints[0]=Integer.parseInt(part2);
						
						changeClientHitPoints();
					}
					else if (read.contains("rollDge")) {					
						
						rollDodge();						
					}
					else if (read.contains("usedDodge0")) {						
						
						dodgeBlowSpell[0] = dodgeBlowSpell[0] - 1;						
					}
					else if (read.contains("usedDodge1")) {						
						
						dodgeBlowSpell[1] = dodgeBlowSpell[1] - 1;						
					}
					else if (read.contains("usedDodge2")) {						
						
						dodgeBlowSpell[2] = dodgeBlowSpell[2] - 1;						
					}
					else if (read.contains("usedDodge3")) {						
						
						dodgeBlowSpell[3] = dodgeBlowSpell[3] - 1;						
					}
					else if (read.contains("usedDodge4")) {						
						
						dodgeBlowSpell[4] = dodgeBlowSpell[4] - 1;						
					}
					else if (read.contains("usedDodge5")) {						
						
						dodgeBlowSpell[5] = dodgeBlowSpell[5] - 1;						
					}
					else if (read.contains("usedBless")) {
						
						blessSpell[5] = blessSpell[5] - 1;
					}
					else if (read.contains("usedHaste")) {
						
						hasteSpell[5] = hasteSpell[5] - 1;																
					}
					else if (read.contains("usedCure")) {
						
						cureSpell[5] = cureSpell[5] - 1;
					}
					else if (read.contains("usedMightyBlow")) {
						
						mightyBlowSpell[5] = mightyBlowSpell[5] - 1;
					}
					else if (read.contains("skillsCheck")) {
						
						skillsCheck();																	
					}
					else if (read.contains("disarmedTurnStart")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						disarmedTurnStart[0]=Integer.parseInt(part2);//USES [0] IN DISARMEDACTION SO CLIENT DOESN'T USE HASTE ON 2ND RD OF BEING DISARMED.				
					}
					else if (read.contains("0didHumanCriticalMiss0")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						didHumanCriticalMiss[0]=part2;											
					}
					else if (read.contains("1didHumanCriticalMiss1")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						didHumanCriticalMiss[1]=part2;											
					}
					else if (read.contains("2didHumanCriticalMiss2")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						didHumanCriticalMiss[2]=part2;											
					}
					else if (read.contains("3didHumanCriticalMiss3")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						didHumanCriticalMiss[3]=part2;											
					}
					else if (read.contains("4didHumanCriticalMiss4")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						didHumanCriticalMiss[4]=part2;											
					}
					else if (read.contains("5didHumanCriticalMiss5")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						didHumanCriticalMiss[5]=part2;											
					}
					else if (read.contains("0canHasDisarmed0")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						canHasDisarmed[0]=part2;											
					}
					else if (read.contains("1canHasDisarmed1")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						canHasDisarmed[1]=part2;											
					}
					else if (read.contains("2canHasDisarmed2")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						canHasDisarmed[2]=part2;											
					}
					else if (read.contains("3canHasDisarmed3")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						canHasDisarmed[3]=part2;											
					}
					else if (read.contains("4canHasDisarmed4")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						canHasDisarmed[4]=part2;											
					}
					else if (read.contains("5canHasDisarmed5")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						canHasDisarmed[5]=part2;											
					}
					else if (read.contains("clientNotDisarmed")) {
						
						clientNotDisarmed();																	
					}
					else if (read.contains("hostSideNotDisarmed")) {
						
						hostSideNotDisarmed();																	
					}
					else if (read.contains("clientDisarmed")) {
						
						clientDisarmed();																	
					}
					else if (read.contains("hostSideDisarmed")) {
						
						hostSideDisarmed();																	
					}
					else if (read.contains("runActionsOnUi")) {
						
						runActionsOnUi();																	
					}
					else if (read.contains("disarmedAction")) {
						
						disarmedAction();																	
					}
					else if (read.contains("victoryDefeatAnimation")) {
						
						victoryDefeatAnimation();																	
					}
					
					else if (read.contains("cmputerTwentySidedRollFromLeft1")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft1);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft1();
					}					
					else if (read.contains("computerTwentySidedRollFromLeft2")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft2);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft2();
					}
					else if (read.contains("computerTwentySidedRollFromLeft3")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft3);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft3();
					}
					else if (read.contains("computerTwentySidedRollFromLeft4")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft4);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft4();
					}
					else if (read.contains("computerTwentySidedRollFromLeft5")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft5);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft5();
					}
					else if (read.contains("computerTwentySidedRollFromLeft6")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft6);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft6();
					}
					else if (read.contains("computerTwentySidedRollFromLeft7")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft7);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft7();
					}
					else if (read.contains("computerTwentySidedRollFromLeft8")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft8);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft8();
					}
					else if (read.contains("computerTwentySidedRollFromLeft9")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft9);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft9();
					}
					else if (read.contains("computerTwentySidedRollFromLeft10")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft10);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft10();
					}			
					else if (read.contains("computerTwentySidedRollFromLeft11")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft11);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft11();
					}
					else if (read.contains("computerTwentySidedRollFromLeft12")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft12);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft12();
					}
					else if (read.contains("computerTwentySidedRollFromLeft13")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft13);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft13();
					}
					else if (read.contains("computerTwentySidedRollFromLeft14")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft14);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft14();
					}
					else if (read.contains("computerTwentySidedRollFromLeft15")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft15);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft15();
					}
					else if (read.contains("computerTwentySidedRollFromLeft16")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft16);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft16();
					}
					else if (read.contains("computerTwentySidedRollFromLeft17")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft17);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft17();
					}
					else if (read.contains("computerTwentySidedRollFromLeft18")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft18);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft18();
					}
					else if (read.contains("computerTwentySidedRollFromLeft19")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft19);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft19();
					}
					else if (read.contains("cmputerTwentySidedRollFromLeft20")) {
						/*
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);		
								img.setBackgroundResource(R.anim.computertwentysidedrollfromleft20);
								img.bringToFront();
						  	  	// Get the background, which has been compiled to an AnimationDrawable object.
						  	  	final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
								
						  	  	MediaPlayerWrapper.play(Client2.this, R.raw.dierolling3b);
						  	  	
						  	  	// Animation is just 1 slide so user can see title.
						  	  	frameAnimation.stop();
						  	  	frameAnimation.start();
					  	    }
				  		});
				  		*/
						/*
						final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
						twentySidedBlank.setVisibility(View.VISIBLE);
						*/
						computerTwentySidedRollFromLeft20();
					}					
					
					else if (read.contains("1computerSixSidedRollFromLeft1")) {
						/*
						ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
						img1.setBackgroundResource(R.drawable.twentytwentyblank);
						img1.setImageResource(R.drawable.twentytwentyblank);
						*/
						computerSixSidedRollFromLeft1();																	
					}
					else if (read.contains("2computerSixSidedRollFromLeft2")) {
						/*
						ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
						img1.setBackgroundResource(R.drawable.twentytwentyblank);
						img1.setImageResource(R.drawable.twentytwentyblank);
						*/
						computerSixSidedRollFromLeft2();																	
					}
					else if (read.contains("3computerSixSidedRollFromLeft3")) {
						/*
						ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
						img1.setBackgroundResource(R.drawable.twentytwentyblank);
						img1.setImageResource(R.drawable.twentytwentyblank);
						*/
						computerSixSidedRollFromLeft3();																	
					}
					else if (read.contains("4computerSixSidedRollFromLeft4")) {
						/*
						ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
						img1.setBackgroundResource(R.drawable.twentytwentyblank);
						img1.setImageResource(R.drawable.twentytwentyblank);
						*/
						computerSixSidedRollFromLeft4();																	
					}
					else if (read.contains("5computerSixSidedRollFromLeft5")) {
						/*
						ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
						img1.setBackgroundResource(R.drawable.twentytwentyblank);
						img1.setImageResource(R.drawable.twentytwentyblank);
						*/
						computerSixSidedRollFromLeft5();																	
					}
					else if (read.contains("6computerSixSidedRollFromLeft6")) {
						/*
						ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
						img1.setBackgroundResource(R.drawable.twentytwentyblank);
						img1.setImageResource(R.drawable.twentytwentyblank);
						*/
						computerSixSidedRollFromLeft6();																	
					}					
					
					else if (read.contains("hasteGraphic")) {
						
						hasteGraphic();																	
					}					
					else if (read.contains("cureGraphic")) {
						
						cureGraphic();																
					}
					else if (read.contains("blessGraphic")) {
						
						blessGraphic();																
					}
					else if (read.contains("criticalMissGraphic")) {
						
						criticalMissGraphic();																
					}
					else if (read.contains("criticalHitGraphic")) {
						
						criticalHitGraphic();																	
					}
					else if (read.contains("dodgeGraphic")) {
						
						dodgeGraphic();														
					}
					else if (read.contains("mghtyBlowGraphic")) {
						
						mightyBlowGraphic();														
					}
					else if (read.contains("stopGraphics")) {
						
						stopGraphics();															
					}
					else {
						updateConversationHandler.post(new updateUIThread(read));
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