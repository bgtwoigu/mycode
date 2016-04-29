package com.zbin.blacknumber;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	Button add_number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BlackNumberDao dao = new BlackNumberDao(getApplicationContext());
	}

	public void init() {
		add_number = (Button) findViewById(R.id.add_number);
	}

	private static class ViewHolder {
		TextView tv_mode_item;
		TextView tv_number_item;
		ImageView delet_button_item;

	}
}