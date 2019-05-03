package com.nedswebsite.ktog;

import java.net.InetAddress;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Client1 extends Activity {//WAS ActionBarActivity (got "app stopped" message on S4 w/o this)
	
	
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
		
		//NEW
		// For the little space between the action & attack button.
		getWindow().getDecorView().setBackgroundColor(Color.BLACK);		
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		//NEW
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
				
		
				
		// Sound stuff:
		//final MediaPlayer buttonSound = MediaPlayer.create(Client1.this, R.raw.swordswing);
			                    	
    	//buttonSound.start();
		
		Intent svc=new Intent(this, Badonk2SoundService.class);
		//stopService(svc);
		startService(svc);
    	
    	
    	chooseGuy();
	}
	
	
	
	public void chooseGuy() {
		
		final Animation animAlphaText = AnimationUtils.loadAnimation(this, R.anim.anim_alpha_text);
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		
		final MediaPlayer buttonSound = MediaPlayer.create(Client1.this, R.raw.swordswing);
		
		
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
		  		ContextThemeWrapper cw = new ContextThemeWrapper(Client1.this, R.layout.avatar_adapter);
		  		AlertDialog.Builder builder = new AlertDialog.Builder(cw, R.style.customalertdialog);
		  		
		  		//ORIGINAL WAY TO DO IT:
	  			//AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
	  			
	  			builder.setCancelable(false);
	  			
	  			// if back pressed: DOES THIS WORK????????????
				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						
						//GOTO SOME METHOD!!!!!!!!!!!!!!
						
						chooseGuy();
					}
				});
	  			
				
	            builder.setAdapter(adapter,
	                    new DialogInterface.OnClickListener() {
	                        @Override
	                        public void onClick(final DialogInterface dialog, int item) {
	                        	
	                        	if (item == 0) {
	                        		
	                        		buttonSound.start();
	                        		
	                        		//stopService(svc);	    				
				    				
				    				Intent intent = new Intent(Client1.this, Client1NewGuy.class);
				    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				    				startActivity(intent);
				    	        	
				    	        	dialog.dismiss();
				    	        	
				    	        	hideSystemUI();
								}
	                        	
	                        	else if (item == 1) {
	                        		
	                        		buttonSound.start();
	                        		
	                        		//stopService(svc);	    				
				    				
				    				Intent intent = new Intent(Client1.this, Client1OldGuy.class);
				    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				    				startActivity(intent);
				    	        	
				    	        	dialog.dismiss();
				    	        	
				    	        	hideSystemUI();
								}
	                        	
	                        	else if (item == 2) {
	                        		
	                        		buttonSound.start();
	                        		
	                        		//stopService(svc);	    				
				    				
				    				onDestroy();
				    	        	
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
	
	
	
	//===================================================================================================
	// SEPERATOR
	//===================================================================================================
	
	
	
	public void onStart() {
		super.onStart();		
		
		try {// NEED THIS???????????? IN MANIFEST: android:pathPrefix="/"
			Intent intent = getIntent();
			Uri data = intent.getData();//http://www.ktog.multiplayer.com/?ip=
			String ip = data.getQueryParameter("ip");
			
			/*DOESNT RECOGNIZE THE "%"?????
			String[] vars = ip.split("?");
			vars = vars[1].split("%");
			hostIP = vars[0].split("=")[1];
			*/
			
			hostIP = ip;
			
			ArrayOfIP.hostIP[5] = hostIP;
			
		    //Toast.makeText(Client1.this, hostIP, Toast.LENGTH_SHORT).show();
		    
		} catch (Throwable t) {
			
			//Toast.makeText(Client1.this, "HOSTIP CONVERSION DID NOT WORK", Toast.LENGTH_SHORT).show();
			Toast toast = Toast.makeText(Client1.this, "HOSTIP CONVERSION DID NOT WORK", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
	
	@Override
    public void onBackPressed() {
			
			//hideSystemUI();
			
			Intent svc=new Intent(this, Badonk2SoundService.class);
			stopService(svc);
			
            super.onBackPressed();
            this.finish();
    }
	
	// DESTROYS EVERYTHING (EXCEPT SERVICE?)
	@Override
	protected void onDestroy() {
		
		Intent svc=new Intent(this, Badonk2SoundService.class);
		stopService(svc);
		
	    android.os.Process.killProcess(android.os.Process.myPid());
	    
	    super.onDestroy();
	}	
	
	public void onPause() {
		
		super.onPause();
		
		Intent svc=new Intent(this, Badonk2SoundService.class);
		stopService(svc);		
	}
	
	public void onResume() {
		
		hideSystemUI();
		
		super.onResume();
		
		Intent svc=new Intent(this, Badonk2SoundService.class);
		startService(svc);		
	}
	
	
	
	//===================================================================================================
	// SEPERATOR
	//===================================================================================================
	
	
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);        
             
        // For sound for buttons:
     	final MediaPlayer buttonSound1 = MediaPlayer.create(Client1.this, R.raw.swordswing);
        final MediaPlayer buttonSound2 = MediaPlayer.create(Client1.this, R.raw.sworddraw1);
        
        final Intent svc=new Intent(this, Badonk2SoundService.class);
 
        // Checks the orientation of the screen.
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        	
        	setContentView(R.layout.activity_main_activity1);
        	
        	//ImageButton onePlayerButton = (ImageButton) findViewById(R.id.imagebuttononeplayer);
    		//ImageButton multiPlayerButton = (ImageButton) findViewById(R.id.imagebuttonmultiplayer);
    		//ImageButton aboutButton = (ImageButton) findViewById(R.id.imagebuttonabout);
    		
    		   		
    		
                	
        	buttonSound1.start();
        	
        	//Intent openMainActivity2 = new Intent("com.nedswebsite.ktog.MAINACTIVITY2");
        	//startActivity(openMainActivity2);
        	//Toast.makeText(Client1.this,"Multi-player not working yet :(", Toast.LENGTH_SHORT).show();
        } 
        
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
        	
        	setContentView(R.layout.activity_main_activity1);
        	
        	//final ImageButton onePlayerButton = (ImageButton) findViewById(R.id.imagebuttononeplayer);
    		//final ImageButton multiPlayerButton = (ImageButton) findViewById(R.id.imagebuttonmultiplayer);
    		//final ImageButton aboutButton = (ImageButton) findViewById(R.id.imagebuttonabout);   		   		
    		
                	
            buttonSound1.start();
            	
            //Intent openMainActivity2 = new Intent("com.nedswebsite.ktog.MAINACTIVITY2");
            //startActivity(openMainActivity2);
            //Toast.makeText(Client1.this,"Multi-player not working yet :(", Toast.LENGTH_SHORT).show();    		
        }
    }	
	
}