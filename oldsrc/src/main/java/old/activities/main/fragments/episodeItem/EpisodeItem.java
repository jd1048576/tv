package jdr.tvtracker.activities.main.fragments.episodeItem;

import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import jdr.tvtracker.data.entities.Episode;
import jdr.tvtracker.data.local.converter.DateConverter;

public class EpisodeItem extends Item {
    @TypeConverters({DateConverter.class})
    private Date airDate;
    private int episodeNumber;
    private int id;
    private String launchId;
    private int launchSource;
    private String name;
    private String overview;
    private String productionCode;
    private int seasonNumber;
    private int showId;
    private String showName;
    private String showPosterPath;
    private String stillPath;
    private double voteAverage;
    private int voteCount;
    private boolean watched;

    public EpisodeItem(int showId, String showName, String showPosterPath, String launchId, int launchSource, int id, int episodeNumber, int seasonNumber, String productionCode, String name, Date airDate, String overview, String stillPath, double voteAverage, int voteCount, boolean watched) {
        this.showId = showId;
        this.showName = showName;
        this.showPosterPath = showPosterPath;
        this.launchId = launchId;
        this.launchSource = launchSource;
        this.id = id;
        this.episodeNumber = episodeNumber;
        this.seasonNumber = seasonNumber;
        this.productionCode = productionCode;
        this.name = name;
        this.airDate = airDate;
        this.overview = overview;
        this.stillPath = stillPath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.watched = watched;
    }

    public int getShowId() {
        return this.showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public String getShowName() {
        return this.showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowPosterPath() {
        return this.showPosterPath;
    }

    public void setShowPosterPath(String showPosterPath) {
        this.showPosterPath = showPosterPath;
    }

    public String getLaunchId() {
        return this.launchId;
    }

    public void setLaunchId(String launchId) {
        this.launchId = launchId;
    }

    public int getLaunchSource() {
        return this.launchSource;
    }

    public void setLaunchSource(int launchSource) {
        this.launchSource = launchSource;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEpisodeNumber() {
        return this.episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public int getSeasonNumber() {
        return this.seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getProductionCode() {
        return this.productionCode;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAirDate() {
        return this.airDate;
    }

    public void setAirDate(Date airDate) {
        this.airDate = airDate;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getStillPath() {
        return this.stillPath;
    }

    public void setStillPath(String stillPath) {
        this.stillPath = stillPath;
    }

    public double getVoteAverage() {
        return this.voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return this.voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isWatched() {
        return this.watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public Episode getEpisode() {
        return new Episode(this.id, this.showId, this.episodeNumber, this.seasonNumber, this.productionCode, this.name, this.airDate, this.overview, this.stillPath, this.voteAverage, this.voteCount, this.watched);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("EpisodeItem{showId=");
        stringBuilder.append(this.showId);
        stringBuilder.append(", showName='");
        stringBuilder.append(this.showName);
        stringBuilder.append('\'');
        stringBuilder.append(", showPosterPath='");
        stringBuilder.append(this.showPosterPath);
        stringBuilder.append('\'');
        stringBuilder.append(", launchId='");
        stringBuilder.append(this.launchId);
        stringBuilder.append('\'');
        stringBuilder.append(", launchSource=");
        stringBuilder.append(this.launchSource);
        stringBuilder.append(", episode=");
        stringBuilder.append(getEpisode());
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public int getType() {
        return 1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (o != null) {
            if (getClass() == o.getClass()) {
                EpisodeItem that = (EpisodeItem) o;
                if (this.showId != that.showId || this.launchSource != that.launchSource || this.id != that.id || this.episodeNumber != that.episodeNumber || this.seasonNumber != that.seasonNumber || Double.compare(that.voteAverage, this.voteAverage) != 0 || this.voteCount != that.voteCount || this.watched != that.watched) {
                    return false;
                }
                String str = this.showName;
                if (str != null) {
                    if (!str.equals(that.showName)) {
                    }
                    str = this.showPosterPath;
                    if (str != null) {
                        if (!str.equals(that.showPosterPath)) {
                        }
                        str = this.launchId;
                        if (str != null) {
                            if (!str.equals(that.launchId)) {
                            }
                            str = this.productionCode;
                            if (str != null) {
                                if (!str.equals(that.productionCode)) {
                                }
                                str = this.name;
                                if (str != null) {
                                    if (!str.equals(that.name)) {
                                    }
                                    Date date = this.airDate;
                                    if (date != null) {
                                        if (!date.equals(that.airDate)) {
                                        }
                                        str = this.overview;
                                        if (str != null) {
                                            if (!str.equals(that.overview)) {
                                            }
                                            str = this.stillPath;
                                            if (str != null) {
                                                z = str.equals(that.stillPath);
                                            } else if (that.stillPath != null) {
                                                z = false;
                                            }
                                            return z;
                                        }
                                        return false;
                                    }
                                    return false;
                                }
                                return false;
                            }
                            return false;
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.id;
    }
}
