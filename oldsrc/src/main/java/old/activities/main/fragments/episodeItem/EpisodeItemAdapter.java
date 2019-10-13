package jdr.tvtracker.activities.main.fragments.episodeItem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.DiffUtil.DiffResult;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jdr.tvtracker.R;
import jdr.tvtracker.data.entities.Episode;
import jdr.tvtracker.utils.BottomSheet;
import jdr.tvtracker.utils.DateUtil;
import jdr.tvtracker.utils.GlideApp;
import jdr.tvtracker.utils.GlideRequest;
import jdr.tvtracker.utils.GlideRequests;
import jdr.tvtracker.utils.ShowLauncher;

class EpisodeItemAdapter extends Adapter<ViewHolder> {
    private final int MODE;
    private final Context context;
    private final DateUtil dateUtil = new DateUtil();
    private List<Item> itemList = new ArrayList();
    private final OnItemClickListener listener;

    class EpisodeItemViewHolder extends ViewHolder {
        final MaterialCardView cardView;
        final TextView days;
        final TextView episodeDetails;
        final TextView episodeName;
        final ImageView poster;
        final TextView showName;
        boolean swipe;

        EpisodeItemViewHolder(View view) {
            super(view);
            this.cardView = (MaterialCardView) view.findViewById(R.id.EpisodeItemViewHolderCardView);
            this.poster = (ImageView) view.findViewById(R.id.EpisodeItemViewHolderPosterImageView);
            this.days = (TextView) view.findViewById(R.id.EpisodeItemViewHolderDaysTextView);
            this.showName = (TextView) view.findViewById(R.id.EpisodeItemViewHolderShowNameTextView);
            this.episodeDetails = (TextView) view.findViewById(R.id.EpisodeItemViewHolderEpisodeDetailsTextView);
            this.episodeName = (TextView) view.findViewById(R.id.EpisodeItemViewHolderEpisodeNameTextView);
        }
    }

    class HeaderViewHolder extends ViewHolder {
        final TextView header;

        HeaderViewHolder(View view) {
            super(view);
            this.header = (TextView) view.findViewById(R.id.headerViewHolderTextView);
        }
    }

    public interface OnItemClickListener {
        void onWatched(Episode episode);
    }

    EpisodeItemAdapter(Context context, int MODE, OnItemClickListener listener) {
        this.context = context;
        this.MODE = MODE;
        this.listener = listener;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_header_view_holder, parent, false));
        }
        return new EpisodeItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_episode_item_view_holder, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).header.setText(((HeaderItem) this.itemList.get(position)).getHeader());
        } else if (holder instanceof EpisodeItemViewHolder) {
            EpisodeItemViewHolder episodeItemViewHolder = (EpisodeItemViewHolder) holder;
            final EpisodeItem episodeItem = (EpisodeItem) this.itemList.get(position);
            final Episode episode = episodeItem.getEpisode();
            episodeItemViewHolder.swipe = episode.getAirDate().getTime() < Calendar.getInstance().getTimeInMillis();
            episodeItemViewHolder.cardView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    new BottomSheet(EpisodeItemAdapter.this.context, episodeItem.getShowName(), episodeItem.getLaunchId(), episodeItem.getLaunchSource(), episode, true, new jdr.tvtracker.utils.BottomSheet.OnItemClickListener() {
                        public void onClick() {
                            EpisodeItemAdapter.this.listener.onWatched(episode);
                        }
                    }).show();
                }
            });
            if (episode.getAirDate().getTime() >= Calendar.getInstance().getTimeInMillis() || episodeItem.getLaunchId() == null) {
                episodeItemViewHolder.cardView.setOnLongClickListener(null);
            } else {
                episodeItemViewHolder.cardView.setOnLongClickListener(new OnLongClickListener() {
                    public boolean onLongClick(View view) {
                        new ShowLauncher(EpisodeItemAdapter.this.context, episode.getSeasonNumber(), episode.getEpisodeNumber(), episodeItem.getLaunchId(), episodeItem.getLaunchSource()).launch();
                        return false;
                    }
                });
            }
            GlideRequests with = GlideApp.with(this.context);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.context.getString(R.string.high_res_poster_url));
            stringBuilder.append(episodeItem.getShowPosterPath());
            GlideRequest placeholder = with.load(stringBuilder.toString()).placeholder((int) R.drawable.ic_placeholder_portrait);
            GlideRequests with2 = GlideApp.with(this.context);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(this.context.getString(R.string.low_res_poster_url));
            stringBuilder2.append(episodeItem.getShowPosterPath());
            placeholder.thumbnail(with2.load(stringBuilder2.toString())).into(episodeItemViewHolder.poster);
            if (this.MODE == 0) {
                if (this.dateUtil.isInLastWeek(episode.getAirDate())) {
                    episodeItemViewHolder.poster.setColorFilter(0);
                    episodeItemViewHolder.days.setVisibility(8);
                } else {
                    episodeItemViewHolder.poster.setColorFilter(this.context.getColor(R.color.colorTint));
                    episodeItemViewHolder.days.setText(new DateUtil().getNumberOfDaysBetweenString(episode.getAirDate()));
                    episodeItemViewHolder.days.setVisibility(0);
                }
            }
            episodeItemViewHolder.showName.setText(episodeItem.getShowName());
            String episodeDetailsString = new StringBuilder();
            episodeDetailsString.append("S");
            episodeDetailsString.append(String.format(Locale.getDefault(), "%02d", new Object[]{Integer.valueOf(episode.getSeasonNumber())}));
            episodeDetailsString.append("E");
            episodeDetailsString.append(String.format(Locale.getDefault(), "%02d", new Object[]{Integer.valueOf(episode.getEpisodeNumber())}));
            episodeItemViewHolder.episodeDetails.setText(episodeDetailsString.toString());
            episodeItemViewHolder.episodeName.setText(episode.getName());
        }
    }

    public int getItemViewType(int position) {
        return ((Item) this.itemList.get(position)).getType();
    }

    public int getItemCount() {
        return this.itemList.size();
    }

    void updateItemList(List<Item> itemList) {
        DiffResult diffResult = DiffUtil.calculateDiff(new EpisodeItemDiffCallback(this.itemList, itemList), true);
        this.itemList = itemList;
        diffResult.dispatchUpdatesTo(this);
    }

    void updateEpisode(int position) {
        this.listener.onWatched(((EpisodeItem) this.itemList.get(position)).getEpisode());
    }
}
