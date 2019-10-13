package jdr.tvtracker.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import jdr.tvtracker.R;

public class IntentSender {
    private final Context context;

    public IntentSender(Context context) {
        this.context = context;
    }

    public void openWebPage(String url) {
        Intent webPageIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if (webPageIntent.resolveActivity(this.context.getPackageManager()) != null) {
            this.context.startActivity(webPageIntent);
        }
    }

    public void share(String showName, int showId) {
        Intent sendIntent = new Intent("android.intent.action.SEND");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Follow ");
        stringBuilder.append(showName);
        stringBuilder.append(" on TV Tracker\nhttps://tvtracker.jdr/show/");
        stringBuilder.append(showId);
        sendIntent.putExtra("android.intent.extra.TEXT", stringBuilder.toString());
        sendIntent.setType("text/plain");
        Context context = this.context;
        context.startActivity(Intent.createChooser(sendIntent, context.getString(R.string.share)));
    }
}
