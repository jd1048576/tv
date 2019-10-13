package jdr.tvtracker.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;
    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("site")
    @Expose
    private String site;
    @SerializedName("size")
    @Expose
    private int size;
    @SerializedName("type")
    @Expose
    private String type;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso6391() {
        return this.iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return this.iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return this.site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Video{id='");
        stringBuilder.append(this.id);
        stringBuilder.append('\'');
        stringBuilder.append(", iso6391='");
        stringBuilder.append(this.iso6391);
        stringBuilder.append('\'');
        stringBuilder.append(", iso31661='");
        stringBuilder.append(this.iso31661);
        stringBuilder.append('\'');
        stringBuilder.append(", key='");
        stringBuilder.append(this.key);
        stringBuilder.append('\'');
        stringBuilder.append(", name='");
        stringBuilder.append(this.name);
        stringBuilder.append('\'');
        stringBuilder.append(", site='");
        stringBuilder.append(this.site);
        stringBuilder.append('\'');
        stringBuilder.append(", size=");
        stringBuilder.append(this.size);
        stringBuilder.append(", type='");
        stringBuilder.append(this.type);
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
                Video video = (Video) o;
                if (this.size != video.size) {
                    return false;
                }
                String str = this.id;
                if (str != null) {
                    if (!str.equals(video.id)) {
                    }
                    str = this.iso6391;
                    if (str != null) {
                        if (!str.equals(video.iso6391)) {
                        }
                        str = this.iso31661;
                        if (str != null) {
                            if (!str.equals(video.iso31661)) {
                            }
                            str = this.key;
                            if (str != null) {
                                if (!str.equals(video.key)) {
                                }
                                str = this.name;
                                if (str != null) {
                                    if (!str.equals(video.name)) {
                                    }
                                    str = this.site;
                                    if (str != null) {
                                        if (!str.equals(video.site)) {
                                        }
                                        str = this.type;
                                        if (str != null) {
                                            z = str.equals(video.type);
                                        } else if (video.type != null) {
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
        }
        return false;
    }

    public int hashCode() {
        String str = this.id;
        return str != null ? str.hashCode() : 0;
    }
}
