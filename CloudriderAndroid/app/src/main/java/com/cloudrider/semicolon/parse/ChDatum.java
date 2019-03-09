
package com.cloudrider.semicolon.parse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChDatum {

    @SerializedName("channelName")
    @Expose
    private String channelName;
    @SerializedName("org1")
    @Expose
    private Org1 org1;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Org1 getOrg1() {
        return org1;
    }

    public void setOrg1(Org1 org1) {
        this.org1 = org1;
    }

}
