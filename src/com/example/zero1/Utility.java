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
}
