package com.nedswebsite.ktog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class Rules extends Activity {
	 
    private ViewFlipper mViewFlipper;
    private GestureDetector mGestureDetector;
 
    int[] resources = {
            R.drawable.ktogrules1,
            R.drawable.ktogrules2,
            R.drawable.ktogrules3
    };
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
	    // USED THE FOLLOWING TO REMOVE TITLE BAR:
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_rules);
        
        // Get the ViewFlipper
        mViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
 
        // Add all the images to the ViewFlipper
        for (int i = 0; i < resources.length; i++) {
            ImageView imageView = new ImageView(this);
            
            // Provides full-screen for ViewFlipper images (stretches them):
            //(good because can't get zoom working w ViewFlipper)
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            
            imageView.setImageResource(resources[i]);
            mViewFlipper.addView(imageView);
        }
        
        // Set in/out flipping animations
        mViewFlipper.setInAnimation(this, android.R.anim.fade_in);
        mViewFlipper.setOutAnimation(this, android.R.anim.fade_out);
 
        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        mGestureDetector = new GestureDetector(this, customGestureDetector);
    }
 
    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
     
            // Swipe left (next)
            if (e1.getX() > e2.getX()) {
                mViewFlipper.setInAnimation(Rules.this, R.anim.left_in);
                mViewFlipper.setOutAnimation(Rules.this, R.anim.left_out);
     
                mViewFlipper.showNext();
            }
     
            // Swipe right (previous)
            if (e1.getX() < e2.getX()) {
                mViewFlipper.setInAnimation(Rules.this, R.anim.right_in);
                mViewFlipper.setOutAnimation(Rules.this, R.anim.right_out);
     
                mViewFlipper.showPrevious();
            }
            
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
 
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        
        return super.onTouchEvent(event);
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
        
    // STUFF FOR ZOOMING:
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }
           
    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(Rules.this, MainActivity1.class);
        Rules.this.startActivity(i);
    }
}