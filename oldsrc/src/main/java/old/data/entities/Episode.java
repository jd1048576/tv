package jdr.tvtracker.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import jdr.tvtracker.data.local.converter.DateConverter;

@Entity(foreignKeys = {@ForeignKey(childColumns = {"showId"}, entity = Show.class, onDelete = 5, parentColumns = {"id"})}, indices = {@Index({"showId"})})
public class Episode {
    @SerializedName("air_date")
    @Expose
    @TypeConverters({DateConverter.class})
    private Date airDate;
    @SerializedName("episode_number")
    @Expose
    private int episodeNumber;
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("production_code")
    @Expose
    private String productionCode;
    @SerializedName("season_number")
    @Expose
    private int seasonNumber;
    private int showId;
    @SerializedName("still_path")
    @Expose
    private String stillPath;
    @SerializedName("vote_average")
    @Expose
    private double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private int voteCount;
    private boolean watched;

    public Episode(int id, int showId, int episodeNumber, int seasonNumber, String productionCode, String name, Date airDate, String overview, String stillPath, double voteAverage, int voteCount, boolean watched) {
        this.id = id;
        this.showId = showId;
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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShowId() {
        return this.showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
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

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Episode{id=");
        stringBuilder.append(this.id);
        stringBuilder.append(", showId=");
        stringBuilder.append(this.showId);
        stringBuilder.append(", episodeNumber=");
        stringBuilder.append(this.episodeNumber);
        stringBuilder.append(", seasonNumber=");
        stringBuilder.append(this.seasonNumber);
        stringBuilder.append(", productionCode='");
        stringBuilder.append(this.productionCode);
        stringBuilder.append('\'');
        stringBuilder.append(", name='");
        stringBuilder.append(this.name);
        stringBuilder.append('\'');
        stringBuilder.append(", airDate=");
        stringBuilder.append(this.airDate);
        stringBuilder.append(", overview='");
        stringBuilder.append(this.overview);
        stringBuilder.append('\'');
        stringBuilder.append(", stillPath='");
        stringBuilder.append(this.stillPath);
        stringBuilder.append('\'');
        stringBuilder.append(", voteAverage=");
        stringBuilder.append(this.voteAverage);
        stringBuilder.append(", voteCount=");
        stringBuilder.append(this.voteCount);
        stringBuilder.append('}');
        return stringBuilder.toString();
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
                Episode episode = (Episode) o;
                if (this.id != episode.id || this.showId != episode.showId || this.episodeNumber != episode.episodeNumber || this.seasonNumber != episode.seasonNumber) {
                    return false;
                }
                String str = this.name;
                if (str != null) {
                    if (!str.equals(episode.name)) {
                    }
                    Date date = this.airDate;
                    if (date != null) {
                        if (!date.equals(episode.airDate)) {
                        }
                        str = this.overview;
                        if (str != null) {
                            if (!str.equals(episode.overview)) {
                            }
                            str = this.stillPath;
                            if (str != null) {
                                z = str.equals(episode.stillPath);
                            } else if (episode.stillPath != null) {
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
        }
        return false;
    }

    public int hashCode() {
        return this.id;
    }
}
