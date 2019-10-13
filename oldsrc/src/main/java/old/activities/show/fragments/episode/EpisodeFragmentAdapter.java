package jdr.tvtracker.activities.show.fragments.episode;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jdr.tvtracker.R;
import jdr.tvtracker.data.entities.Episode;
import jdr.tvtracker.data.entities.Show;
import jdr.tvtracker.utils.BottomSheet;
import jdr.tvtracker.utils.DateUtil;

class EpisodeFragmentAdapter extends Adapter<ViewHolder> {
    private final Context context;
    private List<Episode> episodeList;
    private final OnItemClickListener listener;
    private Show show;

    class EpisodeViewHolder extends ViewHolder {
        final MaterialCardView cardView;
        final ImageView check;
        final TextView days;
        final TextView episodeName;
        final TextView episodeNumber;

        EpisodeViewHolder(View view) {
            super(view);
            this.cardView = (MaterialCardView) view.findViewById(R.id.episodeFragmentEpisodeViewHolderCardView);
            this.episodeNumber = (TextView) view.findViewById(R.id.episodeFragmentEpisodeViewHolderEpisodeNumberTextView);
            this.episodeName = (TextView) view.findViewById(R.id.episodeFragmentEpisodeViewHolderEpisodeNameTextView);
            this.check = (ImageView) view.findViewById(R.id.episodeFragmentEpisodeViewHolderCheckImageView);
            this.days = (TextView) view.findViewById(R.id.episodeFragmentEpisodeViewHolderDaysTextView);
        }
    }

    public interface OnItemClickListener {
        void onEpisodeClick(int i);
    }

    EpisodeFragmentAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpisodeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_fragment_episode_episode_view_holder, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final EpisodeViewHolder episodeViewHolder = (EpisodeViewHolder) holder;
        final Episode episode = (Episode) this.episodeList.get(position);
        episodeViewHolder.cardView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                new BottomSheet(EpisodeFragmentAdapter.this.context, EpisodeFragmentAdapter.this.show.getName(), EpisodeFragmentAdapter.this.show.getLaunchId(), EpisodeFragmentAdapter.this.show.getLaunchSource(), episode, false, new jdr.tvtracker.utils.BottomSheet.OnItemClickListener() {
                    public void onClick() {
                        EpisodeFragmentAdapter.this.listener.onEpisodeClick(episodeViewHolder.getAdapterPosition());
                    }
                }).show();
            }
        });
        if (episode.getAirDate().getTime() > Calendar.getInstance().getTimeInMillis()) {
            episodeViewHolder.check.setVisibility(8);
            episodeViewHolder.days.setText(new DateUtil().getNumberOfDaysBetweenString(episode.getAirDate()));
            episodeViewHolder.days.setVisibility(0);
        } else {
            episodeViewHolder.check.setVisibility(0);
            episodeViewHolder.check.setSelected(episode.isWatched());
            episodeViewHolder.check.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    EpisodeFragmentAdapter.this.listener.onEpisodeClick(episodeViewHolder.getAdapterPosition());
                }
            });
            episodeViewHolder.days.setVisibility(8);
        }
        episodeViewHolder.episodeNumber.setText(String.format(Locale.getDefault(), "%02d", new Object[]{Integer.valueOf(episode.getEpisodeNumber())}));
        episodeViewHolder.episodeName.setText(episode.getName());
    }

    public int getItemCount() {
        return this.episodeList.size();
    }

    void updateShow(Show show) {
        this.show = show;
    }

    void updateEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
        notifyDataSetChanged();
    }
}
