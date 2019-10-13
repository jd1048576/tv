package jdr.tvtracker.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtil {
    private final Calendar calendarNow = Calendar.getInstance();
    private final Calendar calendarTemp;
    private final Calendar calendarToCompare;
    private final SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());

    public DateUtil() {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        this.calendarTemp = Calendar.getInstance(timeZone);
        this.calendarToCompare = Calendar.getInstance(timeZone);
    }

    public String getHeaderString(Date date) {
        long numberOfDaysBetween = getNumberOfDaysBetween(date);
        if (numberOfDaysBetween == 0) {
            return "Today";
        }
        if (numberOfDaysBetween == 1) {
            return "Tomorrow";
        }
        if (numberOfDaysBetween < 7) {
            return this.sdf.format(date);
        }
        return "Later";
    }

    public boolean isInLastWeek(Date date) {
        return getNumberOfDaysBetween(date) < 7;
    }

    public String getNumberOfDaysBetweenString(Date date) {
        long numberOfDaysBetween = getNumberOfDaysBetween(date);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(numberOfDaysBetween));
        if (numberOfDaysBetween > 1) {
            stringBuilder.append("\nDays");
            return stringBuilder.toString();
        }
        stringBuilder.append("\nDay");
        return stringBuilder.toString();
    }

    private long getNumberOfDaysBetween(Date date) {
        this.calendarTemp.setTimeInMillis(date.getTime());
        this.calendarToCompare.set(this.calendarTemp.get(1), this.calendarTemp.get(2), this.calendarTemp.get(5));
        return TimeUnit.DAYS.convert(Math.abs(this.calendarToCompare.getTimeInMillis() - this.calendarNow.getTimeInMillis()), TimeUnit.MILLISECONDS);
    }
}
