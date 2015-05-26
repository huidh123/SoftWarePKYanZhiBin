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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.SHM.constant.Constant;
import com.SHM.tools.HttpUtil;

public class RankingListActivity extends Activity {
	
	private ListView listview;
	private Handler handler;
	private String isSucceed;
	private String resp_success;
	private ProgressDialog progressDialog;
	private int count;
	private TextView my_rank_points;
	private TextView my_rank_place;
	private TextView my_rank_isIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ranking_list);
		
		initView();
		
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				
				switch(msg.what) {
				case 0:
					
					progressDialog.cancel();
					
					final String[] resp_success_after = resp_success.split("9%!0#0");
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					count = (resp_success_after.length - 2)/10;
					int i = 0;
					for(i = 0;i < count - 1;i++){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("rank", i+1);
						map.put("rank_pic", Constant.pics[Integer.valueOf(resp_success_after[10 * i + 4])]);
				        map.put("rank_name", resp_success_after[10 * i + 3]);
				        map.put("rank_points", resp_success_after[10 * i + 10]);
				        list.add(map);
					}
					my_rank_points.setText(resp_success_after[10 * i + 10]);
					my_rank_place.setText((Integer.valueOf(resp_success_after[10 * i + 11]) + 1) + "");
					if(Integer.valueOf(resp_success_after[10 * i +11]) > 19){
						my_rank_isIn.setVisibility(View.GONE);
					}
					
					SimpleAdapter adapter = new SimpleAdapter(RankingListActivity.this,list,R.layout.rankinglistitem,
			                new String[]{"rank","rank_pic","rank_name","rank_points"},
			                new int[]{R.id.rank,R.id.rank_pic,R.id.rank_name,R.id.rank_points});
					listview.setAdapter(adapter);
					
					listview.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(RankingListActivity.this,OtherPeopleInformationShowActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("username", resp_success_after[position*10 + 2]);
							bundle.putString("name", resp_success_after[position*10 + 3]);
							bundle.putInt("picid", Integer.valueOf(resp_success_after[position*10 + 4]));
							bundle.putString("signature", resp_success_after[position*10 + 5]);
							bundle.putString("academe", resp_success_after[position*10 + 6]);
							bundle.putString("profession", resp_success_after[position*10 + 7]);
							bundle.putInt("sex", Integer.valueOf(resp_success_after[position*10 + 8]));
							bundle.putString("qqnumber", resp_success_after[position*10 + 9]);
							bundle.putInt("points", Integer.valueOf(resp_success_after[position*10 + 10]));
							intent.putExtras(bundle);
							startActivity(intent);
						}
					});
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
		
		createLoginProgressDialog();
		
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("username",Constant.username));
				
				String resp = null;
				resp = HttpUtil.sendPost(Constant.URL + "RankingListSearchServlet", param,HTTP.UTF_8);
				
				if(resp == null){
					handler.sendEmptyMessage(1);
				}else if(resp.length() < 7){
					handler.sendEmptyMessage(1);
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
		listview = (ListView) findViewById(R.id.ranking_list_listview);
		my_rank_points = (TextView) findViewById(R.id.my_rank_points);
		my_rank_place = (TextView) findViewById(R.id.my_rank_place);
		my_rank_isIn = (TextView) findViewById(R.id.my_rank_isIn);
	}
	
	private void createLoginProgressDialog(){
	    progressDialog = new ProgressDialog(RankingListActivity.this);
	    progressDialog.setMessage("加载中，请稍候...");
	    progressDialog.setCancelable(false);
	    progressDialog.show();
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ranking_list, menu);
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
