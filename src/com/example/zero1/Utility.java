package com.example.zero1;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Utility {
	public static List<Station> splitStringToArray(String source,String mark1,String mark2){
		ArrayList<Station> stations = new ArrayList<Station>();
		if(source!=null){
			String[] s=source.split(mark1);
			for(int i=1;i<s.length;i++){
				String s1[]=s[i].split(mark2);
				Station station=new Station(s1[0],s1[1],s1[2],s1[3],s1[4]);
				stations.add(station);
			}
			return stations;
		}
		return null;	
	}
	public static String prepareString(String s){
		StringBuilder sb=new StringBuilder();
		boolean lastiseng = false;
//		int i=0;
//		char c=s.charAt(i);
//		Character.UnicodeBlock cub = Character.UnicodeBlock.of(c); 
//		if(cub != Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS){
//			for(i=1;i<s.length();i++){
//				c=s.charAt(i);
//				if(){
//					
//				}
//				sb.append(c);
//			}
//			sb.append(c);
//		}
		for(int i=0;i<s.length();i++){
			char c=s.charAt(i);
			Character.UnicodeBlock cub = Character.UnicodeBlock.of(c); 
			if(cub==Character.UnicodeBlock.BASIC_LATIN){
				if(i==0||lastiseng==true){
					sb.append(c);
					lastiseng=true;
				}else{
					break;
				}
			}else if(cub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
			{
				if(i==0||lastiseng==false){
					sb.append(c);
					lastiseng=false;
				}else{
					break;
				}	
			}
		}
			
		

		return new String(sb);		
	}
	public static ArrayList<TrainInfoHoder> ParseJsonToArray(String s){
		JSONTokener jsonparser=new JSONTokener(s);
		ArrayList<TrainInfoHoder> array=new ArrayList<TrainInfoHoder>();
		try {
			JSONObject traininfo=(JSONObject) jsonparser.nextValue();
			String validateMessagesShowId=traininfo.getString("validateMessagesShowId");
			String status=traininfo.getString("status");
			JSONArray data=traininfo.getJSONArray("data");
			if(status.equals(new String("true"))){
				for(int i=0;i<data.length();i++){
					TrainInfoHoder infohoder=new TrainInfoHoder();
					JSONObject object=data.getJSONObject(i);
					infohoder.secretStr=object.getString("secretStr"); 
					JSONObject jsonob=object.getJSONObject("queryLeftNewDTO");
					infohoder.arrive_time=jsonob.getString("arrive_time");
					infohoder.end_starion_code=jsonob.getString("end_station_telecode");
					infohoder.end_station_name_ch=jsonob.getString("end_station_name");
					infohoder.from_station_code=jsonob.getString("from_station_telecode");
					infohoder.lishi=jsonob.getString("lishi");
					infohoder.rw_num=jsonob.getString("rw_num");
					infohoder.start_station_code=jsonob.getString("start_station_telecode");
					infohoder.start_station_name_ch=jsonob.getString("start_station_name");
					infohoder.start_time=jsonob.getString("start_time");
					infohoder.station_train_code=jsonob.getString("station_train_code");
					infohoder.start_station_name_ch=jsonob.getString("start_station_name");
					infohoder.to_station_code=jsonob.getString("to_station_telecode");
					array.add(infohoder);
					
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return array;
	}
}
