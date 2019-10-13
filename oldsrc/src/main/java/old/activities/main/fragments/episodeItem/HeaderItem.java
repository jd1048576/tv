package jdr.tvtracker.activities.main.fragments.episodeItem;

import java.util.Date;

import jdr.tvtracker.utils.DateUtil;

public class HeaderItem extends Item {
    private final String header;

    public HeaderItem(Date date) {
        this.header = new DateUtil().getHeaderString(date);
    }

    public HeaderItem(String header) {
        this.header = header;
    }

    public String getHeader() {
        return this.header;
    }

    public int getType() {
        return 0;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (o != null) {
            if (getClass() == o.getClass()) {
                HeaderItem that = (HeaderItem) o;
                String str = this.header;
                if (str != null) {
                    z = str.equals(that.header);
                } else if (that.header != null) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }

    public int hashCode() {
        String str = this.header;
        return str != null ? str.hashCode() : 0;
    }
}
