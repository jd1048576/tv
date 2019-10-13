package jdr.tvtracker.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.preference.PreferenceManager;

public class ShowLauncher {
    public static final int LAUNCH_SOURCE_AMAZON = 2;
    public static final int LAUNCH_SOURCE_CUSTOM = 3;
    public static final int LAUNCH_SOURCE_DEFAULT = 0;
    public static final int LAUNCH_SOURCE_NETFLIX = 1;
    private final SharedPreferences SP;
    private final Context context;
    private final int episodeNumber;
    private final String launchId;
    private final int launchSource;
    private final int seasonNumber;

    public ShowLauncher(Context context, int seasonNumber, int episodeNumber, String launchId, int launchSource) {
        this.context = context;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.launchId = launchId;
        this.launchSource = launchSource;
        this.SP = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void launch() {
        if (this.SP.getBoolean("launch", false)) {
            String str = this.launchId;
            if (str != null && !str.equals("")) {
                switch (this.launchSource) {
                    case 0:
                    case 3:
                        openWebPage();
                        return;
                    case 1:
                        openNetflix();
                        return;
                    case 2:
                        openAmazon();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private void openWebPage() {
        String launchUrl;
        String baseUrl = this.SP.getString("base_url", "");
        if (this.launchSource == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(baseUrl);
            stringBuilder.append(this.SP.getString("default_pattern", ""));
            launchUrl = stringBuilder.toString();
        } else {
            launchUrl = this.launchId;
        }
        ComponentName componentName = ComponentName.unflattenFromString(this.SP.getString("browser", ""));
        if (launchUrl.contains("{Base_URL}")) {
            launchUrl = launchUrl.replace("{Base_URL}", baseUrl);
        }
        if (launchUrl.contains("{Launch_ID}")) {
            launchUrl = launchUrl.replace("{Launch_ID}", this.launchId);
        }
        if (launchUrl.contains("{Season_Number}")) {
            launchUrl = launchUrl.replace("{Season_Number}", String.valueOf(this.seasonNumber));
        }
        if (launchUrl.contains("{Episode_Number}")) {
            launchUrl = launchUrl.replace("{Episode_Number}", String.valueOf(this.episodeNumber));
        }
        Intent webPageIntent = new Intent("android.intent.action.VIEW", Uri.parse(launchUrl));
        webPageIntent.setComponent(componentName);
        if (webPageIntent.resolveActivity(this.context.getPackageManager()) != null) {
            this.context.startActivity(webPageIntent);
        }
    }

    private void openNetflix() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://www.netflix.com/title/");
        stringBuilder.append(this.launchId);
        Intent netflixIntent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
        netflixIntent.setPackage("com.netflix.mediaclient");
        netflixIntent.setFlags(268468224);
        if (netflixIntent.resolveActivity(this.context.getPackageManager()) != null) {
            this.context.startActivity(netflixIntent);
        }
    }

    private void openAmazon() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("content://com.amazon.avod.detail/");
        stringBuilder.append(this.launchId);
        Intent amazonIntent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
        amazonIntent.setPackage("com.amazon.avod.thirdpartyclient");
        amazonIntent.setFlags(268468224);
        if (amazonIntent.resolveActivity(this.context.getPackageManager()) != null) {
            this.context.startActivity(amazonIntent);
        }
    }
}
