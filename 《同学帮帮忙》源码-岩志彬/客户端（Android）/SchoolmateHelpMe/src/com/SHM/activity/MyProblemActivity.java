package com.SHM.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.SHM.adapter.MyAdapter;
import com.SHM.constant.Constant;
import com.SHM.tools.GetInformation;
import com.SHM.tools.HttpUtil;
import com.SHM.view.FooterLoad;
import com.SHM.view.FooterLoad.loadListener;



public class MyProblemActivity extends Activity implements OnClickListener,OnRefreshListener{
	
	private ArrayList<Map<String,Object>> listItems_publish = new ArrayList<Map<String,Object>>();  
    private ArrayList<Integer> problemid_publish = new ArrayList<Integer>();
    private ArrayList<String> username_publish = new ArrayList<String>();
    private ArrayList<String> title_publish = new ArrayList<String>();
    private ArrayList<String> content_publish = new ArrayList<String>();
    private ArrayList<String> name_publish = new ArrayList<String>();
    private ArrayList<Integer> sex_publish = new ArrayList<Integer>();
    private ArrayList<Integer> givedpoints_publish = new ArrayList<Integer>();
    private ArrayList<Integer> picid_publish = new ArrayList<Integer>();
    private ArrayList<String> time_publish = new ArrayList<String>();
    private ArrayList<String> solved_publish = new ArrayList<String>();
    
    private ArrayList<Map<String,Object>> listItems_solve = new ArrayList<Map<String,Object>>();  
    private ArrayList<Integer> problemid_solve = new ArrayList<Integer>();
    private ArrayList<String> username_solve = new ArrayList<String>();
    private ArrayList<String> title_solve = new ArrayList<String>();
    private ArrayList<String> content_solve = new ArrayList<String>();
    private ArrayList<String> name_solve = new ArrayList<String>();
    private ArrayList<Integer> sex_solve = new ArrayList<Integer>();
    private ArrayList<Integer> givedpoints_solve = new ArrayList<Integer>();
    private ArrayList<Integer> picid_solve = new ArrayList<Integer>();
    private ArrayList<String> time_solve = new ArrayList<String>();
	
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private MyAdapter adapter_publish;
	private MyAdapter adapter_solve;
	private List<View> mViews = new ArrayList<View>();
	private LinearLayout tabPublish;
	private LinearLayout tabSolve;
	private ImageButton person_publish;
	private ImageButton person_solve;
	private FooterLoad tab_publish_listview;
	private FooterLoad tab_solve_listview;
	private Handler handler;
	private ProgressDialog progressDialog;
	private String isSucceed_publish;
	private String resp_success_publish;
	private String isSucceed_solve;
	private String resp_success_solve;
	private int count;
	private int haveLoadN_publish;
	private int haveLoadN_solve;
	private int flag = 0;
	private int flag_publish = 0;
	private int flag_solve = 0;
	private int flag_publish_click = 0;
	private int flag_solve_click = 0;
	private SwipeRefreshLayout swipeRefreshLayout_publish;
	private SwipeRefreshLayout swipeRefreshLayout_solve;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_problem);
		
		initView();
		
		initEvents();
		
		GetInformation.getInformation();
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch(msg.what){
					case 0:
						if(flag_publish == 1){
							viewPager.setCurrentItem(0);
						}
						progressDialog.cancel();
						final String[] resp_success_after_publish = resp_success_publish.split("9%!0#0");
						count = (resp_success_after_publish.length-1)/10;
						problemid_publish.clear();
					    username_publish.clear();
					    title_publish.clear();
					    content_publish.clear();
					    name_publish.clear();
					    sex_publish.clear();
					    givedpoints_publish.clear();
					    picid_publish.clear();
					    time_publish.clear();
					    solved_publish.clear();
						for(int i = 0;i < count;i++){
							problemid_publish.add(Integer.valueOf(resp_success_after_publish[(count - i - 1)*10+1]));
						    username_publish.add(resp_success_after_publish[(count - i - 1)*10+2]);
						    title_publish.add(resp_success_after_publish[(count - i - 1)*10+3]);
						    content_publish.add(resp_success_after_publish[(count - i - 1)*10+4]);
						    name_publish.add(resp_success_after_publish[(count - i - 1)*10+5]);
						    sex_publish.add(Integer.valueOf(resp_success_after_publish[(count - i - 1)*10+6]));
						    givedpoints_publish.add(Integer.valueOf(resp_success_after_publish[(count - i - 1)*10+7]));
						    picid_publish.add(Integer.valueOf(resp_success_after_publish[(count - i - 1)*10+8]));
						    time_publish.add(resp_success_after_publish[(count - i - 1)*10+9]);
						    solved_publish.add(resp_success_after_publish[(count - i - 1)*10+10]);
						}
						getData_publish();
						showListView_publish(listItems_publish);
						
						tab_publish_listview.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(MyProblemActivity.this,MyPublishProblemShowActivity.class);
								Bundle bundle = new Bundle();
								bundle.putInt("problemid", Integer.valueOf(resp_success_after_publish[(count - position - 1)*10+1]));
								bundle.putString("username", resp_success_after_publish[(count - position - 1)*10+2]);
								bundle.putString("title", resp_success_after_publish[(count - position - 1)*10+3]);
								bundle.putString("content", resp_success_after_publish[(count - position - 1)*10+4]);
								bundle.putString("name", resp_success_after_publish[(count - position - 1)*10+5]);
								bundle.putInt("sex", Integer.valueOf(resp_success_after_publish[(count - position - 1)*10+6]));
								bundle.putInt("givedpoints", Integer.valueOf(resp_success_after_publish[(count - position - 1)*10+7]));
								bundle.putInt("picid", Integer.valueOf(resp_success_after_publish[(count - position - 1)*10+8]));
								bundle.putString("time", resp_success_after_publish[(count - position - 1)*10+9]);
								bundle.putString("IsSolved", resp_success_after_publish[(count - position - 1)*10+10]);
								intent.putExtras(bundle);
								startActivity(intent);
							}
						});
						flag_publish_click = 0;
						swipeRefreshLayout_publish.setRefreshing(false);
						swipeRefreshLayout_solve.setRefreshing(false);
						break;
					case 1:
						progressDialog.cancel();
						if(flag_solve == 1){
							viewPager.setCurrentItem(1);
						}
						if(flag_solve == 0){
							viewPager.setCurrentItem(0);
						}
						listItems_publish.clear();
						problemid_publish.clear();
					    username_publish.clear();
					    title_publish.clear();
					    content_publish.clear();
					    name_publish.clear();
					    sex_publish.clear();
					    givedpoints_publish.clear();
					    picid_publish.clear();
					    time_publish.clear();
					    solved_publish.clear();
					    getData_publish();
						showListView_publish(listItems_publish);
						
						listItems_solve.clear();
						problemid_solve.clear();
					    username_solve.clear();
					    title_solve.clear();
					    content_solve.clear();
					    name_solve.clear();
					    sex_solve.clear();
					    givedpoints_solve.clear();
					    picid_solve.clear();
					    time_solve.clear();
					    getData_solve();
						showListView_solve(listItems_solve);
						
						Toast.makeText(getApplicationContext(), "网络错误，加载失败", Toast.LENGTH_LONG).show();
						swipeRefreshLayout_publish.setRefreshing(false);
						swipeRefreshLayout_solve.setRefreshing(false);
						break;
					case 2:
						progressDialog.cancel();
						
						listItems_publish.clear();
						problemid_publish.clear();
					    username_publish.clear();
					    title_publish.clear();
					    content_publish.clear();
					    name_publish.clear();
					    sex_publish.clear();
					    givedpoints_publish.clear();
					    picid_publish.clear();
					    time_publish.clear();
					    solved_publish.clear();
					    getData_publish();
						showListView_publish(listItems_publish);
						
						Toast.makeText(getApplicationContext(), "您还没有发布过问题呦，快将需要解决的问题告诉小伙伴们~", Toast.LENGTH_LONG).show();
						swipeRefreshLayout_publish.setRefreshing(false);
						swipeRefreshLayout_solve.setRefreshing(false);
						break;
					case 3:
						if(flag_solve == 1){
							viewPager.setCurrentItem(1);
						}
						progressDialog.cancel();
						final String[] resp_success_after_solve = resp_success_solve.split("9%!0#0");
						count = (resp_success_after_solve.length-1)/9;
						problemid_solve.clear();
					    username_solve.clear();
					    title_solve.clear();
					    content_solve.clear();
					    name_solve.clear();
					    sex_solve.clear();
					    givedpoints_solve.clear();
					    picid_solve.clear();
					    time_solve.clear();
						for(int i = 0;i < count;i++){
							problemid_solve.add(Integer.valueOf(resp_success_after_solve[(count - i - 1)*9+1]));
						    username_solve.add(resp_success_after_solve[(count - i - 1)*9+2]);
						    title_solve.add(resp_success_after_solve[(count - i - 1)*9+3]);
						    content_solve.add(resp_success_after_solve[(count - i - 1)*9+4]);
						    name_solve.add(resp_success_after_solve[(count - i - 1)*9+5]);
						    sex_solve.add(Integer.valueOf(resp_success_after_solve[(count - i - 1)*9+6]));
						    givedpoints_solve.add(Integer.valueOf(resp_success_after_solve[(count - i - 1)*9+7]));
						    picid_solve.add(Integer.valueOf(resp_success_after_solve[(count - i - 1)*9+8]));
						    time_solve.add(resp_success_after_solve[(count - i - 1)*9+9]);
						}
						getData_solve();
						showListView_solve(listItems_solve);
						
						tab_solve_listview.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(MyProblemActivity.this,MySolveProblemShowActivity.class);
								Bundle bundle = new Bundle();
								bundle.putInt("problemid", Integer.valueOf(resp_success_after_solve[(count - position - 1)*9+1]));
								bundle.putString("username", resp_success_after_solve[(count - position - 1)*9+2]);
								bundle.putString("title", resp_success_after_solve[(count - position - 1)*9+3]);
								bundle.putString("content", resp_success_after_solve[(count - position - 1)*9+4]);
								bundle.putString("name", resp_success_after_solve[(count - position - 1)*9+5]);
								bundle.putInt("sex", Integer.valueOf(resp_success_after_solve[(count - position - 1)*9+6]));
								bundle.putInt("givedpoints", Integer.valueOf(resp_success_after_solve[(count - position - 1)*9+7]));
								bundle.putInt("picid", Integer.valueOf(resp_success_after_solve[(count - position - 1)*9+8]));
								bundle.putString("time", resp_success_after_solve[(count - position - 1)*9+9]);
								intent.putExtras(bundle);
								startActivity(intent);
							}
						});
						flag_solve_click = 0;
						swipeRefreshLayout_publish.setRefreshing(false);
						swipeRefreshLayout_solve.setRefreshing(false);
						break;
					case 4:
						progressDialog.cancel();
						if(flag_solve == 1){
							viewPager.setCurrentItem(1);
						}
						
						listItems_solve.clear();
						problemid_solve.clear();
					    username_solve.clear();
					    title_solve.clear();
					    content_solve.clear();
					    name_solve.clear();
					    sex_solve.clear();
					    givedpoints_solve.clear();
					    picid_solve.clear();
					    time_solve.clear();
					    getData_solve();
						showListView_solve(listItems_solve);
						
						Toast.makeText(getApplicationContext(), "现在没有正在帮小伙伴们解决的问题，快动起来帮小伙伴们解决问题~", Toast.LENGTH_LONG).show();
						swipeRefreshLayout_publish.setRefreshing(false);
						swipeRefreshLayout_solve.setRefreshing(false);
						break;
					default:
						break;
				}
			}
		};
		
		createLoginProgressDialog();
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("username",Constant.username));
				
				String resp = null;
				resp = HttpUtil.sendPost(Constant.URL + "MyPublishProblemsShowServlet", param,HTTP.UTF_8);
				
				if(resp == null){
					handler.sendEmptyMessage(1);
				}else if(resp.length() < 7){
					if(resp.equals("failed")){
						handler.sendEmptyMessage(2);
					}else{
						handler.sendEmptyMessage(1);
					}
				}else{
					isSucceed_publish = resp.substring(0,7);
					if(isSucceed_publish.equals("success")){
						resp_success_publish = resp;
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
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		tabPublish = (LinearLayout) findViewById(R.id.tabPublish);
		tabSolve = (LinearLayout) findViewById(R.id.tabSolve);
		person_publish = (ImageButton) findViewById(R.id.person_publish);
		person_solve = (ImageButton) findViewById(R.id.person_solve);
		
		LayoutInflater mInflater = LayoutInflater.from(this);
		View tab_publish = mInflater.inflate(R.layout.tab_publish, null);
		View tab_solve = mInflater.inflate(R.layout.tab_solve, null);
		tab_publish_listview = (FooterLoad) tab_publish.findViewById(R.id.tab_publish_listview);
		tab_solve_listview = (FooterLoad) tab_solve.findViewById(R.id.tab_solve_listview);
		swipeRefreshLayout_publish = (SwipeRefreshLayout) tab_publish.findViewById(R.id.tab_publish_listview_fresh);

		swipeRefreshLayout_publish.setOnRefreshListener(this);
		swipeRefreshLayout_publish.setColorScheme(android.R.color.holo_green_dark, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		
		swipeRefreshLayout_solve = (SwipeRefreshLayout) tab_solve.findViewById(R.id.tab_solve_listview_fresh);

		swipeRefreshLayout_solve.setOnRefreshListener(this);
		swipeRefreshLayout_solve.setColorScheme(android.R.color.holo_green_dark, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		
		mViews.add(tab_publish);
		mViews.add(tab_solve);
		
		pagerAdapter = new PagerAdapter() {
			
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(mViews.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = mViews.get(position);
				container.addView(view);
				return view;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				return mViews.size();
			}
		};
		
		viewPager.setAdapter(pagerAdapter);
	}
	
	private void initEvents() {
		// TODO Auto-generated method stub
		tabPublish.setOnClickListener(this);
		tabSolve.setOnClickListener(this);
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				int currentItem = viewPager.getCurrentItem();
				resetImg();
				switch (currentItem) {
				case 0:
					if(flag_publish_click == 0){
						flag = 0;
						flag_publish = 0;
						createLoginProgressDialog();
						new Thread(){
							@Override
							public void run() {
								// TODO Auto-generated method stub
								super.run();
								
								List<NameValuePair> param = new ArrayList<NameValuePair>();
								param.add(new BasicNameValuePair("username",Constant.username));
								
								String resp = null;
								resp = HttpUtil.sendPost(Constant.URL + "MyPublishProblemsShowServlet", param,HTTP.UTF_8);
								
								if(resp == null){
									handler.sendEmptyMessage(1);
								}else if(resp.length() < 7){
									if(resp.equals("failed")){
										handler.sendEmptyMessage(2);
									}else{
										handler.sendEmptyMessage(1);
									}
								}else{
									isSucceed_publish = resp.substring(0,7);
									if(isSucceed_publish.equals("success")){
										resp_success_publish = resp;
										handler.sendEmptyMessage(0);
									}else {
										handler.sendEmptyMessage(1);
									}
								}
							}
						}.start();
					}
					tabPublish.setBackgroundResource(R.drawable.my_publish_background_on);
					break;
				case 1:
					if(flag_solve_click == 0){
						flag = 1;
						flag_solve = 0;
						createLoginProgressDialog();
						new Thread(){
							@Override
							public void run() {
								// TODO Auto-generated method stub
								super.run();
								
								List<NameValuePair> param = new ArrayList<NameValuePair>();
								param.add(new BasicNameValuePair("solve_username",Constant.username));
								
								String resp = null;
								resp = HttpUtil.sendPost(Constant.URL + "MySolveProblemsServlet", param,HTTP.UTF_8);
								
								if(resp == null){
									handler.sendEmptyMessage(1);
								}else if(resp.length() < 7){
									if(resp.equals("failed")){
										handler.sendEmptyMessage(4);
									}else{
										handler.sendEmptyMessage(1);
									}
								}else{
									isSucceed_solve = resp.substring(0,7);
									if(isSucceed_solve.equals("success")){
										resp_success_solve = resp;
										handler.sendEmptyMessage(3);
									}else {
										handler.sendEmptyMessage(1);
									}
								}
							}
						}.start();
					}
					tabSolve.setBackgroundResource(R.drawable.my_solve_background_on);
					break;
				default:
					break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		resetImg();
		switch (v.getId()) {
		case R.id.tabPublish:
			flag_publish_click = 1;
			flag = 0;
			flag_publish = 1;
			createLoginProgressDialog();
			new Thread(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					
					List<NameValuePair> param = new ArrayList<NameValuePair>();
					param.add(new BasicNameValuePair("username",Constant.username));
					
					String resp = null;
					resp = HttpUtil.sendPost(Constant.URL + "MyPublishProblemsShowServlet", param,HTTP.UTF_8);
					
					if(resp == null){
						handler.sendEmptyMessage(1);
					}else if(resp.length() < 7){
						if(resp.equals("failed")){
							handler.sendEmptyMessage(2);
						}else{
							handler.sendEmptyMessage(1);
						}
					}else{
						isSucceed_publish = resp.substring(0,7);
						if(isSucceed_publish.equals("success")){
							resp_success_publish = resp;
							handler.sendEmptyMessage(0);
						}else {
							handler.sendEmptyMessage(1);
						}
					}
				}
			}.start();
			tabPublish.setBackgroundResource(R.drawable.my_publish_background_on);
			break;
		case R.id.tabSolve:
			flag_solve_click = 1;
			flag = 1;
			flag_solve = 1;
			createLoginProgressDialog();
			new Thread(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					
					List<NameValuePair> param = new ArrayList<NameValuePair>();
					param.add(new BasicNameValuePair("solve_username",Constant.username));
					
					String resp = null;
					resp = HttpUtil.sendPost(Constant.URL + "MySolveProblemsServlet", param,HTTP.UTF_8);
					
					if(resp == null){
						handler.sendEmptyMessage(1);
					}else if(resp.length() < 7){
						if(resp.equals("failed")){
							handler.sendEmptyMessage(4);
						}else{
							handler.sendEmptyMessage(1);
						}
					}else{
						isSucceed_solve = resp.substring(0,7);
						if(isSucceed_solve.equals("success")){
							resp_success_solve = resp;
							handler.sendEmptyMessage(3);
						}else {
							handler.sendEmptyMessage(1);
						}
					}
				}
			}.start();
			tabSolve.setBackgroundResource(R.drawable.my_solve_background_on);
			break;
		default:
			break;
		}
	}

	private void resetImg() {
		// TODO Auto-generated method stub
		tabPublish.setBackgroundResource(R.drawable.my_publish_background_off);
		tabSolve.setBackgroundResource(R.drawable.my_solve_background_off);
	}
	
	private void createLoginProgressDialog(){
	    progressDialog = new ProgressDialog(MyProblemActivity.this);
	    progressDialog.setMessage("加载中，请稍候...");
	    progressDialog.setCancelable(false);
	    progressDialog.show();
	  }
	
	private void getData_publish() {
		// TODO Auto-generated method stub
		//AddData datas = null;
		//datas.addDatas();
		listItems_publish.clear();
		int firstloadnum = 0;
		for(int i= 0;(i<10)&&(i<problemid_publish.size());i++)
		{
			 Map<String,Object>  listContent_publish =new  HashMap<String,Object>();
			 listContent_publish.put("pic",Constant.pics[picid_publish.get(i)]);
			 listContent_publish.put("name",name_publish.get(i));
			 listContent_publish.put("time",  time_publish.get(i));
			 listContent_publish.put("sex",Constant.sexs[sex_publish.get(i)]);
			 listContent_publish.put("title",title_publish.get(i));
			 listContent_publish.put("givedpoints",  givedpoints_publish.get(i));
			 listContent_publish.put("content",  content_publish.get(i));
			 if(solved_publish.get(i).equals("donothave")){
				 listContent_publish.put("IsSolved",  "还没有人帮助你呦~");
			 }else{
				 String[] IsSolved_true = solved_publish.get(i).split("42!!2!0");
				 listContent_publish.put("IsSolved", "由" + "  " + IsSolved_true[0] + "-" + IsSolved_true[1] + "  " + "帮忙解决中...");
			 }
		  	 listItems_publish.add(listContent_publish);
		  	 firstloadnum =i;
		}
		haveLoadN_publish =firstloadnum;
	}
 
	 private void getLoadData_publish() {
		// TODO Auto-generated method stub
	
		int footerloadnum = 0;
		for(int i= haveLoadN_publish+1;(i<haveLoadN_publish+10)&&(i<problemid_publish.size());i++)
		{
			 Map<String,Object>  listContent_publish =new  HashMap<String,Object>();
			 listContent_publish.put("pic",Constant.pics[picid_publish.get(i)]);
			 listContent_publish.put("name",name_publish.get(i));
			 listContent_publish.put("time",  time_publish.get(i));
			 listContent_publish.put("sex",Constant.sexs[sex_publish.get(i)]);
			 listContent_publish.put("title",title_publish.get(i));
			 listContent_publish.put("givedpoints",  givedpoints_publish.get(i));
			 listContent_publish.put("content",  content_publish.get(i));
			 if(solved_publish.get(i).equals("donothave")){
				 listContent_publish.put("IsSolved",  "还没有人帮助你呦~");
			 }else{
				 String[] IsSolved_true = solved_publish.get(i).split("42!!2!0");
				 listContent_publish.put("IsSolved","由" + "  " + IsSolved_true[0] + "-" + IsSolved_true[1] + "  " + "帮忙解决中...");
			 }
		  	 listItems_publish.add(listContent_publish);
		  	 footerloadnum=i;
		}
		haveLoadN_publish =footerloadnum;
	}
	 
	 private void getData_solve() {
			// TODO Auto-generated method stub
			//AddData datas = null;
			//datas.addDatas();
		 	listItems_solve.clear();
			int firstloadnum = 0;
			for(int i= 0;(i<10)&&(i<problemid_solve.size());i++)
			{
				 Map<String,Object>  listContent_solve =new  HashMap<String,Object>();
				 listContent_solve.put("pic",Constant.pics[picid_solve.get(i)]);
				 listContent_solve.put("name",name_solve.get(i));
				 listContent_solve.put("time",  time_solve.get(i));
				 listContent_solve.put("sex",Constant.sexs[sex_solve.get(i)]);
				 listContent_solve.put("title",title_solve.get(i));
				 listContent_solve.put("givedpoints",  givedpoints_solve.get(i));
				 listContent_solve.put("content",  content_solve.get(i));
			  	 listItems_solve.add(listContent_solve);
			  	 firstloadnum =i;
			}
			haveLoadN_solve =firstloadnum;
		}
	 
	 private void getLoadData_solve() {
			// TODO Auto-generated method stub
		
			 int footerloadnum = 0;
			for(int i= haveLoadN_solve+1;(i<haveLoadN_solve+10)&&(i<problemid_solve.size());i++)
			{
				Map<String,Object>  listContent_solve =new  HashMap<String,Object>();
				listContent_solve.put("pic",Constant.pics[picid_solve.get(i)]);
				listContent_solve.put("name",name_solve.get(i));
				listContent_solve.put("time",  time_solve.get(i));
				listContent_solve.put("sex",Constant.sexs[sex_solve.get(i)]);
				listContent_solve.put("title",title_solve.get(i));
				listContent_solve.put("givedpoints",  givedpoints_solve.get(i));
				listContent_solve.put("content",  content_solve.get(i));
			  	listItems_solve.add(listContent_solve);
			  	footerloadnum=i;
			}
			haveLoadN_solve =footerloadnum;
		}
	 
	
	private void showListView_publish(ArrayList<Map<String,Object>>  listItems){
		if(haveLoadN_publish<=problemid_publish.size()){
			if(adapter_publish == null){
				tab_publish_listview.setInterface(publishLoad);
				adapter_publish = new MyAdapter(this,listItems,
					R.layout.mypublishlistitem,new String[] {"pic","name","time","sex","title","givedpoints","content","IsSolved"},
					new int[] {R.id.pic_my_publish,R.id.name_my_publish,R.id.time_my_publish,R.id.sex_my_publish,
						R.id.title_my_publish,R.id.givedpoints_my_publish,R.id.content_my_publish,R.id.solved_my_publish });
				tab_publish_listview.setAdapter(adapter_publish);	
			}else{
				adapter_publish.onDateChange(listItems);
			}	
	    }
	}
	
	private void showListView_solve(ArrayList<Map<String,Object>>  listItems){
		if(haveLoadN_solve<=problemid_solve.size()){
			if(adapter_solve == null){
				tab_solve_listview.setInterface(solveLoad);
				adapter_solve = new MyAdapter(this,listItems,
					R.layout.mainlistitem,new String[] {"pic","name","time","sex","title","givedpoints","content"},
					new int[] {R.id.pic,R.id.name,R.id.time,R.id.sex,R.id.title,R.id.givedpoints,R.id.content });
				tab_solve_listview.setAdapter(adapter_solve);	
			}else{
				adapter_solve.onDateChange(listItems);
			}	
	    }
	}

	loadListener publishLoad = new loadListener() {
		
		@Override
		public void onLoad() {
			// TODO Auto-generated method stub
			Handler handler_publish = new Handler();
			handler_publish.postDelayed(new Runnable() {
					// TODO Auto-generated method stub
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(flag == 0){
							if(haveLoadN_publish<problemid_publish.size()){
								getLoadData_publish();
								showListView_publish(listItems_publish);
								tab_publish_listview.loadComplete();
							}else{
								haveLoadN_publish = 65530;
							}
						}
					}
			}, 2000);
		}
	};
	
	loadListener solveLoad = new loadListener() {
		
		@Override
		public void onLoad() {
			// TODO Auto-generated method stub
			Handler handler_solve = new Handler();
			handler_solve.postDelayed(new Runnable() {
					// TODO Auto-generated method stub
					@Override
					public void run() {
						// TODO Auto-generated method stub
					if(flag == 1){
							if(haveLoadN_solve<problemid_solve.size()){
								getLoadData_solve();
								showListView_solve(listItems_solve);
								tab_solve_listview.loadComplete();
							}else{
								haveLoadN_solve = 65530;
							}
						}
						
					}
			}, 2000);
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_problem, menu);
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("username",Constant.username));
				
				String resp = null;
				resp = HttpUtil.sendPost(Constant.URL + "MyPublishProblemsShowServlet", param,HTTP.UTF_8);
				
				if(resp == null){
					handler.sendEmptyMessage(1);
				}else if(resp.length() < 7){
					if(resp.equals("failed")){
						handler.sendEmptyMessage(2);
					}else{
						handler.sendEmptyMessage(1);
					}
				}else{
					isSucceed_publish = resp.substring(0,7);
					if(isSucceed_publish.equals("success")){
						resp_success_publish = resp;
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
		if(flag == 0){
			new Thread(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					
					List<NameValuePair> param = new ArrayList<NameValuePair>();
					param.add(new BasicNameValuePair("username",Constant.username));
					
					String resp = null;
					resp = HttpUtil.sendPost(Constant.URL + "MyPublishProblemsShowServlet", param,HTTP.UTF_8);
					
					if(resp == null){
						handler.sendEmptyMessage(1);
					}else if(resp.length() < 7){
						if(resp.equals("failed")){
							handler.sendEmptyMessage(2);
						}else{
							handler.sendEmptyMessage(1);
						}
					}else{
						isSucceed_publish = resp.substring(0,7);
						if(isSucceed_publish.equals("success")){
							resp_success_publish = resp;
							handler.sendEmptyMessage(0);
						}else {
							handler.sendEmptyMessage(1);
						}
					}
				}
			}.start();
		}else if(flag ==1){
			new Thread(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					
					List<NameValuePair> param = new ArrayList<NameValuePair>();
					param.add(new BasicNameValuePair("solve_username",Constant.username));
					
					String resp = null;
					resp = HttpUtil.sendPost(Constant.URL + "MySolveProblemsServlet", param,HTTP.UTF_8);
					
					if(resp == null){
						handler.sendEmptyMessage(1);
					}else if(resp.length() < 7){
						if(resp.equals("failed")){
							handler.sendEmptyMessage(4);
						}else{
							handler.sendEmptyMessage(1);
						}
					}else{
						isSucceed_solve = resp.substring(0,7);
						if(isSucceed_solve.equals("success")){
							resp_success_solve = resp;
							handler.sendEmptyMessage(3);
						}else {
							handler.sendEmptyMessage(1);
						}
					}
				}
			}.start();
		}
	}
}
