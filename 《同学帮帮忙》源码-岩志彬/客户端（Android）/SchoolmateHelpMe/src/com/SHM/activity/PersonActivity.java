package com.SHM.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.SHM.adapter.ImageAdapter;
import com.SHM.constant.Constant;
import com.SHM.tools.GetInformation;
import com.SHM.tools.HttpUtil;

public class PersonActivity extends Activity {
	
	private ImageView person_pic;
	private TextView person_name;
	private ImageView person_sex;
	private TextView person_academe;
	private TextView person_profession;
	private EditText person_qqnumber;
	private EditText person_signature;
	private TextView person_points;
	private TextView person_id;
	private ImageView person_advice;
	private Button person_back;
	private Button person_update;
	private Handler handler;
	private ProgressDialog progressDialog;
	private int pic_changed = Constant.picid;
	private View view_before = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_person);
		
		initView();
		
		GetInformation.getInformation();
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					progressDialog.cancel();
					Toast.makeText(getApplicationContext(), "信息修改成功", Toast.LENGTH_LONG).show();
					GetInformation.getInformation();
					PersonActivity.this.finish();
					break;
				case 1:
					progressDialog.cancel();
					Toast.makeText(getApplicationContext(), "网络错误，请稍后再试", Toast.LENGTH_LONG).show();
					break;
				case 2:
					progressDialog.cancel();
					Toast.makeText(getApplicationContext(), "提交成功，感谢您宝贵的建议,我们将竭尽全力做得更好", Toast.LENGTH_LONG).show();
					break;
				case 3:
					progressDialog.cancel();
					new AlertDialog.Builder(PersonActivity.this)
						.setMessage("网络错误，提交失败")
						.setPositiveButton("返回", null)
						.create()
						.show();
					break;
				case 4:
					progressDialog.cancel();
					Toast.makeText(getApplicationContext(), "请填写建议", Toast.LENGTH_LONG).show();
					break;
				case 5:
					progressDialog.cancel();
					person_pic.setBackgroundResource(Constant.pics[Constant.picid]);
					Toast.makeText(getApplicationContext(), "头像修改成功", Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}
		};
		
		if(Constant.name.equals("")){
			Toast.makeText(getApplicationContext(), "网络错误，加载失败", Toast.LENGTH_LONG).show();
		}
		if(!(Constant.picid == -1)){
			person_pic.setBackgroundResource(Constant.pics[Constant.picid]);
			person_id.setText(Constant.username);
		}
		if(!Constant.name.equals("")){
			person_name.setText(Constant.name);
		}
		if(!(Constant.sex == -1)){
			person_sex.setBackgroundResource(Constant.sexs[Constant.sex]);
		}
		if(!Constant.academe.equals("")){
			person_academe.setText(Constant.academe);
		}
		if(!Constant.profession.equals("")){
			person_profession.setText(Constant.profession);
		}
		if(!Constant.qqnumber.equals("")){
			if(!Constant.qqnumber.equals("null")){
				person_qqnumber.setText(Constant.qqnumber);
			}
		}
		if(!Constant.signature.equals("")){
			if(!Constant.signature.equals("null")){
				person_signature.setText(Constant.signature);
			}
		}
		if(!(Constant.points == -1)){
			person_points.setText("" + Constant.points);
		}
		
		person_pic.setOnClickListener(picClick);
		person_advice.setOnClickListener(adviceClick);
		person_back.setOnClickListener(backClick);
		person_update.setOnClickListener(updateClick);
	}

	private void initView() {
		// TODO Auto-generated method stub
		person_pic = (ImageView) findViewById(R.id.person_pic);
		person_name = (TextView) findViewById(R.id.person_name);
		person_sex = (ImageView) findViewById(R.id.person_sex);
		person_academe = (TextView) findViewById(R.id.person_academe);
		person_profession = (TextView) findViewById(R.id.person_profession);
		person_qqnumber = (EditText) findViewById(R.id.person_qqnumber);
		person_signature = (EditText) findViewById(R.id.person_signature);
		person_points = (TextView) findViewById(R.id.person_points);
		person_id = (TextView) findViewById(R.id.person_id);
		person_advice = (ImageView) findViewById(R.id.person_advice);
		person_back = (Button) findViewById(R.id.person_back);
		person_update = (Button) findViewById(R.id.person_update);
	}
	
	OnClickListener picClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final AlertDialog dig = new AlertDialog.Builder(PersonActivity.this).create();
			dig.show();
			
			final Window window = dig.getWindow();
			window.setContentView(R.layout.pic_choose_alert);
			
			window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); 
			window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); 
			
			Button bt_pic_choose = (Button) window.findViewById(R.id.bt_alert_pic_choose);
			GridView gridView=(GridView) window.findViewById(R.id.gridview);
			
			gridView.setAdapter(new ImageAdapter(PersonActivity.this)); 
			
			gridView.setOnItemClickListener(new OnItemClickListener() {
				  @Override  
				  public void onItemClick(AdapterView<?> parent, View view,  
						  int position, long id) {
					  if(view_before != null){
						  view_before.setBackgroundResource(Constant.pics[pic_changed]);
					  }
					  view.setBackgroundResource(Constant.pics_selected[position]);
					  pic_changed = position;
					  view_before = view;
				  }  
			});
			
			bt_pic_choose.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					if(pic_changed == Constant.picid){
						dig.dismiss();
						return;
					}else{
						createLoginProgressDialog();
						
						new Thread(){
							@Override
							public void run() {
								super.run();
								
								List<NameValuePair> param = new ArrayList<NameValuePair>();
								param.add(new BasicNameValuePair("username", Constant.username));
								param.add(new BasicNameValuePair("pic_changed", String.valueOf(pic_changed)));
								
								String resp = null;
								resp = HttpUtil.sendPost(Constant.URL + "PicChangedServlet", param, HTTP.UTF_8);
								
								if(resp == null){
									handler.sendEmptyMessage(1);
								}else if(resp.equals("success")){
									Constant.picid = pic_changed;
									handler.sendEmptyMessage(5);
								}else{
									handler.sendEmptyMessage(1);
								}
							};
						}.start();
						dig.dismiss();
					}
				}
			});
		}
	};
	
	OnClickListener adviceClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final AlertDialog dig = new AlertDialog.Builder(PersonActivity.this).create();
			dig.show();
			
			final Window window = dig.getWindow();
			window.setContentView(R.layout.advice_alert);
			
			window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); 
			window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); 
			
			final EditText advice_et =(EditText) window.findViewById(R.id.advice_alert_et);
			Button bt_advice = (Button) window.findViewById(R.id.bt_alert_advice);
			
			
			bt_advice.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					((InputMethodManager)PersonActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).
				    hideSoftInputFromWindow(window.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
					
					createLoginProgressDialog();
					new Thread(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							super.run();
							
							String advice = advice_et.getText().toString();
							
							if(advice.equals("")){
								handler.sendEmptyMessage(4);
								return;
							}
							
							List<NameValuePair> param = new ArrayList<NameValuePair>();
							param.add(new BasicNameValuePair("username",Constant.username));
							param.add(new BasicNameValuePair("advice",advice));
							
							String resp = "";
							resp = HttpUtil.sendPost(Constant.URL + "AdviceServlet", param,HTTP.UTF_8);
							if(resp == null){
								handler.sendEmptyMessage(3);
							}else if(resp.equals("")){
								handler.sendEmptyMessage(3);
							}else if(resp.equals("success")){
								handler.sendEmptyMessage(2);
							}else{
								handler.sendEmptyMessage(3);
							}
						}
					}.start();
					dig.dismiss();
				}
			});
		}
	};
	
	OnClickListener backClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String qqnumber = person_qqnumber.getText().toString();
			String signature = person_signature.getText().toString();
			if(Constant.signature.equals("null")){
				if(signature.equals("")){
					signature = "null";
				}
			}
			if(qqnumber.equals(Constant.qqnumber) && signature.equals(Constant.signature)){
				PersonActivity.this.finish();
			}else{
				new AlertDialog.Builder(PersonActivity.this)
				.setMessage("更改的内容还未保存，退出将清空编辑的内容，是否继续退出？")
				.setPositiveButton("退出", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						PersonActivity.this.finish();
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
		}
	};
	
	OnClickListener updateClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new AlertDialog.Builder(PersonActivity.this)
			.setMessage("确定修改信息么？")
			.setPositiveButton("修改", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					createLoginProgressDialog();
					new Thread(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							super.run();
							
							String qqnumber = person_qqnumber.getText().toString();
							String signature = person_signature.getText().toString();
							
							List<NameValuePair> param = new ArrayList<NameValuePair>();
							param.add(new BasicNameValuePair("username",Constant.username));
							param.add(new BasicNameValuePair("qqnumber",qqnumber));
							param.add(new BasicNameValuePair("signature",signature));
							
							String resp = null;
							resp = HttpUtil.sendPost(Constant.URL + "UpdateInformationServlet", param,HTTP.UTF_8);
							
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			String qqnumber = person_qqnumber.getText().toString();
			String signature = person_signature.getText().toString();
			if(Constant.signature.equals("null")){
				if(signature.equals("")){
					signature = "null";
				}
			}
			if(qqnumber.equals(Constant.qqnumber) && signature.equals(Constant.signature)){
				PersonActivity.this.finish();
			}else{
				new AlertDialog.Builder(PersonActivity.this)
				.setMessage("更改的内容还未保存，退出将清空编辑的内容，是否继续退出？")
				.setPositiveButton("退出", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						PersonActivity.this.finish();
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
		}
		return true;
	}
	
	private void createLoginProgressDialog(){
	    progressDialog = new ProgressDialog(PersonActivity.this);
	    progressDialog.setMessage("请稍候...");
	    progressDialog.setCancelable(false);
	    progressDialog.show();
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.person, menu);
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
