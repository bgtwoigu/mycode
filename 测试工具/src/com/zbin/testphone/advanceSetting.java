package com.zbin.testphone;

import com.zbin.testphoneService.blackhomeService;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class advanceSetting extends Activity {
	private CheckBox blacknumbercheck;
	private SharedPreferences sp;
	private String checkstats = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.advacesetting);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		blacknumbercheck = (CheckBox) findViewById(R.id.blacknumbercheck);
		blacknumbercheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked)
					checkstats = "1";
				else
					checkstats = "2";

				if (checkstats.equals("1")) {
					blacknumbercheck.setChecked(true);
					SharedPreferences.Editor editor = sp.edit();
					editor.putString("stats", checkstats);
					editor.commit();
					Intent intent = new Intent(advanceSetting.this, blackhomeService.class);
					startService(intent);
				} else {
					blacknumbercheck.setChecked(false);
					SharedPreferences.Editor editor = sp.edit();
					editor.putString("stats", checkstats);
					editor.commit();
					Intent intent = new Intent(advanceSetting.this, blackhomeService.class);
					stopService(intent);
				}

			}
		});
		
		
	}
	
	
	

}
