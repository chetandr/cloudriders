
package com.cloudrider.semicolon.parse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChPeer {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ledger")
    @Expose
    private String ledger;
    @SerializedName("chaincodes")
    @Expose
    private Object chaincodes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLedger() {
        return ledger;
    }

    public void setLedger(String ledger) {
        this.ledger = ledger;
    }

    public Object getChaincodes() {
        return chaincodes;
    }

    public void setChaincodes(Object chaincodes) {
        this.chaincodes = chaincodes;
    }

}
