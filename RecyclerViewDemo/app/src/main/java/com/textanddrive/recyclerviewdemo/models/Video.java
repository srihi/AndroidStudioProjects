package com.textanddrive.recyclerviewdemo.models;

/**
 * Created by Niraj on 21-02-2017.
 */

public class Video {
    int vidId;
    String name;
    String channelName;
    long duration;
    String thumbURL;

    public Video(int vidId, String name, String channelName, long duration, String thumbURL) {
        this.vidId = vidId;
        this.name = name;
        this.channelName = channelName;
        this.duration = duration;
        this.thumbURL = thumbURL;
    }

    public int getVidId() {
        return vidId;
    }

    public void setVidId(int vidId) {
        this.vidId = vidId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getThumbURL() {
        return thumbURL;
    }

    public void setThumbURL(String thumbURL) {
        this.thumbURL = thumbURL;
    }
}
