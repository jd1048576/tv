package jdr.tvtracker.utils;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import jdr.tvtracker.R;
import jdr.tvtracker.activities.show.ShowActivity;
import jdr.tvtracker.data.entities.Episode;

public class BottomSheet {
    private BottomSheetDialog bottomSheetDialog;
    private final Context context;
    private final Episode episode;
    private final String launchId;
    private final int launchSource;
    private final OnItemClickListener listener;
    private final boolean showLink;
    private final String showName;

    public interface OnItemClickListener {
        void onClick();
    }

    public BottomSheet(Context context, String showName, String launchId, int launchSource, Episode episode, boolean showLink, OnItemClickListener listener) {
        this.context = context;
        this.showName = showName;
        this.launchId = launchId;
        this.launchSource = launchSource;
        this.episode = episode;
        this.showLink = showLink;
        this.listener = listener;
    }

    public void show() {
        View bottomSheet = View.inflate(this.context, R.layout.bottom_sheet, null);
        this.bottomSheetDialog = new BottomSheetDialog(this.context);
        this.bottomSheetDialog.setContentView(bottomSheet);
        int height = this.context.getResources().getDisplayMetrics().heightPixels;
        bottomSheet.getLayoutParams().height = height - this.context.getResources().getDimensionPixelSize(R.dimen.status_bar);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheet.getParent());
        bottomSheetBehavior.setPeekHeight((height * 5) / 8);
        ImageView show = (ImageView) bottomSheet.findViewById(R.id.bottomSheetShowImageView);
        ImageView launch = (ImageView) bottomSheet.findViewById(R.id.bottomSheetLaunchImageView);
        ImageView popupMenu = (ImageView) bottomSheet.findViewById(R.id.bottomSheetPopupMenuImageView);
        ImageView still = (ImageView) bottomSheet.findViewById(R.id.bottomSheetStillImageView);
        TextView episodeName = (TextView) bottomSheet.findViewById(R.id.bottomSheetEpisodeNameTextView);
        TextView episodeDetails = (TextView) bottomSheet.findViewById(R.id.bottomSheetEpisodeDetailsTextView);
        TextView overview = (TextView) bottomSheet.findViewById(R.id.bottomSheetOverviewTextView);
        FloatingActionButton FAB = (FloatingActionButton) bottomSheet.findViewById(R.id.bottomSheetFloatingActionButton);
        ((TextView) bottomSheet.findViewById(R.id.bottomSheetShowNameTextView)).setText(this.showName);
        if (this.showLink) {
            show.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(BottomSheet.this.context, ShowActivity.class);
                    intent.putExtra("id", BottomSheet.this.episode.getShowId());
                    BottomSheet.this.context.startActivity(intent);
                }
            });
        } else {
            show.setVisibility(8);
        }
        if (!PreferenceManager.getDefaultSharedPreferences(r0.context).getBoolean("launch", false) || r0.launchId == null || r0.episode.getAirDate().getTime() >= Calendar.getInstance().getTimeInMillis()) {
            launch.setVisibility(8);
        } else {
            launch.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    new ShowLauncher(BottomSheet.this.context, BottomSheet.this.episode.getSeasonNumber(), BottomSheet.this.episode.getEpisodeNumber(), BottomSheet.this.launchId, BottomSheet.this.launchSource).launch();
                }
            });
            int i = r0.launchSource;
            if (!(i == 0 || i == 3)) {
                launch.setImageDrawable(r0.context.getDrawable(i == 1 ? R.drawable.ic_netflix : R.drawable.ic_amazon));
            }
        }
        popupMenu.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BottomSheet.this.showMenu(view);
            }
        });
        GlideRequests with = GlideApp.with(r0.context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(r0.context.getString(R.string.high_res_still_url));
        stringBuilder.append(r0.episode.getStillPath());
        GlideRequest placeholder = with.load(stringBuilder.toString()).placeholder((int) R.drawable.ic_placeholder_landscape);
        with = GlideApp.with(r0.context);
        stringBuilder = new StringBuilder();
        stringBuilder.append(r0.context.getString(R.string.low_res_still_url));
        stringBuilder.append(r0.episode.getStillPath());
        placeholder.thumbnail(with.load(stringBuilder.toString())).into(still);
        episodeName.setText(r0.episode.getName());
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());
        String episodeDetailsString = new StringBuilder();
        episodeDetailsString.append("S");
        episodeDetailsString.append(String.format(Locale.getDefault(), "%02d", new Object[]{Integer.valueOf(r0.episode.getSeasonNumber())}));
        episodeDetailsString.append("E");
        episodeDetailsString.append(String.format(Locale.getDefault(), "%02d", new Object[]{Integer.valueOf(r0.episode.getEpisodeNumber())}));
        episodeDetailsString.append(" • ");
        episodeDetailsString.append(sdf.format(r0.episode.getAirDate()));
        episodeDetails.setText(episodeDetailsString.toString());
        if (r0.episode.getOverview().equals("")) {
            overview.setText(R.string.description_currently_unavailable);
        } else {
            overview.setText(r0.episode.getOverview());
        }
        if (r0.episode.getAirDate().getTime() < Calendar.getInstance().getTimeInMillis()) {
            if (!r0.episode.isWatched()) {
                FAB.setImageDrawable(r0.context.getDrawable(R.drawable.ic_watch));
            }
            FAB.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    BottomSheet.this.bottomSheetDialog.dismiss();
                    BottomSheet.this.listener.onClick();
                }
            });
        } else {
            FAB.setVisibility(8);
        }
        r0.bottomSheetDialog.show();
    }

    private void showMenu(View view) {
        PopupMenu popup = new PopupMenu(this.context, view);
        popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                IntentSender intentSender = new IntentSender(BottomSheet.this.context);
                int itemId = item.getItemId();
                if (itemId == R.id.share) {
                    intentSender.share(BottomSheet.this.showName, BottomSheet.this.episode.getShowId());
                    return true;
                } else if (itemId != R.id.tmdb) {
                    return false;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("https://www.themoviedb.org/tv/");
                    stringBuilder.append(BottomSheet.this.episode.getShowId());
                    stringBuilder.append("/season/");
                    stringBuilder.append(BottomSheet.this.episode.getSeasonNumber());
                    stringBuilder.append("/episode/");
                    stringBuilder.append(BottomSheet.this.episode.getEpisodeNumber());
                    intentSender.openWebPage(stringBuilder.toString());
                    return true;
                }
            }
        });
        popup.inflate(R.menu.bottom_sheet_menu);
        popup.show();
    }
}
