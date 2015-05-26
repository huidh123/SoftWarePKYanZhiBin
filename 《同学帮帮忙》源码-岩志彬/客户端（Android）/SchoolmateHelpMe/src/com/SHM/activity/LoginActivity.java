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
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.NumberKeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.SHM.constant.Constant;
import com.SHM.tools.DBManager;
import com.SHM.tools.GetInformation;
import com.SHM.tools.HttpUtil;

public class LoginActivity extends Activity {
	
	private EditText login_username;
	private EditText login_password;
	private Button login;
	private TextView update_password;
	private TextView register;
	private Handler handler;
	private ProgressDialog progressDialog;
	private CheckBox remenber_password;
	private boolean isRemenber = false;
	private DBManager mgr;
	private String success_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		Constant.id = -1;
		Constant.username = "";
		Constant.name = "";
		Constant.picid = -1;
		Constant.signature = "";
		Constant.academe = "";
		Constant.profession = "";
		Constant.sex = -1;
		Constant.qqnumber = "";
		Constant.points = -1;
		
		Bundle bundle = this.getIntent().getExtras();
		String search = bundle.getString("search");
		
		initView();
		
		if(!search.equals("")){
			String[] success_serch = search.split("9%!0#0");
			login_username.setText(success_serch[0]);
			if(!success_serch[1].equals(" ")){
				login_password.setText(success_serch[1]);
				isRemenber = true;
				remenber_password.setChecked(true);
			}
		}
		
		login_password.setKeyListener(new NumberKeyListener() {
			
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
		
		login.setOnClickListener(loginClick);
		update_password.setOnClickListener(updatePasswordClick);
		register.setOnClickListener(registerClick);
		remenber_password.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					isRemenber = true;
				}else if(!isChecked){
					isRemenber = false;
				}
			}
		});
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					progressDialog.cancel();
					String[] success_back_after = success_back.split("9%!0#0");
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("version", success_back_after[1]);
					intent.putExtras(bundle);
					startActivity(intent);
					LoginActivity.this.finish(); 
					mgr.closeDB();
					break;
				case 1:
					progressDialog.cancel();
					new AlertDialog.Builder(LoginActivity.this)
						.setMessage("账号或密码错误")
						.setPositiveButton("返回", null)
						.create()
						.show();
					login_password.setText(null);
					break;
				case 2:
					progressDialog.cancel();
					new AlertDialog.Builder(LoginActivity.this)
						.setMessage("哎呀~服务器君不太给力哦，请检查网络后再次尝试登陆吧")
						.setPositiveButton("返回", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
						}).create().show();
					break;
				case 3:
					progressDialog.cancel();
					new AlertDialog.Builder(LoginActivity.this)
						.setMessage("请输入账号和密码")
						.setPositiveButton("返回", null)
						.create()
						.show();
					break;
				case 4:
					progressDialog.cancel();
					new AlertDialog.Builder(LoginActivity.this)
						.setMessage("请输入账号")
						.setPositiveButton("返回", null)
						.create()
						.show();
					break;
				case 5:
					progressDialog.cancel();
					new AlertDialog.Builder(LoginActivity.this)
						.setMessage("请输入密码")
						.setPositiveButton("返回", null)
						.create()
						.show();
					break;
				case 6:
					progressDialog.cancel();
					new AlertDialog.Builder(LoginActivity.this)
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
	
	private void initView(){
		login_username = (EditText) findViewById(R.id.login_username);
		login_password = (EditText) findViewById(R.id.login_password);
		login = (Button) findViewById(R.id.login);
		update_password = (TextView) findViewById(R.id.update_password);
		register = (TextView) findViewById(R.id.register);
		remenber_password = (CheckBox) findViewById(R.id.remenber_password);
		mgr = new DBManager(this);
	}
	
	OnClickListener loginClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			boolean flag = checkNetworkState();
			
			if(flag == true){
				final String username = login_username.getText().toString();
				final String password = login_password.getText().toString();
				createLoginProgressDialog();
				
				new Thread(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						
						if(username.equals("") && password.equals("")){
							handler.sendEmptyMessage(3);
							return;
						}else if(username.equals("")){
							handler.sendEmptyMessage(4);
							return;
						}else if(password.equals("")){
							handler.sendEmptyMessage(5);
							return;
						}
						
						List<NameValuePair> param = new ArrayList<NameValuePair>();
						param.add(new BasicNameValuePair("username",username));
						param.add(new BasicNameValuePair("password",password));
						String resp = null;
						resp = HttpUtil.sendPost(Constant.URL + "LoginServlet", param,HTTP.UTF_8);
						if(resp == null){
							handler.sendEmptyMessage(2);
						}else if(resp.equals("fail")){
							handler.sendEmptyMessage(1);
						}else if(resp.length() < 7){
							handler.sendEmptyMessage(6);
						}else{
							String issucceed = resp.substring(0, 7);
							if(issucceed.equals("success")){
								success_back = resp;
								Constant.username = username;
								if(isRemenber == true){
									mgr.save(username, password);
								}else if(isRemenber == false){
									mgr.save(username, " ");
								}
								handler.sendEmptyMessage(0);
							}else{
								handler.sendEmptyMessage(6);
							}
						}
					}
				}.start();
			}
		}
	};
	
	OnClickListener updatePasswordClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(LoginActivity.this,UpdatePasswordActivity.class);
			startActivity(intent);
		}
	};
	
	OnClickListener registerClick = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			final AlertDialog dig = new AlertDialog.Builder(LoginActivity.this).create();
			dig.show();
			
			final Window window = dig.getWindow();
			window.setContentView(R.layout.register_alert);
			
			window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); 
			window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); 
			
			Button bt_register = (Button) window.findViewById(R.id.bt_register_alert);
			bt_register.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dig.dismiss();
				}
			});
		}
	};
	
	private void createLoginProgressDialog(){
	    progressDialog = new ProgressDialog(LoginActivity.this);
	    progressDialog.setMessage("登录中...");
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
		new AlertDialog.Builder(LoginActivity.this)
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
	
	static boolean boolKey=false;
	static long oldTime;
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if(boolKey==false){
				Calendar c = Calendar.getInstance();  
				oldTime=c.getTimeInMillis();
				boolKey=true;
				Toast.makeText(getApplicationContext(), "再按一次返回键退出！", 3).show();
			}
			else{
				Calendar c = Calendar.getInstance();  
				long newTime=c.getTimeInMillis();
				System.out.println(newTime+"-"+oldTime+"-");
				if(newTime-oldTime<3000&&newTime-oldTime>300){
					mgr.closeDB();
					System.exit(0);
				}else{
					boolKey=false;
					onKeyDown(keyCode,event);
				}
			}
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
