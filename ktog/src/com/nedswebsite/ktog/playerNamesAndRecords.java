package com.nedswebsite.ktog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class playerNamesAndRecords extends Activity {
	
	//int GamesPlayed;
	//int Wins;
	//int Loses;
	int count = 0;
	
	File[] allFilesIndir;
	File[] allTxtFiles;//FILE/PLAYER NAME
	
	//CAN'T CREATE EMPTY ARRAY
	public String[] name = new String[100];
	public int[] gamesPlayed = new int[100];
	public int[] wins = new int[100];
	public int[] loses = new int[100];
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		// USED THE FOLLOWING TO REMOVE TITLE BAR:
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// This will hide the system bar until user swipes up from bottom or down from top.		
		//getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		
		setContentView(R.layout.activity_playernamesandrecords);		
		// For the little space between the action & attack button.
		getWindow().getDecorView().setBackgroundColor(Color.BLACK);		
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		
		
		
		
		
		final Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/PirataOne-Regular.ttf");		
		
		final TextView titletext = (TextView) findViewById(R.id.textviewtitlektogtext);	
		titletext.setTypeface(typeFace);		
		titletext.setVisibility(View.VISIBLE);				  		
		titletext.append("KtOG");		
		
		
		getCount();
		
		getPlayerName();
		
		getTextAndDataFromFile();
		
		list();
		
		//Toast.makeText(playerNamesAndRecords.this, "Games = " + gamesPlayed[0] + " " + "Wins = " + wins[0] + " " + "Loses = " + loses[0], Toast.LENGTH_LONG).show();
		
		/*
		// NOTICE THE append's BELOW, JUST FOR INTRO (USED setText IN TITLE BUTTON):
		
		//final TableLayout summaryTableLayout = (TableLayout) findViewById(R.id.summaryTable);
		TableLayout playerRecords = (TableLayout) findViewById(R.id.recordsTable);
		
		
		
		final View lineInSummaryTableLayout = (View) findViewById(R.id.line);
		
		
  		
			
		playerRecords.setVisibility(View.VISIBLE);
  		//summaryTableLayout.setMovementMethod(new ScrollingMovementMethod());
  		lineInSummaryTableLayout.setVisibility(View.VISIBLE);
  		
	  		
  	  	TextView statisticstextview = (TextView) findViewById(R.id.summarytext);
  		statisticstextview.setTypeface(typeFace);
  		statisticstextview.append("Player Records");
  		statisticstextview.setVisibility(View.VISIBLE);
	  		
  	  	TextView blanktextview = (TextView) findViewById(R.id.blanktext);
  	  	blanktextview.setTypeface(typeFace);
  	  	blanktextview.append("");
  	  	blanktextview.setVisibility(View.VISIBLE);
	  		
  		TextView hitpointstextview = (TextView) findViewById(R.id.name);
  		hitpointstextview.setTypeface(typeFace);
  		hitpointstextview.append("HP");
  		hitpointstextview.setVisibility(View.VISIBLE);
	  		
  	  	TextView blesstextview = (TextView) findViewById(R.id.wins);
  	  	blesstextview.setTypeface(typeFace);
  	  	blesstextview.append("Bless");
  	  	blesstextview.setVisibility(View.VISIBLE);
	  		
  	  	TextView curetextview = (TextView) findViewById(R.id.loses);
  	  	curetextview.setTypeface(typeFace);
  	  	curetextview.append("Cure");
  	  	curetextview.setVisibility(View.VISIBLE);
  	  	
  	  	
	  		
  	  	TextView player1textview = (TextView) findViewById(R.id.player);
  	  	player1textview.setTypeface(typeFace);
  	  	//player1textview.append(ArrayOfPlayers.player[firstsubscript]);
  	  	player1textview.setVisibility(View.VISIBLE);
  	  	
  	  	TextView hitpointsplayer1textview = (TextView) findViewById(R.id.playerwins);
  	  	hitpointsplayer1textview.setTypeface(typeFace);
  	  	//String hitpointsplayer1String = Integer.toString(ArrayOfHitPoints.hitpoints[firstsubscript]);
  	  	//hitpointsplayer1textview.append(hitpointsplayer1String);
  		hitpointsplayer1textview.setVisibility(View.VISIBLE);
  	  	
  	  	TextView blessplayer1textview = (TextView) findViewById(R.id.playerloses);
  	  	blessplayer1textview.setTypeface(typeFace);
  	  	//String blessplayer1String = Integer.toString(blessSpell[firstsubscript]);
  	  	//blessplayer1textview.append(blessplayer1String);
  	  	blessplayer1textview.setVisibility(View.VISIBLE);
  	  	*/
	}
	
	
	
	
	
	public void getCount() {
		
		File directory = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files");
		File[] files = directory.listFiles();
      
		if (files != null)
			for (int i = 0; i < files.length; ++i) {

			count++;
			File file = files[i];
			}
	}
	
	
	public void getPlayerName() {//ALSO FILE NAME
		
		File directory = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files");
		allFilesIndir = directory.listFiles();
		
		allTxtFiles = directory.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.toUpperCase().endsWith(".TXT")) {					
					
					return new File(dir, name).isFile();
				}
				return false;
			}
		});
	}
	
	
	public void getTextAndDataFromFile() {
		
		File directory = new File("/storage/emulated/0/Android/data/com.nedswebsite.ktog/files");
		
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
			    	  
			    	  String[] parts = line.split(":");
			    	  String part1 = parts[0];
			    	  String part2 = parts[1];
			    	  String part3 = parts[2];
			    	  String part4 = parts[3];
			    	  String part5 = parts[4];
			    	  String part6 = parts[5];		    	  
			    	  
			    	  String filename = file.getName().toString();
			    	  filename = filename.substring(0, filename.lastIndexOf("."));
			    	  
			    	  name[i] = filename;
			    	  //name[0] = file.getName().toString();
			    	  gamesPlayed[i] = Integer.parseInt(part2);
			    	  wins[i] = Integer.parseInt(part4);
			    	  loses[i] = Integer.parseInt(part6);
			    	  
			    	  //Toast.makeText(playerNamesAndRecords.this, "Games = " + gamesPlayed[0] + " " + "Wins = " + wins[0] + " " + "Loses = " + loses[0], Toast.LENGTH_LONG).show();
			      }
			      
			      reader.close();
			   } catch (Exception e) {
			      Log.e("ReadWriteFile", "Unable to read the TestFile.txt file.");
			   }
			
			}
			
			
			
		}
		
		
		
	}
	
	
	public void list() {
		
        TableLayout playerRecords = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" Name ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Games Played ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Wins ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Loses ");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);
        playerRecords.addView(tbrow0);
        for (int i = 0; i < count; i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("" + name[i]);//WON'T DO JUST: allTxtFiles[i]
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(String.valueOf(gamesPlayed[i]));
            //t2v.setText(gamesPlayed[i]);
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText(String.valueOf(wins[i]));
            //t3v.setText(wins[i]);
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText(String.valueOf(loses[i]));
            //t4v.setText(loses[i]);
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            playerRecords.addView(tbrow);
        }
    }
}
