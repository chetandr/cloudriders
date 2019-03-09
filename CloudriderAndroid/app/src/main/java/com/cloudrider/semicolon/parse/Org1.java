
package com.cloudrider.semicolon.parse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Org1 {

    @SerializedName("peers")
    @Expose
    private List<ChPeer> peers = null;

    public List<ChPeer> getPeers() {
        return peers;
    }

    public void setPeers(List<ChPeer> peers) {
        this.peers = peers;
    }

}
