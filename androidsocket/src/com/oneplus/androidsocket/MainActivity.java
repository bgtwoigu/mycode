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
	TextView tv;// 用来显示服务器返回的消息 ：“您好，客户端！”
	Handler mHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.btnconnect);// 连接服务器的按钮
		btn.setOnClickListener(listener);
		tv = (TextView) findViewById(R.id.tv);
		mHandler = new MessageHandler();
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			new ReceiveMessageThread().start();// 开启客户端线程
		}
	};

	/**
	 * 异步操作，TextView显示服务器发来的消息
	 * 
	 * @author S.Rain
	 * 
	 */
	class MessageHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			tv.setText(msg.obj.toString());// 接收客户端线程发来的Message对象，用来显示
		}

	}

	/**
	 * 客户端线程。连到服务器：192.168.1.102:9998 发送消息，之后接收服务器消息，并在TextView显示
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
				dos.writeUTF("您好，服务器！");

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
	public boolean onCreateOptionsMenu(Menu menu) {// 没用，工程创建时自己生成的
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}