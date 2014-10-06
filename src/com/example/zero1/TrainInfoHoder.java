package com.example.zero1;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.format.DateFormat;

public class TrainInfoHoder implements Serializable {
	String station_train_code;
	String start_station_code;
	String end_starion_code;
	String from_station_code;
	String to_station_code;
	String start_station_name_ch;
	String start_train_date;
	String from_station_name_ch;
	String to_station_name_ch;
	String end_station_name_ch;
	String start_time;
	String arrive_time;
	String lishi; 
	//��Ҫ������ֶ�����ϯ��Ӧ��ϵ���޸�
	String yz_num;//yingzuo?
	String rw_num;//ruanwo?
	String yw_num;//yinwo?
	public String wz_num;//wuzuo?
	
	String ze_num;//erdengzuo?
	String zy_num;//yidengzuo?
	String swz_num;//��������
	String secretStr;
	String start_train_date_mmyueddri;
	String start_train_date_week;
	SimpleDateFormat sf=new SimpleDateFormat("yyyymmdd");
	SimpleDateFormat sf1=new SimpleDateFormat("mm��dd��");
//	SimpleDateFormat sf2=new SimpleDateFormat("����dow");
	String [] str = {"","������","����һ","���ڶ�","������","������","������","������",};
	public void convertDateFormat(){
		try {
			Date date=sf.parse(start_train_date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int number = calendar.get(Calendar.DAY_OF_WEEK);
			start_train_date_mmyueddri=sf1.format(date);
			start_train_date_week=str[number];
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
