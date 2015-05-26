package com.SHM.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.SHM.constant.Constant;
import com.SHM.tools.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.AlarmClock;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ProblemShowActivity extends Activity {
	
	private ImageView problem_show_picid;
	private ImageView problem_show_sex;
	private TextView problem_show_name;
	private TextView problem_show_id;
	private TextView problem_show_time;
	private TextView problem_show_givedpoints;
	private TextView problem_show_title;
	private TextView problem_show_content;
	private Button problem_show_back;
	private Button problem_show_reply;
	
	private Handler handler;
	private ProgressDialog progressDialog;
	private int problemid;
	private static String isSucceed;
	private static String resp_success;
	private String username_show_information;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_problem_show);
		
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
		
		initView();
		
		problem_show_picid.setImageResource(Constant.pics[picid]);
		problem_show_sex.setImageResource(Constant.sexs[sex]);
		problem_show_name.setText(name);
		problem_show_time.setText(time);
		problem_show_givedpoints.setText("" + givedpoints);
		problem_show_title.setText(title);
		problem_show_content.setText(content);
		problem_show_id.setText(username);
		problem_show_back.setOnClickListener(backClick);
		problem_show_reply.setOnClickListener(replyClick);
		problem_show_name.setOnClickListener(nameClick);
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch(msg.what){
					case 0:
						progressDialog.cancel();
						ProblemShowActivity.this.finish();
						break;
					case 1:
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(), "网络错误，加载失败", Toast.LENGTH_LONG).show();
						break;
					case 2:
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(), "该问题已经被其他小伙伴解决了呦", Toast.LENGTH_LONG).show();
						break;
					case 3:
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
						
						Intent intent_show_information = new Intent(ProblemShowActivity.this,OtherPeopleInformationShowActivity.class);
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
					default:
						break;
				}
			}
		};
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		problem_show_picid = (ImageView) findViewById(R.id.problem_show_picid);
		problem_show_sex = (ImageView) findViewById(R.id.problem_show_sex);
		problem_show_name = (TextView) findViewById(R.id.problem_show_name);
		problem_show_time = (TextView) findViewById(R.id.problem_show_time);
		problem_show_givedpoints = (TextView) findViewById(R.id.problem_show_givedpoints);
		problem_show_title = (TextView) findViewById(R.id.problem_show_title);
		problem_show_content = (TextView) findViewById(R.id.problem_show_content);
		problem_show_content.setMovementMethod(ScrollingMovementMethod.getInstance());
		problem_show_back = (Button) findViewById(R.id.problem_show_back);
		problem_show_reply = (Button) findViewById(R.id.problem_show_reply);
		problem_show_id = (TextView) findViewById(R.id.problem_show_id);
	}
	
	OnClickListener backClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ProblemShowActivity.this.finish();
		}
	};
	
	OnClickListener replyClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new AlertDialog.Builder(ProblemShowActivity.this)
				.setMessage("确定要帮助小伙伴解决该问题么")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						createLoginProgressDialog();
						new Thread(){
							@Override
							public void run() {
								// TODO Auto-generated method stub
								super.run();
								
								List<NameValuePair> param = new ArrayList<NameValuePair>();
								param.add(new BasicNameValuePair("problemid",String.valueOf(problemid)));
								param.add(new BasicNameValuePair("solve_username",Constant.username));
								param.add(new BasicNameValuePair("solve_name",Constant.name));
								
								String resp = null;
								resp = HttpUtil.sendPost(Constant.URL + "IsSolvedServlet", param,HTTP.UTF_8);
								
								if(resp == null){
									handler.sendEmptyMessage(1);
								}else if(resp.equals("success")){
									handler.sendEmptyMessage(0);
								}else if(resp.equals("fail")){
									handler.sendEmptyMessage(2);
								}else{
									handler.sendEmptyMessage(1);
								}
							}
						}.start();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				})
				.create().show();
		}
	};
	
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
							handler.sendEmptyMessage(3);
						}else{
							handler.sendEmptyMessage(1);
						}
					}
				}
			}.start();
		}
	};
	
	private void createLoginProgressDialog(){
	    progressDialog = new ProgressDialog(ProblemShowActivity.this);
	    progressDialog.setMessage("请稍候...");
	    progressDialog.setCancelable(false);
	    progressDialog.show();
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.problem_show, menu);
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
