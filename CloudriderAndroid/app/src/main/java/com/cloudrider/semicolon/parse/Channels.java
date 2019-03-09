
package com.cloudrider.semicolon.parse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Channels {

    @SerializedName("data")
    @Expose
    private List<ChDatum> data = null;

    public List<ChDatum> getData() {
        return data;
    }

    public void setData(List<ChDatum> data) {
        this.data = data;
    }

}
