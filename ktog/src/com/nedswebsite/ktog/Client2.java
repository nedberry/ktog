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

import com.nedswebsite.ktog.Host.updateUIThread;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
	
	public static int[] disarmedTurnStart = new int[6];
	String didHumanCriticalMiss = "no";
	String didComputerCriticalMiss = "no";
	
	
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
	String istitlestatsopen = "no";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		//final Intent svc=new Intent(this, Badonk2SoundService.class);
		//startService(svc);
		
		
		updateConversationHandler = new Handler();
		
		//startup();
		hostIP = ArrayOfIP.hostIP[0];
		
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
		
		
		MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound6);    	
    			
		
		
		// Crashes if this is put up top.
		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
		
		
		TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
		playerNameTextView.setTypeface(typeFace);		
		playerNameTextView.setText(ArrayOfPlayers.player[0]);		
		
		TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
		computerNameTextView.setTypeface(typeFace);
		computerNameTextView.setText(ArrayOfPlayers.player[1]);
		
		
		ArrayOfHitPoints.hitpoints[0] = 20;//20		
		final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
		playerHitPointsTextView.setTypeface(typeFace);
		playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));		
		
		ArrayOfHitPoints.hitpoints[1] = 20;//20
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
		
		//TO MAKE HIT POINTS PULSATE, USE THIS:
		
		final Animation animPulsingAnimation = AnimationUtils.loadAnimation(Client2.this, R.anim.pulsinganimation);
		playerHitPointsTextView.startAnimation(animPulsingAnimation);		
		computerHitPointsTextView.startAnimation(animPulsingAnimation);
		
		
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
		
		
		preInitiativeTitle();
		
		
		// THESE RUN METHODS ARE THREAD-SAFE, SUPPOSEDLY.
		final Handler h3 = new Handler(); // A handler is associated with the thread it is created in.
  	  	h3.postDelayed(new Runnable() {

  	  		@Override
  	  		public void run()
  	  		{  	  			
  	  			centerscrolltext.setVisibility(View.VISIBLE);
  	  			centerscrolltext.startAnimation(animAlphaText);
	  			centerscrolltext.append("> Welcome, " + ArrayOfPlayers.player[1] + ".");
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
		  	  	  		titleinitiativetext.setTypeface(typeFace);
	  		  			
		  				//titletext.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(100));
		  					
		  	  	  		titleinitiativetext.setVisibility(View.VISIBLE);				  		
		  	  	  		titleinitiativetext.append("Initiative");
		  				
		  	  	  		
		  	  	  		playerHitPointsTextView.clearAnimation();		
		  	  	  		computerHitPointsTextView.clearAnimation();
		  	  	  		
		  				
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
						  	  	  		try {
						  	  	  		
							  	  	  		Toast.makeText(Client2.this, "THIS IS THE HOSTIP" + hostIP, Toast.LENGTH_LONG).show();
							  	  			String str = ArrayOfPlayers.player[1] + " has entered the game!";
							  	  			PrintWriter out = new PrintWriter(new BufferedWriter(
							  	  					new OutputStreamWriter(socket.getOutputStream())),
							  	  					true);
							  	  			out.println(str);
							  	  			//NEW:
							  	  			/*
							  	  			out.flush();
							  	  			out.close();
							  	  			*/
							  	  			
								  	  		centerscrolltext.setVisibility(View.VISIBLE);
						  		  	  		centerscrolltext.startAnimation(animAlphaText);
						  		  			centerscrolltext.append("\n" + ArrayOfPlayers.player[1] + " has entered the game!");
							  	  			
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
		  	  			out.println(ArrayOfPlayers.player[1] + ": " + str);
		  	  			
		  	  			
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
			  	final Animation animAlphaTextRepeat = AnimationUtils.loadAnimation(Client2.this, R.anim.anim_alpha_text_repeat);
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
    
	
	//=============================================================================================
	//SEPERATOR
	//=============================================================================================
	
	
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
		//super.loadUrl(“file:///android_asset/www/index.html”);
		
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
		super.onDestroy();
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);

		if (socket != null) {
			try {
				socket.close();
				
				stopService(svc);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void onBackPressed() {

		AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this);
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);

		alert.setTitle("KtOG");
		alert.setMessage("Are you sure you want to exit?");

		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				dialog.dismiss();
				
				stopService(svc);

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
	
	
	//=============================================================================================
	//SEPERATOR
	//=============================================================================================
	
	
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

					updateConversationHandler.post(new updateUIThread(read));

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
