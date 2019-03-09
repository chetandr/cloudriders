package com.cloudrider.semicolon;

public class NotificationDTO {
    private String title;
    private String subtitle;
    private boolean approve;
    private boolean decline;

    public NotificationDTO(String title, String subtitle, boolean approve, boolean decline) {
        this.title = title;
        this.subtitle = subtitle;
        this.approve = approve;
        this.decline = decline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    public boolean isDecline() {
        return decline;
    }

    public void setDecline(boolean decline) {
        this.decline = decline;
    }
}
