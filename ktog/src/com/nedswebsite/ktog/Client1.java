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
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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

public class Client1 extends Activity {//WAS ActionBarActivity (got "app stopped" message on S4 w/o this)			
	
	public static final int PICK_IMAGE = 100;
	public ImageView customImageView;
	
	String multiplayer = "yes";	
	
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
		
		//NEW
		// For the little space between the action & attack button.
		getWindow().getDecorView().setBackgroundColor(Color.BLACK);		
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		//NEW
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
				
		
		
		
		ImageView img = (ImageView)findViewById(R.id.menu1);
		
		//final ImageButton onePlayerButton = (ImageButton) findViewById(R.id.imagebuttononeplayer);
		//final ImageButton multiPlayerButton = (ImageButton) findViewById(R.id.imagebuttonmultiplayer);
		//final ImageButton aboutButton = (ImageButton) findViewById(R.id.imagebuttonabout);
		
		
		// Sound stuff:
		final MediaPlayer buttonSound = MediaPlayer.create(Client1.this, R.raw.swordswing);
		
		final Intent svc=new Intent(this, Badonk2SoundService.class);
		//stopService(svc);
		startService(svc);		
            
			                    	
    	buttonSound.start();
    	
    	//multiplayer = "yes";
    	
    	
    	AlertDialog.Builder alert = new AlertDialog.Builder(Client1.this);

    	alert.setTitle("Multiplayer");
    	alert.setMessage("Enter Name");

    	// Set an EditText view to get user input:
    	final EditText input = new EditText(Client1.this);
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
    		
    		//Toast.makeText(Client1.this,"MULTIPLAYER = " + multiplayer, Toast.LENGTH_LONG).show();
    		
    		// NEED TO SEND TO ARRAY HERE:
    		String playername = input.getText().toString();
        	
        	
        	ArrayOfPlayers.player[0] = playername;
        	
        	
        	insertToDatabase(playername);	        	
        	
        	// ARRAY ADAPTER WITH ICON STUFF:	        	
        	
        	final String[] items = new String[] {"Crossed Swords", "Stone Dead", "Custom"};
    		final Integer[] avatars = new Integer[] {R.drawable.crossedswords2, R.drawable.stonedead2, R.drawable.computer};
    		
    		ListAdapter adapter = new ArrayAdapterWithIcon(Client1.this, items, avatars);
    		
    		ContextThemeWrapper wrapper = new ContextThemeWrapper(Client1.this, R.layout.avatar_adapter);
    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);
    		//builder.setIcon(R.drawable.computerhead);
    		builder.setTitle("Choose Your Avatar");
    		
    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
    			public void onClick(DialogInterface dialog, int item) { 
    								
    				if (item == 0) {
    					ArrayOfAvatars.avatar[0] = "crossedswords";
    					
    					goToJoin();
	    	        	
	    	        	dialog.dismiss();
	    	        	
	    	        	hideSystemUI();
    				}
    				else if (item == 1) {
    					ArrayOfAvatars.avatar[0] = "stonedead";
    					
    					goToJoin();
	    	        	
	    	        	dialog.dismiss();
	    	        	
	    	        	hideSystemUI();
    				}
    				else if (item == 2) {
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
    	});

    	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    	  public void onClick(DialogInterface dialog, int whichButton) {
    	    
    		  hideSystemUI();
    	  }
    	});
    	
    	alert.show();
	}
	
	
	public void openGallery() {
		
		Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		startActivityForResult(gallery, PICK_IMAGE);
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//Toast.makeText(Client1.this,"WTF!?", Toast.LENGTH_LONG).show();
		switch(requestCode){	
		
		case PICK_IMAGE://FOR IMAGE GALLERY
			
			//Toast.makeText(Client1.this,"WTF!?", Toast.LENGTH_LONG).show();
			
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
				Intent intentFileSize = new Intent(MainActivity1.this, MainActivity2.class);
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
		        
		        Toast.makeText(Client1.this,"IMAGE SIZE = " + file_size, Toast.LENGTH_LONG).show();
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
					
					ListAdapter adapter = new ArrayAdapterWithIcon(Client1.this, items, avatars);
					
					ContextThemeWrapper wrapper = new ContextThemeWrapper(Client1.this, R.layout.avatar_adapter);
					AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);
					//builder.setIcon(R.drawable.computerhead);
					builder.setTitle("Multiplayer");
					
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
								Intent intent = new Intent(Client1.this, Client2.class);
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
				    	
				    	Toast.makeText(Client1.this,"ERROR. Please try again.", Toast.LENGTH_LONG).show();
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
					
					Toast.makeText(Client1.this,"Avatar image must be less than 60 KB (" + file_size +" ).", Toast.LENGTH_LONG).show();
				}
				
				else {
					
					Toast.makeText(Client1.this,"ERROR LOADING IMAGE " + file_size, Toast.LENGTH_LONG).show();
				}
			}
			
			else {
				
				Toast.makeText(Client1.this,"MULTIPLAYER = " + multiplayer, Toast.LENGTH_LONG).show();
			}
		break;		
		
		}
	}
	
	
	public void goToJoin() {
		
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
    	        	
    	        	hideSystemUI();
    	        	
    	        	//Client1.this.finish();
    	        	
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
			
		    //Toast.makeText(Client1.this, hostIP, Toast.LENGTH_LONG).show();
		    
		} catch (Throwable t) {
				Toast.makeText(Client1.this, "HOSTIP CONVERSION DID NOT WORK", Toast.LENGTH_LONG).show();
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
        	//Toast.makeText(Client1.this,"Multi-player not working yet :(", Toast.LENGTH_LONG).show();
            	            				
    		
    		
    		        	
        } 
        
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
        	
        	setContentView(R.layout.activity_main_activity1);
        	
        	//final ImageButton onePlayerButton = (ImageButton) findViewById(R.id.imagebuttononeplayer);
    		//final ImageButton multiPlayerButton = (ImageButton) findViewById(R.id.imagebuttonmultiplayer);
    		//final ImageButton aboutButton = (ImageButton) findViewById(R.id.imagebuttonabout);   		   		
    		
                	
            buttonSound1.start();
            	
            //Intent openMainActivity2 = new Intent("com.nedswebsite.ktog.MAINACTIVITY2");
            //startActivity(openMainActivity2);
            //Toast.makeText(Client1.this,"Multi-player not working yet :(", Toast.LENGTH_LONG).show();    		
        }
    }	
	
}