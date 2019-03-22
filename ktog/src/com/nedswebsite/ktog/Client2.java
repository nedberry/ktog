package com.nedswebsite.ktog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.lang.ClassNotFoundException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;
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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.format.Formatter;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
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
    
	int tempCriticalHit;//# of successful crit hits for that user during game (INCLUdING MB)
	//don't need tempGames, tempWins or tempLoses (just adding 1)
	
	int Games;//currently saved stat
	int Wins;//currently saved stat
	int Loses;//currently saved stat
	int CritHitMB;
	int MaxTurns;
	
	int count = 0;
	
	
	Handler updateConversationHandler;
	
	private Socket socket;
	Socket socket0;
	
	private final int SERVERPORT = 2000;// WAS 5000
	String hostIP;//WAS: private static final String SERVER_IP = "192.168.1.208";
	String iPClient;
	
	
	public int id;
	
	
	int playerNumberAttacked;
	
	int playerNumberAttacking;//NEED THIS OR 'if (id == 0)'
	
	String playersFighting;
	
	
	
	//int i;
	
	//int turn = 0;
	
	
	int gameOn;
	int computerAction;
	//int gameOn = 1;	
	//int turn;
	
	int max = 0;	
	
	int numberOfPlayers; // NEED THIS???????????
	
	//static int playerNumberAttacked;
	
	// NEEDS TO BE GLOBAL??
	//int attackResult;
	//int attackDamage;
	//int criticalHitAttackDamageOne;
	//int criticalHitAttackDamageTwo;
	//int cureResult;
	
	//int computerAttackResultAgainstDisarmed;
	
	int computerAttackDamageDisarmed;
	public int[] attackDamageOneDisarmed = new int[1];
	public int[] attackDamageTwoDisarmed = new int[1];
	
	
	//int turnhumandisarmedcomputer; ========= disarmedTurnStart[1] = turn;
	//int turncomputercritmiss;=====disarmedTurnStart[0] = turn;
	//int turncomputerdisarmedhuman; ========= disarmedTurnStart[0] = turn;
	//int turnhumancritmiss;========disarmedTurnStart[1] = turn;	
	
	
	// SOME OF THESE MAY NEED TO BE AN ARRAY-CLASS:
	public int[] blessSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public int[] cureSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public int[] dodgeBlowSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public int[] mightyBlowSpell = new int[] {1, 1, 1, 1, 1, 1, 1};// {1, 1, 1, 1, 1, 1, 1};
	public int[] hasteSpell = new int[] {2, 2, 2, 2, 2, 2, 2};//	{2, 2, 2, 2, 2, 2, 2};
	
	
	// FOR ORDERING PURPOSES IN TITLE:
	int firstsubscript = 0;//JUST TO INITIALIZE
	int secondsubscript = 0;
	int thirdsubscript = 0;
	int fourthsubscript = 0;
	
	
	int finalAttackDamage;
	
	
	
	
	String win = "na";
	String critHitWithMB = "na";
	
	
	//String image = "noImage";
	
	String zeroAttackingFirst = "no";
	String oneAttackingFirst = "no";
	
	//IS THIS WORKING NOW (W NEW onbackpressed CODE)????????????
	// Using variable because was getting null pointer if onbackpressed before rollfromleft was completed:
	String onBackPressedOk = "no";
	
	//String isInvokingService = "true";	
	
	String isinitiativestarted = "no";	
	String isinitiativestartedinterrupted = "no";
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
	public String[] playerDeadYet = new String[] {"yes", "yes", "yes", "yes", "yes", "yes"}; // NEED 6?????????	
	//String playerDeadYet[] = {"yes", "yes", "yes", "yes", "yes", "yes"};
	public String[] canHasDisarmed = new String[] {"no", "no", "no", "no", "no", "no"}; // NEED 6?????????
	
	public int[] disarmedTurnStart = new int[6];
	//String didHumanCriticalMiss = "no";
	//String didComputerCriticalMiss = "no";
	String[] didHumanCriticalMiss = new String[] {"no", "no", "no", "no", "no", "no"};
	
	
	//FOR onResume
	String isSixSidedReadyToBeRolled = "no";
	String isTwentySidedReadyToBeRolled = "no";
	String isInitiativeOver = "yes";
	String istitlestatsopen = "no";
	
	
	
	String whatAvatar;
	Bitmap bmp;
	byte[] imgbyte;
	OutputStream output;
	//Bitmap bm;
	String encImage;
	File imagefile;
	String image_path;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
		
		
		/*
		if (android.os.Build.VERSION.SDK_INT > 9) {
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
	                .permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	    }
		*/
		
		//MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound6);
		
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
		
		
		//NEW
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		
		
    			
		
		
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
		//ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
		//clientAvatar.setVisibility(View.INVISIBLE);
		
		//ImageView customImage = (ImageView) findViewById(R.id.imageviewavatarright);
		//customImage.getLayoutParams().height = 100;
		//customImage.getLayoutParams().width = 100;
		if (getIntent().getExtras() != null) {			
			
			ImageView customImage = (ImageView) findViewById(R.id.imageviewavatarright);
			
			Intent intent2 = getIntent(); 
			image_path = intent2.getStringExtra("imageUri"); 
			Uri fileUri = Uri.parse(image_path);
			customImage.setImageURI(fileUri);
			
			//image = image_path;
			//String str = "customImage :" + image_path;
			//sendToHost(str);
			
			

			
			
			
			
			
			
			//BitmapDrawable drawable = (BitmapDrawable) customImage.getDrawable();
			//bmp = drawable.getBitmap();
			
			
			//imgbyte = getBytesFromBitmap(bmp);
			
			
			
			
			/*
			File myFile = new File(fileUri.getPath());
			//myFile.getAbsolutePath();
			FileInputStream fis = null;
			try {
				 fis = new FileInputStream (myFile);				 
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Bitmap bm = BitmapFactory.decodeStream(fis);
			imgbyte = getBytesFromBitmap(bm);
			*/
			/*
			//String filepath = "/sdcard/twentysidedbutton.png";
			String filepath = "/storage/extSdCard/twentysidedbutton.png";
		    File imagefile = new File(filepath);
		    FileInputStream fis = null;
		         try {
		             fis = new FileInputStream(imagefile);
		         } catch (FileNotFoundException e) {
		             // TODO Auto-generated catch block
		             e.printStackTrace();
		         }
		         //BitmapDrawable drawable = (BitmapDrawable) customImage.getDrawable();
					//bmp = drawable.getBitmap();
		     Bitmap bm = BitmapFactory.decodeStream(fis);
		     imgbyte = getBytesFromBitmap(bm);
		     //profile.setImageBitmap(bm);
			*/
			
			//ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        
	        //bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
	        
	        //byte[] b = baos.toByteArray();
	        //encImage = Base64.encodeToString(b, Base64.DEFAULT);
		     
			
			
			
			imagefile = new File(getPath(fileUri));
			
			
			//imagefile = new File(fileUri.getPath());
			//imagefile = new File("/storage/extSdCard/twentysidedbutton.png");
			
	        /*OLD WAY (WORKS):
			FileInputStream fis = null;
	        try{
	            fis = new FileInputStream(imagefile);
	        }catch(FileNotFoundException e){
	            e.printStackTrace();
	        }
	        bm = BitmapFactory.decodeStream(fis);
			*/
			
			whatAvatar = "custom";
		}
		
		else if (ArrayOfAvatars.avatar[0].equals("computer")){
			ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
			clientAvatar.setBackgroundResource(R.drawable.computer);
			
			whatAvatar = "computer";
		}
		else if (ArrayOfAvatars.avatar[0].equals("crossedswords")){
			ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
			clientAvatar.setBackgroundResource(R.drawable.crossedswords2);
			
			whatAvatar = "crossedswords";
		}
		else if (ArrayOfAvatars.avatar[0].equals("stonedead")){
			ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
			clientAvatar.setBackgroundResource(R.drawable.stonedead2);
			
			whatAvatar = "stonedead";
		}
		/*
		if (id == 0) {
			
			if (ArrayOfAvatars.avatar[0].equals("computer")){
				clientAvatar.setBackgroundResource(R.drawable.computer);
			}
			else if (ArrayOfAvatars.avatar[0].equals("crossedswords")){
				clientAvatar.setBackgroundResource(R.drawable.crossedswords2);
			}
			else if (ArrayOfAvatars.avatar[0].equals("stonedead")){
				clientAvatar.setBackgroundResource(R.drawable.stonedead2);
			}	
		}
		else if (id == 1) {
			
			if (ArrayOfAvatars.avatar[1].equals("computer")){
				clientAvatar.setBackgroundResource(R.drawable.computer);
			}
			else if (ArrayOfAvatars.avatar[1].equals("crossedswords")){
				clientAvatar.setBackgroundResource(R.drawable.crossedswords2);
			}
			else if (ArrayOfAvatars.avatar[1].equals("stonedead")){
				clientAvatar.setBackgroundResource(R.drawable.stonedead2);
			}
		}
		*/
		
		
		ImageView blessLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftbless);
		blessLeft.setVisibility(View.INVISIBLE);
		blessLeft.setImageDrawable(null);
		ImageView cureLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftcure);
		cureLeft.setVisibility(View.INVISIBLE);
		cureLeft.setImageDrawable(null);
		ImageView dodgeLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftdodge);
		dodgeLeft.setVisibility(View.INVISIBLE);
		dodgeLeft.setImageDrawable(null);
		ImageView mbLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftmb);
		mbLeft.setVisibility(View.INVISIBLE);
		mbLeft.setImageDrawable(null);
		ImageView hasteLeft1 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste1);
		hasteLeft1.setVisibility(View.INVISIBLE);
		hasteLeft1.setImageDrawable(null);
		ImageView hasteLeft2 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste2);
		hasteLeft2.setVisibility(View.INVISIBLE);
		hasteLeft2.setImageDrawable(null);
		
		ImageView blessRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightbless);
		blessRight.setVisibility(View.INVISIBLE);
		blessRight.setImageDrawable(null);
		ImageView cureRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightcure);
		cureRight.setVisibility(View.INVISIBLE);
		cureRight.setImageDrawable(null);
		ImageView dodgeRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightdodge);
		dodgeRight.setVisibility(View.INVISIBLE);
		dodgeRight.setImageDrawable(null);
		ImageView mbRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightmb);
		mbRight.setVisibility(View.INVISIBLE);
		mbRight.setImageDrawable(null);
		ImageView hasteRight1 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste1);
		hasteRight1.setVisibility(View.INVISIBLE);
		hasteRight1.setImageDrawable(null);
		ImageView hasteRight2 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste2);
		hasteRight2.setVisibility(View.INVISIBLE);
		hasteRight2.setImageDrawable(null);
		
		
		
		final ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
		//titleBlankButton.setVisibility(View.INVISIBLE);
		
		final ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
		titleBlankButton.setVisibility(View.INVISIBLE);
		titleBlankButton.setImageDrawable(null);
		
		// Here to prevent premature rolling (use ".setEnabled(false);" in "resultsInitiative()"):
		final ImageView sixSidedBlank = (ImageView) findViewById(R.id.sixsidedanimation);
		sixSidedBlank.setVisibility(View.INVISIBLE);
		sixSidedBlank.setImageDrawable(null);
		
		final ImageView twentySidedBlank = (ImageView) findViewById(R.id.twentysidedanimation);
		twentySidedBlank.setVisibility(View.INVISIBLE);
		twentySidedBlank.setImageDrawable(null);
		
		
		
		// ANIMATIONS RUNNING SLOWER IN THREADS:
		
		scrollAnimationLeftDown();
		
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
		
		MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
		
		
		
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
	  			
	  			
	  			final Handler h4 = new Handler();
	  	  	  	h4.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
	  	  	  			MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
	  	  	  		}
	  	  	  	}, 2750);
	  			
	  			
	  			//startService(svc);
	  	  	  	
	  			
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
	  	  	  			
	  	  	  			
	  	  	  			MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
	  	  	  			
		  				
		  	  	  		/*WAS FOR 'HUMAN' HP:
		  	  	  		playerHitPointsTextView.clearAnimation();		
		  	  	  		*/
		  	  	  		computerHitPointsTextView.clearAnimation();
		  	  	  		
		  				
		  				//final Handler h4 = new Handler();
			  	  	  	//h4.postDelayed(new Runnable() {
			  	  	  		
			  	  	  		//@Override
			  	  	  		//public void run() {				  				
			  	  	  			
			  	  	  			// Sets sixSidedBlank visible & enabled.
			  	  	  			//sixSidedRollFromLeft();  	  	  			
			  		  			
			  	  	  			
				  		  		final Handler h5 = new Handler();
					  	  	  	h5.postDelayed(new Runnable() {
					  	  	  			
					  	  	  			// Does this thread help:?
						  	  	  		@Override
						  	  	  		public void run() {
						  	  	  			
						  	  	  			startService(svc);
						  	  	  			
						  	  	  			try {
						  	  	  		
								  	  	  		//Toast.makeText(Client2.this, "THIS IS THE HOSTIP" + hostIP, Toast.LENGTH_SHORT).show();
							  	  	  			
						  	  	  				
						  	  	  				PrintWriter out = new PrintWriter(new BufferedWriter(
							  	  					new OutputStreamWriter(socket.getOutputStream())),
							  	  					true);
						  	  	  				
						  	  	  				
						  	  	  				
						  	  	  				
						  	  	  				
						  	  	  				
						  	  	  				out.println("whatAvatar :" + whatAvatar);
						  	  	  				
						  	  	  				
						  	  	  				
						  	  	  				
								  	  			String str2 = ArrayOfPlayers.player[0];
								  	  			out.println("PlayerName :" + str2);
								  	  			
								  	  			
								  	  			
								  	  			
								  	  			
								  	  			
								  	  			/*
								  	  			String str3 = image;
								  	  			out.println("cstmImage :" + str3);
								  	  			*/
								  	  			
								  	  			
									  	  		final Handler h6 = new Handler();
									  	  	  	h6.postDelayed(new Runnable() {
									  	  	  			
									  	  	  		@Override
									  	  	  		public void run() {
									  	  	  			
									  	  	  			try {
									  	  	  			
										  	  	  			PrintWriter out = new PrintWriter(new BufferedWriter(
										  	  					new OutputStreamWriter(socket.getOutputStream())),
										  	  					true);
										  	  	  			
										  	  	  			if (id == 0) {
										  	  	  				
											  	  	  			String str = ArrayOfPlayers.player[0] + " has entered the game!";
											  	  	  			out.println(str);
											  	  	  			
											  	  	  			
												  	  	  		final Handler h7 = new Handler();
													  	  	  	h7.postDelayed(new Runnable() {
													  	  	  		@Override
													  	  	  		public void run() {
													  	  	  			
													  	  	  			//Toast.makeText(Client2.this, "The host will start the game...", Toast.LENGTH_SHORT).show();
														  	  	  		Toast toast = Toast.makeText(Client2.this, "The host will start the game...", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
		    	                		            		  			View view = toast.getView();
		    	                		            		  			view.setBackgroundResource(R.drawable.centerscroll3toast);
		    	                		            		  			toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
	
		    	                		            		  			TextView text = (TextView) view.findViewById(android.R.id.message);
		    	                		            		  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		    	                		            		  			text.setTypeface(typeFace);
		    	                		            		  			text.setTextColor(Color.parseColor("#FFFFFF"));
		    	                		            		  			//text.setRotation(-45);
		    	                		            		  			text.setGravity(Gravity.CENTER);
		    	                		            		  			
		    	                		            		  			toast.show();
													  	  	  		}
													  	  	  	}, 2500);
										  	  	  			}
										  	  	  			else if (id == 1) {
										  	  	  				
											  	  	  			String str = ArrayOfPlayers.player[1] + " has entered the game!";
											  	  	  			out.println(str);
											  	  	  			
												  	  	  		final Handler h7 = new Handler();
													  	  	  	h7.postDelayed(new Runnable() {
													  	  	  		@Override
													  	  	  		public void run() {
													  	  	  			
													  	  	  			//Toast.makeText(Client2.this, "The host will start the game...", Toast.LENGTH_SHORT).show();
														  	  	  		Toast toast = Toast.makeText(Client2.this, "The host will start the game...", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
		    	                		            		  			View view = toast.getView();
		    	                		            		  			view.setBackgroundResource(R.drawable.centerscroll3toast);
		    	                		            		  			toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
	
		    	                		            		  			TextView text = (TextView) view.findViewById(android.R.id.message);
		    	                		            		  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		    	                		            		  			text.setTypeface(typeFace);
		    	                		            		  			text.setTextColor(Color.parseColor("#FFFFFF"));
		    	                		            		  			//text.setRotation(-45);
		    	                		            		  			text.setGravity(Gravity.CENTER);
		    	                		            		  			
		    	                		            		  			toast.show();
													  	  	  		}
													  	  	  	}, 2500);
										  	  	  			}
										  	  	  			

										  	  	  			//names();
										  	  	  			
										  	  	  		} catch (UnknownHostException e) {
											  	  			e.printStackTrace();
											  	  		} catch (IOException e) {
											  	  			e.printStackTrace();
											  	  		} catch (Exception e) {
											  	  			e.printStackTrace();
											  	  		}
									  	  	  			
									  	  	  			
//writeTextToFile();			  	  	  			
									  	  		
//getTextFromFile();
									  	  	  			
									  	  	  			
										  	  	  		Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler()
											  	  	    {
											  	  	      @Override
											  	  	      public void uncaughtException (Thread thread, Throwable e)
											  	  	      {
											  	  	        handleUncaughtException (thread, e);
											  	  	      }
											  	  	    });
									  	  	  			
									  	  	  			
									  	  	  			
									  	  	  			
									  	  	  			
									  	  	  			if (whatAvatar.equals("custom")) {
									  	  	  				
											  	  	  		final Handler h8 = new Handler();
												  	  	  	h8.postDelayed(new Runnable() {
												  	  	  			
												  	  	  		@Override
												  	  	  		public void run() {
												  	  	  			
												  	  	  		if (id == 0) {
											  	  	  				
												  	  	  			sendImage0();
											  	  	  			}
											  	  	  			else if (id == 1) {
											  	  	  				
											  	  	  				sendImage1();
											  	  	  			}
													  	  	  			
													  	  	  		//try {           
													  	                /*
														  	  	  		PrintWriter out = new PrintWriter(new BufferedWriter(
														  	  					new OutputStreamWriter(socket.getOutputStream())),
														  	  					true);
														  	  			//out.println("&*," + encImage);
														  	  	  		out.println(encImage);
													  	                 */
												  	  	  			
													  	  	  			
												  	  	  				
													  	  	  			//new Thread(new sendBullShit()).start();
													  	  	  			/*
														  	  	  		InetAddress serverAddr = InetAddress.getByName(hostIP);// WAS: SERVER_IP
	
																		//socket0 = new Socket(serverAddr, SERVERPORT);	
														  	  	  			
														  	  	  		
														  	  	  			
													  	  	  			socket0 = new Socket(serverAddr, 2100);
													  	  	  			//PrintWriter out0 = new PrintWriter(socket.getOutputStream(), true);
														  	  	  	     
													  	  	  			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket0.getOutputStream()));
													  	  	  			bufferedWriter.write(encImage);
													  	  	  			bufferedWriter.flush();
													  	  	  			bufferedWriter.close();
														  	  	  	    */
														  	  	  	     
														  	  	  	     
														  	  	  	     
														  	  	  	   	
																	   	  	
														               
													  	  	  			/*
														  	  	  		OutputStream out = new BufferedOutputStream(socket.getOutputStream());
														  	             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
														  	             writer.write(encImage);
														  	             Log.d("Vicky", "Data to php = " + encImage);
														  	             writer.flush();
														  	             writer.close();
														  	             out.close();
														  	           //socket.connect();
															            */    
														  	  	  		
														  	  	  		
														  	  	  		
														  	  	  			/*
														  	  	  		output = socket.getOutputStream();
													                    
													                    output.write(imgbyte);
													                    output.flush();	
														  	  	  			*/
													                    
														  	  	  			/*
														                    final Handler h6 = new Handler();
																  	  	  	h6.postDelayed(new Runnable() {
																  	  	  			
																  	  	  		@Override
																  	  	  		public void run() {
																	  	  	  			
																  	  	  			try {
																						//output.close();
																  	  	  				output.write(imgbyte);
																  	  	  				output.flush();
																					} catch (IOException e) {
																						// TODO Auto-generated catch block
																						e.printStackTrace();
																					}
																  	  	  		}
																  	  	  	}, 5000);	
														  	  	  			*/
													  	  	  			
													  	  	  			
													  	           // } catch (UnknownHostException e) {
													  	            //    e.printStackTrace();
													  	           // } catch (IOException e) {
													  	           //     e.printStackTrace();
													  	           // } catch (Exception e) {
													  	            //    e.printStackTrace();
													  	            //}
												  	  	  		}
												  	  	  	}, 1000);
											  	  	  	}
									  	  	  			
									  	  	  		}
									  	  	  	}, 1000);
									  	  	  	
								  	  			
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
					  	  	  	}, 1000);
			  	  	  		//}
			  	  	  	//}, 2000);
		  	  	  	}
	  	  	  	}, 3550);//FINAGLING TO GET RIGHT (MAINLY 1ST TIME) - should be at least 4700?	  	  		  			
  	  		}			//WAS: 1000
  	  	}, 1000);//WAS: 2000
  	  	
  	  	
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
	          			
	          			
	          			MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
	              		
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
	              		
	              		
	              		final Handler h5 = new Handler();
        	  	  	  	h5.postDelayed(new Runnable() {		  	  	  			
        	  	  	  			
        	  	  	  		@Override
        		  	  	  	public void run() {

        	  	  	  			MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
        	  	  	  		}
    	  	  	  		}, 500);
	              		
	              		
	              		final Handler h = new Handler();
	  		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
	  		  	  	  			
	  		  	  	  		@Override
	  			  	  	  	public void run() {//HAVE TO USE .setText INSTEAD OF .append (INITIATIVE)
	  		  	  	  			
	    		  	  	  		final TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);    			  	  		    						
	    							
		    		  	  	  	titletext.setVisibility(View.INVISIBLE);
			  	  	  			
	    		  	  	  		//final TextView titlerulestext = (TextView) findViewById(R.id.textviewtitlerulestext);	    							
	    			  	  		//titlerulestext.setVisibility(View.VISIBLE);
			    		  	  	final TableLayout summaryTableLayout = (TableLayout) findViewById(R.id.summaryTable);
			    		  	  	
			    		  	  	//summaryTableLayout.removeAllViews();
			    		  	  	
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
			    		  	  	
			    		  	  	if (numberOfPlayers == 3) {
				  		  	  		
				  			  	  	TextView player3textview = (TextView) findViewById(R.id.player3);
				  			  	  	player3textview.setTypeface(typeFace);
				  			  	  	player3textview.setText(ArrayOfPlayers.player[thirdsubscript]);
				  			  	  	player3textview.setVisibility(View.VISIBLE);		  	  	
				  			  	  	
				  			  	  	TextView hitpoints3textview = (TextView) findViewById(R.id.hitpointsplayer3);
				  			  	  	hitpoints3textview.setTypeface(typeFace);
				  			  	  	String hitpoints3String = Integer.toString(ArrayOfHitPoints.hitpoints[thirdsubscript]);
				  			  	  	hitpoints3textview.setText(hitpoints3String);
				  			  	  	hitpoints3textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	TextView blessplayer3textview = (TextView) findViewById(R.id.blessplayer3);
				  			  	  	blessplayer3textview.setTypeface(typeFace);
				  			  	  	String blessplayer3String = Integer.toString(blessSpell[thirdsubscript]);
				  			  	  	blessplayer3textview.setText(blessplayer3String);
				  			  	  	blessplayer3textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	TextView cureplayer3textview = (TextView) findViewById(R.id.cureplayer3);
				  			  	  	cureplayer3textview.setTypeface(typeFace);
				  			  	  	String cureplayer3String = Integer.toString(cureSpell[thirdsubscript]);
				  			  	  	cureplayer3textview.setText(cureplayer3String);
				  			  	  	cureplayer3textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	TextView dodgeplayer3textview = (TextView) findViewById(R.id.dodgeplayer3);
				  			  	  	dodgeplayer3textview.setTypeface(typeFace);
				  			  	  	String dodgeplayer3String = Integer.toString(dodgeBlowSpell[thirdsubscript]);
				  			  	  	dodgeplayer3textview.setText(dodgeplayer3String);
				  			  	  	dodgeplayer3textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	TextView mightyblowplayer3textview = (TextView) findViewById(R.id.mightyblowplayer3);
				  			  	  	mightyblowplayer3textview.setTypeface(typeFace);
				  			  	  	String mightyblowplayer3String = Integer.toString(mightyBlowSpell[thirdsubscript]);
				  			  	  	mightyblowplayer3textview.setText(mightyblowplayer3String);
				  			  	  	mightyblowplayer3textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	TextView hasteplayer3textview = (TextView) findViewById(R.id.hasteplayer3);
				  			  	  	hasteplayer3textview.setTypeface(typeFace);
				  			  	  	String hasteplayer3String = Integer.toString(hasteSpell[thirdsubscript]);
				  			  	  	hasteplayer3textview.setText(hasteplayer3String);
				  			  	  	hasteplayer3textview.setVisibility(View.VISIBLE);		  	  		
			    		  	  	}
			    		  	  	/*
			    		  	  	else if (numberOfPlayers == 4) {
			  		  	  		
				  			  	  	TextView player3textview = (TextView) findViewById(R.id.player3);
				  			  	  	player3textview.setTypeface(typeFace);
				  			  	  	player3textview.setText(ArrayOfPlayers.player[thirdsubscript]);
				  			  	  	player3textview.setVisibility(View.VISIBLE);		  	  	
				  			  	  	
				  			  	  	TextView hitpoints3textview = (TextView) findViewById(R.id.hitpointsplayer3);
				  			  	  	hitpoints3textview.setTypeface(typeFace);
				  			  	  	String hitpoints3String = Integer.toString(ArrayOfHitPoints.hitpoints[thirdsubscript]);
				  			  	  	hitpoints3textview.setText(hitpoints3String);
				  			  	  	hitpoints3textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	TextView blessplayer3textview = (TextView) findViewById(R.id.blessplayer3);
				  			  	  	blessplayer3textview.setTypeface(typeFace);
				  			  	  	String blessplayer3String = Integer.toString(blessSpell[thirdsubscript]);
				  			  	  	blessplayer3textview.setText(blessplayer3String);
				  			  	  	blessplayer3textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	TextView cureplayer3textview = (TextView) findViewById(R.id.cureplayer3);
				  			  	  	cureplayer3textview.setTypeface(typeFace);
				  			  	  	String cureplayer3String = Integer.toString(cureSpell[thirdsubscript]);
				  			  	  	cureplayer3textview.setText(cureplayer3String);
				  			  	  	cureplayer3textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	TextView dodgeplayer3textview = (TextView) findViewById(R.id.dodgeplayer3);
				  			  	  	dodgeplayer3textview.setTypeface(typeFace);
				  			  	  	String dodgeplayer3String = Integer.toString(dodgeBlowSpell[thirdsubscript]);
				  			  	  	dodgeplayer3textview.setText(dodgeplayer3String);
				  			  	  	dodgeplayer3textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	TextView mightyblowplayer3textview = (TextView) findViewById(R.id.mightyblowplayer3);
				  			  	  	mightyblowplayer3textview.setTypeface(typeFace);
				  			  	  	String mightyblowplayer3String = Integer.toString(mightyBlowSpell[thirdsubscript]);
				  			  	  	mightyblowplayer3textview.setText(mightyblowplayer3String);
				  			  	  	mightyblowplayer3textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	TextView hasteplayer3textview = (TextView) findViewById(R.id.hasteplayer3);
				  			  	  	hasteplayer3textview.setTypeface(typeFace);
				  			  	  	String hasteplayer3String = Integer.toString(hasteSpell[thirdsubscript]);
				  			  	  	hasteplayer3textview.setText(hasteplayer3String);
				  			  	  	hasteplayer3textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	
				  			  	  	TextView player4textview = (TextView) findViewById(R.id.player4);
				  			  	  	player4textview.setTypeface(typeFace);
				  			  	  	player4textview.setText(ArrayOfPlayers.player[fourthsubscript]);
				  			  	  	player4textview.setVisibility(View.VISIBLE);		  	  	
				  			  	  	
				  			  	  	TextView hitpoints4textview = (TextView) findViewById(R.id.hitpointsplayer4);
				  			  	  	hitpoints4textview.setTypeface(typeFace);
				  			  	  	String hitpoints4String = Integer.toString(ArrayOfHitPoints.hitpoints[fourthsubscript]);
				  			  	  	hitpoints4textview.setText(hitpoints4String);
				  			  	  	hitpoints4textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	TextView blessplayer4textview = (TextView) findViewById(R.id.blessplayer4);
				  			  	  	blessplayer4textview.setTypeface(typeFace);
				  			  	  	String blessplayer4String = Integer.toString(blessSpell[fourthsubscript]);
				  			  	  	blessplayer4textview.setText(blessplayer4String);
				  			  	  	blessplayer4textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	TextView cureplayer4textview = (TextView) findViewById(R.id.cureplayer4);
				  			  	  	cureplayer4textview.setTypeface(typeFace);
				  			  	  	String cureplayer4String = Integer.toString(cureSpell[fourthsubscript]);
				  			  	  	cureplayer4textview.setText(cureplayer4String);
				  			  	  	cureplayer4textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	TextView dodgeplayer4textview = (TextView) findViewById(R.id.dodgeplayer4);
				  			  	  	dodgeplayer4textview.setTypeface(typeFace);
				  			  	  	String dodgeplayer4String = Integer.toString(dodgeBlowSpell[fourthsubscript]);
				  			  	  	dodgeplayer4textview.setText(dodgeplayer4String);
				  			  	  	dodgeplayer4textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	TextView mightyblowplayer4textview = (TextView) findViewById(R.id.mightyblowplayer4);
				  			  	  	mightyblowplayer4textview.setTypeface(typeFace);
				  			  	  	String mightyblowplayer4String = Integer.toString(mightyBlowSpell[fourthsubscript]);
				  			  	  	mightyblowplayer4textview.setText(mightyblowplayer4String);
				  			  	  	mightyblowplayer4textview.setVisibility(View.VISIBLE);
				  			  	  	
				  			  	  	TextView hasteplayer4textview = (TextView) findViewById(R.id.hasteplayer4);
				  			  	  	hasteplayer4textview.setTypeface(typeFace);
				  			  	  	String hasteplayer4String = Integer.toString(hasteSpell[fourthsubscript]);
				  			  	  	hasteplayer4textview.setText(hasteplayer4String);
				  			  	  	hasteplayer4textview.setVisibility(View.VISIBLE);
			    		  	  	}    		  	  	
	    			  	  		*/
			    		  	  	
			    		  	  	
			    		  	  	final Handler h3 = new Handler();
		        	  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
		        	  	  	  			
		        	  	  	  		@Override
		        		  	  	  	public void run() {

		        	  	  	  			MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
		        	  	  	  		}
		    	  	  	  		}, 10100);
			    		  	  	
	    			  	  		
		    			  	  	final Handler h = new Handler();
		    		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		    		  	  	  			
		    		  	  	  		@Override
		    			  	  	  	public void run() {
		    		  	  	  			
		    		  	  	  			MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
		    		  	  	  			
			    		  	  	  		//titlerulestext.setVisibility(View.INVISIBLE);
			    		  	  	  		summaryTableLayout.setVisibility(View.INVISIBLE);
			    		  	  	  		
			    		  	  	  		View lineInSummaryTableLayout = (View) findViewById(R.id.line);
			    		  	  	  		lineInSummaryTableLayout.setVisibility(View.INVISIBLE);
		    		  	  	  			
			    		  	  	  		final TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);    			  	  		    						
		    							
			    			  	  		titletext.setVisibility(View.VISIBLE);
			    			  	  		
			    			  	  		titleBlankButton.setEnabled(true);
			    			  	  		
			    			  	  		istitlestatsopen = "no";
		    			  	  	  	}
		    		  	  	  	}, 10500);//WAS: 11250
	  			  	  	  	}
	  		  	  	  	}, 300);//WAS: 600
	          		}         		           				
	          	}            	
			}            
		});
  	  	
  	  	
		chatBlankButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// Toast.makeText(Client2.this, "CHAT TEST",
				// Toast.LENGTH_SHORT).show();				
				
				
				AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this, R.style.customalertdialog);

		    	//alert.setTitle("Chat");
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
		  	  			
			  	  		if (id == 0) {
	  	  	  				
			  	  			out.println(ArrayOfPlayers.player[0] + ": " + str);
	  	  	  			}
	  	  	  			else if (id == 1) {
	  	  	  				
	  	  	  				out.println(ArrayOfPlayers.player[1] + ": " + str);
	  	  	  			}
		  	  			
		  	  			
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
		    	    
		    		  hideSystemUI();
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
	
	
	
	
	
	public void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
	
	
	
	
	
	
	public void writeTextToFile() {//CREATES PLAYER PROFILE IF IT DOESN'T EXIST
		
		//File playerName = null;
		
		try {
			
			if (id == 0) {
				
				//THIS WORKS, BUT THOUGHT BETTER TO SPECIFY IN CASE 'this.getExternalFilesDir(null)' DECIDES NOT TO WORK ON A SPECIFIC DEVICE.
				//File playerName = new File(this.getExternalFilesDir(null), ArrayOfPlayers.player[5] + ".txt");
				
				//playerName = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files", ArrayOfPlayers.player[0] + ".txt");
				
				SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
				boolean check = pref.contains(ArrayOfPlayers.player[0]);
				
				if (!check) {
					
					SharedPreferences.Editor edit = pref.edit();
					edit.putString(ArrayOfPlayers.player[0], "GamesPlayed:0:Wins:0:Loses:0:CritHitMB:0:MaxTurns:0");
								
					edit.commit();
				}
			}
			if (id == 1) {//WAS: else
				
				//THIS WORKS, BUT THOUGHT BETTER TO SPECIFY IN CASE 'this.getExternalFilesDir(null)' DECIDES NOT TO WORK ON A SPECIFIC DEVICE.
				//File playerName = new File(this.getExternalFilesDir(null), ArrayOfPlayers.player[5] + ".txt");
				
				//playerName = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files", ArrayOfPlayers.player[1] + ".txt");
				
				SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
				boolean check = pref.contains(ArrayOfPlayers.player[1]);
				
				if (!check) {
					
					SharedPreferences.Editor edit = pref.edit();
					edit.putString(ArrayOfPlayers.player[1], "GamesPlayed:0:Wins:0:Loses:0:CritHitMB:0:MaxTurns:0");
								
					edit.commit();
				}
			}
			
			/*
			if (!playerName.exists())
				playerName.createNewFile();

				// adds line to the file
				BufferedWriter writer = new BufferedWriter(new FileWriter(playerName, false));//FOR APPENd: true
				writer.write("GamesPlayed:0:Wins:0:Loses:0");
				writer.close();
			*/
			
		} catch (Exception e) {
			//Log.e("ReadWriteFile", "Unable to write to the TestFile.txt file.");
			//Toast.makeText(Client2.this, "Error -- Player not saved.", Toast.LENGTH_SHORT).show();
			
			Toast toast = Toast.makeText(Client2.this, "Error -- Player not saved.", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
  			View view = toast.getView();
  			view.setBackgroundResource(R.drawable.centerscroll3toast);
  			toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER

  			TextView text = (TextView) view.findViewById(android.R.id.message);
  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
  			text.setTypeface(typeFace);
  			text.setTextColor(Color.parseColor("#FFFFFF"));
  			//text.setRotation(-45);
  			text.setGravity(Gravity.CENTER);
  			
  			toast.show();
		}
	}
	
	public void getTextFromFile() {//READS EXISTING DATA FROM PLAYER PROFILE AND WRITES NEW DATA
		
		//File playerName = null;
		try {
			
		
			if (id == 0) {
				
				//THIS WORKS, BUT THOUGHT BETTER TO SPECIFY IN CASE 'this.getExternalFilesDir(null)' DECIDES NOT TO WORK ON A SPECIFIC DEVICE.
				//File playerName = new File(this.getExternalFilesDir(null), ArrayOfPlayers.player[5] + ".txt");
				
				//playerName = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files", ArrayOfPlayers.player[0] + ".txt");
				
				SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
				
				String username = pref.getString(ArrayOfPlayers.player[0], "");
				
				
				String[] parts = username.split(":");
				
				String part1 = parts[0];//GamesPlayed
		  	  	String part2 = parts[1];//0
		  	  	String part3 = parts[2];//Wins
		  	  	String part4 = parts[3];//0
		  	  	String part5 = parts[4];//Loses
		  	  	String part6 = parts[5];//0
		  	  	String part7 = parts[6];//CritHitWithMB
				String part8 = parts[7];//0
				String part9 = parts[8];//MaxTurns
				String part10 = parts[9];//0
		  	  	
		  	  	Games = Integer.parseInt(part2);
		  	  	Wins = Integer.parseInt(part4);
		  	  	Loses = Integer.parseInt(part6);
		  	  	CritHitMB = Integer.parseInt(part8);
		  	  	MaxTurns = Integer.parseInt(part10);
		  	  	
		  	  	
		  	  	SharedPreferences.Editor edit = pref.edit();
		  	  	
		  	  	if (ArrayOfTurn.turn[0] >= MaxTurns) {
		  	  		
		  	  		if (win.equals("yes") && (critHitWithMB.equals("na"))) {
		  	  			//NEW RECORD - TURNS & WINS
			  	  		Toast toast = Toast.makeText(Client2.this, "New Record!" + "\n" + "Turns, Wins", Toast.LENGTH_LONG);//INSTEAD OF "Choose Action": R.string.string_message_id
						View view = toast.getView();
						view.setBackgroundResource(R.drawable.centerscroll3toast);
						//toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
						toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
			
						TextView text = (TextView) view.findViewById(android.R.id.message);
						Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						text.setTypeface(typeFace);
						text.setTextColor(Color.parseColor("#FFFFFF"));
						//text.setRotation(-45);
						text.setGravity(Gravity.CENTER);
						
						/*	ONLY WAY I COUL FIND TO CENTER TEXT IN CUSTOM TOAST FOR S3(4.4.2), S4(4.4.2) & AMAZON(5.3):
						 	if ((getResources().getDisplayMetrics().densityDpi==160) && (android.os.Build.VERSION.RELEASE.startsWith("5.3")))
							text.setPadding(165, 0, 0, 0);
						 */
						toast.show();
		  	  			
		  	  			edit.putString(ArrayOfPlayers.player[0], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 1) + ":Loses:" + (Loses + 0) + ":CritHitMB:" + (CritHitMB + 0) + ":MaxTurns:" + (ArrayOfTurn.turn[0]));
		  	  			edit.commit();
			  	  	}
			  	  	if (win.equals("yes") && (critHitWithMB.equals("yes"))) {
			  	  		//NEW RECORD - TURNS & WINS & CHMB
				  	  	Toast toast = Toast.makeText(Client2.this, "New Record!" + "\n" + "Turns, Wins, CH/MB", Toast.LENGTH_LONG);//INSTEAD OF "Choose Action": R.string.string_message_id
						View view = toast.getView();
						view.setBackgroundResource(R.drawable.centerscroll3toast);
						//toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
						toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
			
						TextView text = (TextView) view.findViewById(android.R.id.message);
						Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						text.setTypeface(typeFace);
						text.setTextColor(Color.parseColor("#FFFFFF"));
						//text.setRotation(-45);
						text.setGravity(Gravity.CENTER);
						
						/*	ONLY WAY I COUL FIND TO CENTER TEXT IN CUSTOM TOAST FOR S3(4.4.2), S4(4.4.2) & AMAZON(5.3):
						 	if ((getResources().getDisplayMetrics().densityDpi==160) && (android.os.Build.VERSION.RELEASE.startsWith("5.3")))
							text.setPadding(165, 0, 0, 0);
						 */
						toast.show();
			  	  		
				  		edit.putString(ArrayOfPlayers.player[0], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 1) + ":Loses:" + (Loses + 0) + ":CritHitMB:" + (CritHitMB + 1) + ":MaxTurns:" + (ArrayOfTurn.turn[0]));
				  		edit.commit();
				  	}
			  	  	
			  	  	if (win.equals("no") && (critHitWithMB.equals("na"))) {
			  	  		//NEW RECORD - TURNS
				  	  	Toast toast = Toast.makeText(Client2.this, "New Record!" + "\n" + "Turns", Toast.LENGTH_LONG);//INSTEAD OF "Choose Action": R.string.string_message_id
						View view = toast.getView();
						view.setBackgroundResource(R.drawable.centerscroll3toast);
						//toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
						toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
			
						TextView text = (TextView) view.findViewById(android.R.id.message);
						Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						text.setTypeface(typeFace);
						text.setTextColor(Color.parseColor("#FFFFFF"));
						//text.setRotation(-45);
						text.setGravity(Gravity.CENTER);
						
						/*	ONLY WAY I COUL FIND TO CENTER TEXT IN CUSTOM TOAST FOR S3(4.4.2), S4(4.4.2) & AMAZON(5.3):
						 	if ((getResources().getDisplayMetrics().densityDpi==160) && (android.os.Build.VERSION.RELEASE.startsWith("5.3")))
							text.setPadding(165, 0, 0, 0);
						 */
						toast.show();
			  	  		
			  	  		edit.putString(ArrayOfPlayers.player[0], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 0) + ":Loses:" + (Loses + 1) + ":CritHitMB:" + (CritHitMB + 0) + ":MaxTurns:" + (ArrayOfTurn.turn[0]));
			  	  		edit.commit();
			  	  	}
			  	  	if (win.equals("no") && (critHitWithMB.equals("yes"))) {
			  	  		//NEW RECORD - TURNS & CHMB
				  	  	Toast toast = Toast.makeText(Client2.this, "New Record!" + "\n" + "Turns, CH/MB", Toast.LENGTH_LONG);//INSTEAD OF "Choose Action": R.string.string_message_id
						View view = toast.getView();
						view.setBackgroundResource(R.drawable.centerscroll3toast);
						//toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
						toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
			
						TextView text = (TextView) view.findViewById(android.R.id.message);
						Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						text.setTypeface(typeFace);
						text.setTextColor(Color.parseColor("#FFFFFF"));
						//text.setRotation(-45);
						text.setGravity(Gravity.CENTER);
						
						/*	ONLY WAY I COUL FIND TO CENTER TEXT IN CUSTOM TOAST FOR S3(4.4.2), S4(4.4.2) & AMAZON(5.3):
						 	if ((getResources().getDisplayMetrics().densityDpi==160) && (android.os.Build.VERSION.RELEASE.startsWith("5.3")))
							text.setPadding(165, 0, 0, 0);
						 */
						toast.show();
			  	  		
				  		edit.putString(ArrayOfPlayers.player[0], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 0) + ":Loses:" + (Loses + 1) + ":CritHitMB:" + (CritHitMB + 1) + ":MaxTurns:" + (ArrayOfTurn.turn[0]));
				  		edit.commit();
				  	}
		  	  	}
		  	  	
		  	  	if (ArrayOfTurn.turn[0] < MaxTurns) {
			  		
		  	  		if (win.equals("yes") && (critHitWithMB.equals("na"))) {
		  	  			//NEW RECORD - WINS
			  	  		Toast toast = Toast.makeText(Client2.this, "New Record!" + "\n" + "Wins", Toast.LENGTH_LONG);//INSTEAD OF "Choose Action": R.string.string_message_id
						View view = toast.getView();
						view.setBackgroundResource(R.drawable.centerscroll3toast);
						//toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
						toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
			
						TextView text = (TextView) view.findViewById(android.R.id.message);
						Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						text.setTypeface(typeFace);
						text.setTextColor(Color.parseColor("#FFFFFF"));
						//text.setRotation(-45);
						text.setGravity(Gravity.CENTER);
						
						/*	ONLY WAY I COUL FIND TO CENTER TEXT IN CUSTOM TOAST FOR S3(4.4.2), S4(4.4.2) & AMAZON(5.3):
						 	if ((getResources().getDisplayMetrics().densityDpi==160) && (android.os.Build.VERSION.RELEASE.startsWith("5.3")))
							text.setPadding(165, 0, 0, 0);
						 */
						toast.show();
		  	  			
		  	  			edit.putString(ArrayOfPlayers.player[0], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 1) + ":Loses:" + (Loses + 0) + ":CritHitMB:" + (CritHitMB + 0) + ":MaxTurns:" + (MaxTurns));
		  	  			edit.commit();
			  	  	}
			  	  	if (win.equals("yes") && (critHitWithMB.equals("yes"))) {
			  	  		//NEW RECORD - WINS & CHMB
				  	  	Toast toast = Toast.makeText(Client2.this, "New Record!" + "\n" + "Wins, CH/MB", Toast.LENGTH_LONG);//INSTEAD OF "Choose Action": R.string.string_message_id
						View view = toast.getView();
						view.setBackgroundResource(R.drawable.centerscroll3toast);
						//toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
						toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
			
						TextView text = (TextView) view.findViewById(android.R.id.message);
						Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						text.setTypeface(typeFace);
						text.setTextColor(Color.parseColor("#FFFFFF"));
						//text.setRotation(-45);
						text.setGravity(Gravity.CENTER);
						
						/*	ONLY WAY I COUL FIND TO CENTER TEXT IN CUSTOM TOAST FOR S3(4.4.2), S4(4.4.2) & AMAZON(5.3):
						 	if ((getResources().getDisplayMetrics().densityDpi==160) && (android.os.Build.VERSION.RELEASE.startsWith("5.3")))
							text.setPadding(165, 0, 0, 0);
						 */
						toast.show();
			  	  		
				  		edit.putString(ArrayOfPlayers.player[0], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 1) + ":Loses:" + (Loses + 0) + ":CritHitMB:" + (CritHitMB + 1) + ":MaxTurns:" + (MaxTurns));
				  		edit.commit();
				  	}
			  	  	
			  	  	if (win.equals("no") && (critHitWithMB.equals("na"))) {
			  	  		//NO RECORDS
			  	  		edit.putString(ArrayOfPlayers.player[0], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 0) + ":Loses:" + (Loses + 1) + ":CritHitMB:" + (CritHitMB + 0) + ":MaxTurns:" + (MaxTurns));
			  	  		edit.commit();
			  	  	}
			  	  	if (win.equals("no") && (critHitWithMB.equals("yes"))) {
			  	  		//NEW RECORD - CHMB
				  	  	Toast toast = Toast.makeText(Client2.this, "New Record!" + "\n" + "CH/MB", Toast.LENGTH_LONG);//INSTEAD OF "Choose Action": R.string.string_message_id
						View view = toast.getView();
						view.setBackgroundResource(R.drawable.centerscroll3toast);
						//toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
						toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
			
						TextView text = (TextView) view.findViewById(android.R.id.message);
						Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						text.setTypeface(typeFace);
						text.setTextColor(Color.parseColor("#FFFFFF"));
						//text.setRotation(-45);
						text.setGravity(Gravity.CENTER);
						
						/*	ONLY WAY I COUL FIND TO CENTER TEXT IN CUSTOM TOAST FOR S3(4.4.2), S4(4.4.2) & AMAZON(5.3):
						 	if ((getResources().getDisplayMetrics().densityDpi==160) && (android.os.Build.VERSION.RELEASE.startsWith("5.3")))
							text.setPadding(165, 0, 0, 0);
						 */
						toast.show();
			  	  		
				  		edit.putString(ArrayOfPlayers.player[0], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 0) + ":Loses:" + (Loses + 1) + ":CritHitMB:" + (CritHitMB + 1) + ":MaxTurns:" + (MaxTurns));
				  		edit.commit();
				  	}
			  	}
			}
			
			if (id == 1) {//WAS: else
				
				//THIS WORKS, BUT THOUGHT BETTER TO SPECIFY IN CASE 'this.getExternalFilesDir(null)' DECIDES NOT TO WORK ON A SPECIFIC DEVICE.
				//File playerName = new File(this.getExternalFilesDir(null), ArrayOfPlayers.player[5] + ".txt");
				
				//playerName = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files", ArrayOfPlayers.player[1] + ".txt");
				
				SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
				
				String username = pref.getString(ArrayOfPlayers.player[1], "");
				
				
				String[] parts = username.split(":");
				
				String part1 = parts[0];//GamesPlayed
		  	  	String part2 = parts[1];//0
		  	  	String part3 = parts[2];//Wins
		  	  	String part4 = parts[3];//0
		  	  	String part5 = parts[4];//Loses
		  	  	String part6 = parts[5];//0
		  	  	String part7 = parts[6];//CritHitWithMB
				String part8 = parts[7];//0
				String part9 = parts[8];//MaxTurns
				String part10 = parts[9];//0
		  	  	
		  	  	Games = Integer.parseInt(part2);
		  	  	Wins = Integer.parseInt(part4);
		  	  	Loses = Integer.parseInt(part6);
		  	  	CritHitMB = Integer.parseInt(part8);
		  	  	MaxTurns = Integer.parseInt(part10);
		  	  	//IF U ADD NEW RECORD, THE GAME WILL CRASH BECAUSE IT DOESN'T EXIST YET ON DEVICE.
		  	  	//HAVE TO UNINSTALL/REINSTALL, BUT LOSE OLD RECORDS. NO FIX YET.
		  	  	
		  	  	SharedPreferences.Editor edit = pref.edit();
		  	  	
		  	  	if (ArrayOfTurn.turn[0] >= MaxTurns) {
		  	  		
		  	  		if (win.equals("yes") && (critHitWithMB.equals("na"))) {
		  	  			//NEW RECORD - TURNS & WINS
			  	  		Toast toast = Toast.makeText(Client2.this, "New Record!" + "\n" + "Turns, Wins", Toast.LENGTH_LONG);//INSTEAD OF "Choose Action": R.string.string_message_id
						View view = toast.getView();
						view.setBackgroundResource(R.drawable.centerscroll3toast);
						//toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
						toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
			
						TextView text = (TextView) view.findViewById(android.R.id.message);
						Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						text.setTypeface(typeFace);
						text.setTextColor(Color.parseColor("#FFFFFF"));
						//text.setRotation(-45);
						text.setGravity(Gravity.CENTER);
						
						/*	ONLY WAY I COUL FIND TO CENTER TEXT IN CUSTOM TOAST FOR S3(4.4.2), S4(4.4.2) & AMAZON(5.3):
						 	if ((getResources().getDisplayMetrics().densityDpi==160) && (android.os.Build.VERSION.RELEASE.startsWith("5.3")))
							text.setPadding(165, 0, 0, 0);
						 */
						toast.show();
		  	  			
		  	  			edit.putString(ArrayOfPlayers.player[1], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 1) + ":Loses:" + (Loses + 0) + ":CritHitMB:" + (CritHitMB + 0) + ":MaxTurns:" + (ArrayOfTurn.turn[0]));
		  	  			edit.commit();
			  	  	}
			  	  	if (win.equals("yes") && (critHitWithMB.equals("yes"))) {
			  	  		//NEW RECORD - TURNS & WINS & CHMB
				  	  	Toast toast = Toast.makeText(Client2.this, "New Record!" + "\n" + "Turns, Wins, CH/MB", Toast.LENGTH_LONG);//INSTEAD OF "Choose Action": R.string.string_message_id
						View view = toast.getView();
						view.setBackgroundResource(R.drawable.centerscroll3toast);
						//toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
						toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
			
						TextView text = (TextView) view.findViewById(android.R.id.message);
						Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						text.setTypeface(typeFace);
						text.setTextColor(Color.parseColor("#FFFFFF"));
						//text.setRotation(-45);
						text.setGravity(Gravity.CENTER);
						
						/*	ONLY WAY I COUL FIND TO CENTER TEXT IN CUSTOM TOAST FOR S3(4.4.2), S4(4.4.2) & AMAZON(5.3):
						 	if ((getResources().getDisplayMetrics().densityDpi==160) && (android.os.Build.VERSION.RELEASE.startsWith("5.3")))
							text.setPadding(165, 0, 0, 0);
						 */
						toast.show();
			  	  		
				  		edit.putString(ArrayOfPlayers.player[1], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 1) + ":Loses:" + (Loses + 0) + ":CritHitMB:" + (CritHitMB + 1) + ":MaxTurns:" + (ArrayOfTurn.turn[0]));
				  		edit.commit();
				  	}
			  	  	
			  	  	if (win.equals("no") && (critHitWithMB.equals("na"))) {
			  	  		//NEW RECORD - TURNS
				  	  	Toast toast = Toast.makeText(Client2.this, "New Record!" + "\n" + "Turns", Toast.LENGTH_LONG);//INSTEAD OF "Choose Action": R.string.string_message_id
						View view = toast.getView();
						view.setBackgroundResource(R.drawable.centerscroll3toast);
						//toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
						toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
			
						TextView text = (TextView) view.findViewById(android.R.id.message);
						Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						text.setTypeface(typeFace);
						text.setTextColor(Color.parseColor("#FFFFFF"));
						//text.setRotation(-45);
						text.setGravity(Gravity.CENTER);
						
						/*	ONLY WAY I COUL FIND TO CENTER TEXT IN CUSTOM TOAST FOR S3(4.4.2), S4(4.4.2) & AMAZON(5.3):
						 	if ((getResources().getDisplayMetrics().densityDpi==160) && (android.os.Build.VERSION.RELEASE.startsWith("5.3")))
							text.setPadding(165, 0, 0, 0);
						 */
						toast.show();
			  	  		
			  	  		edit.putString(ArrayOfPlayers.player[1], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 0) + ":Loses:" + (Loses + 1) + ":CritHitMB:" + (CritHitMB + 0) + ":MaxTurns:" + (ArrayOfTurn.turn[0]));
			  	  		edit.commit();
			  	  	}
			  	  	if (win.equals("no") && (critHitWithMB.equals("yes"))) {
			  	  		//NEW RECORD - TURNS & CHMB
				  	  	Toast toast = Toast.makeText(Client2.this, "New Record!" + "\n" + "Turns, CH/MB", Toast.LENGTH_LONG);//INSTEAD OF "Choose Action": R.string.string_message_id
						View view = toast.getView();
						view.setBackgroundResource(R.drawable.centerscroll3toast);
						//toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
						toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
			
						TextView text = (TextView) view.findViewById(android.R.id.message);
						Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						text.setTypeface(typeFace);
						text.setTextColor(Color.parseColor("#FFFFFF"));
						//text.setRotation(-45);
						text.setGravity(Gravity.CENTER);
						
						/*	ONLY WAY I COUL FIND TO CENTER TEXT IN CUSTOM TOAST FOR S3(4.4.2), S4(4.4.2) & AMAZON(5.3):
						 	if ((getResources().getDisplayMetrics().densityDpi==160) && (android.os.Build.VERSION.RELEASE.startsWith("5.3")))
							text.setPadding(165, 0, 0, 0);
						 */
						toast.show();
			  	  		
				  		edit.putString(ArrayOfPlayers.player[1], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 0) + ":Loses:" + (Loses + 1) + ":CritHitMB:" + (CritHitMB + 1) + ":MaxTurns:" + (ArrayOfTurn.turn[0]));
				  		edit.commit();
				  	}
		  	  	}
		  	  	
		  	  	if (ArrayOfTurn.turn[0] < MaxTurns) {
			  		
		  	  		if (win.equals("yes") && (critHitWithMB.equals("na"))) {
		  	  			//NEW RECORD - WINS
			  	  		Toast toast = Toast.makeText(Client2.this, "New Record!" + "\n" + "Wins", Toast.LENGTH_LONG);//INSTEAD OF "Choose Action": R.string.string_message_id
						View view = toast.getView();
						view.setBackgroundResource(R.drawable.centerscroll3toast);
						//toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
						toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
			
						TextView text = (TextView) view.findViewById(android.R.id.message);
						Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						text.setTypeface(typeFace);
						text.setTextColor(Color.parseColor("#FFFFFF"));
						//text.setRotation(-45);
						text.setGravity(Gravity.CENTER);
						
						/*	ONLY WAY I COUL FIND TO CENTER TEXT IN CUSTOM TOAST FOR S3(4.4.2), S4(4.4.2) & AMAZON(5.3):
						 	if ((getResources().getDisplayMetrics().densityDpi==160) && (android.os.Build.VERSION.RELEASE.startsWith("5.3")))
							text.setPadding(165, 0, 0, 0);
						 */
						toast.show();
		  	  			
		  	  			edit.putString(ArrayOfPlayers.player[1], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 1) + ":Loses:" + (Loses + 0) + ":CritHitMB:" + (CritHitMB + 0) + ":MaxTurns:" + (MaxTurns));
		  	  			edit.commit();
			  	  	}
			  	  	if (win.equals("yes") && (critHitWithMB.equals("yes"))) {
			  	  		//NEW RECORD - WINS & CHMB
				  	  	Toast toast = Toast.makeText(Client2.this, "New Record!" + "\n" + "Wins, CH/MB", Toast.LENGTH_LONG);//INSTEAD OF "Choose Action": R.string.string_message_id
						View view = toast.getView();
						view.setBackgroundResource(R.drawable.centerscroll3toast);
						//toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
						toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
			
						TextView text = (TextView) view.findViewById(android.R.id.message);
						Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						text.setTypeface(typeFace);
						text.setTextColor(Color.parseColor("#FFFFFF"));
						//text.setRotation(-45);
						text.setGravity(Gravity.CENTER);
						
						/*	ONLY WAY I COUL FIND TO CENTER TEXT IN CUSTOM TOAST FOR S3(4.4.2), S4(4.4.2) & AMAZON(5.3):
						 	if ((getResources().getDisplayMetrics().densityDpi==160) && (android.os.Build.VERSION.RELEASE.startsWith("5.3")))
							text.setPadding(165, 0, 0, 0);
						 */
						toast.show();
			  	  		
				  		edit.putString(ArrayOfPlayers.player[1], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 1) + ":Loses:" + (Loses + 0) + ":CritHitMB:" + (CritHitMB + 1) + ":MaxTurns:" + (MaxTurns));
				  		edit.commit();
				  	}
			  	  	
			  	  	if (win.equals("no") && (critHitWithMB.equals("na"))) {
			  	  		//NO RECORDS
			  	  		edit.putString(ArrayOfPlayers.player[1], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 0) + ":Loses:" + (Loses + 1) + ":CritHitMB:" + (CritHitMB + 0) + ":MaxTurns:" + (MaxTurns));
			  	  		edit.commit();
			  	  	}
			  	  	if (win.equals("no") && (critHitWithMB.equals("yes"))) {
			  	  		//NEW RECORD - CHMB
				  	  	Toast toast = Toast.makeText(Client2.this, "New Record!" + "\n" + "CH/MB", Toast.LENGTH_LONG);//INSTEAD OF "Choose Action": R.string.string_message_id
						View view = toast.getView();
						view.setBackgroundResource(R.drawable.centerscroll3toast);
						//toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
						toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
			
						TextView text = (TextView) view.findViewById(android.R.id.message);
						Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						text.setTypeface(typeFace);
						text.setTextColor(Color.parseColor("#FFFFFF"));
						//text.setRotation(-45);
						text.setGravity(Gravity.CENTER);
						
						/*	ONLY WAY I COUL FIND TO CENTER TEXT IN CUSTOM TOAST FOR S3(4.4.2), S4(4.4.2) & AMAZON(5.3):
						 	if ((getResources().getDisplayMetrics().densityDpi==160) && (android.os.Build.VERSION.RELEASE.startsWith("5.3")))
							text.setPadding(165, 0, 0, 0);
						 */
						toast.show();
			  	  		
				  		edit.putString(ArrayOfPlayers.player[1], "GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 0) + ":Loses:" + (Loses + 1) + ":CritHitMB:" + (CritHitMB + 1) + ":MaxTurns:" + (MaxTurns));
				  		edit.commit();
				  	}
			  	}
			}
			
		} catch (Exception e) {
			//Log.e("ReadWriteFile", "Unable to write to the TestFile.txt file.");
			//Toast.makeText(Client2.this, "Error -- Data not saved.", Toast.LENGTH_SHORT).show();
			
			Toast toast = Toast.makeText(Client2.this, "Error -- Data not saved.", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
  			View view = toast.getView();
  			view.setBackgroundResource(R.drawable.centerscroll3toast);
  			toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER

  			TextView text = (TextView) view.findViewById(android.R.id.message);
  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
  			text.setTypeface(typeFace);
  			text.setTextColor(Color.parseColor("#FFFFFF"));
  			//text.setRotation(-45);
  			text.setGravity(Gravity.CENTER);
  			
  			toast.show();
		}
		
		/*
		BufferedReader reader = null;
		   try {
		      reader = new BufferedReader(new FileReader(playerName));
		      String line;

		      while ((line = reader.readLine()) != null) {
		    	  
		    	  String[] parts = line.split(":");
		    	  String part1 = parts[0];
		    	  String part2 = parts[1];
		    	  String part3 = parts[2];
		    	  String part4 = parts[3];
		    	  String part5 = parts[4];
		    	  String part6 = parts[5];
		    	  
		    	  Games = Integer.parseInt(part2);
		    	  Wins = Integer.parseInt(part4);
		    	  Loses = Integer.parseInt(part6);
		    	  
		    	  
		    	  // Adds a line to the file
		    	  BufferedWriter writer = new BufferedWriter(new FileWriter(playerName, false));//FOR APPENd: true
		    	  //WAS:
		    	  //writer.write("GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 1) + ":Loses:" + (Games + 1));
		    	  
		    	  if (win.equals("yes")) {
		    		  writer.write("GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 1) + ":Loses:" + (Loses + 0));
		    		  writer.close();
		    	  }
		    	  if (win.equals("no")) {
		    		  writer.write("GamesPlayed:" + (Games + 1) + ":Wins:" + (Wins + 0) + ":Loses:" + (Loses + 1));
		    		  writer.close();
		    	  }
		      }
		      
		      reader.close();
		      
		   } catch (Exception e) {
			      Log.e("ReadWriteFile", "Unable to read the TestFile.txt file.");
			   }
	   */
		
		//if (playerName != null) {
		/*
		   BufferedReader reader = null;
		   try {
		      reader = new BufferedReader(new FileReader(playerName));
		      String line;

		      while ((line = reader.readLine()) != null) {
		    	  
		    	  //String[] parts = line.split(":");
		    	  //String part1 = parts[0];
		    	  //String part2 = parts[1];
		    	  //String part3 = parts[2];
		    	  //String part4 = parts[3];
		    	  
		    	  //Wins = Integer.parseInt(part2);
		    	  //Loses = Integer.parseInt(part4);
		    	  
		      }
		      
		      
		      //FOLLOWING dOES NOT GIVE: "/storage/emulated/0/Android/data/com.nedswebsite.ktog/files"
		      //SOMETHING LIKE:"data/data/com.nedswebsite.ktog/files"
		      
		      //PackageManager m = getPackageManager();
		      //String s = getPackageName();
		      //try {
		      //    PackageInfo p = m.getPackageInfo(s, 0);
		      //    s = p.applicationInfo.dataDir;
		      //} catch (PackageManager.NameNotFoundException e) {
		      //    Log.w("yourtag", "Error Package name not found ", e);
		      //}
		      
		      //Integer count = countFiles(new File(s), Integer.valueOf(0));
		      
		      
		      //File getFilesCount = new File("/Phone/Android/data/com.nedswebsite.ktog/files");
		      //File getFilesCount = new File(s);
		      File getFilesCount = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files");
		      File[] files = getFilesCount.listFiles();
		      
		      
		      if (files != null)
				for (int i = 0; i < files.length; i++) {

					count++;
					File file = files[i];
				}
		      
		      
		      //File[] files = playerName.listFiles();
		      //int numberOfFiles = files.length;
		      		      
		      
		      //Toast.makeText(Host.this, "Wins = " + Wins + " " + "Loses = " + Loses, Toast.LENGTH_SHORT).show();
		      //Toast.makeText(Host.this, "Number Of Files = " + numberOfFiles, Toast.LENGTH_SHORT).show();
		      Toast.makeText(Client2.this, "Number Of Files = " + count, Toast.LENGTH_SHORT).show();
		      //Toast.makeText(Host.this, "Filepath = " + s, Toast.LENGTH_SHORT).show();
		      //Toast.makeText(Host.this, "Number Of Files = " + count, Toast.LENGTH_SHORT).show();
		      
		      
		      reader.close();
		      */
		   //} catch (Exception e) {
		   //   Log.e("ReadWriteFile", "Unable to read the TestFile.txt file.");
		   //}
		//}
	}
	
	
	
	
	
	
	
	
	
	public void handleUncaughtException (Thread thread, Throwable e) {
		/*
	    e.printStackTrace(); // not all Android versions will print the stack trace automatically

	    Intent intent = new Intent ();
	    intent.setAction ("com.mydomain.SEND_LOG"); // see step 5.
	    intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
	    startActivity (intent);
	    */
		
		
		String str = "disconnected";
  		sendToHost(str);
		

	    System.exit(1); // kill off the crashed app
	}
	
	
	
	
	
	/*
	public void names() {		
		
		Toast.makeText(Client2.this, ArrayOfPlayers.player[5], Toast.LENGTH_SHORT).show();
		Toast.makeText(Client2.this, ArrayOfPlayers.player[0], Toast.LENGTH_SHORT).show();
		Toast.makeText(Client2.this, ArrayOfPlayers.player[1], Toast.LENGTH_SHORT).show();
	}
	*/
	/*
	public byte[] getBytesFromBitmap(Bitmap bitmap) {
	    ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    bitmap.compress(CompressFormat.JPEG, 0, stream);//WAS 70
	    return stream.toByteArray();
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
			Toast.makeText(Client2.this, "HOSTIP CONVERSION DID NOT WORK", Toast.LENGTH_SHORT).show();
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
			Toast.makeText(Client2.this, "HOSTIP CONVERSION DID NOT WORK", Toast.LENGTH_SHORT).show();
		}
		*/
		/*
		try {
			String ip = getIntent().getData().getQueryParameter("ip");
		    if (ip != null) {
		        
		    	
		        hostIP = ip;
		        
		    }
		} catch (Throwable t) {
			Toast.makeText(Client2.this, "HOSTIP CONVERSION DID NOT WORK", Toast.LENGTH_SHORT).show();
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
		    Toast.makeText(Client2.this, hostIP, Toast.LENGTH_SHORT).show();
			} 
		}catch (Throwable t) {
			Toast.makeText(Client2.this, "HOSTIP CONVERSION DID NOT WORK", Toast.LENGTH_SHORT).show();
		}
		*/
		/*
		try {
			Uri uri = Uri.parse("http://www.ktog.multiplayer.com/?ip=" + hostIP);
			//uri.getQueryParameter("ip");
			String value = uri.getQueryParameter("ip");
		    hostIP = value;
		    Toast.makeText(Client2.this, hostIP, Toast.LENGTH_SHORT).show();
		} catch (Throwable t) {
				Toast.makeText(Client2.this, "HOSTIP CONVERSION DID NOT WORK", Toast.LENGTH_SHORT).show();
		}
		*/
		/*
		try {// NEED THIS???????????? IN MANIFEST: android:pathPrefix="/"
			Intent intent = getIntent();
			Uri data = intent.getData();
			String ip = data.getQueryParameter("ip");
			hostIP = ip;
		    Toast.makeText(Client2.this, hostIP, Toast.LENGTH_SHORT).show();
		} catch (Throwable t) {
				Toast.makeText(Client2.this, "HOSTIP CONVERSION DID NOT WORK", Toast.LENGTH_SHORT).show();
		}
		*/
		
	}	
	
	// ONSTOP OR ONDESTROY???????
	@Override
	protected void onDestroy() {		
		
		Intent svc=new Intent(this, Badonk2SoundService.class);
		stopService(svc);
		
		if (socket != null) {
			try {
				socket.close();
				
				//stopService(svc);
				
				android.os.Process.killProcess(android.os.Process.myPid());
			    
			    super.onDestroy();
				
				//stopService(svc);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (socket0 != null) {
			try {
				
				socket0.close();
				
				//stopService(svc);
				
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
		
		AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this, R.style.customalertdialog);
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);

		//alert.setTitle("KtOG");
		alert.setMessage("Are you sure you want to exit?");

		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				
				
				String str = "disconnected";
		  		sendToHost(str);
				
				
				stopService(svc);
				
				dialog.dismiss();
				
				//hideSystemUI();				

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
				
				hideSystemUI();

				// hideNavigation();
			}
		});
		alert.show();

		// Toast.makeText(MainActivity2.this,"onBackPressed WORKING!!!!",
		// Toast.LENGTH_SHORT).show();
	}
	
	public void onResume() {
		
		hideSystemUI();
		
		super.onResume();		
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
		  	  	
		  	  	
		  	  	System.gc();
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
		  	  	
		  	  	
		  	  	System.gc();
			}
  		});
	}
	
	
	//@SuppressWarnings("deprecation")
	public void scrollAnimationLeftDown() {		
		
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
				
				
				System.gc();
	  	    }
  		});	
	}
	
	public void unfoldScrolls() {		
		
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
				
				
				System.gc();
	  	    }
  		});	
	}
	
	public void foldScrolls() {
		
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				playerCardStopFadeInFadeOut();
  	  	  		computerCardStopFadeInFadeOut();
				
				
				// Setting up scroll frame animation.
				ImageView img = (ImageView)findViewById(R.id.scrollanimation);
				img.setBackgroundResource(R.anim.scrollanimationdown);
				
				img.bringToFront();
			
				// Get the background, which has been compiled to an AnimationDrawable object.
				AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
							
				// Start the animation.
				frameAnimation.stop();
				frameAnimation.start();	
				
				
				System.gc();
	  	    }
  		});	
	}
	
	//@SuppressWarnings("deprecation")
	public void unfoldLeftScrollReverse() {		
		
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// Setting up scroll frame animation.
				ImageView img = (ImageView)findViewById(R.id.scrollanimation);
				img.setBackgroundResource(R.anim.scrollanimationleftupreverse);
			
				// Get the background, which has been compiled to an AnimationDrawable object.
				AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
							
				// Start the animation.
				frameAnimation.stop();
				frameAnimation.start();
				
				
				System.gc();
	  	    }
  		});	
	}
	
	public void scrollAnimationLeftUpNoRight() {		
		
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
				
				
				System.gc();
	  	    }
  		});	
	}
	
	public void scrollAnimationRightUpReverse() {
		
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// Setting up scroll frame animation.
				ImageView img = (ImageView)findViewById(R.id.scrollanimation);
				img.setBackgroundResource(R.anim.scrollanimationrightupreverse);
			
				// Get the background, which has been compiled to an AnimationDrawable object.
				AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
							
				// Start the animation.
				frameAnimation.stop();
				frameAnimation.start();
				
				
				System.gc();
	  	    }
  		});	
	}
	
	public void scrollAnimationLeftDownReverse() {
		
		// USING "runOnUiThread(new Runnable() {}" TO SEE IF IT WORKS BETTER THAN NOT USING IT.
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// Setting up scroll frame animation.
				ImageView img = (ImageView)findViewById(R.id.scrollanimation);
				img.setBackgroundResource(R.anim.scrollanimationleftdownreverse);
			
				// Get the background, which has been compiled to an AnimationDrawable object.
				AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
							
				// Start the animation.
				frameAnimation.stop();
				frameAnimation.start();
				
				
				System.gc();
	  	    }
  		});	
	}
	
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
				
				
				System.gc();
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
				  	  	
				  	  	
				  	  	System.gc();
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
				  	  	
				  	  	
				  	  	System.gc();
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
		  	  	  		
		  	  	  		
		  	  	  		System.gc();
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
				
				img1.setImageDrawable(null);
				
				
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
				  	  	
				  	  	
				  	  	System.gc();
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
				
				img1.setImageDrawable(null);
				
				
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
				
				Animation a = AnimationUtils.loadAnimation(Client2.this, R.anim.textscaletobig);					  	  	  	
			  	  	
		  	  	TextView criticalHitGraphic = (TextView)findViewById(R.id.textviewspellgraphicextrasmall);
		  	  	
		  	  	criticalHitGraphic.setVisibility(View.VISIBLE);
			  	criticalHitGraphic.bringToFront();
		  	  	
		  	  	criticalHitGraphic.setTypeface(typeFace);
		  	  	criticalHitGraphic.setText(" Critical Hit");//WAS:"Critical     Hit"
		  	  	
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
				  	  	criticalHitGraphic.setText(" Critical Hit");//WAS:"Critical     Hit"
			  	  	  	
				  	  	criticalHitGraphic.clearAnimation();
				  	  	criticalHitGraphic.startAnimation(a);
				  	  	
				  	  	
				  	  	System.gc();
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
				
				img1.setImageDrawable(null);
				
				
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
				  	  	
				  	  	
				  	  	System.gc();
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
				
				img1.setImageDrawable(null);
				
				
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
				  	  	
				  	  	
				  	  	System.gc();
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
		final Animation shake = AnimationUtils.loadAnimation(this, R.anim.wobblesixsided);
		img.setAnimation(shake);
	}
	
	public void sixSidedWobbleStop() {
		final ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
		
		img.clearAnimation();
		
		
		img.setImageDrawable(null);


		System.gc();
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
	  	  		thread.interrupt();
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
				
				img1.setImageDrawable(null);
				
				
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
				
				img1.setImageDrawable(null);
				
				
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
				
				img1.setImageDrawable(null);
				
				
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
				
				img1.setImageDrawable(null);
				
				
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
				
				img1.setImageDrawable(null);
				
				
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
				
				img1.setImageDrawable(null);
				
				
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
	  	  		thread.interrupt();
		  	  	//MediaPlayerWrapper.play(MainActivity2.this, R.raw.dierolling3b);
		  	  	
		  	  	
		  	  	// Animation is just 1 slide so user can see title.
		  	  	frameAnimation.stop();
		  	  	frameAnimation.start();
	  	    }
  		});	
	}
	
	public void twentySidedWobbleStart() {
		final ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
		final Animation shake = AnimationUtils.loadAnimation(this, R.anim.wobbletwentysided);
		img.setAnimation(shake);
	}
	
	public void twentySidedWobbleStop() {
		final ImageView img = (ImageView)findViewById(R.id.twentysidedanimation);
		
		img.clearAnimation();
		
		
		img.setImageDrawable(null);
		
		
		System.gc();
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
			        	  
			        	  Toast.makeText(Host.this, clientWorkers.get(counter), Toast.LENGTH_SHORT).show();		               		
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
						  		  			
						  		  			//names();
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
	
	public void finishInitiative() {		
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
		
				final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
				//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
				
				Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				centerscrolltext.setTypeface(typeFace);
				
				//final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
				
				// CRASHES IF USE THIS:
				//img.setVisibility(View.INVISIBLE);
				
		  		// Use a blank drawable to hide the imageview animation:
		  		ImageView img = (ImageView)findViewById(R.id.sixsidedanimation);
		  		img.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
				img.setImageResource(R.drawable.sixsixrightleftrotateblank);
				
				img.setImageDrawable(null);
		  		
				
				// Re-enables ability to use srollbar:
				centerscrolltext.bringToFront();												
				/*
				centerscrolltext.setVisibility(View.VISIBLE);													
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> Let the battle begin...");		
				*/
				
				
				//test();
				
				
				
				
				getLocalIpAddress();
				
				String str = "localIPAddress :" + iPClient;
          		sendToHost(str);
				
				
				
          		MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
				
				myInitiativeTransition();
				
				
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

		  	  			MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
		  	  		}
		  	  	}, 500);
				
			  	  		
		  	  	final Handler h2 = new Handler();
		  		h2.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  	  	public void run() {
		  	  			
		  	  			ArrayOfHitPoints.hitpoints[5] = 20;//20
		  	  			
		  	  			if (numberOfPlayers == 3) {
		  	  				
		  	  				ArrayOfHitPoints.hitpoints[1] = 20;//20
		  	  			}
		  	  			
		  	  			
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
				  	  	
				  	  	if (numberOfPlayers == 3) {
				  	  		
					  	  	TextView player3textview = (TextView) findViewById(R.id.player3);
					  	  	player3textview.setTypeface(typeFace);
					  	  	player3textview.append(ArrayOfPlayers.player[thirdsubscript]);
					  	  	player3textview.setVisibility(View.VISIBLE);		  	  	
					  	  	
					  	  	TextView hitpoints3textview = (TextView) findViewById(R.id.hitpointsplayer3);
					  	  	hitpoints3textview.setTypeface(typeFace);
					  	  	String hitpoints3String = Integer.toString(ArrayOfHitPoints.hitpoints[thirdsubscript]);
					  	  	hitpoints3textview.append(hitpoints3String);
					  	  	hitpoints3textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	TextView blessplayer3textview = (TextView) findViewById(R.id.blessplayer3);
					  	  	blessplayer3textview.setTypeface(typeFace);
					  	  	String blessplayer3String = Integer.toString(blessSpell[thirdsubscript]);
					  	  	blessplayer3textview.append(blessplayer3String);
					  	  	blessplayer3textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	TextView cureplayer3textview = (TextView) findViewById(R.id.cureplayer3);
					  	  	cureplayer3textview.setTypeface(typeFace);
					  	  	String cureplayer3String = Integer.toString(cureSpell[thirdsubscript]);
					  	  	cureplayer3textview.append(cureplayer3String);
					  	  	cureplayer3textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	TextView dodgeplayer3textview = (TextView) findViewById(R.id.dodgeplayer3);
					  	  	dodgeplayer3textview.setTypeface(typeFace);
					  	  	String dodgeplayer3String = Integer.toString(dodgeBlowSpell[thirdsubscript]);
					  	  	dodgeplayer3textview.append(dodgeplayer3String);
					  	  	dodgeplayer3textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	TextView mightyblowplayer3textview = (TextView) findViewById(R.id.mightyblowplayer3);
					  	  	mightyblowplayer3textview.setTypeface(typeFace);
					  	  	String mightyblowplayer3String = Integer.toString(mightyBlowSpell[thirdsubscript]);
					  	  	mightyblowplayer3textview.append(mightyblowplayer3String);
					  	  	mightyblowplayer3textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	TextView hasteplayer3textview = (TextView) findViewById(R.id.hasteplayer3);
					  	  	hasteplayer3textview.setTypeface(typeFace);
					  	  	String hasteplayer3String = Integer.toString(hasteSpell[thirdsubscript]);
					  	  	hasteplayer3textview.append(hasteplayer3String);
					  	  	hasteplayer3textview.setVisibility(View.VISIBLE);		  	  		
				  	  	}
				  	  	/*
				  	  	else if (numberOfPlayers == 4) {
				  	  		
					  	  	TextView player3textview = (TextView) findViewById(R.id.player3);
					  	  	player3textview.setTypeface(typeFace);
					  	  	player3textview.append(ArrayOfPlayers.player[thirdsubscript]);
					  	  	player3textview.setVisibility(View.VISIBLE);		  	  	
					  	  	
					  	  	TextView hitpoints3textview = (TextView) findViewById(R.id.hitpointsplayer3);
					  	  	hitpoints3textview.setTypeface(typeFace);
					  	  	String hitpoints3String = Integer.toString(ArrayOfHitPoints.hitpoints[thirdsubscript]);
					  	  	hitpoints3textview.append(hitpoints3String);
					  	  	hitpoints3textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	TextView blessplayer3textview = (TextView) findViewById(R.id.blessplayer3);
					  	  	blessplayer3textview.setTypeface(typeFace);
					  	  	String blessplayer3String = Integer.toString(blessSpell[thirdsubscript]);
					  	  	blessplayer3textview.append(blessplayer3String);
					  	  	blessplayer3textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	TextView cureplayer3textview = (TextView) findViewById(R.id.cureplayer3);
					  	  	cureplayer3textview.setTypeface(typeFace);
					  	  	String cureplayer3String = Integer.toString(cureSpell[thirdsubscript]);
					  	  	cureplayer3textview.append(cureplayer3String);
					  	  	cureplayer3textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	TextView dodgeplayer3textview = (TextView) findViewById(R.id.dodgeplayer3);
					  	  	dodgeplayer3textview.setTypeface(typeFace);
					  	  	String dodgeplayer3String = Integer.toString(dodgeBlowSpell[thirdsubscript]);
					  	  	dodgeplayer3textview.append(dodgeplayer3String);
					  	  	dodgeplayer3textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	TextView mightyblowplayer3textview = (TextView) findViewById(R.id.mightyblowplayer3);
					  	  	mightyblowplayer3textview.setTypeface(typeFace);
					  	  	String mightyblowplayer3String = Integer.toString(mightyBlowSpell[thirdsubscript]);
					  	  	mightyblowplayer3textview.append(mightyblowplayer3String);
					  	  	mightyblowplayer3textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	TextView hasteplayer3textview = (TextView) findViewById(R.id.hasteplayer3);
					  	  	hasteplayer3textview.setTypeface(typeFace);
					  	  	String hasteplayer3String = Integer.toString(hasteSpell[thirdsubscript]);
					  	  	hasteplayer3textview.append(hasteplayer3String);
					  	  	hasteplayer3textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	
					  	  	TextView player4textview = (TextView) findViewById(R.id.player4);
					  	  	player4textview.setTypeface(typeFace);
					  	  	player4textview.append(ArrayOfPlayers.player[fourthsubscript]);
					  	  	player4textview.setVisibility(View.VISIBLE);		  	  	
					  	  	
					  	  	TextView hitpoints4textview = (TextView) findViewById(R.id.hitpointsplayer4);
					  	  	hitpoints4textview.setTypeface(typeFace);
					  	  	String hitpoints4String = Integer.toString(ArrayOfHitPoints.hitpoints[fourthsubscript]);
					  	  	hitpoints4textview.append(hitpoints4String);
					  	  	hitpoints4textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	TextView blessplayer4textview = (TextView) findViewById(R.id.blessplayer4);
					  	  	blessplayer4textview.setTypeface(typeFace);
					  	  	String blessplayer4String = Integer.toString(blessSpell[fourthsubscript]);
					  	  	blessplayer4textview.append(blessplayer4String);
					  	  	blessplayer4textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	TextView cureplayer4textview = (TextView) findViewById(R.id.cureplayer4);
					  	  	cureplayer4textview.setTypeface(typeFace);
					  	  	String cureplayer4String = Integer.toString(cureSpell[fourthsubscript]);
					  	  	cureplayer4textview.append(cureplayer4String);
					  	  	cureplayer4textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	TextView dodgeplayer4textview = (TextView) findViewById(R.id.dodgeplayer4);
					  	  	dodgeplayer4textview.setTypeface(typeFace);
					  	  	String dodgeplayer4String = Integer.toString(dodgeBlowSpell[fourthsubscript]);
					  	  	dodgeplayer4textview.append(dodgeplayer4String);
					  	  	dodgeplayer4textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	TextView mightyblowplayer4textview = (TextView) findViewById(R.id.mightyblowplayer4);
					  	  	mightyblowplayer4textview.setTypeface(typeFace);
					  	  	String mightyblowplayer4String = Integer.toString(mightyBlowSpell[fourthsubscript]);
					  	  	mightyblowplayer4textview.append(mightyblowplayer4String);
					  	  	mightyblowplayer4textview.setVisibility(View.VISIBLE);
					  	  	
					  	  	TextView hasteplayer4textview = (TextView) findViewById(R.id.hasteplayer4);
					  	  	hasteplayer4textview.setTypeface(typeFace);
					  	  	String hasteplayer4String = Integer.toString(hasteSpell[fourthsubscript]);
					  	  	hasteplayer4textview.append(hasteplayer4String);
					  	  	hasteplayer4textview.setVisibility(View.VISIBLE);
				  	  	} 	  	
			  	  		*/
			  	  		
			  	  		//titlerulestext.bringToFront();
				  	  	
				  	  	
				  	  	
				  	  	final Handler h3 = new Handler();
			  	  	  	h3.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {

			  	  	  			MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
			  	  	  		}
			  	  		}, 9200);
				  	  	
				  	  	
				  	  	
				  	  	forChat();
			  	  		
		  	  			  	  			
			  	  		final Handler h4 = new Handler();
				  	  	h4.postDelayed(new Runnable() {
			
				  	  		@Override
				  	  		public void run() {
				  	  			
				  	  			MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
				  	  			
				  	  			//titlerulestext.setVisibility(View.INVISIBLE);
				  	  			summaryTableLayout.setVisibility(View.INVISIBLE);		  	  			
					  	  		
				  	  	  		lineInSummaryTableLayout.setVisibility(View.INVISIBLE);
				  	  	  		
				  	  	  		//summaryTableLayout.removeAllViews();
				  	  			
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
				  	  			
				  	  			//avatarTest();
				  	  			
				  	  			/*
				  	  			// Calls method from another class:
					  	  		Engine  engine = new Engine();
					  	  		Engine.gameEngine();
					  	  		*/
					  	  		
				  	  					  	  			
				  	  			//gameEngine(null, gameOn, gameOn);
				  	  			//gameEngine();
				  	  			
				  	  			
				  	  			
				  	  			//preventinitiativediefromleaking.equals("on");
				  	  			
				  	  			
				  	  			//Thread myThread = new Thread(myRunnable);
				  	  			//myThread.start();
					  	  		
				  	  		}
				  	  	}, 9500); // FINAGELED!	  	  			
		  	  	  	}				//WAS: 10325
		  	  	}, 338); // SHOULD BE AT LEAST 675? WAS 525 		  	    	  	  			  	  			
		  					//WAS: 675
	  			//Toast.makeText(MainActivity2.this,"isinitiativestarted = " +  isinitiativestarted + " aretheredoubles = " + aretheredoubles, Toast.LENGTH_SHORT).show();  	 	
	  	
			}
  		});
	}
	
	
	
	public void getLocalIpAddress(){
		/*   
		try {
		       for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();  
		       en.hasMoreElements();) {
		       NetworkInterface intf = en.nextElement();
		           for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
		           InetAddress inetAddress = enumIpAddr.nextElement();
		                if (!inetAddress.isLoopbackAddress()) {		                	
		                	
		                	
		                	String hostIPFull = inetAddress.getHostAddress();//WAS String
		                	//Toast.makeText(MainActivity1.this, hostIP, Toast.LENGTH_SHORT).show();
		                	
		                	
		                	
		                	hostIP = hostIPFull.split("%")[0];
		                	Toast.makeText(MainActivity1.this, hostIP, Toast.LENGTH_SHORT).show();
		                	//return hostIP;
                            
                            
		                	
		                	return inetAddress.getHostAddress().toString();
		                }
		           }
		       }
		       } catch (Exception ex) {
		          //Log.e("IP Address", ex.toString());
		      }
		      return null;
		      */
		boolean WIFI = false;

		boolean MOBILE = false;

		ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo[] networkInfo = CM.getAllNetworkInfo();

		for (NetworkInfo netInfo : networkInfo) {

			if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))

				if (netInfo.isConnected())

					WIFI = true;

			if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))

				if (netInfo.isConnected())

					MOBILE = true;
		}

		if (WIFI == true) {
			iPClient = GetDeviceipWiFiData();
		}

		if (MOBILE == true) {

			iPClient = GetDeviceipMobileData();
		}
	}
	
	public String GetDeviceipMobileData(){
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface networkinterface = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = networkinterface
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception ex) {
			// Log.e("Current IP", ex.toString());
		}
		return null;
	}

	public String GetDeviceipWiFiData() {//NEEDS:<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />

		WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);

		@SuppressWarnings("deprecation")
		// THERE ARE 3 DIFFERENT FORMATTER CLASSES
		String ip = Formatter.formatIpAddress(wm.getConnectionInfo()
				.getIpAddress());

		return ip;
	}
	
	
	public void forChat() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
				chatBlankButton.bringToFront();
			}
  		});		
	}
	
	public void forScrollTitleChat() {

		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
				// Re-enables ability to use srollbar:
				centerscrolltext.bringToFront();

				ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				titleBlankButton.bringToFront();

				ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
				chatBlankButton.bringToFront();
			}
		});
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
	public void avatarTest() {//DELETE IF NOT USED!!!!!!!!!!!!!

		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				Toast.makeText(Client2.this, "5 = " + ArrayOfAvatars.avatar[5], Toast.LENGTH_SHORT).show();	
				Toast.makeText(Client2.this, "0 = " + ArrayOfAvatars.avatar[0], Toast.LENGTH_SHORT).show();
				Toast.makeText(Client2.this, "1 = " + ArrayOfAvatars.avatar[1], Toast.LENGTH_SHORT).show();
				
				Toast.makeText(Client2.this, "ID =  " + id, Toast.LENGTH_SHORT).show();
			}
		});

	}
	*/
	/*
	public void hpTest5() {//DELETE IF NOT USED!!!!!!!!!!!!!

		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				Toast.makeText(Client2.this, "HOST", Toast.LENGTH_SHORT).show();
				
				Toast.makeText(Client2.this, "players fighting = " + playersFighting, Toast.LENGTH_SHORT).show();
			}
		});
	}
	public void hpTest0() {//DELETE IF NOT USED!!!!!!!!!!!!!

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
					
				Toast.makeText(Client2.this, "AAAA", Toast.LENGTH_SHORT).show();
				
				Toast.makeText(Client2.this, "players fighting = " + playersFighting, Toast.LENGTH_SHORT).show();
			}
		});
	}
	public void hpTest1() {//DELETE IF NOT USED!!!!!!!!!!!!!

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				Toast.makeText(Client2.this, "BBBB", Toast.LENGTH_SHORT).show();
				
				Toast.makeText(Client2.this, "players fighting = " + playersFighting, Toast.LENGTH_SHORT).show();
			}
		});
	}
	*/
	
	/*
	public void playersFightingTest() {//DELETE IF NOT USED!!!!!!!!!!!!!

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				Toast.makeText(Client2.this, playersFighting, Toast.LENGTH_SHORT).show();
				Toast.makeText(Client2.this, playersFighting, Toast.LENGTH_SHORT).show();
				Toast.makeText(Client2.this, playersFighting, Toast.LENGTH_SHORT).show();
			}
		});
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
	
	
	public void gameEngine2Player() {
		
		issixsidedrolledforinitiative = "yes";
		
		aretheredoubles = "no";
		
		
		playersFighting = "fiveVsZero";
		
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
		
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				
				TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
				playerNameTextView.setTypeface(typeFace);		
				playerNameTextView.setText(ArrayOfPlayers.player[5]);
				
				//ArrayOfHitPoints.hitpoints[5] = 20;//20		
				final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
				playerHitPointsTextView.setTypeface(typeFace);
				playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
				
				//ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
				//computerAvatar.setVisibility(View.VISIBLE);
				
				set2PlayerAvatar();//SETS AVATAR FOR [5]
				
				
				scrollAnimationLeftUpNoRight();			
			}
		});
	}
	
	
	public void set2PlayerAvatar() {//SETS AVATAR FOR [5]
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
				ImageView crossedswords2 = (ImageView) findViewById(R.id.imageviewavatarleft2);
				ImageView stonedead2 = (ImageView) findViewById(R.id.imageviewavatarleft3);
				ImageView customImage = (ImageView) findViewById(R.id.imageviewavatarleft4);
				
				
				if (ArrayOfAvatars.avatar[5].equals("custom")){
					crossedswords2.setVisibility(View.INVISIBLE);
					crossedswords2.setImageDrawable(null);
					stonedead2.setVisibility(View.INVISIBLE);
					stonedead2.setImageDrawable(null);
					computerAvatar.setVisibility(View.INVISIBLE);
					computerAvatar.setImageDrawable(null);
					
					
					File imgFile = new  File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files/avatar5");	//WAS .png
																												//WAS: /storage/sdcard0/avatar5

					if(imgFile.exists()){

					    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

					    //ImageView myImage = (ImageView) findViewById(R.id.imageviewavatarleft4);

					    customImage.setImageBitmap(myBitmap);//WAS myImage
					    
					    customImage.setVisibility(View.VISIBLE);//WAS myImage
					}
					else {
						//USE COMPUTER
						crossedswords2.setVisibility(View.INVISIBLE);
						crossedswords2.setImageDrawable(null);
						stonedead2.setVisibility(View.INVISIBLE);
						stonedead2.setImageDrawable(null);
						customImage.setVisibility(View.INVISIBLE);
						customImage.setImageDrawable(null);
						
						computerAvatar.setVisibility(View.VISIBLE);
					}
				}
				else if (ArrayOfAvatars.avatar[5].equals("computer")){
					crossedswords2.setVisibility(View.INVISIBLE);
					crossedswords2.setImageDrawable(null);
					stonedead2.setVisibility(View.INVISIBLE);
					stonedead2.setImageDrawable(null);
					customImage.setVisibility(View.INVISIBLE);
					customImage.setImageDrawable(null);
					
					computerAvatar.setVisibility(View.VISIBLE);
				}
				else if (ArrayOfAvatars.avatar[5].equals("crossedswords")){
					computerAvatar.setVisibility(View.INVISIBLE);
					computerAvatar.setImageDrawable(null);
					stonedead2.setVisibility(View.INVISIBLE);
					stonedead2.setImageDrawable(null);
					customImage.setVisibility(View.INVISIBLE);
					customImage.setImageDrawable(null);
					
					crossedswords2.setVisibility(View.VISIBLE);
				}
				else if (ArrayOfAvatars.avatar[5].equals("stonedead")){
					crossedswords2.setVisibility(View.INVISIBLE);
					crossedswords2.setImageDrawable(null);
					computerAvatar.setVisibility(View.INVISIBLE);
					computerAvatar.setImageDrawable(null);
					customImage.setVisibility(View.INVISIBLE);
					customImage.setImageDrawable(null);
					
					stonedead2.setVisibility(View.VISIBLE);
				}
				
				/*CAN'T USE FOLLOWING BECAUSE UNLIKE imageviewavatarright, I HAVE 4 DIFFERENT VIEWS ON LEFT
				if (ArrayOfAvatars.avatar[5].equals("custom")){
	  	  	    	
		  	  	    File imgFile = new  File("/storage/sdcard0/avatar0");//WAS .png

					if(imgFile.exists()){

					    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

					    ImageView myImage = (ImageView) findViewById(R.id.imageviewavatarright);

					    myImage.setImageBitmap(myBitmap);
					}
	  			}
	  	  	    else if (ArrayOfAvatars.avatar[5].equals("computer")){
	  	  	    	
	  				ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
	  				clientAvatar.setBackgroundResource(R.drawable.computer);
	  			}
	  			else if (ArrayOfAvatars.avatar[5].equals("crossedswords")){
	  				
	  				ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
	  				clientAvatar.setBackgroundResource(R.drawable.crossedswords2);
	  			}
	  			else if (ArrayOfAvatars.avatar[5].equals("stonedead")){
	  				
	  				ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
	  				clientAvatar.setBackgroundResource(R.drawable.stonedead2);
	  			}
	  			*/
			}
		});
	}
	
	public void setPlayerAvatarFor1Left() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
				ImageView crossedswords2 = (ImageView) findViewById(R.id.imageviewavatarleft2);
				ImageView stonedead2 = (ImageView) findViewById(R.id.imageviewavatarleft3);
				ImageView customImage = (ImageView) findViewById(R.id.imageviewavatarleft4);
				
				
				if (ArrayOfAvatars.avatar[1].equals("custom")){
					crossedswords2.setVisibility(View.INVISIBLE);
					crossedswords2.setImageDrawable(null);
					stonedead2.setVisibility(View.INVISIBLE);
					stonedead2.setImageDrawable(null);
					computerAvatar.setVisibility(View.INVISIBLE);
					computerAvatar.setImageDrawable(null);
					
					
					File imgFile = new  File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files/avatar1");	//WAS .png
																												//WAS: /storage/sdcard0/avatar1

					if(imgFile.exists()){

					    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

					    //ImageView myImage = (ImageView) findViewById(R.id.imageviewavatarleft4);

					    customImage.setImageBitmap(myBitmap);
					    
					    customImage.setVisibility(View.VISIBLE);
					}
					else {
						//USE COMPUTER
						crossedswords2.setVisibility(View.INVISIBLE);
						crossedswords2.setImageDrawable(null);
						stonedead2.setVisibility(View.INVISIBLE);
						stonedead2.setImageDrawable(null);
						customImage.setVisibility(View.INVISIBLE);
						customImage.setImageDrawable(null);
						
						computerAvatar.setVisibility(View.VISIBLE);
					}
				}
				else if (ArrayOfAvatars.avatar[1].equals("computer")){
					crossedswords2.setVisibility(View.INVISIBLE);
					crossedswords2.setImageDrawable(null);
					stonedead2.setVisibility(View.INVISIBLE);
					stonedead2.setImageDrawable(null);
					customImage.setVisibility(View.INVISIBLE);
					customImage.setImageDrawable(null);
					
					computerAvatar.setVisibility(View.VISIBLE);
				}
				else if (ArrayOfAvatars.avatar[1].equals("crossedswords")){
					computerAvatar.setVisibility(View.INVISIBLE);
					computerAvatar.setImageDrawable(null);
					stonedead2.setVisibility(View.INVISIBLE);
					stonedead2.setImageDrawable(null);
					customImage.setVisibility(View.INVISIBLE);
					customImage.setImageDrawable(null);
					
					crossedswords2.setVisibility(View.VISIBLE);
				}
				else if (ArrayOfAvatars.avatar[1].equals("stonedead")){
					crossedswords2.setVisibility(View.INVISIBLE);
					crossedswords2.setImageDrawable(null);
					computerAvatar.setVisibility(View.INVISIBLE);
					computerAvatar.setImageDrawable(null);
					customImage.setVisibility(View.INVISIBLE);
					customImage.setImageDrawable(null);
					
					stonedead2.setVisibility(View.VISIBLE);
				}
			}
		});
	}
	
	public void setPlayerAvatarFor0Left() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				//avatarTest();
				
				ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
				ImageView crossedswords2 = (ImageView) findViewById(R.id.imageviewavatarleft2);
				ImageView stonedead2 = (ImageView) findViewById(R.id.imageviewavatarleft3);
				ImageView customImage = (ImageView) findViewById(R.id.imageviewavatarleft4);
				
				
				if (ArrayOfAvatars.avatar[0].equals("custom")){
					crossedswords2.setVisibility(View.INVISIBLE);
					crossedswords2.setImageDrawable(null);
					stonedead2.setVisibility(View.INVISIBLE);
					stonedead2.setImageDrawable(null);
					computerAvatar.setVisibility(View.INVISIBLE);
					computerAvatar.setImageDrawable(null);
					
					
					File imgFile = new  File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files/avatar0");	//WAS .png
																												//WAS: /storage/sdcard0/avatar0

					if(imgFile.exists()){

					    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

					    //ImageView myImage = (ImageView) findViewById(R.id.imageviewavatarleft4);

					    customImage.setImageBitmap(myBitmap);
					    
					    customImage.setVisibility(View.VISIBLE);
					}
					else {
						//USE COMPUTER
						crossedswords2.setVisibility(View.INVISIBLE);
						crossedswords2.setImageDrawable(null);
						stonedead2.setVisibility(View.INVISIBLE);
						stonedead2.setImageDrawable(null);
						customImage.setVisibility(View.INVISIBLE);
						customImage.setImageDrawable(null);
						
						computerAvatar.setVisibility(View.VISIBLE);
					}
				}
				else if (ArrayOfAvatars.avatar[0].equals("computer")){
					crossedswords2.setVisibility(View.INVISIBLE);
					crossedswords2.setImageDrawable(null);
					stonedead2.setVisibility(View.INVISIBLE);
					stonedead2.setImageDrawable(null);
					customImage.setVisibility(View.INVISIBLE);
					customImage.setImageDrawable(null);
					
					computerAvatar.setVisibility(View.VISIBLE);
				}
				else if (ArrayOfAvatars.avatar[0].equals("crossedswords")){
					computerAvatar.setVisibility(View.INVISIBLE);
					computerAvatar.setImageDrawable(null);
					stonedead2.setVisibility(View.INVISIBLE);
					stonedead2.setImageDrawable(null);
					customImage.setVisibility(View.INVISIBLE);
					customImage.setImageDrawable(null);
					
					
					crossedswords2.setVisibility(View.VISIBLE);
					
					//crossedswords2.bringToFront();
					
					//test();
				}
				else if (ArrayOfAvatars.avatar[0].equals("stonedead")){
					crossedswords2.setVisibility(View.INVISIBLE);
					crossedswords2.setImageDrawable(null);
					computerAvatar.setVisibility(View.INVISIBLE);
					computerAvatar.setImageDrawable(null);
					customImage.setVisibility(View.INVISIBLE);
					customImage.setImageDrawable(null);
					
					stonedead2.setVisibility(View.VISIBLE);
				}
				
				//test();
			}
		});
	}
	
	
	public void gameEngineMultiPlayer() {
		
		issixsidedrolledforinitiative = "yes";
		
		aretheredoubles = "no";		
	}
	
	
	public void chooseOpponent() {
		
		if (numberOfPlayers == 3) {
			
			if (id == 0) {
		
				final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);		
				
				runOnUiThread(new Runnable() {
					
		  	  	    @Override
		  	  	    public void run() {
		  	  	    	
			  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
			  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
			  			
			  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
			  			centerscrolltext.setTypeface(typeFace);
			  			
			  			
			  			computerCardStartFadeInFadeOut();
			  			
			  			
			  			/*
			  			centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " is choosing player to attack...");									
						*/
						
						
	  	  	  				
						String str = "> " + ArrayOfPlayers.player[0] + " is choosing player to attack...";
						sendToHost(str);
	  	  	  			
		  	  	    					
						
						final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {	  	  				
			  	  	  					
	  	  	  					final String[] items = {ArrayOfPlayers.player[5], ArrayOfPlayers.player[1]}; 	  	  			
			  	  	  		
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
			  			  		ContextThemeWrapper cw = new ContextThemeWrapper(Client2.this, R.layout.avatar_adapter);
			  			  		AlertDialog.Builder builder = new AlertDialog.Builder(cw, R.style.customalertdialog);		  			  		
			  			  		
			  		  			
			  		  			builder.setCancelable(false);	  			
			  		  			
			  		  			//builder.setTitle("Choose Player To Attack");
			  		  			
				  		  		Toast toast = Toast.makeText(Client2.this, "Choose Player To Attack", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
	        		  			View view = toast.getView();
	        		  			view.setBackgroundResource(R.drawable.centerscroll3toast);
	        		  			toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
	
	        		  			TextView text = (TextView) view.findViewById(android.R.id.message);
	        		  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	        		  			text.setTypeface(typeFace);
	        		  			text.setTextColor(Color.parseColor("#FFFFFF"));
	        		  			//text.setRotation(-45);
	        		  			text.setGravity(Gravity.CENTER);
	        		  			
	        		  			toast.show();
			  		  			
			  		  			
			  					builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			  						@Override
			  						public void onCancel(DialogInterface dialog) {		  							
			  							
			  							chooseOpponent();
			  						}
			  					});
			  		  			
			  					
			  		            builder.setAdapter(adapter,
			  		                    new DialogInterface.OnClickListener() {
			  		                        @Override
			  		                        public void onClick(final DialogInterface dialog, int item) {				  		                        	
			  		                        	
			  		                        	if (item == 0 && playerDeadYet[5].equals("no")) {
			  		                        		
			  		                        		if (zeroAttackingFirst.equals("yes")) {			  		                        			
			  		                        			
			  		                        			playerNumberAttacked = 5;
			  		                        			
//playerAttackingTest();
			  		                        			reveal5onleftclientattacking();
			  		                        			
			  		                        			
			  		                        			String str = "0FirstChooses5";
				  		                        		sendToHost(str);          			
			  		                        			
			  		                        			
			  		                        			playersFighting = "zeroVsFive";//SAME AS WHEN 5 IS ATTACKING 0.
				  		                        		
			  		                        			
			  		                        			//reveal5onleftclientattacking();
			  		                        			
				  		                        		/*
				  		                        		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				  		      						
					  		      						TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
					  		      						playerNameTextView.setTypeface(typeFace);		
					  		      						playerNameTextView.setText(ArrayOfPlayers.player[5]);							
					  		      								
					  		      						final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
					  		      						playerHitPointsTextView.setTypeface(typeFace);
					  		      						playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
					  		      						
					  		      						ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
					  		      						computerAvatar.setVisibility(View.VISIBLE);
					  		                  			
					  		                  			
					  		                  			scrollAnimationLeftUpNoRight();
					  		                  			
					  		                  			
						  		                  		ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
							  		      				img1.setBackgroundResource(R.drawable.twentytwentyblank);
							  		      				img1.setImageResource(R.drawable.twentytwentyblank);
				
							  		      				// Use a blank drawable to hide the imageview animation:
							  		      				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
							  		      				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
							  		      				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
							  		      				*/
			  		                        		}
			  		                        		
			  		                        		else {                    		
			  		                        			
			  		                        			playerNumberAttacked = 5;
			  		                        			
//playerAttackingTest();
			  		                        			
				  		                        		String str = "0chooses5";
				  		                        		sendToHost(str);
				  		                        		
				  		                        		
				  		                        		playersFighting = "zeroVsFive";//SAME AS WHEN 5 IS ATTACKING 0.
				  		                        		
				  		                        		
				  		                        		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				  		      						
					  		      						TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
					  		      						playerNameTextView.setTypeface(typeFace);		
					  		      						playerNameTextView.setText(ArrayOfPlayers.player[5]);							
					  		      								
					  		      						final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
					  		      						playerHitPointsTextView.setTypeface(typeFace);
					  		      						playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
					  		      						
					  		      						//ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
					  		      						//computerAvatar.setVisibility(View.VISIBLE);
					  		                  			
					  		      						set2PlayerAvatar();//SETS AVATAR FOR [5]
					  		      						
					  		                  			
					  		                  			scrollAnimationLeftUpNoRight();
					  		                  			
					  		                  			
						  		                  		ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
							  		      				img1.setBackgroundResource(R.drawable.twentytwentyblank);
							  		      				img1.setImageResource(R.drawable.twentytwentyblank);
							  		      				
							  		      				img1.setImageDrawable(null);
				
							  		      				// Use a blank drawable to hide the imageview animation:
							  		      				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
							  		      				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
							  		      				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
							  		      				
							  		      				img2.setImageDrawable(null);
			  		                        		}			  		                        		
													
													dialog.dismiss();
													
													hideSystemUI();
			  									}
			  		                        	
			  		                        	else if (item == 0 && playerDeadYet[5].equals("yes")) {
			  		                        		
			  		                        		chooseOpponent();
			  		                        	}
			  		                        	
			  		                        	else if (item == 1 && playerDeadYet[1].equals("no")) {
			  		                        		
			  		                        		if (zeroAttackingFirst.equals("yes")) {
			  		                        			
			  		                        			playerNumberAttacked = 1;
			  		                        			
//playerAttackingTest();
			  		                        			reveal1onleftclientattacking();
			  		                        			
			  		                        			
			  		                        			String str = "0FIrstChooses1";
				  		                        		sendToHost(str);
				  		                        		
				  		                        		
				  		                        		playersFighting = "zeroVsOne";
				  		                        		
				  		                        		
				  		                        		//reveal1onleftclientattacking();
				  		                        		
				  		                        		/*
				  		                        		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				  		      						
					  		      						TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
					  		      						playerNameTextView.setTypeface(typeFace);		
					  		      						playerNameTextView.setText(ArrayOfPlayers.player[1]);							
					  		      								
					  		      						final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
					  		      						playerHitPointsTextView.setTypeface(typeFace);
					  		      						playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[1]));
					  		      						
					  		      						ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
					  		      						computerAvatar.setVisibility(View.VISIBLE);
					  		                  			
					  		                  			
					  		                  			scrollAnimationLeftUpNoRight();
					  		                  			
					  		                  			
						  		                  		ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
							  		      				img1.setBackgroundResource(R.drawable.twentytwentyblank);
							  		      				img1.setImageResource(R.drawable.twentytwentyblank);
				
							  		      				// Use a blank drawable to hide the imageview animation:
							  		      				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
							  		      				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
							  		      				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
							  		      				*/
			  		                        		}
			  		                        		
			  		                        		else {
			  		                        			
			  		                        			playerNumberAttacked = 1;
			  		                        			
//playerAttackingTest();
			  		                        			
			  		                        			String str = "0chooses1";
				  		                        		sendToHost(str);
				  		                        		
				  		                        		
				  		                        		playersFighting = "zeroVsOne";
				  		                        		
				  		                        		
				  		                        		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				  		      						
					  		      						TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
					  		      						playerNameTextView.setTypeface(typeFace);		
					  		      						playerNameTextView.setText(ArrayOfPlayers.player[1]);							
					  		      								
					  		      						final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
					  		      						playerHitPointsTextView.setTypeface(typeFace);
					  		      						playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[1]));
					  		      						
					  		      						//ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
					  		      						//computerAvatar.setVisibility(View.VISIBLE);
					  		                  			
					  		      						setPlayerAvatarFor1Left();
					  		      						
					  		                  			
					  		                  			scrollAnimationLeftUpNoRight();
					  		                  			
					  		                  			
						  		                  		ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
							  		      				img1.setBackgroundResource(R.drawable.twentytwentyblank);
							  		      				img1.setImageResource(R.drawable.twentytwentyblank);
							  		      				
							  		      				img1.setImageDrawable(null);
				
							  		      				// Use a blank drawable to hide the imageview animation:
							  		      				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
							  		      				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
							  		      				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
							  		      				
							  		      				img2.setImageDrawable(null);
			  		                        		}
													
													dialog.dismiss();
													
													hideSystemUI();
			  									}
			  		                        	
			  		                        	else if (item == 1 && playerDeadYet[1].equals("yes")) {
			  		                        		
			  		                        		chooseOpponent();
			  		                        	}
			  		                        			  		                        	
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
			
			else if (id == 1) {
				
				final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);		
				
				runOnUiThread(new Runnable() {
					
		  	  	    @Override
		  	  	    public void run() {
		  	  	    	
			  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
			  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
			  			
			  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
			  			centerscrolltext.setTypeface(typeFace);
			  			
			  			
			  			computerCardStartFadeInFadeOut();
			  			
			  			
			  			/*
			  			centerscrolltext.setVisibility(View.VISIBLE);													
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[1] + " is choosing player to attack...");									
						*/
						
						String str = "> " + ArrayOfPlayers.player[1] + " is choosing player to attack...";
						sendToHost(str);
		  	  	    					
						
						final Handler h = new Handler();
			  	  	  	h.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {	  	  				
			  	  	  					
	  	  	  					final String[] items = {ArrayOfPlayers.player[5], ArrayOfPlayers.player[0]}; 	  	  			
			  	  	  		
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
			  			  		ContextThemeWrapper cw = new ContextThemeWrapper(Client2.this, R.layout.avatar_adapter);
			  			  		AlertDialog.Builder builder = new AlertDialog.Builder(cw, R.style.customalertdialog);		  			  		
			  			  		
			  		  			
			  		  			builder.setCancelable(false);	  			
			  		  			
			  		  			//builder.setTitle("Choose Player To Attack");
			  		  			
				  		  		Toast toast = Toast.makeText(Client2.this, "Choose Player To Attack", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
	        		  			View view = toast.getView();
	        		  			view.setBackgroundResource(R.drawable.centerscroll3toast);
	        		  			toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
	
	        		  			TextView text = (TextView) view.findViewById(android.R.id.message);
	        		  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	        		  			text.setTypeface(typeFace);
	        		  			text.setTextColor(Color.parseColor("#FFFFFF"));
	        		  			//text.setRotation(-45);
	        		  			text.setGravity(Gravity.CENTER);
	        		  			
	        		  			toast.show();
			  		  			
			  		  			
			  					builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			  						@Override
			  						public void onCancel(DialogInterface dialog) {		  							
			  							
			  							chooseOpponent();
			  						}
			  					});
			  		  			
			  					
			  		            builder.setAdapter(adapter,
			  		                    new DialogInterface.OnClickListener() {
			  		                        @Override
			  		                        public void onClick(final DialogInterface dialog, int item) {				  		                        	
			  		                        	
			  		                        	if (item == 0 && playerDeadYet[5].equals("no")) {
			  		                        		
			  		                        		if (oneAttackingFirst.equals("yes")) {
			  		                        			
			  		                        			playerNumberAttacked = 5;
			  		                        			
//playerAttackingTest();
			  		                        			reveal5onleftclientattacking();
			  		                        			
			  		                        			
			  		                        			String str = "1FirstChooses5";
				  		                        		sendToHost(str);
				  		                        		
				  		                        		
				  		                        		playersFighting = "oneVsFive";
				  		                        		
				  		                        		
				  		                        		//reveal5onleftclientattacking();
				  		                        		
				  		                        		/*
				  		                        		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				  		      						
					  		      						TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
					  		      						playerNameTextView.setTypeface(typeFace);		
					  		      						playerNameTextView.setText(ArrayOfPlayers.player[5]);							
					  		      								
					  		      						final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
					  		      						playerHitPointsTextView.setTypeface(typeFace);
					  		      						playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
					  		      						
					  		      						ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
					  		      						computerAvatar.setVisibility(View.VISIBLE);
					  		                  			
					  		                  			
					  		                  			scrollAnimationLeftUpNoRight();
					  		                  			
					  		                  			
						  		                  		ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
							  		      				img1.setBackgroundResource(R.drawable.twentytwentyblank);
							  		      				img1.setImageResource(R.drawable.twentytwentyblank);
				
							  		      				// Use a blank drawable to hide the imageview animation:
							  		      				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
							  		      				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
							  		      				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
							  		      				*/
			  		                        		}
			  		                        		
			  		                        		else {
			  		                        			
			  		                        			playerNumberAttacked = 5;
			  		                        			
//playerAttackingTest();
			  		                        			
			  		                        			String str = "1chooses5";
				  		                        		sendToHost(str);
				  		                        		
				  		                        		
				  		                        		playersFighting = "oneVsFive";
				  		                        		
				  		                        		
				  		                        		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				  		      						
					  		      						TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
					  		      						playerNameTextView.setTypeface(typeFace);		
					  		      						playerNameTextView.setText(ArrayOfPlayers.player[5]);							
					  		      								
					  		      						final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
					  		      						playerHitPointsTextView.setTypeface(typeFace);
					  		      						playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
					  		      						
					  		      						//ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
					  		      						//computerAvatar.setVisibility(View.VISIBLE);
					  		                  			
					  		      						set2PlayerAvatar();//SETS AVATAR FOR [5]
					  		      						
					  		                  			
					  		                  			scrollAnimationLeftUpNoRight();
					  		                  			
					  		                  			
						  		                  		ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
							  		      				img1.setBackgroundResource(R.drawable.twentytwentyblank);
							  		      				img1.setImageResource(R.drawable.twentytwentyblank);
							  		      				
							  		      				img1.setImageDrawable(null);
				
							  		      				// Use a blank drawable to hide the imageview animation:
							  		      				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
							  		      				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
							  		      				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
							  		      				
							  		      				img2.setImageDrawable(null);
			  		                        		}
			  		                        		
													dialog.dismiss();
													
													hideSystemUI();
			  									}
			  		                        	
			  		                        	else if (item == 0 && playerDeadYet[5].equals("yes")) {
			  		                        		
			  		                        		chooseOpponent();
			  		                        	}
			  		                        	
			  		                        	else if (item == 1 && playerDeadYet[0].equals("no")) {			  		                        		
													
			  		                        		if (oneAttackingFirst.equals("yes")) {
			  		                        			
			  		                        			playerNumberAttacked = 0;
			  		                        			
//playerAttackingTest();
			  		                        			reveal0onleftclientattacking();
			  		                        			
			  		                        			
			  		                        			String str = "1FIrstChooses0";
				  		                        		sendToHost(str);
				  		                        		
				  		                        		
				  		                        		playersFighting = "oneVsZero";
				  		                        		
				  		                        		
				  		                        		//reveal0onleftclientattacking();
				  		                        		
				  		                        		/*
				  		                        		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				  		      						
					  		      						TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
					  		      						playerNameTextView.setTypeface(typeFace);		
					  		      						playerNameTextView.setText(ArrayOfPlayers.player[0]);							
					  		      								
					  		      						final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
					  		      						playerHitPointsTextView.setTypeface(typeFace);
					  		      						playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
					  		      						
					  		      						ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
					  		      						computerAvatar.setVisibility(View.VISIBLE);
					  		                  			
					  		                  			
					  		                  			scrollAnimationLeftUpNoRight();
					  		                  			
					  		                  			
						  		                  		ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
							  		      				img1.setBackgroundResource(R.drawable.twentytwentyblank);
							  		      				img1.setImageResource(R.drawable.twentytwentyblank);
				
							  		      				// Use a blank drawable to hide the imageview animation:
							  		      				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
							  		      				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
							  		      				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
							  		      				*/
			  		                        		}
			  		                        		
			  		                        		else {
			  		                        			
			  		                        			playerNumberAttacked = 0;
			  		                        			
//playerAttackingTest();
			  		                        			
			  		                        			String str = "1chooses0";
				  		                        		sendToHost(str);
				  		                        		
				  		                        		
				  		                        		playersFighting = "oneVsZero";
				  		                        		
				  		                        		
				  		                        		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				  		      						
					  		      						TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
					  		      						playerNameTextView.setTypeface(typeFace);		
					  		      						playerNameTextView.setText(ArrayOfPlayers.player[0]);							
					  		      								
					  		      						final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
					  		      						playerHitPointsTextView.setTypeface(typeFace);
					  		      						playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
					  		      						
					  		      						//ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
					  		      						//computerAvatar.setVisibility(View.VISIBLE);
					  		                  			
					  		      						setPlayerAvatarFor0Left();
					  		      						
					  		                  			
					  		                  			scrollAnimationLeftUpNoRight();
					  		                  			
					  		                  			
						  		                  		ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
							  		      				img1.setBackgroundResource(R.drawable.twentytwentyblank);
							  		      				img1.setImageResource(R.drawable.twentytwentyblank);
							  		      				
							  		      				img1.setImageDrawable(null);
				
							  		      				// Use a blank drawable to hide the imageview animation:
							  		      				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
							  		      				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
							  		      				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
							  		      				
							  		      				img2.setImageDrawable(null);
			  		                        		}
													
													dialog.dismiss();
													
													hideSystemUI();																						
			  									}
			  		                        	
			  		                        	else if (item == 1 && playerDeadYet[0].equals("yes")) {
			  		                        		
			  		                        		chooseOpponent();
			  		                        	}	  		                        	
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
		}
	}
	
	public void reveal0onright() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
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

    			//ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
    			//clientAvatar.setVisibility(View.VISIBLE);
    			
    			if (ArrayOfAvatars.avatar[0].equals("custom")){
		  	  	    	
			  	  	    File imgFile = new  File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files/avatar0");	//WAS .png
			  	  	    																						//WAS: /storage/sdcard0/avatar0
	
						if(imgFile.exists()){
	
						    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
	
						    ImageView myImage = (ImageView) findViewById(R.id.imageviewavatarright);
	
						    myImage.setImageBitmap(myBitmap);
						}
	  			}
	  	  	    else if (ArrayOfAvatars.avatar[0].equals("computer")){
	  	  	    	
	  				ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
	  				clientAvatar.setBackgroundResource(R.drawable.computer);
	  			}
	  			else if (ArrayOfAvatars.avatar[0].equals("crossedswords")){
	  				
	  				ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
	  				clientAvatar.setBackgroundResource(R.drawable.crossedswords2);
	  			}
	  			else if (ArrayOfAvatars.avatar[0].equals("stonedead")){
	  				
	  				ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
	  				clientAvatar.setBackgroundResource(R.drawable.stonedead2);
	  			}
	  	  			
	  	  			
    			scrollAnimationLeftDown();
    			
    			
    			computerCardStartFadeInFadeOut();
    			
    			
    			//Toast.makeText(Client2.this, "reveal0onright", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void reveal1onright() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
    			
    			TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
    			computerNameTextView.setTypeface(typeFace);
    			computerNameTextView.setText(ArrayOfPlayers.player[1]);
    			//computerNameTextView.setVisibility(View.INVISIBLE);

    			//ArrayOfHitPoints.hitpoints[0] = 20;//20
    			final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
    			computerHitPointsTextView.setTypeface(typeFace);
    			//computerHitPointsTextView.setVisibility(View.INVISIBLE);
    			computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[1]));

    			//ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
    			//clientAvatar.setVisibility(View.VISIBLE);
    			
    			if (ArrayOfAvatars.avatar[1].equals("custom")){
		  	  	    	
			  	  	    File imgFile = new  File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files/avatar1");	//WAS .png
			  	  	    																						//WAS: /storage/sdcard0/avatar1
	
						if(imgFile.exists()){
	
						    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
	
						    ImageView myImage = (ImageView) findViewById(R.id.imageviewavatarright);
	
						    myImage.setImageBitmap(myBitmap);
						}
	  			}
	  	  	    else if (ArrayOfAvatars.avatar[1].equals("computer")){
	  	  	    	
	  				ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
	  				clientAvatar.setBackgroundResource(R.drawable.computer);
	  			}
	  			else if (ArrayOfAvatars.avatar[1].equals("crossedswords")){
	  				
	  				ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
	  				clientAvatar.setBackgroundResource(R.drawable.crossedswords2);
	  			}
	  			else if (ArrayOfAvatars.avatar[1].equals("stonedead")){
	  				
	  				ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
	  				clientAvatar.setBackgroundResource(R.drawable.stonedead2);
	  			}
	  	  			
	  	  			
    			scrollAnimationLeftDown();
    			
    			
    			computerCardStartFadeInFadeOut();
    			
    			
    			//Toast.makeText(Client2.this, "reveal1onright", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void reveal5onleft() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
    			
		  	  	TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
				playerNameTextView.setTypeface(typeFace);		
				playerNameTextView.setText(ArrayOfPlayers.player[5]);							
						
				final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
				playerHitPointsTextView.setTypeface(typeFace);
				playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
				
				//ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
				//computerAvatar.setVisibility(View.VISIBLE);
				
				set2PlayerAvatar();//SETS AVATAR FOR [5]
				
				
				unfoldLeftScroll();
				
				
				playerCardStartFadeInFadeOut();
			}
		});
	}
	
	
	public void reveal5onleftclientattacking() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				clearDisarmGraphicAndSkills();
				
				
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				
				TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
				playerNameTextView.setTypeface(typeFace);		
				playerNameTextView.setText(ArrayOfPlayers.player[5]);							
						
				final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
				playerHitPointsTextView.setTypeface(typeFace);
				playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
				
				//ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
				//computerAvatar.setVisibility(View.VISIBLE);
				
				set2PlayerAvatar();//SETS AVATAR FOR [5]

				
    			scrollAnimationLeftUpNoRight();
    			
    			
        		ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
				
				img1.setImageDrawable(null);

				// Use a blank drawable to hide the imageview animation:
				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
				
				img2.setImageDrawable(null);
			}
		});
	}
	
	public void reveal1onleftclientattacking() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				clearDisarmGraphicAndSkills();
				
				
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				
				TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
				playerNameTextView.setTypeface(typeFace);		
				playerNameTextView.setText(ArrayOfPlayers.player[1]);							
						
				final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
				playerHitPointsTextView.setTypeface(typeFace);
				playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[1]));
				
				//ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
				//computerAvatar.setVisibility(View.VISIBLE);
				
				setPlayerAvatarFor1Left();
				
				
    			scrollAnimationLeftUpNoRight();
    			
    			
        		ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
				
				img1.setImageDrawable(null);

				// Use a blank drawable to hide the imageview animation:
				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
				
				img2.setImageDrawable(null);
			}
		});
	}
	
	public void reveal0onleftclientattacking() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				clearDisarmGraphicAndSkills();
				
				
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				
				TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
				playerNameTextView.setTypeface(typeFace);		
				playerNameTextView.setText(ArrayOfPlayers.player[0]);							
						
				final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
				playerHitPointsTextView.setTypeface(typeFace);
				playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
				
				//ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
				//computerAvatar.setVisibility(View.VISIBLE);
				
				setPlayerAvatarFor0Left();
    			
    			
    			scrollAnimationLeftUpNoRight();
    			
    			
        		ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
				
				img1.setImageDrawable(null);

				// Use a blank drawable to hide the imageview animation:
				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
				
				img2.setImageDrawable(null);
			}
		});
	}
	
	
	public void turn3V35() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {			
				
				//hideImageView();
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
				
				img1.setImageDrawable(null);
		
				// Use a blank drawable to hide the imageview animation:
				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
				
				img2.setImageDrawable(null);
				
				
				final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
				// Re-enables ability to use srollbar:
				centerscrolltext.bringToFront();
				
				ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
		    	titleBlankButton.bringToFront();
		    	
		    	
		    	ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
	  			chatBlankButton.bringToFront();
				
				
		    	computerCardStopFadeInFadeOut();
				playerCardStartFadeInFadeOut();
				
				
				Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				
				TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
				playerNameTextView.setTypeface(typeFace);		
				playerNameTextView.setText(ArrayOfPlayers.player[5]);
				
				//NEED THIS??
				TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
				playerHitPointsTextView.setTypeface(typeFace);
				playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
				//Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);
				//playerHitPointsTextView.startAnimation(animPulsingAnimation);
				
				
				set2PlayerAvatar(); //SETS AVATAR FOR [5]
				
				
				scrollAnimationLeftUpNoRight();
			}
		});		
	}	
	
	
	public void gameEngine3V350() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				playersFighting = "fiveVsZero";
				
				
				MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
				
				foldScrolls();
				//scrollAnimationLeftDownReverse();
				
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {	  	  	  		
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						
						TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
						playerNameTextView.setTypeface(typeFace);		
						playerNameTextView.setText(ArrayOfPlayers.player[5]);							
								
						final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
						playerHitPointsTextView.setTypeface(typeFace);
						playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
						
						//ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
						//computerAvatar.setVisibility(View.VISIBLE);
						
						set2PlayerAvatar();//SETS AVATAR FOR [5]
						
						
						TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
						computerNameTextView.setTypeface(typeFace);
						computerNameTextView.setText(ArrayOfPlayers.player[0]);
						
						final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
						computerHitPointsTextView.setTypeface(typeFace);
						computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
						
						//ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
						//clientAvatar.setVisibility(View.VISIBLE);
						
						if (ArrayOfAvatars.avatar[0].equals("custom")){
			  	  	    	
				  	  	    File imgFile = new  File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files/avatar0");//WAS .png
				  	  	    																					//WAS: /storage/sdcard0/avatar0
		
							if(imgFile.exists()){
		
							    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		
							    ImageView myImage = (ImageView) findViewById(R.id.imageviewavatarright);
		
							    myImage.setImageBitmap(myBitmap);
							}
			  			}
			  	  	    else if (ArrayOfAvatars.avatar[0].equals("computer")){
			  	  	    	
			  				ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
			  				clientAvatar.setBackgroundResource(R.drawable.computer);
			  			}
			  			else if (ArrayOfAvatars.avatar[0].equals("crossedswords")){
			  				
			  				ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
			  				clientAvatar.setBackgroundResource(R.drawable.crossedswords2);
			  			}
			  			else if (ArrayOfAvatars.avatar[0].equals("stonedead")){
			  				
			  				ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
			  				clientAvatar.setBackgroundResource(R.drawable.stonedead2);
			  			}
						
						
						MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
						
						unfoldScrolls();
						
						
						playerCardStartFadeInFadeOut();
	  	  	  		}
	  	  	  	}, 1000);				
			}
		});		
	}
	
	public void gameEngine3V351() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				playersFighting = "fiveVsOne";
				
				
				MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
				
				foldScrolls();
				//scrollAnimationLeftDownReverse();
				
				
				final Handler h = new Handler();
	  	  	  	h.postDelayed(new Runnable() {	  	  	  		
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
						
						TextView playerNameTextView = (TextView)findViewById(R.id.textviewnameleft);		
						playerNameTextView.setTypeface(typeFace);		
						playerNameTextView.setText(ArrayOfPlayers.player[5]);							
								
						final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
						playerHitPointsTextView.setTypeface(typeFace);
						playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
						
						//ImageView computerAvatar = (ImageView) findViewById(R.id.imageviewavatarleft1);
						//computerAvatar.setVisibility(View.VISIBLE);
						
						set2PlayerAvatar();//SETS AVATAR FOR [5]
						
						
						TextView computerNameTextView = (TextView)findViewById(R.id.textviewnameright);
						computerNameTextView.setTypeface(typeFace);
						computerNameTextView.setText(ArrayOfPlayers.player[1]);
						
						final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
						computerHitPointsTextView.setTypeface(typeFace);
						computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[1]));
						
						//ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
						//clientAvatar.setVisibility(View.VISIBLE);
						
						if (ArrayOfAvatars.avatar[1].equals("custom")){
			  	  	    	
				  	  	    File imgFile = new  File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files/avatar1");//WAS .png
				  	  	    																					//WAS: /storage/sdcard0/avatar1
		
							if(imgFile.exists()){
		
							    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		
							    ImageView myImage = (ImageView) findViewById(R.id.imageviewavatarright);
		
							    myImage.setImageBitmap(myBitmap);
							}
			  			}
			  	  	    else if (ArrayOfAvatars.avatar[1].equals("computer")){
			  	  	    	
			  				ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
			  				clientAvatar.setBackgroundResource(R.drawable.computer);
			  			}
			  			else if (ArrayOfAvatars.avatar[1].equals("crossedswords")){
			  				
			  				ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
			  				clientAvatar.setBackgroundResource(R.drawable.crossedswords2);
			  			}
			  			else if (ArrayOfAvatars.avatar[1].equals("stonedead")){
			  				
			  				ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
			  				clientAvatar.setBackgroundResource(R.drawable.stonedead2);
			  			}
						
						
						MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
						
						unfoldScrolls();
						
						
						playerCardStartFadeInFadeOut();
	  	  	  		}
	  	  	  	}, 1000);				
			}
		});		
	}
	
	
	public void turn1V150() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
	  			chatBlankButton.bringToFront();
				
				
				//hideImageView();
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
				
				img1.setImageDrawable(null);
				
				/*
				//TEST:
				img1.bringToFront();
				*/

				// Use a blank drawable to hide the imageview animation:
				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
				
				img2.setImageDrawable(null);
				
				
				computerCardStopFadeInFadeOut();
				playerCardStartFadeInFadeOut();
				
				
				Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				
				//NEED THIS??
				TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
				playerHitPointsTextView.setTypeface(typeFace);
				playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[5]));
				//Animation animPulsingAnimation = AnimationUtils.loadAnimation(MainActivity2.this, R.anim.pulsinganimation);
				//playerHitPointsTextView.startAnimation(animPulsingAnimation);				
				
				
				//Toast.makeText(Client2.this, "Player # Attacked = " + playerNumberAttacked + ", # Of Players = " + numberOfPlayers, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void turn1V105() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {				
				
				//ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
	  			//chatBlankButton.bringToFront();
				
				
				//hideImageView();
				
				// Use a blank drawable to hide the imageview animation:
				// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
				ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
				img1.setBackgroundResource(R.drawable.twentytwentyblank);
				img1.setImageResource(R.drawable.twentytwentyblank);
				
				img1.setImageDrawable(null);

				// Use a blank drawable to hide the imageview animation:
				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
				
				img2.setImageDrawable(null);
				
				
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
				
				//Toast.makeText(Client2.this, "Player # Attacked = " + playerNumberAttacked + ", # Of Players = " + numberOfPlayers, Toast.LENGTH_SHORT).show();
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
				
				img1.setImageDrawable(null);
		
				// Use a blank drawable to hide the imageview animation:
				ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
				img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
				img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
				
				img2.setImageDrawable(null);
				
				
				final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
				// Re-enables ability to use srollbar:
				centerscrolltext.bringToFront();
				
				ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
		    	titleBlankButton.bringToFront();
		    	
		    	
		    	ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
	  			chatBlankButton.bringToFront();
				
				
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
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {  	  	    	
  	  	    	
  	  	    	// Use a blank drawable to hide the imageview animation:
	  	  		// PREVIOUSLY FOUND THAT ANDROID CRASHES IF USE //img.setVisibility(View.INVISIBLE);
	  	  		ImageView img1 = (ImageView)findViewById(R.id.twentysidedanimation);		
	  	  		img1.setBackgroundResource(R.drawable.twentytwentyblank);
	  	  		img1.setImageResource(R.drawable.twentytwentyblank);
	  	  		
	  	  		img1.setImageDrawable(null);
	
	  	  		// Use a blank drawable to hide the imageview animation:
	  	  		ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
	  	  		img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
	  	  		img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
	  	  		
	  	  		img2.setImageDrawable(null);
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
		
				AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this, R.style.customalertdialog);
					
				alert.setCancelable(false);
				
				if (id == 0) {
					
					alert.setMessage(ArrayOfPlayers.player[0] + ", do you want to use Dodge?");
  	  			}
  	  			else if (id == 1) {
  	  				
  	  				alert.setMessage(ArrayOfPlayers.player[1] + ", do you want to use Dodge?");
  	  			}
		    	
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
			    		
			    		hideSystemUI();
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
			    		
			    		hideSystemUI();
		    		}
		    	});		  	    	
		    	alert.show();
  	  	    }
		});
	}
	
	public void changeHostHitPoints() {
		
		//hpTest5();
		
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
	
	public void changeClient0HitPoints() {
		
		//hpTest0();
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
		
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				
								
				if (playersFighting.equals("fiveVsZero") || playersFighting.equals("zeroVsFive") || playersFighting.equals("zeroVsOne")) {
					
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
				
				else if (playersFighting.equals("oneVsZero")) {
					
					final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
					playerHitPointsTextView.setTypeface(typeFace);
					playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[0]));
					
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
  	  	    }
  		});
	}

	public void changeClient1HitPoints() {
		
		//hpTest1();
		
		runOnUiThread(new Runnable() {
	  	    @Override
	  	    public void run() {
	
				final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
				
				
				if (playersFighting.equals("fiveVsOne") || playersFighting.equals("oneVsFive") || playersFighting.equals("oneVsZero")) {
					
					final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
					computerHitPointsTextView.setTypeface(typeFace);
					computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[1]));
					
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
				
				else if (playersFighting.equals("zeroVsOne")) {
					
					final TextView playerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsleft);
					playerHitPointsTextView.setTypeface(typeFace);
					playerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[1]));
					
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
	  	    }
		});
	}


	
	public void victoryDefeatAnimation() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {						
				
				ImageButton titleBlankButton = (ImageButton) findViewById(R.id.imagebuttontitleblank);
				titleBlankButton.setEnabled(false);
				
				
				ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
		  		chatBlankButton.bringToFront();
				
				
		  		
				//test();
		  		
		  		
		  		
		  		//ADD THIS IF CLIENT WORKING AGAIN:
				final Handler h8 = new Handler();
		  	  	h8.postDelayed(new Runnable() {		  	  	  			
		  	  			
		  	  		@Override
		  	  		public void run() {

		  	  			MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
		  	  		}
		  	  	}, 500);
		  		
				
				
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
									
									MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound6);
									
									titlevictorydefeat.append("Defeat");									
									
									istitlestatsopen = "no";
									
									
									win = "no";
									
									writeTextToFile();
					    			
					    			getTextFromFile();
								}
								
								else if (playerDeadYet[0].equals("no") && playerDeadYet[5].equals("yes")) {
									
									MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound4b);
									
									titlevictorydefeat.append("Victory");									
									
									istitlestatsopen = "no";
									
									
									win = "yes";
									
									writeTextToFile();
					    			
					    			getTextFromFile();
								}
							}
							
							else if (numberOfPlayers == 3) {
								
								if (id == 0) {
									
									if (playerDeadYet[5].equals("no") && playerDeadYet[0].equals("yes") && playerDeadYet[1].equals("yes")) {
										
										MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound6);
										
										titlevictorydefeat.append("Defeat");									
										
										istitlestatsopen = "no";
										
										
										win = "no";
										
										writeTextToFile();
						    			
						    			getTextFromFile();
									}
									
									else if (playerDeadYet[1].equals("no") && playerDeadYet[0].equals("yes") && playerDeadYet[5].equals("yes")) {
										
										MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound6);
										
										titlevictorydefeat.append("Defeat");									
										
										istitlestatsopen = "no";
										
										
										win = "no";
										
										writeTextToFile();
						    			
						    			getTextFromFile();
									}
									
									else if (playerDeadYet[0].equals("no") && playerDeadYet[5].equals("yes") && playerDeadYet[1].equals("yes")) {
										
										MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound4b);
										
										titlevictorydefeat.append("Victory");									
										
										istitlestatsopen = "no";
										
										
										win = "yes";
										
										writeTextToFile();
						    			
						    			getTextFromFile();
									}
								}
								else if (id == 1) {
									
									if (playerDeadYet[5].equals("no") && playerDeadYet[1].equals("yes") && playerDeadYet[0].equals("yes")) {
										
										MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound6);
										
										titlevictorydefeat.append("Defeat");									
										
										istitlestatsopen = "no";
										
										
										win = "no";
										
										writeTextToFile();
						    			
						    			getTextFromFile();
									}
									
									else if (playerDeadYet[0].equals("no") && playerDeadYet[1].equals("yes") && playerDeadYet[5].equals("yes")) {
										
										MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound6);
										
										titlevictorydefeat.append("Defeat");									
										
										istitlestatsopen = "no";
										
										
										win = "no";
										
										writeTextToFile();
						    			
						    			getTextFromFile();
									}
									
									else if (playerDeadYet[1].equals("no") && playerDeadYet[5].equals("yes") && playerDeadYet[0].equals("yes")) {
										
										MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound4b);
										
										titlevictorydefeat.append("Victory");									
										
										istitlestatsopen = "no";
										
										
										win = "yes";
										
										writeTextToFile();
						    			
						    			getTextFromFile();
									}
								}								
							}
			  	  	  	}
		  	  	  	}, 300);//WAS: 600
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
									
									MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound6);
									
									titlevictorydefeat.append("Defeat");
									
									
									win = "no";
									
									writeTextToFile();
					    			
					    			getTextFromFile();
									
								}
								
								else if (playerDeadYet[0].equals("no") && playerDeadYet[5].equals("yes")) {
									
									MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound4b);
									
									titlevictorydefeat.append("Victory");
									
									
									win = "yes";
									
									writeTextToFile();
					    			
					    			getTextFromFile();
								}
							}
							
							else if (numberOfPlayers == 3) {
								
								if (id == 0) {
									
									if (playerDeadYet[5].equals("no") && playerDeadYet[0].equals("yes") && playerDeadYet[1].equals("yes")) {
										
										MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound6);
										
										titlevictorydefeat.append("Defeat");									
										
										istitlestatsopen = "no";
										
										
										win = "no";
										
										writeTextToFile();
						    			
						    			getTextFromFile();
									}
									
									else if (playerDeadYet[1].equals("no") && playerDeadYet[0].equals("yes") && playerDeadYet[5].equals("yes")) {
										
										MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound6);
										
										titlevictorydefeat.append("Defeat");									
										
										istitlestatsopen = "no";
										
										
										win = "no";
										
										writeTextToFile();
						    			
						    			getTextFromFile();
									}
									
									else if (playerDeadYet[0].equals("no") && playerDeadYet[5].equals("yes") && playerDeadYet[1].equals("yes")) {
										
										MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound4b);
										
										titlevictorydefeat.append("Victory");									
										
										istitlestatsopen = "no";
										
										
										win = "yes";
										
										writeTextToFile();
						    			
						    			getTextFromFile();
									}
								}
								else if (id == 1) {
									
									if (playerDeadYet[5].equals("no") && playerDeadYet[1].equals("yes") && playerDeadYet[0].equals("yes")) {
										
										MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound6);
										
										titlevictorydefeat.append("Defeat");									
										
										istitlestatsopen = "no";
										
										
										win = "no";
										
										writeTextToFile();
						    			
						    			getTextFromFile();
									}
									
									else if (playerDeadYet[0].equals("no") && playerDeadYet[1].equals("yes") && playerDeadYet[5].equals("yes")) {
										
										MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound6);
										
										titlevictorydefeat.append("Defeat");									
										
										istitlestatsopen = "no";
										
										
										win = "no";
										
										writeTextToFile();
						    			
						    			getTextFromFile();
									}
									
									else if (playerDeadYet[1].equals("no") && playerDeadYet[5].equals("yes") && playerDeadYet[0].equals("yes")) {
										
										MediaPlayerWrapper.play(Client2.this, R.raw.buttonsound4b);
										
										titlevictorydefeat.append("Victory");									
										
										istitlestatsopen = "no";
										
										
										win = "yes";
										
										writeTextToFile();
						    			
						    			getTextFromFile();
									}
								}								
							}
			  	  	  	}
		  	  	  	}, 300);//WAS: 600
				}
							
			}
  		});
	}
	
	
	public void clientNotDisarmed() {
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
		
				TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
		  		disarmedtextright.setVisibility(View.INVISIBLE);
  	  	    }
		});
	}
	
	public void hostSideNotDisarmed() {
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
		
				TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
		  		disarmedtextleft.setVisibility(View.INVISIBLE);
  	  	    }
		});
	}
	
	public void clientDisarmed() {
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
		
				TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
				disarmedtextright.setVisibility(View.VISIBLE);
		  	  	disarmedtextright.bringToFront();
  	  	    }
		});
	}
	
	public void hostSideDisarmed() {
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
		
				TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
				disarmedtextleft.setVisibility(View.VISIBLE);
		  	  	disarmedtextleft.bringToFront();
  	  	    }
		});
	}
	
	
	public void skillsCheckBoth() {
		
		//playersFightingTest();
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
  	  	    	ImageView blessLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftbless);
  	  	    	ImageView cureLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftcure);
  	  	    	ImageView dodgeLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftdodge);
  	  	    	ImageView mbLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftmb);
  	  	    	ImageView hasteLeft1 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste1);
  	  	    	ImageView hasteLeft2 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste2);
  	  	    	
  	  	    	ImageView blessRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightbless);
  	  	    	ImageView cureRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightcure);
  	  	    	ImageView dodgeRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightdodge);
  	  	    	ImageView mbRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightmb);
  	  	    	ImageView hasteRight1 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste1);
  	  	    	ImageView hasteRight2 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste2);
  	  	    	
  	  	    	
  	  	    	blessLeft.setVisibility(View.INVISIBLE);
  	  	    	blessLeft.bringToFront();
  	  	    	blessLeft.setImageDrawable(null);
  	  	    	cureLeft.setVisibility(View.INVISIBLE);
  	  	    	cureLeft.bringToFront();
  	  	    	cureLeft.setImageDrawable(null);
  	  	    	dodgeLeft.setVisibility(View.INVISIBLE);
  	  	    	dodgeLeft.bringToFront();
  	  	    	dodgeLeft.setImageDrawable(null);
  	  	    	mbLeft.setVisibility(View.INVISIBLE);
  	  	    	mbLeft.bringToFront();
  	  	    	mbLeft.setImageDrawable(null);
  	  	    	hasteLeft1.setVisibility(View.INVISIBLE);
  	  	    	hasteLeft1.bringToFront();
  	  	    	hasteLeft1.setImageDrawable(null);
  	  	    	hasteLeft2.setVisibility(View.INVISIBLE);
  	  	    	hasteLeft2.bringToFront();
  	  	    	hasteLeft2.setImageDrawable(null);
  	  	    	blessRight.setVisibility(View.INVISIBLE);
  	  	    	blessRight.bringToFront();
  	  	    	blessRight.setImageDrawable(null);
  	  	    	cureRight.setVisibility(View.INVISIBLE);
  	  	    	cureRight.bringToFront();
  	  	    	cureRight.setImageDrawable(null);
  	  	    	dodgeRight.setVisibility(View.INVISIBLE);
  	  	    	dodgeRight.bringToFront();
  	  	    	dodgeRight.setImageDrawable(null);
  	  	    	mbRight.setVisibility(View.INVISIBLE);
  	  	    	mbRight.bringToFront();
  	  	    	mbRight.setImageDrawable(null);
  	  	    	hasteRight1.setVisibility(View.INVISIBLE);
  	  	    	hasteRight1.bringToFront();
  	  	    	hasteRight1.setImageDrawable(null);
  	  	    	hasteRight2.setVisibility(View.INVISIBLE);
  	  	    	hasteRight2.bringToFront();
  	  	    	hasteRight2.setImageDrawable(null);
  	  	    	
  	  	    	
				//if (numberOfPlayers == 2) {
  	  	    	//}
  	  	    	
  	  	    	if (playersFighting.equals("fiveVsZero") || playersFighting.equals("zeroVsFive")) {
  	  	    		
	  	  	    	if (blessSpell[5] < 1) {						
						
						blessLeft.setVisibility(View.VISIBLE);				
					}				
					if (cureSpell[5] < 1) {						
						
						cureLeft.setVisibility(View.VISIBLE);				
					}			
					if (dodgeBlowSpell[5] < 1) {						
						
						dodgeLeft.setVisibility(View.VISIBLE);				
					}			
					if (mightyBlowSpell[5] < 1) {
						
						
						mbLeft.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[5] < 2) {						
						
						hasteLeft1.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[5] < 1) {						
						
						hasteLeft1.setVisibility(View.VISIBLE);						
						hasteLeft2.setVisibility(View.VISIBLE);				
					}
					
					
					if (blessSpell[0] < 1) {						
						
						blessRight.setVisibility(View.VISIBLE);				
					}				
					if (cureSpell[0] < 1) {						
						
						cureRight.setVisibility(View.VISIBLE);				
					}			
					if (dodgeBlowSpell[0] < 1) {						
						
						dodgeRight.setVisibility(View.VISIBLE);				
					}			
					if (mightyBlowSpell[0] < 1) {						
						
						mbRight.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[0] < 2) {						
						
						hasteRight1.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[0] < 1) {						
						
						hasteRight1.setVisibility(View.VISIBLE);						
						hasteRight2.setVisibility(View.VISIBLE);				
					}
  	  	    	}
  	  	    	
  	  	    	if (playersFighting.equals("fiveVsOne") || playersFighting.equals("oneVsFive")) {
  	    		
	  	  	    	if (blessSpell[5] < 1) {						
						
						blessLeft.setVisibility(View.VISIBLE);				
					}				
					if (cureSpell[5] < 1) {						
						
						cureLeft.setVisibility(View.VISIBLE);				
					}			
					if (dodgeBlowSpell[5] < 1) {						
						
						dodgeLeft.setVisibility(View.VISIBLE);				
					}			
					if (mightyBlowSpell[5] < 1) {						
						
						mbLeft.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[5] < 2) {						
						
						hasteLeft1.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[5] < 1) {						
						
						hasteLeft1.setVisibility(View.VISIBLE);						
						hasteLeft2.setVisibility(View.VISIBLE);				
					}
					
					
					if (blessSpell[1] < 1) {						
						
						blessRight.setVisibility(View.VISIBLE);				
					}				
					if (cureSpell[1] < 1) {						
						
						cureRight.setVisibility(View.VISIBLE);				
					}			
					if (dodgeBlowSpell[1] < 1) {						
						
						dodgeRight.setVisibility(View.VISIBLE);				
					}			
					if (mightyBlowSpell[1] < 1) {						
						
						mbRight.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[1] < 2) {						
						
						hasteRight1.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[1] < 1) {						
						
						hasteRight1.setVisibility(View.VISIBLE);						
						hasteRight2.setVisibility(View.VISIBLE);				
					}
	  	    	}
  	  	    	
  	  	    	if (playersFighting.equals("zeroVsOne")) {
	  	    		
	  	  	    	if (blessSpell[1] < 1) {						
						
						blessLeft.setVisibility(View.VISIBLE);				
					}				
					if (cureSpell[1] < 1) {
						
						
						cureLeft.setVisibility(View.VISIBLE);				
					}			
					if (dodgeBlowSpell[1] < 1) {
						
						dodgeLeft.setVisibility(View.VISIBLE);				
					}			
					if (mightyBlowSpell[1] < 1) {
						
						mbLeft.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[1] < 2) {
						
						hasteLeft1.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[1] < 1) {
						
						hasteLeft1.setVisibility(View.VISIBLE);
						hasteLeft2.setVisibility(View.VISIBLE);				
					}
					
					
					if (blessSpell[0] < 1) {
						
						blessRight.setVisibility(View.VISIBLE);				
					}				
					if (cureSpell[0] < 1) {
						
						cureRight.setVisibility(View.VISIBLE);				
					}			
					if (dodgeBlowSpell[0] < 1) {
						
						dodgeRight.setVisibility(View.VISIBLE);				
					}			
					if (mightyBlowSpell[0] < 1) {
						
						mbRight.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[0] < 2) {
						
						hasteRight1.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[0] < 1) {
						
						hasteRight1.setVisibility(View.VISIBLE);
						hasteRight2.setVisibility(View.VISIBLE);				
					}
	  	    	}
	  	    	
	  	    	if (playersFighting.equals("oneVsZero")) {
  	    		
	  	  	    	if (blessSpell[0] < 1) {
						
						blessLeft.setVisibility(View.VISIBLE);				
					}				
					if (cureSpell[0] < 1) {
						
						cureLeft.setVisibility(View.VISIBLE);				
					}			
					if (dodgeBlowSpell[0] < 1) {
						
						dodgeLeft.setVisibility(View.VISIBLE);				
					}			
					if (mightyBlowSpell[0] < 1) {
						
						mbLeft.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[0] < 2) {
						
						hasteLeft1.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[0] < 1) {
						
						hasteLeft1.setVisibility(View.VISIBLE);
						hasteLeft2.setVisibility(View.VISIBLE);				
					}
					
					
					if (blessSpell[1] < 1) {
						
						blessRight.setVisibility(View.VISIBLE);				
					}				
					if (cureSpell[1] < 1) {
						
						cureRight.setVisibility(View.VISIBLE);				
					}			
					if (dodgeBlowSpell[1] < 1) {
						
						dodgeRight.setVisibility(View.VISIBLE);				
					}			
					if (mightyBlowSpell[1] < 1) {
						
						mbRight.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[1] < 2) {
						
						hasteRight1.setVisibility(View.VISIBLE);				
					}			
					if (hasteSpell[1] < 1) {
						
						hasteRight1.setVisibility(View.VISIBLE);
						hasteRight2.setVisibility(View.VISIBLE);				
					}
	  	    	}
  	  	    }
		});	
	}
	
	public void skillsCheckLeft5() {
		
		//playersFightingTest();
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
  	  	    	ImageView blessLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftbless);
  	  	    	ImageView cureLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftcure);
  	  	    	ImageView dodgeLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftdodge);
  	  	    	ImageView mbLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftmb);
  	  	    	ImageView hasteLeft1 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste1);
  	  	    	ImageView hasteLeft2 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste2);
  	  	    	
  	  	    	blessLeft.setVisibility(View.INVISIBLE);
  	  	    	blessLeft.bringToFront();
  	  	    	blessLeft.setImageDrawable(null);
  	  	    	cureLeft.setVisibility(View.INVISIBLE);
  	  	    	cureLeft.bringToFront();
  	  	    	cureLeft.setImageDrawable(null);
  	  	    	dodgeLeft.setVisibility(View.INVISIBLE);
  	  	    	dodgeLeft.bringToFront();
  	  	    	dodgeLeft.setImageDrawable(null);
  	  	    	mbLeft.setVisibility(View.INVISIBLE);
  	  	    	mbLeft.bringToFront();
  	  	    	mbLeft.setImageDrawable(null);
  	  	    	hasteLeft1.setVisibility(View.INVISIBLE);
  	  	    	hasteLeft1.bringToFront();
  	  	    	hasteLeft1.setImageDrawable(null);
  	  	    	hasteLeft2.setVisibility(View.INVISIBLE);
  	  	    	hasteLeft2.bringToFront();
  	  	    	hasteLeft2.setImageDrawable(null);
  	  	    		
  	  	    	if (blessSpell[5] < 1) {						
					
					blessLeft.setVisibility(View.VISIBLE);				
				}				
				if (cureSpell[5] < 1) {						
					
					cureLeft.setVisibility(View.VISIBLE);				
				}			
				if (dodgeBlowSpell[5] < 1) {						
					
					dodgeLeft.setVisibility(View.VISIBLE);				
				}			
				if (mightyBlowSpell[5] < 1) {
					
					
					mbLeft.setVisibility(View.VISIBLE);				
				}			
				if (hasteSpell[5] < 2) {						
					
					hasteLeft1.setVisibility(View.VISIBLE);				
				}			
				if (hasteSpell[5] < 1) {						
					
					hasteLeft1.setVisibility(View.VISIBLE);						
					hasteLeft2.setVisibility(View.VISIBLE);				
				}
  	  	    }
		});	
	}
	
	public void skillsCheckLeft0() {
		
		//playersFightingTest();
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
  	  	    	ImageView blessLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftbless);
  	  	    	ImageView cureLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftcure);
  	  	    	ImageView dodgeLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftdodge);
  	  	    	ImageView mbLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftmb);
  	  	    	ImageView hasteLeft1 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste1);
  	  	    	ImageView hasteLeft2 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste2);
  	  	    	
  	  	    	blessLeft.setVisibility(View.INVISIBLE);
  	  	    	blessLeft.bringToFront();
  	  	    	blessLeft.setImageDrawable(null);
  	  	    	cureLeft.setVisibility(View.INVISIBLE);
  	  	    	cureLeft.bringToFront();
  	  	    	cureLeft.setImageDrawable(null);
  	  	    	dodgeLeft.setVisibility(View.INVISIBLE);
  	  	    	dodgeLeft.bringToFront();
  	  	    	dodgeLeft.setImageDrawable(null);
  	  	    	mbLeft.setVisibility(View.INVISIBLE);
  	  	    	mbLeft.bringToFront();
  	  	    	mbLeft.setImageDrawable(null);
  	  	    	hasteLeft1.setVisibility(View.INVISIBLE);
  	  	    	hasteLeft1.bringToFront();
  	  	    	hasteLeft1.setImageDrawable(null);
  	  	    	hasteLeft2.setVisibility(View.INVISIBLE);
  	  	    	hasteLeft2.bringToFront();
  	  	    	hasteLeft2.setImageDrawable(null);
  	    		
  	  	    	if (blessSpell[0] < 1) {
					
					blessLeft.setVisibility(View.VISIBLE);				
				}				
				if (cureSpell[0] < 1) {
					
					cureLeft.setVisibility(View.VISIBLE);				
				}			
				if (dodgeBlowSpell[0] < 1) {
					
					dodgeLeft.setVisibility(View.VISIBLE);				
				}			
				if (mightyBlowSpell[0] < 1) {
					
					mbLeft.setVisibility(View.VISIBLE);				
				}			
				if (hasteSpell[0] < 2) {
					
					hasteLeft1.setVisibility(View.VISIBLE);				
				}			
				if (hasteSpell[0] < 1) {
					
					hasteLeft1.setVisibility(View.VISIBLE);
					hasteLeft2.setVisibility(View.VISIBLE);				
				}
  	  	    }
		});	
	}
	
	public void skillsCheckLeft1() {
		
		//playersFightingTest();
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
  	  	    	
  	  	    	ImageView blessLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftbless);
  	  	    	ImageView cureLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftcure);
  	  	    	ImageView dodgeLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftdodge);
  	  	    	ImageView mbLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftmb);
  	  	    	ImageView hasteLeft1 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste1);
  	  	    	ImageView hasteLeft2 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste2);
  	  	    	
  	  	    	blessLeft.setVisibility(View.INVISIBLE);
  	  	    	blessLeft.bringToFront();
  	  	    	blessLeft.setImageDrawable(null);
  	  	    	cureLeft.setVisibility(View.INVISIBLE);
  	  	    	cureLeft.bringToFront();
  	  	    	cureLeft.setImageDrawable(null);
  	  	    	dodgeLeft.setVisibility(View.INVISIBLE);
  	  	    	dodgeLeft.bringToFront();
  	  	    	dodgeLeft.setImageDrawable(null);
  	  	    	mbLeft.setVisibility(View.INVISIBLE);
  	  	    	mbLeft.bringToFront();
  	  	    	mbLeft.setImageDrawable(null);
  	  	    	hasteLeft1.setVisibility(View.INVISIBLE);
  	  	    	hasteLeft1.bringToFront();
  	  	    	hasteLeft1.setImageDrawable(null);
  	  	    	hasteLeft2.setVisibility(View.INVISIBLE);
  	  	    	hasteLeft2.bringToFront();
  	  	    	hasteLeft2.setImageDrawable(null);
	  	    		
  	  	    	if (blessSpell[1] < 1) {						
					
					blessLeft.setVisibility(View.VISIBLE);				
				}				
				if (cureSpell[1] < 1) {
					
					cureLeft.setVisibility(View.VISIBLE);				
				}			
				if (dodgeBlowSpell[1] < 1) {
					
					dodgeLeft.setVisibility(View.VISIBLE);				
				}			
				if (mightyBlowSpell[1] < 1) {
					
					mbLeft.setVisibility(View.VISIBLE);				
				}			
				if (hasteSpell[1] < 2) {
					
					hasteLeft1.setVisibility(View.VISIBLE);				
				}			
				if (hasteSpell[1] < 1) {
					
					hasteLeft1.setVisibility(View.VISIBLE);
					hasteLeft2.setVisibility(View.VISIBLE);				
				}
  	  	    }
		});	
	}
	
	public void skillsCheckRight0() {
		
		//playersFightingTest();
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {  	  	    	
  	  	    	
  	  	    	ImageView blessRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightbless);
  	  	    	ImageView cureRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightcure);
  	  	    	ImageView dodgeRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightdodge);
  	  	    	ImageView mbRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightmb);
  	  	    	ImageView hasteRight1 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste1);
  	  	    	ImageView hasteRight2 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste2);
  	  	    	
  	  	    	blessRight.setVisibility(View.INVISIBLE);
  	  	    	blessRight.bringToFront();
  	  	    	blessRight.setImageDrawable(null);
  	  	    	cureRight.setVisibility(View.INVISIBLE);
  	  	    	cureRight.bringToFront();
  	  	    	cureRight.setImageDrawable(null);
  	  	    	dodgeRight.setVisibility(View.INVISIBLE);
  	  	    	dodgeRight.bringToFront();
  	  	    	dodgeRight.setImageDrawable(null);
  	  	    	mbRight.setVisibility(View.INVISIBLE);
  	  	    	mbRight.bringToFront();
  	  	    	mbRight.setImageDrawable(null);
  	  	    	hasteRight1.setVisibility(View.INVISIBLE);
  	  	    	hasteRight1.bringToFront();
  	  	    	hasteRight1.setImageDrawable(null);
  	  	    	hasteRight2.setVisibility(View.INVISIBLE);
  	  	    	hasteRight2.bringToFront();
  	  	    	hasteRight2.setImageDrawable(null);
					
				if (blessSpell[0] < 1) {						
					
					blessRight.setVisibility(View.VISIBLE);				
				}				
				if (cureSpell[0] < 1) {						
					
					cureRight.setVisibility(View.VISIBLE);				
				}			
				if (dodgeBlowSpell[0] < 1) {						
					
					dodgeRight.setVisibility(View.VISIBLE);				
				}			
				if (mightyBlowSpell[0] < 1) {						
					
					mbRight.setVisibility(View.VISIBLE);				
				}			
				if (hasteSpell[0] < 2) {						
					
					hasteRight1.setVisibility(View.VISIBLE);				
				}			
				if (hasteSpell[0] < 1) {						
					
					hasteRight1.setVisibility(View.VISIBLE);						
					hasteRight2.setVisibility(View.VISIBLE);				
				}
  	  	    }
		});	
	}
	
	public void skillsCheckRight1() {
		
		//playersFightingTest();
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {  	  	    	
  	  	    	
  	  	    	ImageView blessRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightbless);
  	  	    	ImageView cureRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightcure);
  	  	    	ImageView dodgeRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightdodge);
  	  	    	ImageView mbRight = (ImageView) findViewById(R.id.imageviewplayerbox4rightmb);
  	  	    	ImageView hasteRight1 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste1);
  	  	    	ImageView hasteRight2 = (ImageView) findViewById(R.id.imageviewplayerbox4righthaste2); 
  	  	    	
  	  	    	blessRight.setVisibility(View.INVISIBLE);
  	  	    	blessRight.bringToFront();
  	  	    	blessRight.setImageDrawable(null);
  	  	    	cureRight.setVisibility(View.INVISIBLE);
  	  	    	cureRight.bringToFront();
  	  	    	cureRight.setImageDrawable(null);
  	  	    	dodgeRight.setVisibility(View.INVISIBLE);
  	  	    	dodgeRight.bringToFront();
  	  	    	dodgeRight.setImageDrawable(null);
  	  	    	mbRight.setVisibility(View.INVISIBLE);
  	  	    	mbRight.bringToFront();
  	  	    	mbRight.setImageDrawable(null);
  	  	    	hasteRight1.setVisibility(View.INVISIBLE);
  	  	    	hasteRight1.bringToFront();
  	  	    	hasteRight1.setImageDrawable(null);
  	  	    	hasteRight2.setVisibility(View.INVISIBLE);
  	  	    	hasteRight2.bringToFront();
  	  	    	hasteRight2.setImageDrawable(null);
					
				if (blessSpell[1] < 1) {						
					
					blessRight.setVisibility(View.VISIBLE);				
				}				
				if (cureSpell[1] < 1) {						
					
					cureRight.setVisibility(View.VISIBLE);				
				}			
				if (dodgeBlowSpell[1] < 1) {						
					
					dodgeRight.setVisibility(View.VISIBLE);				
				}			
				if (mightyBlowSpell[1] < 1) {						
					
					mbRight.setVisibility(View.VISIBLE);				
				}			
				if (hasteSpell[1] < 2) {						
					
					hasteRight1.setVisibility(View.VISIBLE);				
				}			
				if (hasteSpell[1] < 1) {						
					
					hasteRight1.setVisibility(View.VISIBLE);						
					hasteRight2.setVisibility(View.VISIBLE);				
				}
  	  	    }
		});	
	}
	
	
	//=============================================================================================
	//SEPERATOR
	//=============================================================================================
	
	
	public void runActionsOnUi() {
		
		if (id == 0) {
  				
			playerNumberAttacking = 0;
		}
		else if (id == 1) {
			
			playerNumberAttacking = 1;
		}
		
		
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
		  		ContextThemeWrapper cw = new ContextThemeWrapper(Client2.this, R.layout.avatar_adapter);
		  		AlertDialog.Builder builder = new AlertDialog.Builder(cw, R.style.customalertdialog);
		  		
		  		//ORIGINAL WAY TO DO IT:
	  			//AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
	  			
	  			builder.setCancelable(false);	  			
	  			
	  			//builder.setTitle("Choose Action");
	  			
	  			Toast toast = Toast.makeText(Client2.this, "Choose An Action", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
	  			View view = toast.getView();
	  			view.setBackgroundResource(R.drawable.centerscroll3toast);
	  			toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER

	  			TextView text = (TextView) view.findViewById(android.R.id.message);
	  			//Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			text.setTypeface(typeFace);
	  			text.setTextColor(Color.parseColor("#FFFFFF"));
	  			//text.setRotation(-45);
	  			text.setGravity(Gravity.CENTER);
	  			
	  			toast.show();
	  			
	  			
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
									
									hideSystemUI();
	                        		
	                        		/*
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " attacks...");
									*/
									
									/*
									String str = "> " + ArrayOfPlayers.player[0] + " attacks...";
									sendToHost(str);																
									
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {						  	  	  			
						  	  	  			
						  	  	  			// CHOOSE PLAYER 
						  	  	  			attack();
											
											dialog.dismiss();
											
											hideSystemUI();
							  	  	  	}
						  	  	  	}, 1000);
						  	  	  	*/										
								}
	                        	
	                        	else if (item == 1) {
									
	                        		disarm();
									
									dialog.dismiss();
									
									hideSystemUI();
	                        		
	                        		/*
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " attempts to disarm...");
									*/
									
									/*
									String str = "> " + ArrayOfPlayers.player[0] + " attempts to disarm...";
									sendToHost(str);									
									
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {						  	  	  			
						  	  	  			
						  	  	  			// CHOOSE PLAYER
						  	  	  			disarm();
											
											dialog.dismiss();
											
											hideSystemUI();
							  	  	  	}
						  	  	  	}, 1000);
						  	  	  	*/									
								}
	                        	
	                        	else if (item == 2) {
									
	                        		haste();
	                        		
	                        		dialog.dismiss();
	                        		
	                        		hideSystemUI();
	                        		
	                        		/*
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts haste...");
									*/
									
	                        		/*
									String str = "> " + ArrayOfPlayers.player[0] + " casts haste...";
									sendToHost(str);									
									
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {						  	  	  			
						  	  	  			
											haste();
											
											dialog.dismiss();
											
											hideSystemUI();
							  	  	  	}
						  	  	  	}, 1000);
						  	  	  	*/								
								}
	                        	
	                        	else if (item == 3) {
									
	                        		cure();
									
									dialog.dismiss();
									
									hideSystemUI();
	                        		
	                        		/*
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts cure...");
									*/
									
									/*
									String str = "> " + ArrayOfPlayers.player[0] + " casts cure...";
									sendToHost(str);									
									
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {						  	  	  			
						  	  	  			
											cure();
											
											dialog.dismiss();
											
											hideSystemUI();
							  	  	  	}
						  	  	  	}, 1000);
						  	  	  	*/										
								}
	                        	
	                        	else if (item == 4) {
	                        		
	                        		bless();
									
									dialog.dismiss();
									
									hideSystemUI();
	                        		
									/*
									centerscrolltext.setVisibility(View.VISIBLE);													
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts bless...");
									*/
									
									/*
									String str = "> " + ArrayOfPlayers.player[0] + " casts bless...";
									sendToHost(str);									
									
									
									final Handler h = new Handler();
						  	  	  	h.postDelayed(new Runnable() {		  	  	  			
						  	  	  			
						  	  	  		@Override
							  	  	  	public void run() {						  	  	  			
						  	  	  			
						  	  	  			// CHOOSE PLAYER
						  	  	  			bless();
											
											dialog.dismiss();
											
											hideSystemUI();
							  	  	  	}
						  	  	  	}, 1000);
						  	  	  	*/								
								}
								
								//((AlertDialog) dialog).getButton(dialog.BUTTON1).setGravity(Gravity.CENTER);
								//SET TEXT SIXE IN XML						
								//View messageText = ((TextView) dialog).findViewById(R.id.title);		  		
					            //((TextView) messageText).setGravity(Gravity.CENTER);
					            //messageText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
								
								//isInvokingService = "true";
	                        	
	                            dialog.dismiss();
	                            
	                            hideSystemUI();
	                        }
	                    });	            
	            
	            AlertDialog alert = builder.create();
	            alert.show();	            
	            
	            
	            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	            lp.copyFrom(alert.getWindow().getAttributes());
	            
	            if (getResources().getDisplayMetrics().densityDpi==160) {
	            	
	            	lp.width = 525;
	            }
	            else {
	            	
	            	lp.width = 1050;
	            }
	            
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
		
		if (id == 0) {
				
			playerNumberAttacking = 0;
		}
		else if (id == 1) {
			
			playerNumberAttacking = 1;
		}
		
		
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
		  		
		  		if (id == 0) {
	  	  				
		  			centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + ", you are disarmed. What do you want to do? ");
  	  			}
  	  			else if (id == 1) {
  	  				
  	  				centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[1] + ", you are disarmed. What do you want to do? ");
  	  			}
  	  	    					
				
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
	  			  		ContextThemeWrapper cw = new ContextThemeWrapper(Client2.this, R.layout.avatar_adapter);
	  			  		AlertDialog.Builder builder = new AlertDialog.Builder(cw, R.style.customalertdialog);
	  			  		
	  			  		//ORIGINAL WAY TO DO IT:
	  		  			//AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
	  		  			
	  		  			builder.setCancelable(false);	  			
	  		  			
	  		  			//builder.setTitle("Choose Action");
	  		  			
		  		  		Toast toast = Toast.makeText(Client2.this, "Choose An Action", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
			  			View view = toast.getView();
			  			view.setBackgroundResource(R.drawable.centerscroll3toast);
			  			toast.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
	
			  			TextView text = (TextView) view.findViewById(android.R.id.message);
			  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
			  			text.setTypeface(typeFace);
			  			text.setTextColor(Color.parseColor("#FFFFFF"));
			  			//text.setRotation(-45);
			  			text.setGravity(Gravity.CENTER);
			  			
			  			toast.show();
	  		  			
	  		  			
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
						  	  	  			
						  	  	  			hideSystemUI();
	  		                        		
	  		                        		/*
	  		                        		centerscrolltext.setVisibility(View.VISIBLE);													
									  		centerscrolltext.startAnimation(animAlphaText);
											centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " attacks...");
											*/
											
						  	  	  			/*
											String str = "> " + ArrayOfPlayers.player[0] + " attacks...";
											sendToHost(str);											
											
											
											final Handler h = new Handler();
								  	  	  	h.postDelayed(new Runnable() {		  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {								  	  	  			
								  	  	  			
													//punch();
								  	  	  			
								  	  	  			// CHOOSE PLAYER
								  	  	  			attack();
								  	  	  			
								  	  	  			dialog.dismiss();
								  	  	  			
								  	  	  			hideSystemUI();
									  	  	  	}
								  	  	  	}, 1000);
								  	  	  	*/									
	  									}
	  		                        	
	  		                        	else if (item == 1) {	  		                        														
											
											if (hasteSpell[playerNumberAttacking] < 1) {
												
												dialog.dismiss();
												
												hideSystemUI();
												
												
												AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this, R.style.customalertdialog);
											      
												//alert.setTitle(ArrayOfPlayers.player[playerNumberAttacking] + ", you have already used your Haste spells.");
												alert.setMessage(ArrayOfPlayers.player[playerNumberAttacking] + ", you have already used your Haste spells.");

												/*
									  	    	alert.setMessage("something");
									  	    	*/	    	
										    	
										    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											    	public void onClick(DialogInterface dialog, int whichButton) {
											    		
											    		//hideNavigation();
											    		
											    		disarmedAction();
											    		
											    		dialog.dismiss();
											    		
											    		hideSystemUI();
											    	}
										    	});								    	
										    	alert.show();									
												
												
										    	/*
												centerscrolltext.setVisibility(View.VISIBLE);
										  		centerscrolltext.startAnimation(animAlphaText);
										  		centerscrolltext.append("\n" + "> You have already used your Haste spells.");
												
												disarmedAction();
												*/
											}												
											
											else if ((hasteSpell[playerNumberAttacking] > 0) && !(disarmedTurnStart[playerNumberAttacking] + 2 == ArrayOfTurn.turn[0])) {
												
												haste();
												
												//disarmedAction();
							  	  	  			
							  	  	  			dialog.dismiss();
							  	  	  			
							  	  	  			hideSystemUI();
												
												/*
												centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);
												centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts haste...");
												*/
												
							  	  	  			/*
												String str = "> " + ArrayOfPlayers.player[0] + " casts haste...";
												sendToHost(str);												
												
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
									  	  	  			haste();
									  	  	  			
									  	  	  			dialog.dismiss();
									  	  	  		
									  	  	  			hideSystemUI();
										  	  	  	}
									  	  	  	}, 1000);
									  	  	  	*/												
											}
											
											else {
												
												disarmedAction();
												
												//haste();
												
												dialog.dismiss();
												
												hideSystemUI();
											}										
	  									}
	  		                        	
	  		                        	else if (item == 2) {	  										
	  		                        		
											if (cureSpell[playerNumberAttacking] > 0) {
												
												cure();
							  	  	  			
							  	  	  			dialog.dismiss();
							  	  	  			
							  	  	  			hideSystemUI();
												
												/*
												centerscrolltext.setVisibility(View.VISIBLE);													
										  		centerscrolltext.startAnimation(animAlphaText);
												centerscrolltext.append("\n" + "> " + ArrayOfPlayers.player[0] + " casts cure...");
												*/
												
							  	  	  			/*
												String str = "> " + ArrayOfPlayers.player[0] + " casts cure...";
												sendToHost(str);												
												
												
												final Handler h = new Handler();
									  	  	  	h.postDelayed(new Runnable() {		  	  	  			
									  	  	  			
									  	  	  		@Override
										  	  	  	public void run() {
									  	  	  			
									  	  	  			cure();
									  	  	  			
									  	  	  			dialog.dismiss();
									  	  	  			
									  	  	  			hideSystemUI();
										  	  	  	}
									  	  	  	}, 1000);
									  	  	  	*/													
											}
											
											else if (cureSpell[playerNumberAttacking] < 1) {
												
												dialog.dismiss();
												
												hideSystemUI();
												
												
												AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this, R.style.customalertdialog);
											      
												//alert.setTitle(ArrayOfPlayers.player[playerNumberAttacking] + ", you have already used your Cure spell.");
												alert.setMessage(ArrayOfPlayers.player[playerNumberAttacking] + ", you have already used your Cure spell.");

												/*
									  	    	alert.setMessage("something");
									  	    	*/	    	
										    	
										    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											    	public void onClick(DialogInterface dialog, int whichButton) {
											    		
											    		//hideNavigation();
											    		
											    		disarmedAction();
											    		
											    		dialog.dismiss();
											    		
											    		hideSystemUI();
											    	}
										    	});								    	
										    	alert.show();
												
												//disarmedAction();
											}									
	  									}	  									
	  									
	  									//isInvokingService = "true";
	  		                        	
	  		                            //dialog.dismiss();
	  		                        	
	  		                        	//hideSystemUI();
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
	  			
	  			String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " attacks...";
				sendToHost(str);
	  			
				
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
	  			
	  			if (hasteSpell[playerNumberAttacking] > 0) {
	  				
	  				String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " casts haste...";
					sendToHost(str);
	  				
  					//hasteGraphic();  					
  					
  					String str2 = "hasteGraphic";
  					sendToHost(str2);  					
  					
  					
  					final Handler h1 = new Handler();
  		  	  	  	h1.postDelayed(new Runnable() {
  		  	  	  			
  		  	  	  		@Override
  			  	  	  	public void run() {
  		  	  	  			
  		  	  	  			hasteSpell[playerNumberAttacking] = hasteSpell[playerNumberAttacking] - 1;
  		  	  	  			
  		  	  	  			String str3 = "usedHaste";
  		  	  	  			sendToHost(str3);
  		  	  	  			
  		  	  	  			
  		  	  	  			//skillsCheck();
  		  	  	  			
  		  	  	  			String str4 = "skillsCheckBoth";
  		  	  	  			sendToHost(str4);
  		  	  	  			
  		  	  	  			
  		  	  	  			//stopGraphics();
  		  	  	  			
  		  	  	  			String str5 = "stopGraphics";
  		  	  	  			sendToHost(str5);
  		  	  	  			
  		  	  	  			
  		  	  	  			if (canHasDisarmed[playerNumberAttacking].equals("no")) {	  		  	  	  				
  		  	  	  				
  		  	  	  				// CHOOSE PLAYER
  		  	  	  				
  		  	  	  				ishasteused = "yes";					  	  	  			

				  	  	  		final TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
				  	  	  		hasteGraphic.setVisibility(View.INVISIBLE);
			  	  	  			
				  	  	  		/*
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
								centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> TWO attacks...");
								*/
								String str6 = "> TWO attacks...";
								sendToHost(str6);								
																
								
								final Handler h2 = new Handler();
					  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
					  	  	  			/*
						  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
								  		centerscrolltext.startAnimation(animAlphaText);
										centerscrolltext.append("\n" + "> FIRST attack...");
										*/
										String str7 = "> FIRST attack...";
										sendToHost(str7);										
																				
										
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
  		  	  	  			
  		  	  	  			else if (canHasDisarmed[playerNumberAttacking].equals("yes")) {
		  		  	  	  		
		  		  	  	  		canHasDisarmed[playerNumberAttacking] = "no";
		  		  	  	  		
		  		  	  	  		String str6 = "canHasDisarmed :" + "no";
		  		  	  	  		sendToHost(str6);
		  		  	  	  		

				  	  	  		//final TextView hasteGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
				  	  	  		//hasteGraphic.setVisibility(View.INVISIBLE);
		  		  	  	  		
		  		  	  	  		
		  		  	  	  		/*
		  		  	  	  		TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
		  		  	  	  		disarmedtextright.setVisibility(View.INVISIBLE);
		  		  	  	  		*/
		  		  	  	  		
		  		  	  	  		//disarmedGraphicInvisible();
		  		  	  	  		
		  		  	  	  		if (playersFighting.equals("fiveVsZero") || playersFighting.equals("zeroVsFive")) {	  	  	    	
					  	  	    	
						  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);				  	  	
							  	  	disarmedtextright.setVisibility(View.INVISIBLE);
							  	  	disarmedtextright.bringToFront();
					  	  	    }
					  	  	    
					  	  	    else if (playersFighting.equals("fiveVsOne") || playersFighting.equals("oneVsFive")) {	  	  	    	
					  	  	    	
						  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);				  	  	
							  	  	disarmedtextright.setVisibility(View.INVISIBLE);
							  	  	disarmedtextright.bringToFront();
					  	  	    }
					  	  	    
					  	  	    else if (playersFighting.equals("zeroVsOne")) {	  	  	    	
						  	  	    
						  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);				  	  	
							  	  	disarmedtextright.setVisibility(View.INVISIBLE);
							  	  	disarmedtextright.bringToFront();
					  	  	    }
					  	  	    else if (playersFighting.equals("oneVsZero")) {	  	  	    	
						  	  	    
						  	  	    TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
							  	  	disarmedtextleft.setVisibility(View.INVISIBLE);				  	  	
							  	  	disarmedtextleft.bringToFront();
					  	  	    }
		  		  	  	  		
		  		  	  	  		
			  	  	  			
				  	  	  		/*
				  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
						  		centerscrolltext.startAnimation(animAlphaText);
								centerscrolltext.append("\n" + "> You are no longer disarmed.");
								*/
								String str7 = "> " +  ArrayOfPlayers.player[playerNumberAttacking] + " is no longer disarmed.";
								sendToHost(str7);
								
								
								final Handler h = new Handler();
					  	  	  	h.postDelayed(new Runnable() {		  	  	  			
					  	  	  			
					  	  	  		@Override
						  	  	  	public void run() {
					  	  	  			
						  	  	  		ishasteused = "no";
						  				
						  				String str2 = "secondroundofhasteused";
						  				sendToHost(str2);
						  				
						  				issecondroundofhasteused = "yes";
					  	  	  			
					  	  	  			
					  	  	  			String str8 = "hasteCureDisarmWithBlessDisarmNoBlessBlessCompleted";
					  	  	  			sendToHost(str8);
						  	  	  	}
					  	  	  	}, 2000);						  	  	  																						
							}	  		  	  	  			
  		  	  	  		}
  		  	  	  	}, 6000);	  				
	  			}	  			
	  			
	  			else {
  	  	  			
	  				AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this, R.style.customalertdialog);
				      
					//alert.setTitle(ArrayOfPlayers.player[playerNumberAttacking] + ", you have already used your Haste spells.");
					alert.setMessage(ArrayOfPlayers.player[playerNumberAttacking] + ", you have already used your Haste spells.");

					/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		
				    		//hideNavigation();
				    		
				    		runActionsOnUi();
				    		
				    		dialog.dismiss();
				    		
				    		hideSystemUI();
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
  	  	    	
		
				if (cureSpell[playerNumberAttacking] > 0) {
					
					String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " casts cure...";
					sendToHost(str);
					
					//DON'T NEED THIS BECAUSE PICK OPPONENT BEFORE PICKING CURE:
					//playersFighting = "cure";
					
					cureSpell[playerNumberAttacking] = cureSpell[playerNumberAttacking] - 1;
					
					String str2 = "usedCure";
					sendToHost(str2);
					
					
					//skillsCheck();
					
					String str3 = "skillsCheckBoth";
					sendToHost(str3);
					
					
					//cureGraphic();
					
					String str4 = "cureGraphic";
					sendToHost(str4);			
		  	  	  			
					
	  	  	  		final Handler h2 = new Handler();
		  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
			  	  	  		//final TextView cureGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  	  	  			//cureGraphic.setVisibility(View.INVISIBLE);
		  	  	  			
		  	  	  			//stopGraphics();
		  	  	  			
		  	  	  			String str5 = "stopGraphics";
		  	  	  			sendToHost(str5);
		  	  	  			
		  	  	  			
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
									
									
									ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
					  	  	    	chatBlankButton.bringToFront();									
				  	  	  		}
				  	  	  	}, 750);					  	  	  		
		  	  	  		}
		  	  	  	}, 6000);// CHANGED FROM 3000 TO 6000 BECAUSE DIE WAS COMING IN BEFORE GRAPHIC WAS FINISHED.										
				} 
				
				else {
					
					AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this, R.style.customalertdialog);
				      
					//alert.setTitle(ArrayOfPlayers.player[playerNumberAttacking] + ", you have already used your Cure spell.");
					alert.setMessage(ArrayOfPlayers.player[playerNumberAttacking] + ", you have already used your Cure spell.");

					/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		
				    		//hideNavigation();
				    		
				    		runActionsOnUi();
				    		
				    		dialog.dismiss();
				    		
				    		hideSystemUI();
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
  	  	    	
  	  	    	//ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
  	  	    	//chatBlankButton.bringToFront();
  	  	    	
  	  	    	
	  	  	    final TextView centerscrolltext = (TextView) findViewById(R.id.textviewcenterscrolltext);
	  			//centerscrolltext.setMovementMethod(new ScrollingMovementMethod());		
	  			
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			centerscrolltext.setTypeface(typeFace);
	  			
	  			
	  			String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " attempts to disarm...";
				sendToHost(str);
				
					
				if (blessSpell[playerNumberAttacking] > 0) {
						
					AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this, R.style.customalertdialog);
		  			
					alert.setCancelable(false);
					
		  	    	//alert.setTitle(ArrayOfPlayers.player[playerNumberAttacking] + ", do you want to use your bless spell?");
		  	    	alert.setMessage(ArrayOfPlayers.player[playerNumberAttacking] + ", do you want to use your bless spell?");

		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  	    		public void onClick(DialogInterface dialog, int whichButton) {
		  	    			
		  	    			//hideNavigation();
		  		    		
		  		    		blessSpell[playerNumberAttacking] = blessSpell[playerNumberAttacking] - 1;
		  		    		
		  		    		String str2 = "usedBless";
		  		    		sendToHost(str2);
		  		    		
							
		  		    		//skillsCheck();
		  		    		
		  		    		String str3 = "skillsCheckBoth";
		  		    		sendToHost(str3);
		  		    		
		  		    		
		  		    		dialog.dismiss();
		  		    		
		  		    		hideSystemUI();
		  		    		
		  		    		
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
		  	    			
		  	    			hideSystemUI();
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
		  		String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " uses a bless...";
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
  	  	    	
		
				if (blessSpell[playerNumberAttacking] > 0) {				
					
					String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " casts bless...";
					sendToHost(str);
					
					
					isblessrolled = "yes";
					
					blessSpell[playerNumberAttacking] = blessSpell[playerNumberAttacking] - 1;
					
					String str2 = "usedBless";
					sendToHost(str2);
					
					
					//skillsCheck();
					
					String str3 = "skillsCheckBoth";
					sendToHost(str3);
					
					
					//blessGraphic();	  			 	  	  							  	  	  		
					
					String str4 = "blessGraphic";
					sendToHost(str4);
					
					  	  	  		
		  	  	  	final Handler h2 = new Handler();
		  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
			  	  	  		//final TextView blessGraphic = (TextView)findViewById(R.id.textviewspellgraphic);
		  	  	  			//blessGraphic.setVisibility(View.INVISIBLE);
		  	  	  			
		  	  	  			stopGraphics();
		  	  	  			
		  	  	  			String str5 = "stopGraphics";
		  	  	  			sendToHost(str5);
		  	  	  			
		  	  	  			
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
					
					AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this, R.style.customalertdialog);
				      
					//alert.setTitle(ArrayOfPlayers.player[playerNumberAttacking] + ", you have already used your Bless spell.");
					alert.setMessage(ArrayOfPlayers.player[playerNumberAttacking] + ", you have already used your Bless spell.");

					/*
		  	    	alert.setMessage("something");
		  	    	*/	    	
			    	
			    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog, int whichButton) {
				    		
				    		//hideNavigation();
				    		
				    		runActionsOnUi();
				    		
				    		dialog.dismiss();
				    		
				    		hideSystemUI();
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
	  			
		  		img.setImageDrawable(null);
		  		
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
	  							
  							String str3 = "rollDodgeFor :" + "criticalHit";
  							sendToHost(str3);
  							
  							String str4 = "rollDge";
  							sendToHost(str4);	  							
  							
  							
  							//playerAskedToDodgeCritHit[0] = "yes";
  							
  							//checkForDodgeRoll();
	  					}
  					
	  					// IF NO DODGE BLOW:	  					
	  	  	  			else {
	  	  	  				
		  	  	  			final Handler h = new Handler();
				  	  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			if (mightyBlowSpell[playerNumberAttacking] > 0 && ishasteused.equals("no") && isblessrolled.equals("no")) {
								
										AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this, R.style.customalertdialog);
							  			
										alert.setCancelable(false);
										
							  	    	//alert.setTitle(ArrayOfPlayers.player[playerNumberAttacking] + ", do you want to use Mighty Blow?");
							  	    	alert.setMessage(ArrayOfPlayers.player[playerNumberAttacking] + ", do you want to use Mighty Blow?");

							  	    	/*
							  	    	alert.setMessage("something");
							  	    	*/	  	    	
							  	    	
							  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							  		    	public void onClick(DialogInterface dialog, int whichButton) {							  		    		
							  		    		
							  		    		//hideNavigation();
							  		    		
							  		    		mightyBlowSpell[playerNumberAttacking] = mightyBlowSpell[playerNumberAttacking] - 1;
						  		    		
							  		    		String str5 = "usedMightyBlow";
						  		    			sendToHost(str5);
							  		    		
												
							  		    		//skillsCheck();
							  		    		
							  		    		String str6 = "skillsCheckBoth";
							  		    		sendToHost(str6);
												
							  		    		
							  		    		criticalHitMightyBlowPartOne();							  					
							  		    		
							  		    		dialog.dismiss();
							  		    		
							  		    		hideSystemUI();
							  		    	}
							  	    	});
							  	    	
							  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
							          	  public void onClick(DialogInterface dialog, int whichButton) {
							          		
							          		//hideNavigation();
							          		  
							          		criticalHitPartOne();											
							          		
							          		dialog.dismiss();
							          		
							          		hideSystemUI();
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
  	  			
	  	  	  			
  	  			if (mightyBlowSpell[playerNumberAttacking] > 0 && ishasteused.equals("no") && isblessrolled.equals("no")) {
			
					AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this, R.style.customalertdialog);
		  			
					alert.setCancelable(false);
					
		  	    	//alert.setTitle(ArrayOfPlayers.player[playerNumberAttacking] + ", do you want to use Mighty Blow?");
		  	    	alert.setMessage(ArrayOfPlayers.player[playerNumberAttacking] + ", do you want to use Mighty Blow?");

		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  		    	public void onClick(DialogInterface dialog, int whichButton) {							  		    		
		  		    		
		  		    		//hideNavigation();
		  		    		
		  		    		mightyBlowSpell[playerNumberAttacking] = mightyBlowSpell[playerNumberAttacking] - 1;
	  		    		
		  		    		String str = "usedMightyBlow";
	  		    			sendToHost(str);
		  		    		
							
		  		    		//skillsCheck();
		  		    		
		  		    		String str2 = "skillsCheckBoth";
		  		    		sendToHost(str2);
							
		  		    		
		  		    		criticalHitMightyBlowPartOne();							  					
		  		    		
		  		    		dialog.dismiss();
		  		    		
		  		    		hideSystemUI();
		  		    	}
		  	    	});
		  	    	
		  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
		          	  public void onClick(DialogInterface dialog, int whichButton) {
		          		
		          		//hideNavigation();
		          		  
		          		criticalHitPartOne();											
		          		
		          		dialog.dismiss();
		          		
		          		hideSystemUI();
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
		
		img.setImageDrawable(null);
		
		
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
				  		String str3 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls twice for critical hit damage...";
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
		
		img.setImageDrawable(null);
		
		
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
				  		String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls twice for critical hit damage...";
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
						String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + cureResult + ".";
						sendToHost(str);
						
						
						ArrayOfHitPoints.hitpoints[playerNumberAttacking] = (ArrayOfHitPoints.hitpoints[playerNumberAttacking] + cureResult);
						
						String str2 = "Cure.hitpoints :" + ArrayOfHitPoints.hitpoints[playerNumberAttacking];
						sendToHost(str2);
						
						
						final TextView computerHitPointsTextView = (TextView)findViewById(R.id.textviewhitpointsright);
						computerHitPointsTextView.setTypeface(typeFace);
						computerHitPointsTextView.setText(String.valueOf(ArrayOfHitPoints.hitpoints[playerNumberAttacking]));
		    			//playerHitPointsTextView.bringToFront();
		    			Animation animPulsingAnimation = AnimationUtils.loadAnimation(Client2.this, R.anim.pulsinganimation);
		    			computerHitPointsTextView.startAnimation(animPulsingAnimation);		    					    			
		    			
		    			
						final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
			  	  	  			computerHitPointsTextView.clearAnimation();
			  	  	  			
			  	  	  			//String str3 = "hasteCureDisarmWithBlessDisarmNoBlessBlessCompleted";
			  	  	  			String str3 = "attackDamageCriticalHitDamageCriticalHitMightyBlowDamageCompleted";
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
	  			
	  			
	  			if (canHasDisarmed[playerNumberAttacking].equals("no")) {
	  				
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
								String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + ArrayOfAttackResult.attackResult[0] + ".";
								sendToHost(str);
			  	  	  		}
			  	  	  		
			  	  	  		else if (canHasDisarmed[playerNumberAttacked].equals("yes")) {				  	  	  		
				  	  	  		
			  	  	  			/*
					  	  	  	centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ", +2 for opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 2) + ".");
								*/
								String str2 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + ArrayOfAttackResult.attackResult[0] + ", +2 for opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 2) + ".";
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
											String str3 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + "'s" + " attack hits.";
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
											String str4 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + "'s" + " attack misses.";
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
											String str6 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + "'s" + " attack hits.";
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
											String str7 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + "'s" + " attack misses.";
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
	  			
	  			else if (canHasDisarmed[playerNumberAttacking].equals("yes")) {
	  				
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
								String str9 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + ArrayOfAttackResult.attackResult[0] + ", -1 for being disarmed = " + (ArrayOfAttackResult.attackResult[0] - 1) + ".";
								sendToHost(str9);
			  	  	  		}
			  	  	  		
			  	  	  		else if (canHasDisarmed[playerNumberAttacked].equals("yes")) {				  	  	  		
				  	  	  		
			  	  	  			/*
					  	  	  	centerscrolltext.setVisibility(View.VISIBLE);													
						  		centerscrolltext.startAnimation(animAlphaText);			  		
								centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ", +1 for being disarmed and opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 1) + ".");
								*/
								String str10 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + ArrayOfAttackResult.attackResult[0] + ", +1 for being disarmed and opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 1) + ".";
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
											String str11 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + "'s" + " punch hits.";
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
											String str12 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + "'s" + " punch misses.";
											sendToHost(str12);
											
											
											final Handler h6 = new Handler();
								  	  	  	h6.postDelayed(new Runnable() {	  	  	  			
								  	  	  			
								  	  	  		@Override
									  	  	  	public void run() {  		
							  	  	  				
									  	  	  		//String str13 = "attackDamageCriticalHitDamageCriticalHitMightyBlowDamageCompleted";THIS WAS DIRECTING TO A SECOND ATTACK(LIKE FOR HASTE)
								  	  	  			//String str13 = "hasteCureDisarmWithBlessDisarmNoBlessBlessCompleted";BROKEN FOR CLIENT DISARMED--(NBA) A DISARMED & CAN PUNCH FOREVER. THINK OTHER IS FIXED NOW?
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
											String str14 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + "'s" + " punch hits.";
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
											String str15 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + "'s" + " punch misses.";
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
						
  					String str = "rollDodgeFor :" + "damage2";
					sendToHost(str);
  					
  					String str2 = "rollDge";
					sendToHost(str2);						
					
					
					//playerAskedToDodgeDamage[0] = "yes";
					
					//checkForDodgeRoll();
				}
	  			
	  			// IF NO DODGE BLOW:
	  			
	  			else {
	  			
	  				final Handler h = new Handler();
		  	  	  	h.postDelayed(new Runnable() {		  	  	  			
		  	  	  			
		  	  	  		@Override
			  	  	  	public void run() {
		  	  	  			
			  	  	  		if (mightyBlowSpell[playerNumberAttacking] > 0 && ishasteused.equals("no") && isblessrolled.equals("no") && issecondroundofhasteused.equals("no")) {							
								
								AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this, R.style.customalertdialog);
					  			
								alert.setCancelable(false);
								
					  	    	//alert.setTitle(ArrayOfPlayers.player[playerNumberAttacking] + ", do you want to use Mighty Blow?");
					  	    	alert.setMessage(ArrayOfPlayers.player[playerNumberAttacking] + ", do you want to use Mighty Blow?");

					  	    	/*
					  	    	alert.setMessage("something");
					  	    	*/	  	    	
					  	    	
					  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					  	    		public void onClick(DialogInterface dialog, int whichButton) {
					  		    		
					  		    		//hideNavigation();
					  		    		
					  		    		mightyBlowSpell[playerNumberAttacking] = mightyBlowSpell[playerNumberAttacking] - 1;
					  		    		
					  		    		String str3 = "usedMightyBlow";
					  		    		sendToHost(str3);
					  		    		
										
					  		    		//skillsCheck();
					  		    		
					  		    		String str4 = "skillsCheckBoth";
					  		    		sendToHost(str4);
										
										
					  		    		mightyBlow();										
					  		    		
					  		    		dialog.dismiss();
					  		    		
					  		    		hideSystemUI();
					  		    	}
					  	    	});
					  	    	
					  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
					  	    		public void onClick(DialogInterface dialog, int whichButton) {					  	    			
					          		  
					  	    			//hideNavigation();
					          		  
					  	    			damagePartTwo();					          		  
					          		  
					  	    			dialog.dismiss();
					  	    			
					  	    			hideSystemUI();
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
	  			
	  			
	  			if (mightyBlowSpell[playerNumberAttacking] > 0 && ishasteused.equals("no") && isblessrolled.equals("no") && issecondroundofhasteused.equals("no")) {								
					
					AlertDialog.Builder alert = new AlertDialog.Builder(Client2.this, R.style.customalertdialog);
		  			
					alert.setCancelable(false);
					
		  	    	//alert.setTitle(ArrayOfPlayers.player[playerNumberAttacking] + ", do you want to use Mighty Blow?");
		  	    	alert.setMessage(ArrayOfPlayers.player[playerNumberAttacking] + ", do you want to use Mighty Blow?");

		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  	    		public void onClick(DialogInterface dialog, int whichButton) {
		  		    		
		  		    		//hideNavigation();
		  		    		
		  		    		mightyBlowSpell[playerNumberAttacking] = mightyBlowSpell[playerNumberAttacking] - 1;
		  		    		
		  		    		String str3 = "usedMightyBlow";
		  		    		sendToHost(str3);
		  		    		
							
		  		    		//skillsCheck();
		  		    		
		  		    		String str4 = "skillsCheckBoth";
		  		    		sendToHost(str4);
							
							
		  		    		mightyBlow();										
		  		    		
		  		    		dialog.dismiss();
		  		    		
		  		    		hideSystemUI();
		  		    	}
		  	    	});
		  	    	
		  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
		  	    		public void onClick(DialogInterface dialog, int whichButton) {					  	    			
		          		  
		  	    			//hideNavigation();
		          		  
		  	    			damagePartTwo();					          		  
		          		  
		  	    			dialog.dismiss();
		  	    			
		  	    			hideSystemUI();
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
		
		img.setImageDrawable(null);
		
		
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
		
		img.setImageDrawable(null);
		
		
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
				String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls for damage...";
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
								
								
								ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
			  		  			chatBlankButton.bringToFront();
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
		  		
		  		img.setImageDrawable(null);
	  			
		  		
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
	  					String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " must roll to see if it hit itself...";
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
					  			
					  			
					  			ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
			  		  			chatBlankButton.bringToFront();
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
				  		String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls "	+ ArrayOfAttackResult.attackResult[0]	+ ", +2 for the Bless Spell = "	+ (ArrayOfAttackResult.attackResult[0] + 2) + ".";
				  		sendToHost(str);
				  		
				  		
				  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (ArrayOfAttackResult.attackResult[0] >= 15) {
									
				  	  	  			//canHasDisarmed[playerNumberAttacked] = "yes";
				  	  	  			
				  	  	  			String str4 = "playerDisarmed :" + playerNumberAttacked;
				  	  	  			sendToHost(str4);
				  	  	  			
				  	  	  			whoDisarmedWho();
				  	  	  			
				  	  	  			
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
									  		String str5 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + "'s" + " attempt to disarm misses.";
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
				  		String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls "	+ ArrayOfAttackResult.attackResult[0] + ".";
				  		sendToHost(str);
						
				  		
				  		final Handler h2 = new Handler();
			  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (ArrayOfAttackResult.attackResult[0] >= 17) {
									
					  	  	  		//canHasDisarmed[playerNumberAttacked] = "yes";
					  	  	  		
					  	  	  		//disarmedTurnStart[playerNumberAttacked] = ArrayOfTurn.turn[0];
					  	  	  		
									
					  	  	  		String str2 = "playerDisarmed :" + playerNumberAttacked;
					  	  	  		sendToHost(str2);
					  	  	  		
					  	  	  		whoDisarmedWho();
				  	  	  			
				  	  	  			
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
									  		String str5 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + "'s" + " attempt to disarm misses.";
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
							  		String str7 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls "	+ "a critical miss...";
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
	
	public void whoDisarmedWho() {
		
		if (playerNumberAttacking == 0) {
			
			if (playerNumberAttacked == 5) {
				
				String str = "ZeroDisarmed5";
  	  			sendToHost(str);
			}
			else if (playerNumberAttacked == 1) {
				
				String str = "zroDisarmed1";
  	  			sendToHost(str);
			}
		}
		
		else if (playerNumberAttacking == 1) {
			
			if (playerNumberAttacked == 5) {
				
				String str = "OneDisarmed5";
  	  			sendToHost(str);
			}
			else if (playerNumberAttacked == 0) {
				
				String str = "oeDisarmed0";
  	  			sendToHost(str);
			}
		}
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
					  		String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls "	+ ArrayOfAttackResult.attackResult[0] + ", +2 for Bless Spell = " + (ArrayOfAttackResult.attackResult[0] + 2) + ".";
					  		sendToHost(str);
						}
						
		  	  	  		else if (canHasDisarmed[playerNumberAttacked].equals("yes")) {					
							
		  	  	  			/*
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
					  		centerscrolltext.append("\n" + "> You roll " + ArrayOfAttackResult.attackResult[0] + ", +4 for Bless Spell and opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 4) + ".");
					  		*/
					  		String str2 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls "	+ ArrayOfAttackResult.attackResult[0] + ", +4 for Bless Spell and opponent being disarmed = " + (ArrayOfAttackResult.attackResult[0] + 4) + ".";
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
										String str3 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + "'s"	+ " attack hits.";
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
										String str4 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + "'s"	+ " attack misses.";
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
										String str6 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + "'s"	+ " attack hits.";
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
										String str7 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + "'s"	+ " attack misses.";
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
				  		String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + ArrayOfAttackResult.attackResult[0] + ".";
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
							  		String str2 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " hits itself.";
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
											String str3 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls for damage...";
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
							  		String str4 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " did not hit itself, but must roll to see if it loses it's weapon...";
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
		
		img.setImageDrawable(null);
		
		
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
								
								
								ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
			  		  			chatBlankButton.bringToFront();
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
					  			
					  			
					  			ImageButton chatBlankButton = (ImageButton) findViewById(R.id.textviewcenterscrolltextbutton);
			  		  			chatBlankButton.bringToFront();
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
				  		String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + ArrayOfAttackResult.attackResult[0] + ".";
				  		sendToHost(str);
				  		
				  		
						if (ArrayOfAttackResult.attackResult[0] >= 17) {
							
							canHasDisarmed[playerNumberAttacking] = "yes";							
							
							String str2 = "canHasDisarmed :" + "yes";
	  		  	  	  		sendToHost(str2);				
							
							
							didHumanCriticalMiss[playerNumberAttacking] = "yes";
							
							String str3 = "didHumanCriticalMiss :" + "yes";
							sendToHost(str3);
							
							
							//THIS IS SENT BACK FROM HOST FROM canHasDisarmed ABOVE:
							//disarmedTurnStart[0] = ArrayOfTurn.turn[0];
							
							
							/*
							TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
		  	  	  			disarmedtextleft.setVisibility(View.VISIBLE);
				  	  	  	disarmedtextleft.bringToFront();
				  	  	  	*/
							
							//disarmedGraphicVisible();
							
							if (playersFighting.equals("fiveVsZero") || playersFighting.equals("zeroVsFive")) {	  	  	    	
				  	  	    	
					  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);				  	  	
						  	  	disarmedtextright.setVisibility(View.VISIBLE);
						  	  	disarmedtextright.bringToFront();
				  	  	    }
				  	  	    
				  	  	    else if (playersFighting.equals("fiveVsOne") || playersFighting.equals("oneVsFive")) {	  	  	    	
				  	  	    	
					  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);				  	  	
						  	  	disarmedtextright.setVisibility(View.VISIBLE);
						  	  	disarmedtextright.bringToFront();
				  	  	    }
				  	  	    
				  	  	    else if (playersFighting.equals("zeroVsOne")) {	  	  	    	
					  	  	    
					  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);				  	  	
						  	  	disarmedtextright.setVisibility(View.VISIBLE);
						  	  	disarmedtextright.bringToFront();
				  	  	    }
				  	  	    else if (playersFighting.equals("oneVsZero")) {	  	  	    	
					  	  	    
					  	  	    TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
						  	  	disarmedtextleft.setVisibility(View.VISIBLE);				  	  	
						  	  	disarmedtextleft.bringToFront();
				  	  	    }
				  	  	  	
				  	  	  	
							final Handler h2 = new Handler();
				  	  	  	h2.postDelayed(new Runnable() {		  	  	  			
				  	  	  			
				  	  	  		@Override
					  	  	  	public void run() {
				  	  	  			
				  	  	  			/*
					  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
							  		centerscrolltext.append("\n" + "> You are disarmed.");
							  		*/
							  		String str4 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " is disarmed.";
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
							  		String str6 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " holds on to it's weapon.";
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
	
	public void clearDisarmGraphicAndSkills() {//DUPLICATE METHOD FROM HOST
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {					  	  	    	
  	  	    	
	  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);		  	  	
		  	  	disarmedtextright.setVisibility(View.INVISIBLE);
		  	  	disarmedtextright.bringToFront();
		
		  	  	TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);		  	  	
		  	  	disarmedtextleft.setVisibility(View.INVISIBLE);
		  	  	disarmedtextleft.bringToFront();
		  	  	
		  	  	
		  	  	ImageView blessLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftbless);
	  	    	ImageView cureLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftcure);
	  	    	ImageView dodgeLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftdodge);
	  	    	ImageView mbLeft = (ImageView) findViewById(R.id.imageviewplayerbox4leftmb);
	  	    	ImageView hasteLeft1 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste1);
	  	    	ImageView hasteLeft2 = (ImageView) findViewById(R.id.imageviewplayerbox4lefthaste2);
	  	    	
	  	    	blessLeft.setVisibility(View.INVISIBLE);
	  	    	blessLeft.bringToFront();
	  	    	blessLeft.setImageDrawable(null);
	  	    	cureLeft.setVisibility(View.INVISIBLE);
	  	    	cureLeft.bringToFront();
	  	    	cureLeft.setImageDrawable(null);
	  	    	dodgeLeft.setVisibility(View.INVISIBLE);
	  	    	dodgeLeft.bringToFront();
	  	    	dodgeLeft.setImageDrawable(null);
	  	    	mbLeft.setVisibility(View.INVISIBLE);
	  	    	mbLeft.bringToFront();
	  	    	mbLeft.setImageDrawable(null);
	  	    	hasteLeft1.setVisibility(View.INVISIBLE);
	  	    	hasteLeft1.bringToFront();
	  	    	hasteLeft1.setImageDrawable(null);
	  	    	hasteLeft2.setVisibility(View.INVISIBLE);
	  	    	hasteLeft2.bringToFront();
	  	    	hasteLeft2.setImageDrawable(null);
  	  	    }
		});
	}
	
	/*
	public void disarmedGraphicVisible() {
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {					  	  	    	
  	  	    	
				if (playerNumberAttacking == 0) {
				
		  	  	    if (playersFighting.equals("fiveVsZero") || playersFighting.equals("zeroVsFive")) {	  	  	    	
		  	  	    	
			  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);				  	  	
				  	  	disarmedtextright.setVisibility(View.VISIBLE);
				  	  	disarmedtextright.bringToFront();
		  	  	    }
		  	  	    
		  	  	    //else if (playersFighting.equals("fiveVsOne") || playersFighting.equals("oneVsFive")) {	  	  	    	
		  	  	    	
		  	  	    //}
		  	  	    
		  	  	    else if (playersFighting.equals("zeroVsOne")) {	  	  	    	
			  	  	    
			  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);				  	  	
				  	  	disarmedtextright.setVisibility(View.VISIBLE);
				  	  	disarmedtextright.bringToFront();
		  	  	    }
		  	  	    else if (playersFighting.equals("oneVsZero")) {	  	  	    	
			  	  	    
			  	  	    TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
				  	  	disarmedtextleft.setVisibility(View.VISIBLE);				  	  	
				  	  	disarmedtextleft.bringToFront();
		  	  	    }
				}
				
				else if (playerNumberAttacking == 1) {
					
					
		  	  	    //if (playersFighting.equals("fiveVsZero") || playersFighting.equals("zeroVsFive")) {	  	  	    	
		  	  	    	
		  	  	    //}
		  	  	    
		  	  	    if (playersFighting.equals("fiveVsOne") || playersFighting.equals("oneVsFive")) {	  	  	    	
		  	  	    	
			  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);				  	  	
				  	  	disarmedtextright.setVisibility(View.VISIBLE);
				  	  	disarmedtextright.bringToFront();
		  	  	    }		  	  	    
		  	  	    else if (playersFighting.equals("zeroVsOne")) {	  	  	    	
			  	  	    
			  	  	    TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
				  	  	disarmedtextleft.setVisibility(View.VISIBLE);				  	  	
				  	  	disarmedtextleft.bringToFront();
		  	  	    }
		  	  	    else if (playersFighting.equals("oneVsZero")) {	  	  	    	
			  	  	    
			  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);				  	  	
				  	  	disarmedtextright.setVisibility(View.VISIBLE);
				  	  	disarmedtextright.bringToFront();
		  	  	    }
				}
  	  	    }
		});
	}
	
	public void disarmedGraphicInvisible() {
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {					  	  	    	
  	  	    	
				if (playerNumberAttacking == 0) {
				
		  	  	    if (playersFighting.equals("fiveVsZero") || playersFighting.equals("zeroVsFive")) {	  	  	    	
		  	  	    	
			  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);				  	  	
				  	  	disarmedtextright.setVisibility(View.INVISIBLE);
				  	  	disarmedtextright.bringToFront();
		  	  	    }
		  	  	    
		  	  	    //else if (playersFighting.equals("fiveVsOne") || playersFighting.equals("oneVsFive")) {	  	  	    	
		  	  	    	
		  	  	    //}
		  	  	    
		  	  	    else if (playersFighting.equals("zeroVsOne")) {	  	  	    	
			  	  	    
			  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);				  	  	
				  	  	disarmedtextright.setVisibility(View.INVISIBLE);
				  	  	disarmedtextright.bringToFront();
		  	  	    }
		  	  	    else if (playersFighting.equals("oneVsZero")) {	  	  	    	
			  	  	    
			  	  	    TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
				  	  	disarmedtextleft.setVisibility(View.INVISIBLE);				  	  	
				  	  	disarmedtextleft.bringToFront();
		  	  	    }
				}
				
				else if (playerNumberAttacking == 1) {
					
					
		  	  	    //if (playersFighting.equals("fiveVsZero") || playersFighting.equals("zeroVsFive")) {	  	  	    	
		  	  	    	
		  	  	    //}
		  	  	    
		  	  	    if (playersFighting.equals("fiveVsOne") || playersFighting.equals("oneVsFive")) {	  	  	    	
		  	  	    	
			  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);				  	  	
				  	  	disarmedtextright.setVisibility(View.INVISIBLE);
				  	  	disarmedtextright.bringToFront();
		  	  	    }		  	  	    
		  	  	    else if (playersFighting.equals("zeroVsOne")) {	  	  	    	
			  	  	    
			  	  	    TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
				  	  	disarmedtextleft.setVisibility(View.INVISIBLE);				  	  	
				  	  	disarmedtextleft.bringToFront();
		  	  	    }
		  	  	    else if (playersFighting.equals("oneVsZero")) {	  	  	    	
			  	  	    
			  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);				  	  	
				  	  	disarmedtextright.setVisibility(View.INVISIBLE);
				  	  	disarmedtextright.bringToFront();
		  	  	    }
				}
  	  	    }
		});
	}
	*/
	
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
	  	  	  			
		  	  	  		if (canHasDisarmed[playerNumberAttacking].equals("no")) {
							
		  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Client2.this);
		  	  	  			//SharedPreferences.Editor editor = preferences.edit();
		  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
		  	  	  			
		  	  	  			/*
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll " + attackDamage + ".");
							*/
							String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + attackDamage + ".";
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
							
		  	  	  		else if (canHasDisarmed[playerNumberAttacking].equals("yes")) {
							
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
							String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + attackDamage + ", -2 damage for punch = " + attackDamageDisarmed + " damage.";
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
	  	  	  	}, 4000);
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
	  	  	  			
		  	  	  		if (canHasDisarmed[playerNumberAttacking].equals("no")) {
		  	  	  			
		  	  	  			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Client2.this);
		  	  	  			//SharedPreferences.Editor editor = preferences.edit();
		  	  	  			int attackDamage = preferences.getInt("attackDamage", 0);
		  	  	  			
		  	  	  			/*
			  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll " + attackDamage	+ ".");
							*/
							String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + attackDamage + ".";
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
						
		  	  	  		else if (canHasDisarmed[playerNumberAttacking].equals("yes")) {
							
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
							String str3 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + attackDamage + ", -2 damage for punch = " + attackDamageDisarmed + " damage.";
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
						String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + attackDamage + ".";
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
			  	  	  			
			  	  	  			
								if (ArrayOfHitPoints.hitpoints[playerNumberAttacking] <= 0) {
									
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
				String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ".";
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
								String str2 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " makes second roll...";
								sendToHost(str2);
								
								
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
  	  	    	
	  			/*
	  			centerscrolltext.setVisibility(View.VISIBLE);
		  		centerscrolltext.startAnimation(animAlphaText);
				centerscrolltext.append("\n" + "> You roll " + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] + ".");
	  			*/
				String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] + ".";
				sendToHost(str);
				
	  			
	  			final Handler h1 = new Handler();
	  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
	  	  	  			
	  	  	  		@Override
		  	  	  	public void run() {
	  	  	  			
		  	  	  		if (canHasDisarmed[playerNumberAttacking].equals("no")) {							
							
		  	  	  			/*
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ".");
							*/
							String str2 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ".";
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
							
		  	  	  		else if (canHasDisarmed[playerNumberAttacking].equals("yes")) {							
							
		  	  	  			/*
							centerscrolltext.setVisibility(View.VISIBLE);
					  		centerscrolltext.startAnimation(animAlphaText);
							centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ", -2 damage for punch = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) + ".");
							*/
							String str4 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ", -2 damage for punch = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) + ".";
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
						String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ".";
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
										String str2 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " makes second roll...";
										sendToHost(str2);
										
										
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
	  	  	  			
	  	  	  			/*
		  	  	  		centerscrolltext.setVisibility(View.VISIBLE);
				  		centerscrolltext.startAnimation(animAlphaText);
						centerscrolltext.append("\n" + "> You roll " + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] + ".");
	  	  	  			*/
						String str = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls " + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0] + ".";
						sendToHost(str);
						
						
		  	  	  		final Handler h1 = new Handler();
			  	  	  	h1.postDelayed(new Runnable() {		  	  	  			
			  	  	  			
			  	  	  		@Override
				  	  	  	public void run() {
			  	  	  			
				  	  	  		if (canHasDisarmed[playerNumberAttacking].equals("no")) {							
									
				  	  	  			/*
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ".");
									*/
									String str2 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ".";
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
									
								if (canHasDisarmed[playerNumberAttacking].equals("yes")) {								
									
									/*
									centerscrolltext.setVisibility(View.VISIBLE);
							  		centerscrolltext.startAnimation(animAlphaText);
									centerscrolltext.append("\n" + "> You roll a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ", -2 damage for punch = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) + " damage.");
									*/
									String str5 = "> " + ArrayOfPlayers.player[playerNumberAttacking] + " rolls a total " + (ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) + ", -2 damage for punch = " + ((ArrayOfCriticalHitAttackDamageOne.criticalHitAttackDamageOne[0] + ArrayOfCriticalHitAttackDamageTwo.criticalHitAttackDamageTwo[0]) - 2) + " damage.";
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
		
		critHitWithMB = "yes";
		
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
			
			String str2 = "secondroundofhasteused";
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
				  	  	  	
				  	  	  	img1.setImageDrawable(null);
			
				  	  	  	// Use a blank drawable to hide the imageview animation:
				  	  	  	ImageView img2 = (ImageView)findViewById(R.id.sixsidedanimation);
				  	  	  	img2.setBackgroundResource(R.drawable.sixsixrightleftrotateblank);
				  	  	  	img2.setImageResource(R.drawable.sixsixrightleftrotateblank);
				  	  	  	
				  	  	  	img2.setImageDrawable(null);
		  	  	  			
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
		
		//playerAttackingTest();
	}
	
	
	
	
	
	
	
	/*
	public void playerAttackingTest() {//DELETE IF NOT USED!!!!!!!!!!!!!

		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				Toast.makeText(Client2.this, String.valueOf(playerNumberAttacked), Toast.LENGTH_SHORT).show();
			}
		});
	}
	*/
	
	/*
	public void test() {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {			
				
				Toast.makeText(Client2.this, "WORKING!!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	*/
	
	
	
	
	
	
	
	
	/*
	public void setAvatarForOtherClients() {
		
		if (id == 1) {
			
			ImageView clientAvatar = (ImageView) findViewById(R.id.imageviewavatarright);
			
			if (ArrayOfAvatars.avatar[0].equals("computer")){
				
				ArrayOfAvatars.avatar[1] = "computer";
				clientAvatar.setBackgroundResource(R.drawable.computer);
			}
			else if (ArrayOfAvatars.avatar[0].equals("crossedswords")){
				
				ArrayOfAvatars.avatar[1] = "crossedswords";
				clientAvatar.setBackgroundResource(R.drawable.crossedswords2);
			}
			else if (ArrayOfAvatars.avatar[0].equals("stonedead")){
				
				ArrayOfAvatars.avatar[1] = "stonedead";
				clientAvatar.setBackgroundResource(R.drawable.stonedead2);
			}
		}
	}
	*/
	
	
	
	
	
	
	
	
	
	public String getPath(Uri uri) {
		
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }
	
	public void sendImage0(){
		
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		        try {
		        	
		    		InetAddress address = InetAddress.getByName(hostIP);
			        socket0 = new Socket(address, 3000);
		    		
		    		
			        //File file = new File(image_path);
			        try (InputStream is = new BufferedInputStream(new FileInputStream(imagefile));
			                DataOutputStream dos = new DataOutputStream(socket0.getOutputStream());) {
			            dos.writeLong(imagefile.length()); // <-- remember to read a long on server.
			            int val;
			            while ((val = is.read()) != -1) {
			                dos.write(val);
			            }
			            dos.flush();
			            dos.close();
			            is.close();
			        }
		        	
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		});

		thread.start();
	}
	
	public void sendImage1(){
		
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		        try {
		        	
		        	InetAddress address = InetAddress.getByName(hostIP);
			        socket0 = new Socket(address, 3001);
		    		
		    		
			        //File file = new File(image_path);
			        try (InputStream is = new BufferedInputStream(new FileInputStream(imagefile));
			                DataOutputStream dos = new DataOutputStream(socket0.getOutputStream());) {
			            dos.writeLong(imagefile.length()); // <-- remember to read a long on server.
			            int val;
			            while ((val = is.read()) != -1) {
			                dos.write(val);
			            }
			            dos.flush();
			            dos.close();
			            is.close();
			        }
		        	
		        	/*
		    		InetAddress address = InetAddress.getByName(hostIP);
			        socket0 = new Socket(address, 3001);
		    		
		    		DataOutputStream out = new DataOutputStream(
	                        socket0.getOutputStream());
	                //out.writeChar('I');
	                DataInputStream dis = new DataInputStream(new FileInputStream(imagefile));
	                ByteArrayOutputStream ao = new ByteArrayOutputStream();
	                int read = 0;
	                byte[] buf = new byte[1024];
	                while ((read = dis.read(buf)) > -1) {
	                    ao.write(buf, 0, read);
	                }

	                out.writeLong(ao.size());
	                out.write(ao.toByteArray());
	                out.flush();
	                out.close();
	                dis.close();
		        	*/
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		});

		thread.start();
	}
	
	
	public void ReceiveImage50(){
		try {
			
			ServerSocket clientSocket50 = new ServerSocket(4000);
			File file = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files/avatar5");//WAS: /storage/sdcard0/avatar5
			try (Socket s = clientSocket50.accept();
			        DataInputStream dis = new DataInputStream(
			                s.getInputStream());
			        OutputStream fos = new BufferedOutputStream(
			                new FileOutputStream(file));) {
			    long arrlen = dis.readLong();
			    for (long i = 0; i < arrlen; i++) {
			        fos.write(dis.read());
			    }
			    fos.flush();
			    dis.close();
			    fos.close();
			    clientSocket50.close();
			}
			
			} catch (Exception e) {
		       
		    }
	}
	
	public void ReceiveImage51(){
		try {
			
			ServerSocket clientSocket51 = new ServerSocket(4001);
			File file = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files/avatar5");//WAS: /storage/sdcard0/avatar5
			try (Socket s = clientSocket51.accept();
			        DataInputStream dis = new DataInputStream(
			                s.getInputStream());
			        OutputStream fos = new BufferedOutputStream(
			                new FileOutputStream(file));) {
			    long arrlen = dis.readLong();
			    for (long i = 0; i < arrlen; i++) {
			        fos.write(dis.read());
			    }
			    fos.flush();
			    dis.close();
			    fos.close();
			    clientSocket51.close();
			}
			
			} catch (Exception e) {
		       
		    }
	}
	
	public void ReceiveImage0(){
		try {
			
			ServerSocket clientSocket0 = new ServerSocket(4002);
			File file = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files/avatar0");//WAS: /storage/sdcard0/avatar0
			try (Socket s = clientSocket0.accept();
			        DataInputStream dis = new DataInputStream(
			                s.getInputStream());
			        OutputStream fos = new BufferedOutputStream(
			                new FileOutputStream(file));) {
			    long arrlen = dis.readLong();
			    for (long i = 0; i < arrlen; i++) {
			        fos.write(dis.read());
			    }
			    fos.flush();
			    dis.close();
			    fos.close();
			    clientSocket0.close();
			}
			
			} catch (Exception e) {
		       
		    }
	}
	
	public void ReceiveImage1(){
		try {
			
			ServerSocket clientSocket1 = new ServerSocket(4003);
			File file = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files/avatar1");//WAS: /storage/sdcard0/avatar1
			try (Socket s = clientSocket1.accept();
			        DataInputStream dis = new DataInputStream(
			                s.getInputStream());
			        OutputStream fos = new BufferedOutputStream(
			                new FileOutputStream(file));) {
			    long arrlen = dis.readLong();
			    for (long i = 0; i < arrlen; i++) {
			        fos.write(dis.read());
			    }
			    fos.flush();
			    dis.close();
			    fos.close();
			    clientSocket1.close();
			}
			
			} catch (Exception e) {
		       
		    }
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
	
	
	
	
	
	
	/*
	class sendBullShit implements Runnable {

		@Override
		public void run() {
			
  			try {
  				InetAddress serverAddr = InetAddress.getByName(hostIP);// WAS: SERVER_IP
  				  		  	  			
  	  			socket0 = new Socket(serverAddr, 2100);
  	  			
  	  	  	    
  	  			BufferedWriter bufferedWriter;
  				
  				bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket0.getOutputStream()));
  				
				bufferedWriter.write(encImage);
				bufferedWriter.flush();
	  			bufferedWriter.close();
	  			
  	  			
  	  			//PrintWriter out = new PrintWriter(new BufferedWriter(
	  					//new OutputStreamWriter(socket0.getOutputStream())),
	  					//true);
  	  			
  	  			//out.println("Blahblah");
  	  			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	*/
	
	
	
	
	
	
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
						
						
						//setAvatarForOtherClients();
					}
					
					
					else if (read.contains("hostComputer")) {
						
						ArrayOfAvatars.avatar[5] = "computer";
					}
					else if (read.contains("hostCrossedswords")) {
	
						ArrayOfAvatars.avatar[5] = "crossedswords";
					}
					else if (read.contains("hostStonedead")) {
						
						ArrayOfAvatars.avatar[5] = "stonedead";
					}
					else if (read.contains("hostCustom")) {
						
						ArrayOfAvatars.avatar[5] = "custom";
						
						
						if (id == 0) {
							
							ReceiveImage50();
						}
						else if (id == 1) {
							
							ReceiveImage51();
						}
					}
					else if (read.contains("0Computer")) {
						
						ArrayOfAvatars.avatar[0] = "computer";
					}
					else if (read.contains("0crosswords")) {
						
						ArrayOfAvatars.avatar[0] = "crossedswords";
					}
					else if (read.contains("0stonded")) {
						
						ArrayOfAvatars.avatar[0] = "stonedead";
					}
					else if (read.contains("0cstom")) {
						
						ArrayOfAvatars.avatar[0] = "custom";
						
						
						ReceiveImage0();
					}
					else if (read.contains("1cmpter")) {
						
						ArrayOfAvatars.avatar[1] = "computer";
					}
					else if (read.contains("1Crsswrds")) {
						
						ArrayOfAvatars.avatar[1] = "crossedswords";
					}
					else if (read.contains("1Stned")) {
						
						ArrayOfAvatars.avatar[1] = "stonedead";
					}
					else if (read.contains("1Cstm")) {
						
						ArrayOfAvatars.avatar[1] = "custom";
						
						
						ReceiveImage1();
					}
					
					
					else if (read.contains("playerDeadYet5")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						playerDeadYet[5]=part2;																		
					}
					else if (read.contains("playerDeadYet0")) {
	
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						playerDeadYet[0]=part2;																		
					}
					else if (read.contains("playerDeadYet1")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						playerDeadYet[1]=part2;																		
					}
					else if (read.contains("playerDeadYet2")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						playerDeadYet[2]=part2;																		
					}
					else if (read.contains("playerDeadYet3")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						playerDeadYet[3]=part2;																
					}
					else if (read.contains("rollInitiative")) {
						
						rollInitiative();						
					}					
					else if (read.contains("rerllInitiative")) {			
						
						reRollInitiative();						
					}
					else if (read.contains("finishInitiative")) {			
						
						finishInitiative();						
					}
					/*
					else if (read.contains("myInitiativeTransition")) {			
						
						myInitiativeTransition();						
					}
					*/
					else if (read.contains("FirstSubscript")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						firstsubscript=Integer.parseInt(part2);											
					}
					else if (read.contains("Secondsbscript")) {
	
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						secondsubscript=Integer.parseInt(part2);											
					}
					else if (read.contains("Thirdsuscript")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						thirdsubscript=Integer.parseInt(part2);											
					}
					else if (read.contains("Fourthsubcript")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						fourthsubscript=Integer.parseInt(part2);											
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
					else if (read.contains("PLayerName0")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						ArrayOfPlayers.player[0]=part2;
					}
					else if (read.contains("PlAyerName1")) {
	
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						ArrayOfPlayers.player[1]=part2;
					}
					else if (read.contains("PlaYerName2")) {
	
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						ArrayOfPlayers.player[2]=part2;
					}
					else if (read.contains("PlayErName3")) {
	
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						ArrayOfPlayers.player[3]=part2;																		
					}
					else if (read.contains("GameEngine2Player")) {
						
						gameEngine2Player();																		
					}
					else if (read.contains("gameEngineMultiPlayer")) {
						
						gameEngineMultiPlayer();																		
					}
					else if (read.contains("gameEngine3V350")) {
						
						gameEngine3V350();
					}
					else if (read.contains("gAmeEngine3V351")) {
	
						gameEngine3V351();
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
					else if (read.contains("Trn3V35")) {
						
						turn3V35();																		
					}
					else if (read.contains("PlyrsFighting")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						playersFighting = part2;																		
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
						/*
						else {
							
							test();
						}
						*/
					}					
					else if (read.contains("displayTrn")) {
						
						displayTurn();																	
					}
					else if (read.contains("runActionsOnUi")) {
						
						runActionsOnUi();																	
					}
					
					else if (read.contains("chooseOpponent")) {
						
						chooseOpponent();																	
					}
					
					else if (read.contains("forScrollTitleChat")) {
						
						forScrollTitleChat();																	
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
						
						changeClient0HitPoints();
					}
					else if (read.contains("1ArrayOfHitPoints.hitpoints[1]")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						ArrayOfHitPoints.hitpoints[1]=Integer.parseInt(part2);
						
						changeClient1HitPoints();
					}
					else if (read.contains("2ArrayOfHitPoints.hitpoints[2]")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						ArrayOfHitPoints.hitpoints[2]=Integer.parseInt(part2);
						
						//changeClientHitPoints();
					}
					else if (read.contains("3ArrayOfHitPoints.hitpoints[3]")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						ArrayOfHitPoints.hitpoints[3]=Integer.parseInt(part2);
						
						//changeClientHitPoints();
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
					
					
					
					else if (read.contains("usedBless0")) {
						
						blessSpell[0] = blessSpell[0] - 1;
					}
					else if (read.contains("usedBless1")) {
						
						blessSpell[1] = blessSpell[1] - 1;
					}
					else if (read.contains("usedBless5")) {
						
						blessSpell[5] = blessSpell[5] - 1;
					}
					
					
					else if (read.contains("usedHaste0")) {
						
						hasteSpell[0] = hasteSpell[0] - 1;																
					}
					else if (read.contains("usedHaste1")) {
						
						hasteSpell[1] = hasteSpell[1] - 1;																
					}
					else if (read.contains("usedHaste5")) {
						
						hasteSpell[5] = hasteSpell[5] - 1;																
					}
					
					
					else if (read.contains("usedCure0")) {
						
						cureSpell[0] = cureSpell[0] - 1;
					}
					else if (read.contains("usedCure1")) {
						
						cureSpell[1] = cureSpell[1] - 1;
					}
					else if (read.contains("usedCure5")) {
						
						cureSpell[5] = cureSpell[5] - 1;
					}
					
					
					else if (read.contains("usedMightyBlow0")) {
						
						mightyBlowSpell[0] = mightyBlowSpell[0] - 1;
					}
					else if (read.contains("usedMightyBlow1")) {
						
						mightyBlowSpell[1] = mightyBlowSpell[1] - 1;
					}
					else if (read.contains("usedMightyBlow5")) {
						
						mightyBlowSpell[5] = mightyBlowSpell[5] - 1;
					}
					
					
					
					else if (read.contains("skillsCheckBoth")) {
						
						skillsCheckBoth();																	
					}
					else if (read.contains("skillsCheckLeft5")) {
						
						skillsCheckLeft5();																	
					}
					else if (read.contains("skillsCheckLeft0")) {
						
						skillsCheckLeft0();																	
					}
					else if (read.contains("skillsCheckLeft1")) {
						
						skillsCheckLeft1();																	
					}
					else if (read.contains("skillsCheckRight0")) {
						
						skillsCheckRight0();																	
					}
					else if (read.contains("skillsCheckRight1")) {
						
						skillsCheckRight1();														
					}
					
					
					
					else if (read.contains("disarmedTurnStart")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						disarmedTurnStart[0]=Integer.parseInt(part2);//USES [0] IN DISARMEDACTION SO CLIENT DOESN'T USE HASTE ON 2ND RD OF BEING DISARMED.				
					}
					
					else if (read.contains("DidHumanCriticalMiss0")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						didHumanCriticalMiss[0]=part2;											
					}
					else if (read.contains("dIdHumanCriticalMiss1")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						didHumanCriticalMiss[1]=part2;											
					}
					else if (read.contains("diDHumanCriticalMiss2")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						didHumanCriticalMiss[2]=part2;											
					}
					else if (read.contains("didhumanCriticalMiss3")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						didHumanCriticalMiss[3]=part2;											
					}
					else if (read.contains("didHUmanCriticalMiss4")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						didHumanCriticalMiss[4]=part2;											
					}
					else if (read.contains("didHuManCriticalMiss5")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];	
												
						didHumanCriticalMiss[5]=part2;											
					}
					
					else if (read.contains("CanHasDisarmed0")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];		
												
						canHasDisarmed[0]=part2;
						
						if (numberOfPlayers == 2) {
						
							if (part2.equals("no")) {
								
								runOnUiThread(new Runnable() {
						  	  	    @Override
						  	  	    public void run() {
						  	  	    	
						  	  	    	TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
			  		  	  	  			disarmedtextright.setVisibility(View.INVISIBLE);
						  	  	    	
			  		  	  	  			/* WAS THIS:
						  	  	    	TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
						  	  	    	disarmedtextleft.setVisibility(View.INVISIBLE);
						  	  	    	*/
						  	  	    }
								});
							}
							else if (part2.equals("yes")) {
								
								runOnUiThread(new Runnable() {
						  	  	    @Override
						  	  	    public void run() {
						  	  	    	
						  	  	    	TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
			  		  	  	  			disarmedtextright.setVisibility(View.VISIBLE);
						  	  	    	
			  		  	  	  			/* WAS THIS:
						  	  	    	TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
						  	  	    	disarmedtextleft.setVisibility(View.INVISIBLE);
						  	  	    	*/
						  	  	    }
								});
							}
						}
						
						else if (numberOfPlayers == 3) {
							
							if (part2.equals("no")) {
								
								runOnUiThread(new Runnable() {
						  	  	    @Override
						  	  	    public void run() {					  	  	    	
						  	  	    	
							  	  	    if (playersFighting.equals("fiveVsZero") || playersFighting.equals("zeroVsFive")) {
							  	  	    	
							  	  	    	TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
							  	  	    	disarmedtextright.setVisibility(View.INVISIBLE);
							  	  	    	disarmedtextright.bringToFront();
							  	  	    }								  	  	    
							  	  	    //else if (playersFighting.equals("fiveVsOne") || playersFighting.equals("oneVsFive")) {
							  	  	    	
							  	  	    //	TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
							  	  	    //	disarmedtextright.setVisibility(View.INVISIBLE);
							  	  	    //	disarmedtextright.bringToFront();
							  	  	    //}								  	  	    
							  	  	    else if (playersFighting.equals("zeroVsOne")) {
							  	  	    	
								  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
							  	  	    	disarmedtextright.setVisibility(View.INVISIBLE);
							  	  	    	disarmedtextright.bringToFront();
							  	  	    }
							  	  	    else if (playersFighting.equals("oneVsZero")) {
							  	  	    	
								  	  	    TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);										  	  	
									  	  	disarmedtextleft.setVisibility(View.INVISIBLE);
									  	  	disarmedtextleft.bringToFront();
							  	  	    }
							  	  	    
										
										//disarmedGraphicInvisible();
						  	  	    }
								});
							}
							else if (part2.equals("yes")) {
								
								runOnUiThread(new Runnable() {
						  	  	    @Override
						  	  	    public void run() {
						  	  	    	
							  	  	    if (playersFighting.equals("fiveVsZero") || playersFighting.equals("zeroVsFive")) {
							  	  	    	
								  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
							  	  	    	disarmedtextright.setVisibility(View.VISIBLE);
							  	  	    	disarmedtextright.bringToFront();
							  	  	    }								  	  	    
							  	  	    //else if (playersFighting.equals("fiveVsOne") || playersFighting.equals("oneVsFive")) {
							  	  	    	
							  	  	    //	TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
							  	  	    //	disarmedtextright.setVisibility(View.VISIBLE);
							  	  	    //	disarmedtextright.bringToFront();
							  	  	    //}								  	  	    
							  	  	    else if (playersFighting.equals("zeroVsOne")) {
							  	  	    	
								  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);
							  	  	    	disarmedtextright.setVisibility(View.VISIBLE);
							  	  	    	disarmedtextright.bringToFront();
							  	  	    }
							  	  	    else if (playersFighting.equals("oneVsZero")) {
							  	  	    	
								  	  	    TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);										  	  	
									  	  	disarmedtextleft.setVisibility(View.VISIBLE);
									  	  	disarmedtextleft.bringToFront();
							  	  	    }
							  	  	    
						  	  	    	
						  	  	    	//disarmedGraphicVisible();							  	  	    	
						  	  	    }
								});
							}
						}
					}
					else if (read.contains("cAnHasDisarmed1")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];//GETTING ARRAYOUTOFBOUNDSEXCEPTION		
												
						canHasDisarmed[1]=part2;
						
						if (numberOfPlayers == 3) {
						
							if (part2.equals("no")) {
								
								runOnUiThread(new Runnable() {
						  	  	    @Override
						  	  	    public void run() {
						  	  	    	
						  	  	    	
							  	  	    //if (playersFighting.equals("fiveVsZero") || playersFighting.equals("zeroVsFive")) {
							  	  	    	
							  	  	    //}							  	  	    
							  	  	    if (playersFighting.equals("fiveVsOne") || playersFighting.equals("oneVsFive")) {
							  	  	    	
							  	  	    	TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);									  	  	
									  	  	disarmedtextright.setVisibility(View.INVISIBLE);
									  	  	disarmedtextright.bringToFront();
							  	  	    }
							  	  	    else if (playersFighting.equals("zeroVsOne")) {
							  	  	    	
								  	  	    TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);								  	  	    
								  	  	    disarmedtextleft.setVisibility(View.INVISIBLE);
								  	  	    disarmedtextleft.bringToFront();
							  	  	    }
							  	  	    else if (playersFighting.equals("oneVsZero")) {
							  	  	    	
								  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);									  	  	
									  	  	disarmedtextright.setVisibility(View.INVISIBLE);
									  	  	disarmedtextright.bringToFront();
							  	  	    }
							  	  	    
							  	  	    
							  	  	    //disarmedGraphicInvisible();
						  	  	    }
								});
							}
							else if (part2.equals("yes")) {
								
								runOnUiThread(new Runnable() {
						  	  	    @Override
						  	  	    public void run() {
						  	  	    	
						  	  	    	
							  	  	    //if (playersFighting.equals("fiveVsZero") || playersFighting.equals("zeroVsFive")) {
							  	  	    	
							  	  	    //}
							  	  	    
							  	  	    if (playersFighting.equals("fiveVsOne") || playersFighting.equals("oneVsFive")) {
							  	  	    	
								  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);									  	  	
									  	  	disarmedtextright.setVisibility(View.VISIBLE);
									  	  	disarmedtextright.bringToFront();
							  	  	    }
							  	  	    else if (playersFighting.equals("zeroVsOne")) {
							  	  	    	
								  	  	    TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);								  	  	    
								  	  	    disarmedtextleft.setVisibility(View.VISIBLE);
								  	  	    disarmedtextleft.bringToFront();
							  	  	    }
							  	  	    else if (playersFighting.equals("oneVsZero")) {
							  	  	    	
								  	  	    TextView disarmedtextright = (TextView) findViewById(R.id.textdisarmedright);									  	  	
									  	  	disarmedtextright.setVisibility(View.VISIBLE);
									  	  	disarmedtextright.bringToFront();
							  	  	    }
							  	  	    
						  	  	    	
						  	  	    	//disarmedGraphicVisible();
						  	  	    }
								});
							}
						}
					}
					else if (read.contains("caNHasDisarmed2")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						canHasDisarmed[2]=part2;
						
						if (numberOfPlayers == 4) {
							
							if (part2.equals("no")) {
								
								runOnUiThread(new Runnable() {
						  	  	    @Override
						  	  	    public void run() {						  	  	    	
						  	  	    	
						  	  	    }
								});
							}
						}
					}
					else if (read.contains("canhasDisarmed3")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						canHasDisarmed[3]=part2;
						
						if (numberOfPlayers == 5) {
							
							if (part2.equals("no")) {
								
								runOnUiThread(new Runnable() {
						  	  	    @Override
						  	  	    public void run() {						  	  	    	
						  	  	    	
						  	  	    }
								});
							}
						}
					}					
					else if (read.contains("canHaSDisarmed5")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						canHasDisarmed[5]=part2;
						
						if (numberOfPlayers == 2 || (numberOfPlayers == 3 && playersFighting.equals("fiveVsZero"))) {
						
							if (part2.equals("no")) {
								
								runOnUiThread(new Runnable() {
						  	  	    @Override
						  	  	    public void run() {
						  	  	    	
						  	  	    	TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);
						  	  	    	disarmedtextleft.setVisibility(View.INVISIBLE);
						  	  	    }
								});
							}
						}						
						else if (numberOfPlayers == 3) {
							
							if (part2.equals("no")) {
								
								runOnUiThread(new Runnable() {
						  	  	    @Override
						  	  	    public void run() {						  	  	    	
						  	  	    	
							  	  	    if (playersFighting.equals("fiveVsZero") || playersFighting.equals("zeroVsFive")) {
							  	  	    	
								  	  	    TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);								  	  	    
								  	  	    disarmedtextleft.setVisibility(View.INVISIBLE);
								  	  	    disarmedtextleft.bringToFront();
							  	  	    }
							  	  	    else if (playersFighting.equals("fiveVsOne") || playersFighting.equals("oneVsFive")) {
							  	  	    	
								  	  	    TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);								  	  	    
								  	  	    disarmedtextleft.setVisibility(View.INVISIBLE);
								  	  	    disarmedtextleft.bringToFront();
							  	  	    }
							  	  	    
							  	  	    //if (playersFighting.equals("zeroVsOne")) {
							  	  	    	
							  	  	    //}
							  	  	    //if (playersFighting.equals("oneVsZero")) {
							  	  	    	
							  	  	    //}
							  	  	    
						  	  	    	
						  	  	    	//disarmedGraphicInvisible();
						  	  	    }
								});
							}
							else if (part2.equals("yes")) {
								
								runOnUiThread(new Runnable() {
						  	  	    @Override
						  	  	    public void run() {						  	  	    	
						  	  	    	
							  	  	    if (playersFighting.equals("fiveVsZero") || playersFighting.equals("zeroVsFive")) {
							  	  	    	
								  	  	    TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);								  	  	    
								  	  	    disarmedtextleft.setVisibility(View.VISIBLE);
								  	  	    disarmedtextleft.bringToFront();
							  	  	    }
							  	  	    else if (playersFighting.equals("fiveVsOne") || playersFighting.equals("oneVsFive")) {
							  	  	    	
								  	  	    TextView disarmedtextleft = (TextView) findViewById(R.id.textdisarmedleft);								  	  	    
								  	  	    disarmedtextleft.setVisibility(View.VISIBLE);
								  	  	    disarmedtextleft.bringToFront();
							  	  	    }
							  	  	    
							  	  	    //if (playersFighting.equals("zeroVsOne")) {
							  	  	    	
							  	  	    //}
							  	  	    //if (playersFighting.equals("oneVsZero")) {
							  	  	    	
							  	  	    //}
							  	  	    
						  	  	    	
						  	  	    	//disarmedGraphicVisible();						  	  	    
						  	  	    }
								});
							}
						}
					}
					
					//FOR TIMES WHEN 'CANHASDISARMEDX' ISN'T USED:
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
						
						MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
						
						victoryDefeatAnimation();
						
						/*
						final Handler h = new Handler();
				  	  	h.postDelayed(new Runnable() {		  	  	  			
				  	  			
				  	  		@Override
				  	  		public void run() {
			
				  	  			MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
				  	  		}
				  	  	}, 500);
				  	  	*/
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
					
					else if (read.contains("foldScrolls")) {
						
						hideImageView();
						
						MediaPlayerWrapper.play(Client2.this, R.raw.scroll3);
						
						foldScrolls();
						
						clearDisarmGraphicAndSkills();
					}
					else if (read.contains("reveal0onright")) {
						
						reveal0onright();															
					}
					else if (read.contains("rEveal1onright")) {
						
						reveal1onright();															
					}
					else if (read.contains("reVeal5onleft")) {
						
						reveal5onleft();															
					}
					else if (read.contains("ClientAttackingReveal5OnLeft")) {//revEal5onleftClientAttacking
						
						reveal5onleftclientattacking();
						
						//test();
					}
					else if (read.contains("clntattackingreveal1onleft")) {//reveAl1onleftClientAttacking
	
						reveal1onleftclientattacking();
						
						//test();
					}
					else if (read.contains("CntAttingrevL0onlft")) {//reveaL0onleftClientAttacking
	
						reveal0onleftclientattacking();
						
						//test();
					}
					else if (read.contains("closeRightScroll")) {
						
						scrollAnimationLeftDownReverse();															
					}
					
					else if (read.contains("0attackingFirst")) {
						
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						zeroAttackingFirst=part2;											
					}
					else if (read.contains("1AttackingFirst")) {
	
						String[] parts = read.split(":");
						String part1 = parts[0];  
						//String part2 = parts[1].trim();//IF THERE WAS A SPACE
						String part2 = parts[1];				
												
						oneAttackingFirst=part2;											
					}
					
					
					/*
					else if (read.contains("getNames")) {
						
						names();										
					}
					*/
					
										
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