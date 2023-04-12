package com.rovaindu.serviesdashboard.utils.views.chat.recordview;


public interface OnRecordListener {
    void onStart();
    void onCancel();
    void onFinish(long recordTime);
    void onLessThanSecond();
}
