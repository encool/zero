package com.example.zero1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdater extends FragmentPagerAdapter {

	public MyPagerAdater(FragmentManager fm) {
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
			break;
//			sf=new OrderFragment();
		case 1:
			sf=new OrderFragment();
			break;
		case 2:
			sf=new InfoFragment();
		}
		return sf;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		String s = null;
		switch(position){
		case 0:
			s=new String("查询");
			break;
		case 1:
			s=new String("订单");
			break;
		case 2:
			s=new String("我的资料");
			break;
		}
		return s;
	}

}
