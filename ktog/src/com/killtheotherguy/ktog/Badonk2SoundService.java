package com.killtheotherguy.ktog;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import com.killtheotherguy.ktog.R;

public class Badonk2SoundService extends Service {
	
	private static final String TAG = null;
    MediaPlayer player;
    
    public IBinder onBind(Intent arg0) {
    	
        return null;
    }
    
    @Override
    public void onCreate() {
    	
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.badonk2b);
        player.setLooping(true);
        player.setVolume(100,100);
    }
    
    public int onStartCommand(Intent intent, int flags, int startId) {
    	
        player.start();
        return 1;
    }

    public void onStart(Intent intent, int startId) {
        
    }
    
    public IBinder onUnBind(Intent arg0) {
        
        return null;
    }
    
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }
}