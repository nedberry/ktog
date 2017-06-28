package com.nedswebsite.ktog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.Formatter;
import android.text.style.URLSpan;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Contacts;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

public class MainActivity1 extends Activity {//WAS ActionBarActivity (got "app stopped" message on S4 w/o this)			
	
	public static final int PICK_IMAGE = 100;
	public ImageView customImageView;
	
	String multiplayer = "no";
	
	private static final int CONTACT_PICKER_RESULT = 1001;
	
	InetAddress inetAddress;
	
	String hostIP;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		// USED THE FOLLOWING TO REMOVE TITLE BAR:
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		/*
		// This will hide the system bar until user swipes up from bottom or down from top.		
		getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		*/		
		setContentView(R.layout.activity_main_activity1);
		
		ImageView img = (ImageView)findViewById(R.id.menu1);
		
		final ImageButton onePlayerButton = (ImageButton) findViewById(R.id.imagebuttononeplayer);
		final ImageButton multiPlayerButton = (ImageButton) findViewById(R.id.imagebuttonmultiplayer);
		final ImageButton aboutButton = (ImageButton) findViewById(R.id.imagebuttonabout);
		
		
		// Sound stuff:
		final MediaPlayer buttonSound = MediaPlayer.create(MainActivity1.this, R.raw.swordswing);
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		//stopService(svc);
		startService(svc);
		
		onePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
			                    	
        	buttonSound.start();
        	
        	//stopService(svc);
        	
        	//Toast.makeText(MainActivity1.this,"One player button is working!!", Toast.LENGTH_LONG).show();
        	//USE THIS WHEN READY??:
        	//Intent openNewGuyOldGuy = new Intent("com.nedswebsite.ktog.NewGuyOldGuy");
			//startActivity(openNewGuyOldGuy);
        	
        	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);

        	alert.setTitle("One Player");
        	alert.setMessage("Enter Name");

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
        		  		
        		// NEED TO SEND TO ARRAY HERE:
        		String playername = input.getText().toString();
            	String playercomputer = "Computer".toString();
            	
            	ArrayOfPlayers.player[0] = playername;
            	ArrayOfPlayers.player[1] = playercomputer;
            	
            	insertToDatabase(playername);	        	
	        	
	        	// ARRAY ADAPTER WITH ICON STUFF:	        	
	        	
	        	final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
	    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
	    		
	    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
	    		
	    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
	    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);
	    		//builder.setIcon(R.drawable.computerhead);
	    		builder.setTitle("Choose Your Avatar");
	    		
	    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
	    			public void onClick(DialogInterface dialog, int item) { 
	    								
	    				if (item == 0) {
	    					ArrayOfAvatars.avatar[0] = "computer";
	    					
	    					stopService(svc);	    				
		    				
		    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
		    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    				startActivity(intent);
		    	        	
		    	        	dialog.dismiss();
	    				}
	    				else if (item == 1) {
	    					ArrayOfAvatars.avatar[0] = "crossedswords";
	    					
	    					stopService(svc);	    				
		    				
		    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
		    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    				startActivity(intent);
		    	        	
		    	        	dialog.dismiss();
	    				}
	    				else if (item == 2) {
	    					ArrayOfAvatars.avatar[0] = "stonedead";
	    					
	    					stopService(svc);	    				
		    				
		    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
		    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    				startActivity(intent);
		    	        	
		    	        	dialog.dismiss();
	    				}
	    				else if (item == 3) {
	    					ArrayOfAvatars.avatar[0] = "custom";
	    					
	    					stopService(svc);
	    					
	    					openGallery();
	    					
	    					dialog.dismiss();
	    				}    				
	    	        	
	    	        	//finish();
        	  		}
	    		});	    		
	        	
	            builder.create().show();            
	        	
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
		
		multiPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
			                    	
        	buttonSound.start();
        	
        	multiplayer = "yes";
        	
        	
        	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);

        	alert.setTitle("Multiplayer");
        	alert.setMessage("Enter Name");

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
        		  		
        		// NEED TO SEND TO ARRAY HERE:
        		String playername = input.getText().toString();
            	String playercomputer = "Computer".toString();
            	
            	ArrayOfPlayers.player[0] = playername;
            	ArrayOfPlayers.player[1] = playercomputer;
            	
            	insertToDatabase(playername);	        	
	        	
	        	// ARRAY ADAPTER WITH ICON STUFF:	        	
	        	
	        	final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
	    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
	    		
	    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
	    		
	    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
	    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);
	    		//builder.setIcon(R.drawable.computerhead);
	    		builder.setTitle("Choose Your Avatar");
	    		
	    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
	    			public void onClick(DialogInterface dialog, int item) { 
	    								
	    				if (item == 0) {
	    					ArrayOfAvatars.avatar[0] = "computer";
	    					
	    					goToHostOrJoin();
		    	        	
		    	        	dialog.dismiss();
	    				}
	    				else if (item == 1) {
	    					ArrayOfAvatars.avatar[0] = "crossedswords";
	    					
	    					goToHostOrJoin();
		    	        	
		    	        	dialog.dismiss();
	    				}
	    				else if (item == 2) {
	    					ArrayOfAvatars.avatar[0] = "stonedead";
	    					
	    					goToHostOrJoin();
		    	        	
		    	        	dialog.dismiss();
	    				}
	    				else if (item == 3) {
	    					ArrayOfAvatars.avatar[0] = "custom";	    					
	    					
	    					openGallery();
	    					
	    					dialog.dismiss();
	    				}    				
	    	        	
	    	        	//finish();
        	  		}
	    		});	    		
	        	
	            builder.create().show();            
	        	
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
		
		aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
			                    	
        	buttonSound.start();
        	
        	//Think I need this so user doesn't have to push 'back' more than once (possibly)?
        	//finish();
        	
        	stopService(svc);
        	
        	Intent i = new Intent(MainActivity1.this, Rules.class);
        	MainActivity1.this.startActivity(i);
        	
        	//Toast.makeText(MainActivity1.this,"About button is working!!", Toast.LENGTH_LONG).show();
        	
        	//USE THIS WHEN READY??:
        	//Intent openMain2Activity = new Intent("com.example.ktog1.MAIN2ACTIVITY");
			//startActivity(openMain2Activity);        				
			}
		});
	}
	
	
	public void openGallery() {
		
		Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		startActivityForResult(gallery, PICK_IMAGE);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode){	
		
		case PICK_IMAGE://FOR IMAGE GALLERY
			if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && multiplayer.equals("no")) {
				Uri imageUri = data.getData();
				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
			    intent.putExtra("imageUri", imageUri.toString());
				//intent.putExtra("imageUri", imageUri);
			    startActivity(intent);						    			       	
			}			
			else if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && multiplayer.equals("yes")) {
				Uri imageUri = data.getData();
				
				
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
		    				
		    				Intent intent = new Intent(MainActivity1.this, Client1.class);
		    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    				startActivity(intent);
		    	        	
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
			}
		break;
			
		case CONTACT_PICKER_RESULT:// FOR CONTACT PICKER
			
			if (resultCode == RESULT_OK) {
	            switch (requestCode) {
	            case CONTACT_PICKER_RESULT:
	                // handle contact results
	            	
	            	Cursor cursor = null;
	            	String email = "";
	            	
	            	Uri result = data.getData();
	            	
	            	String id = result.getLastPathSegment();
	            	
	            	
	            	// query for everything phone
	            	cursor = getContentResolver().query(
	            	        Email.CONTENT_URI, null,
	            	        Email.CONTACT_ID + "=?",
	            	        new String[]{id}, null);
	            	
	            	if (cursor.moveToFirst()) {
	            	    int emailIdx = cursor.getColumnIndex(Email.DATA);
	            	    email = cursor.getString(emailIdx);
	            	    //Log.v(DEBUG_TAG, "Got email: " + email);
	            	    
	            	    
	            	    
	            	    
	            	    //E-MAIL METHODS:	            	    
	            	    
	            	    /*
	            	    // URL METHOD:
	            	    //URL sourceUrl = new URL("http://10.0.2.2:2291/acd.asmx/Get_Teams?_userid=" + _userid + "&_sporttype=" + _sporttype);
            	        //URL sourceUrl = new URL("http://www.ktog.multiplayer.com/?userid=" + hostIP);
	            	    String url = "http://www.ktog.multiplayer.com/?verification=" + hostIP + "&username=larry";

	            	    SpannableStringBuilder builder = new SpannableStringBuilder();
	            	    //builder.append("hi friends please visit my website for");
	            	    int start = builder.length();
	            	    builder.append(url);
	            	    int end = builder.length();

	            	    builder.setSpan(new URLSpan(url), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	            	    
	            	    Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
	            	            "mailto",email, null));
	            	    //Intent i = new Intent(Intent.ACTION_SEND);  
	            	    //i.setType("message/rfc822") ;
	            	    //i.putExtra(Intent.EXTRA_EMAIL, new String[]{""});  
	            	    i.putExtra(Intent.EXTRA_SUBJECT,"KtOG Invitation");  
	            	    i.putExtra(Intent.EXTRA_TEXT, builder);  
	            	    //startActivity(Intent.createChooser(i, "Select application"));     	    
	            	    startActivity(i);
	            	    */
	            	    
	            	    
	            	    //OTHER METHOD:
	            	    
            	        Intent gmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
	            	            "mailto",email, null));
            	        //gmailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
            	        gmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "KtOG Invitation");
            	        //gmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Please click link to play:");
            	        //gmailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<a href=\"" + "http://www.ktog.multiplayer.com/launch?hostip=" + hostIP + "\">Link</a>"));
            	        //gmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<a href=\"" + "http://www.ktog.multiplayer.com/launch?hostip=" + hostIP + "\">Link</a>"));         	                 	        
            	        //gmailIntent.putExtra(android.content.Intent.EXTRA_HTML_TEXT, Html.fromHtml("<a href=\"" + "http://www.ktog.multiplayer.com/launch?hostip=" + hostIP + "\">Link</a>"));
            	                    	                   	        
            	        gmailIntent.putExtra(
            	        		Intent.EXTRA_TEXT,
            	        		Html.fromHtml(new StringBuilder()
            	        		    //.append("<p><b>http://www.ktog.multiplayer.com/launch?hostip=</b></p>")
            	        			
            	        			//METHOD 1:
            	        			//.append("<a>http://www.ktog.multiplayer.com/launch?hostip=</a>")
            	        		
            	        			//METHOD 2:
            	        			//.append("<a>http://www.ktog.multiplayer.com/?verification=" + hostIP + "&username=larry</a>")
            	        			.append("<a>http://www.ktog.multiplayer.com/?ip=" + hostIP + "</a>")
            	        			
            	        			//.append(hostIP)
            	        		    .toString())            	        		    
            	        		);
            	        
            	        startActivity(gmailIntent);        	    
	            	    
            	        //Toast.makeText(MainActivity1.this, hostIP, Toast.LENGTH_LONG).show();
            	        
	            	}	            	
	            //break;
	            	
	            	cursor.close();
	            	
	            	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);
		  			
					alert.setCancelable(false);
					
		  	    	alert.setTitle("Do you want to send another invite?");
		  	    	/*
		  	    	alert.setMessage("something");
		  	    	*/	  	    	
		  	    	
		  	    	alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		  		    	public void onClick(DialogInterface dialog, int whichButton) {	  		    		
		  		    		
		  		    		doLaunchContactPicker(customImageView);													  		    		
		  		    	}
		  	    	});
		  	    	
		  	    	alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
		          	  public void onClick(DialogInterface dialog, int whichButton) {
		          		  
		          		  	//hideNavigation();
		          		  	
		          		  	
		    				Intent intent = new Intent(MainActivity1.this, Host.class);
		    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    				startActivity(intent);
		    	        								          		  	
		          		  	
		          		  	dialog.dismiss();
		          	  }
		          	});	  	    	
		  	    	
		  	    	alert.show();
    	        	
	            
	            }

	        } else {
	            // gracefully handle failure
	            //Log.w(DEBUG_TAG, "Warning: activity result not ok");
	        }
		// NEED THIS??:
		break;
		
		}
	}
	
	public void goToHostOrJoin() {
		
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
					
					getLocalIpAddress();
					
					// TURNED OFF FOR DISPLAYING PRIVATE IP PURPOSES:
					doLaunchContactPicker(customImageView);
					
					
					/*
    				Intent intent = new Intent(MainActivity1.this, Host.class);
    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    				startActivity(intent);
    	        	*/
    				
    	        	dialog.dismiss();
				}
				else if (item == 1) {    					
					
					stopService(svc);	    				
    				
    				Intent intent = new Intent(MainActivity1.this, Client1.class);
    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    				startActivity(intent);
    	        	
    	        	dialog.dismiss();
				}
				else if (item == 2) {		
    	        	
    	        	dialog.dismiss();
				}    				    				
	        	
	        	//finish();
	  		}
		});	    		
    	
        builder.create().show();		
	}
	
		
	public void doLaunchContactPicker(View view) {
	    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
	            Contacts.CONTENT_URI);
	    startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
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
		                	//Toast.makeText(MainActivity1.this, hostIP, Toast.LENGTH_LONG).show();
		                	
		                	
		                	
		                	hostIP = hostIPFull.split("%")[0];
		                	Toast.makeText(MainActivity1.this, hostIP, Toast.LENGTH_LONG).show();
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

		if (WIFI == true)

		{
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
			
			final Intent svc=new Intent(this, Badonk2SoundService.class);
			stopService(svc);
			
            super.onBackPressed();
            this.finish();
    }
	
	// DESTROYS EVERYTHING (EXCEPT SERVICE?)
	@Override
	protected void onDestroy() {
		
	    android.os.Process.killProcess(android.os.Process.myPid());
	    
	    super.onDestroy();
	}	
	
	public void onPause() {
		
		super.onPause();
		
		Intent svc=new Intent(this, Badonk2SoundService.class);
		stopService(svc);		
	}
	
	public void onResume() {
		
		super.onResume();
		
		Intent svc=new Intent(this, Badonk2SoundService.class);
		startService(svc);		
	}	
	
	
	
	
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
        /*
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
            textViewResult.setText("Inserted");
        }*/
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(player);		
    }
	
	
	
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);        
             
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
    		
    		onePlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
    			public void onClick(View v) {    			                    	
            	buttonSound1.start();
            	//stopService(svc);
            	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);

            	alert.setTitle("One Player");
            	alert.setMessage("Enter Name");

            	// Set an EditText view to get user input 
            	final EditText input = new EditText(MainActivity1.this);
            	input.setSingleLine(true);
            	// Limits to 1 line (clicking return is like clicking "ok".)
            	alert.setView(input);
            	
            	// THIS WILL GET KEYBOARD AUTOMATICALLY FOR S4:
            	/*
            	input.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                */

            	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int whichButton) {            		         		
            		  		
            		// NEED TO SEND TO ARRAY HERE:
            		String playername = input.getText().toString();
                	String playercomputer = "Computer".toString();
                	
                	ArrayOfPlayers.player[0] = playername;
                	ArrayOfPlayers.player[1] = playercomputer;
                	
                	insertToDatabase(playername);    	        	
    	        	
    	        	// ARRAY ADAPTER WITH ICON STUFF:    	        	
    	        	
                	final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
    	    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
    	    		
    	    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
    	    		
    	    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
    	    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);
    	    		//builder.setIcon(R.drawable.computerhead);
    	    		builder.setTitle("Choose Your Avatar");
    	    		
    	    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
    	    			public void onClick(DialogInterface dialog, int item) { 
    	    								
    	    				if (item == 0) {
    	    					ArrayOfAvatars.avatar[0] = "computer";
    	    					
    	    					stopService(svc);
        	    				
        	    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
        	    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        	    				startActivity(intent);
        	                	
        	                	dialog.dismiss();
    	    				}
    	    				else if (item == 1) {
    	    					ArrayOfAvatars.avatar[0] = "crossedswords";
    	    					
    	    					stopService(svc);
        	    				
        	    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
        	    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        	    				startActivity(intent);
        	                	
        	                	dialog.dismiss();
    	    				}
    	    				else if (item == 2) {
    	    					ArrayOfAvatars.avatar[0] = "stonedead";
    	    					
    	    					stopService(svc);
        	    				
        	    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
        	    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        	    				startActivity(intent);
        	                	
        	                	dialog.dismiss();
    	    				}
    	    				else if (item == 3) {
    	    					ArrayOfAvatars.avatar[0] = "custom";
    	    					
    	    					stopService(svc);
    	    					
    	    					openGallery();
    	    					
    	    					dialog.dismiss();
    	    				}   	    				
    	                	
    	                	//finish();
            	  		}
    	    		});	    		
    	        	
    	            builder.create().show();    	            
    	        	
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
    		
    		multiPlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
    			public void onClick(View v) {
                	
            	buttonSound1.start();
            	
            	//Intent openMainActivity2 = new Intent("com.nedswebsite.ktog.MAINACTIVITY2");
            	//startActivity(openMainActivity2);
            	Toast.makeText(MainActivity1.this,"Multi-player not working yet :(", Toast.LENGTH_LONG).show();
            	            				
    			}
    		});
    		
    		aboutButton.setOnClickListener(new View.OnClickListener() {
                @Override
    			public void onClick(View v) {    			                    	
            	buttonSound1.start();            	
            	//Think I need this so user doesn't have to push 'back' more than once (possibly)?
            	//finish();
            	
            	stopService(svc);
            	
            	Intent i = new Intent(MainActivity1.this, Rules.class);
            	MainActivity1.this.startActivity(i);            	
            	//USE THIS WHEN READY??:
            	//Intent openMain2Activity = new Intent("com.example.ktog1.MAIN2ACTIVITY");
    			//startActivity(openMain2Activity);            				
    			}
    		});	
        	buttonSound1.start();        	
        } 
        
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
        	
        	setContentView(R.layout.activity_main_activity1);
        	
        	final ImageButton onePlayerButton = (ImageButton) findViewById(R.id.imagebuttononeplayer);
    		final ImageButton multiPlayerButton = (ImageButton) findViewById(R.id.imagebuttonmultiplayer);
    		final ImageButton aboutButton = (ImageButton) findViewById(R.id.imagebuttonabout);

    		onePlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
    			public void onClick(View v) {    			                    	
            	buttonSound1.start();
            	//stopService(svc);
            	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);

            	alert.setTitle("One Player");
            	alert.setMessage("Enter Name");

            	// Set an EditText view to get user input 
            	final EditText input = new EditText(MainActivity1.this);
            	input.setSingleLine(true);
            	// Limits to 1 line (clicking return is like clicking "ok".)
            	alert.setView(input);
            	
            	// THIS WILL GET KEYBOARD AUTOMATICALLY FOR S4:
            	/*
            	input.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                */

            	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            	public void onClick(DialogInterface dialog, int whichButton) {            		         		
            		  		
            		// NEED TO SEND TO ARRAY HERE:
            		String playername = input.getText().toString();
                	String playercomputer = "Computer".toString();
                	
                	ArrayOfPlayers.player[0] = playername;
                	ArrayOfPlayers.player[1] = playercomputer;
                	
                	insertToDatabase(playername);    	        	
    	        	
    	        	// ARRAY ADAPTER WITH ICON STUFF:    	        	
    	        	
                	final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
    	    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
    	    		
    	    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
    	    		
    	    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
    	    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);
    	    		//builder.setIcon(R.drawable.computerhead);
    	    		builder.setTitle("Choose Your Avatar");
    	    		
    	    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
    	    			public void onClick(DialogInterface dialog, int item) { 
    	    								
    	    				if (item == 0) {
    	    					ArrayOfAvatars.avatar[0] = "computer";
    	    					
    	    					stopService(svc);
        	    				
        	    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
        	    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        	    				startActivity(intent);
        	                	
        	                	dialog.dismiss();
    	    				}
    	    				else if (item == 1) {
    	    					ArrayOfAvatars.avatar[0] = "crossedswords";
    	    					
    	    					stopService(svc);
        	    				
        	    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
        	    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        	    				startActivity(intent);
        	                	
        	                	dialog.dismiss();
    	    				}
    	    				else if (item == 2) {
    	    					ArrayOfAvatars.avatar[0] = "stonedead";
    	    					
    	    					stopService(svc);
        	    				
        	    				Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
        	    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        	    				startActivity(intent);
        	                	
        	                	dialog.dismiss();
    	    				}
    	    				else if (item == 3) {
    	    					ArrayOfAvatars.avatar[0] = "custom";
    	    					
    	    					stopService(svc);
    	    					
    	    					openGallery();
    	    					
    	    					dialog.dismiss();
    	    				}   	    				
    	                	
    	                	//finish();
            	  		}
    	    		});	    		
    	        	
    	            builder.create().show();   	            
    	        	
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
    		
    		multiPlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	
                buttonSound1.start();
                	
                //Intent openMainActivity2 = new Intent("com.nedswebsite.ktog.MAINACTIVITY2");
                //startActivity(openMainActivity2);
                Toast.makeText(MainActivity1.this,"Multi-player not working yet :(", Toast.LENGTH_LONG).show();
                	             				
    			}
    		});
    		
    		aboutButton.setOnClickListener(new View.OnClickListener() {
                @Override
    			public void onClick(View v) {    			                    	
            	buttonSound1.start();            	
            	//Think I need this so user doesn't have to push 'back' more than once (possibly)?
            	//finish();
            	
            	stopService(svc);
            	
            	Intent i = new Intent(MainActivity1.this, Rules.class);
            	MainActivity1.this.startActivity(i);            	
            	//USE THIS WHEN READY??:
            	//Intent openMain2Activity = new Intent("com.example.ktog1.MAIN2ACTIVITY");
    			//startActivity(openMain2Activity);            				
    			}
    		});    		
        	buttonSound2.start();
        }
    }	
}