package com.oneplus.androidsocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView tv;// ������ʾ���������ص���Ϣ �������ã��ͻ��ˣ���
	Handler mHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.btnconnect);// ���ӷ������İ�ť
		btn.setOnClickListener(listener);
		tv = (TextView) findViewById(R.id.tv);
		mHandler = new MessageHandler();
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			new ReceiveMessageThread().start();// �����ͻ����߳�
		}
	};

	/**
	 * �첽������TextView��ʾ��������������Ϣ
	 * 
	 * @author S.Rain
	 * 
	 */
	class MessageHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			tv.setText(msg.obj.toString());// ���տͻ����̷߳�����Message����������ʾ
		}

	}

	/**
	 * �ͻ����̡߳�������������192.168.1.102:9998 ������Ϣ��֮����շ�������Ϣ������TextView��ʾ
	 * 
	 * @author S.Rain
	 * 
	 */
	String IP="172.21.8.194";
	class ReceiveMessageThread extends Thread {

		public void run() {
			try {
				InetAddress address=InetAddress.getByName(IP);
				Log.d("test", address.toString());
				Socket socket = new Socket(address, 9998);
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF("���ã���������");

				DataInputStream dis = new DataInputStream(socket.getInputStream());
				Message msg = mHandler.obtainMessage();
				msg.obj = dis.readUTF();
				mHandler.sendMessage(msg);
				socket.close();
				dos.close();
				dis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {// û�ã����̴���ʱ�Լ����ɵ�
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}