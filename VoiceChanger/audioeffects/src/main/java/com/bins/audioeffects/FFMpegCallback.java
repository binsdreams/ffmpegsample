package com.bins.audioeffects;

public interface FFMpegCallback {

    void onSuccess(String[] commands);
    void onCancel(String[] commands);
    void onFailed(String[] commands,int rc,String out);

}
