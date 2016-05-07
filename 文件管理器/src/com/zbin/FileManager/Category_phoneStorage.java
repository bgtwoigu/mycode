package com.zbin.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.FileManager.R;
import com.zbin.bean.storageinfo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Category_phoneStorage extends Activity {
	private static final String ROOT_PATH = "/";
	private List<storageinfo> storageInfos = null;

	private storageinfo storageInfo;
	/*
	 * // 存储文件名称 private ArrayList<String> names = null; // 存储文件路径 private
	 * ArrayList<String> paths = null;
	 */
	private View view;
	private EditText editText;
	private ListView listview;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.phonestorage_main);
		listview = (ListView) findViewById(R.id.phoneStorage);
		// 显示文件列表
		// File f = new
		 String f=Environment.getDataDirectory().getPath();
		 Log.w("###############", f.toString());
		
		storageInfos = this.showfiles("/sdcard");
		listview.setAdapter(new MyAdapter(Category_phoneStorage.this, storageInfos));

	}

	/*
	 * private void showFileDir(String path) { names = new ArrayList<String>();
	 * paths = new ArrayList<String>(); File file = new File(path); File[] files
	 * = file.listFiles(); listview = (ListView)
	 * findViewById(R.id.phoneStorage); // 如果当前目录不是根目录 if
	 * (!ROOT_PATH.equals(path)) { names.add("@1"); paths.add(ROOT_PATH);
	 * 
	 * names.add("@2"); paths.add(file.getParent()); } // 添加所有文件 for (File f :
	 * files) { names.add(f.getName()); paths.add(f.getPath()); } //
	 * listview.setAdapter(new MyAdapter()); listview.setAdapter(new
	 * MyAdapter(Category_phoneStorage.this, names, paths));
	 * 
	 * }
	 */

	public List<storageinfo> showfiles(String f2) {
		List<storageinfo> storageInfos = new ArrayList<storageinfo>();
		storageInfo = new storageinfo();
		// Environment.getExternalStorageDirectory()
		// Log.w("zbin@@@@@@@@@@@@@@@@@@@@@@@", f.getName());
		File file = new File(f2);
		File[] files = file.listFiles();
		// Log.w("###############", files.toString());
		
		if (files == null) {
			storageInfo.setName("wo shi kong de ");
			storageInfos.add(storageInfo);
		} else {
			for (File f : files) {
			 if (f.isFile()) {
					Log.w("zbin........", f.getName());
					// if (f.getName().contains(".rc")) {
					storageInfo.setName(f.getName());
					storageInfo.setPath(f.getParent());
					storageInfo.setPath(f.getPath());
					Log.w("zbin........", f.getParent());
					Log.w("zbin........", f.getPath());
					Log.w("zbin........", f.getName());
					storageInfos.add(storageInfo);
				}
				
				 else { storageInfo.setName("no match files");
				  storageInfos.add(storageInfo); }
				 

			}
		}
		return storageInfos;
	}

	class MyAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private Bitmap directory, file;
		private Context context;
		/*
		 * // 存储文件名称 private ArrayList<String> names = null; // 存储文件路径 private
		 * ArrayList<String> paths = null;
		 */
		// 参数初始化
		private List<storageinfo> storageInfos = null;

		public MyAdapter(Context context, List<storageinfo> storageInfos) {
			this.storageInfos = storageInfos;
			this.context = context;
			// directory = BitmapFactory.decodeResource(context.getResources(),
			// R.drawable.d);
			// file = BitmapFactory.decodeResource(context.getResources(),
			// R.drawable.f);
			// 缩小图片
			// directory = small(directory, 0.16f);
			// file = small(file, 0.1f);
			// inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return storageInfos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return storageInfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (null == convertView) {
				convertView = View.inflate(context, R.layout.phonestorage_item, null);
				holder = new ViewHolder();
				holder.text = (TextView) convertView.findViewById(R.id.phonestoragename);
				holder.path = (TextView) convertView.findViewById(R.id.phonestoragepathname);
				// holder.image = (ImageView)
				// convertView.findViewById(R.id.imageView);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			storageInfo = storageInfos.get(position);
			holder.path.setText(storageInfo.getPath());
			holder.text.setText(storageInfo.getName());

			/*
			 * File f = new File(paths.get(position).toString()); if
			 * (names.get(position).equals("@1")) { holder.text.setText("/"); //
			 * holder.image.setImageBitmap(directory); } else if
			 * (names.get(position).equals("@2")) { holder.text.setText("..");
			 * // holder.image.setImageBitmap(directory); }
			 */ /*
				 * else { holder.text.setText(f.getName()); if (f.isDirectory())
				 * { //holder.image.setImageBitmap(directory); } else if
				 * (f.isFile()) { //holder.image.setImageBitmap(file); } else {
				 * System.out.println(f.getName()); }
				 */
			// }
			return convertView;
		}

		private class ViewHolder {
			private TextView text;
			private TextView path;
			private ImageView image;
		}

		/*
		 * private Bitmap small(Bitmap map, float num) { Matrix matrix = new
		 * Matrix(); matrix.postScale(num, num); return Bitmap.createBitmap(map,
		 * 0, 0, map.getWidth(), map.getHeight(), matrix, true); }
		 */
	}
}

/*
 * @Override protected void onListItemClick(ListView l, View v, int position,
 * long id) { String path = paths.get(position); File file = new File(path); //
 * 文件存在并可读 if (file.exists() && file.canRead()) { if (file.isDirectory()) { //
 * 显示子目录及文件 showFileDir(path); } else { // 处理文件 fileHandle(file); } } // 没有权限
 * else { Resources res = getResources(); new
 * AlertDialog.Builder(this).setTitle("Message")
 * .setMessage(res.getString(R.string.no_permission)) .setPositiveButton("OK",
 * new OnClickListener() {
 * 
 * @Override public void onClick(DialogInterface dialog, int which) {
 * 
 * } }).show(); } super.onListItemClick(l, v, position, id); }
 * 
 * // 对文件进行增删改 private void fileHandle(final File file) { OnClickListener
 * listener = new DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick(DialogInterface dialog, int which) { // 打开文件 if
 * (which == 0) { openFile(file); } // 修改文件名 else if (which == 1) {
 * LayoutInflater factory = LayoutInflater .from(MainActivity.this); view =
 * factory.inflate(R.layout.rename_dialog, null); editText = (EditText)
 * view.findViewById(R.id.editText); editText.setText(file.getName());
 * 
 * OnClickListener listener2 = new DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick(DialogInterface dialog, int which) { // TODO
 * Auto-generated method stub String modifyName = editText.getText().toString();
 * final String fpath = file.getParentFile().getPath(); final File newFile = new
 * File(fpath + "/" + modifyName); if (newFile.exists()) { // 排除没有修改情况 if
 * (!modifyName.equals(file.getName())) { new
 * AlertDialog.Builder(MainActivity.this) .setTitle("注意!")
 * .setMessage("文件名已存在，是否覆盖？") .setPositiveButton( "确定", new
 * DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick( DialogInterface dialog, int which) { if
 * (file.renameTo(newFile)) { showFileDir(fpath); displayToast("重命名成功！"); } else
 * { displayToast("重命名失败！"); } } }) .setNegativeButton( "取消", new
 * DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick( DialogInterface dialog, int which) {
 * 
 * } }).show(); } } else { if (file.renameTo(newFile)) { showFileDir(fpath);
 * displayToast("重命名成功！"); } else { displayToast("重命名失败！"); } } } }; AlertDialog
 * renameDialog = new AlertDialog.Builder( MainActivity.this).create();
 * renameDialog.setView(view); renameDialog.setButton("确定", listener2);
 * renameDialog.setButton2("取消", new DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick(DialogInterface dialog, int which) { // TODO
 * Auto-generated method stub
 * 
 * } }); renameDialog.show(); } // 删除文件 else { new
 * AlertDialog.Builder(MainActivity.this) .setTitle("注意!")
 * .setMessage("确定要删除此文件吗？") .setPositiveButton("确定", new
 * DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick( DialogInterface dialog, int which) { if
 * (file.delete()) { // 更新文件列表 showFileDir(file.getParent());
 * displayToast("删除成功！"); } else { displayToast("删除失败！"); } } })
 * .setNegativeButton("取消", new DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick( DialogInterface dialog, int which) {
 * 
 * } }).show(); } } }; // 选择文件时，弹出增删该操作选项对话框 String[] menu = { "打开文件", "重命名",
 * "删除文件" }; new AlertDialog.Builder(MainActivity.this).setTitle("请选择要进行的操作!")
 * .setItems(menu, listener) .setPositiveButton("取消", new
 * DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick(DialogInterface dialog, int which) {
 * 
 * } }).show(); }
 * 
 * // 打开文件 private void openFile(File file) { Intent intent = new Intent();
 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 * intent.setAction(android.content.Intent.ACTION_VIEW);
 * 
 * String type = getMIMEType(file); intent.setDataAndType(Uri.fromFile(file),
 * type); startActivity(intent); }
 * 
 * // 获取文件mimetype private String getMIMEType(File file) { String type = "";
 * String name = file.getName(); // 文件扩展名 String end =
 * name.substring(name.lastIndexOf(".") + 1, name.length()) .toLowerCase(); if
 * (end.equals("m4a") || end.equals("mp3") || end.equals("wav")) { type =
 * "audio"; } else if (end.equals("mp4") || end.equals("3gp")) { type = "video";
 * } else if (end.equals("jpg") || end.equals("png") || end.equals("jpeg") ||
 * end.equals("bmp") || end.equals("gif")) { type = "image"; } else { //
 * 如果无法直接打开，跳出列表由用户选择 type = "*"; } type += "/*"; return type; }
 * 
 * private void displayToast(String message) {
 * Toast.makeText(Category_phoneStorage.this, message, Toast.LENGTH_SHORT)
 * .show(); }
 */
// File[] files = new File(filePath).listFiles();
