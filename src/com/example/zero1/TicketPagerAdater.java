package com.example.zero1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TicketPagerAdater extends FragmentPagerAdapter {

	public TicketPagerAdater(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		Fragment sf=null;
		switch(index){
		case 0:
			sf=new QueryFragment();
		}
		return sf;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

}
