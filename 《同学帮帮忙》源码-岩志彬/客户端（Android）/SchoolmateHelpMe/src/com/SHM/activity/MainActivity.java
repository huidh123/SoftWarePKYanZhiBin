package com.SHM.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.SHM.adapter.MyAdapter;
import com.SHM.constant.Constant;
import com.SHM.tools.GetInformation;
import com.SHM.tools.HttpUtil;
import com.SHM.view.FooterLoad;
import com.SHM.view.FooterLoad.loadListener;
import com.SHM.view.SatelliteMenu;
import com.SHM.view.SatelliteMenu.OnMenuItemClickListener;

public class MainActivity extends Activity implements loadListener,OnRefreshListener{
	
    private ArrayList<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();  
    private ArrayList<Integer> problemid = new ArrayList<Integer>();
    private ArrayList<String> username = new ArrayList<String>();
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> content = new ArrayList<String>();
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<Integer> sex = new ArrayList<Integer>();
    private ArrayList<Integer> givedpoints = new ArrayList<Integer>();
    private ArrayList<Integer> picid = new ArrayList<Integer>();
    private ArrayList<String> time = new ArrayList<String>();
   
    private int haveLoadN;
    private MyAdapter adapter;
	private FooterLoad listview;
	private SatelliteMenu satelliteMenu;
	private Handler handler;
	private ProgressDialog progressDialog;
	private String isSucceed;
	private String resp_success;
	private int count;
	private SwipeRefreshLayout swipeRefreshLayout;
	private ImageView search;
	private String version;
	private String version_update;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		 
		initView();
		
		Bundle bundle = this.getIntent().getExtras();
		String version_get = bundle.getString("version");
		version = version_get;
		version_update = version_get;
		
		satelliteMenu.setOnMenuItemClickListener(onMenuItemClickListener);
		search.setOnClickListener(searchClick);
		
		createLoginProgressDialog();
		
		GetInformation.getInformation();
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch(msg.what){
					case 0:
						progressDialog.cancel();
						final String[] resp_success_after = resp_success.split("9%!0#0");
						count = (resp_success_after.length-1)/9;
						problemid.clear();
					    username.clear();
					    title.clear();
					    content.clear();
					    name.clear();
					    sex.clear();
					    givedpoints.clear();
					    picid.clear();
					    time.clear();
						for(int i = 0;i < count;i++){
							problemid.add(Integer.valueOf(resp_success_after[(count - i - 1)*9+1]));
						    username.add(resp_success_after[(count - i - 1)*9+2]);
						    title.add(resp_success_after[(count - i - 1)*9+3]);
						    content.add(resp_success_after[(count - i - 1)*9+4]);
						    name.add(resp_success_after[(count - i - 1)*9+5]);
						    sex.add(Integer.valueOf(resp_success_after[(count - i - 1)*9+6]));
						    givedpoints.add(Integer.valueOf(resp_success_after[(count - i - 1)*9+7]));
						    picid.add(Integer.valueOf(resp_success_after[(count - i - 1)*9+8]));
						    time.add(resp_success_after[(count - i - 1)*9+9]);
						}
						getData();
						showListView(listItems);
						
						listview.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(MainActivity.this,ProblemShowActivity.class);
								Bundle bundle = new Bundle();
								bundle.putInt("problemid", Integer.valueOf(resp_success_after[(count - position - 1)*9+1]));
								bundle.putString("username", resp_success_after[(count - position - 1)*9+2]);
								bundle.putString("title", resp_success_after[(count - position - 1)*9+3]);
								bundle.putString("content", resp_success_after[(count - position - 1)*9+4]);
								bundle.putString("name", resp_success_after[(count - position - 1)*9+5]);
								bundle.putInt("sex", Integer.valueOf(resp_success_after[(count - position - 1)*9+6]));
								bundle.putInt("givedpoints", Integer.valueOf(resp_success_after[(count - position - 1)*9+7]));
								bundle.putInt("picid", Integer.valueOf(resp_success_after[(count - position - 1)*9+8]));
								bundle.putString("time", resp_success_after[(count - position - 1)*9+9]);
								intent.putExtras(bundle);
								startActivity(intent);
							}
						});
						swipeRefreshLayout.setRefreshing(false);
						if(!version.equals("donothavenewversion")){
							version = "donothavenewversion";
							new AlertDialog.Builder(MainActivity.this)
							.setTitle("版本更新")
							.setMessage("检测到新版本，是否进行更新？")
							.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(Intent.ACTION_VIEW);
									intent.setData(Uri.parse(version_update));
									startActivity(intent);
									dialog.cancel();
								}
							})
							.setNegativeButton("下次提醒", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							})
							.create().show();
						}
						break;
					case 1:
						progressDialog.cancel();
						Toast.makeText(getApplicationContext(), "网络错误，加载失败", Toast.LENGTH_LONG).show();
						swipeRefreshLayout.setRefreshing(false);
						if(!version.equals("donothavenewversion")){
							version = "donothavenewversion";
							new AlertDialog.Builder(MainActivity.this)
							.setTitle("版本更新")
							.setMessage("检测到新版本，是否进行更新？")
							.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(Intent.ACTION_VIEW);
									intent.setData(Uri.parse(version_update));
									startActivity(intent);
									dialog.cancel();
								}
							})
							.setNegativeButton("下次提醒", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							})
							.create().show();
						}
						break;
					case 2:
						progressDialog.cancel();
						listItems.clear();
						problemid.clear();
					    username.clear();
					    title.clear();
					    content.clear();
					    name.clear();
					    sex.clear();
					    givedpoints.clear();
					    picid.clear();
					    time.clear();
					    getData();
						showListView(listItems);
						Toast.makeText(getApplicationContext(), "现在没有待解决的问题呦，快来发布想要解决的问题吧", Toast.LENGTH_LONG).show();
						swipeRefreshLayout.setRefreshing(false);
						if(!version.equals("donothavenewversion")){
							version = "donothavenewversion";
							new AlertDialog.Builder(MainActivity.this)
							.setTitle("版本更新")
							.setMessage("检测到新版本，是否进行更新？")
							.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(Intent.ACTION_VIEW);
									intent.setData(Uri.parse(version_update));
									startActivity(intent);
									dialog.cancel();
								}
							})
							.setNegativeButton("下次提醒", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							})
							.create().show();
						}
						break;
					default:
						break;
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				
				String resp = null;
				resp = HttpUtil.sendPost(Constant.URL + "ProblemsShowServlet", null,HTTP.UTF_8);
				
				if(resp == null){
					handler.sendEmptyMessage(1);
				}else if(resp.length() < 7){
					if(resp.equals("failed")){
						handler.sendEmptyMessage(2);
					}else{
						handler.sendEmptyMessage(1);
					}
				}else{
					isSucceed = resp.substring(0,7);
					if(isSucceed.equals("success")){
						resp_success = resp;
						handler.sendEmptyMessage(0);
					}else {
						handler.sendEmptyMessage(1);
					}
				}
			}
		}.start();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		satelliteMenu = (SatelliteMenu) findViewById(R.id.satelliteMenu);
		listview = (FooterLoad)findViewById(R.id.mainlistview);
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mainlistview_fresh);
		search = (ImageView) findViewById(R.id.main_search);

		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_green_dark, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
	}
	
	OnMenuItemClickListener onMenuItemClickListener = new OnMenuItemClickListener() {
		
		@Override
		public void onClick(View view, int pos) {
			// TODO Auto-generated method stub
			switch(pos){
				case 1:
					Intent intent_person = new Intent(MainActivity.this,PersonActivity.class);
					startActivity(intent_person);
					break;
				case 2:
					Intent intent_my_problem = new Intent(MainActivity.this,MyProblemActivity.class);
					startActivity(intent_my_problem);
					break;
				case 3:
					Intent intent_publish_problem = new Intent(MainActivity.this,PublishProblemActivity.class);
					startActivity(intent_publish_problem);
					break;
				case 4:
					Intent intent_ranking_list = new Intent(MainActivity.this,RankingListActivity.class);
					startActivity(intent_ranking_list);
					break;
				default:
					break;
			}
		}
	};
	
	OnClickListener searchClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainActivity.this,SearchActivity.class);
			startActivity(intent);
		}
	};

	private void getData() {
		// TODO Auto-generated method stub
		//AddData datas = null;
		//datas.addDatas();
		listItems.clear();
		int firstloadnum = 0;
		for(int i= 0;(i<10)&&(i<problemid.size());i++)
		{
			 Map<String,Object>  listContent =new  HashMap<String,Object>();
			 listContent.put("pic",Constant.pics[picid.get(i)]);
			 listContent.put("name",name.get(i));
			 listContent.put("time",  time.get(i));
			 listContent.put("sex",Constant.sexs[sex.get(i)]);
			 listContent.put("title",title.get(i));
			 listContent.put("givedpoints",  givedpoints.get(i));
			 listContent.put("content",  content.get(i));
		  	 listItems.add(listContent);
		  	 firstloadnum =i;
		}
        haveLoadN =firstloadnum;
	}
 
	 private void getLoadData() {
		// TODO Auto-generated method stub
	
		 int footerloadnum = 0;
		for(int i= haveLoadN+1;(i<haveLoadN+10)&&(i<problemid.size());i++)
		{
			 Map<String,Object>  listContent =new  HashMap<String,Object>();
		  	listContent.put("pic",Constant.pics[picid.get(i)]);
		  	listContent.put("name",name.get(i));
		  	listContent.put("time",  time.get(i));
		  	listContent.put("sex",Constant.sexs[sex.get(i)]);
		  	listContent.put("title",title.get(i));
		  	listContent.put("givedpoints",  givedpoints.get(i));
		  	listContent.put("content",  content.get(i));
		  	listItems.add(listContent);
		  	footerloadnum=i;
		}
		haveLoadN =footerloadnum;
	}
	 
	
	private void showListView(ArrayList<Map<String,Object>>  listItems){
		if(haveLoadN<=problemid.size()){
			if(adapter == null){
				listview.setInterface(this);
				adapter = new MyAdapter(this,listItems,
					R.layout.mainlistitem,new String[] {"pic","name","time","sex","title","givedpoints","content"},
					new int[] {R.id.pic,R.id.name,R.id.time,R.id.sex,R.id.title,R.id.givedpoints,R.id.content });
				listview.setAdapter(adapter);	
			}else{
				 adapter.onDateChange(listItems);
			}	
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	@Override
	public void onLoad() {
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
				// TODO Auto-generated method stub
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(haveLoadN<problemid.size()){
						  getLoadData();
					    showListView(listItems);
						listview.loadComplete();
					}else{
						haveLoadN = 65530;
					}
				}
		}, 2000);
	}
	
	private void createLoginProgressDialog(){
	    progressDialog = new ProgressDialog(MainActivity.this);
	    progressDialog.setMessage("加载中，请稍候...");
	    progressDialog.setCancelable(false);
	    progressDialog.show();
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
				Time t=new Time();
				Calendar c = Calendar.getInstance();  
				long newTime=c.getTimeInMillis();
				System.out.println(newTime+"-"+oldTime+"-");
				if(newTime-oldTime<3000&&newTime-oldTime>300){
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				
				String resp = null;
				resp = HttpUtil.sendPost(Constant.URL + "ProblemsShowServlet", null,HTTP.UTF_8);
				
				if(resp == null){
					handler.sendEmptyMessage(1);
				}else if(resp.length() < 7){
					if(resp.equals("failed")){
						handler.sendEmptyMessage(2);
					}else{
						handler.sendEmptyMessage(1);
					}
				}else{
					isSucceed = resp.substring(0,7);
					if(isSucceed.equals("success")){
						resp_success = resp;
						handler.sendEmptyMessage(0);
					}else {
						handler.sendEmptyMessage(1);
					}
				}
			}
		}.start();
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				
				String resp = null;
				resp = HttpUtil.sendPost(Constant.URL + "ProblemsShowServlet", null,HTTP.UTF_8);
				
				if(resp == null){
					handler.sendEmptyMessage(1);
				}else if(resp.length() < 7){
					if(resp.equals("failed")){
						handler.sendEmptyMessage(2);
					}else{
						handler.sendEmptyMessage(1);
					}
				}else{
					isSucceed = resp.substring(0,7);
					if(isSucceed.equals("success")){
						resp_success = resp;
						handler.sendEmptyMessage(0);
					}else {
						handler.sendEmptyMessage(1);
					}
				}
			}
		}.start();
	}
}
