package com.SHM.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.SHM.constant.Constant;
import com.SHM.tools.HttpUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MySolveProblemShowActivity extends Activity {
	
	private ImageView my_solve_problem_show_picid;
	private ImageView my_solve_problem_show_sex;
	private TextView my_solve_problem_show_name;
	private TextView my_solve_problem_show_id;
	private TextView my_solve_problem_show_time;
	private TextView my_solve_problem_show_givedpoints;
	private TextView my_solve_problem_show_title;
	private TextView my_solve_problem_show_content;
	
	private int problemid;
	private ProgressDialog progressDialog;
	private static String isSucceed;
	private static String resp_success;
	private String username_show_information;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_solve_problem_show);
		
		Bundle bundle = this.getIntent().getExtras();
		int get_problemid = bundle.getInt("problemid");
		problemid = get_problemid;
		String username = bundle.getString("username");
		username_show_information = username;
		String title = bundle.getString("title");
		String content = bundle.getString("content");
		String name = bundle.getString("name");
		int sex = bundle.getInt("sex");
		int givedpoints = bundle.getInt("givedpoints");
		int picid = bundle.getInt("picid");
		String time = bundle.getString("time");
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch(msg.what){
					case 0:
						progressDialog.cancel();
						
						String[] resp_success_after = resp_success.split("9%!0#0");
						String name_show_information = resp_success_after[2];
						int picid_show_information = Integer.valueOf(resp_success_after[3]);
						String signature_show_information = resp_success_after[4];
						String academe_show_information = resp_success_after[5];
						String profession_show_information = resp_success_after[6];
						int sex_show_information = Integer.valueOf(resp_success_after[7]);
						String qqnumber_show_information = resp_success_after[8];
						int points_show_information = Integer.valueOf(resp_success_after[9]);
						
						Intent intent_show_information = new Intent(MySolveProblemShowActivity.this,OtherPeopleInformationShowActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("username",username_show_information);
						bundle.putString("name", name_show_information);
						bundle.putInt("picid", picid_show_information);
						bundle.putString("signature", signature_show_information);
						bundle.putString("academe", academe_show_information);
						bundle.putString("profession", profession_show_information);
						bundle.putInt("sex", sex_show_information);
						bundle.putString("qqnumber", qqnumber_show_information);
						bundle.putInt("points", points_show_information);
						intent_show_information.putExtras(bundle);
						startActivity(intent_show_information);
						break;
					case 1:
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(), "网络错误，加载失败", Toast.LENGTH_LONG).show();
						break;
					default:
						break;
				}
			}
		};
		
		initView();
		
		my_solve_problem_show_picid.setImageResource(Constant.pics[picid]);
		my_solve_problem_show_sex.setImageResource(Constant.sexs[sex]);
		my_solve_problem_show_name.setText(name);
		my_solve_problem_show_time.setText(time);
		my_solve_problem_show_givedpoints.setText("" + givedpoints);
		my_solve_problem_show_title.setText(title);
		my_solve_problem_show_content.setText(content);
		my_solve_problem_show_id.setText(username);
		my_solve_problem_show_name.setOnClickListener(nameClick);
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		my_solve_problem_show_picid = (ImageView) findViewById(R.id.my_solve_problem_show_picid);
		my_solve_problem_show_sex = (ImageView) findViewById(R.id.my_solve_problem_show_sex);
		my_solve_problem_show_name = (TextView) findViewById(R.id.my_solve_problem_show_name);
		my_solve_problem_show_time = (TextView) findViewById(R.id.my_solve_problem_show_time);
		my_solve_problem_show_givedpoints = (TextView) findViewById(R.id.my_solve_problem_show_givedpoints);
		my_solve_problem_show_title = (TextView) findViewById(R.id.my_solve_problem_show_title);
		my_solve_problem_show_content = (TextView) findViewById(R.id.my_solve_problem_show_content);
		my_solve_problem_show_content.setMovementMethod(ScrollingMovementMethod.getInstance());
		my_solve_problem_show_id = (TextView) findViewById(R.id.my_solve_problem_show_id);
	}
	
	OnClickListener nameClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			createLoginProgressDialog();
			new Thread(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					
					List<NameValuePair> param = new ArrayList<NameValuePair>();
					param.add(new BasicNameValuePair("username",username_show_information));
					String resp = null;                               
					resp = HttpUtil.sendPost(Constant.URL + "GetInformationServlet", param,HTTP.UTF_8);
					if(resp == null){
						handler.sendEmptyMessage(1);
					}else if(resp.length() < 7){
						handler.sendEmptyMessage(1);
					}else{
						isSucceed = resp.substring(0,7);
						if(isSucceed.equals("success")){
							resp_success = resp;
							handler.sendEmptyMessage(0);
						}else{
							handler.sendEmptyMessage(1);
						}
					}
				}
			}.start();
		}
	};
	
	private void createLoginProgressDialog(){
	    progressDialog = new ProgressDialog(MySolveProblemShowActivity.this);
	    progressDialog.setMessage("请稍候...");
	    progressDialog.setCancelable(false);
	    progressDialog.show();
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_solve_problem_show, menu);
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
