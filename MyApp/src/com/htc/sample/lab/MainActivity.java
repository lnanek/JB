package com.htc.sample.lab;

import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.LayoutTransition.TransitionListener;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

public class MainActivity extends SherlockActivity {
		
	private static final int CHANGE_LAYOUT_DURATION_MS = 5000;
	private static final int SHOW_IMAGE_DELAY_MS = 2000;
	private Uri imageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Save any image location the app was started with for later use.
        imageUri = getIntent().getData();
        
        setContentView(R.layout.activity_main);        
        
        addFullScreenWindowFlag();
        dimSystemUi();        
    }
    
	@Override
	protected void onResume() {
		super.onResume();
        dimSystemUi();

        // Wait a little bit before changing the layout so we can see it happen.
        getWindow().getDecorView().postDelayed(new Runnable() {
			@Override
			public void run() {
				// Animate changing the layout.
		        animateReplaceContentWithImage();
			}
        }, SHOW_IMAGE_DELAY_MS);
	}

    private void animateReplaceContentWithImage() {
    	// Set the container up with a transition.
        final ViewGroup container = (ViewGroup) findViewById(R.id.container);        
        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(CHANGE_LAYOUT_DURATION_MS);        
		container.setLayoutTransition(transition);

		// Remove the previous content.
        container.removeAllViews();
        // Add a new image.
        final ImageView image = new ImageView(this);
        // Use the image location we were started with if it exists, otherwise the launcher icon.
        if ( null == imageUri ) {
        image.setImageResource(R.drawable.ic_launcher);
        } else {
        	image.setImageURI(imageUri);
        }
        container.addView(image, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        
        // When clicked, demonstrate starting an activity with an animation.
        image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View aV) {
				startSecondActivityScalingImageUpFromView(aV);
			}
		});

        // When changing the layouts is finished animation, animate the view bouncing.
        transition.addTransitionListener(new TransitionListener() {
			@Override
			public void endTransition(LayoutTransition aTransition, ViewGroup aContainer, View aView, int aTransitionType) {
				animateViewBouncing(image);
			}
			@Override
			public void startTransition(LayoutTransition aTransition, ViewGroup aContainer, View aView, int aTransitionType) {
			}        	
        });
    }

	public void addFullScreenWindowFlag() {
        // Hide status bar at top on handsets.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);    	
    }
    
	public void dimSystemUi() {
		final View rootView = getWindow().getDecorView();
        
        // Dim navigation buttons and hide other elements of navigation bar.
        rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE );
        
        // Hide navigation completely on some devices while no user input.
        // On JellyBean emulator it will hide the bottom navigation until first tap, for example.
        //rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        
        // Hide status and navigation, also ActionBar if using Window.FEATURE_ACTION_BAR_OVERLAY.
        //rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
	}
	 
   public void startSecondActivityScalingImageUpFromView(View view) {
      // Create a scale-up animation that originates at the view being pressed.
      ActivityOptions opts = ActivityOptions.makeScaleUpAnimation(view, 0, 0, view.getWidth(), view.getHeight());
      // Request the activity be started, using the custom animation options.
      startActivity(new Intent(MainActivity.this, SecondActivity.class), opts.toBundle());
   }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate your menu.
        getSupportMenuInflater().inflate(R.menu.activity_main, menu);

        return true;
    }

	public void animateViewBouncing(final ImageView image) {
		// Example of scaling a property named in a String.
		//ObjectAnimator.ofFloat(image, "alpha", 0f).start();
		
		// Simple property animation.
		//ObjectAnimator.ofFloat(image, View.TRANSLATION_Y, 100f).start();
		
		// A set of animations.
		AnimatorSet bouncer = new AnimatorSet();
		ObjectAnimator moveUp = ObjectAnimator.ofFloat(image, View.TRANSLATION_Y, -100f);
		ObjectAnimator moveDown = ObjectAnimator.ofFloat(image, View.TRANSLATION_Y, 0f);
		ObjectAnimator moveUpLess = ObjectAnimator.ofFloat(image, View.TRANSLATION_Y, -50f);
		ObjectAnimator moveDownAgain = ObjectAnimator.ofFloat(image, View.TRANSLATION_Y, 0f);
		bouncer.playSequentially(moveUp, moveDown, moveUpLess, moveDownAgain);
		bouncer.setTarget(image);
		bouncer.start();
	}
}
