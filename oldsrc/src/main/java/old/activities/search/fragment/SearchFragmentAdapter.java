package jdr.tvtracker.activities.search.fragment;

import android.content.Context;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jdr.tvtracker.R;
import jdr.tvtracker.activities.show.ShowActivity;
import jdr.tvtracker.data.entities.Show;
import jdr.tvtracker.utils.CircularProgress;
import jdr.tvtracker.utils.GlideApp;
import jdr.tvtracker.utils.GlideRequest;
import jdr.tvtracker.utils.GlideRequests;

class SearchFragmentAdapter extends Adapter<ViewHolder> {
    private final Context context;
    private List<Show> showList = new ArrayList();

    class ShowViewHolder extends ViewHolder {
        final ImageView backdrop;
        final MaterialCardView cardView;
        final CircularProgress circularProgress;
        final TextView firstAirDate;
        final TextView name;

        ShowViewHolder(View view) {
            super(view);
            this.cardView = (MaterialCardView) view.findViewById(R.id.searchFragmentShowViewHolderCardView);
            this.backdrop = (ImageView) view.findViewById(R.id.searchFragmentShowViewHolderBackdropImageView);
            this.circularProgress = (CircularProgress) view.findViewById(R.id.searchFragmentShowViewHolderCircularProgress);
            this.name = (TextView) view.findViewById(R.id.searchFragmentShowViewHolderNameTextView);
            this.firstAirDate = (TextView) view.findViewById(R.id.searchFragmentShowViewHolderFirstAirDateTextView);
        }
    }

    SearchFragmentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_search_fragment_show_view_holder, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShowViewHolder showViewHolder = (ShowViewHolder) holder;
        final Show show = (Show) this.showList.get(position);
        if (show != null) {
            showViewHolder.cardView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(SearchFragmentAdapter.this.context, ShowActivity.class);
                    intent.putExtra("id", show.getId());
                    SearchFragmentAdapter.this.context.startActivity(intent);
                }
            });
            GlideRequests with = GlideApp.with(this.context);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.context.getString(R.string.high_res_backdrop_url));
            stringBuilder.append(show.getBackdropPath());
            GlideRequest placeholder = with.load(stringBuilder.toString()).placeholder((int) R.drawable.ic_placeholder_landscape);
            GlideRequests with2 = GlideApp.with(this.context);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(this.context.getString(R.string.low_res_backdrop_url));
            stringBuilder2.append(show.getBackdropPath());
            placeholder.thumbnail(with2.load(stringBuilder2.toString())).into(showViewHolder.backdrop);
            showViewHolder.circularProgress.setValue(show.getVoteAverage());
            showViewHolder.name.setText(show.getName());
            if (show.getFirstAirDate() != null) {
                showViewHolder.firstAirDate.setText(new SimpleDateFormat("yyyy", Locale.getDefault()).format(show.getFirstAirDate()));
            }
        }
    }

    public int getItemCount() {
        return this.showList.size();
    }

    void updateShowList(List<Show> showList) {
        int previousListSize = getItemCount();
        this.showList = showList;
        notifyItemRangeChanged(previousListSize - 1, getItemCount() - previousListSize);
    }
}
