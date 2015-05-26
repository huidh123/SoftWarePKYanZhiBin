package com.SHM.constant;

import com.SHM.activity.R;

public class Constant {
	public final static String URL = "http://202.102.144.50:8080/SHMS/";
	public static int id;
	public static String username;
	public static String name;
	public static int picid;
	public static String signature;
	public static String academe;
	public static String profession;
	public static int sex;
	public static String qqnumber;
	public static int points;
	
	public static Integer[] sexs = {  
		R.drawable.male, R.drawable.female,
	}; 
	
	public static Integer[] pics = {  
		R.drawable.head1, R.drawable.head2,  
	    R.drawable.head3, R.drawable.head4,
		R.drawable.head5, R.drawable.head6,  
	};  
	
	public static Integer[] pics_selected = {  
		R.drawable.head1_selected, R.drawable.head2_selected,  
	    R.drawable.head3_selected, R.drawable.head4_selected,  
		R.drawable.head5_selected, R.drawable.head6_selected,  
	};  
}
