package jdr.tvtracker.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalIds {
    @SerializedName("facebook_id")
    @Expose
    private String facebookId;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("instagram_id")
    @Expose
    private String instagramId;
    @SerializedName("tvdb_id")
    @Expose
    private int tvdbId;
    @SerializedName("twitter_id")
    @Expose
    private String twitterId;

    public String getImdbId() {
        return this.imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public int getTvdbId() {
        return this.tvdbId;
    }

    public void setTvdbId(int tvdbId) {
        this.tvdbId = tvdbId;
    }

    public String getFacebookId() {
        return this.facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getInstagramId() {
        return this.instagramId;
    }

    public void setInstagramId(String instagramId) {
        this.instagramId = instagramId;
    }

    public String getTwitterId() {
        return this.twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ExternalIds{imdbId='");
        stringBuilder.append(this.imdbId);
        stringBuilder.append('\'');
        stringBuilder.append(", tvdbId=");
        stringBuilder.append(this.tvdbId);
        stringBuilder.append(", facebookId=");
        stringBuilder.append(this.facebookId);
        stringBuilder.append(", instagramId=");
        stringBuilder.append(this.instagramId);
        stringBuilder.append(", twitterId=");
        stringBuilder.append(this.twitterId);
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
                ExternalIds that = (ExternalIds) o;
                if (this.tvdbId != that.tvdbId) {
                    return false;
                }
                String str = this.imdbId;
                if (str != null) {
                    if (!str.equals(that.imdbId)) {
                    }
                    str = this.facebookId;
                    if (str != null) {
                        if (!str.equals(that.facebookId)) {
                        }
                        str = this.instagramId;
                        if (str != null) {
                            if (!str.equals(that.instagramId)) {
                            }
                            str = this.twitterId;
                            if (str != null) {
                                z = str.equals(that.twitterId);
                            } else if (that.twitterId != null) {
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
        String str = this.imdbId;
        int i = 0;
        int hashCode = (((str != null ? str.hashCode() : 0) * 31) + this.tvdbId) * 31;
        String str2 = this.facebookId;
        int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        str2 = this.instagramId;
        hashCode = (hashCode2 + (str2 != null ? str2.hashCode() : 0)) * 31;
        str2 = this.twitterId;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode + i;
    }
}
