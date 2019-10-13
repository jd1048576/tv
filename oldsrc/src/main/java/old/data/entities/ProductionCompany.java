package jdr.tvtracker.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCompany {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("logo_path")
    @Expose
    private String logoPath;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("origin_country")
    @Expose
    private String originCountry;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogoPath() {
        return this.logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginCountry() {
        return this.originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ProductionCompany{id=");
        stringBuilder.append(this.id);
        stringBuilder.append(", logoPath='");
        stringBuilder.append(this.logoPath);
        stringBuilder.append('\'');
        stringBuilder.append(", name='");
        stringBuilder.append(this.name);
        stringBuilder.append('\'');
        stringBuilder.append(", originCountry='");
        stringBuilder.append(this.originCountry);
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
                ProductionCompany that = (ProductionCompany) o;
                if (this.id != that.id) {
                    return false;
                }
                String str = this.logoPath;
                if (str != null) {
                    if (!str.equals(that.logoPath)) {
                    }
                    str = this.name;
                    if (str != null) {
                        if (!str.equals(that.name)) {
                        }
                        str = this.originCountry;
                        if (str != null) {
                            z = str.equals(that.originCountry);
                        } else if (that.originCountry != null) {
                            z = false;
                        }
                        return z;
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
