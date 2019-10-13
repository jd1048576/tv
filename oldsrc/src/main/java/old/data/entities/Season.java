package jdr.tvtracker.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Season {
    @SerializedName("episodes")
    @Expose
    private List<Episode> episodeList;
    @SerializedName("season_number")
    @Expose
    private int seasonNumber;

    public Season(int seasonNumber, List<Episode> episodeList) {
        this.seasonNumber = seasonNumber;
        this.episodeList = episodeList;
    }

    public int getSeasonNumber() {
        return this.seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public List<Episode> getEpisodeList() {
        return this.episodeList;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    public int getEpisodeCount() {
        List list = this.episodeList;
        return list != null ? list.size() : 0;
    }

    public void setWatchedEpisodeCount(int watchedEpisodeCount) {
        for (int i = 0; i < this.episodeList.size(); i++) {
            if (i < watchedEpisodeCount) {
                ((Episode) this.episodeList.get(i)).setWatched(true);
            } else {
                ((Episode) this.episodeList.get(i)).setWatched(false);
            }
        }
    }

    public int getWatchedEpisodeCount() {
        int watchedEpisodeCount = 0;
        List list = this.episodeList;
        if (list != null && list.size() > 0) {
            for (Episode episode : this.episodeList) {
                if (episode.isWatched()) {
                    watchedEpisodeCount++;
                }
            }
        }
        return watchedEpisodeCount;
    }

    public int getAiredEpisodeCount() {
        int airedEpisodeCount = 0;
        List list = this.episodeList;
        if (list != null && list.size() > 0) {
            for (Episode episode : this.episodeList) {
                if (episode.getAirDate().getTime() < Calendar.getInstance().getTimeInMillis()) {
                    airedEpisodeCount++;
                }
            }
        }
        return airedEpisodeCount;
    }

    public void addEpisode(Episode episode) {
        if (this.episodeList == null) {
            this.episodeList = new ArrayList();
        }
        this.episodeList.add(episode);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Season{seasonNumber=");
        stringBuilder.append(this.seasonNumber);
        stringBuilder.append(", episodeCount=");
        stringBuilder.append(getEpisodeCount());
        stringBuilder.append(", watchedEpisodeCount=");
        stringBuilder.append(getWatchedEpisodeCount());
        stringBuilder.append(", episodeList=");
        stringBuilder.append(this.episodeList);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
