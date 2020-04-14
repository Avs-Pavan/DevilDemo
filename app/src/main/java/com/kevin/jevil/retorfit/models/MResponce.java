package com.kevin.jevil.retorfit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MResponce {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("ad")
    @Expose
    private Ad ad;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

}
