package com.zbin.lock;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

public class LockReceiver extends DeviceAdminReceiver {

	/* (non-Javadoc)
	 * @see android.app.admin.DeviceAdminReceiver#onEnabled(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onEnabled(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onEnabled(context, intent);
		  System.out.println("����ʹ��"); 
	}

	/* (non-Javadoc)
	 * @see android.app.admin.DeviceAdminReceiver#onDisabled(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onDisabled(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onDisabled(context, intent);
		 System.out.println("ȡ������"); 
	}

	/* (non-Javadoc)
	 * @see android.app.admin.DeviceAdminReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		   System.out.println("onreceiver"); 
	}

}
