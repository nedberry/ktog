package com.nedswebsite.ktog;

import java.io.IOException;
import java.util.ArrayList;
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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

public class MainActivity1 extends ActionBarActivity {

	//NEED THE 6?
	public static String[] player = new String[6];
	//NEED THE 6?
	public static String[] avatar = new String[6];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// USED THE FOLLOWING TO REMOVE TITLE BAR:
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
				
		setContentView(R.layout.activity_main_activity1);
		
		ImageView img = (ImageView)findViewById(R.id.menu1);
		
		final ImageButton onePlayerButton = (ImageButton) findViewById(R.id.imagebuttononeplayer);
		final ImageButton multiPlayerButton = (ImageButton) findViewById(R.id.imagebuttonmultiplayer);
		final ImageButton aboutButton = (ImageButton) findViewById(R.id.imagebuttonabout);
		
		// For sound for buttons:
		final MediaPlayer buttonSound = MediaPlayer.create(MainActivity1.this, R.raw.swordswing);
		
		onePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
			                    	
        	buttonSound.start();        	
        	
        	//Toast.makeText(MainActivity1.this,"One player button is working!!", Toast.LENGTH_LONG).show();
        	//USE THIS WHEN READY??:
        	//Intent openNewGuyOldGuy = new Intent("com.nedswebsite.ktog.NewGuyOldGuy");
			//startActivity(openNewGuyOldGuy);
        	
        	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity1.this);

        	alert.setTitle("KtOG");
        	alert.setMessage("Enter Name");

        	// Set an EditText view to get user input 
        	final EditText input = new EditText(MainActivity1.this);
        	alert.setView(input);

        	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int whichButton) {
        		        		
        		// Do something with value!        		
        		  		
        		//NEED TO SEND TO ARRAY HERE:
	        	player[0] = input.getText().toString();
	        	player[1] = "Computer".toString();
	        	
	        	//Toast.makeText(MainActivity1.this, "Welcome " + player[0] + "!", Toast.LENGTH_LONG).show();
        		
	        	insertToDatabase(player[0]);
	        	
	        	
	        	// ARRAY ADAPTER WITH ICON STUFF:
	        	
	        	final String[] avatar = new String[6];
	        	final String[] items = new String[] {"Computer", "Crossed Swords", "Stone Dead"};
	    		final Integer[] avatars = new Integer[] {R.drawable.computer, R.drawable.crossedswords2, R.drawable.stonedead2};
	    		
	    		ListAdapter adapter = new ArrayAdapterWithIcon(MainActivity1.this, items, avatars);
	    		
	    		ContextThemeWrapper wrapper = new ContextThemeWrapper(MainActivity1.this, R.layout.avatar_adapter);
	    		AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);
	    		//builder.setIcon(R.drawable.computerhead);
	    		builder.setTitle("Choose Your Avatar");
	    		
	    		builder.setAdapter(adapter, new DialogInterface.OnClickListener() { 
	    			public void onClick(DialogInterface dialog, int item) { 
	    								
	    				if (item == 0) {
	    					avatar[0] = "computer";
	    				}
	    				if (item == 1) {
	    					avatar[0] = "crossedswords";
	    				}
	    				if (item == 2) {
	    					avatar[0] = "stonedead";
	    				}
	        	
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
        	
        	Toast.makeText(MainActivity1.this,"Multi-player button is working!!", Toast.LENGTH_LONG).show();
        	//USE THIS WHEN READY??:
        	//Intent openMain2Activity = new Intent("com.example.ktog1.MAIN2ACTIVITY");
			//startActivity(openMain2Activity);		
        				
			}
		});
		
		aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
			                    	
        	buttonSound.start();
        	
        	//Think I need this so user doesn't have to push 'back' more than once (possibly)?
        	finish();
        	
        	Intent i = new Intent(MainActivity1.this, Rules.class);
        	MainActivity1.this.startActivity(i);
        	
        	//Toast.makeText(MainActivity1.this,"About button is working!!", Toast.LENGTH_LONG).show();
        	
        	//USE THIS WHEN READY??:
        	//Intent openMain2Activity = new Intent("com.example.ktog1.MAIN2ACTIVITY");
			//startActivity(openMain2Activity);		
        				
			}
		});
	
	}
	
	
	
	private void insertToDatabase(final String player){
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
	
	
	
}