package com.example.zero1;

//import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
//import android.support.v7.*;
import android.support.v7.app.ActionBar;
//import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

public class AppActivity extends android.support.v7.app.ActionBarActivity {
	FragmentPagerAdapter ticketPageAdapter;
	ViewPager viewpager;
	public AppActivity() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		viewpager=(ViewPager) findViewById(R.id.pager);
		viewpager.setAdapter(new TicketPagerAdater(getSupportFragmentManager()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
