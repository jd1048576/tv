package jdr.tvtracker.data.remote.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import jdr.tvtracker.data.entities.ContentRating;

public class ContentRatingsResponse {
    @SerializedName("results")
    @Expose
    private List<ContentRating> contentRatingList = null;

    public List<ContentRating> getContentRatingList() {
        return this.contentRatingList;
    }

    public void setContentRatingList(List<ContentRating> contentRatingList) {
        this.contentRatingList = contentRatingList;
    }
}
