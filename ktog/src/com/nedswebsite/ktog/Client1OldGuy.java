package com.nedswebsite.ktog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
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


import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Contacts;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Client1OldGuy extends Activity {//WAS ActionBarActivity (got "app stopped" message on S4 w/o this)			
	
	public static final int PICK_IMAGE = 100;
	public ImageView customImageView;
	
	String multiplayer = "yes";	
	
	InetAddress inetAddress;
	
	String hostIP;
	
	
	String tempName;
	
	int count = 0;
	
	public String[] name = new String[100];
	
	
	
	
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
		//final MediaPlayer buttonSound = MediaPlayer.create(Client1OldGuy.this, R.raw.swordswing);
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		//stopService(svc);
		startService(svc);		
            
			                    	
    	//buttonSound.start();
    	
    	//multiplayer = "yes";
    	
    	getCount();
    	
    	getName();
	}
	
	
	
	
	
	public void getCount() {
		
		SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
		count = pref.getAll().size();
	}
	
	
	
	
	
	public void getName() {
		
		
	  			
		  		final MediaPlayer buttonSound = MediaPlayer.create(Client1OldGuy.this, R.raw.swordswing);
        		
        		final Intent svc=new Intent(Client1OldGuy.this, Badonk2SoundService.class);
		                        	
		                        	
        		if (count == 0) {
	        		
	        		//Toast.makeText(Client1OldGuy.this, "No Guys", Toast.LENGTH_SHORT).show();
			   		
	        		
	        		Toast toast = Toast.makeText(Client1OldGuy.this, "No Guys", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
	        		
	        		
					//stopService(svc);
					//startService(svc);
	        	}
	        	
	        	else {
	        	
	        	//try {
				      
	        			getPlayerNamesFromFile();
	        			
	        			
	        			//stopService(svc);
						//startService(svc);
	        			
	        			
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
	    		  		ContextThemeWrapper cw = new ContextThemeWrapper(Client1OldGuy.this, R.layout.avatar_adapter);
	    		  		AlertDialog.Builder builder = new AlertDialog.Builder(cw, R.style.customalertdialog);		  			  		
	    		  		
	    	  			
	    		  		//builder.setTitle("Choose Your Guy");
	    		  		//Toast.makeText(Client1OldGuy.this,"Choose Your Guy", Toast.LENGTH_SHORT).show();
	    		  		
	    		  		
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
	    						
	    						Intent i = new Intent(Client1OldGuy.this, MainActivity1.class);
	    			        	Client1OldGuy.this.startActivity(i);
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
	    		                        		
	    		                        		//Toast.makeText(Client1OldGuy.this, name[i], Toast.LENGTH_SHORT).show();
	    		                        		
	    		                        		
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
	    		                		  		ContextThemeWrapper cw = new ContextThemeWrapper(Client1OldGuy.this, R.layout.avatar_adapter);
	    		                		  		AlertDialog.Builder builder = new AlertDialog.Builder(cw, R.style.customalertdialog);		  			  		
	    		                		  		
	    		                	  			
	    		                		  		//builder.setTitle("Choose Your Game");
	    		                		  		//Toast.makeText(Client1OldGuy.this,"Choose Your Game", Toast.LENGTH_SHORT).show();
	    		                		  		
	    		                		  		
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
	    		        	    						
	    		        	    						Intent i = new Intent(Client1OldGuy.this, MainActivity1.class);
	    		        	    			        	Client1OldGuy.this.startActivity(i);
	    		        	    					}
	    		        	    				});
	    		                				*/
	    		                		  		
	    		                	            builder.setAdapter(adapter,
	    		                	                    new DialogInterface.OnClickListener() {
	    		                	                        @Override
	    		                	                        public void onClick(final DialogInterface dialog, int item) {
	    		                	                        	
	    	                		                        	if (item == 0) {
	    	                		                        		
	    	                		                        		buttonSound.start();
	    	                		                        		
	    	                		                        		
	    	                		                        		ArrayOfPlayers.player[0] = tempName;
	    	                		                        		ArrayOfPlayers.player[1] = "Computer";
	    	                		                        		
	    	                		                        		
	    	                		                        		final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
	    	                		            		    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
	    	                		            		    		
	    	                		            		    		ListAdapter adapter = new ArrayAdapterWithIcon(Client1OldGuy.this, items, avatars);
	    	                		            		    		
	    	                		            		    		ContextThemeWrapper wrapper = new ContextThemeWrapper(Client1OldGuy.this, R.layout.avatar_adapter);
	    	                		            		    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
	    	                		            		    		//builder.setIcon(R.drawable.computerhead);
	    	                		            		    		
	    	                		            		    		//builder.setTitle("Choose Your Avatar");
	    	                		            		    		//Toast.makeText(Client1OldGuy.this,"Choose Your Avatar", Toast.LENGTH_SHORT).show();
	    	                		            		    		
	    	                		            		    		
	    	                		            		    		Toast toast = Toast.makeText(Client1OldGuy.this, "Choose An Avatar", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
	    	                		        	    						
	    	                		        	    						Intent i = new Intent(Client1OldGuy.this, MainActivity1.class);
	    	                		        	    			        	Client1OldGuy.this.startActivity(i);
	    	                		        	    					}
	    	                		        	    				});
	    	                		            		    		*/
	    	                		            		    		
	    	                		            		    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
	    	                		            		    			public void onClick(DialogInterface dialog, int item) { 
	    	                		            		    								
	    	                		            		    				if (item == 0) {
	    	                		            		    					ArrayOfAvatars.avatar[0] = "computer";
	    	                		            		    					
	    	                		            		    					stopService(svc);	    				
	    	                		            			    				
	    	                		            			    				Intent intent = new Intent(Client1OldGuy.this, MainActivity2.class);
	    	                		            			    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	    	                		            			    				startActivity(intent);
	    	                		            			    	        	
	    	                		            			    	        	dialog.dismiss();
	    	                		            			    	        	
	    	                		            			    	        	hideSystemUI();
	    	                		            		    				}
	    	                		            		    				else if (item == 1) {
	    	                		            		    					ArrayOfAvatars.avatar[0] = "crossedswords";
	    	                		            		    					
	    	                		            		    					stopService(svc);	    				
	    	                		            			    				
	    	                		            			    				Intent intent = new Intent(Client1OldGuy.this, MainActivity2.class);
	    	                		            			    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	    	                		            			    				startActivity(intent);
	    	                		            			    	        	
	    	                		            			    	        	dialog.dismiss();
	    	                		            			    	        	
	    	                		            			    	        	hideSystemUI();
	    	                		            		    				}
	    	                		            		    				else if (item == 2) {
	    	                		            		    					ArrayOfAvatars.avatar[0] = "stonedead";
	    	                		            		    					
	    	                		            		    					stopService(svc);	    				
	    	                		            			    				
	    	                		            			    				Intent intent = new Intent(Client1OldGuy.this, MainActivity2.class);
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
	    	                		                        		
	    	                		                        		buttonSound.start();
	    	                		                        		
	    	                		                        		multiplayer = "yes";
	    	                		                        		
	    	                		                        		ArrayOfPlayers.player[0] = tempName;
	    	                		                        		
	    	                		                        		
	    	                		                        		final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead", "Custom"};
	    	                		            		    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
	    	                		            		    		
	    	                		            		    		ListAdapter adapter = new ArrayAdapterWithIcon(Client1OldGuy.this, items, avatars);
	    	                		            		    		
	    	                		            		    		ContextThemeWrapper wrapper = new ContextThemeWrapper(Client1OldGuy.this, R.layout.avatar_adapter);
	    	                		            		    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
	    	                		            		    		//builder.setIcon(R.drawable.computerhead);
	    	                		            		    		
	    	                		            		    		//builder.setTitle("Choose Your Avatar");
	    	                		            		    		//Toast.makeText(Client1OldGuy.this,"Choose Your Avatar", Toast.LENGTH_SHORT).show();
	    	                		            		    		
	    	                		            		    		
	    	                		            		    		Toast toast = Toast.makeText(Client1OldGuy.this, "Choose An Avatar", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
	    	                		        	    						
	    	                		        	    						Intent i = new Intent(Client1OldGuy.this, MainActivity1.class);
	    	                		        	    			        	Client1OldGuy.this.startActivity(i);
	    	                		        	    					}
	    	                		        	    				});
	    	                		            		    		*/
	    	                		            		    		
	    	                		            		    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
	    	                		            		    			public void onClick(DialogInterface dialog, int item) { 
	    	                		            		    								
	    	                		            		    				if (item == 0) {
	    	                		            		    					
	    	                		            		    					buttonSound.start();
	    	                		            		    					
	    	                		            		    					ArrayOfAvatars.avatar[0] = "computer";
	    	                		            		    					
	    	                		            		    					goToJoin();
	    	                		            			    	        	
	    	                		            			    	        	dialog.dismiss();
	    	                		            			    	        	
	    	                		            			    	        	hideSystemUI();
	    	                		            		    				}
	    	                		            		    				else if (item == 1) {
	    	                		            		    					
	    	                		            		    					buttonSound.start();
	    	                		            		    					
	    	                		            		    					ArrayOfAvatars.avatar[0] = "crossedswords";
	    	                		            		    					
	    	                		            		    					goToJoin();
	    	                		            			    	        	
	    	                		            			    	        	dialog.dismiss();
	    	                		            			    	        	
	    	                		            			    	        	hideSystemUI();
	    	                		            		    				}
	    	                		            		    				else if (item == 2) {
	    	                		            		    					
	    	                		            		    					buttonSound.start();
	    	                		            		    					
	    	                		            		    					ArrayOfAvatars.avatar[0] = "stonedead";
	    	                		            		    					
	    	                		            		    					goToJoin();
	    	                		            			    	        	
	    	                		            			    	        	dialog.dismiss();
	    	                		            			    	        	
	    	                		            			    	        	hideSystemUI();
	    	                		            		    				}
	    	                		            		    				else if (item == 3) {
	    	                		            		    					
	    	                		            		    					buttonSound.start();
	    	                		            		    					
	    	                		            		    					ArrayOfAvatars.avatar[0] = "custom";	    					
	    	                		            		    					
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
	    		                	        	
	    		                	        	//Intent i = new Intent(Client1OldGuy.this, MainActivity1.class);
	    		                	        	//Client1OldGuy.this.startActivity(i);
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
	    	        	
	    	        	//Intent i = new Intent(Client1OldGuy.this, MainActivity1.class);
	    	        	//Client1OldGuy.this.startActivity(i);
	        			
				   //} catch (Exception e) {
				      
				   		//Toast.makeText(Client1OldGuy.this, "No Guys", Toast.LENGTH_SHORT).show();
				   		
						//stopService(svc);
						//startService(svc);
				//}
            	}
  	    	
  	  	    
    		
		 
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
			
			
			i++;
		}
	}
	
	
	
	public void openGallery() {
		
		Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		startActivityForResult(gallery, PICK_IMAGE);
	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//Toast.makeText(Client1.this,"WTF!?", Toast.LENGTH_SHORT).show();
		switch(requestCode){	
		
		case PICK_IMAGE://FOR IMAGE GALLERY
			
			//Toast.makeText(Client1.this,"WTF!?", Toast.LENGTH_SHORT).show();
			
			if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && multiplayer.equals("yes")) {
				
				final Uri imageUri = data.getData();
				Uri imageUri2 = imageUri;
				
				/*
				File myFile = new File(imageUri.getPath());
				myFile.getAbsolutePath();
				
				int file_size = Integer.parseInt(String.valueOf(myFile.length()/1024));//from bytes to kilobytes
				*/
				
				//imageUri = data.getData();
				
				/*
				Intent intentFileSize = new Intent(Client1OldGuy.this, MainActivity2.class);
				intentFileSize.putExtra("imageUri", imageUri.toString());
				String image_path= intentFileSize.getStringExtra("imageUri"); 
				Uri fileUri = Uri.parse(image_path);
				*/
				Bitmap bitmap = null;
				try {
					bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri2);
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
		        
		        //Toast.makeText(Client1.this,"IMAGE SIZE = " + file_size, Toast.LENGTH_SHORT).show();
		        Toast toast = Toast.makeText(Client1OldGuy.this, "IMAGE SIZE = " + file_size, Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
					
					final Intent svc=new Intent(this, Badonk2SoundService.class);
					
					final String[] items = new String[] {"Join", "Cancel"};
					final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.computer};
					
					ListAdapter adapter = new ArrayAdapterWithIcon(Client1OldGuy.this, items, avatars);
					
					ContextThemeWrapper wrapper = new ContextThemeWrapper(Client1OldGuy.this, R.layout.avatar_adapter);
					AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
					//builder.setIcon(R.drawable.computerhead);
					//builder.setTitle("Multiplayer");
					
					builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
						public void onClick(DialogInterface dialog, int item) { 
											
							if (item == 0) {    					
								
								stopService(svc);
								
			    				/*
			    				Intent intent = new Intent(Client1.this, Client2.class);
			    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			    				
			    				//intent.putExtra("hostIP", hostIP);		    				
			    				startActivity(intent);		    				
			    	        	*/
								Intent intent = new Intent(Client1OldGuy.this, Client2.class);
							    intent.putExtra("imageUri", imageUri.toString());
								//intent.putExtra("imageUri", imageUri);
							    startActivity(intent);
								
			    	        	
			    	        	dialog.dismiss();
			    	        	
			    	        	hideSystemUI();
			    	        	
			    	        	//finish();
							}
							else if (item == 1) {		
			    	        	
			    	        	dialog.dismiss();
			    	        	
			    	        	hideSystemUI();
			    	        	
			    	        	finish();
							}    				    				
				        	
				        	//finish();
				  		}
					});	    		
			    	
			        builder.create().show();
			        
					/*
					Intent intent = new Intent(Client1.this, Client2.class);
				    intent.putExtra("imageUri", imageUri.toString());
					//intent.putExtra("imageUri", imageUri);
				    startActivity(intent);
				    */
				    
				    //finish();
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
				        File file = new File("/storage/sdcard0/avatarClientScaled");
				        FileOutputStream outputStream = new FileOutputStream(file);
				        
				        selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);
				        
				        imageUri = Uri.fromFile(file);
				        //imageUri.fromFile(new File("/storage/sdcard0/avatar5"));
	
				        //return file;
				    } catch (Exception e) {
				    	
				    	Toast.makeText(Client1.this,"ERROR. Please try again.", Toast.LENGTH_SHORT).show();
				        //return null;
				    }
					
					
					final Intent svc=new Intent(this, Badonk2SoundService.class);
					
					final String[] items = new String[] {"Join", "Cancel"};
					final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.computer};
					
					ListAdapter adapter = new ArrayAdapterWithIcon(Client1.this, items, avatars);
					
					ContextThemeWrapper wrapper = new ContextThemeWrapper(Client1.this, R.layout.avatar_adapter);
					AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);
					//builder.setIcon(R.drawable.computerhead);
					builder.setTitle("Multiplayer");
					
					builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
						public void onClick(DialogInterface dialog, int item) { 
											
							if (item == 0) {    					
								
								stopService(svc);
								
			    				
			    				Intent intent = new Intent(Client1.this, Client2.class);
			    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			    				
			    				//intent.putExtra("hostIP", hostIP);		    				
			    				startActivity(intent);		    				
			    	        	
			    	        	dialog.dismiss();
			    	        	
			    	        	//finish();
							}
							else if (item == 1) {		
			    	        	
			    	        	dialog.dismiss();
			    	        	
			    	        	finish();
							}    				    				
				        	
				        	//finish();
				  		}
					});	    		
			    	
			        builder.create().show();
			        
					
					Intent intent = new Intent(Client1.this, Client2.class);
				    intent.putExtra("imageUri", imageUri.toString());
					//intent.putExtra("imageUri", imageUri);
				    startActivity(intent);
				    
				    //finish();
				}
				*/
				else if (file_size > 6) {
					
					//Toast.makeText(Client1.this,"Avatar image must be less than 60 KB (" + file_size +" ).", Toast.LENGTH_SHORT).show();
					Toast toast2 = Toast.makeText(Client1OldGuy.this, "Avatar image must be less than 60 KB (" + file_size +" ).", Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
					
					//Toast.makeText(Client1.this,"ERROR LOADING IMAGE " + file_size, Toast.LENGTH_SHORT).show();
					Toast toast3 = Toast.makeText(Client1OldGuy.this, "ERROR LOADING IMAGE " + file_size, Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
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
			
			else {
				
				//Toast.makeText(Client1.this,"MULTIPLAYER = " + multiplayer, Toast.LENGTH_SHORT).show();
				Toast toast4 = Toast.makeText(Client1OldGuy.this, "MULTIPLAYER = " + multiplayer, Toast.LENGTH_SHORT);//INSTEAD OF "Choose Action": R.string.string_message_id
	  			View view4 = toast4.getView();
	  			view4.setBackgroundResource(R.drawable.centerscroll3toast);
	  			toast4.setGravity(Gravity.CENTER, 0, 0);//CAN CHANGE X, Y POSITIONS RELATIVE TO CENTER

	  			TextView text4 = (TextView) view4.findViewById(android.R.id.message);
	  			Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");
	  			text4.setTypeface(typeFace);
	  			text4.setTextColor(Color.parseColor("#FFFFFF"));
	  			//text.setRotation(-45);
	  			text4.setGravity(Gravity.CENTER);
	  			
	  			toast4.show();
			}
			
			break;//NEED THIS?
		}
	}
	
	
	public void goToJoin() {
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		
		final MediaPlayer buttonSound = MediaPlayer.create(Client1OldGuy.this, R.raw.swordswing);
		
		
		final String[] items = new String[] {"Join", "Cancel"};
		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.computer};
		
		ListAdapter adapter = new ArrayAdapterWithIcon(Client1OldGuy.this, items, avatars);
		
		ContextThemeWrapper wrapper = new ContextThemeWrapper(Client1OldGuy.this, R.layout.avatar_adapter);
		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper, R.style.customalertdialog);
		//builder.setIcon(R.drawable.computerhead);
		//builder.setTitle("Multiplayer");
		
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int item) { 
								
				if (item == 0) {    					
					
					buttonSound.start();
					
					stopService(svc);  				
					
					
    				Intent intent = new Intent(Client1OldGuy.this, Client2.class);
    				//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    				
    				//intent.putExtra("hostIP", hostIP);    				
    				startActivity(intent);
    				
    	        	dialog.dismiss();
    	        	
    	        	hideSystemUI();
    	        	
    	        	//Client1.this.finish();
    	        	
    	        	//finish();
				}
				else if (item == 1) {
					
					buttonSound.start();
    	        	
    	        	dialog.dismiss();
    	        	
    	        	hideSystemUI();
    	        	
    	        	finish();
    	        	
				}    				    				
	        	
	        	//finish();
	  		}
		});	    		
    	
        builder.create().show();		
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

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
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
     	final MediaPlayer buttonSound1 = MediaPlayer.create(Client1OldGuy.this, R.raw.swordswing);
        final MediaPlayer buttonSound2 = MediaPlayer.create(Client1OldGuy.this, R.raw.sworddraw1);
        
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