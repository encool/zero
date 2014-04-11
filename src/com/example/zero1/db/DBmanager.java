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
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);  
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里  
        writabledb = helper.getWritableDatabase();  
        readabledb = helper.getReadableDatabase();
	}
	public void add(List<com.example.zero1.Station> stas){
		writabledb.beginTransaction();  //开始事务  
        try {  
            for (com.example.zero1.Station station : stas) {  
            	writabledb.execSQL("INSERT INTO station VALUES(null,?, ?, ?, ?, ?)", new Object[]{station.station_thr_code,station.station_name_ch,station.station_code,station.station_name_pingyin,station.station_pingyin_shou});  
            }  
            writabledb.setTransactionSuccessful();  //设置事务成功完成  
        } finally {  
        	writabledb.endTransaction();    //结束事务  
        }  
	}
}
