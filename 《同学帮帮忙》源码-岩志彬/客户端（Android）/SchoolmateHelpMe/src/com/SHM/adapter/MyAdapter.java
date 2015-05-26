package com.SHM.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.SimpleAdapter;

public class MyAdapter extends SimpleAdapter {
	
	ArrayList<Map<String,Object>>  listItems;
	
	public MyAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}
	
	public void onDateChange(ArrayList<Map<String,Object>> listItems) {
		this.listItems= listItems;
		this.notifyDataSetChanged();
	}
	

}
