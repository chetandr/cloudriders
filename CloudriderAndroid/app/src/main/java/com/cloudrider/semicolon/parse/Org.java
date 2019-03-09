
package com.cloudrider.semicolon.parse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Org {

    @SerializedName("orgname")
    @Expose
    private String orgname;
    @SerializedName("peers")
    @Expose
    private List<Peer> peers = null;

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public List<Peer> getPeers() {
        return peers;
    }

    public void setPeers(List<Peer> peers) {
        this.peers = peers;
    }

}
