package com.oneplus.opcameratest;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.oneplus.camera.AutoTestConstants;
import com.oneplus.camera.IAutoTestService;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/21.
 */
public class TestOperation {
    private static final String TAG = "TestOperation";
    public IAutoTestService mAutoTestService = null;
    public Map<String, String> mapAvailableSetting = new HashMap<String, String>();
    private Context mContext;

    public TestOperation (Context mContext, IAutoTestService mAutoTestService, Map<String, String> mapAvailableSetting) {
        this.mAutoTestService = mAutoTestService;
        this.mapAvailableSetting = mapAvailableSetting;
        this.mContext = mContext;
    }

    public Map<String, String> capturePhoto (List<Map> listPars, String strCameraLensFacing, int intCaptureNum, boolean delCapturedMedia) {
        Map<String, String> mResult = new HashMap<>();
        mResult.put("Result", "failed");
        mResult.put("ErrorMsg", "");

        try {
            mAutoTestService.setStringState(AutoTestConstants.STATE_KEY_CAMERA_LENS_FACING, strCameraLensFacing);
            while (!mAutoTestService.getStringState(AutoTestConstants.STATE_KEY_CAMERA_LENS_FACING, "NA").equalsIgnoreCase(strCameraLensFacing)) {
                Thread.sleep(500);
            }
            Thread.sleep(2000);

            Log.d(TAG, "Set Elements: " + listPars);
            Map<String, String> mSetParametersResult = setCameraParameters(listPars);
            if (mSetParametersResult.get("Result").equalsIgnoreCase("failed")) {
                mResult.put("ErrorMsg", mSetParametersResult.get("ErrorMsg"));
                return mResult;
            }

            Log.d(TAG, "Start Capture Photo");
            for (int i=0; i<intCaptureNum; i++) {
                while (!mAutoTestService.getBooleanState(AutoTestConstants.STATE_KEY_IS_READY, false)) {
                    Thread.sleep(2000);
                }
                mAutoTestService.performAction(AutoTestConstants.ACTION_START_PHOTO_CAPTURE, 0);

                while (true) {
                    Thread.sleep(500);
                    if (!mAutoTestService.getStringState(AutoTestConstants.STATE_KEY_PHOTO_CAPTURE_STATE, "NA").equalsIgnoreCase("READY"))
                        continue;

                    if (mAutoTestService.getBooleanState(AutoTestConstants.STATE_KEY_IS_SAVING_MEDIA, true))
                        continue;

                    Thread.sleep(2000);
                    break;
                }

//                while (!mAutoTestService.getStringState(AutoTestConstants.STATE_KEY_PHOTO_CAPTURE_STATE, "NA").equalsIgnoreCase("READY")) {
//                    Log.d(TAG, "State PHOTO_CAPTURE_STATE: " + mAutoTestService.getStringState(AutoTestConstants.STATE_KEY_PHOTO_CAPTURE_STATE, "NA"));
//                    Thread.sleep(2000);
//                }

//                while (mAutoTestService.getBooleanState(AutoTestConstants.STATE_KEY_IS_SAVING_MEDIA, true)) {
//                    Log.d(TAG, "State IS_SAVING_MEDIA: " + mAutoTestService.getBooleanState(AutoTestConstants.STATE_KEY_IS_SAVING_MEDIA, false));
//                    Thread.sleep(2000);
//                }

                String strLastSavedMediaPath = mAutoTestService.getStringState(AutoTestConstants.STATE_KEY_LAST_SAVED_MEDIA, "");
                Log.d(TAG, "State LAST_SAVED_MEDIA: " + strLastSavedMediaPath);
                if (strLastSavedMediaPath.length() == 0 | !(new File(strLastSavedMediaPath)).exists()) {
                    mResult.put("ErrorMsg", "Can not find captured photo at " + strLastSavedMediaPath);
                    return mResult;
                }

                if (delCapturedMedia) {
                    if ((new File(strLastSavedMediaPath)).delete()) {
                        Log.d(TAG, "Media deleted: " + strLastSavedMediaPath);
                        callMediaScannerBroadCast(strLastSavedMediaPath);
                    } else {
                        Log.d(TAG, "Media not deleted: " + strLastSavedMediaPath);
                    }
                }
            }

        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
            mResult.put("ErrorMsg", ex.getMessage());
            return mResult;
        }

        mResult.put("Result", "pass");
        return mResult;
    }

    public Map<String, String> setCameraParameters (List<Map> listPars) {
        Map<String, String> mResult = new HashMap<>();
        mResult.put("Result", "failed");
        mResult.put("ErrorMsg", "");

        for (Map<String, String> mMapCurrPar : listPars) {
            String strName = "";
            String strValue = "";

            for (String strKey : mMapCurrPar.keySet()) {
                strName = mapAvailableSetting.get(strKey);
                strValue = mMapCurrPar.get(strKey);
            }

            Log.d(TAG, "Set element: " + strName + " to " + strValue);
            try {
                if (mAutoTestService.setStringState(strName, strValue)) {
                    Log.d(TAG, "Set element pass");
                    Thread.sleep(1000);
                    while (!mAutoTestService.getStringState(strName, "NA").equalsIgnoreCase(strValue)) {
                        Thread.sleep(500);
                    }
                } else {
                    Log.d(TAG, "Set element failed");
                    mResult.put("ErrorMsg", "Failed to set element: " + strName + " to " + strValue);
                    return mResult;
                }
            } catch (Exception ex) {
                Log.d(TAG, ex.getMessage());
                mResult.put("ErrorMsg", ex.getMessage());
                return mResult;
            }
        }

        mResult.put("Result", "pass");
        return mResult;
    }

    public void callMediaScannerBroadCast(String strToBeScanned) {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
//            strToBeScanned = "/storage/emulated/0/DCIM/Camera/";
//            strToBeScanned = new String[] { strToBeScanned }
            MediaScannerConnection.scanFile(mContext,
                    new String[] { Environment.getExternalStorageDirectory().toString() },
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                    Log.e("ExternalStorage", "Scanned " + path + ":");
                    Log.e("ExternalStorage", "-> uri=" + uri);
                }
            });
        } else {
            Log.e("-->", " < 14");
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }

}
