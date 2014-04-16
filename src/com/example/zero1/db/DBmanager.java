package com.example.zero1.db;
import java.util.ArrayList;
import java.util.List;

import com.example.zero1.Station;
import com.example.zero1.Utility;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBmanager {

	private StationDBOpenHelper helper;
	private SQLiteDatabase writabledb;
	private SQLiteDatabase readabledb;
	public DBmanager(Context context) {
		// TODO Auto-generated constructor st ub
        helper = new StationDBOpenHelper(context);  
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
//	public boolean isNeedUpdateStation(){
//		Cursor c=readabledb.query("starion", new String[]{"last_update_time","station_count"}, "tablename = 'station'", null, null, null, null);
//		while(c.moveToNext()){
//			int lastupdate=c.getInt(c.getColumnIndex("last_update_time"));
//			int stationcount=c.getInt(c.getColumnIndex("station_count"));
//		}
//	}
	public ArrayList<Station> queryStationFromdb(int limit,String s){
		ArrayList<Station> Stations=new ArrayList<Station>();
		String stationstring=Utility.prepareString(s);
		String selection = "";
		char c=stationstring.charAt(0);
		Character.UnicodeBlock cub = Character.UnicodeBlock.of(c);
		if(cub==Character.UnicodeBlock.BASIC_LATIN){
			selection="station_pingyin_shou LIKE '" + stationstring + "%' OR station_name_pingyin LIKE '"+stationstring+"%' LIMIT "+String.valueOf(limit) ;
		}
		else if(cub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS){
			selection="station_name_ch LIKE '"+stationstring+"%' LIMIT "+String.valueOf(limit);
		}
		Cursor cur=readabledb.query("station", null, selection, null, null, null, null);
		while(cur.moveToNext()){
//        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, station_thr_code VARCHAR, station_name_ch TEXT, station_code VARCHAR, station_name_pingyin VARCHAR, station_pingyin_shou VARCHAR)");  
			Station sta=new Station();
			sta.station_thr_code=cur.getString(cur.getColumnIndex("station_thr_code"));
			sta.station_name_ch=cur.getString(cur.getColumnIndex("station_name_ch"));
			sta.station_code=cur.getString(cur.getColumnIndex("station_code"));
			sta.station_name_pingyin=cur.getString(cur.getColumnIndex("station_name_pingyin"));
			sta.station_pingyin_shou=cur.getString(cur.getColumnIndex("station_pingyin_shou"));
			Stations.add(sta);
		}
		cur.close();
		return Stations;	
	}
	public void closeBD(){
		writabledb.close();
		readabledb.close();
	}
}
