package com.zbin.FileManager;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.FileManager.R;
import com.zbin.Util.Category_image_Util;
import com.zbin.bean.Category_image_info;

public class Category_images extends Activity {
	private ListView listview;
	//private GridView gridView;

	private List<Category_image_info> imageInfos = null;

	private Category_image_info imageInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_main);

		listview = (ListView) findViewById(R.id.image_LIST);
		imageInfos = Category_image_Util.getImageInfos(Category_images.this);
		listview.setAdapter(new imagelistadpter(Category_images.this,
				imageInfos));
	}

	class imagelistadpter extends BaseAdapter {

		private Context context;
		private List<Category_image_info> imageInfos = null;

		public imagelistadpter(Context context,
				List<Category_image_info> imageInfos) {
			this.context = context;
			this.imageInfos = imageInfos;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageInfos.size();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = View.inflate(Category_images.this,
						R.layout.image_item, null);
				viewHolder.imageTitle = (TextView) convertView
						.findViewById(R.id.time);
				viewHolder.imageview = (ImageView) convertView
						.findViewById(R.id.VIEW);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();// 通过getTag的方法将数据取出来
			}
			imageInfo = imageInfos.get(position);
			// Bitmap bitmap =BitmapFactory.decodeFile(imageInfo.getPath());
		
			Bitmap bitmap = zoomImage(
					BitmapFactory.decodeFile(imageInfo.getPath()), 200, 200);

			viewHolder.imageview.setImageBitmap(bitmap);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//viewHolder.imageTitle.setText(imageInfo.getTitle());
			// Log.i("ahahahhhhhhhhhhhhhhhhhhhhhhhhhhhh", imageInfo.getTitle());
			// viewHolder.imageview.setImageURI(Uri.parse(imageInfo.getPath()));
			// // 显示艺术家

			return convertView;
		}

	}

	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
			double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}

	public class ViewHolder {
		// 所有控件对象引用
		public ImageView imageview; // 专辑图片
		public TextView imageTitle; // 歌曲名称

	}

}
