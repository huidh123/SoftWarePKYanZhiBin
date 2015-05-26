package com.SHM.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.SHM.adapter.MyAdapter;
import com.SHM.constant.Constant;
import com.SHM.tools.HttpUtil;
import com.SHM.view.FooterLoad;
import com.SHM.view.FooterLoad.loadListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends Activity implements loadListener{
	
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
	
	private ImageButton search_back;
	private EditText search_et;
	private ImageButton search_search;
	private Handler handler;
	private ProgressDialog progressDialog;
	private static String isSucceed;
	private static String resp_success;
	private int count;
	private int haveLoadN;
    private MyAdapter adapter;
	private FooterLoad listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);
		
		initView();
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				
				switch (msg.what) {
				case 0:
					Toast.makeText(getApplicationContext(), "搜索关键字不能为空", Toast.LENGTH_LONG).show();
					break;
				case 1:
					progressDialog.cancel();
					Toast.makeText(getApplicationContext(), "网络错误，搜索失败", Toast.LENGTH_LONG).show();
					break;
				case 2:
					progressDialog.cancel();
					Toast.makeText(getApplicationContext(), "未找到相关信息", Toast.LENGTH_LONG).show();
					break;
				case 3:
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
							Intent intent = new Intent(SearchActivity.this,ProblemShowActivity.class);
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
					break;
				default:
					break;
				}
			}
		};
		
		search_back.setOnClickListener(backClick);
		search_search.setOnClickListener(searchClick);
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		search_back = (ImageButton) findViewById(R.id.search_back);
		search_et = (EditText) findViewById(R.id.search_et);
		search_search = (ImageButton) findViewById(R.id.search_search);
		listview = (FooterLoad) findViewById(R.id.search_listview);
	}
	
	OnClickListener backClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SearchActivity.this.finish();
		}
	};
	
	OnClickListener searchClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final String searchContent = search_et.getText().toString();
			if(searchContent.equals("")){
				handler.sendEmptyMessage(0);
				return;
			}
			
			createLoginProgressDialog();
			
			new Thread(){
				@Override
				public void run() {
					super.run();
					
					List<NameValuePair> param = new ArrayList<NameValuePair>();
					param.add(new BasicNameValuePair("searchContent", searchContent));
					
					String resp = null;  
					resp = HttpUtil.sendPost(Constant.URL + "SearchServlet", param,HTTP.UTF_8);
					
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
							handler.sendEmptyMessage(3);
						}else{
							handler.sendEmptyMessage(1);
						}
					}
				};
			}.start();
		}
	};
	
	private void createLoginProgressDialog(){
	    progressDialog = new ProgressDialog(SearchActivity.this);
	    progressDialog.setMessage("加载中，请稍候...");
	    progressDialog.setCancelable(false);
	    progressDialog.show();
	  }
	
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
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		listItems.clear();
		showListView(listItems);
		final String searchContent = search_et.getText().toString();
		if(searchContent.equals("")){
			handler.sendEmptyMessage(0);
			return;
		}
		
		createLoginProgressDialog();
		
		new Thread(){
			@Override
			public void run() {
				super.run();
				
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("searchContent", searchContent));
				
				String resp = null;  
				resp = HttpUtil.sendPost(Constant.URL + "SearchServlet", param,HTTP.UTF_8);
				
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
						handler.sendEmptyMessage(3);
					}else{
						handler.sendEmptyMessage(1);
					}
				}
			};
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
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
