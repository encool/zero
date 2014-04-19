package com.example.zero1.db;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class StationDBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "station.db";  
    private static final int DATABASE_VERSION = 1; 
	public StationDBOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
    public StationDBOpenHelper(Context context) {  
        //CursorFactory设置为null,使用默认值  
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }  

	public StationDBOpenHelper(Context context, String name,
			CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//没表时--调试

		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS station" +  
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, station_thr_code VARCHAR, station_name_ch TEXT, station_code VARCHAR, station_name_pingyin VARCHAR, station_pingyin_shou VARCHAR)");  
		


		db.execSQL("CREATE TABLE IF NOT EXISTS stationupdateinfo" +
					"(_id INTEGER PRIMARY KEY,tablename TEXT,last_update_time INTEGER,station_count INTEGER)");
		db.execSQL("INSERT INTO stationupdateinfo(_id,tablename,last_update_time,station_count) VALUES(1,'station',0,0)");

		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
//		Cursor c=db.rawQuery("select count(*) as c from Sqlite_master  where type ='table' and name ='stationupdateinf'", null);
//		int boo=c.getCount();
	}

}
