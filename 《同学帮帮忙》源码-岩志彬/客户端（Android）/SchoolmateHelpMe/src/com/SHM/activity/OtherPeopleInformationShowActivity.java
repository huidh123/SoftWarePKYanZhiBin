package com.SHM.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.SHM.constant.Constant;

public class OtherPeopleInformationShowActivity extends Activity {
	
	private TextView other_people_information_points;
	private ImageView other_people_information_pic;
	private TextView other_people_information_name;
	private ImageView other_people_information_sex;
	private TextView other_people_information_academe;
	private TextView other_people_information_profession;
	private TextView other_people_information_id;
	private TextView other_people_information_qqnumber;
	private TextView other_people_information_signature;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_other_people_information_show);
		
		Bundle bundle = this.getIntent().getExtras();
		int points = bundle.getInt("points");
		int picid = bundle.getInt("picid");
		String name = bundle.getString("name");
		int sex = bundle.getInt("sex");
		String academe = bundle.getString("academe");
		String profession = bundle.getString("profession");
		String id = bundle.getString("username");
		String qqnumber = bundle.getString("qqnumber");
		String signature = bundle.getString("signature");
		
		initView();
		
		other_people_information_points.setText(points + "");
		other_people_information_pic.setBackgroundResource(Constant.pics[picid]);
		other_people_information_name.setText(name);
		other_people_information_sex.setBackgroundResource(Constant.sexs[sex]);
		other_people_information_academe.setText(academe);
		other_people_information_profession.setText(profession);
		other_people_information_id.setText(id);
		
		if(qqnumber.equals("null")){
			other_people_information_qqnumber.setText("该同学没有留下QQ号码喲~");
		}else if(qqnumber.equals("")){
			other_people_information_qqnumber.setText("该同学没有留下QQ号码喲~");
		}else{
			other_people_information_qqnumber.setText(qqnumber);
		}
		if(signature.equals("null")){
			other_people_information_signature.setText("该同学比较懒，没有留下任何心情~");
		}else if(signature.equals("")){
			other_people_information_signature.setText("该同学比较懒，没有留下任何心情~");
		}else{
			other_people_information_signature.setText(signature);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		other_people_information_points = (TextView) findViewById(R.id.other_people_information_points);
		other_people_information_pic = (ImageView) findViewById(R.id.other_people_information_pic);
		other_people_information_name = (TextView) findViewById(R.id.other_people_information_name);
		other_people_information_sex = (ImageView) findViewById(R.id.other_people_information_sex);
		other_people_information_academe = (TextView) findViewById(R.id.other_people_information_academe);
		other_people_information_profession = (TextView) findViewById(R.id.other_people_information_profession);
		other_people_information_id = (TextView) findViewById(R.id.other_people_information_id);
		other_people_information_qqnumber = (TextView) findViewById(R.id.other_people_information_qqnumber);
		other_people_information_signature = (TextView) findViewById(R.id.other_people_information_signature);
		other_people_information_signature.setMovementMethod(new ScrollingMovementMethod());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.other_people_information_show, menu);
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
