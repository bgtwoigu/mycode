package com.zbin.testphone;

import java.util.List;

import com.zbin.bean.BlackNumberInfo;
import com.zbin.dao.BlackNumberDao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class blackhome extends Activity {
	private BlackNumberAdpter adapter = null;
	private Button ok;
	private Button cancel;
	private EditText balcknumber_text;
	private String balcknumber;
	private AlertDialog dialog;
	private Button add;
	private TextView tv_home_item;
	private ListView list_home;
	private LinearLayout prressbar;
	private CheckBox checkphone, checksms;

	private BlackNumberDao blackNumberDao;
	private BlackNumberInfo info;
	private List<BlackNumberInfo> infos;
	private SharedPreferences sp;
	/*
	 * private Handler handler = new Handler() {
	 * 
	 * @Override public void handleMessage(Message msg) { // TODO Auto-generated
	 * method stub super.handleMessage(msg); if (adapter == null) { adapter =
	 * new BlackNumberAdpter(); list_home.setAdapter(adapter); } else {
	 * adapter.notifyDataSetChanged();// 通知数据适配器更新下界面。 }
	 * 
	 * }
	 * 
	 * };
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.black_home_main);
		sp = getSharedPreferences("config", MODE_WORLD_WRITEABLE);
		String stats=sp.getString("stats", "1");
		
		add = (Button) findViewById(R.id.add);
		list_home = (ListView) findViewById(R.id.list_home);
		prressbar = (LinearLayout) findViewById(R.id.progressbar);
		blackNumberDao = new BlackNumberDao(getApplicationContext());
		infos = blackNumberDao.findAll();
	
//		SharedPreferences.Editor editor = sp.edit();
//		editor.putString("stats", "");

		/*
		 * Intent intent = new Intent(blackhome.this, blackhomeService.class);
		 * startService(intent);
		 */
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog();

			}
		});
		// 设置listview的每一条的点击事件
		list_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				/*
				 * BlackNumberInfo info = infos.get(position);
				 * blackNumberDao.delete(info.getNumber());// 删除数据库的数据
				 * infos.remove(info);// 删除list集合里面的数据
				 * 
				 */ dialog();

				adapter.notifyDataSetChanged();

			}
		});
		if (adapter == null) {
			adapter = new BlackNumberAdpter();
			list_home.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();// 通知数据适配器更新下界面。
		}

	}

	/**
	 * 适配器
	 */

	private class BlackNumberAdpter extends BaseAdapter {

		@Override
		public int getCount() {

			return infos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = null;
			ViewHolder viewHolder;
			if (convertView != null) {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();

			} else {
				view = View.inflate(getApplicationContext(), R.layout.blacknumber_listview_item, null);
				viewHolder = new ViewHolder();
				viewHolder.tv_home_item = (TextView) view.findViewById(R.id.number_home_item);
				viewHolder.delet_button_item = (ImageView) view.findViewById(R.id.delet_button_item);
				view.setTag(viewHolder);

			}

			BlackNumberInfo info = infos.get(position);
			// String mode = info.getMode();

			viewHolder.tv_home_item.setText(info.getNumber());
			viewHolder.delet_button_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					BlackNumberInfo info = infos.get(position);
					blackNumberDao.delete(info.getNumber());// 删除数据库的数据
					infos.remove(info);// 删除list集合里面的数据
					adapter.notifyDataSetChanged();
					/*
					 * AlertDialog.Builder builder = new
					 * Builder(getApplicationContext()); builder.setTitle("提示");
					 * builder.setMessage("确定要删除么？");
					 * 
					 * builder.setPositiveButton("确定", new
					 * DialogInterface.OnClickListener() {
					 * 
					 * @Override public void onClick(DialogInterface dialog, int
					 * which) { BlackNumberInfo info = infos.get(position);
					 * blackNumberDao.delete(info.getNumber());// 删除数据库的数据
					 * infos.remove(info);// 删除list集合里面的数据
					 * adapter.notifyDataSetChanged();
					 * 
					 * } }); builder.setNegativeButton("取消", new
					 * DialogInterface.OnClickListener() {
					 * 
					 * @Override public void onClick(DialogInterface dialog, int
					 * which) { finish(); } });
					 */

				}
			});

			return view;
		}

	}

	private static class ViewHolder {
		TextView tv_home_item;
		ImageView delet_button_item;

	}

	/**
	 * 对话框
	 */
	public void dialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// LayoutInflater layoutInflater = getLayoutInflater();
		View DialogView = View.inflate(getApplicationContext(), R.layout.dailog, null);// inflate(R.layout.dailog,
																						// null);
		ok = (Button) DialogView.findViewById(R.id.ok);
		cancel = (Button) DialogView.findViewById(R.id.cancel);
		checkphone = (CheckBox) DialogView.findViewById(R.id.phonestop);
		checksms = (CheckBox) DialogView.findViewById(R.id.smsstop);
		balcknumber_text = (EditText) DialogView.findViewById(R.id.balcknumber_text);
		// balcknumber_text = (EditText) findViewById(R.id.balcknumber_text);

		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 设置checkbox的状态-----------------------------
				boolean id = checkphone.isChecked();
				boolean id1 = checksms.isChecked();
				String mode = "0";
				if (id)
					mode = "1";
				if (id1)
					mode = "2";
				if (id && id1)
					mode = "3";
				// 设置checkbox的状态-----------------------------

				// 点击确定加载数据到listview显示，
				balcknumber = balcknumber_text.getText().toString().trim();
				blackNumberDao.add(balcknumber, mode);

				// 销毁当前对话框
				dialog.dismiss();
				// 刷新界面。
				info = new BlackNumberInfo();
				info.setMode(mode);
				info.setNumber(balcknumber);
				// 把新的黑名单信息加入到当前界面的集合里面
				infos.add(info);
				// blackNumberDao.findall();
				adapter.notifyDataSetChanged();// 通知界面更新

			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog = builder.create();
		dialog.setView(DialogView, 0, 0, 0, 0);
		dialog.show();
	}

}
