package com.example.recorddemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnCallStateChangedListener {

    //华为nova3系统通话录音路径地址
    private static final String HUAWEI_NOVA3_RECORD_PATH = "/Sounds/CallRecord/";
    private static final String TAG ="MainActivity";
    private CallingStateListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //请求获得存储功能权限
        PermisionUtils.verifyStoragePermissions(MainActivity.this);
        listener = CallingStateListener.getInstance(MainActivity.this);
        listener.setOnCallStateChangedListener(this);


        listener.startListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " );
        if (listener != null) {
            listener.stopListener();
        }
    }

    @Override
    public void onCallStateChanged(int state, String number) {
        switch (state) {
            case OnCallStateChangedListener.STATE_RINGING:
                Log.e(TAG, "当前状态为响铃，来电号码：" + number);
                //对应的数据交互方法
                break;
            case OnCallStateChangedListener.STATE_IN:
                Log.e(TAG, "当前状态为接听");
                //对应的数据交互方法
                break;
            case OnCallStateChangedListener.STATE_OUT:
                Log.e(TAG, "当前状态为拨打，拨打号码：" + number);
                //对应的数据交互方法
                break;
            case OnCallStateChangedListener.STATE_IDLE:
                Log.e(TAG, "当前状态为挂断");
                //对应的数据交互方法
                getRecordList();
                break;
        }

    }

    /**
     * 获取系统录音列表
     */
    private void getRecordList(){
        List<String> sNames = getRecordName();
        for(String s : sNames){
            Log.e(TAG, "系统录音列表信息为 " + s );
        }
    }

    private List<String> getRecordName(){
        //华为nova3机型手机录音之后的存储路径如下
        String path = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + HUAWEI_NOVA3_RECORD_PATH;
        Log.e(TAG, "getRecordName: " + path );
        File file = new File(path);
        File[] files=file.listFiles();
        if (files == null){
            Log.e("error","空目录");return null;}
        List<String> s = new ArrayList<>();
        for(int i =0;i<files.length;i++){
            s.add(files[i].getAbsolutePath());
        }
        return s;

    }
}
