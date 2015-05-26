package com.SHM.activity;

import com.SHM.tools.DBManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

public class SplashActivity extends Activity {
	
	private final int SPLASH_DISPLAY_LENGHT = 2000;
	private DBManager mgr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		mgr = new DBManager(this);
		
		new Handler().postDelayed(new Runnable() {  
			 public void run() {  
				 
				 String search = mgr.get();
				 
				 Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
				 Bundle bundle = new Bundle();
				 bundle.putString("search", search);
				 intent.putExtras(bundle);
				 SplashActivity.this.startActivity(intent); 
				 SplashActivity.this.overridePendingTransition(R.anim.enter_login, R.anim.out_splash);
				 SplashActivity.this.finish(); 
				 mgr.closeDB();
				 }
			 }, SPLASH_DISPLAY_LENGHT);  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
