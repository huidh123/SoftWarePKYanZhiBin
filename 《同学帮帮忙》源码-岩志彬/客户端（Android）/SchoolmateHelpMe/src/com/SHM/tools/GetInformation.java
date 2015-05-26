package com.SHM.tools;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import android.os.Handler;
import android.os.Message;
import com.SHM.constant.Constant;

public class GetInformation {
	
	private static Handler personHandler;
	private static String isSucceed;
	private static String resp_success;
	
	public static void getInformation() {
		
		personHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					String[] resp_success_after = resp_success.split("9%!0#0");
					Constant.id = Integer.valueOf(resp_success_after[1]);
					Constant.name = resp_success_after[2];
					Constant.picid = Integer.valueOf(resp_success_after[3]);
					Constant.signature = resp_success_after[4];
					Constant.academe = resp_success_after[5];
					Constant.profession = resp_success_after[6];
					Constant.sex = Integer.valueOf(resp_success_after[7]);
					Constant.qqnumber = resp_success_after[8];
					Constant.points = Integer.valueOf(resp_success_after[9]);
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
				
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("username",Constant.username));
				String resp = "fail";                               
				resp = HttpUtil.sendPost(Constant.URL + "GetInformationServlet", param,HTTP.UTF_8);
				if(resp == null){
					return;
				}else if(resp.length() < 7){
					return;
				}else{
					isSucceed = resp.substring(0,7);
					if(isSucceed.equals("success")){
						resp_success = resp;
						personHandler.sendEmptyMessage(0);
					}else{
						return;
					}
				}
			}
		}.start();
		
	}
}
