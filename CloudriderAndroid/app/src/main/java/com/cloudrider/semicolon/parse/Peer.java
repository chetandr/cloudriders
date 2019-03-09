
package com.cloudrider.semicolon.parse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Peer {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ledger")
    @Expose
    private String ledger;
    @SerializedName("chaincodecount")
    @Expose
    private Integer chaincodecount;
    @SerializedName("chaincodes")
    @Expose
    private List<String> chaincodes = null;

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

    public Integer getChaincodecount() {
        return chaincodecount;
    }

    public void setChaincodecount(Integer chaincodecount) {
        this.chaincodecount = chaincodecount;
    }

    public List<String> getChaincodes() {
        return chaincodes;
    }

    public void setChaincodes(List<String> chaincodes) {
        this.chaincodes = chaincodes;
    }

}
