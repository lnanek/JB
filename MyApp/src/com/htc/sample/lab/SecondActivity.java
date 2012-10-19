package com.htc.sample.lab;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class SecondActivity extends SherlockActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Use the logo in the ActionBar as a home button with an arrow.
		final ActionBar bar = getSupportActionBar();
		bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME,
				ActionBar.DISPLAY_SHOW_HOME);
		bar.setHomeButtonEnabled(true);
		bar.setDisplayShowHomeEnabled(true);
		bar.setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_second);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem aItem) {
		// When home is pressed, finish, which shows the first Activity again.
		if (aItem.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(aItem);
	}
}
