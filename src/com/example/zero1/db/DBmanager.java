package com.example.zero1.db;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBmanager {

	private StationOpenHelper helper;
	private SQLiteDatabase writabledb;
	private SQLiteDatabase readabledb;
	public DBmanager(Context context) {
		// TODO Auto-generated constructor stub
        helper = new StationOpenHelper(context);  
        //��ΪgetWritableDatabase�ڲ�������mContext.openOrCreateDatabase(mName, 0, mFactory);  
        //����Ҫȷ��context�ѳ�ʼ��,���ǿ��԰�ʵ����DBManager�Ĳ������Activity��onCreate��  
        writabledb = helper.getWritableDatabase();  
        readabledb = helper.getReadableDatabase();
	}
	public void add(List<com.example.zero1.Station> stas){
		writabledb.beginTransaction();  //��ʼ����  
        try {  
            for (com.example.zero1.Station station : stas) {  
            	writabledb.execSQL("INSERT INTO station VALUES(null,?, ?, ?, ?, ?)", new Object[]{station.station_thr_code,station.station_name_ch,station.station_code,station.station_name_pingyin,station.station_pingyin_shou});  
            }  
            writabledb.setTransactionSuccessful();  //��������ɹ����  
        } finally {  
        	writabledb.endTransaction();    //��������  
        }  
	}
}
