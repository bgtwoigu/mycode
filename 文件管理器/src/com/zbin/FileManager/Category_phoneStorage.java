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
	 * // �洢�ļ����� private ArrayList<String> names = null; // �洢�ļ�·�� private
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
		// ��ʾ�ļ��б�
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
	 * findViewById(R.id.phoneStorage); // �����ǰĿ¼���Ǹ�Ŀ¼ if
	 * (!ROOT_PATH.equals(path)) { names.add("@1"); paths.add(ROOT_PATH);
	 * 
	 * names.add("@2"); paths.add(file.getParent()); } // ��������ļ� for (File f :
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
		 * // �洢�ļ����� private ArrayList<String> names = null; // �洢�ļ�·�� private
		 * ArrayList<String> paths = null;
		 */
		// ������ʼ��
		private List<storageinfo> storageInfos = null;

		public MyAdapter(Context context, List<storageinfo> storageInfos) {
			this.storageInfos = storageInfos;
			this.context = context;
			// directory = BitmapFactory.decodeResource(context.getResources(),
			// R.drawable.d);
			// file = BitmapFactory.decodeResource(context.getResources(),
			// R.drawable.f);
			// ��СͼƬ
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
 * �ļ����ڲ��ɶ� if (file.exists() && file.canRead()) { if (file.isDirectory()) { //
 * ��ʾ��Ŀ¼���ļ� showFileDir(path); } else { // �����ļ� fileHandle(file); } } // û��Ȩ��
 * else { Resources res = getResources(); new
 * AlertDialog.Builder(this).setTitle("Message")
 * .setMessage(res.getString(R.string.no_permission)) .setPositiveButton("OK",
 * new OnClickListener() {
 * 
 * @Override public void onClick(DialogInterface dialog, int which) {
 * 
 * } }).show(); } super.onListItemClick(l, v, position, id); }
 * 
 * // ���ļ�������ɾ�� private void fileHandle(final File file) { OnClickListener
 * listener = new DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick(DialogInterface dialog, int which) { // ���ļ� if
 * (which == 0) { openFile(file); } // �޸��ļ��� else if (which == 1) {
 * LayoutInflater factory = LayoutInflater .from(MainActivity.this); view =
 * factory.inflate(R.layout.rename_dialog, null); editText = (EditText)
 * view.findViewById(R.id.editText); editText.setText(file.getName());
 * 
 * OnClickListener listener2 = new DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick(DialogInterface dialog, int which) { // TODO
 * Auto-generated method stub String modifyName = editText.getText().toString();
 * final String fpath = file.getParentFile().getPath(); final File newFile = new
 * File(fpath + "/" + modifyName); if (newFile.exists()) { // �ų�û���޸���� if
 * (!modifyName.equals(file.getName())) { new
 * AlertDialog.Builder(MainActivity.this) .setTitle("ע��!")
 * .setMessage("�ļ����Ѵ��ڣ��Ƿ񸲸ǣ�") .setPositiveButton( "ȷ��", new
 * DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick( DialogInterface dialog, int which) { if
 * (file.renameTo(newFile)) { showFileDir(fpath); displayToast("�������ɹ���"); } else
 * { displayToast("������ʧ�ܣ�"); } } }) .setNegativeButton( "ȡ��", new
 * DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick( DialogInterface dialog, int which) {
 * 
 * } }).show(); } } else { if (file.renameTo(newFile)) { showFileDir(fpath);
 * displayToast("�������ɹ���"); } else { displayToast("������ʧ�ܣ�"); } } } }; AlertDialog
 * renameDialog = new AlertDialog.Builder( MainActivity.this).create();
 * renameDialog.setView(view); renameDialog.setButton("ȷ��", listener2);
 * renameDialog.setButton2("ȡ��", new DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick(DialogInterface dialog, int which) { // TODO
 * Auto-generated method stub
 * 
 * } }); renameDialog.show(); } // ɾ���ļ� else { new
 * AlertDialog.Builder(MainActivity.this) .setTitle("ע��!")
 * .setMessage("ȷ��Ҫɾ�����ļ���") .setPositiveButton("ȷ��", new
 * DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick( DialogInterface dialog, int which) { if
 * (file.delete()) { // �����ļ��б� showFileDir(file.getParent());
 * displayToast("ɾ���ɹ���"); } else { displayToast("ɾ��ʧ�ܣ�"); } } })
 * .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick( DialogInterface dialog, int which) {
 * 
 * } }).show(); } } }; // ѡ���ļ�ʱ��������ɾ�ò���ѡ��Ի��� String[] menu = { "���ļ�", "������",
 * "ɾ���ļ�" }; new AlertDialog.Builder(MainActivity.this).setTitle("��ѡ��Ҫ���еĲ���!")
 * .setItems(menu, listener) .setPositiveButton("ȡ��", new
 * DialogInterface.OnClickListener() {
 * 
 * @Override public void onClick(DialogInterface dialog, int which) {
 * 
 * } }).show(); }
 * 
 * // ���ļ� private void openFile(File file) { Intent intent = new Intent();
 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 * intent.setAction(android.content.Intent.ACTION_VIEW);
 * 
 * String type = getMIMEType(file); intent.setDataAndType(Uri.fromFile(file),
 * type); startActivity(intent); }
 * 
 * // ��ȡ�ļ�mimetype private String getMIMEType(File file) { String type = "";
 * String name = file.getName(); // �ļ���չ�� String end =
 * name.substring(name.lastIndexOf(".") + 1, name.length()) .toLowerCase(); if
 * (end.equals("m4a") || end.equals("mp3") || end.equals("wav")) { type =
 * "audio"; } else if (end.equals("mp4") || end.equals("3gp")) { type = "video";
 * } else if (end.equals("jpg") || end.equals("png") || end.equals("jpeg") ||
 * end.equals("bmp") || end.equals("gif")) { type = "image"; } else { //
 * ����޷�ֱ�Ӵ򿪣������б����û�ѡ�� type = "*"; } type += "/*"; return type; }
 * 
 * private void displayToast(String message) {
 * Toast.makeText(Category_phoneStorage.this, message, Toast.LENGTH_SHORT)
 * .show(); }
 */
// File[] files = new File(filePath).listFiles();
