package com.example.asynctaskpractice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
//���͵�ַ��http://blog.csdn.net/dmk877/article/details/49366421
public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	ListView lvImooc;
	private String url="http://www.imooc.com/api/teacher?type=4&num=40";
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        lvImooc=(ListView) findViewById(R.id.lv_imooc);
        new NewsAsyncTask().execute(url);
    }
    
    private List<NewsBean> getJsonData(String url){
    	
    	List<NewsBean> newsBeanList=new ArrayList();
    	try {
			String jsonString = readStream(new URL(url).openStream());
			NewsBean newsBean;
			JSONObject jsonObject;
			
			try {
				jsonObject=new JSONObject(jsonString);
				JSONArray jsonArray = new JSONArray();
				jsonArray=jsonObject.getJSONArray("data");
				
				for(int i=0;i<jsonArray.length();i++){
					jsonObject = jsonArray.getJSONObject(i);
					newsBean=new NewsBean();
					newsBean.picSmall=jsonObject.getString("picSmall");
					newsBean.picBig=jsonObject.getString("picBig");
					newsBean.name=jsonObject.getString("name");
					newsBean.description=jsonObject.getString("description");
					newsBeanList.add(newsBean);
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
		return newsBeanList;
    	
    }
    
    /**
     * ͨ��inputstream������ҳ���ص�����
     * @param is
     * @return
     */
    private String readStream(InputStream is){
    	InputStreamReader isr;
    	StringBuffer result=new StringBuffer();
    	try {
    		String line="";
			isr=new InputStreamReader(is, "utf-8");
			BufferedReader br=new BufferedReader(isr);
			while((line=br.readLine())!=null){
				result.append(line);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return result.toString();
    }
    
    /**
     * ʵ��������첽����
     * @author Shimmer
     *
     */
    class NewsAsyncTask extends AsyncTask<String, Void, List<NewsBean>>{

		@Override
		protected List<NewsBean> doInBackground(String... params) {
			return getJsonData(params[0]);
		}
    	
		@Override
		protected void onPostExecute(List<NewsBean> result) {
			super.onPostExecute(result);
			
			NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this,result,lvImooc);
			lvImooc.setAdapter(newsAdapter);
			
		}
    }
    
}
