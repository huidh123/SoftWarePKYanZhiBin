package com.SHM.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private DBHelper helper;
	private SQLiteDatabase db;
	
	public DBManager(Context context) {
		// TODO Auto-generated constructor stub
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}
	
	public String get(){
		Cursor c = db.rawQuery("SELECT username,password FROM users where flag = 'savepasswordflag'", null);
		if(c.moveToNext()){
			String username = c.getString(0);
			String password = c.getString(1);
			return username + "9%!0#0" + password;
		}else{
			return "";
		}
	}
	
	public void save(String username, String password){
		db.delete("users", "flag=?", new String[]{"savepasswordflag"});
		
		ContentValues values = new ContentValues();
		values.put("username", "");
		values.put("password", " ");
		values.put("flag", "savepasswordflag");
		db.insert("users", "", values);
		
		ContentValues cv = new ContentValues();
		cv.put("username", username);
		cv.put("password", password);
		db.update("users", cv, "flag=?", new String[]{"savepasswordflag"});
	}
	
	public void closeDB(){
		db.close();
	}
	
	

}
