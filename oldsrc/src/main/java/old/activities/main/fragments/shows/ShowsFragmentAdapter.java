package jdr.tvtracker.activities.main.fragments.shows;

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

import java.util.ArrayList;
import java.util.List;

import jdr.tvtracker.R;
import jdr.tvtracker.activities.show.ShowActivity;
import jdr.tvtracker.data.entities.ShowBasic;
import jdr.tvtracker.utils.GlideApp;
import jdr.tvtracker.utils.GlideRequest;
import jdr.tvtracker.utils.GlideRequests;

class ShowsFragmentAdapter extends Adapter<ViewHolder> {
    private final Context context;
    private List<ShowBasic> showList = new ArrayList();

    class ShowViewHolder extends ViewHolder {
        final MaterialCardView cardView;
        final ImageView poster;

        ShowViewHolder(View view) {
            super(view);
            this.cardView = (MaterialCardView) view.findViewById(R.id.showsFragmentShowViewHolderCardView);
            this.poster = (ImageView) view.findViewById(R.id.showsFragmentShowViewHolderPosterImageView);
        }
    }

    ShowsFragmentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_fragment_shows_show_view_holder, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShowViewHolder showViewHolder = (ShowViewHolder) holder;
        final ShowBasic show = (ShowBasic) this.showList.get(position);
        showViewHolder.cardView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ShowsFragmentAdapter.this.context, ShowActivity.class);
                intent.putExtra("id", show.getId());
                ShowsFragmentAdapter.this.context.startActivity(intent);
            }
        });
        GlideRequests with = GlideApp.with(this.context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.context.getString(R.string.high_res_poster_url));
        stringBuilder.append(show.getPosterPath());
        GlideRequest placeholder = with.load(stringBuilder.toString()).placeholder((int) R.drawable.ic_placeholder_portrait);
        GlideRequests with2 = GlideApp.with(this.context);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(this.context.getString(R.string.low_res_poster_url));
        stringBuilder2.append(show.getPosterPath());
        placeholder.thumbnail(with2.load(stringBuilder2.toString())).into(showViewHolder.poster);
    }

    public int getItemCount() {
        return this.showList.size();
    }

    void updateShowsList(List<ShowBasic> showList) {
        this.showList = showList;
        notifyDataSetChanged();
    }
}
