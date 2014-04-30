package com.example.zero1.db;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zero1.Station;
import com.example.zero1.Utility;

public class DBmanager {

	private StationDBOpenHelper helper;
	private SQLiteDatabase writabledb;
	private SQLiteDatabase readabledb;
	Context context;
	public DBmanager(Context context) {
		// TODO Auto-generated constructor st ub
		this.context=context;
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
	public boolean deleteData(String tablename){
		writabledb.delete(tablename, null, null);
		return false;	
	}
	public boolean isNeedUpdateStation(){
		Date d = new Date();
		long i=d.getTime();
		Cursor c=readabledb.query("stationupdateinfo", new String[]{"last_update_time","station_count"}, null, null, null, null, null);
		while(c.moveToNext()){
			long lastupdate=c.getLong(c.getColumnIndex("last_update_time"));
			int stationcount=c.getInt(c.getColumnIndex("station_count"));
			if((i-lastupdate)>2592000000L||stationcount==0){
				return true;
			}			
		}
		c.close();
		return false;
	}
	public void updateStationTableInfo(int count){
		ContentValues val=new ContentValues();
		Date d = new Date();
		long i=d.getTime();
		val.put("last_update_time", i);
		val.put("station_count",count);
		writabledb.update("stationupdateinfo", val, null, null);
	}
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
		this.writabledb.close();
		this.readabledb.close();
	}
	public void addRecentlyStation(Station station){
		Cursor c=readabledb.rawQuery("select count(*) as num from recentlystation", null);
		int num;
		c.moveToNext();
		num=c.getInt(c.getColumnIndex("num"));
		c.close();
		if(num<StationDBOpenHelper.RECENTSTATIONNUM){
			writabledb.execSQL("INSERT INTO recentlystation VALUES(null,?, ?, ?, ?, ?,?)", 
					new Object[]{station.station_thr_code,station.station_name_ch,station.station_code,station.station_name_pingyin,station.station_pingyin_shou,new Date().getTime()});  
		}else{
			ContentValues val=new ContentValues();
			Date d = new Date();
			long i=d.getTime();
			val.put("time", i);
//			station_thr_code VARCHAR, station_name_ch TEXT, station_code VARCHAR, station_name_pingyin VARCHAR, station_pingyin_shou VARCHAR, time INTEGER
			val.put("station_thr_code", station.station_thr_code);
			val.put("station_name_ch", station.station_name_ch);
			val.put("station_code",station.station_code);
			val.put("station_name_pingyin", station.station_name_pingyin);
			val.put("station_pingyin_shou",station.station_pingyin_shou);
			Cursor c1=readabledb.rawQuery("select MIN(time) as mintime from recentlystation", null);
			c1.moveToNext();
			long l=c1.getLong(c1.getColumnIndex("mintime"));
			writabledb.update("recentlystation", val, "time='"+String.valueOf(l)+"'", null);
			c1.close();
		}
    	
	}
	public ArrayList<Station> getRecentlyStation(){
		ArrayList<Station> stations=new ArrayList<Station>();
		Cursor cur=readabledb.query("recentlystation", null, null, null, null, null, null);
		while(cur.moveToNext()){
//        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, station_thr_code VARCHAR, station_name_ch TEXT, station_code VARCHAR, station_name_pingyin VARCHAR, station_pingyin_shou VARCHAR)");  
			Station sta=new Station();
			sta.station_thr_code=cur.getString(cur.getColumnIndex("station_thr_code"));
			sta.station_name_ch=cur.getString(cur.getColumnIndex("station_name_ch"));
			sta.station_code=cur.getString(cur.getColumnIndex("station_code"));
			sta.station_name_pingyin=cur.getString(cur.getColumnIndex("station_name_pingyin"));
			sta.station_pingyin_shou=cur.getString(cur.getColumnIndex("station_pingyin_shou"));
			stations.add(sta);
			}
		cur.close();
		return stations;
	}
}
