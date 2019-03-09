package com.cloudrider.semicolon.dto;

public class PeerDTO {

    String peerTitle;
    String peerSubTitle;
    int chainCodeCount;
    int channelCount;

    public PeerDTO() {
    }

    public PeerDTO(String peerTitle, String peerSubTitle, int chainCodeCount, int lederCount) {
        this.peerTitle = peerTitle;
        this.peerSubTitle = peerSubTitle;
        this.chainCodeCount = chainCodeCount;
        this.channelCount = lederCount;
    }

    public String getPeerSubTitle() {
        return peerSubTitle;
    }

    public void setPeerSubTitle(String peerSubTitle) {
        this.peerSubTitle = peerSubTitle;
    }

    public int getChainCodeCount() {
        return chainCodeCount;
    }

    public void setChainCodeCount(int chainCodeCount) {
        this.chainCodeCount = chainCodeCount;
    }

    public int getChannelCount() {
        return channelCount;
    }

    public void setChannelCount(int lederCount) {
        this.channelCount = lederCount;
    }

    public String getPeerTitle() {
        return peerTitle;
    }

    public void setPeerTitle(String peerTitle) {
        this.peerTitle = peerTitle;
    }
}
