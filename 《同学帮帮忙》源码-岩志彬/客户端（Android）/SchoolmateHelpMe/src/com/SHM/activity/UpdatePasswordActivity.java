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
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.NumberKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePasswordActivity extends Activity {
	
	private EditText update_password_username;
	private EditText update_password_current_password;
	private EditText update_password_new_password;
	private EditText update_password_sure_new_password;
	private Button bt_back;
	private Button bt_update_password;
	private Handler handler;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_update_password);
		
		initView();
		
		update_password_current_password.setKeyListener(new NumberKeyListener() {
			
			@Override
			public int getInputType() {
				// TODO Auto-generated method stub
				return android.text.InputType.TYPE_CLASS_TEXT;
			}
			
			@Override
			protected char[] getAcceptedChars() {
				// TODO Auto-generated method stub
				char[] numberChars = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
						'n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C',
						'D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S',
						'T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8',
						'9','.','_','!','?','#','@','$','%','^','&','*','(',')','~',':'};
				return numberChars;
			}
		});
		
		update_password_new_password.setKeyListener(new NumberKeyListener() {
			
			@Override
			public int getInputType() {
				// TODO Auto-generated method stub
				return android.text.InputType.TYPE_CLASS_TEXT;
			}
			
			@Override
			protected char[] getAcceptedChars() {
				// TODO Auto-generated method stub
				char[] numberChars = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
						'n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C',
						'D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S',
						'T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8',
						'9','.','_','!','?','#','@','$','%','^','&','*','(',')','~',':'};
				return numberChars;
			}
		});
		
		update_password_sure_new_password.setKeyListener(new NumberKeyListener() {
			
			@Override
			public int getInputType() {
				// TODO Auto-generated method stub
				return android.text.InputType.TYPE_CLASS_TEXT;
			}
			
			@Override
			protected char[] getAcceptedChars() {
				// TODO Auto-generated method stub
				char[] numberChars = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
						'n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C',
						'D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S',
						'T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8',
						'9','.','_','!','?','#','@','$','%','^','&','*','(',')','~',':'};
				return numberChars;
			}
		});
		
		bt_back.setOnClickListener(backClick);
		bt_update_password.setOnClickListener(updateClick);
		
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					progressDialog.cancel();
					Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();
					UpdatePasswordActivity.this.finish(); 
					break;
				case 1:
					progressDialog.cancel();
					new AlertDialog.Builder(UpdatePasswordActivity.this)
						.setMessage("请输入账号")
						.setPositiveButton("返回", null)
						.create()
						.show();
					break;
				case 2:
					progressDialog.cancel();
					new AlertDialog.Builder(UpdatePasswordActivity.this)
						.setMessage("请输入当前密码")
						.setPositiveButton("返回", null)
						.create()
						.show();
					break;
				case 3:
					progressDialog.cancel();
					new AlertDialog.Builder(UpdatePasswordActivity.this)
						.setMessage("请输入新密码")
						.setPositiveButton("返回", null)
						.create()
						.show();
					break;
				case 4:
					progressDialog.cancel();
					new AlertDialog.Builder(UpdatePasswordActivity.this)
						.setMessage("请确认新密码")
						.setPositiveButton("返回", null)
						.create()
						.show();
					break;
				case 5:
					progressDialog.cancel();
					new AlertDialog.Builder(UpdatePasswordActivity.this)
						.setMessage("两次输入的新密码不一致，请重新输入")
						.setPositiveButton("返回", null)
						.create()
						.show();
					update_password_new_password.setText("");
					update_password_sure_new_password.setText("");
					break;
				case 6:
					progressDialog.cancel();
					new AlertDialog.Builder(UpdatePasswordActivity.this)
						.setMessage("服务器维护中，给您带来不便请见谅")
						.setPositiveButton("退出", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								System.exit(0);
							}
						}).create().show();
					break;
				case 7:
					progressDialog.cancel();
					new AlertDialog.Builder(UpdatePasswordActivity.this)
						.setMessage("原始账号或密码错误")
						.setPositiveButton("返回", null)
						.create()
						.show();
					update_password_current_password.setText("");
					update_password_new_password.setText("");
					update_password_sure_new_password.setText("");
					break;
				case 8:
					progressDialog.cancel();
					new AlertDialog.Builder(UpdatePasswordActivity.this)
						.setMessage("网络错误，请稍后再试")
						.setPositiveButton("返回", null)
						.create()
						.show();
					break;
				default:
					break;
				}
			}
		};
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		update_password_username = (EditText) findViewById(R.id.update_password_username);
		update_password_current_password = (EditText) findViewById(R.id.update_password_current_password);
		update_password_new_password = (EditText) findViewById(R.id.update_password_new_password);
		update_password_sure_new_password = (EditText) findViewById(R.id.update_password_sure_new_password);
		bt_back = (Button) findViewById(R.id.update_password_back);
		bt_update_password = (Button) findViewById(R.id.update_password_update);
	}
	
	OnClickListener backClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			UpdatePasswordActivity.this.finish();
		}
	};
	
	OnClickListener updateClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			boolean flag = checkNetworkState();
			
			if(flag == true){
				
				createLoginProgressDialog();
				
				new Thread(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						
						String username = update_password_username.getText().toString();
						String currentPassword = update_password_current_password.getText().toString();
						String newPassword = update_password_new_password.getText().toString();
						String sureNewPassword = update_password_sure_new_password.getText().toString();
						
						if(username.equals("")){
							handler.sendEmptyMessage(1);
							return;
						}else if(currentPassword.equals("")){
							handler.sendEmptyMessage(2);
							return;
						}else if(newPassword.equals("")){
							handler.sendEmptyMessage(3);
							return;
						}else if(sureNewPassword.equals("")){
							handler.sendEmptyMessage(4);
							return;
						}
						if(!newPassword.equals(sureNewPassword)){
							handler.sendEmptyMessage(5);
							return;
						}
						
						List<NameValuePair> param = new ArrayList<NameValuePair>();
						param.add(new BasicNameValuePair("username",username));
						param.add(new BasicNameValuePair("currentPassword",currentPassword));
						param.add(new BasicNameValuePair("newPassword",newPassword));
						String resp = null;
						resp = HttpUtil.sendPost(Constant.URL + "UpdatePasswordServlet", param,HTTP.UTF_8);
						if(resp == null){
							handler.sendEmptyMessage(6);
						}else if(resp.equals("success")){
							handler.sendEmptyMessage(0);
						}else if(resp.equals("fail")){
							handler.sendEmptyMessage(7);
						}else{
							handler.sendEmptyMessage(8);
						}
					}
				}.start();
			}
		}
	};
	
	private void createLoginProgressDialog(){
	    progressDialog = new ProgressDialog(UpdatePasswordActivity.this);
	    progressDialog.setMessage("请稍候...");
	    progressDialog.setCancelable(false);
	    progressDialog.show();
	  }
	
	private boolean checkNetworkState() {
		boolean flag = false;
		//得到网络连接信息
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		//去进行判断网络是否连接
		if (manager.getActiveNetworkInfo() != null) {
			flag = manager.getActiveNetworkInfo().isAvailable();
		}
		if (!flag) {
			setNetwork();
		} 
		
		return flag;
	}
	
	private void setNetwork(){
		new AlertDialog.Builder(UpdatePasswordActivity.this)
			.setTitle("网络设置")
			.setMessage("当前无网络连接，请先设置后再登陆")
			.setPositiveButton("设置",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent = null;
					/**
					 * 判断手机系统的版本！如果API大于10 就是3.0+
					 * 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
					 */
					if (android.os.Build.VERSION.SDK_INT > 10) {
						intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
					} else {
						intent = new Intent();
						ComponentName component = new ComponentName(
								"com.android.settings",
								"com.android.settings.WirelessSettings");
						intent.setComponent(component);
						intent.setAction("android.intent.action.VIEW");
					}
					startActivity(intent);

				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			.create()
			.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_password, menu);
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
