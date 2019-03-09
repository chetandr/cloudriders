
package com.cloudrider.semicolon.parse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("consortiumname")
    @Expose
    private String consortiumname;
    @SerializedName("orgs")
    @Expose
    private List<Org> orgs = null;

    public String getConsortiumname() {
        return consortiumname;
    }

    public void setConsortiumname(String consortiumname) {
        this.consortiumname = consortiumname;
    }

    public List<Org> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<Org> orgs) {
        this.orgs = orgs;
    }

}
