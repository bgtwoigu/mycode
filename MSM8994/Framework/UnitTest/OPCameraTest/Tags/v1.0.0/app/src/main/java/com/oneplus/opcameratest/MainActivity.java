package com.oneplus.opcameratest;

import com.oneplus.camera.AutoTestConstants;
import com.oneplus.camera.IAutoTestService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.hardware.camera2.CameraManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "OPCameraTest";
    private boolean isTimerTestStartRunning = false;
    private Button btnStartTest;
    private Switch switchShowAdvancedSetting;
    private CheckBox checkBoxCapturePhoto;
    private CheckBox checkBoxCaptureVideo;
    private RadioGroup mRadioGroup;
    private CountDownTimer timerTestStart;
    private ExpandableListView expandableListView;
    private CamParameterAdapter mListAdapter;
    private Map<String, String> mapRearLensAvailableSetting = new HashMap<String, String>();
    private Map<String, String> mapFrontLensAvailableSetting = new HashMap<String, String>();
    private Map<String, String> mapAvailableSetting = new HashMap<String, String>();
    private List<String> listCameraSetting = new ArrayList<String>();

    private IAutoTestService mAutoTestService;
    private final ServiceConnection mAutoTestServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            onAutoTestServiceDisconnected();
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            onAutoTestServiceConnected(IAutoTestService.Stub.asInterface(service));
        }
    };

    private Handler mHandler;
    private boolean mIsReadyToTest;
    private final Runnable mCheckReadyStateRunnable = new Runnable() {
        @Override
        public void run() {
            if(mAutoTestService == null)
                return;
            try {
                if(mAutoTestService.getBooleanState(AutoTestConstants.STATE_KEY_IS_READY, false))
                    onReadyToTest();
                else
                    mHandler.postDelayed(mCheckReadyStateRunnable, 100);
            } catch(RemoteException ex) {
                ex.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mHandler = new Handler();
        connectToOPCameraService();
        initUI();
    }

    private void initUI() {
        timerTestStart = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isTimerTestStartRunning = true;
                btnStartTest.setText("test will start in "
                        + millisUntilFinished / 1000 + " secs. "
                        + "Click to cancel");
            }

            @Override
            public void onFinish() {
                isTimerTestStartRunning = false;
                btnStartTest.setText("Start test");
                btnStartTest.clearAnimation();
                startOPCameraTestSample();
            }
        };

        btnStartTest = (Button) findViewById(R.id.btnStartTest);
        btnStartTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isTimerTestStartRunning) {
                    isTimerTestStartRunning = false;
                    timerTestStart.cancel();
                    btnStartTest.clearAnimation();
                    btnStartTest.setText("Start test");
                }
                else {
                    startOPCameraTest();
                }
            }
        });

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroupLensFacing);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                initAdvancedSettingList();
            }
        });

        expandableListView = (ExpandableListView)findViewById(R.id.expandableListView);
        switchShowAdvancedSetting = (Switch) findViewById(R.id.switchAdvancedSetting);
        switchShowAdvancedSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    expandableListView.setVisibility(View.VISIBLE);
                }
                else {
                    expandableListView.setVisibility(View.INVISIBLE);
                }
            }
        });

        checkBoxCapturePhoto = (CheckBox) findViewById(R.id.checkBoxCapturePhoto);
        checkBoxCapturePhoto.setOnCheckedChangeListener(new CaptureModeOnCheckedChangeListener());
        checkBoxCaptureVideo = (CheckBox) findViewById(R.id.checkBoxCaptureVideo);
        checkBoxCaptureVideo.setOnCheckedChangeListener(new CaptureModeOnCheckedChangeListener());
    }

    private class CaptureModeOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton button, boolean isChecked){
            initAdvancedSettingList();
        }
    }

//    private void initAdvancedSettingList() {
//        groupList = new LinkedHashMap<Item,ArrayList<Item>>();
//
//        ArrayList<Item> groupsList = fetchGroups();
//        Log.d(TAG, "GroupsListSize: " + String.valueOf(groupsList.size()));
//
//        try {
//            for (Item itemGroup : groupsList) {
//                Log.d(TAG, "Add Group " + itemGroup.id);
//
//                ArrayList<Item> groupMembers = new ArrayList<Item>();
//                groupMembers.addAll(fetchGroupMembers(itemGroup.id));
//
//                itemGroup.name = itemGroup.name + " (" + groupMembers.size() + ")";
//                groupList.put(itemGroup, groupMembers);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        mAdapter = new ExpListViewAdapterWithCheckbox(this, expandableListView, groupList);
//        expandableListView.setAdapter(mAdapter);
//    }

    private void initAdvancedSettingList() {
        ArrayList<String> groupNames = new ArrayList<>();
        for (String strSettingName:listCameraSetting) {
            groupNames.add(strSettingName);
        }
        Log.d(TAG, "GroupsListSize: " + String.valueOf(groupNames.size()));

        ArrayList<ArrayList<CamParameter>> mListCamParameters = new ArrayList<>();
        for (String strGroupName:groupNames) {
            String strAvailablePar = "";
            ArrayList<CamParameter> mListCamParameter = new ArrayList<>();
            try {
                if (this.mRadioGroup.getCheckedRadioButtonId() == R.id.radioButtonRearCamera) {
                    strAvailablePar = mapRearLensAvailableSetting.get(strGroupName);
                } else if (this.mRadioGroup.getCheckedRadioButtonId() == R.id.radioButtonFrontCamera){
                    strAvailablePar = mapFrontLensAvailableSetting.get(strGroupName);
                }
                Log.d(TAG, "AvailablePar: " + strAvailablePar);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (!strAvailablePar.isEmpty()) {
                for (String strPar:strAvailablePar.split(";")) {
                    CamParameter mCamParameter = new CamParameter();
                    mCamParameter.Name = strPar;
                    mCamParameter.Id = strPar;

                    Pattern mR = Pattern.compile("Time-lapse|Slow-motion|Video");
                    if (mR.matcher(strPar).matches() | strGroupName.equalsIgnoreCase(AutoTestConstants.STATE_KEY_AVAILABLE_VIDEO_SIZES)) {
                        mCamParameter.Type = "VIDEO";
                    } else {
                        mCamParameter.Type = "PHOTO";
                    }

                    if (strGroupName.equalsIgnoreCase(AutoTestConstants.STATE_KEY_AVAILABLE_FLASH_MODES) & !strPar.equalsIgnoreCase("AUTO")) {
                        mCamParameter.Type = "PHOTO|VIDEO";
                    }

                    if (checkBoxCapturePhoto.isChecked() & mCamParameter.getType().contains("PHOTO")) {
                        mCamParameter.Enable = true;
                        mCamParameter.State = true;
                    }

                    if (checkBoxCaptureVideo.isChecked() & mCamParameter.getType().contains("VIDEO")) {
                        mCamParameter.Enable = true;
                        mCamParameter.State = true;
                    }

                    if (mCamParameter.getName().equalsIgnoreCase("Panorama") | mCamParameter.getName().equalsIgnoreCase("Manual")) {
                        mCamParameter.Name = strPar + " (TBD)";
                        mCamParameter.Enable = false;
                        mCamParameter.State = false;
                    }

                    mListCamParameter.add(mCamParameter);
                }
            }
            mListCamParameters.add(mListCamParameter);
        }

        mListAdapter = new CamParameterAdapter(this, groupNames, mListCamParameters);
        expandableListView.setAdapter(mListAdapter);
    }

    private void getAvailableCameraLensAndSetting() {
        String strAvailableCameraLens = "";
        listCameraSetting.add(AutoTestConstants.STATE_KEY_AVAILABLE_CAPTURE_MODES);
        listCameraSetting.add(AutoTestConstants.STATE_KEY_AVAILABLE_FLASH_MODES);
        listCameraSetting.add(AutoTestConstants.STATE_KEY_AVAILABLE_PHOTO_SIZES);
        listCameraSetting.add(AutoTestConstants.STATE_KEY_AVAILABLE_SCENES);
        listCameraSetting.add(AutoTestConstants.STATE_KEY_AVAILABLE_VIDEO_SIZES);

        mapAvailableSetting.put(AutoTestConstants.STATE_KEY_AVAILABLE_CAPTURE_MODES, AutoTestConstants.STATE_KEY_CAPTURE_MODE);
        mapAvailableSetting.put(AutoTestConstants.STATE_KEY_AVAILABLE_FLASH_MODES, AutoTestConstants.STATE_KEY_FLASH_MODES);
        mapAvailableSetting.put(AutoTestConstants.STATE_KEY_AVAILABLE_PHOTO_SIZES, AutoTestConstants.STATE_KEY_PHOTO_SIZE);
        mapAvailableSetting.put(AutoTestConstants.STATE_KEY_AVAILABLE_SCENES, AutoTestConstants.STATE_KEY_SCENE);
        mapAvailableSetting.put(AutoTestConstants.STATE_KEY_AVAILABLE_VIDEO_SIZES, AutoTestConstants.STATE_KEY_VIDEO_SIZE);

        try {
            mAutoTestService.start(AutoTestConstants.START_MODE_NORMAL, 0);
            while (!mAutoTestService.getBooleanState(AutoTestConstants.STATE_KEY_IS_READY, false)) {
                Thread.sleep(500);
            }

            strAvailableCameraLens = mAutoTestService.getStringState(AutoTestConstants.STATE_KEY_AVAILABLE_CAMERA_LENS_FACING, "NA");
            Log.d(TAG, "AvailableCamerLens: " + strAvailableCameraLens);

            if (!strAvailableCameraLens.isEmpty()) {
                for (String strCameraLens:strAvailableCameraLens.split(";")) {
                    Log.d(TAG, "CamerLens: " + strCameraLens);
                    mAutoTestService.setStringState(AutoTestConstants.STATE_KEY_CAMERA_LENS_FACING, strCameraLens);
                    while (!mAutoTestService.getStringState(AutoTestConstants.STATE_KEY_CAMERA_LENS_FACING, "NA").equalsIgnoreCase(strCameraLens)) {
                        Thread.sleep(500);
                    }

                    for (String strSettingName:listCameraSetting) {
                        Log.d(TAG, strCameraLens + ": " + strSettingName);
                        if (strCameraLens.equalsIgnoreCase("BACK")) {
                            mapRearLensAvailableSetting.put(strSettingName, mAutoTestService.getStringState(strSettingName, "NA"));
                        } else if (strCameraLens.equalsIgnoreCase("FRONT")) {
                            mapFrontLensAvailableSetting.put(strSettingName, mAutoTestService.getStringState(strSettingName, "NA"));
                        }
                    }
                }
            }

            mAutoTestService.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<Item> fetchGroups(){
        ArrayList<Item> groupList = new ArrayList<Item>();

        for (String strSettingName:listCameraSetting) {
            Item itemSetting = new Item();
            itemSetting.id = strSettingName;
            itemSetting.name = strSettingName;
            itemSetting.isChecked = true;
            groupList.add(itemSetting);
        }

        return groupList;
    }

    private ArrayList<Item> fetchGroupMembers(String groupId){
        ArrayList<Item> groupMembers = new ArrayList<Item>();
        String strAvailablePar = "";

        try {
//            strAvailablePar = mAutoTestService.getStringState(groupId, "N/A");
            if (this.mRadioGroup.getCheckedRadioButtonId() == R.id.radioButtonRearCamera) {
                strAvailablePar = mapRearLensAvailableSetting.get(groupId);
            } else if (this.mRadioGroup.getCheckedRadioButtonId() == R.id.radioButtonFrontCamera){
                strAvailablePar = mapFrontLensAvailableSetting.get(groupId);
            }
            Log.d(TAG, "AvailablePar: " + strAvailablePar);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (!strAvailablePar.isEmpty()) {
            for (String strPar:strAvailablePar.split(";")) {
                Item itemPar = new Item();
                itemPar.id = strPar;
                itemPar.name = strPar;
                itemPar.isChecked = true;
                groupMembers.add(itemPar);
            }
        }

        return groupMembers;
    }

    private void getTestList() {
        Map<String, String> mMapTestCon = new HashMap<>();
        Map<String, String> mMapTestPar = new LinkedHashMap<>();
        if (this.mRadioGroup.getCheckedRadioButtonId() == R.id.radioButtonRearCamera) {
            mMapTestCon.put("CAMERA_LENS_FACING", "BACK");
        } else if (this.mRadioGroup.getCheckedRadioButtonId() == R.id.radioButtonFrontCamera){
            mMapTestCon.put("CAMERA_LENS_FACING", "FRONT");
        }
        mMapTestCon.put("PHOTO_CAPTURE_NUM", ((EditText) findViewById(R.id.editTextPhotoNum)).getText().toString());
        mMapTestCon.put("VIDEO_CAPTURE_NUM", ((EditText) findViewById(R.id.editTextVideoMin)).getText().toString());

        if (this.checkBoxCapturePhoto.isChecked()) {
            Log.d(TAG, "Photo capture test checked");

            for (int i=0; i<mListAdapter.getGroupCount(); i++) {
                Log.d(TAG, "Check group " + (String) mListAdapter.getGroup(i));
                String mStrParValue = "";
                for (int j=0; j<mListAdapter.getChildrenCount(i); j++) {
                    CamParameter mCamParameter = (CamParameter) mListAdapter.getChild(i, j);
                    if (mCamParameter.getState() & mCamParameter.getType().contains("PHOTO")) {
                        Log.d(TAG,"Get checked parameter: " + mCamParameter.getName());
                        mStrParValue += mCamParameter.getName() + ";";
                    }
                }
                if (mStrParValue.length() > 0) {
                    mMapTestPar.put((String) mListAdapter.getGroup(i), mStrParValue);
                }
            }

            // check state
            if(mAutoTestService == null || mIsReadyToTest)
                return;

            // start
            try {
                Toast.makeText(this, "Test Start", Toast.LENGTH_LONG).show();
                mAutoTestService.start(AutoTestConstants.START_MODE_NORMAL, 0);
                while (!mAutoTestService.getBooleanState(AutoTestConstants.STATE_KEY_IS_READY, false)) {
                    Thread.sleep(500);
                }
                TestOperation curTestOperation = new TestOperation(this, mAutoTestService, mapAvailableSetting);

                List <List<Map<String, String>>> mArrayListParAll = new ArrayList<>();
                for (String strName:mMapTestPar.keySet()) {
                    List <Map<String, String>> mArrayListPar = new ArrayList<>();
                    for (String strValue:mMapTestPar.get(strName).split(";")) {
                        Map<String, String> mTmpMap = new HashMap<>();
                        mTmpMap.put(strName, strValue);
                        mArrayListPar.add(mTmpMap);
                    }
                    Log.d(TAG, "mArrayListPar: " + mArrayListPar.toString());
                    mArrayListParAll.add(mArrayListPar);
                }
                Log.d(TAG, "mArrayListParAll: " + mArrayListParAll.toString());

                MixedRangeCombinationIterable<Map> iterableCombination = new MixedRangeCombinationIterable<Map>(mArrayListParAll);
                for (List<Map> listPars:iterableCombination) {
                    Map<String, String> mResult = curTestOperation.capturePhoto(listPars, mMapTestCon.get("CAMERA_LENS_FACING"), Integer.parseInt(mMapTestCon.get("PHOTO_CAPTURE_NUM")), true);
                    if (mResult.get("Result").equalsIgnoreCase("failed")) {
                        Log.d(TAG, "Capture photo failed: " + mResult.get("ErrorMsg"));
                    }
                }

                mAutoTestService.stop();
            } catch(RemoteException ex) {
                Toast.makeText(this, "Fail to start : " + ex.getMessage(), Toast.LENGTH_LONG).show();
                return;
            } catch (Exception ex) {
                Log.d(TAG, ex.getMessage());
            }
        }

        // start check ready state
        mHandler.postDelayed(mCheckReadyStateRunnable, 100);
        Toast.makeText(this, "Test Finished", Toast.LENGTH_LONG).show();
    }

    private void startOPCameraTest() {
        getTestList();
    }

    private void startOPCameraTestSample() {
        // check state
        if(mAutoTestService == null || mIsReadyToTest)
            return;

        // start
        try {
            Toast.makeText(this, "Test Start", Toast.LENGTH_LONG).show();
            mAutoTestService.start(AutoTestConstants.START_MODE_NORMAL, 0);
            Thread.sleep(5000);
            mAutoTestService.stop();
        } catch(RemoteException ex) {
            Toast.makeText(this, "Fail to start : " + ex.getMessage(), Toast.LENGTH_LONG).show();
            return;
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }

        // start check ready state
        mHandler.postDelayed(mCheckReadyStateRunnable, 100);
        Toast.makeText(this, "Test Finished", Toast.LENGTH_LONG).show();
    }

    // Test Service

    // Connect to auto-test service.
    private void connectToOPCameraService() {
        // check state
        if(mAutoTestService != null)
            return;

        // connect
        Intent intent = new Intent();
        intent.setClassName("com.oneplus.camera", "com.oneplus.camera.AutoTestService");
        try {
            if (!this.bindService(intent, mAutoTestServiceConnection, Context.BIND_AUTO_CREATE))
                Toast.makeText(this, "Fail to connect to service", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Called when auto-test service connected.
    private void onAutoTestServiceConnected(IAutoTestService service) {
        Toast.makeText(this, "Service connected", Toast.LENGTH_LONG).show();
        mAutoTestService = service;

        Animation animationButton = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animationButton.setDuration(500); // duration - half a second
        animationButton.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animationButton.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animationButton.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in

        getAvailableCameraLensAndSetting();
        initAdvancedSettingList();
        btnStartTest.startAnimation(animationButton);
        timerTestStart.start();
    }


    // Called when auto-test service disconnected.
    private void onAutoTestServiceDisconnected() {
        Toast.makeText(this, "Service disconnected", Toast.LENGTH_LONG).show();
        mAutoTestService = null;
        mIsReadyToTest = false;
        mHandler.removeCallbacks(mCheckReadyStateRunnable);
    }

    // Called when destroying.
    @Override
    protected void onDestroy() {
        // disconnect auto-test service
        this.unbindService(mAutoTestServiceConnection);

        // call super
        super.onDestroy();
    }

    // Called when ready to test.
    private void onReadyToTest() {
        Toast.makeText(this, "Ready to test", Toast.LENGTH_LONG).show();
        mIsReadyToTest = true;
    }
}
