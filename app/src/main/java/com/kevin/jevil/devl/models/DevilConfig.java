package com.kevin.jevil.devl.models;

import android.content.Context;

public class DevilConfig {

    private boolean isLocalEnabled;
    private boolean isRealTimeEnabled;
    private String devilsAddress;
    private Context mContext;
    private String mTag;
    private String willTopic;
    private String userNameOrId;


    public DevilConfig(boolean isLocalEnabled, boolean isRealTimeEnabled, String devilsAddress, Context context, String mTag, String willTopic, String userNameOrId) {
        this.isLocalEnabled = isLocalEnabled;
        this.isRealTimeEnabled = isRealTimeEnabled;
        this.devilsAddress = devilsAddress;
        this.mContext = context;
        this.mTag = mTag;
        this.willTopic = willTopic;
        this.userNameOrId = userNameOrId;
    }

    public boolean isLocalEnabled() {
        return isLocalEnabled;
    }

    public void setLocalEnabled(boolean localEnabled) {
        isLocalEnabled = localEnabled;
    }

    public boolean isRealTimeEnabled() {
        return isRealTimeEnabled;
    }

    public void setRealTimeEnabled(boolean realTimeEnabled) {
        isRealTimeEnabled = realTimeEnabled;
    }

    public String getDevilsAddress() {
        return devilsAddress;
    }

    public void setDevilsAddress(String devilsAddress) {
        this.devilsAddress = devilsAddress;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String mTag) {
        this.mTag = mTag;
    }

    public String getWillTopic() {
        return willTopic;
    }

    public void setWillTopic(String willTopic) {
        this.willTopic = willTopic;
    }

    public String getUserNameOrId() {
        return userNameOrId;
    }

    public void setUserNameOrId(String userNameOrId) {
        this.userNameOrId = userNameOrId;
    }
}
