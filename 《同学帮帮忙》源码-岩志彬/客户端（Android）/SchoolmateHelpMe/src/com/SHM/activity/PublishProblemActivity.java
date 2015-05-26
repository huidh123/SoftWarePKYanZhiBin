package com.SHM.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.SHM.constant.Constant;
import com.SHM.tools.GetInformation;
import com.SHM.tools.HttpUtil;

public class PublishProblemActivity extends Activity {
	
	private EditText publish_title;
	private EditText publish_content;
	private EditText publish_points;
	private Button publish_back;
	private Button publish_publish;
	private Handler handler;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_publish_problem);
		
		initView();
		
		GetInformation.getInformation();
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch(msg.what){
					case 0:
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_LONG).show();
						PublishProblemActivity.this.finish();
						break;
					case 1:
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(), "网络错误，发布失败", Toast.LENGTH_LONG).show();
						break;
					case 2:
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(), "标题不能为空~", Toast.LENGTH_LONG).show();
						break;
					case 3:
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(), "内容不能为空~", Toast.LENGTH_LONG).show();
						break;
					case 4:
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(), "你没有那么多积分可以用了呦", Toast.LENGTH_LONG).show();
						break;
					default:
						break;
				}
			}
		};
		
		publish_back.setOnClickListener(backClick);
		publish_publish.setOnClickListener(publishClick);
	}

	private void initView() {
		// TODO Auto-generated method stub
		publish_title = (EditText) findViewById(R.id.publish_title);
		publish_content = (EditText) findViewById(R.id.publish_content);
		publish_points = (EditText) findViewById(R.id.publish_points);
		publish_back = (Button) findViewById(R.id.publish_back);
		publish_publish = (Button) findViewById(R.id.publish_publish);
	}
	
	OnClickListener backClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			new AlertDialog.Builder(PublishProblemActivity.this)
			.setMessage("问题还未发布成功，退出将会清空编辑的内容，是否继续退出？")
			.setPositiveButton("退出", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					PublishProblemActivity.this.finish();
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
	
	OnClickListener publishClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			new AlertDialog.Builder(PublishProblemActivity.this)
			.setMessage("已经编辑完成问题，确定发布？")
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
							
							String title = publish_title.getText().toString();
							String content = publish_content.getText().toString();
							String givedpoints = publish_points.getText().toString();
							if(title.equals("")){
								handler.sendEmptyMessage(2);
								return;
							}
							if(content.equals("")){
								handler.sendEmptyMessage(3);
								return;
							}
							if(givedpoints.equals("")){
								givedpoints = String.valueOf(0);
							}
							if(Integer.valueOf(givedpoints) > Constant.points){
								handler.sendEmptyMessage(4);
								return;
							}
							
							Constant.points = Constant.points - Integer.valueOf(givedpoints);
							
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String time = df.format(new Date());
							
							List<NameValuePair> param = new ArrayList<NameValuePair>();
							param.add(new BasicNameValuePair("title",title));
							param.add(new BasicNameValuePair("content",content));
							param.add(new BasicNameValuePair("username",Constant.username));
							param.add(new BasicNameValuePair("name",Constant.name));
							param.add(new BasicNameValuePair("sex",String.valueOf(Constant.sex)));
							param.add(new BasicNameValuePair("givedpoints",givedpoints));
							param.add(new BasicNameValuePair("picid",String.valueOf(Constant.picid)));
							param.add(new BasicNameValuePair("time",time));
							param.add(new BasicNameValuePair("points",String.valueOf(Constant.points)));
							
							String resp = null;
							resp = HttpUtil.sendPost(Constant.URL + "PublishProblemServlet", param,HTTP.UTF_8);
							
							if(resp == null){
								handler.sendEmptyMessage(1);
							}else if(resp.equals("success")){
								handler.sendEmptyMessage(0);
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
	
	private void createLoginProgressDialog(){
	    progressDialog = new ProgressDialog(PublishProblemActivity.this);
	    progressDialog.setMessage("请稍候...");
	    progressDialog.setCancelable(false);
	    progressDialog.show();
	  }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			new AlertDialog.Builder(PublishProblemActivity.this)
			.setMessage("问题还未发布成功，退出将会清空编辑的内容，是否继续退出？")
			.setPositiveButton("退出", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					PublishProblemActivity.this.finish();
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
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.publish_problem, menu);
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
