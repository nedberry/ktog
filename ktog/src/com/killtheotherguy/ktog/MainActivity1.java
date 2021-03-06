package com.killtheotherguy.ktog;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Text;


import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.Formatter;
import android.text.style.URLSpan;
import android.util.Log;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.killtheotherguy.ktog.R;

public class MainActivity1 extends Activity {//WAS ActionBarActivity (got "app stopped" message on S4 w/o this)			
	
	public String[] name = new String[100];
	//public String[] name;
	String tempName;
	int count = 0;
	
	int numberOfPlayers;
	
	
	
	public static final int PICK_IMAGE = 100;
	public ImageView customImageView;
	
	String multiplayer = "no";
	
	private static final int CONTACT_PICKER_RESULT = 1001;
	private static final int SENT = 0;
	private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
	
	InetAddress inetAddress;
	
	String hostIP;
	
	Uri imageUri;
	
	boolean isMessageSent = false;
	//boolean needtoclearui = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		//Toast.makeText(MainActivity1.this,"EMPTY SPACE TEST", Toast.LENGTH_SHORT).show();
		
		/*
		// USED THE FOLLOWING TO REMOVE TITLE BAR:
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// This will hide the system bar until user swipes up from bottom or down from top.		
		//getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_IMMERSIVE
        //        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
				
		setContentView(R.layout.activity_main_activity1);
		*/
		
		
		// USED THE FOLLOWING TO REMOVE TITLE BAR:
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		//getActionBar().hide();
		
		
		// This will hide the system bar until user swipes up from bottom or down from top.		
		//getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		
		
		setContentView(R.layout.activity_main_activity1);		
		// For the little space between the action & attack button.
		getWindow().getDecorView().setBackgroundColor(Color.BLACK);		
		
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		
		
		
		//ImageView img = (ImageView)findViewById(R.id.menu1);
		
		final ImageButton onePlayerButton = (ImageButton) findViewById(R.id.imagebuttononeplayer);
		final ImageButton multiPlayerButton = (ImageButton) findViewById(R.id.imagebuttonmultiplayer);
		final ImageButton aboutButton = (ImageButton) findViewById(R.id.imagebuttonabout);
		final Button recordsButton = (Button) findViewById(R.id.buttonrecords);
		final Button guysButton = (Button) findViewById(R.id.buttonguys);
		
		
		// Sound stuff:
		final MediaPlayer buttonSound = MediaPlayer.create(MainActivity1.this, R.raw.swordswing);
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		//stopService(svc);
		startService(svc);
		
		
		getCount();
		
		
		onePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
			    
            	numberOfPlayers = 1;//will be 1 or 2 to indicate single or multiplayer
            	
	        	buttonSound.start();
	        	
	        	//stopService(svc);
	        	
	        	//Toast.makeText(MainActivity1.this,"One player button is working!!", Toast.LENGTH_SHORT).show();
	        	//USE THIS WHEN READY??:
	        	//Intent openNewGuyOldGuy = new Intent("com.nedswebsite.ktog.NewGuyOldGuy");
				//startActivity(openNewGuyOldGuy);
	        	
	        	//AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);
	        	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this, R.style.customalertdialog);
	        	//AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this, R.style.customalertdialog).setInverseBackgroundForced(true);
	        	
	        	
	        	Toast toast = Toast.makeText(MainActivity1.this, "Enter name", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
	  			View view = toast.getView();
	  			view.setBackgroundResource(R.drawable.centerscroll3toast);
	  			toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER

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
	        	
	        	
	        	//alert.setCancelable(false);
	        	alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
	    	        @Override
	    	        public void onDismiss(DialogInterface dialog) {
	    	        	
	    	        	hideSystemUI();
	    	        }
	    	    });
	        	
	        	
	        	//alert.setTitle("One Player");
	        	//alert.setMessage("Enter name:");
	
	        	// Set an EditText view to get user input:
	        	final EditText input = new EditText(MainActivity1.this);
	        	input.setSingleLine(true);
	        	// Limits to 1 line (clicking return is like clicking "ok".)
	        	alert.setView(input);
	        	// Limits the number of characters entered to 14.
	        	InputFilter[] FilterArray = new InputFilter[1];
	        	FilterArray[0] = new InputFilter.LengthFilter(14);
	        	input.setFilters(FilterArray);
	        	
	        	// THIS WILL GET KEYBOARD AUTOMATICALLY FOR S4:
	        	/*
	        	input.requestFocus();
	            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	            */
	        	
	        	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        	public void onClick(DialogInterface dialog, int whichButton) {        		         		
	        		
	        		if (input.getText().toString().isEmpty()) {
	        			
	        			Toast toast = Toast.makeText(MainActivity1.this, "Name cannot be blank.", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
	        		     
	        			hideSystemUI();
	        		}
	        		
	        		else {
	        		     
	        			//needtoclearui = true;//USE:builder.setOnDismissListener
		        		
		        		// NEED TO SEND TO ARRAY HERE:
		        		String playername = input.getText().toString();
		            	String playercomputer = "Computer".toString();
		            	
		            	ArrayOfPlayers.player[0] = playername;
		            	ArrayOfPlayers.player[1] = playercomputer;
		            	
		            	//savePlayerName();
		            	
		            	//insertToDatabase(playername);	        	
			        	
			        	// ARRAY ADAPTER WITH ICON STUFF:	        	
			        	
			        	final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
			    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
			    		
			    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
			    		
			    		
			    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
			    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
			    		//builder.setIcon(R.drawable.computerhead);
			    		//builder.setTitle("Choose Your Avatar");
			    		//builder.setMessage("Choose Your Avatar");
			    		
			    		//Toast.makeText(MainActivity1.this,"Choose Your Avatar", Toast.LENGTH_SHORT).show();
			    		
			    		Toast toast = Toast.makeText(MainActivity1.this, "Choose An Avatar", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
			  			View view = toast.getView();
			  			view.setBackgroundResource(R.drawable.centerscroll3toast);
			  			//view.bringToFront();
			  			
			  			//view.setRotation(45);//ALREADY ROTATED IN GIMP
			  			//toast.setGravity(Gravity.TOP, whichButton, whichButton);
			  			toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
			  			
			  			//toast.setGravity(Gravity.TOP|Gravity.RIGHT, 450, 300);
			  			//toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);

			  			TextView text = (TextView) view.findViewById(android.R.id.message);
			  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
			  			text.setTypeface(typeFace);
			  			//text.setTextSize(40);
			  			text.setTextColor(Color.parseColor("#FFFFFF"));
			  			
			  			//text.setRotation(-45);
			  			//toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
			  			//text.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
			  			text.setGravity(Gravity.CENTER);
			  			
			  			toast.show();
			  			
			    		/*IN CASE ABOVE STOPS WORKING:
			    		LayoutInflater inflater = getLayoutInflater();

			    		View layout = inflater.inflate(R.layout.custom_toast,
			    		(ViewGroup) findViewById(R.id.custom_toast_layout_id));

			    		// set a message
			    		TextView text = (TextView) layout.findViewById(R.id.text);
			    		text.setText("Choose An Avatar");

			    		// Toast...
			    		Toast toast = new Toast(getApplicationContext());
			    		toast.setGravity(Gravity.TOP_VERTICAL, 0, 0);
			    		toast.setDuration(Toast.LENGTH_SHORT);
			    		toast.setView(layout);
			    		toast.show();
			    		
			    		
			    		(in res/layout/custom_toast.xml)
			    		<?xml version="1.0" encoding="utf-8"?>
						<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
					    android:id="@+id/custom_toast_layout_id"
					    android:layout_width="fill_parent"
					    android:layout_height="fill_parent"
					    android:background="#FFF"
					    android:orientation="horizontal"
					    android:padding="5dp" >

	    				<TextView
				        android:id="@+id/text"
				        android:layout_width="wrap_content"
				        android:layout_height="fill_parent"
				        android:textColor="#FFF" />

						</LinearLayout>
			  			*/
			  			
			    		
			    		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
			    	        @Override
			    	        public void onDismiss(DialogInterface dialog) {
			    	        	
			    	        	hideSystemUI();
			    	        }
			    	    });
			    		
			    		
			    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
			    			public void onClick(DialogInterface dialog, int item) { 
			    								
			    				if (item == 0) {
			    					ArrayOfAvatars.avatar[0] = "computer";
			    					
			    					stopService(svc);	    				
				    				
				    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
				    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				    				startActivity(intent);
				    	        	
				    	        	dialog.dismiss();
				    	        	
				    	        	hideSystemUI();
			    				}
			    				else if (item == 1) {
			    					ArrayOfAvatars.avatar[0] = "crossedswords";
			    					
			    					stopService(svc);
				    				
				    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
				    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				    				startActivity(intent);
				    	        	
				    	        	dialog.dismiss();
				    	        	
				    	        	hideSystemUI();
			    				}
			    				else if (item == 2) {
			    					ArrayOfAvatars.avatar[0] = "stonedead";
			    					
			    					stopService(svc);	    				
				    				
				    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
				    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				    				startActivity(intent);
				    	        	
				    	        	dialog.dismiss();
				    	        	
				    	        	hideSystemUI();
			    				}
			    				else if (item == 3) {
			    					ArrayOfAvatars.avatar[0] = "custom";
			    					
			    					stopService(svc);
			    					
			    					openGallery();
			    					
			    					dialog.dismiss();
			    					
			    					hideSystemUI();
			    				}    				
			    	        	
			    	        	//finish();
		        	  		}
			    		});	    		
			        	
			            builder.create().show();
	        		}
	        	  }
	        	});
	
	        	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	        	  public void onClick(DialogInterface dialog, int whichButton) {
	        	    
	        		  hideSystemUI();
	        	  }
	        	});
	        	
	        	alert.show();
			}
		});
		
		multiPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
			    /*
				// Here, thisActivity is the current activity
				if (ContextCompat.checkSelfPermission(MainActivity1.this,
						android.Manifest.permission.READ_CONTACTS)
				        != PackageManager.PERMISSION_GRANTED) {
	
				    // Permission is not granted
				    // Should we show an explanation?
				    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity1.this,
				    		android.Manifest.permission.READ_CONTACTS)) {
				        // Show an explanation to the user *asynchronously* -- don't block
				        // this thread waiting for the user's response! After the user
				        // sees the explanation, try again to request the permission.
				    } else {
				        // No explanation needed; request the permission
				        ActivityCompat.requestPermissions(MainActivity1.this,
				                new String[]{android.Manifest.permission.READ_CONTACTS},
				                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
	
				        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
				        // app-defined int constant. The callback method gets the
				        // result of the request.
				    }
				} else {
					*/
					numberOfPlayers = 2;//will be 1 or 2 to indicate single or multiplayer
	            	
		        	buttonSound.start();
		        	
		        	multiplayer = "yes";
		        	
		        	
		        	goToHostOrJoin();
		        	
		        	/*
		        	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this, R.style.customalertdialog);
		
		        	//alert.setTitle("Multiplayer");
		        	//alert.setMessage("Enter Name");
		        	
		        	Toast toast = Toast.makeText(MainActivity1.this, "Enter name", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
		        	
		        	
		        	alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
		    	        @Override
		    	        public void onDismiss(DialogInterface dialog) {
		    	        	
		    	        	hideSystemUI();
		    	        }
		    	    });
		        	
		        	
		        	//alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
					//	@Override
					//	public void onCancel(DialogInterface dialog) {		  							
					//		
					//		//IF NOTHING DOESN'T WORK, TRY:
					//		
					//		stopService(svc);
					//		
					//		Intent i = new Intent(MainActivity1.this, MainActivity1.class);
				    //    	MainActivity1.this.startActivity(i);
					//	}
					//});
		        	
		
		        	// Set an EditText view to get user input:
		        	final EditText input = new EditText(MainActivity1.this);
		        	input.setSingleLine(true);
		        	// Limits to 1 line (clicking return is like clicking "ok".)
		        	alert.setView(input);
		        	// Limits the number of characters entered to 14.
		        	InputFilter[] FilterArray = new InputFilter[1];
		        	FilterArray[0] = new InputFilter.LengthFilter(14);
		        	input.setFilters(FilterArray);
		        	
		        	// THIS WILL GET KEYBOARD AUTOMATICALLY FOR S4:
		        	
		        	//input.requestFocus();
		            //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		            //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		            
		        	
		        	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        	public void onClick(DialogInterface dialog, int whichButton) {        		         		
		        		
		        		if (input.getText().toString().isEmpty()) {
		        			
		        			Toast toast = Toast.makeText(MainActivity1.this, "Name cannot be blank.", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
		        		     
		        			hideSystemUI();
		        		}
		        		
		        		else {
		        			
			        		// NEED TO SEND TO ARRAY HERE:
			        		String playername = input.getText().toString();
			            	//String playercomputer = "Computer".toString();
			            	
			            	ArrayOfPlayers.player[5] = playername;
			            	//ArrayOfPlayers.player[1] = playercomputer;
			            	
			            	//savePlayerName();
			            	
			            	//insertToDatabase(playername);	        	
				        	
				        	// ARRAY ADAPTER WITH ICON STUFF:	        	
				        	
				        	final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
				    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
				    		
				    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
				    		
				    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
				    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
				    		//builder.setIcon(R.drawable.computerhead);
				    		
				    		//builder.setTitle("Choose Your Avatar");
				    		//Toast.makeText(MainActivity1.this,"Choose Your Avatar", Toast.LENGTH_SHORT).show();
				    		
				    		
				    		Toast toast = Toast.makeText(MainActivity1.this, "Choose An Avatar", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
				    		
				    		
				    		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
				    	        @Override
				    	        public void onDismiss(DialogInterface dialog) {
				    	        	
				    	        	hideSystemUI();
				    	        }
				    	    });
				    		
				    		
				    		//builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
		    				//	@Override
		    				//	public void onCancel(DialogInterface dialog) {		  							
		    				//		
		    				//		//IF NOTHING DOESN'T WORK, TRY:
		    				//		
		    				//		stopService(svc);
		    				//		
		    				//		Intent i = new Intent(MainActivity1.this, MainActivity1.class);
		    			    //    	MainActivity1.this.startActivity(i);
		    				//	}
		    				//});
				    		
				    		
				    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
				    			public void onClick(DialogInterface dialog, int item) { 
				    								
				    				if (item == 0) {
				    					ArrayOfAvatars.avatar[5] = "computer";
				    					
				    					goToHostOrJoin();
					    	        	
					    	        	dialog.dismiss();
					    	        	
					    	        	hideSystemUI();
				    				}
				    				else if (item == 1) {
				    					ArrayOfAvatars.avatar[5] = "crossedswords";
				    					
				    					goToHostOrJoin();
					    	        	
					    	        	dialog.dismiss();
					    	        	
					    	        	hideSystemUI();
				    				}
				    				else if (item == 2) {
				    					ArrayOfAvatars.avatar[5] = "stonedead";
				    					
				    					goToHostOrJoin();
					    	        	
					    	        	dialog.dismiss();
					    	        	
					    	        	hideSystemUI();
				    				}
				    				else if (item == 3) {
				    					ArrayOfAvatars.avatar[5] = "custom";	    					
				    					
				    					openGallery();
				    					
				    					dialog.dismiss();
				    					
				    					hideSystemUI();
				    				}    				
				    	        	
				    	        	//finish();
			        	  		}
				    		});	    		
				        	
				            builder.create().show();            
		        		}
		        	  }
		        	});
		        	
		        	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		        	  public void onClick(DialogInterface dialog, int whichButton) {
		        	    
		        		  hideSystemUI();
		        	  }
		        	});
		        	
		        	alert.show();
					
				    // Permission has already been granted
				//}
		        */
			}
		});
		
		aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
			                    	
	        	buttonSound.start();
	        	
	        	//Think I need this so user doesn't have to push 'back' more than once (possibly)?
	        	//finish();
	        	
	        	stopService(svc);
	        	
	        	Intent i = new Intent(MainActivity1.this, Rules.class);
	        	MainActivity1.this.startActivity(i);
	        	
	        	//Toast.makeText(MainActivity1.this,"About button is working!!", Toast.LENGTH_SHORT).show();
	        	
	        	//USE THIS WHEN READY??:
	        	//Intent openMain2Activity = new Intent("com.example.ktog1.MAIN2ACTIVITY");
				//startActivity(openMain2Activity);
			}
		});
		
		recordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
			                    	
	        	buttonSound.start();
	        	
	        	stopService(svc);
    			
    			//Intent i = new Intent(MainActivity1.this, playerNamesAndRecords.class);
    			//MainActivity1.this.startActivity(i);
    			
	        	if (count == 0) {
	        		
	        		//Toast.makeText(MainActivity1.this, "No Records", Toast.LENGTH_SHORT).show();
	    			
	        		
	        		Toast toast = Toast.makeText(MainActivity1.this, "No Records", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
	        		
	        		
	    			stopService(svc);
					startService(svc);
	        	}
	        	
	        	else {
	        		
	        		stopService(svc);
	    			
	    			Intent i = new Intent(MainActivity1.this, playerNamesAndRecords.class);
	    			MainActivity1.this.startActivity(i);
	        	}
	        	
	        	
	        	/*
	        	String filename = "myPrefs";
	        	File f = new File(getApplicationContext().getApplicationInfo().dataDir + "/shared_prefs/" + filename + ".xml");
	        	
    			if (f.exists()) {
    				
    				stopService(svc);
	    			
	    			Intent i = new Intent(MainActivity1.this, playerNamesAndRecords.class);
	    			MainActivity1.this.startActivity(i);
    			}
    			    
    			else {
    				
    				Toast.makeText(MainActivity1.this, "No Records", Toast.LENGTH_SHORT).show();
	    			
	    			stopService(svc);
					startService(svc);
    			}
	        	*/
	        	
	        	/*
	        	SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
	        	String value = pref.getString("unknown_key",null);
	        	
	        	if (value == null) {
	        	    
	        		Toast.makeText(MainActivity1.this, "No Records", Toast.LENGTH_SHORT).show();
	    			
	    			stopService(svc);
					startService(svc);
	        		
	        	} else {

	        		stopService(svc);
	    			
	    			Intent i = new Intent(MainActivity1.this, playerNamesAndRecords.class);
	    			MainActivity1.this.startActivity(i);
	        	}
	        	*/
	        	
	        	//stopService(svc);
	        	
	        	/*
	        	File directory = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files");
	    		File[] files = directory.listFiles();
	          
	    		if (files != null) {
	    			
	    			stopService(svc);
	    			
	    			Intent i = new Intent(MainActivity1.this, playerNamesAndRecords.class);
	    			MainActivity1.this.startActivity(i);
	    		}
	    		
	    		else {
	    			
	    			Toast.makeText(MainActivity1.this, "No Records", Toast.LENGTH_SHORT).show();
	    			
	    			stopService(svc);
					startService(svc);
	    		}
	    		*/
			}
		});
		
		guysButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	
	        	buttonSound.start();
	        	
	        	
	        	stopService(svc);
	        	
	        	
	        	//getCount();
	        	
	        	//Toast.makeText(MainActivity1.this, String.valueOf(count), Toast.LENGTH_SHORT).show();
	        	
	        	
	        	if (count == 0) {
	        		
	        		//Toast.makeText(MainActivity1.this, "No Guys", Toast.LENGTH_SHORT).show();
			   		
	        		
	        		Toast toast = Toast.makeText(MainActivity1.this, "No Guys", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
	        		
	        		
					stopService(svc);
					startService(svc);
	        	}
	        	
	        	else {
	        	
	        	//try {
				      
	        			getPlayerNamesFromFile();
	        			
	        			
	        			stopService(svc);
						startService(svc);
	        			
	        			
	        			// Instead of String[] items, Here you can also use ArrayList for your custom object..
	    	        	
	    	        	final String[] nameForAdapter = new String[count];
	    	        	
	    	        	for (int i = 0; i < count; i++) {
	    	        		
	    	        		nameForAdapter[i] = name[i];
	    	        	}
	    	        	
	    		  		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row, R.id.title, nameForAdapter) {
	    	
	    		  		    ViewHolder holder;
	    		  		    Drawable icon;//FOR LAST AVATAR THAT WAS USED??? (LOCATION CAN BE SAVE LIKE OTHER GAME DATA)
	    	
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
	    	
	    		  		        holder.title.setText(nameForAdapter[position]);
	    		  		        //holder.icon.setImageDrawable(drawable);     
	    		  		        
	    	
	    		  		        return convertView;
	    		  		    }
	    		  		};
	    		  		
	    		  		
	    		  		// THIS WAY ALLOWS YOU TO STYLE THE DIALOG (ex. background doesn't dim.):
	    		  		ContextThemeWrapper cw = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
	    		  		AlertDialog.Builder builder = new AlertDialog.Builder(cw, R.style.customalertdialog);		  			  		
	    		  		
	    	  			
	    		  		//builder.setTitle("Choose Your Guy");
	    		  		//Toast.makeText(MainActivity1.this,"Choose Your Guy", Toast.LENGTH_SHORT).show();
	    		  		
	    		  		
	    	  			//builder.setCancelable(false);
	    		  		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
	    	    	        @Override
	    	    	        public void onDismiss(DialogInterface dialog) {
	    	    	        	
	    	    	        	hideSystemUI();
	    	    	        }
	    	    	    });
	    		  		
	    		  		
	    	  			/*
	    				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
	    					@Override
	    					public void onCancel(DialogInterface dialog) {		  							
	    						
	    						//IF NOTHING DOESN'T WORK, TRY:
	    						
	    						stopService(svc);
	    						
	    						Intent i = new Intent(MainActivity1.this, MainActivity1.class);
	    			        	MainActivity1.this.startActivity(i);
	    					}
	    				});
	    	  			*/
	    				
	    	            builder.setAdapter(adapter,
	    	                    new DialogInterface.OnClickListener() {
	    	                        @Override
	    	                        public void onClick(final DialogInterface dialog, int item) {
	    	                        	
	    	                        	for (int i = 0; i < count; i++) {
	    	                        	
	    		                        	if (item == i) {
	    		                        		
	    		                        		buttonSound.start();
	    		                        		
	    		                        		
	    		                        		//ArrayOfPlayers.player[5] = name[i];
	    		                        		tempName = name[i];
	    		                        		
	    		                        		//Toast.makeText(MainActivity1.this, name[i], Toast.LENGTH_SHORT).show();
	    		                        		
	    		                        		
	    		                        		final String[] items = {"One Player", "Multiplayer"};
	    		                        		
	    		                        		// Instead of String[] items, Here you can also use ArrayList for your custom object..
	    		                        		
	    		                		  		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row, R.id.title,items) {
	    		                	
	    		                		  		    ViewHolder holder;
	    		                		  		    Drawable icon;//FOR LAST AVATAR THAT WAS USED??? (LOCATION CAN BE SAVE LIKE OTHER GAME DATA)
	    		                	
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
	    		                		  		ContextThemeWrapper cw = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
	    		                		  		AlertDialog.Builder builder = new AlertDialog.Builder(cw, R.style.customalertdialog);		  			  		
	    		                		  		
	    		                	  			
	    		                		  		//builder.setTitle("Choose Your Game");
	    		                		  		//Toast.makeText(MainActivity1.this,"Choose Your Game", Toast.LENGTH_SHORT).show();
	    		                		  		
	    		                		  		
	    		                	  			//builder.setCancelable(false);
	    		                		  		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
	    		        	    	    	        @Override
	    		        	    	    	        public void onDismiss(DialogInterface dialog) {
	    		        	    	    	        	
	    		        	    	    	        	hideSystemUI();
	    		        	    	    	        }
	    		        	    	    	    });
	    		                		  		
	    		                		  		/*
	    		                		  		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
	    		        	    					@Override
	    		        	    					public void onCancel(DialogInterface dialog) {		  							
	    		        	    						
	    		        	    						//IF NOTHING DOESN'T WORK, TRY:
	    		        	    						
	    		        	    						stopService(svc);
	    		        	    						
	    		        	    						Intent i = new Intent(MainActivity1.this, MainActivity1.class);
	    		        	    			        	MainActivity1.this.startActivity(i);
	    		        	    					}
	    		        	    				});
	    		                				*/
	    		                		  		
	    		                	            builder.setAdapter(adapter,
	    		                	                    new DialogInterface.OnClickListener() {
	    		                	                        @Override
	    		                	                        public void onClick(final DialogInterface dialog, int item) {
	    		                	                        	
	    	                		                        	if (item == 0) {
	    	                		                        		
	    	                		                        		numberOfPlayers = 1;//will be 1 or 2 to indicate single or multiplayer
	    	                		                        		
	    	                		                        		buttonSound.start();
	    	                		                        		
	    	                		                        		
	    	                		                        		ArrayOfPlayers.player[0] = tempName;
	    	                		                        		ArrayOfPlayers.player[1] = "Computer";
	    	                		                        		
	    	                		                        		
	    	                		                        		final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
	    	                		            		    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
	    	                		            		    		
	    	                		            		    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
	    	                		            		    		
	    	                		            		    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
	    	                		            		    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
	    	                		            		    		//builder.setIcon(R.drawable.computerhead);
	    	                		            		    		
	    	                		            		    		//builder.setTitle("Choose Your Avatar");
	    	                		            		    		//Toast.makeText(MainActivity1.this,"Choose Your Avatar", Toast.LENGTH_SHORT).show();
	    	                		            		    		
	    	                		            		    		
	    	                		            		    		Toast toast = Toast.makeText(MainActivity1.this, "Choose An Avatar", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
	    	                		            		    		
	    	                		            		    		
	    	                		            		    		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
	    	                		        	    	    	        @Override
	    	                		        	    	    	        public void onDismiss(DialogInterface dialog) {
	    	                		        	    	    	        	
	    	                		        	    	    	        	hideSystemUI();
	    	                		        	    	    	        }
	    	                		        	    	    	    });
	    	                		            		    		
	    	                		            		    		/*
	    	                		            		    		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
	    	                		        	    					@Override
	    	                		        	    					public void onCancel(DialogInterface dialog) {		  							
	    	                		        	    						
	    	                		        	    						//IF NOTHING DOESN'T WORK, TRY:
	    	                		        	    						
	    	                		        	    						stopService(svc);
	    	                		        	    						
	    	                		        	    						Intent i = new Intent(MainActivity1.this, MainActivity1.class);
	    	                		        	    			        	MainActivity1.this.startActivity(i);
	    	                		        	    					}
	    	                		        	    				});
	    	                		            		    		*/
	    	                		            		    		
	    	                		            		    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
	    	                		            		    			public void onClick(DialogInterface dialog, int item) { 
	    	                		            		    								
	    	                		            		    				if (item == 0) {
	    	                		            		    					ArrayOfAvatars.avatar[0] = "computer";
	    	                		            		    					
	    	                		            		    					stopService(svc);	    				
	    	                		            			    				
	    	                		            			    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
	    	                		            			    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	    	                		            			    				startActivity(intent);
	    	                		            			    	        	
	    	                		            			    	        	dialog.dismiss();
	    	                		            			    	        	
	    	                		            			    	        	hideSystemUI();
	    	                		            		    				}
	    	                		            		    				else if (item == 1) {
	    	                		            		    					ArrayOfAvatars.avatar[0] = "crossedswords";
	    	                		            		    					
	    	                		            		    					stopService(svc);	    				
	    	                		            			    				
	    	                		            			    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
	    	                		            			    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	    	                		            			    				startActivity(intent);
	    	                		            			    	        	
	    	                		            			    	        	dialog.dismiss();
	    	                		            			    	        	
	    	                		            			    	        	hideSystemUI();
	    	                		            		    				}
	    	                		            		    				else if (item == 2) {
	    	                		            		    					ArrayOfAvatars.avatar[0] = "stonedead";
	    	                		            		    					
	    	                		            		    					stopService(svc);	    				
	    	                		            			    				
	    	                		            			    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
	    	                		            			    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	    	                		            			    				startActivity(intent);
	    	                		            			    	        	
	    	                		            			    	        	dialog.dismiss();
	    	                		            			    	        	
	    	                		            			    	        	hideSystemUI();
	    	                		            		    				}
	    	                		            		    				else if (item == 3) {
	    	                		            		    					ArrayOfAvatars.avatar[0] = "custom";
	    	                		            		    					
	    	                		            		    					stopService(svc);
	    	                		            		    					
	    	                		            		    					openGallery();
	    	                		            		    					
	    	                		            		    					dialog.dismiss();
	    	                		            		    					
	    	                		            		    					hideSystemUI();
	    	                		            		    				}    				
	    	                		            		    	        	
	    	                		            		    	        	//finish();
	    	                		            	        	  		}
	    	                		            		    		});	    		
	    	                		            		        	
	    	                		            		            builder.create().show();
	    	                		                        	}
	    	                		                        	
	    	                		                        	else if (item == 1) {
	    	                		                        		
	    	                		                        		numberOfPlayers = 2;//will be 1 or 2 to indicate single or multiplayer
	    	                		                        		
	    	                		                        		buttonSound.start();
	    	                		                        		
	    	                		                        		multiplayer = "yes";
	    	                		                        		
	    	                		                        		
	    	                		                        		goToHostOrJoin();
	    	                		                        		
	    	                		                        		
	    	                		                        		/*
	    	                		                        		ArrayOfPlayers.player[5] = tempName;
	    	                		                        		
	    	                		                        		
	    	                		                        		final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
	    	                		            		    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
	    	                		            		    		
	    	                		            		    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
	    	                		            		    		
	    	                		            		    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
	    	                		            		    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
	    	                		            		    		//builder.setIcon(R.drawable.computerhead);
	    	                		            		    		
	    	                		            		    		//builder.setTitle("Choose Your Avatar");
	    	                		            		    		//Toast.makeText(MainActivity1.this,"Choose Your Avatar", Toast.LENGTH_SHORT).show();
	    	                		            		    		
	    	                		            		    		
	    	                		            		    		Toast toast = Toast.makeText(MainActivity1.this, "Choose An Avatar", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
	    	                		            		    		
	    	                		            		    		
	    	                		            		    		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
	    	                		        	    	    	        @Override
	    	                		        	    	    	        public void onDismiss(DialogInterface dialog) {
	    	                		        	    	    	        	
	    	                		        	    	    	        	hideSystemUI();
	    	                		        	    	    	        }
	    	                		        	    	    	    });
	    	                		            		    		
	    	                		            		    		
	    	                		            		    		//builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
	    	                		        	    				//	@Override
	    	                		        	    				//	public void onCancel(DialogInterface dialog) {		  							
	    	                		        	    				//		
	    	                		        	    				//		//IF NOTHING DOESN'T WORK, TRY:
	    	                		        	    				//		
	    	                		        	    				//		stopService(svc);
	    	                		        	    				//		
	    	                		        	    				//		Intent i = new Intent(MainActivity1.this, MainActivity1.class);
	    	                		        	    			    //    	MainActivity1.this.startActivity(i);
	    	                		        	    				//	}
	    	                		        	    				//});
	    	                		            		    		
	    	                		            		    		
	    	                		            		    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
	    	                		            		    			public void onClick(DialogInterface dialog, int item) { 
	    	                		            		    								
	    	                		            		    				if (item == 0) {
	    	                		            		    					ArrayOfAvatars.avatar[5] = "computer";
	    	                		            		    					
	    	                		            		    					goToHostOrJoin();
	    	                		            			    	        	
	    	                		            			    	        	dialog.dismiss();
	    	                		            			    	        	
	    	                		            			    	        	hideSystemUI();
	    	                		            		    				}
	    	                		            		    				else if (item == 1) {
	    	                		            		    					ArrayOfAvatars.avatar[5] = "crossedswords";
	    	                		            		    					
	    	                		            		    					goToHostOrJoin();
	    	                		            			    	        	
	    	                		            			    	        	dialog.dismiss();
	    	                		            			    	        	
	    	                		            			    	        	hideSystemUI();
	    	                		            		    				}
	    	                		            		    				else if (item == 2) {
	    	                		            		    					ArrayOfAvatars.avatar[5] = "stonedead";
	    	                		            		    					
	    	                		            		    					goToHostOrJoin();
	    	                		            			    	        	
	    	                		            			    	        	dialog.dismiss();
	    	                		            			    	        	
	    	                		            			    	        	hideSystemUI();
	    	                		            		    				}
	    	                		            		    				else if (item == 3) {
	    	                		            		    					ArrayOfAvatars.avatar[5] = "custom";	    					
	    	                		            		    					
	    	                		            		    					openGallery();
	    	                		            		    					
	    	                		            		    					dialog.dismiss();
	    	                		            		    					
	    	                		            		    					hideSystemUI();
	    	                		            		    				}    				
	    	                		            		    	        	
	    	                		            		    	        	//finish();
	    	                		            	        	  		}
	    	                		            		    		});	    		
	    	                		            		        	
	    	                		            		            builder.create().show();
	    	                		            		            */
	    	                		                        	}
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
	    		                	            
	    		                	            
	    		                	            //h.removeCallbacks(this);
	    		                	        	
	    		                	        	//Intent i = new Intent(MainActivity1.this, MainActivity1.class);
	    		                	        	//MainActivity1.this.startActivity(i);
	    		                        	}
	    	                        	}
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
	    	            
	    	            
	    	            //h.removeCallbacks(this);
	    	        	
	    	        	//Intent i = new Intent(MainActivity1.this, MainActivity1.class);
	    	        	//MainActivity1.this.startActivity(i);
	        			
				   //} catch (Exception e) {
				      
				   		//Toast.makeText(MainActivity1.this, "No Guys", Toast.LENGTH_SHORT).show();
				   		
						//stopService(svc);
						//startService(svc);
				//}
            	}
			}
		});
	}
	
	
	/*
	@SuppressLint("Override")
	public void onRequestPermissionsResult(int requestCode,
	        String[] permissions, int[] grantResults) {
	    switch (requestCode) {
	        case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
	            // If request is cancelled, the result arrays are empty.
	            if (grantResults.length > 0
	                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
	                // permission was granted, yay! Do the
	                // contacts-related task you need to do.
	            	
	            	
	            	numberOfPlayers = 2;//will be 1 or 2 to indicate single or multiplayer
	            	
		        	//buttonSound.start();
		        	
		        	multiplayer = "yes";
		        	
		        	
		        	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this, R.style.customalertdialog);
		
		        	//alert.setTitle("Multiplayer");
		        	//alert.setMessage("Enter Name");
		        	
		        	Toast toast = Toast.makeText(MainActivity1.this, "Enter name", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
		        	
		        	
		        	alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
		    	        @Override
		    	        public void onDismiss(DialogInterface dialog) {
		    	        	
		    	        	hideSystemUI();
		    	        }
		    	    });
		        	
		        	
		        	//alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
					//	@Override
					//	public void onCancel(DialogInterface dialog) {		  							
					//		
					//		//IF NOTHING DOESN'T WORK, TRY:
					//		
					//		stopService(svc);
					//		
					//		Intent i = new Intent(MainActivity1.this, MainActivity1.class);
				    //    	MainActivity1.this.startActivity(i);
					//	}
					//});
		        	
		
		        	// Set an EditText view to get user input:
		        	final EditText input = new EditText(MainActivity1.this);
		        	input.setSingleLine(true);
		        	// Limits to 1 line (clicking return is like clicking "ok".)
		        	alert.setView(input);
		        	// Limits the number of characters entered to 14.
		        	InputFilter[] FilterArray = new InputFilter[1];
		        	FilterArray[0] = new InputFilter.LengthFilter(14);
		        	input.setFilters(FilterArray);
		        	
		        	// THIS WILL GET KEYBOARD AUTOMATICALLY FOR S4:
		        	
		        	//input.requestFocus();
		            //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		            //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		            
		        	
		        	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        	public void onClick(DialogInterface dialog, int whichButton) {        		         		
		        		
		        		if (input.getText().toString().isEmpty()) {
		        			
		        			Toast toast = Toast.makeText(MainActivity1.this, "Name cannot be blank.", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
		        		     
		        			hideSystemUI();
		        		}
		        		
		        		else {
		        			
			        		// NEED TO SEND TO ARRAY HERE:
			        		String playername = input.getText().toString();
			            	//String playercomputer = "Computer".toString();
			            	
			            	ArrayOfPlayers.player[5] = playername;
			            	//ArrayOfPlayers.player[1] = playercomputer;
			            	
			            	//savePlayerName();
			            	
			            	//insertToDatabase(playername);	        	
				        	
				        	// ARRAY ADAPTER WITH ICON STUFF:	        	
				        	
				        	final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
				    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
				    		
				    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
				    		
				    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
				    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
				    		//builder.setIcon(R.drawable.computerhead);
				    		
				    		//builder.setTitle("Choose Your Avatar");
				    		//Toast.makeText(MainActivity1.this,"Choose Your Avatar", Toast.LENGTH_SHORT).show();
				    		
				    		
				    		Toast toast = Toast.makeText(MainActivity1.this, "Choose An Avatar", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
				    		
				    		
				    		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
				    	        @Override
				    	        public void onDismiss(DialogInterface dialog) {
				    	        	
				    	        	hideSystemUI();
				    	        }
				    	    });
				    		
				    		
				    		//builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
		    				//	@Override
		    				//	public void onCancel(DialogInterface dialog) {		  							
		    				//		
		    				//		//IF NOTHING DOESN'T WORK, TRY:
		    				//		
		    				//		stopService(svc);
		    				//		
		    				//		Intent i = new Intent(MainActivity1.this, MainActivity1.class);
		    			    //    	MainActivity1.this.startActivity(i);
		    				//	}
		    				//});
				    		
				    		
				    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
				    			public void onClick(DialogInterface dialog, int item) { 
				    								
				    				if (item == 0) {
				    					ArrayOfAvatars.avatar[5] = "computer";
				    					
				    					goToHostOrJoin();
					    	        	
					    	        	dialog.dismiss();
					    	        	
					    	        	hideSystemUI();
				    				}
				    				else if (item == 1) {
				    					ArrayOfAvatars.avatar[5] = "crossedswords";
				    					
				    					goToHostOrJoin();
					    	        	
					    	        	dialog.dismiss();
					    	        	
					    	        	hideSystemUI();
				    				}
				    				else if (item == 2) {
				    					ArrayOfAvatars.avatar[5] = "stonedead";
				    					
				    					goToHostOrJoin();
					    	        	
					    	        	dialog.dismiss();
					    	        	
					    	        	hideSystemUI();
				    				}
				    				else if (item == 3) {
				    					ArrayOfAvatars.avatar[5] = "custom";	    					
				    					
				    					openGallery();
				    					
				    					dialog.dismiss();
				    					
				    					hideSystemUI();
				    				}    				
				    	        	
				    	        	//finish();
			        	  		}
				    		});	    		
				        	
				            builder.create().show();            
		        		}
		        	  }
		        	});
		        	
		        	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		        	  public void onClick(DialogInterface dialog, int whichButton) {
		        	    
		        		  hideSystemUI();
		        	  }
		        	});
		        	
		        	alert.show();
	            	
	            	
	            	
	            } else {
	                // permission denied, boo! Disable the
	                // functionality that depends on this permission.
	            }
	            return;
	        }

	        // other 'case' lines to check for other
	        // permissions this app might request.
	    }
	}
	*/
	
	
	
	
	
	
	
	
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
	
	
	public void getCount() {
		
		SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
		count = pref.getAll().size();
		
		/*
		File directory = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files");
		File[] files = directory.listFiles();
      
		if (files != null)
			for (int i = 0; i < files.length; ++i) {

				count++;
				File file = files[i];
			}
			*/
	}
	
	public void getPlayerNamesFromFile() {
		
		SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
		
		Map<String,?> keys = pref.getAll();
		int i = 0;
		for(Map.Entry<String,?> entry : keys.entrySet()) {
			
			Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());
			
			//for (int i = 0; i < count; ++i) {
				
			
			String userstats = entry.getValue().toString();
			
			String[] parts = userstats.split(":");
			
			String part1 = parts[0];
			String part2 = parts[1];
			String part3 = parts[2];
			String part4 = parts[3];
			String part5 = parts[4];
			String part6 = parts[5];
		
			
			name[i] = entry.getKey();
			//name[0] = file.getName().toString();
			//gamesPlayed[i] = Integer.parseInt(part2);
			//wins[i] = Integer.parseInt(part4);
			//loses[i] = Integer.parseInt(part6);
			
			i++;
		}
		
		//File directory = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files");
		
		// Refresh the data so it can seen when the device is plugged in a
		// computer. You may have to unplug and replug the device to see the
		// latest changes. This is not necessary if the user should not modify
		// the files.
		//MediaScannerConnection.scanFile(this, new String[]{directory.toString()}, null, null);
		/*
		File[] files = directory.listFiles();
		
		for (int i = 0; i < files.length; ++i) {
			
			File file = files[i];
			
			//File playerName = new File(this.getExternalFilesDir(null), ArrayOfPlayers.player[5] + ".txt");
			if (file != null) {
			   
			   BufferedReader reader = null;
			   try {
			      reader = new BufferedReader(new FileReader(file));
			      String line;

			      while ((line = reader.readLine()) != null) {
			    	  
			    	  String filename = file.getName().toString();
			    	  filename = filename.substring(0, filename.lastIndexOf("."));
			    	  
			    	  name[i] = filename;			    	  
			    	  
			    	  //Toast.makeText(playerNamesAndRecords.this, "Games = " + gamesPlayed[0] + " " + "Wins = " + wins[0] + " " + "Loses = " + loses[0], Toast.LENGTH_SHORT).show();
			      }
			      
			      reader.close();
			   } catch (Exception e) {
			      Log.e("ReadWriteFile", "Unable to read the TestFile.txt file.");
			   }
			}
		}
		*/
	}
	
	
	public void openGallery() {
		
		Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		startActivityForResult(gallery, PICK_IMAGE);
	}
	
	
	/*
	@Override
	public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
		
		super.startActivityForResult(intent, SENT, options);
		if (requestCode==SENT && isMessageSent) {
			
			if (ArrayOfAvatars.avatar[5].equals("custom")) {
				  
				Intent intent2 = new Intent(MainActivity1.this, Host.class);
				intent2.putExtra("imageUri", imageUri.toString());
				//intent.putExtra("imageUri", imageUri);
				startActivity(intent2);
	  		}
	  		  
	        else {

	        	Intent intent3 = new Intent(MainActivity1.this, Host.class);
	        	//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	        	startActivity(intent3);
	        }
		}
	}
	*/
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode) {
		/*
		case SENT:
		
			if (resultCode == RESULT_OK) {
				
				if (ArrayOfAvatars.avatar[5].equals("custom")) {
					  
					Intent intent2 = new Intent(MainActivity1.this, Host.class);
					intent2.putExtra("imageUri", imageUri.toString());
					//intent.putExtra("imageUri", imageUri);
					startActivity(intent2);
		  		}
		  		  
		        else {

		        	Intent intent3 = new Intent(MainActivity1.this, Host.class);
		        	//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		        	startActivity(intent3);
		        }
			}
		break;		
		*/
		case PICK_IMAGE://FOR IMAGE GALLERY
			
			if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && multiplayer.equals("no")) {
				
				imageUri = data.getData();
				
				/*
				Intent intentFileSize = new Intent(MainActivity1.this, MainActivity2.class);
				intentFileSize.putExtra("imageUri", imageUri.toString());
				String image_path= intentFileSize.getStringExtra("imageUri"); 
				Uri fileUri = Uri.parse(image_path);
				*/
				Bitmap bitmap = null;
				try {
					bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

		        ByteArrayOutputStream stream = new ByteArrayOutputStream();
		        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
		        byte[] imageInByte = stream.toByteArray();
		        float file_size = (imageInByte.length)/1024;//from bytes to kb
		        
		        
		        //Toast.makeText(MainActivity1.this,"IMAGE SIZE = " + file_size, Toast.LENGTH_SHORT).show();
		        
		        Toast toast = Toast.makeText(MainActivity1.this, "IMAGE SIZE = " + file_size, Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
		        
		        //APPARENT SCALING AT ABOUT FACTOR OF 6, DEPENING ON SIZE.	        
		        //0=MAX COMPRESSION, 100=LEAST COMPRESSION
				
				
				
				//File myFile = new File(imageUri.getPath());
				//myFile.getAbsolutePath();
				
				//float file_size = Integer.parseInt(String.valueOf(myFile.length()/1024));	//from bytes to kilobytes
																							//(1 MB = 1024 KBytes)
																							//float for files less than 1 (will get 0 for these if int)
				if (file_size > 0 && file_size < 6) {
					
					Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
				    intent.putExtra("imageUri", imageUri.toString());
					//intent.putExtra("imageUri", imageUri);
				    startActivity(intent);
				}
				/*
				else if (file_size > 100 && file_size < 201) {
					
					try {
	
				        // BitmapFactory options to downsize the image
				        BitmapFactory.Options o = new BitmapFactory.Options();
				        o.inJustDecodeBounds = true;
				        o.inSampleSize = 6;
				        // factor of downsizing the image
	
				        FileInputStream inputStream = new FileInputStream(myFile);
				        //Bitmap selectedBitmap = null;
				        BitmapFactory.decodeStream(inputStream, null, o);
				        inputStream.close();
	
				        // The new size we want to scale to
				        final int REQUIRED_SIZE=75;
	
				        // Find the correct scale value. It should be the power of 2.
				        int scale = 1;
				        while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
				                        o.outHeight / scale / 2 >= REQUIRED_SIZE) {
				            scale *= 2;
				        }
	
				        BitmapFactory.Options o2 = new BitmapFactory.Options();
				        o2.inSampleSize = scale;
				        inputStream = new FileInputStream(myFile);
	
				        Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
				        inputStream.close();
	
				        // here i override the original image file
				        //file.createNewFile();
				        File file = new File("/storage/sdcard0/avatar5");
				        FileOutputStream outputStream = new FileOutputStream(file);
				        
				        selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);
				        
				        imageUri = Uri.fromFile(file);
				        //imageUri.fromFile(new File("/storage/sdcard0/avatar5"));
	
				        //return file;
				    } catch (Exception e) {
				    	
				    	Toast.makeText(MainActivity1.this,"ERROR. Please try again.", Toast.LENGTH_SHORT).show();
				        //return null;
				    }
					
					Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
				    intent.putExtra("imageUri", imageUri.toString());
					//intent.putExtra("imageUri", imageUri);
				    startActivity(intent);
				}
				*/
				else if (file_size > 6) {
					
					//Toast.makeText(MainActivity1.this,"Avatar image must be less than 60 KB (" + file_size +" ).", Toast.LENGTH_SHORT).show();
					Toast toast2 = Toast.makeText(MainActivity1.this, "Avatar image must be less than 60 KB (" + file_size +" ).", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
		  			View view2 = toast2.getView();
		  			view2.setBackgroundResource(R.drawable.centerscroll3toast);
		  			toast2.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER

		  			TextView text2 = (TextView) view2.findViewById(android.R.id.message);
		  			//Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		  			text2.setTypeface(typeFace);
		  			text2.setTextColor(Color.parseColor("#FFFFFF"));
		  			//text.setRotation(-45);
		  			text2.setGravity(Gravity.CENTER);
		  			
		  			toast2.show();
				}
				
				else {
					
					//Toast.makeText(MainActivity1.this,"ERROR LOADING IMAGE " + file_size, Toast.LENGTH_SHORT).show();
					Toast toast3 = Toast.makeText(MainActivity1.this, "ERROR LOADING IMAGE " + file_size, Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
		  			View view3 = toast3.getView();
		  			view3.setBackgroundResource(R.drawable.centerscroll3toast);
		  			toast3.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER

		  			TextView text3 = (TextView) view3.findViewById(android.R.id.message);
		  			//Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		  			text3.setTypeface(typeFace);
		  			text3.setTextColor(Color.parseColor("#FFFFFF"));
		  			//text.setRotation(-45);
		  			text3.setGravity(Gravity.CENTER);
		  			
		  			toast3.show();
				}
			}
			
			else if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && multiplayer.equals("yes")) {
				/*
				imageUri = data.getData();
				
				
				File myFile = new File(imageUri.getPath());
				myFile.getAbsolutePath();
				
				float file_size = Integer.parseInt(String.valueOf(myFile.length()/1024));//from bytes to kilobytes
				*/
				
				imageUri = data.getData();
				
				/*
				Intent intentFileSize = new Intent(MainActivity1.this, MainActivity2.class);
				intentFileSize.putExtra("imageUri", imageUri.toString());
				String image_path= intentFileSize.getStringExtra("imageUri"); 
				Uri fileUri = Uri.parse(image_path);
				*/
				Bitmap bitmap = null;
				try {
					bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

		        ByteArrayOutputStream stream = new ByteArrayOutputStream();
		        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
		        byte[] imageInByte = stream.toByteArray();
		        float file_size = (imageInByte.length)/1024;//from bytes to kb
		        
		        //Toast.makeText(MainActivity1.this,"IMAGE SIZE = " + file_size, Toast.LENGTH_SHORT).show();
		        Toast toast = Toast.makeText(MainActivity1.this, "IMAGE SIZE = " + file_size, Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
		        
		        //APPARENT SCALING AT ABOUT FACTOR OF 6, DEPENING ON SIZE.	        
		        //0=MAX COMPRESSION, 100=LEAST COMPRESSION
				
				
				
				File myFile = new File(imageUri.getPath());
				myFile.getAbsolutePath();
				
				//float file_size = Integer.parseInt(String.valueOf(myFile.length()/1024));	//from bytes to kilobytes
																							//(1 MB = 1024 KBytes)
																							//float for files less than 1 (will get 0 for these if int)
				
				if (file_size > 0 && file_size < 6) {
					
					goToHostOrJoin();
				}
				/*
				else if (file_size > 100 && file_size < 201) {
					
					try {

				        // BitmapFactory options to downsize the image
				        BitmapFactory.Options o = new BitmapFactory.Options();
				        o.inJustDecodeBounds = true;
				        o.inSampleSize = 6;
				        // factor of downsizing the image

				        FileInputStream inputStream = new FileInputStream(myFile);
				        //Bitmap selectedBitmap = null;
				        BitmapFactory.decodeStream(inputStream, null, o);
				        inputStream.close();

				        // The new size we want to scale to
				        final int REQUIRED_SIZE=75;

				        // Find the correct scale value. It should be the power of 2.
				        int scale = 1;
				        while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
				                        o.outHeight / scale / 2 >= REQUIRED_SIZE) {
				            scale *= 2;
				        }

				        BitmapFactory.Options o2 = new BitmapFactory.Options();
				        o2.inSampleSize = scale;
				        inputStream = new FileInputStream(myFile);

				        Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
				        inputStream.close();

				        // here i override the original image file
				        //file.createNewFile();
				        File file = new File("/storage/sdcard0/avatar5");
				        FileOutputStream outputStream = new FileOutputStream(file);
				        
				        selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);
				        
				        imageUri = Uri.fromFile(file);
				        //imageUri.fromFile(new File("/storage/sdcard0/avatar5"));

				        //return file;
				    } catch (Exception e) {
				    	
				    	Toast.makeText(MainActivity1.this,"ERROR. Please try again.", Toast.LENGTH_SHORT).show();
				        //return null;
				    }
					
					goToHostOrJoin();
				}
				*/
				else if (file_size > 6) {
					
					//Toast.makeText(MainActivity1.this,"Avatar image must be less than 60 KB (" + file_size +" ).", Toast.LENGTH_SHORT).show();
					Toast toast2 = Toast.makeText(MainActivity1.this, "Avatar image must be less than 60 KB (" + file_size +" ).", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
		  			View view2 = toast2.getView();
		  			view2.setBackgroundResource(R.drawable.centerscroll3toast);
		  			toast2.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER

		  			TextView text2 = (TextView) view2.findViewById(android.R.id.message);
		  			//Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		  			text2.setTypeface(typeFace);
		  			text2.setTextColor(Color.parseColor("#FFFFFF"));
		  			//text.setRotation(-45);
		  			text2.setGravity(Gravity.CENTER);
		  			
		  			toast2.show();
				}
				
				else {
					
					//Toast.makeText(MainActivity1.this,"ERROR LOADING IMAGE " + file_size, Toast.LENGTH_SHORT).show();
					Toast toast3 = Toast.makeText(MainActivity1.this, "ERROR LOADING IMAGE " + file_size, Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
		  			View view3 = toast3.getView();
		  			view3.setBackgroundResource(R.drawable.centerscroll3toast);
		  			toast3.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER

		  			TextView text4 = (TextView) view3.findViewById(android.R.id.message);
		  			//Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
		  			text4.setTypeface(typeFace);
		  			text4.setTextColor(Color.parseColor("#FFFFFF"));
		  			//text.setRotation(-45);
		  			text4.setGravity(Gravity.CENTER);
		  			
		  			toast3.show();
				}
				
				/*
				final Intent svc=new Intent(this, Badonk2SoundService.class);
				
				final String[] items = new String[] {"Host", "Join", "Cancel"};
				final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.computer, R.drawable.computer};
				
				ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
				
				ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
				AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);
				//builder.setIcon(R.drawable.computerhead);
				builder.setTitle("Multiplayer");
				
				builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
					public void onClick(DialogInterface dialog, int item) { 
										
						if (item == 0) {    					
							
							stopService(svc);	    				
		    				
		    				Intent intent = new Intent(MainActivity1.this, Host.class);
		    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    				startActivity(intent);
		    	        	
		    	        	dialog.dismiss();
						}
						else if (item == 1) {    					
							
							stopService(svc);	    				
		    				
		    				//Intent intent = new Intent(MainActivity1.this, Client1.class);
		    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    				//startActivity(intent);
							Toast.makeText(MainActivity1.this,"JOIN BY INVITE ONLY :(", Toast.LENGTH_SHORT).show();
		    	        	
		    	        	dialog.dismiss();
						}
						else if (item == 2) {		
		    	        	
		    	        	dialog.dismiss();
						}    				    				
			        	
			        	//finish();
			  		}
				});	    		
		    	
		        builder.create().show();
		        
				
				Intent intent = new Intent(MainActivity1.this, Host.class);
			    intent.putExtra("imageUri", imageUri.toString());
				//intent.putExtra("imageUri", imageUri);
			    startActivity(intent);
			    */				    			       	
			}
		break;
		
		
		//"CONTACT_PICKER_RESULT" WAS HERE
		 
		
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	public void goToHostOrJoin() {
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		
		final String[] items = new String[] {"Host", "Join", "Cancel"};
		//final String[] items = new String[] {"Host", "Cancel"};
		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.computer, R.drawable.computer};
		//final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.computer};
		
		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
		
		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
		//builder.setIcon(R.drawable.computerhead);
		
		//builder.setTitle("Multiplayer");
		
		
		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
	        @Override
	        public void onDismiss(DialogInterface dialog) {
	        	
	        	hideSystemUI();
	        }
	    });
		
		/*
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {		  							
				
				//IF NOTHING DOESN'T WORK, TRY:
				
				stopService(svc);
				
				Intent i = new Intent(MainActivity1.this, MainActivity1.class);
	        	MainActivity1.this.startActivity(i);
			}
		});
		*/
		
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int item) { 
								
				if (item == 0) {    					
					
					//stopService(svc);					
					
					
					chooseHostGuy();
					//goToHost();
					
					
					//chooseGmailOrText();					
					
					//getLocalIpAddress();
					
					// TURNED OFF FOR DISPLAYING PRIVATE IP PURPOSES:
					//doLaunchContactPicker(customImageView);
					
					
					/*
    				Intent intent = new Intent(MainActivity1.this, Host.class);
    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    				startActivity(intent);
    	        	*/
    				
    	        	dialog.dismiss();
    	        	
    	        	hideSystemUI();
				}
				
				else if (item == 1) {
					
					//stopService(svc);
					
					
					chooseClientGuy();
					//goToClient();
    				
    				//Intent intent = new Intent(MainActivity1.this, Client1.class);
    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    				//startActivity(intent);
					
					//Toast.makeText(MainActivity1.this,"JOIN BY INVITE ONLY :(", Toast.LENGTH_SHORT).show();
					/*
					Toast toast = Toast.makeText(MainActivity1.this, "Join is by invite only now.", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
		  			*/
					
					
    	        	dialog.dismiss();
    	        	
    	        	hideSystemUI();
				}
				else if (item == 2) {		
    	        	
    	        	dialog.dismiss();
    	        	
    	        	hideSystemUI();
				}    				    				
	        	/*
				else if (item == 1) {
					
					dialog.dismiss();
    	        	
    	        	hideSystemUI();
				}
				*/
	        	//finish();
	  		}
		});	    		
    	
        builder.create().show();		
	}
	
	
	public void chooseHostGuy() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		
		final MediaPlayer buttonSound = MediaPlayer.create(MainActivity1.this, R.raw.swordswing);
		
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
	  			
	  			// NEW WAY TO DO DIALOG
		  		final String[] items = {"New Guy", "Old Guy", "Cancel"};
	
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
	
	
		  		        holder.title.setText(items[position]);
		  		        
	
		  		        return convertView;
		  		    }
		  		};		  		
		  		
		  		
		  		// THIS WAY ALLOWS YOU TO STYLE THE DIALOG (ex. background doesn't dim.):
		  		ContextThemeWrapper cw = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
		  		AlertDialog.Builder builder = new AlertDialog.Builder(cw, R.style.customalertdialog);
		  		
		  		//ORIGINAL WAY TO DO IT:
	  			//AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
	  			
		  		
	  			//builder.setCancelable(false);
	  			/*
	  			// if back pressed: DOES THIS WORK????????????
				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						
						//GOTO SOME METHOD!!!!!!!!!!!!!!
						
						chooseHostGuy();
					}
				});
	  			*/
		  		
		  		
		  		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
	    	        @Override
	    	        public void onDismiss(DialogInterface dialog) {
	    	        	
	    	        	hideSystemUI();
	    	        }
	    	    });
		  		
				
	            builder.setAdapter(adapter,
	                    new DialogInterface.OnClickListener() {
	                        @Override
	                        public void onClick(final DialogInterface dialog, int item) {
	                        	
	                        	if (item == 0) {
	                        		
	                        		buttonSound.start();
	                        		
	                        		//stopService(svc);	    				
				    				
				    				Intent intent = new Intent(MainActivity1.this, Host1NewGuy.class);
				    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				    				startActivity(intent);
				    	        	
				    	        	dialog.dismiss();
				    	        	
				    	        	hideSystemUI();
								}
	                        	
	                        	else if (item == 1) {
	                        		
	                        		buttonSound.start();
	                        		
	                        		//stopService(svc);	    				
				    				
				    				Intent intent = new Intent(MainActivity1.this, Host1OldGuy.class);
				    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				    				startActivity(intent);
				    	        	
				    	        	dialog.dismiss();
				    	        	
				    	        	hideSystemUI();
								}
	                        	
	                        	else if (item == 2) {
	                        		
	                        		buttonSound.start();
	                        		
	                        		//stopService(svc);	    				
				    				
				    				//onDestroy();
				    	        	
				    	        	dialog.dismiss();
				    	        	
				    	        	hideSystemUI();
								}
	                        	
								
								//((AlertDialog) dialog).getButton(dialog.BUTTON1).setGravity(Gravity.CENTER);
								//SET TEXT SIXE IN XML						
								//View messageText = ((TextView) dialog).findViewById(R.id.title);		  		
					            //((TextView) messageText).setGravity(Gravity.CENTER);
					            //messageText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
								
								
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
	
	
	public void chooseClientGuy() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		
		final MediaPlayer buttonSound = MediaPlayer.create(MainActivity1.this, R.raw.swordswing);
		
		
		runOnUiThread(new Runnable() {
  	  	    @Override
  	  	    public void run() {
	  			
	  			// NEW WAY TO DO DIALOG
		  		final String[] items = {"New Guy", "Old Guy", "Cancel"};
	
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
	
	
		  		        holder.title.setText(items[position]);
		  		        
	
		  		        return convertView;
		  		    }
		  		};		  		
		  		
		  		
		  		// THIS WAY ALLOWS YOU TO STYLE THE DIALOG (ex. background doesn't dim.):
		  		ContextThemeWrapper cw = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
		  		AlertDialog.Builder builder = new AlertDialog.Builder(cw, R.style.customalertdialog);
		  		
		  		//ORIGINAL WAY TO DO IT:
	  			//AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
	  			
		  		
	  			//builder.setCancelable(false);
	  			/*
	  			// if back pressed: DOES THIS WORK????????????
				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						
						//GOTO SOME METHOD!!!!!!!!!!!!!!
						
						chooseClientGuy();
					}
				});
	  			*/
		  		
		  		
		  		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
	    	        @Override
	    	        public void onDismiss(DialogInterface dialog) {
	    	        	
	    	        	hideSystemUI();
	    	        }
	    	    });
		  		
				
	            builder.setAdapter(adapter,
	                    new DialogInterface.OnClickListener() {
	                        @Override
	                        public void onClick(final DialogInterface dialog, int item) {
	                        	
	                        	if (item == 0) {
	                        		
	                        		buttonSound.start();
	                        		
	                        		//stopService(svc);	    				
				    				
				    				Intent intent = new Intent(MainActivity1.this, Client1NewGuy.class);
				    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				    				startActivity(intent);
				    	        	
				    	        	dialog.dismiss();
				    	        	
				    	        	hideSystemUI();
								}
	                        	
	                        	else if (item == 1) {
	                        		
	                        		buttonSound.start();
	                        		
	                        		//stopService(svc);	    				
				    				
				    				Intent intent = new Intent(MainActivity1.this, Client1OldGuy.class);
				    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				    				startActivity(intent);
				    	        	
				    	        	dialog.dismiss();
				    	        	
				    	        	hideSystemUI();
								}
	                        	
	                        	else if (item == 2) {
	                        		
	                        		buttonSound.start();
	                        		
	                        		//stopService(svc);	    				
				    				
				    				//onDestroy();
				    	        	
				    	        	dialog.dismiss();
				    	        	
				    	        	hideSystemUI();
								}
	                        	
								
								//((AlertDialog) dialog).getButton(dialog.BUTTON1).setGravity(Gravity.CENTER);
								//SET TEXT SIXE IN XML						
								//View messageText = ((TextView) dialog).findViewById(R.id.title);		  		
					            //((TextView) messageText).setGravity(Gravity.CENTER);
					            //messageText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
								
								
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
	
	
	
	
	
	
	
	
	
	
	
	/*	
	public void doLaunchContactPicker(View view) {(DELETE/COMMENT OUT)
	    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
	            Contacts.CONTENT_URI);
	    startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
	}
	*/
	
	//===================================================================================================
	// SEPERATOR
	//===================================================================================================
	
	
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
			hostIP = GetDeviceipWiFiData();
		}

		if (MOBILE == true) {

			hostIP = GetDeviceipMobileData();
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
			
			Toast toast = Toast.makeText(MainActivity1.this, "Unbale to connect using mobile data.", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
	
	
	//===================================================================================================
	// SEPERATOR
	//===================================================================================================
		
	@Override
    public void onBackPressed() {		
		/*
		if (needtoclearui) {
			
			hideSystemUI();
			
			needtoclearui = false;
		}
		*/
		//else {
		
		//hideSystemUI();
		
		//Toast.makeText(MainActivity1.this, "SMS TEST", Toast.LENGTH_SHORT).show();
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		stopService(svc);
		
        super.onBackPressed();
        this.finish();
		
		/*
		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);
		
    	alert.setTitle("KtOG");	  	    	
    	alert.setMessage("Are you sure you want to exit?");	  	    		  	    	
    	
    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
    			
    			onBackPressedStuffToDo();    							  		    			  		    		
    		}
    	});
    	
    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
    			
    			dialog.dismiss();    					         		            		  
    		}
    	});	  	    	
    	alert.show();
    	*/
        
		//}
    }
	/*
	public void onBackPressedStuffToDo() {
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		stopService(svc);
		
        super.onBackPressed();
        this.finish();
	}	
	*/
	
	// DESTROYS EVERYTHING (EXCEPT SERVICE?)
	@Override
	protected void onDestroy() {
		
		Intent svc=new Intent(this, Badonk2SoundService.class);
		stopService(svc);
		
		//Toast.makeText(MainActivity1.this, "SMS TEST", Toast.LENGTH_SHORT).show();
		
		ArrayOfInvites.invites = new int[1];
		
	    android.os.Process.killProcess(android.os.Process.myPid());
	    
	    
	    //GC doesn't happen immediately and need time to decide which objects are eligible and ready for GC.The solution is to break any references to the listener (the junkowner), in this case from the button, which practically makes the listener as a GCroot with only short path to the junk and make the GC decide faster to reclaim the junk memory.
	    findViewById(R.id.imagebuttononeplayer).setOnClickListener(null);
	    findViewById(R.id.imagebuttonmultiplayer).setOnClickListener(null);
	    findViewById(R.id.imagebuttonabout).setOnClickListener(null);
	    
	    
	    super.onDestroy();
	}	
	
	public void onPause() {
		
		//Toast.makeText(MainActivity1.this, "SMS TEST", Toast.LENGTH_SHORT).show();
		
		super.onPause();
		
		Intent svc=new Intent(this, Badonk2SoundService.class);
		stopService(svc);
		
		/*
		if (!isMessageSent) {
			
			super.onPause();
			
			Intent svc=new Intent(this, Badonk2SoundService.class);
			stopService(svc);
		}
		
		else if (isMessageSent) {
			
			if (ArrayOfAvatars.avatar[5].equals("custom")) {
				  
				Intent intent2 = new Intent(MainActivity1.this, Host.class);
				intent2.putExtra("imageUri", imageUri.toString());
				//intent.putExtra("imageUri", imageUri);
				startActivity(intent2);
	  		}
	  		  
	        else {

	        	Intent intent3 = new Intent(MainActivity1.this, Host.class);
	        	//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	        	startActivity(intent3);
	        }
			
			super.onPause();
			
			Intent svc=new Intent(this, Badonk2SoundService.class);
			stopService(svc);
		}
		*/		
	}
	
	public void onResume() {
		
		hideSystemUI();
		
		/*
		if (!isMessageSent) {
		
			//Toast.makeText(MainActivity1.this, "SMS TEST", Toast.LENGTH_SHORT).show();
			
			super.onResume();
			
			Intent svc=new Intent(this, Badonk2SoundService.class);
			startService(svc);
		}
		
		else if (isMessageSent) {
			
			if (ArrayOfAvatars.avatar[5].equals("custom")) {
				  
				Intent intent2 = new Intent(MainActivity1.this, Host1.class);
				intent2.putExtra("imageUri", imageUri.toString());
				//intent.putExtra("imageUri", imageUri);
				startActivity(intent2);
	  		}
	  		  
	        else {

	        	Intent intent3 = new Intent(MainActivity1.this, Host1.class);
	        	//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	        	startActivity(intent3);
	        }
			
			isMessageSent = false;
			
			super.onResume();
			
			Intent svc=new Intent(this, Badonk2SoundService.class);
			startService(svc);
		}
		*/
		
		super.onResume();
		
		Intent svc=new Intent(this, Badonk2SoundService.class);
		startService(svc);
	}	
	
	
	//===================================================================================================
	// SEPERATOR
	//===================================================================================================
	
	/*
	public static void insertToDatabase(final String player){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String player = params[0];
                              
                //String name = editTextName.getText().toString();
                //String add = editTextAdd.getText().toString();
 
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("player", player));
                 
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://nedswebsite.com/insert-db.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
 
                    HttpResponse response = httpClient.execute(httpPost);
 
                    HttpEntity entity = response.getEntity(); 
          
                } catch (ClientProtocolException e) {
 
                } catch (IOException e) {
 
                }
                return "success";
            }
        }
        
        //@Override
        //protected void onPostExecute(String result) {
        //    super.onPostExecute(result);
		//
        //    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        //    TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
        //    textViewResult.setText("Inserted");
        //}
        
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(player);		
    }
    */
	
	/*
	public void savePlayerName() {
		
		try {
			
			if (numberOfPlayers == 1) {
				
				File playerName = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files", ArrayOfPlayers.player[0] + ".txt");			
				//File playerName = new File(this.getExternalFilesDir(null), ArrayOfPlayers.player[5] + ".txt");
				
				if (!playerName.exists()) {
				
					playerName.createNewFile();
				}
				else if (playerName.exists()) {
					
					Toast.makeText(MainActivity1.this, ArrayOfPlayers.player[0] + " is a saved name.", Toast.LENGTH_SHORT).show();
				}
			}
			
			else if (numberOfPlayers == 2) {//or more
				
				File playerName = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files", ArrayOfPlayers.player[5] + ".txt");			
				//File playerName = new File(this.getExternalFilesDir(null), ArrayOfPlayers.player[5] + ".txt");
				
				if (!playerName.exists()) {
				
					playerName.createNewFile();
				}
				else if (playerName.exists()) {
					
					Toast.makeText(MainActivity1.this, ArrayOfPlayers.player[5] + " is a saved name.", Toast.LENGTH_SHORT).show();
					
					
					//AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);

		        	//alert.setTitle("Player Name");
		        	//alert.setMessage("You are using the saved name " + "\"" + ArrayOfPlayers.player[5] + "\"" +".");
		        	
		        	
		        	//alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        		//public void onClick(DialogInterface dialog, int whichButton) {
			    	        	
		        			//dialog.dismiss();
	        	  		//}
		    		//});	

		        	//alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		        		//public void onClick(DialogInterface dialog, int whichButton) {
		        			
		        			//dialog.dismiss();
		        			
		        			//finish();
		        		//}
		        	//});
		        	
		        	//alert.show();
		        	
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
	
	//===================================================================================================
	// SEPERATOR
	//===================================================================================================
	
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);        
        
        hideSystemUI();
        
        // For sound for buttons:
     	final MediaPlayer buttonSound1 = MediaPlayer.create(MainActivity1.this, R.raw.swordswing);
        final MediaPlayer buttonSound2 = MediaPlayer.create(MainActivity1.this, R.raw.sworddraw1);
        
        final Intent svc=new Intent(this, Badonk2SoundService.class);
 
        // Checks the orientation of the screen.
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        	
        	setContentView(R.layout.activity_main_activity1);
        	
        	ImageButton onePlayerButton = (ImageButton) findViewById(R.id.imagebuttononeplayer);
    		ImageButton multiPlayerButton = (ImageButton) findViewById(R.id.imagebuttonmultiplayer);
    		ImageButton aboutButton = (ImageButton) findViewById(R.id.imagebuttonabout);
    		Button recordsButton = (Button) findViewById(R.id.buttonrecords);
    		Button guysButton = (Button) findViewById(R.id.buttonguys);
    		
    		final MediaPlayer buttonSound = MediaPlayer.create(MainActivity1.this, R.raw.swordswing);
    		
    		
    		onePlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
    			public void onClick(View v) {
    			    
                	numberOfPlayers = 1;//will be 1 or 2 to indicate single or multiplayer
                	
    	        	buttonSound.start();
    	        	
    	        	//stopService(svc);
    	        	
    	        	//Toast.makeText(MainActivity1.this,"One player button is working!!", Toast.LENGTH_SHORT).show();
    	        	//USE THIS WHEN READY??:
    	        	//Intent openNewGuyOldGuy = new Intent("com.nedswebsite.ktog.NewGuyOldGuy");
    				//startActivity(openNewGuyOldGuy);
    	        	
    	        	//AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);
    	        	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this, R.style.customalertdialog);
    	        	//AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this, R.style.customalertdialog).setInverseBackgroundForced(true);
    	        	
    	        	
    	        	Toast toast = Toast.makeText(MainActivity1.this, "Enter name", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
    	  			View view = toast.getView();
    	  			view.setBackgroundResource(R.drawable.centerscroll3toast);
    	  			toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER

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
    	        	
    	        	
    	        	//alert.setCancelable(false);
    	        	alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
    	    	        @Override
    	    	        public void onDismiss(DialogInterface dialog) {
    	    	        	
    	    	        	hideSystemUI();
    	    	        }
    	    	    });
    	        	
    	        	
    	        	//alert.setTitle("One Player");
    	        	//alert.setMessage("Enter name:");
    	
    	        	// Set an EditText view to get user input:
    	        	final EditText input = new EditText(MainActivity1.this);
    	        	input.setSingleLine(true);
    	        	// Limits to 1 line (clicking return is like clicking "ok".)
    	        	alert.setView(input);
    	        	// Limits the number of characters entered to 14.
    	        	InputFilter[] FilterArray = new InputFilter[1];
    	        	FilterArray[0] = new InputFilter.LengthFilter(14);
    	        	input.setFilters(FilterArray);
    	        	
    	        	// THIS WILL GET KEYBOARD AUTOMATICALLY FOR S4:
    	        	/*
    	        	input.requestFocus();
    	            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    	            */
    	        	
    	        	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	        	public void onClick(DialogInterface dialog, int whichButton) {        		         		
    	        		
    	        		if (input.getText().toString().isEmpty()) {
    	        			
    	        			Toast toast = Toast.makeText(MainActivity1.this, "Name cannot be blank.", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    	        		     
    	        			hideSystemUI();
    	        		}
    	        		
    	        		else {
    	        		     
    	        			//needtoclearui = true;//USE:builder.setOnDismissListener
    		        		
    		        		// NEED TO SEND TO ARRAY HERE:
    		        		String playername = input.getText().toString();
    		            	String playercomputer = "Computer".toString();
    		            	
    		            	ArrayOfPlayers.player[0] = playername;
    		            	ArrayOfPlayers.player[1] = playercomputer;
    		            	
    		            	//savePlayerName();
    		            	
    		            	//insertToDatabase(playername);	        	
    			        	
    			        	// ARRAY ADAPTER WITH ICON STUFF:	        	
    			        	
    			        	final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
    			    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
    			    		
    			    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
    			    		
    			    		
    			    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
    			    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
    			    		//builder.setIcon(R.drawable.computerhead);
    			    		//builder.setTitle("Choose Your Avatar");
    			    		//builder.setMessage("Choose Your Avatar");
    			    		
    			    		//Toast.makeText(MainActivity1.this,"Choose Your Avatar", Toast.LENGTH_SHORT).show();
    			    		
    			    		Toast toast = Toast.makeText(MainActivity1.this, "Choose An Avatar", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
    			  			View view = toast.getView();
    			  			view.setBackgroundResource(R.drawable.centerscroll3toast);
    			  			//view.bringToFront();
    			  			
    			  			//view.setRotation(45);//ALREADY ROTATED IN GIMP
    			  			//toast.setGravity(Gravity.TOP, whichButton, whichButton);
    			  			toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
    			  			
    			  			//toast.setGravity(Gravity.TOP|Gravity.RIGHT, 450, 300);
    			  			//toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);

    			  			TextView text = (TextView) view.findViewById(android.R.id.message);
    			  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
    			  			text.setTypeface(typeFace);
    			  			//text.setTextSize(40);
    			  			text.setTextColor(Color.parseColor("#FFFFFF"));
    			  			
    			  			//text.setRotation(-45);
    			  			//toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
    			  			//text.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
    			  			text.setGravity(Gravity.CENTER);
    			  			
    			  			toast.show();
    			  			
    			    		/*IN CASE ABOVE STOPS WORKING:
    			    		LayoutInflater inflater = getLayoutInflater();

    			    		View layout = inflater.inflate(R.layout.custom_toast,
    			    		(ViewGroup) findViewById(R.id.custom_toast_layout_id));

    			    		// set a message
    			    		TextView text = (TextView) layout.findViewById(R.id.text);
    			    		text.setText("Choose An Avatar");

    			    		// Toast...
    			    		Toast toast = new Toast(getApplicationContext());
    			    		toast.setGravity(Gravity.TOP_VERTICAL, 0, 0);
    			    		toast.setDuration(Toast.LENGTH_SHORT);
    			    		toast.setView(layout);
    			    		toast.show();
    			    		
    			    		
    			    		(in res/layout/custom_toast.xml)
    			    		<?xml version="1.0" encoding="utf-8"?>
    						<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    					    android:id="@+id/custom_toast_layout_id"
    					    android:layout_width="fill_parent"
    					    android:layout_height="fill_parent"
    					    android:background="#FFF"
    					    android:orientation="horizontal"
    					    android:padding="5dp" >

    	    				<TextView
    				        android:id="@+id/text"
    				        android:layout_width="wrap_content"
    				        android:layout_height="fill_parent"
    				        android:textColor="#FFF" />

    						</LinearLayout>
    			  			*/
    			  			
    			    		
    			    		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
    			    	        @Override
    			    	        public void onDismiss(DialogInterface dialog) {
    			    	        	
    			    	        	hideSystemUI();
    			    	        }
    			    	    });
    			    		
    			    		
    			    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
    			    			public void onClick(DialogInterface dialog, int item) { 
    			    								
    			    				if (item == 0) {
    			    					ArrayOfAvatars.avatar[0] = "computer";
    			    					
    			    					stopService(svc);	    				
    				    				
    				    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
    				    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    				    				startActivity(intent);
    				    	        	
    				    	        	dialog.dismiss();
    				    	        	
    				    	        	hideSystemUI();
    			    				}
    			    				else if (item == 1) {
    			    					ArrayOfAvatars.avatar[0] = "crossedswords";
    			    					
    			    					stopService(svc);	    				
    				    				
    				    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
    				    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    				    				startActivity(intent);
    				    	        	
    				    	        	dialog.dismiss();
    				    	        	
    				    	        	hideSystemUI();
    			    				}
    			    				else if (item == 2) {
    			    					ArrayOfAvatars.avatar[0] = "stonedead";
    			    					
    			    					stopService(svc);	    				
    				    				
    				    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
    				    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    				    				startActivity(intent);
    				    	        	
    				    	        	dialog.dismiss();
    				    	        	
    				    	        	hideSystemUI();
    			    				}
    			    				else if (item == 3) {
    			    					ArrayOfAvatars.avatar[0] = "custom";
    			    					
    			    					stopService(svc);
    			    					
    			    					openGallery();
    			    					
    			    					dialog.dismiss();
    			    					
    			    					hideSystemUI();
    			    				}    				
    			    	        	
    			    	        	//finish();
    		        	  		}
    			    		});	    		
    			        	
    			            builder.create().show();
    	        		}
    	        	  }
    	        	});
    	
    	        	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    	        	  public void onClick(DialogInterface dialog, int whichButton) {
    	        	    
    	        		  hideSystemUI();
    	        	  }
    	        	});
    	        	
    	        	alert.show();
    			}
    		});		
    		
    		multiPlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
    			public void onClick(View v) {
    			    /*
    				// Here, thisActivity is the current activity
    				if (ContextCompat.checkSelfPermission(MainActivity1.this,
    						android.Manifest.permission.READ_CONTACTS)
    				        != PackageManager.PERMISSION_GRANTED) {
    	
    				    // Permission is not granted
    				    // Should we show an explanation?
    				    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity1.this,
    				    		android.Manifest.permission.READ_CONTACTS)) {
    				        // Show an explanation to the user *asynchronously* -- don't block
    				        // this thread waiting for the user's response! After the user
    				        // sees the explanation, try again to request the permission.
    				    } else {
    				        // No explanation needed; request the permission
    				        ActivityCompat.requestPermissions(MainActivity1.this,
    				                new String[]{android.Manifest.permission.READ_CONTACTS},
    				                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
    	
    				        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
    				        // app-defined int constant. The callback method gets the
    				        // result of the request.
    				    }
    				} else {
    					*/
    					numberOfPlayers = 2;//will be 1 or 2 to indicate single or multiplayer
    	            	
    		        	buttonSound.start();
    		        	
    		        	multiplayer = "yes";
    		        	
    		        	
    		        	goToHostOrJoin();
    		        	
    		        	/*
    		        	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this, R.style.customalertdialog);
    		
    		        	//alert.setTitle("Multiplayer");
    		        	//alert.setMessage("Enter Name");
    		        	
    		        	Toast toast = Toast.makeText(MainActivity1.this, "Enter name", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    		        	
    		        	
    		        	alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
    		    	        @Override
    		    	        public void onDismiss(DialogInterface dialog) {
    		    	        	
    		    	        	hideSystemUI();
    		    	        }
    		    	    });
    		        	
    		        	
    		        	//alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
    					//	@Override
    					//	public void onCancel(DialogInterface dialog) {		  							
    					//		
    					//		//IF NOTHING DOESN'T WORK, TRY:
    					//		
    					//		stopService(svc);
    					//		
    					//		Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    				    //    	MainActivity1.this.startActivity(i);
    					//	}
    					//});
    		        	
    		
    		        	// Set an EditText view to get user input:
    		        	final EditText input = new EditText(MainActivity1.this);
    		        	input.setSingleLine(true);
    		        	// Limits to 1 line (clicking return is like clicking "ok".)
    		        	alert.setView(input);
    		        	// Limits the number of characters entered to 14.
    		        	InputFilter[] FilterArray = new InputFilter[1];
    		        	FilterArray[0] = new InputFilter.LengthFilter(14);
    		        	input.setFilters(FilterArray);
    		        	
    		        	// THIS WILL GET KEYBOARD AUTOMATICALLY FOR S4:
    		        	
    		        	//input.requestFocus();
    		            //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    		            //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    		            
    		        	
    		        	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    		        	public void onClick(DialogInterface dialog, int whichButton) {        		         		
    		        		
    		        		if (input.getText().toString().isEmpty()) {
    		        			
    		        			Toast toast = Toast.makeText(MainActivity1.this, "Name cannot be blank.", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    		        		     
    		        			hideSystemUI();
    		        		}
    		        		
    		        		else {
    		        			
    			        		// NEED TO SEND TO ARRAY HERE:
    			        		String playername = input.getText().toString();
    			            	//String playercomputer = "Computer".toString();
    			            	
    			            	ArrayOfPlayers.player[5] = playername;
    			            	//ArrayOfPlayers.player[1] = playercomputer;
    			            	
    			            	//savePlayerName();
    			            	
    			            	//insertToDatabase(playername);	        	
    				        	
    				        	// ARRAY ADAPTER WITH ICON STUFF:	        	
    				        	
    				        	final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
    				    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
    				    		
    				    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
    				    		
    				    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
    				    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
    				    		//builder.setIcon(R.drawable.computerhead);
    				    		
    				    		//builder.setTitle("Choose Your Avatar");
    				    		//Toast.makeText(MainActivity1.this,"Choose Your Avatar", Toast.LENGTH_SHORT).show();
    				    		
    				    		
    				    		Toast toast = Toast.makeText(MainActivity1.this, "Choose An Avatar", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    				    		
    				    		
    				    		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
    				    	        @Override
    				    	        public void onDismiss(DialogInterface dialog) {
    				    	        	
    				    	        	hideSystemUI();
    				    	        }
    				    	    });
    				    		
    				    		
    				    		//builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
    		    				//	@Override
    		    				//	public void onCancel(DialogInterface dialog) {		  							
    		    				//		
    		    				//		//IF NOTHING DOESN'T WORK, TRY:
    		    				//		
    		    				//		stopService(svc);
    		    				//		
    		    				//		Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    		    			    //    	MainActivity1.this.startActivity(i);
    		    				//	}
    		    				//});
    				    		
    				    		
    				    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
    				    			public void onClick(DialogInterface dialog, int item) { 
    				    								
    				    				if (item == 0) {
    				    					ArrayOfAvatars.avatar[5] = "computer";
    				    					
    				    					goToHostOrJoin();
    					    	        	
    					    	        	dialog.dismiss();
    					    	        	
    					    	        	hideSystemUI();
    				    				}
    				    				else if (item == 1) {
    				    					ArrayOfAvatars.avatar[5] = "crossedswords";
    				    					
    				    					goToHostOrJoin();
    					    	        	
    					    	        	dialog.dismiss();
    					    	        	
    					    	        	hideSystemUI();
    				    				}
    				    				else if (item == 2) {
    				    					ArrayOfAvatars.avatar[5] = "stonedead";
    				    					
    				    					goToHostOrJoin();
    					    	        	
    					    	        	dialog.dismiss();
    					    	        	
    					    	        	hideSystemUI();
    				    				}
    				    				else if (item == 3) {
    				    					ArrayOfAvatars.avatar[5] = "custom";	    					
    				    					
    				    					openGallery();
    				    					
    				    					dialog.dismiss();
    				    					
    				    					hideSystemUI();
    				    				}    				
    				    	        	
    				    	        	//finish();
    			        	  		}
    				    		});	    		
    				        	
    				            builder.create().show();            
    		        		}
    		        	  }
    		        	});
    		        	
    		        	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		        	  public void onClick(DialogInterface dialog, int whichButton) {
    		        	    
    		        		  hideSystemUI();
    		        	  }
    		        	});
    		        	
    		        	alert.show();
    					
    				    // Permission has already been granted
    				//}
    		        */
    			}
    		});
    		
    		aboutButton.setOnClickListener(new View.OnClickListener() {
                @Override
    			public void onClick(View v) {
    			                    	
    	        	buttonSound.start();
    	        	
    	        	//Think I need this so user doesn't have to push 'back' more than once (possibly)?
    	        	//finish();
    	        	
    	        	stopService(svc);
    	        	
    	        	Intent i = new Intent(MainActivity1.this, Rules.class);
    	        	MainActivity1.this.startActivity(i);
    	        	
    	        	//Toast.makeText(MainActivity1.this,"About button is working!!", Toast.LENGTH_SHORT).show();
    	        	
    	        	//USE THIS WHEN READY??:
    	        	//Intent openMain2Activity = new Intent("com.example.ktog1.MAIN2ACTIVITY");
    				//startActivity(openMain2Activity);
    			}
    		});
    		
    		recordsButton.setOnClickListener(new View.OnClickListener() {
                @Override
    			public void onClick(View v) {
    			                    	
    	        	buttonSound.start();
    	        	
    	        	stopService(svc);
        			
        			//Intent i = new Intent(MainActivity1.this, playerNamesAndRecords.class);
        			//MainActivity1.this.startActivity(i);
        			
    	        	if (count == 0) {
    	        		
    	        		//Toast.makeText(MainActivity1.this, "No Records", Toast.LENGTH_SHORT).show();
    	    			
    	        		
    	        		Toast toast = Toast.makeText(MainActivity1.this, "No Records", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    	        		
    	        		
    	    			stopService(svc);
    					startService(svc);
    	        	}
    	        	
    	        	else {
    	        		
    	        		stopService(svc);
    	    			
    	    			Intent i = new Intent(MainActivity1.this, playerNamesAndRecords.class);
    	    			MainActivity1.this.startActivity(i);
    	        	}
    	        	
    	        	
    	        	/*
    	        	String filename = "myPrefs";
    	        	File f = new File(getApplicationContext().getApplicationInfo().dataDir + "/shared_prefs/" + filename + ".xml");
    	        	
        			if (f.exists()) {
        				
        				stopService(svc);
    	    			
    	    			Intent i = new Intent(MainActivity1.this, playerNamesAndRecords.class);
    	    			MainActivity1.this.startActivity(i);
        			}
        			    
        			else {
        				
        				Toast.makeText(MainActivity1.this, "No Records", Toast.LENGTH_SHORT).show();
    	    			
    	    			stopService(svc);
    					startService(svc);
        			}
    	        	*/
    	        	
    	        	/*
    	        	SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    	        	String value = pref.getString("unknown_key",null);
    	        	
    	        	if (value == null) {
    	        	    
    	        		Toast.makeText(MainActivity1.this, "No Records", Toast.LENGTH_SHORT).show();
    	    			
    	    			stopService(svc);
    					startService(svc);
    	        		
    	        	} else {

    	        		stopService(svc);
    	    			
    	    			Intent i = new Intent(MainActivity1.this, playerNamesAndRecords.class);
    	    			MainActivity1.this.startActivity(i);
    	        	}
    	        	*/
    	        	
    	        	//stopService(svc);
    	        	
    	        	/*
    	        	File directory = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files");
    	    		File[] files = directory.listFiles();
    	          
    	    		if (files != null) {
    	    			
    	    			stopService(svc);
    	    			
    	    			Intent i = new Intent(MainActivity1.this, playerNamesAndRecords.class);
    	    			MainActivity1.this.startActivity(i);
    	    		}
    	    		
    	    		else {
    	    			
    	    			Toast.makeText(MainActivity1.this, "No Records", Toast.LENGTH_SHORT).show();
    	    			
    	    			stopService(svc);
    					startService(svc);
    	    		}
    	    		*/
    			}
    		});
    		
    		guysButton.setOnClickListener(new View.OnClickListener() {
                @Override
    			public void onClick(View v) {
                	
    	        	buttonSound.start();
    	        	
    	        	
    	        	stopService(svc);
    	        	
    	        	
    	        	//getCount();
    	        	
    	        	//Toast.makeText(MainActivity1.this, String.valueOf(count), Toast.LENGTH_SHORT).show();
    	        	
    	        	
    	        	if (count == 0) {
    	        		
    	        		//Toast.makeText(MainActivity1.this, "No Guys", Toast.LENGTH_SHORT).show();
    			   		
    	        		
    	        		Toast toast = Toast.makeText(MainActivity1.this, "No Guys", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    	        		
    	        		
    					stopService(svc);
    					startService(svc);
    	        	}
    	        	
    	        	else {
    	        	
    	        	//try {
    				      
    	        			getPlayerNamesFromFile();
    	        			
    	        			
    	        			stopService(svc);
    						startService(svc);
    	        			
    	        			
    	        			// Instead of String[] items, Here you can also use ArrayList for your custom object..
    	    	        	
    	    	        	final String[] nameForAdapter = new String[count];
    	    	        	
    	    	        	for (int i = 0; i < count; i++) {
    	    	        		
    	    	        		nameForAdapter[i] = name[i];
    	    	        	}
    	    	        	
    	    		  		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row, R.id.title, nameForAdapter) {
    	    	
    	    		  		    ViewHolder holder;
    	    		  		    Drawable icon;//FOR LAST AVATAR THAT WAS USED??? (LOCATION CAN BE SAVE LIKE OTHER GAME DATA)
    	    	
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
    	    	
    	    		  		        holder.title.setText(nameForAdapter[position]);
    	    		  		        //holder.icon.setImageDrawable(drawable);     
    	    		  		        
    	    	
    	    		  		        return convertView;
    	    		  		    }
    	    		  		};
    	    		  		
    	    		  		
    	    		  		// THIS WAY ALLOWS YOU TO STYLE THE DIALOG (ex. background doesn't dim.):
    	    		  		ContextThemeWrapper cw = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
    	    		  		AlertDialog.Builder builder = new AlertDialog.Builder(cw, R.style.customalertdialog);		  			  		
    	    		  		
    	    	  			
    	    		  		//builder.setTitle("Choose Your Guy");
    	    		  		//Toast.makeText(MainActivity1.this,"Choose Your Guy", Toast.LENGTH_SHORT).show();
    	    		  		
    	    		  		
    	    	  			//builder.setCancelable(false);
    	    		  		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
    	    	    	        @Override
    	    	    	        public void onDismiss(DialogInterface dialog) {
    	    	    	        	
    	    	    	        	hideSystemUI();
    	    	    	        }
    	    	    	    });
    	    		  		
    	    		  		
    	    	  			/*
    	    				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
    	    					@Override
    	    					public void onCancel(DialogInterface dialog) {		  							
    	    						
    	    						//IF NOTHING DOESN'T WORK, TRY:
    	    						
    	    						stopService(svc);
    	    						
    	    						Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    	    			        	MainActivity1.this.startActivity(i);
    	    					}
    	    				});
    	    	  			*/
    	    				
    	    	            builder.setAdapter(adapter,
    	    	                    new DialogInterface.OnClickListener() {
    	    	                        @Override
    	    	                        public void onClick(final DialogInterface dialog, int item) {
    	    	                        	
    	    	                        	for (int i = 0; i < count; i++) {
    	    	                        	
    	    		                        	if (item == i) {
    	    		                        		
    	    		                        		buttonSound.start();
    	    		                        		
    	    		                        		
    	    		                        		//ArrayOfPlayers.player[5] = name[i];
    	    		                        		tempName = name[i];
    	    		                        		
    	    		                        		//Toast.makeText(MainActivity1.this, name[i], Toast.LENGTH_SHORT).show();
    	    		                        		
    	    		                        		
    	    		                        		final String[] items = {"One Player", "Multiplayer"};
    	    		                        		
    	    		                        		// Instead of String[] items, Here you can also use ArrayList for your custom object..
    	    		                        		
    	    		                		  		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row, R.id.title,items) {
    	    		                	
    	    		                		  		    ViewHolder holder;
    	    		                		  		    Drawable icon;//FOR LAST AVATAR THAT WAS USED??? (LOCATION CAN BE SAVE LIKE OTHER GAME DATA)
    	    		                	
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
    	    		                		  		ContextThemeWrapper cw = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
    	    		                		  		AlertDialog.Builder builder = new AlertDialog.Builder(cw, R.style.customalertdialog);		  			  		
    	    		                		  		
    	    		                	  			
    	    		                		  		//builder.setTitle("Choose Your Game");
    	    		                		  		//Toast.makeText(MainActivity1.this,"Choose Your Game", Toast.LENGTH_SHORT).show();
    	    		                		  		
    	    		                		  		
    	    		                	  			//builder.setCancelable(false);
    	    		                		  		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
    	    		        	    	    	        @Override
    	    		        	    	    	        public void onDismiss(DialogInterface dialog) {
    	    		        	    	    	        	
    	    		        	    	    	        	hideSystemUI();
    	    		        	    	    	        }
    	    		        	    	    	    });
    	    		                		  		
    	    		                		  		/*
    	    		                		  		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
    	    		        	    					@Override
    	    		        	    					public void onCancel(DialogInterface dialog) {		  							
    	    		        	    						
    	    		        	    						//IF NOTHING DOESN'T WORK, TRY:
    	    		        	    						
    	    		        	    						stopService(svc);
    	    		        	    						
    	    		        	    						Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    	    		        	    			        	MainActivity1.this.startActivity(i);
    	    		        	    					}
    	    		        	    				});
    	    		                				*/
    	    		                		  		
    	    		                	            builder.setAdapter(adapter,
    	    		                	                    new DialogInterface.OnClickListener() {
    	    		                	                        @Override
    	    		                	                        public void onClick(final DialogInterface dialog, int item) {
    	    		                	                        	
    	    	                		                        	if (item == 0) {
    	    	                		                        		
    	    	                		                        		numberOfPlayers = 1;//will be 1 or 2 to indicate single or multiplayer
    	    	                		                        		
    	    	                		                        		buttonSound.start();
    	    	                		                        		
    	    	                		                        		
    	    	                		                        		ArrayOfPlayers.player[0] = tempName;
    	    	                		                        		ArrayOfPlayers.player[1] = "Computer";
    	    	                		                        		
    	    	                		                        		
    	    	                		                        		final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
    	    	                		            		    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
    	    	                		            		    		
    	    	                		            		    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
    	    	                		            		    		
    	    	                		            		    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
    	    	                		            		    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
    	    	                		            		    		//builder.setIcon(R.drawable.computerhead);
    	    	                		            		    		
    	    	                		            		    		//builder.setTitle("Choose Your Avatar");
    	    	                		            		    		//Toast.makeText(MainActivity1.this,"Choose Your Avatar", Toast.LENGTH_SHORT).show();
    	    	                		            		    		
    	    	                		            		    		
    	    	                		            		    		Toast toast = Toast.makeText(MainActivity1.this, "Choose An Avatar", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    	    	                		            		    		
    	    	                		            		    		
    	    	                		            		    		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
    	    	                		        	    	    	        @Override
    	    	                		        	    	    	        public void onDismiss(DialogInterface dialog) {
    	    	                		        	    	    	        	
    	    	                		        	    	    	        	hideSystemUI();
    	    	                		        	    	    	        }
    	    	                		        	    	    	    });
    	    	                		            		    		
    	    	                		            		    		/*
    	    	                		            		    		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
    	    	                		        	    					@Override
    	    	                		        	    					public void onCancel(DialogInterface dialog) {		  							
    	    	                		        	    						
    	    	                		        	    						//IF NOTHING DOESN'T WORK, TRY:
    	    	                		        	    						
    	    	                		        	    						stopService(svc);
    	    	                		        	    						
    	    	                		        	    						Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    	    	                		        	    			        	MainActivity1.this.startActivity(i);
    	    	                		        	    					}
    	    	                		        	    				});
    	    	                		            		    		*/
    	    	                		            		    		
    	    	                		            		    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
    	    	                		            		    			public void onClick(DialogInterface dialog, int item) { 
    	    	                		            		    								
    	    	                		            		    				if (item == 0) {
    	    	                		            		    					ArrayOfAvatars.avatar[0] = "computer";
    	    	                		            		    					
    	    	                		            		    					stopService(svc);	    				
    	    	                		            			    				
    	    	                		            			    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
    	    	                		            			    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    	    	                		            			    				startActivity(intent);
    	    	                		            			    	        	
    	    	                		            			    	        	dialog.dismiss();
    	    	                		            			    	        	
    	    	                		            			    	        	hideSystemUI();
    	    	                		            		    				}
    	    	                		            		    				else if (item == 1) {
    	    	                		            		    					ArrayOfAvatars.avatar[0] = "crossedswords";
    	    	                		            		    					
    	    	                		            		    					stopService(svc);	    				
    	    	                		            			    				
    	    	                		            			    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
    	    	                		            			    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    	    	                		            			    				startActivity(intent);
    	    	                		            			    	        	
    	    	                		            			    	        	dialog.dismiss();
    	    	                		            			    	        	
    	    	                		            			    	        	hideSystemUI();
    	    	                		            		    				}
    	    	                		            		    				else if (item == 2) {
    	    	                		            		    					ArrayOfAvatars.avatar[0] = "stonedead";
    	    	                		            		    					
    	    	                		            		    					stopService(svc);	    				
    	    	                		            			    				
    	    	                		            			    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
    	    	                		            			    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    	    	                		            			    				startActivity(intent);
    	    	                		            			    	        	
    	    	                		            			    	        	dialog.dismiss();
    	    	                		            			    	        	
    	    	                		            			    	        	hideSystemUI();
    	    	                		            		    				}
    	    	                		            		    				else if (item == 3) {
    	    	                		            		    					ArrayOfAvatars.avatar[0] = "custom";
    	    	                		            		    					
    	    	                		            		    					stopService(svc);
    	    	                		            		    					
    	    	                		            		    					openGallery();
    	    	                		            		    					
    	    	                		            		    					dialog.dismiss();
    	    	                		            		    					
    	    	                		            		    					hideSystemUI();
    	    	                		            		    				}    				
    	    	                		            		    	        	
    	    	                		            		    	        	//finish();
    	    	                		            	        	  		}
    	    	                		            		    		});	    		
    	    	                		            		        	
    	    	                		            		            builder.create().show();
    	    	                		                        	}
    	    	                		                        	
    	    	                		                        	else if (item == 1) {
    	    	                		                        		
    	    	                		                        		numberOfPlayers = 2;//will be 1 or 2 to indicate single or multiplayer
    	    	                		                        		
    	    	                		                        		buttonSound.start();
    	    	                		                        		
    	    	                		                        		multiplayer = "yes";
    	    	                		                        		
    	    	                		                        		
    	    	                		                        		goToHostOrJoin();
    	    	                		                        		
    	    	                		                        		
    	    	                		                        		/*
    	    	                		                        		ArrayOfPlayers.player[5] = tempName;
    	    	                		                        		
    	    	                		                        		
    	    	                		                        		final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
    	    	                		            		    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
    	    	                		            		    		
    	    	                		            		    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
    	    	                		            		    		
    	    	                		            		    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
    	    	                		            		    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
    	    	                		            		    		//builder.setIcon(R.drawable.computerhead);
    	    	                		            		    		
    	    	                		            		    		//builder.setTitle("Choose Your Avatar");
    	    	                		            		    		//Toast.makeText(MainActivity1.this,"Choose Your Avatar", Toast.LENGTH_SHORT).show();
    	    	                		            		    		
    	    	                		            		    		
    	    	                		            		    		Toast toast = Toast.makeText(MainActivity1.this, "Choose An Avatar", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    	    	                		            		    		
    	    	                		            		    		
    	    	                		            		    		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
    	    	                		        	    	    	        @Override
    	    	                		        	    	    	        public void onDismiss(DialogInterface dialog) {
    	    	                		        	    	    	        	
    	    	                		        	    	    	        	hideSystemUI();
    	    	                		        	    	    	        }
    	    	                		        	    	    	    });
    	    	                		            		    		
    	    	                		            		    		
    	    	                		            		    		//builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
    	    	                		        	    				//	@Override
    	    	                		        	    				//	public void onCancel(DialogInterface dialog) {		  							
    	    	                		        	    				//		
    	    	                		        	    				//		//IF NOTHING DOESN'T WORK, TRY:
    	    	                		        	    				//		
    	    	                		        	    				//		stopService(svc);
    	    	                		        	    				//		
    	    	                		        	    				//		Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    	    	                		        	    			    //    	MainActivity1.this.startActivity(i);
    	    	                		        	    				//	}
    	    	                		        	    				//});
    	    	                		            		    		
    	    	                		            		    		
    	    	                		            		    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
    	    	                		            		    			public void onClick(DialogInterface dialog, int item) { 
    	    	                		            		    								
    	    	                		            		    				if (item == 0) {
    	    	                		            		    					ArrayOfAvatars.avatar[5] = "computer";
    	    	                		            		    					
    	    	                		            		    					goToHostOrJoin();
    	    	                		            			    	        	
    	    	                		            			    	        	dialog.dismiss();
    	    	                		            			    	        	
    	    	                		            			    	        	hideSystemUI();
    	    	                		            		    				}
    	    	                		            		    				else if (item == 1) {
    	    	                		            		    					ArrayOfAvatars.avatar[5] = "crossedswords";
    	    	                		            		    					
    	    	                		            		    					goToHostOrJoin();
    	    	                		            			    	        	
    	    	                		            			    	        	dialog.dismiss();
    	    	                		            			    	        	
    	    	                		            			    	        	hideSystemUI();
    	    	                		            		    				}
    	    	                		            		    				else if (item == 2) {
    	    	                		            		    					ArrayOfAvatars.avatar[5] = "stonedead";
    	    	                		            		    					
    	    	                		            		    					goToHostOrJoin();
    	    	                		            			    	        	
    	    	                		            			    	        	dialog.dismiss();
    	    	                		            			    	        	
    	    	                		            			    	        	hideSystemUI();
    	    	                		            		    				}
    	    	                		            		    				else if (item == 3) {
    	    	                		            		    					ArrayOfAvatars.avatar[5] = "custom";	    					
    	    	                		            		    					
    	    	                		            		    					openGallery();
    	    	                		            		    					
    	    	                		            		    					dialog.dismiss();
    	    	                		            		    					
    	    	                		            		    					hideSystemUI();
    	    	                		            		    				}    				
    	    	                		            		    	        	
    	    	                		            		    	        	//finish();
    	    	                		            	        	  		}
    	    	                		            		    		});	    		
    	    	                		            		        	
    	    	                		            		            builder.create().show();
    	    	                		            		            */
    	    	                		                        	}
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
    	    		                	            
    	    		                	            
    	    		                	            //h.removeCallbacks(this);
    	    		                	        	
    	    		                	        	//Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    	    		                	        	//MainActivity1.this.startActivity(i);
    	    		                        	}
    	    	                        	}
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
    	    	            
    	    	            
    	    	            //h.removeCallbacks(this);
    	    	        	
    	    	        	//Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    	    	        	//MainActivity1.this.startActivity(i);
    	        			
    				   //} catch (Exception e) {
    				      
    				   		//Toast.makeText(MainActivity1.this, "No Guys", Toast.LENGTH_SHORT).show();
    				   		
    						//stopService(svc);
    						//startService(svc);
    				//}
                	}
    			}
    		});
    		
        	buttonSound1.start();        	
        }
        
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
        	
        	hideSystemUI();
        	
        	setContentView(R.layout.activity_main_activity1);
        	
        	final ImageButton onePlayerButton = (ImageButton) findViewById(R.id.imagebuttononeplayer);
    		final ImageButton multiPlayerButton = (ImageButton) findViewById(R.id.imagebuttonmultiplayer);
    		final ImageButton aboutButton = (ImageButton) findViewById(R.id.imagebuttonabout);
    		final Button recordsButton = (Button) findViewById(R.id.buttonrecords);
    		final Button guysButton = (Button) findViewById(R.id.buttonguys);
    		
    		final MediaPlayer buttonSound = MediaPlayer.create(MainActivity1.this, R.raw.swordswing);
    		

    		onePlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
    			public void onClick(View v) {
    			    
                	numberOfPlayers = 1;//will be 1 or 2 to indicate single or multiplayer
                	
    	        	buttonSound.start();
    	        	
    	        	//stopService(svc);
    	        	
    	        	//Toast.makeText(MainActivity1.this,"One player button is working!!", Toast.LENGTH_SHORT).show();
    	        	//USE THIS WHEN READY??:
    	        	//Intent openNewGuyOldGuy = new Intent("com.nedswebsite.ktog.NewGuyOldGuy");
    				//startActivity(openNewGuyOldGuy);
    	        	
    	        	//AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);
    	        	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this, R.style.customalertdialog);
    	        	//AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this, R.style.customalertdialog).setInverseBackgroundForced(true);
    	        	
    	        	
    	        	Toast toast = Toast.makeText(MainActivity1.this, "Enter name", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
    	  			View view = toast.getView();
    	  			view.setBackgroundResource(R.drawable.centerscroll3toast);
    	  			toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER

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
    	        	
    	        	
    	        	//alert.setCancelable(false);
    	        	alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
    	    	        @Override
    	    	        public void onDismiss(DialogInterface dialog) {
    	    	        	
    	    	        	hideSystemUI();
    	    	        }
    	    	    });
    	        	
    	        	
    	        	//alert.setTitle("One Player");
    	        	//alert.setMessage("Enter name:");
    	
    	        	// Set an EditText view to get user input:
    	        	final EditText input = new EditText(MainActivity1.this);
    	        	input.setSingleLine(true);
    	        	// Limits to 1 line (clicking return is like clicking "ok".)
    	        	alert.setView(input);
    	        	// Limits the number of characters entered to 14.
    	        	InputFilter[] FilterArray = new InputFilter[1];
    	        	FilterArray[0] = new InputFilter.LengthFilter(14);
    	        	input.setFilters(FilterArray);
    	        	
    	        	// THIS WILL GET KEYBOARD AUTOMATICALLY FOR S4:
    	        	/*
    	        	input.requestFocus();
    	            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    	            */
    	        	
    	        	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	        	public void onClick(DialogInterface dialog, int whichButton) {        		         		
    	        		
    	        		if (input.getText().toString().isEmpty()) {
    	        			
    	        			Toast toast = Toast.makeText(MainActivity1.this, "Name cannot be blank.", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    	        		     
    	        			hideSystemUI();
    	        		}
    	        		
    	        		else {
    	        		     
    	        			//needtoclearui = true;//USE:builder.setOnDismissListener
    		        		
    		        		// NEED TO SEND TO ARRAY HERE:
    		        		String playername = input.getText().toString();
    		            	String playercomputer = "Computer".toString();
    		            	
    		            	ArrayOfPlayers.player[0] = playername;
    		            	ArrayOfPlayers.player[1] = playercomputer;
    		            	
    		            	//savePlayerName();
    		            	
    		            	//insertToDatabase(playername);	        	
    			        	
    			        	// ARRAY ADAPTER WITH ICON STUFF:	        	
    			        	
    			        	final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
    			    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
    			    		
    			    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
    			    		
    			    		
    			    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
    			    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
    			    		//builder.setIcon(R.drawable.computerhead);
    			    		//builder.setTitle("Choose Your Avatar");
    			    		//builder.setMessage("Choose Your Avatar");
    			    		
    			    		//Toast.makeText(MainActivity1.this,"Choose Your Avatar", Toast.LENGTH_SHORT).show();
    			    		
    			    		Toast toast = Toast.makeText(MainActivity1.this, "Choose An Avatar", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
    			  			View view = toast.getView();
    			  			view.setBackgroundResource(R.drawable.centerscroll3toast);
    			  			//view.bringToFront();
    			  			
    			  			//view.setRotation(45);//ALREADY ROTATED IN GIMP
    			  			//toast.setGravity(Gravity.TOP, whichButton, whichButton);
    			  			toast.setGravity(Gravity.TOP, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER
    			  			
    			  			//toast.setGravity(Gravity.TOP|Gravity.RIGHT, 450, 300);
    			  			//toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);

    			  			TextView text = (TextView) view.findViewById(android.R.id.message);
    			  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
    			  			text.setTypeface(typeFace);
    			  			//text.setTextSize(40);
    			  			text.setTextColor(Color.parseColor("#FFFFFF"));
    			  			
    			  			//text.setRotation(-45);
    			  			//toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
    			  			//text.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
    			  			text.setGravity(Gravity.CENTER);
    			  			
    			  			toast.show();
    			  			
    			    		/*IN CASE ABOVE STOPS WORKING:
    			    		LayoutInflater inflater = getLayoutInflater();

    			    		View layout = inflater.inflate(R.layout.custom_toast,
    			    		(ViewGroup) findViewById(R.id.custom_toast_layout_id));

    			    		// set a message
    			    		TextView text = (TextView) layout.findViewById(R.id.text);
    			    		text.setText("Choose An Avatar");

    			    		// Toast...
    			    		Toast toast = new Toast(getApplicationContext());
    			    		toast.setGravity(Gravity.TOP_VERTICAL, 0, 0);
    			    		toast.setDuration(Toast.LENGTH_SHORT);
    			    		toast.setView(layout);
    			    		toast.show();
    			    		
    			    		
    			    		(in res/layout/custom_toast.xml)
    			    		<?xml version="1.0" encoding="utf-8"?>
    						<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    					    android:id="@+id/custom_toast_layout_id"
    					    android:layout_width="fill_parent"
    					    android:layout_height="fill_parent"
    					    android:background="#FFF"
    					    android:orientation="horizontal"
    					    android:padding="5dp" >

    	    				<TextView
    				        android:id="@+id/text"
    				        android:layout_width="wrap_content"
    				        android:layout_height="fill_parent"
    				        android:textColor="#FFF" />

    						</LinearLayout>
    			  			*/
    			  			
    			    		
    			    		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
    			    	        @Override
    			    	        public void onDismiss(DialogInterface dialog) {
    			    	        	
    			    	        	hideSystemUI();
    			    	        }
    			    	    });
    			    		
    			    		
    			    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
    			    			public void onClick(DialogInterface dialog, int item) { 
    			    								
    			    				if (item == 0) {
    			    					ArrayOfAvatars.avatar[0] = "computer";
    			    					
    			    					stopService(svc);	    				
    				    				
    				    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
    				    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    				    				startActivity(intent);
    				    	        	
    				    	        	dialog.dismiss();
    				    	        	
    				    	        	hideSystemUI();
    			    				}
    			    				else if (item == 1) {
    			    					ArrayOfAvatars.avatar[0] = "crossedswords";
    			    					
    			    					stopService(svc);	    				
    				    				
    				    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
    				    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    				    				startActivity(intent);
    				    	        	
    				    	        	dialog.dismiss();
    				    	        	
    				    	        	hideSystemUI();
    			    				}
    			    				else if (item == 2) {
    			    					ArrayOfAvatars.avatar[0] = "stonedead";
    			    					
    			    					stopService(svc);	    				
    				    				
    				    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
    				    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    				    				startActivity(intent);
    				    	        	
    				    	        	dialog.dismiss();
    				    	        	
    				    	        	hideSystemUI();
    			    				}
    			    				else if (item == 3) {
    			    					ArrayOfAvatars.avatar[0] = "custom";
    			    					
    			    					stopService(svc);
    			    					
    			    					openGallery();
    			    					
    			    					dialog.dismiss();
    			    					
    			    					hideSystemUI();
    			    				}    				
    			    	        	
    			    	        	//finish();
    		        	  		}
    			    		});	    		
    			        	
    			            builder.create().show();
    	        		}
    	        	  }
    	        	});
    	
    	        	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    	        	  public void onClick(DialogInterface dialog, int whichButton) {
    	        	    
    	        		  hideSystemUI();
    	        	  }
    	        	});
    	        	
    	        	alert.show();
    			}
    		});		
    		
    		multiPlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
    			public void onClick(View v) {
    			    /*
    				// Here, thisActivity is the current activity
    				if (ContextCompat.checkSelfPermission(MainActivity1.this,
    						android.Manifest.permission.READ_CONTACTS)
    				        != PackageManager.PERMISSION_GRANTED) {
    	
    				    // Permission is not granted
    				    // Should we show an explanation?
    				    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity1.this,
    				    		android.Manifest.permission.READ_CONTACTS)) {
    				        // Show an explanation to the user *asynchronously* -- don't block
    				        // this thread waiting for the user's response! After the user
    				        // sees the explanation, try again to request the permission.
    				    } else {
    				        // No explanation needed; request the permission
    				        ActivityCompat.requestPermissions(MainActivity1.this,
    				                new String[]{android.Manifest.permission.READ_CONTACTS},
    				                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
    	
    				        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
    				        // app-defined int constant. The callback method gets the
    				        // result of the request.
    				    }
    				} else {
    					*/
    					numberOfPlayers = 2;//will be 1 or 2 to indicate single or multiplayer
    	            	
    		        	buttonSound.start();
    		        	
    		        	multiplayer = "yes";
    		        	
    		        	
    		        	goToHostOrJoin();
    		        	
    		        	/*
    		        	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this, R.style.customalertdialog);
    		
    		        	//alert.setTitle("Multiplayer");
    		        	//alert.setMessage("Enter Name");
    		        	
    		        	Toast toast = Toast.makeText(MainActivity1.this, "Enter name", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    		        	
    		        	
    		        	alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
    		    	        @Override
    		    	        public void onDismiss(DialogInterface dialog) {
    		    	        	
    		    	        	hideSystemUI();
    		    	        }
    		    	    });
    		        	
    		        	
    		        	//alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
    					//	@Override
    					//	public void onCancel(DialogInterface dialog) {		  							
    					//		
    					//		//IF NOTHING DOESN'T WORK, TRY:
    					//		
    					//		stopService(svc);
    					//		
    					//		Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    				    //    	MainActivity1.this.startActivity(i);
    					//	}
    					//});
    		        	
    		
    		        	// Set an EditText view to get user input:
    		        	final EditText input = new EditText(MainActivity1.this);
    		        	input.setSingleLine(true);
    		        	// Limits to 1 line (clicking return is like clicking "ok".)
    		        	alert.setView(input);
    		        	// Limits the number of characters entered to 14.
    		        	InputFilter[] FilterArray = new InputFilter[1];
    		        	FilterArray[0] = new InputFilter.LengthFilter(14);
    		        	input.setFilters(FilterArray);
    		        	
    		        	// THIS WILL GET KEYBOARD AUTOMATICALLY FOR S4:
    		        	
    		        	//input.requestFocus();
    		            //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    		            //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    		            
    		        	
    		        	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    		        	public void onClick(DialogInterface dialog, int whichButton) {        		         		
    		        		
    		        		if (input.getText().toString().isEmpty()) {
    		        			
    		        			Toast toast = Toast.makeText(MainActivity1.this, "Name cannot be blank.", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    		        		     
    		        			hideSystemUI();
    		        		}
    		        		
    		        		else {
    		        			
    			        		// NEED TO SEND TO ARRAY HERE:
    			        		String playername = input.getText().toString();
    			            	//String playercomputer = "Computer".toString();
    			            	
    			            	ArrayOfPlayers.player[5] = playername;
    			            	//ArrayOfPlayers.player[1] = playercomputer;
    			            	
    			            	//savePlayerName();
    			            	
    			            	//insertToDatabase(playername);	        	
    				        	
    				        	// ARRAY ADAPTER WITH ICON STUFF:	        	
    				        	
    				        	final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
    				    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
    				    		
    				    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
    				    		
    				    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
    				    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
    				    		//builder.setIcon(R.drawable.computerhead);
    				    		
    				    		//builder.setTitle("Choose Your Avatar");
    				    		//Toast.makeText(MainActivity1.this,"Choose Your Avatar", Toast.LENGTH_SHORT).show();
    				    		
    				    		
    				    		Toast toast = Toast.makeText(MainActivity1.this, "Choose An Avatar", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    				    		
    				    		
    				    		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
    				    	        @Override
    				    	        public void onDismiss(DialogInterface dialog) {
    				    	        	
    				    	        	hideSystemUI();
    				    	        }
    				    	    });
    				    		
    				    		
    				    		//builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
    		    				//	@Override
    		    				//	public void onCancel(DialogInterface dialog) {		  							
    		    				//		
    		    				//		//IF NOTHING DOESN'T WORK, TRY:
    		    				//		
    		    				//		stopService(svc);
    		    				//		
    		    				//		Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    		    			    //    	MainActivity1.this.startActivity(i);
    		    				//	}
    		    				//});
    				    		
    				    		
    				    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
    				    			public void onClick(DialogInterface dialog, int item) { 
    				    								
    				    				if (item == 0) {
    				    					ArrayOfAvatars.avatar[5] = "computer";
    				    					
    				    					goToHostOrJoin();
    					    	        	
    					    	        	dialog.dismiss();
    					    	        	
    					    	        	hideSystemUI();
    				    				}
    				    				else if (item == 1) {
    				    					ArrayOfAvatars.avatar[5] = "crossedswords";
    				    					
    				    					goToHostOrJoin();
    					    	        	
    					    	        	dialog.dismiss();
    					    	        	
    					    	        	hideSystemUI();
    				    				}
    				    				else if (item == 2) {
    				    					ArrayOfAvatars.avatar[5] = "stonedead";
    				    					
    				    					goToHostOrJoin();
    					    	        	
    					    	        	dialog.dismiss();
    					    	        	
    					    	        	hideSystemUI();
    				    				}
    				    				else if (item == 3) {
    				    					ArrayOfAvatars.avatar[5] = "custom";	    					
    				    					
    				    					openGallery();
    				    					
    				    					dialog.dismiss();
    				    					
    				    					hideSystemUI();
    				    				}    				
    				    	        	
    				    	        	//finish();
    			        	  		}
    				    		});	    		
    				        	
    				            builder.create().show();            
    		        		}
    		        	  }
    		        	});
    		        	
    		        	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    		        	  public void onClick(DialogInterface dialog, int whichButton) {
    		        	    
    		        		  hideSystemUI();
    		        	  }
    		        	});
    		        	
    		        	alert.show();
    					
    				    // Permission has already been granted
    				//}
    		        */
    			}
    		});
    		
    		aboutButton.setOnClickListener(new View.OnClickListener() {
                @Override
    			public void onClick(View v) {
    			                    	
    	        	buttonSound.start();
    	        	
    	        	//Think I need this so user doesn't have to push 'back' more than once (possibly)?
    	        	//finish();
    	        	
    	        	stopService(svc);
    	        	
    	        	Intent i = new Intent(MainActivity1.this, Rules.class);
    	        	MainActivity1.this.startActivity(i);
    	        	
    	        	//Toast.makeText(MainActivity1.this,"About button is working!!", Toast.LENGTH_SHORT).show();
    	        	
    	        	//USE THIS WHEN READY??:
    	        	//Intent openMain2Activity = new Intent("com.example.ktog1.MAIN2ACTIVITY");
    				//startActivity(openMain2Activity);
    			}
    		});
    		
    		recordsButton.setOnClickListener(new View.OnClickListener() {
                @Override
    			public void onClick(View v) {
    			                    	
    	        	buttonSound.start();
    	        	
    	        	stopService(svc);
        			
        			//Intent i = new Intent(MainActivity1.this, playerNamesAndRecords.class);
        			//MainActivity1.this.startActivity(i);
        			
    	        	if (count == 0) {
    	        		
    	        		//Toast.makeText(MainActivity1.this, "No Records", Toast.LENGTH_SHORT).show();
    	    			
    	        		
    	        		Toast toast = Toast.makeText(MainActivity1.this, "No Records", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    	        		
    	        		
    	    			stopService(svc);
    					startService(svc);
    	        	}
    	        	
    	        	else {
    	        		
    	        		stopService(svc);
    	    			
    	    			Intent i = new Intent(MainActivity1.this, playerNamesAndRecords.class);
    	    			MainActivity1.this.startActivity(i);
    	        	}
    	        	
    	        	
    	        	/*
    	        	String filename = "myPrefs";
    	        	File f = new File(getApplicationContext().getApplicationInfo().dataDir + "/shared_prefs/" + filename + ".xml");
    	        	
        			if (f.exists()) {
        				
        				stopService(svc);
    	    			
    	    			Intent i = new Intent(MainActivity1.this, playerNamesAndRecords.class);
    	    			MainActivity1.this.startActivity(i);
        			}
        			    
        			else {
        				
        				Toast.makeText(MainActivity1.this, "No Records", Toast.LENGTH_SHORT).show();
    	    			
    	    			stopService(svc);
    					startService(svc);
        			}
    	        	*/
    	        	
    	        	/*
    	        	SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    	        	String value = pref.getString("unknown_key",null);
    	        	
    	        	if (value == null) {
    	        	    
    	        		Toast.makeText(MainActivity1.this, "No Records", Toast.LENGTH_SHORT).show();
    	    			
    	    			stopService(svc);
    					startService(svc);
    	        		
    	        	} else {

    	        		stopService(svc);
    	    			
    	    			Intent i = new Intent(MainActivity1.this, playerNamesAndRecords.class);
    	    			MainActivity1.this.startActivity(i);
    	        	}
    	        	*/
    	        	
    	        	//stopService(svc);
    	        	
    	        	/*
    	        	File directory = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files");
    	    		File[] files = directory.listFiles();
    	          
    	    		if (files != null) {
    	    			
    	    			stopService(svc);
    	    			
    	    			Intent i = new Intent(MainActivity1.this, playerNamesAndRecords.class);
    	    			MainActivity1.this.startActivity(i);
    	    		}
    	    		
    	    		else {
    	    			
    	    			Toast.makeText(MainActivity1.this, "No Records", Toast.LENGTH_SHORT).show();
    	    			
    	    			stopService(svc);
    					startService(svc);
    	    		}
    	    		*/
    			}
    		});
    		
    		guysButton.setOnClickListener(new View.OnClickListener() {
                @Override
    			public void onClick(View v) {
                	
    	        	buttonSound.start();
    	        	
    	        	
    	        	stopService(svc);
    	        	
    	        	
    	        	//getCount();
    	        	
    	        	//Toast.makeText(MainActivity1.this, String.valueOf(count), Toast.LENGTH_SHORT).show();
    	        	
    	        	
    	        	if (count == 0) {
    	        		
    	        		//Toast.makeText(MainActivity1.this, "No Guys", Toast.LENGTH_SHORT).show();
    			   		
    	        		
    	        		Toast toast = Toast.makeText(MainActivity1.this, "No Guys", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    	        		
    	        		
    					stopService(svc);
    					startService(svc);
    	        	}
    	        	
    	        	else {
    	        	
    	        	//try {
    				      
    	        			getPlayerNamesFromFile();
    	        			
    	        			
    	        			stopService(svc);
    						startService(svc);
    	        			
    	        			
    	        			// Instead of String[] items, Here you can also use ArrayList for your custom object..
    	    	        	
    	    	        	final String[] nameForAdapter = new String[count];
    	    	        	
    	    	        	for (int i = 0; i < count; i++) {
    	    	        		
    	    	        		nameForAdapter[i] = name[i];
    	    	        	}
    	    	        	
    	    		  		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row, R.id.title, nameForAdapter) {
    	    	
    	    		  		    ViewHolder holder;
    	    		  		    Drawable icon;//FOR LAST AVATAR THAT WAS USED??? (LOCATION CAN BE SAVE LIKE OTHER GAME DATA)
    	    	
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
    	    	
    	    		  		        holder.title.setText(nameForAdapter[position]);
    	    		  		        //holder.icon.setImageDrawable(drawable);     
    	    		  		        
    	    	
    	    		  		        return convertView;
    	    		  		    }
    	    		  		};
    	    		  		
    	    		  		
    	    		  		// THIS WAY ALLOWS YOU TO STYLE THE DIALOG (ex. background doesn't dim.):
    	    		  		ContextThemeWrapper cw = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
    	    		  		AlertDialog.Builder builder = new AlertDialog.Builder(cw, R.style.customalertdialog);		  			  		
    	    		  		
    	    	  			
    	    		  		//builder.setTitle("Choose Your Guy");
    	    		  		//Toast.makeText(MainActivity1.this,"Choose Your Guy", Toast.LENGTH_SHORT).show();
    	    		  		
    	    		  		
    	    	  			//builder.setCancelable(false);
    	    		  		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
    	    	    	        @Override
    	    	    	        public void onDismiss(DialogInterface dialog) {
    	    	    	        	
    	    	    	        	hideSystemUI();
    	    	    	        }
    	    	    	    });
    	    		  		
    	    		  		
    	    	  			/*
    	    				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
    	    					@Override
    	    					public void onCancel(DialogInterface dialog) {		  							
    	    						
    	    						//IF NOTHING DOESN'T WORK, TRY:
    	    						
    	    						stopService(svc);
    	    						
    	    						Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    	    			        	MainActivity1.this.startActivity(i);
    	    					}
    	    				});
    	    	  			*/
    	    				
    	    	            builder.setAdapter(adapter,
    	    	                    new DialogInterface.OnClickListener() {
    	    	                        @Override
    	    	                        public void onClick(final DialogInterface dialog, int item) {
    	    	                        	
    	    	                        	for (int i = 0; i < count; i++) {
    	    	                        	
    	    		                        	if (item == i) {
    	    		                        		
    	    		                        		buttonSound.start();
    	    		                        		
    	    		                        		
    	    		                        		//ArrayOfPlayers.player[5] = name[i];
    	    		                        		tempName = name[i];
    	    		                        		
    	    		                        		//Toast.makeText(MainActivity1.this, name[i], Toast.LENGTH_SHORT).show();
    	    		                        		
    	    		                        		
    	    		                        		final String[] items = {"One Player", "Multiplayer"};
    	    		                        		
    	    		                        		// Instead of String[] items, Here you can also use ArrayList for your custom object..
    	    		                        		
    	    		                		  		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row, R.id.title,items) {
    	    		                	
    	    		                		  		    ViewHolder holder;
    	    		                		  		    Drawable icon;//FOR LAST AVATAR THAT WAS USED??? (LOCATION CAN BE SAVE LIKE OTHER GAME DATA)
    	    		                	
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
    	    		                		  		ContextThemeWrapper cw = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
    	    		                		  		AlertDialog.Builder builder = new AlertDialog.Builder(cw, R.style.customalertdialog);		  			  		
    	    		                		  		
    	    		                	  			
    	    		                		  		//builder.setTitle("Choose Your Game");
    	    		                		  		//Toast.makeText(MainActivity1.this,"Choose Your Game", Toast.LENGTH_SHORT).show();
    	    		                		  		
    	    		                		  		
    	    		                	  			//builder.setCancelable(false);
    	    		                		  		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
    	    		        	    	    	        @Override
    	    		        	    	    	        public void onDismiss(DialogInterface dialog) {
    	    		        	    	    	        	
    	    		        	    	    	        	hideSystemUI();
    	    		        	    	    	        }
    	    		        	    	    	    });
    	    		                		  		
    	    		                		  		/*
    	    		                		  		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
    	    		        	    					@Override
    	    		        	    					public void onCancel(DialogInterface dialog) {		  							
    	    		        	    						
    	    		        	    						//IF NOTHING DOESN'T WORK, TRY:
    	    		        	    						
    	    		        	    						stopService(svc);
    	    		        	    						
    	    		        	    						Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    	    		        	    			        	MainActivity1.this.startActivity(i);
    	    		        	    					}
    	    		        	    				});
    	    		                				*/
    	    		                		  		
    	    		                	            builder.setAdapter(adapter,
    	    		                	                    new DialogInterface.OnClickListener() {
    	    		                	                        @Override
    	    		                	                        public void onClick(final DialogInterface dialog, int item) {
    	    		                	                        	
    	    	                		                        	if (item == 0) {
    	    	                		                        		
    	    	                		                        		numberOfPlayers = 1;//will be 1 or 2 to indicate single or multiplayer
    	    	                		                        		
    	    	                		                        		buttonSound.start();
    	    	                		                        		
    	    	                		                        		
    	    	                		                        		ArrayOfPlayers.player[0] = tempName;
    	    	                		                        		ArrayOfPlayers.player[1] = "Computer";
    	    	                		                        		
    	    	                		                        		
    	    	                		                        		final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
    	    	                		            		    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
    	    	                		            		    		
    	    	                		            		    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
    	    	                		            		    		
    	    	                		            		    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
    	    	                		            		    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
    	    	                		            		    		//builder.setIcon(R.drawable.computerhead);
    	    	                		            		    		
    	    	                		            		    		//builder.setTitle("Choose Your Avatar");
    	    	                		            		    		//Toast.makeText(MainActivity1.this,"Choose Your Avatar", Toast.LENGTH_SHORT).show();
    	    	                		            		    		
    	    	                		            		    		
    	    	                		            		    		Toast toast = Toast.makeText(MainActivity1.this, "Choose An Avatar", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    	    	                		            		    		
    	    	                		            		    		
    	    	                		            		    		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
    	    	                		        	    	    	        @Override
    	    	                		        	    	    	        public void onDismiss(DialogInterface dialog) {
    	    	                		        	    	    	        	
    	    	                		        	    	    	        	hideSystemUI();
    	    	                		        	    	    	        }
    	    	                		        	    	    	    });
    	    	                		            		    		
    	    	                		            		    		/*
    	    	                		            		    		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
    	    	                		        	    					@Override
    	    	                		        	    					public void onCancel(DialogInterface dialog) {		  							
    	    	                		        	    						
    	    	                		        	    						//IF NOTHING DOESN'T WORK, TRY:
    	    	                		        	    						
    	    	                		        	    						stopService(svc);
    	    	                		        	    						
    	    	                		        	    						Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    	    	                		        	    			        	MainActivity1.this.startActivity(i);
    	    	                		        	    					}
    	    	                		        	    				});
    	    	                		            		    		*/
    	    	                		            		    		
    	    	                		            		    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
    	    	                		            		    			public void onClick(DialogInterface dialog, int item) { 
    	    	                		            		    								
    	    	                		            		    				if (item == 0) {
    	    	                		            		    					ArrayOfAvatars.avatar[0] = "computer";
    	    	                		            		    					
    	    	                		            		    					stopService(svc);	    				
    	    	                		            			    				
    	    	                		            			    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
    	    	                		            			    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    	    	                		            			    				startActivity(intent);
    	    	                		            			    	        	
    	    	                		            			    	        	dialog.dismiss();
    	    	                		            			    	        	
    	    	                		            			    	        	hideSystemUI();
    	    	                		            		    				}
    	    	                		            		    				else if (item == 1) {
    	    	                		            		    					ArrayOfAvatars.avatar[0] = "crossedswords";
    	    	                		            		    					
    	    	                		            		    					stopService(svc);	    				
    	    	                		            			    				
    	    	                		            			    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
    	    	                		            			    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    	    	                		            			    				startActivity(intent);
    	    	                		            			    	        	
    	    	                		            			    	        	dialog.dismiss();
    	    	                		            			    	        	
    	    	                		            			    	        	hideSystemUI();
    	    	                		            		    				}
    	    	                		            		    				else if (item == 2) {
    	    	                		            		    					ArrayOfAvatars.avatar[0] = "stonedead";
    	    	                		            		    					
    	    	                		            		    					stopService(svc);	    				
    	    	                		            			    				
    	    	                		            			    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
    	    	                		            			    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    	    	                		            			    				startActivity(intent);
    	    	                		            			    	        	
    	    	                		            			    	        	dialog.dismiss();
    	    	                		            			    	        	
    	    	                		            			    	        	hideSystemUI();
    	    	                		            		    				}
    	    	                		            		    				else if (item == 3) {
    	    	                		            		    					ArrayOfAvatars.avatar[0] = "custom";
    	    	                		            		    					
    	    	                		            		    					stopService(svc);
    	    	                		            		    					
    	    	                		            		    					openGallery();
    	    	                		            		    					
    	    	                		            		    					dialog.dismiss();
    	    	                		            		    					
    	    	                		            		    					hideSystemUI();
    	    	                		            		    				}    				
    	    	                		            		    	        	
    	    	                		            		    	        	//finish();
    	    	                		            	        	  		}
    	    	                		            		    		});	    		
    	    	                		            		        	
    	    	                		            		            builder.create().show();
    	    	                		                        	}
    	    	                		                        	
    	    	                		                        	else if (item == 1) {
    	    	                		                        		
    	    	                		                        		numberOfPlayers = 2;//will be 1 or 2 to indicate single or multiplayer
    	    	                		                        		
    	    	                		                        		buttonSound.start();
    	    	                		                        		
    	    	                		                        		multiplayer = "yes";
    	    	                		                        		
    	    	                		                        		
    	    	                		                        		goToHostOrJoin();
    	    	                		                        		
    	    	                		                        		
    	    	                		                        		/*
    	    	                		                        		ArrayOfPlayers.player[5] = tempName;
    	    	                		                        		
    	    	                		                        		
    	    	                		                        		final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
    	    	                		            		    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
    	    	                		            		    		
    	    	                		            		    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
    	    	                		            		    		
    	    	                		            		    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
    	    	                		            		    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
    	    	                		            		    		//builder.setIcon(R.drawable.computerhead);
    	    	                		            		    		
    	    	                		            		    		//builder.setTitle("Choose Your Avatar");
    	    	                		            		    		//Toast.makeText(MainActivity1.this,"Choose Your Avatar", Toast.LENGTH_SHORT).show();
    	    	                		            		    		
    	    	                		            		    		
    	    	                		            		    		Toast toast = Toast.makeText(MainActivity1.this, "Choose An Avatar", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
    	    	                		            		    		
    	    	                		            		    		
    	    	                		            		    		builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
    	    	                		        	    	    	        @Override
    	    	                		        	    	    	        public void onDismiss(DialogInterface dialog) {
    	    	                		        	    	    	        	
    	    	                		        	    	    	        	hideSystemUI();
    	    	                		        	    	    	        }
    	    	                		        	    	    	    });
    	    	                		            		    		
    	    	                		            		    		
    	    	                		            		    		//builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
    	    	                		        	    				//	@Override
    	    	                		        	    				//	public void onCancel(DialogInterface dialog) {		  							
    	    	                		        	    				//		
    	    	                		        	    				//		//IF NOTHING DOESN'T WORK, TRY:
    	    	                		        	    				//		
    	    	                		        	    				//		stopService(svc);
    	    	                		        	    				//		
    	    	                		        	    				//		Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    	    	                		        	    			    //    	MainActivity1.this.startActivity(i);
    	    	                		        	    				//	}
    	    	                		        	    				//});
    	    	                		            		    		
    	    	                		            		    		
    	    	                		            		    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
    	    	                		            		    			public void onClick(DialogInterface dialog, int item) { 
    	    	                		            		    								
    	    	                		            		    				if (item == 0) {
    	    	                		            		    					ArrayOfAvatars.avatar[5] = "computer";
    	    	                		            		    					
    	    	                		            		    					goToHostOrJoin();
    	    	                		            			    	        	
    	    	                		            			    	        	dialog.dismiss();
    	    	                		            			    	        	
    	    	                		            			    	        	hideSystemUI();
    	    	                		            		    				}
    	    	                		            		    				else if (item == 1) {
    	    	                		            		    					ArrayOfAvatars.avatar[5] = "crossedswords";
    	    	                		            		    					
    	    	                		            		    					goToHostOrJoin();
    	    	                		            			    	        	
    	    	                		            			    	        	dialog.dismiss();
    	    	                		            			    	        	
    	    	                		            			    	        	hideSystemUI();
    	    	                		            		    				}
    	    	                		            		    				else if (item == 2) {
    	    	                		            		    					ArrayOfAvatars.avatar[5] = "stonedead";
    	    	                		            		    					
    	    	                		            		    					goToHostOrJoin();
    	    	                		            			    	        	
    	    	                		            			    	        	dialog.dismiss();
    	    	                		            			    	        	
    	    	                		            			    	        	hideSystemUI();
    	    	                		            		    				}
    	    	                		            		    				else if (item == 3) {
    	    	                		            		    					ArrayOfAvatars.avatar[5] = "custom";	    					
    	    	                		            		    					
    	    	                		            		    					openGallery();
    	    	                		            		    					
    	    	                		            		    					dialog.dismiss();
    	    	                		            		    					
    	    	                		            		    					hideSystemUI();
    	    	                		            		    				}    				
    	    	                		            		    	        	
    	    	                		            		    	        	//finish();
    	    	                		            	        	  		}
    	    	                		            		    		});	    		
    	    	                		            		        	
    	    	                		            		            builder.create().show();
    	    	                		            		            */
    	    	                		                        	}
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
    	    		                	            
    	    		                	            
    	    		                	            //h.removeCallbacks(this);
    	    		                	        	
    	    		                	        	//Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    	    		                	        	//MainActivity1.this.startActivity(i);
    	    		                        	}
    	    	                        	}
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
    	    	            
    	    	            
    	    	            //h.removeCallbacks(this);
    	    	        	
    	    	        	//Intent i = new Intent(MainActivity1.this, MainActivity1.class);
    	    	        	//MainActivity1.this.startActivity(i);
    	        			
    				   //} catch (Exception e) {
    				      
    				   		//Toast.makeText(MainActivity1.this, "No Guys", Toast.LENGTH_SHORT).show();
    				   		
    						//stopService(svc);
    						//startService(svc);
    				//}
                	}
    			}
    		});
    		
        	buttonSound2.start();
        }
    }	
}