package com.SHM.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CalendarContract.Colors;
import android.support.v7.appcompat.R.color;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SHM.constant.Constant;
import com.SHM.tools.HttpUtil;

public class MyPublishProblemShowActivity extends Activity {
	
	private ImageView my_publish_problem_show_picid;
	private ImageView my_publish_problem_show_sex;
	private TextView my_publish_problem_show_name;
	private TextView my_publish_problem_show_time;
	private TextView my_publish_problem_show_givedpoints;
	private TextView my_publish_problem_show_title;
	private TextView my_publish_problem_show_content;
	private Button my_publish_problem_show_delete;
	private Button my_publish_problem_show_solved;
	private TextView my_publish_problem_show_helper;
	
	private Handler handler;
	private ProgressDialog progressDialog;
	private int problemid;
	private static String isSucceed;
	private static String resp_success;
	private String username_show_information;
	private int givedpoints_solved;
	private String IsSolved_solved;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_publish_problem_show);
		
		Bundle bundle = this.getIntent().getExtras();
		int get_problemid = bundle.getInt("problemid");
		problemid = get_problemid;
		String username = bundle.getString("username");
		String title = bundle.getString("title");
		String content = bundle.getString("content");
		String name = bundle.getString("name");
		int sex = bundle.getInt("sex");
		int givedpoints = bundle.getInt("givedpoints");
		givedpoints_solved = givedpoints;
		int picid = bundle.getInt("picid");
		String time = bundle.getString("time");
		String IsSolved = bundle.getString("IsSolved");
		IsSolved_solved = IsSolved;
		
		initView();
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch(msg.what){
					case 0:
						progressDialog.cancel();
						
						MyPublishProblemShowActivity.this.finish();
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
						
						Intent intent_show_information = new Intent(MyPublishProblemShowActivity.this,OtherPeopleInformationShowActivity.class);
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
					case 4:
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(), "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
						break;
					case 5:
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(), "问题成功解决，对应的积分已经转给帮助你的小伙伴了~", Toast.LENGTH_LONG).show();
						MyPublishProblemShowActivity.this.finish();
						break;
					case 6:
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(), "问题成功删除，对应的积分已经返还到您的积分中~", Toast.LENGTH_LONG).show();
						MyPublishProblemShowActivity.this.finish();
						break;
					default:
						break;
				}
			}
		};
		
		my_publish_problem_show_picid.setImageResource(Constant.pics[picid]);
		my_publish_problem_show_sex.setImageResource(Constant.sexs[sex]);
		my_publish_problem_show_name.setText(name);
		my_publish_problem_show_time.setText(time);
		my_publish_problem_show_givedpoints.setText("" + givedpoints);
		my_publish_problem_show_title.setText(title);
		my_publish_problem_show_content.setText(content);
		if(IsSolved.equals("donothave")){
			my_publish_problem_show_helper.setText("暂时还没有人帮你解决这个问题呦~");
		}else{
			String[] IsSolved_true = IsSolved.split("42!!2!0");
			my_publish_problem_show_helper.setTextColor(Color.rgb(31, 119, 16));
			my_publish_problem_show_helper.setText(IsSolved_true[0] + "  " + IsSolved_true[1]);
			username_show_information = IsSolved_true[1];
			my_publish_problem_show_helper.setOnClickListener(new OnClickListener() {
				
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
			});
		}
		my_publish_problem_show_delete.setOnClickListener(deleteClick);
		my_publish_problem_show_solved.setOnClickListener(solvedClick);
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		my_publish_problem_show_picid = (ImageView) findViewById(R.id.my_publish_problem_show_picid);
		my_publish_problem_show_sex = (ImageView) findViewById(R.id.my_publish_problem_show_sex);
		my_publish_problem_show_name = (TextView) findViewById(R.id.my_publish_problem_show_name);
		my_publish_problem_show_time = (TextView) findViewById(R.id.my_publish_problem_show_time);
		my_publish_problem_show_givedpoints = (TextView) findViewById(R.id.my_publish_problem_show_givedpoints);
		my_publish_problem_show_title = (TextView) findViewById(R.id.my_publish_problem_show_title);
		my_publish_problem_show_content = (TextView) findViewById(R.id.my_publish_problem_show_content);
		my_publish_problem_show_content.setMovementMethod(ScrollingMovementMethod.getInstance());
		my_publish_problem_show_delete = (Button) findViewById(R.id.my_publish_problem_show_delete);
		my_publish_problem_show_solved = (Button) findViewById(R.id.my_publish_problem_show_solved);
		my_publish_problem_show_helper = (TextView) findViewById(R.id.my_publish_problem_show_helper);
	}
	
	OnClickListener deleteClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			new AlertDialog.Builder(MyPublishProblemShowActivity.this)
				.setMessage("您已经自己解决该问题，或者帮助您的小伙伴无法帮您正确解决问题，可以通过点击下面的确定按钮将该问题删除")
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
								param.add(new BasicNameValuePair("username",Constant.username));
								param.add(new BasicNameValuePair("givedpoints",String.valueOf(givedpoints_solved)));
								param.add(new BasicNameValuePair("id_deleted",String.valueOf(problemid)));
								param.add(new BasicNameValuePair("IsSolved_solved",IsSolved_solved));
								
								String resp = null;                               
								resp = HttpUtil.sendPost(Constant.URL + "ProblemsDeleteServlet", param,HTTP.UTF_8);
								if(resp == null){
									handler.sendEmptyMessage(4);
								}else if(resp.length() < 7){
									handler.sendEmptyMessage(4);
								}else if(resp.equals("success")){
									handler.sendEmptyMessage(6);
								}else{
									handler.sendEmptyMessage(4);
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
				}).create().show();
		}
	};
	
	OnClickListener solvedClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(IsSolved_solved.equals("donothave")){
				Toast.makeText(getApplicationContext(), "还没有人帮你解决这个问题呦~", Toast.LENGTH_LONG).show();
			}else{
				new AlertDialog.Builder(MyPublishProblemShowActivity.this)
				.setMessage("该问题确实得到解决了么~点击确定按钮将会把对应的积分转给帮助你的小伙伴呦")
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
								param.add(new BasicNameValuePair("username",username_show_information));
								param.add(new BasicNameValuePair("givedpoints",String.valueOf(givedpoints_solved)));
								param.add(new BasicNameValuePair("id_solved",String.valueOf(problemid)));
								
								String resp = null;                               
								resp = HttpUtil.sendPost(Constant.URL + "ProblemsSolvedServlet", param,HTTP.UTF_8);
								if(resp == null){
									handler.sendEmptyMessage(4);
								}else if(resp.length() < 7){
									handler.sendEmptyMessage(4);
								}else if(resp.equals("success")){
									handler.sendEmptyMessage(5);
								}else{
									handler.sendEmptyMessage(4);
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
				}).create().show();
			}
		}
	};
	
	private void createLoginProgressDialog(){
	    progressDialog = new ProgressDialog(MyPublishProblemShowActivity.this);
	    progressDialog.setMessage("请稍候...");
	    progressDialog.setCancelable(false);
	    progressDialog.show();
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_publish_problem_show, menu);
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
