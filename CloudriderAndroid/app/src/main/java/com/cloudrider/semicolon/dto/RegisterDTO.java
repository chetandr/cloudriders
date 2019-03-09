package com.cloudrider.semicolon.dto;

public class RegisterDTO {

    String orgName;
    String peers;
    String domain;
    String channel;
    String orderer;
    String ordererHost;
    String certType;

    public RegisterDTO() {
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPeers() {
        return peers;
    }

    public void setPeers(String peers) {
        this.peers = peers;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOrderer() {
        return orderer;
    }

    public void setOrderer(String orderer) {
        this.orderer = orderer;
    }

    public String getOrdererHost() {
        return ordererHost;
    }

    public void setOrdererHost(String ordererHost) {
        this.ordererHost = ordererHost;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }
}
