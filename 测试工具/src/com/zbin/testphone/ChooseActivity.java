package com.zbin.testphone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends Activity {
	Button mButton;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.choose);
				
		mButton=(Button)findViewById(R.id.mButton);
		 mButton.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
            	 
            	 
         	    String str = getPackageName();
        	    Log.e("song","package:"+str);
        	    Intent localIntent = new Intent("android.provider.Telephony.ACTION_CHANGE_DEFAULT");
        	    localIntent.putExtra("package", str);
        	    startActivity(localIntent);
            	 
            	 
            	 
                /* Intent intent =
                         new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                 intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, 
                         getPackageName());
                 startActivity(intent);*/
             }
         });
		
	}
	

}
