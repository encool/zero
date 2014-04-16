package com.example.zero1;

import java.util.ArrayList;
import java.util.List;

public class Utility {
	public static List<Station> splitStringToArray(String source,String mark1,String mark2){
		ArrayList<Station> stations = new ArrayList<Station>();
		String[] s=source.split(mark1);
		for(int i=1;i<s.length;i++){
			String s1[]=s[i].split(mark2);
			Station station=new Station(s1[0],s1[1],s1[2],s1[3],s1[4]);
			stations.add(station);
		}
		return stations;	
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
}
