package com.zbin.Util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.zbin.bean.Category_image_info;

public class Category_image_Util  {
	private List<Category_image_info> imageInfos = null;

	private static Category_image_info imageInfo;
	
	public int getcount(Context context){
		Cursor cursor = context.getContentResolver().query(  
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,  
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);  
		return cursor.getCount();
		
		
		
	}
	public static List<Category_image_info> getImageInfos(Context context) {  
        Cursor cursor = context.getContentResolver().query(  
        		MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,  
                null );  
          
        List<Category_image_info> imageInfos = new ArrayList<Category_image_info>();  
        for (int i = 0; i < cursor.getCount(); i++) {  
            cursor.moveToNext();  
            imageInfo=new Category_image_info();
            Category_image_info musicInfo = new Category_image_info();  
            long id = cursor.getLong(cursor  
                    .getColumnIndex(MediaStore.Images.Media._ID));   //Í¼Æ¬id  
            String title = cursor.getString((cursor   
                    .getColumnIndex(MediaStore.Images.Media.DESCRIPTION))); 
            String path = cursor.getString(cursor  
                    .getColumnIndex(MediaStore.Images.Media.DATA)); // ÒÕÊõ¼Ò  
            //String uri=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media))
            Log.i("zbin", title+path);
            imageInfo.setId(id);
            imageInfo.setTitle(title);
            imageInfo.setPath(path);
            imageInfos.add(imageInfo);
            
            
        }  
        cursor.close();
        return imageInfos;  
    }  
}
