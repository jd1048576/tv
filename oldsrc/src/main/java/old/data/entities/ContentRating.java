package jdr.tvtracker.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentRating {
    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;
    @SerializedName("rating")
    @Expose
    private String rating;

    public String getIso31661() {
        return this.iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ContentRating{iso31661='");
        stringBuilder.append(this.iso31661);
        stringBuilder.append('\'');
        stringBuilder.append(", rating='");
        stringBuilder.append(this.rating);
        stringBuilder.append('\'');
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
                ContentRating that = (ContentRating) o;
                String str = this.iso31661;
                if (str != null) {
                    if (!str.equals(that.iso31661)) {
                    }
                    str = this.rating;
                    if (str != null) {
                        z = str.equals(that.rating);
                    } else if (that.rating != null) {
                        z = false;
                    }
                    return z;
                }
                return false;
            }
        }
        return false;
    }

    public int hashCode() {
        String str = this.iso31661;
        return str != null ? str.hashCode() : 0;
    }
}
