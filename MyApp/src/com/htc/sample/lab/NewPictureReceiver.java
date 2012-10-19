package com.htc.sample.lab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Gets notified when new pictures are added.
 */
public class NewPictureReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context aContext, Intent aIntent) {
		// Log the new picture and start the app with the picture URI as the data of the intent.
		Log.i("NewPictureReceiver", "Data is: " + aIntent.getData());		
		final Intent intent = new Intent(aContext, MainActivity.class);
		intent.setData(aIntent.getData());
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		aContext.startActivity(intent);		
	}

}
