package com.killtheotherguy.ktog;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.VideoView;
import com.killtheotherguy.ktog.R;

public class Video extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
						
		// USED THE FOLLOWING TO REMOVE TITLE BAR:
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_video);
		
		showVideo();
	}
	
	public void showVideo() {
        final VideoView vv = (VideoView)findViewById(R.id.videoview);
        Uri uri = Uri.parse("android.resource://com.killtheotherguy.ktog/"+R.raw.ktogintrovideo);
        vv.setVideoURI(uri);
        
        //vv.requestFocus();
        
        vv.start();
        
        
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
            	// USE TO PRESERVE MEMORY
            	finish();
            	Intent openMainActivity = new Intent("com.killtheotherguy.ktog.MAINACTIVITY1");
            	startActivity(openMainActivity);            	
            }
        });        
        
        
        // By tapping button (screen), player skips video.                        
        final ImageButton imageButton = (ImageButton) findViewById(R.id.videobutton1);
        
        //imageButton1.bringToFront();
        
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
                          	
            	// Perform action on click
            	Intent openMainActivity = new Intent("com.killtheotherguy.ktog.MAINACTIVITY1");
            	startActivity(openMainActivity);
            	// USE TO PRESERVE MEMORY
            	finish();            	
            }
        });
                
    }
		
	protected void onResume(VideoView vv) {
	    vv.resume();
	    super.onResume();
	}

	protected void onPause(VideoView vv) {
	    vv.suspend();
	    super.onPause();
	}

	protected void onDestroy(VideoView vv) {
	    vv.stopPlayback();
	    vv.setVideoURI(null);
	    super.onDestroy();
	}
}