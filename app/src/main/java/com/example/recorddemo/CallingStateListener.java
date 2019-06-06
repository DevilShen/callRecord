package com.example.recorddemo;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * 管理电话的监听机制和回调
 */
public class CallingStateListener extends PhoneStateListener {

    private static CallingStateListener INSTANCE;

    private boolean isListening = false; //是否正在回调
    private int CALL_STATE = TelephonyManager.CALL_STATE_IDLE; //电话状态

    private TelephonyManager mTelephonyManager = null;
    /**
     * 回调
     */
    private OnCallStateChangedListener mOnCallStateChangedListener = null;

    /**
     * @param context 上下文
     */
    private CallingStateListener(Context context) {
        mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }


    private CallingStateListener() {
    }


    public static CallingStateListener getInstance(Context context) {
        if(null == INSTANCE){
            synchronized (CallingStateListener.class){
                INSTANCE = new CallingStateListener(context);
            }
        }
        return INSTANCE;
    }


    /**
     * 启动监听
     *
     * @return
     */
    public boolean startListener() {
        if (isListening) {
            return false;
        }
        isListening = true;
        mTelephonyManager.listen(this, PhoneStateListener.LISTEN_CALL_STATE);
        return true;
    }

    /**
     * 结束监听
     *
     * @return
     */
    public boolean stopListener() {
        if (!isListening) {
            return false;
        }
        isListening = false;
        mTelephonyManager.listen(this, PhoneStateListener.LISTEN_NONE);
        return true;
    }

    /**
     * @param state       状态
     * @param mobilePhone 手机号
     */
    @Override
    public void onCallStateChanged(int state, String mobilePhone) {
        switch (state) {
            //当前状态为挂断
            case TelephonyManager.CALL_STATE_IDLE:
                if (mOnCallStateChangedListener != null) {
                    mOnCallStateChangedListener.onCallStateChanged(OnCallStateChangedListener.STATE_IDLE, mobilePhone);
                }
                CALL_STATE = state;
                break;
            //当前状态为接听或拨打
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if (mOnCallStateChangedListener != null) {
                    mOnCallStateChangedListener.onCallStateChanged(CALL_STATE == TelephonyManager.CALL_STATE_RINGING ?
                            OnCallStateChangedListener.STATE_IN : OnCallStateChangedListener.STATE_OUT, mobilePhone);
                }
                CALL_STATE = state;
                break;
            //当前状态为响铃
            case TelephonyManager.CALL_STATE_RINGING:
                if (mOnCallStateChangedListener != null) {
                    mOnCallStateChangedListener.onCallStateChanged(OnCallStateChangedListener.STATE_RINGING, mobilePhone);
                }
                CALL_STATE = state;
                break;
        }
    }


    /**
     * 监听回调
     *
     * @param onCallStateChangedListener OnCallStateChangedListener
     */
    public void setOnCallStateChangedListener(OnCallStateChangedListener onCallStateChangedListener) {
        this.mOnCallStateChangedListener = onCallStateChangedListener;
    }

}