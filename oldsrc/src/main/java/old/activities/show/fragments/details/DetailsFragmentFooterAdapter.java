package jdr.tvtracker.activities.show.fragments.details;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import jdr.tvtracker.R;
import jdr.tvtracker.activities.show.ShowActivity;
import jdr.tvtracker.data.entities.ShowBasic;
import jdr.tvtracker.utils.GlideApp;
import jdr.tvtracker.utils.GlideRequest;
import jdr.tvtracker.utils.GlideRequests;

class DetailsFragmentFooterAdapter extends Adapter<ViewHolder> {
    static final int MODE_RECOMMENDED = 1;
    static final int MODE_SIMILAR = 2;
    private int MODE;
    private final Context context;
    private final List<ShowBasic> recommendationList;
    private final List<ShowBasic> similarList;

    class ShowViewHolder extends ViewHolder {
        final ConstraintLayout constraintLayout;
        final ImageView poster;

        ShowViewHolder(View view) {
            super(view);
            this.constraintLayout = (ConstraintLayout) view.findViewById(R.id.detailsFragmentFooterListViewConstraintLayout);
            this.poster = (ImageView) view.findViewById(R.id.detailsFragmentFooterListViewPosterImageView);
        }
    }

    DetailsFragmentFooterAdapter(Context context, List<ShowBasic> recommendationList, List<ShowBasic> similarList) {
        this.context = context;
        this.recommendationList = recommendationList;
        this.similarList = similarList;
        this.MODE = recommendationList.size() > 0 ? 1 : 2;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_fragment_details_footer_list_view_update, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShowViewHolder showViewHolder = (ShowViewHolder) holder;
        final ShowBasic show = (ShowBasic) (this.MODE == 1 ? this.recommendationList : this.similarList).get(position);
        showViewHolder.constraintLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DetailsFragmentFooterAdapter.this.context, ShowActivity.class);
                intent.putExtra("id", show.getId());
                DetailsFragmentFooterAdapter.this.context.startActivity(intent);
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
        switch (this.MODE) {
            case 1:
                return this.recommendationList.size();
            case 2:
                return this.similarList.size();
            default:
                return 0;
        }
    }

    int getMode() {
        return this.MODE;
    }

    void switchMode(int MODE) {
        this.MODE = MODE;
        notifyDataSetChanged();
    }
}
