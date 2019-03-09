package com.cloudrider.semicolon.dto;

public class ChannelDTO {

    String channelTitle;
    String channelSubTitle;

    public ChannelDTO() {
    }

    public ChannelDTO(String channelTitle, String channelSubTitle) {
        this.channelTitle = channelTitle;
        this.channelSubTitle = channelSubTitle;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getChannelSubTitle() {
        return channelSubTitle;
    }

    public void setChannelSubTitle(String channelSubTitle) {
        this.channelSubTitle = channelSubTitle;
    }
}
