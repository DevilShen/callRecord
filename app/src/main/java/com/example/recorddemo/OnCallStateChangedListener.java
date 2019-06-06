package com.example.recorddemo;
/**
 * 监听回调
 */
public interface OnCallStateChangedListener {
    int STATE_IDLE = 0;//已挂断
    int STATE_IN = 1;//正在接听（已接通）
    int STATE_OUT = 2;//正在拨打（已接通或未接通）
    int STATE_RINGING = 3;//未接听，正在响铃

    /**
     * @param state  状态
     * @param number 手机号
     */
    void onCallStateChanged(int state, String number);
}
