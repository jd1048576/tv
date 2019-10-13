package jdr.tvtracker.data.remote.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import jdr.tvtracker.data.entities.Video;

public class VideosResponse {
    @SerializedName("results")
    @Expose
    private List<Video> videoList = null;

    public List<Video> getVideoList() {
        return this.videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}
