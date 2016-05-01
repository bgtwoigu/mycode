package com.example.testrssi;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WifiListActivity extends Activity {  
	  
    private WifiManager wifiManager;  
    List<ScanResult> list; 
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_wifi_list);  
        init();  
    }  
  
    private void init() {  
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);  
          
        openWifi();  
        list = wifiManager.getScanResults();  
        ListView listView = (ListView) findViewById(R.id.listView);  
        if (list == null) {  
            Toast.makeText(this, "wifiδ�򿪣�", Toast.LENGTH_LONG).show();  
        }else {  
            listView.setAdapter(new MyAdapter(this,list));
           /* listView.setOnItemClickListener(new OnItemClickListener() {  
            	  
                @Override  
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                        long arg3) {  
                    setTitle("�����"+arg2+"����Ŀ");  
                }

            });  */

        }  
          
    }  
      
    /** 
     *  ��WIFI 
     */  
    private void openWifi() {  
       if (!wifiManager.isWifiEnabled()) {  
        wifiManager.setWifiEnabled(true);  
       }  
        
    }  
  
    public class MyAdapter extends BaseAdapter {  
  
        LayoutInflater inflater;  
        List<ScanResult> list;  
        public MyAdapter(Context context, List<ScanResult> list) {  
            // TODO Auto-generated constructor stub  
            this.inflater = LayoutInflater.from(context);  
            this.list = list;  
        }  
  
        @Override  
        public int getCount() {  
            // TODO Auto-generated method stub  
            return list.size();  
        }  
  
        @Override  
        public Object getItem(int position) {  
            // TODO Auto-generated method stub  
            return position;  
        }  
  
        @Override  
        public long getItemId(int position) {  
            // TODO Auto-generated method stub  
            return position;  
        }  
  
        @Override  
        public View getView(int position, View convertView, ViewGroup parent) {  
            // TODO Auto-generated method stub 
        	/*
       getBSSID() ��ȡBSSID
       getDetailedStateOf() ��ȡ�ͻ��˵���ͨ��
       getHiddenSSID() ���SSID �Ƿ�����
       getIpAddress() ��ȡIP ��ַ
       getLinkSpeed() ������ӵ��ٶ�
       getMacAddress() ���Mac ��ַ
       getRssi() ���802.11n ������ź�
       getSSID() ���SSID
       getSupplicanState() ���ؾ���ͻ���״̬����Ϣ

        	 */
            View view = null;  
            view = inflater.inflate(R.layout.item_wifi_list, null);  
            ScanResult scanResult = list.get(position);  
            TextView textView = (TextView) view.findViewById(R.id.textView);  
            textView.setText(scanResult.SSID);  
            TextView signalStrenth = (TextView) view.findViewById(R.id.signal_strenth);
            //signalStrenth.setText(String.valueOf(Math.abs(scanResult.level)));
            
             
            StringBuffer mStringBuffer = null;
            mStringBuffer = new StringBuffer(); 
            //level ת��rssi ˵��http://www.zhihu.com/question/21106590
            //RSSI = level - NoiceFloor 
            //NoiceFloorһ��ȡ-96dBm
            //������� level �� -60dBm, RSSI ���� 36
            mStringBuffer = mStringBuffer.append("NO.").append(position + 1)   
                    .append(" SSID:").append(scanResult.SSID).append(" BSSID��")   
                    .append(scanResult.BSSID).append(" \n�������ܣ�")   
                    .append(scanResult.capabilities).append(" \nƵ�ʣ�")   
                    .append(scanResult.frequency).append("\nRSSI��")   
                    .append(scanResult.level+96).append(" \n����������")   
                    .append(scanResult.describeContents()).append("\n\n");
            signalStrenth.setText(mStringBuffer.toString());
            //mLabelWifi.setText(mStringBuffer.toString());
            System.out.println(mStringBuffer.toString());
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);  
            //�ж��ź�ǿ�ȣ���ʾ��Ӧ��ָʾͼ��  
            if (Math.abs(scanResult.level) > 100) {  
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_s3));  
            } else if (Math.abs(scanResult.level) > 80) {  
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_s2));  
            } else if (Math.abs(scanResult.level) > 70) {  
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_s2));  
            } else if (Math.abs(scanResult.level) > 60) {  
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_s2));  
            } else if (Math.abs(scanResult.level) > 50) {  
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_s1));  
            } else {  
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_s0));  
            }  
            return view;  
        }

  
    }  
  
} 
